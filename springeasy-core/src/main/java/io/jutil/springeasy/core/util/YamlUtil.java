package io.jutil.springeasy.core.util;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author Jin Zheng
 * @since 2025-01-18
 */
public class YamlUtil {
	private YamlUtil() {
	}

	public interface PostProcessor {
		void postHandle();
	}

	public static <T> T parseString(String text, Class<T> clazz) {
		var yaml = getYaml(clazz);
		var obj = yaml.loadAs(text, clazz);
		return handlePostProcessor(obj);
	}

	public static <T> T parse(String path, Class<T> clazz) {
		var yaml = getYaml(clazz);
		try (var is = FileUtil.readStream(path)) {
			T obj = yaml.load(is);
			return handlePostProcessor(obj);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static <T> Yaml getYaml(Class<T> clazz) {
		var property = new YamlPropertyUtils();
		property.setSkipMissingProperties(true);

		var options = new LoaderOptions();
		var constructor = new Constructor(clazz, options);
		constructor.setPropertyUtils(property);
		return new Yaml(constructor);
	}

	private static <T> T handlePostProcessor(T obj) {
		if (obj instanceof PostProcessor postProcessor) {
			postProcessor.postHandle();
		}
		return obj;
	}

	private static class YamlPropertyUtils extends PropertyUtils {
		@Override
		public Property getProperty(Class<?> type, String name) {
			return super.getProperty(type, StringUtil.toLowerCamelCase(name));
		}
	}
}
