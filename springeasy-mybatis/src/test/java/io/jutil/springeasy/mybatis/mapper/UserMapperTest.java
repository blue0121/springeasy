package io.jutil.springeasy.mybatis.mapper;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.mybatis.entity.Status;
import io.jutil.springeasy.mybatis.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */
public abstract class UserMapperTest {
	@Autowired
	UserMapper mapper;

	final JSONObject body = JSONObject.of("key", "value");

	@BeforeEach
	public void beforeEach() {
		mapper.deleteAll();
	}

	@Test
	public void testCrud() {
		var entity = new UserEntity();
		entity.setId(IdGeneratorFactory.longId());
		entity.setName("blue");
		entity.setBody(body);
		entity.setStatus(Status.INACTIVE);
		Assertions.assertEquals(1, mapper.insert(entity));

		var view = mapper.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(entity.getName(), view.getName());
		Assertions.assertEquals(Status.INACTIVE, view.getStatus());
		Assertions.assertEquals(body, view.getBody());
		Assertions.assertNotNull(view.getCreateTime());
		Assertions.assertNotNull(view.getUpdateTime());

		var map = mapper.listAll();
		Assertions.assertEquals(1, map.size());
		var view0 = map.get(entity.getId());
		Assertions.assertEquals(entity.getId(), view0.getId());
		Assertions.assertEquals(entity.getName(), view0.getName());
		Assertions.assertEquals(Status.INACTIVE, view0.getStatus());
		Assertions.assertEquals(body, view0.getBody());
		Assertions.assertNotNull(view0.getCreateTime());
		Assertions.assertNotNull(view0.getUpdateTime());

		entity.setName("red");
		Assertions.assertEquals(1, mapper.update(entity));
		view = mapper.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(entity.getName(), view.getName());

		Assertions.assertEquals(1, mapper.deleteAll());
		Assertions.assertNull(mapper.getOne(entity.getId()));
	}
}
