package io.jutil.springeasy.core.io.filter;

import io.jutil.springeasy.core.io.FileFilter;
import io.jutil.springeasy.core.io.FileInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024/7/27
 */
class FileFilterUtilTest {

	@Test
	void testFilter1() {
		var list = FileNameFilterTest.create("a", "b", "c");
		var result = FileFilterUtil.filter(list, new AllFileFilter());
		Assertions.assertEquals(list, result);
	}

	@Test
	void testFilter2() {
		var list = FileNameFilterTest.create("a", "b", "c");
		var result = FileFilterUtil.filter(list, new NoFileFilter());
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void testFilter3() {
		var list = FileNameFilterTest.create("a", "b", "c");
		var result = FileFilterUtil.filter(list);
		Assertions.assertEquals(list, result);
	}

	@Test
	void testFilter4() {
		var list = FileNameFilterTest.create("a", "b", "c");
		var result = FileFilterUtil.filter(list, new AllFileFilter(), new NoFileFilter());
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void testFilter5() {
		var list = FileNameFilterTest.create("a", "b", "c");
		var result = FileFilterUtil.filter(list, new NoFileFilter(), new AllFileFilter());
		Assertions.assertTrue(result.isEmpty());
	}

	class AllFileFilter implements FileFilter {

		@Override
		public Collection<FileInfo> filter(Collection<FileInfo> fileInfoList) {
			return fileInfoList;
		}
	}

	class NoFileFilter implements FileFilter {

		@Override
		public Collection<FileInfo> filter(Collection<FileInfo> fileInfoList) {
			return List.of();
		}
	}
}
