package com.inditex.similarproducts.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        HttpClient httpClient = HttpClient.create()
                .compress(true)
                .followRedirect(true);

        ReactorClientHttpConnector connector =
                new ReactorClientHttpConnector(httpClient);

        return builder
                .clientConnector(connector)
                .baseUrl("http://localhost:3001") 
                .build();
    }
}
