package com.interviews.ProductMergerAPI.controllers;

import com.interviews.ProductMergerAPI.domains.UnifiedProduct;
import com.interviews.ProductMergerAPI.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/products")
    public ResponseEntity<UnifiedProduct[]> getProducts() {
        UnifiedProduct[] products = productService.getProducts("BASIC");
        return products.length == 0 ?
                new ResponseEntity<>(new UnifiedProduct[]{}, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/products/{productType}")
    public ResponseEntity<UnifiedProduct[]> getProductsByType(@PathVariable String productType) {
        UnifiedProduct[] products = productService.getProducts(productType);
        return products.length == 0 ?
                new ResponseEntity<>(new UnifiedProduct[]{}, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(products, HttpStatus.OK);
    }

}
