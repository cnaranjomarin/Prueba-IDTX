package com.inditex.similarproducts.infrastructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.similarproducts.application.SimilarProductService;
import com.inditex.similarproducts.domain.model.ProductDetail;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class SimilarProductsController {

	private final SimilarProductService service;

	public SimilarProductsController(SimilarProductService service) {
		this.service = service;
	}

	@GetMapping("/{productId}/similar")
	public ResponseEntity<List<ProductDetail>> getSimilarProducts(@PathVariable String productId) {
		try {
			List<ProductDetail> result = service.getSimilarProducts(productId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
