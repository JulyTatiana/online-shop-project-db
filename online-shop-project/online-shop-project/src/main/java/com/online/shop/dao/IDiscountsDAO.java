package com.online.shop.dao;

import com.online.shop.model.Discounts;

public interface IDiscountsDAO {
    Discounts findById(Integer discountId);
    boolean insert(Discounts discount);
    boolean update(Discounts discount);
    boolean delete(Integer discountId);
}
