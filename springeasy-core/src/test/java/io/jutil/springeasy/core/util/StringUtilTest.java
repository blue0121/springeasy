package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
class StringUtilTest {

	@Test
	void testSplit() {
		String s1 = "1,2;3   4  5|6";
		List<String> list1 = Arrays.asList("1", "2", "3", "4", "5", "6");
		Assertions.assertEquals(list1, StringUtil.split(s1));
		Assertions.assertTrue(StringUtil.split(null).isEmpty());
		Assertions.assertTrue(StringUtil.split("").isEmpty());
	}

	@Test
	void testLeftPad() {
		Assertions.assertNull(StringUtil.leftPad(null, 0, "0"));
		Assertions.assertEquals("000123", StringUtil.leftPad("123", 6, "0"));
		Assertions.assertEquals("1234", StringUtil.leftPad("1234", 2, "0"));
		Assertions.assertEquals("01234", StringUtil.leftPad("1234", 5, "0"));
		Assertions.assertEquals("001234", StringUtil.leftPad("1234", 5, "00"));
	}

	@Test
	void testJoin() {
		Assertions.assertEquals("a,b,c", StringUtil.join(Arrays.asList("a", "b", "c"), ","));
		Assertions.assertEquals("a", StringUtil.join(Arrays.asList("a"), ","));
		Assertions.assertEquals("abc", StringUtil.join(Arrays.asList("a", "b", "c"), ""));
		Assertions.assertEquals("abc", StringUtil.join(Arrays.asList("a", "b", "c"), null));
		Assertions.assertNull(StringUtil.join(null, null));
		Assertions.assertNull(StringUtil.join(new ArrayList<>(), null));
	}

	@Test
	void testGetJdbcType() {
		Assertions.assertEquals("mysql", StringUtil.getJdbcType("jdbc:mysql://localhost:3306/db"));
		Assertions.assertEquals("oracle", StringUtil.getJdbcType("jdbc:oracle:thin:@localhost:1521:db"));
		Assertions.assertEquals("sqlserver", StringUtil.getJdbcType("jdbc:sqlserver://localhost:1433;databasename=db"));
		Assertions.assertEquals("postgresql", StringUtil.getJdbcType("jdbc:postgresql://localhost:5432/db"));
	}

	@Test
	void testRepeat() {
		String repeat1 = StringUtil.repeat("?", 4, ",");
		Assertions.assertEquals("?,?,?,?", repeat1);

		String repeat2 = StringUtil.repeat("?", 4, null);
		Assertions.assertEquals("????", repeat2);

		String repeat3 = StringUtil.repeat("?", 1, null);
		Assertions.assertEquals("?", repeat3);
	}

	@Test
	void testSqlPlaceHolder() {
		Assertions.assertEquals("?", StringUtil.sqlPlaceHolder(1));
		Assertions.assertEquals("?,?", StringUtil.sqlPlaceHolder(2));
		Assertions.assertEquals("?,?,?,?,?", StringUtil.sqlPlaceHolder(5));
	}

	@Test
	void testToIpv4() {
		Assertions.assertEquals("127.0.0.1", StringUtil.toIpv4(2130706433));
		Assertions.assertEquals("0.0.0.0", StringUtil.toIpv4(0));
		Assertions.assertEquals("192.168.1.1", StringUtil.toIpv4(-1062731519));
	}

	@Test
	void testToLowerCamelCase() {
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("a-b-c"));
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("A-b-c"));
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("a_b_c"));
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("a-b_c"));
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("a-bC"));
		Assertions.assertEquals("aBC", StringUtil.toLowerCamelCase("aBC"));
	}
}
