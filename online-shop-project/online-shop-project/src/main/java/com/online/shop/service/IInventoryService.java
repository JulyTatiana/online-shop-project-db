package com.online.shop.service;

import com.online.shop.model.Inventory;

public interface IInventoryService {

    Inventory getInventoryByProductId(Integer productId);
    boolean addInventory(Inventory inventory);
    boolean updateInventory(Inventory inventory);
    boolean removeInventory(Integer productId);
}
