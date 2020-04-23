//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

/**
WWSEOABRDD_class=COM.ibm.eannounce.abr.sg.WWSEOABRDD
WWSEOABRDD_enabled=true
WWSEOABRDD_idler_class=A
WWSEOABRDD_keepfile=true
WWSEOABRDD_read_only=true
WWSEOABRDD_report_type=DGTYPE01
WWSEOABRDD_vename=WWSEODDABRVE
WWSEOABRDD_CAT1=RPTCLASS.WWSEOABRDD
WWSEOABRDD_CAT2=WWSEO.PDHDOMAIN
WWSEOABRDD_CAT3=RPTSTATUS
WWSEOABRDD_CAT4=SER.SERNAM
WWSEOABRDD_SUBSCRVE=WWDERDATASNVE

 *
 * WWSEOABRDD.java,v
 * Revision 1.25  2010/06/24 19:27:03  wendy
 * updates for BH FS ABR Derived Data Derivation 20100317.doc
 *
 * Revision 1.24  2008/08/22 12:20:45  wendy
 * Added more debug
 *
 * Revision 1.23  2008/08/15 13:09:16  wendy
 * CQ00001974-WI - LCM with GX1 - Move attributes from TECHINFO to DERIVEDDATA
 *
 * Revision 1.22  2008/01/30 19:39:17  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.21  2006/05/24 19:23:19  wendy
 * Correct msg when Procs exist but no Planar exists
 *
 * Revision 1.20  2006/03/23 21:16:56  wendy
 * more error handling
 *
 * Revision 1.19  2006/03/23 16:29:15  wendy
 * Updated error msgs
 *
 * Revision 1.18  2006/03/23 00:27:58  wendy
 * removed bad character from description
 *
 * Revision 1.17  2006/03/23 00:24:57  wendy
 * Corrected error msg
 *
 * Revision 1.16  2006/03/23 00:09:34  wendy
 * More logic and msg changes
 *
 * Revision 1.15  2006/03/21 21:36:07  wendy
 * Split VE, added more debug output, changed logic, more work needed
 *
 * Revision 1.14  2006/03/19 04:44:08  anhtuan
 * Per Wayne MEMORY.CAPUNIT can not be KB. If MEMORY.CAPUNIT = KB then print error message and nav name of MEMORY entity.
 *
 * Revision 1.13  2006/03/16 18:26:38  anhtuan
 * set hddCAP to type float.
 *
 * Revision 1.12  2006/03/14 20:45:38  anhtuan
 * Updated specs 30b FS xSeries Derived Data Derivation ABR 20060314.doc: RAM Sockets Available is no longer derived
 *
 * Revision 1.11  2006/03/09 16:32:31  anhtuan
 * TIR USRO-R-LBAR-6LYQNB.
 *
 * Revision 1.10  2006/02/28 19:13:20  anhtuan
 * Updated specs for CR0130064137. New attributes: BAYAVAILTYPE, ACCSS, BAYFF.
 *
 * Revision 1.9  2006/02/25 21:23:58  anhtuan
 * Remove redundant stuff.
 *
 * Revision 1.8  2006/02/23 03:33:53  anhtuan
 * AHE compliant.
 *
 * Revision 1.7  2006/02/22 04:00:59  anhtuan
 * Use PokUtils.
 *
 * Revision 1.6  2006/02/02 16:45:36  anhtuan
 * Fix.
 *
 * Revision 1.5  2006/01/27 16:29:41  anhtuan
 * Check for null values of COFCAT, COFSUBCAT, COFGRP.
 *
 * Revision 1.4  2006/01/26 15:03:13  anhtuan
 * AHE copyright.
 *
 * Revision 1.3  2006/01/26 04:24:17  anhtuan
 * Fixes.
 *
 * Revision 1.1  2005/09/08 15:56:09  anhtuan
 * Initial version.
 *
 *
 *
 *
 * </pre>
 *
 * @author     Anhtuan Nguyen
 * @created    August 30, 2005
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.transactions.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.*;

/**********************************************************************************
 * WWSEOABRDD class
 * 
 * BH FS ABR Derived Data Derivation 20110517b.doc
 * queue lseo.adsabrstatus based on wwseo.status and lifecycle
 * "The Derived Data ABR will be enhanced to queue LSEO�s that are children of the affected WWSEO to 
 * generate XML to be feed downstream if needed."
 * 
 *BH FS ABR Derived Data Derivation 20100715c.doc
 *RCQ00129135 Planars no longer need slots
 *
 * From spec "SG FS ABR Derived Data Derivation 20080814.doc"
 */
public class WWSEOABRDD extends PokBaseABR
{
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	private static final String NEWLINE = new String(FOOL_JTEST);
	private static final String INDENT1 = "&nbsp;&nbsp;";
	private static final String DELIMITER = "|";
	private static final long KB = 1000L;
	private static final long MB = 1000000L;
	private static final long GB = 1000000000L;
	private static final String HARDWARE = "100";
	private static final String SYSTEM = "126";
	private static final String DRAWER = "162";
	private static final String BASE = "150";
	private static final String PLANAR_SLOTAVAIL = "0020";
	private static final String MEMORYCARD_SLOTAVAIL = "0010";
	private static final String EXPDUNIT_SLOTAVAIL = "0030";

	private static final String SYSTEM_UNIT_BASE="273";
	private static final String CUSTOMER_FEATURE_CHOICE="230";
	private static final String UNSELECTED_FEAT_ATTACHMNT="240";
	private static final String SELECTABLE_FEAT_MECH="238";

	private static final String STATUS_FINAL = "0020";
	private static final String STATUS_R4REVIEW   = "0040";
	private final static String FOREVER_DATE = "9999-12-31";
	private static final String ABR_INPROCESS = "0050";
	private static final String ABR_QUEUED = "0020";
	private static final String LIFECYCLE_Develop	= "LF02";// LIFECYCLE	=	"Develop" (LF02)
	private static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan

	private ResourceBundle rsBundle = null;
	private PDGUtility pdgUtil = new PDGUtility();
	private OPICMList derivedDataAttList = new OPICMList();
	private Hashtable metaTbl = new Hashtable();
	private int totAvailBay = 0;
	private int totAvailCardSlot = 0;
	private Object[] args = new String[10];

	private Hashtable fcElemTbl = new Hashtable();
	private Vector errMsgVct = new Vector(1); // prevent duplicate error msgs to user
	private String strNow=null;

	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run()
	{
		String HEADER = "<head>"+
		EACustom.getMetaTags(getDescription()) + NEWLINE +
		EACustom.getCSS() + NEWLINE +
		EACustom.getTitle("{0} {1}") + NEWLINE +
		"</head>" + NEWLINE + "<body id=\"ibm-com\">" +
		EACustom.getMastheadDiv() + NEWLINE +
		"<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE+
		"<p><b>Date: </b>{2}<br /><b>User: </b>{3} ({4})<br /><b>Description: </b>{5}</p>"+NEWLINE+
		"<!-- {6} -->" + NEWLINE;

		MessageFormat msgf;
		String navName = "";

		println(EACustom.getDocTypeHtml()); //Output the doctype and html

		try
		{
			start_ABRBuild();

			rsBundle = ResourceBundle.getBundle(this.getClass().getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

			EntityItem wwseoEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			addDebug("WWSEOABRDD entered for " + wwseoEntity.getKey()+" extract "+
					m_abri.getVEName()+	NEWLINE + PokUtils.outputList(m_elist));

			//Default set to pass
			setReturnCode(PASS);

//			fixme remove this.. avoid msgs to test userid
//setCreateDGEntity(false);   

			//NAME is navigate attributes
			navName = getNavigationName(wwseoEntity);

			rptSb.append("<h2>"+m_elist.getParentEntityGroup().getLongDescription()+" "+navName+ "</h2>" + NEWLINE);

			// m_elist this will have a MODEL if FEATUREs are linked
			// to the WWSEO, there will be only one because only FEATUREs linked to a particular
			// MODEL can be linked to a WWSEO
			addHeading(3," Domain and Model verification:");
			verifyDomainAndModel(wwseoEntity);  
			if (getReturnCode()==PASS)//only create/update DD for MODEL with 100 = HW, 126 = System, 150 = Base
			{
				//Perform checking
				performChecking(wwseoEntity);

				strNow = m_db.getDates().getNow().substring(0, 10);

				//Update DERIVEDDATA
				updateDerivedDataEntity(wwseoEntity);

				if(getReturnCode()==PASS) {
					//X.	Post Processing
					postProcess(wwseoEntity);

					String msg = rsBundle.getString("SUCCESS");
					args[0] = m_elist.getEntityGroup("DERIVEDDATA").getLongDescription();
					msgf = new MessageFormat(msg);
					rptSb.append("<p>"+msgf.format(args) + "</p>" + NEWLINE);
				}
			}
		}
		catch(Throwable exc)
		{
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE="<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(FAIL);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
		}
		finally
		{
			cleanUp();
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass("WWSEOABRDD");
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName + ((getReturnCode() == PASS) ? " Passed" : " Failed");
		args[2] = getNow();
		args[3] = m_prof.getOPName();
		args[4] = m_prof.getRoleDescription();
		args[5] = getDescription();
		args[6] = getABRVersion();

		rptSb.insert(0, msgf.format(args) + NEWLINE);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

	/**
	 * X.	Post Processing
	 * 
	 * If this ABR is successful and makes a change to Derived Product Data (DERIVEDDATA), then there may be a 
	 * need to queue the XML generation for LSEOs that are children of this WW Single Entity Offering (WWSEO).
	 * 
	 * All children LSEO of the WWSEO where the LSEO�s �Unpublish Date � Target� (LSEOUNPUBDATEMTRGT) > NOW() 
	 * need to be handled based on the STATUS of the WWSEO and LSEO as follows:
	 * 1.	WW Single Entity Offering (WWSEO) Status (STATUS) is "Ready for Review" (0040) and 
	 * �Life Cycle� (LIFECYCLE) = "Develop" (LF02)  | "Plan" (LF01)
	 * �	If the LSEO �Life Cycle� (LIFECYCLE) = "Develop" (LF02) | "Plan" (LF01) and 
	 * 		�Status� (STATUS) = "Ready for Review" (0040), then set ADS XML Feed ABR (ADSABRSTATUS) = &ADSFEEDRFR.
	 * 2.	WW Single Entity Offering (WWSEO) Status (STATUS) is Final (0020) 
	 * �	If the LSEO �Life Cycle� (LIFECYCLE) = "Develop" (LF02) | "Plan" (LF01) and 
	 * 		�Status� (STATUS) = "Ready for Review" (0040), then set ADS XML Feed ABR (ADSABRSTATUS) = &ADSFEEDRFR.
	 * �	If the LSEO �Status� (STATUS) = "Final" (0020), then set ADS XML Feed ABR (ADSABRSTATUS) = &ADSFEED.
	 * 
	 * The ABR�s property file should provide the values for &ADSFEEDRFR and &ADSFEED.
	 * 
	 * @param wwseoItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	private void postProcess(EntityItem wwseoItem) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException{
		String status = PokUtils.getAttributeFlagValue(wwseoItem, "STATUS");
		String lifecycle = PokUtils.getAttributeFlagValue(wwseoItem, "LIFECYCLE");
		if (lifecycle==null || lifecycle.length()==0){
			lifecycle = LIFECYCLE_Plan;
		}
		addDebug("postProcess: "+wwseoItem.getKey()+" status "+status+" lifecycle "+lifecycle);

		if(STATUS_FINAL.equals(status) || 	//2.	WW Single Entity Offering (WWSEO) Status (STATUS) is Final (0020) 
				(STATUS_R4REVIEW.equals(status) && //1.	WW Single Entity Offering (WWSEO) Status (STATUS) is "Ready for Review" (0040) and 
						(LIFECYCLE_Plan.equals(lifecycle) ||   // �Life Cycle� (LIFECYCLE) = "Develop" (LF02)  | "Plan" (LF01)
								LIFECYCLE_Develop.equals(lifecycle)))){ 
		}else{
			addDebug("postProcess: "+wwseoItem.getKey()+" status and/or lifecycle criteria not met");
			return;
		}

		boolean queueFinal = STATUS_FINAL.equals(status); // only queue final lseo if wwseo is final

		// pull VE - only goes to LSEO
		EntityList lseolist = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db, m_prof, "DQVEWWSEOLSEO"),
				new EntityItem[] { wwseoItem });

		addDebug("postProcess DQVEWWSEOLSEO: "+PokUtils.outputList(lseolist));
		Vector vctReturnsEntityKeys = new Vector();
		String queuedValue = getQueuedValueForItem("ADSABRSTATUS");
		String rfrqueuedValue = getRFRQueuedValueForItem("ADSABRSTATUS");
		EntityGroup lseoGrp = lseolist.getEntityGroup("LSEO");
		for (int g=0; g<lseoGrp.getEntityItemCount(); g++){
			EntityItem lseo = lseoGrp.getEntityItem(g);
			status = PokUtils.getAttributeFlagValue(lseo, "STATUS");
			String wdDate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
			addDebug("postProcess: "+lseo.getKey()+" status "+status+" wdDate "+wdDate);
			//	LSEO�s �Unpublish Date � Target� (LSEOUNPUBDATEMTRGT) > NOW() 
			if(strNow.compareTo(wdDate)<0){
				//	If the LSEO �Status� (STATUS) = "Final" (0020), 
				if(STATUS_FINAL.equals(status)){
					if(queueFinal){ // 2.	WW Single Entity Offering (WWSEO) Status (STATUS) is Final (0020) 
						// then set ADS XML Feed ABR (ADSABRSTATUS) = &ADSFEED.
						setFlagValue("ADSABRSTATUS", queuedValue,lseo,vctReturnsEntityKeys);
					}else{
						addDebug("postProcess: skipping final "+lseo.getKey()+" wwseo is not final");
					}
				}else if (STATUS_R4REVIEW.equals(status)){
					//If the LSEO �Life Cycle� (LIFECYCLE) = "Develop" (LF02) | "Plan" (LF01) and 
					//�Status� (STATUS) = "Ready for Review" (0040), 
					// check lifecycle
					lifecycle = PokUtils.getAttributeFlagValue(lseo, "LIFECYCLE");
					addDebug("postProcess: "+lseo.getKey()+" lifecycle "+lifecycle);
					if (lifecycle==null || lifecycle.length()==0){
						lifecycle = LIFECYCLE_Plan;
					}
					if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
							LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
						//then set ADS XML Feed ABR (ADSABRSTATUS) = &ADSFEEDRFR.
						setFlagValue("ADSABRSTATUS", rfrqueuedValue,lseo,vctReturnsEntityKeys);
					}else{
						addDebug("postProcess: skipping rfr "+lseo.getKey()+" lifecycle is not plan or develop");
					}
				}
			}else{
				addDebug("postProcess: skipping withdrawn "+lseo.getKey());
			}
		}

		if(vctReturnsEntityKeys.size()>0){
			updatePDH(vctReturnsEntityKeys);
		}
		lseolist.dereference();
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 */
	private void updatePDH(Vector vctReturnsEntityKeys)
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug("updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys.size());

		try {
			m_db.update(m_prof, vctReturnsEntityKeys, false, false);
		}
		finally {
			vctReturnsEntityKeys.clear();
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending("finally after updatePDH");
		}
	}

	/**********************************
	 * stringbuffer used for report output
	 */
	private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**
	 * @param attrcode
	 * @return
	 */
	private String getQueuedValueForItem(String attrcode){
		// get queued value from properties for that
		String abrAttrCode = "LSEOABRSTATUS";
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.QUEUEDVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,ABR_QUEUED);
	}
	/**
	 * @param attrcode
	 * @return
	 */
	private String getRFRQueuedValueForItem(String attrcode){
		// get rfrqueued value from properties for that
		String abrAttrCode = "LSEOABRSTATUS";
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.RFRQUEUEDVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,ABR_QUEUED);
	}
	/***********************************************
	 *  Sets the specified Flag Attribute on the specified Entity
	 *
	 * @param strAttributeCode
	 * @param strAttributeValue
	 * @param item
	 * @param vctReturnsEntityKeys
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void setFlagValue(String strAttributeCode, String strAttributeValue,
			EntityItem item, Vector vctReturnsEntityKeys) throws SQLException, MiddlewareException
	{
		addDebug("setFlagValue entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
				strAttributeValue);
		
		if (strAttributeValue!=null && strAttributeValue.trim().length()==0) {
			addDebug("setFlagValue: "+strAttributeCode+" was blank for "+item.getKey()+", it will be ignored");
			return;
		}

		//get the current value
		String curval = PokUtils.getAttributeFlagValue(item,strAttributeCode);
		if (strAttributeValue.equals(curval)){
			addDebug("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
			return;
		}

		// if the specified abr is inprocess, wait
		checkForInProcess(item,strAttributeCode);

		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
		rek.m_vctAttributes = new Vector();
		vctReturnsEntityKeys.addElement(rek);

		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), item.getEntityType(), item.getEntityID(),
				strAttributeCode, strAttributeValue, 1, m_cbOn);

		rek.m_vctAttributes.addElement(sf);

		//ATTR_SET = {0} was set to {1} for {2} {3}
		MessageFormat msgf = new MessageFormat(rsBundle.getString("ATTR_SET"));

		args[0] = PokUtils.getAttributeDescription(item.getEntityGroup(), strAttributeCode, strAttributeCode);
		args[1] = strAttributeValue;
		// get flag description
		EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) item.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (mfa!=null){
			MetaFlag mf = mfa.getMetaFlag(strAttributeValue);
			if (mf!=null){
				args[1] = mf.toString();
			}
		}else{
			addDebug("Error: "+strAttributeCode+" not found in META for "+item.getEntityType());
		}

		args[2] = item.getEntityGroup().getLongDescription();
		args[3] = getNavigationName(item);

		addOutput(msgf.format(args));
	}

	/**
	 * if the specified abr is inprocess, wait
	 * 
	 * @param item
	 * @param strAttributeCode
	 */
	private void checkForInProcess(EntityItem item,String strAttributeCode){
		try{
			int maxTries = 0;
			String curval = // doesnt work on 'A' type attr getAttributeFlagEnabledValue(item,strAttributeCode);
				PokUtils.getAttributeFlagValue(item,strAttributeCode);

			addDebug("checkForInProcess:  entered "+item.getKey()+" "+strAttributeCode+" is "+curval);

			if (ABR_INPROCESS.equals(curval)){
				DatePackage dpNow = m_db.getDates();
				// get current updates by setting to endofday
				m_prof.setValOnEffOn(dpNow.getEndOfDay(),dpNow.getEndOfDay());

				while(ABR_INPROCESS.equals(curval) && maxTries<20){ // allow 10 minutes
					maxTries++;
					addDebug("checkForInProcess: "+strAttributeCode+" is "+curval+" sleeping 30 secs");
					Thread.sleep(30000);
					// get entity again
					EntityGroup eg = new EntityGroup(null,m_db, m_prof, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, m_prof, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem,strAttributeCode);
					addDebug("checkForInProcess: "+strAttributeCode+" is now "+curval+" after sleeping");
				}
			}
		}catch(Exception exc){
			System.err.println("Exception in checkForInProcess "+exc);
			exc.printStackTrace();
		}
	}

	/**********************************************************************************
	 * This ABR will only consider WWSEOs where PDHDOMAIN = xSeries (0050) | (0390) �Converged Products� 
	 * and the parent MODEL is classified:
	 * 	COFCAT = Hardware (100)
	 * 	COFSUBCAT = System (126) | Drawer (162)
	 * 	COFGRP = Base (150)
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private void verifyDomainAndModel(EntityItem rootItem) throws MiddlewareRequestException, SQLException, MiddlewareException
	{
		EntityGroup mdlGrp = m_elist.getEntityGroup("MODEL");
		String modelCOFCAT;
		String modelCOFSUBCAT;
		String modelCOFGRP;
		EntityItem modelItem = null;
		EntityList list = null;
		boolean isok = false;		

		// this will have a MODEL if FEATUREs are linked
		// to the WWSEO, there will be only one because only FEATUREs linked to a particular
		// MODEL can be linked to a WWSEO
		if(mdlGrp.getEntityItemCount() == 0) {
			// pull second VE to do classification check - only goes to MODEL
			list = m_db.getEntityList(m_prof,
					new ExtractActionItem(null, m_db, m_prof, "WWSEODDABRVE2"),
					new EntityItem[] { rootItem });
			mdlGrp = list.getEntityGroup("MODEL");
			addDebug("DEBUG WWSEODDABRVE2: "+PokUtils.outputList(list));
		}

		modelItem = mdlGrp.getEntityItem(0);

		modelCOFCAT = getAttributeFlagEnabledValue(modelItem, "COFCAT");
		modelCOFSUBCAT = getAttributeFlagEnabledValue(modelItem, "COFSUBCAT");
		modelCOFGRP = getAttributeFlagEnabledValue(modelItem, "COFGRP");

		//100 = HW, 126 = System, 150 = Base
		isok= (HARDWARE.equals(modelCOFCAT) && 
				(SYSTEM.equals(modelCOFSUBCAT) || DRAWER.equals(modelCOFSUBCAT)) && 
				BASE.equals(modelCOFGRP));

		if (!isok) {
			args[0] = modelItem.getEntityGroup().getLongDescription();
			printError("MODEL_CLASSIFICATION_ERROR",args);  // "Error - {0} classification is not HW-(System|Drawer)-Base.
			print3a(modelItem);
		}else{
			checkDomain(rootItem);
		}

		if (list !=null) {
			list.dereference();
		}
	}
	/**********************************
	 * stringbuffer used for report output
	 */
	private void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}	
	/**********************************
	 * stringbuffer used for report output
	 */
	private void addHeading(int level, String msg) { rptSb.append("<h"+level+">"+msg+"</h"+level+">"+NEWLINE);}

	/*************************************************************************************
	 * Check the PDHDOMAIN
	 * xseries and converged prod need DQ checks in the ABRs but the other domains like iseries don't
	 * because those Brands do not want any checking, they do not use STATUS, they want no process
	 * 
	 */
	private void checkDomain(EntityItem rootEntity)
	{
		boolean bdomainInList = false;
		String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
		addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
		if (domains.equals("all")){
			bdomainInList = true;
		}else{
			Set testSet = new HashSet();
			StringTokenizer st1 = new StringTokenizer(domains,",");
			while (st1.hasMoreTokens()) {
				testSet.add(st1.nextToken());
			}
			bdomainInList = PokUtils.contains(rootEntity, "PDHDOMAIN", testSet);
			testSet.clear();
		}

		if (!bdomainInList){
			addDebug("PDHDOMAIN did not include "+domains+", ["+
					PokUtils.getAttributeValue(rootEntity, "PDHDOMAIN",", ", "", false)+"]");
			args[0] = rootEntity.getEntityGroup().getLongDescription();
			args[1] = PokUtils.getAttributeValue(rootEntity, "PDHDOMAIN",", ", "", false);
			printError("DOMAIN_ERROR",args);  // DOMAIN_ERROR = Error - &quot;{0}&quot; {1} Domain is not supported.
		}
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	protected String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();

		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)
		{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}

		return navName.toString().trim();
	}

	/********************************************************************************
	 * Find entities of the destination type linked to the EntityItems in the source
	 * vector through the specified link type.  Both uplinks and downlinks are checked
	 * though only one will contain a match.
	 * All objects in the source Vector must be EntityItems of the same entity type
	 *
	 * @param entityItem EntityItem
	 * @param linkType   String Association or Relator type linking the entities
	 * @param destType   String EntityType to match
	 * @returns Vector of EntityItems
	 */
	private static Vector getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
	{
		// find entities thru 'linkType' relators
		Vector destVct = new Vector(1);
		getLinkedEntities(entityItem, linkType, destType, destVct);
		return destVct;
	}
	//------------------------------------------------------
	// get string for messages
	/**************************************
	 * Get long description and navigation name for specified entity
	 * @param item
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getLD_NDN(EntityItem item) throws SQLException, MiddlewareException    {
		return item.getEntityGroup().getLongDescription()+" &quot;"+getNavigationName(item)+"&quot;";
	}
	/********************************************************************************
	 * Find entities of the destination type linked to the specified EntityItem through
	 * the specified link type.  Both uplinks and downlinks are checked though only
	 * one will contain a match. Report ALL duplicates!
	 *
	 * @param entityItem EntityItem
	 * @param linkType   String Association or Relator type linking the entities
	 * @param destType   String EntityType to match
	 * @param destVct    Vector EntityItems found are returned in this vector
	 */
	private static void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
			Vector destVct)
	{
		if (entityItem!=null) {
			// see if this relator is used as an uplink
			for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
			{
				EANEntity entityLink = entityItem.getUpLink(ui);
				if (entityLink.getEntityType().equals(linkType))
				{
					// check for destination entity as an uplink
					for (int i=0; i<entityLink.getUpLinkCount(); i++)
					{
						EANEntity entity = entityLink.getUpLink(i);
						if (entity.getEntityType().equals(destType)) {
							destVct.addElement(entity); }
					}
				}
			}

			// see if this relator is used as a downlink
			for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
			{
				EANEntity entityLink = entityItem.getDownLink(ui);
				if (entityLink.getEntityType().equals(linkType))
				{
					// check for destination entity as a downlink
					for (int i=0; i<entityLink.getDownLinkCount(); i++)
					{
						EANEntity entity = entityLink.getDownLink(i);
						if (entity.getEntityType().equals(destType)) {
							destVct.addElement(entity); }
					}
				}
			}
		}
	}

	/***********************************************
	 * Get ABR description
	 *
	 * @return java.lang.String
	 */
	public String getDescription()
	{
		return "The ABR will derive a subset of the attributes for the WWSEO DERIVEDDATA and the available SLOTs and available BAYs.";
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getABRVersion()
	{
		return "1.29";
	}

	/***********************************************
	 *  Perform checking on the slotsavail, planar, memory card, expansion unit and mechanical pkg
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 *
	 */
	private void performChecking(EntityItem wwseoEntity) throws SQLException, MiddlewareException
	{
		// check SLOTSAVAIL
		addHeading(3,m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription()+" verification:");
		Hashtable slotsAvailTbl = verifySlotsAvail(wwseoEntity);
		Vector matchedSlotVct = new Vector();
		// get all elements
		addDebug("-----------------");
		getElements();

		// check PLANAR
		addDebug("-----------------");
		addHeading(3,m_elist.getEntityGroup("PLANAR").getLongDescription()+" checks:");
		checkPlanar(slotsAvailTbl,matchedSlotVct);

		// check memory card 
		addDebug("-----------------");
		addHeading(3,m_elist.getEntityGroup("MEMORYCARD").getLongDescription()+" checks:");
		checkMemoryCard(slotsAvailTbl,matchedSlotVct);

		// check expansion unit
		addDebug("-----------------");
		addHeading(3,m_elist.getEntityGroup("EXPDUNIT").getLongDescription()+" checks:");
		checkExpansionUnit(slotsAvailTbl,matchedSlotVct);

		// check mechanical pkg
		addHeading(3,m_elist.getEntityGroup("MECHPKG").getLongDescription()+" checks:");
		addDebug("-----------------");
		checkMechPkg(wwseoEntity);

		// check for values that did not have a match
		addHeading(3,m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription()+" checks:");
		Enumeration eNum = slotsAvailTbl.keys();
		while (eNum.hasMoreElements()){
			String key = (String)eNum.nextElement();
			if (matchedSlotVct.contains(key)){
				continue;
			}
			EntityItem slotsAvailItem = (EntityItem)slotsAvailTbl.get(key);
			addDebug("performChecking: No match found for "+slotsAvailItem.getKey()+" with "+key);
			//If a SLOTSAVAIL of a given ELEMENTTYPE & SLOTTYPE for which there is no corresponding ELEMENTTYPE & SLOT.SLOTTYPE, then report an error.
			//Error Text:  A SLOTSAVAIL exists for a non-existent ELEMENTTYPE & SLOTTYPE
			//Line 3a:  SLOTSAVAIL
			args[0] = slotsAvailItem.getEntityGroup().getLongDescription();
			args[1] = PokUtils.getAttributeDescription(slotsAvailItem.getEntityGroup(), "ELEMENTTYPE", "ELEMENTTYPE");
			args[2] = PokUtils.getAttributeDescription(slotsAvailItem.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
			args[3] = PokUtils.getAttributeDescription(slotsAvailItem.getEntityGroup(), "SLOTSZE", "SLOTSZE");
			//INVALID_SLOTAVAIL_ERROR = Error - A &quot;{0}&quot; exists for a non-existent &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot;.
			printError("INVALID_SLOTAVAIL_ERROR",args);
			print3a(slotsAvailItem);
		}

		matchedSlotVct.clear();
		slotsAvailTbl.clear();
	}

	/**
	 * Verify SLOTSAVAIL have values for ELEMENTTYPE and SLOTTYPE and that there are no duplicates
	 * @param wwseoEntity
	 * @return Hashtable with key=elementtype+slottype flag codes, value is the SLOTSAVAIL item
	 */
	private Hashtable verifySlotsAvail(EntityItem wwseoEntity){
		// get all duplicate links too		
		Vector slotsAvailVector = getAllLinkedEntities(wwseoEntity, "WWSEOSLOTSAVAIL", "SLOTSAVAIL");
		addDebug("verifySlotsAvail: Found "+slotsAvailVector.size()+" SLOTSAVAIL for "+wwseoEntity.getKey());

		// look at all slotsavail, must have elementtype and slottype and be unique combinations
		Hashtable slotsAvailTbl = new Hashtable();
		EntityGroup slotsAvailGrp = m_elist.getEntityGroup("SLOTSAVAIL"); 
		for (int i=0; i<slotsAvailVector.size(); i++) {
			EntityItem slotsAvail = (EntityItem)slotsAvailVector.elementAt(i);
			String elementType = getAttributeFlagEnabledValue(slotsAvail, "ELEMENTTYPE");
			String slotType = getAttributeFlagEnabledValue(slotsAvail, "SLOTTYPE");
			String slotSze = getAttributeFlagEnabledValue(slotsAvail, "SLOTSZE");
			addDebug("verifySlotsAvail: Checking "+slotsAvail.getKey()+" elemtype: "+elementType+
					" slotType: "+slotType+" slotSze: "+slotSze);
			if(elementType==null){ // if not set then this is an error
				//If a SLOTSAVAIL does not have an ELEMENTTYPE specified, report an error.
				// Error Text:  SLOTSAVAIL ELEMENTTYPE is empty.
				// Line 3a: SLOTSAVAIL
				args[0] = slotsAvailGrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(slotsAvailGrp, "ELEMENTTYPE", "ELEMENTTYPE");
				printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				print3a(slotsAvail);
			}else if(slotType==null){ // if not set then this is an error
				//If a SLOTSAVAIL does not have an SLOTTYPE specified, report an error.
				// Error Text:  SLOTSAVAIL SLOTTYPE is empty.
				// Line 3a: SLOTSAVAIL
				args[0] = slotsAvailGrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(slotsAvailGrp, "SLOTTYPE", "SLOTTYPE");
				printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				print3a(slotsAvail);
			}else if(slotSze==null){ // if not set then this is an error
				//If a SLOTSAVAIL does not have an SLOTSZE specified, report an error.
				// Error Text:  SLOTSAVAIL SLOTSZE is empty.
				// Line 3a: SLOTSAVAIL
				args[0] = slotsAvailGrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(slotsAvailGrp, "SLOTSZE", "SLOTSZE");
				printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				print3a(slotsAvail);
			}else {
				EntityItem item = (EntityItem)slotsAvailTbl.get(elementType+slotType+slotSze);
				if (item != null) {
					//If there is more than one SLOTSAVAIL of a given ELEMENTTYPE & SLOTTYPE, then report an error.
					//Error Text:  More than one SLOTSAVAIL of a given ELEMENTTYPE & SLOTTYPE
					//Line 3a:  SLOTSAVAIL
					args[0] = slotsAvailGrp.getLongDescription();
					args[1] = PokUtils.getAttributeDescription(slotsAvailGrp, "ELEMENTTYPE", "ELEMENTTYPE");
					args[2] = PokUtils.getAttributeDescription(slotsAvailGrp, "SLOTTYPE", "SLOTTYPE");
					args[3] = PokUtils.getAttributeDescription(slotsAvailGrp, "SLOTSZE", "SLOTSZE");
					//DUPLICATE_SLOTAVAIL_ERROR = Error - More than one &quot;{0}&quot; of a given &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot; found. 
					printError("DUPLICATE_SLOTAVAIL_ERROR",args); 
					print3a(slotsAvail, false);	
					print3a(item);
				}else{
					slotsAvailTbl.put(elementType+slotType+slotSze,slotsAvail);
					//For the WWSEO, set DERIVEDDATA.TOTAVAILCARDSLOT equal to the sum SLOTSAVAIL.SLOTAVAIL
					String slotAvailStr = PokUtils.getAttributeValue(slotsAvail, "SLOTAVAIL", "", "0", false);
					int slotAvail = Integer.parseInt(slotAvailStr);					
					totAvailCardSlot += slotAvail;
					addDebug("verifySlotsAvail: Adding "+slotsAvail.getKey()+" SLOTAVAIL:"+slotAvailStr+" to total:"+totAvailCardSlot);
				}
			}				
		}	
		// release memory
		slotsAvailVector.clear();

		return slotsAvailTbl;
	}

	/***********************************************
	 *  Get all elements and the path to them for this WWSEO
	 *
	 */
	private void getElements()
	{
		EntityGroup wwseopsGrp = m_elist.getEntityGroup("WWSEOPRODSTRUCT");
		// get all the features  WWSEOPRODSTRUCT->PRODSTRUCT<-FEATURE
		for (int i=0; i<wwseopsGrp.getEntityItemCount(); i++){
			EntityItem wwseoprodstructItem = wwseopsGrp.getEntityItem(i);
			EntityItem prodstructItem = (EntityItem) wwseoprodstructItem.getDownLink(0);

			for(int pi = 0; pi < prodstructItem.getUpLinkCount(); pi++)	{
				EntityItem eai = (EntityItem)prodstructItem.getUpLink(pi);
				if(eai.getEntityType().equals("FEATURE")) {
					EntityItem featureItem = (EntityItem)eai; 
					addDebug("getElements: Checking "+wwseoprodstructItem.getKey()+" "+prodstructItem.getKey()+
							" "+featureItem.getKey());  				
					for (int f=0; f<featureItem.getDownLinkCount(); f++){
						EntityItem elemRel = (EntityItem)featureItem.getDownLink(f);
						if (elemRel.getEntityType().equals("PRODSTRUCT")){
							continue;
						}
						for (int e=0; e<elemRel.getDownLinkCount(); e++){
							EntityItem elem = (EntityItem)elemRel.getDownLink(e);
							FCElement fcelem = new FCElement(wwseoprodstructItem,prodstructItem,featureItem,elemRel,elem);
							addDebug("getElements: Created "+fcelem+" confqty:"+fcelem.getConfQty()+" qty:"+fcelem.getQty());
							Vector vct = (Vector)fcElemTbl.get(elem.getEntityType());
							if (vct ==null){
								vct = new Vector(1);
								fcElemTbl.put(elem.getEntityType(), vct);
							}
							vct.addElement(fcelem);
						}    					
					}
				}
			}
		}
	}

	/***********************************************
	 * Check PLANAR
	 * A WWSEO should have exactly one PLANAR and the PLANAR should have one or more SLOTs. 
	 * A PLANAR may be shared across multiple FEATUREs
	 * 
	 * it must have 1 or more SLOTs
	 * If the WWSEO does not have any PLANAR, then report an error.
	 * Error Text:  There is no PLANAR.
	 * Note:  Line 3a & Line 3b are not applicable
	 * 
	 * If the WWSEO has more than one PLANAR, then report an error
	 * Error Text:  More than one PLANAR
	 * Line 3a:  PLANAR
	 * 
	 * If the PLANAR does not have any SLOTs, then assume SLOT.SLOTTOT = 0 AND PLANAR.TOTCARDSLOT = 0. 
	 * If PLANAR.TOTCARDSLOT <> 0, report an error.
	 * Error Text: PLANAR does not have any SLOT but PLANAR.TOTCARDSLOT is not 0.
	 * Line 3a: PLANAR
	 * deleted this check
	 * RCQ00129135 Planars no longer need slots
	 * If the WWSEO has one PLANAR and the PLANAR does not have any SLOTs, then report an error.
	 * Error Text:  The PLANAR does not have any SLOT
	 * Line 3a:  PLANAR
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkPlanar(Hashtable slotsAvailTbl, Vector matchedSlotVct) throws SQLException, MiddlewareException{
		EntityGroup planarGrp = m_elist.getEntityGroup("PLANAR");
		if(planarGrp.getEntityItemCount() == 0) {
			//If the WWSEO does not have any PLANAR, then report an error.
			//Error Text:  There is no PLANAR.
			args[0] = planarGrp.getLongDescription();
			//ELEM_NOTFOUND_ERROR = Error - There is no &quot;{0}&quot; found.
			printError("ELEM_NOTFOUND_ERROR",args, true); 
		}
		else {//BH FS ABR Derived Data Derivation 20130514.doc
//			A WWSEO should have one or more PLANARs and each PLANAR should have one or more SLOTs. A PLANAR may be shared across multiple FEATUREs.
			//For each PLANAR, if the sum of its SLOT.SLOTTOT is greater than PLANAR.TOTCARDSLOT, then report an error.
//			Error Text:  Too many SLOT for the PLANAR
//			Line 3a:  PLANAR
			Vector planarVct = (Vector)fcElemTbl.get("PLANAR");     
			addDebug("checkPlanar entered for "+planarVct.size()+" PLANAR");
			for (int i=0; i<planarVct.size(); i++){
				FCElement fce = (FCElement)planarVct.elementAt(i);
				EntityItem plannarItem = fce.getElement();
				Vector slotV = PokUtils.getAllLinkedEntities(plannarItem, "PLANARSLOT", "SLOT");
				String planarTotCardSlotStr = PokUtils.getAttributeValue(plannarItem, "TOTCARDSLOT" , "", "0", false);
				if(slotV.size() == 0){					
					addDebug("checkPlanar "+plannarItem.getKey()+" did not have any SLOTs TOTCARDSLOT="+planarTotCardSlotStr);
					if(!planarTotCardSlotStr.equals("0")){
						//INVALID_TOTCARDSLOT_ERR = <b>Error</b> - &quot;{0}&quot; does not have any {1} but {2} is not 0.
						args[0] = planarGrp.getLongDescription();
						args[1] = m_elist.getEntityGroup("SLOT").getLongDescription();
						args[2] = PokUtils.getAttributeDescription(planarGrp, "TOTCARDSLOT", "TOTCARDSLOT");
						printError("INVALID_TOTCARDSLOT_ERR",args); 
						print3a(plannarItem); 
					}else{
						addHeading(4,getLD_NDN(plannarItem)+" has No SLOTs");
					}
				}else{				
					// Check if the sum of its SLOT.SLOTTOT is greater than PLANAR.TOTCARDSLOT				
					int planarTotCardSlot = Integer.parseInt(planarTotCardSlotStr);
					int totalSlotTot = fce.checkSlots(slotsAvailTbl,PLANAR_SLOTAVAIL, matchedSlotVct);
					addDebug("checkplanar: " + fce.getElement().getKey()
							+ ".TOTCARDSLOT=" + planarTotCardSlot
							+ " totalSlotTot:" + totalSlotTot);
					if (totalSlotTot > planarTotCardSlot) {
						String errmsgkey = fce.getElement().getKey() + ":SLOT";
						// same planar linked twice, prevent 2 err msgs
						if (!errMsgVct.contains(errmsgkey)) {
							errMsgVct.add(errmsgkey);
							args[0] = m_elist.getEntityGroup("SLOT").getLongDescription();
							args[1] = m_elist.getEntityGroup("PLANAR").getLongDescription();
							printError("TOO_MANY_CHILDREN_ERR", args); //Error - Too many {0} for the {1}.
							print3a(fce.getElement());
						}
					}// end of if(totalSlotTot > planarTotCardSlot)
				}
			}
			
		}
		
		//comment below 
//		else if(planarGrp.getEntityItemCount() == 1){
//			EntityGroup planarSlotGrp = m_elist.getEntityGroup("PLANARSLOT");
//			if (planarSlotGrp.getEntityItemCount()==0){
//				//RCQ00129135 Planars no longer need slots
//				//If the WWSEO has one PLANAR and the PLANAR does not have any SLOTs, then report an error.
//				// Error Text:  The PLANAR does not have any SLOT
//				// Line 3a:  PLANAR
//				//	args[0] = planarGrp.getLongDescription();
//				//	args[1] = m_elist.getEntityGroup("SLOT").getLongDescription();
//				//	printError("MISSING_CHILD_ERROR",args); //Error - The {0} does not have any {1}.
//				//	print3a(planarGrp.getEntityItem(0));    
//				//If the PLANAR does not have any SLOTs, but PLANAR.TOTCARDSLOT <> 0, report an error.
//				// Error Text:  PLANAR does not have any Slot but PLANAR.TOTCARDSLOT is not 0.
//				// Line 3a: PLANAR
//				String planarTotCardSlotStr = PokUtils.getAttributeValue(planarGrp.getEntityItem(0), "TOTCARDSLOT" , "", "0", false);
//				addDebug("checkPlanar "+planarGrp.getEntityItem(0).getKey()+" did not have any SLOTs TOTCARDSLOT="+planarTotCardSlotStr);
//				if(!planarTotCardSlotStr.equals("0")){
//					//INVALID_TOTCARDSLOT_ERR = <b>Error</b> - &quot;{0}&quot; does not have any {1} but {2} is not 0.
//					args[0] = planarGrp.getLongDescription();
//					args[1] = m_elist.getEntityGroup("SLOT").getLongDescription();
//					args[2] = PokUtils.getAttributeDescription(planarGrp, "TOTCARDSLOT", "TOTCARDSLOT");
//					printError("INVALID_TOTCARDSLOT_ERR",args); 
//					print3a(planarGrp.getEntityItem(0)); 
//				}else{
//					addHeading(4,getLD_NDN(planarGrp.getEntityItem(0))+" has No SLOTs");
//				}
//			}else{                       		
//				Vector planarVct = (Vector)fcElemTbl.get("PLANAR");     
//				addDebug("checkPlanar entered for "+planarVct.size()+" PLANAR");
//				for (int i=0; i<planarVct.size(); i++){
//					FCElement fce = (FCElement)planarVct.elementAt(i);
//
//					//Check if the sum of its SLOT.SLOTTOT is greater than PLANAR.TOTCARDSLOT
//					String planarTotCardSlotStr = PokUtils.getAttributeValue(fce.getElement(), "TOTCARDSLOT" , "", "0", false);
//					int planarTotCardSlot = Integer.parseInt(planarTotCardSlotStr);
//					int totalSlotTot = fce.checkSlots(slotsAvailTbl, PLANAR_SLOTAVAIL,matchedSlotVct);
//					addDebug("checkplanar: "+fce.getElement().getKey()+ ".TOTCARDSLOT="+planarTotCardSlot+" totalSlotTot:"+totalSlotTot);
//					if(totalSlotTot > planarTotCardSlot)
//					{
//						String errmsgkey = fce.getElement().getKey()+":SLOT";
//						// same planar linked twice, prevent 2 err msgs
//						if (!errMsgVct.contains(errmsgkey)){
//							errMsgVct.add(errmsgkey);
//							args[0] = m_elist.getEntityGroup("SLOT").getLongDescription();
//							args[1] = m_elist.getEntityGroup("PLANAR").getLongDescription();
//							printError("TOO_MANY_CHILDREN_ERR",args); //Error - Too many {0} for the {1}.
//							print3a(fce.getElement());
//						}
//					}//end of if(totalSlotTot > planarTotCardSlot) 
//				}
//			}
//		}
//		else {
//			//If the WWSEO has more than one PLANAR, then report an error
//			//Error Text:  More than one PLANAR
//			//Line 3a:  PLANAR
//			args[0] = planarGrp.getLongDescription();
//			printError("MAX_1_ERROR",args); //Error - More than one {0}.
//			for(int i = 0; i < planarGrp.getEntityItemCount(); i++) {
//				print3a(planarGrp.getEntityItem(i),false);
//			}
//			rptSb.append("</p>" + NEWLINE);
//		}
	}

	/***********************************************
	 * A WWSEO may also have MEMORYCARD which may have zero or more SLOTs. 
	 * 
	 * For each MEMORYCARD, if the sum of its SLOT.SLOTTOT is greater than MEMORYCARD.MEMRYCRDTOTALSLOTS, 
	 * then report an error.
	 * Error Text:  Too many SLOT for the MEMORYCARD
	 * Line 3a:  MEMORYCARD
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkMemoryCard(Hashtable slotsAvailTbl, Vector matchedSlotVct) throws SQLException, MiddlewareException
	{
		Vector mcVct = (Vector)fcElemTbl.get("MEMORYCARD");
		addDebug("checkMemoryCard entered for "+(mcVct==null?0:mcVct.size())+" MEMORYCARD");
		if (mcVct != null){
			for (int i=0; i<mcVct.size(); i++){
				FCElement fce = (FCElement)mcVct.elementAt(i);
				EntityItem memItem = fce.getElement();
				// Check if the sum of its SLOT.SLOTTOT is greater than MEMORYCARD.MEMRYCRDTOTALSLOTS
				String mcTotCardSlotStr = PokUtils.getAttributeValue(memItem, "MEMRYCRDTOTALSLOTS" , "", "0", false);
				int mcTotCardSlot = Integer.parseInt(mcTotCardSlotStr);
				int slotTotal = fce.checkSlots(slotsAvailTbl, MEMORYCARD_SLOTAVAIL, matchedSlotVct);
				addDebug("checkMemoryCard "+memItem.getKey()+ ".MEMRYCRDTOTALSLOTS="+mcTotCardSlot+" slotTotal:"+slotTotal);
				if(slotTotal > mcTotCardSlot){
					args[0] = m_elist.getEntityGroup("SLOT").getLongDescription();
					args[1] = m_elist.getEntityGroup("MEMORYCARD").getLongDescription();
					printError("TOO_MANY_CHILDREN_ERR",args); //Error - Too many {0} for the {1}.
					print3a(memItem);
				}
			}
		}
	}    

	/***********************************************
	 * A WWSEO may also have EXPDUNIT which may have zero or more SLOTs.
	 * 
	 * For each EXPDUNIT, if the sum of its SLOT.SLOTTOT is greater than EXPDUNIT.EXPNDUNITSOLTSTOT 
	 * then report an error.
	 * Error Text:  Too many SLOT for the EXPDUNIT
	 * Line 3a:  EXPDUNIT
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkExpansionUnit(Hashtable slotsAvailTbl, Vector matchedSlotVct) throws SQLException, MiddlewareException
	{
		Vector mcVct = (Vector)fcElemTbl.get("EXPDUNIT");
		addDebug("checkExpansionUnit entered for "+(mcVct==null?0:mcVct.size())+" EXPDUNIT");
		if (mcVct != null){
			for (int i=0; i<mcVct.size(); i++){
				FCElement fce = (FCElement)mcVct.elementAt(i);
				EntityItem expItem = fce.getElement();
				// Check if the sum of its SLOT.SLOTTOT is greater than EXPDUNIT.EXPNDUNITSOLTSTOT
				String mcTotCardSlotStr = PokUtils.getAttributeValue(expItem, "EXPNDUNITSOLTSTOT" , "", "0", false);
				int mcTotCardSlot = Integer.parseInt(mcTotCardSlotStr);

				int slotTotal = fce.checkSlots(slotsAvailTbl, EXPDUNIT_SLOTAVAIL, matchedSlotVct);
				addDebug("checkExpansionUnit "+expItem.getKey()+ ".EXPNDUNITSOLTSTOT="+mcTotCardSlot+" slotTotal "+slotTotal);
				if(slotTotal > mcTotCardSlot){
					args[0] = m_elist.getEntityGroup("SLOT").getLongDescription();
					args[1] = m_elist.getEntityGroup("EXPDUNIT").getLongDescription();
					printError("TOO_MANY_CHILDREN_ERR",args); //Error - Too many {0} for the {1}.
					print3a(expItem);
				}
			}
		}
	}  
	/***********************************************
	 * Check MECHPKG
	 * A WWSEO has FEATUREs (found via WWSEOPRODSTRUCT to PRODSTRUCT to FEATURE). 
	 * Some FEATUREs have MECHPKGs which have BAYs. 
	 * 
	 * There must be one or more MECHPKG and it must be linked to
	 * �	Zero or one Features where HW Feature Category (HWFCCAT) = �System Unit Base� (273)
	 * �Logical AND/OR�
	 * �	Zero or more Features where HW Feature Category (HWFCCAT) = �Selectable Feature Mechanical� (238).
	 * �Logical AND/OR�
	 * �	Zero or more Features where HW Feature Category (HWFCCAT) = �Unselected Feature Attachment� (240)
	 * �Logical AND/OR�
	 * �	Zero or more Features where HW Feature Category (HWFCCAT) = �Customer Feature Choice� (230)
	 * 
	 * If no MECHPKG (i.e. zero) are found then report an error.
	 * Error Text:	No MECHPKG found.
	 * 
	 * If more than one MECHPKG where HW Feature Category (HWFCCAT) = �System Unit Base� (273) is found that 
	 * meet this criteria, then report an error.
	 * Error Text:  	More than one MECHPKG.
	 * Line 3b: 		MECHPKG    
	 *
	 *If a MECHPKG is linked to a FEATURE where HW Feature Category (HWFCCAT) not in �System Unit Base� (273);
	 * �Selectable Feature Mechanical� (238); �Unselected Feature Attachment� (240); 
	 * �Customer Feature Choice� (230), then report an error.
	 * 	Error Text:	MECHPKG linked to an invalid Feature Category
	 * 	Line 3b:	MECHPKG
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkMechPkg(EntityItem wwseoEntity) throws SQLException, MiddlewareException
	{
		EntityGroup baysAvailGrp = m_elist.getEntityGroup("BAYSAVAIL");
		// check the bays and accumulate totals
		Hashtable baysAvailTbl = verifyBaysAvail(wwseoEntity);
		Vector matchedBaysVct = new Vector();

		Vector sysUnitBaseVct = new Vector(1);
		Vector mechPkgVector = new Vector();
		Vector mcVct = (Vector)fcElemTbl.get("MECHPKG");
		addDebug("checkMechPkg: entered for "+(mcVct==null?0:mcVct.size())+" MECHPKG");
		if (mcVct != null){
			for (int i=0; i<mcVct.size(); i++){
				FCElement fce = (FCElement)mcVct.elementAt(i);
				EntityItem mechpkg = fce.getElement();
				EntityItem featItem = fce.getFeature();
				String category = getAttributeFlagEnabledValue(featItem, "HWFCCAT");
				if (SYSTEM_UNIT_BASE.equals(category) ||
						CUSTOMER_FEATURE_CHOICE.equals(category) ||
						UNSELECTED_FEAT_ATTACHMNT.equals(category) ||
						SELECTABLE_FEAT_MECH.equals(category)) {
					mechPkgVector.addElement(fce);
					if(SYSTEM_UNIT_BASE.equals(category)){
						sysUnitBaseVct.add(fce);
					}
				}else{
					//If a MECHPKG is linked to a FEATURE where HW Feature Category (HWFCCAT) not in �System Unit Base� (273);
					// �Selectable Feature Mechanical� (238); �Unselected Feature Attachment� (240); 
					// �Customer Feature Choice� (230), then report an error.
					// 	Error Text:	MECHPKG linked to an invalid Feature Category
					// 	Line 3b:	MECHPKG
					//INVALID_HWFCCAT_ERR = Error - &quot;{0}&quot; linked to an invalid Feature Category
					addDebug("checkMechPkg: "+featItem.getKey()+" had "+mechpkg.getKey()+" but HWFCCAT was "+
							PokUtils.getAttributeValue(featItem, "HWFCCAT", "", "", false));
					args[0] = m_elist.getEntityGroup("MECHPKG").getLongDescription();
					printError("INVALID_HWFCCAT_ERR",args,true);
					print3b(featItem,mechpkg);
					rptSb.append("</p>" + NEWLINE);
				}
			}
		}    	

		if (mechPkgVector.size()==0){
			//If no MECHPKG (i.e. zero) are found then report an error.
			//Error Text:	No MECHPKG found.
			//Line 3b:		MECHPKG
			args[0] = m_elist.getEntityGroup("MECHPKG").getLongDescription();
			//ELEM_NOTFOUND_ERROR = Error - There is no &quot;{0}&quot; found.
			printError("ELEM_NOTFOUND_ERROR",args,true);
		}
		if (sysUnitBaseVct.size()>1){
			// If more than one MECHPKG where HW Feature Category (HWFCCAT) = �System Unit Base� (273) is found that 
			//meet this criteria, then report an error.
			//Error Text:  	More than one MECHPKG.
			//Line 3b: 		MECHPKG  
			args[0] = m_elist.getEntityGroup("MECHPKG").getLongDescription();
			printError("MAX_1_ERROR",args); //Error - More than one {0}.
			for(int i = 0; i < sysUnitBaseVct.size(); i++) {
				FCElement fce = (FCElement)sysUnitBaseVct.elementAt(i);
				print3b(fce.getFeature(), fce.getElement());
			}
			rptSb.append("</p>" + NEWLINE);
		}

		/**
		 * D.	MECHPKG BAY
		 * For the MECHPKG, if the sum of its BAY.BAYTOT is greater than MECHPKG.TOTBAY, then report an error.
		 * Error Text:  Too many BAY for the MECHPKG
		 * Line 3a:  MECHPKG
		 */
		for (int i=0; i<mechPkgVector.size(); i++){
			FCElement fce = (FCElement)mechPkgVector.elementAt(i);
			Vector bayVct = getAllLinkedEntities(fce.getElement(),"MECHPKGBAY","BAY");
			if (bayVct.size()>0){    			  
				String mechPkgTotBayStr = PokUtils.getAttributeValue(fce.getElement(), "TOTBAY", "", "0", false);
				int mechPkgTotBay = Integer.parseInt(mechPkgTotBayStr);
				// check for matching baysavail or duplicate bays
				int totalBayTot = fce.checkBays(baysAvailTbl, matchedBaysVct);
				addDebug("checkMechPkg: "+fce+" TOTBAY="+mechPkgTotBayStr+" totalBayTot="+totalBayTot);
				if(totalBayTot > mechPkgTotBay) {
					args[0] = m_elist.getEntityGroup("BAY").getLongDescription();
					args[1] = fce.getElement().getEntityGroup().getLongDescription();
					printError("TOO_MANY_CHILDREN_ERR",args); //Error - Too many {0} for the {1}.
					print3a(fce.getElement(),false);        			  
					for(int b = 0; b < bayVct.size(); b++){
						print3a((EntityItem) bayVct.get(b),false);
					}

					rptSb.append("</p>" + NEWLINE);
				}//end of if(totalBayTot > mechPkgTotBay)    			  
			}
			bayVct.clear();
		}

		//Check if a BAYSAVAIL exists for a non-existent BAY.BAYTYPE+BAY.ACCSS+BAY.BAYFF
		for (Enumeration eNum = baysAvailTbl.keys(); eNum.hasMoreElements();)
		{
			String bayInfo = (String) eNum.nextElement();
			if (matchedBaysVct.contains(bayInfo)){
				continue;
			}

			EntityItem baysAvailItem = (EntityItem)baysAvailTbl.get(bayInfo);
			EntityGroup bgrp = m_elist.getEntityGroup("BAY");
			args[0] = baysAvailGrp.getLongDescription();
			args[1] = PokUtils.getAttributeDescription(bgrp, "BAYTYPE", "BAYTYPE");
			args[2] = PokUtils.getAttributeDescription(bgrp, "ACCSS", "ACCSS");		  
			args[3] = PokUtils.getAttributeDescription(bgrp, "BAYFF", "BAYFF");
			//INVALID_BAYSAVAIL_ERROR = Error - A &quot;{0}&quot; exists for a non-existent &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot;.
			printError("INVALID_BAYSAVAIL_ERROR",args); 
			print3a(baysAvailItem);
			addDebug("No BAY for "+baysAvailItem.getKey()+" "+bayInfo);
		}

		matchedBaysVct.clear();
		mechPkgVector.clear();
		baysAvailTbl.clear();
		sysUnitBaseVct.clear();
	}

	/***********************************************
	 * Check BAYSAVAIL for duplicate types and also accumulate totAvailBay for deriveddata
	 *
	 */
	private Hashtable verifyBaysAvail(EntityItem wwseoEntity){
		// get all duplicate links too		
		Vector baysAvailVector = getAllLinkedEntities(wwseoEntity, "WWSEOBAYSAVAIL", "BAYSAVAIL");
		addDebug("verifyBaysAvail: Found "+baysAvailVector.size()+" BAYSAVAIL for "+wwseoEntity.getKey());

		//Check if more than one BAYSAVAIL of a given BAYAVAILTYPE+ACCSS+BAYFF
		// BAYAVAILTYPE+ACCSS+BAYFF must be unique
		Hashtable bayAvailHs = new Hashtable();
		EntityGroup baysAvailGrp = m_elist.getEntityGroup("BAYSAVAIL"); 
		for (int i=0; i<baysAvailVector.size(); i++) {
			EntityItem baysAvailItem = (EntityItem)baysAvailVector.elementAt(i);      
			String bayAvailType = getAttributeFlagEnabledValue(baysAvailItem, "BAYAVAILTYPE"); // use flag value
			String bayAccss = getAttributeFlagEnabledValue(baysAvailItem, "ACCSS"); // use flag value
			String bayFF = getAttributeFlagEnabledValue(baysAvailItem, "BAYFF"); // use flag value
			String bayInfo;
			addDebug("verifyBaysAvail: Checking "+baysAvailItem.getKey()+" bayAvailType: "+bayAvailType+" bayAccss: "+bayAccss+" bayFF:"+bayFF);
			if (bayAvailType == null ||
					bayAccss == null ||
					bayFF == null) {

				args[0] = baysAvailGrp.getLongDescription();
				if (bayAvailType==null){
					args[1] = PokUtils.getAttributeDescription(baysAvailGrp, "BAYAVAILTYPE", "BAYAVAILTYPE");
					printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				}
				if (bayAccss==null){
					args[1] = PokUtils.getAttributeDescription(baysAvailGrp, "ACCSS", "ACCSS");
					printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				}
				if (bayFF==null){
					args[1] = PokUtils.getAttributeDescription(baysAvailGrp, "BAYFF", "BAYFF");
					printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
				}
				print3a(baysAvailItem);
				continue;
			}
			bayInfo = bayAvailType +bayAccss + bayFF;
			EntityItem item = (EntityItem)bayAvailHs.get(bayInfo);
			if (item != null){
				//Found duplicate BAYAVAILTYPE+ACCSS+BAYFF skip duplicates in TOTAVAILBAY count
				args[0] = baysAvailGrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(baysAvailGrp, "BAYAVAILTYPE", "BAYAVAILTYPE");
				args[2] = PokUtils.getAttributeDescription(baysAvailGrp, "ACCSS", "ACCSS");
				args[3] = PokUtils.getAttributeDescription(baysAvailGrp, "BAYFF", "BAYFF");
				//DUPLICATE_BAYSAVAIL_ERROR = Error - More than one &quot;{0}&quot; of a given &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot; found. 
				printError("DUPLICATE_BAYSAVAIL_ERROR",args); 
				print3a(baysAvailItem, false);
				print3a(item);
			}
			else{
				//For the WWSEO, set DERIVEDDATA.TOTAVAILBAY equal to the sum BAYSAVAIL.BAYAVAIL
				String bayAvailStr = PokUtils.getAttributeValue(baysAvailItem, "BAYAVAIL", "", "0", false);
				bayAvailHs.put(bayInfo,baysAvailItem);
				int bayAvail = Integer.parseInt(bayAvailStr);
				totAvailBay += bayAvail;
				addDebug("verifyBaysAvail: Adding "+baysAvailItem.getKey()+" BAYAVAIL:"+bayAvailStr+" to total:"+totAvailBay);
			}
		}

		baysAvailVector.clear();
		return bayAvailHs;
	}

	/***********************************************
	 *  A WWSEO has at most one DERIVEDDATA. If there are none, the ABR will create one. 
	 *  If there is one, the ABR will update it. 
	 *  
	 *  If there is more than one DERIVEDDATA, then report an error.
	 *  Error Text:  More than one DERIVEDDATA
	 *  Line 3a:  DERIVEDDATA
	 * @throws SBRException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private void updateDerivedDataEntity(EntityItem wwseoEntity) throws MiddlewareRequestException, 
	SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		EntityGroup ddGrp = m_elist.getEntityGroup("DERIVEDDATA");
		if (ddGrp.getEntityItemCount()==0) {
			//No DERIVEDDATA found so go ahead and create one
			EntityItem derivedDataItem = createDerivedDataEntity(wwseoEntity);

			//set derived data for DERIVEDDATA
			setDerivedData(derivedDataItem);    
		}
		else if(ddGrp.getEntityItemCount() == 1) {
			EntityItem derivedDataItem = ddGrp.getEntityItem(0);
			//set derived data for DERIVEDDATA
			setDerivedData(derivedDataItem);
		}
		else if(ddGrp.getEntityItemCount() > 1) {
			//Report an error
			args[0] = ddGrp.getLongDescription();
			printError("MAX_1_ERROR",args); //Error - More than one {0}.
			for(int i = 0; i < ddGrp.getEntityItemCount(); i++) {
				print3a(ddGrp.getEntityItem(i), false);
			}
			rptSb.append("</p>" + NEWLINE);
		}
	}

	/***********************************************
	 * Create DERIVEDDATA and link it to WWSEO
	 * Derived Data (DERIVEDDATA) is a child of WWSEO. There maybe zero or one of these. 
	 * If there is one, then update it. If one does not exist, create it.
	 * 
	 * If the ABR creates a DERIVEDDATA, the following attributes are created:
	 * 	COMNAME = WWSEO.COMNAME
	 * 	PDHDOMAIN=WWSEO.PDHDOMAIN
	 * 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * @throws SBRException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @return EntityItem
	 */
	private EntityItem createDerivedDataEntity(EntityItem rootEntity) throws MiddlewareRequestException, 
	SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		EntityItem derivedDataItem = null;
		try
		{
			EntityGroup egDD = new EntityGroup(null, m_db, m_prof, "DERIVEDDATA", "Edit", false);
			String comName = PokUtils.getAttributeValue(rootEntity, "COMNAME", "", "", false);
			String pdhDomain = getAttributeFlagValue(rootEntity, "PDHDOMAIN", ",");

			derivedDataAttList.put("DERIVEDDATA:COMNAME", "COMNAME=" + comName);
			derivedDataAttList.put("DERIVEDDATA:PDHDOMAIN", "PDHDOMAIN=" + pdhDomain);

			derivedDataItem = pdgUtil.createEntity(m_db, m_prof, "DERIVEDDATA", derivedDataAttList);
			//m_prof = pdgUtil.setProfValOnEffOn(m_db, m_prof);
			derivedDataItem = new EntityItem(egDD, m_prof, m_db, derivedDataItem.getEntityType(), derivedDataItem.getEntityID());
			pdgUtil.linkEntities(m_db, m_prof, rootEntity, new EntityItem[]{derivedDataItem}, "WWSEODERIVEDDATA");
		}finally{
			derivedDataAttList.remove("DERIVEDDATA:COMNAME");
			derivedDataAttList.remove("DERIVEDDATA:PDHDOMAIN");
		}
		return derivedDataItem;
	}

	/***********************************************
	 * set derived data for DERIVEDDATA entity
	 * @throws SBRException 
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 *
	 */
	private void setDerivedData(EntityItem derivedDataItem) throws MiddlewareRequestException, MiddlewareException, SQLException, SBRException
	{
		addDebug("-----------------");
		getDDAvailSlotsTotalSlots();
		addDebug("-----------------");
		getDDAvailBaysTotalBays();
		addDebug("-----------------");
		getDDMemoryRAMStandard();
		addDebug("-----------------");
		getDDTotalL2CacheStandard();
		addDebug("-----------------");
		getDDNoOfProcStandard(); 
		addDebug("-----------------");
		getDDNoOfInstHardDrvs();
		addDebug("-----------------");

		displayDD();

		pdgUtil.updateAttribute(m_db, m_prof, derivedDataItem, derivedDataAttList);
	}

	/***
	 * A.	Available Slots and Total Slots
	 * 
	 * For the WWSEO, set DERIVEDDATA.TOTAVAILCARDSLOT equal to the sum SLOTSAVAIL.SLOTAVAIL
	 * 
	 * For the WWSEO, set DERIVEDDATA.TOTDERIVEDSLOT equal to the sum of
	 * �	WWSEOPRODSTRUCT.CONFQTY * FEATUREMEMORYCARD.QTY * MEMORYCARD.MEMRYCRDTOTALSLOTS
	 * �	WWSEOPRODSTRUCT.CONFQTY * FEATUREPLANAR.QTY * PLANAR.TOTCARDSLOT
	 * �	WWSEOPRODSTRUCT.CONFQTY * EXPDUNIT.TOTCARDSLOT
	 */
	private void getDDAvailSlotsTotalSlots(){
		//For the WWSEO, set DERIVEDDATA.TOTAVAILCARDSLOT equal to the sum SLOTSAVAIL.SLOTAVAIL
		// this should be set no matter what
		derivedDataAttList.put("DERIVEDDATA:TOTAVAILCARDSLOT", "TOTAVAILCARDSLOT=" + totAvailCardSlot);
		addDebug("Calculate TOTDERIVEDSLOT");
		//For the WWSEO, set DERIVEDDATA.TOTDERIVEDSLOT equal to the sum of
		//�	WWSEOPRODSTRUCT.CONFQTY * FEATUREMEMORYCARD.QTY * MEMORYCARD.MEMRYCRDTOTALSLOTS
		//�	WWSEOPRODSTRUCT.CONFQTY * FEATUREPLANAR.QTY * PLANAR.TOTCARDSLOT
		//�	WWSEOPRODSTRUCT.CONFQTY * EXPDUNIT.TOTCARDSLOT
		int totderivedslot = getSlotCardCount("MEMORYCARD",  "MEMRYCRDTOTALSLOTS") +
		getSlotCardCount("PLANAR",  "TOTCARDSLOT")+
		getSlotCardCount("EXPDUNIT",  "TOTCARDSLOT");

		derivedDataAttList.put("DERIVEDDATA:TOTDERIVEDSLOT", "TOTDERIVEDSLOT=" + totderivedslot);
	}
	/****
	 * Accumulate slot count for the specified element type
	 * @param elemType
	 * @param elemAttr
	 * @return int
	 */
	private int getSlotCardCount(String elemType, String elemAttr){
		int totderivedslot = 0;
		Vector vct = (Vector)fcElemTbl.get(elemType);
		addDebug("getSlotCardCount for "+(vct==null?0:vct.size())+" "+elemType);
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				EntityItem elemItem = fce.getElement();
				// EXPDUNIT.TOTCARDSLOT was missing from meta 
				EANMetaAttribute metaAttr = elemItem.getEntityGroup().getMetaAttribute(elemAttr);
				if (metaAttr==null) {
					setReturnCode(FAIL);
					rptSb.append("<p><span style=\"color:#c00; font-weight:bold;\">Attribute &quot;"+
							elemAttr+"&quot; NOT found in &quot;"+
							elemItem.getEntityType()+"&quot; META data.</span></p>");
					return 0; 
				}

				String totCardSlotStr = PokUtils.getAttributeValue(elemItem, elemAttr , "", "0", false);
				int totCardSlot = Integer.parseInt(totCardSlotStr);
				int count = fce.getQuantity();
				addDebug("getSlotCardCount["+i+"]: "+fce+ " "+elemAttr+":"+totCardSlot+" qty:"+count+
						" element total:"+(totCardSlot*count));
				totderivedslot+=(totCardSlot*count);
			}
		}   
		return totderivedslot;
	}
	/****
	 * B.	Available Bays and Total Bays
	 * 
	 * For the WWSEO, set DERIVEDDATA.TOTAVAILBAY equal to the sum BAYSAVAIL.BAYAVAIL
	 * 
	 * For the WWSEO, set DERIVEDDATA.TOTDERIVEDBAY equal to the sum of  
	 * WWSEOPRODSTRUCT.CONFQTY * FEATUREMECHPKG.QTY * MECHPKG.TOTBAY for all instances of MECHPKG.
	 */
	private void getDDAvailBaysTotalBays(){
		// DERIVEDDATA.TOTAVAILBAY equal to the sum of BAYSAVAIL.BAYAVAIL, but duplicate types need to
		// be skipped, this was done when mechpkg was checked
		// this should be set no matter what
		derivedDataAttList.put("DERIVEDDATA:TOTAVAILBAY", "TOTAVAILBAY=" + totAvailBay);

		int totderivedbay = 0;
		Vector vct = (Vector)fcElemTbl.get("MECHPKG");
		addDebug("Calculate TOTDERIVEDBAY for "+(vct==null?0:vct.size())+" MECHPKG");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				EntityItem elemItem = fce.getElement();
				String totBayStr = PokUtils.getAttributeValue(elemItem, "TOTBAY" , "", "0", false);
				int totBay = Integer.parseInt(totBayStr);
				int count = fce.getQuantity();
				totderivedbay+=(totBay*count);
				addDebug(fce+ " TOTBAY:"+totBay+" qty:"+count+" totderivedbay:"+totderivedbay);
			}
		}   
		derivedDataAttList.put("DERIVEDDATA:TOTDERIVEDBAY", "TOTDERIVEDBAY=" + totderivedbay);	        
	}
	/***********************************************
	 * Get derived data for DERIVEDDATA.MEMRYRAMSTD and DERIVEDDATA.MEMRYRAMSTDUNIT
	 * MEMRYRAMSTDUNIT	0030	MB
	 * MEMRYRAMSTDUNIT	0040	GB
	 * 
	 * CAPUNIT			C0010	MB
	 * CAPUNIT			C0030	--  bad migration value
	 * CAPUNIT			C0040	GB
	 * CAPUNIT			C0050	kB
	 * CAPUNIT			D0020	M   bad migration value
	 * 
	 * the data has MEMORY.CAPUNIT=kB. According to 30b Data Model Meta Load 20060316.xls file flag values of
	 * CAPUNIT are kB, MB, and GB.
	 * Wendy - that attribute will continue to have the allowed value kB; however, you should never see it.
	 * If you get an allowed value that you do not recognize and if there is no math required,
	 * then continue with the value as is and try to update derived data. If derived data does not have
	 * the required allowed value - report an error showing the value needed. Similarly, if you get a value
	 * like kB and math is required, then report an error.
	 * 
	 * C.	Memory Standard
	 * This calculation must consider the following possibilities
	 * 1.	WWSEOPRODSTRUCT.CONFQTY
	 * 2.	Multiple FEATUREs with one or more children of type MEMORY
	 * 
	 * e.g. WWSEOPRODSTRUCT.CONFQTY = 2 and a FEATURE with two children of type MEMORY where 
	 * MEMORY.MEMRYCAP = 2 and MEMORY.CAPUNIT = GB. The derived data would give 8 GB.
	 * 
	 * Used for calculation:
	 * 	MEMORY.MEMRYCAP
	 * 	MEMORY.CAPUNIT
	 * 
	 * Calculation result:
	 * 	DERIVEDDATA.MEMRYRAMSTD
	 * 	DERIVEDDATA.MEMRYRAMSTDUNIT
	 * 
	 * If MEMORY.CAPUNIT is empty, set to �M� or ��� then report an error.
	 * Error Text:  MEMORY CAPUNIT is empty or invalid flag value.
	 * Line 3b: MEMORY
	 * 
	 * WWSEOPRODSTRUCT.CONFQTY (Qty in example below) is the Configured Quantity of a FEATURE that when 
	 * described by MEMORY is considered in the calculations. The Capacity Units (CAPUNIT) must also be 
	 * used in the calculations.
	 * 
	 * If there are no MEMORY, then MEMRYRAMSTD = 0 and MEMRYRAMSTDUNIT = MB.
	 * The following example is for explanation purposes and may not represent a real offering.
	 * Qty	Feature	Memory	memrycap	capunit
	 * 1		123456	512MB	512			MB
	 * 2		234567	256MB	256			MB
	 * 
	 * In this example, the DERIVEDDATA
	 * 	MEMRYRAMSTD = 1
	 * 	MEMRYRAMSTDUNIT = GB
	 * 
	 * The calculation should use integer arithmetic and must consider the units as:
	 * �	GB = 1,000 * MB
	 * 
	 * The calculation is:
	 * Qty = (1 * 512) + (2 * 256) = 1,024 MB
	 * 
	 * The units are then established as follows:
	 * This approach ensures that the inaccuracy of floating point does not produce undesirable results. 
	 * Convert to a character string and use the integer result for the comparison. Use the first criterion 
	 * that matches.
	 * If result > 1,000 then remove the right most two characters and place the decimal point to the left 
	 * of the right most character. The units = GB.
	 * If the right most character is �0� (zero), remove the right most 2 characters (i.e. there is no fraction, 
	 * therefore remove the decimal point and the fraction).
	 * 
	 * Note:  KB is not an allowed value in 3.0b (the only allowed values are MB and GB).
	 * 
	 */
	private void getDDMemoryRAMStandard()
	{
		long memoryCapUnit = 0L;
		long memoryRAMStandard = 0L;

		String tempStr = "";
		String memoryRAMStandardStr = "0";
		String memoryRAMStandardUnitStr = "";

		// get all the memory elements
		Vector vct = (Vector)fcElemTbl.get("MEMORY");
		addDebug("Calculate MEMRYRAMSTD for "+(vct==null?0:vct.size())+" MEMORY");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				addDebug("checking memory for "+fce);
				EntityItem memoryItem = fce.getElement();
				String memoryCapStr = PokUtils.getAttributeValue(memoryItem, "MEMRYCAP", "", "0", false);
				String memoryCapUnitStr = PokUtils.getAttributeValue(memoryItem, "CAPUNIT", "", "", false);
				addDebug(memoryItem.getKey()+" MEMRYCAP="+memoryCapStr+" CAPUNIT="+memoryCapUnitStr+" quantity:"+fce.getQuantity());

				int memoryCap = Integer.parseInt(memoryCapStr);
				if (memoryCap>0){
					if("".equals(memoryCapUnitStr) ||  // wasn't set
							"--".equals(memoryCapUnitStr) ||  // bad migrated flag value
							"M".equals(memoryCapUnitStr))  // bad migrated flag value
					{
						//Report an error
						args[0] = memoryItem.getEntityGroup().getLongDescription();
						args[1] = PokUtils.getAttributeDescription(memoryItem.getEntityGroup(), "MEMRYCAP", "MEMRYCAP");
						printError("INVALID_FLAG_VALUE_ERR",args); //Error - {0} {1} is empty or invalid flag value.
						print3b(fce.getFeature(), memoryItem);
						rptSb.append("</p>" + NEWLINE);
					}else {
						memoryCapUnitStr = memoryCapUnitStr.trim().toUpperCase();
						memoryCapUnit = getUnit(memoryCapUnitStr, "MEMORY.CAPUNIT", memoryItem);
						memoryRAMStandard += (fce.getQuantity() * memoryCap * memoryCapUnit);
					}
				}
			} // end memory element vct loop
		}   

		//set the memory
		if(memoryRAMStandard>0){
			memoryRAMStandardStr = convertResultToString(memoryRAMStandard);
			tempStr = getUnitStr(memoryRAMStandard);
			addDebug("Total memory bytes: "+memoryRAMStandard+" converted:"+memoryRAMStandardStr+
					" units "+tempStr);

			memoryRAMStandardUnitStr = getFlagCodeForDesc("MEMRYRAMSTDUNIT", tempStr);

			if(null == memoryRAMStandardUnitStr) //MEMRYRAMSTDUNIT	0030	MB
			{
				EntityGroup egrp = m_elist.getEntityGroup("DERIVEDDATA");
				// bad meta or calcs ended in KB
				memoryRAMStandardUnitStr="0030";  // force it to MB
				memoryRAMStandardStr = "0";  // force to zero
				args[0] = egrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(egrp, "MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT");
				args[2] = tempStr;
				printError("INVALID_FLAGS_ERR",args,true); //Error - {0} {1} flag values do not have {2}");
			}
		}//end of if(memoryRAMStandard > 0)
		else{
			//Set DERIVEDDATA.MEMRYRAMSTD = 0 AND DERIVEDDATA.MEMRYRAMSTDUNIT = MB
			memoryRAMStandardUnitStr = getFlagCodeForDesc("MEMRYRAMSTDUNIT", "MB");
			if(null == memoryRAMStandardUnitStr){
				// bad meta
				EntityGroup egrp = m_elist.getEntityGroup("DERIVEDDATA");
				memoryRAMStandardUnitStr="0030";  // force it to MB
				args[0] = egrp.getLongDescription();
				args[1] = PokUtils.getAttributeDescription(egrp, "MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT");
				args[2] = tempStr;
				printError("INVALID_FLAGS_ERR",args,true); //Error - {0} {1} flag values do not have {2}");
			}
		}

		derivedDataAttList.put("DERIVEDDATA:MEMRYRAMSTD", "MEMRYRAMSTD=" + memoryRAMStandardStr);
		derivedDataAttList.put("DERIVEDDATA:MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT=" + memoryRAMStandardUnitStr);
	}

	/***********************************************
	 * Get derived data for DERIVEDDATA.TOTL2CACHESTD and DERIVEDDATA.TOTL2CACHESTDUNIT
	 * D.	Total L2 Cache Standard
	 * 
	 * Used for calculation:
	 * 	PROC.PROCL2CACHE
	 * 	PROC.PROCL2CACHEUNIT
	 * 
	 * Calculation result:
	 * 	Find the first FEATURE with a PROC.
	 * 	DERIVEDDATA.TOTL2CACHESTD = PROC.PROCL2CACHE
	 * 	DERIVEDDATA.TOTL2CACHESTDUNIT = PROC.PROCL2CACHEUNIT
	 * 
	 * Verification:
	 * 	Then verify that DERIVEDDATA.TOTL2CACHESTD & TOTL2CACHESTDUNIT has to match
	 * 	every PROC.PROCL2CACHE & PROCL2CACHEUNIT.
	 * 
	 * If there is a mismatch, then report an error.
	 * Error Text:  PROCL2CACHE & PROCL2CACHEUNIT are not consistent for all PROCs.
	 * Line 3b: PROC
	 * 
	 * If PROC.PROCL2CACHEUNIT is not set, then report an error.
	 * Error Text:  PROC PROCL2CACHEUNIT is empty
	 * Line 3b:  PROC
	 * 
	 * If there are no PROC, then the result is �0� (zero) �KB�.
	 */
	private String procL2CacheStr = null;
	private String procL2CacheUnitStr = null;

	private void getDDTotalL2CacheStandard()
	{
		String totL2CacheStandardStr = "";
		String totL2CacheStandardUnitStr = "";

		// get all the processor elements
		Vector vct = (Vector)fcElemTbl.get("PROC");
		addDebug("Calculate TOTL2CACHESTD for "+(vct==null?0:vct.size())+" PROC");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				// find first feature with a proc
				// Then verify that DERIVEDDATA.TOTL2CACHESTD & TOTL2CACHESTDUNIT has to match
				// 	every PROC.PROCL2CACHE & PROCL2CACHEUNIT.
				FCElement fce = (FCElement)vct.elementAt(i);
				checkProcL2Cache(fce);
			}
		}   

		if (procL2CacheStr == null){
			procL2CacheStr = "0";
		}
		if (procL2CacheUnitStr == null){
			procL2CacheUnitStr = "KB";
		}

		totL2CacheStandardStr = getFlagCodeForDesc("TOTL2CACHESTD", procL2CacheStr);
		if(null == totL2CacheStandardStr){
			EntityGroup egrp = m_elist.getEntityGroup("DERIVEDDATA");
			totL2CacheStandardStr ="0010"; //TOTL2CACHESTD	0010	0 
			args[0] = egrp.getLongDescription();
			args[1] = PokUtils.getAttributeDescription(egrp, "TOTL2CACHESTD", "TOTL2CACHESTD");
			args[2] = procL2CacheStr;
			printError("INVALID_FLAGS_ERR",args,true); //Error - {0} {1} flag values do not have {2}");
		}

		totL2CacheStandardUnitStr = getFlagCodeForDesc("TOTL2CACHESTDUNIT", procL2CacheUnitStr);
		if(null == totL2CacheStandardUnitStr){
			EntityGroup egrp = m_elist.getEntityGroup("DERIVEDDATA");
			totL2CacheStandardUnitStr= "0010";//TOTL2CACHESTDUNIT	0010	KB
			args[0] = egrp.getLongDescription();
			args[1] = PokUtils.getAttributeDescription(egrp, "TOTL2CACHESTDUNIT", "TOTL2CACHESTDUNIT");
			args[2] = procL2CacheUnitStr;
			printError("INVALID_FLAGS_ERR",args,true); //Error - {0} {1} flag values do not have {2}");
		}

		derivedDataAttList.put("DERIVEDDATA:TOTL2CACHESTD", "TOTL2CACHESTD=" + totL2CacheStandardStr);
		derivedDataAttList.put("DERIVEDDATA:TOTL2CACHESTDUNIT", "TOTL2CACHESTDUNIT=" + totL2CacheStandardUnitStr);
	}

	private void checkProcL2Cache(FCElement fce)
	{
		addDebug("checkProcL2Cache entered for "+fce);
		EntityItem procItem = fce.getElement();
		EntityItem featureItem = fce.getFeature();
		String tmpL2CacheStr = PokUtils.getAttributeValue(procItem, "PROCL2CACHE", "", "0", false);
		String tmpL2CacheUnitStr = PokUtils.getAttributeValue(procItem, "PROCL2CACHEUNIT", "", "", false);
		addDebug(procItem.getKey()+" PROCL2CACHE="+tmpL2CacheStr+" PROCL2CACHEUNIT "+
				tmpL2CacheUnitStr);

		if("".equals(tmpL2CacheUnitStr)) // no units were specified for the cache size
		{
			//Report an error
			EntityGroup egrp = procItem.getEntityGroup();
			args[0] = egrp.getLongDescription();
			args[1] = PokUtils.getAttributeDescription(egrp, "PROCL2CACHEUNIT", "PROCL2CACHEUNIT");
			printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
			print3b(fce.getFeature(), procItem);
			rptSb.append("</p>" + NEWLINE);
			tmpL2CacheUnitStr = "KB"; // default to KB
		} 
		if (procL2CacheUnitStr ==null){ // first one found
			procL2CacheStr = tmpL2CacheStr;
			procL2CacheUnitStr = tmpL2CacheUnitStr.trim().toUpperCase();
		}else{
			//If there is a mismatch, then report an error.
			// Error Text:  PROCL2CACHE & PROCL2CACHEUNIT are not consistent for all PROCs.
			// Line 3b: PROC
			//INVALID_L2CACHE_ERR = Error - &quot;{0}&quot; and &quot;{1}&quot; are not consistent for all {2}s.				
			if ((!tmpL2CacheStr.equals(procL2CacheStr)) ||
					(!tmpL2CacheUnitStr.equals(procL2CacheUnitStr)))
			{
				args[0] = PokUtils.getAttributeDescription(procItem.getEntityGroup(), "PROCL2CACHE", "PROCL2CACHE");
				args[1] = PokUtils.getAttributeDescription(procItem.getEntityGroup(), "PROCL2CACHEUNIT", "PROCL2CACHEUNIT");
				args[2] = m_elist.getEntityGroup("PROC").getLongDescription();
				printError("INVALID_L2CACHE_ERR",args); 
				print3b(featureItem, procItem);
				rptSb.append("</p>" + NEWLINE);
			}
		}

		// the feature can only have one PROC, check here
		Vector procVector = getAllLinkedEntities(featureItem, "FEATUREPROC", "PROC"); // get dupes
		if (procVector.size()>1) { // can only have 1 PROC per FEATURE, output error
			procItem = (EntityItem) procVector.get(0);
			String errmsgkey = featureItem.getKey()+":PROC";
			if (!errMsgVct.contains(errmsgkey)){
				errMsgVct.add(errmsgkey);
				args[0] = m_elist.getEntityGroup("PROC").getLongDescription();
				args[1] = m_elist.getEntityGroup("FEATURE").getLongDescription();
				printError("TOO_MANY_CHILDREN_ERR",args); //Error - Too many {0} for the {1}.
				print3b(featureItem, procItem);
				addDebug(featureItem.getKey()+" Too many PROC: "+procItem.getKey());

				for(int j = 1; j < procVector.size(); j++)	{
					procItem = (EntityItem) procVector.get(j);
					print3a(procItem, false);
					addDebug(featureItem.getKey()+" Too many PROC: "+procItem.getKey());
				}//end of for(int j = 0; j < procVector.size(); j++)
				rptSb.append("</p>" + NEWLINE);
			}
		}

		procVector.clear();
	}

	/***********************************************
	 *
	 * @param unitStr String
	 * @param msg String
	 * @param ei EntityItem
	 * @returns int
	 */
	private long getUnit(String unitStr, String msg, EntityItem ei)
	{
		long unit = 0;

		if("KB".equals(unitStr)) {
			unit = KB;
		}
		else if("MB".equals(unitStr)){
			unit = MB;
		}
		else if("GB".equals(unitStr)) {
			unit = GB;
		}
		else  {
			args[0] = msg;
			//{0} is not KB or MB or GB. So default to 0. 
			MessageFormat msgf = new MessageFormat(rsBundle.getString("INVALID_UNIT"));
			rptSb.append("<p>"+msgf.format(args)+"<br />" + NEWLINE);						
			print3a(ei);
		}

		return unit;
	}

	/***********************************************
	 * If result >= 1,000,000,000 then remove the right most eight characters and place the decimal point
	 * to the left of the right most character. The units = GB.
	 * If result >= 1,000,000 then remove the right most five characters and place the decimal point
	 * to the left of the right most character. The units = MB.
	 * If result >= 1,000 then remove the right most two characters and place the decimal point
	 * to the left of the right most character. The units = KB.
	 *
	 * If the right most character is 0 (zero), remove the right most 2 characters(i.e. there is no fraction, therefore remove the decimal point and the fraction).
	 *
	 * @param n long
	 * @returns String
	 */
	private String convertResultToString(long n)
	{
		String str1 = Long.toString(n);
		int i = str1.length();

		if(n >= GB) {
			str1 = str1.substring(0, i - 8);
			i = str1.length();
			str1 = str1.substring(0, i - 1) + "." + str1.substring(i - 1);
		}
		else if(n >= MB) {
			str1 = str1.substring(0, i - 5);
			i = str1.length();
			str1 = str1.substring(0, i - 1) + "." + str1.substring(i - 1);
		}
		else if(n >= KB){
			str1 = str1.substring(0, i - 2);
			i = str1.length();
			str1 = str1.substring(0, i - 1) + "." + str1.substring(i - 1);
		}

		i = str1.length();
		if(str1.lastIndexOf("0") == (i - 1)) {
			str1 = str1.substring(0, i -2);
		}

		return str1;
	}

	/***********************************************
	 *
	 * @param n long
	 * @returns String
	 */
	private String getUnitStr(long n)
	{
		String unit = "";

		if(n >= GB) {
			unit = "GB";
		}
		else if(n >= MB) {
			unit = "MB";
		}
		else if(n >= KB) {
			unit = "KB";
		}

		return unit;
	}

	/***********************************************
	 * Get derived data for DERIVEDDATA.NOOFPROCSTD and DERIVEDDATA.NOOFPROCMAX
	 *
	 *F.	Number of Processors Standard
	 *
	 * Used for calculation:
	 * 	PLANAR.NOOFPROCMAX
	 * 	EXPDUNIT.NOOFPROCMAX
	 * 	FEATUREPROC.QTY
	 * 	WWSEOPRODSTRUCT.CONFQTY
	 * 
	 * Calculation result:
	 * 	For each Feature that is described by one or more PROC, sum the following as DERIVEDDATA.NOOFPROCSTD:
	 * 	�	WWSEOPRODSTRUCT.CONFQTY * FEATUREPROC.QTY
	 * 
	 * Calculation result:
	 * 	For each FEATURE that is described by one or more EXPDUNIT, sum the following as qtysum1:
	 * 	�	WWSEOPRODSTRUCT.CONFQTY * EXPDUNIT.NOOFPROCMAX
	 * 	For each FEATURE that is described by one or more PLANAR, sum the following as qtysum2:
	 * 	�	WWSEOPRODSTRUCT.CONFQTY * FEATUREPLANAR.QTY * PLANAR.NOOFPROCMAX
	 * 	DERIVEDDATA.NOOFPROCMAX = qtysum1 + qtysum2
	 * 	
	 * If DERIVEDDATA.NOOFPROCSTD is greater than PLANAR.NOOFPROCMAX, report an error.
	 * 	Error Text:  The DERIVEDDATA NOOFPROCSTD is greater than PLANAR.NOOFPROCMAX plus EXPDUNIT.NOOFPROCMAX.
	 * 	Line 3b:  PLANAR
	 * 	Line 3b:  PROC
	 */
	private void getDDNoOfProcStandard()
	{
		int noOfProcStandard = 0;
		int procQtySum1 = 0;
		int procQtySum2 = 0;

		//For each Feature that is described by one or more PROC, sum the following as DERIVEDDATA.NOOFPROCSTD:
		// 	�	WWSEOPRODSTRUCT.CONFQTY * FEATUREPROC.QTY
		// get all the proc elements
		Vector vct = (Vector)fcElemTbl.get("PROC");
		addDebug("Calculate NOOFPROCSTD for "+(vct==null?0:vct.size())+" PROC");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				noOfProcStandard+=fce.getQuantity();
				addDebug("checking PROC["+i+"] for "+fce+" quantity:"+fce.getQuantity()+" noOfProcStandard:"+noOfProcStandard);
			}
		}

		//For each FEATURE that is described by one or more EXPDUNIT, sum the following as qtysum1:
		// 	�	WWSEOPRODSTRUCT.CONFQTY * EXPDUNIT.NOOFPROCMAX
		vct = (Vector)fcElemTbl.get("EXPDUNIT");
		addDebug("Calculate qtysum1 for "+(vct==null?0:vct.size())+" EXPDUNIT");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				addDebug("checking EXPDUNIT["+i+"] for "+fce);
				// EXPDUNIT.NOOFPROCMAX was missing from meta 
				EANMetaAttribute metaAttr = fce.getElement().getEntityGroup().getMetaAttribute("NOOFPROCMAX");
				if (metaAttr==null) {
					setReturnCode(FAIL);
					rptSb.append("<p><span style=\"color:#c00; font-weight:bold;\">Attribute &quot;"+
							"NOOFPROCMAX&quot; NOT found in &quot;"+
							fce.getElement().getEntityType()+"&quot; META data.</span></p>");
					procQtySum1 = 0; 
					break;
				}				
				String noOfProcMaxStr = PokUtils.getAttributeValue(fce.getElement(), "NOOFPROCMAX", "", "0", false);
				int noOfProcMax = Integer.parseInt(noOfProcMaxStr); 				
				procQtySum1+=(fce.getQuantity()*noOfProcMax);
				addDebug(fce.getElement().getKey()+" NOOFPROCMAX="+noOfProcMaxStr+" quantity:"+fce.getQuantity()+
						" procQtySum1="+procQtySum1);
			}
		}		
		//For each FEATURE that is described by one or more PLANAR, sum the following as qtysum2:
		// 	�	WWSEOPRODSTRUCT.CONFQTY * FEATUREPLANAR.QTY * PLANAR.NOOFPROCMAX
		vct = (Vector)fcElemTbl.get("PLANAR");
		addDebug("Calculate qtysum2 for "+(vct==null?0:vct.size())+" PLANAR");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				addDebug("checking PLANAR["+i+"] for "+fce);
				String noOfProcMaxStr = PokUtils.getAttributeValue(fce.getElement(), "NOOFPROCMAX", "", "0", false);	            
				int noOfProcMax = Integer.parseInt(noOfProcMaxStr); 				
				procQtySum2+=(fce.getQuantity()*noOfProcMax);
				addDebug(fce.getElement().getKey()+" NOOFPROCMAX="+noOfProcMaxStr+" quantity:"+
						fce.getQuantity()+" procQtySum2="+procQtySum2);
			}
		}	

		//If DERIVEDDATA.NOOFPROCSTD is greater than PLANAR.NOOFPROCMAX plus EXPDUNIT.NOOFPROCMAX, report an error.
		//Error Text:  The DERIVEDDATA NOOFPROCSTD is greater than PLANAR.NOOFPROCMAX plus EXPDUNIT.NOOFPROCMAX.
		//Line 3b:  PLANAR
		//Line 3b:  PROC
		if (noOfProcStandard > (procQtySum1 +procQtySum2))	{
			EntityGroup egrp = m_elist.getEntityGroup("DERIVEDDATA");
			args[0] = egrp.getLongDescription();
			args[1] = PokUtils.getAttributeDescription(egrp, "NOOFPROCSTD", "NOOFPROCSTD");
			egrp = m_elist.getEntityGroup("PLANAR");
			args[2] = egrp.getLongDescription();
			args[3] = PokUtils.getAttributeDescription(egrp, "NOOFPROCMAX", "NOOFPROCMAX");
			egrp = m_elist.getEntityGroup("EXPDUNIT");
			args[4] = egrp.getLongDescription();
			args[5] = PokUtils.getAttributeDescription(egrp, "NOOFPROCMAX", "NOOFPROCMAX");
			printError("NOOFPROCSTD_1_ERROR",args); //Error - {0} {1} is greater than {2} {3} plus {4} {5}.
			vct = (Vector)fcElemTbl.get("PLANAR");
			if (vct != null){
				for (int i=0; i<vct.size(); i++){
					FCElement fce = (FCElement)vct.elementAt(i);
					print3b(fce.getFeature(), fce.getElement());
				}
			}
			vct = (Vector)fcElemTbl.get("EXPDUNIT");
			if (vct != null){
				for (int i=0; i<vct.size(); i++){
					FCElement fce = (FCElement)vct.elementAt(i);
					print3b(fce.getFeature(), fce.getElement());
				}
			}
			vct = (Vector)fcElemTbl.get("PROC");
			if (vct != null){
				for (int i=0; i<vct.size(); i++){
					FCElement fce = (FCElement)vct.elementAt(i);
					print3b(fce.getFeature(), fce.getElement());
				}
			}

			rptSb.append("</p>" + NEWLINE);
		}

		// set this no matter what
		derivedDataAttList.put("DERIVEDDATA:NOOFPROCSTD", "NOOFPROCSTD=" + noOfProcStandard);
		//DERIVEDDATA.NOOFPROCMAX = qtysum1 + qtysum2
		derivedDataAttList.put("DERIVEDDATA:NOOFPROCMAX", "NOOFPROCMAX=" + (procQtySum1 +procQtySum2));
	}

	/***********************************************
	 * G.	Number of Installed Hard Drives
	 * Used for calculation:
	 * 	HDD.HDDCAP
	 * 	FEATUREHDD.QTY
	 * 
	 * Calculation result:
	 * DERIVEDDATA.NOOFINSTHARDDRVS
	 * 
	 * The quantity is WWSEOPRODSTRUCT.CONFQTY.
	 * Consider FEATUREs that are described by HDD where HDDCAP > 0, then sum WWSEOPRODSTRUCT.CONFQTY
	 * times FEATUREHDD.QTY as DERIVEDDATA.NOOFINSTHARDDRVS.
	 */
	private void getDDNoOfInstHardDrvs()
	{
		int noOfInstHardDrvs = 0;
		Vector vct = (Vector)fcElemTbl.get("HDD");
		addDebug("Calculate NOOFINSTHARDDRVS for "+(vct==null?0:vct.size())+" HDD");
		if (vct != null){
			for (int i=0; i<vct.size(); i++){
				FCElement fce = (FCElement)vct.elementAt(i);
				EntityItem elemItem = fce.getElement();
				String hddCapStr = PokUtils.getAttributeValue(elemItem, "HDDCAP" , "", "0", false);
				float hddCap = Float.parseFloat(hddCapStr);
				if (hddCap>0){
					noOfInstHardDrvs+=fce.getQuantity();
				}
				addDebug(elemItem.getKey()+ ".HDDCAP="+hddCap+" quantity: "+fce.getQuantity()+" noOfInstHardDrvs: "+noOfInstHardDrvs);        		
			}
		}

		// this should be set no matter what
		derivedDataAttList.put("DERIVEDDATA:NOOFINSTHARDDRVS", "NOOFINSTHARDDRVS=" + noOfInstHardDrvs);
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationNameWithoutCountryList(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();

		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null){
			// NAME is navigate attributes
			EntityGroup eg =  new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(),metaList);
		}
		for(int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			if(!("COUNTRYLIST".equals(ma.getAttributeCode())))
			{
				if (navName.length()>0) {
					navName.append(", ");
				}
				navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), DELIMITER, "", false));
			}
		}

		return navName.toString();
	}

	/***********************************************
	 * Print 3a format message
	 * Line 3a:
	 * The entity type long description followed by the entity's Navigation Display Attributes,
	 * all in a single line.
	 *
	 * @param item EntityItem
	 * @param closeP boolean
	 */
	private void print3a(EntityItem item, boolean closeP)
	{
		StringBuffer tmpsb = new StringBuffer(INDENT1);
		String navname = item.getEntityType();
		tmpsb.append(item.getEntityGroup().getLongDescription());

		try  {
			navname = getNavigationNameWithoutCountryList(item);
		} catch(Exception ex) {
			logMessage("print3a: "+item.getKey()+" Got exception " + ex);
			ex.printStackTrace();
		}

		tmpsb.append(" "+navname);
		if (closeP){
			tmpsb.append("</p>" + NEWLINE);
		}
		else{
			tmpsb.append("<br />" + NEWLINE); // more lines will follow
		}

		rptSb.append(tmpsb.toString());
	}

	/***********************************************
	 * Print 3a format message
	 * Line 3a:
	 * The entity type long description followed by the entity's Navigation Display Attributes,
	 * all in a single line.
	 *
	 * @param item EntityItem
	 */
	private void print3a(EntityItem item)
	{
		print3a(item, true);
	}

	/***********************************************
	 * Print 3b format message
	 * Line 3b:
	 * The FEATURE long description followed by FEATURECODE followed by the 'element'
	 * long description followed by the element's Navigation Display Attributes, all in a single line.
	 *
	 * @param featureItem EntityItem
	 * @param element EntityItem
	 */
	private void print3b(EntityItem featureItem, EntityItem element)
	{
		StringBuffer tmpsb = new StringBuffer(INDENT1);
		String navname = element.getEntityType();
		tmpsb.append(featureItem.getEntityGroup().getLongDescription()+
				" "+PokUtils.getAttributeValue(featureItem, "FEATURECODE",", ", "", false));

		tmpsb.append(": "+element.getEntityGroup().getLongDescription());

		try  {
			navname = getNavigationNameWithoutCountryList(element);
		} catch(Exception ex) {
			logMessage("print3b: Got exception " + ex);
		}

		tmpsb.append(": "+navname + "<br />" + NEWLINE);  // 3b always expects another 3a line

		rptSb.append(tmpsb.toString());
	}

	/***********************************************
	 * Get the flag code that corresponds to this flag description
	 *
	 * @param attributeCode String
	 * @param flagDesc String
	 * @return String flag code
	 */
	private String getFlagCodeForDesc(String attributeCode, String flagDesc)
	{
		String attFlagCode = null;

		EntityGroup eg = m_elist.getEntityGroup("DERIVEDDATA");
		EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg.getMetaAttribute(attributeCode);

		if(null == mfa) {
			//BAD_META = Can not retrieve Flag values for {0}
			String msg = rsBundle.getString("BAD_META");
			args[0] = "DERIVEDDATA." + attributeCode;
			MessageFormat msgf = new MessageFormat(msg);
			rptSb.append("<p>"+msgf.format(args) + "</p>" + NEWLINE);        	
		}else{
			//rptSb.append("<!-- DERIVEDDATA." + attributeCode + NEWLINE);
			for(int i = 0; i < mfa.getMetaFlagCount(); i++)	{
				MetaFlag mf = mfa.getMetaFlag(i);
				String desc = mf.toString().trim().toUpperCase();
				//rptSb.append("  " + desc + ", "+mf.getFlagCode()+NEWLINE);
				if (flagDesc.equals(desc)) {
					attFlagCode = mf.getFlagCode();
					break;
				}
			}
			//rptSb.append(" -->" + NEWLINE);
		}

		return attFlagCode;
	}

	/**********************************************************************************
	 * Get Locale based on NLSID
	 *
	 * @return java.util.Locale
	 */
	private Locale getLocale(int nlsID)
	{
		Locale locale = null;
		switch (nlsID)
		{
		case 1:
			locale = Locale.US;
			break;
		case 2:
			locale = Locale.GERMAN;
			break;
		case 3:
			locale = Locale.ITALIAN;
			break;
		case 4:
			locale = Locale.JAPANESE;
			break;
		case 5:
			locale = Locale.FRENCH;
			break;
		case 6:
			locale = new Locale("es", "ES");
			break;
		case 7:
			locale = Locale.UK;
			break;
		default:
			locale = Locale.US;
		break;
		}
		return locale;
	}

	/*****************************************************************************
	 * Get the current Flag Value for the specified attribute, null if not set
	 *
	 * @param entityItem EntityItem
	 * @param attrCode String attribute code to get value for
	 * @param deli String delimiter
	 * @return String attribute flag code
	 */
	public static String getAttributeFlagValue(EntityItem entityItem, String attrCode, String deli)
	{
		EANMetaAttribute metaAttr = entityItem.getEntityGroup().getMetaAttribute(attrCode);
		// Multi-flag values will be separated by |
		EANAttribute attr = entityItem.getAttribute(attrCode);
		String val=null;
		if (attr != null) {
			if (attr instanceof EANFlagAttribute)
			{
				StringBuffer sb = new StringBuffer();

				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) attr.get();
				for (int i = 0; i < mfArray.length; i++)
				{
					// get selection
					if (mfArray[i].isSelected())
					{
						if (sb.length()>0) {
							sb.append(deli); }
						sb.append(mfArray[i].getFlagCode());
						if (metaAttr.getAttributeType().equals("U")) {
							break; }
					}
				}
				val = sb.toString();
			}
		}

		return val;
	}

	/********************************************************************************
	 * display the deriveddata attribute list
	 *
	 */
	private void displayDD()
	{
		Set keySet = derivedDataAttList.keySet();
		Iterator itr = keySet.iterator();

		rptSb.append("<!-- Content of derivedDataAttList" + NEWLINE);
		while(itr.hasNext())
		{
			String key = (String) itr.next();
			rptSb.append(key + ", " + derivedDataAttList.get(key) + NEWLINE);
		}
		rptSb.append("-->" + NEWLINE);
	}

	/********************************************************************************
	 * Release memory
	 *
	 */
	private void cleanUp()
	{
		if (fcElemTbl != null){
			for (Enumeration eNum = fcElemTbl.elements(); eNum.hasMoreElements();)  {
				Vector vct = (Vector)eNum.nextElement();
				for (int i=0; i<vct.size(); i++){
					FCElement fce = (FCElement) vct.elementAt(i);
					fce.dereference();
				}
				vct.clear();
			}
			fcElemTbl.clear();
			fcElemTbl = null;
		}

		errMsgVct.clear();
		errMsgVct = null;
		pdgUtil = null;
		derivedDataAttList.clear();
		derivedDataAttList = null;
		metaTbl.clear();
		metaTbl=null;
	}

	/**********************************************************************************
	 * Line 1:
	 * Error - followed by the error text
	 * Always use the entity's Long Description (i.e. replace ENTITYTYPE with its Long Description)
	 * and if applicable, the attribute's Long Description.
	 * @param bundleid String
	 * @param msgargs Object[] with replacement strings
	 */
	private void printError(String bundleid, Object[] msgargs) {
		printError(bundleid, msgargs, false);
	}

	/**********************************************************************************
	 * Line 1:
	 * Error - followed by the error text
	 * Always use the entity's Long Description (i.e. replace ENTITYTYPE with its Long Description)
	 * and if applicable, the attribute's Long Description.
	 */
	private void printError(String bundleid, Object[] msgargs, boolean closep) {
		String msg = rsBundle.getString(bundleid);
		MessageFormat msgf = new MessageFormat(msg);
		rptSb.append("<p>"+msgf.format(msgargs));
		if (closep){
			rptSb.append("</p>" + NEWLINE);
		}else{
			rptSb.append("<br />" + NEWLINE);
		}
		setReturnCode(FAIL);
	}

	/******
	 * Elements is a way to refer to the entity types that provide the technical details for both MODEL and FEATURE. 
	 * Elements are children of MODEL and FEATURE; however, xSeries does not use Elements at Model. Therefore, 
	 * this ABR will only consider Elements of FEATURE. The following table shows the various Elements with 
	 * the following columns:
	 * 	# of Elements - If not blank, then it is the attribute code on the relator from FEATURE to the Element 
	 * 		that specifies the quantity of the Element related to the  FEATURE
	 * 	Element - The entity type of the Element
	 * 	# of Children - If not blank, then it is the attribute code on the relator from the Element to the 
	 * 		Child that specifies the number of this child related to the Element
	 * 	Child - The entity type of the Child of the Element
	 * 
	 * Whenever checks and/or derivations are made, these quantities must be taken into account.
	 * A WWSEO has multiple Features defined via WWSEOPRODSTRUCT which has a Quantity (CONFQTY). This is the 
	 * number of the selected FEATUREs in the configuration. This quantity must be taken into account.
	 * 
	 * If any of the �quantity� attributes is empty, then assume a quantity of one.
	 * 
	 * For example, the number of processors would be equal to the sum of:
	 * WWSEOPRODSTRUCT.CONFQTY * WWSEOPRODSTRUCT-d : PRODSTRUCT-u : FEATUREPROC.QTY
	 * 
	 * # of Elements	Element			# of Children	Child
	 * 					EXPDUNIT						SLOT
	 * QTY				HDD
	 * QTY				MECHPKG			BAYQTY			BAY
	 * QTY				MEMORYCARD		SLOTQTY			SLOT
	 * QTY				PLANAR			SLOTQTY			SLOT
	 * QTY				PROC							TECHCAP
	 */
	private class FCElement {
		protected String key;
		private int confQty = 1;
		private int qty = 1;
		private EntityItem elementItem=null;
		private EntityItem featureItem=null;

		/***
		 * used for elements
		 * @param wwseops
		 * @param ps
		 * @param fc
		 * @param fcelemrel
		 * @param elem
		 */
		FCElement(EntityItem wwseops, EntityItem ps, EntityItem fc, EntityItem fcelemrel, EntityItem elem){
			key = wwseops.getKey()+":"+ps.getKey()+":"+fc.getKey()+":"+fcelemrel.getKey()+":"+elem.getKey();
			elementItem = elem;
			featureItem = fc;
			// WWSEOPRODSTRUCT.CONFQTY
			String qtyStr = PokUtils.getAttributeValue(wwseops, "CONFQTY", "", "1",false);
			confQty = Integer.parseInt(qtyStr);
			// check for attribute on FEATURE->ELEMENT relator.QTY
			EANMetaAttribute metaAttr = fcelemrel.getEntityGroup().getMetaAttribute("QTY");
			if (metaAttr==null) {
				addDebug("QTY not found in meta for "+fcelemrel.getKey());
			}
			else{
				qtyStr = PokUtils.getAttributeValue(fcelemrel, "QTY", "", "1",false);
				qty = Integer.parseInt(qtyStr);
			}
		}

		void dereference(){
			key=null;
			elementItem = null;
			featureItem = null;
		}
		EntityItem getElement() { return elementItem;}
		EntityItem getFeature() { return featureItem;}
		int getQuantity(){
			return confQty*qty;
		}
		int getConfQty() { return confQty;}
		int getQty() { return qty; }

		/****
		 * Each instance of Expansion Unit (EXPDUNIT) may have at most one child of type Slot (SLOT) of a 
		 * given Slot Type (SLOTTYPE) and Slot Size (SLOTSZE). For every SLOTTYPE and Slot Size (SLOTSZE), 
		 * there must exist a Slots Available (SLOTSAVAIL) of the same SLOTTYPE and Slot Size (SLOTSZE) where 
		 * ELEMENTTYPE = �Expansion Unit� (0030) for the WWSEO (found via WWSEOSLOTSAVAIL). 
		 * 
		 * Each instance of PLANAR may have at most one child of type SLOT of a given SLOTTYPE and Slot Size 
		 * (SLOTSZE). For every SLOTTYPE and Slot Size (SLOTSZE), there must exist a SLOTSAVAIL of the same 
		 * SLOTTYPE and Slot Size (SLOTSZE) where ELEMENTTYPE = �Planar� (0020) for the WWSEO (found via WWSEOSLOTSAVAIL).
		 * If PLANARSLOT.SLOTQTY > 1, then this rule is violated.
		 * 
		 * Similarly, each instance of MEMORYCARD may have at most one child of type SLOT of a given SLOTTYPE 
		 * and Slot Size (SLOTSZE). For every SLOTTYPE and Slot Size (SLOTSZE), there must exist a SLOTSAVAIL 
		 * of the same SLOTTYPE and Slot Size (SLOTSZE) where ELEMENTTYPE = �Memory Card� (0010) for the WWSEO 
		 * (found via WWSEOSLOTSAVAIL). If MEMORYCARDSLOT.SLOTQTY > 1, then this rule is violated.
		 * @throws MiddlewareException 
		 * @throws SQLException 
		 */
		int checkSlots(Hashtable slotsAvailTbl, String elemType, Vector matchedSlotVct) throws SQLException, MiddlewareException{
			int totalSlotTot = 0;
			String attrCode = "SLOTQTY";
			Hashtable slotTbl = new Hashtable();
			addDebug("checkSlots: entered for "+this);
			String heading = getLD_NDN(getElement());
			if(getElement().getDownLinkCount()>0){
				heading  = heading +" "+
				((EntityItem)getElement().getDownLink(0)).getEntityGroup().getLongDescription()+" checks:";
			}else{
				heading  = heading +" has No SLOTs";
			}
			addHeading(4,heading);

			// look at all downlinks
			for (int ce =0; ce<getElement().getDownLinkCount(); ce++){
				EntityItem slotrel = (EntityItem)getElement().getDownLink(ce);				
				EANMetaAttribute metaAttr = slotrel.getEntityGroup().getMetaAttribute(attrCode);
				if (metaAttr==null) {
					// EXPDUNITSLOT does not have a quantity
					addDebug("checkSlots["+ce+"]: "+getElement().getKey()+":"+slotrel.getKey()+" 'Quantity' not found in meta");
				}else{
					String qtyStr = PokUtils.getAttributeValue(slotrel, attrCode, "", "1",false);
					addDebug("checkSlots["+ce+"]: "+getElement().getKey()+":"+slotrel.getKey()+" qty: "+qtyStr);
					int relqty = Integer.parseInt(qtyStr);
					if (relqty>1){
						addDebug("checkSlots: Error qty>1 qty: "+qtyStr+" on "+slotrel.getKey());
						EntityItem slotItem =  (EntityItem)slotrel.getDownLink(0);
						// If MEMORYCARDSLOT.SLOTQTY > 1, then this rule is violated., then report an error.
						//Line 3a:  SLOT
						//INVALID_QTY_ERROR = Error - Quantity was greater than one for a given &quot;{0}&quot; found.
						args[0] = slotItem.getEntityGroup().getLongDescription();						
						printError("INVALID_QTY_ERROR",args); 
						print3b(getFeature(),getElement());
						print3a(slotItem);	
					}
				}				
				for (int cr = 0; cr<slotrel.getDownLinkCount(); cr++){
					EntityItem slotItem = (EntityItem)slotrel.getDownLink(cr);
					// get slottype
					String slotType = getAttributeFlagEnabledValue(slotItem, "SLOTTYPE");
					String slotSze = getAttributeFlagEnabledValue(slotItem, "SLOTSZE");

					addDebug("checkSlots: Checking "+getElement().getKey()+":"+slotrel.getKey()+":"+
							slotItem.getKey()+" slotType:"+slotType+" slotSze:"+slotSze);
					if(slotType==null){ // if not set then this is an error
						//If a SLOT does not have an SLOTTYPE specified, report an error.
						// Error Text:  SLOT.SLOTTYPE is empty.
						// Line 3a: SLOT
						args[0] = slotItem.getEntityGroup().getLongDescription();
						args[1] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
						printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
						print3a(slotItem);
					} else if(slotSze==null){ // if not set then this is an error
						//If a SLOT does not have an SLOTSZE specified, report an error.
						// Error Text:  SLOT.SLOTSZE is empty.
						// Line 3a: SLOT
						args[0] = slotItem.getEntityGroup().getLongDescription();
						args[1] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTSZE", "SLOTSZE");
						printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
						print3a(slotItem);
					} else {
						EntityItem slot = (EntityItem)slotTbl.get(slotType+slotSze);
						if (slot==null){ // not found yet
							slotTbl.put(slotType+slotSze, slotItem);							
							EntityItem slotsAvailItem = (EntityItem)slotsAvailTbl.get(elemType+slotType+slotSze);
							if (slotsAvailItem == null){
								//If there isn�t a SLOTSAVAIL of a required ELEMENTTYPE & SLOTTYPE, then report an error.
								//Error Text:  There is no SLOTSAVAIL of a given ELEMENTTYPE & SLOTTYPE
								//Line 3b:  Expansion|Planar|MemoryCard
								//Line 3a:  Slot
								args[0] = m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription();
								args[1] = PokUtils.getAttributeDescription(m_elist.getEntityGroup("SLOTSAVAIL"), "ELEMENTTYPE", "ELEMENTTYPE");
								args[2] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
								args[3] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTSZE", "SLOTSZE");
								//NO_SLOTSAVAIL_ERROR = Error - There is no &quot;{0}&quot; of a given &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot;.
								printError("NO_SLOTSAVAIL_ERROR",args); 
								print3b(getFeature(),getElement());
								print3a(slotItem);
							}else{
								matchedSlotVct.add(elemType+slotType+slotSze);// this one is accounted for
								String slotTotStr = PokUtils.getAttributeValue(slotItem, "SLOTTOT", "", "0", false);
								int slotTot = Integer.parseInt(slotTotStr);
								totalSlotTot += slotTot;
								addDebug("checkSlots: adding "+slotItem.getKey()+".SLOTTOT="+slotTotStr+" totalSlotTot:"+totalSlotTot);
							}
						}else{
							//If there is more than one SLOT of a given SLOTTYPE, then report an error.
							//Line 3a:  SLOT
							args[0] = slotItem.getEntityGroup().getLongDescription();
							args[1] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
							args[2] = PokUtils.getAttributeDescription(slotItem.getEntityGroup(), "SLOTSZE", "SLOTSZE");
							//DUPLICATE_SLOT_ERROR = Error - More than one &quot;{0}&quot; of a given &quot;{1}&quot; and &quot;{2}&quot; found.
							printError("DUPLICATE_SLOT_ERROR",args); 
							print3b(getFeature(),getElement());
							print3a(slotItem, false);	
							print3a(slot);
						}
					}		
				}
			}

			slotTbl.clear();
			return totalSlotTot;
		}    	
		/****
		 * An instance of MECHPKG may have more than one BAY.  Each combination of a given 
		 * BAY.BAYTYPE+BAY.ACCSS+BAY.BAYFF must be unique. For every BAY.BAYTYPE+BAY.ACCSS+BAY.BAYFF 
		 * there must exist a BAYSAVAIL of the same BAYSAVAIL. BAYAVAILTYPE + BAYSAVAIL.ACCSS + BAYSAVAIL.BAYFF.  
		 * Each combination of a given BAYSAVAIL.BAYAVAILTYPE +BAY.ACCSS+BAY.BAYFF must be unique. 
		 * If MECHPKGBAY.BAYQTY > 1, then this rule is violated.
		 * @throws MiddlewareException 
		 * @throws SQLException 
		 */
		int checkBays(Hashtable baysAvailTbl, Vector matchedBaysVct) throws SQLException, MiddlewareException{
			int totalBayTot = 0;
			String attrCode = "BAYQTY";
			Hashtable bayTbl = new Hashtable();
			addDebug("checkBays: entered for "+this);
			String heading = getLD_NDN(getElement());
			if(getElement().getDownLinkCount()>0){
				heading  = heading +" "+
				((EntityItem)getElement().getDownLink(0)).getEntityGroup().getLongDescription()+" checks:";
			}else{
				heading  = heading +" has No BAYs";
			}
			addHeading(4,heading);
			// look at all downlinks
			for (int ce =0; ce<getElement().getDownLinkCount(); ce++){
				EntityItem bayrel = (EntityItem)getElement().getDownLink(ce);
				String qtyStr = PokUtils.getAttributeValue(bayrel, attrCode, "", "1",false);
				addDebug("checkBays["+ce+"]: "+getElement().getKey()+":"+bayrel.getKey()+" qty: "+qtyStr);
				int relqty = Integer.parseInt(qtyStr);
				if (relqty>1){
					addDebug("checkBays: Error qty>1 qty: "+qtyStr+" on "+bayrel.getKey());
					EntityItem bayItem = (EntityItem)bayrel.getDownLink(0);
					//MECHPKGBAY.BAYQTY > 1, then this rule is violated., then report an error.
					//INVALID_QTY_ERROR = Error - Quantity was greater than one for a given &quot;{0}&quot; found.
					args[0] = bayItem.getEntityGroup().getLongDescription();						
					printError("INVALID_QTY_ERROR",args);
					print3b(getFeature(),getElement());
					print3a(bayItem);	
				}

				for (int cr = 0; cr<bayrel.getDownLinkCount(); cr++){
					EntityItem bayItem = (EntityItem)bayrel.getDownLink(cr);					
					// get baytype
					String bayType = getAttributeFlagEnabledValue(bayItem, "BAYTYPE");
					String bayAccss = getAttributeFlagEnabledValue(bayItem, "ACCSS"); // use flag value
					String bayFF = getAttributeFlagEnabledValue(bayItem, "BAYFF"); // use flag value
					String bayInfo;
					addDebug("checkBays: Checking "+getElement().getKey()+" "+bayrel.getKey()+" "+
							bayItem.getKey()+" bayType:"+bayType+" bayAccss:"+bayAccss+" bayFF:"+bayFF);
					if (bayType == null ||
							bayAccss == null ||
							bayFF == null) 
					{
						args[0] = bayItem.getEntityGroup().getLongDescription();
						if (bayType==null){
							args[1] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYTYPE", "BAYTYPE");
							printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
						}
						if (bayAccss==null){
							args[1] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "ACCSS", "ACCSS");
							printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
						}
						if (bayFF==null){
							args[1] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYFF", "BAYFF");
							printError("ATTR_EMPTY_ERR",args); //Error - {0} {1} is empty.
						}
						print3a(bayItem);
						continue;
					}
					bayInfo = bayType +bayAccss + bayFF;						
					EntityItem bay = (EntityItem)bayTbl.get(bayInfo);
					if (bay==null){
						bayTbl.put(bayInfo, bayItem);							
						EntityItem baysAvailItem = (EntityItem)baysAvailTbl.get(bayInfo);
						if (baysAvailItem == null){
							//If there isn�t a BAYSAVAIL of a required BAY.BAYTYPE+BAY.ACCESS+BAY.BAYFF, then report an error.
							//Error Text:  There is no BAYSAVAIL of a given BAY.BAYTYPE+BAY.ACCESS+BAY.BAYFF
							//Line 3b:  MECHPKG
							//Line 3a:  BAY
							args[0] = m_elist.getEntityGroup("BAYSAVAIL").getLongDescription();
							args[1] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYTYPE", "BAYTYPE");
							args[2] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "ACCSS", "ACCSS");
							args[3] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYFF", "BAYFF");

							//NO_BAYSAVAIL_ERROR = Error - There is no &quot;{0}&quot; of a required &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot;.
							printError("NO_BAYSAVAIL_ERROR",args); 
							print3b(getFeature(),getElement());
							print3a(bayItem);								
						}else{
							matchedBaysVct.add(bayInfo);// this one is accounted for
							String bayTotStr = PokUtils.getAttributeValue(bayItem, "BAYTOT", "", "0", false);				            
							int bayTot = Integer.parseInt(bayTotStr);
							totalBayTot += bayTot;	
							addDebug("checkBays: adding "+bayItem.getKey()+".BAYTOT="+bayTotStr+" totalBayTot:"+totalBayTot);
						}
					}else{
						//If there is more than one BAY of a given BAYTYPE+ACCSS+BAYFF, then report an error.
						//Error Text:  More than one BAY of a given BAYTYPE+ACCSS+BAYFF
						//Line 3b:  MECHPKG 
						//Line 3a:  BAY
						args[0] = bayItem.getEntityGroup().getLongDescription();
						args[1] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYTYPE", "BAYTYPE");
						args[2] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "ACCSS", "ACCSS");
						args[3] = PokUtils.getAttributeDescription(bayItem.getEntityGroup(), "BAYFF", "BAYFF");

						//DUPLICATE_BAYS_ERROR = Error - More than one &quot;{0}&quot; of a given &quot;{1}&quot; and &quot;{2}&quot; and &quot;{3}&quot; found.
						printError("DUPLICATE_BAYS_ERROR",args);  
						print3b(getFeature(),getElement());
						print3a(bayItem,false);	
						print3a(bay);	
					}
				}
			}

			bayTbl.clear();
			return totalBayTot;
		}    	    	
		public String toString() {
			return key;
		}
	}

}
