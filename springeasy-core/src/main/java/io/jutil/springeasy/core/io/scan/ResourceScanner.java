package io.jutil.springeasy.core.io.scan;

/**
 * @author Jin Zheng
 * @since 2023-06-07
 */
public interface ResourceScanner {

	void scan(String base, String path, ResourceHandler...handlers);

}
