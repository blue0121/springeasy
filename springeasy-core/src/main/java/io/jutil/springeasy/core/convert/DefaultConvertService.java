package io.jutil.springeasy.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class DefaultConvertService implements ConvertService {
	private final DefaultConversionService conversionService;

	public DefaultConvertService() {
		conversionService = (DefaultConversionService) DefaultConversionService.getSharedInstance();
        conversionService.addConverterFactory(new LocalDateToDateConverterFactory());
		conversionService.addConverterFactory(new StringToDateConverterFactory());
        conversionService.addConverterFactory(new StringToLocalDateConverterFactory());
        conversionService.addConverterFactory(new DateToLocalDateConverterFactory());
	}

	@Override
	public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
		return conversionService.canConvert(sourceType, targetType);
	}

	@Override
	public <T> T convert(Object source, Class<T> targetType) {
		return conversionService.convert(source, targetType);
	}

	public void addConverter(GenericConverter converter) {
		conversionService.addConverter(converter);
	}

	public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType,
	                                Converter<? super S, ? extends T> converter) {
		conversionService.addConverter(sourceType, targetType, converter);
	}

	public void addConverterFactory(ConverterFactory<?, ?> factory) {
		conversionService.addConverterFactory(factory);
	}

}
