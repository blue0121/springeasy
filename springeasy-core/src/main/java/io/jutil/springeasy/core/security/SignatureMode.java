package io.jutil.springeasy.core.security;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public enum SignatureMode {

    MD5_RSA("MD5WithRSA"),

    SHA1_RSA("SHA1WithRSA"),
    SHA224_RSA("SHA224WithRSA"),
    SHA256_RSA("SHA256WithRSA"),
    SHA384_RSA("SHA384WithRSA"),
    SHA512_RSA("SHA512WithRSA"),
    SHA3_224_RSA("SHA3-224WithRSA"),
    SHA3_256_RSA("SHA3-256WithRSA"),
    SHA3_384_RSA("SHA3-384WithRSA"),
    SHA3_512_RSA("SHA3-512WithRSA"),

    SHA1_DSA("SHA1WithDSA"),
    SHA224_DSA("SHA224WithDSA"),
    SHA256_DSA("SHA256WithDSA"),
    SHA384_DSA("SHA384WithDSA"),
    SHA512_DSA("SHA512WithDSA"),
    SHA3_224_DSA("SHA3-224WithDSA"),
    SHA3_256_DSA("SHA3-256WithDSA"),
    SHA3_384_DSA("SHA3-384WithDSA"),
    SHA3_512_DSA("SHA3-512WithDSA"),

    SHA1_ECDSA("SHA1WithECDSA"),
    SHA224_ECDSA("SHA224WithECDSA"),
    SHA256_ECDSA("SHA256WithECDSA"),
    SHA384_ECDSA("SHA384WithECDSA"),
    SHA512_ECDSA("SHA512WithECDSA"),
    SHA3_224_ECDSA("SHA3-224WithECDSA"),
    SHA3_256_ECDSA("SHA3-256WithECDSA"),
    SHA3_384_ECDSA("SHA3-384WithECDSA"),
    SHA3_512_ECDSA("SHA3-512WithECDSA"),

    ED_25519("Ed25519"),

    ED_448("Ed448"),
    ;

    private String key;
    SignatureMode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
