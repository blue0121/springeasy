package io.jutil.springeasy.jdo.parser.impl;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.Mapper;
import io.jutil.springeasy.jdo.dialect.Dialect;
import io.jutil.springeasy.jdo.parser.MapperMetadata;
import io.jutil.springeasy.jdo.parser.Parser;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
@Slf4j
public class ParserFacade implements Parser {
	private static Set<Class<? extends Annotation>> annotationSet =
			Set.of(Entity.class, Mapper.class);

	private final Dialect dialect;
	private final Map<Class<? extends Annotation>, Parser> parserMap = new HashMap<>();
	private final MetadataCache metadataCache = new MetadataCache();

	public ParserFacade(Dialect dialect) {
		this.dialect = dialect;
		parserMap.put(Entity.class, new EntityParser(dialect));
		parserMap.put(Mapper.class, new MapperParser(dialect));
	}

	@Override
	public MapperMetadata parse(Class<?> clazz) {
		var metadata = metadataCache.get(clazz);
		if (metadata != null) {
			log.warn("{} 已经解析过", clazz.getName());
			return metadata;
		}

		for (var annotation : clazz.getAnnotations()) {
			if (!annotationSet.contains(annotation.annotationType())) {
				continue;
			}

			Parser parser = parserMap.get(annotation.annotationType());
			if (parser != null) {
				log.debug("找到 {} 处理器: {}", annotation.annotationType().getSimpleName(), parser);
				var m = parser.parse(clazz);
				metadataCache.put(m);
				return m;
			}
		}
		return null;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public MetadataCache getMetadataCache() {
		return metadataCache;
	}
}
