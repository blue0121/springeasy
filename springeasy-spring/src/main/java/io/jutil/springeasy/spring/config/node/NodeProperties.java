package io.jutil.springeasy.spring.config.node;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jin Zheng
 * @since 2024-05-28
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.node")
public class NodeProperties implements PropertiesChecker {
	private int machineId;

	@Override
	public void check() {
		AssertUtil.nonNegative(machineId, "machineId");
		MachineIdContext.setMachineId(machineId);
	}
}
