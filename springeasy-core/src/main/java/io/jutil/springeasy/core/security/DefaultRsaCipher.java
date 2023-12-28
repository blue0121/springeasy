package io.jutil.springeasy.core.security;

import io.jutil.springeasy.core.util.AssertUtil;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class DefaultRsaCipher implements RsaCipher {
	private static final String ALGORITHM = "RSA";

	private Cipher initCipher(Key key, int mode) throws Exception {
		AssertUtil.notNull(key, "Key");
		if (!(key instanceof RSAPrivateKey) && !(key instanceof RSAPublicKey)) {
			throw new SecurityException("Key is not RSAPublicKey/RSAPrivateKey");
		}

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(mode, key);
		return cipher;
	}

	@Override
	public byte[] encrypt(PublicKey key, byte[] data) {
		AssertUtil.notEmpty(data, "Data");
		try {
			var cipher = this.initCipher(key, Cipher.ENCRYPT_MODE);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	@Override
	public String encryptString(PublicKey key, String data) {
		AssertUtil.notEmpty(data, "Data");
		byte[] raw = this.encrypt(key, data.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(raw);
	}

	@Override
	public byte[] decrypt(PrivateKey key, byte[] raw) {
		AssertUtil.notEmpty(raw, "Data");
		try {
			var cipher = this.initCipher(key, Cipher.DECRYPT_MODE);
			return cipher.doFinal(raw);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	@Override
	public String decryptString(PrivateKey key, String rawBase64) {
		AssertUtil.notEmpty(rawBase64, "Raw");
		byte[] raw = Base64.getDecoder().decode(rawBase64);
		byte[] data = this.decrypt(key, raw);
		return new String(data, StandardCharsets.UTF_8);
	}
}
