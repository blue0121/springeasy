package io.jutil.springeasy.core.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class Stack<E> {
	private final List<E> array;

	public Stack() {
		this(10);
	}

	public Stack(int capacity) {
		this.array = new ArrayList<>(capacity);
	}

	public E pop() {
		if (array.isEmpty()) {
			return null;
		}
		return array.remove(array.size() - 1);
	}

	public void push(E o) {
		array.add(o);
	}

	public E peek() {
		if (array.isEmpty()) {
			return null;
		}
		return array.get(array.size() - 1);
	}

	public int size() {
		return array.size();
	}

	public boolean isEmpty() {
		return array.isEmpty();
	}
}
