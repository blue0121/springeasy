package io.jutil.springeasy.jdo.parser;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface SqlItem {

	String getSql();

	List<String> getParameterNameList();
}
