package io.jutil.springeasy.jpa.entity;

import io.jutil.springeasy.core.util.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2023-11-10
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "test_user_log")
public class UserLogEntity extends LongIdEntity {
    @Column(name = "user_id", columnDefinition = "int8", nullable = false)
    private Long userId;

    @Column(name = "log_time", columnDefinition = "timestamp(0)", nullable = false, updatable = false)
    private LocalDateTime logTime;

    @Transient
    private String userName;

    @PrePersist
    public void onPrePersist() {
        this.logTime = DateUtil.now();
    }
}
