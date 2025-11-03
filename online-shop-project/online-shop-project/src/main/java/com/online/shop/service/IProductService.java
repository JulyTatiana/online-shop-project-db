package com.online.shop.service;

import com.online.shop.model.Product;

public interface IProductService {
    Product getProductById(int productId);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
}
