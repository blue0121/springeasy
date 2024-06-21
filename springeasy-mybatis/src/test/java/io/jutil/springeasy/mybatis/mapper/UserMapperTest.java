package io.jutil.springeasy.mybatis.mapper;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.mybatis.BaseTest;
import io.jutil.springeasy.mybatis.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */

class UserMapperTest extends BaseTest {
	@Autowired
	UserMapper mapper;

	@BeforeEach
	void beforeEach() {
		mapper.deleteAll();
	}

	@Test
	void testCrud() {
		var entity = new UserEntity();
		entity.setId(IdGeneratorFactory.longId());
		entity.setName("blue");
		Assertions.assertEquals(1, mapper.insert(entity));
		var now = DateUtil.now();

		var view = mapper.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(entity.getName(), view.getName());
		Assertions.assertTrue(DateUtil.equal(now, view.getCreateTime()));
		Assertions.assertTrue(DateUtil.equal(now, view.getUpdateTime()));

		var map = mapper.listAll();
		Assertions.assertEquals(1, map.size());
		var view0 = map.get(entity.getId());
		Assertions.assertEquals(entity.getId(), view0.getId());
		Assertions.assertEquals(entity.getName(), view0.getName());
		Assertions.assertTrue(DateUtil.equal(now, view0.getCreateTime()));
		Assertions.assertTrue(DateUtil.equal(now, view0.getUpdateTime()));

		entity.setName("red");
		Assertions.assertEquals(1, mapper.update(entity));
		view = mapper.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(entity.getName(), view.getName());

		Assertions.assertEquals(1, mapper.deleteAll());
		Assertions.assertNull(mapper.getOne(entity.getId()));
	}
}
