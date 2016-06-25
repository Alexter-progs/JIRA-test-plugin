package com.alextergroup.teamLeadPlugin.rest.model;

import javax.xml.bind.annotation.*;
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
    public Date manufactureDate;
    @XmlElement
    public Date expirationDate;
    @XmlElement
    public String description;
}