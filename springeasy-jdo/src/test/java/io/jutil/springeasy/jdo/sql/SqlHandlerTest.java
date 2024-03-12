package io.jutil.springeasy.jdo.sql;

import io.jutil.springeasy.jdo.dialect.Dialect;
import io.jutil.springeasy.jdo.dialect.MySQLDialect;
import io.jutil.springeasy.jdo.model.GroupEntity;
import io.jutil.springeasy.jdo.model.GroupMapper;
import io.jutil.springeasy.jdo.model.ResultMapper;
import io.jutil.springeasy.jdo.model.UserEntity;
import io.jutil.springeasy.jdo.parser.impl.MetadataCache;
import io.jutil.springeasy.jdo.parser.impl.ParserFacade;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
public class SqlHandlerTest {
	protected final Dialect dialect;
	protected final ParserFacade parserFacade;
	protected final MetadataCache metadataCache;
	protected final SqlHandlerFacade facade;

	public SqlHandlerTest() {
		this.dialect = new MySQLDialect();
		this.parserFacade = new ParserFacade(dialect);
		this.metadataCache = parserFacade.getMetadataCache();
		this.facade = new SqlHandlerFacade();

		parserFacade.parse(GroupEntity.class);
		parserFacade.parse(GroupMapper.class);
		parserFacade.parse(ResultMapper.class);
		parserFacade.parse(UserEntity.class);
	}

}
