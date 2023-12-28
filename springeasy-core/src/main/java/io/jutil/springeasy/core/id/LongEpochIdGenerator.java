package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.util.NumberUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
class LongEpochIdGenerator extends AbstractIdGenerator<Long> {
    private final EpochOptions options;
    private final int sequenceShift;
    private final int timestampShift;
    private final long ip;

	LongEpochIdGenerator(EpochOptions options) {
        super(options.getSequenceBits());
        this.options = options;
        this.sequenceShift = options.getMachineIdBits();
        this.timestampShift = options.getMachineIdBits() + options.getSequenceBits();
        this.ip = options.getMachineId() & NumberUtil.maskForInt(options.getMachineIdBits());
	}

    @Override
    public synchronized Long generate() {
        this.generateSequence();

        return ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | (sequence << sequenceShift)
                | ip;
    }

}
