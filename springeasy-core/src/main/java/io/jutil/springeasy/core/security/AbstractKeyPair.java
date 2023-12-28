package io.jutil.springeasy.core.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
abstract class AbstractKeyPair implements KeyPair {
	protected X509Certificate certificate;
	protected PublicKey publicKey;
	protected PrivateKey privateKey;

	public Certificate getCertificate() {
		return certificate;
	}

	@Override
	public PublicKey getPublic() {
		return publicKey;
	}

	@Override
	public PrivateKey getPrivate() {
		return privateKey;
	}
}
