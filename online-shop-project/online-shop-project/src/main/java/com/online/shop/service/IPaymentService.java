package com.online.shop.service;

public interface IPaymentService {
    boolean processPayment(int orderId, double amount);
    boolean refundPayment(int paymentId);
}
