package io.jutil.springeasy.spring.bean;

import io.jutil.springeasy.spring.config.HttpProperties;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2022-12-26
 */
@NoArgsConstructor
public class HttpTemplateRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private HttpProperties httpProperties;


    private HttpClient initHttpClient() {
        return HttpClient.newHttpClient();
    }

    private void registryHttpClient(BeanDefinitionRegistry registry, HttpClient httpClient,
                                    HttpProperties.HttpConfigProperties config) {
        Map<String, String> headerMap = new HashMap<>();
        if (config.getHeaders() != null && !config.getHeaders().isEmpty()) {
            for (var header : config.getHeaders()) {
                headerMap.put(header.getName(), header.getValue());
            }
        }
        var clazz = config.isAsync() ? AsyncHttpTemplateFactoryBean.class
                : HttpTemplateFactoryBean.class;

        var definition = new RootBeanDefinition(clazz);
        var propertyValues = definition.getPropertyValues();
        propertyValues.addPropertyValue("defaultHeaders", headerMap);
        propertyValues.addPropertyValue("id", config.getId());
        propertyValues.addPropertyValue("baseUrl", config.getBaseUrl());
        propertyValues.addPropertyValue("httpClient", httpClient);
        registry.registerBeanDefinition(config.getId(), definition);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (httpProperties.getConfigs() == null || httpProperties.getConfigs().isEmpty()) {
            return;
        }

        var httpClient = this.initHttpClient();
        for (var config : httpProperties.getConfigs()) {
            this.registryHttpClient(registry, httpClient, config);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // NOSONAR
    }

    @Override
    public void setEnvironment(Environment environment) {
        var result = Binder.get(environment).bind("springeasy.http", HttpProperties.class);
        this.httpProperties = result.get();
    }
}
