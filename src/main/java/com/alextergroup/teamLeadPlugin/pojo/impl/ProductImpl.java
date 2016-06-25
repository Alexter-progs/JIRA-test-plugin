package com.alextergroup.teamLeadPlugin.pojo.impl;

import com.alextergroup.teamLeadPlugin.pojo.Product;

import java.util.Date;

public class ProductImpl implements Product {
    private String productName;
    private Date expirationDate;
    private Date manufactureDate;
    private String manufacturer;
    private String description;

    public ProductImpl(String productName, Date expirationDate, Date manufactureDate, String manufacturer, String description) {
        this.productName = productName;
        this.expirationDate = expirationDate;
        this.manufactureDate = manufactureDate;
        this.manufacturer = manufacturer;
        this.description = description;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public Date getManufactureDate() {
        return manufactureDate;
    }

    @Override
    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
