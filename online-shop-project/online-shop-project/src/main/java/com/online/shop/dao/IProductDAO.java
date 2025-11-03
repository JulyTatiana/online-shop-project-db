package com.online.shop.dao;

import com.online.shop.model.Product;

public interface IProductDAO {
    Product findById(Integer productId);
    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(Integer productId);
}
