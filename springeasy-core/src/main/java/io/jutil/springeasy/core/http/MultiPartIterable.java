package io.jutil.springeasy.core.http;

import java.util.Iterator;

/**
 * @author Jin Zheng
 * @since 1.0 2020-04-30
 */
class MultiPartIterable implements Iterable<byte[]> {
	private final Iterator<byte[]> iterator;

	MultiPartIterable(Iterator<byte[]> iterator) {
		this.iterator = iterator;
	}

	@Override
	public Iterator<byte[]> iterator() {
		return iterator;
	}
}
