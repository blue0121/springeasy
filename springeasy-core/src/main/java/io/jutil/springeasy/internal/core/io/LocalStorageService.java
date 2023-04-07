package io.jutil.springeasy.internal.core.io;

import io.jutil.springeasy.core.io.FileInfo;
import io.jutil.springeasy.core.io.StorageException;
import io.jutil.springeasy.core.io.StorageService;
import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Slf4j
public class LocalStorageService implements StorageService {
	private static final DeleteDirectory DELETE_DIR = new DeleteDirectory();
	private final String rootPath;

	public LocalStorageService(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public InputStream read(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(false, rootPath, path);
			return Files.newInputStream(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String readString(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(false, rootPath, path);
			return Files.readString(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public byte[] readBytes(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(false, rootPath, path);
			try (var in = Files.newInputStream(targetPath)) {
				return in.readAllBytes();
			}
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public FileInfo write(String path, InputStream in) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(true, rootPath, path);
			try (in) {
				Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
				return new LocalFileInfo(targetPath);
			}
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public OutputStream write(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(true, rootPath, path);
			return Files.newOutputStream(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public FileInfo writeString(String path, String content) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(true, rootPath, path);
			Files.writeString(targetPath, content);
			return new LocalFileInfo(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public FileInfo writeBytes(String path, byte[] bytes) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(true, rootPath, path);
			Files.write(targetPath, bytes);
			return new LocalFileInfo(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void copy(String srcPath, String dstPath) throws StorageException {
		try {
			var src = FileUtil.buildPath(false, rootPath, srcPath);
			var dst = FileUtil.buildPath(true, rootPath, dstPath);
			Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copy file [{}] to [{}]", src, dst);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void move(String srcPath, String dstPath) throws StorageException {
		try {
			var src = FileUtil.buildPath(false, rootPath, srcPath);
			var dst = FileUtil.buildPath(true, rootPath, dstPath);
			Files.move(src, dst, StandardCopyOption.ATOMIC_MOVE);
			log.info("Move file [{}] to [{}]", src, dst);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void remove(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(false, rootPath, path);
			if (!Files.exists(targetPath)) {
				log.warn("File not found: {}", targetPath);
				return;
			}

			if (Files.isDirectory(targetPath)) {
				Files.walkFileTree(targetPath, DELETE_DIR);
			} else {
				var success = Files.deleteIfExists(targetPath);
				log.info("Remove file [{}] {}", targetPath, success);
			}
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public boolean exists(String path) throws StorageException {
		try {
			var targetPath = FileUtil.buildPath(false, rootPath, path);
			return Files.exists(targetPath);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}
}
