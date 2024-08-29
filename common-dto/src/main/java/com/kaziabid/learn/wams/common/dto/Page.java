package com.kaziabid.learn.wams.common.dto;

/**
 * @author Kazi Abid
 */
public record Page(long pageNo, long totalPages, int itemsPerPage) implements CommonDTO {
    public static Page build() {
        return new Page(1, 0, 10);
    }

    public static Page build(long totalPages) {
        return new Page(1, totalPages, 10);
    }

    public static Page build(long totalPages, int itemsPerPage) {
        return new Page(1, totalPages, itemsPerPage);
    }

    public boolean canHaveNext() {
        return pageNo < totalPages;
    }

    public Page nextPage() {
        return new Page(pageNo + 1, totalPages, itemsPerPage);
    }
}
