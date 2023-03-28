package io.jutil.springeasy.internal.core.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Slf4j
class ZipFileUtil {

	private ZipFileUtil() {
	}

	static void zip(ZipOutputStream out, Path srcPath, Path filePath,
	                byte[] buf) throws IOException {
		var entryName = srcPath.relativize(filePath).toString();
		if (entryName.isEmpty()) {
			entryName = srcPath.getFileName().toString();
		}
		int read = -1;
		out.putNextEntry(new ZipEntry(entryName));
		try (var in = Files.newInputStream(filePath)) {
			while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
			}
		} finally {
			out.closeEntry();
		}
		log.debug("Add zip entry: {}", entryName);
	}

}
