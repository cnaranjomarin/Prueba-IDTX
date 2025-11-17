package com.inditex.similarproducts.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.inditex.similarproducts.domain.model.ProductDetail;
import com.inditex.similarproducts.domain.port.ProductExternalPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimilarProductService {

	private final ProductExternalPort externalPort;

	public SimilarProductService(ProductExternalPort externalPort) {
		this.externalPort = externalPort;
	}

	public List<ProductDetail> getSimilarProducts(String productId) {

		List<String> ids = externalPort.getSimilarProductIds(productId);
		return ids.stream().map(externalPort::getProductDetail).filter(Objects::nonNull).toList();
	}
}
