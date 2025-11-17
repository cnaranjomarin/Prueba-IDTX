package com.inditex.similarproducts.domain.port;

import java.util.List;

import com.inditex.similarproducts.domain.model.ProductDetail;

public interface ProductExternalPort {

	List<String> getSimilarProductIds(String productId);

	ProductDetail getProductDetail(String productId);
}
