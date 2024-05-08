package io.jutil.springeasy.core.http;

import java.net.http.HttpHeaders;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
public interface Response<T> {
	String CONCAT = ",";

	/**
	 * Http status code
	 *
	 * @return
	 */
	int getStatusCode();

	boolean is2xxStatus();

	boolean is4xxStatus();

	boolean is5xxStatus();


	/**
	 * Http headers
	 *
	 * @return
	 */
	HttpHeaders getHeaders();

	/**
	 * Http body
	 *
	 * @return
	 */
	T getBody();

}
