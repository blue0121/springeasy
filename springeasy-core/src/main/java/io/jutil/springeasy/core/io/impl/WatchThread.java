package io.jutil.springeasy.core.io.impl;

import io.jutil.springeasy.core.io.StorageWatcher;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2024-12-22
 */
@Slf4j
class WatchThread implements Runnable {
	private static final int INTERVAL_SEC = 10;
	private static final String THREAD_NAME = "WatchThread";

	private final WatchService watchService;
	@SuppressWarnings("java:S3077")
	private volatile Map<Path, StorageWatcher.WatchListener> listenerMap;
	private Thread thread;

	public WatchThread(WatchService watchService, Map<Path, StorageWatcher.WatchListener> listenerMap) {
		this.watchService = watchService;
		this.listenerMap = listenerMap;
	}

	void startWatch() {
		if (thread != null) {
			return;
		}
		synchronized (this) {
			if (thread != null) {
				return;
			}
			thread = new Thread(this, THREAD_NAME);
			thread.setDaemon(true);
			thread.start();
			log.info("Start watch thread: {}", THREAD_NAME);
		}
	}

	void stopWatch() {
		if (thread != null) {
			log.info("Stop watch thread.");
			thread.interrupt();
		}
	}

	@Override
	public void run() {
		try {
			while (!listenerMap.isEmpty()) {
				var key = watchService.poll(INTERVAL_SEC, TimeUnit.SECONDS);
				if (key == null) {
					continue;
				}
				var events = key.pollEvents();
				for (var event : events) {
					WatchEvent.Kind<?> kind = event.kind();
					Path eventPath = (Path) event.context();
					log.debug("Event: {} on path {}", kind, eventPath);
				}
				if (!key.reset()) {
					log.warn("WatchKey invalid: {}", key);
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("{} interrupted", THREAD_NAME, e);
		}
	}
}
