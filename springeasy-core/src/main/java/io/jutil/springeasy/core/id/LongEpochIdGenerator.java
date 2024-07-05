package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.util.NumberUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
class LongEpochIdGenerator extends AbstractIdGenerator<Long> {
    private static final int TOTAL_SHIFT = 63;
    private final EpochOptions options;
    private final int timestampBits;
    private final int ipShift;
    private final int timestampShift;
    private final long ip;

    LongEpochIdGenerator() {
        this(new EpochOptions());
    }

	LongEpochIdGenerator(EpochOptions options) {
        super(options.getSequenceBits());
        this.options = options;
        this.timestampBits = TOTAL_SHIFT - options.getMachineIdBits() - options.getSequenceBits();
        this.ipShift = timestampBits + options.getSequenceBits();
        this.timestampShift = options.getSequenceBits();
        this.ip = options.getMachineId() & NumberUtil.maskForInt(options.getMachineIdBits());
	}

    @Override
    public synchronized Long generate() {
        this.generateSequence();
        return (ip << ipShift)
                | ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | sequence;
    }

}
