package io.jutil.springeasy.internal.core.id;

import io.jutil.springeasy.core.id.EpochOptions;
import io.jutil.springeasy.core.util.NetworkUtil;
import io.jutil.springeasy.core.util.NumberUtil;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
public class LongEpochIdGenerator extends AbstractIdGenerator<Long> {
    private final EpochOptions options;
    private final long sequenceShift;
    private final long timestampShift;
    private final long ip;

    public LongEpochIdGenerator() {
        this(new EpochOptions());
    }

	public LongEpochIdGenerator(EpochOptions options) {
        super(options.getSequenceBits());
        this.options = options;
        this.sequenceShift = options.getIpBits();
        this.timestampShift = options.getIpBits() + options.getSequenceBits();
        this.ip = NetworkUtil.getIpForInt() & NumberUtil.maskForInt(options.getIpBits());
	}

    @Override
    public synchronized Long generate() {
        this.generateSequence();

        return ((lastTimestamp - options.getEpochMillis()) << timestampShift)
                | (sequence << sequenceShift)
                | ip;
    }

}
