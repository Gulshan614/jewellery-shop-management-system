package com.jewellery.shop.service.impl;

import com.jewellery.shop.dto.ProductRequest;
import com.jewellery.shop.dto.ProductResponse;
import com.jewellery.shop.entity.Product;
import com.jewellery.shop.exception.ResourceNotFoundException;
import com.jewellery.shop.repository.ProductRepository;
import com.jewellery.shop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return ProductResponse.from(repository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return ProductResponse.from(find(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> search(String q, String category, Pageable pageable) {
        String query = (q == null || q.isBlank()) ? null : q.trim();
        String cat = (category == null || category.isBlank()) ? null : category.trim();
        return repository.search(query, cat, pageable).map(ProductResponse::from);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = find(id);
        apply(product, request);
        return ProductResponse.from(repository.save(product));
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Product find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    private void apply(Product p, ProductRequest r) {
        p.setName(r.name().trim());
        p.setCategory(r.category().trim());
        p.setMetal(r.metal());
        p.setWeightGrams(r.weightGrams());
        p.setMakingChargePercent(r.makingChargePercent());
        p.setStock(r.stock());
    }
}
