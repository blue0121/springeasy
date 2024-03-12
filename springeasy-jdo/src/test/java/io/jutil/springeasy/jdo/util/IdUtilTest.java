package io.jutil.springeasy.jdo.util;

import io.jutil.springeasy.core.reflect.ClassFieldOperation;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.parser.IdMetadata;
import io.jutil.springeasy.jdo.parser.IdType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
class IdUtilTest {
	@Test
	void testCheckSingleId() {
		IdMetadata id = this.create("id1", IdType.LONG, GeneratorType.SNOWFLAKE);
		var entityConfig = Mockito.mock(EntityMetadata.class);
		Mockito.when(entityConfig.getTargetClass()).then(i -> Object.class);
		Assertions.assertThrows(JdoException.class, () -> IdUtil.checkSingleId(entityConfig));

		Mockito.when(entityConfig.getIdMetadata()).thenReturn(id);
		var id2 = IdUtil.checkSingleId(entityConfig);
		Assertions.assertEquals(id, id2);
	}

	private IdMetadata create(String fieldName, IdType idType, GeneratorType generatorType) {
		IdMetadata id = Mockito.spy(IdMetadata.class);
		Mockito.when(id.getFieldName()).thenReturn(fieldName);
		Mockito.when(id.getIdType()).thenReturn(idType);
		Mockito.when(id.getGeneratorType()).thenReturn(generatorType);
		Mockito.when(id.getFieldOperation()).thenReturn(Mockito.mock(ClassFieldOperation.class));
		return id;
	}
}
