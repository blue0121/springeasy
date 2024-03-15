package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlConst;
import io.jutil.springeasy.jdo.sql.SqlParameter;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import io.jutil.springeasy.jdo.util.IdUtil;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class DeleteSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var args = request.getArgs();
		AssertUtil.notEmpty(args, "参数");

		IdUtil.checkSingleId(config);
		if (args.size() == 1) {
			var sqlItem = config.getSqlMetadata().getDeleteById();
			response.setSql(sqlItem.getSql());
			response.addParameter(SqlParameter.create(args.get(0)));
			return;
		}

		var sqlItem = config.getSqlMetadata().getDeleteByIdList();
		var sql = String.format(sqlItem.getSql(), StringUtil.repeat(SqlConst.PLACEHOLDER, args.size(), SqlConst.SEPARATOR));
		response.setSql(sql);
		for (var arg : args) {
			response.addParameter(SqlParameter.create(arg));
		}
	}
}
