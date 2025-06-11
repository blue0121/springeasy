package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import io.jutil.springeasy.core.id.IdGeneratorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-12-19
 */
@SuppressWarnings("java:S3577")
class JsonUtilTest2 {

	@Test
	void testLong() {
		var u1 = new User(IdGeneratorFactory.longId());
		var json = JsonUtil.output(u1);
		System.out.println(json);
		var obj = JSON.parseObject(json);
		Assertions.assertEquals(String.valueOf(u1.id), obj.getString("id"));

		var u2 = JsonUtil.fromString(json, User.class);
		Assertions.assertEquals(u1.id, u2.id);
	}

	@Getter
	@AllArgsConstructor
	static class User {
		private Long id;
	}

	@Test
	void testString() {
		var str = "hello world";
		var json = JsonUtil.toString(str);
		System.out.println(json);
		var view = JsonUtil.fromString(json, String.class);
		System.out.println(view);
		Assertions.assertEquals(str, view);
	}
}
