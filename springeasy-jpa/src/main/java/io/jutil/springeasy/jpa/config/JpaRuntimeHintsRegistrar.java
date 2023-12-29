package io.jutil.springeasy.jpa.config;

import io.jutil.springeasy.jpa.id.LongIdGenerator;
import io.jutil.springeasy.jpa.id.String20IdGenerator;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeHint;

/**
 * @author Jin Zheng
 * @since 2023-11-27
 */
public class JpaRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader loader) {
		var builder = TypeHint.builtWith(MemberCategory.values());
		var reflectionHints = hints.reflection();
		reflectionHints.registerType(LongIdGenerator.class, builder);
		reflectionHints.registerType(String20IdGenerator.class, builder);
		reflectionHints.registerType(FastJsonFormatMapper.class, builder);
	}
}
