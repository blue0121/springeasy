package io.jutil.springeasy.jdo.parser;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface Parser {
	/**
	 * 解析 @Entity/@Mapper 注解
	 *
	 * @param clazz
	 * @return
	 */
	MapperMetadata parse(Class<?> clazz);
}
