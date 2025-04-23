package io.jutil.springeasy.core.io.impl;

import io.jutil.springeasy.core.io.StorageWatcher;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin Zheng
 * @since 2024-12-22
 */
@Slf4j
public class LocalStorageWatcher implements StorageWatcher {
	private final Map<Path, WatchListener> listenerMap = new ConcurrentHashMap<>();
	private final Map<Path, List<WatchKey>> watchKeyMap = new ConcurrentHashMap<>();
	private final WatchService watchService;
	private final WatchThread watchThread;

	public LocalStorageWatcher() {
		try {
			this.watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		this.watchThread = new WatchThread(watchService, listenerMap);
	}

	@Override
	public void watch(Path path, WatchListener listener, Type... types) {
		var kinds = this.check(path, listener, types);
		List<WatchKey> watchKeyList = new ArrayList<>();
		try {
			var watchDirectory = new WatchDirectory(watchService, kinds, watchKeyList);
			Files.walkFileTree(path, watchDirectory);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		listenerMap.put(path, listener);
		watchKeyMap.put(path, watchKeyList);
		watchThread.startWatch();
	}

	@Override
	public void unwatch(Path path) {
		var keyList = watchKeyMap.remove(path);
		if (keyList != null && !keyList.isEmpty()) {
			for (var key : keyList) {
				key.cancel();
			}
		}
		listenerMap.remove(path);
	}

	@Override
	public void stopWatch() {
		watchThread.stopWatch();
	}

	private java.nio.file.WatchEvent.Kind<?>[] check(Path path, WatchListener listener, Type... types) {
		AssertUtil.notNull(path, "path");
		AssertUtil.notNull(listener, "listener");
		AssertUtil.notEmpty(types, "types");
		if (!Files.exists(path)) {
			throw new IllegalArgumentException(path + " 不存在");
		}
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException(path + " 不是目录");
		}

		var kinds = new java.nio.file.WatchEvent.Kind<?>[types.length];
		int i = 0;
		for (var type : types) {
			switch (type) {
				case CREATE -> kinds[i] = StandardWatchEventKinds.ENTRY_CREATE;
				case MODIFY -> kinds[i] = StandardWatchEventKinds.ENTRY_MODIFY;
				case DELETE -> kinds[i] = StandardWatchEventKinds.ENTRY_DELETE;
			}
			i++;
		}
		return kinds;
	}
}
