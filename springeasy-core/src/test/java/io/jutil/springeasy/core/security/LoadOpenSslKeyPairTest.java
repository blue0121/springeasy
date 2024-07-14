package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class LoadOpenSslKeyPairTest {
	private static String prefix = "/cert";

	@ParameterizedTest
	@CsvSource({"RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key,false",
			"RSA,/public.pem,/private.key,false",
			"RSA,/public.crt,/private.key,true",
			"DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key,false",
			"EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key,false",
			"ED_25519,/ed25519_pub.pub,/ed25519_priv.key,false",
			"ED_448,/ed448_pub.pub,/ed448_priv.key,false"})
	void testLoadOpenSsl(String mode, String pubPath, String privPath, boolean cert) {
		var inPub = LoadOpenSslKeyPairTest.class.getResourceAsStream(prefix + pubPath);
		var inPriv = LoadOpenSslKeyPairTest.class.getResourceAsStream(prefix + privPath);
		var keyPairMode = KeyPairMode.valueOf(mode);

		var keyPair = SecurityFactory.loadOpenSslKeyPair(keyPairMode, inPub, inPriv);
		Assertions.assertNotNull(keyPair);
		Assertions.assertNotNull(keyPair.getPublic());
		Assertions.assertNotNull(keyPair.getPrivate());

		var abstractKeyPair = (AbstractKeyPair) keyPair;
		if (cert) {
			Assertions.assertNotNull(abstractKeyPair.getCertificate());
		} else {
			Assertions.assertNull(abstractKeyPair.getCertificate());
		}
	}
}
