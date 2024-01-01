package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.util.AssertUtil;

import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
public class CodecFactory {
	private CodecFactory() {
	}

	public static byte[] encode(ExternalSerializable target) {
		return encode(target, Const.INC_CAP);
	}

	public static byte[] encode(ExternalSerializable target, int capacity) {
		AssertUtil.notNull(target, "Serializable");

		var buffer = new ByteArrayBuffer(capacity);
		var encoder = new ByteArrayEncoder(buffer);
		target.encode(encoder);
		return encoder.getByteArray();
	}

	public static String encodeBase64(ExternalSerializable target) {
		return encodeBase64(target, Const.INC_CAP);
	}

	public static String encodeBase64(ExternalSerializable target, int capacity) {
		var data = encode(target, capacity);
		if (data == null || data.length == 0) {
			return null;
		}
		return Base64.getEncoder().encodeToString(data);
	}

	public static void decode(ExternalSerializable target, byte[] data) {
		AssertUtil.notNull(target, "MessageSerializable");

		var decoder = new ByteArrayDecoder(data);
		target.decode(decoder);
	}

	public static void decode(ExternalSerializable target, String base64) {
		AssertUtil.notEmpty(base64, "Serializable data");
		var data = Base64.getDecoder().decode(base64);
		decode(target, data);
	}
}
