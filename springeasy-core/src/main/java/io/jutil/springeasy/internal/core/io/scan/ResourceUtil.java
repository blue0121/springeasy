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
	static final char DOT = '.';
	static final char SLASH = '/';
	static final String CLASS_EXT = ".class";

	public static String dotToSlash(String str) {
		return str.replace(DOT, SLASH);
	}

	public static String pkgToClasspath(String str) {
		return "classpath:" + dotToSlash(str);
	}

	public static String slashToDot(String str) {
		return str.replace(SLASH, DOT);
	}

	public static boolean isClass(String path) {
		return path.endsWith(CLASS_EXT);
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

	public static String extractPackage(String base, String path) {
		var pos = path.indexOf(base);
		if (pos == -1) {
			return null;
		}

		var subPath = path.substring(pos);
		if (!isClass(subPath)) {
			return null;
		}

		var pkg = slashToDot(subPath);
		return pkg.replace(CLASS_EXT, "");
	}

}
