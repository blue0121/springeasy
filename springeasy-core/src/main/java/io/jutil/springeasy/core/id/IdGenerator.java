package io.jutil.springeasy.core.id;

/**
 * @author Jin Zheng
 * @since 2023-05-30
 */
public interface IdGenerator<T> {

    T generate();

}
