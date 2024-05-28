package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.codec.Uuid;
import io.jutil.springeasy.core.util.ByteUtil;
import io.jutil.springeasy.core.util.NetworkUtil;

/**
 * @author Jin Zheng
 * @since 2024-05-09
 */
class UuidGenerator extends AbstractIdGenerator<String> {
	private static final int SEQUENCE_BITS = 16;
	private final byte[] addr;

	UuidGenerator() {
		super(SEQUENCE_BITS);
		this.addr = ByteUtil.mask(NetworkUtil.getHardwareAddress());
	}

	@Override
	public synchronized String generate() {
		var id = new byte[16];
		System.arraycopy(addr, 0, id, 0, 6);
		this.generateTimestamp(id);
		return Uuid.encode(id);
	}

	private synchronized void generateTimestamp(byte[] id) {
		this.generateSequence();
		this.writeLong(id, lastTimestamp, 6, 8);
		id[14] = (byte) ((sequence >> 8) & 0xff);
		id[15] = (byte) (sequence & 0xff);
	}
}
