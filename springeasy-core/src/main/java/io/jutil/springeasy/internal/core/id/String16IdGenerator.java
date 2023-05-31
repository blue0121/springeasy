package io.jutil.springeasy.internal.core.id;

import io.jutil.springeasy.core.codec.Base32;
import io.jutil.springeasy.core.id.EpochOptions;
import io.jutil.springeasy.core.util.NetworkUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-15
 */
public class String16IdGenerator extends AbstractIdGenerator<String> {
    private static final int SEQUENCE_BITS = 12;

    private final EpochOptions options;
    private final int timestampShift;
    private final byte[] ip;

	public String16IdGenerator(EpochOptions options) {
        super(SEQUENCE_BITS);
        this.options = options;
        this.timestampShift = SEQUENCE_BITS;
        this.ip = NetworkUtil.getIpForByteArray();
	}

    @Override
    public String generate() {
        var id = new byte[10];
        var timestamp = this.generateTimestamp();
        this.writeTimestamp(id, timestamp, 7);
        System.arraycopy(this.ip, 1, id, 7, 3);
        return Base32.encode(id);
    }

    private synchronized long generateTimestamp() {
        this.generateSequence();

        return ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | sequence;
    }

}
