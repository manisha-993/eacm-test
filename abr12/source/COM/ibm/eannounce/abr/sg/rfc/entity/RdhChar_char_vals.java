/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChar_char_vals {
	@SerializedName("CHARACT")
	private String charact;
	
	@SerializedName("FLDELETE")
	private String fldelete;
	
	@SerializedName("VALUE")
	private String value;
	
	/**
	 * @return the charact
	 */
	public String getCharact() {
		return charact;
	}
	/**
	 * @param charact the charact to set
	 */
	public void setCharact(String charact) {
		this.charact = charact;
	}
	/**
	 * @return the fldelete
	 */
	public String getFldelete() {
		return fldelete;
	}
	/**
	 * @param fldelete the fldelete to set
	 */
	public void setFldelete(String fldelete) {
		this.fldelete = fldelete;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
