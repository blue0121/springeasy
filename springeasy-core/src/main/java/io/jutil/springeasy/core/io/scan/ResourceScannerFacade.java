package io.jutil.springeasy.core.io.scan;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
@Slf4j
public class ResourceScannerFacade {

	private ResourceScannerFacade() {
	}

	public static void scanPackage(String base, ResourceHandler...handlers) {
		AssertUtil.notEmpty(base, "Base");
		if (handlers.length == 0) {
			log.warn("ResourceHandler is empty");
			return;
		}

		var path = ResourceUtil.pkgToClasspath(base);
		scanPath(path, handlers);
	}

	public static void scanPath(String base, ResourceHandler...handlers) {
		AssertUtil.notEmpty(base, "Base");
		if (handlers.length == 0) {
			log.warn("ResourceHandler is empty");
			return;
		}

		if (!FileUtil.isClassPath(base)) {
			handleFs(base, base, handlers);
			return;
		}
		var path = FileUtil.extractClassPath(base);
		var loader = Thread.currentThread().getContextClassLoader();
		try {
			var urls = loader.getResources(path);
			while (urls.hasMoreElements()) {
				var url = urls.nextElement();
				handleUrl(path, url, handlers);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static void handleUrl(String base, URL url, ResourceHandler...handlers) {
		var protocol = url.getProtocol();
		switch (protocol) {
			case "file" -> handleFs(base, url.getPath(), handlers);
			case "jar" -> handleJar(base, url.getPath(), handlers);
			default -> throw new UnsupportedOperationException(
					"Unknown protocol: " + protocol);
		}
	}

	private static void handleFs(String base, String path, ResourceHandler...handlers) {
		path = FileUtil.getRealPath(path);
		log.debug("Scan file system, dir: {}, path: {}", base, path);
		FileSystemResourceScanner.INSTANCE.scan(base, path, handlers);
	}

	private static void handleJar(String base, String path, ResourceHandler...handlers) {
		log.debug("Scan jar file, dir: {}, path: {}", base, path);
		JarResourceScanner.INSTANCE.scan(base, path, handlers);
	}

}
