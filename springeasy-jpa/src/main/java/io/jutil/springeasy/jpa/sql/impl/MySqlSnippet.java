package io.jutil.springeasy.jpa.sql.impl;

import io.jutil.springeasy.jpa.sql.SqlSnippet;

/**
 * @author Jin Zheng
 * @since 2023-12-08
 */
public class MySqlSnippet implements SqlSnippet {

	@Override
	public String getJsonString(String str) {
		return str;
	}

}
