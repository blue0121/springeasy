package io.jutil.springeasy.core.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-12-03
 */
@Slf4j
class BacklogHighlightTest {

	@Test
	void testColorLogger() {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
	}
}
