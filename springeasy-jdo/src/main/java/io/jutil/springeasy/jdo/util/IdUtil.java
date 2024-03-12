package io.jutil.springeasy.jdo.util;

import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.parser.IdMetadata;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
public class IdUtil {
	private IdUtil() {
	}

	public static IdMetadata checkSingleId(EntityMetadata entityMetadata) {
		var id = entityMetadata.getIdMetadata();
		if (id == null) {
			throw new JdoException(entityMetadata.getTargetClass().getName()
					+ " @Id主键个数大于1，不支持该操作");
		}

		return id;
	}
}
