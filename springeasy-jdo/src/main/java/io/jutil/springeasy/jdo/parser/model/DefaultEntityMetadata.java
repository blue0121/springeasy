package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.parser.IdMetadata;
import io.jutil.springeasy.jdo.parser.MetadataType;
import io.jutil.springeasy.jdo.parser.SqlMetadata;
import io.jutil.springeasy.jdo.parser.TransientMetadata;
import io.jutil.springeasy.jdo.parser.VersionMetadata;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Getter
public class DefaultEntityMetadata extends DefaultMapperMetadata implements EntityMetadata {
	private String tableName;
	private IdMetadata idMetadata;
	private Map<String, IdMetadata> idMap;
	private VersionMetadata versionMetadata;
	private Map<String, TransientMetadata> transientMap;
	private SqlMetadata sqlMetadata;

	public DefaultEntityMetadata() {
		this.metadataType = MetadataType.ENTITY;
	}

	@Override
	public void check() {
		super.check();
		AssertUtil.notEmpty(tableName, "表名");
		AssertUtil.notEmpty(idMap, "主键配置");
		AssertUtil.notNull(transientMap, "额外字段配置");
		AssertUtil.notNull(sqlMetadata, "SQL配置");

		idMap.forEach((k, v) -> this.check(v));
		transientMap.forEach((k, v) -> this.check(v));
		if (sqlMetadata instanceof DefaultSqlMetadata s) {
			s.check();
		}
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setIdMap(Map<String, IdMetadata> idMap) {
		AssertUtil.notEmpty(idMap, "IdMetadataMap");
		if (idMap == null || idMap.isEmpty()) {
			this.idMap = Map.of();
		} else if (idMap.size() == 1) {
			this.idMetadata = idMap.entrySet().iterator().next().getValue();
			this.idMap = Collections.unmodifiableMap(idMap);
		} else {
			this.idMap = Collections.unmodifiableMap(idMap);
		}
	}

	public void setVersionMetadata(VersionMetadata versionMetadata) {
		if (this.versionMetadata != null) {
			throw new JdoException("只能有1个 @Version: " + classOperation.getTargetClass().getName());
		}
		this.versionMetadata = versionMetadata;
	}

	public void setTransientMap(Map<String, TransientMetadata> transientMap) {
		if (transientMap == null || transientMap.isEmpty()) {
			this.transientMap = Map.of();
		} else {
			this.transientMap = Collections.unmodifiableMap(transientMap);
		}
	}

	public void setSqlMetadata(SqlMetadata sqlMetadata) {
		this.sqlMetadata = sqlMetadata;
	}

}
