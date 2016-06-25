package com.alextergroup.teamLeadPlugin.rest.model;

import com.alextergroup.teamLeadPlugin.entity.ProductEntity;

public class Mapper {
    public static ProductModel toProductModel(ProductEntity entity){
        ProductModel productModel = new ProductModel();

        productModel.id = entity.getID();
        productModel.productName = entity.getProductName();
        productModel.manufacturer = entity.getManufacturer();
        productModel.manufactureDate = entity.getManufactureDate();
        productModel.expirationDate = entity.getExpirationDate();
        productModel.description = entity.getDescription();

        return productModel;
    }
}
