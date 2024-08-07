package io.jutil.springeasy.core.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Slf4j
@RestController
public class TestController {

	@GetMapping("/test/{name}")
	public String sayGet(@PathVariable("name") String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@PostMapping(value = "/test")
	public String sayPost(@RequestBody String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@PatchMapping("/test")
	public String sayPatch(@RequestBody String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@PutMapping("/test")
	public String sayPut(@RequestBody String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@DeleteMapping("/test/{name}")
	public String sayDelete(@PathVariable("name") String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		String str = "name: {0}, size: {1}";
		return MessageFormat.format(str, file.getOriginalFilename(), file.getSize());
	}

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> download() {
		var in = TestController.class.getResourceAsStream("/json/string.json");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(in));
	}

}
