package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class NumberToDictionaryConverterTest extends ConverterTest {

	@Test
	void testIntToDictionary() {
		this.verifyConvert(Integer.class, Dictionary.class);
		var status = service.convert(0, Status.class);
		Assertions.assertEquals(Status.ACTIVE, status);
	}

	@Test
	void testLongToDictionary() {
		this.verifyConvert(Long.class, Dictionary.class);
		var status = service.convert(1, Status.class);
		Assertions.assertEquals(Status.INACTIVE, status);
	}
}
