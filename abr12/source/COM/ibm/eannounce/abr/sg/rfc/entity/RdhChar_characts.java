/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChar_characts {
	@SerializedName("CHARACT")
	private String	charact;
	
	@SerializedName("DATATYPE")
	private String	datatype;
	
	@SerializedName("CHARNUMBER")
	private String charnumber;
	
	@SerializedName("DECPLACES")
	private String	decplaces;	
	
	@SerializedName("NEG_VALS")
	private String	neg_vals;	
	
	@SerializedName("STATUS")
	private String	status;	
	
	@SerializedName("GROUP")
	private String	group;	
	
	@SerializedName("VALASSIGNM")
	private String	valassignm;
	
	@SerializedName("NO_ENTRY")
	private String	no_entry;
	
	@SerializedName("NO_DISPLAY")
	private String	no_display;
	
	@SerializedName("ADDIT_VALS")
	private String	addit_vals;
	
	@SerializedName("CASESENS")
	private String casesens;
	
	public String getCharact() {
		return charact;
	}
	public void setCharact(String charact) {
		this.charact = charact;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getCharnumber() {
		return charnumber;
	}
	public void setCharnumber(String charnumber) {
		this.charnumber = charnumber;
	}
	public String getDecplaces() {
		return decplaces;
	}
	public void setDecplaces(String decplaces) {
		this.decplaces = decplaces;
	}
	public String getNeg_vals() {
		return neg_vals;
	}
	public void setNeg_vals(String neg_vals) {
		this.neg_vals = neg_vals;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getValassignm() {
		return valassignm;
	}
	public void setValassignm(String valassignm) {
		this.valassignm = valassignm;
	}
	public String getNo_entry() {
		return no_entry;
	}
	public void setNo_entry(String no_entry) {
		this.no_entry = no_entry;
	}
	public String getNo_display() {
		return no_display;
	}
	public void setNo_display(String no_display) {
		this.no_display = no_display;
	}
	public String getAddit_vals() {
		return addit_vals;
	}
	public void setAddit_vals(String addit_vals) {
		this.addit_vals = addit_vals;
	}
    /**
     * @return the casesens
     */
    public String getCasesens()
    {
        return casesens;
    }
    /**
     * @param casesens the casesens to set
     */
    public void setCasesens(String casesens)
    {
        this.casesens = casesens;
    }

}
