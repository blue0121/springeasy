package io.jutil.springeasy.spring.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.codec.json.Formatter;
import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.spring.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Jin Zheng
 * @since 2025-12-28
 */
class UserControllerTest extends BaseTest {
	private final LocalDateTime dateTime = DateUtil.now();
	private final LocalDate date = LocalDate.now();
	private final LocalTime time = LocalTime.now();

	@BeforeEach
	void beforeEach() {
		this.init();
	}

	@Test
	void testCrud() {
		var user = new UserController.User();
		user.setName("test");
		user.setStatus(Status.ACTIVE);
		user.setDateTime(dateTime);
		user.setDate(date);
		user.setTime(time);

		var url = this.buildUrl("/users");
		var response = httpTemplate.post(url, Json.output(user));
		Assertions.assertEquals(200, response.getStatusCode());
		var jsonNode = JSONObject.parseObject(response.getBody());
		this.verify(jsonNode, "test", 1, dateTime, date, time);
		var id = jsonNode.getString("id");

		response = httpTemplate.get(url);
		Assertions.assertEquals(200, response.getStatusCode());
		var array = JSONArray.parseArray(response.getBody());
		Assertions.assertEquals(1, array.size());
		jsonNode = array.getJSONObject(0);
		this.verify(jsonNode, "test", 1, dateTime, date, time);

		user.setName("test2");
		user.setStatus(Status.DISABLED);

		url = this.buildUrl("/users/" + id);
		response = httpTemplate.put(url, Json.output(user));
		Assertions.assertEquals(200, response.getStatusCode());
		jsonNode = JSONObject.parseObject(response.getBody());
		this.verify(jsonNode, "test2", 2, dateTime, date, time);

		response = httpTemplate.delete(url);
		Assertions.assertEquals(204, response.getStatusCode());

		url = this.buildUrl("/users");
		response = httpTemplate.get(url);
		Assertions.assertEquals(200, response.getStatusCode());
		array = JSONArray.parseArray(response.getBody());
		Assertions.assertTrue(array.isEmpty());
	}

	@Test
	void testCreate_failed() {
		var user = new UserController.User();
		user.setName("test");
		user.setDateTime(dateTime);
		user.setDate(date);
		user.setTime(time);

		var url = this.buildUrl("/users");
		var response = httpTemplate.post(url, Json.output(user));
		Assertions.assertEquals(400, response.getStatusCode());
		var jsonNode = JSONObject.parseObject(response.getBody());
		this.verifyFailed(jsonNode, 400_000, "无效参数: 状态不能为空");
	}

	@Test
	void testUpdate_failed() {
		var user = new UserController.User();
		user.setName("test");
		user.setStatus(Status.ACTIVE);
		user.setDateTime(dateTime);
		user.setDate(date);
		user.setTime(time);

		var url = this.buildUrl("/users");
		var response = httpTemplate.post(url, Json.output(user));
		Assertions.assertEquals(200, response.getStatusCode());
		var jsonNode = JSONObject.parseObject(response.getBody());
		this.verify(jsonNode, "test", 1, dateTime, date, time);

		user.setName("test2");

		url = this.buildUrl("/users/-1");
		response = httpTemplate.put(url, Json.output(user));
		Assertions.assertEquals(404, response.getStatusCode());
		jsonNode = JSONObject.parseObject(response.getBody());
		this.verifyFailed(jsonNode, 404_002, "-1 不存在");
	}

	private void verify(JSONObject node, String name, int status, LocalDateTime dateTime,
	                    LocalDate date, LocalTime time) {
		Assertions.assertNotNull(node);
		Assertions.assertEquals(name, node.getString("name"));
		Assertions.assertEquals(status, node.getIntValue("status"));
		Assertions.assertEquals(Formatter.DATE_TIME.format(dateTime), node.getString("dateTime"));
		Assertions.assertEquals(Formatter.DATE.format(date), node.getString("date"));
		Assertions.assertEquals(Formatter.TIME.format(time), node.getString("time"));
	}

	private void verifyFailed(JSONObject node, int code, String message) {
		Assertions.assertNotNull(node);
		Assertions.assertEquals(code, node.getIntValue("code"));
		Assertions.assertEquals(message, node.getString("message"));
	}
}
