package io.jutil.springeasy.core.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-01-18
 */
class YamlUtilTest {

	@Test
	void testParse() {
		var config = YamlUtil.parse("classpath:/yaml/test.yaml", Config.class);
		this.verify(config);
	}

	@Test
	void testParseString() {
		var yaml = """
				config-list:
				  - name: test1
				    status: 1
				    type: type1
				  - name: test2
				    status: 2
				    type: type2
				config-map:
				  test1:
				    status: 1
				    type: type1
				  test2:
				    status: 2
				    type: type2""";
		var config = YamlUtil.parseString(yaml, Config.class);
		this.verify(config);
	}

	private void verify(Config config) {
		Assertions.assertNotNull(config);

		var configList = config.getConfigList();
		Assertions.assertEquals(2, configList.size());
		this.verify(configList.getFirst(), "test1", 1, "type1");
		this.verify(configList.getLast(), "test2", 2, "type2");

		var configMap = config.getConfigMap();
		Assertions.assertEquals(2, configMap.size());
		this.verify(configMap.get("test1"), "test1", 1, "type1");
		this.verify(configMap.get("test2"), "test2", 2, "type2");
	}

	private void verify(Data data, String name, int status, String type) {
		Assertions.assertNotNull(data);
		Assertions.assertEquals(name, data.getName());
		Assertions.assertEquals(status, data.getStatus());
		Assertions.assertEquals(type, data.getType());
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Config implements YamlUtil.PostProcessor {
		private List<Data> configList;
		private Map<String, Data> configMap;

		@Override
		public void postHandle() {
			if (configMap == null || configMap.isEmpty()) {
				return;
			}
			for (var entry : configMap.entrySet()) {
				entry.getValue().setName(entry.getKey());
			}
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Data {
		private String name;
		private int status;
		private String type;
	}
}
