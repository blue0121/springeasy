package io.jutil.springeasy.spring.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Jin Zheng
 * @since 2023-12-04
 */
@ExtendWith(MockitoExtension.class)
class IpUtilTest {
	@Mock
	HttpServletRequest request;

	@Test
	void testGetIp() {
		Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		var ip = IpUtil.getIp(request);
		Assertions.assertEquals("127.0.0.1", ip);
	}
}
