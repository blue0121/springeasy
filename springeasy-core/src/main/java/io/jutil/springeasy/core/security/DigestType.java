package io.jutil.springeasy.core.security;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public enum DigestType {

	MD5("MD5"),

	SHA_1("SHA-1"),
	SHA_224("SHA-224"),
	SHA_256("SHA-256"),
	SHA_384("SHA-384"),
	SHA_512("SHA-512"),
	SHA3_224("SHA3-224"),
	SHA3_256("SHA3-256"),
	SHA3_384("SHA3-384"),
	SHA3_512("SHA3-512"),
	;

	private String key;
	DigestType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
