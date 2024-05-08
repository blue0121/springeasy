package io.jutil.springeasy.core.security;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class GenerateKeyPair extends AbstractKeyPair {
	private final KeyPairMode mode;

	GenerateKeyPair(KeyPairMode mode) {
		this.mode = mode;
		this.init();
	}

	private void init() {
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance(mode.getKey());
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
		if (mode.getSize() > 0) {
			generator.initialize(mode.getSize());
		}
		var keyPair = generator.generateKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}
}
