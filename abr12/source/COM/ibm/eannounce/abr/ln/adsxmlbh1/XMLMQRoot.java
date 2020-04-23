// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008,2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;
/**********************************************************************************
* Used for checking entities and structure queued by DQ abrs
*
*/
// XMLMQRoot.java,v
// $Log: XMLMQRoot.java,v $
// Revision 1.1  2015/02/04 14:55:49  wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.40  2015/01/26 15:51:22  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.39  2014/01/07 13:04:24  guobin
// update debug level for cache log
//
// Revision 1.38  2013/12/03 13:23:16  guobin
// fix model group may have null point exception .
//
// Revision 1.37  2013/11/12 16:03:17  guobin
// delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.36  2013/10/24 09:39:50  guobin
//  IN4396940 - fix the locate xsd files ,using inputstream instead of url in  xsd parse method.
//
// Revision 1.35  2013/10/18 02:08:59  guobin
// IN4396940 - While running the model ADS ABR - its encountering the following error due to a downlevel XSD.  Include XSD files into abr.jar
//
// Revision 1.34  2013/08/16 05:27:14  guobin
// fix end of service part 2 dependent on catlgor BHALM00193159
//
// Revision 1.33  2013/05/29 12:30:03  guobin
// EACM XSD validation additional update set ADSABRSTATUS prefix on properites file.
//
// Revision 1.32  2013/05/29 08:42:00  guobin
// EACM XSD validation RCQ240647 , add validate method to do xml validation
//
// Revision 1.31  2013/03/29 06:51:34  wangyulo
// support RESEND from CACHE and support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
// Revision 1.30  2013/02/06 12:38:02  guobin
// RTC defect875877 - PUBTO values in TMF_UPDATE XML incorrect - BHCQ 157335 and add debug level output to rpt.
//
// Revision 1.29  2012/11/27 06:40:30  wangyulo
// fix the Defect 846153-- Cache load of REFOFER failed on LPAR5
//
// Revision 1.28  2012/10/22 14:33:41  guobin
// Build request - for defect 820634  update activity ='W' for withdraw offering.- spec BH FS ABR Catalog DB Compatibility Gen 20121011.doc
//
// Revision 1.27  2012/09/30 07:05:43  guobin
// fix defect 799135 ( RFR data now flowing to BH prod)
//
// Revision 1.26  2012/08/14 09:37:20  wangyulo
// Fixing DTSOFMSG of 9999-12-31 in cache load
//
// Revision 1.25  2012/08/02 13:46:47  wangyulo
// fix the defect 770704- BH Defect BHALM109267 - correction to SWPRODSTRUCT for old data
//
// Revision 1.24  2012/02/28 08:52:42  guobin
// [Work Item 655030] PCR-1 separate new VE's for Version 0.5 from V1.0
//
// Revision 1.23  2011/12/23 05:11:06  guobin
// Remove the adjustment of T2 by 30 seconds for defect 623425. According to the doc BH FS ABR Data Transformation System Feed 20111214.doc
//
// Revision 1.22  2011/12/20 12:53:18  guobin
// Changes for a New country offering and CACHE update
//
// Revision 1.21  2011/11/10 07:25:39  guobin
// Fix Defect 598849 Invalid countrylist for PRODSTRUCT with no AVAIL. VE  PRODSTRUCT2 don't need to check Planed AVAIL
//
// Revision 1.20  2011/10/31 14:09:51  guobin
// support  0.5 XML using the old MQ CID
//
// Revision 1.19  2011/10/26 08:04:15  guobin
//  Final support for old data CQ 67890  Changed to handle offerings that have an AVAIL left in Draft where the data is older than 2010-03-01
//
// Revision 1.18  2011/10/17 13:44:50  guobin
// Support both 0.5 and 1.0 XML together
//
// Revision 1.17  2011/09/27 02:05:40  guobin
// check the return value of getEntitylistforDiff().  It may return null if there is change for request Planed Avail.
//
// Revision 1.16  2011/07/15 14:41:39  guobin
// change the parameter of PutXMLCache method .
//
// Revision 1.15  2011/06/27 19:28:50  wendy
// adslseo mergelists needs mapping from both extracts
//
// Revision 1.7  2010/01/08 12:43:18  wendy
// cvs failure again
//
// Revision 1.5  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.4  2008/05/03 23:29:32  wendy
// Changed to support generation of large XML files
//
// Revision 1.3  2008/05/02 19:05:19  wendy
// Check for null xml before notify()
//
// Revision 1.2  2008/05/01 12:07:29  wendy
// Allow control of notification
//
// Revision 1.1  2008/04/29 14:30:47  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQRoot extends XMLMQAdapter
{
    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
    	if(abr.isSystemResendCache() && abr.isSystemResendCacheExist()){
    		processSystemResendCached(abr, rootEntity);
    	}else{
    		processThis(abr, profileT1, profileT2, rootEntity, true);
    	}

    }

	/**
	 * @param abr
	 * @param rootEntity
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void processSystemResendCached(ADSABRSTATUS abr,
			EntityItem rootEntity) throws SQLException, MiddlewareException {
		long lastTime =System.currentTimeMillis();
		long runTime = 0;
		String key10 = "10";
		Hashtable VersionmqVct = null;
		VersionmqVct = getMQPropertiesVN(rootEntity, abr);

		Vector mq10 = (Vector) VersionmqVct.get(key10);
		String xml10MQ = abr.getSystemResendCacheXml();
		if ( xml10MQ != null && mq10 != null) {
			// RQK change to pass mqVct
			abr.addDebug("SystemResendCached generate xml from the cache table");
			abr.notify(this, rootEntity.getKey(), xml10MQ, mq10);

			//For the reconciliation Reports
			Vector EXTXMLFEEDVct = (Vector)VersionmqVct.get(KEY_SETUPArry);
			abr.setExtxmlfeedVct(EXTXMLFEEDVct);
			runTime = System.currentTimeMillis();
			abr.addDebugComment(D.EBUG_DETAIL, " Time for XML MQ diff: " + Stopwatch.format(runTime - lastTime));
		}else{
			abr.addXMLGenMsg("NO_CHANGES_FND", rootEntity.getKey());
		}
	}

    /**********************************
    * create xml and write to queue if notifynow=true
    */
    protected void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity,
    	boolean notifyNow)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		long lastTime =System.currentTimeMillis();
		long runTime = 0;
	    String m_strEpoch = "1980-01-01-00.00.00.000000";
	    String DTS2 = profileT2.getEffOn();
		// do pull at current time so proper meta gets cached!!!
		// some meta is post 1980 so VE gets bad info
		// do pull at time2 (toDate)
		// case 1 abr.isXMLCACHE() Initialization Cache. generate full XML(1.0)
		// case 2 triggered ADS XML, after call getMQPropertiesFN, there are mqVct
		//             1 only 0.5 then generate full XML, but not put into Cache
		//             2 only 1.0 then generate full XML put into Cache, than generate delta xml send to MQ.
		//             3 Both 0.5 and 1.0 then process 1 and 2

		//  step 1. must extract T2, T1. need to check whether has changed attribute.
		//  step 2. extract T1, if isXMLCACHE(), then T1 =1980...

		boolean requestV05 = false;
		boolean requestV10 = false;
		boolean request = false;
		boolean onlycache = false;
		String key05 = "05";
		String key10 = "10";
		XMLMQ mqAbr05 = null;
		String vename = "dummy";
		String vename2 = "dummy";
		// not call getMQPropertiesVN for the isXMLCACHE
		Hashtable VersionmqVct = null;
		if (!abr.isXMLCACHE()){
			VersionmqVct = getMQPropertiesVN(rootEntity, abr);
			requestV05 = VersionmqVct.containsKey(key05);
			requestV10 = VersionmqVct.containsKey(key10);
		}
		String statusAttr = getStatusAttr();
		String statusFlag = abr.getAttributeFlagEnabledValue(rootEntity, statusAttr);
	    // check whether have request version number to sent data to MQ
	    // BH 0.5 Release, the Data Quality ABR will NOT queue this ABR if the Status (STATUS) = Ready for Review since only Final data needs to be sent according to BH System Feed20110914.doc
	    // For Release 0.5 only, a temporary filter needs to be supported. This filter should exclude SVCMO
		if (!abr.isXMLCACHE()){
			if (requestV05 || requestV10){
				if (requestV05){
					//TODO Resend RFR
					if(abr.isSystemResendRFR() || abr.isSystemResendCache()){
						requestV05 = false;
						abr.addOutput("Warning: For Version 0.5, Final System RESEND RFR and RESEND CACHE is not support "+rootEntity.getKey());
					}else if ("0020".equals(statusFlag)){
							//BH 0.5 Release, the Data Quality ABR will NOT queue this ABR if the Status (STATUS) = Ready for Review since only Final data needs to be sent according to BH System Feed20110914.doc
							if (checkSVCMOD(rootEntity) ){
			                    //For Release 0.5 only, a temporary filter needs to be supported. This filter should exclude SVCMOD
								requestV05 = false;
								if (!requestV10){
									abr.addError("Error: exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) "+rootEntity.getKey() + " But it still update cache with full XML Version.");
									onlycache = true;
									request = true;
									profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
								}else {
									abr.addOutput("Warning: For Release0.5 exclude SVCMOD where SVCMODCATG = 'Productized Service' (SCSC0004) "+rootEntity.getKey());
									abr.addMSGLOGReason("Warning for V0.5: exclude SVCMODCATG = 'Productized Service' (SCSC0004)");
								}
							}else{
								request = true;
							}
					} else {
						requestV05 = false;
						if (!requestV10){
							onlycache = true;
							request = true;
							profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
							abr.addError("Error: Status is not Final, for Version 0.5 only Final data needs to be sent. "+rootEntity.getKey() + " But it still update cache with full XML Version.");
						}else{
							abr.addOutput("Warning: Status is not Final, for Version 0.5 only Final data needs to be sent. "+rootEntity.getKey());
							abr.addMSGLOGReason("Warning for V0.5 Status is not Final.");
						}
					}

				}
			    if (requestV10){
			    	request = true;
			    }
			}else {
			    //Doc :	Apply the applicable filters and if the data is valid for the filters, then continue. Otherwise there is no need to generate XML for this request
				onlycache = true;
			    request = true;
			    profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
				abr.addError("Error:  " + rootEntity.getKey() + " not valid for the filter(EXTXMLFEED), can not find request Version and Mod! but it still update cache with full XML Version");
			}
		}else{
//			 If runing Cache Initialize, then don't need to consider country filter( not need to consider Version 05,10)
			request = true;
			abr.addDebug("Running in IDL Initialize Cache!");
		}
		//TODO  if onlly cache 0.5 then get 0.5 VE for model lseo and svcmod, don't need to extract for VE 1.0
		if (!abr.isXMLCACHE() && requestV05 && !requestV10){
			String versionKey = rootEntity.getEntityType() + key05;
			mqAbr05 = getXMLMQ(abr, versionKey);
			if (mqAbr05 != null){
				vename = mqAbr05.getVeName();
				vename2 = "dummy";
				abr.addDebug("only generate xml for 0.5, the VE name is :"  + vename);
			}else{
				request = false;
			}
		} else{
			 vename = getVeName();
			 vename2 = getVeName2();
		}
	    Hashtable hshMapT2B = null;
		EntityList listT2 = abr.getEntityListForDiff(profileT2, vename, rootEntity);
		if (!request){
			abr.addError("Error: Can not find request version, please check EXTXMLFEED.",rootEntity);
		}
		if (listT2 != null && request){
			if (!vename2.equals("dummy")) {
				EntityList listT2B = null;
				if (vename2.equals("ADSPRODSTRUCT2")||vename2.equals("ADSSWPRODSTRUCT2")){
					 EntityGroup modelGrp = listT2.getEntityGroup("MODEL");
					 if (modelGrp != null){
						 EntityItem eiArray[] = modelGrp.getEntityItemsAsArray();
						 EntityItem modelitem = eiArray[0];
						 if (modelitem != null){
						     listT2B = abr.getEntityListForDiff(profileT2, vename2,modelitem);		
						 }
					 }else {
						 abr.addError("there is no modelitem !");
					 }
					 
				}else {
			         listT2B = abr.getEntityListForDiff(profileT2, vename2,rootEntity);
				}
				if(listT2B!= null){
					if (vename2.equals("ADSLSEO2")) {
						// get all steps for this VE
						hshMapT2B =	((ExtractActionItem)listT2B.getParentActionItem()).generateVESteps(abr.getDB(), profileT2, rootEntity.getEntityType());
					}
					if (vename2.equals("ADSPRODSTRUCT2")||vename2.equals("ADSSWPRODSTRUCT2")) {
						// get all steps for this VE
							hshMapT2B = ((ExtractActionItem)listT2B.getParentActionItem()).generateVESteps(abr.getDB(), profileT2,"MODEL");
					}
				}else{
					abr.addDebug("After extract EntityList at T2, the return value is null");
					return;
				}
				mergeLists(abr,listT2,listT2B);
				
			}

			// do pull at time1 (fromDate)
			EntityList listT1 = abr.getEntityListForDiff(profileT1, vename, rootEntity);
			if (!vename2.equals("dummy")) {
				EntityList listT1B = null;
				if (vename2.equals("ADSPRODSTRUCT2")||vename2.equals("ADSSWPRODSTRUCT2")){

					 EntityGroup modelGrp = listT1.getEntityGroup("MODEL");
					 EntityItem eiArray[] = modelGrp.getEntityItemsAsArray();
					 if (eiArray != null && eiArray.length >0){
					     EntityItem modelitem = eiArray[0];
					     listT1B = abr.getEntityListForDiff(profileT1, vename2,modelitem);
						 mergeLists(abr,listT1,listT1B);
					 }
				}else{
				     listT1B = abr.getEntityListForDiff(profileT1, vename2,rootEntity);
				     mergeLists(abr,listT1,listT1B);
				}
			}
			runTime = System.currentTimeMillis();
			abr.addDebugComment(D.EBUG_DETAIL, "Time for both pulls: "+Stopwatch.format(runTime-lastTime));
			lastTime=runTime;
			// get VE steps for later flattening the VE
			Hashtable hshMap =
				((ExtractActionItem)listT2.getParentActionItem()).generateVESteps(abr.getDB(), profileT2,
					rootEntity.getEntityType());
			if (vename2.equals("ADSLSEO2")) {
				/*hshMap.put("1MODELWWSEOU","Hi");
				hshMap.put("1MODELU","Hi");
				hshMap.put("1WWSEOU","Hi");
				//Add ADSLSEO2 for speicbid LSEO
				hshMap.put("2MODELAVAILD","Hi");
				hshMap.put("2AVAILD","Hi");
				hshMap.put("3AVAILANNAD","Hi");
				hshMap.put("3ANNOUNCEMENTD","Hi");
				hshMap.put("3AVAILSLEORGAD","Hi");
				hshMap.put("3SLEORGNPLNTCODED","Hi");
				hshMap.put("2MDLCATLGORD","Hi");
				hshMap.put("2CATLGORD","Hi");

				hshMap.put("2MODTAXRELEVANCED","Hi");
				hshMap.put("2TAXCATGD","Hi");
				hshMap.put("2MODELTAXGRPD","Hi");
				hshMap.put("2TAXGRPD","Hi");

				hshMap.put("3TAXCATGSLEORGAD","Hi");
				hshMap.put("3TAXGRPSLEORGAD","Hi");
				*/
				// merge all steps
				hshMap.putAll(hshMapT2B);
				hshMapT2B.clear();
				}
			if (vename2.equals("ADSPRODSTRUCT2")) {
				hshMap.put("0FEATUREU","Hi");
				hshMap.put("0MODELD","Hi");
				hshMap.putAll(hshMapT2B);
				hshMapT2B.clear();
			}
			if (vename2.equals("ADSSWPRODSTRUCT2")) {
				hshMap.put("0SWFEATUREU","Hi");
				hshMap.put("0MODELD","Hi");
				hshMap.putAll(hshMapT2B);
				hshMapT2B.clear();
			}
//			if (vename.equals("ADSSWPRODSTRUCT")){
//				hshMap.put("0SWFEATUREU","Hi");
//				hshMap.put("0MODELD","Hi");
//			}
			// group all changes by entitytype, except for root entity
			Hashtable diffTbl = new Hashtable();
			Hashtable diffTb2 = new Hashtable();
			Vector diffVct = new Vector();
			boolean chgsFnd = false;
            boolean reusable = false;
            DiffVE diff = null;
            EntityList listT1full = null;

            //If a delta version is required, then extract data for T1 as described earlier in the document.
            //If there is an <AVAILABILITYELEMENT> where
            //<AVAILABILITYACTION> is Update and the
            //<COUNTRY_FC> is new (added), then send a Full XML
            //instead of a delta with <AVAILABILITYACTION> set to Add.
//            if (!onlycache && !abr.isXMLCACHE() && hasNewCountry(abr,listT1,listT2)){
//            	reusable = true;
//				profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
//			    listT1full = abr.getEntityListForDiff(profileT1, vename, rootEntity);
//			    diff = new DiffVE(listT1full, listT2, hshMap);
//				diff.setCheckAllNLS(true);
//				abr.addDebug("hshMapfull: "+hshMap);
//				abr.addDebugComment(D.EBUG_DETAIL, "time1full flattened VE: " + diff.getPriorDiffVE());
//				abr.addDebugComment(D.EBUG_DETAIL, "time2full flattened VE: "+diff.getCurrentDiffVE());
//
//				// merge time1 and time2 flattened VEs into one with adds and deletes marked
//				diffVct = diff.diffVE();
//				abr.addDebugComment(D.EBUG_DETAIL, " setdiffTb1 info:\n" + diff.getDebug());
//			    setdiffTb1(vename, diffTbl, reusable, hshMap, listT1full, listT2, rootEntity, diffVct, abr, profileT1);
//			    chgsFnd = true;
//            } else {
//				 flatten both VEs using the VE steps
		    diff = new DiffVE(listT1, listT2, hshMap);
			diff.setCheckAllNLS(true);
			abr.addDebug("hshMap: "+hshMap);
			abr.addDebugComment(D.EBUG_DETAIL, "time1 flattened VE: "+diff.getPriorDiffVE());
			abr.addDebugComment(D.EBUG_DETAIL, "time2 flattened VE: "+diff.getCurrentDiffVE());

			// merge time1 and time2 flattened VEs into one with adds and deletes marked
		    diffVct = diff.diffVE();
		    abr.addDebugComment(D.EBUG_SPEW, " diffVE info:\n"+diff.getDebug());
		    abr.addDebugComment(D.EBUG_SPEW, " diffVE flattened VE: "+diffVct);

			runTime = System.currentTimeMillis();
			abr.addDebugComment(D.EBUG_DETAIL, " Time for diff: "+Stopwatch.format(runTime-lastTime));
			lastTime=runTime;
			for (int x=0; x<diffVct.size(); x++){
				DiffEntity de = (DiffEntity)diffVct.elementAt(x);
				// keep track of any changes
				chgsFnd = chgsFnd || de.isChanged();
			}
			String xmlCache = null;
			if (chgsFnd && ! abr.isPeriodicABR()){
				if (m_strEpoch.equals(profileT1.getValOn())) {
					reusable = true;
					abr.addDebugComment(D.EBUG_DETAIL, "Reuse T1 Entitylist!");
				}
				//abr.isXMLCACHE() Initialization Cache. generate full XML(1.0)
				if (abr.isXMLCACHE() || onlycache ) {
					if (diffTbl.size()<=0)
						setdiffTb1(vename, diffTbl, reusable, hshMap, listT1full,listT2, rootEntity,
							diffVct, abr, profileT1);
					//		    	create XML for cache

					//				case 1. initalization Cache. generate full XML 1.0 put into Cache
					xmlCache = generateMutilXML(abr, this, diffTbl, reusable, null);


                    //new added
					boolean ifpass = false;
					String entitytype = rootEntity.getEntityType();
					String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
					if (ifNeed != null && "YES".equals(ifNeed.toUpperCase())) {
					   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
					   if (xsdfile != null && "NONE".equals(xsdfile)) {
					    	abr.addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
					    } else {
					    	long rtm = System.currentTimeMillis();
					    	ifpass = validatexml(abr,xsdfile,xmlCache);
					    	long ltm = System.currentTimeMillis();
							abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
					    	if (ifpass) {
					    		abr.addDebug("the xml for "+entitytype+" passed the validation");
					    	}
					    }
					} else {
						abr.addOutput("the xml for "+entitytype+" doesn't need to be validated");
						ifpass = true;
					}

					//new added end

					//add flag(new added)
					if (xmlCache != null && ifpass) {

						abr.putXMLIDLCache(listT2, xmlCache, DTS2);
					}
					runTime = System.currentTimeMillis();
					abr.addDebugComment(D.EBUG_DETAIL, " Time for Initialization Cache XML Catch diff: "
						+ Stopwatch.format(runTime - lastTime));
					lastTime = runTime;
				} else {
					// case 2 triggered ADS XML, after call getMQPropertiesFN, there are mqVct
					//             1 only 0.5 then generate full XML, but not put into Cache
					//             2 only 1.0 then generate full XML put into Cache, than generate delta xml send to MQ.
					//             3 Both 0.5 and 1.0 then process 1 and 2
					String xml05MQ = null;
					String xml10MQ = null;
					String T1DTS = profileT1.getEffOn();
					if (requestV10) {
						//2 only 1.0 then generate full XML put into Cache, than generate delta xml send to MQ.
						if (diffTbl.size()<=0)
							setdiffTb1(vename, diffTbl, reusable, hshMap, listT1full, listT2, rootEntity,
								diffVct, abr, profileT1);
						//generate full 1.0 XML put into Cache
						xmlCache = generateMutilXML(abr,this,diffTbl, reusable, null);


//						new added
						boolean ifpass = false;
						String entitytype = rootEntity.getEntityType();
						String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
						if (ifNeed != null && "YES".equals(ifNeed.toUpperCase())) {
						   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
						    if (xsdfile!=null && "NONE".equals(xsdfile)) {
						    	abr.addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
						    } else {
						    	long rtm = System.currentTimeMillis();
						    	ifpass = validatexml(abr,xsdfile,xmlCache);
						    	long ltm = System.currentTimeMillis();
								abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
						    	if (ifpass) {
						    		abr.addDebug("the xml for "+entitytype+" passed the validation");
						    	}
						    }
						} else {
							abr.addOutput("the xml for "+entitytype+" doesn't need to be validated");
							ifpass = true;
						}

						//new added end


						//add flag(new added)
						if (xmlCache != null && ifpass) {
							abr.putXMLIDLCache(listT2, xmlCache, DTS2);
						}
						if (reusable) {
							xml10MQ = xmlCache;
						} else {
							profileT1.setValOnEffOn(T1DTS,T1DTS);							
							for (int x = 0; x < diffVct.size(); x++) {
								DiffEntity de = (DiffEntity) diffVct.elementAt(x);
								// keep track of any changes
								// abr.addDebug(" de.isChanged(): "+de.isChanged()+"
								// "+de.toString());

								// must be able to find up and down links from a
								// diffentity
								diffTb2.put(de.getKey(), de);

								String type = de.getEntityType();
								if (de.isRoot()) {
									type = "ROOT";
								}
								Vector vct = (Vector) diffTb2.get(type);
								if (vct == null) {
									vct = new Vector();
									diffTb2.put(type, vct);
								}
								vct.add(de);
							}
							// make sure there is a vector for each entitygroup in
							// the extract
							for (int i = 0; i < listT2.getEntityGroupCount(); i++) {
								String type = listT2.getEntityGroup(i).getEntityType();
								Vector vct = (Vector) diffTb2.get(type);
								if (vct == null) {
									vct = new Vector();
									diffTb2.put(type, vct);
								}
							}
							// generate Delta XML , and send to MQ
							xml10MQ = generateXML(abr, diffTb2);

						}
						Vector mq10 = (Vector) VersionmqVct.get(key10);
						//add flag(new added)
						if (notifyNow && xml10MQ != null && mq10 != null && ifpass) {
							// RQK change to pass mqVct
							abr.notify(this, rootEntity.getKey(), xml10MQ, mq10);

						}
					}
					if (requestV05) {
						if (diffTbl.size()<=0)
							setdiffTb1(vename, diffTbl, reusable, hshMap, listT1full, listT2, rootEntity, diffVct, abr, profileT1);
						//generate full 0.5 XML. 0.5 always full.
						String versionKey = rootEntity.getEntityType() + key05;
						if (mqAbr05 == null) {
							mqAbr05 = getXMLMQ(abr, versionKey);
						}
						xml05MQ = generateMutilXML(abr, mqAbr05, diffTbl, reusable, versionKey);
						Vector mq05 = (Vector) VersionmqVct.get(key05);
						if (notifyNow && xml05MQ != null && mq05 != null) {
							//RQK change to pass mqVct
							abr.notify(mqAbr05, rootEntity.getKey(), xml05MQ, mq05);
						}
					}
					Vector EXTXMLFEEDVct = (Vector)VersionmqVct.get(KEY_SETUPArry);
					abr.setExtxmlfeedVct(EXTXMLFEEDVct);
					runTime = System.currentTimeMillis();
					abr.addDebugComment(D.EBUG_DETAIL, " Time for XML MQ diff: " + Stopwatch.format(runTime - lastTime));

				}
			} else {
				//NO_CHANGES_FND=No Changes found for {0}
				abr.addXMLGenMsg("NO_CHANGES_FND", rootEntity.getKey());
			}

			// release memory
//			if (listT1full != null)
//			listT1full.dereference();
			if (listT1 != null){
				listT1.dereference();
				listT1 = null;
			}
			
			if (listT2 != null){
				listT2.dereference();
				listT2 = null;
			}
			
			hshMap.clear();
			hshMap = null;
			diffVct.clear();
			diffVct = null;
			if (diff != null)
			diff.dereference();
			diff = null;
			
			for (Enumeration eNum = diffTbl.elements(); eNum.hasMoreElements();)  {
				Object obj = eNum.nextElement();
				if(obj instanceof Vector){
					((Vector)obj).clear();
				}
			}
			diffTbl.clear();
			diffTbl = null;
			for (Enumeration eNum = diffTb2.elements(); eNum.hasMoreElements();)  {
				Object obj = eNum.nextElement();
				if(obj instanceof Vector){
					((Vector)obj).clear();
				}
			}
			diffTb2.clear();
			diffTb2 = null;
		} else{
			abr.addDebug("After extract EntityList at T2, the return value " + (listT2 == null?" is null":" is not null, but can not find the request version."));
		}

    }


    /**********************************
     * use dom4j validate xml file(new added)
     */
    private boolean validatexml(ADSABRSTATUS abr, String xsdfile, String xmlfile) {

    	String packagePath ="/COM/ibm/eannounce/abr/sg/adsxmlbh1";
	    boolean ifpass = true;
        try {
            XMLErrorHandler errorHandler = new XMLErrorHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            Document xmldoc = DocumentHelper.parseText(xmlfile);
            //new added(2013-10-11)
            //abr.addDebug("validatexml begin:");
            xsdfile =packagePath + xsdfile.substring(xsdfile.indexOf("/"));
            InputStream inputStream=this.getClass().getResourceAsStream(xsdfile);
//            abr.addDebug("validatexml inputStream is" + (inputStream!=null?" not null.":" is null"));
            abr.addDebug("validatexml xsdfile="+ xsdfile);
            
//            URL url = this.getClass().getResource(xsdfile);
//            
//            File file = new File(url.getPath());
//            abr.addDebug("validatexml url="+ url);
//            abr.addDebug("validatexml url.getPath="+ url.getPath());
            
            //new added end
            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            //parser.setProperty(
                //    "http://java.sun.com/xml/jaxp/properties/schemaSource",
                //    "file:" + xsdfile);
          //new added(2013-10-11)
            parser.setProperty(
                             "http://java.sun.com/xml/jaxp/properties/schemaSource",
                             inputStream);
            //new added end
            SAXValidator validator = new SAXValidator(parser.getXMLReader());
            validator.setErrorHandler(errorHandler);
            validator.validate(xmldoc);

            if (errorHandler.getErrors().hasContent()) {
                String st = errorHandler.getErrors().asXML();
                abr.addError("the validation for this xml failed because: "+st);
            	abr.addMSGLOGReason("Failed validate :" + st);
                ifpass = false;
            } else {
            	abr.addOutput("the validation for this xml successfully");
            }
        } catch (Exception ex) {
        	abr.addError("Error:the validation for xml failed,because:"+ex.getMessage());
        	abr.addMSGLOGReason("Failed validate :" + ex.getMessage());
            ifpass = false;
        }
        return ifpass;
    }

    // new added end


    /**
     * get XMLMQ for xml 0.5 lseo model and svcmod
     * @param abr
     * @param versionKey
     * @return
     * @throws IOException
     */
    private XMLMQ getXMLMQ (ADSABRSTATUS abr, String versionKey) throws IOException{
    	String clsname = (String) ADSABRSTATUS.ABR_VERSION_TBL.get(versionKey);
    	XMLMQ mqAbr = null;
		if (clsname != null) {
			try {
			    mqAbr = (XMLMQ) Class.forName(clsname).newInstance();

			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new IOException("Can not instance " + clsname + " class!");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new IOException("Can not access " + clsname + " class!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new IOException("Can not find " + clsname + " class!");
			}

		} else {
			 abr.addError("Generated XML does not support version :"+versionKey);
		}
		return mqAbr;
    }
    /**
     * 	 * get diffTb1 , switch  T1 to 1980... , extract dif
     * @param diffTbl
     * @param reusable
     * @param hshMap
     * @param listT2
     * @param rootEntity
     * @param diffVct
     * @param abr
     * @param profileT1
     * @throws MiddlewareException
     * @throws ParserConfigurationException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException
     * @throws IOException
     * @throws SQLException
     */
	private void setdiffTb1(String vename, Hashtable diffTbl , boolean reusable, Hashtable hshMap, EntityList listT1full, EntityList listT2, EntityItem rootEntity, Vector diffVct, ADSABRSTATUS abr, Profile profileT1)
		throws MiddlewareException, ParserConfigurationException, EANBusinessRuleException,
		MiddlewareShutdownInProgressException, IOException, SQLException {
		String m_strEpoch = "1980-01-01-00.00.00.000000";
		Vector diffVctfull = new Vector();
		DiffVE diffvefull = null;
		if (reusable) {
			diffVctfull = diffVct;
		} else {
			profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
			listT1full = abr.getEntityListForDiff(profileT1, vename, rootEntity);
			diffvefull = new DiffVE(listT1full, listT2, hshMap);
			diffvefull.setCheckAllNLS(true);
//			abr.addDebug("hshMapfull: "+hshMap);
//			abr.addDebug("time1full flattened VE: " + diffvefull.getPriorDiffVE());
//			abr.addDebug("time2full flattened VE: "+diffvefull.getCurrentDiffVE());

			// merge time1 and time2 flattened VEs into one with adds and deletes marked
			diffVctfull = diffvefull.diffVE();
//			abr.addDebug(" setdiffTb1 info:\n" + diffvefull.getDebug());

		}

		// group all changes by entitytype, except for root entity
		for (int x = 0; x < diffVctfull.size(); x++) {
			DiffEntity de = (DiffEntity) diffVctfull.elementAt(x);
			//abr.addDebug(" de.isChanged(): "+de.isChanged()+" "+de.toString());

			// must be able to find up and down links from a diffentity
			diffTbl.put(de.getKey(), de);

			String type = de.getEntityType();
			if (de.isRoot()) {
				type = "ROOT";
			}
			Vector vct = (Vector) diffTbl.get(type);
			if (vct == null) {
				vct = new Vector();
				diffTbl.put(type, vct);
			}
			vct.add(de);
		}
		for (int i = 0; i < listT2.getEntityGroupCount(); i++) {
			String type = listT2.getEntityGroup(i).getEntityType();
			Vector vct = (Vector) diffTbl.get(type);
			if (vct == null) {
				vct = new Vector();
				diffTbl.put(type, vct);
			}
		}
	}
    /***********************************************
    *  Get the xml
    *
    *@return java.lang.String
    */
    protected String generateXML(ADSABRSTATUS abr, Hashtable diffTbl)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException
    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
        //update from Document to org.w3c.doc.Document(new added)
		org.w3c.dom.Document document = builder.newDocument();  // Create
		XMLElem xmlMap = getXMLMap(); // get list of xml elements

		Element root = null;

		StringBuffer debugSb = new StringBuffer();
		xmlMap.addElements(abr.getDB(),diffTbl, document, root,null,debugSb);

		abr.addDebugComment(D.EBUG_DETAIL, "GenXML debug: "+ADSABRSTATUS.NEWLINE 
				          + " debug log size="+ debugSb.toString().length() 
				          +  ADSABRSTATUS.NEWLINE + debugSb.toString());
		debugSb = null;
		//abr.addDebugComment(D.EBUG_DETAIL, "showMemory debug: " + ABRUtil.showMemory());
		String xml = abr.transformXML(this, document);
		//abr.addDebug("Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);

		return xml;
	}

    /***********************************************
	 *  Get the xml
	 *
	 *@return java.lang.String
	 */
	protected String generateMutilXML(ADSABRSTATUS abr, XMLMQ mqAbr, Hashtable diffTbl, boolean reusable, String versionKey)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException, ParserConfigurationException,
		java.rmi.RemoteException, COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, IOException, javax.xml.transform.TransformerException {
		String xml = null;
		if (versionKey==null){
		// if versionKey is null, then generate 1.0 xml report
			if (reusable) {
				xml = generateXML(abr, diffTbl);
			} else {
				XMLElem xmlMap = null;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
                //update from Document to org.w3c.doc.Document(new added)
				org.w3c.dom.Document document = builder.newDocument(); // Create
				xmlMap = getXMLMap();
				Element root = null;
				StringBuffer debugSb = new StringBuffer();
				xmlMap.addElements(abr.getDB(), diffTbl, document, root, null, debugSb);
				abr.addDebugComment(D.EBUG_SPEW, "GenCacheXML debug: " + ADSABRSTATUS.NEWLINE + debugSb.toString());
				xml = abr.transformCacheXML(this, document);
				abr.addDebugComment(D.EBUG_SPEW, "Generated Cache xml:" + ADSABRSTATUS.NEWLINE + xml + ADSABRSTATUS.NEWLINE);

			}
		} else{
		// else versionkey is not null, then generate 0.5 xml report
			XMLElem xmlMap = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			//update from Document to org.w3c.doc.Document(new added)
			org.w3c.dom.Document document = builder.newDocument(); // Create
			xmlMap = mqAbr.getXMLMap(); // get list of xml elements
			Element root = null;
			StringBuffer debugSb = new StringBuffer();
			xmlMap.addElements(abr.getDB(), diffTbl, document, root, null, debugSb);
			abr.addDebugComment(D.EBUG_DETAIL, "Generated debug infor: Version " + versionKey + ADSABRSTATUS.NEWLINE + debugSb.toString());
			xml = abr.transformXML(this, document);
			abr.addDebugComment(D.EBUG_DETAIL, "Generated Version " + versionKey +" xml:" + ADSABRSTATUS.NEWLINE + xml + ADSABRSTATUS.NEWLINE);
		}
		return xml;
	}
	/**
	 * For Release 0.5 only, a temporary filter needs to be supported. This filter should exclude SVCMOD
	 * where SVCMODSUBCATG = Productized Service (SCSC0004)
	 * @param rootEntity
	 * @return
	 */
    private boolean checkSVCMOD(EntityItem rootEntity) {
    	boolean isExcludeSVCMOD =false;
    	String attributevalue ="";
    	String entitytype = rootEntity.getEntityType();
    	if(entitytype.equals("SVCMOD")){
    		attributevalue = PokUtils.getAttributeFlagValue(rootEntity, "SVCMODSUBCATG");
    		if(attributevalue.equals("SCSC0004")){
    			isExcludeSVCMOD = true;
    		}
    	}
		return isExcludeSVCMOD;
	}
    /**
     * Delete following block code because this requirement has been deleted in rewrite doc ABR Data Transformation System Feed 20121210.doc After checking with Wayne on 2013/5/20, we decide to remove it.  
     * ABR Data Transformation System Feed 20111006.doc
     * 30 Final -new country for an offering and CACHE update If a downstream system is not interested
     * (i.e. not sent based on filter criteria) in an offering  based on the initial country that it is offered in,
     *  then that downstream system will not have the full XML when a country is added that the downstream system is interested in.
     *  This change will send full XML whenever a country is added to the XML <AVAILABILITYLIST>
     *  1 special case: PRODSTRUCT, if there is no AVAIL found for the PRODSTRUCT, then we wil send full xml than strictly accroding to Note from Dave 11/17/2011
     *  2 special case: 1. For LSEO If WWSEO.SPECBID = No (11457), then see the MODLE related AVAIL, if there is Country is added for MODEL related AVAIL, should I send full XML ? then we wil send full xml than strictly accroding to Note from Dave 11/17/2011
     * @param abr
     * @param rootEntity
     * @param table
     * @param availType
     * @return
     */
//    private boolean hasNewCountry(ADSABRSTATUS abr,  EntityList list1, EntityList list2){
//    	long lastTime =System.currentTimeMillis();
//		long runTime = 0;
//    	boolean hasNewCountry = false;
//    	String availType = CHEAT;
//    	EntityItem rootEntity  = list2.getParentEntityGroup().getEntityItem(0);
//    	if ("PRODSTRUCT".equals(rootEntity.getEntityType()))
//		    availType = "OOFAVAIL";
//		if ("SWPRODSTRUCT".equals(rootEntity.getEntityType()))
//		    availType = "SWPRODSTRUCTAVAIL";
//		if ("MODEL".equals(rootEntity.getEntityType()))
//		    availType = "MODELAVAIL";
//		if ("LSEO".equals(rootEntity.getEntityType()))
//		    availType = "LSEOAVAIL";
//		if ("SVCMOD".equals(rootEntity.getEntityType()))
//		    availType = "SVCMODAVAIL";
//		if ("MODELCONVERT".equals(rootEntity.getEntityType()))
//		    availType = "MODELCONVERTAVAIL";
//		if ("LSEOBUNDLE".equals(rootEntity.getEntityType()))
//			availType = "LSEOBUNDLEAVAIL";
//		if (availType.equals(CHEAT)){
//			return false;
//		}
//    	Vector plnAvlVct = getPlannedAvails(abr, list2, availType);
//    	Vector priorPlnAvlVct = getPlannedAvails(abr, list1, availType);
//       	//1 special case PRODSTRUCT, if there is no AVAIL found for the PRODSTRUCT, then we wil send full xml than strictly accroding to Note from Dave 11/17/2011
//    	if ("OOFAVAIL".equals(availType)){
//			if (plnAvlVct.size() <= 0){
//	    		return true;
//			}
//    	}
//    	// 2 special case: 1. For LSEO If WWSEO.SPECBID = No(11457), then see the MODLE related AVAIL, if there is Country is added for MODEL related AVAIL, should I send full XML ? then we wil send full xml than strictly accroding to Note from Dave 11/17/2011
//        if ("LSEOAVAIL".equals(availType)){
//        	if (isDerivefromLSEOMODEL(abr,list2)){
//        		availType = "MODELAVAIL";
//        		plnAvlVct = getPlannedAvails(abr, list2, availType);
//        		priorPlnAvlVct = getPlannedAvails(abr, list1, availType);
//        		abr.addDebugComment(D.EBUG_INFO,"hasNewCountry: LSEO SPECBID = NO, get MODELAVAIL related AVAIL vector size " + plnAvlVct.size());
//        	}
//        }
//        // 3 special case: 3. If LSEOBUNDLE.SPECBID is not equal to 11458 (Yes) , then derive from
//        // LSEOBUNDLE.AVAIL, else if is equal to 11458, then derive from LSEOBUNDLE.CountryList
//        if ("LSEOBUNDLEAVAIL".equals(availType)){
//        	if (isDerivefromLSEOBUNDLE(abr,rootEntity)){
//        	     Vector prevVct = new Vector();
//        	     Vector currVct = new Vector();
//    			//put all current country into currvSet.
//        	     currVct = getLSEOBUNDLECountry(abr, rootEntity);
//        	     EntityItem priorRootEntity  = list1.getParentEntityGroup().getEntityItem(0);
//        	     prevVct = getLSEOBUNDLECountry(abr, priorRootEntity);
//        	     for (int ii=0; ii < currVct.size(); ii++){
//        	    	 String ctryVal = (String)currVct.elementAt(ii);
//        				if (!prevVct.contains(ctryVal)) { // If AVAIL.COUNTRYLIST has
//        					hasNewCountry = true;
//        					break;								// a country added, set hasNewcountry true
//        				}
//        		 }// end of currset while(itr.hasNext())
//        	     return hasNewCountry;
//        	}
//        }
//        HashSet prevSet = new HashSet();
//		HashSet currSet = new HashSet();
//    	for (int ii = 0; ii < plnAvlVct.size(); ii++) {
//			  // else if one country in the countrylist has changed, update
//						// this row to update!
//					// get current set of countries
//    		EntityItem curritem = (EntityItem)plnAvlVct.elementAt(ii);
//			EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
//			abr.addDebugComment(D.EBUG_INFO,"Check hasNewCountry  for curr avail: fAtt "
//				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST"));
//			if (fAtt != null && fAtt.toString().length() > 0) {
//				// Get the selected Flag codes.
//				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
//				for (int i = 0; i < mfArray.length; i++) {
//					// get selection
//					if (mfArray[i].isSelected()) {
//						currSet.add(mfArray[i].getFlagCode());
//					} // metaflag is selected
//				}// end of flagcodes
//			}
//    	}
//
//    	for (int ii = 0; ii < priorPlnAvlVct.size(); ii++){
//    		EntityItem prioritem = (EntityItem)priorPlnAvlVct.elementAt(ii);
//			// get prev set of countries
//    		EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
//			abr.addDebugComment(D.EBUG_INFO,"Check hasNewCountry  for prev avail:  fAtt and prevanncodeAtt "
//				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") );
//			if (fAtt != null && fAtt.toString().length() > 0) {
//				// Get the selected Flag codes.
//				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
//				for (int i = 0; i < mfArray.length; i++) {
//					// get selection
//					if (mfArray[i].isSelected()) {
//						prevSet.add(mfArray[i].getFlagCode());
//					} // metaflag is selected
//				}// end of flagcodes
//			}
//    	}
//		// look for changes in country
//		Iterator itr = currSet.iterator();
//		while (itr.hasNext()) {
//			String ctryVal = (String) itr.next();
//			if (!prevSet.contains(ctryVal)) { // If AVAIL.COUNTRYLIST has
//				hasNewCountry = true;
//				break;								// a country added, set hasNewcountry true
//			}
//		}// end of currset while(itr.hasNext())
//    	runTime = System.currentTimeMillis();
//		abr.addDebugComment(D.EBUG_INFO, "Time for check isNewaddCountry: "+Stopwatch.format(runTime-lastTime));
//    	return hasNewCountry;
//    }
//	/**
//	 *
//	 * get planned avails - availtype cant be changed
//	 * @param abr
//	 * @param list1
//	 * @param list2
//	 * @param availType
//	 * @return
//	 */
//	private Vector getPlannedAvails(ADSABRSTATUS abr, EntityList list, String availType) {
//		Vector avlVct = new Vector(1);
//		Vector allVct = getAvailOfAvailType(list, availType);
//		abr.addDebugComment(D.EBUG_DETAIL, "XMLMQRoot.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
//			+ (allVct == null ? "null" : "" + allVct.size()) );
//		if (allVct == null) {
//			return avlVct;
//		}
//
//		// find those of specified type
//		for (int i = 0; i < allVct.size(); i++) {
//			EntityItem item = (EntityItem) allVct.elementAt(i);
//			abr.addDebugComment(D.EBUG_DETAIL, "XMLMQRoot.getPlannedAvails checking[" + i + "]:" + item.getKey() + " AVAILTYPE: "
//				+ PokUtils.getAttributeFlagValue(item, "AVAILTYPE") );
//			EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("AVAILTYPE");
//			if (fAtt != null && fAtt.isSelected("146")) {
//				avlVct.add(item);
//			}
//		}
//		return avlVct;
//	}
//	/**
//	 * availType: MODELAVAIL
//	 *            OOFAVAIL
//	 *            SWPRODSTRUCTAVAIL
//	 * get AVAIL of availType
//	 * @param itemArray
//	 * @param availType
//	 * @return
//	 */
//	private Vector getAvailOfAvailType(EntityList list2, String availType) {
//        Vector availVec = new Vector();
//		EntityGroup availGrop  = list2.getEntityGroup("AVAIL");
//		EntityItem itemArray[] = availGrop.getEntityItemsAsArray();
//        for (int j = 0; j < itemArray.length; j++) {
//			EntityItem availItem = itemArray[j];
//			Vector availTypeVect = availItem.getUpLink();
//			for (int i = 0; i < availTypeVect.size(); i++) {
//				EntityItem relator = (EntityItem) availTypeVect.elementAt(i);
//				if (relator != null && availType.equals(relator.getEntityType())) {
//					availVec.add(availItem);
//				}
//			}
//
//		}
//		return availVec;
//	}
//	/********************
//	 *   If WWSEO.SPECBID is not equal to 'No' (11457), then derive from MODEL.
//	 *  @param table
//	 *         Hashtable that contain all the entities.
//	 *   If WWSEO.SPECBID is not equal to 'No' (11457), then derive from
//	 *  from MODEL AVAIL countrylist attributes
//	 **/
//	private boolean isDerivefromLSEOMODEL(ADSABRSTATUS abr, EntityList list) {
//		boolean isfromLSEO = false;
//		EntityGroup availGrop  = list.getEntityGroup("WWSEO");
//		abr.addDebugComment(D.EBUG_DETAIL,"DerivefromLSEO looking for WWSEO.SPECBID. allVct.size:" + (availGrop == null ? "null" : "" + availGrop.getEntityItemsAsArray().length));
//		if (availGrop != null){
//			EntityItem itemArray[] = availGrop.getEntityItemsAsArray();
//	        for (int j = 0; j < itemArray.length; j++) {
//	        	EntityItem item = (EntityItem) itemArray[j];
//	        	abr.addDebugComment(D.EBUG_DETAIL,"DerivefromLSEO WWSEO checking[" + j + "]: current" + item.getKey()
//					+ " SPECBID: " + PokUtils.getAttributeValue(item, "SPECBID", ", ", CHEAT, false));
//	        	EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("SPECBID");
//				if (fAtt != null && !fAtt.isSelected("11457")) {
//					isfromLSEO = true;
//					break;
//				}
//
//	        }
//		}
//		return isfromLSEO;
//	}
//	/***************************************************************************
//	 * If LSEOBUNDLE.SPECBID is not equal to 11458 (Yes) , then derive from
//	 * LSEOBUNDLE.AVAIL, else if is equal to 11458, then derive from LSEOBUNDLE.CountryList
//	 *
//	 * @param table
//	 *            Hashtable that contain all the entities.
//	 * @param availtype
//	 *            AVAIL.AVAILTYPE
//	 * @param debugSb
//	 *            StringBuffer logo output.
//	 *
//	 */
//	private boolean isDerivefromLSEOBUNDLE(ADSABRSTATUS abr, EntityItem curritem) {
//		boolean isfromLSEO = false;
//		if (curritem != null) {
//			abr.addDebugComment(D.EBUG_DETAIL,"DerivefromLSEO" + curritem.getKey() + " SPECBID: "
//				+ PokUtils.getAttributeValue(curritem, "SPECBID", ", ", CHEAT, false));
//			EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("SPECBID");
//			if (fAtt != null && fAtt.isSelected("11458")) {
//				isfromLSEO = true;
//			}
//		}
//		return isfromLSEO;
//	}
//	/**
//	 * get the countrylist Vector from LSEOBUNDLE
//	 * @param abr
//	 * @param item
//	 * @return
//	 */
//	private Vector getLSEOBUNDLECountry(ADSABRSTATUS abr, EntityItem item){
//		Vector ctryVct = new Vector();
//		if (item != null){
//			EANFlagAttribute ctryAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
//			abr.addDebugComment(D.EBUG_DETAIL, "getLSEOBUNDLECountry lseobundle: ctryAtt "
//				+ PokUtils.getAttributeFlagValue(item, "COUNTRYLIST"));
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						ctryVct.add(ctryVal);
//					}
//				}
//			}
//		}
//		return ctryVct;
//	}
    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()    {
        return "$Revision: 1.1 $";
    }
}
