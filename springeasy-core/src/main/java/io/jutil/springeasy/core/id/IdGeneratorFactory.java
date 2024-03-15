package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.cache.Singleton;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
public class IdGeneratorFactory {

	private IdGeneratorFactory() {
	}

	public static String string20() {
		var id = Singleton.get(String20IdGenerator.class, k -> {
			var options = new EpochOptions();
			return new String20IdGenerator(options);
		});
		return id.generate();
	}

	public static Long longId() {
		return longId(0);
	}

	public static Long longId(int machineId) {
		var param = LongEpochIdGenerator.class.getName() + machineId;
		var id = Singleton.get(param, k -> {
			var options = new EpochOptions(machineId);
			return new LongEpochIdGenerator(options);
		});
		return id.generate();
	}
}
