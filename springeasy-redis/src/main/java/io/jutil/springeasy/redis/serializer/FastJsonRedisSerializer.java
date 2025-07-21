package io.jutil.springeasy.redis.serializer;

import io.jutil.springeasy.core.codec.json.Json;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Jin Zheng
 * @since 2025-05-05
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
	@Override
	public byte[] serialize(T value) throws SerializationException {
		if (value == null) {
			return new byte[0];
		}
		try {
			return Json.toBytes(value);
		} catch (Exception e) {
			throw new SerializationException("无法序列化: " + e.getMessage(), e);
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			return Json.fromBytes(bytes);
		} catch (Exception e) {
			throw new SerializationException("无法反序列化: " + e.getMessage(), e);
		}
	}
}
