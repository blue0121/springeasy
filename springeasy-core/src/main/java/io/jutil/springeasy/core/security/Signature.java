package io.jutil.springeasy.core.security;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
public interface Signature {

	byte[] sign(PrivateKey key, byte[] data);

	byte[] sign(PrivateKey key, InputStream in);

	String signString(PrivateKey key, String data);

	boolean verify(PublicKey key, byte[] data, byte[] sign);

	boolean verify(PublicKey key, InputStream in, byte[] sign);

	boolean verifyString(PublicKey key, String data, String signBase64);

}
