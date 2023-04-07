package io.jutil.springeasy.core.codec;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
public interface ExternalSerializable {

	void encode(Encoder encoder);

	void decode(Decoder decoder);
}
