package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
@Slf4j
public class DateUtil {
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

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		AssertUtil.notEmpty(pattern, "Pattern");
		var format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

}
