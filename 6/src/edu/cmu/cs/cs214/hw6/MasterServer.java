package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.StaffUtils;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * This class represents the "master server" in the distributed map/reduce
 * framework. The {@link MasterServer} is in charge of managing the entire
 * map/reduce computation from beginning to end. The {@link MasterServer}
 * listens for incoming client connections on a distinct host/port address, and
 * is passed an array of {@link WorkerInfo} objects when it is first initialized
 * that provides it with necessary information about each of the available
 * workers in the system (i.e. each worker's name, host address, port number,
 * and the set of {@link Partition}s it stores). A single map/reduce computation
 * managed by the {@link MasterServer} will typically behave as follows:
 *
 * <ol>
 * <li>Wait for the client to submit a map/reduce task.</li>
 * <li>Distribute the {@link MapTask} across a set of "map-workers" and wait for
 * all map-workers to complete.</li>
 * <li>Distribute the {@link ReduceTask} across a set of "reduce-workers" and
 * wait for all reduce-workers to complete.</li>
 * <li>Write the locations of the final results files back to the client.</li>
 * </ol>
 */
public class MasterServer extends Thread {
	private static final String TAG = "MasterServer";

	/** Create at most one thread per available processor on this machine. */
	private static final int MAX_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors();

	private final int mPort;
	private final List<WorkerInfo> mWorkers;

	private ExecutorService mExecutorClient;
	private ExecutorService mExecutorWorker;

	// after mapping, some partitions are done while others are not, this is to
	// record if any partition is done, to avoid being mapped by various map
	// workers
	private Map<Partition, Boolean> partitionStatus;
	// record each worker's availability
	private Map<WorkerInfo, Boolean> workersAvailability;
	// list of available workers, the index is hash code
	private List<WorkerInfo> availableWorkers;
	// list of shuffled paths, an index is the hash code of a list of paths
	// this should be done after shuffling, and used for reducing
	private List<List<String>> shuffledPaths;
	// Record each shuffleResultPath with each WorkerInfo
	private Map<String, WorkerInfo> shufflePathWorkers;
	// record each worker's intermediate results' paths
	// this should be built after mapping, and used for shuffling
	private Map<WorkerInfo, List<String>> workersInterPaths;
	// record the final results of paths, should be done after reducing
	private List<String> finalResults = new ArrayList<String>();

	/**
	 * The {@link MasterServer} constructor.
	 *
	 * @param masterPort
	 *            The port to listen on.
	 * @param workers
	 *            Information about each of the available workers in the system.
	 */
	public MasterServer(int masterPort, List<WorkerInfo> workers) {
		mPort = masterPort;
		mWorkers = workers;

		// initialization
		mExecutorClient = Executors.newSingleThreadExecutor();
		int numThreads = Math.min(MAX_POOL_SIZE, mWorkers.size());
		mExecutorWorker = Executors.newFixedThreadPool(numThreads);

		partitionStatus = new HashMap<Partition, Boolean>();
		workersAvailability = new HashMap<WorkerInfo, Boolean>();
		availableWorkers = new ArrayList<WorkerInfo>();
		shuffledPaths = new ArrayList<List<String>>();
		shufflePathWorkers = new HashMap<String, WorkerInfo>();
		workersInterPaths = new HashMap<WorkerInfo, List<String>>();
		finalResults = new ArrayList<String>();

		for (WorkerInfo worker : mWorkers) {
			for (Partition partition : worker.getPartitions()) {
				if (!partitionStatus.containsKey(partition)) {
					partitionStatus.put(partition, false);
				}
			}
			// initially grant all workers are not dead
			workersAvailability.put(worker, true);
			workersInterPaths.put(worker, new ArrayList<String>());

			// delete intermediate and final files for the new round
			String interDir = WorkerStorage
					.getIntermediateResultsDirectory(worker.getName());
			File interFiles = new File(interDir);
			for (File iFile : interFiles.listFiles()) {
				iFile.delete();
			}
			String finalDir = WorkerStorage.getFinalResultsDirectory(worker
					.getName());
			File finalFiles = new File(finalDir);
			for (File fFile : finalFiles.listFiles()) {
				fFile.delete();
			}
		}
	}

	@Override
	public void run() {
		// same as the worker server, except the WorkerCommandHandler
		try {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(mPort);
			} catch (IOException e) {
				Log.e(TAG, "Could not open server socket on port " + mPort
						+ ".", e);
				return;
			}

			Log.i(TAG, "Listening for incoming commands on port " + mPort + ".");

			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					mExecutorClient.execute(new WorkerCommandHandler(
							clientSocket));
				} catch (IOException e) {
					Log.e(TAG,
							"Error while listening for incoming connections.",
							e);
					break;
				}
			}

			Log.i(TAG, "Shutting down...");

			try {
				serverSocket.close();
			} catch (IOException e) {
				Log.i(TAG,
						"IOExcetion, ignore because we're about to exit anyway.");
			}
		} finally {
			mExecutorClient.shutdown();
		}
	}

	/**
	 * Handles a single worker-client request.
	 * 
	 * @author zhaoru
	 *
	 */
	private class WorkerCommandHandler implements Runnable {
		private final Socket mSocket;

		public WorkerCommandHandler(Socket socket) {
			mSocket = socket;
		}

		@Override
		public void run() {
			List<MapCallable> mapCallables = new ArrayList<MapCallable>();
			List<ShuffleCallable> shuffleCallables = new ArrayList<ShuffleCallable>();
			List<ReduceCallable> reduceCallables = new ArrayList<ReduceCallable>();
			try {
				Log.i(TAG, "Begin Mapping.");

				// read the first one, should be the map task, second should be
				// reduce task
				ObjectInputStream in = new ObjectInputStream(
						mSocket.getInputStream());
				MapTask mapTask = (MapTask) in.readObject();

				boolean doneMapping = false;
				// when there is execution exception, the mapping is undone
				while (!doneMapping) {
					for (WorkerInfo worker : mWorkers) {
						if (workersAvailability.get(worker))
							mapCallables.add(new MapCallable(mapTask, worker));
						else
							Log.i(TAG, worker.getName() + "is dead");
					}

					List<Future<List<String>>> mapResults = mExecutorWorker
							.invokeAll(mapCallables);

					// mapping should be done if no execution exception
					// afterwards
					doneMapping = true;
					for (int i = 0; i < mapResults.size(); i++) {
						MapCallable mapCallable = mapCallables.get(i);
						Future<List<String>> mapResult = mapResults.get(i);

						try {
							List<String> resultPaths = mapResult.get();
							WorkerInfo worker = mapCallable.mWorker;
							// there may already exist some paths for that
							// worker,
							// we cannot override them, we have to add new paths
							// to
							// existing paths
							List<String> existingInterPaths = workersInterPaths
									.get(worker);
							existingInterPaths.addAll(resultPaths);
							workersInterPaths.put(worker, existingInterPaths);
						} catch (ExecutionException e) {
							// one result of one worker failed
							WorkerInfo failWorkerMap = mapCallable.mWorker;
							// make the failed worker unavailable
							workersAvailability.put(failWorkerMap, false);

							// inform the dead worker
							Log.i(TAG, failWorkerMap.getName() + " (host: "
									+ failWorkerMap.getHost() + ", port: "
									+ failWorkerMap.getPort() + ") "
									+ "failed to execute map task.");

							// make the partitions of the failed worker undone
							for (Partition partition : failWorkerMap
									.getPartitions()) {
								partitionStatus.put(partition, false);
								// inform the undone partitions
								Log.i(TAG,
										"Partition: "
												+ partition.getPartitionName()
												+ " for "
												+ partition.getWorkerName()
												+ " is undone due to this failed worker");
							}
							// some worker is failed, we should do the mapping
							// again
							doneMapping = false;

							// inform all undone partitions
							List<Partition> undonePartitions = new ArrayList<Partition>();
							for (Map.Entry<Partition, Boolean> entry : partitionStatus
									.entrySet()) {
								if (!entry.getValue())
									undonePartitions.add(entry.getKey());
							}
							Log.i(TAG, undonePartitions.toString()
									+ " are still undone");
						}

					}
				}

				Log.i(TAG, "Done mapping, begin shuffing.");

				// set the available workers
				availableWorkers.clear();
				// shuffledPaths.clear();
				// shufflePathWorkers.clear();
				for (Map.Entry<WorkerInfo, Boolean> entry : workersAvailability
						.entrySet()) {
					if (entry.getValue())
						availableWorkers.add(entry.getKey());
				}
				for (WorkerInfo worker : mWorkers) {
					shuffleCallables.add(new ShuffleCallable(worker,
							availableWorkers.size()));
					// intend to initialize the size of shuffledPaths, which is
					// the availableWorkers size
					shuffledPaths.add(new ArrayList<String>());
				}

				List<Future<List<String>>> shuffleResults = mExecutorWorker
						.invokeAll(shuffleCallables);

				for (int j = 0; j < shuffleResults.size(); j++) {
					ShuffleCallable shuffleCallable = shuffleCallables.get(j);
					Future<List<String>> shuffleResult = shuffleResults.get(j);
					try {
						List<String> shuffledPathsOneWorker = shuffleResult
								.get();
						for (String path : shuffledPathsOneWorker) {
							// [0] is the hash code, [1] is the path belongs to
							// that hash code
							String[] pair = path.split("#");
							int hashCode = Integer.parseInt(pair[0]);
							// get the list of shuffled paths, an index is the
							// hash code of a list of paths
							shuffledPaths.get(hashCode).add(pair[1]);
							shufflePathWorkers.put(pair[1],
									availableWorkers.get(hashCode));
						}
					} catch (ExecutionException e) {
						// should exist error if shuffle cannot be done
						// peacefully, but failure worker in map should not
						// terminate the program
						WorkerInfo failWorkerShuffle = shuffleCallable.mWorker;
						Log.e(TAG, failWorkerShuffle.getName() + " (host: "
								+ failWorkerShuffle.getHost() + ", port: "
								+ failWorkerShuffle.getPort() + ") "
								+ "failed to execute shuffle task.", e);

					}
				}

				Log.i(TAG, "Done shuffing, begin reducing.");

				ReduceTask reduceTask = (ReduceTask) in.readObject();
				for (WorkerInfo worker : mWorkers) {
					if (workersAvailability.get(worker))
						reduceCallables.add(new ReduceCallable(reduceTask,
								worker));
					else
						// do not output error
						Log.i(TAG, worker.getName() + "is dead");
				}

				List<Future<String>> reduceResults = mExecutorWorker
						.invokeAll(reduceCallables);

				for (int k = 0; k < reduceResults.size(); k++) {
					ReduceCallable reduceCallable = reduceCallables.get(k);
					Future<String> reduceResultFurure = reduceResults.get(k);

					try {
						String reduceResult = reduceResultFurure.get();
						finalResults.add(reduceResult);
						Log.i(TAG, "Final path: " + reduceResult);
					} catch (ExecutionException e) {
						// should exist error if reduce cannot be done
						// peacefully, but failure worker in map should not
						// terminate the program
						WorkerInfo failWorkerReduce = reduceCallable.mWorker;
						Log.e(TAG, failWorkerReduce.getName() + " (host: "
								+ failWorkerReduce.getHost() + ", port: "
								+ failWorkerReduce.getPort() + ") "
								+ "failed to execute reduce task.", e);
					}
				}
				ObjectOutputStream out = new ObjectOutputStream(
						mSocket.getOutputStream());
				out.writeObject(finalResults);

				Log.i(TAG, "Done reducing.");

			} catch (IOException e) {
				Log.e(TAG, "Received invalid task from client.", e);
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "Fail to read info from client socket.", e);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				mExecutorWorker.shutdown();
			}
		}

	}

	/**
	 * stands for a map callable to be executed in invokeAll()
	 * 
	 * @author zhaoru
	 *
	 */
	private class MapCallable implements Callable<List<String>> {
		private MapTask mMapTask;
		private WorkerInfo mWorker;

		public MapCallable(MapTask mapTask, WorkerInfo worker) {
			mMapTask = mapTask;
			mWorker = worker;
		}

		@Override
		public List<String> call() throws Exception {
			// if this worker is dead, return null
			if (!workersAvailability.get(mWorker))
				return null;

			List<String> results = new ArrayList<String>();
			for (Partition partition : mWorker.getPartitions()) {
				synchronized (this) {
					// if true, do not need to work on the partition that is
					// already
					// done. should be in synchronized
					if (partitionStatus.get(partition)) {
						continue;
					}
					// We need to block other actions when updating the status
					// of partition
					partitionStatus.put(partition, true);
				}

				Socket socket = null;
				try {
					socket = new Socket(mWorker.getHost(), mWorker.getPort());
					ObjectOutputStream out = new ObjectOutputStream(
							socket.getOutputStream());
					out.writeObject(new ExecuteMapCommand(mMapTask, partition));

					ObjectInputStream in = new ObjectInputStream(
							socket.getInputStream());
					String path = (String) in.readObject();
					results.add(path);
				} catch (IOException e) {
					Log.e(TAG,
							"Error when connecting with the map task workers!",
							e);
				} finally {
					try {
						if (socket != null)
							socket.close();
					} catch (IOException e) {
						Log.i(TAG,
								"IOExcetion, ignore because we're about to exit anyway.");
					}
				}
			}
			return results;
		}
	}

	/**
	 * stands for a shuffle callable to be executed in invokeAll()
	 * 
	 * @author zhaoru
	 *
	 */
	private class ShuffleCallable implements Callable<List<String>> {
		private WorkerInfo mWorker;
		private int numAvaiWorkers;

		public ShuffleCallable(WorkerInfo worker, int num) {
			mWorker = worker;
			numAvaiWorkers = num;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> call() throws Exception {
			// if this worker is dead, return null
			if (!workersAvailability.get(mWorker))
				return null;

			List<String> results = new ArrayList<String>();
			Socket socket = null;
			try {
				socket = new Socket(mWorker.getHost(), mWorker.getPort());
				ObjectOutputStream out = new ObjectOutputStream(
						socket.getOutputStream());
				// a shuffle command needs the worker, intermediate paths of
				// that worker, and available workers currently
				out.writeObject(new ExecuteShuffleCommand(mWorker,
						workersInterPaths.get(mWorker), numAvaiWorkers));

				ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());
				results = (List<String>) in.readObject();
			} catch (IOException e) {
				Log.e(TAG,
						"Error when connecting with the shuffle task workers.",
						e);
			} finally {
				try {
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					Log.i(TAG,
							"IOExcetion, ignore because we're about to exit anyway.");
				}
			}
			return results;
		}

	}

	/**
	 * stands for a reduce callable to be executed in invokeAll()
	 * 
	 * @author zhaoru
	 *
	 */
	private class ReduceCallable implements Callable<String> {
		private ReduceTask mReduceTask;
		private WorkerInfo mWorker;
		private final List<ExecuteTransferCommand> transferCommands;

		public ReduceCallable(ReduceTask reduceTask, WorkerInfo worker) {
			mReduceTask = reduceTask;
			mWorker = worker;
			transferCommands = new ArrayList<ExecuteTransferCommand>();
			for (String path : shuffledPaths.get(availableWorkers
					.indexOf(mWorker))) {
				transferCommands.add(new ExecuteTransferCommand(path,
						shufflePathWorkers.get(path)));
			}
		}

		@Override
		public String call() throws Exception {
			// if this worker is dead, return null
			if (!workersAvailability.get(mWorker))
				return null;

			String finalPath = null;
			Socket socket = null;
			try {
				socket = new Socket(mWorker.getHost(), mWorker.getPort());
				ObjectOutputStream out = new ObjectOutputStream(
						socket.getOutputStream());
				out.writeObject(new ExecuteReduceCommand(transferCommands,
						mWorker, mReduceTask));

				ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());
				finalPath = (String) in.readObject();
			} catch (Exception e) {
				Log.e(TAG,
						"Error when connecting with the reduce task workers.");
				throw e;
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					Log.i(TAG,
							"IOExcetion, ignore because we're about to exit anyway.");
				}
			}
			return finalPath;
		}

	}

	/********************************************************************/
	/***************** STAFF CODE BELOW. DO NOT MODIFY. *****************/
	/********************************************************************/

	/**
	 * Starts the master server on a distinct port. Information about each
	 * available worker in the distributed system is parsed and passed as an
	 * argument to the {@link MasterServer} constructor. This information can be
	 * either specified via command line arguments or via system properties
	 * specified in the <code>master.properties</code> and
	 * <code>workers.properties</code> file (if no command line arguments are
	 * specified).
	 * 
	 * @param args
	 *            arguments used to executed
	 */
	public static void main(String[] args) {
		StaffUtils.makeMasterServer(args).start();
	}

}
