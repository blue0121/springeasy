package io.jutil.springeasy.core.io.scan;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public interface ResourceInfo {

	String getDir();

	String getFullPath();

	String getFileName();

	long getSize();

	InputStream getInputStream() throws IOException;

	default byte[] readBytes() {
		try (var is = this.getInputStream()) {
			return is.readAllBytes();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	default String readString() {
		var bytes = this.readBytes();
		return new String(bytes, StandardCharsets.UTF_8);
	}

	Class<?> resolveClass();
}
