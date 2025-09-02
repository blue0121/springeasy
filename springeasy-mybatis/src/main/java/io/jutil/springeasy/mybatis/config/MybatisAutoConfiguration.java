package io.jutil.springeasy.mybatis.config;

import io.jutil.springeasy.mybatis.handler.FastjsonArrayTypeHandler;
import io.jutil.springeasy.mybatis.handler.FastjsonObjectTypeHandler;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Jin Zheng
 * @since 2025-08-31
 */
@Configuration
public class MybatisAutoConfiguration {

	@Bean
	public ConfigurationCustomizer configurationCustomizer(DataSource dataSource) {
		var databaseInfo = MetadataUtil.getDatabaseInfo(dataSource);
		return config -> {
			var registry = config.getTypeHandlerRegistry();
			registry.register(new FastjsonObjectTypeHandler(databaseInfo));
			registry.register(new FastjsonArrayTypeHandler(databaseInfo));
		};
	}

}
