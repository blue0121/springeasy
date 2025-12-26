package io.jutil.springeasy.mybatis.dao;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.codec.json.Dict;
import io.jutil.springeasy.mybatis.metadata.DatabaseInfo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Types;

/**
 * @author Jin Zheng
 * @since 2025-12-26
 */
public class JsonAwareBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {
	private final DatabaseInfo databaseInfo;

	public JsonAwareBeanPropertySqlParameterSource(Object object, DatabaseInfo databaseInfo) {
		super(object);
		this.databaseInfo = databaseInfo;
	}

	@Override
	public Object getValue(String paramName) {
		var value = super.getValue(paramName);

		return switch (value) {
			case null -> null;
			case Dict dict -> dict.getCode();
			case JSONObject object -> object.toJSONString();
			case JSONArray array -> array.toJSONString();
			default -> value;
		};
	}

	@Override
	public int getSqlType(String paramName) {
		var value = super.getValue(paramName);

		return switch (value) {
			case Dict dict -> Types.INTEGER;
			case JSONObject object -> this.getJsonType();
			case JSONArray array -> this.getJsonType();
			case null, default -> super.getSqlType(paramName);
		};
	}

	private int getJsonType() {
		return switch (databaseInfo.product()) {
			case POSTGRESQL -> Types.OTHER;
			case H2, MYSQL -> Types.VARCHAR;
		};
	}
}
