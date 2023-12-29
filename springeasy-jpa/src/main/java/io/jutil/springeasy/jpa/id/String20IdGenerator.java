package io.jutil.springeasy.jpa.id;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
@NoArgsConstructor
public class String20IdGenerator implements IdentifierGenerator {
	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		return IdGeneratorFactory.string20();
	}
}
