package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceHandler;

/**
 * @author Jin Zheng
 * @since 2023-06-07
 */
public interface ResourceScanner {

	void scan(String base, String path, ResourceHandler...handlers);

}
