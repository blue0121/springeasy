package io.jutil.springeasy.internal.core.io.scan;

import io.jutil.springeasy.core.io.scan.ResourceInfo;
import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Jin Zheng
 * @since 2023-06-12
 */
@Slf4j
public class JarResourceInfo implements ResourceInfo {
	private final ClassLoader loader;
	private final String dir;
	private final String path;
	private final JarEntry entry;
	private final JarFile jarFile;

	public JarResourceInfo(ClassLoader loader, String dir, String path,
	                       JarEntry entry, JarFile jarFile) {
		this.loader = loader;
		this.dir = dir;
		this.path = path;
		this.entry = entry;
		this.jarFile = jarFile;
	}

	@Override
	public String getDir() {
		return dir;
	}

	@Override
	public String getFullPath() {
		var name = entry.getName();
		var file = ResourceUtil.extractJarFile(path);
		return file + "!" + name;
	}

	@Override
	public String getFileName() {
		return FileUtil.getFilenameWithExt(entry.getName());
	}

	@Override
	public long getSize() {
		return entry.getSize();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return jarFile.getInputStream(entry);
	}

	@Override
	public Class<?> resolveClass() {
		var pkg = ResourceUtil.extractPackage(dir, entry.getName());
		return ResourceUtil.loadClass(loader, pkg);
	}
}
