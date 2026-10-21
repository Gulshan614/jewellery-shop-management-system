package com.jewellery.shop.service;

import com.jewellery.shop.dto.ProductRequest;
import com.jewellery.shop.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse getById(Long id);
    Page<ProductResponse> search(String q, String category, Pageable pageable);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
}
