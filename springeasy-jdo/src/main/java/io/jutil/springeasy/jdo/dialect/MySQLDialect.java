package io.jutil.springeasy.jdo.dialect;

import io.jutil.springeasy.jdo.annotation.LockModeType;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class MySQLDialect implements Dialect {
	public static final String PROTOCOL = "mysql";
	private static final String QUOT = "`";

	@Override
	public String escape() {
		return QUOT;
	}

	@Override
	public String escape(String key) {
		StringBuilder t = new StringBuilder(key.length() + 4);
		t.append(QUOT).append(key).append(QUOT);
		return t.toString();
	}

	@Override
	public String page(String sql, int start, int size) {
		StringBuilder t = new StringBuilder(sql.length() + 16);
		t.append(sql);
		t.append(" limit ");
		t.append(start);
		t.append(",");
		t.append(size);
		return t.toString();
	}

	@Override
	public String lock(String sql, LockModeType type) {
		var t = new StringBuilder(sql);
		switch (type) {
			case NONE:
				return sql;
			case READ:
				t.append(" mutex in share mode");
				break;
			case WRITE:
				t.append(" for update");
				break;
			default:
				break;
		}
		return t.toString();
	}

}
