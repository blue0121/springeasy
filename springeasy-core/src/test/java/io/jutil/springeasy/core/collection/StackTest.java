package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class StackTest {

	@Test
	void testEmpty() {
		Stack<Integer> stack = Stack.create();
		Assertions.assertNull(stack.peek());
		Assertions.assertEquals(0, stack.size());
		Assertions.assertTrue(stack.isEmpty());
		Assertions.assertNull(stack.pop());
		Assertions.assertEquals(0, stack.size());
		Assertions.assertTrue(stack.isEmpty());
	}

	@Test
	void testPush() {
		Stack<Integer> stack = Stack.create();
		stack.push(1);
		Assertions.assertFalse(stack.isEmpty());
		Assertions.assertEquals(1, stack.size());
		Assertions.assertEquals(1, stack.peek());
		Assertions.assertEquals(1, stack.pop());
		Assertions.assertNull(stack.peek());
		Assertions.assertNull(stack.pop());
		Assertions.assertTrue(stack.isEmpty());
		Assertions.assertEquals(0, stack.size());
	}

}
