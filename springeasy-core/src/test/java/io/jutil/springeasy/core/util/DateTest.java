package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;

/**
 * @author Jin Zheng
 * @since 2023-01-10
 */
public class DateTest {

	@Test
	public void testInstant() {
		var now1 = System.currentTimeMillis();
		var ins = Instant.now();
		var now2 = ins.getEpochSecond() * 1000 + ins.getNano() / 1_000_000;
		System.out.println(now1);
		System.out.println(now2);
		System.out.println(ins.toEpochMilli());
	}

}
