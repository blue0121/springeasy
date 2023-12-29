package io.jutil.springeasy.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = Generator.LONG_ID_NAME, strategy = Generator.LONG_ID_GENERATOR)
	@Column(name = "id", columnDefinition = "int8", nullable = false)
	protected Long id;
}
