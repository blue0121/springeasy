package io.jutil.springeasy.spring.convert;

import io.jutil.springeasy.core.dict.Dictionary;
import io.jutil.springeasy.core.dict.DictionaryCache;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class NumberToDictionaryConverterFactory  implements ConverterFactory<Number, Dictionary> {

    @Override
    public <T extends Dictionary> Converter<Number, T> getConverter(Class<T> targetType) {
        if (!Dictionary.class.isAssignableFrom(targetType)) {
            throw new IllegalArgumentException("目标类型不是字典类型：" + targetType.getName());
        }

        return new NumberToDictionaryConverter<>(targetType);
    }

    private class NumberToDictionaryConverter<T extends Dictionary> implements Converter<Number, T> {
        private final Class<T> dictType;
        private final DictionaryCache cache = DictionaryCache.getInstance();

        public NumberToDictionaryConverter(Class<T> dictType) {
            this.dictType = dictType;
        }

        @Override
        public T convert(Number source) {
            return cache.getFromIndex(dictType, source.intValue());
        }
    }
}
