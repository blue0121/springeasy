package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class SignatureTest {
	private byte[] data = "texttexttext".getBytes(StandardCharsets.UTF_8);

	private static String prefix = "/cert";

	@ParameterizedTest
	@CsvSource({"MD5_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA1_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA224_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA256_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA384_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA512_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA3_224_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA3_256_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA3_384_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA3_512_RSA,RSA,/rsa_pub.pub,/rsa_priv_pkcs8.key",
			"SHA1_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA224_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA256_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA384_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA512_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA3_224_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA3_256_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA3_384_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA3_512_DSA,DSA,/dsa_pub.pub,/dsa_priv_pkcs8.key",
			"SHA1_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA224_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA256_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA384_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA512_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA3_224_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA3_256_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA3_384_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"SHA3_512_ECDSA,EC,/ecdsa_pub.pub,/ecdsa_priv_pkcs8.key",
			"ED_25519,ED_25519,/ed25519_pub.pub,/ed25519_priv.key",
			"ED_448,ED_448,/ed448_pub.pub,/ed448_priv.key"})
	void testSignature(SignatureMode mode, KeyPairMode keyMode, String pubPath, String privPath) {
		var inPub = LoadOpenSslKeyPairTest.class.getResourceAsStream(prefix + pubPath);
		var inPriv = LoadOpenSslKeyPairTest.class.getResourceAsStream(prefix + privPath);
		var keyPair = SecurityFactory.loadOpenSslKeyPair(keyMode, inPub, inPriv);

		var signature = SecurityFactory.createSignature(mode);
		var sign = signature.sign(keyPair.getPrivate(), data);
		System.out.println(mode.getKey() + " 签名长度: " + sign.length);
		Assertions.assertTrue(signature.verify(keyPair.getPublic(), data, sign));
	}
}
