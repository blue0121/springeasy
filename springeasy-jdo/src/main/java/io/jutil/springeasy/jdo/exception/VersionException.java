package io.jutil.springeasy.jdo.exception;

/**
 * @author Jin Zheng
 * @since 2024-02-17
 */
public class VersionException extends JdoException {
	private static final long serialVersionUID = 1L;

	private final Class<?> clazz;

	public VersionException(Class<?> clazz) {
		this(clazz, "版本冲突");
	}

	public VersionException(Class<?> clazz, String message) {
		super(clazz.getName() + " " + message);
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
