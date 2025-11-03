package com.online.shop.model;

public class Discounts {
    private int discountId;
    private String code;
    private double percentage;
    private double minimumPurchaseAmount;
    private String description;


    public Discounts(int discountId, String code, double percentage) {
        this.discountId = discountId;
        this.code = code;
        this.percentage = percentage;
    }

    public Discounts(int discountId, String code, double percentage, double minimumPurchaseAmount, String description) {
        this.discountId = discountId;
        this.code = code;
        this.percentage = percentage;
        this.minimumPurchaseAmount = minimumPurchaseAmount;
        this.description = description;
    }

    public Discounts() {

    }

    public int getDiscountId() {
        return discountId;
    }

    public String getCode() {
        return code;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getMinimumPurchaseAmount() {
        return minimumPurchaseAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setMinimumPurchaseAmount(double minimumPurchaseAmount) {
        this.minimumPurchaseAmount = minimumPurchaseAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
