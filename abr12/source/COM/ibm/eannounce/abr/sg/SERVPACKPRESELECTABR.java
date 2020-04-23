//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2001,2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//SERVPACKPRESELECTABR.java,v
//Revision 1.18  2010/02/08 14:51:46  wendy
//RCQ00086595-WI -- Service Pac PreSelect for Simple Service Bundles
//
//SERVPACKPRESELECTABR.java,v
//Revision 1.20  2010/02/28 02:41:05  wendy
//cvs failure
//

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/*************************
 * SERVPACKPRESELECTABR
 * 
 * from BH FS ABR CatalogRole ABR_PDG 20091222.doc
 *  
B.	ServicePacs

A MODEL is a Service if MODEL.COFCAT = “Service” (102) and the ServicePac type of the service is MODEL.COFSUBCAT 
(e.g. Remote Technical Support). A LSEO is a ServicePac if its parent WWSEO has a parent MODEL that is a ServicePac. 
A LSEOBUNDLE is a service pac if BUNDLETYPE is only “Service” (102) and ServicePac Bundle Type (SVCPACBNDLTYPE) 
is the type of Service.

A “system” (WWSEO 1 or Model1) has many potential ServicePacs (WWSEO 3 or LSEOBUNDLE2) of various types.

C.	Preselect

This is used to specify by type of service a single ServicePac as the default selection when ordering a system. 
Since this is for the Catalog which is by country, the ServicePac will be a LSEO or a LSEOBUNDLE.

Data Model
EntityType	LongDescription
CATLGSVCPACPRESEL	Catalog ServicePac PreSelect


Attribute	Type	Description
LSEOMKTGDESC	T	LSEO Marketing Description
CATMACHTYPE	U	Machine Type
OFFCOUNTRY	U	Offering Country
PDHDOMAIN	F	Domains
PRESELINDC	U	Preselect Indicator
PRESELSEOTYPE	U	SEO Type
CATSEOID	T	SEO ID
CATWORKFLOW	U	Workflow
SVCPACTYPE	T	ServicePac Type

 * 
E.	Checking ABR

This ABR (SERVPACKPRESELECTABR) is queued via a User Interface workflow action. The user will 
navigate to a list of CATLGCNTRY where a workflow action is used to queue this ABR. The ABR will 
limit itself to the CATLGCNTRY.OFFCOUNTRY of the instance selected.

The ABR validates the CATLGSVCPACPRESEL data for the PDHDOMAINs of the Workgroup of the PROFILE that queues the ABR.

For each instance of CATLGSVCPACPRESEL where PRESELINDC = “Yes”, verify that:

If PRESELSEOTYPE = “LSEO”, then
	1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
		LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or 
		consider it to be 9999-12-31 if it doesn’t exist).
	2.	Verify that the LSEO has a grandparent MODEL with COFCAT = “Service” and COFSUBCAT = SVCPACTYPE
	3.	Verify that the LSEO (SEOID) is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
		described in the prior section for the Generating ABR.
Else (note that the value is “LSEOBUNDLE”)
	1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
		BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
		consider it to be 9999-12-31 if it doesn’t exist).
	2.	Verify that the LSEOBUNDLE has only one value for BUNDLETYPE and it is 102 (Service)
		and that SVCPACTYPE = LSEOBUNDLE.SVCPACBNDLTYPE
	3.	Verify that the LSEOBUNDLE is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
	described in the prior section for the Generating ABR.

If not, then report an error listing all attributes of the CATLGSVCPACPRESEL that is in error.

LSEO VE to get to MODEL
SG	Action/Attribute	SERVPACKPRESELECTPDG	TYPE	Extract	EXTSERVPACKPDG1	
SG	Action/Entity	EXTSERVPACKPDG1	MODELWWSEO	U	1	
SG	Action/Entity	EXTSERVPACKPDG1	WWSEOLSEO	U	0	

 */
public class SERVPACKPRESELECTABR extends SVCPACPRESELBase {
	
    private PDGUtility m_utility = new PDGUtility();
    private EntityItem curCatLgSvcItem = null;
    private static final int MEM_LIMIT=10;
    
    /*******************
     * do the checks for this ABR
     * @see COM.ibm.eannounce.abr.sg.SVCPACPRESELBase#executeThis()
     */
    protected void executeThis() throws MiddlewareRequestException, 
    SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, 
    EANBusinessRuleException 
    {		 	
    	// search for CATLGSVCPACPRESEL with PRESELINDC=Yes and OFFCOUNTRY=CATLGCNTRY.OFFCOUNTRY
    	EntityItem[] aeiSVCPAC = searchForCATLGSVCPACPRESEL();

    	if(getReturnCode()== PokBaseABR.PASS){
    		// search is running out of memory, must do this in chunks
    		// split into lseo and lseobundle svcpacs
    		Vector lseoSvcVct = new Vector();
    		Vector lseoBdlSvcVct = new Vector();

    		Hashtable flagTbl = new Hashtable();
    		for (int i = 0; i < aeiSVCPAC.length; i++) {
    			curCatLgSvcItem = aeiSVCPAC[i];
    			String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);
    			String catmachtype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATMACHTYPE, "", "", false);
    			String svcpactype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_SVCPACTYPE, "", "", false);
    			String seotype = getAttributeFlagEnabledValue(curCatLgSvcItem, ATT_PRESELSEOTYPE);

    			addDebug("checking "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid+
    					" "+ATT_CATMACHTYPE+": "+catmachtype+" "+ATT_SVCPACTYPE+": "+svcpactype+
    					" "+ATT_PRESELSEOTYPE+":" + seotype);

    			if (catseoid.length() == 0) {
    				//BLANK_ERR = {0} {1} is blank.
    				args[0] = getLD_NDN(curCatLgSvcItem);
    				args[1] = PokUtils.getAttributeDescription(curCatLgSvcItem.getEntityGroup(), ATT_CATSEOID, ATT_CATSEOID);
    				addError("BLANK_ERR",args);
    				continue;
    			}
    			if (svcpactype.length() == 0) {
    				//BLANK_ERR = {0} {1} is blank.
    				args[0] = getLD_NDN(curCatLgSvcItem);
    				args[1] = PokUtils.getAttributeDescription(curCatLgSvcItem.getEntityGroup(), ATT_SVCPACTYPE, ATT_SVCPACTYPE);
    				addError("BLANK_ERR",args);
    				continue;
    			}

    			//Note: If PRESELSEOTYPE is not specified, assume “LSEO”.
    			if (seotype==null || seotype.length()==0){
    				seotype = PRESELSEOTYPE_LSEO;
    			}
    			//If PRESELSEOTYPE = “LSEO”, then
    			if (PRESELSEOTYPE_LSEO.equals(seotype)){
    				// find MODEL.COFSUBCAT flag code corresponding to the text CATLGSVCPACPRESEL.SVCPACTYPE
    				String modelFlag = findSVCPACTYPEFlag(flagTbl,svcpactype,ATT_COFSUBCAT);
    				if (modelFlag == null) {
    					//FLAG_ERR = A value for {0} matching &quot;{1}&quot; does not exist
    					args[0] = ATT_COFSUBCAT;
    					args[1] = svcpactype;
    					addError("FLAG_ERR",args);
    					continue;
    				}

    				lseoSvcVct.add(curCatLgSvcItem);
    			}else{
    				// find "LSEOBUNDLE.SVCPACBNDLTYPE" flag code corresponding to the text CATLGSVCPACPRESEL.SVCPACTYPE 
    				findSVCPACTYPEFlag(flagTbl,svcpactype,ATT_SVCPACBNDLTYPE);
    				lseoBdlSvcVct.add(curCatLgSvcItem);
    			}
    		}

    		// loop thru lseo and pull extracts in chunks
    		if (lseoSvcVct.size() >0) {
    			doLSEOSvcChunks(lseoSvcVct, flagTbl);
    			lseoSvcVct.clear();

        		addOutput("");// add spacer
    		} // end LSEO

    		// loop thru lseobdl and pull extracts in chunks
    		if (lseoBdlSvcVct.size() >0) {
    			doLSEOBDLSvcChunks(lseoBdlSvcVct, flagTbl);
    			lseoBdlSvcVct.clear();
    		} // end LSEO

    		flagTbl.clear();
    	} // rc=pass
    }
    /**
     * look thru lseobundles in chunks
     * @param lseobdlSvcVct
     * @param flagTbl
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private void doLSEOBDLSvcChunks(Vector lseobdlSvcVct, Hashtable flagTbl) throws SQLException, 
    MiddlewareException, MiddlewareShutdownInProgressException {
    	addHeading(2,"LSEOBUNDLE ServicePac Checks");
    	Hashtable tmp = new Hashtable();
    	Vector svcVct = new Vector();
    	if (lseobdlSvcVct.size()>MEM_LIMIT) {
    		int numSeo = lseobdlSvcVct.size()/MEM_LIMIT;
    		int numUsed=0;
    		for (int i=0; i<=numSeo; i++)  {
    			tmp.clear();
    			for (int x=0; x<MEM_LIMIT; x++)  {
    				if (numUsed == lseobdlSvcVct.size()){
    					break;
    				}

    				curCatLgSvcItem = (EntityItem)lseobdlSvcVct.elementAt(numUsed++);
    				String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);

    				addDebug("checking "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid);

    				//1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
    				//BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
    				//consider it to be 9999-12-31 if it doesn’t exist).
    				Vector bdlVct = findLSEOBDLForCheck1(catseoid);
    				if (bdlVct.size()>0){
    					//collect all LSEOBUNDLE to attempt to improve perf
    					tmp.put(curCatLgSvcItem.getKey(), bdlVct);
    					svcVct.add(curCatLgSvcItem);
    				}
    			}

    			if (tmp.size()>0) { // could be 0 if num entities is multiple of limit
    				doLSEOBDLSvcChecks(tmp, svcVct, flagTbl);
    				svcVct.clear();
    			}
    		}
    	}
    	else   {
    		// build vector of EntityItems
    		for(int ie=0; ie<lseobdlSvcVct.size();ie++) {
    			curCatLgSvcItem = (EntityItem)lseobdlSvcVct.elementAt(ie);
    			String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);
    			addDebug("checking "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid);

    			//1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
    			//BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
    			//consider it to be 9999-12-31 if it doesn’t exist).
    			Vector bdlVct = findLSEOBDLForCheck1(catseoid);
    			if (bdlVct.size()>0){
    				//collect all LSEOBUNDLE to attempt to improve perf
    				tmp.put(curCatLgSvcItem.getKey(), bdlVct);
    				svcVct.add(curCatLgSvcItem);
    			}
    		}

    		// pull extracts
    		if (tmp.size()>0) {
    			doLSEOBDLSvcChecks(tmp, svcVct, flagTbl);
    			svcVct.clear();
    		}
    	}

    	tmp.clear();
    	tmp = null;
    }
    /**look thru lseo in chunks
     * @param lseoSvcVct
     * @param flagTbl
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private void doLSEOSvcChunks(Vector lseoSvcVct, Hashtable flagTbl) throws SQLException, 
    MiddlewareException, MiddlewareShutdownInProgressException {
    	addHeading(2,"LSEO ServicePac Checks");
    	Hashtable tmp = new Hashtable();
    	Vector svcVct = new Vector();
    	if (lseoSvcVct.size()>MEM_LIMIT) {
    		int numSeo = lseoSvcVct.size()/MEM_LIMIT;
    		int numUsed=0;
    		for (int i=0; i<=numSeo; i++)  {
    			tmp.clear();
    			for (int x=0; x<MEM_LIMIT; x++)  {
    				if (numUsed == lseoSvcVct.size()){
    					break;
    				}

    				curCatLgSvcItem = (EntityItem)lseoSvcVct.elementAt(numUsed++);
    				String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);

    				addDebug("checking "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid);

    				//1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
    				//LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or 
    				//consider it to be 9999-12-31 if it doesn’t exist).
    				Vector vct = findLSEOForCheck1(catseoid); // should only be one, but allow for multiple
    				if (vct.size()>0){
    					//collect all LSEO to attempt to improve perf
    					tmp.put(curCatLgSvcItem.getKey(), vct);
    					svcVct.add(curCatLgSvcItem);
    				}
    			}

    			if (tmp.size()>0) { // could be 0 if num entities is multiple of limit
    				doLSEOSvcChecks(tmp, svcVct, flagTbl);
    				svcVct.clear();
    			}
    		}
    	}
    	else   {
    		// build vector of EntityItems
    		for(int ie=0; ie<lseoSvcVct.size();ie++) {
    			curCatLgSvcItem = (EntityItem)lseoSvcVct.elementAt(ie);
    			String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);
    			addDebug("checking "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid);

    			//1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
    			//LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or 
    			//consider it to be 9999-12-31 if it doesn’t exist).
    			Vector vct = findLSEOForCheck1(catseoid); // should only be one, but allow for multiple
    			if (vct.size()>0){
    				//collect all LSEO to attempt to improve perf
    				tmp.put(curCatLgSvcItem.getKey(), vct);
    				svcVct.add(curCatLgSvcItem);
    			}
    		}

    		// pull extracts
    		if (tmp.size()>0) {
    			doLSEOSvcChecks(tmp, svcVct, flagTbl);
    			svcVct.clear();
    		}
    	}

    	tmp.clear();
    	tmp = null;
    }

    /**
     * do the rest of the LSEO checks
     * 
     * @param lseoTbl
     * @param svcVct
     * @param flagTbl
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private void doLSEOSvcChecks(Hashtable lseoTbl, Vector svcVct, Hashtable flagTbl) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
    	if (lseoTbl.size()>0){ // found lseo to check - this is for all CATLGSVCPACPRESEL
    		// handle PRESELSEOTYPE = “LSEO” now
    		ExtractActionItem lseoExtAction = new ExtractActionItem(null, m_db, m_prof, "EXTSERVPACKPDG1");
    		// pull all lseo to model now at once to improve perf
    		Vector allLseoVct = new Vector();
    		for (int i = 0; i < svcVct.size(); i++) {
    			curCatLgSvcItem = (EntityItem)svcVct.elementAt(i);
    			Vector vct = (Vector)lseoTbl.get(curCatLgSvcItem.getKey());
    			if (vct == null){
    				// skip this one, it had errors earlier or was lseobundle
    				continue;
    			}
    			allLseoVct.addAll(vct);
    		}
    		EntityItem[] lseoArray = null; 
    		if (allLseoVct.size()>0){
    			lseoArray = new EntityItem[allLseoVct.size()];
    			allLseoVct.copyInto(lseoArray);
    			allLseoVct.clear();
    		}
    		EntityList el = EntityList.getEntityList(m_db, m_prof, lseoExtAction, lseoArray);
			addDebug("Extract for LSEOs EXTSERVPACKPDG1 "+PokUtils.outputList(el));

			ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof, "SERVPACLSEO");

			for (int i = 0; i < svcVct.size(); i++) {
				curCatLgSvcItem = (EntityItem)svcVct.elementAt(i);
				Vector vct = (Vector)lseoTbl.get(curCatLgSvcItem.getKey());
				if (vct == null){
					// skip this one, it had errors earlier or was lseobundle
					addDebug("skipping further processing of "+curCatLgSvcItem.getKey());
					continue;
				}

				//was PRESELSEOTYPE = “LSEO”, so
				boolean hadErr = doLSEOChecks(flagTbl, vct, el, xai);
				if (!hadErr){
					//NO_ERRORS= <b>{0}</b><br />No Errors found.
					args[0] = getLD_NDN(curCatLgSvcItem);
					addMessage("", "NO_ERRORS", args);
				}

				vct.clear();
				curCatLgSvcItem = null;
			}//CATLGSVCPACPRESEL loop

			el.dereference();
			lseoTbl.clear();
		}// found lseo to check - this is for all CATLGSVCPACPRESEL	
	}
	/**
	 * do the rest of the LSEOBUNDLE checks
	 * 
	 * @param lseoBdlTbl
	 * @param svcVct
	 * @param flagTbl
	 * @throws MiddlewareRequestException
	 * @throws SQLException 
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void doLSEOBDLSvcChecks(Hashtable lseoBdlTbl, Vector svcVct, Hashtable flagTbl) 
			throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		if (lseoBdlTbl.size()>0){ // found lseo to check - this is for all CATLGSVCPACPRESEL
	 		ExtractActionItem bdlxai = new ExtractActionItem(null, m_db, m_prof, "SERVPACLSEOBDL");
    		ExtractActionItem dummyxai = new ExtractActionItem(null, m_db, m_prof, "dummy");
		
			// pull all lseo to model now at once to improve perf
			Vector allLseoBdlVct = new Vector();
			for (int i = 0; i < svcVct.size(); i++) {
				curCatLgSvcItem = (EntityItem)svcVct.elementAt(i);
				Vector vct = (Vector)lseoBdlTbl.get(curCatLgSvcItem.getKey());
				if (vct == null){
					// skip this one, it had errors earlier or was lseo
					continue;
				}
				allLseoBdlVct.addAll(vct);
			}
			EntityItem[] eiArray = null; 
			if (allLseoBdlVct.size()>0){
				eiArray = new EntityItem[allLseoBdlVct.size()];
				allLseoBdlVct.copyInto(eiArray);
				allLseoBdlVct.clear();
			}
			EntityList el = EntityList.getEntityList(m_db, m_prof, dummyxai, eiArray);
			addDebug("Extract for LSEOBUNDLEs "+PokUtils.outputList(el));
	
			for (int i = 0; i < svcVct.size(); i++) {
				curCatLgSvcItem = (EntityItem)svcVct.elementAt(i);
				Vector vct = (Vector)lseoBdlTbl.get(curCatLgSvcItem.getKey());
				if (vct == null){
					// skip this one, it had errors earlier or was lseo
					addDebug("skipping further processing of "+curCatLgSvcItem.getKey());
					continue;
				}

				//was PRESELSEOTYPE = “LSEOBUNDLE”, so
				boolean hadErr = doLSEOBDLChecks(flagTbl, vct, el, bdlxai);
				if (!hadErr){
					//NO_ERRORS= <b>{0}</b><br />No Errors found.
					args[0] = getLD_NDN(curCatLgSvcItem);
					addMessage("", "NO_ERRORS", args);
				}

				vct.clear();
				curCatLgSvcItem = null;
			}//CATLGSVCPACPRESEL loop

			el.dereference();
			lseoBdlTbl.clear();
		}		
	}
	/*****************
	 * make sure OFFCOUNTRY has value
	 * 
	 */
	protected void verifyRequest(){
	    EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
	    
		offCountryFlag = PokUtils.getAttributeFlagValue(rootEntity, ATT_OFFCOUNTRY);
		offCountry = PokUtils.getAttributeValue(rootEntity, ATT_OFFCOUNTRY, "", PokUtils.DEFNOTPOPULATED, false);
		
		addDebug("verifyRequest: "+rootEntity.getKey()+" offCountryFlag "+offCountryFlag+" offCountry "+offCountry);
		
		if (offCountryFlag==null){
			//MISSING_ERR = {0} is not specified.
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), ATT_OFFCOUNTRY, ATT_OFFCOUNTRY);
			addError("MISSING_ERR",args);
		}
	}
	/************************
	 * check all LSEOs for the current CATLGSVCPACPRESEL
	 * 
	 * A MODEL is a Service if MODEL.COFCAT = “Service” (102) and the ServicePac type of 
	 * the service is MODEL.COFSUBCAT (e.g. Remote Technical Support). A LSEO is a ServicePac if its 
	 * parent WWSEO has a parent MODEL that is a ServicePac
	 * 
	 * If PRESELSEOTYPE = “LSEO”, then
	 * 	1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and
	 * 	LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or
	 * 	consider it to be 9999-12-31 if it doesn’t exist).
	 * 	2.	Verify that the LSEO has a grandparent MODEL with COFCAT = “Service” and COFSUBCAT = SVCPACTYPE
	 * 	3.	Verify that the LSEO (SEOID) is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as
	 * 	described in the prior section for the Generating ABR.
	 * 
	 * @param flagTbl
	 * @param lseoVct
	 * @param lseoList
	 * @param xai
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private boolean doLSEOChecks(Hashtable flagTbl, Vector lseoVct, 
			EntityList lseoList,ExtractActionItem xai) throws SQLException, MiddlewareException, 
			MiddlewareShutdownInProgressException
	{
		boolean hadError = false;

		String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);
		String catmachtype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATMACHTYPE, "", "", false);
		String svcpactype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_SVCPACTYPE, "", "", false);
		
		// find MODEL flag code corresponding to the text CATLGSVCPACPRESEL.SVCPACTYPE
		String modelFlag = (String)flagTbl.get(ATT_COFSUBCAT+svcpactype);

		addDebug("doLSEOChecks "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid+
				" "+ATT_CATMACHTYPE+": "+catmachtype+" "+ATT_SVCPACTYPE+": "+svcpactype+
				" modelFlag "+modelFlag);
		
		EntityGroup lseoGrp = lseoList.getParentEntityGroup();
		for (int i=0; i<lseoVct.size(); i++){ // only look at the LSEO for this CATLGSVCPACPRESEL
			EntityItem eiLSEO = (EntityItem)lseoVct.elementAt(i);
			eiLSEO = lseoGrp.getEntityItem(eiLSEO.getKey()); // get extract version
			Vector wwseoVct = PokUtils.getAllLinkedEntities(eiLSEO, "WWSEOLSEO", "WWSEO");
			Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
			addDebug("doLSEOChecks checking "+eiLSEO.getKey()+" wwseovct "+wwseoVct.size()+" mdlvct "+mdlVct.size());

			//2.	Verify that the LSEO has a grandparent MODEL with COFCAT = “Service” 
			//and COFSUBCAT = SVCPACTYPE
			boolean mdlfnd = false;
			for (int m = 0; m < mdlVct.size(); m++) {
				EntityItem eiMODEL = (EntityItem)mdlVct.elementAt(m);
				String cofcat = PokUtils.getAttributeFlagValue(eiMODEL, ATT_COFCAT);
				String cofsubcat = PokUtils.getAttributeFlagValue(eiMODEL, ATT_COFSUBCAT);
				addDebug("doLSEOChecks checking " + eiMODEL.getKey()+" COFCAT:"+
						cofcat+" COFSUBCAT:"+cofsubcat);
				if (COFCAT_Service.equals(cofcat)&& // is Service 
						modelFlag.equals(cofsubcat)){//COFGRP = SVCPACTYPE
					mdlfnd = true;
					break;
				}
			}

			if (!mdlfnd) {
				hadError = true;
				//LSEO_MDL_ERR = {0} does not have a grandparent MODEL with {1}: Service and {2}: {3}. Grandparent is {4}
				args[0] = getLD_NDN(eiLSEO);
				args[1] = PokUtils.getAttributeDescription(lseoList.getEntityGroup("MODEL"), ATT_COFCAT, ATT_COFCAT);
				args[2] = PokUtils.getAttributeDescription(lseoList.getEntityGroup("MODEL"), ATT_COFSUBCAT, ATT_COFSUBCAT);
				args[3] = svcpactype;
				String mdl = "Not found";
				if (mdlVct.size()>0){
					mdl = getLD_NDN((EntityItem)mdlVct.elementAt(0));
				}
				args[4] = mdl;
				addError("LSEO_MDL_ERR",args);
				continue;
			}

			//3.	Verify that the LSEO (SEOID) is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
			//described in the prior section for the Generating ABR.
			// follow the LSEO's WWSEO to the compatible MODEL
			EntityItem[] wwseoArray = new EntityItem[wwseoVct.size()];
			wwseoVct.copyInto(wwseoArray);
			
			boolean bMTCompat = verifySEOtechCompat(wwseoArray, catmachtype,xai);
			if (!bMTCompat) {
				hadError = true;
				//MACHTYPE_MSG = {0} SEOID is not compatible with this Machine Type {1}
				args[0] = getLD_NDN(eiLSEO);
				args[1] = catmachtype;
				addError("MACHTYPE_MSG",args);
			}

			wwseoVct.clear();
			mdlVct.clear();
		}	
		
		return hadError;
	}

	/******************
	 * A LSEOBUNDLE is a service pac if BUNDLETYPE is only “Service” (102) and 
	 * ServicePac Bundle Type (SVCPACBNDLTYPE) is the type of Service.
	 * 
	 * Else (note that the value is “LSEOBUNDLE”)
	 * 	1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and
	 * 	BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or
	 * 	consider it to be 9999-12-31 if it doesn’t exist).
	 * 	2.	Verify that the LSEOBUNDLE has only one value for BUNDLETYPE and it is 102 (Service)
	 * 	and that SVCPACTYPE = LSEOBUNDLE.SVCPACBNDLTYPE
	 * 
	 * 	3.	Verify that the LSEOBUNDLE is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as
	 * 	described in the prior section for the Generating ABR.
	 * 
	 * @param flagTbl
	 * @param lseobdlVct
	 * @param el
	 * @param xai
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private boolean doLSEOBDLChecks(Hashtable flagTbl,  Vector lseobdlVct, EntityList el,
			ExtractActionItem xai) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		boolean hadError = false;
		
		String catseoid = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATSEOID, "", "", false);
		String catmachtype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_CATMACHTYPE, "", "", false);
		String svcpactype = PokUtils.getAttributeValue(curCatLgSvcItem, ATT_SVCPACTYPE, "", "", false);
		
		// find SVCPACBNDLTYPE flag code corresponding to the text CATLGSVCPACPRESEL.SVCPACTYPE
		String svcpacbdltypeFlag = (String)flagTbl.get(ATT_SVCPACBNDLTYPE+svcpactype);
		
		addDebug("doLSEOBDLChecks "+curCatLgSvcItem.getKey()+" "+ATT_CATSEOID+": "+catseoid+
				" "+ATT_CATMACHTYPE+": "+catmachtype+" "+ATT_SVCPACTYPE+": "+svcpactype+
				" svcpacbdltypeFlag "+svcpacbdltypeFlag);

		EntityGroup lseobdlGrp = el.getParentEntityGroup();
		for (int i=0; i<lseobdlVct.size(); i++){ // only look at the LSEO for this CATLGSVCPACPRESEL
			EntityItem lseobdl = (EntityItem)lseobdlVct.elementAt(i);
			lseobdl = lseobdlGrp.getEntityItem(lseobdl.getKey()); // get extract version

			String bdltype = getAttributeFlagEnabledValue(lseobdl, ATT_BUNDLETYPE);
			String svcpacbdltype = getAttributeFlagEnabledValue(lseobdl, ATT_SVCPACBNDLTYPE);
			addDebug("doLSEOBDLChecks "+lseobdl.getKey()+" bdltype "+bdltype+" svcpacbdltype "+svcpacbdltype);

			if (svcpacbdltypeFlag==null){
				hadError = true;
				//FLAG_ERR = A value for {0} matching &quot;{1}&quot; does not exist
				args[0] = PokUtils.getAttributeDescription(lseobdl.getEntityGroup(), ATT_SVCPACBNDLTYPE, ATT_SVCPACBNDLTYPE);
				args[1] = svcpactype;
				addError("FLAG_ERR",args);
				svcpacbdltypeFlag=""; //prevent NPE in next chk
			}

			//2.	Verify that the LSEOBUNDLE has only one value for BUNDLETYPE and it is 102 (Service)
			// and that SVCPACTYPE = LSEOBUNDLE.SVCPACBNDLTYPE 
			if(!COFCAT_Service.equals(bdltype) ||  // is Service 
					!svcpacbdltypeFlag.equals(svcpacbdltype)){//SVCPACBNDLTYPE = SVCPACTYPE 
				hadError = true;
				//SVCPAC_ERR = {0} SEOID is not a ServicePac.  {1} and {2}
				args[0] = getLD_NDN(lseobdl);
				args[1] = getLD_Value(lseobdl, ATT_BUNDLETYPE);
				args[2] = getLD_Value(lseobdl, ATT_SVCPACBNDLTYPE);
				addError("SVCPAC_ERR",args);
				continue;
			}

			//3.	Verify that the LSEOBUNDLE is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
			//described in the prior section for the Generating ABR.
			boolean bMTCompat = verifySEOtechCompat(
					new EntityItem[] { new EntityItem(null, m_prof, lseobdl.getEntityType(),
							lseobdl.getEntityID()) }, catmachtype,xai);
			if (!bMTCompat) {
				hadError = true;
				//MACHTYPE_MSG = {0} SEOID is not compatible with this Machine Type {1}
				args[0] = getLD_NDN(lseobdl);
				args[1] = catmachtype;
				addError("MACHTYPE_MSG",args);
			}
		} //end bundle loop

		return hadError;
	}
	/*************
	 * If not, then report an error listing all attributes of the CATLGSVCPACPRESEL that is in error.
	 */
	private void listAllCatLgSvcpacAttr(){
		if (curCatLgSvcItem != null){

			String catlgsvcInfo = "";
			try{
				catlgsvcInfo = getLD_NDN(curCatLgSvcItem);
			}catch(Exception e){}
			StringBuffer sb = new StringBuffer("<table width='100%'><caption>"+catlgsvcInfo+"</caption>");
			sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+"><th>Attribute</th><th>Value</th></tr>"+NEWLINE);
			int lineCnt=0;
			for (int i=0; i<curCatLgSvcItem.getAttributeCount(); i++){
				EANAttribute attr = curCatLgSvcItem.getAttribute(i);
				String attrCode = attr.getAttributeCode();
				String desc = PokUtils.getAttributeDescription(curCatLgSvcItem.getEntityGroup(),attrCode,attrCode);
				String value = PokUtils.getAttributeValue(curCatLgSvcItem, attrCode,", ",PokUtils.DEFNOTPOPULATED,true);
				sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">");
				sb.append("<td>"+desc+"</td>"+"<td>"+value+"</td>");
				sb.append("</tr>"+NEWLINE);
			}
			sb.append("</table>");
			addOutput(sb.toString());
			
			curCatLgSvcItem = null;// only put this info out once
		}
	}

	/****************************
	 * Find attr flag that matches the text
	 * for LSEO MODEL.COFSUBCAT = SVCPACTYPE
	 * for LSEOBUNDLE LSEOBUNDLE.SVCPACBNDLTYPE = SVCPACTYPE
	 * 
	 * @param flagTbl
	 * @param strSVCPACTYPE
	 * @param flagAttr
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String findSVCPACTYPEFlag(Hashtable flagTbl, String strSVCPACTYPE, String flagAttr) throws SQLException, MiddlewareException
	{
		String mdlFlag = (String)flagTbl.get(flagAttr+strSVCPACTYPE);
		if (mdlFlag==null){
			// find flagAttr flag code corresponding to the text CATLGSVCPACPRESEL.SVCPACTYPE
			String[] mdlFlagArray = m_utility.getFlagCodeForExactDesc(m_db, m_prof, flagAttr, strSVCPACTYPE);
			if (mdlFlagArray != null && mdlFlagArray.length > 0) {
				mdlFlag = mdlFlagArray[0];
    			flagTbl.put(flagAttr+strSVCPACTYPE, mdlFlag);
    			for (int i=0; i<mdlFlagArray.length; i++){
    				addDebug("findSVCPACTYPEFlag  "+flagAttr+" exact match "+strSVCPACTYPE+" found "+mdlFlagArray[i]);
    			}
			}else{
				//try using like match on description
				mdlFlagArray = m_utility.getFlagCodeForLikedDesc(m_db, m_prof, flagAttr, strSVCPACTYPE);
				if (mdlFlagArray != null && mdlFlagArray.length > 0) {
					mdlFlag = mdlFlagArray[0];
	    			flagTbl.put(flagAttr+strSVCPACTYPE, mdlFlag);
	    			for (int i=0; i<mdlFlagArray.length; i++){
	    				addDebug("findSVCPACTYPEFlag "+flagAttr+" like match "+strSVCPACTYPE+" found "+mdlFlagArray[i]);
	    			}
				}
			}
		}
		return mdlFlag;
	}

	/**********************
	 * Find CATLGSVCPACPRESEL with PRESELINDC=Yes and OFFCOUNTRY matching the root CATLGCNTRY entity
	 * 
	 * - The ABR will limit itself to the CATLGCNTRY.OFFCOUNTRY of the instance selected.
	 * - The ABR validates the CATLGSVCPACPRESEL data for the PDHDOMAINs of the Workgroup of the PROFILE that 
	 * queues the ABR. (search action is limited by domain)
	 * - For each instance of CATLGSVCPACPRESEL where PRESELINDC = “Yes”, verify...
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem[] searchForCATLGSVCPACPRESEL()
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		addDebug("searchForCATLGSVCPACPRESEL  "+ATT_OFFCOUNTRY+" "+offCountryFlag);
		EntityItem eia[]= null;
		Vector attrVct = new Vector(2);
		attrVct.addElement(ATT_PRESELINDC);
		attrVct.addElement(ATT_OFFCOUNTRY);
	
		Vector valVct = new Vector(2);
		valVct.addElement(PRESELINDC_Yes);
		valVct.addElement(offCountryFlag);
	
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					"SRDCATLGSVCPACPRESEL1", "CATLGSVCPACPRESEL", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForCATLGSVCPACPRESEL SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug("searchForCATLGSVCPACPRESEL found "+eia[i].getKey());
			}
		}else{
			EntityItem rootItem = m_elist.getParentEntityGroup().getEntityItem(0);
			//CATLGSVCPACPRESEL_ERR = No &quot;Catalog Service Pack PreSelect&quot; with Preselect Indicator: Yes and {0} exist
			args[0] = getLD_Value(rootItem, ATT_OFFCOUNTRY);
			addError("CATLGSVCPACPRESEL_ERR",args);
		}
		
		attrVct.clear();
		valVct.clear();
		return eia;
	}
    
    /**********************************
    * used for error output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Error: '
    *
    */
    protected void addError(String errCode, Object args[])
    {
		listAllCatLgSvcpacAttr();
		
		super.addError(errCode, args);
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){ 
        m_utility.dereference();
        m_utility = null;
        
        curCatLgSvcItem = null;
    	
        super.dereference();
	}

	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		return "Service Pack Preselect ABR";
	}

	/**
	 * getRevision
	 *
	 * @return
	 * @author Owner
	 */
	public String getRevision() {
		return "1.20";
	}

	/**
	 * getVersion
	 *
	 * @return
	 * @author Owner
	 */
	public static String getVersion() {
		return "SERVPACKPRESELECTABR.java,v 1.18 2010/02/08 14:51:46 wendy Exp";
	}

	/**
	 * getABRVersion
	 *
	 * @return
	 * @author Owner
	 */
	public String getABRVersion() {
		return "1.20";
	}
}
