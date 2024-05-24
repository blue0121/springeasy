package io.jutil.springeasy.spring.config.cache;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import io.jutil.springeasy.spring.config.PropertiesUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.cache")
public class CacheProperties implements PropertiesChecker {
	private boolean enabled;
	private String type;
	private int expireSec;
	private int maxSize;
	private int expireOtherSec;
	private int maxOtherSize;
	private List<CacheItemProperties> items;

	private Map<String, CacheItemProperties> itemMap = new ConcurrentHashMap<>();

	@Override
	public void check() {
		if (!enabled) {
			return;
		}

		AssertUtil.notEmpty(type, "type");
		AssertUtil.positive(expireSec, "expire second");
		AssertUtil.positive(maxSize, "max size");
		if (expireOtherSec <= 0) {
			expireOtherSec = expireSec * PropertiesChecker.FIVE_TIMES;
		}
		if (maxOtherSize <= 0) {
			maxOtherSize = maxSize * PropertiesChecker.FIVE_TIMES;
		}

		PropertiesUtil.check(items, p -> {
			if (p.expireSec <= 0) {
				p.expireSec = expireSec;
			}
			if (p.maxSize <= 0) {
				p.maxSize = maxSize;
			}
			if (p.expireOtherSec <= 0) {
				p.expireOtherSec = p.expireSec * PropertiesChecker.FIVE_TIMES;
			}
			if (p.maxOtherSize <= 0) {
				p.maxOtherSize = p.maxSize * PropertiesChecker.FIVE_TIMES;
			}
			itemMap.put(p.id, p);
		});
	}

	public CacheItemProperties getItem(String id) {
		return itemMap.computeIfAbsent(id, k -> {
			var config = new CacheItemProperties();
			config.setId(id);
			config.setExpireSec(expireSec);
			config.setMaxSize(maxSize);
			config.setExpireOtherSec(expireOtherSec);
			config.setMaxOtherSize(maxOtherSize);
			return config;
		});
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class CacheItemProperties implements PropertiesChecker {
		private String id;
		private int expireSec;
		private int maxSize;
		private int expireOtherSec;
		private int maxOtherSize;

		@Override
		public void check() {
			AssertUtil.notEmpty(id, "id");
		}
	}
}
