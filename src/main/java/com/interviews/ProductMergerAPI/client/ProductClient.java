package com.interviews.ProductMergerAPI.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviews.ProductMergerAPI.client.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class ProductClient {

    private static final String productsURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_v2.json";

    private final ObjectMapper objectMapper;

    public ProductClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Set<Product> getProducts() {

        // Introducing a Jackson-based Exchange Strategy that can be built into the WebClient.
        ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper)))
                        .build();

        // Add Exchange Strategy to WebClient...
        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).build();

        Mono<Set<Product>> response = client
                .get()
                .uri(productsURL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<Product>>() {
                })
                .log();

        System.out.println("Set of products: " + response.block());

        return response.block();
    }


}
