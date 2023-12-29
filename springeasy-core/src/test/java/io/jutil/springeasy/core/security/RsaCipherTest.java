package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class RsaCipherTest {
	private String data = "texttexttext";

	private void testRsa(KeyPair key) {
		var cipher = SecurityFactory.createRsaCipher();
		byte[] raw1 = cipher.encrypt(key.getPublic(), data.getBytes(StandardCharsets.UTF_8));
		String raw1Base64 = Base64.getEncoder().encodeToString(raw1);
		System.out.println("encrypted: " + raw1Base64);
		byte[] src1 = cipher.decrypt(key.getPrivate(), raw1);
		Assertions.assertEquals(data, new String(src1, StandardCharsets.UTF_8));
	}

	@Test
	void testGenerate() {
		KeyPair key = SecurityFactory.generateKeyPair(KeyPairMode.RSA_2048);
		this.testRsa(key);
	}

	@Test
	void testLoadOpenSsl1() {
		var inPub = RsaCipherTest.class.getResourceAsStream("/cert/public.pem");
		var inPriv = RsaCipherTest.class.getResourceAsStream("/cert/private.key");
		KeyPair key = SecurityFactory.loadOpenSslKeyPair(KeyPairMode.RSA, inPub, inPriv);
		this.testRsa(key);
	}

	@Test
	void testLoadOpenSsl2() {
		InputStream inPub = RsaCipherTest.class.getResourceAsStream("/cert/public.crt");
		InputStream inPriv = RsaCipherTest.class.getResourceAsStream("/cert/private.key");
		KeyPair key = SecurityFactory.loadOpenSslKeyPair(KeyPairMode.RSA, inPub, inPriv);
		this.testRsa(key);
	}

	@Test
	void testNotRsa() {
		KeyPair key = SecurityFactory.generateKeyPair(KeyPairMode.DSA_512);
		RsaCipher cipher = SecurityFactory.createRsaCipher();
		var pubKey = key.getPublic();
		var priKey = key.getPrivate();
		var bytes = data.getBytes();
		Assertions.assertThrows(SecurityException.class, () -> cipher.encrypt(pubKey, bytes));
		Assertions.assertThrows(SecurityException.class, () -> cipher.decrypt(priKey, bytes));
	}
}
