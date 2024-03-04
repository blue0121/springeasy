package io.jutil.springeasy.core.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Slf4j
public abstract class ResourceScheduleJob implements ScheduleJob {

	@Override
	public final void run(ScheduleContext ctx) {
		var list = this.listResource(ctx.getId());
		if (list == null || list.isEmpty()) {
			return;
		}

		for (var entity : list) {
			this.runResource(ctx, entity);
		}
	}

	private void runResource(ScheduleContext ctx, ResourceEntity entity) {
		var mutex = ctx.getMutexFactory().create(entity.getMutexKey());
		if (!mutex.tryLock()) {
			return;
		}

		try {
			var result = this.handleResource(entity);
			if (result) {
				this.deleteResource(entity);
			}
		} finally {
			mutex.unlock();
		}
	}

	protected abstract boolean handleResource(ResourceEntity entity);

	protected abstract List<ResourceEntity> listResource(String jobId);

	protected abstract void deleteResource(ResourceEntity entity);
}
