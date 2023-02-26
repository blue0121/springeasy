package io.jutil.springeasy.spring.config;

import io.jutil.springeasy.spring.convert.DictionaryToNumberConverterFactory;
import io.jutil.springeasy.spring.convert.DictionaryToStringConverter;
import io.jutil.springeasy.spring.convert.NumberToDictionaryConverterFactory;
import io.jutil.springeasy.spring.convert.StringToDictionaryConverterFactory;
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
		registry.addConverter(new DictionaryToStringConverter());

		registry.addConverterFactory(new StringToDictionaryConverterFactory());
		registry.addConverterFactory(new DictionaryToNumberConverterFactory());
		registry.addConverterFactory(new NumberToDictionaryConverterFactory());

		log.info("Converter register: DictionaryConverter");
	}
}
