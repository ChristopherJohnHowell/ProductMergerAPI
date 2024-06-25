package com.interviews.ProductMergerAPI.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviews.ProductMergerAPI.client.model.Product;
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

@Component
@Slf4j
public class ProductClient {

    private WebClient.Builder webClientBuilder;
    private RestTemplate restTemplate;
    private static final String productsURL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_v2.json";

    public ProductClient() {
        this.webClientBuilder = WebClient.builder();
        this.restTemplate = new RestTemplate();
    }

    /**
     * Returns Products from Product Client Service
     *
     * @return products
     */
    public Set<Product> getProducts() {
        try {
            Product[] productArr = restTemplate.getForObject(productsURL, Product[].class);
            return productArr == null ? new HashSet<>() : new HashSet<>(Arrays.asList(productArr));
        } catch (Exception e) {
            log.error("Could not retrieve products!");
            return null;
        }
    }

    /**
     * Returns Products from Product Client Service
     *
     * @return products
     */
    public Set<Product> getProductsX() {
        // Introducing a Jackson-based Exchange Strategy that can be built into the WebClient.
        ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper())))
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
