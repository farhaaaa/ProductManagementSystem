/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.services;

import com.coachbar.pms.entity.Product;
import com.coachbar.pms.repository.ProductRepository;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Farha Mansuri
 */
@Service
public class ProductService {

    private final static Log logger = LogFactory.getLog(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Map<String, String> createProduct(Product product) {
        try {
            productRepository.save(product);
            logger.info("Product saved successfully.");
            return Collections.singletonMap("success", "Product saved successfully");
        } catch (Exception e) {
            logger.error("Error occurred during createProduct():" + e);
            return Collections.singletonMap("error", "Error occurred while saving product");
        }
    }

    public boolean isProductExists(Long id) {
        return productRepository.existsById(id);
    }

    public Map<String, String> updateProduct(Long id, Product product) {
        try {
            product.setId(id);
            productRepository.save(product);
            logger.info("Product updated successfully.");
            return Collections.singletonMap("success", "Product updated successfully");
        } catch (Exception e) {
            logger.error("Error occurred during updateProduct():" + e);
            return Collections.singletonMap("error", "Error occurred while updating product");
        }
    }

    public Map<String, String> deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            logger.info("Product deleted successfully.");
            return Collections.singletonMap("success", "Product deleted successfully");
        } catch (Exception e) {
            logger.error("Error occurred during deleteProduct():" + e);
            return Collections.singletonMap("error", "Error occurred while deleting product");
        }
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
