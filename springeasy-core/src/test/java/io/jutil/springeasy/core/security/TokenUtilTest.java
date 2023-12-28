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
		Assertions.assertThrows(TokenException.class, () -> TokenUtil.verify(keyPair.getPublic(), token2));
	}
}
