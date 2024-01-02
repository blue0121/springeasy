package io.jutil.springeasy.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author zhengj
 * @date 2009-2-15
 * @date 1.0
 * @since 1.0
 */
public class StringUtil {
	public static final String STR_SPLIT = "[\\s,;\\|]";
	public static final char IP_SEP = '.';

	private StringUtil() {
	}

	/**
	 * 在字符串上加上掩码
	 *
	 * @param src    原字符串
	 * @param start  开始位置，从0开始
	 * @param length 替换长度
	 * @param mark   掩码
	 * @return 加上掩码后的字符串
	 */
	public static String mask(String src, int start, int length, String mark) {
		int len = src.length();
		if (len <= start) {
			return src;
		}

		StringBuilder sb = new StringBuilder(src.substring(0, start));
		for (int i = 0; i < Math.ceil((double) length / mark.length()); i++) {
			sb.append(mark);
		}

		if (len > start + length) {
			sb.append(src.substring(start + length));
		}

		return sb.toString();

	}

	/**
	 * 生成字符串序列
	 *
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @param start  开始序号
	 * @param end    结束序号
	 * @param length 序列长度
	 * @return 字符串序列
	 */
	public static List<String> sequence(String prefix, String suffix, int start, int end, int length) {
		if (start < 0 || end < 0) {
			throw new IllegalArgumentException("start 或 end 不能小于 0");
		}
		if (start > end) {
			throw new IllegalArgumentException("start 不能大于 end");
		}

		prefix = (prefix == null ? "" : prefix);
		suffix = (suffix == null ? "" : suffix);
		List<String> list = new ArrayList<>();
		for (int i = start; i <= end; i++) {
			StringBuilder seq = new StringBuilder(prefix.length() + suffix.length() + length);
			seq.append(prefix);
			String val = String.valueOf(i);
			int j = length - val.length();
			while (j-- > 0) {
				seq.append("0");
			}
			seq.append(val);
			list.add(seq.toString());
			seq.append(suffix);
		}
		return list;
	}

	/**
	 * 把 "5, 10, 50, 100, 150, 200, 500, 1000, 2000, 5000" 分割成字符串列表
	 *
	 * @param str 待分割字符串
	 * @return 字符串列表
	 */
	public static List<String> split(String str) {
		List<String> list = new ArrayList<>();
		if (str == null || str.isEmpty()) {
			return list;
		}

		for (String s : str.split(STR_SPLIT)) {
			s = s.trim();
			if (s.isEmpty()) {
				continue;
			}

			list.add(s);
		}
		return list;
	}

	/**
	 * 左填充字符串
	 *
	 * @param str 原始字符串
	 * @param len 目标字符串长度
	 * @param pad 填充字符串
	 * @return 目标字符串
	 */
	public static String leftPad(String str, int len, String pad) {
		if (str == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len - str.length(); i += pad.length()) {
			sb.append(pad);
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 连接字符串
	 *
	 * @param list      字符串列表
	 * @param separator 分割符
	 * @return 连接后的字符串
	 */
	public static String join(Collection<?> list, String separator) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		String sep = separator == null ? "" : separator;
		StringBuilder concat = new StringBuilder();
		for (Object obj : list) {
			concat.append(obj).append(sep);
		}
		if (concat.length() > sep.length()) {
			concat.delete(concat.length() - sep.length(), concat.length());
		}
		return concat.toString();
	}

	/**
	 * 获取Jdbc类型，jdbc:mysql://localhost:3306/yourDBName => mysql
	 *
	 * @param jdbcUrl
	 * @return
	 */
	public static String getJdbcType(String jdbcUrl) {
		if (jdbcUrl == null || jdbcUrl.isEmpty()) {
			return null;
		}

		int len = 5;
		int index = jdbcUrl.indexOf(":", len);
		if (index == -1) {
			return null;
		}

		return jdbcUrl.substring(len, index);
	}

	/**
	 * 产生重复字符串
	 *
	 * @param item      重复项
	 * @param times     重复次数
	 * @param separator 分割符
	 * @return 重复字符串
	 */
	public static String repeat(String item, int times, String separator) {
		AssertUtil.notEmpty(item, "Item");
		AssertUtil.positive(times, "Times");

		String sep = separator == null ? "" : separator;
		StringBuilder concat = new StringBuilder();
		for (int i = 0; i < times; i++) {
			concat.append(item).append(sep);
		}
		if (concat.length() > sep.length()) {
			concat.delete(concat.length() - sep.length(), concat.length());
		}
		return concat.toString();
	}

	public static String sqlPlaceHolder(int count) {
		return repeat("?", count, ",");
	}

	public static String toIpv4(int ip) {
		var str = new StringBuilder();
		str.append((ip >>> 24) & 0xff).append(IP_SEP);
		str.append((ip >>> 16) & 0xff).append(IP_SEP);
		str.append((ip >>> 8) & 0xff).append(IP_SEP);
		str.append(ip & 0xff);
		return str.toString();
	}
}
