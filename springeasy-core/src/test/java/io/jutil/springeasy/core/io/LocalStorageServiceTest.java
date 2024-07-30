package io.jutil.springeasy.core.io;

import io.jutil.springeasy.core.io.impl.LocalStorageService;
import io.jutil.springeasy.core.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
	private final String source = "/logback.xml";

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
	void testWriteString() {
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
	void testRemoveDirectory() {
		var is = LocalStorageServiceTest.class.getResourceAsStream(source);
		String path = dir + filename;
		storageService.write(path, is);
		Assertions.assertTrue(storageService.isDirectory(dir));
		Assertions.assertFalse(storageService.isDirectory(path));
		Assertions.assertTrue(storageService.exists(path));
		Assertions.assertTrue(storageService.exists(dir));

		storageService.remove(dir);
		Assertions.assertFalse(storageService.exists(path));
		Assertions.assertFalse(storageService.exists(dir));
	}

	@Test
	void testListFiles() {
		var text = "test_content";
		String path = dir + filename;
		String subPath = dir + dir + filename;
		storageService.writeString(path, text);
		storageService.writeString(subPath, text);

		var list = storageService.listFiles(dir);
		Assertions.assertEquals(2, list.size());
		System.out.println(JsonUtil.output(list));
		List<String> relativeList = new ArrayList<>();
		for (var fileInfo : list) {
			relativeList.add(fileInfo.getRelativePath());
		}
		Assertions.assertTrue(relativeList.contains(path));
		Assertions.assertTrue(relativeList.contains(subPath));

		storageService.remove(dir);
	}
}
