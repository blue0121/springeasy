package io.jutil.springeasy.core.http;

import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 1.0 2020-04-30
 */
class MultiPartBodyPublisher {
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String STR_SPLIT = "---------------------------";
	private static final String STR_BOUNDARY = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final List<MultiPartItem> itemList = new ArrayList<>();
	private final Charset charset;
	private final String boundary;

	MultiPartBodyPublisher() {
		this(StandardCharsets.UTF_8, null);
	}

	MultiPartBodyPublisher(String boundary) {
		this(StandardCharsets.UTF_8, boundary);
	}

	MultiPartBodyPublisher(Charset charset) {
		this(charset, null);
	}

	MultiPartBodyPublisher(Charset charset, String boundary) {
		this.charset = charset;
		if (boundary == null || boundary.isEmpty()) {
			this.boundary = this.generateBoundary();
		}
		else {
			this.boundary = boundary;
		}
	}

	public HttpRequest.BodyPublisher build() {
		if (itemList.isEmpty()) {
			throw new IllegalArgumentException("Empty MultiPart");
		}

		this.addFinalBoundary();
		MultiPartIterator iterator = new MultiPartIterator(itemList, boundary);
		return HttpRequest.BodyPublishers.ofByteArrays(new MultiPartIterable(iterator));
	}

	public MultiPartBodyPublisher addPart(String name, String value) {
		MultiPartItem item = new MultiPartItem(name, value);
		itemList.add(item);
		return this;
	}

	public MultiPartBodyPublisher addPart(String name, Path file) {
		MultiPartItem item = new MultiPartItem(name, file);
		itemList.add(item);
		return this;
	}

	public MultiPartBodyPublisher addPart(String name, Supplier<InputStream> stream, String filename, String contentType) {
		MultiPartItem item = new MultiPartItem(name, stream, filename, contentType);
		itemList.add(item);
		return this;
	}

	private void addFinalBoundary() {
		MultiPartItem item = new MultiPartItem();
		item.setType(MultiPartType.FINAL_BOUNDARY);
		itemList.add(item);
	}

	private String generateBoundary() {
		StringBuilder buffer = new StringBuilder(STR_SPLIT.length() * 2);
		buffer.append(STR_SPLIT);
		int count = 14;

		for (int i = 0; i < count; ++i) {
			int index = RANDOM.nextInt(STR_BOUNDARY.length());
			buffer.append(STR_BOUNDARY.charAt(index));
		}
		return buffer.toString();
	}

	public String getBoundary() {
		return boundary;
	}
}
