package io.jutil.springeasy.spring.config;

import io.jutil.springeasy.internal.core.convert.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterRegistry;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@Slf4j
@Configuration
public class ConverterAutoConfiguration implements InitializingBean {
	@Autowired
	private ConverterRegistry registry;

	@Override
	public void afterPropertiesSet() throws Exception {
		registry.addConverterFactory(new LocalDateToDateConverterFactory());
		registry.addConverterFactory(new StringToDateConverterFactory());
		registry.addConverterFactory(new StringToLocalDateConverterFactory());
		registry.addConverterFactory(new DateToLocalDateConverterFactory());

		registry.addConverter(new DictionaryToStringConverter());
		registry.addConverterFactory(new StringToDictionaryConverterFactory());
		registry.addConverterFactory(new NumberToDictionaryConverterFactory());
		registry.addConverterFactory(new DictionaryToNumberConverterFactory());
	}
}
