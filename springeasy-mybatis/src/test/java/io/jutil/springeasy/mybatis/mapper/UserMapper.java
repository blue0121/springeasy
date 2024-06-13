package io.jutil.springeasy.mybatis.mapper;

import io.jutil.springeasy.mybatis.entity.UserEntity;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */
@Mapper
public interface UserMapper {

	int insert(UserEntity entity);

	int update(UserEntity entity);

	UserEntity getOne(@Param("id") Long id);

	@MapKey("id")
	Map<Long, UserEntity> listAll();

	int deleteAll();
}
