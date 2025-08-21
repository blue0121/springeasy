package io.jutil.springeasy.spring.http;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2025-08-21
 */
@Getter
@Setter
@NoArgsConstructor
public class TestDictRequest {
	@NotNull(message = "状态不能为空")
	private Status status;
}
