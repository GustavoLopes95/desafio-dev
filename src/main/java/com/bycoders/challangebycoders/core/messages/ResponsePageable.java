package com.bycoders.challangebycoders.core.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePageable {
    private Long count;

    /**
     * page number, 0 indicate the first page.
     */
    private Integer pageNumber;

    /**
     * size of page, 0 indicate infinite-sized.
     */
    private Integer pageSize;

    /**
     * Offset from the of pagination.
     */
    private Long offset;

    /**
     * the number total of pages.
     */
    private Integer TotalPages;

    public ResponsePageable(Long count, Integer pageNumber, Integer pageSize, Long offset, Integer totalPages) {
        this.count = count;
        this.pageNumber = pageNumber + 1;
        this.pageSize = pageSize;
        this.offset = offset;
        TotalPages = totalPages;
    }
}
