package io.jutil.springeasy.jdo.util;

import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.parser.VersionMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
class VersionUtilTest {

	@Test
	void testIsForce() {
		var entity1 = this.create("id1", true, 1);
		Assertions.assertTrue(VersionUtil.isForce(entity1));

		var entity2 = this.create("id1", false, 1);
		Assertions.assertFalse(VersionUtil.isForce(entity2));

		var entity3 = Mockito.mock(EntityMetadata.class);
		Assertions.assertFalse(VersionUtil.isForce(entity3));
	}

	private EntityMetadata create(String fieldName, boolean isForce, int defaultValue) {
		var entity = Mockito.mock(EntityMetadata.class);
		var ver = Mockito.mock(VersionMetadata.class);
		Mockito.when(entity.getVersionMetadata()).thenReturn(ver);
		Mockito.when(ver.getFieldName()).thenReturn(fieldName);
		Mockito.when(ver.isForce()).thenReturn(isForce);
		Mockito.when(ver.getDefaultValue()).thenReturn(defaultValue);
		return entity;
	}

}
