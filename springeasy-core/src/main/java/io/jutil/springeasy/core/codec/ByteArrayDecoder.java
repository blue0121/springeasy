package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.util.NumberUtil;

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
	private final ByteArrayBuffer buffer;

	public ByteArrayDecoder(byte[] buf) {
		this.buffer = new ByteArrayBuffer(buf);
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
	@SuppressWarnings("java:S1905")
	public int readUInt() {
		int x = 0;
		int s = 0;
		for (int i = 0; i < Const.MAX_VAR_INT_LEN; i++) {
			var b = buffer.readByte();
			if (b >= 0) {
				return x | ((int)b) << s;
			}
			x |= (b & 0x7f) << s;
			s += 7;
		}
		return x;
	}

	@Override
	public long readULong() {
		long x = 0L;
		int s = 0;
		for (int i = 0; i < Const.MAX_VAR_INT_LEN; i++) {
			var b = buffer.readByte();
			if (b >= 0) {
				return x | ((long)b) << s;
			}
			x |= ((long)b & 0x7f) << s;
			s += 7;
		}
		return x;
	}

	@Override
	public int readSInt() {
		var x = this.readUInt();
		return NumberUtil.zigZagDecode(x);
	}

	@Override
	public long readSLong() {
		var x = this.readULong();
		return NumberUtil.zigZagDecode(x);
	}

	@Override
	public byte readByte() {
		buffer.checkReadIndex(Const.BYTE_LEN);
		return buffer.readByte();
	}

	@Override
	public int readBytes(byte[] buf) {
		return buffer.readBytes(buf);
	}

	@Override
	public short readShort() {
		buffer.checkReadIndex(Const.SHORT_LEN);
		return (short) ((0xff & buffer.readByte()) | (0xff & buffer.readByte()) << 8);
	}

	@Override
	public int readInt() {
		buffer.checkReadIndex(Const.INT_LEN);
		int x = 0;
		for (int i = 0; i < Const.INT_LEN; i++) {
			int s = (buffer.readByte() & 0xff) << (i * 8);
			x |= s;
		}
		return x;
	}

	@Override
	public long readLong() {
		buffer.checkReadIndex(Const.LONG_LEN);
		long x = 0;
		for (int i = 0; i < Const.LONG_LEN; i++) {
			long s = (long) (buffer.readByte() & 0xff) << (i * 8);
			x |= s;
		}
		return x;
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
		return val != (byte) 0;
	}

	@Override
	public char readChar() {
		buffer.checkReadIndex(Const.SHORT_LEN);
		return (char) ((0xff & buffer.readByte()) | (0xff & buffer.readByte()) << 8);
	}

	@Override
	public String readString() {
		var len = this.readUInt();
		if (len == 0) {
			return "";
		}
		buffer.checkReadIndex(len);
		var str = new String(buffer.getBuffer(), buffer.getReadIndex(), len, StandardCharsets.UTF_8);
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
