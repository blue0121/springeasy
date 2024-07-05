package io.jutil.springeasy.spring.config.node;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2024-05-28
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(NodeProperties.class)
public class NodeAutoConfiguration implements InitializingBean {
	@Autowired
	@SuppressWarnings("java:S6813")
	NodeProperties prop;

	@Override
	public void afterPropertiesSet() {
		prop.check();
	}
}
