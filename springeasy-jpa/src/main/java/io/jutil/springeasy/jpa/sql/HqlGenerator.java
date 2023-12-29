package io.jutil.springeasy.jpa.sql;

import io.jutil.springeasy.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-09
 */
public class HqlGenerator {
    private HqlGenerator() {
    }

    public static String where(Map<String, ?> args) {
        if (args == null || args.isEmpty()) {
            return "";
        }

        List<String> set = new ArrayList<>();
        for (var entry : args.entrySet()) {
            set.add(entry.getKey() + "=:" + entry.getKey());
        }
        return "where " + StringUtil.join(set, " and ");
    }

    public static String updateSet(Map<String, ?> fieldSet) {
        List<String> set = new ArrayList<>();
        for (var entry : fieldSet.entrySet()) {
            set.add(entry.getKey() + "=:" + entry.getKey());
        }
        return StringUtil.join(set, ",");
    }
}
