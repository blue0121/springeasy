package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@ExtendWith(MockitoExtension.class)
class ResourceScheduleJobTest {
	@Mock
	ScheduleContext ctx;
	@Mock
	MutexFactory factory;
	@Mock
	Mutex mutex;

	private final String id = "id";

	@BeforeEach
	void beforeEach() {
		Mockito.when(ctx.getId()).thenReturn(id);
		Mockito.when(ctx.getMutexFactory()).thenReturn(factory);
		Mockito.when(factory.create(Mockito.anyString())).thenReturn(mutex);
	}

	@Test
	void testRun1() {
		Mockito.when(mutex.tryLock()).thenReturn(true);
		var job = new Job();
		job.flag = true;
		job.run(ctx);
		Assertions.assertTrue(job.run);
		Assertions.assertTrue(job.delete);
	}

	@Test
	void testRun2() {
		Mockito.when(mutex.tryLock()).thenReturn(true);
		var job = new Job();
		job.flag = false;
		job.run(ctx);
		Assertions.assertTrue(job.run);
		Assertions.assertFalse(job.delete);
	}

	@Test
	void testRun3() {
		Mockito.when(mutex.tryLock()).thenReturn(false);
		var job = new Job();
		job.flag = false;
		job.run(ctx);
		Assertions.assertFalse(job.run);
		Assertions.assertFalse(job.delete);
	}

	@Slf4j
	static class Job extends ResourceScheduleJob {
		boolean flag = false;
		boolean run = false;
		boolean delete = false;

		@Override
		protected boolean handleResource(ResourceEntity entity) {
			log.info("Handle resource, jobId: {}, resourceId: {}, mutexKey: {}",
					entity.getJobId(), entity.getResourceId(), entity.getMutexKey());
			run = true;
			return flag;
		}

		@Override
		protected List<ResourceEntity> listResource(String jobId) {
			return List.of(new Entity("jobId", "resourceId", "mutexKey"));
		}

		@Override
		protected void deleteResource(ResourceEntity entity) {
			log.info("Delete resource, jobId: {}, resourceId: {}, mutexKey: {}",
					entity.getJobId(), entity.getResourceId(), entity.getMutexKey());
			delete = true;
		}
	}

	@Getter
	@AllArgsConstructor
	static class Entity implements ResourceEntity {
		String jobId;
		String resourceId;
		String mutexKey;
	}
}
