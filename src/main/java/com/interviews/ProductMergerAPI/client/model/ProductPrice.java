package com.interviews.ProductMergerAPI.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductPrice {

    @Id
    @JsonProperty("product_uid")
    private int productUid;

    @JsonProperty("unit_price")
    private double unitPrice;

    @JsonProperty("unit_price_measure")
    private String unitPriceMeasure;

    @JsonProperty("unit_price_measure_amount")
    private int unitPriceMeasureAmount;
}
