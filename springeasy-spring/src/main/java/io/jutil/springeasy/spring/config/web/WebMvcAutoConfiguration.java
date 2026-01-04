package io.jutil.springeasy.spring.config.web;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.core.io.scan.ResourceScannerFacade;
import io.jutil.springeasy.spring.exception.ErrorCodeExceptionHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@Configuration
@Import(ErrorCodeExceptionHandler.class)
@EnableConfigurationProperties(WebProperties.class)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {
	private final WebProperties prop;

	public WebMvcAutoConfiguration(WebProperties prop) {
		this.prop = prop;
	}

	@PostConstruct
	public void init() {
		var pkgList = prop.getDictScanPackages();
		if (pkgList != null && !pkgList.isEmpty()) {
			var fileHandler = new DictFileHandler();
			for (var pkg : pkgList) {
				ResourceScannerFacade.scanPackage(pkg, fileHandler);
			}
		}
	}

	@Override
	public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
		var converter = this.getConvertor();
		builder.withJsonConverter(converter);
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
