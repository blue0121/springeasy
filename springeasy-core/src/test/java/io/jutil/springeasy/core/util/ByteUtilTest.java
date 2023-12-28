package io.jutil.springeasy.core.util;

import io.jutil.springeasy.core.codec.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class ByteUtilTest {
	@Test
	void testRandom() {
		var key = ByteUtil.random(32);
		Assertions.assertEquals(32, key.length);
		var hex = Hex.encode(key);
		System.out.println(hex);
		Assertions.assertEquals(64, hex.length());
	}

	@Test
	void testConcat1() {
		var b1 = new byte[]{1};
		var b2 = new byte[]{2};
		var data = ByteUtil.concat(b1, b2);
		System.out.println(Arrays.toString(data));
		Assertions.assertEquals(2, data.length);
		var view = new byte[]{1, 2};
		Assertions.assertArrayEquals(view, data);
	}

	@Test
	void testConcat2() {
		var b1 = new byte[]{1, 11, 111};
		var b2 = new byte[]{2, 22};
		var b3 = new byte[]{3};
		var data = ByteUtil.concat(b1, b2, b3);
		System.out.println(Arrays.toString(data));
		Assertions.assertEquals(6, data.length);
		var view = new byte[]{1, 11, 111, 2, 22, 3};
		Assertions.assertArrayEquals(view, data);
	}

	@Test
	void testConcat3() {
		var data1 = ByteUtil.concat();
		Assertions.assertEquals(0, data1.length);

		var data2 = ByteUtil.concat(null);
		Assertions.assertEquals(0, data2.length);

		var data3 = ByteUtil.concat(new byte[0]);
		Assertions.assertEquals(0, data3.length);

		var data4 = ByteUtil.concat(new byte[0], null);
		Assertions.assertEquals(0, data4.length);

		var data5 = ByteUtil.concat(null, new byte[0]);
		Assertions.assertEquals(0, data5.length);
	}

	@Test
	void testMask() {
		var src1 = new byte[] {1,2,3};
		var dst1 = new byte[] {2,4,6};
		Assertions.assertArrayEquals(dst1, ByteUtil.mask(src1));
	}
}
