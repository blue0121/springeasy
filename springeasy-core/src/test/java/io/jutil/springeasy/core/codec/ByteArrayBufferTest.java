package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.internal.core.codec.ByteArrayBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author Jin Zheng
 * @since 2023-01-12
 */
class ByteArrayBufferTest {
	private final ByteArrayBuffer buffer = new ByteArrayBuffer();


	@Test
	void testByte() {
		byte b1 = 1;
		byte b2 = 2;
		byte b3 = 3;
		buffer.addCapacity(3);
		buffer.writeByte(b1);
		buffer.writeByte(b2);
		buffer.writeByte(b3);

		Assertions.assertEquals(3, buffer.getSize());
		Assertions.assertEquals(128, buffer.getCapacity());

		Assertions.assertEquals(b1, buffer.readByte());
		Assertions.assertEquals(b2, buffer.readByte());
		Assertions.assertEquals(b3, buffer.readByte());
	}

	@Test
	void testBytes1() {
		byte[] b1 = new byte[3];
		byte[] b2 = new byte[129];
		Arrays.fill(b1, (byte) 10);
		Arrays.fill(b2, (byte) 1);

		buffer.addCapacity(b1.length);
		buffer.writeBytes(b1);

		Assertions.assertEquals(125, buffer.getRemain());
		Assertions.assertEquals(3, buffer.getSize());
		Assertions.assertEquals(128, buffer.getCapacity());

		Assertions.assertEquals(3, buffer.readBytes(b2));
		byte[] b3 = new byte[129];
		Arrays.fill(b3, (byte)1);
		b3[0] = (byte) 10;
		b3[1] = (byte) 10;
		b3[2] = (byte) 10;
		Assertions.assertArrayEquals(b3, b2);
	}

	@Test
	void testBytes2() {
		byte[] b1 = new byte[3];
		byte[] b2 = new byte[129];
		Arrays.fill(b1, (byte) 10);
		Arrays.fill(b2, (byte) 1);

		buffer.addCapacity(b2.length);
		buffer.writeBytes(b2);

		Assertions.assertEquals(127, buffer.getRemain());
		Assertions.assertEquals(129, buffer.getSize());
		Assertions.assertEquals(256, buffer.getCapacity());

		Assertions.assertEquals(3, buffer.readBytes(b1));
		byte[] b3 = new byte[3];
		Arrays.fill(b3, (byte)1);
		Assertions.assertArrayEquals(b3, b1);
	}
}
