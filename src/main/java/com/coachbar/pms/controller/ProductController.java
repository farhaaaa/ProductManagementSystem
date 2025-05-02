/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.controller;

import com.coachbar.pms.entity.Product;
import com.coachbar.pms.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Farha Mansuri
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Manage products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Add a new product (Admin only)", description = "Creates a new product. Only users with Admin role can perform this operation.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(schema = @Schema(example = "{\"message\": \"Product created successfully\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(example = "{\"error\": \"Failed to create product\"}"))),
        @ApiResponse(responseCode = "403", description = "Access denied - Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "Update an existing product (Admin only)", description = "Updates the details of a product by its ID. Only Admin users can access this endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product updated successfully",
                content = @Content(schema = @Schema(example = "{\"success\": \"Product updated successfully\"}"))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(example = "{\"message\": \"Product not found\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(example = "{\"error\": \"Error occurred while updating product\"}"))),
        @ApiResponse(responseCode = "403", description = "Access denied - Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "Delete a product by ID (Admin only)", description = "Deletes a product from the system by its ID. Accessible only to users with Admin authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product deleted successfully",
                content = @Content(schema = @Schema(example = "{\"success\": \"Product deleted successfully\"}"))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(example = "{\"message\": \"Product not found\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(example = "{\"error\": \"Error occurred while deleting product\"}"))),
        @ApiResponse(responseCode = "403", description = "Access denied - Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "Get all products (paginated)", description = "Returns a paginated and sorted list of products. Accessible to all authenticated users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products fetched successfully")
    })
    @SecurityRequirement(name = "bearerAuth")
    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ResponseEntity<Page<Product>> getAllProducts(@Parameter(description = "Page number (starts from 0)", example = "0") @RequestParam(defaultValue = "0") int page, @Parameter(description = "Number of items per page", example = "5") @RequestParam(defaultValue = "5") int size, @Parameter(description = "Field to sort by", example = "productName") @RequestParam(defaultValue = "productName") String sortBy, @Parameter(description = "Sort direction: asc or desc", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getAllProducts(pageable);

        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "Fetches a single product based on the provided ID. Accessible to all authenticated users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found and returned"),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(example = "\"Product not found with id: 10\"")))
    })
    @SecurityRequirement(name = "bearerAuth")
    @RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
