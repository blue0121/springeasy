package io.jutil.springeasy.spring.config;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@Slf4j
public class SpringBeans {
	private SpringBeans() {
	}

	public static  <T extends BeanName> T getBean(List<? extends T> beanList, String id) {
		for (var bean : beanList) {
			if (bean.getId().equals(id)) {
				return bean;
			}
		}
		throw new IllegalArgumentException("Not found bean, id: " + id);
	}

	public static <T extends BeanName> Map<String, T> toBeanMap(List<T> beanList) {
		Map<String, T> map = new HashMap<>();
		for (var bean : beanList) {
			if (map.containsKey(bean.getId())) {
				throw new IllegalArgumentException("Duplicate bean, id: " + bean.getId());
			}
			map.put(bean.getId(), bean);
		}
		return map;
	}

}
