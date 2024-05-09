package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.jpa.id.UuidGenerator;
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
 * @since 2024-05-09
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseUuidEntity {
	@Id
	@GeneratedValue(generator = Generator.UUID_NAME)
	@GenericGenerator(name = Generator.UUID_NAME, type = UuidGenerator.class)
	@Column(name = "id", columnDefinition = "uuid", nullable = false)
	protected String id;

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
		var that = (BaseUuidEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
