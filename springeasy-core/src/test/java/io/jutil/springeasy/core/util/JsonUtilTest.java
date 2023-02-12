package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
public class JsonUtilTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public JsonUtilTest() {
	}

    @Test
    public void testToString() {
        var now = DateUtil.now();
        var user = new User(1, "blue", now);
        var json = JsonUtil.toString(user);
        System.out.println(json);
        var obj = JSON.parseObject(json);
        Assertions.assertEquals(1, obj.getIntValue("id"));
        Assertions.assertEquals("blue", obj.getString("name"));
        Assertions.assertEquals(formatter.format(now), obj.getString("createTime"));
        Assertions.assertEquals(User.class.getName(), obj.getString("@type"));
    }

    @Test
    public void testOutput() {
        var now = DateUtil.now();
        var user = new User(1, "blue", now);
        var json = JsonUtil.output(user);
        System.out.println(json);
        var obj = JSON.parseObject(json);
        Assertions.assertEquals(1, obj.getIntValue("id"));
        Assertions.assertEquals("blue", obj.getString("name"));
        Assertions.assertEquals(formatter.format(now), obj.getString("createTime"));
        Assertions.assertFalse(obj.containsKey("@type"));
    }

    @Test
    public void testFromString() {
        var json1 = "{\"@type\":\"io.jutil.springeasy.core.util.JsonUtilTest$User\",\"createTime\":\"2022-12-20 14:50:53\",\"id\":1,\"name\":\"blue\"}";
        var json2 = "{\"createTime\":\"2022-12-20 14:50:54\",\"id\":1,\"name\":\"blue\"}";

        User user1 = JsonUtil.fromString(json1);
        Assertions.assertEquals(1, user1.getId());
        Assertions.assertEquals("blue", user1.getName());

        User user2 = JsonUtil.fromString(json2, User.class);
        Assertions.assertEquals(1, user2.getId());
        Assertions.assertEquals("blue", user2.getName());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Integer id;
        private String name;
        private LocalDateTime createTime;
    }

}
