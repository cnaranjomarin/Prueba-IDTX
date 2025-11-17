package com.inditex.similarproducts.application;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SimilarProductServiceTest {

	@Mock
	private ProductExternalPort externalPort;

	private SimilarProductService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		service = new SimilarProductService(externalPort);
	}

	@Test
	void whenSimilarProductsExist_thenReturnList() {

		String productId = "1";

		ProductDetail p1 = new ProductDetail("2", "Shirt", 20.0, true);
		ProductDetail p2 = new ProductDetail("3", "Jeans", 40.0, true);

		when(externalPort.getSimilarProductIds(productId)).thenReturn(Flux.just("2", "3"));

		when(externalPort.getProductDetail("2")).thenReturn(Mono.just(p1));

		when(externalPort.getProductDetail("3")).thenReturn(Mono.just(p2));

		StepVerifier.create(service.getSimilarProducts(productId)).expectNext(List.of(p1, p2)).verifyComplete();
	}

	@Test
	void whenSomeProductsNotFound_thenReturnOnlyExisting() {

		String productId = "1";

		ProductDetail p1 = new ProductDetail("2", "Shirt", 20.0, true);

		when(externalPort.getSimilarProductIds(productId)).thenReturn(Flux.just("2", "3"));

		when(externalPort.getProductDetail("2")).thenReturn(Mono.just(p1));

		// Producto 3 no existe
		when(externalPort.getProductDetail("3")).thenReturn(Mono.empty());

		StepVerifier.create(service.getSimilarProducts(productId)).expectNext(List.of(p1)).verifyComplete();
	}

	@Test
	void whenNoSimilarProducts_thenReturnEmptyList() {

		when(externalPort.getSimilarProductIds("10")).thenReturn(Flux.empty());

		StepVerifier.create(service.getSimilarProducts("10")).expectNext(List.of()) // lista vacía
				.verifyComplete();
	}

	@Test
	void whenErrorOccurs_thenPropagateError() {

		when(externalPort.getSimilarProductIds("99")).thenReturn(Flux.error(new RuntimeException("backend error")));

		StepVerifier.create(service.getSimilarProducts("99")).expectError(RuntimeException.class).verify();
	}
}
