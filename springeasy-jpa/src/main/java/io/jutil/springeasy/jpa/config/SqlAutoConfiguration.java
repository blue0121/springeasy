package io.jutil.springeasy.jpa.config;

import io.jutil.springeasy.jpa.sql.SqlSnippetFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
@Slf4j
@Configuration
@ConditionalOnClass(JdbcTemplate.class)
public class SqlAutoConfiguration {

    @SuppressWarnings("java:S2440")
    @Bean
    public SqlSnippetFacade sqlSnippetFacade(DataSourceProperties prop) {
        log.info("Initialize SqlSnippetFacade, jdbc url: {}", prop.getUrl());
        return new SqlSnippetFacade(prop);
    }
}
