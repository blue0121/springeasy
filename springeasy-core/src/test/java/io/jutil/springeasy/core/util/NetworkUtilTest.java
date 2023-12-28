package io.jutil.springeasy.core.util;

import io.jutil.springeasy.core.codec.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
class NetworkUtilTest {
	@Test
	void testGetIpv4Address() {
		var ipv4 = NetworkUtil.getIpv4Address();
		Assertions.assertNotNull(ipv4);
		System.out.println(ipv4.getHostAddress());
	}

	@Test
	void testGetHardwareAddress() {
		var mac = NetworkUtil.getHardwareAddress();
		Assertions.assertNotNull(mac);
		Assertions.assertNotEquals(0, mac.length);
		System.out.println(Hex.encode(mac));
	}

}
