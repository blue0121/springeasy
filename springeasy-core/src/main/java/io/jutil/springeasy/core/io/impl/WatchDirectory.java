package io.jutil.springeasy.core.io.impl;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-12-22
 */
@Slf4j
class WatchDirectory implements FileVisitor<Path> {
	private final WatchService watchService;
	private final WatchEvent.Kind<?>[] kinds;
	private final List<WatchKey> watchKeyList;

	public WatchDirectory(WatchService watchService, WatchEvent.Kind<?>[] kinds,
	                      List<WatchKey> watchKeyList) {
		this.watchService = watchService;
		this.kinds = kinds;
		this.watchKeyList = watchKeyList;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		var key = dir.register(watchService, kinds);
		log.info("Watch directory: {}, kinds: {}", dir, Arrays.toString(kinds));
		this.watchKeyList.add(key);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		if (exc != null) {
			throw exc;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (exc != null) {
			throw exc;
		}
		return FileVisitResult.CONTINUE;
	}
}
