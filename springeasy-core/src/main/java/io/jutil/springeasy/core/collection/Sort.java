package io.jutil.springeasy.core.collection;

import io.jutil.springeasy.core.util.StringUtil;
import jakarta.validation.ValidationException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-17
 */
public class Sort {
	private final List<Order> orderList = new ArrayList<>();

	public Sort() {
	}

	public Sort(String field) {
		orderList.add(new Order(field));
	}

	public Sort(String field, Direction direction) {
		orderList.add(new Order(field, direction));
	}

	public Sort(String field, String direction) {
		orderList.add(new Order(field, direction));
	}

	public Sort(String field, int direction) {
		orderList.add(new Order(field, direction));
	}

	public void add(String field) {
		orderList.add(new Order(field, Direction.DESC));
	}

	public void add(String field, Direction direction) {
		orderList.add(new Order(field, direction));
	}

	public void add(String field, String direction) {
		orderList.add(new Order(field, direction));
	}

	public void add(String field, int direction) {
		orderList.add(new Order(field, direction));
	}

	public void check(Collection<String> validFieldList) {
		for (var order : orderList) {
			order.check(validFieldList);
		}
	}

	public String toOrderByString(Map<String, String> fieldMap) {
		if (orderList.isEmpty()) {
			return "";
		}

		var orderByList = orderList.stream()
				.map(e -> e.toOrderByString(fieldMap))
				.toList();
		return "ORDER BY " + StringUtil.join(orderByList, ",");
	}

	@Getter
	public static class Order {
		private final String field;
		private final Direction direction;

		public Order(String field) {
			this(field, Direction.DESC);
		}

		public Order(String field, Direction direction) {
			this.field = field;
			this.direction = direction == null ? Direction.DESC : direction;
		}

		public Order(String field, String direction) {
			this.field = field;
			this.direction = Direction.from(direction);
		}

		public Order(String field, int direction) {
			this.field = field;
			this.direction = Direction.from(direction);
		}

		public void check(Collection<String> validFieldList) {
			for (var validField : validFieldList) {
				if (validField.equalsIgnoreCase(field)) {
					return;
				}
			}
			throw new ValidationException("无效的排序字段: " + field);
		}

		public String toOrderByString(Map<String, String> fieldMap) {
			String alias = null;
			if (fieldMap != null && !fieldMap.isEmpty()) {
				alias = fieldMap.get(field);
			}
			if (alias == null || alias.isEmpty()) {
				return field + " " + direction.name();
			}
			return alias + " " + direction.name();
		}
	}

	public enum Direction {
		ASC,
		DESC,
		;

		public static Direction from(String text) {
			if (ASC.name().equalsIgnoreCase(text)) {
				return ASC;
			}
			return DESC;
		}

		public static Direction from(int order) {
			if (ASC.ordinal() == order) {
				return ASC;
			}
			return DESC;
		}
	}
}
