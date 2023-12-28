package io.jutil.springeasy.spring.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-12-04
 */
public class IpUtil {
	private static final String STR_UNKNOWN = "unknown";
	private static final List<String> KEY_LIST = List.of("x-forwarded-for",
			"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"X-Real-IP");

	private IpUtil() {
	}

	public static String getIp(HttpServletRequest request) {
		String ip = null;
		for (var key : KEY_LIST) {
			ip = request.getHeader(key);
			if (ip != null && !ip.isEmpty() && !STR_UNKNOWN.equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}
}
