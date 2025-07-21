package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSON;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2025-07-14
 */
class EnumJsonTest {

	@Test
	void testUser1() {
		var user = new User("blue", Status.ACTIVE);
		var json = Json.toString(user);
		System.out.println(json);
		User view = Json.fromString(json);
		Assertions.assertEquals(user, view);
	}

	@Test
	void testUser2() {
		var json = """
				{
					"name": "blue",
					"status": 0
				}""";
		User view = Json.fromString(json, User.class);
		Assertions.assertEquals("blue", view.name);
		Assertions.assertEquals(Status.ACTIVE, view.status);
	}

	@Test
	void testUser3() {
		var json = """
				{
					"name": "blue",
					"status": "ACTIVE"
				}""";
		User view = Json.fromString(json, User.class);
		Assertions.assertEquals("blue", view.name);
		Assertions.assertEquals(Status.ACTIVE, view.status);
	}

	@Test
	void testUser4() {
		var json = """
				{
					"name": "blue",
					"status": 2
				}""";
		Assertions.assertThrows(ValidationException.class, () -> Json.fromString(json, User.class));
	}

	@Test
	void testUser5() {
		var json = """
				{
					"name": "blue",
					"status": "abc"
				}""";
		Assertions.assertThrows(ValidationException.class, () -> Json.fromString(json, User.class));
	}

	@Test
	void testResult1() {
		var result = new Result("blue", Type.YES);
		var json = Json.toString(result);
		System.out.println(json);
		Result view = Json.fromString(json);
		Assertions.assertEquals(result, view);
	}

	@Test
	void testResult2() {
		var json = """
				{
					"name": "blue",
					"type": 1
				}""";
		Result view = Json.fromString(json, Result.class);
		Assertions.assertEquals("blue", view.name);
		Assertions.assertEquals(Type.YES, view.type);
	}

	@Test
	void testResult3() {
		var json = """
				{
					"name": "blue",
					"type": "YES"
				}""";
		Result view = Json.fromString(json, Result.class);
		Assertions.assertEquals("blue", view.name);
		Assertions.assertEquals(Type.YES, view.type);
	}

	@Test
	void testResult4() {
		var json = """
				{
					"name": "blue",
					"type": 0
				}""";
		Assertions.assertThrows(ValidationException.class, () -> Json.fromString(json, Result.class));
	}

	@Test
	void testResult5() {
		var json = """
				{
					"name": "blue",
					"type": "abc"
				}""";
		Assertions.assertThrows(ValidationException.class, () -> Json.fromString(json, Result.class));
	}

	enum Status {
		ACTIVE,
		INACTIVE,
		;

		static {
			JSON.register(Status.class, new EnumObjectReader<>(Status.class));
		}
	}

	@Getter
	@AllArgsConstructor
	enum Type implements Dict {
		YES(1, "是"),
		NO(2, "否"),

		;
		private final int code;
		private final String label;

		static {
			JSON.register(Type.class, new EnumObjectReader<>(Type.class));
			JSON.register(Type.class, EnumObjectWriter.INSTANCE);
		}
	}

	record User(String name, Status status) {
	}

	record Result(String name, Type type) {}
}
