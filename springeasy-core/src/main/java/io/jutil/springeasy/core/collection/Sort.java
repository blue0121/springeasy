package io.jutil.springeasy.core.collection;

import io.jutil.springeasy.core.util.StringUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-11-17
 */
public class Sort {
	private final List<Order> orderList = new ArrayList<>();

	public Sort(String field) {
		orderList.add(new Order(field));
	}

	public Sort(String field, Direction direction) {
		orderList.add(new Order(field, direction));
	}

	public Sort(String field, String direction) {
		orderList.add(new Order(field, direction));
	}

	public void add(String field, Direction direction) {
		orderList.add(new Order(field, direction));
	}

	public void add(String field, String direction) {
		orderList.add(new Order(field, direction));
	}

	public String toOrderBy(String prefix) {
		List<String> orderByList = orderList.stream().map(e -> e.toString(prefix)).toList();
		return StringUtil.join(orderByList, ",");
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

		public String toString(String prefix) {
			if (prefix == null || prefix.isEmpty()) {
				return field + " " + direction.name().toLowerCase();
			}
			return prefix + "." + field + " " + direction.name().toLowerCase();
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
	}
}
