package io.jutil.springeasy.jdo.model;

import io.jutil.springeasy.jdo.annotation.Column;
import io.jutil.springeasy.jdo.annotation.Mapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@Getter
@Setter
@NoArgsConstructor
@Mapper
public class ResultMapper {
	private String userId;
	private String name;
	@Column(name = "message")
	private String msg;

}
