package io.jutil.springeasy.mybatis.handler;

import com.alibaba.fastjson2.JSONArray;
import io.jutil.springeasy.mybatis.metadata.DatabaseInfo;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author Jin Zheng
 * @since 2025-08-31
 */
@MappedTypes(JSONArray.class)
public class FastjsonArrayTypeHandler extends JsonTypeHandler<JSONArray> {

	public FastjsonArrayTypeHandler(DatabaseInfo databaseInfo) {
		super(databaseInfo);
	}

	@Override
	protected JSONArray parse(String json) {
		if (json == null) {
			return null;
		}
		return JSONArray.parseArray(json);
	}

}
