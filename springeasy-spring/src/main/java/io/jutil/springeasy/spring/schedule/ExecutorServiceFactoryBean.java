package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.schedule.ExecutorThreadFactory;
import io.jutil.springeasy.spring.config.schedule.ExecutorProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
public class ExecutorServiceFactoryBean implements FactoryBean<ExecutorService>, InitializingBean {
	private String id;
	private ExecutorProperties.Type type;
	private int queueCapacity;
	private int coreSize;
	private int maxSize;

	private ExecutorService executor;

	@Override
	public ExecutorService getObject() throws Exception {
		return executor;
	}

	@Override
	public Class<?> getObjectType() {
		return ExecutorService.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		var queue = switch (this.queueCapacity) {
			case 0 -> new SynchronousQueue<Runnable>();
			default -> new LinkedBlockingQueue<Runnable>(this.queueCapacity);
		};
		var threadFactory = new ExecutorThreadFactory(this.id);
		this.executor = new ThreadPoolExecutor(this.coreSize, this.maxSize,
				1, TimeUnit.MINUTES, queue, threadFactory);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(ExecutorProperties.Type type) {
		this.type = type;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public void setCoreSize(int coreSize) {
		this.coreSize = coreSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
