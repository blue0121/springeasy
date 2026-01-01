package io.jutil.springeasy.core.codec.json;

import java.time.format.DateTimeFormatter;

/**
 * @author Jin Zheng
 * @since 2025-12-27
 */
public class Formatter {
	public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
}
