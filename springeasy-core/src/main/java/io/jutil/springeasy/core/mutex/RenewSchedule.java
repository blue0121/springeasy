package io.jutil.springeasy.core.mutex;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
public interface RenewSchedule {

	void startRenew(String jobId);

	void stopRenew(String jobId);

	void shutdown();

	int getKeepAliveSec();

	int getExpireSec();

	String getInstanceId();
}
