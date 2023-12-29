package io.jutil.springeasy.jpa.sql;

import io.jutil.springeasy.core.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-10
 */
public class DefaultExpression implements Expression {
    private final Map<String, Object> paramMap = new HashMap<>();
    private final List<String> sqlList = new ArrayList<>();
    private String orderBy;

    @Override
    public void and(String sql, String key, Object value) {
        sqlList.add(sql);
        paramMap.put(key, value);
    }

    @Override
    public void orderBy(String sql) {
        this.orderBy = sql;
    }

    public String toHqlWhere() {
        var snippet = new StringBuilder(64);
        if (!paramMap.isEmpty()) {
            snippet.append("where ").append(StringUtil.join(sqlList, " and "));
        }
        return snippet.toString();
    }

    public String toHqlSnippet() {
        var snippet = new StringBuilder(64);
        if (!paramMap.isEmpty()) {
            snippet.append("where ").append(StringUtil.join(sqlList, " and "));
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            snippet.append(" order by ").append(orderBy);
        }
        return snippet.toString();
    }

    public Map<String, Object> toArgs() {
        return paramMap;
    }
}
