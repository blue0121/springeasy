package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.parser.IdMetadata;
import io.jutil.springeasy.jdo.parser.IdType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Getter
@Setter
@NoArgsConstructor
public class DefaultIdMetadata extends DefaultFieldMetadata implements IdMetadata {
	private IdType idType;
	private GeneratorType generatorType;

	public void check() {
		super.check();
		AssertUtil.notNull(idType, "主键类型");
		AssertUtil.notNull(generatorType, "主键生成类型");
	}

}
