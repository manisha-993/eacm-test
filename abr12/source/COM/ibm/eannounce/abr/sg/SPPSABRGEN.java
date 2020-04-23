//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*********************************************
 * SPPSABRGEN
 * 
 * D.	Generating ABR
 * This ABR replaces the current PDG in production that supports ServicePac PreSelect.
 * The PDG should be removed from system when this new ABR is promoted to the system.
 * 
 * Setup Entity:
 * EntityType	LongDescription
 * SVCPACPRESELREQ	Catalog ServicePac PreSelect Request
 * 
 * Attribute	Description	Comment
 * MACHTYPEATR	Machine Type
 * OFFCOUNTRY	Offering Country
 * LSEOIDLIST	LSEO SEOID List	Comma separated
 * BDLSEOIDLIST	LSEO Bundle SEOID list	Comma separated
 * SPPSABRGEN	ServicePac PreSelect ABR
 * PDHDOMAIN	Domains
 * 
 * For each MACHTYPEATR, OFFCOUNTRY, SEOID; check to see if it is already in the data (CATLGSVCPACPRESEL). 
 * If not, proceed with the following process:
 * I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
 * 	1.	LSEO
 * 		LSEO via WWSEOLSEO-u: MODELWWSEO-u
 * 		a)	If MODEL.COFCAT <> “Service” (102), then report an error stating that this LSEO SEOID is not a ServicePac.
 *  	List the grandparent MODEL along with the LSEO.
 *  	b)	If OFFCOUNTRY NOT IN LSEO.COUNTRYLIST, then report an error stating that this LSEO is not available in OFFCOUNTRY.
 *  2.	LSEOBUNDLE
 *  	a)	If LSEOBUNDLE. BUNDLETYPE = “Hardware” (100) | “Software” (101), then report an error stating that this
 *  	LSEO SEOID is not a ServicePac.  
 *  	b)	If OFFCOUNTRY NOT IN LSEOBUNDLE.COUNTRYLIST, then report an error stating that this LSEOBUNDLE is not
 *  	available in OFFCOUNTRY.
 * II.	Verify that the SEO has a “technical compatibility”:
 * 	1.	Must be in a SEOCGOS
 * 		LSEO via WWSEOLSEO-u: SEOCGOSSEO-u
 * 		or
 * 		LSEOBUNDLE via SEOCGOSBDL-u
 *  2.	Which will be in a SEOCG
 *  	SEOCGOS via SEOCGSEOCGOS-u
 *  3.	MACHTYPEATR must be in SEOCG from SEOCG via SEOCGMDL-d to MODEL.
 *  	CATMACHTYPE must match a MODEL.MACHTYPEATR. Note that there may be several MODELs and they could have different
 *  	MACHTYPEATR. CATMACHTYPE only has to match one of them to be legal.
 *  If a match is not found, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not compatible with this
 *  Machine Type (CATMACHTYPE)
 * III.	Validate that the SEO is still available:
 *  1.	LSEO
 *  	LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT  If LSOEUNPUBDATEMTRGT is empty, assume 9999-12-31
 *  	or
 *  2.	LSEOBUNDLE
 *  	BUNDLPUBDATEMTRGT <= NOW() <= BUNDLUNPUBDATEMTRGT If BUNDLUNPUBDATEMTRGT is empty, assume 9999-12-31
 *  If the preceding checks fail, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not available.
 *  
 * IV.	Generate Catalog ServicePac PreSelect (CATLGSVCPACPRESEL)
 * 1.	LSEO
 * The values are from attributes of SVCPACPRESELREQ if not specified.
 * 		LSEOMKTGDESC = LSEO.LSEOMKTGDESC
 * 		CATMACHTYPE = MACHTYPEATR
 * 		OFFCOUNTRY = OFFCOUNTRY
 * 		PDHDOMAIN = the workgroup’s default
 * 		PRESELINDC = Yes
 * 		PRESELSEOTYPE = “LSEO”
 * 		CATSEOID = SEOID from LSEOIDLIST
 * 		CATWORKFLOW = “New”
 * 		SVCPACTYPE is set to LSEO’s grandparent MODEL.COFSUBCAT
 * 2.	LSEOBUNDLE
 * The values are from attributes of SVCPACPRESELREQ if not specified.
 * 		LSEOMKTGDESC = LSEOBUNDLE.BUNDLMKTGDESC
 * 		CATMACHTYPE = MACHTYPEATR
 * 		OFFCOUNTRY = OFFCOUNTRY
 * 		PDHDOMAIN = the workgroup’s default
 * 		PRESELINDC = Yes
 * 		PRESELSEOTYPE = “LSEOBUNDLE”
 * 		CATSEOID = SEOID from BDLSEOIDLIST
 * 		CATWORKFLOW = “New”
 * 		SVCPACTYPE = LSEOBUNDLE.SVCPACBNDLTYPE
 * 
 * A report is generated with all error messages or a message that nothing failed followed by a list of
 * CATLGSVCPACPRESELs generated. When the error message references an entity, use the Navigation Display Name.
 *
 */
//$Log: SPPSABRGEN.java,v $
//Revision 1.1  2010/02/08 14:51:46  wendy
//RCQ00086595-WI -- Service Pac PreSelect for Simple Service Bundles
//
public class SPPSABRGEN extends SVCPACPRESELBase {
	private final static String CR_CATLGSVCPACPRESEL_ACTION = "CRCATLGSVCPACPRESEL";
	private final static String ATT_LSEOMKTGDESC = "LSEOMKTGDESC";
	private final static String ATT_PDHDOMAIN = "PDHDOMAIN";
	private final static String ATT_CATWORKFLOW = "CATWORKFLOW";
	private static final String CATWORKFLOW_New = "New";
	
    private String machtypeatr = null;
    private Vector bdlSeoidsVct = null;
    private Vector lseoSeoidsVct = null;
    
	/**********
	 * do the checks and generate CATLGSVCPACPRESEL as needed
	 * 
     * @see COM.ibm.eannounce.abr.sg.SVCPACPRESELBase#executeThis()
     */
    protected void executeThis() throws MiddlewareRequestException, 
    SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException 
    {
    	//For each MACHTYPEATR, OFFCOUNTRY, SEOID; check to see if it is already in the data (CATLGSVCPACPRESEL). 
    	//match LSEOIDLIST with PRESELSEOTYPE=LSEO and BDLSEOIDLIST with PRESELSEOTYPE=LSEOBUNDLE
    	// Note: If PRESELSEOTYPE is not specified, assume “LSEO”.
    	//If not, proceed with the following process:

    	if (bdlSeoidsVct!=null){ // LSEOBUNDLE request, PRESELSEOTYPE=LSEOBUNDLE must be true
    		ExtractActionItem bdlxai = new ExtractActionItem(null, m_db, m_prof, "SERVPACLSEOBDL");
    		addHeading(2,"LSEOBUNDLE ServicePac Generation");
    		// search for existing CATLGSVCPACPRESEL with matching CATMACHTYPE, OFFCOUNTRY and CATSEOID
    		Vector bldSeoidVct = searchForCATLGSVCPACPRESEL(false);
    		// those not matched are returned
    		for(int i=0; i<bldSeoidVct.size(); i++){
    			String seoid = bldSeoidVct.elementAt(i).toString();
    			Vector bdlItemVct = doLSEOBDLChecks(seoid, bdlxai); 
    			if (bdlItemVct.size()>0){
    				// create a CATLGSVCPACPRESEL if criteria is met
    				createBdlCATLGSVCPACPRESEL(seoid,bdlItemVct);
    				EntityItem item = (EntityItem)bdlItemVct.firstElement();
    				item.getEntityGroup().getEntityList().dereference(); // deref here, done with items
    				bdlItemVct.clear();
    			}
    		}
    		addOutput(""); // add a spacer between bdl and lseo
    	}

    	if (lseoSeoidsVct != null){ // LSEO request, PRESELSEOTYPE may be null
    		Hashtable lseoTbl = new Hashtable();
    		addHeading(2,"LSEO ServicePac Generation");
    		// search for existing CATLGSVCPACPRESEL with matching CATMACHTYPE, OFFCOUNTRY and CATSEOID
    		Vector lseoSeoidVct = searchForCATLGSVCPACPRESEL(true);  // those seoid not matched are returned
    		Vector allLseoVct = new Vector();

    		for(int i=0; i<lseoSeoidVct.size(); i++){
    			String seoid = lseoSeoidVct.elementAt(i).toString();
    			//I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
    			//	1.	LSEO
    			//	b)	If OFFCOUNTRY NOT IN LSEO.COUNTRYLIST, then report an error stating that this LSEO is not available in OFFCOUNTRY. 

    			//III.	Validate that the SEO is still available:
    			//	1.	LSEO
    			//	LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT	If LSEOUNPUBDATEMTRGT is empty, assume 9999-12-31
    			// get all valid LSEO for this seoid, OFFCOUNTRY must be in COUNTRYLIST and date must be in range
    			addHeading(3,"Verify LSEO ServicePac SEOID "+seoid+" Availability");
    			Vector lseoItemVct = findLSEOForCheck1(seoid); // should only be one, but allow for multiple
    			if (lseoItemVct.size()>0){
    				//collect all LSEO to attempt to improve perf
    				lseoTbl.put(seoid, lseoItemVct);
    				allLseoVct.addAll(lseoItemVct);
    			}
    		}

    		if (lseoTbl.size()>0){ // found lseo to check - this is for all seoids
    			ExtractActionItem lseoExtAction = new ExtractActionItem(null, m_db, m_prof, "EXTSERVPACKPDG1");
    			// pull all lseo to model now at once to improve perf
    			EntityItem[] lseoArray = null; 
    			if (allLseoVct.size()>0){
    				lseoArray = new EntityItem[allLseoVct.size()];
    				allLseoVct.copyInto(lseoArray);
    				allLseoVct.clear();
    			}
    			EntityList el = EntityList.getEntityList(m_db, m_prof, lseoExtAction, lseoArray);
    			addDebug("Extract for LSEOs to mdl EXTSERVPACKPDG1 "+PokUtils.outputList(el));

    			ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof, "SERVPACLSEO");

    			for(int i=0; i<lseoSeoidVct.size(); i++){
    				String seoid = lseoSeoidVct.elementAt(i).toString();
    				Vector lseoVct = (Vector)lseoTbl.get(seoid);
    				if (lseoVct==null){
    					continue; // no match found
    				}
    				addHeading(3,"Verify LSEO ServicePac SEOID "+seoid+" Technical Compatibility");
    				boolean hadErr = doLSEOChecks(seoid, lseoVct, el, xai);
    				if (!hadErr){
    					// create a CATLGSVCPACPRESEL if criteria is met
    					createLseoCATLGSVCPACPRESEL(seoid,lseoVct);
    				}

    				lseoVct.clear();
    			}//seoid loop

    			el.dereference();

    			lseoTbl.clear();
    			lseoSeoidVct.clear();
    		}
    	} // end LSEO list
    }

    /******************
     * 
     * IV.	Generate Catalog ServicePac PreSelect (CATLGSVCPACPRESEL)
     * 1.	LSEO
     * The values are from attributes of SVCPACPRESELREQ if not specified.
     * 		LSEOMKTGDESC = LSEO.LSEOMKTGDESC
     * 		CATMACHTYPE = MACHTYPEATR
     * 		OFFCOUNTRY = OFFCOUNTRY
     * 		PDHDOMAIN = the workgroup’s default
     * 		PRESELINDC = Yes
     * 		PRESELSEOTYPE = “LSEO”
     * 		CATSEOID = SEOID from LSEOIDLIST
     * 		CATWORKFLOW = “New”
     * 		SVCPACTYPE is set to LSEO’s grandparent MODEL.COFSUBCAT
     * 
     * @param seoid
     * @param lseoVct
     * @throws MiddlewareRequestException
     * @throws RemoteException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws EANBusinessRuleException
     */
    private void createLseoCATLGSVCPACPRESEL(String seoid, Vector lseoVct) throws 
    MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, 
		MiddlewareShutdownInProgressException, EANBusinessRuleException
	{
		addDebug("createLseoCATLGSVCPACPRESEL entered for seoid "+seoid+" lseovct "+lseoVct.size());

		for (int i=0; i<lseoVct.size(); i++){
			EntityItem lseo = (EntityItem)lseoVct.elementAt(i);

			addHeading(3,"Create ServicePac for "+getLD_NDN(lseo));

			Vector wwseoVct = PokUtils.getAllLinkedEntities(lseo, "WWSEOLSEO", "WWSEO");
			Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
			EntityItem mdlItem = (EntityItem)mdlVct.firstElement();
			addDebug("createLseoCATLGSVCPACPRESEL "+lseo.getKey()+" "+mdlItem.getKey());

			wwseoVct.clear();
			mdlVct.clear();

			createCATLGSVCPACPRESEL(seoid, PokUtils.getAttributeValue(lseo, ATT_LSEOMKTGDESC, "", "", false), 
					PokUtils.getAttributeValue(mdlItem, ATT_COFSUBCAT, ", ", "", false), 
					PRESELSEOTYPE_LSEO);
		}
	}

	/***************
	 * IV.	Generate Catalog ServicePac PreSelect (CATLGSVCPACPRESEL)
	 * 2.	LSEOBUNDLE
	 * The values are from attributes of SVCPACPRESELREQ if not specified.
	 * 		LSEOMKTGDESC = LSEOBUNDLE.BUNDLMKTGDESC
	 * 		CATMACHTYPE = MACHTYPEATR
	 * 		OFFCOUNTRY = OFFCOUNTRY
	 * 		PDHDOMAIN = the workgroup’s default
	 * 		PRESELINDC = Yes
	 * 		PRESELSEOTYPE = “LSEOBUNDLE”
	 * 		CATSEOID = SEOID from BDLSEOIDLIST
	 * 		CATWORKFLOW = “New”
	 * 		SVCPACTYPE = LSEOBUNDLE.SVCPACBNDLTYPE
	 * 
	 * @param seoid
	 * @param bdlVct
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void createBdlCATLGSVCPACPRESEL(String seoid, Vector bdlVct)
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		addDebug("createBdlCATLGSVCPACPRESEL entered for seoid "+seoid+" bdlvct "+bdlVct.size());

		for (int i=0; i<bdlVct.size(); i++){
			EntityItem lseobdl = (EntityItem)bdlVct.elementAt(i);
			addHeading(3,"Create ServicePac for "+getLD_NDN(lseobdl));
			addDebug("createBdlCATLGSVCPACPRESEL "+lseobdl.getKey());
			
			createCATLGSVCPACPRESEL(seoid, PokUtils.getAttributeValue(lseobdl, "BUNDLMKTGDESC", "", "", false), 
					PokUtils.getAttributeValue(lseobdl, ATT_SVCPACBNDLTYPE, ", ", "", false), 
					PRESELSEOTYPE_LSEOBDL);
		}
	}
	
	/**
	 * create a CATLGSVCPACPRESEL with specified attributes
	 * 
CATLGSVCPACPRESEL	LSEOMKTGDESC	T	LSEO Marketing Description
CATLGSVCPACPRESEL	CATMACHTYPE	T	Machine Type
CATLGSVCPACPRESEL	OFFCOUNTRY	U	Offering Country
CATLGSVCPACPRESEL	PDHDOMAIN	F	Domains
CATLGSVCPACPRESEL	PRESELINDC	U	Preselect Indicator

CATLGSVCPACPRESEL	CATSEOID	T	SEO ID
CATLGSVCPACPRESEL	CATWORKFLOW	S	Workflow
CATLGSVCPACPRESEL	SVCPACTYPE	T	ServicePac Type
	 * @param seoid
	 * @param mktdesc
	 * @param svcpactype
	 * @param preselseotype
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void createCATLGSVCPACPRESEL(String seoid, String mktdesc, String svcpactype, 
			String preselseotype)
	throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		addDebug("createCATLGSVCPACPRESEL entered for seoid "+seoid+" LSEOMKTGDESC "+mktdesc+
				" SVCPACTYPE "+svcpactype+" PRESELSEOTYPE "+preselseotype);

		EntityItem wgItem = new EntityItem(null, getProfile(), "WG", getProfile().getWGID());
		
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement(ATT_LSEOMKTGDESC); //LSEOMKTGDESC	T	LSEO Marketing Description
		attrValTbl.put(ATT_LSEOMKTGDESC, mktdesc);//LSEOBUNDLE.BUNDLMKTGDESC or LSEO.LSEOMKTGDESC
		attrCodeVct.addElement(ATT_CATMACHTYPE);
		attrValTbl.put(ATT_CATMACHTYPE, machtypeatr);  //CATMACHTYPE	T	Machine Type
		attrCodeVct.addElement(ATT_CATSEOID);
		attrValTbl.put(ATT_CATSEOID, seoid);  //CATSEOID	T	SEO ID
		attrCodeVct.addElement(ATT_SVCPACTYPE); //SVCPACTYPE  T	ServicePac Type
		attrValTbl.put(ATT_SVCPACTYPE, svcpactype);  //LSEOBUNDLE.SVCPACBNDLTYPE or LSEO’s grandparent MODEL.COFSUBCAT
			 
		// write the flags
		attrCodeVct.addElement(ATT_OFFCOUNTRY);
		attrValTbl.put(ATT_OFFCOUNTRY,offCountryFlag);//OFFCOUNTRY	U	Offering Country
		attrCodeVct.addElement(ATT_PRESELINDC);
		attrValTbl.put(ATT_PRESELINDC,PRESELINDC_Yes);//PRESELINDC	U	Preselect Indicator
		attrCodeVct.addElement(ATT_CATWORKFLOW);
		attrValTbl.put(ATT_CATWORKFLOW,CATWORKFLOW_New);//CATWORKFLOW S	New	New
		attrCodeVct.addElement(ATT_PRESELSEOTYPE);
		attrValTbl.put(ATT_PRESELSEOTYPE,preselseotype); //PRESELSEOTYPE

		attrCodeVct.addElement(ATT_PDHDOMAIN);
	
		String value = getProfile().getPDHDomainFlagCodes();
		StringTokenizer st = new StringTokenizer(value,",");
		Vector tmp = new Vector(st.countTokens());
		while(st.hasMoreTokens()) {
			tmp.addElement(st.nextToken().trim());
		}

		attrValTbl.put(ATT_PDHDOMAIN, tmp);
		
		StringBuffer debugSb = new StringBuffer();
		EntityItem item = null;
		try{	
			// create the entity
			item = ABRUtil.createEntity(getDatabase(), getProfile(), CR_CATLGSVCPACPRESEL_ACTION, wgItem,  
					"CATLGSVCPACPRESEL", attrCodeVct, attrValTbl, debugSb); 
			//CREATE_SUCCESS = Successfully created {0}.
			args[0] = getLD_NDN(item);
			addMessage("", "CREATE_SUCCESS", args);
		}catch(EANBusinessRuleException ere){
			throw ere;
		}finally{
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
			if (item == null){
				setReturnCode(FAIL);
				addOutput("ERROR: Can not create CATLGSVCPACPRESEL entity for seoid: "+seoid);
			}	
			attrCodeVct.clear();
			attrValTbl.clear();
			tmp.clear();
		}
	}
	
	/******************
	 * I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
	 * 1.	LSEO
	 * 		LSEO via WWSEOLSEO-u: MODELWWSEO-u
	 * 		a)	If MODEL.COFCAT <> “Service” (102), then report an error stating that this LSEO SEOID is not a ServicePac.
	 * 		List the grandparent MODEL along with the LSEO.
	 * II.	Verify that the SEO has a “technical compatibility”:
	 * 1.	Must be in a SEOCGOS
	 * 		LSEO via WWSEOLSEO-u: SEOCGOSSEO-u
	 * 2.	Which will be in a SEOCG
	 * 		SEOCGOS via SEOCGSEOCGOS-u
	 * 3.	MACHTYPEATR must be in SEOCG from SEOCG via SEOCGMDL-d to MODEL.
	 * CATMACHTYPE must match a MODEL.MACHTYPEATR. Note that there may be several MODELs and they could have different
	 * MACHTYPEATR. CATMACHTYPE only has to match one of them to be legal.
	 * If a match is not found, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not compatible with this
	 * Machine Type (CATMACHTYPE)
	 * 
	 * If the preceding checks fail, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not available.
	 * 
	 * @param seoid
	 * @param lseoVct - Vector of LSEO that met check 1
	 * @param lseoList
	 * @param xai
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private boolean doLSEOChecks(String seoid, Vector lseoVct, 
			EntityList lseoList,ExtractActionItem xai) throws SQLException, MiddlewareException, 
			MiddlewareShutdownInProgressException
	{
		boolean hadError = false;

		addDebug("doLSEOChecks seoid: "+seoid);
		
		EntityGroup lseoGrp = lseoList.getParentEntityGroup();

		EntityItem lseoArray[] = new EntityItem[lseoVct.size()];
		lseoVct.copyInto(lseoArray);;
		for (int i=0; i<lseoArray.length; i++){ // only look at the LSEO for this seoid
			EntityItem eiLSEO =lseoArray[i];
			lseoVct.remove(eiLSEO); // replace the item in the vct with the one with extract info
			eiLSEO = lseoGrp.getEntityItem(eiLSEO.getKey()); // get extract version
			lseoVct.addElement(eiLSEO);
		
			Vector wwseoVct = PokUtils.getAllLinkedEntities(eiLSEO, "WWSEOLSEO", "WWSEO");
			Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
			addDebug("doLSEOChecks checking "+eiLSEO.getKey()+" wwseovct "+wwseoVct.size()+" mdlvct "+mdlVct.size());

			//2.	Verify that the LSEO has a grandparent MODEL with COFCAT = “Service” 
			boolean mdlfnd = false;
			for (int m = 0; m < mdlVct.size(); m++) {
				EntityItem eiMODEL = (EntityItem)mdlVct.elementAt(m);
				String cofcat = PokUtils.getAttributeFlagValue(eiMODEL, ATT_COFCAT);
				addDebug("doLSEOChecks checking " + eiMODEL.getKey()+" COFCAT:"+cofcat);
				if (COFCAT_Service.equals(cofcat)){// is Service 
					mdlfnd = true;
					break;
				}
			}

			if (!mdlfnd) {
				hadError = true;
				//SVCPAC_ERR2 = {0} SEOID is not a ServicePac. Grandparent Model is {1}
				args[0] = getLD_NDN(eiLSEO);
				String mdl = "Not found";
				if (mdlVct.size()>0){
					mdl = getLD_NDN((EntityItem)mdlVct.elementAt(0));
				}
				args[1] = mdl;
				addError("SVCPAC_ERR2",args);
				continue;
			}

			//3.	Verify that the LSEO (SEOID) is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
			//described in the prior section for the Generating ABR.
			// follow the LSEO's WWSEO to the compatible MODEL
			EntityItem[] wwseoArray = new EntityItem[wwseoVct.size()];
			wwseoVct.copyInto(wwseoArray);
			
			boolean bMTCompat = verifySEOtechCompat(wwseoArray,machtypeatr, xai);
			if (!bMTCompat) {
				hadError = true;
				//MACHTYPE_MSG = {0} SEOID is not compatible with this Machine Type {1}
				args[0] = getLD_NDN(eiLSEO);
				args[1] = machtypeatr;
				addError("MACHTYPE_MSG",args);
			}

			wwseoVct.clear();
			mdlVct.clear();
		}	
		
		return hadError;
	}

	/************
	 * make sure MACHTYPEATR and OFFCOUNTRY have values and also one of the lists has values
	 * 
	 * MACHTYPEATR	Machine Type	
	 * OFFCOUNTRY	Offering Country	
	 * LSEOIDLIST	LSEO SEOID List	Comma separated
	 * BDLSEOIDLIST	LSEO Bundle SEOID list	Comma separated
	 * 
	 */
	protected void verifyRequest(){
	    EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
	    
		offCountryFlag = PokUtils.getAttributeFlagValue(rootEntity, ATT_OFFCOUNTRY);
		offCountry = PokUtils.getAttributeValue(rootEntity, ATT_OFFCOUNTRY, "", PokUtils.DEFNOTPOPULATED, false);
		
		machtypeatr = PokUtils.getAttributeFlagValue(rootEntity,"MACHTYPEATR");
		
		String lseoList = PokUtils.getAttributeValue(rootEntity, "LSEOIDLIST", "", null, false);
		String bdlList = PokUtils.getAttributeValue(rootEntity, "BDLSEOIDLIST", "", null, false);
			
		addDebug("verifyRequest: "+rootEntity.getKey()+" offCountryFlag "+offCountryFlag+" offCountry "+offCountry+
				" machtypeatr "+machtypeatr+" lseoList "+lseoList+" bdlList "+bdlList);
		
		if (offCountryFlag==null){
			//MISSING_ERR = {0} is not specified.
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), ATT_OFFCOUNTRY, ATT_OFFCOUNTRY);
			addError("MISSING_ERR",args);
		}
		if (machtypeatr==null){
			//MISSING_ERR = {0} is not specified.
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR");
			addError("MISSING_ERR",args);
		}
		if(lseoList==null && bdlList==null){
			//MISSING_ERR = {0} is not specified.
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "LSEOIDLIST", "LSEOIDLIST")+
				" and "+PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "BDLSEOIDLIST", "BDLSEOIDLIST");
			addError("MISSING_ERR",args);
		}

		if(lseoList!=null){
			lseoSeoidsVct = verifySeoids("LSEOIDLIST", lseoList);
		}
		if(bdlList!=null){
			bdlSeoidsVct = verifySeoids("BDLSEOIDLIST", bdlList);
		}
	}
	
	/***************
	 * make sure no duplicate seoids within a single list and all are 7 chars long
	 * @param attrcode
	 * @param idlist
	 * @return
	 */
	private Vector verifySeoids(String attrcode, String idlist){
		Vector vct = new Vector();
        StringTokenizer st = new StringTokenizer(idlist,",");
        while(st.hasMoreTokens()) {
        	String seoid = st.nextToken().trim();
        	if (seoid.length()==7){
        		if (vct.contains(seoid)){
        			//DUPLICATE_SEOID_ERR = Duplicate SEOID {0} specified in {1}.
        			args[0] = seoid;
        			args[1] = PokUtils.getAttributeDescription(m_elist.getParentEntityGroup(), attrcode, attrcode);
        			addError("DUPLICATE_SEOID_ERR",args);
        		}else{
        			vct.addElement(seoid);
        		}
        	}else{
        		// INVALID_SEOID_ERR = Invalid SEOID {0} specified in {1}.
        		args[0] = seoid;
    			args[1] = PokUtils.getAttributeDescription(m_elist.getParentEntityGroup(), attrcode, attrcode);
    			addError("INVALID_SEOID_ERR",args);
        	}
        }
        
        return vct;
	}
	
	/**********************
	 * I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
	 * 2.	LSEOBUNDLE
	 * 	a)	If LSEOBUNDLE. BUNDLETYPE = “Hardware” (100) | “Software” (101), then report an error stating that this
	 * 	LSEO SEOID is not a ServicePac.
	 * 	b)	If OFFCOUNTRY NOT IN LSEOBUNDLE.COUNTRYLIST, then report an error stating that this LSEOBUNDLE is not
	 * 	available in OFFCOUNTRY.
	 * II.	Verify that the SEO has a “technical compatibility”:
	 * 1.	Must be in a SEOCGOS
	 * 		LSEOBUNDLE via SEOCGOSBDL-u
	 * 2.	Which will be in a SEOCG
	 * 		SEOCGOS via SEOCGSEOCGOS-u
	 * 3.	MACHTYPEATR must be in SEOCG from SEOCG via SEOCGMDL-d to MODEL.
	 * CATMACHTYPE must match a MODEL.MACHTYPEATR. Note that there may be several MODELs and they could have different
	 * MACHTYPEATR. CATMACHTYPE only has to match one of them to be legal.
	 * If a match is not found, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not compatible with this
	 * Machine Type (CATMACHTYPE)
	 * III.	Validate that the SEO is still available:
	 * 2.	LSEOBUNDLE
	 * 		BUNDLPUBDATEMTRGT <= NOW() <= BUNDLUNPUBDATEMTRGT If BUNDLUNPUBDATEMTRGT is empty, assume 9999-12-31
	 * If the preceding checks fail, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not available.
	 * 
	 * @param seoid
	 * @param xai
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private Vector doLSEOBDLChecks(String seoid ,ExtractActionItem xai) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		boolean hadError = false;
		addHeading(3,"Verify LSEOBUNDLE ServicePac SEOID "+seoid+" Availability");
		//I 2 b Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
		// and III	Validate that the SEO is still available:
		Vector bdlVct = findLSEOBDLForCheck1(seoid);

		if (bdlVct.size()>0){	
	   		ExtractActionItem dummyxai = new ExtractActionItem(null, m_db, m_prof, "dummy");
	   		EntityItem[] eiArray = new EntityItem[bdlVct.size()];
	   		bdlVct.copyInto(eiArray);
	   		bdlVct.clear();

			EntityList el = EntityList.getEntityList(m_db, m_prof, dummyxai, eiArray);
			addDebug("Extract for LSEOBUNDLEs attr chks "+PokUtils.outputList(el));

			for (int j = 0; j < el.getParentEntityGroup().getEntityItemCount(); j++) {
				EntityItem lseobdl = el.getParentEntityGroup().getEntityItem(j);
				String bdltype = getAttributeFlagEnabledValue(lseobdl, ATT_BUNDLETYPE);
				addDebug("doLSEOBDLChecks "+lseobdl.getKey()+" bdltype "+bdltype);
				//I 2 a)	If LSEOBUNDLE. BUNDLETYPE = “Hardware” (100) | “Software” (101), then report an error stating that this 
				//LSEO SEOID is not a ServicePac. 
				if(!COFCAT_Service.equals(bdltype)){
					hadError = true;
					//SVCPAC_ERR = {0} SEOID is not a ServicePac.  {1} and {2}
					args[0] = getLD_NDN(lseobdl);
					args[1] = getLD_Value(lseobdl, ATT_BUNDLETYPE);
					args[2] = getLD_Value(lseobdl, ATT_SVCPACBNDLTYPE);
					addError("SVCPAC_ERR",args);
					continue;
				}

				//II.	Verify that the SEO has a “technical compatibility”:
				addHeading(3,"Verify LSEOBUNDLE ServicePac SEOID "+seoid+" Technical Compatibility");
				//Verify that the LSEOBUNDLE is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE)
				boolean bMTCompat = verifySEOtechCompat(
						new EntityItem[] { new EntityItem(null, m_prof, lseobdl.getEntityType(),
								lseobdl.getEntityID()) },machtypeatr, xai);
				if (!bMTCompat) {
					hadError = true;
					//MACHTYPE_MSG = {0} SEOID is not compatible with this Machine Type {1}
					args[0] = getLD_NDN(lseobdl);
					args[1] = machtypeatr;
					addError("MACHTYPE_MSG",args);
					continue;
				}
				bdlVct.add(lseobdl); // put the one from the extract into the vct
			}
			// do not deref list here unless there were errors, need info from lseobdl
			if (hadError){
				if (bdlVct.size()>0){
    				EntityItem item = (EntityItem)bdlVct.firstElement();
    				item.getEntityGroup().getEntityList().dereference(); // deref here, done with items
    				bdlVct.clear();
    			}
			}
		}else{// nothing found
			hadError = true;
		}

		return bdlVct;
	}

	/**********************
	 * search for existing CATLGSVCPACPRESEL with matching CATMACHTYPE, OFFCOUNTRY and CATSEOID
	 * 
	 * For each MACHTYPEATR, OFFCOUNTRY, SEOID; check to see if it is already in the data (CATLGSVCPACPRESEL).
	 * match LSEOIDLIST with PRESELSEOTYPE=LSEO and
	 * BDLSEOIDLIST with PRESELSEOTYPE=LSEOBUNDLE (default to LSEO)
	 * 
	 * SVCPACPRESELREQ	Catalog ServicePac PreSelect Request
	 * 
	 * Attribute	Description	Comment
	 * MACHTYPEATR	Machine Type
	 * OFFCOUNTRY	Offering Country
	 * LSEOIDLIST	LSEO SEOID List	Comma separated
	 * BDLSEOIDLIST	LSEO Bundle SEOID list	Comma separated
	 * 	
	 * @param isLseo if true, use lseolist
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private Vector searchForCATLGSVCPACPRESEL(boolean isLseo)
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector seoidsVct = new Vector();
	
		Vector attrVct = new Vector(2);
		attrVct.addElement(ATT_OFFCOUNTRY);
		attrVct.addElement(ATT_CATMACHTYPE);
		attrVct.addElement(ATT_CATSEOID);
		
		Vector valVct = new Vector(2);
		valVct.addElement(offCountryFlag);
		valVct.addElement(machtypeatr);
		
		Vector vct = bdlSeoidsVct;
		if (isLseo){
			vct=lseoSeoidsVct;
		}
		for (int x=0; x<vct.size(); x++){
			String catseoid = vct.elementAt(x).toString();
			valVct.addElement(catseoid);
			
			addDebug("searchForCATLGSVCPACPRESEL searching for OFFCOUNTRY "+
					offCountryFlag+" CATMACHTYPE "+machtypeatr+" CATSEOID "+catseoid+" check PRESELSEOTYPE "+
					(isLseo?PRESELSEOTYPE_LSEO:PRESELSEOTYPE_LSEOBDL));
			
			try{
				StringBuffer debugSb = new StringBuffer();
				// use EOD for prof timestamps
				COM.ibm.opicmpdh.middleware.Profile eodProf = m_prof.getNewInstance(m_db);
				String eod = m_db.getDates().getEndOfDay();
				eodProf.setValOnEffOn(eod, eod);
				addDebug("Set tmp profile to eod "+eod);
				
				EntityItem eia[] = ABRUtil.doSearch(getDatabase(), eodProf, 
						"SRDCATLGSVCPACPRESEL1", "CATLGSVCPACPRESEL", false, attrVct, valVct, debugSb);
				if (debugSb.length()>0){
					addDebug(debugSb.toString());
				}
				if (eia!=null && eia.length > 0){			
					for (int i=0; i<eia.length; i++){
						// check PRESELSEOTYPE
						String preselseotype = PokUtils.getAttributeFlagValue(eia[i], ATT_PRESELSEOTYPE);
						addDebug("searchForCATLGSVCPACPRESEL found "+eia[i].getKey()+" for CATSEOID "+
								catseoid+" with PRESELSEOTYPE "+preselseotype);

						if (isLseo){
							args[0] = this.getLD_NDN(eia[i]);
							args[1] = this.getLD_Value(eia[i], ATT_PRESELSEOTYPE);
							args[2] = catseoid;
							if(PRESELSEOTYPE_LSEOBDL.equals(preselseotype)){
								//mismatch - was lseobundle
								//MISMATCH_ERR = {0} {1} currently exists for SEO {2} but does not match {3} request.
								args[3] = "LSEO";
								addError("MISMATCH_ERR",args);
							}else{
								//EXISTS_ERR = {0} {1} currently exists for SEO {2}.
								addError("EXISTS_ERR",args);
							}
						}else { // was bundle request
							args[0] = this.getLD_NDN(eia[i]);
							args[1] = this.getLD_Value(eia[i], ATT_PRESELSEOTYPE);
							args[2] = catseoid;
							if (PRESELSEOTYPE_LSEO.equals(preselseotype) || preselseotype==null){
								//mismatch - was lseo, null is default to lseo
								//MISMATCH_ERR = {0} {1} currently exists for SEO {2} but does not match {3} request.
								args[3] = "LSEOBUNDLE";
								addError("MISMATCH_ERR",args);
							}else{
								//EXISTS_ERR = {0} {1} currently exists for SEO {2}.
								addError("EXISTS_ERR",args);
							}
						}
					}
				}else{
					seoidsVct.add(catseoid);
				}
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				addDebug("searchForCATLGSVCPACPRESEL SBRException: "+exBuf.getBuffer().toString());
			}
			
			valVct.removeElement(catseoid);
		}
		
		attrVct.clear();
		valVct.clear();
		
		return seoidsVct;
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){ 
		machtypeatr = null;
		if (bdlSeoidsVct!=null){
			bdlSeoidsVct.clear();
			bdlSeoidsVct = null;
		}
		if (lseoSeoidsVct!=null){
		    lseoSeoidsVct.clear();
		    lseoSeoidsVct = null;
		}
	    
        super.dereference();
	}

	/**
	 *  Get ABR description
	 *
	 *@return    java.lang.String
	 */
	public String getDescription() {
		return "Service Pack Preselect Generator ABR";
	}

	/**
	 * getRevision
	 *
	 * @return
	 */
	public String getRevision() {
		return "$Revision: 1.1 $";
	}

	/**
	 * getVersion
	 *
	 * @return
	 */
	public static String getVersion() {
		return "$Id: SPPSABRGEN.java,v 1.1 2010/02/08 14:51:46 wendy Exp $";
	}

	/**
	 * getABRVersion
	 *
	 * @return
	 */
	public String getABRVersion() {
		return "SPPSABRGEN.java";
	}
}
