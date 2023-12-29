package io.jutil.springeasy.core.io;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * @author Jin Zheng
 * @since 2023-12-03
 */
public class BacklogHighlight extends ForegroundCompositeConverterBase<ILoggingEvent> {
	@Override
	protected String getForegroundColorCode(ILoggingEvent event) {
		return switch (event.getLevel().toInt()) {
			case Level.INFO_INT -> ANSIConstants.BOLD + ANSIConstants.CYAN_FG;
			case Level.WARN_INT -> ANSIConstants.BOLD + ANSIConstants.MAGENTA_FG;
			case Level.ERROR_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
			default -> ANSIConstants.DEFAULT_FG;
		};
	}
}
