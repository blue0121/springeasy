package io.jutil.springeasy.jpa.sql;

/**
 * @author Jin Zheng
 * @since 2023-11-10
 */
public interface Expression {

    void and(String sql, String key, Object value);

    void orderBy(String sql);

}
