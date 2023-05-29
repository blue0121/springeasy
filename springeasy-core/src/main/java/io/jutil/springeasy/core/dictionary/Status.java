package io.jutil.springeasy.core.dictionary;

import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2023-05-27
 */
@Getter
public enum Status implements Dictionary {
	ACTIVE(0, "正常", Color.PRIMARY),
	INACTIVE(1, "作废", Color.DANGER),
	;

	private int index;
	private String label;
	private Color color;

	Status(int index, String label, Color color) {
		this.index = index;
		this.label = label;
		this.color = color;
		DictionaryCache.getInstance().add(this);
	}

	@Override
	public String getName() {
		return this.name();
	}
}
