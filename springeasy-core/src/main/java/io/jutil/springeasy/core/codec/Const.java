package io.jutil.springeasy.core.codec;

/**
 * @author Jin Zheng
 * @since 2023-04-07
 */
class Const {
	static final int INC_LEFT_SHIFT = 7;
	static final int INC_CAP = 1 << INC_LEFT_SHIFT;
	static final int BYTE_LEN = 1;
	static final int SHORT_LEN = 2;
	static final int INT_LEN = 4;
	static final int LONG_LEN = 8;
	static final int ZERO_IDX = 0;
	static final int LEN_EMPTY = 0;

	static final int MAX_VAR_INT_LEN = 5;
	static final int MAX_VAR_LONG_LEN = 10;

	private Const() {
	}
}
