package com.example.trenes.Apis;

import java.util.List;

public class PaginationContainer<T> {
    public List<T> results;
    public Long timestamp;
    public Integer total;

    public List<T> getResults() {
        return this.results;
    }

    public Integer getTotal() {
        return this.total;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

}
