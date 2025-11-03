package com.online.shop.service.Implementation;

import com.online.shop.dao.IInventoryDAO;
import com.online.shop.dao.IOrderDAO;
import com.online.shop.dao.implementationDAO.OrderDAOImplementation;
import com.online.shop.model.Order;
import com.online.shop.service.IOrderService;
import com.online.shop.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class OrderServiceImplementation implements IOrderService {

    private IOrderDAO orderDAO= new OrderDAOImplementation();

    @Override
    public Order createOrder(int userId, Map<Integer, Integer> productQuantities) {
        return orderDAO.insert(userId, productQuantities);
    }

    @Override
    public boolean cancelOrder(int orderId) {
        return orderDAO.delete(orderId);
    }
}
