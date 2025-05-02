/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.controller;

import com.coachbar.pms.entity.Product;
import com.coachbar.pms.services.ProductService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Farha Mansuri
 */
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }

    @Test
    void createProduct_AdminSuccess() throws Exception {
        when(productService.createProduct(any(Product.class)))
                .thenReturn(Collections.singletonMap("success", "Product saved successfully"));

        mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"productName\":\"Test Product\","
                        + "\"description\":\"Desc\","
                        + "\"price\":123.45,"
                        + "\"quantity\":10"
                        + "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value("Product saved successfully"));

        // 4) Verify service was called once
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ServiceError() throws Exception {
        when(productService.createProduct(any(Product.class)))
                .thenReturn(Collections.singletonMap("error", "Failed to create product"));

        mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"productName\":\"BadProduct\","
                        + "\"description\":\"Desc\","
                        + "\"price\":1.00,"
                        + "\"quantity\":1"
                        + "}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Failed to create product"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    
}
