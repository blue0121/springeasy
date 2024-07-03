package io.jutil.springeasy.mybatis.mapper;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.mybatis.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-07-01
 */
@Service
@Transactional
public class UserService {
	@Autowired
	UserDao userDao;

	@Autowired
	UserMapper userMapper;

	public int deleteAll() {
		return userMapper.deleteAll();
	}

	public Map<Long, UserEntity> listAll() {
		return userMapper.listAll();
	}

	public void test1(boolean valid) {
		this.insert("blue");
		this.insertList(9);
		if (!valid) {
			throw new RuntimeException();
		}
	}

	public void test2(boolean valid) {
		this.insertList(9);
		this.insert("blue");
		if (!valid) {
			throw new RuntimeException();
		}
	}

	private int insert(String name) {
		var entity = new UserEntity();
		entity.setId(IdGeneratorFactory.longId());
		entity.setName(name);
		return userMapper.insert(entity);
	}

	private int insertList(int count) {
		List<UserEntity> list = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			var entity = new UserEntity();
			entity.setName("blue" + i);
			list.add(entity);
		}
		return userDao.insertList(UserMapper.class, list);
	}
}
