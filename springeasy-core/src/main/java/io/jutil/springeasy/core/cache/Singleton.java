package io.jutil.springeasy.core.cache;

import io.jutil.springeasy.core.util.AssertUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public class Singleton {
	private static final String STR_PARAMETER = "Parameter";
	private static final String STR_OBJECT = "Object";

	private static final ConcurrentMap<Object, Object> POOL = new ConcurrentHashMap<>();

	private Singleton() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object param) {
		AssertUtil.notNull(param, STR_PARAMETER);
		return (T) POOL.get(param);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object param, Function<Object, T> f) {
		AssertUtil.notNull(param, STR_PARAMETER);
		AssertUtil.notNull(f, "Function");
		return (T) POOL.computeIfAbsent(param, k -> f.apply(param));
	}

	public static void put(Object object) {
		AssertUtil.notNull(object, STR_OBJECT);
		put(object.getClass(), object);
	}

	public static void put(Object param, Object object) {
		AssertUtil.notNull(param, STR_PARAMETER);
		AssertUtil.notNull(object, STR_OBJECT);
		Object old = POOL.putIfAbsent(param, object);
		if (old != null && old != object) {
			throw new IllegalArgumentException(param + " exist");
		}
	}

	public static void remove(Object param) {
		AssertUtil.notNull(param, STR_PARAMETER);
		POOL.remove(param);
	}

	public static void clear() {
		POOL.clear();
	}

	public static boolean isEmpty() {
		return POOL.isEmpty();
	}

	public static int size() {
		return POOL.size();
	}
}
