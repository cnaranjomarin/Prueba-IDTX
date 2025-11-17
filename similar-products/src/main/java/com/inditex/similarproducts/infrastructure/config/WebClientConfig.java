package com.inditex.similarproducts.infrastructure.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient(WebClient.Builder builder) {

		HttpClient httpClient = HttpClient.create().compress(true).followRedirect(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500).responseTimeout(Duration.ofSeconds(1))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1))
						.addHandlerLast(new WriteTimeoutHandler(1)));

		return builder.clientConnector(new ReactorClientHttpConnector(httpClient)).baseUrl("http://localhost:3001")
				.build();
	}
}
