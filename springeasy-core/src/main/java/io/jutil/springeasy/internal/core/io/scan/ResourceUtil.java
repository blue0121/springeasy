package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceHandler;
import io.jutil.springeasy.core.io.scan.ResourceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
@Slf4j
public class ResourceUtil {

	public static String dotToSlash(String str) {
		return str.replace('.', '/');
	}

	public static String pkgToClasspath(String str) {
		return "classpath:" + dotToSlash(str);
	}

	public static String slashToDot(String str) {
		return str.replace('/', '.');
	}

	public static void handle(ResourceInfo info, ResourceHandler...handlers) {
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
