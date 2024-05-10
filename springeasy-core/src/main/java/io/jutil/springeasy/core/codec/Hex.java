package io.jutil.springeasy.core.codec;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
public class Hex {
    private static final int RADIX = 16;
    private static final int HEX_TO_BIT = 4;
    private static final int BYTE_MASK = 0xff;

	private Hex() {
	}

    public static String encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        var builder = new StringBuilder(bytes.length << 1);
        for (var b : bytes) {
            encode(builder, b);
        }
        return builder.toString();
    }

    public static void encode(StringBuilder builder, byte b) {
        builder.append(Character.forDigit((b >>> HEX_TO_BIT) & 0xf, RADIX));
        builder.append(Character.forDigit(b & 0xf, RADIX));
    }

    public static byte[] decode(String hex) {
        var len = hex.length();
        if ((len & 1) != 0) {
            throw new IllegalArgumentException("无效16进制字符串");
        }

        var bytes = new byte[len >>> 1];
        for (int i = 0, j = 0; i < len; i += 2, j++) {
            bytes[j] = decode(hex.charAt(i), hex.charAt(i + 1));
        }
        return bytes;
    }

    public static byte decode(char highCh, char lowCh) {
        var high = Character.digit(highCh, RADIX);
        var low = Character.digit(lowCh, RADIX);
        return (byte) (((high << HEX_TO_BIT) | low) & BYTE_MASK);
    }

}
