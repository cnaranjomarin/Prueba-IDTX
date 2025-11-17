package com.inditex.similarproducts.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

class SimilarProductServiceTest {

	private final ProductExternalPort externalPort = mock(ProductExternalPort.class);
	private final SimilarProductService service = new SimilarProductService(externalPort);

	@Test
	void returnsSimilarProductsCorrectly() {

		when(externalPort.getSimilarProductIds("1")).thenReturn(List.of("2", "3", "4"));

		when(externalPort.getProductDetail("2")).thenReturn(new ProductDetail("2", "Dress", 19.99, true));

		when(externalPort.getProductDetail("3")).thenReturn(new ProductDetail("3", "Blazer", 29.99, false));

		when(externalPort.getProductDetail("4")).thenReturn(new ProductDetail("4", "Boots", 39.99, true));

		List<ProductDetail> result = service.getSimilarProducts("1");

		assertThat(result).hasSize(3);
		assertThat(result).extracting("id").containsExactly("2", "3", "4");

		verify(externalPort).getSimilarProductIds("1");
		verify(externalPort).getProductDetail("2");
		verify(externalPort).getProductDetail("3");
		verify(externalPort).getProductDetail("4");
	}

	@Test
	void filtersNullProducts() {
		when(externalPort.getSimilarProductIds("1")).thenReturn(List.of("2", "3"));

		when(externalPort.getProductDetail("2")).thenReturn(new ProductDetail("2", "Item", 10.0, true));

		when(externalPort.getProductDetail("3")).thenReturn(null);

		List<ProductDetail> result = service.getSimilarProducts("1");

		assertThat(result).hasSize(1);
		assertThat(result.get(0).id()).isEqualTo("2");
	}

	@Test
	void returnsEmptyListIfNoSimilarIds() {
		when(externalPort.getSimilarProductIds("1")).thenReturn(List.of());

		List<ProductDetail> result = service.getSimilarProducts("1");

		assertThat(result).isEmpty();
	}

	@Test
	void ignoresProductsNotFound() {
		when(externalPort.getSimilarProductIds("1")).thenReturn(List.of("2", "999"));

		when(externalPort.getProductDetail("2")).thenReturn(new ProductDetail("2", "Dress", 19.99, true));

		when(externalPort.getProductDetail("999")).thenReturn(null);

		List<ProductDetail> result = service.getSimilarProducts("1");

		assertThat(result).hasSize(1);
		assertThat(result.get(0).id()).isEqualTo("2");
	}

}
