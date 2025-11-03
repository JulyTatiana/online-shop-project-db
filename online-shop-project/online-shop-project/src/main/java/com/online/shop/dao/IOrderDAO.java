package com.online.shop.dao;

import com.online.shop.model.Order;

import java.util.Map;

public interface IOrderDAO {
    Order findById(Integer orderId);
    boolean insert(Order order);
    boolean update(Order order);
    boolean delete(Integer orderId);

    Order insert(int userId, Map<Integer, Integer> productQuantities);
}
