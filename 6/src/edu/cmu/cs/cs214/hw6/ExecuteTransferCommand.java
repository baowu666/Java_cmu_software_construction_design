package edu.cmu.cs.cs214.hw6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import edu.cmu.cs.cs214.hw6.util.KeyValuePair;
import edu.cmu.cs.cs214.hw6.util.Log;

/**
 * transfer key value pairs among workers by sockets, workers will receive key
 * value pairs from other workers in order to start the reduce task.
 * 
 * @author zhaoru
 *
 */
public class ExecuteTransferCommand extends WorkerCommand {
	private static final long serialVersionUID = 8883674853631384827L;
	private static final String TAG = "ExecuteTransferCommand";

	private final String path;
	private final WorkerInfo worker;

	/**
	 * Constructor
	 * 
	 * @param path1
	 *            the path to transfer
	 * @param worker1
	 *            worker from
	 */
	public ExecuteTransferCommand(String path1, WorkerInfo worker1) {
		path = path1;
		worker = worker1;
	}

	/**
	 * get the worker who owns this path
	 * 
	 * @return the worker
	 */
	public WorkerInfo getWorker() {
		return worker;
	}

	@Override
	public void run() {
		Socket socket = getSocket();
		String[] pair = null;
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				pair = scanner.nextLine().split("\t");
				if (pair.length >= 2)
					out.writeObject(new KeyValuePair(pair[0], pair[1]));
				else
					out.writeObject(new KeyValuePair(pair[0], "1"));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Fail to create or find the file.", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when executing the transfer task.", e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "IOException when closing socket.");
			}
		}
	}

}
