package io.jutil.springeasy.mybatis.mutex.exeutor;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2025-06-08
 */
public interface Executor {

	void createTable();

	boolean canStart(String key, String instanceId, LocalDateTime expireTime);

	void delete(String key);

	void renew(Collection<String> keys, String instanceId, LocalDateTime expireTime);

}
