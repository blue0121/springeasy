package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.collection.ConcurrentHashSet;
import io.jutil.springeasy.core.util.WaitUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author Jin Zheng
 * @since 2022-08-12
 */
abstract class IdGeneratorTest<T> {
    protected IdGenerator<T> generator;

    @Test
    void testId() {
        int count = 5;
        Set<String> set = new ConcurrentHashSet<>();
        for (int i = 0; i < count; i++) {
            var id = this.toString(generator.generate());
            set.add(id);
            System.out.printf("ID值: %s\n", id);
        }
        Assertions.assertEquals(count, set.size());
    }

    @Test
    void testSingle() {
        int count = 1_000_000;
        Set<T> set = new ConcurrentHashSet<>();
        var start = System.currentTimeMillis();
        for (int j = 0; j < count; j++) {
            this.addId(set);
        }
        var used = System.currentTimeMillis() - start;
        System.out.printf("单线程, 用时: %d ms, 速度: %g/ms.\n", used, (double)set.size() / used);
        Assertions.assertEquals(count, set.size(), "单线程下ID有重复");
    }

    @Test
    void testMulti() {
        int threads = 50;
        int count = 100_000;
        Set<T> set = new ConcurrentHashSet<>();
        var executor = Executors.newFixedThreadPool(threads);
        var latch = new CountDownLatch(threads);
        var start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < count; j++) {
                        this.addId(set);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        WaitUtil.await(latch);
        var used = System.currentTimeMillis() - start;
        System.out.printf("多线程, 用时: %d ms, 速度: %g/ms.\n", used, (double)set.size() / used);
        Assertions.assertEquals(threads * count, set.size(), "多线程下ID有重复");
    }

    protected void addId(Set<T> set) {
        var id = generator.generate();
        if (!set.add(id)) {
            System.out.printf("重复ID: %s\n", this.toString(id));
        }
    }

    protected abstract String toString(T id);

}
