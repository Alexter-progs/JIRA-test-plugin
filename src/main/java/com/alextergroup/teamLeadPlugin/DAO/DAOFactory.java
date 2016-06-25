package com.alextergroup.teamLeadPlugin.DAO;

import com.alextergroup.teamLeadPlugin.DAO.impl.ProductDAOImpl;
import com.atlassian.activeobjects.external.ActiveObjects;

public class DAOFactory {

    private static ProductDAO productDAO = null;
    private static DAOFactory instance = null;
    private static ActiveObjects ao;

    public DAOFactory(ActiveObjects ao) {
        DAOFactory.ao = ao;
    }

    public static synchronized DAOFactory getInstance(){
        if(instance == null){
            instance = new DAOFactory(ao);
        }
        return instance;
    }

    public ProductDAO getProductDAO(){
        if(productDAO == null){
            productDAO = new ProductDAOImpl(ao);
        }
        return productDAO;
    }
}
