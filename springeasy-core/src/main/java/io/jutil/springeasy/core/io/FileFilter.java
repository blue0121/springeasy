package io.jutil.springeasy.core.io;

import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2024/7/26
 */
public interface FileFilter {

	Collection<FileInfo> filter(Collection<FileInfo> fileInfoList);

}
