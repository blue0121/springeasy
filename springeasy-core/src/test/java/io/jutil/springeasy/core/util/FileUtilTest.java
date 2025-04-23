package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class FileUtilTest {

	@Test
	void testReadString() {
		String json = FileUtil.readString("classpath:/json/string.json");
		System.out.println(json);
		Assertions.assertNotNull(json);
		Assertions.assertFalse(json.isEmpty());
		JSONObject object = JSON.parseObject(json);
		Assertions.assertNotNull(object);
		Assertions.assertEquals("blue", object.getString("name"));
	}

	@Test
	void testGetFilenameWithExt() {
		Assertions.assertEquals("a", FileUtil.getFilenameWithExt("a"));
		Assertions.assertEquals("a", FileUtil.getFilenameWithExt("/a"));
		Assertions.assertEquals("a.z", FileUtil.getFilenameWithExt("a.z"));
		Assertions.assertEquals("a.z", FileUtil.getFilenameWithExt("/b/a.z"));
		Assertions.assertEquals("abc.zip", FileUtil.getFilenameWithExt("/b/abc.zip"));
		Assertions.assertEquals("efg.zip", FileUtil.getFilenameWithExt("/c/d/z/y/x/efg.zip"));
	}

	@Test
	void testGetExtension() {
		Assertions.assertEquals("a", FileUtil.getExtension("a"));
		Assertions.assertEquals("b", FileUtil.getExtension("a/b"));
		Assertions.assertEquals(".t", FileUtil.getExtension("a.t"));
		Assertions.assertEquals(".t", FileUtil.getExtension("a/b.t"));
	}

	@Test
	void testGetFilenameWithoutExt() {
		Assertions.assertEquals("a", FileUtil.getFilenameWithoutExt("a"));
		Assertions.assertEquals("a", FileUtil.getFilenameWithoutExt("/a"));
		Assertions.assertEquals("a", FileUtil.getFilenameWithoutExt("a.z"));
		Assertions.assertEquals("a", FileUtil.getFilenameWithoutExt("/b/a.z"));
		Assertions.assertEquals("abc", FileUtil.getFilenameWithoutExt("/b/abc.zip"));
		Assertions.assertEquals("efg", FileUtil.getFilenameWithoutExt("/c/d/z/y/x/efg.zip"));
	}

	@Test
	void testGetRelativePath() {
		Assertions.assertEquals("/a", FileUtil.getRelativePath("/a", ""));
		Assertions.assertEquals("/a/b", FileUtil.getRelativePath("/a/b", ""));
		Assertions.assertEquals("/b/c", FileUtil.getRelativePath("/a/b/c", "/a"));
		Assertions.assertEquals("/b/c/d", FileUtil.getRelativePath("/a/b/c/d", "/a"));
		Assertions.assertEquals("/c/d/e", FileUtil.getRelativePath("/a/b/c/d/e", "/a/b"));
		Assertions.assertEquals("/b/c", FileUtil.getRelativePath("/a/b/c", "/a/x"));
	}

	@Test
	void testConcat() {
		Assertions.assertEquals("/", FileUtil.concat());
		Assertions.assertEquals("/a", FileUtil.concat("/", "/a"));
		Assertions.assertEquals("/a", FileUtil.concat("a"));
		Assertions.assertEquals("/a/b", FileUtil.concat("a", "b"));
		Assertions.assertEquals("/a/b", FileUtil.concat("/a/", "/b/"));
		Assertions.assertEquals("/a/b/c/d", FileUtil.concat("a/b", "c", "/d//"));
	}

}
