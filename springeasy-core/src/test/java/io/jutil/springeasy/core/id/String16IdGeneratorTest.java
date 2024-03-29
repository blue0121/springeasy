package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
class String16IdGeneratorTest extends IdGeneratorTest<String> {
	String16IdGeneratorTest() {
        var options = new EpochOptions();
        this.generator = new String16IdGenerator(options);
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
