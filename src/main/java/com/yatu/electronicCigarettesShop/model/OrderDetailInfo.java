package com.yatu.electronicCigarettesShop.model;

public class OrderDetailInfo {
    private int id;
 
    private int productId;
    private String productName;
 
    private int quantity;
    private double price;
    private double amount;
 
    public OrderDetailInfo() {
 
    }
 
    // Using for Hibernate Query.
    public OrderDetailInfo(int id, int productId, //
            String productName, int quantity, double price, double amount) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getProductId() {
        return productId;
    }
 
    public void setProductId(int productId) {
        this.productId = productId;
    }
 
    public String getProductName() {
        return productName;
    }
 
    public void setProductName(String productName) {
        this.productName = productName;
    }
 
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
