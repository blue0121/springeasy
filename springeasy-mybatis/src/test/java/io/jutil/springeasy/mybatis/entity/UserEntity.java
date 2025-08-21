package io.jutil.springeasy.mybatis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends LongIdEntity {
	private String name;
	private Status status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
