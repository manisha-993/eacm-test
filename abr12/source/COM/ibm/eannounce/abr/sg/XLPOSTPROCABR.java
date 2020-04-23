// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.translation.PackageID;
import COM.ibm.opicmpdh.translation.Translation;
import COM.ibm.opicmpdh.translation.TranslationEntity;
import COM.ibm.opicmpdh.translation.TranslationPackage;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*******************************************************************************
 * XLPOSTPROCABR class handles Translation Post Process
 */

//$Log: XLPOSTPROCABR.java,v $
//Revision 1.15  2014/01/13 13:52:40  wendy
//migration to V17
//
//Revision 1.14  2012/02/06 08:51:36  wangyulo
//change ADSFEED_VALUE from 0020 to 0030 (get from properties file)
//to fix the issue IN1240073 - Materials missing AAG value
//
public final class XLPOSTPROCABR extends PokBaseABR {

	private static final Map ENTITY_STATUS_ATTRIBUTE = new HashMap();

	private static final Map ENTITY_WITHDRAW_ATTRIBUTE = new HashMap();

	private static final String STATUS_FINAL = "Final";

	//private static final String ADSFEED_VALUE = "0020";
	private static final String ADSFEED_VALUE = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue("XLPOSTPROCABR");
	
	private static final DateFormat WITHDRAW_DATE_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy");

	private static final DateFormat DB_TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm.ss.SSS000");
	
	private static final int[] NLSIDS = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
	
	private static final Map report = new HashMap();
	
	private static final Map errorReport = new HashMap();

	static {
		// Map Entity Types with their respective Status and Withdraw Attribute
		// names
		ENTITY_STATUS_ATTRIBUTE.put("CATNAV", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("FB", "FBSTATUS");
		ENTITY_STATUS_ATTRIBUTE.put("FEATURE", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("IPSCFEAT", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("LSEO", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("LSEOBUNDLE", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("MM", "MMSTATUS");
		ENTITY_STATUS_ATTRIBUTE.put("MODEL", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("SVCFEATURE", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("SVCMOD", "STATUS");
		ENTITY_STATUS_ATTRIBUTE.put("SWFEATURE", "STATUS");

		ENTITY_WITHDRAW_ATTRIBUTE.put("CATNAV", "PUBTO");
		ENTITY_WITHDRAW_ATTRIBUTE.put("FB", "PUBTO");
		ENTITY_WITHDRAW_ATTRIBUTE.put("FEATURE", "WITHDRAWDATEEFF_T");
		ENTITY_WITHDRAW_ATTRIBUTE.put("IPSCFEAT", "WITHDRAWDATEEFF_T");
		ENTITY_WITHDRAW_ATTRIBUTE.put("LSEO", "LSEOUNPUBDATEMTRGT");
		ENTITY_WITHDRAW_ATTRIBUTE.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
		ENTITY_WITHDRAW_ATTRIBUTE.put("MM", "PUBTO");
		ENTITY_WITHDRAW_ATTRIBUTE.put("MODEL", "WTHDRWEFFCTVDATE");
		ENTITY_WITHDRAW_ATTRIBUTE.put("SVCFEATURE", "WITHDRAWDATEEFF_T");
		ENTITY_WITHDRAW_ATTRIBUTE.put("SVCMOD", "WTHDRWEFFCTVDATE");
		ENTITY_WITHDRAW_ATTRIBUTE.put("SWFEATURE", "WITHDRAWDATEEFF_T");
	}

	/*
	 * This variables are cached by their "get" method.
	 */

	private Date nowAsDate;

	public Date getNowAsDate() {
		if (nowAsDate == null) {
			try {
				nowAsDate = DB_TIMESTAMP_FORMAT.parse(getNow());
			} catch (ParseException e) {
				handleException(e);
			}
		}
		return nowAsDate;
	}

	/*
	 * End of cached variables
	 */

	/*
	 * This PackageID is used in the Translation.getPDHPackage in the
	 * getTranslationPackage() method below
	 */
	private PackageID getPackageID(int nlsid) {
		PackageID packageID;
		try {
			EntityGroup eg = m_db.getEntityGroup(m_prof,
					m_abri.getEntityType(), "Edit");
			EntityItem ei = new EntityItem(eg, m_prof, m_db, m_abri
					.getEntityType(), m_abri.getEntityID());
			EANFlagAttribute fa = (EANFlagAttribute) ei
					.getAttribute("BILLINGCODE");
			String strBillingCode = "";
			if (fa != null) {
				strBillingCode = fa.getFirstActiveFlagCode();
			}
			if (strBillingCode.equals("PCD")) {
				strBillingCode = "";
			}
			packageID = new PackageID(getEntityType(), getEntityID(), nlsid,
					"N/A", m_strNow, strBillingCode);
			return packageID;
		} catch (Exception e) {
			handleException(e);
		}

		return null;
	}

	private EntityList getEntityList(PackageID packageID) throws MiddlewareRequestException,
			SQLException, MiddlewareException {
		D.ebug("Getting entity list for PackageID nlsid="+packageID.getNLSID()+", language="+packageID.getLanguage());

		EntityGroup egXLATEGRP = new EntityGroup(null, m_db, m_prof,
				getEntityType(), "Edit");
		EntityItem eiParent = new EntityItem(egXLATEGRP, m_prof, m_db,
				getEntityType(), getEntityID());
		EntityItem[] aei = new EntityItem[] { eiParent };
		EANFlagAttribute flagExt = (EANFlagAttribute) eiParent
				.getAttribute("TRANSEXTRACTATTR");
		String strExtract = (flagExt == null) ? "EXTXLATEGRP1" : flagExt
				.getFirstActiveFlagCode();
		ExtractActionItem eai = new ExtractActionItem(null, m_db, m_prof,
				strExtract);
		EntityList entityList = new EntityList(m_db, m_prof, eai, aei,
				"XLATEGRP");
		entityList.getProfile().setReadLanguage(
				new NLSItem(packageID.getNLSID(), packageID.getLanguage()));
		return entityList;
	}
	
	private int nlsidFound;

	public void execute_run() {
		nlsidFound = -1;
		try {
			setReturnCode(PASS);
			start_ABRBuild(false);
			setNow();
			setControlBlock();
			buildRptHeader();
			for (int i = 0; i < NLSIDS.length; i++) {
				D.ebug("Checking NLSID "+NLSIDS[i]);
				if (nlsidFound != -1) break;
				handleLanguage(NLSIDS[i]);
			}

		} catch (Exception e) {
			handleException(e);
		} finally {
			if (nlsidFound == -1) {
				println("<p>Error: No NLSID found!</p>");
			} else {
				println("<h2>Not Queued Entities</h2>");
				Vector errorList = (Vector) errorReport.get(new Integer(nlsidFound));
				if (errorList != null) {
					Enumeration teIterator = errorList.elements();
					while (teIterator.hasMoreElements()) {
						StringBuffer sb = (StringBuffer) teIterator.nextElement();
						println(sb.toString());
					}
				}
				println("<br>");
				println("<h2>Queued Entities</h2>");
				Vector list = (Vector) report.get(new Integer(nlsidFound));
				if (list != null) {
					Enumeration teIterator = list.elements();
					while (teIterator.hasMoreElements()) {
						StringBuffer sb = (StringBuffer) teIterator.nextElement();
						println(sb.toString());
					}
				}
			}
		    setDGString(getABRReturnCode());
		    setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
			printDGSubmitString();
			println(EACustom.getTOUDiv());
			buildReportFooter();
		}
	}

	private void handleLanguage(int nlsid) throws SQLException,
			MiddlewareException {
		PackageID packageID = getPackageID(nlsid);
		EntityList entityList = null;
		try {
			entityList = getEntityList(packageID);
		} catch (Exception e) {
			D.ebug("Ignoring NLSID "+nlsid+". Reason: "+e.getClass()+" : "+e.getMessage());
			return;
		}
		
		TranslationPackage translationPackage = Translation.getETSPackage(m_db,
				m_prof, packageID);
		if (translationPackage == null) {
			D.ebug("Ignoring NLSID "+nlsid+". Reason: TranslationPackage is null");
			return;
		}
		
		Enumeration teIterator = translationPackage.getDataRequest()
				.getEntityElements();
		while (teIterator.hasMoreElements()) {
			TranslationEntity translationEntity = (TranslationEntity) teIterator.nextElement();
			handleTranslationEntity(nlsid, translationEntity, entityList);
		}
	}

	/**
	 * Handles the translation entity with the following rule Entity with Status =
	 * Final and Withdraw date after NOW will change the ADSABRSTATUS to
	 * &ADSFEED
	 */
	private void handleTranslationEntity(int nlsid, TranslationEntity translationEntity, EntityList el)
			throws MiddlewareException, SQLException {
		String entityType = translationEntity.getEntityType();
		int entityID = translationEntity.getEntityID();

		String statusAttributeName = (String) ENTITY_STATUS_ATTRIBUTE
				.get(entityType);
		String withdrawAttributeName = (String) ENTITY_WITHDRAW_ATTRIBUTE
				.get(entityType);

		D.ebug("Processing entity "+entityType+" ("+entityID+")");
		D.ebug("statusAttributeName = "+statusAttributeName);
		D.ebug("withdrawAttributeName = "+withdrawAttributeName);
		
		if (statusAttributeName == null) {
			return; // This entity type is not handled (not in the map), ignore
		}

		EntityGroup eg = el.getEntityGroup(entityType);
		EntityItem ei = eg.getEntityItem(entityType, entityID);
		
		StringBuffer sb = new StringBuffer();
		
		printEntityStart(sb, ei);
		boolean queued = false;
		String errorMessage = null;

		// This rule is applied only when STATUS = Final (0020)
		String statusValue = PokUtils.getAttributeValue(ei,
				statusAttributeName, "", "", false);
		EANAttribute withdrawAttribute = null;
		String withdrawDateValue = null;
		
		D.ebug("statusValue = "+statusValue);
		if (STATUS_FINAL.equals(statusValue)) {
			withdrawAttribute = ei.getAttribute(withdrawAttributeName);
			if (withdrawAttribute == null) {
				D.ebug("withdraw date is null");
				queued = true;
			} else {
				withdrawDateValue = PokUtils.getAttributeValue(ei,
						withdrawAttributeName, "", "", false);
				D.ebug("withdrawDateValue = "+withdrawDateValue);
				try {
					Date date = WITHDRAW_DATE_FORMAT.parse(withdrawDateValue);
					// Withdraw date must be after NOW
					if (date.after(getNowAsDate())) {
						queued = true;
					} else {
						errorMessage = "Withdrawal Date is past";
					}
				} catch (ParseException e) {
					handleException(e);
				}
			}
		} else {
			errorMessage = "Status is not final";
		}
		
		if (queued) {
			D.ebug("nlsidFound = "+nlsid);
			nlsidFound = nlsid;
			// Apply the rule: Set entity�s attribute ADSABRSTATUS to
			// ADSFEED (0020)
			setFlagValue(ei, "ADSABRSTATUS", ADSFEED_VALUE);
			printEntityEnd(sb);
			addEntityReport(nlsid, sb);
		} else {
			printAttribute(sb, "Status", ei.getAttribute(statusAttributeName), statusValue);
			printAttribute(sb, "Withdraw date", withdrawAttribute, withdrawDateValue);
			printErrorMessage(sb, errorMessage);
			printEntityEnd(sb);
			addEntityErrorReport(nlsid, sb);
		}
		

	}

	public String getABRVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "XLPOSTPROCABR";
	}

	private void handleException(Exception ex) {
		println("<p><h3 style=\"color:#c00; font-weight:bold;\">Exception: "+ex.getMessage()+"</h3>");
		ex.printStackTrace(getPrintWriter());
		println("</p>");
		ex.printStackTrace();
	}
	
	 /*
	   *  builds the standard header and submits it to the passed PrintWriter
	   */
	  private void buildRptHeader() throws SQLException, MiddlewareException{

		  String strVersion = getVersion();
		  String astr1[] = new String[]{getABRDescription(), strVersion, getEnterprise()};
		  String strMessage1 = buildMessage(MSG_IAB0001I, astr1);
		
		  println(EACustom.getDocTypeHtml());
		  println("<head>");
		  println(EACustom.getMetaTags("XLPOSTPROCABR.java"));
		  println(EACustom.getCSS());
		  println(EACustom.getTitle(getShortClassName(getClass())));
		  println("</head>");
		  println("<body id=\"ibm-com\">");
		  println(EACustom.getMastheadDiv());
		  
		  println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + strMessage1 + "</em></p>");
		  println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">" + NEW_LINE +
			  "<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
			  getABRVersion() + "</td></tr>" + NEW_LINE +
			  "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" +
			  getValOn() + "</td></tr>" + NEW_LINE +
			  "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" +
			  getEffOn() + "</td></tr>" + NEW_LINE +
			  "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" +
			  getNow() + "</td></tr>" + NEW_LINE +
			  "</table>");
		  println("<h3>Description: </h3>" + getDescription() + NEW_LINE + "<hr />" + NEW_LINE);

	  }
	  
	  private void addEntityReport(int nlsid, StringBuffer sb) {
		  Integer key = new Integer(nlsid);
		  Vector vec = (Vector) report.get(key);
		  if (vec == null) {
			  vec = new Vector();
			  report.put(key, vec);
		  }
		  vec.add(sb);
	  }

	  private void addEntityErrorReport(int nlsid, StringBuffer sb) {
		  Integer key = new Integer(nlsid);
		  Vector vec = (Vector) errorReport.get(key);
		  if (vec == null) {
			  vec = new Vector();
			  errorReport.put(key, vec);
		  }
		  vec.add(sb);
	  }
	  
	  private void printEntityStart(StringBuffer sb, EntityItem entityItem) {
		  sb.append("<p>");
		  sb.append("Entity type: "+entityItem.getEntityType()+"<br>");
		  sb.append("Entity ID: "+entityItem.getEntityID()+"<br>");
		  sb.append("Entity description: "+entityItem.getLongDescription()+"<br>");
	  }
	  
	  private void printEntityEnd(StringBuffer sb) {
		  sb.append("<br></p>");
	  }

	  private void printAttribute(StringBuffer sb, String name, EANAttribute attribute, String status) {
		  if (attribute == null) {
			  sb.append(name+" (null): "+status+"<br>");
		  } else {
			  sb.append(name+" ("+attribute.getLongDescription()+"): "+status+"<br>");
		  }
	  }

	  private void printErrorMessage(StringBuffer sb, String errorMessage) {
		  sb.append("<b>Error: "+errorMessage+"</b><br>");
	  }

}
