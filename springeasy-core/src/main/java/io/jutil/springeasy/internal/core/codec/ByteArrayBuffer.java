package io.jutil.springeasy.internal.core.codec;

import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
@Slf4j
public class ByteArrayBuffer {
	private int writeIndex = 0;
	private int readIndex = 0;

	private int size = 0;
	private int capacity = 0;

	private byte[] buffer;

	public ByteArrayBuffer() {
	}

	public void writeByte(byte val) {
		this.buffer[writeIndex++] = val;
		this.size += 1;
	}

	public void writeBytes(byte[] val) {
		System.arraycopy(val, CodecConstant.ZERO_IDX, buffer, writeIndex, val.length);
		this.writeIndex += val.length;
		this.size += val.length;
	}

	public byte readByte() {
		return buffer[readIndex++];
	}

	public int readBytes(byte[] buf) {
		AssertUtil.notEmpty(buf, "Buf");
		int read = Math.min(buf.length, size - readIndex);
		if (read > CodecConstant.ZERO_IDX) {
			System.arraycopy(buffer, readIndex, buf, CodecConstant.ZERO_IDX, read);
			this.readIndex += read;
		}
		return read;
	}

	public void addCapacity(int size) {
		AssertUtil.positive(size, "Size");
		var remain = capacity - writeIndex;
		log.debug(">> capacity: {}, size: {}, remain: {}", capacity, size, remain);
		if (remain >= size) {
			return;
		}

		int times = (capacity + size + CodecConstant.INC_CAP - 1) >> CodecConstant.INC_LEFT_SHIFT;
		this.capacity = times << CodecConstant.INC_LEFT_SHIFT;
		log.debug(">>>> capacity: {}, times: {}", capacity, times);
		byte[] newBuf = new byte[this.capacity];
		if (buffer != null) {
			System.arraycopy(buffer, CodecConstant.ZERO_IDX, newBuf, CodecConstant.ZERO_IDX, buffer.length);
		}
		this.buffer = newBuf;
	}

	public void checkReadIndex(int offset) {
		AssertUtil.positive(offset, "Offset");
		if (readIndex + offset > size) {
			throw new IndexOutOfBoundsException("缓冲区溢出");
		}
	}

	public void resetWriteIndex(int offset) {
		if (offset == CodecConstant.ZERO_IDX) {
			this.writeIndex = CodecConstant.ZERO_IDX;
			return;
		}

		if (offset < CodecConstant.ZERO_IDX || offset > capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + capacity);
		}

		this.writeIndex = offset;
	}

	public void skipWriteIndex(int offset) {
		if (writeIndex + offset < CodecConstant.ZERO_IDX || writeIndex + offset > capacity) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于等于" + (capacity - writeIndex));
		}

		this.writeIndex += offset;
	}

	public void resetReadIndex(int offset) {
		if (offset == CodecConstant.ZERO_IDX) {
			this.readIndex = CodecConstant.ZERO_IDX;
			return;
		}

		if (offset < CodecConstant.ZERO_IDX || offset > size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于" + size);
		}
		this.readIndex = offset;
	}

	public void skipReadIndex(int offset) {
		if (readIndex + offset < CodecConstant.ZERO_IDX || readIndex + offset > size) {
			throw new IndexOutOfBoundsException("偏移量不能小于0或大于" + (size - readIndex));
		}
		this.readIndex += offset;
	}

	public int getSize() {
		return this.size;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getRemain() {
		return this.capacity - this.writeIndex;
	}

	public int getReadIndex() {
		return this.readIndex;
	}

	public int getWriteIndex() {
		return this.writeIndex;
	}

	byte[] getByteArray() {
		return this.buffer;
	}
}
