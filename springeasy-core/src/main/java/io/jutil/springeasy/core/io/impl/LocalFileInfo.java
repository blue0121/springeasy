package io.jutil.springeasy.core.io.impl;

import io.jutil.springeasy.core.io.FileInfo;
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
public class LocalFileInfo implements FileInfo {
	private final String filename;
	private final long size;

	public LocalFileInfo(Path path) {
		this.filename = path.getFileName().toString();
		try {
			this.size = Files.size(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
