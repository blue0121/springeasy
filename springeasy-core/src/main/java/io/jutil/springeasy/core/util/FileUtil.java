package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-02-07
 */
@Slf4j
public class FileUtil {
	private static final String STR_DOT = ".";
	private static final String STR_SLASH = "/";
	private static final String KEY_CLASSPATH = "classpath:";

	private FileUtil() {
	}

	/**
	 * 从类所在模块路径文件读取文本
	 *
	 * @param path     classpath: 开头表示从类路径读取，否则从文件系统读取
	 * @return 读取的文本
	 */
	public static String readString(String path) {
		AssertUtil.notEmpty(path, "Path");

		if (isClassPath(path)) {
			var classpath = extractClassPath(path);
			try (var is = FileUtil.class.getResourceAsStream(classpath)) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		try (var is = new FileInputStream(path)) {
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Path buildPath(boolean isCreateDir, String...paths) throws IOException {
		var targetPath = Paths.get(concat(paths));
		var dir = targetPath.getParent();
		if (isCreateDir && !Files.exists(dir)) {
			Files.createDirectories(dir);
			log.info("Create Directory: {}", dir);
		}
		return targetPath;
	}

	public static boolean isClassPath(String path) {
		return path.startsWith(KEY_CLASSPATH);
	}

	public static String extractClassPath(String path) {
		return path.substring(KEY_CLASSPATH.length());
	}

	public static String getRealPath(String classpath) {
		var url = FileUtil.class.getResource(classpath);
		if (url == null) {
			return null;
		}
		var path = url.getPath();
		if (path.startsWith(STR_SLASH) && path.contains(":")) {
			return path.substring(1);
		}
		return path;
	}

	public static String getFilenameWithExt(String path) {
		if (path == null) {
			return null;
		}

		int slashPos = path.lastIndexOf(STR_SLASH);
		if (slashPos == -1) {
			return path;
		} else {
			return path.substring(slashPos + 1);
		}
	}

	public static String getFilenameWithoutExt(String path) {
		if (path == null) {
			return null;
		}
		int slashPos = path.lastIndexOf(STR_SLASH);
		int dotPos = path.lastIndexOf(STR_DOT);
		if (slashPos == -1) {
			if (dotPos == -1) {
				return path;
			} else {
				return path.substring(0, dotPos);
			}
		} else {
			if (dotPos == -1) {
				return path.substring(slashPos + 1);
			} else {
				return path.substring(slashPos + 1, dotPos);
			}
		}
	}

	public static String getRelativeDir(String path, String prefix) {
		List<String> pathList = new ArrayList<>();
		splitPath(pathList, path);

		List<String> prefixList = new ArrayList<>();
		splitPath(prefixList, prefix);

		if (prefixList.size() >= pathList.size()) {
			return STR_SLASH;
		}

		int index = 0;
		while (index < prefixList.size()) {
			if (!pathList.get(index).equals(prefixList.get(index))) {
				break;
			}
			index++;
		}
		if (index == pathList.size() - 1) {
			return STR_SLASH;
		}
		var sb = new StringBuilder();
		for (int i = index; i < pathList.size() - 1; i++) {
			sb.append(STR_SLASH).append(pathList.get(i));
		}
		return sb.toString();
	}

	private static void splitPath(List<String> pathList, String path) {
		if (path == null || path.isEmpty()) {
			return;
		}

		var splits = path.split(STR_SLASH);
		for (var split : splits) {
			if (split.isEmpty() || split.equals(STR_SLASH)) {
				continue;
			}
			pathList.add(split);
		}
	}

	public static String concat(String...paths) {
		if (paths == null || paths.length == 0) {
			return STR_SLASH;
		}
		List<String> list = new ArrayList<>();
		for (var path : paths) {
			splitPath(list, path);
		}
		var sb = new StringBuilder();
		for (var str : list) {
			sb.append(STR_SLASH).append(str);
		}
		return sb.toString();
	}

}
