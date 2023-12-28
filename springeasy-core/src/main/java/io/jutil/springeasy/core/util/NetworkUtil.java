package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @author Jin Zheng
 * @since 2023-05-30
 */
@Slf4j
public class NetworkUtil {
    private NetworkUtil() {
    }

    public static byte[] getIpv4ForByteArray() {
        var ipv4 = getIpv4Address();
        AssertUtil.notNull(ipv4, "IPv4");
        return ipv4.getAddress();
    }

    public static String getIpv4ForString() {
        var ipv4 = getIpv4Address();
        AssertUtil.notNull(ipv4, "IPv4");
        return ipv4.getHostAddress();
    }

    public static int getIpv4ForInt() {
        var ipv4 = getIpv4Address();
        AssertUtil.notNull(ipv4, "IPv4");
        return ipv4.hashCode();
    }

    @SuppressWarnings("java:S135")
    public static Inet4Address getIpv4Address() {
        try {
            var networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                var networkInterface = networkInterfaces.nextElement();
                var inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    var inetAddress = inetAddresses.nextElement();
                    if (!(inetAddress instanceof Inet4Address ipv4)) {
                        continue;
                    }
                    if (ipv4.isLoopbackAddress() || ipv4.isLinkLocalAddress()) {
                        continue;
                    }

                    return ipv4;
                }
            }
        }
        catch (SocketException e) {
            log.error("Error,", e);
        }
        return null;
    }

    public static byte[] getHardwareAddress() {
        try {
            var networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                var networkInterface = networkInterfaces.nextElement();
                var inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    var inetAddress = inetAddresses.nextElement();
                    if (!(inetAddress instanceof Inet4Address ipv4)) {
                        continue;
                    }
                    if (ipv4.isLoopbackAddress() || ipv4.isLinkLocalAddress()) {
                        continue;
                    }

                    return networkInterface.getHardwareAddress();
                }
            }
        }
        catch (SocketException e) {
            log.error("Error,", e);
        }
        return null;
    }
}
