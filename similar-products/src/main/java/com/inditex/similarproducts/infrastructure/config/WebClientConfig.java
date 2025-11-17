package com.inditex.similarproducts.infrastructure.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient productWebClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:3001").clientConnector(
				new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofMillis(2000))

				)).build();
	}

}
