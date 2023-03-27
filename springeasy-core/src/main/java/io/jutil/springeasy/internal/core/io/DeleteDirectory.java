package io.jutil.springeasy.internal.core.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Slf4j
public class DeleteDirectory implements FileVisitor<Path> {

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		var success = Files.deleteIfExists(file);
		log.info("Remove file [{}] {}", file, success);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (exc != null) {
			throw exc;
		}
		var success = Files.deleteIfExists(dir);
		log.info("Remove directory [{}] {}", dir, success);
		return FileVisitResult.CONTINUE;
	}

}
