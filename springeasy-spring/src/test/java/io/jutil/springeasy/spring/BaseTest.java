package io.jutil.springeasy.spring;

import io.jutil.springeasy.core.http.HttpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@ActiveProfiles("http")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

	@LocalServerPort
	protected int port;

	@Autowired
	@Qualifier("localhost")
	protected HttpTemplate httpTemplate;

	protected void init() {

	}

	protected String buildUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

}
