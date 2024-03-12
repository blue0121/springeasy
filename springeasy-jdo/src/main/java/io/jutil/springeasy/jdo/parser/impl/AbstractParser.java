package io.jutil.springeasy.jdo.parser.impl;

import io.jutil.springeasy.core.reflect.ClassFieldOperation;
import io.jutil.springeasy.jdo.annotation.Column;
import io.jutil.springeasy.jdo.dialect.Dialect;
import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.parser.Parser;
import io.jutil.springeasy.jdo.parser.model.DefaultFieldMetadata;

import java.lang.reflect.Modifier;

/**
 * @author Jin Zheng
 * @since 2024-03-08
 */
public abstract class AbstractParser implements Parser {
	protected final Dialect dialect;

	public AbstractParser(Dialect dialect) {
		this.dialect = dialect;
	}

	protected String getColumnName(ClassFieldOperation field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			if (field.getGetterMethod() == null) {
				throw new JdoException("找不到Getter方法: " + field.getFieldName());
			}
			if (field.getSetterMethod() == null) {
				throw new JdoException("找不到Setter方法: " + field.getFieldName());
			}
		}

		Column annotation = field.getAnnotation(Column.class);
		if (annotation != null) {
			return annotation.name();
		}
		return this.toColumnName(field.getFieldName());
	}

	/**
	 * 字段 => 列名，类名 => 表名
	 *
	 * @return
	 */
	protected String toColumnName(String field) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < field.length(); i++) {
			char c = field.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					sb.append('_');
				}

				sb.append(Character.toLowerCase(c));
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	protected void setFieldMetadata(ClassFieldOperation field, DefaultFieldMetadata metadata) {
		String columnName = this.getColumnName(field);
		metadata.setColumnName(columnName);
		metadata.setBeanField(field);
	}

}
