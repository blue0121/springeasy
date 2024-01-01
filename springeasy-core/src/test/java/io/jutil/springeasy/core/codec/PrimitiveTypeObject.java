package io.jutil.springeasy.core.codec;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
@Getter
@Setter
@NoArgsConstructor
public class PrimitiveTypeObject implements ExternalSerializable {
	private byte valByte;
	private short valShort;
	private int valInt;
	private long valLong;
	private float valFloat;
	private double valDouble;
	private boolean valBool;
	private char valChar;

	private int uInt;
	private long uLong;
	private int sInt;
	private long sLong;

	@Override
	public void encode(Encoder encoder) {
		encoder.writeByte(valByte);
		encoder.writeShort(valShort);
		encoder.writeInt(valInt);
		encoder.writeLong(valLong);
		encoder.writeFloat(valFloat);
		encoder.writeDouble(valDouble);
		encoder.writeBoolean(valBool);
		encoder.writeChar(valChar);

		encoder.writeUInt(uInt);
		encoder.writeULong(uLong);
		encoder.writeSInt(sInt);
		encoder.writeSLong(sLong);
	}

	@Override
	public void decode(Decoder decoder) {
		this.valByte = decoder.readByte();
		this.valShort = decoder.readShort();
		this.valInt = decoder.readInt();
		this.valLong = decoder.readLong();
		this.valFloat = decoder.readFloat();
		this.valDouble = decoder.readDouble();
		this.valBool = decoder.readBoolean();
		this.valChar = decoder.readChar();

		this.uInt = decoder.readUInt();
		this.uLong = decoder.readULong();
		this.sInt = decoder.readSInt();
		this.sLong = decoder.readSLong();
	}
}
