package io.jutil.springeasy.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
}
