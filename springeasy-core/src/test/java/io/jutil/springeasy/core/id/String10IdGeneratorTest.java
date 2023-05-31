package io.jutil.springeasy.core.id;

import io.jutil.springeasy.internal.core.id.String10IdGenerator;

/**
 * @author Jin Zheng
 * @since 2022-08-16
 */
public class String10IdGeneratorTest extends IdGeneratorTest<String> {
	public String10IdGeneratorTest() {
        var options = new EpochOptions();
        this.generator = new String10IdGenerator(options);
	}

    @Override
    protected String toString(String id) {
        return id;
    }

}
