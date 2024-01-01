package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.util.AssertUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Jin Zheng
 * @since 2024-01-01
 */
@Getter
public class ByteArrayBuffer {
	private int writeIndex = 0;
	private int readIndex = 0;

	private int size = 0;
	private int capacity = 0;

	private byte[] buffer;

	public ByteArrayBuffer() {
		this(Const.INC_CAP);
	}

	public ByteArrayBuffer(int capacity) {
		AssertUtil.positive(capacity, "Capacity");
		this.buffer = new byte[capacity];
		this.capacity = capacity;
	}

	public ByteArrayBuffer(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buffer");
		this.buffer = buf;
		this.size = buf.length;
		this.capacity = buf.length;
	}

	public void ensureCapacity(int growth) {
		var remain = this.getRemain();
		if (remain >= growth) {
			return;
		}

		int times = (capacity + growth + Const.INC_CAP - 1 - remain) >> Const.INC_LEFT_SHIFT;
		this.capacity = times << Const.INC_LEFT_SHIFT;
		this.buffer = Arrays.copyOf(buffer, capacity);
	}

	public void writeByte(byte val) {
		this.buffer[writeIndex++] = val;
		this.size += 1;
	}

	public void writeBytes(byte[] val) {
		System.arraycopy(val, Const.ZERO_IDX, buffer, writeIndex, val.length);
		this.writeIndex += val.length;
		this.size += val.length;
	}

	public byte readByte() {
		return buffer[readIndex++];
	}

	public int readBytes(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buf");
		int read = Math.min(buf.length, size - readIndex);
		if (read > Const.ZERO_IDX) {
			System.arraycopy(buffer, readIndex, buf, Const.ZERO_IDX, read);
			this.readIndex += read;
		}
		return read;
	}

	public void checkReadIndex(int offset) {
		AssertUtil.positive(offset, "Offset");
		if (readIndex + offset > size) {
			throw new IndexOutOfBoundsException("缓冲区溢出");
		}
	}

	public void resetWriteIndex(int offset) {
		if (offset == Const.ZERO_IDX) {
			this.writeIndex = Const.ZERO_IDX;
			return;
		}

		if (offset < Const.ZERO_IDX || offset > capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + capacity);
		}

		this.writeIndex = offset;
	}

	public void skipWriteIndex(int offset) {
		if (writeIndex + offset < Const.ZERO_IDX || writeIndex + offset > capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + (capacity - writeIndex));
		}

		this.writeIndex += offset;
	}

	public void resetReadIndex(int offset) {
		if (offset == Const.ZERO_IDX) {
			this.readIndex = Const.ZERO_IDX;
			return;
		}

		if (offset < Const.ZERO_IDX || offset > size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于" + size);
		}
		this.readIndex = offset;
	}

	public void skipReadIndex(int offset) {
		if (readIndex + offset < Const.ZERO_IDX || readIndex + offset > size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于" + (size - readIndex));
		}
		this.readIndex += offset;
	}

	public int getRemain() {
		return this.capacity - this.writeIndex;
	}

	public byte[] toByteArray() {
		return Arrays.copyOf(this.buffer, this.size);
	}
}
