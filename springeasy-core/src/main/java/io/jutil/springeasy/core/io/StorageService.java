package io.jutil.springeasy.core.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface StorageService {

	InputStream read(String path) throws StorageException;

	String readString(String path) throws StorageException;

	byte[] readBytes(String path) throws StorageException;

	FileInfo write(String path, InputStream in) throws StorageException;

	OutputStream write(String path) throws StorageException;

	FileInfo writeString(String path, String content) throws StorageException;

	FileInfo writeBytes(String path, byte[] bytes) throws StorageException;

	void copy(String srcPath, String dstPath) throws StorageException;

	void move(String srcPath, String dstPath) throws StorageException;

	void remove(String path) throws StorageException;

	boolean exists(String path) throws StorageException;

	boolean isDirectory(String path) throws StorageException;

	Collection<FileInfo> listFiles(String path, FileFilter ...filters) throws StorageException;

}
