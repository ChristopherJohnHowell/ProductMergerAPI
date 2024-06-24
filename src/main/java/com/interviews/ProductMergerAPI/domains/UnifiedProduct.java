package com.interviews.ProductMergerAPI.domains;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UnifiedProduct {

    @Id
    private int productUid;
    private String productType;
    private String name;
    private String fullUrl;
    private double unitPrice;
    private String unitPriceMeasure;
    private int unitPriceMeasureAmount;

}
