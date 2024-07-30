package io.jutil.springeasy.core.io.filter;

import io.jutil.springeasy.core.io.FileInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2024/7/27
 */
class FileNameFilterTest {

	@Test
	void testNamePattern() {
		var filter = FileNameFilter.fromNamePattern(Pattern.compile("^\\d+$"));
		var list = create("123", "456");
		var result = filter.filter(list);
		Assertions.assertEquals(list, result);
	}

	@Test
	void testNamePattern1() {
		var filter = FileNameFilter.fromNamePattern(Pattern.compile("^\\d+$"));
		var list = create("abc", "456");
		var result = filter.filter(list);
		Assertions.assertEquals(1, result.size());
	}

	@Test
	void testAntPattern() {
		var filter = FileNameFilter.fromAntPattern("a*1");
		var list = create("ab1", "abc1", "a1");
		var result = filter.filter(list);
		Assertions.assertEquals(list, result);
	}

	@Test
	void testAntPattern1() {
		var filter = FileNameFilter.fromAntPattern("a*1");
		var list = create("ab1", "456");
		var result = filter.filter(list);
		Assertions.assertEquals(1, result.size());
	}

	public static List<FileInfo> create(String ...names) {
		List<FileInfo> list = new ArrayList<>();
		for (var name : names) {
			var fileInfo = Mockito.mock(FileInfo.class);
			Mockito.when(fileInfo.getFilename()).thenReturn(name);
			list.add(fileInfo);
		}
		return list;
	}

}
