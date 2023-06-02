package io.jutil.springeasy.internal.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.DictionaryCache;
import io.jutil.springeasy.core.util.NumberUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class StringToDictionaryConverterFactory implements ConverterFactory<String, Dictionary> {

    @Override
    public <T extends Dictionary> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToDictionary(targetType);
    }

    private class StringToDictionary<T extends Dictionary> implements Converter<String, T> {
        private static final DictionaryCache CACHE = DictionaryCache.getInstance();
        private final Class<T> dictType;

        public StringToDictionary(Class<T> dictType) {
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
                return CACHE.getFromIndex(dictType, index);
            }
            else {
                var dict = CACHE.getFromName(dictType, str);
                if (dict != null) {
                    return dict;
                }
                return CACHE.getFromLabel(dictType, str);
            }
        }
    }
}
