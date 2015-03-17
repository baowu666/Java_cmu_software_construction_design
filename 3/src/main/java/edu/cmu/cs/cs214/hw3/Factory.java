package edu.cmu.cs.cs214.hw3;

/**
 * Interface of both bus factory and person factory
 * @author zhaoru
 *
 */
public interface Factory {
	/**
	 * one step stands for one second, this is what a factory would do within one step
	 */
	void step();
}
