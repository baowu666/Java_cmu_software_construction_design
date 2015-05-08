package edu.cmu.cs.cs214.hw6;

import java.io.Serializable;
import java.util.List;

/**
 * Stores information about a single worker in the map/reduce framework.
 */
public class WorkerInfo implements Serializable {
	private static final long serialVersionUID = -5637255268128878875L;

	private final String mName;
	private final String mHost;
	private final int mPort;
	private final List<Partition> mPartitions;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            worker name
	 * @param host
	 *            worker host
	 * @param port
	 *            worker port
	 * @param partitions
	 *            worker partitions
	 */
	public WorkerInfo(String name, String host, int port,
			List<Partition> partitions) {
		mName = name;
		mHost = host;
		mPort = port;
		mPartitions = partitions;
	}

	/**
	 * Returns the worker's name.
	 * 
	 * @return worker name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Returns the worker's host address.
	 * 
	 * @return worker host
	 */
	public String getHost() {
		return mHost;
	}

	/**
	 * Returns the worker's port.
	 * 
	 * @return worker port
	 */
	public int getPort() {
		return mPort;
	}

	/**
	 * Returns a {@link List} of this worker's {@link Partition}s.
	 * 
	 * @return worker partitions
	 */
	public List<Partition> getPartitions() {
		return mPartitions;
	}

	@Override
	public String toString() {
		return String.format("<%s: host=%s, port=%d, partitions=%s",
				WorkerInfo.class.getSimpleName(), mHost, mPort,
				mPartitions.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mHost == null ? 0 : mHost.hashCode());
		result = prime * result + mPort;
		for (Partition partition : mPartitions) {
			result = prime * result
					+ (partition == null ? 0 : partition.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorkerInfo)) {
			return false;
		}
		WorkerInfo other = (WorkerInfo) o;
		return equals(mHost, other.mHost) && mPort == other.mPort
				&& equals(mPartitions, other.mPartitions);
	}

	private static boolean equals(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

}
