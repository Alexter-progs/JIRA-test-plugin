package com.alextergroup.teamLeadPlugin.DAO.impl;

import com.alextergroup.teamLeadPlugin.DAO.ProductDAO;
import com.alextergroup.teamLeadPlugin.entity.ProductEntity;
import com.alextergroup.teamLeadPlugin.pojo.Product;
import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;

public class ProductDAOImpl implements ProductDAO {

    private final ActiveObjects ao;

    public ProductDAOImpl(ActiveObjects ao){
        this.ao = ao;
    }

    @Override
    public ProductEntity addProduct(final Product product) throws Exception {
        return ao.executeInTransaction(() -> {
            ProductEntity productEntity = ao.create(ProductEntity.class);

            productEntity.setProductName(product.getProductName());
            productEntity.setManufacturer(product.getManufacturer());
            productEntity.setManufactureDate(product.getManufactureDate());
            productEntity.setExpirationDate(product.getExpirationDate());
            productEntity.setDescription(product.getDescription());

            productEntity.save();
            return productEntity;
        });
    }

    @Override
    public ProductEntity[] getProducts() throws Exception {
        return ao.executeInTransaction(() -> ao.find(ProductEntity.class));
    }

    @Override
    public ProductEntity getProductByID(long id) throws Exception {
        return ao.executeInTransaction(() -> ao.find(ProductEntity.class, Query.select().where("ID=?", id))[0]);
    }

    @Override
    public ProductEntity deleteProduct(final long id) throws Exception {
        return ao.executeInTransaction(() -> {
            ProductEntity productEntity = ao.find(ProductEntity.class, Query.select().where("ID=?",id))[0];
            ao.delete(productEntity);

            return productEntity;
        });
    }

    @Override
    public ProductEntity updateProduct(final long id, final Product product) throws Exception {
        return ao.executeInTransaction(() -> {
            ProductEntity productEntity = ao.find(ProductEntity.class, Query.select().where("ID=?", id))[0];

            if (product.getProductName() != null){
                productEntity.setProductName(product.getProductName());
            }
            if (product.getManufacturer() != null){
                productEntity.setManufacturer(product.getManufacturer());
            }
            if (product.getManufactureDate() != null){
                productEntity.setManufactureDate(product.getManufactureDate());
            }
            if (product.getExpirationDate() != null){
                productEntity.setExpirationDate(product.getExpirationDate());
            }
            if (product.getDescription() != null){
                productEntity.setDescription(product.getDescription());
            }

            productEntity.save();
            return productEntity;
        });
    }
}
