package io.jutil.springeasy.jdo.sql;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jdo.sql.generator.BatchInsertSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.BatchUpdateSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.CountSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.DeleteBySqlHandler;
import io.jutil.springeasy.jdo.sql.generator.DeleteSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.ExistSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.GetFieldSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.GetIdSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.GetSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.IncSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.InsertSqlHandler;
import io.jutil.springeasy.jdo.sql.generator.UpdateSqlHandler;
import io.jutil.springeasy.jdo.sql.map.ColumnSqlHandler;
import io.jutil.springeasy.jdo.sql.map.IdSqlHandler;
import io.jutil.springeasy.jdo.sql.map.InsertIdSqlHandler;
import io.jutil.springeasy.jdo.sql.map.InsertVersionSqlHandler;
import io.jutil.springeasy.jdo.sql.map.UpdateIdSqlHandler;
import io.jutil.springeasy.jdo.sql.map.UpdateVersionSqlHandler;
import io.jutil.springeasy.jdo.sql.map.VersionSqlHandler;
import io.jutil.springeasy.jdo.sql.parameter.ParameterSqlHandler;

import java.util.EnumMap;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
public class SqlHandlerFacade {
	private final EnumMap<SqlType, List<SqlHandler>> handlerMap = new EnumMap<>(SqlType.class);

	public SqlHandlerFacade() {
		this.init();
	}

	private void init() {
		var insertId = new InsertIdSqlHandler();
		var updateId = new UpdateIdSqlHandler();
		var id = new IdSqlHandler();
		var column = new ColumnSqlHandler();
		var insertVersion = new InsertVersionSqlHandler();
		var updateVersion = new UpdateVersionSqlHandler();
		var version = new VersionSqlHandler();

		var param = new ParameterSqlHandler();

		this.addHandler(SqlType.INSERT, insertId, column, insertVersion, new InsertSqlHandler(), param);
		this.addHandler(SqlType.UPDATE, updateId, column, updateVersion, new UpdateSqlHandler(), param);
		this.addHandler(SqlType.INC, updateId, column, version, new IncSqlHandler(), param);
		this.addHandler(SqlType.COUNT, id, column, version, new CountSqlHandler(), param);
		this.addHandler(SqlType.GET, id, column, version, new GetSqlHandler(), param);
		this.addHandler(SqlType.GET_FIELD, id, column, version, new GetFieldSqlHandler(), param);
		this.addHandler(SqlType.EXIST, id, column, version, new ExistSqlHandler(), param);
		this.addHandler(SqlType.DELETE_BY, id, column, version, new DeleteBySqlHandler(), param);
		this.addHandler(SqlType.DELETE, new DeleteSqlHandler());
		this.addHandler(SqlType.GET_ID, new GetIdSqlHandler());
		this.addHandler(SqlType.BATCH_INSERT, new BatchInsertSqlHandler(param, insertId, column, insertVersion));
		this.addHandler(SqlType.BATCH_UPDATE, new BatchUpdateSqlHandler(param, updateId, column, updateVersion));
	}

	private void addHandler(SqlType type, SqlHandler...handlers) {
		var list = List.of(handlers);
		handlerMap.put(type, list);
	}

	private List<SqlHandler> getHandlers(SqlType type) {
		AssertUtil.notNull(type, "SqlType");
		var handlers = handlerMap.get(type);
		if (handlers == null || handlers.isEmpty()) {
			throw new UnsupportedOperationException("未知 SqlType: " + type);
		}
		return handlers;
	}

	public SqlResponse handle(SqlType type, SqlRequest request) {
		var response = new SqlResponse(request.getMetadata());
		var handlers = this.getHandlers(type);
		for (var handler : handlers) {
			handler.handle(request, response);
		}
		return response;
	}
}
