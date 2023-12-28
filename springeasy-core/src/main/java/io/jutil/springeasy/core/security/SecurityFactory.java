package io.jutil.springeasy.core.security;

import io.jutil.springeasy.core.util.AssertUtil;

import java.io.InputStream;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public class SecurityFactory {
    private SecurityFactory() {
    }

    public static KeyPair generateKeyPair(KeyPairMode mode) {
        AssertUtil.notNull(mode, "KeyPairMode");
        return new GenerateKeyPair(mode);
    }

    public static KeyPair loadOpenSslKeyPair(KeyPairMode mode, InputStream...inputs) {
        AssertUtil.notNull(mode, "KeyPairMode");
        return new LoadOpenSslKeyPair(mode, inputs);
    }

    public static Digest createDigest(DigestType type) {
        AssertUtil.notNull(type, "Digest Type");
        return new DefaultDigest(type);
    }

    public static RsaCipher createRsaCipher() {
        return new DefaultRsaCipher();
    }

    public static Signature createSignature(SignatureMode mode) {
        AssertUtil.notNull(mode, "Signature Mode");
        return new DefaultSignature(mode);
    }
}
