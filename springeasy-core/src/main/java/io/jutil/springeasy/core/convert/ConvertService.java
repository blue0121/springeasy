package io.jutil.springeasy.core.convert;

/**
 * @author Jin Zheng
 * @since 2023-05-31
 */
public interface ConvertService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);

}
