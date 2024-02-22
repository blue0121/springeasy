package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * @author Jin Zheng
 * @since 2023-01-10
 */
class DateTest {

	@Test
	void testNow() {
		var now = DateUtil.now();
		System.out.println(now);
		Assertions.assertNotNull(now);
		Assertions.assertTrue(now.toInstant(ZoneOffset.UTC).toEpochMilli() > 0);
	}

	@CsvSource({"1,true", "999,true", "1000,true", "1001,false"})
	@ParameterizedTest
	void testEqual(int offset, boolean equal) {
		var now = DateUtil.now();
		var time = now.plus(offset, ChronoUnit.MILLIS);
		Assertions.assertEquals(equal, DateUtil.equal(now, time));
		Assertions.assertEquals(equal, DateUtil.equal(time, now));
	}

}
