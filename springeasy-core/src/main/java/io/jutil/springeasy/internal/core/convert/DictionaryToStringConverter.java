package io.jutil.springeasy.internal.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class DictionaryToStringConverter implements Converter<Dictionary, String> {

    @Override
    public String convert(Dictionary source) {
        if (source == null) {
            return null;
        }
        return source.getName();
    }
}
