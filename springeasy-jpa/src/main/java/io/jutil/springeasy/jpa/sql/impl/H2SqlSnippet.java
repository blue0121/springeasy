package io.jutil.springeasy.jpa.sql.impl;

import com.alibaba.fastjson2.JSON;
import io.jutil.springeasy.jpa.sql.SqlSnippet;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
public class H2SqlSnippet implements SqlSnippet {

    @Override
    public String getJsonString(String str) {
        return JSON.parseObject(str, String.class);
    }

}
