package io.jutil.springeasy.mybatis.handler;

import io.jutil.springeasy.core.codec.json.Dict;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-08-17
 */
public class DictTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {
	private final Map<Integer, T> codeMap = new HashMap<>();
	private final Map<Integer, T> ordinalMap = new HashMap<>();

	public DictTypeHandler(Class<T> clazz) {
		for (var e : clazz.getEnumConstants()) {
			ordinalMap.put(e.ordinal(), e);
			if (e instanceof Dict d) {
				codeMap.put(d.getCode(), e);
			}
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter instanceof Dict dict) {
			ps.setInt(i, dict.getCode());
		} else {
			ps.setInt(i, parameter.ordinal());
		}
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int code = rs.getInt(columnName);
		return this.fromCode(code, rs.wasNull());
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int code = rs.getInt(columnIndex);
		return this.fromCode(code, rs.wasNull());
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int code = cs.getInt(columnIndex);
		return this.fromCode(code, cs.wasNull());
	}

	private T fromCode(int code, boolean wasNull) {
		if (code == 0 && wasNull) {
			return null;
		}
		var t = codeMap.get(code);
		if (t != null) {
			return t;
		}
		return ordinalMap.get(code);
	}
}
