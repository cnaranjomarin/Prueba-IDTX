package com.inditex.similarproducts.infrastructure.client;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductApiClient implements ProductExternalPort {

	private final WebClient webClient;

	public ProductApiClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Flux<String> getSimilarProductIds(String productId) {
		return webClient.get().uri("/product/{id}/similarids", productId).retrieve().bodyToFlux(Integer.class)
				.map(Object::toString).timeout(Duration.ofMillis(800)) // evita colgarnos si el backend se muere
				.onErrorResume(WebClientResponseException.NotFound.class, ex -> Flux.empty()); // si no existe el
																								// producto, no hay
																								// similares
	}

	@Override
	public Mono<ProductDetail> getProductDetail(String productId) {
		return webClient.get().uri("/product/{id}", productId).retrieve().bodyToMono(ProductDetail.class)
				.timeout(Duration.ofMillis(800))
				.onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty()); // si no existe, no
																								// devolvemos nada (se
																								// filtrará después)
	}
}
