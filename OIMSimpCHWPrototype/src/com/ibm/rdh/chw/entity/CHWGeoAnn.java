package com.ibm.rdh.chw.entity;

/**
 * Insert the type's description here.
 * Creation date: (4/30/2001 9:05:03 AM)
 * @author: Muhammed Afaq
 */
public class CHWGeoAnn implements java.io.Serializable{
	private java.util.Date GADate;
	private java.util.Date MESGADate;
	private java.lang.String AnnouncementLetter;
	private java.lang.String Geography;
	private java.lang.String AnnDocNo;
	private java.lang.String EPICNumber;
	private java.util.Date announcementDate;
	private boolean changed = false;
	
/**
 * CHWGEOANN constructor comment.
 */
public CHWGeoAnn() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:09:56 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnDocNo() {
	return AnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (5/2/2001 10:08:24 AM)
 * @return java.util.Date
 */
public java.util.Date getAnnouncementDate() {
	return announcementDate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:07:56 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnouncementLetter() {
	return AnnouncementLetter;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:10:42 AM)
 * @return java.lang.String
 */
public java.lang.String getEPICNumber() {
	return EPICNumber;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:06:06 AM)
 * @return java.util.Date
 */
public java.util.Date getGADate() {
	return GADate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:08:59 AM)
 * @return java.lang.String
 */
public java.lang.String getGeography() {
	return Geography;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:06:40 AM)
 * @return java.util.Date
 */
public java.util.Date getMESGADate() {
	return MESGADate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:09:56 AM)
 * @param newAnnDocNo java.lang.String
 */
public void setAnnDocNo(java.lang.String newAnnDocNo) {
	AnnDocNo = newAnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (5/2/2001 10:08:24 AM)
 * @param newAnnouncementDate java.util.Date
 */
public void setAnnouncementDate(java.util.Date newAnnouncementDate) {
	announcementDate = newAnnouncementDate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:07:56 AM)
 * @param newAnnouncementLetter java.lang.String
 */
public void setAnnouncementLetter(java.lang.String newAnnouncementLetter) {
	AnnouncementLetter = newAnnouncementLetter;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:10:42 AM)
 * @param newEPICNumber java.lang.String
 */
public void setEPICNumber(java.lang.String newEPICNumber) {
	EPICNumber = newEPICNumber;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:06:06 AM)
 * @param newGADate java.util.Date
 */
public void setGADate(java.util.Date newGADate) {
	GADate = newGADate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:08:59 AM)
 * @param newGeography java.lang.String
 */
public void setGeography(java.lang.String newGeography) {
	Geography = newGeography;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 9:06:40 AM)
 * @param newMESGADate java.util.Date
 */
public void setMESGADate(java.util.Date newMESGADate) {
	MESGADate = newMESGADate;
}
/**
 * Insert the method's description here.
 * Creation date: (5/3/2001 1:55:35 PM)
 * @return java.lang.String
 */
public String toString() {

	StringBuffer s= new StringBuffer(300);

	s.append("GADate >> "+GADate+"\n");
	s.append("MESGADate >> "+MESGADate+"\n");
	s.append("AnnouncementLetter >> "+AnnouncementLetter+"\n");
	s.append("Geography >> "+Geography+"\n");

	s.append("AnnDocNo >> "+AnnDocNo+"\n");
	s.append("EPICNumber >> "+EPICNumber+"\n");
	s.append("announcementDate >> "+announcementDate+"\n");
	s.append("Changed >> " + changed + "\n");
	
	return s.toString();

}
	/**
	 * Returns the changed.
	 * @return boolean
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the changed.
	 * @param changed The changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
