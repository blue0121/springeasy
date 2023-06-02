package io.jutil.springeasy.internal.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.DictionaryCache;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class NumberToDictionaryConverterFactory implements ConverterFactory<Number, Dictionary> {

    @Override
    public <T extends Dictionary> Converter<Number, T> getConverter(Class<T> targetType) {
        return new NumberToDictionary<>(targetType);
    }

    private class NumberToDictionary<T extends Dictionary> implements Converter<Number, T> {
        private final Class<T> dictType;

        public NumberToDictionary(Class<T> dictType) {
            this.dictType = dictType;
        }

        @Override
        public T convert(Number source) {
            return DictionaryCache.getInstance().getFromIndex(dictType, source.intValue());
        }
    }
}
