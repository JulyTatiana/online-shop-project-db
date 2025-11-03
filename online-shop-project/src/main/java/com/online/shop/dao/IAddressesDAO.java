package com.online.shop.dao;

import com.online.shop.model.Addresses;

public interface IAddressesDAO {
    Addresses findById(Integer addressId);
    boolean insert(Addresses address);
    boolean update(Addresses address);
    boolean delete(Integer addressId);
}
