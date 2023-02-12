package io.jutil.springeasy.core.dict;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
public class Status extends Dictionary {
	public static final Status ACTIVE = new Status(0, "正常", Color.PRIMARY);
	public static final Status INACTIVE = new Status(1, "作废", Color.DANGER);

	private Status(int index, String name, Color color) {
		super(index, name, color);
	}

}
