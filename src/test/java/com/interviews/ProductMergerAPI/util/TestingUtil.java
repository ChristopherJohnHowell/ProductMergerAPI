package com.interviews.ProductMergerAPI.util;

import com.interviews.ProductMergerAPI.client.model.Product;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;

import java.util.HashSet;
import java.util.Set;

public class TestingUtil {

    public Set<Product> generateProducts() {
        Set<Product> testProducts = new HashSet<>();
        testProducts.add(Product.builder().productUid(1999888).productType("BASIC").name("Super Chicken").fullUrl("www.superchicken.com").build());
        testProducts.add(Product.builder().productUid(123).productType("BASIC2").name("Super Beef").fullUrl("www.superbeef.com").build());
        return testProducts;
    }

    public Set<ProductPrice> generateProductPrices() {
        Set<ProductPrice> testProductPrices = new HashSet<>();
        testProductPrices.add(ProductPrice.builder().productUid(1999888).unitPrice(19.99).unitPriceMeasure("kg").unitPriceMeasureAmount(1).build());
        testProductPrices.add(ProductPrice.builder().productUid(123).unitPrice(25.00).unitPriceMeasure("kg").unitPriceMeasureAmount(2).build());
        return testProductPrices;
    }

}
