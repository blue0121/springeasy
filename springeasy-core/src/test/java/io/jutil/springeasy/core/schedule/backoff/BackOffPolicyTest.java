package io.jutil.springeasy.core.schedule.backoff;

import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2024-04-28
 */
class BackOffPolicyTest {

	@CsvSource({"1,2", "10,20"})
	@ParameterizedTest
	void testFixedBackOffPolicy(int retry, int result) {
		BackOffPolicy policy = new FixedBackOffPolicy();
		this.verify(policy, retry, result);
	}

	@CsvSource({"1,2", "10,1024"})
	@ParameterizedTest
	void testExponentialBackOffPolicy(int retry, int result) {
		BackOffPolicy policy = new ExponentialBackOffPolicy();
		this.verify(policy, retry, result);
	}

	private void verify(BackOffPolicy policy, int retry, int result) {
		var now = DateUtil.now();
		var time = policy.nextExecutionTime(retry);
		var expect = now.plusMinutes(result);
		Assertions.assertTrue(DateUtil.equal(expect, time));
	}
}
