package com.interviews.ProductMergerAPI.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviews.ProductMergerAPI.client.ProductClient;
import com.interviews.ProductMergerAPI.client.ProductPriceClient;
import com.interviews.ProductMergerAPI.client.model.Product;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;
import com.interviews.ProductMergerAPI.domains.UnifiedProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductService {

    public UnifiedProduct[] getProducts(String productType) {
        Set<Product> products = new ProductClient(new ObjectMapper()).getProducts();
        if (products == null) {
            log.error("Products API returns null!");
            return new UnifiedProduct[]{};
        }

        Set<ProductPrice> productPrices = new ProductPriceClient(new ObjectMapper()).getProductPrices();
        if (productPrices == null) {
            log.error("Prices API returns null!");
            return new UnifiedProduct[]{};
        }

        Map<Integer, ProductPrice> priceMap = productPrices.stream()
                .collect(Collectors.toMap(ProductPrice::getProductUid, price -> price));

        List<UnifiedProduct> resultUnifiedProductList = products.stream()
                .filter(product -> productType == null || product.getProductType().equals(productType))
                .map(product -> {
                    ProductPrice price = priceMap.get(product.getProductUid());
                    if (price != null) {
                        return UnifiedProduct.builder()
                                .productUid(product.getProductUid())
                                .productType(product.getProductType())
                                .name(product.getName())
                                .fullUrl(product.getFullUrl())
                                .unitPrice(price.getUnitPrice())
                                .unitPriceMeasure(price.getUnitPriceMeasure())
                                .unitPriceMeasureAmount(price.getUnitPriceMeasureAmount())
                                .build();
                    }
                    return null;
                })
                .toList();

        UnifiedProduct[] resultArr = new UnifiedProduct[resultUnifiedProductList.size()];
        return resultUnifiedProductList.toArray(resultArr);
    }

}
