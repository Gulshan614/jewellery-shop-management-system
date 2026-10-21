package com.jewellery.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jewellery.shop.dto.ProductRequest;
import com.jewellery.shop.dto.ProductResponse;
import com.jewellery.shop.entity.Metal;
import com.jewellery.shop.entity.Product;
import com.jewellery.shop.exception.ResourceNotFoundException;
import com.jewellery.shop.repository.ProductRepository;
import com.jewellery.shop.service.impl.ProductServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock ProductRepository repository;
    @InjectMocks ProductServiceImpl service;

    private ProductRequest request() {
        return new ProductRequest("Gold chain", "Chain", Metal.GOLD_22K,
                new BigDecimal("12.5"), new BigDecimal("12"), 8);
    }

    @Test
    void createSavesAndReturnsProduct() {
        when(repository.save(any(Product.class))).thenAnswer(i -> {
            Product p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProductResponse response = service.create(request());

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Gold chain");
        assertThat(response.metal()).isEqualTo(Metal.GOLD_22K);
        assertThat(response.stock()).isEqualTo(8);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateChangesFields() {
        Product existing = new Product();
        existing.setId(5L);
        when(repository.findById(5L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        ProductResponse response = service.update(5L, request());

        assertThat(response.category()).isEqualTo("Chain");
        assertThat(existing.getStock()).isEqualTo(8);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(7L)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteRemovesExistingProduct() {
        Product existing = new Product();
        when(repository.findById(3L)).thenReturn(Optional.of(existing));

        service.delete(3L);

        verify(repository).delete(existing);
    }
}
