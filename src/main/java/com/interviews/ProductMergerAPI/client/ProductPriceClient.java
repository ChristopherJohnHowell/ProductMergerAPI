package com.interviews.ProductMergerAPI.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviews.ProductMergerAPI.client.model.ProductPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class ProductPriceClient {

    private final WebClient.Builder webClientBuilder;
    private final RestTemplate restTemplate;

    private static final String productsPriceURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_price_v2.json";

    public ProductPriceClient() {
        this.webClientBuilder = WebClient.builder();
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get Product Prices from related service.
     * <p>
     * NOTE: This is the old way (2017) to use API Client Calls
     *
     * @return productPrices
     */
    public Set<ProductPrice> getProductPrices() {
        try {
            ProductPrice[] productArr = restTemplate.getForObject(productsPriceURL, ProductPrice[].class);
            return productArr == null ? null : new HashSet<>(Arrays.asList(productArr));
        } catch (Exception e) {
            log.error("Could not retrieve products!");
            return null;
        }
    }

    /**
     * Get Product Prices from related service.
     * <p>
     * NOTE: This is the modern approach to use API Client Calls.
     *
     * @return productPrices
     */
    public Set<ProductPrice> getProductPricesX() {
        // Introducing a Jackson-based Exchange Strategy that can be built into the WebClient.
        ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(clientCodecConfigure -> clientCodecConfigure.defaultCodecs()
                                .jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper())))
                        .build();
        // Add Exchange Strategy to WebClient...
        WebClient client = webClientBuilder.exchangeStrategies(exchangeStrategies).build();
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
