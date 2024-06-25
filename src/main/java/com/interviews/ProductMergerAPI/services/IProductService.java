package com.interviews.ProductMergerAPI.services;

import com.interviews.ProductMergerAPI.domains.UnifiedProduct;

public interface IProductService {

    UnifiedProduct[] getProducts(String productType);

}
