package com.alextergroup.teamLeadPlugin.pojo;

import java.util.Date;

public interface Product {
    String getProductName();
    void setProductName(String productName);
    Date getExpirationDate();
    void setExpirationDate(Date expirationDate);
    Date getManufactureDate();
    void setManufactureDate(Date manufactureDate);
    String getManufacturer();
    void setManufacturer(String manufacturer);
    String getDescription();
    void setDescription(String description);
}
