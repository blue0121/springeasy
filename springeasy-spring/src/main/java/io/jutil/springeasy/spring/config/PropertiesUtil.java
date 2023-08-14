package io.jutil.springeasy.spring.config;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
public class PropertiesUtil {
	private PropertiesUtil() {
	}

	public static <T extends PropertiesChecker> void check(List<T> propList) {
		check(propList, null);
	}

	public static <T extends PropertiesChecker> void check(List<T> propList, Consumer<T> f) {
		if (propList == null || propList.isEmpty()) {
			return;
		}
		for (var prop : propList) {
			prop.check();
			if (f != null) {
				f.accept(prop);
			}
		}
	}
}
