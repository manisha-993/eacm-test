package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.util.HashMap;

public class WTAASCCNHWENTITY {
	private String type;
	private String machtype;
	private String model;
	private String featureCode;
	private String toMachtype;
	private String toModel;		
	private String annDate;
	private HashMap price = new HashMap();
	
	public HashMap getPrice() {
		return price;
	}
	public void setPrice(HashMap price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMachtype() {
		return machtype;
	}
	public void setMachtype(String machtype) {
		this.machtype = machtype;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFeatureCode() {
		return featureCode;
	}
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}
	public String getToMachtype() {
		return toMachtype;
	}
	public void setToMachtype(String toMachtype) {
		this.toMachtype = toMachtype;
	}
	public String getToModel() {
		return toModel;
	}
	public void setToModel(String toModel) {
		this.toModel = toModel;
	}
	public String getAnnDate() {
		return annDate;
	}
	public void setAnnDate(String annDate) {
		this.annDate = annDate;
	}	
}

