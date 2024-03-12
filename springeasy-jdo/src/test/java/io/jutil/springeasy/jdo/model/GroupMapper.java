package io.jutil.springeasy.jdo.model;

import io.jutil.springeasy.jdo.annotation.Mapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@Getter
@Setter
@NoArgsConstructor
@Mapper
public class GroupMapper {
	private Long id;
	private String name;
	private Integer count;

	public static GroupMapper create(Long id, String name, Integer count) {
		GroupMapper group = new GroupMapper();
		group.setId(id);
		group.setName(name);
		group.setCount(count);
		return group;
	}

	public static void verify(GroupMapper group, Long id, String name, Integer count) {
		Assertions.assertNotNull(group);
		Assertions.assertEquals(id, group.getId());
		Assertions.assertEquals(name, group.getName());
		Assertions.assertEquals(count, group.getCount());
	}
}
