package io.jutil.springeasy.jdo.model;

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
public class Group {
	private Long id;
	private String name;
	private Integer type;
	private Integer state;

}
