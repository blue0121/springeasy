package io.jutil.springeasy.internal.core.io;

import io.jutil.springeasy.core.io.PackException;
import io.jutil.springeasy.core.io.PackService;
import io.jutil.springeasy.core.io.StorageException;
import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Slf4j
public class ZipPackService implements PackService {
	private final String rootPath;

	public ZipPackService(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public void pack(String packPath, String... srcPaths) throws PackException {
		AssertUtil.notEmpty(packPath, "Zip file path");
		AssertUtil.notEmpty(srcPaths, "Source Paths");
		var buf = new byte[IoConst.BUFFER_SIZE];
		var target = this.buildPath(true, rootPath, packPath);

		try (var out = new ZipOutputStream(Files.newOutputStream(target))) {
			for (var srcPath : srcPaths) {
				var src = FileUtil.buildPath(false, rootPath, srcPath);
				if (!Files.exists(src)) {
					throw new PackException(srcPath + " is not exists");
				}
				if (Files.isDirectory(src)) {
					var zipDir = new ZipDirectory(out, src, buf);
					Files.walkFileTree(src, zipDir);
				} else {
					ZipFileUtil.zip(out, src, src, buf);
				}
			}
		} catch (IOException e) {
			throw new PackException(e);
		}
		log.info("Create zip file: {}", packPath);
	}

	private Path buildPath(boolean isCreateDir, String...paths) {
		try {
			return FileUtil.buildPath(isCreateDir, paths);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void unpack(String packPath, String unpackPath) throws PackException {
		AssertUtil.notEmpty(packPath, "Zip file path");
		AssertUtil.notEmpty(unpackPath, "Zip file path");
		var target = this.buildPath(false, rootPath, packPath);
		var unzip = this.buildPath(true, rootPath, unpackPath);
		if (!Files.exists(target)) {
			throw new PackException(packPath + " is not exists");
		}

		try (var zip = new ZipFile(target.toFile())) {
			var entries = zip.entries();
			while (entries.hasMoreElements()) {
				var entry = entries.nextElement();
				var name = entry.getName();
				if (entry.isDirectory()) {
					var dir = FileUtil.buildPath(false, unzip.toString(), name);
					if (!Files.exists(dir)) {
						Files.createDirectories(dir);
					}
				} else {
					var file = FileUtil.buildPath(true, unzip.toString(), name);
					try (var in = zip.getInputStream(entry)) {
						Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}
		} catch (IOException e) {
			throw new PackException(e);
		}
		log.info("Extract zip file: {}", packPath);
	}

}
