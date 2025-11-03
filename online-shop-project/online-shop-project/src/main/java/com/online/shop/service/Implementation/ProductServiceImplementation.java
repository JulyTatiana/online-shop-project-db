package com.online.shop.service.Implementation;

import com.online.shop.dao.IProductDAO;
import com.online.shop.dao.implementationDAO.ProductDAOImplementation;
import com.online.shop.model.Product;
import com.online.shop.service.IProductService;
import com.online.shop.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImplementation implements IProductService {

  private IProductDAO productDAO = new ProductDAOImplementation();
    @Override
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    @Override
    public boolean addProduct(Product product) {
        return productDAO.insert(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    @Override
    public boolean deleteProduct(int productId) {
        return productDAO.delete(productId);
    }
}
