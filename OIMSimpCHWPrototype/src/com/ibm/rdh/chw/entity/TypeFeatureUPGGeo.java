package com.ibm.rdh.chw.entity;

/**
 * Insert the type's description here.
 * Creation date: (4/30/2001 10:17:30 AM)
 * @author: Muhammed Afaq
 */
public class TypeFeatureUPGGeo {
	private java.lang.String AnnDocNo;
	private java.lang.String Geography;
	private boolean Promoted;
	private java.lang.String FromFeature;
	private java.lang.String Type;
	private java.lang.String FromType;
	private java.lang.String Feature;
	private boolean deleted;



	private boolean noChargePurchase=false;
	private boolean itemReturn=false;
/**
 * TypeFeatureUPGGeo constructor comment.
 */
public TypeFeatureUPGGeo() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (5/24/2001 11:09:42 AM)
 * @return java.lang.String
 * @param tfObj com.ibm.pprds.epimshw.TypeFeature
 */
public String calculateRange() {
	String range;
	int strLen = this.getFeature().length();
	for (int i = 0; i < strLen; i++) {
		if (Character.isLetter(this.getFeature().charAt(i))) {
			range = "";
			return range;
		}
	}
	range = this.getFeature().substring(0, 1) + this.getFeature().substring(1, 2) + "00";
	return range;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:58:35 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnDocNo() {
	return AnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:02:03 AM)
 * @return java.lang.String
 */
public java.lang.String getFeature() {
	return Feature;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:00:58 AM)
 * @return java.lang.String
 */
public java.lang.String getFromFeature() {
	return FromFeature;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:01:40 AM)
 * @return java.lang.String
 */
public java.lang.String getFromType() {
	return FromType;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:59:05 AM)
 * @return java.lang.String
 */
public java.lang.String getGeography() {
	return Geography;
}
/**
 * Insert the method's description here.
 * Creation date: (8/17/2001 11:31:23 AM)
 * @return boolean
 */
public boolean getItemReturn() {
	return itemReturn;
}
/**
 * Insert the method's description here.
 * Creation date: (8/17/2001 11:30:34 AM)
 * @return boolean
 */
public boolean getNoChargePurchase() {
	return noChargePurchase;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:00:34 AM)
 * @return int
 */
public boolean getPromoted() {
	return Promoted;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:01:14 AM)
 * @return java.lang.String
 */
public java.lang.String getType() {
	return Type;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:58:35 AM)
 * @param newAnnDocNo java.lang.String
 */
public void setAnnDocNo(java.lang.String newAnnDocNo) {
	AnnDocNo = newAnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:02:03 AM)
 * @param newFeature java.lang.String
 */
public void setFeature(java.lang.String newFeature) {
	Feature = newFeature;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:00:58 AM)
 * @param newFromFeature java.lang.String
 */
public void setFromFeature(java.lang.String newFromFeature) {
	FromFeature = newFromFeature;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:01:40 AM)
 * @param newFromType java.lang.String
 */
public void setFromType(java.lang.String newFromType) {
	FromType = newFromType;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:59:05 AM)
 * @param newGeography java.lang.String
 */
public void setGeography(java.lang.String newGeography) {
	Geography = newGeography;
}
/**
 * Insert the method's description here.
 * Creation date: (8/17/2001 11:31:23 AM)
 * @param newItemReturn boolean
 */
public void setItemReturn(boolean newItemReturn) {
	itemReturn = newItemReturn;
}
/**
 * Insert the method's description here.
 * Creation date: (8/17/2001 11:30:34 AM)
 * @param newNoChargePurchase boolean
 */
public void setNoChargePurchase(boolean newNoChargePurchase) {
	noChargePurchase = newNoChargePurchase;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:00:34 AM)
 * @param newPromoted int
 */
public void setPromoted(boolean newPromoted) {
	Promoted = newPromoted;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 11:01:14 AM)
 * @param newType java.lang.String
 */
public void setType(java.lang.String newType) {
	Type = newType;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */


public String toString() {
	
	StringBuffer s= new StringBuffer(300);

	s.append("AnnDocNo >> "+ AnnDocNo+"\n");
	s.append("Geography >> " + Geography+"\n");
	s.append("Promoted >> " + Promoted+"\n");
	s.append("fromfeature >> "+FromFeature+"\n");
	s.append("type >> "+ Type+"\n");	
	s.append("fromtype >> "+ FromType+"\n");
	s.append("feature >> "+Feature+"\n");
		

	return s.toString();
}


	public boolean isCrossType() {
		boolean ans = false;
	
		if (this.getFromType().equals(this.getType())) {
			ans = false;
		} else {
			ans = true;
		}
		return ans;
	}

	/**
	 * Returns the deleted.
	 * @return boolean
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Sets the deleted.
	 * @param deleted The deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
