package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.jpa.id.LongIdGenerator;
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
 * @since 2023-10-28
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class LongIdEntity {
	@Id
	@GeneratedValue(generator = Generator.LONG_ID_NAME)
	@GenericGenerator(name = Generator.LONG_ID_NAME, type = LongIdGenerator.class)
	@Column(name = "id", columnDefinition = "int8", nullable = false)
	protected Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var that = (LongIdEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
