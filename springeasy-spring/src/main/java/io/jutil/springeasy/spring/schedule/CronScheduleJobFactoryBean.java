package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.schedule.CronScheduleJob;
import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.spring.config.SpringBeans;
import io.jutil.springeasy.spring.config.schedule.ScheduleProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2024-03-01
 */
public class CronScheduleJobFactoryBean implements FactoryBean<CronScheduleJob>,
		BeanFactoryAware {
	private final ScheduleProperties prop;
	private final MutexFactory mutexFactory;
	private final List<SpringScheduleJob> jobList;
	private BeanFactory beanFactory;

	public CronScheduleJobFactoryBean(ScheduleProperties prop, MutexFactory mutexFactory,
	                                  List<SpringScheduleJob> jobList) {
		this.prop = prop;
		this.mutexFactory = mutexFactory;
		this.jobList = jobList;
	}

	@Override
	public CronScheduleJob getObject() throws Exception {
		var executor = beanFactory.getBean(prop.getExecutor(), ExecutorService.class);
		var schedule = new CronScheduleJob(executor, mutexFactory);
		for (var config : prop.getConfigs()) {
			ExecutorService localExecutor = null;
			if (config.getExecutor() != null && !config.getExecutor().isEmpty()) {
				localExecutor = beanFactory.getBean(config.getExecutor(), ExecutorService.class);
			}
			var job = SpringBeans.getBean(jobList, config.getId());
			schedule.add(config.getId(), config.getCronTrigger(), job, localExecutor);
		}
		return schedule;
	}

	@Override
	public Class<?> getObjectType() {
		return CronScheduleJob.class;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
