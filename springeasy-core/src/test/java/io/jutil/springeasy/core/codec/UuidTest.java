package io.jutil.springeasy.core.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-05-10
 */
class UuidTest {

	@Test
	void test() {
		var uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		var bytes = Uuid.decode(uuid);
		Assertions.assertEquals(16, bytes.length);

		var newUuid = Uuid.encode(bytes);
		Assertions.assertEquals(uuid, newUuid);
	}
}
