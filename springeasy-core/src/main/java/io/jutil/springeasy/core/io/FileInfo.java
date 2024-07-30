package io.jutil.springeasy.core.io;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface FileInfo {

	/**
	 * /root-path/dir/filename.zip
	 */
	String getFullPath();

	/**
	 * /root-path
	 */
	String getRootPath();

	/**
	 * /dir/filename.zip
	 */
	String getRelativePath();

	/**
	 * filename.zip
	 */
	String getFilename();

	long getSize();
}
