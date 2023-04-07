package io.jutil.springeasy.core.codec;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
@Getter
@Setter
public class StringTypeObject implements ExternalSerializable {
	private String valStr;

	@Override
	public void encode(Encoder encoder) {
		encoder.writeString(valStr);
	}

	@Override
	public void decode(Decoder decoder) {
		this.valStr = decoder.readString();
	}
}
