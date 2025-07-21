package io.jutil.springeasy.spring.config.node;

import io.jutil.springeasy.core.id.IdGeneratorFactory;

/**
 * @author Jin Zheng
 * @since 2025-07-16
 */
public class LongIdContext {
	private LongIdContext() {
	}

	public static Long longId() {
		var machineId = MachineIdContext.getMachineId();
		return IdGeneratorFactory.longId(machineId);
	}
}
