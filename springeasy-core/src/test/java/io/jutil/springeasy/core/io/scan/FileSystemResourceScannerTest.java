package io.jutil.springeasy.core.io.scan;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
class FileSystemResourceScannerTest {

	@Test
	void testScanPath() {
		var handler = new FileHandler();
		ResourceScannerFacade.scanPath("classpath:json", handler);

		var infoList = handler.infoList;
		Assertions.assertEquals(1, infoList.size());
		Assertions.assertEquals("string.json", infoList.get(0).getFileName());

		var nameList = handler.nameList;
		Assertions.assertEquals(1, nameList.size());
		Assertions.assertEquals("string.json", nameList.get(0));
	}

	@Test
	void testScanPackage() {
		var handler = new FileHandler();
		ResourceScannerFacade.scanPackage("io.jutil.springeasy.core.io.scan", handler);

		var nameList = handler.nameList;
		Assertions.assertTrue(nameList.size() > 1);
		Assertions.assertTrue(nameList.contains("FileSystemResourceScannerTest.class"));
	}

	@Getter
	private class FileHandler implements ResourceHandler {
		private final List<ResourceInfo> infoList = new ArrayList<>();
		private final List<String> nameList = new ArrayList<>();

		@Override
		public boolean accepted(ResourceInfo info) {
			return true;
		}

		@Override
		public Result handle(ResourceInfo info) {
			System.out.println(info.getFileName());
			infoList.add(info);
			nameList.add(info.getFileName());
			return Result.CONTINUE;
		}
	}
}
