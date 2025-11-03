package com.online.shop.dao.mybatisiml;

import com.online.shop.model.Product;
import com.online.shop.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class ProductMyBatisDAO {

    public Product findById(int productId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.findById(productId);
        }
    }

    public List<Product> findAll() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.findAll();
        }
    }

    public void insert(Product product) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            mapper.insert(product);
        }
    }

    public void update(Product product) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            mapper.update(product);
        }
    }

    public void delete(int productId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            mapper.delete(productId);
        }
    }
}
