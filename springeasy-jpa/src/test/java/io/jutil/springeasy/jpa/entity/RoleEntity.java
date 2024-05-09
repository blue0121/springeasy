package io.jutil.springeasy.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "test_role")
public class RoleEntity extends BaseUuidEntity {

	@Column(name = "name", columnDefinition = "varchar(50)", nullable = false)
	private String name;

}
