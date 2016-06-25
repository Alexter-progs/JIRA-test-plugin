package com.alextergroup.teamLeadPlugin.DAO;

import com.alextergroup.teamLeadPlugin.entity.ProductEntity;
import com.alextergroup.teamLeadPlugin.pojo.Product;

public interface ProductDAO {

    public ProductEntity addProduct(Product product) throws Exception;

    public ProductEntity[] getProducts() throws Exception;

    public ProductEntity deleteProduct(long id) throws Exception;

    public ProductEntity updateProduct(long id, Product product) throws Exception;

    public ProductEntity getProductByID(long id) throws Exception;
}
