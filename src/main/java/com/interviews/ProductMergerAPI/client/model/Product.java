package com.interviews.ProductMergerAPI.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

// Just remember: B A N D
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @JsonProperty("product_uid")
    private int productUid;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("full_url")
    private String fullUrl;

}
