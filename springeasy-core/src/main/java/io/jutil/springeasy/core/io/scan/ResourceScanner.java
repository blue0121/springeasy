package io.jutil.springeasy.core.io.scan;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public interface ResourceScanner {

	void scan(String base, ResourceHandler...handlers);

}
