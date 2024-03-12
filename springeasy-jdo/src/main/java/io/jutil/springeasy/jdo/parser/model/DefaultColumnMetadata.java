package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.jdo.parser.ColumnMetadata;
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
public class DefaultColumnMetadata extends DefaultFieldMetadata implements ColumnMetadata {
	private boolean mustInsert;
	private boolean mustUpdate;

}
