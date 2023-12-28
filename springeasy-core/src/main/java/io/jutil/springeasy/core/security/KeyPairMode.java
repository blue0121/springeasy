package io.jutil.springeasy.core.security;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public enum KeyPairMode {
    RSA("RSA", 2048),
    RSA_2048("RSA", 2048),
    RSA_3084("RSA", 3084),
    RSA_4096("RSA", 4096),

    DSA("DSA", 1024),
    DSA_512("DSA", 512),
    DSA_768("DSA", 768),
    DSA_1024("DSA", 1024),

    EC("EC", 256),
    EC_256("EC", 256),
    EC_384("EC", 384),
    EC_521("EC", 521),

    ED_25519("Ed25519", 0),

    ED_448("Ed448", 0),
    ;

    private String key;
    private int size;

    KeyPairMode(String key, int size) {
        this.key = key;
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }
}
