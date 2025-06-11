package io.jutil.springeasy.core.mutex;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
public interface Mutex {

	boolean tryLock();

	void unlock();

	String getKey();

}
