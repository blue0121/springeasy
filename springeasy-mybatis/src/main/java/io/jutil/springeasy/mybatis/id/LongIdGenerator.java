package io.jutil.springeasy.mybatis.id;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.spring.config.node.MachineIdContext;

/**
 * @author Jin Zheng
 * @since 2024-06-11
 */
public class LongIdGenerator {

	public static long nextId() {
		var machineId = MachineIdContext.getMachineId();
		return IdGeneratorFactory.longId(machineId);
	}
}
