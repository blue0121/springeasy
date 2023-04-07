package io.jutil.springeasy.core.codec;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
public interface Encoder {

	default void resetWriteIndex() {
		this.resetWriteIndex(0);
	}

	void resetWriteIndex(int offset);

	void skipWriteIndex(int offset);

	void writeByte(byte val);

	void writeBytes(byte[] val);

	void writeShort(short val);

	void writeInt(int val);

	void writeLong(long val);

	void writeFloat(float val);

	void writeDouble(double val);

	void writeBoolean(boolean val);

	void writeChar(char val);

	void writeString(String val);

	void writeDate(Date val);

	void writeLocalDateTime(LocalDateTime val);

	void writeInstant(Instant val);

	int getWriteIndex();

	int getCapacity();
}
