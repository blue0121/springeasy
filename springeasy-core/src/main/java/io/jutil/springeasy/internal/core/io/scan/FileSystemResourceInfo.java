package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
@Slf4j
public class FileSystemResourceInfo implements ResourceInfo {
	private final ClassLoader loader;
	private final String dir;
	private final Path path;
	private final long size;

	public FileSystemResourceInfo(ClassLoader loader, String dir, Path path) {
		this.loader = loader;
		this.dir = dir;
		this.path = path;
		try {
			this.size = Files.size(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public String getDir() {
		return dir;
	}

	@Override
	public String getFullPath() {
		return path.toString();
	}

	@Override
	public String getFileName() {
		return path.getFileName().toString();
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return Files.newInputStream(path);
	}

	@Override
	public Class<?> resolveClass() {
		var pkg = ResourceUtil.extractPackage(dir, path.toString());
		if (pkg == null) {
			return null;
		}

		try {
			return loader.loadClass(pkg);
		} catch (ClassNotFoundException e) {
			log.warn("Resolve class error, ", e);
			return null;
		}
	}
}
