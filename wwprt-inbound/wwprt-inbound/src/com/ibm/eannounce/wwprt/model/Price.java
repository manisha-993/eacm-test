package com.ibm.eannounce.wwprt.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Price {
    private String offeringname;
    private String offeringtype;
    private String startdate;
    private String currency;
    private String cabletype;
    private String cableid;
    private String releasets;
    private String pricevalue;
    private String varianttype;
    private String variantname;
    private String country;
    private String pricetype;
    private String onshore;
    private String enddate;
    private String pricevalueusd;
    private String factor;
    private String type;
    private String xsiType;
    public Price(){

    this.setType("I");
    this.setVarianttype("hello");
    }
    @XmlElement(name = "offeringname")
    public void setOfferingname(String offeringname) {
        this.offeringname = offeringname;
    }

    @XmlElement(name = "offeringtype")
    public void setOfferingtype(String offeringtype) {
        this.offeringtype = offeringtype;
    }

    @XmlElement(name = "startdate")
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @XmlElement(name = "currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name = "cabletype")
    public void setCabletype(String cabletype) {
        this.cabletype = cabletype;
    }

    @XmlElement(name = "cableid")
    public void setCableid(String cableid) {
        this.cableid = cableid;
    }

    @XmlElement(name = "releasets")
    public void setReleasets(String releasets) {
        this.releasets = releasets;
    }

    @XmlElement(name = "pricevalue")
    public void setPricevalue(String pricevalue) {
        this.pricevalue = pricevalue;
    }

    @XmlElement(name = "varianttype")
    public void setVarianttype(String varianttype) {
        this.varianttype = varianttype;
    }

    @XmlElement(name = "variantname")
    public void setVariantname(String variantname) {
        this.variantname = variantname;
    }

    @XmlElement(name = "country")
    public void setCountry(String country) {
        this.country = country;
    }

    @XmlElement(name = "pricetype")
    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    @XmlElement(name = "onshore")
    public void setOnshore(String onshore) {
        this.onshore = onshore;
    }

    @XmlElement(name = "enddate")
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    @XmlElement(name = "pricevalueusd")
    public void setPricevalueusd(String pricevalueusd) {
        this.pricevalueusd = pricevalueusd;
    }

    @XmlElement(name = "factor")
    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getType() {
        return type;
    }

    @XmlAttribute(name="type")
    public void setType(String type) {
        this.type = type;
    }
    @XmlAttribute(name="xsi:type")

    public void setXsiType(String xsiType) {
        this.xsiType = xsiType;
    }
    public String getOfferingname() {
        return offeringname;
    }

    public String getOfferingtype() {
        return offeringtype;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCabletype() {
        return cabletype;
    }

    public String getCableid() {
        return cableid;
    }

    public String getReleasets() {
        return releasets;
    }

    public String getPricevalue() {
        return pricevalue;
    }

    public String getVarianttype() {
        return varianttype;
    }

    public String getVariantname() {
        return variantname;
    }

    public String getCountry() {
        return country;
    }

    public String getPricetype() {
        return pricetype;
    }

    public String getOnshore() {
        return onshore;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getPricevalueusd() {
        return pricevalueusd;
    }

    public String getFactor() {
        return factor;
    }

    public String getXsiType() {
        return xsiType;
    }


}
