package com.interviews.ProductMergerAPI.services.impl;

import com.interviews.ProductMergerAPI.client.ProductClient;
import com.interviews.ProductMergerAPI.client.ProductPriceClient;
import com.interviews.ProductMergerAPI.client.model.Product;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;
import com.interviews.ProductMergerAPI.client.model.utils.ProductTypesUtil;
import com.interviews.ProductMergerAPI.domains.UnifiedProduct;
import com.interviews.ProductMergerAPI.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductClient productClient;
    private final ProductPriceClient productPriceClient;

    public ProductServiceImpl(ProductClient productClient, ProductPriceClient productPriceClient) {
        this.productClient = productClient;
        this.productPriceClient = productPriceClient;
    }

    /**
     * Returns an array of UnifiedProduct objects filtered with the provided productType.
     *
     * @param productType
     * @return UnifiedProductsArr
     */
    @Override
    public UnifiedProduct[] getProducts(String productType) {

        Set<Product> products = productClient.getProducts();
        if (products == null || products.isEmpty()) {
            log.error("Products API returns null!");
            return null;
        }

        Set<ProductPrice> productPrices = productPriceClient.getProductPrices();
        if (productPrices == null || productPrices.isEmpty()) {
            log.error("Prices API returns null!");
            return null;
        }

        Map<Integer, ProductPrice> priceMap = productPrices.stream()
                .collect(Collectors.toMap(ProductPrice::getProductUid, price -> price)); // Becomes: <productUid, price>

        List<UnifiedProduct> resultUnifiedProductList = products.stream()
                .filter(product -> productType == null || ProductTypesUtil.isValidProductType(productType))
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
