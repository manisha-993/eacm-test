/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class Rdhzdmprktbl
{
    @SerializedName("ZDMCLASS")
    private String zdmclass;
    @SerializedName("ZDMRELNUM")
    private String zdmrelnum;
    @SerializedName("ZDMSTATUS")
    private String zdmstatus;
	public String getZdmclass() {
		return zdmclass;
	}
	public void setZdmclass(String zdmclass) {
		this.zdmclass = zdmclass;
	}
	public String getZdmrelnum() {
		return zdmrelnum;
	}
	public void setZdmrelnum(String zdmrelnum) {
		this.zdmrelnum = zdmrelnum;
	}
	public String getZdmstatus() {
		return zdmstatus;
	}
	public void setZdmstatus(String zdmstatus) {
		this.zdmstatus = zdmstatus;
	}
    
    
	
}
