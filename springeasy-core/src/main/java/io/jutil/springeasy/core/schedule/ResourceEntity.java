package io.jutil.springeasy.core.schedule;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface ResourceEntity {

	String getJobId();

	String getResourceId();

	String getMutexKey();
}
