package io.jutil.springeasy.spring.node;

import io.jutil.springeasy.spring.Application;
import io.jutil.springeasy.spring.config.node.MachineIdContext;
import io.jutil.springeasy.spring.config.node.NodeProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-05-28
 */
@ActiveProfiles("node")
@SpringBootTest(classes = Application.class)
class NodePropertiesTest {
	@Autowired
	NodeProperties prop;

	@Test
	void testMachineId() {
		int machineId = 100;
		Assertions.assertEquals(machineId, prop.getMachineId());
		Assertions.assertEquals(machineId, MachineIdContext.getMachineId());
	}
}
