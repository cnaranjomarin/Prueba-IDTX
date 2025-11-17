package com.inditex.similarproducts.infrastructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.similarproducts.application.SimilarProductService;
import com.inditex.similarproducts.domain.exception.NotFoundException;
import com.inditex.similarproducts.domain.model.ProductDetail;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class SimilarProductsController {

	private final SimilarProductService service;

	public SimilarProductsController(SimilarProductService service) {
		this.service = service;
	}

	@GetMapping("/{productId}/similar")
	public Mono<ResponseEntity<List<ProductDetail>>> getSimilar(@PathVariable("productId") String productId) {

		return service.getSimilarProducts(productId).map(ResponseEntity::ok).onErrorResume(NotFoundException.class,
				e -> Mono.just(ResponseEntity.notFound().build()));
	}

}
