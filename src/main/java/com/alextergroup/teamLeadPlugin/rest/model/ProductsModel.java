package com.alextergroup.teamLeadPlugin.rest.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ProductsModel {

    @XmlElement
    public long count;
    @XmlElement
    public List<ProductModel> products;

    public ProductsModel(long count, List<ProductModel> products){
        this.count = count;
        this.products = products;
    }


}
