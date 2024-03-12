package io.jutil.springeasy.jdo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {
	/**
	 * 是否强制使用乐观锁，默认为true
	 */
	boolean force() default true;

	/**
	 * 默认值，默认是1
	 * @return
	 */
	int defaultValue() default 1;
}
