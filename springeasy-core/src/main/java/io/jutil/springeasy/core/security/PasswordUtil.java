package io.jutil.springeasy.core.security;

/**
 * @author Jin Zheng
 * @since 2023-11-15
 */
public class PasswordUtil {

	public static String encrypt(String password, String salt) {
		var digest = new DefaultDigest(DigestType.SHA3_256);
		var src = password + "{" + salt + "}";
		return digest.digestToHex(src);
	}

	public static boolean verify(String password, String salt, String encrypted) {
		return encrypt(password, salt).equals(encrypted);
	}

}
