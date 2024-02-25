package com.ottugi.curry.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PageUtil {
    public static <T> Page<T> convertResponseDtoPages(List<T> dtoList, int page, int size) {
        int totalItems = dtoList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);

        List<T> pageList = dtoList.subList(fromIndex, toIndex);
        return new PageImpl<>(pageList, PageRequest.of(page - 1, size), totalItems);
    }
}
