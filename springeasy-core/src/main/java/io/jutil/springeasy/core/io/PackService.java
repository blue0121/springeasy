package io.jutil.springeasy.core.io;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface PackService {
	void pack(String packPath, String...srcPaths) throws PackException;

	void unpack(String packPath, String unpackPath) throws PackException;
}
