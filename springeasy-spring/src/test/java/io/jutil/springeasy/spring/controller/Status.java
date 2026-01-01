package io.jutil.springeasy.spring.controller;

import io.jutil.springeasy.core.codec.json.Dict;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2025-08-21
 */
@Getter
public enum Status implements Dict {
	ACTIVE(1, "正常"),
	DISABLED(2, "作废"),
	;

	private final int code;
	private final String label;

	Status(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
