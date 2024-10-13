package io.jutil.springeasy.spring.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-10-13
 */
class YamlUtilTest {

	@Test
	void testParse() {
		var backup = YamlUtil.parse("classpath:/yaml/backup_1.yaml", BackupConfig.class);
		Assertions.assertNotNull(backup);

		var projectList = backup.getProjects();
		Assertions.assertEquals(1, projectList.size());
		var project = projectList.get(0);
		Assertions.assertEquals("project1", project.getName());
		Assertions.assertEquals("gs://localhost", project.getGcsUri());

		var instanceList = project.getInstances();
		Assertions.assertEquals(1, instanceList.size());
		var instance = instanceList.get(0);
		Assertions.assertEquals("sit", instance.getName());
		Assertions.assertEquals("bucket", instance.getTargetBucket());

		var storage = project.getStorage();
		Assertions.assertNotNull(storage);
		Assertions.assertTrue(storage.isEnabled());
		Assertions.assertEquals("S3Bucket", storage.getName());
		Assertions.assertEquals("S3", storage.getTargetType());
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class BackupConfig {
		private List<ProjectConfig> projects;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ProjectConfig {
		private String name;
		private String gcsUri;
		private StorageConfig storage;
		private List<InstanceConfig> instances;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class InstanceConfig {
		private String name;
		private String targetBucket;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class StorageConfig {
		private boolean enabled;
		private String name;
		private String targetType;
	}
}
