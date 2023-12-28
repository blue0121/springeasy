package io.jutil.springeasy.spring.config.web;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import io.jutil.springeasy.spring.exception.ErrorCodeExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@Configuration
@Import(ErrorCodeExceptionHandler.class)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		var converter = this.getConvertor();
		converters.add(0, converter);
	}

	private FastJsonHttpMessageConverter getConvertor() {
		var converter = new FastJsonHttpMessageConverter();
		converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));

		var config = new FastJsonConfig();
		config.setWriterFeatures(JSONWriter.Feature.WriteEnumUsingOrdinal,
				JSONWriter.Feature.WriteLongAsString);
		converter.setFastJsonConfig(config);
		return converter;
	}
}
