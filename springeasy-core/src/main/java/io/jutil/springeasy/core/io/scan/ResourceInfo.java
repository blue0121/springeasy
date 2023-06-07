package io.jutil.springeasy.core.io.scan;

import java.io.IOException;
import java.io.InputStream;

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

	Class<?> resolveClass();
}
