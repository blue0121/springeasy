package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.jpa.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
@SpringBootTest(classes = Application.class)
class UuidEntityTest {
	@Autowired
	RoleRepository repository;

	final String name = "blue";

	@BeforeEach
	void beforeEach() {
		repository.deleteAllInBatch();
	}

	@Test
	void testSave() {
		var entity = new RoleEntity();
		entity.setName(name);
		repository.save(entity);
		System.out.println(entity.getId());

		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getCreateTime());
		Assertions.assertNotNull(entity.getUpdateTime());

		var view = repository.findById(entity.getId()).orElse(null);
		Assertions.assertNotNull(view);
		Assertions.assertEquals(name, view.getName());
	}

}
