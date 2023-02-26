package io.jutil.springeasy.springmvc.exception;

import com.alibaba.fastjson2.JSONObject;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-01-02-16:34
 */
public class ErrorCode {
	public static final int STATUS_TO_CODE = 1_000;

	public static final String CODE = "code";
	public static final String MESSAGE = "message";

	// 200
	public static final ErrorCode SUCCESS = new ErrorCode(200, 0, "成功");

	// 400
	public static final ErrorCode INVALID_PARAM = new ErrorCode(400, 0, "无效参数: {0}");
	public static final ErrorCode INVALID_JSON = new ErrorCode(400, 1, "无效JSON格式");
	public static final ErrorCode NO_PARAM = new ErrorCode(400, 2, "没有参数");
	public static final ErrorCode OUT_OF_MAX_UPLOAD_SIZE = new ErrorCode(400, 3, "上传文件超出限制");
	public static final ErrorCode INVALID_UPLOAD_FILE = new ErrorCode(400, 4, "无效文件上传");
	public static final ErrorCode EXISTS = new ErrorCode(400, 5, "{0} 已经存在");
	public static final ErrorCode REQUIRED = new ErrorCode(400, 6, "{0} 不能为空");

	// 401
	public static final ErrorCode LOGIN_FAILURE = new ErrorCode(401, 0, "登录失败");
	public static final ErrorCode INVALID_TOKEN = new ErrorCode(401, 1, "无效令牌");
	public static final ErrorCode INVALID_SESSION = new ErrorCode(401, 2, "无效会话");
	public static final ErrorCode INVALID_CAPTCHA = new ErrorCode(401, 3, "无效验证码");

	// 403
	public static final ErrorCode NOT_ACCESS = new ErrorCode(403, 0, "无访问权限");

	// 404
	public static final ErrorCode NOT_FOUND = new ErrorCode(404, 0, "找不到URL");
	public static final ErrorCode NO_DATA = new ErrorCode(404, 1, "没有数据");
	public static final ErrorCode NOT_EXISTS = new ErrorCode(404, 2, "{0} 不存在");

	// 500
	public static final ErrorCode ERROR = new ErrorCode(500, 0, "系统错误");
	public static final ErrorCode SYS_ERROR = new ErrorCode(500, 1, "发生错误: {0}");

	private int httpStatus;
	private int code;
	private String message;
	private Set<Integer> codeSet = new HashSet<>();

	protected ErrorCode(int httpStatus, int code, String message) {
		this.httpStatus = httpStatus;
		this.code = httpStatus * STATUS_TO_CODE + code;
		this.message = message;
		this.check();
	}

	private void check() {
		if (!codeSet.add(code)) {
			throw new IllegalArgumentException("错误码重复: " + code);
		}
	}

	public final int getCode() {
		return code;
	}

	public final String getMessage(Object... args) {
		return MessageFormat.format(message, args);
	}

	public final int getHttpStatus() {
		return httpStatus;
	}

	public final String toJsonString(Object... args) {
		JSONObject json = new JSONObject();
		json.put(CODE, this.getCode());
		json.put(MESSAGE, this.getMessage(args));
		return json.toJSONString();
	}

	public ErrorCodeException newException(Object... args) {
		return new ErrorCodeException(this, args);
	}
}
