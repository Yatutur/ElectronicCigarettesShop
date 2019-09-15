package com.yatu.electronicCigarettesShop.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yatu.electronicCigarettesShop.dto.ProductDTO;

public class ProductInfo {

    private int id;
    private String name;
    private double price;
 
    private boolean newProduct=false;
 
    // Upload file.
    private CommonsMultipartFile fileData;
 
    public ProductInfo() {
    }
 
    public ProductInfo(ProductDTO product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }
 
    public ProductInfo(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

}
