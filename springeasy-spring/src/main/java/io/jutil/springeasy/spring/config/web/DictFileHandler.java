package io.jutil.springeasy.spring.config.web;

import com.alibaba.fastjson2.JSON;
import io.jutil.springeasy.core.codec.json.Dict;
import io.jutil.springeasy.core.codec.json.EnumObjectReader;
import io.jutil.springeasy.core.codec.json.EnumObjectWriter;
import io.jutil.springeasy.core.io.scan.ResourceHandler;
import io.jutil.springeasy.core.io.scan.ResourceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2025-08-17
 */
@Slf4j
public class DictFileHandler implements ResourceHandler {

	@Override
	public boolean accepted(ResourceInfo info) {
		return info.getFileName().endsWith(".class");
	}

	@Override
	public Result handle(ResourceInfo info) {
		var clazz = info.resolveClass();
		if (clazz != null && clazz.isEnum() && Dict.class.isAssignableFrom(clazz)) {
			JSON.register(clazz, new EnumObjectReader<>(clazz));
			JSON.register(clazz, EnumObjectWriter.INSTANCE);
			log.info("找到并注册 Dict 类型: {}", clazz.getName());
		}

		return Result.CONTINUE;
	}
}
