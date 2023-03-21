package io.jutil.springeasy.core.dict;

import com.alibaba.fastjson2.JSON;
import io.jutil.springeasy.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
class StatusTest {
    private DictionaryCache cache = DictionaryCache.getInstance();


    @Test
    void testToString() {
        var status = Status.ACTIVE;
        var json = JsonUtil.output(status);
        System.out.println(json);
        var obj = JSON.parseObject(json);
        Assertions.assertEquals(0, obj.getIntValue("value"));
        Assertions.assertEquals("正常", obj.getString("name"));
        Assertions.assertEquals("primary", obj.getString("color"));

        Assertions.assertEquals("Status{正常}", status.toString());
    }

    @Test
    void testString() {
        Assertions.assertEquals("正常", cache.getNameFromObject(Status.ACTIVE));
        Assertions.assertEquals("作废", cache.getNameFromObject(Status.INACTIVE));

        Assertions.assertEquals(Status.ACTIVE, cache.getFromName(Status.class, "正常"));
        Assertions.assertEquals(Status.INACTIVE, cache.getFromName(Status.class, "作废"));
    }

    @Test
    void testNumber() {
        Assertions.assertEquals(0, cache.getIndexFromObject(Status.ACTIVE));
        Assertions.assertEquals(1, cache.getIndexFromObject(Status.INACTIVE));

        Assertions.assertEquals(Status.ACTIVE, cache.getFromIndex(Status.class, 0));
        Assertions.assertEquals(Status.INACTIVE, cache.getFromIndex(Status.class, 1));
    }

    @Test
    void testUser() {
        var user = new User(1, "blue", Status.ACTIVE);
        var json = JsonUtil.toString(user);
        User view = JsonUtil.fromString(json);
        Assertions.assertNotNull(json);
        Assertions.assertEquals(1, view.getId());
        Assertions.assertEquals("blue", view.getName());
        Assertions.assertEquals(Status.ACTIVE, view.getStatus());
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Integer id;
        private String name;
        private Status status;
    }

}
