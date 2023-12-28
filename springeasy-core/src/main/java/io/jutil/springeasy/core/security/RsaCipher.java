package io.jutil.springeasy.core.security;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public interface RsaCipher {

    byte[] encrypt(PublicKey key, byte[] data);

    String encryptString(PublicKey key, String data);

    byte[] decrypt(PrivateKey key, byte[] raw);

    String decryptString(PrivateKey key, String rawBase64);
}
