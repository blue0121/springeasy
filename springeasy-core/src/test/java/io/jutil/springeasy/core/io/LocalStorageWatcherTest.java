package io.jutil.springeasy.core.io;

import io.jutil.springeasy.core.io.impl.LocalStorageService;
import io.jutil.springeasy.core.io.impl.LocalStorageWatcher;
import io.jutil.springeasy.core.util.WaitUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Jin Zheng
 * @since 2024-12-22
 */
class LocalStorageWatcherTest {
	private static LocalStorageWatcher watcher;
	private static StorageService storageService;

	private static Path watchPath = Paths.get("/tmp/watch");

	@BeforeAll
	public static void beforeAll() throws IOException {
		watcher = new LocalStorageWatcher();
		storageService = new LocalStorageService(watchPath.toString());
		if (!Files.exists(watchPath)) {
			Files.createDirectories(watchPath);
		}
	}

	@AfterAll
	public static void afterAll() {
		watcher.stopWatch();
	}

	@Test
	void testWatch() {
		var listener = new TestListener();
		watcher.watch(watchPath, listener, StorageWatcher.Type.CREATE, StorageWatcher.Type.MODIFY);
		storageService.writeString("/test.txt", "test");

		WaitUtil.sleep(10000);

		watcher.unwatch(watchPath);
	}

	static class TestListener implements StorageWatcher.WatchListener {
		StorageWatcher.WatchEvent event;

		@Override
		public void onEvent(StorageWatcher.WatchEvent event) {
			this.event = event;
		}
	}
}
