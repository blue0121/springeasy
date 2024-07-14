package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author Jin Zheng
 * @since 2023-11-29
 */
class TokenUtilTest {

	@Test
	void test() {
		var keyPair = TokenUtil.generate();
		var data = "Hello World!".getBytes(StandardCharsets.UTF_8);
		var token = TokenUtil.create(keyPair.getPrivate(), data);
		System.out.println(token);
		var view = TokenUtil.verify(keyPair.getPublic(), token);
		Assertions.assertArrayEquals(data, view);

		var token2 = token + "1";
		var pubKey = keyPair.getPublic();
		Assertions.assertThrows(TokenException.class, () -> TokenUtil.verify(pubKey, token2));
	}

	@Test
	void testLoad() {
		var publicKeyPath = "classpath:/cert/ed25519_pub.pub";
		var privateKeyPath = "classpath:/cert/ed25519_priv.key";
		var keyPair = TokenUtil.load(publicKeyPath, privateKeyPath);
		Assertions.assertNotNull(keyPair);
		Assertions.assertNotNull(keyPair.getPublic());
		Assertions.assertNotNull(keyPair.getPrivate());
	}
}
