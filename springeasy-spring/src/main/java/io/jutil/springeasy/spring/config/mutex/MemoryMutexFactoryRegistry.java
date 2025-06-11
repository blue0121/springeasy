package io.jutil.springeasy.spring.config.mutex;

import io.jutil.springeasy.core.mutex.memory.MemoryMutexFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author Jin Zheng
 * @since 2025-05-30
 */
public class MemoryMutexFactoryRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
	private MutexProperties prop;

	private void registryMutexFactory(BeanDefinitionRegistry registry,
	                                  MutexProperties.MutexConfigProperties config) {
		RootBeanDefinition definition = null;
		switch (config.getMutexType()) {
			case MEMORY:
				definition = new RootBeanDefinition(MemoryMutexFactory.class);
				break;
		}

		if (definition != null) {
			registry.registerBeanDefinition(config.getId(), definition);
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		if (prop.getConfigs() == null || prop.getConfigs().isEmpty()) {
			return;
		}

		for (var config : prop.getConfigs()) {
			if (!config.isEnabled()) {
				continue;
			}

			this.registryMutexFactory(registry, config);
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		var result = Binder.get(environment).bind("springeasy.mutex", MutexProperties.class);
		this.prop = result.get();
		this.prop.check();
	}
}
