package com.online.shop.service;

import com.online.shop.model.Order;

import java.util.Map;

public interface IOrderService {
    Order createOrder(int userId, Map<Integer, Integer> productQuantities);
    boolean cancelOrder(int orderId);
}
