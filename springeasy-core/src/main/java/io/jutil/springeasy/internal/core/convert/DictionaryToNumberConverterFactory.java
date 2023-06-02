package io.jutil.springeasy.internal.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.NumberUtils;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class DictionaryToNumberConverterFactory implements ConverterFactory<Dictionary, Number> {

    @Override
    public <T extends Number> Converter<Dictionary, T> getConverter(Class<T> targetType) {
        return new DictionaryToNumber<>(targetType);
    }

    private class DictionaryToNumber<T extends Number> implements Converter<Dictionary, T> {
        private final Class<T> numType;

        private DictionaryToNumber(Class<T> numType) {
            this.numType = numType;
        }

        @Override
        public T convert(Dictionary source) {
            int num = source.getIndex();
            return NumberUtils.convertNumberToTargetClass(num, numType);
        }
    }
}
