package io.jutil.springeasy.core.dict;


import io.jutil.springeasy.internal.core.dict.DefaultDictionaryCache;

/**
 * @author Jin Zheng
 * @since 2022-12-21
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

	/**
	 * 根据字典索引值获取对象
	 *
	 * @param clazz 字典类型
	 * @param index 索引值
	 * @return 对象
	 */
	<T extends Dictionary> T getFromIndex(Class<T> clazz, Integer index);

	/**
	 * 根据字典中文名称获取对象
	 *
	 * @param clazz 字典类型
	 * @param name  中文名称
	 * @return 对象
	 */
	<T extends Dictionary> T getFromName(Class<T> clazz, String name);

	/**
	 * 根据对象获取字典索引值
	 *
	 * @param dict 对象
	 * @return 字典索引值
	 */
	int getIndexFromObject(Dictionary dict);

	/**
	 * 根据对象获取字典中文名称
	 *
	 * @param dict 对象
	 * @return 字典中文名称
	 */
	String getNameFromObject(Dictionary dict);

}
