package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.schedule.ExecutorThreadFactory;
import io.jutil.springeasy.spring.config.schedule.ExecutorProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@Slf4j
@Setter
public class ExecutorServiceFactoryBean implements FactoryBean<ExecutorService>, InitializingBean {
	private String id;
	private ExecutorProperties.Type type;
	private int queueCapacity;
	private int coreSize;
	private int maxSize;

	private ExecutorService executor;

	@Override
	public @Nullable ExecutorService getObject() {
		return executor;
	}

	@Override
	public @Nullable Class<?> getObjectType() {
		return ExecutorService.class;
	}

	@Override
	public void afterPropertiesSet() {
		this.executor = switch (type) {
			case PLATFORM -> this.createPlatform();
			case VIRTUAL -> this.createVirtual();
		};
	}

	private ExecutorService createPlatform() {
		var queue = switch (this.queueCapacity) {
			case 0 -> new SynchronousQueue<Runnable>();
			default -> new LinkedBlockingQueue<Runnable>(this.queueCapacity);
		};
		var threadFactory = new ExecutorThreadFactory(this.id);
		log.info("创建 ExecutorService, id: {}, coreSize: {}, maxSize: {}, queueType: {}",
				this.id, this.coreSize, this.maxSize, queue.getClass().getSimpleName());
		return new ThreadPoolExecutor(this.coreSize, this.maxSize,
				1, TimeUnit.MINUTES, queue, threadFactory);
	}

	private ExecutorService createVirtual() {
		return Executors.newVirtualThreadPerTaskExecutor();
	}
}
