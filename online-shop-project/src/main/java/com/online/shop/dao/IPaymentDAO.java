package com.online.shop.dao;

import com.online.shop.model.Payment;

public interface IPaymentDAO {
    Payment findById(Integer paymentId);
    boolean insert(Payment payment);
    boolean update(Payment payment);
    boolean delete(Integer paymentId);
}
