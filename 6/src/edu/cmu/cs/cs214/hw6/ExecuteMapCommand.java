package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.cmu.cs.cs214.hw6.util.Log;
import edu.cmu.cs.cs214.hw6.util.WorkerStorage;

/**
 * ExecuteMapCommand class, which can execute the map task when called.
 * 
 * @author zhaoru
 *
 */
public class ExecuteMapCommand extends WorkerCommand {
	private static final long serialVersionUID = -5487107349795911741L;
	private static final String TAG = "ExecuteMapCommand";

	private MapTask mTask;
	private Partition partition;

	/**
	 * Constructor
	 * 
	 * @param newMapTask
	 *            the map task
	 * @param newPartition
	 *            the partition where to execute
	 */
	public ExecuteMapCommand(MapTask newMapTask, Partition newPartition) {
		mTask = newMapTask;
		partition = newPartition;
	}

	@Override
	public void run() {
		String interResultsDir = WorkerStorage
				.getIntermediateResultsDirectory(partition.getWorkerName());
		String filename = "Partition_" + partition.getPartitionName()
				+ "-Worker_" + partition.getWorkerName() + ".txt";
		String interPath = interResultsDir + "/" + filename;

		Log.i(TAG, "Intermediate result path is " + interPath);

		Socket socket = null;
		Emitter emitter = null;
		FileInputStream in = null;
		ObjectOutputStream out = null;

		try {
			socket = getSocket();
			// executed files should be in one path
			emitter = new EmitterImpl(interPath);

			// files in one partition of one worker are executed
			for (File file : partition) {
				// two files are in one partition of one worker
				in = new FileInputStream(file);
				mTask.execute(in, emitter);
			}

			in.close();

			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(interPath);
			out.close();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "FileNotFoundException when creating emitter", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when executing map task", e);
		} finally {
			try {
				if (emitter != null)
					emitter.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				Log.e(TAG,
						"IOException, fail to close emitter or socket after mapping.",
						e);
			}
		}
	}

}
