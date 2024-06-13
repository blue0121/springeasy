package io.jutil.springeasy.mybatis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2024-06-13
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class LongIdEntity {
	protected long id;
}
