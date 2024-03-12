package io.jutil.springeasy.jdo.util;

import io.jutil.springeasy.jdo.parser.EntityMetadata;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
public class VersionUtil {
	private VersionUtil() {
	}

	public static boolean isForce(EntityMetadata config) {
		var verConfig = config.getVersionMetadata();
		if (verConfig == null) {
			return false;
		}
		return verConfig.isForce();
	}
}
