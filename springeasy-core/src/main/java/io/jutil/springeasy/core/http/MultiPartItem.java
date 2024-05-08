package io.jutil.springeasy.core.http;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 1.0 2020-04-30
 */
class MultiPartItem {
	static final String BOUNDARY = "--";
	static final int BUFFER_SIZE = 8192;
	static final int HEADER_SIZE = 512;
	static final String NEW_LINE = "\r\n";
	static final String CONTENT_TYPE = "application/octet-stream";

	private MultiPartType type;
	private String name;
	private String value;
	private Path path;
	private Supplier<InputStream> stream;
	private String filename;
	private String contentType;

	MultiPartItem() {
	}

	MultiPartItem(String name, String value) {
		this.type = MultiPartType.STRING;
		this.name = name;
		this.value = value;
	}

	MultiPartItem(String name, Path path) {
		this.type = MultiPartType.FILE;
		this.name = name;
		this.path = path;
	}

	MultiPartItem(String name, Supplier<InputStream> stream, String filename, String contentType) {
		this.type = MultiPartType.STREAM;
		this.name = name;
		this.stream = stream;
		this.filename = filename;
		this.contentType = contentType;
	}

	MultiPartType getType() {
		return type;
	}

	void setType(MultiPartType type) {
		this.type = type;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

	Path getPath() {
		return path;
	}

	void setPath(Path path) {
		this.path = path;
	}

	Supplier<InputStream> getStream() {
		return stream;
	}

	void setStream(Supplier<InputStream> stream) {
		this.stream = stream;
	}

	String getFilename() {
		return filename;
	}

	void setFilename(String filename) {
		this.filename = filename;
	}

	String getContentType() {
		return contentType;
	}

	void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
