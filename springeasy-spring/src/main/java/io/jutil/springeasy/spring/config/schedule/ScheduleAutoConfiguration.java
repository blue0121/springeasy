package io.jutil.springeasy.spring.config.schedule;

import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.spring.schedule.CronScheduleJobFactoryBean;
import io.jutil.springeasy.spring.schedule.SpringScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2024-03-01
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ScheduleProperties.class)
@ConditionalOnProperty(prefix = "springeasy.schedule", name = "enabled", havingValue = "true")
@SuppressWarnings("java:S6813")
public class ScheduleAutoConfiguration implements BeanFactoryAware {
	private BeanFactory beanFactory;

	private Set<String> getJobIdSet(ScheduleProperties prop) {
		if (prop.getConfigs() == null || prop.getConfigs().isEmpty()) {
			return Set.of();
		}

		Set<String> idSet = new HashSet<>();
		for (var config : prop.getConfigs()) {
			idSet.add(config.getId());
		}

		return Set.copyOf(idSet);
	}

	@Bean
	public CronScheduleJobFactoryBean cronScheduleJob(ScheduleProperties prop,
	                                                  List<SpringScheduleJob> jobList) {
		prop.check();
		var mutexFactory = beanFactory.getBean(prop.getMutex(), MutexFactory.class);
		log.info("MutexFactory is: {}", mutexFactory.getClass().getSimpleName());
		return new CronScheduleJobFactoryBean(prop, mutexFactory, jobList);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
