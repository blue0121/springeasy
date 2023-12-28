package io.jutil.springeasy.core.security;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Jin Zheng
 * @since 2023-10-10
 */
public interface KeyPair {

    PublicKey getPublic();

    PrivateKey getPrivate();

}
