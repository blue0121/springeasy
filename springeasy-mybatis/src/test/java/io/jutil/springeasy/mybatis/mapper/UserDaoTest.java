package io.jutil.springeasy.mybatis.mapper;

import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.mybatis.BaseTest;
import io.jutil.springeasy.mybatis.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-06-13
 */
@Slf4j
class UserDaoTest extends BaseTest {
	@Autowired
	UserDao userDao;

	@Autowired
	UserMapper userMapper;

	@BeforeEach
	void beforeEach() {
		userMapper.deleteAll();
	}

	@Test
	void testInsertListTest() {
		this.insertListTest();
	}

	List<UserEntity> insertListTest() {
		int count = 1000;
		List<UserEntity> list = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			var entity = new UserEntity();
			entity.setName("blue" + i);
			list.add(entity);
		}

		long start = System.currentTimeMillis();
		var rs = userDao.insertList(UserMapper.class, list);
		log.info("InsertList 用时: {} ms", System.currentTimeMillis() - start);
		Assertions.assertEquals(count, rs);
		this.verify(list);
		return list;
	}

	@Test
	void testUpdateListTest() {
		int count = 1000;
		List<UserEntity> list = this.insertListTest();

		for (int i = 1; i <= count; i++) {
			var entity = list.get(i - 1);
			entity.setName("red" + i);
		}
		long start = System.currentTimeMillis();
		int rs = userDao.updateList(UserMapper.class, list);
		log.info("UpdateList 用时: {} ms", System.currentTimeMillis() - start);
		Assertions.assertEquals(count, rs);
		this.verify(list);
	}

	private void verify(List<UserEntity> list) {
		var now = DateUtil.now();
		var map = userMapper.listAll();
		Assertions.assertEquals(list.size(), map.size());
		for (var entity : list) {
			var view = map.get(entity.getId());
			if (view == null) {
				System.out.println(entity.getId());
			}
			Assertions.assertNotNull(view);
			Assertions.assertEquals(entity.getName(), view.getName());
			Assertions.assertTrue(DateUtil.equal(now, view.getCreateTime()));
			Assertions.assertTrue(DateUtil.equal(now, view.getUpdateTime()));
		}
	}
}
