package io.jutil.springeasy.spring.config.schedule;

import io.jutil.springeasy.spring.schedule.ExecutorServiceFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
public class ExecutorServiceRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
	private ExecutorProperties prop;

	private void registryExecutorService(BeanDefinitionRegistry registry,
	                                     ExecutorProperties.ExecutorConfigProperties config) {
		var definition = new RootBeanDefinition(ExecutorServiceFactoryBean.class);
		var propertyValues = definition.getPropertyValues();
		propertyValues.addPropertyValue("id", config.getId());
		propertyValues.addPropertyValue("type", config.getType());
		propertyValues.addPropertyValue("queueCapacity", config.getQueueCapacity());
		propertyValues.addPropertyValue("coreSize", config.getCoreSize());
		propertyValues.addPropertyValue("maxSize", config.getMaxSize());
		registry.registerBeanDefinition(config.getId(), definition);
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		if (prop.getConfigs() == null || prop.getConfigs().isEmpty()) {
			return;
		}

		for (var config : prop.getConfigs()) {
			if (!config.isEnabled()) {
				continue;
			}

			this.registryExecutorService(registry, config);
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		var result = Binder.get(environment).bind("springeasy.executor", ExecutorProperties.class);
		this.prop = result.get();
		this.prop.check();
	}
}
