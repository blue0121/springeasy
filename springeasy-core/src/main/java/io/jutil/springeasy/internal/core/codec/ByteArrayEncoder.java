package io.jutil.springeasy.internal.core.codec;

import io.jutil.springeasy.core.codec.Encoder;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
@NoArgsConstructor
public class ByteArrayEncoder implements Encoder {
	private final ByteArrayBuffer buffer = new ByteArrayBuffer();


	@Override
	public void resetWriteIndex(int offset) {
		buffer.resetWriteIndex(offset);
	}

	@Override
	public void skipWriteIndex(int offset) {
		buffer.skipWriteIndex(offset);
	}

	@Override
	public void writeByte(byte val) {
		buffer.addCapacity(CodecConstant.BYTE_LEN);
		buffer.writeByte(val);
	}

	@Override
	public void writeBytes(byte[] val) {
		AssertUtil.notEmpty(val, "Value");
		buffer.addCapacity(val.length);
		buffer.writeBytes(val);
	}

	@Override
	public void writeShort(short val) {
		buffer.addCapacity(CodecConstant.SHORT_LEN);
		buffer.writeByte((byte) ((val >> 8) & 0xff));
		buffer.writeByte((byte) (val & 0xff));
	}

	@Override
	public void writeInt(int val) {
		buffer.addCapacity(CodecConstant.INT_LEN);
		buffer.writeByte((byte) ((val >> 24) & 0xff));
		buffer.writeByte((byte) ((val >> 16) & 0xff));
		buffer.writeByte((byte) ((val >> 8) & 0xff));
		buffer.writeByte((byte) (val & 0xff));
	}

	@Override
	public void writeLong(long val) {
		buffer.addCapacity(CodecConstant.LONG_LEN);
		buffer.writeByte((byte) ((val >> 56) & 0xff));
		buffer.writeByte((byte) ((val >> 48) & 0xff));
		buffer.writeByte((byte) ((val >> 40) & 0xff));
		buffer.writeByte((byte) ((val >> 32) & 0xff));
		buffer.writeByte((byte) ((val >> 24) & 0xff));
		buffer.writeByte((byte) ((val >> 16) & 0xff));
		buffer.writeByte((byte) ((val >> 8) & 0xff));
		buffer.writeByte((byte) (val & 0xff));
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
		buffer.addCapacity(CodecConstant.BYTE_LEN);
		buffer.writeByte(val ? (byte) 1 : (byte) 0);
	}

	@Override
	public void writeChar(char val) {
		buffer.addCapacity(CodecConstant.SHORT_LEN);
		buffer.writeByte((byte) ((val >> 8) & 0xff));
		buffer.writeByte((byte) (val & 0xff));
	}

	@Override
	public void writeString(String val) {
		if (val == null || val.isEmpty()) {
			this.writeInt(CodecConstant.LEN_EMPTY);
			return;
		}
		byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
		buffer.addCapacity(CodecConstant.INT_LEN + bytes.length);
		this.writeInt(bytes.length);
		this.writeBytes(bytes);
	}

	@Override
	public void writeDate(Date val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
			return;
		}

		this.writeLong(val.getTime());
	}

	@Override
	public void writeLocalDateTime(LocalDateTime val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
			return;
		}
		var instant = val.atZone(ZoneId.systemDefault()).toInstant();
		this.writeLong(instant.toEpochMilli());
	}

	@Override
	public void writeInstant(Instant val) {
		if (val == null) {
			this.writeLong(CodecConstant.LEN_EMPTY);
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
		var array = buffer.getByteArray();
		if (array == null || array.length == 0) {
			return new byte[CodecConstant.ZERO_IDX];
		}

		byte[] newBuf = new byte[buffer.getSize()];
		System.arraycopy(array, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, buffer.getSize());
		return newBuf;
	}

}
