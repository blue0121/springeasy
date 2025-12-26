package io.jutil.springeasy.mybatis.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
	private long id;
	private String name;
	private Status status;
	private JSONObject body;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
