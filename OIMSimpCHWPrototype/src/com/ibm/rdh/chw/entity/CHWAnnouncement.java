package com.ibm.rdh.chw.entity;



import java.sql.Date;
import org.apache.log4j.Logger;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.LogManager;
//seopromote
//import com.ibm.pprds.epimshw.seo.LseoBundle;
//import com.ibm.pprds.epimshw.seo.LseoCollection;
//import com.ibm.pprds.epimshw.seo.LseoBundleCollection;



/**
 * Insert the type's description here.
 * Creation date: (4/19/2001 11:00:59 AM)
 * @author: Lizsolette Williams
 */
public class CHWAnnouncement {
	
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();
	private java.lang.String annDocNo;
	//private java.lang.String revenueDivision;
	private String div;	//RQ0724066720
	
	
	private java.lang.String announcementType;
	private java.lang.String segmentAcronym;
	
	
	private java.lang.String promoteStatus;
	private java.lang.String pkgOffering;
	private java.lang.String oem;
	private java.lang.String industryDefCat;
	
	private java.lang.String maintPeriod;

	
	private boolean ior=false;
	private boolean ioe=false;
	private boolean coe=false;
	private boolean cce=false;
	private boolean fex=false;
	private boolean ccr=false;
	private boolean ios=false;
	private boolean ice=false;
	private boolean ics=false;
	private boolean ie8=false;
	private boolean ces=false;
	private boolean cfm=false;

	
	private String asdo;
	private String reasonForChg;
	private boolean apAll=false;
	private boolean aspa=false;
	private boolean japan=false;
	private boolean australia=false;
	private boolean bangladesh=false;
	private boolean brunai=false;
	private boolean hongkong=false;
	private boolean indonesia=false;
	private boolean india=false;
	private boolean southKorea=false;
	private boolean macao=false;
	private boolean malaysia=false;
	private boolean myanmar=false;
	private boolean newZealand=false;
	private boolean philippines=false;
	private boolean china=false;
	private boolean sriLanka=false;
	private boolean singapore=false;
	private boolean taiwan=false;
	private boolean thailand=false;
	private String notes;
	
	
	private Date releaseDate;

	//seopromote
//	private LseoCollection lseoCollection;
//	private LseoBundleCollection lseoBunCollection;
//	
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 12:05:39 PM)
 */
public CHWAnnouncement() {}
/**
 * This CHWAnnouncement Contructor populates its variables with data for a specific Announcement Document Number.
 *
 * @param anndocno The Announcement Document Number.	
 *
 * Creation date: (4/19/2001 12:05:39 PM)
 */
public CHWAnnouncement(String geo, String anndocno) throws HWPIMSAbnormalException {

	
	
	
	}
	

/**
 * This CHWAnnouncement Contructor populates its variables with data for a specific Announcement Document Number.
 *
 * @param anndocno The Announcement Document Number.
 * @param promote The Announcement promote status.
 * @exception A HWPIMSAbnormalException is thrown whenever an error occurs while creating the CHWAnnouncement Object.	
 *
 * Creation date: (4/19/2001 12:05:39 PM)
 */
public CHWAnnouncement(String geo, String anndocno, boolean promote) throws HWPIMSAbnormalException {

	
	}

/**
 * convertNull converts the input object to an empty String if it is the null object
 * Creation date: (5/9/00 9:04:25 PM)
 * @return java.lang.String
 * @param input java.lang.String
 */
public String convertNull(String input) {
	if (input == null)
		return "";
	else
		return input;
}


/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:03:37 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnDocNo() {
	return annDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:09:18 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnouncementType() {
	return announcementType;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:56:56 PM)
 * @return int
 */
public boolean getApAll() {
	return apAll;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:47:57 PM)
 * @return java.lang.String
 */
public java.lang.String getAsdo() {
	return asdo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:57:48 PM)
 * @return int
 */
public boolean getAspa() {
	return aspa;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:59:50 PM)
 * @return int
 */
public boolean getAustralia() {
	return australia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:03:35 PM)
 * @return int
 */
public boolean getBangladesh() {
	return bangladesh;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:03:54 PM)
 * @return int
 */
public boolean getBrunai() {
	return brunai;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:37:43 PM)
 * @return int
 */
public boolean getCce() {
	return cce;
}

public boolean getFex() {
	return fex;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:03 PM)
 * @return int
 */
public boolean getCcr() {
	return ccr;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:52 PM)
 * @return int
 */
public boolean getCes() {
	return ces;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:40:09 PM)
 * @return int
 */
public boolean getCfm() {
	return cfm;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:25 PM)
 * @return int
 */
public boolean getChina() {
	return china;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:37:14 PM)
 * @return int
 */
public boolean getCoe() {
	return coe;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:10 PM)
 * @return int
 */
public boolean getHongkong() {
	return hongkong;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:41 PM)
 * @return int
 */
public boolean getIce() {
	return ice;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:02 PM)
 * @return int
 */
public boolean getIcs() {
	return ics;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:22 PM)
 * @return int
 */
public boolean getIe8() {
	return ie8;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:47 PM)
 * @return int
 */
public boolean getIndia() {
	return india;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:30 PM)
 * @return int
 */
public boolean getIndonesia() {
	return indonesia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:33:34 PM)
 * @return java.lang.String
 */
public java.lang.String getIndustryDefCat() {
	return industryDefCat;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:36:50 PM)
 * @return int
 */
public boolean getIoe() {
	return ioe;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:36:32 PM)
 * @return int
 */
public boolean getIor() {
	return ior;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:21 PM)
 * @return int
 */
public boolean getIos() {
	return ios;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:58:27 PM)
 * @return int
 */
public boolean getJapan() {
	return japan;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:05:51 PM)
 * @return int
 */
public boolean getMacao() {
	return macao;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:35:57 PM)
 * @return java.lang.String
 */
public java.lang.String getMaintPeriod() {
	return maintPeriod;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:07 PM)
 * @return int
 */
public boolean getMalaysia() {
	return malaysia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:26 PM)
 * @return int
 */
public boolean getMyanmar() {
	return myanmar;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:49 PM)
 * @return int
 */
public boolean getNewZealand() {
	return newZealand;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:09:30 PM)
 * @return java.lang.String
 */
public java.lang.String getNotes() {
	return notes;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:31:22 PM)
 * @return java.lang.String
 */
public java.lang.String getOem() {
	return oem;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:10 PM)
 * @return int
 */
public boolean getPhilippines() {
	return philippines;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:30:51 PM)
 * @return java.lang.String
 */
public java.lang.String getPkgOffering() {
	return pkgOffering;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:29:00 PM)
 * @return java.lang.String
 */
public java.lang.String getPromoteStatus() {
	return promoteStatus;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:48:38 PM)
 * @return java.lang.String
 */
public java.lang.String getReasonForChg() {
	return reasonForChg;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 5:01:51 PM)
 * @return java.sql.Date
 */
public java.util.Date getReleaseDate() {
	return releaseDate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:05:35 AM)
 * @return java.lang.String
 */
//RQ0724066720 Changes
public java.lang.String getDiv() {
	return div;
}

/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:09:49 AM)
 * @return java.lang.String
 */
public java.lang.String getSegmentAcronym() {
	return segmentAcronym;
}
/**
 * This method returns the Announcement's serializationKey.
 * @return a String containing the Announcement's serializationKey. 
 */
private String getSerializationKey() {
	return this.annDocNo+"_"+"CHW_A";
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:54 PM)
 * @return int
 */
public boolean getSingapore() {
	return singapore;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:05:04 PM)
 * @return int
 */
public boolean getSouthKorea() {
	return southKorea;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:41 PM)
 * @return int
 */
public boolean getSriLanka() {
	return sriLanka;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:08:21 PM)
 * @return int
 */
public boolean getTaiwan() {
	return taiwan;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:08:39 PM)
 * @return int
 */
public boolean getThailand() {
	return thailand;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:03:37 AM)
 * @param newAnnDocNo java.lang.String
 */
public void setAnnDocNo(java.lang.String newAnnDocNo) {
	annDocNo = newAnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:09:18 AM)
 * @param newAnnouncementType java.lang.String
 */
public void setAnnouncementType(java.lang.String newAnnouncementType) {
	announcementType = newAnnouncementType;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:56:56 PM)
 * @param newApaAll int
 */
public void setApAll(boolean newApAll) {
	apAll = newApAll;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:47:57 PM)
 * @param newAsdo java.lang.String
 */
public void setAsdo(java.lang.String newAsdo) {
	asdo = newAsdo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:57:48 PM)
 * @param newAspa int
 */
public void setAspa(boolean newAspa) {
	aspa = newAspa;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:59:50 PM)
 * @param newAustralia int
 */
public void setAustralia(boolean newAustralia) {
	australia = newAustralia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:03:35 PM)
 * @param newBangladesh int
 */
public void setBangladesh(boolean newBangladesh) {
	bangladesh = newBangladesh;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:03:54 PM)
 * @param newBrunai int
 */
public void setBrunai(boolean newBrunai) {
	brunai = newBrunai;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:37:43 PM)
 * @param newCce int
 */
public void setCce(boolean newCce) {
	cce = newCce;
}

public void setFex(boolean newFex) {
	fex = newFex;
}


/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:03 PM)
 * @param newCcr int
 */
public void setCcr(boolean newCcr) {
	ccr = newCcr;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:52 PM)
 * @param newCes int
 */
public void setCes(boolean newCes) {
	ces = newCes;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:40:09 PM)
 * @param newCfm int
 */
public void setCfm(boolean newCfm) {
	cfm = newCfm;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:25 PM)
 * @param newChina int
 */
public void setChina(boolean newChina) {
	china = newChina;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:37:14 PM)
 * @param newCoe int
 */
public void setCoe(boolean newCoe) {
	coe = newCoe;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:10 PM)
 * @param newHongkong int
 */
public void setHongkong(boolean newHongkong) {
	hongkong = newHongkong;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:41 PM)
 * @param newIce int
 */
public void setIce(boolean newIce) {
	ice = newIce;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:02 PM)
 * @param newIcs int
 */
public void setIcs(boolean newIcs) {
	ics = newIcs;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:39:22 PM)
 * @param newIe8 int
 */
public void setIe8(boolean newIe8) {
	ie8 = newIe8;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:47 PM)
 * @param newIndia int
 */
public void setIndia(boolean newIndia) {
	india = newIndia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:04:30 PM)
 * @param newIndonesia int
 */
public void setIndonesia(boolean newIndonesia) {
	indonesia = newIndonesia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:33:34 PM)
 * @param newIndustryDefCat java.lang.String
 */
public void setIndustryDefCat(java.lang.String newIndustryDefCat) {
	industryDefCat = newIndustryDefCat;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:36:50 PM)
 * @param newIoe int
 */
public void setIoe(boolean newIoe) {
	ioe = newIoe;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:36:32 PM)
 * @param newIor int
 */
public void setIor(boolean newIor) {
	ior = newIor;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:38:21 PM)
 * @param newIos int
 */
public void setIos(boolean newIos) {
	ios = newIos;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:58:27 PM)
 * @param newJapan int
 */
public void setJapan(boolean newJapan) {
	japan = newJapan;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:05:51 PM)
 * @param newMacao int
 */
public void setMacao(boolean newMacao) {
	macao = newMacao;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:35:57 PM)
 * @param newMaintPeriod java.lang.String
 */
public void setMaintPeriod(java.lang.String newMaintPeriod) {
	maintPeriod = newMaintPeriod;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:07 PM)
 * @param newMalaysia int
 */
public void setMalaysia(boolean newMalaysia) {
	malaysia = newMalaysia;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:26 PM)
 * @param newMyanmar int
 */
public void setMyanmar(boolean newMyanmar) {
	myanmar = newMyanmar;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:06:49 PM)
 * @param newNewZealand int
 */
public void setNewZealand(boolean newNewZealand) {
	newZealand = newNewZealand;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:09:30 PM)
 * @param newNotes java.lang.String
 */
public void setNotes(java.lang.String newNotes) {
	notes = newNotes;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:31:22 PM)
 * @param newOem java.lang.String
 */
public void setOem(java.lang.String newOem) {
	oem = newOem;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:10 PM)
 * @param newPhilippines int
 */
public void setPhilippines(boolean newPhilippines) {
	philippines = newPhilippines;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:30:51 PM)
 * @param newPkgOffering java.lang.String
 */
public void setPkgOffering(java.lang.String newPkgOffering) {
	pkgOffering = newPkgOffering;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:29:00 PM)
 * @param newPromoteStatus java.lang.String
 */
public void setPromoteStatus(java.lang.String newPromoteStatus) {
	promoteStatus = newPromoteStatus;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 1:48:38 PM)
 * @param newReasonForChg java.lang.String
 */
public void setReasonForChg(java.lang.String newReasonForChg) {
	reasonForChg = newReasonForChg;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 5:01:51 PM)
 * @param newReleaseDate java.sql.Date
 */
public void setReleaseDate(java.sql.Date newReleaseDate) {
	releaseDate = newReleaseDate;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:05:35 AM)
 * @param newRevenueDivision java.lang.String
 */

//RQ0724066720 Changes 
public void setDiv(java.lang.String newDiv) {
	div = newDiv;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:09:49 AM)
 * @param newSegmentAcronym java.lang.String
 */
public void setSegmentAcronym(java.lang.String newSegmentAcronym) {
	segmentAcronym = newSegmentAcronym;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:54 PM)
 * @param newSingapore int
 */
public void setSingapore(boolean newSingapore) {
	singapore = newSingapore;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:05:04 PM)
 * @param newSouthKorea int
 */
public void setSouthKorea(boolean newSouthKorea) {
	southKorea = newSouthKorea;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:07:41 PM)
 * @param newSriLanka int
 */
public void setSriLanka(boolean newSriLanka) {
	sriLanka = newSriLanka;
}
/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:08:21 PM)
 * @param newTaiwan int
 */
public void setTaiwan(boolean newTaiwan) {
	taiwan = newTaiwan;
}

/**
 * Insert the method's description here.
 * Creation date: (4/24/2001 2:08:39 PM)
 * @param newThailand int
 */
public void setThailand(boolean newThailand) {
	thailand = newThailand;
}


/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
public String toString() {
	StringBuffer s = new StringBuffer(8000);
	s.append("annDocNo >> " + annDocNo + "\n");
	s.append("div >> " + div + "\n"); //RQRQ0724066720
	s.append("announcementType >> " + announcementType + "\n");
	s.append("segmentAcronym >> " + segmentAcronym + "\n");
	s.append("promoteStatus >> " + promoteStatus + "\n");
	s.append("pkgOffering >> " + pkgOffering + "\n");
	s.append("oem >> " + oem + "\n");
	s.append("maintPeriod >> " + maintPeriod + "\n");
	s.append("ior >> " + ior + "\n");
	s.append("ioe >> " + ioe + "\n");
	s.append("coe >> " + coe + "\n");
	s.append("cce >> " + cce + "\n");
	s.append("ccr >> " + ccr + "\n");
	s.append("ios >> " + ios + "\n");
	s.append("ice >> " + ice + "\n");
	s.append("ics >> " + ics + "\n");
	s.append("ie8 >> " + ie8 + "\n");
	s.append("ces >> " + ces + "\n");
	s.append("cfm >> " + cfm + "\n");
	s.append("asdo >> " + asdo + "\n");
	s.append("reasonForChg >> " + reasonForChg + "\n");
	s.append("apAll >> " + apAll + "\n");
	s.append("ccr >> " + ccr + "\n");
	s.append("aspa >> " + aspa + "\n");
	s.append("japan >> " + japan + "\n");
	s.append("australia >> " + australia + "\n");
	s.append("bangladesh >> " + bangladesh + "\n");
	s.append("brunai >> " + brunai + "\n");
	s.append("hongkong >> " + hongkong + "\n");
	s.append("indonesia >> " + indonesia + "\n");
	s.append("india >> " + india + "\n");
	s.append("southKorea >> " + southKorea + "\n");
	s.append("macao >> " + macao + "\n");
	s.append("malaysia >> " + malaysia + "\n");
	s.append("myanmar >> " + myanmar + "\n");
	s.append("newZealand >> " + newZealand + "\n");
	s.append("philippines >> " + philippines + "\n");
	s.append("china >> " + china + "\n");
	s.append("sriLanka >> " + sriLanka + "\n");
	s.append("brunai >> " + brunai + "\n");
	s.append("singapore >> " + singapore + "\n");
	s.append("taiwan >> " + taiwan + "\n");
	s.append("thailand >> " + thailand + "\n");
	s.append("notes >> " + notes + "\n");
	s.append("releaseDate >> " + releaseDate + "\n");
	
	
	return s.toString();
}


		public boolean isCustomSeo(){
		boolean customSeo = false;
			if(getAnnouncementType().equalsIgnoreCase("SPB")){
				customSeo = true;
			}	
			else{
				customSeo = false ;
			}
		return customSeo;	
	}	


}
