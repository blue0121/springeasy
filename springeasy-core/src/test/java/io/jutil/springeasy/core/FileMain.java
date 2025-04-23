package io.jutil.springeasy.core;


import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Jin Zheng
 * @since 2025-03-23
 */
public class FileMain {
	public static void main(String[] args) throws IOException {
		var dir = "/Users/jin/Downloads/04 猴子警长之风车岛疑云科普百科科学推理宝宝巴士";
		var visitor = new RenameFileVisitor();
		Files.walkFileTree(Paths.get(dir), visitor);
	}

	@Slf4j
	static class RenameFileVisitor implements FileVisitor<Path> {
		private static String subfix = "猴子警长之风车岛疑云-宝宝巴士故事";

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			var dirName = file.getParent().toAbsolutePath().toString();
			var fileName = file.getFileName().toString();
			var extension = FileUtil.getExtension(fileName);
			var fileNameWithoutExt = FileUtil.getFilenameWithoutExt(fileName);
			if (!fileNameWithoutExt.endsWith(subfix)) {
				return FileVisitResult.CONTINUE;
			}

			var subFileName = fileNameWithoutExt.replace(subfix, "");
			var dest = Paths.get(dirName, subFileName + extension);
			log.info("move src: {}, dest: {}", file, dest);
			Files.move(file, dest, StandardCopyOption.ATOMIC_MOVE);
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
}
