package io.jutil.springeasy.core.collection;

import lombok.Getter;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-11-08
 */
@Getter
public class Page {
    private final int pageIndex;
    private final int pageSize;
    private int totalPage;

    private int offset;
    private int total;
    private List<?> contents;
    private Sort sort;

    public Page() {
        this(1, 10);
    }

    public Page(int pageIndex) {
        this(pageIndex, 10);
    }

    public Page(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getContents() {
        return (List<T>) contents;
    }

    public void setTotal(int total) {
        this.total = total;
        this.totalPage = (total + pageSize - 1) / pageSize;
        this.offset = (pageIndex - 1) * pageSize;
    }

    public void setContents(List<?> contents) {
        this.contents = contents;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
