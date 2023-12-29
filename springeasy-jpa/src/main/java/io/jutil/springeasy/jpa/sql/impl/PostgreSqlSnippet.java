package io.jutil.springeasy.jpa.sql.impl;

import io.jutil.springeasy.jpa.sql.SqlSnippet;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
public class PostgreSqlSnippet implements SqlSnippet {

    @Override
    public String getJsonString(String str) {
        return str;
    }

}
