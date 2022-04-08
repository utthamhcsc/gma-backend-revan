package com.catalog.productcatalog.util;

import org.springframework.data.domain.PageImpl;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PageUtil
{
    default <T> Page<T> createPageFromList(final List<T> list, final Pageable pageable) {
        if (list == null) {
            throw new IllegalArgumentException("To create a Page, the list mustn't be null!");
        }
        final int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > list.size()) {
            return (Page<T>)new PageImpl((List)new ArrayList(), pageable, 0L);
        }
        final int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
        return (Page<T>)new PageImpl((List)list.subList(startOfPage, endOfPage), pageable, (long)list.size());
    }
}
