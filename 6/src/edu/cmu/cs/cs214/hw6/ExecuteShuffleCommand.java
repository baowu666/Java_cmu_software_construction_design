package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * let the worker handle the intermediate files of its local file system and
 * split the the intermediate files into several shuffle files according to the
 * hash values of all the reduce workers. Then the worker can use socket to send
 * shuffle results to different workers according to the index (hash codes).
 * 
 * @author zhaoru
 *
 */
public class ExecuteShuffleCommand extends WorkerCommand {
	private static final long serialVersionUID = 35814424782049034L;
	private static final String TAG = "ExecuteShuffleCommand";

	private WorkerInfo mWorker;
	private List<String> interPaths;
	private int numAvaiWorkers;

	/**
	 * Constructor
	 * 
	 * @param worker
	 *            the worker to execute the shuffle
	 * @param paths
	 *            list of paths of the intermediate files
	 * @param num
	 *            number of workers to execute
	 */
	public ExecuteShuffleCommand(WorkerInfo worker, List<String> paths, int num) {
		mWorker = worker;
		interPaths = paths;
		numAvaiWorkers = num;
	}

	@Override
	public void run() {
		// each emitter is responsible for a shuffle file to be written
		List<Emitter> emitters = new ArrayList<Emitter>();
		// where the shuffled files should be stored
		String shuffleDir = WorkerStorage
				.getIntermediateResultsDirectory(mWorker.getName());
		// real path names of the shuffled files
		List<String> shufflePaths = new ArrayList<String>();
		// results to write to socket, which is a hash code with path name pair
		List<String> results = new ArrayList<String>();

		for (int i = 0; i < numAvaiWorkers; i++) {
			String pathName = shuffleDir + "/shuffle_num_" + i + ".txt";
			shufflePaths.add(pathName);

			results.add(i + "#" + pathName);

			Emitter emitter = new EmitterImpl(pathName);
			emitters.add(emitter);
		}

		Socket socket = getSocket();
		try {
			for (String interPath : interPaths) {
				Scanner scanner = new Scanner(new File(interPath));
				while (scanner.hasNextLine()) {
					String[] pair = scanner.nextLine().split("\t");
					if (pair.length >= 2) {
						String key = pair[0];
						String value = pair[1];
						// hash code of each key/value pair
						int hashcode = Math
								.abs(key.hashCode() % numAvaiWorkers);
						emitters.get(hashcode).emit(key, value);
					}
				}
				scanner.close();
			}

			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(results);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file.", e);
		} catch (IOException e) {
			Log.e(TAG, "IOexception when executing the shuffle task.", e);
		}
	}

}
