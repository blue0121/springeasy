package io.jutil.springeasy.jdo.model;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
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
@Entity(table = "usr_group")
public class GroupEntity {
	@Id(generator = GeneratorType.ASSIGNED)
	private Long id;
	private String name;
	private Integer count;

	public static GroupEntity create(Long id, String name, Integer count) {
		GroupEntity group = new GroupEntity();
		group.setId(id);
		group.setName(name);
		group.setCount(count);
		return group;
	}

	public static void verify(GroupEntity group, Long id, String name, Integer count) {
		Assertions.assertNotNull(group);
		Assertions.assertEquals(id, group.getId());
		Assertions.assertEquals(name, group.getName());
		Assertions.assertEquals(count, group.getCount());
	}
}
