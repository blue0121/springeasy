package io.jutil.springeasy.mybatis.handler;

import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.mybatis.metadata.DatabaseInfo;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author Jin Zheng
 * @since 2025-08-31
 */
@MappedTypes(JSONObject.class)
public class FastjsonObjectTypeHandler extends JsonTypeHandler<JSONObject> {

	public FastjsonObjectTypeHandler(DatabaseInfo databaseInfo) {
		super(databaseInfo);
	}

	@Override
	protected JSONObject parse(String json) {
		if (json == null) {
			return null;
		}
		return JSONObject.parseObject(json);
	}

}
