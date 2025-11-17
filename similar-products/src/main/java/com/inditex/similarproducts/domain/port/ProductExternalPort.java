package com.inditex.similarproducts.domain.port;

import com.inditex.similarproducts.domain.model.ProductDetail;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductExternalPort {

	Flux<String> getSimilarProductIds(String productId);

	Mono<ProductDetail> getProductDetail(String productId);
}
