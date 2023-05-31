package io.jutil.springeasy.core.id;

import io.jutil.springeasy.internal.core.id.String24IdGenerator;

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
