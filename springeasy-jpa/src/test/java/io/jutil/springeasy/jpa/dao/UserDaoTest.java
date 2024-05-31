package io.jutil.springeasy.jpa.dao;

import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.jpa.Application;
import io.jutil.springeasy.jpa.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-02
 */
@SpringBootTest(classes = Application.class)
@Transactional
class UserDaoTest {
	@Autowired
	UserDao userDao;

	final String name1 = "blue1";
	final String name2 = "blue2";

	@BeforeEach
	void beforeEach() {
		userDao.deleteAll();
	}

	@Test
	void testCrud() {
		var entity = new UserEntity();
		entity.setName(name1);
		userDao.insert(entity);
		Assertions.assertNotNull(entity.getId());

		var view = userDao.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(name1, view.getName());

		entity.setName(name2);
		userDao.update(entity);

		view = userDao.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(name2, view.getName());

		System.out.println(entity.getId());
		var rs = userDao.deleteList(List.of(entity.getId()));
		Assertions.assertEquals(1, rs);

		var list = userDao.getList(List.of(entity.getId()));
		Assertions.assertEquals(0, list.size());
	}

	@Test
	void testDelete() {
		var entity = new UserEntity();
		entity.setName(name1);
		userDao.insert(entity);
		Assertions.assertNotNull(entity.getId());

		var view = userDao.getOne(entity.getId());
		Assertions.assertNotNull(view);
		Assertions.assertEquals(name1, view.getName());

		var rs = userDao.deleteList(List.of(entity.getId()));
		Assertions.assertEquals(1, rs);

		Assertions.assertNull(userDao.getOne(entity.getId()));
	}

	@Test
	void testAll() {
		var entity = new UserEntity();
		entity.setName(name1);
		userDao.insert(entity);

		Assertions.assertEquals(1, userDao.listAll().size());
		Assertions.assertEquals(1, userDao.count());

		Assertions.assertEquals(1, userDao.deleteAll());

		Assertions.assertEquals(0, userDao.listAll().size());
		Assertions.assertEquals(0, userDao.count());
	}

	@Test
	void testUpdateBy() {
		var entity = new UserEntity();
		entity.setName(name1);
		userDao.insert(entity);

		var rs = userDao.updateBy(entity.getId(), Map.of("name", name2));
		Assertions.assertTrue(rs > 0);

		var view = userDao.getOne(entity.getId());
		userDao.refresh(view);
		Assertions.assertNotNull(view);
		Assertions.assertEquals(name2, view.getName());
		Assertions.assertEquals(name2, entity.getName());
	}

	@Test
	void testDeleteBy() {
		var entity = new UserEntity();
		entity.setName(name1);
		userDao.insert(entity);

		Assertions.assertEquals(1, userDao.count());

		var rs = userDao.deleteBy(Map.of("name", name1));
		Assertions.assertTrue(rs > 0);

		Assertions.assertEquals(0, userDao.count());
	}

	@Test
	void testPage() {
		int count = 10;
		for (int i = 1; i <= count; i++) {
			var entity = new UserEntity();
			entity.setName("blue" + i);
			userDao.insert(entity);
		}
		Assertions.assertEquals(10, userDao.count());

		var page = new Page(1, 2);
		page = userDao.listPage(page, f -> f.orderBy("e.id"));
		Assertions.assertEquals(1, page.getPageIndex());
		Assertions.assertEquals(2, page.getPageSize());
		Assertions.assertEquals(10, page.getTotal());
		Assertions.assertEquals(5, page.getTotalPage());
		List<UserEntity> list1 = page.getContents();
		Assertions.assertEquals(2, list1.size());
		Assertions.assertEquals(name1, list1.get(0).getName());
		Assertions.assertEquals(name2, list1.get(1).getName());

		page = new Page(5, 2);
		page = userDao.listPage(page, f -> f.orderBy("e.id desc"));
		Assertions.assertEquals(5, page.getPageIndex());
		Assertions.assertEquals(2, page.getPageSize());
		Assertions.assertEquals(10, page.getTotal());
		Assertions.assertEquals(5, page.getTotalPage());
		List<UserEntity> list5 = page.getContents();
		Assertions.assertEquals(2, list5.size());
		Assertions.assertEquals(name2, list5.get(0).getName());
		Assertions.assertEquals(name1, list5.get(1).getName());
	}
}
