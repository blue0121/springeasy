package io.jutil.springeasy.core.collection;

import java.util.Arrays;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public class Tuple {
	private final Object[] cacheObjects;

	public Tuple(Object...objects) {
		this.cacheObjects = Arrays.copyOf(objects, objects.length);
	}

	public int getSize() {
		return this.cacheObjects.length;
	}

	@SuppressWarnings("unchecked")
	public <T> T getN(int index) {
		if (index < 1 || index > cacheObjects.length) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return (T) this.cacheObjects[index - 1];
	}

	public <T> T getFirst() {
		return getN(1);
	}

	public <T> T getSecond() {
		return getN(2);
	}

	public <T> T getThird() {
		return getN(3);
	}
}
