package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
@Slf4j
public class StringPlaceholder {
	private final String prefix;
	private final String suffix;
	private final String split;

	public StringPlaceholder() {
		this("{", "}", ":");
	}

	public StringPlaceholder(String prefix, String suffix, String split) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.split = split;
		AssertUtil.notEmpty(prefix, "Prefix");
		AssertUtil.notEmpty(suffix, "Suffix");
		AssertUtil.notEmpty(split, "Split");
	}

	public String template(String tpl, Map<String, String> variable) {
		AssertUtil.notEmpty(tpl, "Template");
		AssertUtil.notNull(variable, "Variable");

		var content = new StringBuilder(tpl.length() * 2);
		int startPos = tpl.indexOf(prefix);
		int endPos = tpl.indexOf(suffix, startPos);

		while (startPos != -1 && endPos > startPos + prefix.length()) {
			content.append(tpl.substring(0, startPos));
			var value = this.getVariable(tpl, variable, startPos, endPos);
			content.append(value);

			tpl = tpl.substring(endPos + suffix.length());
			startPos = tpl.indexOf(prefix);
			endPos = tpl.indexOf(suffix, startPos);
		}

		this.complete(content, tpl, startPos, endPos);
		return content.toString();
	}

	private String getVariable(String tpl, Map<String, String> variable,
	                           int startPos, int endPos) {
		var key = tpl.substring(startPos + prefix.length(), endPos);
		String defaultValue = null;
		int defaultValuePos = key.indexOf(split);
		if (defaultValuePos != -1) {
			defaultValue = key.substring(defaultValuePos + split.length());
			key = key.substring(0, defaultValuePos);
		}

		var value = variable.get(key);
		if (value == null) {
			if (defaultValue == null) {
				throw new IllegalArgumentException("Invalid variable: " + key);
			}
			value = defaultValue;
		}
		return value;
	}

	private void complete(StringBuilder content, String tpl, int startPos, int endPos) {
		if (endPos == -1 || endPos < startPos + prefix.length()) {
			content.append(tpl);
		} else {
			content.append(tpl.substring(endPos + suffix.length()));
		}
	}
}
