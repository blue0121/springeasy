package io.jutil.springeasy.core.collection;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-17
 */
class SortTest {
	final Map<String, String> fieldMap = Map.of("id", "e.id", "name", "e.name");

	@Test
	void testToOrderByString1() {
		Assertions.assertEquals("e.id DESC",
				new Sort("id").toOrderByString(fieldMap));
		Assertions.assertEquals("e.id ASC",
				new Sort("id", Sort.Direction.ASC).toOrderByString(fieldMap));
		Assertions.assertEquals("e.id DESC",
				new Sort("id", "DESC").toOrderByString(fieldMap));
		Assertions.assertEquals("e.id DESC",
				new Sort("id").toOrderByString(fieldMap));
	}

	@Test
	void testToOrderByString2() {
		var sort = new Sort("id", "DESC");
		sort.add("name", "asc");
		Assertions.assertEquals("e.id DESC,e.name ASC", sort.toOrderByString(fieldMap));
	}

	@Test
	void testCheck() {
		var sort = new Sort("id", "DESC");
		sort.check(List.of("id"));

		Assertions.assertThrows(ValidationException.class, () -> sort.check(List.of("eid")));
	}
}
