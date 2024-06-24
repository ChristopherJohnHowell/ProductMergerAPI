package com.interviews.ProductMergerAPI.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class ProductPriceClient {

    private static final String productsPriceURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_price_v2.json";

    private final ObjectMapper objectMapper;

    public ProductPriceClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Set<ProductPrice> getProductPrices() {

        // Introducing a Jackson-based Exchange Strategy that can be built into the WebClient.
        ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper)))
                        .build();

        // Add Exchange Strategy to WebClient...
        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).build();

        Mono<Set<ProductPrice>> response = client
                .get()
                .uri(productsPriceURL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<ProductPrice>>() {
                })
                .log();

        System.out.println("Set of products: " + response.block());

        return response.block();
    }


}
