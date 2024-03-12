package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.jdo.annotation.Column;
import io.jutil.springeasy.jdo.annotation.Mapper;
import io.jutil.springeasy.jdo.dialect.MySQLDialect;
import io.jutil.springeasy.jdo.parser.impl.ParserFacade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
class MapperParserTest extends ParserTest {

	@ParameterizedTest
	@CsvSource({"io.jutil.springeasy.jdo.parser.MapperParserTest$CommonClass",
			"io.jutil.springeasy.jdo.parser.MapperParserTest$CommonClass2"})
	void testParse(Class<?> clazz) {
		var facade = new ParserFacade(new MySQLDialect());
		var metadata = facade.parse(clazz);

		Assertions.assertNotNull(metadata);
		Assertions.assertEquals(MetadataType.MAPPER, metadata.getMetadataType());
		Assertions.assertEquals(clazz, metadata.getTargetClass());

		var columnMap = metadata.getColumnMap();
		Assertions.assertEquals(3, columnMap.size());

		this.checkField(columnMap.get("groupId"), "groupId", "group_id");
		this.checkField(columnMap.get("username"), "username", "username");
		this.checkField(columnMap.get("password"), "password", "pwd");
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@Mapper
	class CommonClass {
		private Integer groupId;
		private String username;
		@Column(name = "pwd")
		private String password;
	}

	@NoArgsConstructor
	@Mapper
	class CommonClass2 {
		public Integer groupId;
		public String username;
		@Column(name = "pwd")
		public String password;
	}

}
