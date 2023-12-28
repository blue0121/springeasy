package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.util.NetworkUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
class Base32Test {
	private org.apache.commons.codec.binary.Base32 commonsBase32;

	public Base32Test() {
		this.commonsBase32 = new org.apache.commons.codec.binary.Base32();
	}

	@Test
	void testEncode() {
		String str = "hello world";
		var bytes = str.getBytes();

		System.out.println(Hex.encode(bytes));
		var base32 = Base32.encode(bytes);
		System.out.println(base32);
		Assertions.assertArrayEquals(bytes, Base32.decode(base32));

		this.verify(bytes, base32);
	}

	@Test
	void test2() {
		var ip = NetworkUtil.getIpv4ForByteArray();
		System.out.println(Hex.encode(ip));
		var base32 = Base32.encode(ip);
		System.out.println(base32);
		Assertions.assertArrayEquals(ip, Base32.decode(base32));

		this.verify(ip, base32);
	}

	private void verify(byte[] bytes, String base32) {
		var str = commonsBase32.encodeToString(bytes);
		System.out.println(str);
		var index = str.indexOf('=');
		if (index != -1) {
			str = str.substring(0, index);
		}
		Assertions.assertEquals(str.toLowerCase(), base32);
	}

}
