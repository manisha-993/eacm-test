/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChar_char_descr {
	@SerializedName("CHARACT")
	private String charact;
	
	@SerializedName("CHDESCR")
	private String chdescr;
	
	@SerializedName("LANGUAGE")
	private String language;

	public String getCharact() {
		return charact;
	}

	public void setCharact(String charact) {
		this.charact = charact;
	}

	public String getChdescr() {
		return chdescr;
	}

	public void setChdescr(String chdescr) {
		this.chdescr = chdescr;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
