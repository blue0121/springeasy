package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.jpa.id.String20IdGenerator;
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
public abstract class String20IdEntity {
	@Id
	@GeneratedValue(generator = Generator.STRING_20_ID_NAME)
	@GenericGenerator(name = Generator.STRING_20_ID_NAME, type = String20IdGenerator.class)
	@Column(name = "id", columnDefinition = "char(20)", nullable = false)
	protected String id;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var that = (String20IdEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
