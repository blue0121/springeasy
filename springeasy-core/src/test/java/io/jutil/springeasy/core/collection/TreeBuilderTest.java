package io.jutil.springeasy.core.collection;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-12-19
 */
class TreeBuilderTest {

	@Test
	void testBuild1() {
		List<TreeItem> itemList = new ArrayList<>();
		itemList.add(new TreeItem(1L,null,"name1"));
		itemList.add(new TreeItem(2L,null,"name2"));
		itemList.add(new TreeItem(3L,null,"name3"));
		var array = TreeBuilder.build(itemList);
		System.out.println(JsonUtil.output(array));
		Assertions.assertEquals(3, array.size());
		this.verify(array.getJSONObject(0), 1L, null, "name1");
		this.verify(array.getJSONObject(1), 2L, null, "name2");
		this.verify(array.getJSONObject(2), 3L, null, "name3");
	}

	@Test
	void testBuild2() {
		List<TreeItem> itemList = new ArrayList<>();
		itemList.add(new TreeItem(1L,null,"name1"));
		itemList.add(new TreeItem(2L,null,"name2"));

		itemList.add(new TreeItem(11L,1L,"name11"));
		itemList.add(new TreeItem(12L,1L,"name12"));

		itemList.add(new TreeItem(21L,2L,"name21"));
		itemList.add(new TreeItem(22L,2L,"name22"));

		itemList.add(new TreeItem(121L,21L,"name121"));
		itemList.add(new TreeItem(122L,21L,"name122"));

		var array = TreeBuilder.build(itemList);
		System.out.println(JsonUtil.output(array));
		Assertions.assertEquals(2, array.size());

		var child1 = array.getJSONObject(0);
		var child2 = array.getJSONObject(1);
		this.verify(child1, 1L, null, "name1");
		this.verify(child2, 2L, null, "name2");

		var children1 = child1.getJSONArray("children");
		var children2 = child2.getJSONArray("children");
		Assertions.assertEquals(2, children1.size());
		Assertions.assertEquals(2, children2.size());

		this.verify(children1.getJSONObject(0), 11L, 1L, "name11");
		this.verify(children1.getJSONObject(1), 12L, 1L, "name12");

		this.verify(children2.getJSONObject(0), 21L, 2L, "name21");
		this.verify(children2.getJSONObject(1), 22L, 2L, "name22");

		var children21 = children2.getJSONObject(0).getJSONArray("children");
		Assertions.assertEquals(2, children21.size());

		this.verify(children21.getJSONObject(0), 121L, 21L, "name121");
		this.verify(children21.getJSONObject(1), 122L, 21L, "name122");
	}

	private void verify(JSONObject obj, Long id, Long parentId, String name) {
		Assertions.assertNotNull(obj);
		Assertions.assertEquals(id, obj.getLong("id"));
		Assertions.assertEquals(parentId, obj.getLong("parentId"));
		Assertions.assertEquals(name, obj.getString("name"));
	}

	@Getter
	@AllArgsConstructor
	class TreeItem implements TreeBuilder.Item {
		Long id;
		Long parentId;
		String name;

	}
}
