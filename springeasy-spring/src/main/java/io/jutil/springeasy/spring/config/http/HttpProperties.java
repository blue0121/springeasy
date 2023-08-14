package io.jutil.springeasy.spring.config.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-01-31
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.http")
public class HttpProperties {

    private List<HttpConfigProperties> configs;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HttpConfigProperties {
        private String id;
        private boolean async = false;
        private String baseUrl;
        private List<HttpHeaderProperties> headers;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HttpHeaderProperties {
        private String name;
        private String value;
    }
}
