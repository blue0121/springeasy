package io.jutil.springeasy.core.security;

import io.jutil.springeasy.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2023-11-29
 */
@Slf4j
public class TokenUtil {
	private static final KeyPairMode KEY_PAIR_MODE = KeyPairMode.ED_25519;
	private static final SignatureMode SIGNATURE_MODE = SignatureMode.ED_25519;
	private static final char SEP = '.';

	private TokenUtil() {
	}

	public static KeyPair generate() {
		return new GenerateKeyPair(KEY_PAIR_MODE);
	}

	public static KeyPair load(String publicKeyPath, String privateKeyPath) {
		var publicInput = FileUtil.readStream(publicKeyPath);
		var privateInput = FileUtil.readStream(privateKeyPath);
		return new LoadOpenSslKeyPair(KeyPairMode.ED_25519, publicInput, privateInput);
	}

	public static String create(PrivateKey key, byte[] data) {
		var encoder = Base64.getUrlEncoder();
		var signature = new DefaultSignature(SIGNATURE_MODE);
		var sign = signature.sign(key, data);
		var dataBase64 = encoder.encodeToString(data);
		var signBase64 = encoder.encodeToString(sign);
		var token = new StringBuilder(dataBase64.length() + signBase64.length() + 1);
		token.append(dataBase64).append(SEP).append(signBase64);
		return token.toString();
	}

	public static byte[] verify(PublicKey key, String token) {
		int pos = token.indexOf(SEP);
		if (pos == -1) {
			throw new TokenException("令牌无效");
		}
		var decoder = Base64.getUrlDecoder();
		var dataBase64 = token.substring(0, pos);
		var signBase64 = token.substring(pos + 1);
		byte[] data = null;
		byte[] sign = null;
		try {
			data = decoder.decode(dataBase64);
			sign = decoder.decode(signBase64);
		} catch (IllegalArgumentException e) {
			log.error("Decode base64 error,", e);
			throw new TokenException("令牌无效");
		}
		var signature = new DefaultSignature(SIGNATURE_MODE);
		var result = signature.verify(key, data, sign);
		if (!result) {
			throw new TokenException("令牌签名无效");
		}
		return data;
	}

}
