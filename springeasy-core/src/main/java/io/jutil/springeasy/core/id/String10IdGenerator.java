package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.codec.Base32;

/**
 * @author Jin Zheng
 * @since 2022-08-22
 */
class String10IdGenerator extends AbstractIdGenerator<String> {
    private static final int SEQUENCE_BITS = 12;

    private final EpochOptions options;
    private final int timestampShift;

	public String10IdGenerator(EpochOptions options) {
        super(SEQUENCE_BITS);
        this.options = options;
        this.timestampShift = SEQUENCE_BITS;
	}

    @Override
    public String generate() {
        var id = new byte[6];
        var timestamp = this.generateTimestamp();
        this.writeTimestamp(id, timestamp, 6);
        return Base32.encode(id);
    }

    private synchronized long generateTimestamp() {
        this.generateSequence();

        return ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | sequence;
    }

}
