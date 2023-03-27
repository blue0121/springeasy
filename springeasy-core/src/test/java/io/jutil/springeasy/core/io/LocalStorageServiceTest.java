package io.jutil.springeasy.core.io;

import io.jutil.springeasy.internal.core.io.LocalStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class LocalStorageServiceTest {
	private final StorageService storageService;

	private final String dir;
	private final String filename;
	private final String rootPath = "/tmp";
	private final String source = "/log4j2.xml";

	LocalStorageServiceTest() {
		storageService = new LocalStorageService(rootPath);

		dir = "/" + UUID.randomUUID();
		filename = "/" + UUID.randomUUID();
	}

	@Test
	void testWriteAndRemove() throws IOException {
		var is = LocalStorageServiceTest.class.getResourceAsStream(source);
		Assertions.assertNotNull(is);
		String path = dir + filename;
		var metadata = storageService.write(path, is);
		Assertions.assertTrue(metadata.getSize() > 0);
		Assertions.assertEquals(filename, "/" + metadata.getFilename());

		Path savePath = Paths.get(rootPath, path);
		Assertions.assertTrue(storageService.exists(path));

		byte[] src = null;
		try (var iis = LocalStorageServiceTest.class.getResourceAsStream(source)) {
			src = iis.readAllBytes();
		}
		byte[] dest = Files.readAllBytes(savePath);
		Assertions.assertArrayEquals(src, dest);

		storageService.remove(dir);
		Assertions.assertFalse(storageService.exists(dir));
		Assertions.assertFalse(Files.exists(savePath));
	}

	@Test
	void testCopyAndMove() throws IOException {
		var is = LocalStorageServiceTest.class.getResourceAsStream(source);
		storageService.write(filename, is);
		Assertions.assertTrue(storageService.exists(filename));

		String path = dir + filename;
		storageService.move(filename, path);

		Assertions.assertFalse(storageService.exists(filename));
		Assertions.assertTrue(storageService.exists(path));

		storageService.copy(path, filename);
		Assertions.assertTrue(storageService.exists(filename));

		byte[] src = null;
		try (var iis = LocalStorageServiceTest.class.getResourceAsStream(source)) {
			src = iis.readAllBytes();
		}
		byte[] dest = null;
		try (var iis = storageService.read(filename)) {
			dest = iis.readAllBytes();
		}
		Assertions.assertArrayEquals(src, dest);

		try (var iis = storageService.read(path)) {
			dest = iis.readAllBytes();
		}
		Assertions.assertArrayEquals(src, dest);

		storageService.remove(dir);
		storageService.remove(filename);
		Assertions.assertFalse(storageService.exists(dir));
		Assertions.assertFalse(storageService.exists(path));
		Assertions.assertFalse(storageService.exists(filename));
	}

	@Test
	void testWriteString() throws Exception {
		String str = "test_content";
		var meta = storageService.writeString(filename, str);
		Assertions.assertNotNull(meta);
		Assertions.assertEquals(str.getBytes(StandardCharsets.UTF_8).length, meta.getSize());
		Assertions.assertEquals(filename, "/" + meta.getFilename());

		String txt = storageService.readString(filename);
		Assertions.assertEquals(str, txt);

		storageService.remove(filename);
	}

	@Test
	void testRemoveDirectory() throws IOException {
		var is = LocalStorageServiceTest.class.getResourceAsStream(source);
		String path = dir + filename;
		storageService.write(path, is);
		Assertions.assertTrue(storageService.exists(path));
		Assertions.assertTrue(storageService.exists(dir));

		storageService.remove(dir);
		Assertions.assertFalse(storageService.exists(path));
		Assertions.assertFalse(storageService.exists(dir));
	}
}
