package io.jutil.springeasy.jdo.expression;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
public interface Expression {

	Expression add(String sql);

	Expression add(Expression expression);

	static Expression and() {
		return new DefaultExpression(ExpressionOperator.AND);
	}

	static Expression or() {
		return new DefaultExpression(ExpressionOperator.OR);
	}

	static OrderBy orderBy() {
		return new DefaultOrderBy();
	}

}
