package io.jutil.springeasy.core.io.scan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
		var info = infoList.get(0);
		Assertions.assertEquals("string.json", info.getFileName());
		Assertions.assertEquals("""
				{
				  "name": "blue"
				}""", info.readString());

		var nameList = handler.nameList;
		Assertions.assertEquals(1, nameList.size());
		Assertions.assertEquals("string.json", nameList.get(0));
	}

	@Test
	void testScanPackage() {
		var handler = new FileHandler();
		ResourceScannerFacade.scanPackage("io.jutil.springeasy.core.io.scan", handler);

		Assertions.assertTrue(handler.nameList.size() > 1);
		Assertions.assertTrue(handler.classList.size() > 1);
		Assertions.assertTrue(handler.nameList.contains("FileSystemResourceScannerTest.class"));
		Assertions.assertTrue(handler.classList.contains(FileSystemResourceScannerTest.class));
	}
}
