package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-17
 */
class String24IdGeneratorTest extends IdGeneratorTest<String> {
	String24IdGeneratorTest() {
        generator = new String24IdGenerator();
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
