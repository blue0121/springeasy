package io.jutil.springeasy.spring.controller;

import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.spring.exception.BaseErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jin Zheng
 * @since 2025-12-28
 */
@RestController
@RequestMapping(value = "/users",
		produces = {MediaType.APPLICATION_JSON_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserController {
	private final Map<Long, User> userMap = new ConcurrentHashMap<>();
	private final AtomicLong idCounter = new AtomicLong();

	@PostMapping
	public User create(@RequestBody @Valid User user) {
		user.setId(idCounter.incrementAndGet());
		log.info("Create user: {}", Json.output(user));
		userMap.put(user.getId(), user);
		return user;
	}

	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody @Valid User user) {
		if (!userMap.containsKey(id)) {
			throw BaseErrorCode.NOT_EXISTS.newException(id);
		}
		user.setId(id);
		log.info("Update user: {}", Json.output(user));
		userMap.put(id, user);
		return user;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		log.info("Delete user.id: {}", id);
		var rs = userMap.remove(id);
		if (rs == null) {
			throw BaseErrorCode.NOT_EXISTS.newException(id);
		}
	}

	@GetMapping
	public Collection<User> findAll() {
		log.info("List all users");
		return userMap.values();
	}

	@Getter
	@Setter
	public static class User {
		@NotNull(message = "ID不能为空")
		private long id;

		@NotEmpty(message = "用户名不能为空")
		private String name;

		@NotNull(message = "状态不能为空")
		private Status status;

		private LocalDateTime dateTime;
		private LocalDate date;
		private LocalTime time;
	}

}
