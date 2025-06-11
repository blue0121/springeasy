package io.jutil.springeasy.mybatis.metadata;

/**
 * @author Jin Zheng
 * @since 2025-06-05
 */
public enum DatabaseProduct {
	MYSQL,
	POSTGRESQL,
	H2,
	;

	public static DatabaseProduct getProduct(String strProduct) {
		for (var product : DatabaseProduct.values()) {
			if (product.name().equalsIgnoreCase(strProduct)) {
				return product;
			}
		}
		throw new IllegalArgumentException("未知的数据库名称");
	}
}
