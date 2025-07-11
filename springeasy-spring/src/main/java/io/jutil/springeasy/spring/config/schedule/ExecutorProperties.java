package io.jutil.springeasy.spring.config.schedule;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import io.jutil.springeasy.spring.config.PropertiesUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.schedule")
public class ExecutorProperties implements PropertiesChecker {
	private boolean enabled;
	private List<ExecutorConfigProperties> configs;

	@Override
	public void check() {
		if (!enabled) {
			return;
		}
		PropertiesUtil.check(configs);
	}


	@Getter
	@Setter
	@NoArgsConstructor
	public static class ExecutorConfigProperties implements PropertiesChecker {
		private String id;
		private boolean enabled = true;
		private Type type = Type.PLATFORM;
		private int queueCapacity;
		private int coreSize;
		private int maxSize;

		@Override
		public void check() {
			if (!enabled) {
				return;
			}
			AssertUtil.notEmpty(id, "id");
			AssertUtil.notNull(type, "type");
			AssertUtil.nonNegative(queueCapacity, "queueCapacity");
			AssertUtil.positive(coreSize, "coreSize");
			AssertUtil.positive(maxSize, "maxSize");
		}
	}

	public enum Type {
		PLATFORM,
		VIRTUAL,
	}
}
