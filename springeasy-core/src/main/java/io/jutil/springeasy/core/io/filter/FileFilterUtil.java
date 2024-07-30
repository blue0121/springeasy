package io.jutil.springeasy.core.io.filter;

import io.jutil.springeasy.core.io.FileFilter;
import io.jutil.springeasy.core.io.FileInfo;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2024/7/26
 */
public class FileFilterUtil {
	private FileFilterUtil() {
	}

	public static Collection<FileInfo> filter(Collection<FileInfo> fileInfoList,
	                                          FileFilter...filters) {
		if (filters == null) {
			return fileInfoList;
		}
		for (var filter : filters) {
			if (fileInfoList == null || fileInfoList.isEmpty()) {
				return fileInfoList;
			}
			fileInfoList = filter.filter(fileInfoList);
		}
		return fileInfoList;
	}

}
