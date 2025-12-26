package io.jutil.springeasy.mybatis.mapper;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.mybatis.dao.BatchDao;
import io.jutil.springeasy.mybatis.entity.Status;
import io.jutil.springeasy.mybatis.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
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
	UserMapper userMapper;

	@Autowired
	BatchDao batchDao;

	final JSONObject body = JSONObject.of("key", "value");

	public int deleteAll() {
		return userMapper.deleteAll();
	}

	public Map<Long, UserEntity> listAll() {
		return userMapper.listAll();
	}

	public void test1(boolean valid) {
		Assertions.assertEquals(1, this.insert("blue"));
		var count = 9;
		Assertions.assertEquals(9, this.insertList(count));
		if (!valid) {
			throw new RuntimeException();
		}
	}

	public void test2(boolean valid) {
		var count = 9;
		Assertions.assertEquals(9, this.insertList(count));
		Assertions.assertEquals(1, this.insert("blue"));
		if (!valid) {
			throw new RuntimeException();
		}
	}

	private int insert(String name) {
		var entity = new UserEntity();
		entity.setId(IdGeneratorFactory.longId());
		entity.setName(name);
		entity.setStatus(Status.ACTIVE);
		entity.setBody(body);
		return userMapper.insert(entity);
	}

	private int insertList(int count) {
		List<UserEntity> list = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			var entity = new UserEntity();
			entity.setId(IdGeneratorFactory.longId());
			entity.setName("blue" + i);
			entity.setStatus(Status.ACTIVE);
			entity.setBody(body);
			list.add(entity);
		}
		var sql = """
				insert into test_user (id, name, status, body, create_time, update_time) values
				 (:id, :name, :status, :body, now(), now())""";
		return batchDao.update(sql, list);
	}
}
