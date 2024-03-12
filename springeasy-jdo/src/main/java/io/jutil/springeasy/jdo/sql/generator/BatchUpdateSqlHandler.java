package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import io.jutil.springeasy.jdo.util.IdUtil;
import io.jutil.springeasy.jdo.util.VersionUtil;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
public class BatchUpdateSqlHandler extends AbstractSqlHandler {
	private final SqlHandler parameterSqlHandler;
	private final SqlHandler[] mapHandlers;

	public BatchUpdateSqlHandler(SqlHandler parameterSqlHandler, SqlHandler... mapHandlers) {
		this.parameterSqlHandler = parameterSqlHandler;
		this.mapHandlers = mapHandlers;
	}

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var objectList = request.getArgs();
		var id = IdUtil.checkSingleId(config);
		var isForceVersion = VersionUtil.isForce(config);
		var sqlItem = isForceVersion ? config.getSqlMetadata().getUpdateByIdAndVersion() : config.getSqlMetadata().getUpdateById();
		response.setSql(sqlItem.getSql());
		response.setForceVersion(isForceVersion);

		for (var object : objectList) {
			var req = SqlRequest.create(object, config, false);
			var resp = this.handle(config, req);
			for (var name : sqlItem.getParameterNameList()) {
				resp.addName(name);
			}
			parameterSqlHandler.handle(req, resp);

			response.addBatchParameter(resp.toParameterList());
		}
	}

	private SqlResponse handle(EntityMetadata config, SqlRequest request) {
		var response = new SqlResponse(config);
		for (var handler : mapHandlers) {
			handler.handle(request, response);
		}

		return response;
	}
}
