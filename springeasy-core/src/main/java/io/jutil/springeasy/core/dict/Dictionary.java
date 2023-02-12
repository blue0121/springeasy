package io.jutil.springeasy.core.dict;

import io.jutil.springeasy.internal.core.dict.DefaultDictionaryCache;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2020-04-11
 */
@Getter
@EqualsAndHashCode
public abstract class Dictionary {
	private static DefaultDictionaryCache cache = DefaultDictionaryCache.getInstance();

	private int index;
	private String name;
	private Color color;

	protected Dictionary(int index, String name, Color color) {
		this.index = index;
		this.name = name;
		this.color = color;

		cache.add(this);
	}

	@Override
	public String toString() {
		String type = this.getClass().getSimpleName();
		return String.format("%s{%s}", type, name);
	}

}
