package com.nscorp.obis.response.data;

import lombok.Data;

import java.util.List;

@Data
public class PaginationWrapper {

    private List<?> pagedEntity;
    private int pageNumber;
    private int totalPages;
    private Long totalValues;

    public PaginationWrapper(List<?> pagedEntity, int pageNumber, int totalPages, Long totalValues) {
        this.pagedEntity = pagedEntity;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.totalValues = totalValues;
    }
}
