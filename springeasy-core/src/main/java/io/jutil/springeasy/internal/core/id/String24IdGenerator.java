package io.jutil.springeasy.internal.core.id;


import io.jutil.springeasy.core.codec.Base32;
import io.jutil.springeasy.core.util.NetworkUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-17
 */
public class String24IdGenerator extends AbstractIdGenerator<String> {
	private static final int SEQUENCE_BITS = 24;

    private final byte[] ip;

	public String24IdGenerator() {
        super(SEQUENCE_BITS);
		this.ip = NetworkUtil.getIpForByteArray();
	}

    @Override
    public synchronized String generate() {
	    var id = new byte[15];
	    this.generateTimestamp(id);
	    System.arraycopy(this.ip, 0, id, 11, 4);
	    return Base32.encode(id);
    }

	private synchronized void generateTimestamp(byte[] id) {
		this.generateSequence();
		this.writeTimestamp(id, lastTimestamp, 8);
		this.writeTimestamp(id, sequence, 3);
	}

}
