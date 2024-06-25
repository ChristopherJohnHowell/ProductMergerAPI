package com.interviews.ProductMergerAPI.controllers;

import com.interviews.ProductMergerAPI.domains.UnifiedProduct;
import com.interviews.ProductMergerAPI.services.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    /**
     * Returns Unified Products based on provided ProductType request parameter
     *
     * @param productType Type of Product
     * @return UnifiedProducts Array
     */
    @GetMapping(path = "/products")
    public ResponseEntity<UnifiedProduct[]> getProductsByType(@RequestParam(name = "productType", required = false) final String productType) {
        UnifiedProduct[] products = productService.getProducts(productType);
        return products.length == 0 ?
                new ResponseEntity<>(new UnifiedProduct[]{}, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(products, HttpStatus.OK);
    }

}
