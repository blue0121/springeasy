package io.jutil.springeasy.internal.core.dict;

import com.alibaba.fastjson2.JSON;
import io.jutil.springeasy.core.dict.Dictionary;
import io.jutil.springeasy.core.dict.DictionaryCache;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@Slf4j
public class DefaultDictionaryCache implements DictionaryCache {
    private static DefaultDictionaryCache instance = new DefaultDictionaryCache();

    private Map<Class<? extends Dictionary>, Map<Integer, Dictionary>> indexMap = new HashMap<>();
    private Map<Class<? extends Dictionary>, Map<String, Dictionary>> nameMap = new HashMap<>();

	private DefaultDictionaryCache() {
	}

    public static DefaultDictionaryCache getInstance() {
        return instance;
    }

    public void add(Dictionary dict) {
        var clazz = dict.getClass();

        var index = indexMap.computeIfAbsent(clazz, k -> new HashMap<>());
        if (index.containsKey(dict.getIndex())) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " 重复 index 值: " + dict.getIndex());
        }
        index.put(dict.getIndex(), dict);

        var name = nameMap.computeIfAbsent(clazz, k -> new HashMap<>());
        if (name.containsKey(dict.getName())) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " 重复 name 值: " + dict.getName());
        }
        name.put(dict.getName(), dict);

        JSON.registerIfAbsent(clazz, new DictionaryReader<>(clazz));
        log.debug("Found Dictionary[{}], index: {}, name: {}", clazz.getSimpleName(), dict.getIndex(), dict.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dictionary> T getFromIndex(Class<T> clazz, Integer index) {
        var map = indexMap.get(clazz);
        if (map == null) {
            return null;
        }
        return (T) map.get(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dictionary> T getFromName(Class<T> clazz, String name) {
        var map = nameMap.get(clazz);
        if (map == null) {
            return null;
        }
        return (T) map.get(name);
    }

    @Override
    public int getIndexFromObject(Dictionary dict) {
        return dict.getIndex();
    }

    @Override
    public String getNameFromObject(Dictionary dict) {
        return dict.getName();
    }
}
