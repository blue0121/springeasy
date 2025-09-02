package io.jutil.springeasy.mybatis.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jin Zheng
 * @since 2024-07-03
 */
public abstract class UserServiceTest {
	@Autowired
	UserService service;

	@BeforeEach
	public void beforeEach() {
		service.deleteAll();
	}

	@Test
	public void testValid1() {
		service.test1(true);
		var list = service.listAll();
		Assertions.assertEquals(10, list.size());
	}

	@Test
	public void testValid2() {
		service.test2(true);
		var list = service.listAll();
		Assertions.assertEquals(10, list.size());
	}

	@Test
	public void testInvalid1() {
		Assertions.assertThrows(RuntimeException.class, () -> service.test1(false));
		var list = service.listAll();
		Assertions.assertTrue(list.isEmpty());
	}

	@Test
	public void testInvalid2() {
		Assertions.assertThrows(RuntimeException.class, () -> service.test2(false));
		var list = service.listAll();
		Assertions.assertTrue(list.isEmpty());
	}
}
