package com.ibm.rdh.chw.entity;


public class TypeModelFeature implements java.io.Serializable {
	private boolean MESUpgrade = false;
	private boolean CustomerSetup = false;
	private boolean PlantInstallOnly = false;
	private String AnnDocNo;
	private String Feature;
	private String Model;
	private String Type;
	private boolean deleted = false;
	private boolean promoted = false;
	private java.lang.String featureID;

	private java.lang.String ModificationReason;

	/**
	 * TypeModelFeature constructor comment.
	 */
	public TypeModelFeature() {
		super();
	}

	
	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:53
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAnnDocNo() {
		return AnnDocNo;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:06
	 * AM)
	 * 
	 * @return int
	 */
	public boolean getCustomerSetup() {
		return CustomerSetup;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:14:24
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFeature() {
		return Feature;
	}

	public java.lang.String getFeatureID() {
		return featureID;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:12:46
	 * AM)
	 * 
	 * @return int
	 */
	public boolean getMESUpgrade() {
		return MESUpgrade;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:14:44
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getModel() {
		return Model;
	}

	/**
	 * Insert the method's description here. Creation date: (5/15/2001 4:08:07
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getModificationReason() {
		return ModificationReason;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:29
	 * AM)
	 * 
	 * @return int
	 */
	public boolean getPlantInstallOnly() {
		return PlantInstallOnly;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:15:00
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getType() {
		return Type;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:53
	 * AM)
	 * 
	 * @param newAnnDocNo
	 *            java.lang.String
	 */
	public void setAnnDocNo(java.lang.String newAnnDocNo) {
		AnnDocNo = newAnnDocNo;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:06
	 * AM)
	 * 
	 * @param newCustomerSetup
	 *            int
	 */
	public void setCustomerSetup(boolean newCustomerSetup) {
		CustomerSetup = newCustomerSetup;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:14:24
	 * AM)
	 * 
	 * @param newFeature
	 *            java.lang.String
	 */
	public void setFeature(java.lang.String newFeature) {
		Feature = newFeature;
	}

	public void setFeatureID(java.lang.String newFeatureID) {
		featureID = newFeatureID;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:12:46
	 * AM)
	 * 
	 * @param newMESUpgrade
	 *            int
	 */
	public void setMESUpgrade(boolean newMESUpgrade) {
		MESUpgrade = newMESUpgrade;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:14:44
	 * AM)
	 * 
	 * @param newModel
	 *            java.lang.String
	 */
	public void setModel(java.lang.String newModel) {
		Model = newModel;
	}

	/**
	 * Insert the method's description here. Creation date: (5/15/2001 4:08:07
	 * PM)
	 * 
	 * @param newModificationReason
	 *            java.lang.String
	 */
	public void setModificationReason(java.lang.String newModificationReason) {
		ModificationReason = newModificationReason;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:13:29
	 * AM)
	 * 
	 * @param newPlantInstallOnly
	 *            int
	 */
	public void setPlantInstallOnly(boolean newPlantInstallOnly) {
		PlantInstallOnly = newPlantInstallOnly;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 9:15:00
	 * AM)
	 * 
	 * @param newType
	 *            java.lang.String
	 */
	public void setType(java.lang.String newType) {
		Type = newType;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */

	public String toString() {

		StringBuffer s = new StringBuffer(300);

		s.append("MESUpgrade >> " + MESUpgrade + "\n");
		s.append("CustomerSetup >> " + CustomerSetup + "\n");
		s.append("PlantInstallOnly >> " + PlantInstallOnly + "\n");
		s.append("AnnDocNo >> " + AnnDocNo + "\n");
		s.append("Feature >> " + Feature + "\n");
		s.append("Model >> " + Model + "\n");
		s.append("Type >> " + Type + "\n");

		return s.toString();
	}

	/**
	 * Returns the deleted.
	 * 
	 * @return boolean
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Sets the deleted.
	 * 
	 * @param deleted
	 *            The deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Returns the promoted.
	 * 
	 * @return boolean
	 */
	public boolean isPromoted() {
		return promoted;
	}

	public boolean isRPQ() {
		boolean ans = false;

		if (this.getFeatureID().equals("Q")) {
			ans = true;
		} else {
			ans = false;
		}

		return ans;
	}

	/**
	 * Sets the promoted.
	 * 
	 * @param promoted
	 *            The promoted to set
	 */
	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	

	public String convertNull(String input) {
		if (input == null) {
			return "";
		} else {
			return input;
		}
	}

}
