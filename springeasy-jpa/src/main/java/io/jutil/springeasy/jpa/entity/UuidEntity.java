package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.jpa.id.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class UuidEntity {
	@Id
	@GeneratedValue(generator = Generator.UUID_NAME)
	@GenericGenerator(name = Generator.UUID_NAME, type = UuidGenerator.class)
	@Column(name = "id", columnDefinition = "uuid", nullable = false)
	protected String id;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var that = (UuidEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
