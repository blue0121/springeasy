package io.jutil.springeasy.jdo.model;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
import io.jutil.springeasy.jdo.annotation.Transient;
import io.jutil.springeasy.jdo.annotation.Version;
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
@Entity(table = "usr_user")
public class UserEntity {
	@Id(generator = GeneratorType.SNOWFLAKE)
	private Long id;
	@Version
	private Integer version;
	private Integer groupId;
	private String name;
	private String password;
	private State state;
	@Transient
	private String groupName;


	public static UserEntity create(Integer groupId, String name, String password, State state) {
		UserEntity user = new UserEntity();
		user.setGroupId(groupId);
		user.setName(name);
		user.setPassword(password);
		user.setState(state);
		return user;
	}

	public static void verify(UserEntity user, Integer version, Integer groupId, String name, String password, State state) {
		Assertions.assertNotNull(user);
		Assertions.assertEquals(version, user.getVersion());
		Assertions.assertEquals(groupId, user.getGroupId());
		Assertions.assertEquals(name, user.getName());
		Assertions.assertEquals(password, user.getPassword());
		Assertions.assertEquals(state, user.getState());
	}
}
