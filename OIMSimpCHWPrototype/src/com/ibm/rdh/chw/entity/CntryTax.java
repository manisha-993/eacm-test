/*
 * Created on Oct 28, 2007 TODO To change the template for this generated file
 * go to Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.rdh.chw.entity;
/**
 * @author venkatarao 
 * TODO To change the template for this generated type
 *      comment go to Window - Preferences - Java - Code Style - Code
 *      Templates
 */
public class CntryTax implements java.io.Serializable
{
    private String _country;
    private String _taxCategory;
    private String _taxClassification;
    /**
     *Constructor with no-args.
     */
    public CntryTax()
    {
        super();
    }
    /**
     * Constructor with 
     * @param cntry - cntry code
     * @param taxCategory 
     * @param taxClassification
     */
    public CntryTax(String country, String taxCategory, String taxClassification) 
    {
		_country = country;
		_taxCategory = taxCategory;
        _taxClassification = taxClassification;
    }
    /**
     * set cntry code
     * @param cntry
     */
    public void setCountry(String country)
    {
		_country = country;
    }
    /**
     * 
     * @return country
     */
    public String getCountry()
    {
        return _country;
    }
    /**
     * Tax Category.
     * @param category
     */
    public void setTaxCategory(String category)
    {
        _taxCategory = category;
    }
    /**
     * 
     * @return taxCategory
     */
    public String getTaxCategory()
    {
        return _taxCategory;
    }
    /**
     * Tax Classification.
     * @param classification
     */
    public void setClassification(String classification)
    {
        _taxClassification = classification;
    }
    /**
     * 
     * @return Tax Classification.
     */
    public String getClassification()
    {
        return _taxClassification;
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer(1000);
        s.append("Country >>"+ _country +"\n");
        s.append("TaxCategory >>"+ _taxCategory +"\n");
        s.append("TaxClassification >>"+ _taxClassification +"\n");
        return s.toString();
    }
   
}
