package com.alextergroup.teamLeadPlugin.rest.model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ProductModel {

    @XmlElement
    public long id;
    @XmlElement
    public String productName;
    @XmlElement
    public String manufacturer;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date manufactureDate;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date expirationDate;
    @XmlElement
    public String description;
}