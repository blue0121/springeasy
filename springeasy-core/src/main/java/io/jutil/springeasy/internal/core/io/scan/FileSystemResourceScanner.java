package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
@Slf4j
public class FileSystemResourceScanner implements ResourceScanner {
	public static final FileSystemResourceScanner INSTANCE = new FileSystemResourceScanner();

	@Override
	public void scan(String base, String path, ResourceHandler... handlers) {
		var loader = Thread.currentThread().getContextClassLoader();
		var visitor = new ScanFileVisitor(loader, base, handlers);
		var p = Paths.get(path);
		if (!Files.isDirectory(p)) {
			log.warn("[{}] is not directory", path);
			return;
		}

		try {
			Files.walkFileTree(p, visitor);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private class ScanFileVisitor extends SimpleFileVisitor<Path> {
		private final ClassLoader loader;
		private final String dir;
		private final ResourceHandler[] handlers;

		public ScanFileVisitor(ClassLoader loader, String dir, ResourceHandler[] handlers) {
			this.loader = loader;
			this.dir = dir;
			this.handlers = handlers;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			var info = new FileSystemResourceInfo(loader, dir, file);
			ResourceUtil.handle(info, handlers);

			return FileVisitResult.CONTINUE;
		}
	}
}
