package COM.ibm.eannounce.darule;

//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.sql.SQLException;
import java.text.*;
import java.io.*;

/**
 * 
 * B.	Offering Data
 * New (or changed) offering information requires that the Derived Attributes for the offering be derived (or updated). 
 * The Offering entities will have a new Workflow action to queue this ABR Offering data that is â€œReady for Reviewâ€� 
 * but never â€œFinalâ€� or that is â€œFinalâ€� needs to be queued for XML generation. This ABR will have a properties file to 
 * specify the values for queuing the XML generation (ADSABRSTATUS). If STATUS is â€œRead for Reviewâ€�,
 * ADSABRSTATUS will be set to &ADSFEEDRFR. If STATUS is â€œFinalâ€�, ADSABRSTATUS will be set to &ADSFEED. 
 * These two symbols are defined in the properties file.

 * @author guobin
 *
 */
public class CATDATAABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();

	private StringBuffer xmlgenSb = new StringBuffer();

	private static final char[] FOOL_JTEST = { '\n' };

	static final String NEWLINE = new String(FOOL_JTEST);

	private PrintWriter dbgPw = null;

	private int dbgLen = 0;;

	protected static final Hashtable ITEM_VE_TBL;

	protected static final Hashtable WTHDRWEFFCTVDATE_Attr_TBL;

	protected static final String STATUS_FINAL = "0020";

	protected static final String STATUS_R4REVIEW = "0040";

	protected static final String ABR_INPROCESS = "0050";

	protected static final String CHEAT = "@@";

	protected static final String LIFECYCLE_Develop = "LF02";// LIFECYCLE	=	"Develop" (LF02)

	protected static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan

	protected static final String LIFECYCLE_Launch = "LF03";// LIFECYCLE	=	LF01	Plan

	protected static final String DALIFECYCLE_Change = "50";

	private String actionTaken = "";
	
	private String navName = "";
	
	private String rootDesc = "";

	private EntityList m_elist = null;

	private ResourceBundle rsBundle = null;

	private Object[] args = new String[10];

	private StringBuffer userxmlSb = new StringBuffer(); // output in the report, not value sent to MQ - diff transform used
	static {
		ITEM_VE_TBL = new Hashtable();
		ITEM_VE_TBL.put("WWSEO", "DAVEWWSEO");
		ITEM_VE_TBL.put("FEATURE", "DAVEFEATURE");
		ITEM_VE_TBL.put("MODEL", "DAVEMODEL");
		ITEM_VE_TBL.put("SVCMOD", "DAVESVCMOD");
		ITEM_VE_TBL.put("LSEOBUNDLE", "DAVELSEOBUNDLE");
		ITEM_VE_TBL.put("LSEO", "DAVELSEO");
	}
	static {
		WTHDRWEFFCTVDATE_Attr_TBL = new Hashtable();
		WTHDRWEFFCTVDATE_Attr_TBL.put("FEATURE", "WITHDRAWDATEEFF_T");
		WTHDRWEFFCTVDATE_Attr_TBL.put("LSEO", "LSEOUNPUBDATEMTRGT");
		WTHDRWEFFCTVDATE_Attr_TBL.put("LSEOBUNDLE", "BUNDLUNPUBDATEMTRGT");
		WTHDRWEFFCTVDATE_Attr_TBL.put("MODEL", "WTHDRWEFFCTVDATE");
		WTHDRWEFFCTVDATE_Attr_TBL.put("SVCMOD", "WTHDRWEFFCTVDATE");
		WTHDRWEFFCTVDATE_Attr_TBL.put("SWFEATURE", "WITHDRAWDATEEFF_T");
		WTHDRWEFFCTVDATE_Attr_TBL.put("WWSEO", "WTHDRWEFFCTVDATE");
	}

	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run() {
		// must split because too many arguments for messageformat, max of 10.. this was 11
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
			+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
			+ EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;

		MessageFormat msgf;

		println(EACustom.getDocTypeHtml()); //Output the doctype and html
		try {
			start_ABRBuild(false); // dont pull VE yet
			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			setReturnCode(PASS);
			String rootType = getEntityType();
			String VeName = (String) ITEM_VE_TBL.get(rootType);
			if (VeName != null) {
				//get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
				ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof, VeName);
				//xai.setSkipCleanup(true);
				m_elist = m_db.getEntityList(m_prof, xai, new EntityItem[] { new EntityItem(null, m_prof, getEntityType(),
					getEntityID()) });
				// get root from VE
				EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
				rootDesc = m_elist.getParentEntityGroup().getLongDescription();
				String statusFlag = getAttributeFlagEnabledValue(rootEntity, "STATUS");
				//NAME is navigate attributes
				navName = getNavigationName(rootEntity);
				try {
					if (!STATUS_FINAL.equals(statusFlag) && !STATUS_R4REVIEW.equals(statusFlag)) {
						//RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
						//addError("CATADATAABR was queued; however, it is not Ready for Review or Final.");
						addError(rsBundle.getString("ROOT_NOT_RFRFIANL"));
					} else if ("WWSEO".equals(rootEntity.getEntityType())) {
						//If DARULE.DAENTITYTYPE = â€œWWSEOâ€�, then
						//For all WWSEO where the is at least one child LSEO where
						//	Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
						//	NOW() <= the parent MODELâ€™s Withdrawal Effective Date (WTHDRWEFFCTVDATE)
						//  MODEL ---> WWSEO---> LSEO
						Vector lseoVec = getValidLSEO(rootEntity);
						if (lseoVec.size() > 0) {
							if (verifyMODELWithdrawEffe(rootEntity)) {
								boolean chgsmade = DARuleEngineMgr.updateCatData(m_db, m_prof, rootEntity, rptSb);
								if (chgsmade) {
									//They  will not queue WWSEO.ADSABRSTATUS, they will queue LSEO.ADSABRSTATUS.  
									//WWSEO does not have an ADSABRSTATUS attribute.
									//The LIFECYCLE check is done on LSEO, not WWSEO
									//For example:
									//Find one WWSEO which has at least one LSEO and LSEO.STATUS is equals to "RFR" and 
									//WWSEO.LSEO.LIFECYCLE <> "Develop" (LF02)  | "Plan" (LF01)  and trigger the ABR by workflow action.
                                    for (int j=0; j<lseoVec.size(); j++){
                                    	EntityItem lseo = (EntityItem)lseoVec.elementAt(j);
                                    	if (isValidQueueItem(lseo)) {
    										setFlagValue(m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"),
    											lseo);
    										actionTaken = rsBundle.getString("SUCCESS");
    									} else {
    										addDebug(rsBundle.getString("STATUS_Final_Before"));
    									}	
                                    }
								} else {
									actionTaken = rsBundle.getString("NO_CHANGE_FOUND");
									//addXMLGenMsg("No Chang Found, Do not need to Queue ADSABRSTATUS");
								}
							}
						} else {
							addDebug(rootEntity.getKey() + " have no child LSEO which is not Final or R4R");
							//addError("CATADATAABR was queued; however, root WWSEO have no child LSEO which is not Final or R4R.");
							addError(rsBundle.getString("NOT_FOUND_LSEORFRFINAL"));
						}
						lseoVec.clear();

					} else if (verifyMODELWithdrawEffe(rootEntity)){						
						boolean chgsmade = DARuleEngineMgr.updateCatData(m_db, m_prof, rootEntity, rptSb);
						if (chgsmade) {
							if (isValidQueueItem(rootEntity)) {
								setFlagValue(m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"),
									rootEntity);
								actionTaken = rsBundle.getString("SUCCESS");
							} else {
								addDebug(rsBundle.getString("STATUS_Final_Before"));
							}
						} else {
							actionTaken = rsBundle.getString("NO_CHANGE_FOUND");
							//addXMLGenMsg("No Chang Found, Do not need to Queue ADSABRSTATUS");
						}
					}
				} catch (DARuleException DARexc) {
					String err = DARexc.getMessage();
					addError(err);
				}

			}
		} catch (Throwable exc) {
			addXMLGenMsg("Failed Found Exception");
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + exc.getMessage());
			logError(exBuf.getBuffer().toString());
		} finally {
			if (m_elist != null)
				m_elist.dereference();
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriters();
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		//
		String header2 = null;
		header2 = buildDQTriggeredRptHeader();
		//XML_MSG= XML Message
		String info = header1 + header2 + userxmlSb.toString() + "</pre>" + NEWLINE;
		rptSb.insert(0, info);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

	/**
	 * If DARULE.DAENTITYTYPE = â€œWWSEOâ€�, then
	 For all WWSEO where the is at least one child LSEO where
	 Status (STATUS) = â€œReady for Reviewâ€� or â€œFinalâ€�
	 NOW() <= the parent MODELâ€™s Withdrawal Effective Date (WTHDRWEFFCTVDATE)
	 then derive or update CATDATA for DAATTRIBUTECODE (see the prior section)
	 If there is no value for WTHDRWEDDCTVDATE, then return true

	 * @param rootEntity
	 * @return
	 * @throws IOException 
	 */
	private boolean verifyMODELWithdrawEffe(EntityItem rootEntity) throws IOException {
		boolean result = false;
		if ("WWSEO".equals(rootEntity.getEntityType())){
			Vector uplinkVect = rootEntity.getUpLink();
			for (int i = 0; i < uplinkVect.size(); i++) {
				EntityItem relator = (EntityItem) uplinkVect.elementAt(i);
				if (relator != null && "MODELWWSEO".equals(relator.getEntityType())) {
					EntityItem model = (EntityItem) relator.getUpLink(0);
					if (model != null && "MODEL".equals(model.getEntityType())) {
						String attrCode = (String) WTHDRWEFFCTVDATE_Attr_TBL.get(model.getEntityType());
						if (attrCode != null) {	
							EANMetaAttribute metaAttr = model.getEntityGroup().getMetaAttribute(attrCode);
							if (metaAttr == null){
								addOutput("The Global Withdrawal Date Effective:" + attrCode +" not found in Meta data.");
								return false;
							}
							String withdrwDate = PokUtils.getAttributeValue(model, attrCode, "", CHEAT, false);
							addDebug("The value of Global Withdrawal Date Effective : " + withdrwDate);
							if (CHEAT.equals(withdrwDate)){
								addDebug("there is no value for WTHDRWEDDCTVDATE, return ture.");
								return true;
							}					
							String currentTime = m_prof.getNow().substring(0,10);
							if (currentTime.compareTo(withdrwDate) <= 0) {
								result = true;
							} else {
								result = false;
								addError(rsBundle.getString("Expire_Withdraw_Effective"));
								//addError("Failed excute updateCATADATA, because NOW > the parent MODELâ€™s Withdrawal Effective Date.");
							}
						} else {
							addError(rsBundle.getString("NOT_FOUND_Withdraw_Effective_ATTR"));
							addDebug("There is no attribute code specified for Global Withdrawal Date Effective. "
								+ rootEntity.getKey());

						}
						break;
					}
				}
			}
			
		}else {
			String attrCode = (String) WTHDRWEFFCTVDATE_Attr_TBL.get(rootEntity.getEntityType());
			if (attrCode != null) {					
				String withdrwDate = PokUtils.getAttributeValue(rootEntity, attrCode, "", CHEAT, false);
				addDebug("The value of Global Withdrawal Date Effective : " + withdrwDate);
				EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(attrCode);
				if (metaAttr == null){
					addOutput("The Global Withdrawal Date Effective:" + attrCode +" not found in Meta data.");
					return false;
				}
				if (CHEAT.equals(withdrwDate)){
					addDebug("there is no value for WTHDRWEDDCTVDATE, return ture.");
					return true;
				}					
				String currentTime = m_prof.getNow().substring(0,10);
				if (currentTime.compareTo(withdrwDate) <= 0) {
					result = true;
				} else {
					result = false;
					addError(rsBundle.getString("Expire_Withdraw_Effective"));
					//addError("Failed excute updateCATADATA, because NOW > the parent MODELâ€™s Withdrawal Effective Date.");
				}
			} else {
				addError(rsBundle.getString("NOT_FOUND_Withdraw_Effective_ATTR"));
				addDebug("There is no attribute code specified for Global Withdrawal Date Effective. "
					+ rootEntity.getKey());

			}
			
		}
		
		return result;
	}


	/**
	 * XML generator ABR may need to be queued. Offering data that is 'Ready for
	 * Review' but never 'Final' or that is â€œFinalâ€� needs to be queued for XML
	 * generation
	 * LIFECYCLE_Develop = "LF02";// LIFECYCLE	=	"Develop" (LF02)
     * LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan

	 * @param queuedItem
	 * @return
	 */
	private boolean isValidQueueItem(EntityItem queuedItem) {
		String statusFlag = getAttributeFlagEnabledValue(queuedItem, "STATUS");
		String lifeCycleFlag = getAttributeFlagEnabledValue(queuedItem, "LIFECYCLE");
		if (statusFlag == null ){
			addOutput("Entity attribute Status is null, do not queue ADSABRSTATUS");
			return false;
		}
		if (STATUS_FINAL.equals(statusFlag)){
			addOutput("Entity attribute Status is final, Queue ADSABRSTATUS");
			return true;
		}
		if (lifeCycleFlag == null) {
			addOutput("Entity attribute LifeCycle is null, do not queue ADSABRSTATUS");	
			return false;
		}
		if (STATUS_R4REVIEW.equals(statusFlag) && LIFECYCLE_Launch.equals(lifeCycleFlag)) {
			return false;
		}
		return true;
	}
	/**
	 * It should be looking each LSEO linked to the WWSEO and be queueing ADSABRSTATUS for each LSEO that meets the STATUS and LIFECYCLE checks.
     * The code is currently only using one LSEO.
	 * @param rootEntity
	 */
	private Vector getValidLSEO(EntityItem rootEntity) {
        Vector lseoVec = new Vector();
		Vector lseoVect = rootEntity.getDownLink();
		for (int i = 0; i < lseoVect.size(); i++) {
			EntityItem relator = (EntityItem) lseoVect.elementAt(i);
			if (relator != null && "WWSEOLSEO".equals(relator.getEntityType())) {
				EntityItem lseo = (EntityItem) relator.getDownLink(0);
				if (lseo != null && "LSEO".equals(lseo.getEntityType())) {
					String statusFlag = getAttributeFlagEnabledValue(lseo, "STATUS");
					if (STATUS_FINAL.equals(statusFlag) || STATUS_R4REVIEW.equals(statusFlag)){
						lseoVec.add(lseo);	
					}
				}
			}
		}
		return lseoVec;
	}

	/***********************************************
	 *  Sets the specified Flag Attribute on the specififed Entity
	 *
	 *@param    profile Profile
	 *@param    strAttributeCode The Flag Attribute Code
	 *@param    strAttributeValue The Flag Attribute Value
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareBusinessRuleException 
	 */
	protected void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue, EntityItem item)
		throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
		logMessage(getDescription() + " ***** " + item.getKey() + " " + strAttributeCode + " set to: " + strAttributeValue);
		addDebug("setFlagValue entered " + item.getKey() + " for " + strAttributeCode + " set to: " + strAttributeValue);

		if (strAttributeValue != null && strAttributeValue.trim().length() == 0) {
			addDebug("setFlagValue: " + strAttributeCode + " was blank for " + item.getKey() + ", it will be ignored");
			return;
		}

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (metaAttr == null) {
			addDebug("setFlagValue: " + strAttributeCode + " was not in meta for " + item.getEntityType() + ", nothing to do");
			logMessage(getDescription() + " ***** " + strAttributeCode + " was not in meta for " + item.getEntityType()
				+ ", nothing to do");
			return;
		}

		if (strAttributeValue != null) {
			//get the current value
			String curval = //getAttributeFlagEnabledValue(item,strAttributeCode);
			PokUtils.getAttributeFlagValue(item, strAttributeCode);
			if (strAttributeValue.equals(curval)) { // allow reset of STATUS if hard fail in update
				addDebug("setFlagValue: " + strAttributeCode + " was already set to " + curval + " for " + item.getKey()
					+ ", nothing to do");
				logMessage("setFlagValue: " + strAttributeCode + " was already set to " + curval + " for " + item.getKey()
					+ ", nothing to do");
				return;
			}

			// if this is queueing an abr, make sure it isnt 'in process' now
			if (metaAttr.getAttributeType().equals("A")) {
				// if the specified abr is inprocess, wait
				checkForInProcess(profile, item, strAttributeCode);
			}

			if (m_cbOn == null) {
				setControlBlock(); // needed for attribute updates
			}
			ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(), item.getEntityID(), true);
			SingleFlag sf = new SingleFlag(m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), strAttributeCode,
				strAttributeValue, 1, m_cbOn);
			Vector vctAtts = new Vector();
			Vector vctReturnsEntityKeys = new Vector();
			vctAtts.addElement(sf);
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
			m_db.update(m_prof, vctReturnsEntityKeys, false, false);
			m_db.commit();
		}
	}

	//	 if the specified abr is inprocess, wait
	private void checkForInProcess(Profile profile, EntityItem item, String strAttributeCode) {
		try {
			int maxTries = 0;
			String curval = // doesnt work on 'A' type attr getAttributeFlagEnabledValue(item,strAttributeCode);
			PokUtils.getAttributeFlagValue(item, strAttributeCode);

			addDebug("checkForInProcess:  entered " + item.getKey() + " " + strAttributeCode + " is " + curval);

			if (ABR_INPROCESS.equals(curval)) {
				DatePackage dpNow = m_db.getDates();
				// get current updates by setting to endofday
				profile.setValOnEffOn(dpNow.getEndOfDay(), dpNow.getEndOfDay());

				while (ABR_INPROCESS.equals(curval) && maxTries < 20) { // allow 10 minutes
					maxTries++;
					addDebug("checkForInProcess: " + strAttributeCode + " is " + curval + " sleeping 30 secs");
					Thread.sleep(30000);
					// get entity again
					EntityGroup eg = new EntityGroup(null, m_db, profile, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, profile, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem, strAttributeCode);
					addDebug("checkForInProcess: " + strAttributeCode + " is now " + curval + " after sleeping");
				}
			}
		} catch (Exception exc) {
			System.err.println("Exception in checkForInProcess " + exc);
			exc.printStackTrace();
		}
	}

	/**
	 * CATDATAABRSTATUS_ADSABRSTATUS_queuedValue=0020
	 * 
	 * @param abrcode
	 * @return
	 */
	private String getQueuedValue(String abrcode) {
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(m_abri.getABRCode() + "_" + abrcode);
	}

	/******************************************
	 * build xml generation msg
	 */
	protected void addXMLGenMsg(String info) {
		xmlgenSb.append(info + "<br />");
	}

	/******************************************
	 * A.	Data Quality Triggered ABRs
	 *
	 * The Report should identify:
	 * -	USERID (USERTOKEN)
	 * -	Role
	 * -	Workgroup
	 * -	Date/Time
	 * -	Status
	 * -	Prior feed Date/Time
	 * -	Prior Status
	 * -	Action Taken
	 */
	private String buildDQTriggeredRptHeader() {
		String HEADER2 = "<table>" + NEWLINE 
		    + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
			+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE 
			+ "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE
			+ "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE 
			+ "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE 
			+ "<tr><th>Action Taken: </th><td>{5}</td></tr>" + NEWLINE 
			+ "</table>" + NEWLINE;
		MessageFormat msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = m_prof.getNow();
		args[4] = rootDesc+": "+navName;
		args[5] = actionTaken + "<br />" + xmlgenSb.toString();
		return msgf.format(args);
	}
	/**********************************************************************************
	 *  Get Name based on navigation attributes
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg =  new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
		for (int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			navName.append(" ");
		}

		return navName.toString();
	}
	
	private void closePrintWriters() {
		if (dbgPw != null) {
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
	}

	/**********************************
	 * get database
	 */
	protected Database getDB() {
		return m_db;
	}

	/**********************************
	 * get attributecode
	 */
	protected String getABRAttrCode() {
		return m_abri.getABRCode();
	}

	/**********************************
	 * add msg to report output
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		if (dbgPw != null) {
			dbgLen += msg.length();
			dbgPw.println(msg);
			dbgPw.flush();
		} else {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}

	/**********************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion() {
		//return "1.8"; cvs failure
		return "1.12";//"1.10";
	}

	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription() {
		return "CATADATAABRSTATUS";
	}
}
