package io.jutil.springeasy.core.io.impl;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipOutputStream;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class ZipDirectory implements FileVisitor<Path> {
	private final ZipOutputStream out;
	private final Path srcPath;
	private final byte[] buf;

	public ZipDirectory(ZipOutputStream out, Path srcPath, byte[] buf) {
		this.out = out;
		this.srcPath = srcPath;
		this.buf = buf;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		ZipFileUtil.zip(out, srcPath, file, buf);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
