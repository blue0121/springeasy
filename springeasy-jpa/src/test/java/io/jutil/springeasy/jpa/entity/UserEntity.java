package io.jutil.springeasy.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "test_user")
public class UserEntity extends BaseLongIdEntity {

	@Column(name = "name", columnDefinition = "varchar(50)", nullable = false)
	private String name;

}
