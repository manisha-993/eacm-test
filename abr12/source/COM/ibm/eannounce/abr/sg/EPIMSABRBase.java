// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**********************************************************************************
* EPIMSABRBase class EPIMS* classes will be derived from this for each entity type
* EPIMSABRSTATUS will launch the correct class
* From "SG FS ABR ePIMS InAndOut Bound 20071010.doc"
*
* This is a complete replacement for the current ePIMS Notification ABRs.
*
* There is additional complexity in this ABR since notifications should not be sent until
* the data quality is Final and the prerequisites are satisfied. For example, a Custom (special bid)
* LSEO can not be sent to ePIMS until after ePIMS has notified EACM that all of the Features in
* the LSEO have been Released.
*
* This ABR applies to entity types that may or may not require ePIMS notifications; however, they
* are part of the criteria for those that are sent to ePIMS. These entity types will have an additional
* attribute named 'ePIMS Status' (EPIMSSTATUS) with the following allowed values:
* 	'Not Ready' (EPNOT):  Default value.
* Since this is a new attribute, there is existing data for which there will not be a value. This ABR will assume a value of 'Not Ready' whenever there is no value for ePIMS Status.
* 	'Waiting' (EPWAIT):  Data that needs to be sent to ePIMS is waiting on a dependent item since ePIMS has not notified EACM that the dependent item has been Promoted/Released.
* 	'Promoted' (Promoted): means that the data has been promoted to MMLC and all data dependent on this item can flow except for Custom (Special Bid) SEOs.
* 	'Released' (Released): means that the data has been released from MMLC to down stream systems. Custom (Special Bid) SEOs wait for referenced data (e.g. LSEOBUNDLEs wait for LSEOs) to be Released before they are sent to ePIMS.
*
* The rest of allowed values are used for LSEOs and LSEOBUNDLEs
*
* 	'Sent' (Sent): means that the data was sent to ePIMS
* 	'Waiting Again' (EPWAITAGAIN): data that needs to be sent again to ePIMS is waiting on a dependent
* item since ePIMS has not notified EACM that the dependent item has been Promoted/Released. This happens
* when a Sales Org was added.
* 	'Sent Again' (EPSENTAGAIN): means that the data was sent to ePIMS again because data of interest
* to ePIMS was changed.
*
* A single ABR supports this functionality based on the Entity Type that queues this function utilizing
* AttributeCode EPIMSABRSTATUS of Type A.
*
*
*/
// EPIMSABRBase.java,v
// Revision 1.7  2009/08/09 01:31:41  wendy
// Dont need to hang onto properties file after load attr list
//
// Revision 1.6  2009/08/07 18:29:12  wendy
// Move AttributesOfInterest to properties file
//
// Revision 1.5  2008/04/08 12:26:45  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR WWPRT Notification 20080407.doc"
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.4  2008/01/30 19:39:15  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2007/11/30 22:23:19  wendy
// cleanup
//
// Revision 1.2  2007/11/28 22:58:07  wendy
// merged with WWPRT abr base class
//
// Revision 1.1  2007/11/16 19:32:55  nancy
// Init for GX EPIMs ABRs
//
//
public abstract class EPIMSABRBase
{
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final Hashtable EPIMS_TRANS_TBL;

    protected final static String EPIMSMQSERIES = "EPIMSMQSERIES";
    protected final static String WWPRTMQSERIES = "WWPRTMQSERIES";

    private String failedStr = "Failed";  // hang onto this so dont have to get over and over
    private String xmlgen = "Not Required";  // set to Success or Failed or not required

    protected static final String STATUS_FINAL = "0020";
	protected static final String SPECBID_YES = "11458";
    protected static final String SYSFEEDRESEND_YES = "Yes";
    protected static final String SYSFEEDRESEND_NO = "No";

    protected EPIMSWWPRTBASE epimsAbr = null;

/*
EPIMSSTATUS	EPNOT		Not Ready
EPIMSSTATUS	EPSENTAGAIN	Sent Again
EPIMSSTATUS	EPWAIT		Waiting
EPIMSSTATUS	EPWAITAGAIN	Waiting Again
EPIMSSTATUS	Promoted	Promoted
EPIMSSTATUS	Released	Released
EPIMSSTATUS	Sent		Sent
*/
	protected static final String NOT_READY="EPNOT";
	protected static final String SENT_AGAIN="EPSENTAGAIN";
	protected static final String WAITING="EPWAIT";
	protected static final String WAIT_AGAIN="EPWAITAGAIN";
	protected static final String PROMOTED="Promoted";
	protected static final String RELEASED="Released";
	protected static final String SENT="Sent";
    static {
/*
Current Value	Allowed Values
------------------------------------
Not Ready		Promoted or Released
Waiting			Promoted or Released
Promoted		Released
Released		None
*/
        EPIMS_TRANS_TBL = new Hashtable();
        EPIMS_TRANS_TBL.put(NOT_READY, "Promoted:Released");  // Not Ready	->	Promoted or Released
        EPIMS_TRANS_TBL.put(WAITING, "Promoted:Released");  // Waiting	->	Promoted or Released
        EPIMS_TRANS_TBL.put(PROMOTED, "Released");  // Promoted->Released
    }
    protected static final void loadAttrOfInterest(String propName,Hashtable attrTbl){
    	Properties epimsProperties = loadProperties(propName);
		for (Enumeration e = epimsProperties.propertyNames(); e.hasMoreElements();){
			String etype = (String)e.nextElement(); 
			String attrlist = (String)epimsProperties.get(etype);
			StringTokenizer st1 = new StringTokenizer(attrlist,",");
			Vector attrVct = new Vector();
			while (st1.hasMoreTokens()) {
				attrVct.add(st1.nextToken().trim());
			}
			String attrArray[] = new String[attrVct.size()];
			attrVct.copyInto(attrArray);
			attrTbl.put(etype,attrArray);
			attrVct.clear();
		}
    }
    private static final Properties loadProperties(String propName)
    {
    	Properties epimsProperties=null;
    	InputStream in = null;
    	try {
    		EPIMSABRBase objToGetClassLoader = new EPIMSLSEOBUNDLEABR();
    		in = objToGetClassLoader.getClass().getResourceAsStream(propName);

    		if (in!=null){
    			epimsProperties = new Properties();
    			epimsProperties.load(in);
    		}else{
    			System.err.println("EPIMSABRBase failure to loadProperties "+propName);
    		}
    	} catch (Exception x) {
    		System.err.println("EPIMSABRBase Unable to loadProperties "+propName+" "+ x);
    	}
    	finally {
    		if (in != null) {
    			try {
    				in.close();
    			} catch (java.io.IOException e) {
    				System.out.println("I/O Exception "+e.getMessage());
    			}
    		}
    	}
    	return epimsProperties;
    }
    protected PDGUtility pdgUtility = new PDGUtility();

    /**
     *  Execute ABR.
     *
     */
    public void execute_run(EPIMSWWPRTBASE epims) throws Exception
    {
        epimsAbr = epims;
        failedStr = epimsAbr.getBundle().getString("FAILED"); // "Failed"
        execute();
    }
    public String getXMLGenMsg() { return xmlgen; }

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
    */
    protected Vector getMQPropertiesFN() { return null;}

    /**********************************
    * get the name of the VE to use
    */
    protected abstract String getVeName();

    /**********************************
    * execute the derived class
    */
    protected abstract void execute() throws Exception;

    /**********************************
    * resend the msg and always reset the flag
    */
    protected void resendSystemFeed(EntityItem rootEntity, String statusFlag)
    	throws Exception
	{
		try{
			handleResend(rootEntity, statusFlag);
		}catch(Exception e) {
			throw e;
		}finally{
			epimsAbr.setFlagValue("SYSFEEDRESEND", SYSFEEDRESEND_NO); // reset the flag
		}
	}

   /**********************************
    * derived classes must implement their resend checks
    */
	protected abstract void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException;

    /**********************************
    * get xml object mapping
    */
    private Vector getXMLMap()
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		return getXMLMap(epimsAbr.isFirstFinal());
	}

    /**********************************
    * get xml object mapping
    */
    protected Vector getXMLMap(boolean isFirst) {
		return null;
	}

    /**********************************
    * add message to report output
    */
    protected void addOutput(String msg) { epimsAbr.addOutput(msg);}

    /**********************************
    * add debug info
    */
    protected void addDebug(String msg) { epimsAbr.addDebug(msg);}

    /**********************************
    * add error info and fail abr
    */
    protected void addError(String msg) { epimsAbr.addError(msg);}

    /**********************************
    * add error info and fail abr
    */
    protected void addError(String errCode, Object args[]) { epimsAbr.addError(errCode, args);}
    /**********************************
    * add error info and fail abr
    */
    protected void addWarning(String errCode, Object args[]) { epimsAbr.addWarning(errCode, args);}

    /**********************************
    * search for EP* items
    *@param sb StringBuffer with search criteria
    *@param strSai String with search action name
    *@param type String with search entity type
    * /
    protected EntityItem[] epSearch(StringBuffer sb, String strSai, String type)
		throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.eannounce.objects.SBRException
	{
		epimsAbr.getDB().debug(D.EBUG_SPEW,EPIMSWWPRTBASE.getShortClassName(getClass())+
			" searching for "+type+": " + sb.toString());
		addDebug("epSearch: searching for "+type+": " + sb.toString());

		EntityItem[] aei = pdgUtility.dynaSearch(epimsAbr.getDB(), epimsAbr.getProfile(),
			null, strSai, type, sb.toString());

		if (aei ==null || aei.length==0){
			addDebug("epSearch NO "+type+" found");
		}else{
			StringBuffer db = new StringBuffer();
			for (int i=0; i<aei.length; i++){
				db.append(aei[i].getKey()+" ");
			}
			if (aei.length >1){ // this cant really happen.. but check anyway
				throw new MiddlewareRequestException("Search action "+strSai+
					" returned more than one "+db);
			}

			addDebug("epSearch Search found "+db);
		}

		return aei;
	}

	/**********************************
	*	Update PRODSTRUCT.EPIMSSTATUS = epf.EPSTATUS
	*'Update' is different then 'Set'
	*The 'Update' is performed based on the current value of EPIMSSTATUS and the new value of EPSTATUS as follows:
	*Current Value	Allowed Values
	*Not Ready		Promoted or Released
	*Waiting		Promoted or Released
	*Promoted		Released
	*Released		None
	*
	* /
	protected void updateEPIMSSTATUS(String epstatus)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
		// EPSTATUS is a T not U but promoted or released are the same as flagcodes now
		String curval = epimsAbr.getAttributeFlagEnabledValue(rootEntity,"EPIMSSTATUS");
		String allowVal = (String)EPIMS_TRANS_TBL.get(curval);
		addDebug("updateEPIMSSTATUS curval "+curval+" allowVal "+allowVal);
		if (allowVal != null && allowVal.indexOf(epstatus)!=-1){
			setEPIMSSTATUS(epstatus);
		}
	}

	/**********************************
	*This is an assignment statement and sets a new value for EPIMSSTATUS if the new value
	*is different than the current value.
	* /
	protected void setEPIMSSTATUS(String status)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
	{
        epimsAbr.setFlagValue("EPIMSSTATUS", status);
	}
	/**********************************
	* Create a EPWAITINGQUEUE entity with these values
	* 	EPWAITET	Set = root entitytype
	* 	EPWAITEID	Set = root entityid
	* 	EPONET		Set = other.entitytype
	* 	EPONEID		Set = other.entityid
	* 	EPSTATUS	derived
	* /
	protected void addToQueue(EntityItem rootEntity, EntityItem onItem, EPWQGenerator epwqGen,
		String epstatus)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		addDebug("addToQueue entered root "+rootEntity.getKey()+" onitem: "+onItem.getKey()+
			" epstatus "+epstatus);
		epwqGen.reset();
		// add the attributes
		// EPWAITET     Set = root type
		epwqGen.addAttribute(EPWQGenerator.EPWAITET, rootEntity.getEntityType());
		// EPWAITEID    Set = root.entityid
		epwqGen.addAttribute(EPWQGenerator.EPWAITEID, ""+rootEntity.getEntityID());
		// EPONET       Set = 'other'
		epwqGen.addAttribute(EPWQGenerator.EPONET, onItem.getEntityType());
		// EPONEID      Set = other.entityid
		epwqGen.addAttribute(EPWQGenerator.EPONEID, ""+onItem.getEntityID());
		// EPSTATUS     Set = epstatus
		epwqGen.addAttribute(EPWQGenerator.EPSTATUS, epstatus);

		// create the EPWAITINGQUEUE
		epwqGen.createEntity();
	}

	/**********************************
	* Check part2 in EPIMSLSEOABR, for EPIMSWWSEOABR, set WWSEO.EPIMSSTATUS = to returned value
    * B.	Part 2 - LSEO’s Features
    *
    * LSEOPRODSTRUCT:PRODSTRUCT as p
    * LSEOSWPRODSTRUCT:SWPRODSTRUCT as p
    * WWSEOLSEO:WWSEO as w
    *
    * Note: the preceding gives two cases for the following.
    *
    * If w.SPECBID = Yes (11458) then
    * 	If any p.EPIMSSTATUS <> 'Released' then
    * 		Add (LSEO,WWSEO)to EPwaitingQueue for EPSTATUS = 'Released'
    * 		Part2 ='Waiting'  (WWSEO:Set WWSEO.EPIMSSTATUS to 'Waiting' (EPWAIT))
    * 	Else
    * 		Part2 = 'Released' (WWSEO:Set WWSEO.EPIMSSTATUS to 'Released' (Released))
    * 	End if
    * Else
    * 	If any p.EPIMSSTATUS <> 'Released' or 'Promoted' then
    * 		Add LSEO to EPwaitingQueue for EPSTATUS = 'Promoted'
    * 		Part2 ='Waiting' (WWSEO:Set WWSEO.EPIMSSTATUS to 'Waiting' (EPWAIT))
    * 	Else
    * 		Part2 ='Promoted' (WWSEO:Set WWSEO.EPIMSSTATUS to 'Promoted' (Promoted))
    * 	End if
    * End if
    *
    * EPwaitingQueue (EPWAITINGQUEUE)
    * EPWAITET	Set = 'LSEO'
    * EPWAITEID	Set = entityid
    * EPONET		Set = p.entitytype
    * EPONEID		Set = p.entityid
    * EPMACHTYPE	not set
    * EPMODELATR	not set
    * EPFEATURE	not set
    * EPSALESORG	not set
    * EPSTATUS	see above
    *
	* /
	protected String checkWWSEOLSEOFeatures(EntityList list, boolean isSpecBid, EPWQGenerator epwqGen)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		String epimsStatus = null; // a SVC WWSEO will not have ps or swps
		addDebug("checkWWSEOLSEOFeatures entered for specbid: "+isSpecBid);
        EntityItem rootEntity = list.getParentEntityGroup().getEntityItem(0);

		if (isSpecBid){  // is Yes
			epimsStatus = RELEASED;
			// check all PRODSTRUCT
			EntityGroup egrp = list.getEntityGroup("PRODSTRUCT");
			if (egrp.getEntityItemCount()>0){
				epimsStatus = checkSpecBidProdstructs(rootEntity, epwqGen,egrp);
			}

			// check all SWPRODSTRUCT
			egrp = list.getEntityGroup("SWPRODSTRUCT");
			if (egrp.getEntityItemCount()>0){
				String epimsStatus2 = checkSpecBidProdstructs(rootEntity, epwqGen,egrp);
				if (!epimsStatus.equals(WAITING)){ // not waiting, so overwrite is ok
					epimsStatus = epimsStatus2;
				}
			}
		}else{ // not specbid
			epimsStatus = PROMOTED;
			// check all PRODSTRUCT
			EntityGroup egrp = list.getEntityGroup("PRODSTRUCT");
			if (egrp.getEntityItemCount()>0){
				epimsStatus = checkNonSpecBidProdstructs(rootEntity, epwqGen,egrp);
			}

			// check all SWPRODSTRUCT
			egrp = list.getEntityGroup("SWPRODSTRUCT");
			if (egrp.getEntityItemCount()>0){
				String epimsStatus2 = checkNonSpecBidProdstructs(rootEntity, epwqGen,egrp);
				if (!epimsStatus.equals(WAITING)){ // not waiting, so overwrite is ok
					epimsStatus = epimsStatus2;
				}
			}
		}

		addDebug("checkWWSEOLSEOFeatures returning: "+epimsStatus);
		return epimsStatus;
	}

	/**********************************
	* Check (sw)prodstructs ((WWSEO)LSEO’s Features) for WWSEO.SPECBID = Yes (11458)
    * 	If any p.EPIMSSTATUS <> 'Released' then
    * 		Add LSEO to EPwaitingQueue for EPSTATUS = 'Released'
    * 		Part2 ='Waiting'
    * 	Else
    * 		Part2 = 'Released'
    * 	End if
    *
    * EPwaitingQueue (EPWAITINGQUEUE)
    * EPWAITET	Set = root
    * EPWAITEID	Set = entityid
    * EPONET		Set = p.entitytype
    * EPONEID		Set = p.entityid
    * EPMACHTYPE	not set
    * EPMODELATR	not set
    * EPFEATURE	not set
    * EPSALESORG	not set
    * EPSTATUS	see above?
    *
	* /
	private String checkSpecBidProdstructs(EntityItem rootEntity,
		EPWQGenerator epwqGen, EntityGroup egrp)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		String epimsStatus = RELEASED;
		addDebug("checkSpecBidProdstructs entered for : "+egrp.getLongDescription());

		for (int i=0; i<egrp.getEntityItemCount(); i++){
			EntityItem pitem = egrp.getEntityItem(i);
			String p_epimsFlag = epimsAbr.getAttributeFlagEnabledValue(pitem, "EPIMSSTATUS");
			if (p_epimsFlag == null){
				p_epimsFlag = NOT_READY;
			}
			addDebug("checkSpecBidProdstructs "+pitem.getKey()+" EPIMSSTATUS: "+
				PokUtils.getAttributeValue(pitem, "EPIMSSTATUS",", ", NOT_READY, false)+
				" ["+p_epimsFlag+"] ");
			if (!p_epimsFlag.equals(RELEASED)){
				epimsStatus = WAITING;
				addToQueue(rootEntity,pitem,epwqGen,RELEASED);
			}
		}

		addDebug("checkSpecBidProdstructs returning: "+epimsStatus);
		return epimsStatus;
	}

	/**********************************
	* Check (sw)prodstructs ((WWSEO)LSEO’s Features) for WWSEO.SPECBID = No (11457)
    * 	If any p.EPIMSSTATUS <> 'Released' or 'Promoted' then
    * 		Add LSEO to EPwaitingQueue for EPSTATUS = 'Promoted'
    * 		Part2 ='Waiting'
    * 	Else
    * 		Part2 ='Promoted'
    * 	End if
    *
    * EPwaitingQueue (EPWAITINGQUEUE)
    * EPWAITET	Set = root
    * EPWAITEID	Set = entityid
    * EPONET		Set = p.entitytype
    * EPONEID		Set = p.entityid
    * EPMACHTYPE	not set
    * EPMODELATR	not set
    * EPFEATURE	not set
    * EPSALESORG	not set
    * EPSTATUS	see above?
    *
	* /
	private String checkNonSpecBidProdstructs(EntityItem rootEntity,
		EPWQGenerator epwqGen, EntityGroup egrp)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		String epimsStatus = PROMOTED;
		addDebug("checkNonSpecBidProdstructs entered for : "+egrp.getLongDescription());

		for (int i=0; i<egrp.getEntityItemCount(); i++){
			EntityItem pitem = egrp.getEntityItem(i);
			String p_epimsFlag = epimsAbr.getAttributeFlagEnabledValue(pitem, "EPIMSSTATUS");
			if (p_epimsFlag == null){
				p_epimsFlag = NOT_READY;
			}
			addDebug("checkNonSpecBidProdstructs "+pitem.getKey()+" EPIMSSTATUS: "+
				PokUtils.getAttributeValue(pitem, "EPIMSSTATUS",", ", NOT_READY, false)+
				" ["+p_epimsFlag+"] ");
			if (!(p_epimsFlag.equals(RELEASED) || p_epimsFlag.equals(PROMOTED))){
				epimsStatus = WAITING;
				addToQueue(rootEntity,pitem,epwqGen,PROMOTED);
			}
		}

		addDebug("checkNonSpecBidProdstructs returning: "+epimsStatus);

		return epimsStatus;
	}

    /**********************************
    * build and feed the xml
    */
    protected void notifyAndSetStatus(String epimsStatus) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        ParserConfigurationException,
        javax.xml.transform.TransformerException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        IOException
    {
        Object[] args = new String[3];
        MessageFormat msgf = null;
        try{
            Vector mqVct = getMQPropertiesFN();
            if (mqVct==null){
                addDebug("No MQ properties files, nothing will be generated.");
            }else{
                String xml = generateXML();
                int sendCnt=0;
                addDebug("Generated xml:"+NEWLINE+xml+NEWLINE);

                // write to each queue, only one now, but leave this just in case
                for (int i=0; i<mqVct.size(); i++){
                    String mqProperties = (String)mqVct.elementAt(i);
                    ResourceBundle rsBundleMQ = ResourceBundle.getBundle(mqProperties,
                        EPIMSABRSTATUS.getLocale(epimsAbr.getProfile().getReadLanguage().getNLSID()));
                    Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
                    boolean bNotify = ((Boolean)ht.get(MQUsage.NOTIFY)).booleanValue();

                    if (bNotify) {
                        try{
                            MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml, ht);
                            //SENT_SUCCESS = XML was generated and sent successfully for {0}.
                            msgf = new MessageFormat(epimsAbr.getBundle().getString("SENT_SUCCESS"));
                            args[0] = mqProperties;
                            addOutput(msgf.format(args));
                            sendCnt++;
                            if (!xmlgen.equals(failedStr)){  // dont overwrite a failed notice
                                xmlgen = epimsAbr.getBundle().getString("SUCCESS");//"Success";
                            }
                        }catch (com.ibm.mq.MQException ex) {
                            //MQ_ERROR = Error: An MQSeries error occurred for {0}: Completion code {1} Reason code {2}.
                            xmlgen = failedStr;
                            msgf = new MessageFormat(epimsAbr.getBundle().getString("MQ_ERROR"));
                            args[0] = mqProperties;
                            args[1] = ""+ex.completionCode;
                            args[2] = ""+ex.reasonCode;
                            addError(msgf.format(args));
                            ex.printStackTrace(System.out);
                        } catch (java.io.IOException ex) {
                            //MQIO_ERROR = Error: An error occurred when writing to the MQ message buffer for {0}: {1}
                            xmlgen = failedStr;
                            msgf = new MessageFormat(epimsAbr.getBundle().getString("MQIO_ERROR"));
                            args[0] = mqProperties;
                            args[1] = ex.toString();
                            addError(msgf.format(args));
                            ex.printStackTrace(System.out);
                        }
                    }else{
                        //NO_NOTIFY = XML was generated but NOTIFY was false in the {0} properties file.
                        msgf = new MessageFormat(epimsAbr.getBundle().getString("NO_NOTIFY"));
                        args[0] = mqProperties;
                        addError(msgf.format(args));
                        xmlgen = epimsAbr.getBundle().getString("NOT_SENT");//"Not sent";
                    }
                }
				if (sendCnt>0 && sendCnt!=mqVct.size()){ // some went but not all
					xmlgen = epimsAbr.getBundle().getString("ALL_NOT_SENT");//"Not sent to all";
				}
/*				if (epimsStatus != null) { // GX version
					if (sendCnt==mqVct.size()){
						// set root entity to specified EPIMSSTATUS
						setEPIMSSTATUS(epimsStatus);
					}else{
						addDebug("Not setting EPIMSSTATUS to "+epimsStatus+" due to send errors");
					}
				}
*/
            }
        }catch(IOException ioe){
            // only get this if a required node was not populated
            //REQ_ERROR = Error: {0}
            msgf = new MessageFormat(epimsAbr.getBundle().getString("REQ_ERROR"));
            args[0] = ioe.getMessage();
            addError(msgf.format(args));
            xmlgen = failedStr;
        }catch(java.sql.SQLException x){
            xmlgen = failedStr;
            throw x;
        }catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
            xmlgen = failedStr;
            throw x;
        }catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
            xmlgen = failedStr;
            throw x;
        }catch(ParserConfigurationException x){
            xmlgen = failedStr;
            throw x;
        }catch(javax.xml.transform.TransformerException x){
            xmlgen = failedStr;
            throw x;
        }
    }

    /**********************************
    * generate the xml
    */
    private String generateXML() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        ParserConfigurationException,
        javax.xml.transform.TransformerException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        IOException
    {
        EntityList eList = epimsAbr.getEntityList();
        Profile profile = epimsAbr.getProfile();
        Vector xmlVct = getXMLMap(); // get list of xml elements

        //Wayne Kehrli  Base message is NLSID=1
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
        // use last final DTS for setting notification element
        profile.setValOnEffOn(epimsAbr.getLastFinalDTS(), epimsAbr.getLastFinalDTS());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();  // Create

        Element root = null;

        StringBuffer debugSb = new StringBuffer();
        for(int i=0; i<xmlVct.size(); i++){
            SAPLElem sapl = (SAPLElem)xmlVct.elementAt(i);
            //rptSb.append("<pre>"+sapl+"</pre>\n");
            sapl.addElements(epimsAbr.getDB(),eList, document, root,debugSb);
        }
        addDebug("GenXML debug: "+NEWLINE+debugSb.toString());

        //Output the XML

        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "no");
        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        //create string from xml tree
        java.io.StringWriter sw = new java.io.StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(document);
        trans.transform(source, result);
        String xmlString = SAPLElem.removeCheat(sw.toString());

        return xmlString;
    }

    /**********************************
    * check for any changes in structure or specified attr between current chg to final and prior chg
    */
    protected boolean changeOfInterest()
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        boolean hadChgs = false;
        String lastdts = epimsAbr.getLastFinalDTS();
        String priordts = epimsAbr.getPriorFinalDTS();

        if (lastdts.equals(priordts)){
            addDebug("changeOfInterest Only one transition to Final found, no changes can exist.");
            return hadChgs;
        }

        // set lastfinal date in profile
        Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        lastprofile.setValOnEffOn(lastdts, lastdts);

        // pull VE
        // get VE name
        String VEname = getVeName();
        // create VE for lastfinal time
        EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
            new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
            new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

         // debug display list of groups
        addDebug("changeOfInterest dts: "+lastdts+" extract: "+VEname+NEWLINE +
            PokUtils.outputList(lastFinalList));

        // set prior date in profile
        Profile profile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        profile.setValOnEffOn(priordts, priordts);

        // create VE for prior time
        EntityList list = epimsAbr.getDB().getEntityList(profile,
            new ExtractActionItem(null, epimsAbr.getDB(),profile,VEname),
            new EntityItem[] { new EntityItem(null, profile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

         // debug display list of groups
        addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
            PokUtils.outputList(list));

        // get the attributes as one string for each entityitem key
        Hashtable currlistRep = getStringRep(lastFinalList, getAttrOfInterest());
        Hashtable prevlistRep = getStringRep(list, getAttrOfInterest());

        hadChgs = changeOfInterest(currlistRep, prevlistRep);

        list.dereference();
        lastFinalList.dereference();
		currlistRep.clear();
		prevlistRep.clear();

        return hadChgs;
    }

    /**********************************
    * Classes that dont override protected boolean changeOfInterest() must supply a hashtable
    */
	protected Hashtable getAttrOfInterest() { return null;}

    /**********************************
    * Look at hashtables representing the Entitylists from time1 and time2
    * all we need to know is if there was a difference, not exactly what it was
    */
	protected boolean changeOfInterest(Hashtable currlistRep,Hashtable prevlistRep){
		boolean hadChgs = false;
		if (currlistRep.keySet().containsAll(prevlistRep.keySet()) &&
			prevlistRep.keySet().containsAll(currlistRep.keySet())) {
			// structure matches
			addDebug("changeOfInterest: no change in structure found");

			// look at attributes
			if (!(currlistRep.values().containsAll(prevlistRep.values()) &&
				prevlistRep.values().containsAll(currlistRep.values()))) {
				hadChgs = true;
				addDebug("changeOfInterest: difference in values found: "+NEWLINE+"prev "+prevlistRep.values()+
					NEWLINE+"curr "+currlistRep.values());
			}else{
				addDebug("changeOfInterest: no change in values found");
			}
		}else{
			// structure changed
			hadChgs = true;
			addDebug("changeOfInterest: difference in keysets(structure) found: "+NEWLINE+"prev "+prevlistRep.keySet()+
				NEWLINE+"curr "+currlistRep.keySet());
		}

		return hadChgs;
	}

    /**********************************
    * get hashtable with entitylist converted to strings.  key is entityitem key
    * value is the concatenated list of all attributes of interest.
    */
	protected Hashtable getStringRep(EntityList list, Hashtable attrOfInterest) {
		addDebug(NEWLINE+"getStringRep: entered for "+list.getProfile().getValOn());
		Hashtable listTbl = new Hashtable();
		if (attrOfInterest==null){
			addDebug("getStringRep: coding ERROR attrOfInterest hashtable was null");
			return listTbl;
		}
        EntityGroup eg =list.getParentEntityGroup();
		String attrlist[] = (String[])attrOfInterest.get(eg.getEntityType());
		if (attrlist ==null){
			addDebug("getStringRep: No list of 'attr of interest' found for "+eg.getEntityType());
		}
		for (int e=0; e<eg.getEntityItemCount(); e++)
		{
			EntityItem theItem = eg.getEntityItem(e);
			String str = epimsAbr.generateString(theItem, attrlist);

			// add empty string for those items without 'attr of interest' for structure chk
			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
			listTbl.put(theItem.getKey(),str);
		}


		for (int i=0; i<list.getEntityGroupCount(); i++)
		{
			eg =list.getEntityGroup(i);
			attrlist = (String[])attrOfInterest.get(eg.getEntityType());
			if (attrlist ==null){
				addDebug("getStringRep: No list of 'attr of interest' found for "+eg.getEntityType());
			}
			for (int e=0; e<eg.getEntityItemCount(); e++)
			{
				EntityItem theItem = eg.getEntityItem(e);
				String str = epimsAbr.generateString(theItem, attrlist);

				// add empty string for those items without 'attr of interest' for structure chk
				addDebug("getStringRep: put "+theItem.getKey()+" "+str);
                listTbl.put(theItem.getKey(),str);
			}
		}
		return listTbl;
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public abstract String getVersion();


}
