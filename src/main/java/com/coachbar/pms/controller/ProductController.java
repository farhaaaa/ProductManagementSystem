/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.controller;

import com.coachbar.pms.entity.Product;
import com.coachbar.pms.services.ProductService;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Farha Mansuri
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> createProduct(@Valid @RequestBody Product product) {
        Map<String, String> resultMap = productService.createProduct(product);
        if (resultMap.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        if (!productService.isProductExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Product not found"));
        }
        Map<String, String> resultMap = productService.updateProduct(id, product);
        if (resultMap.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        if (!productService.isProductExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Product not found"));
        }
        Map<String, String> resultMap = productService.deleteProduct(id);
        if (resultMap.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        }
    }
}
