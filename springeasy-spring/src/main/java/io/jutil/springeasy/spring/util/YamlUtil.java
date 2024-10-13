package io.jutil.springeasy.spring.util;

import io.jutil.springeasy.core.util.FileUtil;
import io.jutil.springeasy.core.util.StringUtil;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author Jin Zheng
 * @since 2024-10-12
 */
public class YamlUtil {
	private YamlUtil() {
	}

	public static <T> T parse(String path, Class<T> clazz) {
		var property = new YamlPropertyUtils();
		property.setSkipMissingProperties(true);

		var options = new LoaderOptions();
		var constructor = new Constructor(clazz, options);
		constructor.setPropertyUtils(property);
		var yaml = new Yaml(constructor);
		try (var is = FileUtil.readStream(path)) {
			return yaml.load(is);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static class YamlPropertyUtils extends PropertyUtils {
		@Override
		public Property getProperty(Class<?> type, String name) {
			return super.getProperty(type, StringUtil.toLowerCamelCase(name));
		}
	}

}
