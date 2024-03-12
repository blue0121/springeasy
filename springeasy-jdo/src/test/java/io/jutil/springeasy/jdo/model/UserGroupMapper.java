package io.jutil.springeasy.jdo.model;

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
public class UserGroupMapper {
	private Integer id;
	private Integer groupId;
	private String name;
	private String password;
	private State state;
	private String groupName;

}
