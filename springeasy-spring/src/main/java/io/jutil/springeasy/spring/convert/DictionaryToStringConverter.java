package io.jutil.springeasy.spring.convert;

import io.jutil.springeasy.core.dict.Dictionary;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class DictionaryToStringConverter implements Converter<Dictionary, String> {

	@Override
	public String convert(Dictionary source) {
		return source.getName();
	}
}
