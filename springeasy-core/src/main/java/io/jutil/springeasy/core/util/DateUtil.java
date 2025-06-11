package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
@Slf4j
public class DateUtil {
	public static final DateTimeFormatter DATE_TIME_FORMATTER =
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private DateUtil() {
	}

	/**
	 * 当前时间，精确到秒
	 *
	 * @return
	 */
	public static LocalDateTime now() {
		return now(ChronoUnit.SECONDS);
	}

	public static LocalDateTime now(TemporalUnit unit) {
		var now = LocalDateTime.now();
		return now.truncatedTo(unit);
	}

	@SuppressWarnings("java:S1221")
	public static boolean equal(LocalDateTime time1, LocalDateTime time2) {
		return equal(time1, time2, ChronoUnit.SECONDS);
	}


	@SuppressWarnings("java:S1221")
	public static boolean equal(LocalDateTime time1, LocalDateTime time2, TemporalUnit unit) {
		var ts1 = time1.toInstant(ZoneOffset.UTC).toEpochMilli();
		var ts2 = time2.toInstant(ZoneOffset.UTC).toEpochMilli();
		return equal(ts1, ts2, unit);
	}

	public static boolean equal(long time1, long time2) {
		return equal(time1, time2, ChronoUnit.SECONDS);
	}

	public static boolean equal(long time1, long time2, TemporalUnit unit) {
		var in = unit.getDuration().toMillis();
		return Math.abs(time1 - time2) <= in;
	}

	public static Date parse(String text, String pattern) {
		if (text == null || text.isEmpty()) {
			return null;
		}

		AssertUtil.notEmpty(pattern, "Pattern");
		var format = new SimpleDateFormat(pattern);
		try {
			return format.parse(text);
		}
		catch (ParseException e) {
			log.warn("Unknown date pattern: {} - {}", pattern, text);
			return null;
		}
	}

	public static String format(LocalDateTime dateTime) {
		return dateTime.format(DATE_TIME_FORMATTER);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		AssertUtil.notEmpty(pattern, "Pattern");
		var format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

}
