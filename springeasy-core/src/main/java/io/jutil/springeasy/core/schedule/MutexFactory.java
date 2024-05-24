package io.jutil.springeasy.core.schedule;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface MutexFactory {

	String getType();

	Mutex create(String id);

}
