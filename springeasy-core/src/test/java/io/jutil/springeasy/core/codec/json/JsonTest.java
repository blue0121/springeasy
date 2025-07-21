package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-07-14
 */
class JsonTest {

	@Test
	void testToString() {
		var now = DateUtil.now();
		var user = User.create(now);
		var json = Json.toString(user);
		System.out.println(json);
		var obj = JSON.parseObject(json);
		User.verify(obj, now);
		Assertions.assertEquals(User.class.getName(), obj.getString("@type"));
	}

	@Test
	void testToBytes() {
		var now = DateUtil.now();
		var user = User.create(now);
		var buf = Json.toBytes(user);

		var obj = JSON.parseObject(buf);
		User.verify(obj, now);
		Assertions.assertEquals(User.class.getName(), obj.getString("@type"));

		User view1 = Json.fromBytes(buf);
		User.verify(view1, now);

		var view2 = Json.fromBytes(buf, User.class);
		User.verify(view2, now);
	}

	@Test
	void testOutput() {
		var now = DateUtil.now();
		var user = new User(1, "blue", now, 2L);
		var json = Json.output(user);
		System.out.println(json);
		var obj = JSON.parseObject(json);
		Assertions.assertEquals(1, obj.getIntValue("id"));
		Assertions.assertEquals("blue", obj.getString("name"));
		Assertions.assertEquals(DateUtil.format(now), obj.getString("createTime"));
		Assertions.assertEquals("2", obj.getString("longId"));
		Assertions.assertFalse(obj.containsKey("@type"));
	}

	@Test
	void testRemoveType1() {
		var now = DateUtil.now();
		var user = new User(1, "blue", now, 2L);
		var json = Json.toString(user);
		var obj = JSON.parseObject(json);
		Assertions.assertTrue(obj.containsKey("@type"));
		Json.removeType(obj);
		System.out.println(obj);
		Assertions.assertFalse(obj.containsKey("@type"));
	}

	@Test
	void testSerialize1() {
		var cat1 = new Cat("cat1", "cat11");
		var cat2 = new Cat("cat2", "cat22");

		var animalList = new AnimalList();
		animalList.setAnimalList(List.of(cat1, cat2));
		var json = Json.toString(animalList);

		AnimalList view = Json.fromString(json);
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
		var json = Json.toString(animalList);

		AnimalList view = Json.fromString(json, AnimalList.class);
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
		private long longId;

		static User create(LocalDateTime now) {
			return new User(1, "blue", now, 2L);
		}

		static void verify(JSONObject obj, LocalDateTime now) {
			Assertions.assertNotNull(obj);
			Assertions.assertEquals(1, obj.getIntValue("id"));
			Assertions.assertEquals("blue", obj.getString("name"));
			Assertions.assertEquals(DateUtil.format(now), obj.getString("createTime"));
			Assertions.assertEquals("2", obj.getString("longId"));
		}

		static void verify(User user, LocalDateTime now) {
			Assertions.assertNotNull(user);
			Assertions.assertEquals(1, user.getId());
			Assertions.assertEquals("blue", user.getName());
			Assertions.assertEquals(now, user.getCreateTime());
			Assertions.assertEquals(2L, user.getLongId());
		}
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
	abstract static class Animal {
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
