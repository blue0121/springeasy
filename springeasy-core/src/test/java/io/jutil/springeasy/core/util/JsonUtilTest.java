package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
class JsonUtilTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void testToBytes() {
        Assertions.assertEquals(0, JsonUtil.toBytes(null).length);
        Assertions.assertArrayEquals(new byte[] {1}, JsonUtil.toBytes(new byte[] {1}));
        Assertions.assertArrayEquals("正常".getBytes(), JsonUtil.toBytes("正常"));

        var user = new User(1, "blue", DateUtil.now());
        var b = JsonUtil.toBytes(user);
        Assertions.assertTrue(b.length > 0);
    }

    @Test
    void testFromBytes1() {
        Assertions.assertNull(JsonUtil.fromBytes(null));

        var user = new User(1, "blue", DateUtil.now());
        var b = JsonUtil.toBytes(user);
        User view = JsonUtil.fromBytes(b);
        this.verify(user, view);
    }

    private void verify(User user, User view) {
        Assertions.assertEquals(user.getId(), view.getId());
        Assertions.assertEquals(user.getName(), view.getName());
        Assertions.assertEquals(user.getCreateTime(), view.getCreateTime());
    }

    @Test
    void testFromBytes2() {
        Assertions.assertNull(JsonUtil.fromBytes(null, Object.class));
        Assertions.assertArrayEquals(new byte[] {1}, JsonUtil.fromBytes(new byte[] {1}, byte[].class));
        Assertions.assertEquals("正常", JsonUtil.fromBytes("正常".getBytes(), String.class));

        var user = new User(1, "blue", DateUtil.now());
        var b = JsonUtil.toBytes(user);
        User view = JsonUtil.fromBytes(b, User.class);
        this.verify(user, view);
    }

    @Test
    void testToString() {
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
    void testOutput() {
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
    void testFromString() {
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
