package io.jutil.springeasy.springmvc.controller;

import io.jutil.springeasy.core.dict.Status;
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
public class DictionaryResponse {
	private String name;
    private Status status;
}
