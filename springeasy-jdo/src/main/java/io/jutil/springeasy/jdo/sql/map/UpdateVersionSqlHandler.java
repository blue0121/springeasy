package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.exception.VersionException;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class UpdateVersionSqlHandler extends VersionSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		super.handle(request, response);

		var version = request.getMetadata().getVersionMetadata();
		if (version == null || !version.isForce()) {
			return;
		}

		var object = response.toParamMap().get(version.getFieldName());
		if (object == null) {
			throw new VersionException(request.getTargetClass(), "缺少版本号");
		}
	}
}
