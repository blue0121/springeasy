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
public abstract class String20IdEntity {
	@Id
	@GeneratedValue(generator = Generator.STRING_20_ID_NAME)
	@GenericGenerator(name = Generator.STRING_20_ID_NAME, strategy = Generator.STRING_20_ID_GENERATOR)
	@Column(name = "id", columnDefinition = "char(20)", nullable = false)
	protected String id;
}
