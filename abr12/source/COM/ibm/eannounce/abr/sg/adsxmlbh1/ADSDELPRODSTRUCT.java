package COM.ibm.eannounce.abr.sg.adsxmlbh1;

public class ADSDELPRODSTRUCT {
	private String entityType;
	private String entityId;
	private String modelType;
	private String modelId;
	private String machtypeatr;
	private String modelatr;
	private String featureType;
	private String featureId;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	private String featureCode;

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getMachtypeatr() {
		return machtypeatr;
	}

	public void setMachtypeatr(String machtypeatr) {
		this.machtypeatr = machtypeatr;
	}

	public String getModelatr() {
		return modelatr;
	}

	public void setModelatr(String modelatr) {
		this.modelatr = modelatr;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}
}
