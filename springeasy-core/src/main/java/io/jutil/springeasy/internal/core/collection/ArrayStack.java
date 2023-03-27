package io.jutil.springeasy.internal.core.collection;

import io.jutil.springeasy.core.collection.Stack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class ArrayStack<E> implements Stack<E> {
	private final List<E> array;

	public ArrayStack() {
		array = new ArrayList<>();
	}

	@Override
	public E pop() {
		if (array.isEmpty()) {
			return null;
		}
		return array.remove(array.size() - 1);
	}

	@Override
	public void push(E o) {
		array.add(o);
	}

	@Override
	public E peek() {
		if (array.isEmpty()) {
			return null;
		}
		return array.get(array.size() - 1);
	}

	@Override
	public int size() {
		return array.size();
	}

	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
}
