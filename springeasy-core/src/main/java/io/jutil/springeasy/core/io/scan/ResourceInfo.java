package io.jutil.springeasy.core.io.scan;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public interface ResourceInfo {

	String getDir();

	String getFullPath();

	String getFileName();

	String getFileExt();

	long getSize();

	Class<?> resolveClass();
}
