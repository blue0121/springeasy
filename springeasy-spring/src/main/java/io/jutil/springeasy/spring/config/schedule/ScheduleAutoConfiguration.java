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
public class ScheduleAutoConfiguration {
	@Autowired(required = false)
	DataSource dataSource;

	@Bean
	public MutexFactory mutexFactory(ScheduleProperties prop) {
		prop.check();
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
	                                                  MutexFactory mutexFactory,
	                                                  List<SpringScheduleJob> jobList) {
		prop.check();
		return new CronScheduleJobFactoryBean(prop, mutexFactory, jobList);
	}

}
