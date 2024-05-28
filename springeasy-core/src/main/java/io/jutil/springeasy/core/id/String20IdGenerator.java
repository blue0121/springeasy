package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.codec.Base32;
import io.jutil.springeasy.core.util.ByteUtil;
import io.jutil.springeasy.core.util.NetworkUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-15
 */
class String20IdGenerator extends AbstractIdGenerator<String> {
    private static final int SEQUENCE_BITS = 12;

    private final EpochOptions options;
    private final int timestampShift;
    private final byte[] ip;

	String20IdGenerator(EpochOptions options) {
        super(SEQUENCE_BITS);
        this.options = options;
        this.timestampShift = SEQUENCE_BITS;
        this.ip = ByteUtil.mask(NetworkUtil.getIpv4ForByteArray());
	}

    @Override
    public String generate() {
        var id = new byte[12];
        var timestamp = this.generateTimestamp();
        System.arraycopy(this.ip, 0, id, 0, 4);
        this.writeLong(id, timestamp, 4, 8);
        return Base32.encode(id);
    }

    private synchronized long generateTimestamp() {
        this.generateSequence();

        return ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | sequence;
    }

}
