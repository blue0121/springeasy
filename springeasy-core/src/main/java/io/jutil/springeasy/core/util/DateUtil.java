package io.jutil.springeasy.core.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
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

}
