package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.core.util.WaitUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
class DbRenewScheduleTest {
	SqlExecutor sqlExecutor;

	DbRenewSchedule schedule;

	final String instanceId = UUID.randomUUID().toString();
	final String jobId = "jobId123";

	@BeforeEach
	void beforeEach() {
		this.sqlExecutor = Mockito.mock(SqlExecutor.class);
		this.schedule = new DbRenewSchedule(sqlExecutor, instanceId, 1, 5);

	}

	@Test
	void testRenew() {
		schedule.startRenew(jobId);
		WaitUtil.sleep(1500);

		var jobIdCaptor = ArgumentCaptor.forClass(Set.class);
		var expireCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
		Mockito.verify(sqlExecutor, Mockito.atLeastOnce()).renew(jobIdCaptor.capture(),
				Mockito.eq(instanceId), expireCaptor.capture());
		Assertions.assertEquals(Set.of(jobId), jobIdCaptor.getValue());
		var expire = expireCaptor.getValue();
		var now = DateUtil.now(ChronoUnit.SECONDS).plusSeconds(5);
		System.out.println(">>>>>>>>" + now + " - " + expire);
		Assertions.assertTrue(DateUtil.equal(now, expire));

		schedule.stopRenew(jobId);
	}

}
