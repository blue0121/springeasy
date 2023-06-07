package io.jutil.springeasy.core.io.scan;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public interface ResourceHandler {

	boolean accepted(ResourceInfo info);

	Result handle(ResourceInfo info);

	enum Result {
		CONTINUE,
		TERMINATE,
	}
}
