package io.jutil.springeasy.mybatis.handler;

import io.jutil.springeasy.mybatis.metadata.DatabaseInfo;
import io.jutil.springeasy.mybatis.metadata.DatabaseProduct;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Jin Zheng
 * @since 2025-08-31
 */
public abstract class JsonTypeHandler<T> extends BaseTypeHandler<T> {
	protected final DatabaseInfo databaseInfo;

	public JsonTypeHandler(DatabaseInfo databaseInfo) {
		this.databaseInfo = databaseInfo;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
			throws SQLException {
		var json = parameter != null ? parameter.toString() : null;
		if (json == null) {
			return;
		}
		if (databaseInfo.product() == DatabaseProduct.POSTGRESQL) {
			ps.setObject(i, json, Types.OTHER);
		} else {
			ps.setString(i, json);
		}
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return this.parse(rs.getString(columnName));
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.parse(rs.getString(columnIndex));
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.parse(cs.getString(columnIndex));
	}

	protected abstract T parse(String json);
}
