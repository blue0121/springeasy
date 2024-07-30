package io.jutil.springeasy.core.io.impl;

import io.jutil.springeasy.core.io.FileInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-07-16
 */
@Slf4j
@Getter
class FilesInDirectory implements FileVisitor<Path> {
	private final String rootPath;
	private final List<FileInfo> fileList = new ArrayList<>();

	public FilesInDirectory(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		var fileInfo = new LocalFileInfo(file, rootPath);
		fileList.add(fileInfo);
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
