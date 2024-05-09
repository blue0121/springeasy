package io.jutil.springeasy.jpa.id;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
@NoArgsConstructor
public class UuidGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor implementor, Object object) {
		return IdGeneratorFactory.uuid();
	}
}
