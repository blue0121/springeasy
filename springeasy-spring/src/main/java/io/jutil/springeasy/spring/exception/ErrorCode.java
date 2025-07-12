package io.jutil.springeasy.spring.exception;

import com.alibaba.fastjson2.JSONObject;

import java.text.MessageFormat;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@SuppressWarnings("javaarchitecture:S7027")
public record ErrorCode(int code, String message) {

	public int httpStatus() {
		return code / 1000;
	}

	public String getMessage(Object... args) {
		return MessageFormat.format(message, args);
	}

	public String toJsonString(Object... args) {
		var json = new JSONObject();
		json.put("code", this.code());
		json.put("message", this.getMessage(args));
		return json.toJSONString();
	}

	public ErrorCodeException newException(Object... args) {
		return new ErrorCodeException(this, args);
	}

}
