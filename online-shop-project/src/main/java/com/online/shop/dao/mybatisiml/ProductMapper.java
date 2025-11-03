package com.online.shop.dao.mybatisiml;

import com.online.shop.model.Product;
import java.util.List;

public interface ProductMapper {
    Product findById(int productId);
    List<Product> findAll();
    void insert(Product product);
    void update(Product product);
    void delete(int productId);
}
