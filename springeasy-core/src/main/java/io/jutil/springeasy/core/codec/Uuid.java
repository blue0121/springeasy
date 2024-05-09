package io.jutil.springeasy.core.codec;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
public class Uuid {
	private static final int RADIX = 16;
	private static final int HEX_TO_BIT = 4;
	private static final int BYTE_MASK = 0xff;
	private static final int BYTES_LEN = 16;
	private static final int STRING_LEN = 36;

	public static String encode(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		if (bytes.length != BYTES_LEN) {
			throw new IllegalArgumentException("无效UUID字节数组");
		}
		var builder = new StringBuilder(STRING_LEN);
		int i = 0;
		for (var b : bytes) {
			switch (i) {
				case 4,6,8,10 -> builder.append('-');
			}
			builder.append(Character.forDigit((b >>> HEX_TO_BIT) & 0xf, RADIX));
			builder.append(Character.forDigit(b & 0xf, RADIX));

			i++;
		}
		return builder.toString();
	}
}
