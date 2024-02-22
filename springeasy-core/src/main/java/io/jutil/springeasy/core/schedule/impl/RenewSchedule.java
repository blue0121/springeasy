package io.jutil.springeasy.core.schedule.impl;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
public interface RenewSchedule {

	void startRenew(String jobId);

	void stopRenew(String jobId);

}
