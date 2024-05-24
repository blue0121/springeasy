package io.jutil.springeasy.spring.config.schedule;

import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.core.schedule.db.DbMutexFactory;
import io.jutil.springeasy.core.schedule.memory.MemoryMutexFactory;
import io.jutil.springeasy.spring.schedule.CronScheduleJobFactoryBean;
import io.jutil.springeasy.spring.schedule.SpringScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
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
public class ScheduleAutoConfiguration {
	@Autowired(required = false)
	DataSource dataSource;

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
	                                                  List<MutexFactory> mutexFactoryList,
	                                                  List<SpringScheduleJob> jobList) {
		prop.check();
		var mutexFactory = this.getMutexFactory(prop, mutexFactoryList);
		log.info("MutexFactory is: {}", mutexFactory.getClass().getSimpleName());
		return new CronScheduleJobFactoryBean(prop, mutexFactory, jobList);
	}

	private MutexFactory getMutexFactory(ScheduleProperties prop,
	                                     List<MutexFactory> mutexFactoryList) {
		MutexFactory mutexFactory = null;
		for (var factory : mutexFactoryList) {
			if (prop.getMutex().equalsIgnoreCase(factory.getType())) {
				mutexFactory = factory;
				break;
			}
		}
		if (mutexFactory != null) {
			return mutexFactory;
		}

		return switch (prop.getMutex()) {
			case "db" -> this.createDbMutexFactory(prop);
			case "memory" -> new MemoryMutexFactory();
			default -> throw new UnsupportedOperationException("未知 mutex 类型: " + prop.getMutex());
		};
	}

	private DbMutexFactory createDbMutexFactory(ScheduleProperties prop) {
		var factory = new DbMutexFactory(dataSource, prop.getMutexKey());
		factory.setKeepAliveSec(prop.getKeepAliveSec());
		factory.setExpireSec(prop.getExpireSec());
		factory.setJobIdList(this.getJobIdSet(prop));
		return factory;
	}

}
