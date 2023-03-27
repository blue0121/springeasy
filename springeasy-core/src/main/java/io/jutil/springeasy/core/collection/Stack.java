package io.jutil.springeasy.core.collection;

import io.jutil.springeasy.internal.core.collection.ArrayStack;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface Stack<E> {
	static <T> Stack<T> create() {
		return new ArrayStack<>();
	}

	E pop();

	void push(E o);

	E peek();

	int size();

	boolean isEmpty();
}
