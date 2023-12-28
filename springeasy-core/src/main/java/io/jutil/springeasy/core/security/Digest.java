package io.jutil.springeasy.core.security;

import java.io.InputStream;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public interface Digest {

	byte[] digest(byte[] src);

	byte[] digest(String src);

	byte[] digest(InputStream is);

	String digestToHex(byte[] src);

	String digestToHex(String src);

	String digestToHex(InputStream is);


	DigestType getDigestType();

}
