package com.techdevsolutions.springBoot.beans;

import java.util.Objects;

public class Filter {
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";
    public static final String FILTER_LOGIC_AND = "and";
    public static final String FILTER_LOGIC_OR = "or";
    public static final String FILTER_LOGIC_NOT = "not";

    public static final Integer DEFAULT_SIZE = 10;
    public static final Integer DEFAULT_PAGE = 0;
    public static final String DEFAULT_ORDER = Filter.SORT_DESC;
    public static final String DEFAULT_SORT = "updatedDate";
    public static final String DEFAULT_FILTER_LOGIC = Filter.FILTER_LOGIC_AND;

    private Integer size = Filter.DEFAULT_SIZE;
    private Integer page = Filter.DEFAULT_PAGE;
    private String sort = Filter.DEFAULT_SORT;
    private String order = Filter.DEFAULT_ORDER;
    private String filters = "";
    private String filterLogic = Filter.FILTER_LOGIC_AND;

    public Filter() {
    }

    public Filter(Integer size, Integer page, String sort, String order, String filters, String filterLogic) {
        this.size = size;
        this.page = page;
        this.sort = sort;
        this.order = order;
        this.filters = filters;
        this.filterLogic = filterLogic;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getFilterLogic() {
        return filterLogic;
    }

    public void setFilterLogic(String filterLogic) {
        this.filterLogic = filterLogic;
    }

}
