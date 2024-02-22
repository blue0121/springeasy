package io.jutil.springeasy.core.schedule;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
public interface ScheduleJob {

	void run(ScheduleContext ctx);

}
