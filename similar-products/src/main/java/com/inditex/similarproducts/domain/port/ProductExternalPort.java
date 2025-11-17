package com.inditex.similarproducts.domain.port;

import com.inditex.similarproducts.domain.model.ProductDetail;

import java.util.List;

public interface ProductExternalPort {

    List<String> getSimilarProductIds(String productId);

    ProductDetail getProductDetail(String productId);
}
