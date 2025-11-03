package com.online.shop.model;

public class Inventory {
    private int productId;
    private int stock;
    private int reserved;

    public Inventory(int productId, int stock, int reserved) {
        this.productId = productId;
        this.stock = stock;
        this.reserved = reserved;
    }

    public Inventory() {}

    public int getProductId() {
        return productId;
    }

    public int getStock() {
        return stock;
    }

    public int getReserved() {
        return reserved;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
}
