package io.jutil.springeasy.core.codec;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
public interface Decoder {

	default void resetReadIndex() {
		this.resetReadIndex(0);
	}

	void resetReadIndex(int offset);

	void skipReadIndex(int offset);

	int readUInt();

	long readULong();

	int readSInt();

	long readSLong();

	int readInt();

	long readLong();


	byte readByte();

	int readBytes(byte[] buf);

	short readShort();

	float readFloat();

	double readDouble();

	boolean readBoolean();

	char readChar();

	String readString();

	Date readDate();

	LocalDateTime readLocalDateTime();

	Instant readInstant();

	int getReadIndex();

	int getSize();

	int getCapacity();
}
