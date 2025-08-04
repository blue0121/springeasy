package io.jutil.springeasy.core.collection;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

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

    @Test
    void testCheck() {
        var list = List.of("id");
        var page = new Page();
        page.check(list);

        page.setSort(new Sort("id"));
        page.check(list);

        page.setSort(new Sort("id1"));
        Assertions.assertThrows(ValidationException.class, () -> page.check(list));
    }

    @Test
    void testSetSortIfAbsent() {
        var page = new Page();
        var sort1 = new Sort("id1");
        var sort2 = new Sort("id2");
        page.setSortIfAbsent(() -> sort1);
        Assertions.assertSame(sort1, page.getSort());

        page.setSortIfAbsent(() -> sort2);
        Assertions.assertSame(sort1, page.getSort());
    }

    @Test
    void testToOrderByString() {
        var page = new Page();
        Assertions.assertNull(page.toOrderByString(null));

        page.setSortIfAbsent(() -> new Sort("id"));
        Assertions.assertEquals("id DESC", page.toOrderByString(null));
        Assertions.assertEquals("eid DESC", page.toOrderByString(Map.of("id", "eid")));
    }
}
