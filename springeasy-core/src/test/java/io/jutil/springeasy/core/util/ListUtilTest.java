package io.jutil.springeasy.core.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-10-16
 */
class ListUtilTest {

	final User u1 = User.create(1, 11, "name1");
	final User u11 = User.create(11, 11, "name11");
	final User u2 = User.create(2, 12, "name2");
	final User u22 = User.create(22, 12, "name22");

	@Test
	void testMap() {
		List<User> list = new ArrayList<>();
		list.add(u1);
		list.add(null);
		list.add(u2);
		var map = ListUtil.toMap(list, User::getId);
		Assertions.assertEquals(2, map.size());
		Assertions.assertSame(u1, map.get(1L));
		Assertions.assertSame(u2, map.get(2L));
	}

	@Test
	void testGroupMap() {
		List<User> list = new ArrayList<>();
		list.add(u1);
		list.add(u11);
		list.add(null);
		list.add(u2);
		list.add(u22);

		var map = ListUtil.groupMap(list, User::getGroupId);
		Assertions.assertEquals(2, map.size());

		var l1 = map.get(11L);
		Assertions.assertEquals(2, l1.size());
		Assertions.assertSame(u1, l1.getFirst());
		Assertions.assertSame(u11, l1.getLast());

		var l2 = map.get(12L);
		Assertions.assertEquals(2, l2.size());
		Assertions.assertSame(u2, l2.getFirst());
		Assertions.assertSame(u22, l2.getLast());
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static class User {
		private long id;
		private long groupId;
		private String name;

		public static User create(long id, long groupId, String name) {
			var user = new User();
			user.setId(id);
			user.setGroupId(groupId);
			user.setName(name);
			return user;
		}
	}
}
