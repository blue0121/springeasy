package io.jutil.springeasy.core.codec;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
public class Uuid {
	private static final int BYTES_LEN = 16;
	private static final int STRING_LEN = 36;
	private static final char CH_JOIN = '-';

	private Uuid() {
	}

	public static String encode(byte[] bytes) {
		if (bytes == null || bytes.length != BYTES_LEN) {
			throw new IllegalArgumentException("无效UUID字节数组");
		}
		var builder = new StringBuilder(STRING_LEN);
		for (int i = 0; i < bytes.length; i++) {
			if (i == 4 || i == 6 || i == 8 || i == 10) {
				builder.append(CH_JOIN);
			}
			var b = bytes[i];
			Hex.encode(builder, b);
		}
		return builder.toString();
	}

	@SuppressWarnings("java:S127")
	public static byte[] decode(String uuid) {
		if (uuid == null || uuid.length() != STRING_LEN) {
			throw new IllegalArgumentException("无效UUID字符串");
		}
		var bytes = new byte[BYTES_LEN];
		for (int i = 0, j = 0; i < uuid.length(); i += 2, j++) {
			if (uuid.charAt(i) == CH_JOIN) {
				i++;
			}
			bytes[j] = Hex.decode(uuid.charAt(i), uuid.charAt(i + 1));
		}
		return bytes;
	}
}
