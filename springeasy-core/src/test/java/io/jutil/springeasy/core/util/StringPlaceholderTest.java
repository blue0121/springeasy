package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
class StringPlaceholderTest {
	@Test
	void testTemplate1() {
		var holder = new StringPlaceholder("{", "}", ":");
		var tpl = "{one}-{two}={one}";
		var param = Map.of("one", "2","two", "1");
		var content = holder.template(tpl, param);
		Assertions.assertEquals("2-1=2", content);
	}

	@Test
	void testTemplate2() {
		var holder = new StringPlaceholder("${", "}", ":");
		var tpl = "${p1:v}-${p2:c}=${p3}";
		var param = Map.of("p1", "a", "p3", "d");
		var content = holder.template(tpl, param);
		Assertions.assertEquals("a-c=d", content);
	}

	@Test
	void testTemplate3() {
		var holder = new StringPlaceholder("${", "}", ":");
		var tpl = "${p1:v}-${p2:c}=${p3}";
		var param = Map.of("p1", "a");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> holder.template(tpl, param));
	}
}
