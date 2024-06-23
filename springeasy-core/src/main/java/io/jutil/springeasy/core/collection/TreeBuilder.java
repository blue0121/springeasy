package io.jutil.springeasy.core.collection;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jin Zheng
 * @since 2023-12-19
 */
@Slf4j
public class TreeBuilder {
	private static final Long ROOT_PARENT_ID = 0L;
	private static final String KEY_CHILDREN = "children";

	public static <T extends Item> JSONArray build(List<T> list) {
		var map = list.stream()
				.collect(Collectors.groupingBy(e ->
					e.getParentId() == null ? ROOT_PARENT_ID : e.getParentId()
				));
		return build(map);
	}

	public static <T extends Item> JSONArray build(Map<Long, List<T>> map) {
		var rootList = map.get(ROOT_PARENT_ID);
		var array = new JSONArray();
		if (rootList == null || rootList.isEmpty()) {
			return array;
		}

		for (var item : rootList) {
			var obj = JSON.parseObject(JsonUtil.output(item));
			array.add(obj);
			buildChildren(obj, map, item.getId());
		}
		return array;
	}

	private static <T extends Item> void buildChildren(JSONObject obj,
	                                                   Map<Long, List<T>> map,
	                                                   Long parentId) {
		var list = map.get(parentId);
		var children = new JSONArray();
		if (list == null || list.isEmpty()) {
			return;
		}

		for (var item : list) {
			var child = JSON.parseObject(JsonUtil.output(item));
			children.add(child);
			buildChildren(child, map, item.getId());
		}
		obj.put(KEY_CHILDREN, children);
	}


	public interface Item {
		Long getId();
		Long getParentId();
	}
}
