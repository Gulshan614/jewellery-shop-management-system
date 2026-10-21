package com.jewellery.shop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jewellery.shop.dto.ProductRequest;
import com.jewellery.shop.dto.ProductResponse;
import com.jewellery.shop.entity.Metal;
import com.jewellery.shop.service.ProductService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock ProductService service;
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ProductController(service)).build();
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any(ProductRequest.class))).thenReturn(new ProductResponse(
                1L, "Gold chain", "Chain", Metal.GOLD_22K, new BigDecimal("12.5"),
                new BigDecimal("12"), 8, null, null));

        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"name":"Gold chain","category":"Chain","metal":"GOLD_22K",
                 "weightGrams":12.5,"makingChargePercent":12,"stock":8}"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gold chain"));
    }

    @Test
    void createWithBlankNameReturns400() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"name":"","category":"Chain","metal":"GOLD_22K",
                 "weightGrams":12.5,"makingChargePercent":12,"stock":8}"""))
                .andExpect(status().isBadRequest());
    }
}
