package io.jutil.springeasy.core.io;

import java.nio.file.Path;

/**
 * @author Jin Zheng
 * @since 2024-12-22
 */
public interface StorageWatcher {

	void watch(Path path, WatchListener listener, Type...types);

	void unwatch(Path path);

	void stopWatch();

	enum Type {
		CREATE,
		MODIFY,
		DELETE
	}

	interface WatchListener {
		void onEvent(WatchEvent event);
	}

	record WatchEvent(FileInfo fileInfo, Type type) {}

}
