package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
class LongEpochIdGeneratorTest extends IdGeneratorTest<Long> {
	LongEpochIdGeneratorTest() {
		var options = new EpochOptions();
		options.setMachineIdBits(8);
		options.setSequenceBits(12);
        this.generator = new LongEpochIdGenerator(options);
	}

    @Override
    protected String toString(Long id) {
        return Long.toHexString(id);
    }
}
