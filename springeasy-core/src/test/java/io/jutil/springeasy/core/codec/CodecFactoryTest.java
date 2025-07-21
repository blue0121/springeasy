package io.jutil.springeasy.core.codec;

import io.jutil.springeasy.core.codec.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
@Slf4j
class CodecFactoryTest {

	@Test
	void testPrimitiveType() {
		var obj1 = new PrimitiveTypeObject();
		obj1.setValByte((byte) 0x01);
		obj1.setValShort((short) 0x0100);
		obj1.setValInt(0x01000000);
		obj1.setValLong(0x0100000000000000L);
		obj1.setValFloat(1.1f);
		obj1.setValDouble(100.001);
		obj1.setValBool(true);
		obj1.setValChar('z');
		obj1.setUInt(127);
		obj1.setULong(127);
		obj1.setSInt(-127);
		obj1.setSLong(-127);

		var base64 = CodecFactory.encodeBase64(obj1);
		log.info("Byte, len: {}, text: {}", base64.length(), base64);
		var json = Json.output(obj1);
		log.info("Byte, len: {}, text: {}", json.length(), json);
		var obj2 = new PrimitiveTypeObject();
		CodecFactory.decode(obj2, base64);

		Assertions.assertEquals(obj1.getValByte(), obj2.getValByte());
		Assertions.assertEquals(obj1.getValShort(), obj2.getValShort());
		Assertions.assertEquals(obj1.getValInt(), obj2.getValInt());
		Assertions.assertEquals(obj1.getValLong(), obj2.getValLong());
		Assertions.assertEquals(obj1.getValFloat(), obj2.getValFloat());
		Assertions.assertEquals(obj1.getValDouble(), obj2.getValDouble());
		Assertions.assertEquals(obj1.isValBool(), obj2.isValBool());
		Assertions.assertEquals(obj1.getValChar(), obj2.getValChar());
		Assertions.assertEquals(obj1.getUInt(), obj2.getUInt());
		Assertions.assertEquals(obj1.getSInt(), obj2.getSInt());
		Assertions.assertEquals(obj1.getSInt(), obj2.getSInt());
		Assertions.assertEquals(obj1.getSLong(), obj2.getSLong());
	}

	@ParameterizedTest
	@CsvSource({"blue", "<NULL>", "<EMPTY>"})
	void testStringType(String val) {
		if (val.equals("<NULL>")) {
			val = null;
		} else if (val.equals("<EMPTY>")) {
			val = "";
		}
		var obj1 = new StringTypeObject();
		obj1.setValStr(val);

		var base64 = CodecFactory.encodeBase64(obj1);
		log.info("Byte, len: {}, text: {}", base64.length(), base64);
		var json = Json.output(obj1);
		log.info("Byte, len: {}, text: {}", json.length(), json);
		var obj2 = new StringTypeObject();
		CodecFactory.decode(obj2, base64);

		if (val != null) {
			Assertions.assertEquals(obj1.getValStr(), obj2.getValStr());
		} else {
			Assertions.assertEquals("", obj2.getValStr());
		}
	}

	@Test
	void testDateType1() {
		var obj1 = new DateTypeObject();
		obj1.setValDate(new Date());
		obj1.setValLocalDateTime(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
		obj1.setValInstant(Instant.now().plus(1, ChronoUnit.DAYS));

		var base64 = CodecFactory.encodeBase64(obj1);
		log.info("Byte, len: {}, text: {}", base64.length(), base64);
		var json = Json.output(obj1);
		log.info("Byte, len: {}, text: {}", json.length(), json);
		var obj2 = new DateTypeObject();
		CodecFactory.decode(obj2, base64);

		Assertions.assertEquals(obj1.getValDate(), obj2.getValDate());
		Assertions.assertEquals(obj1.getValLocalDateTime().truncatedTo(ChronoUnit.MILLIS), obj2.getValLocalDateTime());
		Assertions.assertEquals(obj1.getValInstant().truncatedTo(ChronoUnit.MILLIS), obj2.getValInstant());
	}

	@Test
	void testDateType2() {
		var obj1 = new DateTypeObject();

		var base64 = CodecFactory.encodeBase64(obj1);
		log.info("Byte, len: {}, text: {}", base64.length(), base64);
		var json = Json.output(obj1);
		log.info("Byte, len: {}, text: {}", json.length(), json);
		var obj2 = new DateTypeObject();
		CodecFactory.decode(obj2, base64);

		Assertions.assertNull(obj2.getValDate());
		Assertions.assertNull(obj2.getValLocalDateTime());
		Assertions.assertNull(obj2.getValInstant());
	}
}
