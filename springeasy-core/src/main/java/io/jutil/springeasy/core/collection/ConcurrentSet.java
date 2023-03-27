package io.jutil.springeasy.core.collection;

import io.jutil.springeasy.internal.core.collection.ConcurrentHashSet;

import java.util.Collection;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface ConcurrentSet<E> extends Set<E> {
	static <E> ConcurrentSet<E> create(Collection<E> c) {
		if (c == null || c.isEmpty()) {
			return new ConcurrentHashSet<>();
		}

		return new ConcurrentHashSet<>(c);
	}
}
