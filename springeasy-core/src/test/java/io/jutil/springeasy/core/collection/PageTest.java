package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-11-08
 */
class PageTest {

    final List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @CsvSource({"1,0", "2,2", "3,4", "4,6", "5,8", "6,10"})
    @ParameterizedTest
    void testOffset(int pageIndex, int offset) {
        var page = new Page(pageIndex, 2);
        page.setTotal(list.size());
        Assertions.assertEquals(offset, page.getOffset());
    }

    @Test
    void testContents() {
        var page = new Page();
        page.setContents(list);
        Assertions.assertEquals(list, page.getContents());
    }
}
