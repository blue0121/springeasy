package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
import io.jutil.springeasy.jdo.annotation.Transient;
import io.jutil.springeasy.jdo.annotation.Version;
import io.jutil.springeasy.jdo.dialect.MySQLDialect;
import io.jutil.springeasy.jdo.parser.impl.ParserFacade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
class EntityParserTest extends ParserTest {

	@ParameterizedTest
	@CsvSource({"io.jutil.springeasy.jdo.parser.EntityParserTest$CommonClass",
			"io.jutil.springeasy.jdo.parser.EntityParserTest$CommonClass2"})
	void testParse(Class<?> clazz) {
		var facade = new ParserFacade(new MySQLDialect());
		var metadata = (EntityMetadata) facade.parse(clazz);
		this.checkTable(metadata, "c_common_class");

		var idMap = metadata.getIdMap();
		var columnMap = metadata.getColumnMap();
		var transientMap = metadata.getTransientMap();
		var fieldMap = metadata.getFieldMap();
		Assertions.assertEquals(1, idMap.size());
		Assertions.assertEquals(2, columnMap.size());
		Assertions.assertEquals(1, transientMap.size());
		Assertions.assertEquals(5, fieldMap.size());

		this.checkId(idMap.get("id"), IdType.LONG, GeneratorType.SNOWFLAKE,
				"id", "id");
		this.checkId(metadata.getIdMetadata(), IdType.LONG, GeneratorType.SNOWFLAKE,
				"id", "id");
		this.checkVersion(metadata.getVersionMetadata(), true, 1,
				"version", "version");
		this.checkColumn(columnMap.get("groupId"), false, false,
				"groupId", "group_id");
		this.checkColumn(columnMap.get("username"), false, false,
				"username", "username");
		this.checkField(transientMap.get("groupName"), "groupName", "group_name");

		var sql = metadata.getSqlMetadata();
		this.checkSql(sql.getSelectById(), "select * from c_common_class where id=?", List.of("id"));
		this.checkSql(sql.getSelectByIdList(), "select * from c_common_class where id in (%s)", List.of("id"));
		this.checkSql(sql.getInsert(), "insert into c_common_class (id,version,group_id,username) values (?,?,?,?)", List.of("id","version","groupId","username"));
		this.checkSql(sql.getUpdateById(), "update c_common_class set group_id=?,username=? where id=?", List.of("groupId","username","id"));
		this.checkSql(sql.getUpdateByIdAndVersion(), "update c_common_class set group_id=?,username=?,version=version+1 where id=? and version=?", List.of("groupId","username","id","version"));
		this.checkSql(sql.getDeleteById(), "delete from c_common_class where id=?", List.of("id"));
		this.checkSql(sql.getDeleteByIdList(), "delete from c_common_class where id in (%s)", List.of("id"));
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@Entity(table = "c_common_class")
	class CommonClass {
		@Id(generator = GeneratorType.SNOWFLAKE)
		private Long id;
		@Version
		private Integer version;
		private Integer groupId;
		private String username;
		@Transient
		private String groupName;
	}

	@NoArgsConstructor
	@Entity(table = "c_common_class")
	class CommonClass2 {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		@Version
		public Integer version;
		public Integer groupId;
		public String username;
		@Transient
		public String groupName;
	}

}
