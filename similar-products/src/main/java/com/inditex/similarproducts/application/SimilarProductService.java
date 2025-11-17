package com.inditex.similarproducts.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SimilarProductService {

	private final ProductExternalPort externalPort;

	public SimilarProductService(ProductExternalPort externalPort) {
		this.externalPort = externalPort;
	}

	public Mono<List<ProductDetail>> getSimilarProducts(String productId) {

		return externalPort.getSimilarProductIds(productId)
				// flatMap → lanza las llamadas a detalle en paralelo (no secuencial)
				.flatMap(externalPort::getProductDetail)
				// getProductDetail devuelve Mono.empty() si 404, así que no hay nulls
				.collectList();
	}
}
