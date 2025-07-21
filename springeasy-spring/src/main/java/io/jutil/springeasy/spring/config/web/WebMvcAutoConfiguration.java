package io.jutil.springeasy.spring.config.web;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.spring.exception.ErrorCodeExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@Configuration
@Import(ErrorCodeExceptionHandler.class)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

	@Bean
	public static MethodValidationPostProcessor validationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		var converter = this.getConvertor();
		converters.addFirst(converter);
	}

	private FastJsonHttpMessageConverter getConvertor() {
		var converter = new FastJsonHttpMessageConverter();
		converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));

		var config = new FastJsonConfig();
		config.setWriterFeatures(Json.OUTPUT);
		converter.setFastJsonConfig(config);
		return converter;
	}
}
