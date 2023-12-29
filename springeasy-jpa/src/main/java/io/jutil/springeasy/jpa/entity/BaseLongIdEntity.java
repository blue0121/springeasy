package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.core.util.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseLongIdEntity {
	@Id
	@GeneratedValue(generator = Generator.LONG_ID_NAME)
	@GenericGenerator(name = Generator.LONG_ID_NAME, strategy = Generator.LONG_ID_GENERATOR)
	@Column(name = "id", columnDefinition = "int8", nullable = false)
	protected Long id;

	@Column(name = "create_time", columnDefinition = "timestamp(0)", nullable = false, updatable = false)
	protected LocalDateTime createTime;

	@Column(name = "update_time", columnDefinition = "timestamp(0)", nullable = false)
	protected LocalDateTime updateTime;


	@PrePersist
	public void onPrePersist() {
		this.createTime = DateUtil.now();
		this.updateTime = this.createTime;
	}

	@PreUpdate
	public void onPreUpdate() {
		this.updateTime = DateUtil.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var that = (BaseLongIdEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
