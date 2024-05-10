package io.jutil.springeasy.core.security;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
class LoadOpenSslKeyPair extends AbstractKeyPair {
	private static final String MINUS = "-";
	private static final String CERT = "X.509";

	private final String algorithm;

	LoadOpenSslKeyPair(KeyPairMode mode, InputStream...inputs) {
		this.algorithm = mode.getKey();
		for (var in : inputs) {
			this.read(in);
		}
	}

	@SuppressWarnings("java:S135")
	private void read(InputStream in) {
		var str = new StringBuilder();
		int i = 0;
		String type = null;
		try (var scanner = new Scanner(in)) {
			while (scanner.hasNextLine()) {
				var line = scanner.nextLine();
				if (line == null || line.isEmpty())
					continue;
				if (i == 0 && line.startsWith(MINUS)) {
					type = line;
					continue;
				}
				if (line.startsWith(MINUS))
					continue;

				str.append(line);
				i++;
			}
		}
		if (type == null || type.isEmpty())
			return;

		var bytes = Base64.getDecoder().decode(str.toString());
		if (type.contains("BEGIN CERTIFICATE")) {
			this.readCertificate(bytes);
		} else if (type.contains("BEGIN PUBLIC KEY")) {
			this.readPublicKey(bytes);
		} else if (type.contains("BEGIN PRIVATE KEY")) {
			this.readPrivateKey(bytes);
		}

	}

	private void readCertificate(byte[] bytes) {
		var is = new ByteArrayInputStream(bytes);
		try {
			var factory = CertificateFactory.getInstance(CERT);
			this.certificate = (X509Certificate) factory.generateCertificate(is);
			this.publicKey = certificate.getPublicKey();
		} catch (CertificateException e) {
			throw new SecurityException(e);
		}
	}

	private void readPublicKey(byte[] bytes) {
		var keySpec = new X509EncodedKeySpec(bytes);
		try {
			var factory = KeyFactory.getInstance(algorithm);
			this.publicKey = factory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	private void readPrivateKey(byte[] bytes) {
		var keySpec = new PKCS8EncodedKeySpec(bytes);
		try {
			var factory = KeyFactory.getInstance(algorithm);
			this.privateKey = factory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}
}
