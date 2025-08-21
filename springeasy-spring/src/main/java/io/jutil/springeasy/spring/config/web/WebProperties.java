package io.jutil.springeasy.spring.config.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-08-17
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.web")
public class WebProperties {
	private List<String> dictScanPackages;
}
