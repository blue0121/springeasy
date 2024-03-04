package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
class JsonUtilTest {
	@Test
	void testRemoveType1() {
		var now = DateUtil.now();
		var user = new User(1, "blue", now);
		var json = JsonUtil.toString(user);
		var obj = JSON.parseObject(json);
		Assertions.assertTrue(obj.containsKey("@type"));
		JsonUtil.removeType(obj);
		System.out.println(obj);
		Assertions.assertFalse(obj.containsKey("@type"));
	}

	@Test
	void testToString() {
		var now = DateUtil.now();
		var user = new User(1, "blue", now);
		var json = JsonUtil.toString(user);
		System.out.println(json);
		var obj = JSON.parseObject(json);
		Assertions.assertEquals(1, obj.getIntValue("id"));
		Assertions.assertEquals("blue", obj.getString("name"));
		Assertions.assertEquals(DateUtil.format(now), obj.getString("createTime"));
		Assertions.assertEquals(User.class.getName(), obj.getString("@type"));
	}

	@Test
	void testOutput() {
		var now = DateUtil.now();
		var user = new User(1, "blue", now);
		var json = JsonUtil.output(user);
		System.out.println(json);
		var obj = JSON.parseObject(json);
		Assertions.assertEquals(1, obj.getIntValue("id"));
		Assertions.assertEquals("blue", obj.getString("name"));
		Assertions.assertEquals(DateUtil.format(now), obj.getString("createTime"));
		Assertions.assertFalse(obj.containsKey("@type"));
	}

	@Test
	void testFromString() {
		var json1 = "{\"@type\":\"io.jutil.springeasy.core.util.JsonUtilTest$User\",\"createTime\":\"2022-12-20 14:50:53\",\"id\":1,\"name\":\"blue\"}";
		var json2 = "{\"createTime\":\"2022-12-20 14:50:54\",\"id\":1,\"name\":\"blue\"}";

		JsonUtil.registerAutoType("io.jutil.springeasy.core");
		User user1 = JsonUtil.fromString(json1);
		Assertions.assertEquals(1, user1.getId());
		Assertions.assertEquals("blue", user1.getName());

		User user2 = JsonUtil.fromString(json2, User.class);
		Assertions.assertEquals(1, user2.getId());
		Assertions.assertEquals("blue", user2.getName());
	}

	@Test
	void testAutoType() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		var user = new User(1, "blue", now);
		var json = JsonUtil.toBytes(user);
		System.out.println(new String(json));

		User view = JsonUtil.fromBytes(json);
		Assertions.assertEquals(User.class, view.getClass());
		Assertions.assertEquals(1, view.getId());
		Assertions.assertEquals("blue", view.getName());
	}

	@Test
	void testAutoType2() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		var user = new User(1, "blue", now);
		var json = JsonUtil.toString(user);
		System.out.println(json);

		User view = JsonUtil.fromString(json);
		Assertions.assertEquals(User.class, view.getClass());
		Assertions.assertEquals(1, view.getId());
		Assertions.assertEquals("blue", view.getName());
	}

	@Test
	void testSerialize1() {
		var cat1 = new Cat("cat1", "cat11");
		var cat2 = new Cat("cat2", "cat22");

		var animalList = new AnimalList();
		animalList.setAnimalList(List.of(cat1, cat2));
		var json = JsonUtil.toString(animalList);

		AnimalList view = JsonUtil.fromString(json);
		var list = view.getAnimalList();
		Assertions.assertEquals(2, list.size());
		Assertions.assertEquals(Cat.class, list.get(0).getClass());
		Assertions.assertEquals("cat1", list.get(0).getName());
		Assertions.assertEquals(Cat.class, list.get(1).getClass());
		Assertions.assertEquals("cat2", list.get(1).getName());
	}

	@Test
	void testSerialize2() {
		var dog1 = new Dog("dog1", "dog11");
		var dog2 = new Dog("dog2", "dog22");

		var animalList = new AnimalList();
		animalList.setAnimalList(List.of(dog1, dog2));
		var json = JsonUtil.toString(animalList);

		AnimalList view = JsonUtil.fromString(json, AnimalList.class);
		var list = view.getAnimalList();
		Assertions.assertEquals(2, list.size());
		Assertions.assertEquals(Dog.class, list.get(0).getClass());
		Assertions.assertEquals("dog1", list.get(0).getName());
		Assertions.assertEquals(Dog.class, list.get(1).getClass());
		Assertions.assertEquals("dog2", list.get(1).getName());
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class User {
		private Integer id;
		private String name;
		private LocalDateTime createTime;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static class AnimalList {
		private List<? extends Animal> animalList;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static abstract class Animal {
		private String name;

		public Animal(String name) {
			this.name = name;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static class Cat extends Animal {
		private String kind;

		public Cat(String name, String kind) {
			super(name);
			this.kind = kind;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static class Dog extends Animal {
		private String type;

		public Dog(String name, String type) {
			super(name);
			this.type = type;
		}
	}
}
