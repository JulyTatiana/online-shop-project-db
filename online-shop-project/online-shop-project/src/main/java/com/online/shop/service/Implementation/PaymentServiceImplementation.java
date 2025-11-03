package com.online.shop.service.Implementation;

import com.online.shop.dao.IPaymentDAO;
import com.online.shop.dao.implementationDAO.PaymentDAOImplementation;
import com.online.shop.service.IPaymentService;
import com.online.shop.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentServiceImplementation implements IPaymentService {

    private IPaymentDAO paymentDAO= new PaymentDAOImplementation();

    @Override
    public boolean processPayment(int orderId, double amount) {
        return false;
    }

    @Override
    public boolean refundPayment(int paymentId) {
        return paymentDAO.delete(paymentId);
    }
}
