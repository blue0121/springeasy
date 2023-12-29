package io.jutil.springeasy.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jin Zheng
 * @since 2023-10-28
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
