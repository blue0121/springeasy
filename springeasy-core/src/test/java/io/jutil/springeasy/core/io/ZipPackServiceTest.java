package io.jutil.springeasy.core.io;

import io.jutil.springeasy.core.io.impl.LocalStorageService;
import io.jutil.springeasy.core.io.impl.ZipPackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class ZipPackServiceTest {
	private StorageService storageService;
	private PackService packService;

	private final String root = "/tmp/local_path/zip";
	private final String txt1 = "hello 1.txt";
	private final String txt2 = "hello 2/2.txt";
	private final String txt3 = "hello 2/3/3.txt";

	ZipPackServiceTest() {
		storageService = new LocalStorageService(root);
		packService = new ZipPackService(root);
	}

	@BeforeEach
	void beforeEach() {
		storageService.remove("");
	}

	@Test
	void testFile() {
		storageService.writeString("/src/1.txt", txt1);
		packService.pack("1.zip", "/src/1.txt");

		packService.unpack("1.zip", "/unzip/1");

		Assertions.assertTrue(storageService.exists("/unzip/1/1.txt"));
		Assertions.assertEquals(txt1, storageService.readString("/unzip/1/1.txt"));
	}

	@Test
	void testFile2() {
		storageService.writeString("/src/2/2.txt", txt2);
		packService.pack("2.zip", "/src/2");

		packService.unpack("2.zip", "/unzip/2");

		Assertions.assertTrue(storageService.exists("/unzip/2/2.txt"));
		Assertions.assertEquals(txt2, storageService.readString("/unzip/2/2.txt"));
	}

	@Test
	void testDir() {
		storageService.writeString("/src/1.txt", txt1);
		storageService.writeString("/src/2/2.txt", txt2);
		storageService.writeString("/src/2/3/3.txt", txt3);

		packService.pack("3.zip", "/src/1.txt", "/src/2");

		packService.unpack("3.zip", "/unzip/3");

		Assertions.assertTrue(storageService.exists("/unzip/3/1.txt"));
		Assertions.assertTrue(storageService.exists("/unzip/3/2.txt"));
		Assertions.assertTrue(storageService.exists("/unzip/3/3/3.txt"));
		Assertions.assertEquals(txt1, storageService.readString("/unzip/3/1.txt"));
		Assertions.assertEquals(txt2, storageService.readString("/unzip/3/2.txt"));
		Assertions.assertEquals(txt3, storageService.readString("/unzip/3/3/3.txt"));
	}
}
