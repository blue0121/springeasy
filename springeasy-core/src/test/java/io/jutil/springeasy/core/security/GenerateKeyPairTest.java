package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class GenerateKeyPairTest {

	@Test
	void testRSA() {
		this.testKeyPair(KeyPairMode.RSA);
		this.testKeyPair(KeyPairMode.RSA_2048);
		this.testKeyPair(KeyPairMode.RSA_3084);
		this.testKeyPair(KeyPairMode.RSA_4096);
	}

	@Test
	void testDSA() {
		this.testKeyPair(KeyPairMode.DSA);
		this.testKeyPair(KeyPairMode.DSA_512);
		this.testKeyPair(KeyPairMode.DSA_768);
		this.testKeyPair(KeyPairMode.DSA_1024);
	}

	@Test
	void testECDSA() {
		this.testKeyPair(KeyPairMode.EC);
		this.testKeyPair(KeyPairMode.EC_256);
		this.testKeyPair(KeyPairMode.EC_384);
		this.testKeyPair(KeyPairMode.EC_521);
	}

	@Test
	void testEd25519() {
		this.testKeyPair(KeyPairMode.ED_25519);
	}

	@Test
	void testEd448() {
		this.testKeyPair(KeyPairMode.ED_448);
	}

	private void testKeyPair(KeyPairMode mode) {
		KeyPair keyPair = SecurityFactory.generateKeyPair(mode);
		Assertions.assertNotNull(keyPair);
		Assertions.assertNotNull(keyPair.getPrivate());
		Assertions.assertNotNull(keyPair.getPublic());
		System.out.println(mode + " 公钥长度: " + keyPair.getPrivate().getEncoded().length
		+ ", 私钥长度: " + keyPair.getPublic().getEncoded().length);
	}
}
