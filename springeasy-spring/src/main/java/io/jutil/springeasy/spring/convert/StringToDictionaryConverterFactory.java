package io.jutil.springeasy.spring.convert;

import io.jutil.springeasy.core.dict.Dictionary;
import io.jutil.springeasy.core.dict.DictionaryCache;
import io.jutil.springeasy.core.util.NumberUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class StringToDictionaryConverterFactory implements ConverterFactory<String, Dictionary> {

    @Override
    public <T extends Dictionary> Converter<String, T> getConverter(Class<T> targetType) {
        if (!Dictionary.class.isAssignableFrom(targetType)) {
            throw new IllegalArgumentException("目标类型不是字典类型：" + targetType.getName());
        }

        return new StringToDictionaryConverter<>(targetType);
    }

    private class StringToDictionaryConverter<T extends Dictionary> implements Converter<String, T> {
        private final Class<T> dictType;
        private final DictionaryCache cache = DictionaryCache.getInstance();

        public StringToDictionaryConverter(Class<T> dictType) {
            this.dictType = dictType;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            String str = source.trim();
            if (str.isEmpty()) {
                return null;
            }

            if (NumberUtil.isInteger(str)) {
                Integer index = Integer.valueOf(str);
                return cache.getFromIndex(dictType, index);
            }
            else {
                return cache.getFromName(dictType, str);
            }
        }
    }
}
