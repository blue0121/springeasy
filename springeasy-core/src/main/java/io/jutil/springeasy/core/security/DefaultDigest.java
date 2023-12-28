package io.jutil.springeasy.core.security;

import io.jutil.springeasy.core.codec.Hex;
import io.jutil.springeasy.core.util.AssertUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
class DefaultDigest implements Digest {
    private final DigestType type;

    DefaultDigest(DigestType type) {
        this.type = type;
    }

    private MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance(type.getKey());
        } catch (NoSuchAlgorithmException e) {
            throw new java.lang.SecurityException(e);
        }
    }

    @Override
    public byte[] digest(byte[] src) {
        AssertUtil.notEmpty(src, "Source");
        var md = this.getMessageDigest();
        return md.digest(src);
    }

    @Override
    public byte[] digest(String src) {
        AssertUtil.notEmpty(src, "Source");
        return this.digest(src.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] digest(InputStream is) {
        AssertUtil.notNull(is, "InputStream");
        var md = this.getMessageDigest();
        var buf = new byte[SecurityConstant.BUF_SIZE];
        int read = -1;
        try (is) {
            while ((read = is.read(buf)) != -1) {
                md.update(buf, 0, read);
            }
            return md.digest();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String digestToHex(byte[] src) {
        var bytes = this.digest(src);
        return Hex.encode(bytes);
    }

    @Override
    public String digestToHex(String src) {
        var bytes = this.digest(src);
        return Hex.encode(bytes);
    }

    @Override
    public String digestToHex(InputStream is) {
        var bytes = this.digest(is);
        return Hex.encode(bytes);
    }

    @Override
    public DigestType getDigestType() {
        return type;
    }
}
