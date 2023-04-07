package io.jutil.springeasy.internal.core.codec;

import io.jutil.springeasy.core.codec.Decoder;
import io.jutil.springeasy.core.util.AssertUtil;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
public class ByteArrayDecoder implements Decoder {
	private final ByteArrayBuffer buffer = new ByteArrayBuffer();

	public ByteArrayDecoder(byte[] buf) {
		this.addBuffer(buf);
	}

	public void addBuffer(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buffer");
		buffer.addCapacity(buf.length);
		buffer.writeBytes(buf);
	}

	@Override
	public void resetReadIndex(int offset) {
		buffer.resetReadIndex(offset);
	}

	@Override
	public void skipReadIndex(int offset) {
		buffer.skipReadIndex(offset);
	}

	@Override
	public byte readByte() {
		buffer.checkReadIndex(CodecConstant.BYTE_LEN);
		return buffer.readByte();
	}

	@Override
	public int readBytes(byte[] buf) {
		return buffer.readBytes(buf);
	}

	@Override
	public short readShort() {
		buffer.checkReadIndex(CodecConstant.SHORT_LEN);
		return (short) (((0xff & buffer.readByte()) << 8) | (0xff & buffer.readByte()));
	}

	@Override
	public int readInt() {
		buffer.checkReadIndex(CodecConstant.INT_LEN);
		return ((0xff & buffer.readByte()) << 24)
				| ((0xff & buffer.readByte()) << 16)
				| ((0xff & buffer.readByte()) << 8)
				| (0xff & buffer.readByte());
	}

	@Override
	public long readLong() {
		buffer.checkReadIndex(CodecConstant.LONG_LEN);
		return ((0xffL & buffer.readByte()) << 56)
				| ((0xffL & buffer.readByte()) << 48)
				| ((0xffL & buffer.readByte()) << 40)
				| ((0xffL & buffer.readByte()) << 32)
				| ((0xffL & buffer.readByte()) << 24)
				| ((0xffL & buffer.readByte()) << 16)
				| ((0xffL & buffer.readByte()) << 8)
				| (0xffL & buffer.readByte());
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(this.readInt());
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(this.readLong());
	}

	@Override
	public boolean readBoolean() {
		byte val = this.readByte();
		return !(val == (byte) 0);
	}

	@Override
	public char readChar() {
		buffer.checkReadIndex(CodecConstant.SHORT_LEN);
		return (char) (((0xff & buffer.readByte()) << 8) | (0xff & buffer.readByte()));
	}

	@Override
	public String readString() {
		var len = this.readInt();
		if (len == 0) {
			return "";
		}
		buffer.checkReadIndex(len);
		var str = new String(buffer.getByteArray(), buffer.getReadIndex(), len, StandardCharsets.UTF_8);
		buffer.skipReadIndex(len);
		return str;
	}

	@Override
	public Date readDate() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		return new Date(val);
	}

	@Override
	public LocalDateTime readLocalDateTime() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		var instant = Instant.ofEpochMilli(val);
		return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	@Override
	public Instant readInstant() {
		var val = this.readLong();
		if (val == 0L) {
			return null;
		}
		return Instant.ofEpochMilli(val);
	}

	@Override
	public int getReadIndex() {
		return buffer.getReadIndex();
	}

	@Override
	public int getSize() {
		return buffer.getSize();
	}

	@Override
	public int getCapacity() {
		return buffer.getCapacity();
	}

}
