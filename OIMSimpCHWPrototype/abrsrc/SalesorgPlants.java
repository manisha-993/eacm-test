package COM.ibm.eannounce.abr.sg.rfc;

import java.util.Vector;

public class SalesorgPlants {

	private String countryCode;
	private String countryName;
	private String geo;
	private String salesorg;
	private Vector<String> plants;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getSalesorg() {
		return salesorg;
	}

	public void setSalesorg(String salesorg) {
		this.salesorg = salesorg;
	}

	public Vector<String> getPlants() {
		return plants;
	}

	public void setPlants(Vector<String> plants) {
		this.plants = plants;
	}

}
