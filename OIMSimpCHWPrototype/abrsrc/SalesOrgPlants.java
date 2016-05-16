package COM.ibm.eannounce.abr.sg.rfc;

import java.util.Vector;

public class SalesOrgPlants {

	private String genAreaCode;
	private String genAreaName;
	private String geo;
	private String salesorg;
	private Vector<String> plants;

	public String getGenAreaCode() {
		return genAreaCode;
	}

	public void setGenAreaCode(String genAreaCode) {
		this.genAreaCode = genAreaCode;
	}

	public String getGenAreaName() {
		return genAreaName;
	}

	public void setGenAreaName(String genAreaName) {
		this.genAreaName = genAreaName;
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
