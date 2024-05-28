package io.jutil.springeasy.jpa.id;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.spring.config.node.MachineIdContext;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
@NoArgsConstructor
public class LongIdGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor implementor, Object object) {
		var machineId = MachineIdContext.getMachineId();
		return IdGeneratorFactory.longId(machineId);
	}
}
