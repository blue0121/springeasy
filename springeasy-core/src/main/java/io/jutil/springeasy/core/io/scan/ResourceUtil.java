package io.jutil.springeasy.core.io.scan;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-06-05
 */
@Slf4j
class ResourceUtil {
	static final char DOT = '.';
	static final char SLASH = '/';
	static final String CLASS_EXT = ".class";
	static final String FILE_PROTOCOL = "file:";

	private ResourceUtil() {
	}

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

	public static String extractJarFile(String path) {
		if (!path.startsWith(FILE_PROTOCOL)) {
			return null;
		}
		var pos = path.indexOf('!');
		if (pos == -1) {
			return path.substring(FILE_PROTOCOL.length());
		}
		return path.substring(FILE_PROTOCOL.length(), pos);
	}

	@SuppressWarnings("java:S135")
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

	public static Class<?> loadClass(ClassLoader loader, String pkg) {
		if (pkg == null) {
			return null;
		}

		try {
			return loader.loadClass(pkg);
		} catch (ClassNotFoundException e) {
			log.warn("Resolve class error, ", e);
			return null;
		}
	}
}
