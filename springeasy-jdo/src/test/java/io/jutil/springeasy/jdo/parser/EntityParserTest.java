package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.jdo.annotation.Column;
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
	@CsvSource({"io.jutil.springeasy.jdo.parser.EntityParserTest$LongClass",
			"io.jutil.springeasy.jdo.parser.EntityParserTest$LongClass2"})
	void testParse(Class<?> clazz) {
		var facade = new ParserFacade(new MySQLDialect());
		var metadata = (EntityMetadata) facade.parse(clazz);
		this.checkTable(metadata, "c_long_class");

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
		this.checkColumn(columnMap.get("groupId"), false, false, false,
				"groupId", "group_id", "varchar(50)");
		this.checkColumn(columnMap.get("username"), false, false, true,
				"username", "username", "varchar(50)");
		this.checkField(transientMap.get("groupName"), "groupName", "group_name");

		var sql = metadata.getSqlMetadata();
		this.checkSql(sql.getSelectById(), "select * from c_long_class where id=?", List.of("id"));
		this.checkSql(sql.getSelectByIdList(), "select * from c_long_class where id in (%s)", List.of("id"));
		this.checkSql(sql.getInsert(), "insert into c_long_class (id,version,group_id,username) values (?,?,?,?)", List.of("id","version","groupId","username"));
		this.checkSql(sql.getUpdateById(), "update c_long_class set group_id=?,username=? where id=?", List.of("groupId","username","id"));
		this.checkSql(sql.getUpdateByIdAndVersion(), "update c_long_class set group_id=?,username=?,version=version+1 where id=? and version=?", List.of("groupId","username","id","version"));
		this.checkSql(sql.getDeleteById(), "delete from c_long_class where id=?", List.of("id"));
		this.checkSql(sql.getDeleteByIdList(), "delete from c_long_class where id in (%s)", List.of("id"));
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@Entity(table = "c_long_class")
	class LongClass {
		@Id
		private Long id;
		@Version
		private Integer version;
		@Column(name = "group_id", definition = "varchar(50)", nullable = false)
		private Integer groupId;
		@Column(name = "username", definition = "varchar(50)")
		private String username;
		@Transient
		private String groupName;
	}

	@NoArgsConstructor
	@Entity(table = "c_long_class")
	class LongClass2 {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		@Version
		public Integer version;
		@Column(name = "group_id", definition = "varchar(50)", nullable = false)
		public Integer groupId;
		@Column(name = "username", definition = "varchar(50)")
		public String username;
		@Transient
		public String groupName;
	}

}
