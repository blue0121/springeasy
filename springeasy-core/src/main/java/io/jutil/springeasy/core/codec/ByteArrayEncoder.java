package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.util.AssertUtil;
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
public class ByteArrayEncoder implements Encoder {
	private final ByteArrayBuffer buffer;

	public ByteArrayEncoder(ByteArrayBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void resetWriteIndex(int offset) {
		buffer.resetWriteIndex(offset);
	}

	@Override
	public void skipWriteIndex(int offset) {
		buffer.skipWriteIndex(offset);
	}

	@Override
	public void writeUInt(int val) {
		buffer.ensureCapacity(Const.MAX_VAR_INT_LEN);
		var x = val;
		while (x >= 0x80) {
			buffer.writeByte((byte) (x | 0x80));
			x >>>= 7;
		}
		buffer.writeByte((byte) x);
	}

	@Override
	public void writeULong(long val) {
		buffer.ensureCapacity(Const.MAX_VAR_LONG_LEN);
		var x = val;
		while (x >= 0x80) {
			buffer.writeByte((byte) (x | 0x80));
			x >>>= 7;
		}
		buffer.writeByte((byte) x);
	}

	@Override
	public void writeSInt(int val) {
		var x = NumberUtil.zigZagEncode(val);
		this.writeUInt(x);
	}

	@Override
	public void writeSLong(long val) {
		var x = NumberUtil.zigZagEncode(val);
		this.writeULong(x);
	}

	@Override
	public void writeByte(byte val) {
		buffer.ensureCapacity(Const.BYTE_LEN);
		buffer.writeByte(val);
	}

	@Override
	public void writeBytes(byte[] val) {
		AssertUtil.notEmpty(val, "Value");
		buffer.ensureCapacity(val.length);
		buffer.writeBytes(val);
	}

	@Override
	public void writeShort(short val) {
		buffer.ensureCapacity(Const.SHORT_LEN);
		var x = val;
		for (int i = 0; i < Const.SHORT_LEN; i++) {
			buffer.writeByte((byte) (x & 0xff));
			x >>>= 8;
		}
	}

	@Override
	public void writeInt(int val) {
		buffer.ensureCapacity(Const.INT_LEN);
		var x = val;
		for (int i = 0; i < Const.INT_LEN; i++) {
			buffer.writeByte((byte) (x & 0xff));
			x >>>= 8;
		}
	}

	@Override
	public void writeLong(long val) {
		buffer.ensureCapacity(Const.LONG_LEN);
		var x = val;
		for (int i = 0; i < Const.LONG_LEN; i++) {
			buffer.writeByte((byte) (x & 0xff));
			x >>>= 8;
		}
	}

	@Override
	public void writeFloat(float val) {
		this.writeInt(Float.floatToRawIntBits(val));
	}

	@Override
	public void writeDouble(double val) {
		this.writeLong(Double.doubleToRawLongBits(val));
	}

	@Override
	public void writeBoolean(boolean val) {
		buffer.ensureCapacity(Const.BYTE_LEN);
		buffer.writeByte(val ? (byte) 1 : (byte) 0);
	}

	@Override
	public void writeChar(char val) {
		buffer.ensureCapacity(Const.SHORT_LEN);
		var x = val;
		for (int i = 0; i < Const.SHORT_LEN; i++) {
			buffer.writeByte((byte) (x & 0xff));
			x >>= 8;
		}
	}

	@Override
	public void writeString(String val) {
		if (val == null || val.isEmpty()) {
			this.writeUInt(Const.LEN_EMPTY);
			return;
		}
		byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
		buffer.ensureCapacity(Const.INT_LEN + bytes.length);
		this.writeUInt(bytes.length);
		this.writeBytes(bytes);
	}

	@Override
	public void writeDate(Date val) {
		if (val == null) {
			this.writeLong(Const.LEN_EMPTY);
			return;
		}

		this.writeLong(val.getTime());
	}

	@Override
	public void writeLocalDateTime(LocalDateTime val) {
		if (val == null) {
			this.writeLong(Const.LEN_EMPTY);
			return;
		}
		var instant = val.atZone(ZoneId.systemDefault()).toInstant();
		this.writeLong(instant.toEpochMilli());
	}

	@Override
	public void writeInstant(Instant val) {
		if (val == null) {
			this.writeLong(Const.LEN_EMPTY);
			return;
		}
		this.writeLong(val.toEpochMilli());
	}

	@Override
	public int getWriteIndex() {
		return buffer.getWriteIndex();
	}

	@Override
	public int getCapacity() {
		return buffer.getCapacity();
	}

	public byte[] getByteArray() {
		return buffer.toByteArray();
	}

}
