package io.jutil.springeasy.core.util;

import java.security.SecureRandom;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class ByteUtil {
	private static final SecureRandom RANDOM = new SecureRandom();

	private ByteUtil() {
	}

	public static byte[] random(int length) {
		AssertUtil.positive(length, "Length");
		byte[] key = new byte[length];
		RANDOM.nextBytes(key);
		return key;
	}

	public static String toHexString(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			String hex = Integer.toHexString(b & 0xff);
			if (hex.length() < 2) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	public static byte[] concat(byte[]...buffers) {
		if (buffers == null || buffers.length == 0) {
			return new byte[0];
		}

		int size = 0;
		for (var buffer : buffers) {
			if (buffer != null && buffer.length > 0) {
				size += buffer.length;
			}
		}

		if (size == 0) {
			return new byte[0];
		}

		byte[] data = new byte[size];
		int index = 0;
		for (var buffer : buffers) {
			if (buffer != null && buffer.length > 0) {
				System.arraycopy(buffer, 0, data, index, buffer.length);
				index += buffer.length;
			}
		}
		return data;
	}
}