/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhChar_chv_descr {
	@SerializedName("CHARACT")
	private String charact;
	
	@SerializedName("FLDELETE")
	private String fldelete;
	
	@SerializedName("VALUE")
	private String value;
	
	@SerializedName("LANGUAGE")
	private String language;
	
	@SerializedName("VALDESCR")
	private String valdescr;

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

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the valdescr
	 */
	public String getValdescr() {
		return valdescr;
	}

	/**
	 * @param valdescr the valdescr to set
	 */
	public void setValdescr(String valdescr) {
	    if(valdescr != null && valdescr.length() > 30)
	    {
	        valdescr = valdescr.substring(0, 30);
	    }
		this.valdescr = valdescr;
	}
	
	

}
