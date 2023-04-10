package com.ibm.eannounce.wwprt.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "wwprttxn")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceXml {
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String id;
    @XmlAttribute(name = "noNamespaceSchemaLocation", namespace = "http://www.w3.org/2001/XMLSchema-instance")
    private String schemaLocation;

    @XmlElement(name = "price")
    private List<Price> prices = new ArrayList<>();
    public void add(Price price){
        prices.add(price);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
    // 构造函数和Getter/Setter方法省略
}