package io.jutil.springeasy.jdo.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class DefaultOrderByTest {

	@Test
	void test1() {
		OrderBy order = new DefaultOrderBy();
		order.add("id desc");
		order.add("date asc");
		String str = order.toString();
		Assertions.assertEquals("id desc,date asc", str);
	}

	@Test
	void test2() {
		OrderBy order = new DefaultOrderBy();
		order.add("id desc");
		String str = order.toString();
		Assertions.assertEquals("id desc", str);
	}

	@Test
	void test3() {
		OrderBy order = new DefaultOrderBy();
		String str = order.toString();
		Assertions.assertTrue(str.isEmpty());
	}

}
