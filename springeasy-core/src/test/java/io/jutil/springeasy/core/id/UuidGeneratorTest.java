package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2022-08-17
 */
class UuidGeneratorTest extends IdGeneratorTest<String> {
	UuidGeneratorTest() {
        generator = new UuidGenerator();
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
