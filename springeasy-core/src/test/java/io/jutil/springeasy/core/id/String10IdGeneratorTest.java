package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
class String10IdGeneratorTest extends IdGeneratorTest<String> {
	String10IdGeneratorTest() {
        var options = new EpochOptions();
        this.generator = new String10IdGenerator(options);
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
