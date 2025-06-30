package io.jutil.springeasy.spring.config.schedule;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import io.jutil.springeasy.spring.config.PropertiesUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.schedule")
public class ScheduleProperties implements PropertiesChecker {
	private boolean enabled;
	private String mutex;
	private String executor;
	private List<ScheduleConfigProperties> configs;

	private int expireSec;

	@Override
	public void check() {
		if (!enabled) {
			return;
		}
		AssertUtil.notEmpty(mutex, "Mutex");
		AssertUtil.notEmpty(executor, "Executor");
		PropertiesUtil.check(configs, p -> {
			if (p.executor == null || p.executor.isEmpty()) {
				p.executor = executor;
			}
		});
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ScheduleConfigProperties implements PropertiesChecker {
		private String id;
		private boolean enabled = true;
		private String cron;
		private String executor;

		private CronTrigger cronTrigger;

		@Override
		public void check() {
			if (!enabled) {
				return;
			}
			AssertUtil.notEmpty(id, "Id");
			AssertUtil.notEmpty(cron, "Cron");
			cronTrigger = new CronTrigger(cron);
		}
	}

}
