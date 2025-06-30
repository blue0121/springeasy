package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.spring.config.mutex.MutexProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
public class RedisMutexFactoryRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
	private MutexProperties prop;

	private void registryMutexFactory(BeanDefinitionRegistry registry,
	                                  MutexProperties.MutexConfigProperties config) {
		RootBeanDefinition definition = null;
		switch (config.getMutexType()) {
			case REDIS:
				definition = new RootBeanDefinition(RedisMutexFactory.class);
				var values = definition.getConstructorArgumentValues();
				values.addIndexedArgumentValue(0, new RuntimeBeanReference("stringRedisTemplate"));
				values.addIndexedArgumentValue(1, config.getKey());
				values.addIndexedArgumentValue(2, config.getKeepAliveSec());
				values.addIndexedArgumentValue(3, config.getExpireSec());
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
