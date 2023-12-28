package io.jutil.springeasy.core.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
class DigestTest {
    private String key = "test_data";
    private String path = "/logback.xml";

    @ParameterizedTest
    @CsvSource({"MD5,16",
            "SHA_1,20",
            "SHA_224,28", "SHA3_224,28",
            "SHA_256,32", "SHA3_256,32",
            "SHA_384,48", "SHA3_384,48",
            "SHA_512,64", "SHA3_512,64"})
    void testDigest(String type, int len) {
        var digest = SecurityFactory.createDigest(DigestType.valueOf(type));
        int len2 = len * 2;
        Assertions.assertEquals(len, digest.digest(key).length);
        Assertions.assertEquals(len2, digest.digestToHex(key).length());
        Assertions.assertEquals(len2, digest.digestToHex(key.getBytes(StandardCharsets.UTF_8)).length());
        var is = DigestTest.class.getResourceAsStream(path);
        Assertions.assertEquals(len, digest.digest(is).length);
        is = DigestTest.class.getResourceAsStream(path);
        Assertions.assertEquals(len2, digest.digestToHex(is).length());
        Assertions.assertEquals(type, digest.getDigestType().name());
    }

}
