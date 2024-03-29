package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.dictionary.Status;
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
