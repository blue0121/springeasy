package io.jutil.springeasy.mybatis.dao;

import io.jutil.springeasy.mybatis.metadata.DatabaseInfo;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jin Zheng
 * @since 2025-12-25
 */
public class BatchDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final DatabaseInfo databaseInfo;

	public BatchDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		var dataSource = jdbcTemplate.getJdbcTemplate().getDataSource();
		this.databaseInfo = MetadataUtil.getDatabaseInfo(dataSource);
	}

	public <T> int update(@NonNull String sql, List<T> list) {
		return this.update(sql, list, null);
	}

	public <T> int update(@NonNull String sql, List<T> list, @Nullable Consumer<T> f) {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		SqlParameterSource[] parameterSources = new SqlParameterSource[list.size()];
		int i = 0;
		for (T t : list) {
			if (f != null) {
				f.accept(t);
			}
			parameterSources[i] = new JsonAwareBeanPropertySqlParameterSource(t, databaseInfo);
			i++;
		}
		var results = jdbcTemplate.batchUpdate(sql, parameterSources);
		int result = 0;
		for (var s : results) {
			result += s;
		}
		return result;
	}
}
