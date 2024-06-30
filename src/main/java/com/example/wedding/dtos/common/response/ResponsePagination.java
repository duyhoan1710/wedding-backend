package com.example.wedding.dtos.common.response;


import java.util.List;

public record ResponsePagination<T>(int page, int limit, long totalResults, List<T> data) {}
