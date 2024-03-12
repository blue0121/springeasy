package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.jdo.parser.VersionMetadata;
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
public class DefaultVersionMetadata extends DefaultFieldMetadata implements VersionMetadata {
	private boolean force;
	private int defaultValue;

}
