package io.jutil.springeasy.core.id;

import io.jutil.springeasy.internal.core.id.String16IdGenerator;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
public class String16IdGeneratorTest extends IdGeneratorTest<String> {
	public String16IdGeneratorTest() {
        var options = new EpochOptions();
        this.generator = new String16IdGenerator(options);
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
