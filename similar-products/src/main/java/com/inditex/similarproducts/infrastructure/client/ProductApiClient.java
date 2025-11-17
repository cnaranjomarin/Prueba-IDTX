package com.inditex.similarproducts.infrastructure.client;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductApiClient implements ProductExternalPort {

	private final WebClient webClient;

	public ProductApiClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<String> getSimilarProductIds(String productId) {
		return webClient.get().uri("/product/{id}/similarids", productId).retrieve().bodyToFlux(Integer.class) // ← aquí
																												// está
																												// la
																												// clave
				.map(Object::toString) // lo convertimos a String
				.collectList().block();
	}

	@Override
	public ProductDetail getProductDetail(String productId) {
		try {
			return webClient.get().uri("/product/{id}", productId).retrieve().bodyToMono(ProductDetail.class).block();

		} catch (WebClientResponseException.NotFound e) {
			return null; // según contrato: si no existe → se excluye
		}
	}
}
