/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhBomm_api03
{
    @SerializedName("ITEM_CATEG")
    private String  item_categ;
    
    @SerializedName("ITEM_NO")
    private String  item_no;
    
    @SerializedName("COMPONENT")
    private String  component;
    
    @SerializedName("COMP_QTY")
    private String  comp_qty;
    
    @SerializedName("COMP_UNIT")
    private String  comp_unit;
    
    @SerializedName("REL_SALES")
    private String  rel_sales;
    
    @SerializedName("PURCH_ORG")
    private String  purch_org;
    
    @SerializedName("PURCH_GRP")
    private String  purch_grp;
    
    @SerializedName("IDENTIFIER")
    private String  identifier;
    
    @SerializedName("FLDELETE")
    private String  fldelete;
    
    /**
     * @return the item_categ
     */
    public String getItem_categ()
    {
        return item_categ;
    }
    /**
     * @param item_categ the item_categ to set
     */
    public void setItem_categ(String item_categ)
    {
        this.item_categ = item_categ;
    }
    /**
     * @return the item_no
     */
    public String getItem_no()
    {
        return item_no;
    }
    /**
     * @param item_no the item_no to set
     */
    public void setItem_no(String item_no)
    {
        this.item_no = item_no;
    }
    /**
     * @return the component
     */
    public String getComponent()
    {
        return component;
    }
    /**
     * @param component the component to set
     */
    public void setComponent(String component)
    {
        this.component = component;
    }
    /**
     * @return the comp_qty
     */
    public String getComp_qty()
    {
        return comp_qty;
    }
    /**
     * @param comp_qty the comp_qty to set
     */
    public void setComp_qty(String comp_qty)
    {
        this.comp_qty = comp_qty;
    }
    /**
     * @return the comp_unit
     */
    public String getComp_unit()
    {
        return comp_unit;
    }
    /**
     * @param comp_unit the comp_unit to set
     */
    public void setComp_unit(String comp_unit)
    {
        this.comp_unit = comp_unit;
    }
    /**
     * @return the rel_sales
     */
    public String getRel_sales()
    {
        return rel_sales;
    }
    /**
     * @param rel_sales the rel_sales to set
     */
    public void setRel_sales(String rel_sales)
    {
        this.rel_sales = rel_sales;
    }
    /**
     * @return the purch_org
     */
    public String getPurch_org()
    {
        return purch_org;
    }
    /**
     * @param purch_org the purch_org to set
     */
    public void setPurch_org(String purch_org)
    {
        this.purch_org = purch_org;
    }
    /**
     * @return the purch_grp
     */
    public String getPurch_grp()
    {
        return purch_grp;
    }
    /**
     * @param purch_grp the purch_grp to set
     */
    public void setPurch_grp(String purch_grp)
    {
        this.purch_grp = purch_grp;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier()
    {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    /**
     * @return the fldelete
     */
    public String getFldelete()
    {
        return fldelete;
    }
    /**
     * @param fldelete the fldelete to set
     */
    public void setFldelete(String fldelete)
    {
        this.fldelete = fldelete;
    }
    
    
}
