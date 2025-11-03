package com.online.shop.service.Implementation;

import com.online.shop.dao.IInventoryDAO;
import com.online.shop.dao.implementationDAO.InventoryDAOImplementation;
import com.online.shop.model.Inventory;
import com.online.shop.service.IInventoryService;
import com.online.shop.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryServiceImplementation implements IInventoryService {

    private IInventoryDAO inventoryDAO= new InventoryDAOImplementation();

    @Override
    public Inventory getInventoryByProductId(Integer productId) {
        return inventoryDAO.findByProductId(productId);
    }

    @Override
    public boolean addInventory(Inventory inventory) {
        return inventoryDAO.insert(inventory);
    }

    @Override
    public boolean updateInventory(Inventory inventory) {
        return inventoryDAO.update(inventory);
    }

    @Override
    public boolean removeInventory(Integer productId) {
        return inventoryDAO.delete(productId);
    }
}
