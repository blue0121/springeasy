package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceHandler;
import io.jutil.springeasy.core.io.scan.ResourceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
@Slf4j
class ResourceUtil {

	static void handle(ResourceInfo info, ResourceHandler...handlers) {
		for (var handler : handlers) {
			try {
				if (!handler.accepted(info)) {
					continue;
				}
				var result = handler.handle(info);
				if (result == ResourceHandler.Result.TERMINATE) {
					break;
				}
			} catch (Exception e) {
				log.error("ResourceHandler error, ", e);
			}
		}
	}

}
