package com.interviews.ProductMergerAPI.services.utils;

import java.util.HashSet;
import java.util.Set;

public class ProductTypesUtil {

    private static Set<String> productTypes = new HashSet<>();

    static {
        productTypes.add("BASIC");
        productTypes.add("BASIC2");
    }

    public static boolean isValidProductType(String productType) {
        return productTypes.contains(productType);
    }

}
