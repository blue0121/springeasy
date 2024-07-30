package io.jutil.springeasy.core.io.impl;

import io.jutil.springeasy.core.io.FileInfo;
import io.jutil.springeasy.core.util.FileUtil;
import lombok.Getter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Getter
class LocalFileInfo implements FileInfo {
	private final String fullPath;
	private final String relativePath;
	private final String filename;
	private final long size;
	private final String rootPath;

	public LocalFileInfo(Path path, String rootPath) {
		this.rootPath = rootPath;
		this.fullPath = path.toString();
		this.relativePath = this.calcRelativePath();
		this.filename = path.getFileName().toString();
		try {
			this.size = Files.size(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String calcRelativePath() {
		return FileUtil.getRelativePath(fullPath, rootPath);
	}

}
