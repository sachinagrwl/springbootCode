package com.nscorp.obis.response.data;

import java.util.List;

import org.springframework.data.domain.Page;

public class PaginatedResponse<T> {
	private List<T> content;
	private Integer currentPage;
	private Boolean hasNextPage;
	private Boolean hasPreviousPage;
	private Integer totalPages;
	private Long totalCountOfRecords;
	private Integer currentPageCountOfRecords;
	private Integer pageSize;

	public PaginatedResponse() {
		super();
	}

	public static <T> PaginatedResponse<T> of(Page<T> page) {
		PaginatedResponse<T> response = new PaginatedResponse<T>();
		response.setContent(page.getContent());
		response.setCurrentPage(page.getNumber());
		response.setHasNextPage(page.hasNext());
		response.setHasPreviousPage(page.hasPrevious());
		response.setTotalPages(page.getTotalPages());
		response.setTotalCountOfRecords(page.getTotalElements());
		response.setCurrentPageCountOfRecords(page.getNumberOfElements());
		response.setPageSize(page.getSize());
		return response;
	}
	
	public static <T> PaginatedResponse<T> of(List<T> content,Page<?> page) {
		PaginatedResponse<T> response = new PaginatedResponse<T>();
		response.setContent(content);
		response.setCurrentPage(page.getNumber());
		response.setHasNextPage(page.hasNext());
		response.setHasPreviousPage(page.hasPrevious());
		response.setTotalPages(page.getTotalPages());
		response.setTotalCountOfRecords(page.getTotalElements());
		response.setCurrentPageCountOfRecords(page.getNumberOfElements());
		response.setPageSize(page.getSize());
		return response;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(Boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalCountOfRecords() {
		return totalCountOfRecords;
	}

	public void setTotalCountOfRecords(Long totalCountOfRecords) {
		this.totalCountOfRecords = totalCountOfRecords;
	}

	public Integer getCurrentPageCountOfRecords() {
		return currentPageCountOfRecords;
	}

	public void setCurrentPageCountOfRecords(Integer currentPageCountOfRecords) {
		this.currentPageCountOfRecords = currentPageCountOfRecords;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PaginatedResponse [content=" + content + ", currentPage=" + currentPage + ", hasNextPage=" + hasNextPage
				+ ", hasPreviousPage=" + hasPreviousPage + ", totalPages=" + totalPages + ", totalCountOfRecords="
				+ totalCountOfRecords + ", currentPageCountOfRecords=" + currentPageCountOfRecords + ", pageSize="
				+ pageSize + "]";
	}
	
}
