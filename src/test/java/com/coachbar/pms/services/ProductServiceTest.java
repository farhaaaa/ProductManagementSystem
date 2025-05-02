/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.services;

import com.coachbar.pms.entity.Product;
import com.coachbar.pms.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

/**
 *
 * @author Farha Mansuri
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct_success() {

        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);

        when(productRepository.save(product)).thenReturn(product);

        Map<String, String> result = productService.createProduct(product);

        assertEquals("Product saved successfully", result.get("success"));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testCreateProduct_exception() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);

        when(productRepository.save(product)).thenThrow(new RuntimeException("DB error"));

        Map<String, String> result = productService.createProduct(product);

        assertEquals("Error occurred while saving product", result.get("error"));
        verify(productRepository, times(1)).save(product);
    }
}
