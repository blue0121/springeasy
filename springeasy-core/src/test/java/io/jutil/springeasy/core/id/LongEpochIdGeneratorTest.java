package io.jutil.springeasy.core.id;

import io.jutil.springeasy.internal.core.id.LongEpochIdGenerator;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
class LongEpochIdGeneratorTest extends IdGeneratorTest<Long> {
	LongEpochIdGeneratorTest() {
		var options = new EpochOptions();
		options.setIpBits(8);
		options.setSequenceBits(12);
        this.generator = new LongEpochIdGenerator(options);
	}

    @Override
    protected String toString(Long id) {
        return Long.toHexString(id);
    }
}
