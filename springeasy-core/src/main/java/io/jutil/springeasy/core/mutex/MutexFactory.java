package io.jutil.springeasy.core.mutex;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface MutexFactory {

	MutexType getType();

	Mutex create(String key);

	void destroy();

}
