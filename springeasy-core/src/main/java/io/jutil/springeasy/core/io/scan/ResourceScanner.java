package io.jutil.springeasy.core.io.scan;

/**
 * @author Jin Zheng
 * @since 2023-06-07
 */
interface ResourceScanner {

	void scan(String base, String path, ResourceHandler...handlers);

}
