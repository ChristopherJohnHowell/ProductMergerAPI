package com.interviews.ProductMergerAPI.services;


import com.interviews.ProductMergerAPI.client.ProductClient;
import com.interviews.ProductMergerAPI.client.ProductPriceClient;
import com.interviews.ProductMergerAPI.client.model.Product;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;
import com.interviews.ProductMergerAPI.domains.UnifiedProduct;
import com.interviews.ProductMergerAPI.services.impl.ProductServiceImpl;
import com.interviews.ProductMergerAPI.utils.TestingUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductClient productClient;

    @Mock
    private ProductPriceClient productPriceClient;

    @InjectMocks
    private ProductServiceImpl underTest;

    @Test
    public void getProduct_WithValidData_ShouldReturnProduct() {
        Set<Product> testProducts = new TestingUtil().generateProducts();
        Set<ProductPrice> testProductPrices = new TestingUtil().generateProductPrices();
        // NOTE: You have to have an Autowired variable in your classes to test them! (productClient & productPriceClient)
        when(productClient.getProducts()).thenReturn(testProducts);
        when(productPriceClient.getProductPrices()).thenReturn(testProductPrices);
        // Execute underTest method...
        UnifiedProduct[] result = underTest.getProducts("BASIC");
        UnifiedProduct[] productsArr = new UnifiedProduct[]{
                UnifiedProduct.builder()
                        .productUid(1999888)
                        .productType("BASIC")
                        .name("Super Chicken")
                        .fullUrl("www.superchicken.com")
                        .unitPrice(19.99)
                        .unitPriceMeasure("kg")
                        .unitPriceMeasureAmount(1).build()};
        assertEquals(productsArr[0].getProductUid(), result[0].getProductUid());
        assertEquals(productsArr[0].getProductType(), result[0].getProductType());
        assertEquals(productsArr[0].getName(), result[0].getName());
    }

    @Test
    public void getProduct_withInvalidData_ShouldReturnNull() {
        Set<Product> testProducts = new TestingUtil().generateProducts();
        Set<ProductPrice> testProductPrices = new TestingUtil().generateProductPrices();
        // NOTE: You have to have an Autowired variable in your classes to test them! (productClient & productPriceClient)
        when(productClient.getProducts()).thenReturn(testProducts);
        when(productPriceClient.getProductPrices()).thenReturn(testProductPrices);
        // Execute underTest method...
        UnifiedProduct[] result = underTest.getProducts("INVALID");
        assertEquals(result.length, 0);
    }

    @Test
    public void getProduct_withNoProductType_ShouldReturnMultipleProducts() {
        Set<Product> testProducts = new TestingUtil().generateProducts();
        Set<ProductPrice> testProductPrices = new TestingUtil().generateProductPrices();
        // NOTE: You have to have an Autowired variable in your classes to test them! (productClient & productPriceClient)
        when(productClient.getProducts()).thenReturn(testProducts);
        when(productPriceClient.getProductPrices()).thenReturn(testProductPrices);
        // Execute underTest method...
        UnifiedProduct[] result = underTest.getProducts(null);
        UnifiedProduct[] productsArr = new UnifiedProduct[]{
                UnifiedProduct.builder()
                        .productUid(123)
                        .productType("BASIC2")
                        .name("Super Beef")
                        .fullUrl("www.superbeef.com")
                        .unitPrice(25.00)
                        .unitPriceMeasure("kg")
                        .unitPriceMeasureAmount(2).build(),
                UnifiedProduct.builder()
                        .productUid(1999888)
                        .productType("BASIC")
                        .name("Super Chicken")
                        .fullUrl("www.superchicken.com")
                        .unitPrice(19.99)
                        .unitPriceMeasure("kg")
                        .unitPriceMeasureAmount(1).build()
        };
        assertEquals(result.length, 2);
        assertEquals(productsArr[0].getProductUid(), result[0].getProductUid());
        assertEquals(productsArr[0].getProductType(), result[0].getProductType());
        assertEquals(productsArr[0].getName(), result[0].getName());
        assertEquals(productsArr[1].getProductUid(), result[1].getProductUid());
        assertEquals(productsArr[1].getProductType(), result[1].getProductType());
        assertEquals(productsArr[1].getName(), result[1].getName());
    }

}
