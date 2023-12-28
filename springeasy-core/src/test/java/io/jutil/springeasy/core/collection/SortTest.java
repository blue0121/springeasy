package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-11-17
 */
class SortTest {
	final String prefix = "e";

	@Test
	void test1() {
		Assertions.assertEquals("e.id desc",
				new Sort("id").toOrderBy(prefix));
		Assertions.assertEquals("e.id asc",
				new Sort("id", Sort.Direction.ASC).toOrderBy(prefix));
		Assertions.assertEquals("e.id desc",
				new Sort("id", "desc").toOrderBy(prefix));
		Assertions.assertEquals("id desc",
				new Sort("id").toOrderBy(null));
	}

	@Test
	void test2() {
		var sort = new Sort("id", "desc");
		sort.add("sort", "asc");
		Assertions.assertEquals("e.id desc,e.sort asc", sort.toOrderBy(prefix));
	}

}
