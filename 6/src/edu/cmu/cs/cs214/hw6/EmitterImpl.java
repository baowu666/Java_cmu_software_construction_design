package edu.cmu.cs.cs214.hw6;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cmu.cs.cs214.hw6.util.Log;

/**
 * Class for {@link MapTask}s and {@link ReduceTask}s will write key value pairs
 * to file with specified path, if the file exists, append content at the end of
 * the file.
 * 
 * @author zhaoru
 */
public class EmitterImpl implements Emitter {
	private static final String TAG = "Emitter";

	private PrintWriter out;

	/**
	 * Constructor
	 * 
	 * @param path
	 *            the path to be written to
	 */
	public EmitterImpl(String path) {
		// set autoFlush to true, or the file just created will overwrite
		try {
			out = new PrintWriter(new FileOutputStream(path), true);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "FileNotFoundException when opening emitter path", e);
		}
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

	@Override
	public void emit(String key, String value) throws IOException {
		out.write(key + '\t' + value + '\n');
	}

}
