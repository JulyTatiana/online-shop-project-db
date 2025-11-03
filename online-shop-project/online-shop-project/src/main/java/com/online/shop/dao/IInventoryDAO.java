package com.online.shop.dao;

import com.online.shop.model.Inventory;

public interface IInventoryDAO {
    Inventory findByProductId(Integer productId);
    boolean insert(Inventory inventory);
    boolean update(Inventory inventory);
    boolean delete(Integer productId);
}
