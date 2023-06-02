package io.jutil.springeasy.springmvc.controller;

import io.jutil.springeasy.core.dictionary.Status;
import io.jutil.springeasy.core.validation.group.GetOperation;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@Getter
@Setter
@NoArgsConstructor
public class DictionaryRequest {
    @NotEmpty(groups = {GetOperation.class}, message = "名称不能为空")
	private String name;
    private Status status;

}
