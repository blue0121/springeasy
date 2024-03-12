package io.jutil.springeasy.jdo.expression;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
public enum ExpressionOperator {
	AND(" and "),

	OR(" or ");


	private String name;

	ExpressionOperator(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
