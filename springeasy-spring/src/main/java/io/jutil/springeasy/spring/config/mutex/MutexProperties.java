package io.jutil.springeasy.spring.config.mutex;

import io.jutil.springeasy.core.mutex.MutexType;
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
 * @since 2025-05-08
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.mutex")
public class MutexProperties implements PropertiesChecker {
	private boolean enabled;
	private int keepAliveSec = 60;
	private int expireSec;
	private List<MutexConfigProperties> configs;

	@Override
	public void check() {
		if (!enabled) {
			return;
		}

		AssertUtil.positive(keepAliveSec, "keepAliveSec");
		this.expireSec = keepAliveSec * 5;
		PropertiesUtil.check(configs, c -> {
			if (c.keepAliveSec <= 0) {
				c.keepAliveSec = keepAliveSec;
				c.expireSec = expireSec;
			}
		});
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class MutexConfigProperties implements PropertiesChecker {
		private String id;
		private boolean enabled = true;
		private String type;
		private String key;
		private int keepAliveSec;
		private int expireSec;

		private MutexType mutexType;

		@Override
		public void check() {
			if (!enabled) {
				return;
			}
			AssertUtil.notEmpty(id, "id");
			AssertUtil.notEmpty(type, "type");
			AssertUtil.notEmpty(key, "key");
			this.expireSec = keepAliveSec * 5;
			this.mutexType = MutexType.getType(type);
			AssertUtil.notNull(mutexType, "MutexType");
		}
	}
}
