/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RdhMatm_geo
{
    @SerializedName("NAME")
    private String name;
    @SerializedName("VMSTA")
    private String vmsta;
    @SerializedName("VMSTD")
    private String vmstd;
    @SerializedName("SALES_ORG")
    private List<RdhMatm_sales_org> sales_orgs;
    @SerializedName("TAX_COUNTRY")
    private List<RdhMatm_tax_country> tax_countries;
    @SerializedName("PLANT")
    private List<RdhMatm_plant > plants;
    
    /**
     * @return the tax_country list
     */
    public List<RdhMatm_tax_country> getTax_countries()
    {
        return tax_countries;
    }
    /**
     * @param tax_country the tax_country list to set
     */
    public void setTax_countries(List<RdhMatm_tax_country> tax_countries)
    {
        this.tax_countries = tax_countries;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @return the vmsta
     */
    public String getVmsta()
    {
        return vmsta;
    }
    /**
     * @param vmsta the vmsta to set
     */
    public void setVmsta(String vmsta)
    {
        this.vmsta = vmsta;
    }
    /**
     * @return the vmstd
     */
    public String getVmstd()
    {
        return vmstd;
    }
    /**
     * @param vmstd the vmstd to set
     */
    public void setVmstd(String vmstd)
    {
        this.vmstd = vmstd;
    }
    /**
     * @return the sales_org list
     */
    public List<RdhMatm_sales_org> getSales_orgs()
    {
        return sales_orgs;
    }
    /**
     * @param sales_org the sales_org list to set
     */
    public void setSales_orgs(List<RdhMatm_sales_org> sales_orgs)
    {
        this.sales_orgs = sales_orgs;
    }
    /**
     * @return the plants
     */
    public List<RdhMatm_plant> getPlants()
    {
        return plants;
    }
    /**
     * @param plants the plants to set
     */
    public void setPlants(List<RdhMatm_plant> plants)
    {
        this.plants = plants;
    }
    
}
