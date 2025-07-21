package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2025-07-16
 */
public interface Counter {
	String KEY_DEFAULT = "__default__";

	String next(String key);

	void addOption(Option option);

	record Option(String key, int min, int max, int length) {}

}
