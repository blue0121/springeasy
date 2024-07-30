package io.jutil.springeasy.core.io.filter;

import io.jutil.springeasy.core.io.FileFilter;
import io.jutil.springeasy.core.io.FileInfo;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2024/7/27
 */
public class FileNameFilter implements FileFilter {
	private Pattern namePattern;
	private String antPattern;
	private AntPathMatcher pathMatcher;

	private FileNameFilter() {
	}

	public static FileNameFilter fromNamePattern(Pattern namePattern) {
		var filter = new FileNameFilter();
		filter.namePattern = namePattern;
		return filter;
	}

	public static FileNameFilter fromAntPattern(String antPattern) {
		var filter = new FileNameFilter();
		filter.antPattern = antPattern;
		filter.pathMatcher = new AntPathMatcher();
		return filter;
	}

	@Override
	public Collection<FileInfo> filter(Collection<FileInfo> fileInfoList) {
		List<FileInfo> list = new ArrayList<>();
		for (FileInfo fileInfo : fileInfoList) {
			if (this.match(fileInfo.getFilename())) {
				list.add(fileInfo);
			}
		}
		return list;
	}

	private boolean match(String name) {
		if (namePattern != null) {
			return namePattern.matcher(name).matches();
		}
		if (pathMatcher != null) {
			return pathMatcher.match(antPattern, name);
		}
		return true;
	}
}
