package edu.cmu.cs.cs214.hw6;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cmu.cs.cs214.hw6.util.KeyValuePair;
import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * collect data from other workers by sockets and do the reduceTask. Each worker
 * will execute several transfer commands.
 * 
 * @author zhaoru
 *
 */
public class ExecuteReduceCommand extends WorkerCommand {
	private static final long serialVersionUID = 8641755479391895579L;
	private static final String TAG = "ExecuteReduceCommand";
	private static final int POOL_SIZE = Runtime.getRuntime()
			.availableProcessors();

	private WorkerInfo mWorker;
	private ReduceTask reduceTask;

	private List<ExecuteTransferCommand> transferCommands;
	private Map<String, List<String>> reduceData;

	/**
	 * Constructor
	 * 
	 * @param transfers
	 *            list of ExecuteTransferCommand
	 * @param worker
	 *            the worker to execute
	 * @param task
	 *            the task to execute
	 */
	public ExecuteReduceCommand(List<ExecuteTransferCommand> transfers,
			WorkerInfo worker, ReduceTask task) {
		mWorker = worker;
		reduceTask = task;
		transferCommands = transfers;
		reduceData = Collections
				.synchronizedMap(new HashMap<String, List<String>>());
	}

	@Override
	public void run() {
		final ExecutorService mExecutor = Executors
				.newFixedThreadPool(POOL_SIZE);
		String fileName = "final_" + mWorker.getName() + ".txt";
		String finalPath = WorkerStorage.getFinalResultsDirectory(mWorker
				.getName()) + "/" + fileName;
		Socket socket = getSocket();
		Emitter emitter = null;
		try {
			emitter = new EmitterImpl(finalPath);

			// start transfer task for this worker
			List<TransferCallable> transferCallables = new ArrayList<TransferCallable>();
			for (ExecuteTransferCommand transferCommand : transferCommands) {
				transferCallables.add(new TransferCallable(transferCommand));
			}

			List<Future<Void>> data = mExecutor.invokeAll(transferCallables);

			for (int i = 0; i < data.size(); i++) {
				TransferCallable transferCallable = transferCallables.get(i);
				Future<Void> result = data.get(i);
				try {
					result.get();
				} catch (ExecutionException e) {
					WorkerInfo failWorker = transferCallable.worker;
					Log.e(TAG,
							failWorker.getName() + " (host: "
									+ failWorker.getHost() + ", port: "
									+ failWorker.getPort() + ") "
									+ "failed to transfer task.", e);
				}
			}
			// end transfer task for this worker

			// reduce task
			for (Map.Entry<String, List<String>> entry : reduceData.entrySet()) {
				Iterator<String> iter = entry.getValue().iterator();
				reduceTask.execute(entry.getKey(), iter, emitter);
			}
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(finalPath);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file.", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when executing the reduce task.", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			try {
				if (emitter != null) {
					emitter.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "IOException when closing socket.");
			}
		}

	}

	/**
	 * nested class used to be called in invokeAll()
	 * 
	 * @author zhaoru
	 *
	 */
	private class TransferCallable implements Callable<Void> {
		private WorkerInfo worker;
		private ExecuteTransferCommand executeTransferCommand;

		public TransferCallable(ExecuteTransferCommand transferCommand) {
			worker = transferCommand.getWorker();
			executeTransferCommand = transferCommand;
		}

		@Override
		public Void call() throws Exception {
			Socket socket = null;
			try {
				socket = new Socket(worker.getHost(), worker.getPort());
				ObjectOutputStream out = new ObjectOutputStream(
						socket.getOutputStream());
				out.writeObject(executeTransferCommand);

				ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());
				// try to get all keypairs sent from other workers
				while (true) {
					KeyValuePair keyPair = (KeyValuePair) in.readObject();
					String key = keyPair.getKey();
					String value = keyPair.getValue();
					if (reduceData.containsKey(key)) {
						reduceData.get(key).add(value);
					} else {
						// set up synchronized list to add value for specified
						// key
						List<String> old = Collections
								.synchronizedList(new ArrayList<String>());
						old.add(value);
						reduceData.put(key, old);
					}
				}
			} catch (EOFException e) {
				return null;
			} catch (FileNotFoundException e) {
				Log.e(TAG, "Fail to create or find the file.", e);
			} catch (IOException e) {
				Log.e(TAG, "IOException when executing the reduce task.", e);
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
			return null;
		}

	}
}
