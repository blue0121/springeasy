package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.cache.Singleton;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class ConvertServiceFactory {
    private ConvertServiceFactory() {
    }

    public static ConvertService getConvertService() {
        return Singleton.get(ConvertService.class, k -> new DefaultConvertService());
    }
}
