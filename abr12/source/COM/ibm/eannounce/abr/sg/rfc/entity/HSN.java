package COM.ibm.eannounce.abr.sg.rfc.entity;

public class HSN {
	
	String country=null;
	String aLand=null;
	String machType=null;
	String steuc=null;
	String availStatus=null;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getaLand() {
		return aLand;
	}
	public void setaLand(String aLand) {
		this.aLand = aLand;
	}
	public String getMachType() {
		return machType;
	}
	public void setMachType(String machType) {
		this.machType = machType;
	}
	public String getSteuc() {
		return steuc;
	}
	public void setSteuc(String steuc) {
		this.steuc = steuc;
	}
	public String getAvailStatus() {
		return availStatus;
	}
	public void setAvailStatus(String availStatus) {
		this.availStatus = availStatus;
	}
	@Override
	public String toString() {
		return "HSN [country=" + country + ", aLand=" + aLand + ", machType=" + machType + ", steuc=" + steuc
				+ ", availStatus=" + availStatus + "]";
	}
	
	

}
