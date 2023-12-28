package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
class String20IdGeneratorTest extends IdGeneratorTest<String> {
	String20IdGeneratorTest() {
        var options = new EpochOptions();
        this.generator = new String20IdGenerator(options);
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
