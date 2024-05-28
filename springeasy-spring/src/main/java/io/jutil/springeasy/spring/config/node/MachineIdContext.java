package io.jutil.springeasy.spring.config.node;

/**
 * @author Jin Zheng
 * @since 2024-05-28
 */
public class MachineIdContext {
	private static int machineId;

	public static void setMachineId(int machineId) {
		MachineIdContext.machineId = machineId;
	}

	public static int getMachineId() {
		return machineId;
	}
}
