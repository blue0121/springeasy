package io.jutil.springeasy.jdo.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class DefaultExpressionTest {

	@Test
	void testAnd() {
		Expression expression = Expression.and();
		expression.add("id=1")
				.add("name='a'");
		Assertions.assertEquals("id=1 and name='a'", expression.toString());

		Expression expression2 = Expression.and();
		expression2.add("state=1")
				.add(expression);
		Assertions.assertEquals("state=1 and (id=1 and name='a')", expression2.toString());
	}

	@Test
	void testOr() {
		Expression expression = Expression.or();
		expression.add("id=1")
				.add("name='a'");
		Assertions.assertEquals("id=1 or name='a'", expression.toString());

		Expression expression2 = Expression.or();
		expression2.add("state=1")
				.add(expression);
		Assertions.assertEquals("state=1 or (id=1 or name='a')", expression2.toString());
	}

	@Test
	void testAndOr() {
		Expression expression = Expression.or();
		expression.add("id=1")
				.add("name='a'");
		Assertions.assertEquals("id=1 or name='a'", expression.toString());

		Expression expression2 = Expression.and();
		expression2.add("state=1")
				.add(expression);
		Assertions.assertEquals("state=1 and (id=1 or name='a')", expression2.toString());
	}

}
