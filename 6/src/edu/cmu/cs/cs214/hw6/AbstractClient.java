package edu.cmu.cs.cs214.hw6;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw6.util.Log;

/**
 * An abstract client class used primarily for code reuse between the
 * {@link WordCountClient} and {@link WordPrefixClient}.
 */
public abstract class AbstractClient {
	private static final String TAG = "Client";

	private final String mMasterHost;
	private final int mMasterPort;

	/**
	 * The {@link AbstractClient} constructor.
	 *
	 * @param masterHost
	 *            The host name of the {@link MasterServer}.
	 * @param masterPort
	 *            The port that the {@link MasterServer} is listening on.
	 */
	public AbstractClient(String masterHost, int masterPort) {
		mMasterHost = masterHost;
		mMasterPort = masterPort;
	}

	protected abstract MapTask getMapTask();

	protected abstract ReduceTask getReduceTask();

	/**
	 * Execute map/reduce work
	 */
	public void execute() {
		final MapTask mapTask = getMapTask();
		final ReduceTask reduceTask = getReduceTask();

		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			socket = new Socket(mMasterHost, mMasterPort);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(mapTask);
			out.writeObject(reduceTask);

			// write object then initialize input stream can avoid the program
			// blocking
			in = new ObjectInputStream(socket.getInputStream());
			// We know this object is ArrayList<String> type
			@SuppressWarnings("unchecked")
			List<String> result = (ArrayList<String>) in.readObject();

			Log.i(TAG, "The result locations are:");
			for (String path : result) {
				Log.i(TAG, path);
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException when connecting to the master server.", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "The type we get does not match.", e);
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				Log.i(TAG,
						"IOExcetion, ignore because we're about to exit anyway.");
			}
		}
	}

}
