package io.jutil.springeasy.springmvc.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-01-02-21:29
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorCode {
	private int code;
	private String message;
}
