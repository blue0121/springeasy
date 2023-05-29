package io.jutil.springeasy.core.dictionary;

import io.jutil.springeasy.internal.core.dictionary.DefaultDictionaryCache;

/**
 * @author Jin Zheng
 * @since 2023-05-28
 */
public interface DictionaryCache {
	/**
	 * 获取字典解析器的实例(单例)
	 *
	 * @return
	 */
	static DictionaryCache getInstance() {
		return DefaultDictionaryCache.getInstance();
	}

	void add(Dictionary dictionary);

	/**
	 * 根据字典索引值获取对象
	 *
	 * @param clazz 字典类型
	 * @param index 索引值
	 * @return 对象
	 */
	<T extends Dictionary> T getFromIndex(Class<T> clazz, Integer index);

	/**
	 * 根据字典英文名称获取对象
	 *
	 * @param clazz 字典类型
	 * @param name  英文名称
	 * @return 对象
	 */
	<T extends Dictionary> T getFromName(Class<T> clazz, String name);

	/**
	 * 根据字典中文名称获取对象
	 *
	 * @param clazz 字典类型
	 * @param label 中文名称
	 * @return 对象
	 */
	<T extends Dictionary> T getFromLabel(Class<T> clazz, String label);
}
