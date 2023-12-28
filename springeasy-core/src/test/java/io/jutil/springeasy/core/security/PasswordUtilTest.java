package io.jutil.springeasy.core.security;

import io.jutil.springeasy.core.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-11-15
 */
class PasswordUtilTest {

	@Test
	void testEncrypt() {
		var salt = "salt";
		var password = StringUtil.repeat("abc", 3, "-");
		var encrypted = PasswordUtil.encrypt(password, salt);
		Assertions.assertNotNull(encrypted);
		System.out.println(encrypted);
		Assertions.assertTrue(PasswordUtil.verify(password, salt, encrypted));
	}

}
