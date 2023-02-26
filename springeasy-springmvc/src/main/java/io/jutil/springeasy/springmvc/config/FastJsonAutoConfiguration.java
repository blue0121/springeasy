package io.jutil.springeasy.springmvc.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Configuration
public class FastJsonAutoConfiguration implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		var converter = this.getConvertor();
		converters.add(0, converter);
	}

	private FastJsonHttpMessageConverter getConvertor() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
		FastJsonConfig config = new FastJsonConfig();
		config.setWriterFeatures(JSONWriter.Feature.WriteEnumUsingToString);
		converter.setFastJsonConfig(config);
		return converter;
	}
}
