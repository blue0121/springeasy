package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceHandler;
import io.jutil.springeasy.core.io.scan.ResourceScanner;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public class ResourceScannerFacade implements ResourceScanner {
	@Override
	public void scan(String base, ResourceHandler...handlers) {

	}

	enum Type {
		FileSystem,
		CLASSPATH,
	}
}
