// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
/****
 * BH FS ABR AVAIL AutoGen 20100426.doc
 * 		- feature countrylist check chgs
 * BH FS ABR AVAIL AutoGen 20100413.doc
 * 		- needs new attr AUTOAVAIL.MTMLIST
 * 		- updated VE EXAAMODEL
 * 
 * BH FS ABR AVAIL AutoGen 20100301.doc
 * 
 * The UI will allow users to either run this ABR for one or more Availability Generation (AUTOAVAIL). 
 * Each requested Availability Generation is handled by a separate instance of this ABR.
 *
 *IV.	Invocation

A user will be able to work with an Announcement (ANNOUNCEMENT) and one or more Availability Generation 
(AUTOAVAIL) requests.

The Availability Generation request has the following attributes:
AUTOAVAILABRSTATUS	A
AVAILTYPE	U
COMMENTS	L
COMNAME	T
COUNTRYLIST	F
EFFECTIVEDATE	T
GENAREASELECTION	F
MACHTYPEATR	U
MODELATR	T
ORDERSYSNAME	U
PDHDOMAIN	F

 
VI.	FUNCTIONALITY

This function is limited to creating Availability (AVAIL) for the requested Machine Type Model (MODEL) 
and Product Structure (PRODSTRUCT)  for MODELs where COFCAT = “Hardware” (100) and COFGRP = “Base” (150).

The function does not consider any Availability (AVAIL) that may already be referenced by the 
Machine Type Model and/or Product Structure (aka TMF). It simply adds Availability based on the 
Availability Generation request.

The function is limited to Availability Type (AVAILTYPE) “Planned Availability” and “Last Order”.

The Availability of the Model will be exactly as specified via the request; however, the Availability 
of the Product Structure may be limited to a subset of the Country List based on the Feature’s Country List.

CAT1= “AUTOAVAILABR”
CAT2= value of (AUTOAVAIL.PDHDOMAIN)
CAT3=RptStatus (aka Return Code)
CAT4= 


AUTOAVAILABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.AUTOAVAILABRSTATUS
AUTOAVAILABRSTATUS_enabled=true
AUTOAVAILABRSTATUS_idler_class=A
AUTOAVAILABRSTATUS_keepfile=true
AUTOAVAILABRSTATUS_read_only=false
AUTOAVAILABRSTATUS_vename=EXAUTOAVAIL
AUTOAVAILABRSTATUS_CAT1=AUTOAVAILABR
AUTOAVAILABRSTATUS_CAT2=ROOTTYPE.PDHDOMAIN
AUTOAVAILABRSTATUS_CAT3=RPTSTATUS
 */
// $Log: AUTOAVAILABRSTATUS.java,v $
// Revision 1.10  2014/01/13 13:53:29  wendy
// migration to V17
//
// Revision 1.9  2010/12/18 01:43:35  wendy
// Enhance msg
//
// Revision 1.8  2010/07/21 17:19:53  wendy
// MN43681843 outofmemory exception, break up VE pulls
//
// Revision 1.7  2010/04/27 15:06:49  wendy
// BH FS ABR AVAIL AutoGen 20100426b.doc updates
//
// Revision 1.6  2010/04/26 20:04:30  wendy
// BH FS ABR AVAIL AutoGen 20100426.doc updates
//
// Revision 1.5  2010/04/22 11:19:46  wendy
// BH FS ABR AVAIL AutoGen 20100413.doc updates
//
// Revision 1.4  2010/04/07 14:21:57  wendy
// spec chg BH FS ABR AVAIL AutoGen 20100406.doc
//
// Revision 1.3  2010/03/17 15:51:01  wendy
// add more debug and allow match on null ORDERSYSNAME
//
// Revision 1.2  2010/03/17 12:27:36  wendy
// null avail if no match found
//
// Revision 1.1  2010/03/11 12:25:32  wendy
// Init for BH0.5 AVAIL Autogen
//
public class AUTOAVAILABRSTATUS extends PokBaseABR {

    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private Object[] args = new String[10];

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    //private Hashtable availTbl = new Hashtable();  // key is GENAREASELECTION+COUNTRYLIST, value is the avail
    
    private EntityItem genAvail = null;
    
    private static final String MODEL_SRCHACTION_NAME = "SRDMODEL16"; //  - domain restricted 

    private static final String MODEL_AVAIL_CREATEACTION_NAME  = "CRPEERAVAIL";      
    private static final String PS_AVAIL_CREATEACTION_NAME  = "CRPEERAVAIL2";  
    private static final String OOFAVAIL_LINKACTION_NAME  = "LINKAVAILPRODSTRUCT2"; 
    private static final String MODELAVAIL_LINKACTION_NAME  = "LINKAVAILMODEL"; 
    
    // attributes to copy from autoavail to avail
    private static final String[] AALIST_ATTR = {
    	"COMNAME","AVAILTYPE","EFFECTIVEDATE","ORDERSYSNAME","PDHDOMAIN","GENAREASELECTION","COUNTRYLIST"
    	}; 
    
    private static final String HARDWARE = "100";
    private static final String BASE = "150";
	private static final String ORDERCODE_INITIAL = "5957"; //ORDERCODE 5957	I	Initial

	private static final String PLANNEDAVAIL = "146";
	private static final String LASTORDERAVAIL = "149";

	/*
FCTYPE	100	Primary FC
FCTYPE	110	Secondary FC
FCTYPE	120	RPQ-ILISTED
FCTYPE	130	RPQ-PLISTED
FCTYPE	0140	RPQ-RLISTED
FCTYPE	402	Placeholder
	*/
	private static final String RPQ_ILISTED = "120";
	private static final String RPQ_PLISTED = "130";
	private static final String RPQ_RLISTED = "0140";
	private static final String PLACEHOLDER = "402";
 	private static final Set FCTYPE_SET;
	private static final Hashtable NDN_TBL;
 	static{
 		FCTYPE_SET = new HashSet();
 		FCTYPE_SET.add(RPQ_ILISTED);
 		FCTYPE_SET.add(RPQ_PLISTED);
 		FCTYPE_SET.add(RPQ_RLISTED);
 		FCTYPE_SET.add(PLACEHOLDER);

		NDN_TBL = new Hashtable();
	/*
The NDN of PRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
FEATURE.FEATURECODE
	*/
		NDN ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		NDN ndnFc = new NDN("FEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("PRODSTRUCT",ndnMdl);
	}
 	
    private EntityGroup modelEG = null;
	
    /**********************************
     *  Execute ABR.
     */
    public void execute_run()
    {
        /*
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
			Any errors or list LSEO created or changed
        */
        // must split because too many arguments for messageformat, max of 10.. this was 11
        String HEADER = "<head>"+
             EACustom.getMetaTags(getDescription()) + NEWLINE +
             EACustom.getCSS() + NEWLINE +
             EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
             EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
             "<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
             "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
             "<tr><th>Return code: </th><td>{5}</td></tr>"+NEWLINE +
             "</table>"+NEWLINE+
            "<!-- {6} -->" + NEWLINE;

        MessageFormat msgf;
        String rootDesc="";
        String abrversion="";

        println(EACustom.getDocTypeHtml()); //Output the doctype and html

        try
        {
        	long startTime = System.currentTimeMillis();
            start_ABRBuild(); // pull VE

            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
            // get root from VE
            EntityItem autoEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +autoEntity.getKey()+
                " extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            addDebug("Time to pull root VE: "+Stopwatch.format(System.currentTimeMillis()-startTime));

            //Default set to pass
            setReturnCode(PASS);
//fixme remove this.. avoid msgs to userid for testing
// setCreateDGEntity(false);

            //NAME is navigate attributes
            navName = getNavigationName(autoEntity);
        	EntityItem annItem = m_elist.getEntityGroup("ANNOUNCEMENT").getEntityItem(0); // must exist
            rootDesc = m_elist.getParentEntityGroup().getLongDescription()+" &quot;"+navName+"&quot;"+
            		" <br />for "+getLD_NDN(annItem);
            
            //check values and find model
            Vector mdlVct  = validateSetup(autoEntity, annItem);
            if(getReturnCode()== PokBaseABR.PASS){  
            	String availType = PokUtils.getAttributeFlagValue(autoEntity, "AVAILTYPE");

            	for (int m=0; m<mdlVct.size(); m++){
            		// pull a VE from the MODELs to PRODSTRUCT, FEATURE 
            		//	EntityItem mdlArray[] = new EntityItem[mdlVct.size()];
            		//mdlVct.copyInto(mdlArray);  MN43681843 got outofmemory, do them one at a time now
                   	long mdlTime = System.currentTimeMillis();
                   	
            		EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
            		EntityList mdlList = m_db.getEntityList(m_elist.getProfile(),
            				new ExtractActionItem(null, m_db, m_elist.getProfile(), "EXAAMODEL"),
            				new EntityItem[]{mdlitem});
            		//mdlArray);
            		addDebug(" Extract EXAAMODEL "+NEWLINE+PokUtils.outputList(mdlList));

            		if (PLANNEDAVAIL.equals(availType)){
            			createPlannedAvails(autoEntity,mdlList,annItem);
            		}else{
            			createLastOrderAvails(autoEntity,mdlList,annItem);
            		}
                    addDebug("Time for "+mdlitem.getKey()+": "+Stopwatch.format(System.currentTimeMillis()-mdlTime));
                    
            		mdlList.dereference();
                }
            	mdlVct.clear();
            }
                       
            addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
        }
        catch(Throwable exc) {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
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
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if(!isReadOnly())
            {
                clearSoftLock();
            }
        }
        
        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getDescription();
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = rootDesc;
        args[5] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
        args[6] = abrversion+" "+getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>

        metaTbl.clear();
    }
 
    /**
     * C.	AVAIL for MODEL
     * 	If AUTOAVAIL.AVAILTYPE = “Planned Availability” (146) then
     * 		Create and reference the AVAIL via MODELAVAIL
     * 
     * D.	PRODSTRUCT Eligibility
     * 	If AUTOAVAIL.AVAILTYPE = “Planned Availability” (146) then
     * 		If PRODSTRUCT.ANNDATE  is Not Null and PRODSTRUCT.ANNDATE = ANNOUNCEMENT.ANNDATE AND  
     * 			IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130), “Placeholder” (402) 
     * 			then create/reference AVAIL (see next section)
     * 		OR
     * 		If PRODSTRUCT.ANNDATE  is Null and PRODSTRUCT-u: FEATURE.FIRSTANNDATE <=  ANNOUNCEMENT.ANNDATE AND 
     * 			IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130) ), “Placeholder” (402)
     * 			then create/reference AVAIL (see next section)
     * 
     * E.	PRODSTRUCT Create/Reference AVAIL
     * If the prior section indicates that the ABR should create/reference an AVAIL, then proceed with this section.
     * If all countries in the AUTOAVAIL.COUNTRYLIST are in the PRODSTRUCT-u: FEATURE.COUNTRYLIST  
     * 	(Note:  the GENAREASELECTIONs do not have to match)
     * 	Then
     * 		1.	Create / Reference the Availability (AVAIL) for this AUTOAVAIL.
     * 		2.	Done with this PRODSTRUCT
     * 	Else
     * 		Do NOT create/reference an AVAIL
     * 
     * @param mdlList
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws EANBusinessRuleException 
     * @throws RemoteException 
     */
    private void createPlannedAvails(EntityItem autoEntity, EntityList mdlList, EntityItem annItem) throws 
    SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, 
    MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
    	LinkActionItem lai = new LinkActionItem(null, m_db,m_prof,OOFAVAIL_LINKACTION_NAME);
    	LinkActionItem mlai = new LinkActionItem(null, m_db,m_prof,MODELAVAIL_LINKACTION_NAME);
        String annCodeName = PokUtils.getAttributeFlagValue(annItem, "ANNCODENAME");
        String anndate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", "", false);
		ArrayList autoCtry = getCountriesAsList(autoEntity);  
		
        addDebug("createPlannedAvails "+annItem.getKey()+" ANNCODENAME: "+annCodeName+" ANNDATE: "+anndate);
        AttrSet attrSet = new AttrSet(autoEntity, annCodeName);
     
    	EntityGroup mdlGrp = mdlList.getParentEntityGroup();
        //	Create and reference the AVAIL via MODELAVAIL
    	for (int m=0; m<mdlGrp.getEntityItemCount(); m++){
    		EntityItem mdlItem = mdlGrp.getEntityItem(m);
    		createOrRefAvail(mdlItem, MODEL_AVAIL_CREATEACTION_NAME, attrSet,mlai);
    	}
    	// check prodstructs
    	EntityGroup psGrp = mdlList.getEntityGroup("PRODSTRUCT");
    	for (int i=0; i<psGrp.getEntityItemCount(); i++){
    		EntityItem psitem = psGrp.getEntityItem(i);
    		if(checkPsEligibility(true, psitem, anndate)){
    			EntityItem featItem = (EntityItem)psitem.getUpLink(0);
    			ArrayList featCtry = getCountriesAsList(featItem); 
    			if (featCtry.containsAll(autoCtry)){
    				addDebug("createPlannedAvails "+featItem.getKey()+" countries contains all autoavail ctrys");
    				/*
    				* If all countries in the AUTOAVAIL.COUNTRYLIST are in the PRODSTRUCT-u: FEATURE.COUNTRYLIST 
    				* Then
    				* 	1.	Create / Reference the Availability (AVAIL) for this AUTOAVAIL.
    				* 	2.	Done with this PRODSTRUCT
    				*/
    				createOrRefAvail(psitem, PS_AVAIL_CREATEACTION_NAME, attrSet,lai);
    			}else{
    				/* Else
    				 * Do NOT create/reference an AVAIL
    				 */
    				addDebug("createPlannedAvails skipping "+psitem.getKey()+" "+featItem.getKey()+" ctrylist "+
    						featCtry+" does not contain all of autoavail "+autoCtry);
    				ArrayList autoCtryDesc = getCountryDescAsList(autoEntity);
    				ArrayList featCtryDesc = getCountryDescAsList(featItem);
    				autoCtryDesc.removeAll(featCtryDesc); // find what is left over
    				
    				//SKIPPED_FCCTRY_MSG = {0} is ineligible to reference an Avail because {1} {2} does not contain all of the {3} {4}
    				/*args[0] = this.getLD_NDN(psitem);
    				args[1] = this.getLD_NDN(featItem);
    				args[2] = this.getLD_Value(featItem, "COUNTRYLIST");
    				args[3] = autoEntity.getEntityGroup().getLongDescription();
    				args[4] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG", args));*/
    				
    				//SKIPPED_FCCTRY_MSG2 = {0} is ineligible to reference an Avail because {1} {2} does not contain these values in the {3} {4} {5}
    				args[0] = this.getLD_NDN(psitem);
    				args[1] = this.getLD_NDN(featItem);
    				args[2] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				args[3] = autoEntity.getEntityGroup().getLongDescription();
    				args[4] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				args[5] = autoCtryDesc.toString();
    				addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG2", args));
    				
    				featCtryDesc.clear();
    		    	autoCtryDesc.clear();
    			}
    			featCtry.clear();
    		}else{
    			addDebug("createPlannedAvails skipping ineligible "+psitem.getKey());
    		}
    	}
    	

    	autoCtry.clear();
    	attrSet.dereference();
    	attrSet = null;
    }
    
    /**
     * create the avail if it doesnt already exist
     * It will create a Last Order AVAIL for the PRODSTRUCT - it will never use an existing AVAIL - 
     * only those that are created during a single execution of the ABR can be reused (referenced)
     * Wendy: so if a MODEL already has an AVAIL that matches AUTOAVAIL, but PRODSTRUCT does not, 
     * 	I create a duplicate AVAIL and link it to the PRODSTRUCT?
     * Wayne Kehrli: yes
     * Wayne Kehrli: the MODEL will be world wide
     * Wayne Kehrli: the prodstructs will not
     * Wayne Kehrli: i.e. most likely
     * @param parent
     * @param actionName
     * @param attrSet
     * @param lai
     * @throws MiddlewareRequestException
     * @throws RemoteException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException
     * @throws LockException
     * @throws WorkflowException
     */
    private void createOrRefAvail(EntityItem parent, String actionName, AttrSet attrSet,
    		LinkActionItem lai) 
    throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
    	if (genAvail==null){ // create and ref
    		addDebug("createOrRefAvail: No previous avail created");
    		long startTime = System.currentTimeMillis();
    		StringBuffer debugSb = new StringBuffer();
    		genAvail = ABRUtil.createEntity(m_db, m_prof, actionName, parent,  
    				"AVAIL", attrSet.getAttrCodes(), attrSet.getAttrValues(),  debugSb);
    		if (debugSb.length()>0){
    			addDebug(debugSb.toString());
    		}
    		if(genAvail!=null){
    			addDebug("createOrRefAvail: Time to create "+genAvail.getKey()+
    					" with parent "+parent.getKey()+" "+Stopwatch.format(System.currentTimeMillis()-startTime));

    			//CREATED_MSG = Created and referenced {0} for {1}
    			args[0] = this.getLD_NDN(genAvail);
    			args[1] = this.getLD_NDN(parent);
    			addOutput(getResourceMsg("CREATED_MSG", args));
    		}else{
    			// CREATE_ERR = Unable to generate {0}
    			args[0] = parent.getEntityGroup().getEntityList().getEntityGroup("AVAIL").getLongDescription();
    			addError("CREATE_ERR",args);
    		}
    	}else{ // just ref
    		addDebug("createOrRefAvail: referencing previous "+genAvail.getKey());
    		//link the avail to the ps
    		long startTime = System.currentTimeMillis();
    		EntityItem parentArray[] = new EntityItem[]{parent};
    		EntityItem childArray[] = new EntityItem[]{genAvail};

    		// do the link
    		lai.setParentEntityItems(parentArray);
    		lai.setChildEntityItems(childArray);
    		m_db.executeAction(m_prof, lai);

    		addDebug("createOrRefAvail: Time to link "+parent.getKey()+" to "+genAvail.getKey()+": "+Stopwatch.format(System.currentTimeMillis()-startTime));

    		//REFFED_MSG = Referenced {0} for {1}
    		args[0] = this.getLD_NDN(genAvail);
    		args[1] = this.getLD_NDN(parent);
    		addOutput(getResourceMsg("REFFED_MSG", args));
    	}
    }

    /**
     * D.	PRODSTRUCT Eligibility
     * If AUTOAVAIL.AVAILTYPE = “Planned Availability” (146) then
     *		If PRODSTRUCT.ANNDATE  is Not Null and PRODSTRUCT.ANNDATE = ANNOUNCEMENT.ANNDATE AND  
     *		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130),“Placeholder” (402)  then create/reference AVAIL
     *		OR
     *		If PRODSTRUCT.ANNDATE  is Null and PRODSTRUCT-u: FEATURE.FIRSTANNDATE <=  ANNOUNCEMENT.ANNDATE AND 
     *		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130),“Placeholder” (402)  then create/reference AVAIL
     * 
     * If AUTOAVAIL.AVAILTYPE = “Last Order” (149) Then
     * 		If PRODSTRUCT. WTHDRWEFFCTVDATE is Not Null and PRODSTRUCT. WTHDRWEFFCTVDATE = ANNOUNCEMENT.ANNDATE  AND 
     * 		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130),“Placeholder” (402)  then create/reference AVAIL
     * 		OR
     * 		If PRODSTRUCT. WTHDRWEFFCTVDATE is Null  and ORDERCODE=”Initial”(5957) AND 
     * 		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130),“Placeholder” (402)   then create/reference AVAIL
     * 		Note: For any PRODSTRUCT that is “MES” or “BOTH” user has to create/reference Avail manually.
     * 
     * @param isPlaAvail
     * @param psitem
     * @param anndate
     * @return
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private boolean checkPsEligibility(boolean isPlaAvail, EntityItem psitem, String anndate) 
    throws SQLException, MiddlewareException
    {
    	boolean isok = false;
    	String attrcode = "ANNDATE";
    	if (!isPlaAvail){
    		attrcode = "WTHDRWEFFCTVDATE";
    	}
       	// check FCTYPE
  		// find the FEATURE
		EntityItem featItem = (EntityItem)psitem.getUpLink(0);
		//IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130),“Placeholder” (402)  then create/reference AVAIL
    	if (PokUtils.contains(featItem, "FCTYPE", FCTYPE_SET)){
    	  	addDebug("checkPsEligibility "+psitem.getKey()+" ineligible "+featItem.getKey()+" FCTYPE:"+
    	  			PokUtils.getAttributeFlagValue(featItem, "FCTYPE"));
        	//SKIPPED_MSG = {0} will not have an Avail referenced because {1} 
    		args[0] = this.getLD_NDN(psitem);
			args[1] = this.getLD_NDN(featItem)+" has "+this.getLD_Value(featItem, "FCTYPE");
			addOutput(getResourceMsg("SKIPPED_MSG", args));
        	return isok;
    	}
    	
    	String date = PokUtils.getAttributeValue(psitem, attrcode, "", null, false);
    	String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
        
        addDebug("checkPsEligibility "+psitem.getKey()+" "+attrcode+": "+date+" ordercode "+ordercode);
    	if(date==null){
    		if(isPlaAvail){
    			//If PRODSTRUCT.ANNDATE  is Null and PRODSTRUCT-u: FEATURE.FIRSTANNDATE <=  ANNOUNCEMENT.ANNDATE 
    			attrcode = "FIRSTANNDATE";
    			date = PokUtils.getAttributeValue(featItem, attrcode, "", m_strForever, false);
        		isok = date.compareTo(anndate)<=0;
                if(!isok){
                    addDebug("checkPsEligibility "+featItem.getKey()+" "+attrcode+": "+date);
                	//SKIPPED_DATE_MSG2 = {0} will not have an Avail referenced because {1} {2} is after Announcement {3}
            		args[0] = this.getLD_NDN(psitem);
        			args[1] = this.getLD_NDN(featItem);
        			args[2] = this.getLD_Value(featItem, attrcode);
        			args[3]= anndate;
        			addOutput(getResourceMsg("SKIPPED_DATE_MSG2", args));
                }
    		}else{
    			//If PRODSTRUCT.WTHDRWEFFCTVDATE is Null  and ORDERCODE=”Initial”(5957)
    			if (!ORDERCODE_INITIAL.equals(ordercode)){
    				addDebug("checkPsEligibility "+psitem.getKey()+" ineligible lastorderavail ordercode "+ordercode);
    				//SKIPPED_MSG = {0} will not have an Avail referenced because {1} 
    				args[0] = this.getLD_NDN(psitem);
    				args[1] = this.getLD_Value(psitem, "ORDERCODE");
    				addOutput(getResourceMsg("SKIPPED_MSG", args));
    			}else{
    				isok=true;
    			}
    		}
    	}else{    	  
    		//LastOrderAvail:If PRODSTRUCT.WTHDRWEFFCTVDATE is Not Null and PRODSTRUCT.WTHDRWEFFCTVDATE = ANNOUNCEMENT.ANNDATE
    		//PlannedAvail: If PRODSTRUCT.ANNDATE is Not Null and PRODSTRUCT.ANNDATE = ANNOUNCEMENT.ANNDATE then create/reference AVAIL
    		isok = date.equals(anndate);
    		if(!isok){
    			//SKIPPED_DATE_MSG = {0} will not have an Avail referenced because {1} does not match Announcement {2} 
    			args[0] = this.getLD_NDN(psitem);
    			args[1] = this.getLD_Value(psitem, attrcode);
    			args[2]= anndate;
    			addOutput(getResourceMsg("SKIPPED_DATE_MSG", args));
    		}
    	}
    		
    	return isok;
    }
    
    /**
     * C.	AVAIL for MODEL
     * This ABR does NOT create a “Last Order” for a MODEL
     * 
     * D.	PRODSTRUCT Eligibility
     * If AUTOAVAIL.AVAILTYPE = “Last Order” (149) then
     * 	If PRODSTRUCT. WTHDRWEFFCTVDATE is Not Null and PRODSTRUCT. WTHDRWEFFCTVDATE = ANNOUNCEMENT.ANNDATE  AND 
     * 		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130) ), “Placeholder” (402)  
     * 		then create/reference AVAIL (see next section)
     * 	OR
     * 	If PRODSTRUCT. WTHDRWEFFCTVDATE is Null  and the ORDERCODE=”Initial”(5957) AND 
     * 		IF FEATURE.FCTYPE<>” RPQ-RLISTED”( 0140),”RPQ-ILISTED”(120), “RPQ-PLISTED”(130) ), “Placeholder” (402)  
     * 		then create/reference AVAIL (see next section)
     * 		Note: For any PRODSTRUCT that is “MES” or “BOTH” user has to create/reference Avail manually.
     * 
     * E.	PRODSTRUCT Create/Reference AVAIL
     * If the prior section indicates that the ABR should create/reference an AVAIL, then proceed with this section.
     *	If PRODSTRUCT-u: FEATURE.COUNTRYLIST is identical with AUTOAVAIL.COUNTRYLIST
     * 		(Note:  the GENAREASELECTIONs do not have to match)
     * 		Then
     * 		1.	Create / Reference the Availability (AVAIL) for this AUTOAVAIL.
     * 		2.	Done with this PRODSTRUCT
     * Else
     * 	Do NOT create/reference an AVAIL
     * 
     * @param mdlList
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
     * @throws WorkflowException 
     * @throws LockException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws EANBusinessRuleException 
     * @throws RemoteException 
     */
    private void createLastOrderAvails(EntityItem autoEntity, EntityList mdlList,EntityItem annItem) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
    	LinkActionItem lai = new LinkActionItem(null, m_db,m_prof,OOFAVAIL_LINKACTION_NAME);
        String annCodeName = PokUtils.getAttributeFlagValue(annItem, "ANNCODENAME");
        String anndate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", "", false);
		ArrayList autoCtry = getCountriesAsList(autoEntity);  
		
        addDebug("createLastOrderAvails "+annItem.getKey()+" ANNCODENAME: "+annCodeName+" ANNDATE: "+anndate);
        AttrSet attrSet = new AttrSet(autoEntity, annCodeName);

    	// check prodstructs
    	EntityGroup psGrp = mdlList.getEntityGroup("PRODSTRUCT");
    	for (int i=0; i<psGrp.getEntityItemCount(); i++){
    		EntityItem psitem = psGrp.getEntityItem(i);
    		if(checkPsEligibility(false, psitem, anndate)){
    			EntityItem featItem = (EntityItem)psitem.getUpLink(0);
    			ArrayList featCtry = getCountriesAsList(featItem); 
    			if (featCtry.containsAll(autoCtry)){
    				addDebug("createLastOrderAvails "+featItem.getKey()+" countries contains all autoavail ctrys");
    				/*
    				*If all countries in the AUTOAVAIL.COUNTRYLIST are in the PRODSTRUCT-u: FEATURE.COUNTRYLIST  
    				* Then
    				* 1.	Create / Reference the Availability (AVAIL) for this AUTOAVAIL.
    				* 2.	Done with this PRODSTRUCT
    				*/
    				createOrRefAvail(psitem, PS_AVAIL_CREATEACTION_NAME, attrSet,lai);
    			}else{
    				/* Else
    				 * Do NOT create/reference an AVAIL
    				 */
    				addDebug("createLastOrderAvails skipping "+psitem.getKey()+" "+featItem.getKey()+" ctrylist "+
    						featCtry+" does not contain all of autoavail "+autoCtry);
    				ArrayList autoCtryDesc = getCountryDescAsList(autoEntity);
    				ArrayList featCtryDesc = getCountryDescAsList(featItem);
    				autoCtryDesc.removeAll(featCtryDesc); // find what is left over
    				//SKIPPED_FCCTRY_MSG = {0} is ineligible to reference an Avail because {1} {2} does not contain all of the {3} {4}
    				/*args[0] = this.getLD_NDN(psitem);
    				args[1] = this.getLD_NDN(featItem);
    				args[2] = this.getLD_Value(featItem, "COUNTRYLIST");
    				args[3] = autoEntity.getEntityGroup().getLongDescription();
    				args[4] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG", args));
    				*/
    				//SKIPPED_FCCTRY_MSG2 = {0} is ineligible to reference an Avail because {1} {2} does not contain these values in the {3} {4} {5}
    				args[0] = this.getLD_NDN(psitem);
    				args[1] = this.getLD_NDN(featItem);
    				args[2] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				args[3] = autoEntity.getEntityGroup().getLongDescription();
    				args[4] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    				args[5] = autoCtryDesc.toString();
    				addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG2", args));
    				
    				featCtryDesc.clear();
    		    	autoCtryDesc.clear();
    			}
    		}else{
    			addDebug("createLastOrderAvails skipping ineligible "+psitem.getKey());
    		}
    	}
    	
    	autoCtry.clear();
    	attrSet.dereference();
    	attrSet = null;
    }
    
    /*************************
     * Verify valid values are in the setup entity
     * 
     * A.	Checking
     * 1.	AUTOAVAIL.COUNTRYLIST must be a subset of the parent ANNOUNCEMENT.COUNTRYLIST.
     * 2.	AUTOAVAIL.AVAILTYPE must be either:
     * 	a.	Planned Availability (146)
     * 	b.	Last Order (149)
     * 3.	MODEL must exist where:
     * 	a.	MODEL.MACHTYPEATR & MODEL.MODELATR IN AUTOAVAIL.MTMLIST
     * 	b.	MODEL.COFCAT = “Hardware” (100)
     * 	c.	MODEL.COFGRP = “Base” (150)
     * 
     * Report all errors and do not proceed if there are any errors.
     * 
     * @param autoEntity
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException    
     */
    private Vector validateSetup(EntityItem autoEntity, EntityItem annItem) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException
    {
    	ArrayList annCtry = getCountriesAsList(annItem);    
		ArrayList autoCtry = getCountriesAsList(autoEntity);  
   
		String mtmlist = PokUtils.getAttributeValue(autoEntity, "MTMLIST", "", "", false);
		
		Vector ttttmmmVct = new Vector();
		if(mtmlist.length()>0){
			StringTokenizer st = new StringTokenizer(mtmlist,",");
			while(st.hasMoreTokens()) {
				String ttttmmm = st.nextToken().trim();
				if(ttttmmm.length()!=7){
					//INVALID_MTMATTR_ERR = Invalid {0} specified. MTM &quot;{1}&quot; was not seven characters long.
		        	args[0] = this.getLD_Value(autoEntity, "MTMLIST");
		        	args[1] = ttttmmm;
					addError("INVALID_MTMATTR_ERR",args);
					continue;
				}
				ttttmmmVct.addElement(ttttmmm);
			}
		}else{
			//INVALID_ATTR_ERR = Invalid {0} specified
        	args[0] = PokUtils.getAttributeDescription(autoEntity.getEntityGroup(), "MTMLIST", "MTMLIST");
			addError("INVALID_ATTR_ERR",args);
		}
		
        String availType = PokUtils.getAttributeFlagValue(autoEntity, "AVAILTYPE");

        addDebug("validateSetup "+autoEntity.getKey()+" MTMLIST:"+mtmlist+
        		" AVAILTYPE: "+availType+" setupCtry "+autoCtry+" "+annItem.getKey()+" annCtry "+annCtry);
    	//1.	AUTOAVAIL.COUNTRYLIST must be a subset of the parent ANNOUNCEMENT.COUNTRYLIST.
        if(autoCtry.size()==0 || !annCtry.containsAll(autoCtry)){
        	//INVALID_CTRY_ERR = {0} must be a subset of the {1} {2}
        	args[0] = this.getLD_Value(autoEntity, "COUNTRYLIST");
        	args[1] = annItem.getEntityGroup().getLongDescription();
        	args[2] = this.getLD_Value(annItem, "COUNTRYLIST");
			addError("INVALID_CTRY_ERR",args);
        }
        
     	//2.	AUTOAVAIL.AVAILTYPE must be either:
        //	a.	Planned Availability (146)
        //	b.	Last Order (149)
        if(PLANNEDAVAIL.equals(availType) ||
           LASTORDERAVAIL.equals(availType)){
        }else{
        	//INVALID_ATTR_ERR = Invalid {0} specified
        	args[0] = this.getLD_Value(autoEntity, "AVAILTYPE");
			addError("INVALID_ATTR_ERR",args);
        }
  
        if(getReturnCode()!= PokBaseABR.PASS){
        	annCtry.clear();
        	autoCtry.clear();
        	return null;
        }
        
        long startTime = System.currentTimeMillis();
        //3.	MODEL must exist where:
    	//a.	MODEL.MACHTYPEATR & MODEL.MODELATR IN AUTOAVAIL.MTMLIST
    	//b.	MODEL.COFCAT = “Hardware” (100)
    	//c.	MODEL.COFGRP = “Base” (150)
        Vector mdlVct  = searchForModel(ttttmmmVct);
       
        addDebug("Time to do model searches: "+Stopwatch.format(System.currentTimeMillis()-startTime));

        //If AUTOAVAIL.AVAILTYPE = “Planned Availability” (146) then MODEL.ANNDATE = ANNOUNCEMENT.ANNDATE
        if(PLANNEDAVAIL.equals(availType)){
        	for (int i=0; i<mdlVct.size(); i++){
        		EntityItem mdlItem = (EntityItem)mdlVct.elementAt(i);
        		checkDates(annItem,mdlItem, "ANNDATE");
        	}
        }

    	// release memory
    	annCtry.clear();
    	autoCtry.clear();
    	annCtry=null;
    	autoCtry=null;
    	
    	return mdlVct;
    }

    /**
     * get country flag codes in a list
     * @param item
     * @return
     */
    private ArrayList getCountriesAsList(EntityItem item){
    	ArrayList crtylist = new ArrayList();
    	EANFlagAttribute ctrylist = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
    	if (ctrylist != null) {
    		// Get the selected Flag codes.
    		MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
    		for (int im = 0; im < mfArray.length; im++) {
    			// get selection
    			if (mfArray[im].isSelected()) {
    				crtylist.add(mfArray[im].getFlagCode());
    			}
    		} //end for
    	} //end of null chk
    	
    	return crtylist;
    }
 
    /**
     * get country descriptions in a list
     * @param item
     * @return
     */
    private ArrayList getCountryDescAsList(EntityItem item){
    	ArrayList crtylist = new ArrayList();
    	EANFlagAttribute ctrylist = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
    	if (ctrylist != null) {
    		// Get the selected Flag codes.
    		MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
    		for (int im = 0; im < mfArray.length; im++) {
    			// get selection
    			if (mfArray[im].isSelected()) {
    				crtylist.add(mfArray[im].toString());
    			}
    		} //end for
    	} //end of null chk
    	
    	return crtylist;
    }
    
    /**
     * @param annItem
     * @param mdlItem
     * @param mdlAttr
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkDates(EntityItem annItem,EntityItem model, String mdlAttr) 
    throws SQLException, MiddlewareException
    {
    	// MODEL.ANNDATE is not a nav attr
    	// get entity again
    	if (modelEG==null){
    		modelEG = new EntityGroup(null,m_db, m_prof, model.getEntityType(), "Edit", false);	
    	}
    
    	EntityItem mdlItem = new EntityItem(modelEG, m_prof, m_db, model.getEntityType(), model.getEntityID());
		
    	String anndate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", "", false);
    	String mdldate = PokUtils.getAttributeValue(mdlItem, mdlAttr, "", "", false);
    	addDebug("checkDates "+annItem.getKey()+" anndate "+anndate+" "+mdlItem.getKey()+" mdldate "+mdldate);

    	if (!anndate.equals(mdldate)){
    		//MDL_DATE_ERR = {0} {1} does not match {2} {3}
    		args[0] = getLD_NDN(annItem);
    		args[1] = getLD_Value(annItem, "ANNDATE");
    		args[2] = getLD_NDN(mdlItem);
    		args[3] = getLD_Value(mdlItem,mdlAttr);
    		addError("MDL_DATE_ERR",args);
    	}
    	// release memory but keep the group
    	modelEG.removeEntityItem(mdlItem);
    	for (int z = mdlItem.getAttributeCount() - 1; z >= 0; z--) {
    		EANAttribute att = mdlItem.getAttribute(z);
    		mdlItem.removeAttribute(att);
    	}
    	mdlItem.setParent(null);
    }
    
	/*************************************
	 * 3.	MODEL must exist where:
	 *	 a.	MODEL.MACHTYPEATR & MODEL.MODELATR IN AUTOAVAIL.MTMLIST
	 *	 b.	MODEL.COFCAT = “Hardware” (100)
	 *	 c.	MODEL.COFGRP = “Base” (150)
	 * @param machtype
	 * @param modelatr
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private Vector searchForModel(Vector ttttmmmVct) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector mdlVct = new Vector(1);
		Vector valVct = new Vector(3);
		Vector attrVct = new Vector(3);
		attrVct.addElement("MACHTYPEATR");
		attrVct.addElement("MODELATR");
		attrVct.addElement("COFCAT");
		Vector tmpVct = new Vector(1);
		for (int m=0; m<ttttmmmVct.size(); m++){
			tmpVct.clear();
			valVct.clear();
			
			String ttttmmm = ttttmmmVct.elementAt(m).toString();
			String machtype = ttttmmm.substring(0, 4);
			String modelatr = ttttmmm.substring(4, 7);

			addDebug("searchForModel ttttmmm["+m+"] machtype "+machtype+" modelatr "+modelatr);
			EntityItem eia[]= null;

			valVct.addElement(machtype);
			valVct.addElement(modelatr);
			valVct.addElement(HARDWARE);

			try{
				StringBuffer debugSb = new StringBuffer();
				eia= ABRUtil.doSearch(getDatabase(), m_prof, 
						MODEL_SRCHACTION_NAME, "MODEL", false, attrVct, valVct, debugSb);
				if (debugSb.length()>0){
					addDebug(debugSb.toString());
				}
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				addDebug("searchForModel SBRException: "+exBuf.getBuffer().toString());
			}
			if (eia!=null && eia.length > 0){			
				for (int i=0; i<eia.length; i++){
					String cofgrp = PokUtils.getAttributeFlagValue(eia[i], "COFGRP");
					addDebug("searchForModel found "+eia[i].getKey()+" cofgrp "+cofgrp);
					if (BASE.equals(cofgrp)){
						tmpVct.add(eia[i]);
					}
					eia[i] = null;
				}
			}

			if (tmpVct.size()==0){
				//MDL_NOTFOUND_ERR = Cannot find any Model with {0}, {1}, MODEL.COFCAT=Hardware and MODEL.COFGRP=Base.
				args[0] = machtype;
				args[1] = modelatr;
				addError("MDL_NOTFOUND_ERR",args);
			}
			else{
				mdlVct.addAll(tmpVct);
			}
		} // end ttttmmm vct loop
		
		tmpVct.clear();
		attrVct.clear();
		valVct.clear();
		tmpVct=null;
		attrVct=null;
		valVct=null;

		return mdlVct;
	}
    /************************************
     * @param item
     * @param attrCode
     * @return
     */
    private String getLD_Value(EntityItem item, String attrCode)   {
    	return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
    		PokUtils.getAttributeValue(item, attrCode, ",", PokUtils.DEFNOTPOPULATED, false);
    }
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
   
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
    	super.dereference();

    	rsBundle = null;
    	modelEG = null;
    	rptSb = null;
        args = null;

        metaTbl = null;
        navName = null;
        genAvail = null;
        //availTbl.clear();
        //availTbl = null;
    }
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.10 $";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "AUTOAVAILABRSTATUS";
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	private void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}
	
	/**********************************
	 * get message using resource
	 *
	 * @param resrcCode
	 * @param args
	 * @return
	 */
	private String getResourceMsg(String resrcCode, Object args[])
	{
		String msg = rsBundle.getString(resrcCode);
		if (args != null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		return msg;
	}
	/**********************************
	 * used for error output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	private void addError(String errCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(rsBundle.getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	} 
	
	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[])
	{
		String msg = rsBundle.getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

    /**********************************************************************************
     *  Get Name based on navigation attributes for specified entity
     *
     *@return java.lang.String
     */
     private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
     {
         StringBuffer navName = new StringBuffer();
         NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
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
         if (ndn!=null){ // must get other attr from parent and child entities
         	EntityList psList = null;
         	StringBuffer sb = new StringBuffer();
 			EntityItem ei = getNDNitem(theItem,ndn.getEntityType());
         	// performance issue with bringing back too many features and models for prodstruct error msg
 			if (ei==null){
 				if (theItem.getEntityType().endsWith("PRODSTRUCT")){	
 					addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType()+
 						" pulling small VE for this "+theItem.getKey());
 					// pull VE with just this prodstruct
 					String PS_VE = "EXRPT3FM";  // just feature->ps->model
 					if (theItem.getEntityType().equals("SWPRODSTRUCT")){	
 						PS_VE = "EXRPT3SWFM";  // just swfeature->swps->model
 					}else if (theItem.getEntityType().equals("IPSCSTRUC")){
 						PS_VE = "EXRPT3IPSCFM";  // just IPSCFEAT->IPSCSTRUC->svcmod
 					}
 					psList = m_db.getEntityList(theItem.getProfile(),
 						new ExtractActionItem(null, m_db, theItem.getProfile(),PS_VE),
 						new EntityItem[] { new EntityItem(null, theItem.getProfile(), theItem.getEntityType(),
 							theItem.getEntityID()) });

 					theItem = psList.getParentEntityGroup().getEntityItem(0);
 					ei = getNDNitem(theItem,ndn.getEntityType());
 				}
 			}

 			if (ei!=null){
 				sb.append("("+ndn.getTag());
 				for (int y=0; y<ndn.getAttr().size(); y++){
 					String attrcode = ndn.getAttr().elementAt(y).toString();
 					sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
 					if (y+1<ndn.getAttr().size()){
 						sb.append(" ");
 					}
 				}
 				sb.append(") ");
 			}else{
 				addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType());
 			}
 			ndn = ndn.getNext();
 			if (ndn !=null){
 				ei = getNDNitem(theItem,ndn.getEntityType());
 				if (ei!=null){
 					sb.append("("+ndn.getTag());
 					for (int y=0; y<ndn.getAttr().size(); y++){
 						String attrcode = ndn.getAttr().elementAt(y).toString();
 						sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
 						if (y+1<ndn.getAttr().size()){
 							sb.append(" ");
 						}
 					}
 					sb.append(") ");
 				}else{
 					addDebug("NO entity found for next ndn.getEntityType(): "+ndn.getEntityType());
 				}
 			}
 			navName.insert(0,sb.toString());

 			if (psList != null){
 				psList.dereference();
 			}
 		} // end getting other entity info

         return navName.toString().trim();
     }
     /**********************************************************************************
      * Find entity item to use for building the navigation display name when more then
      * one entity is needed, like for PRODSTRUCT
      *
      *@return EntityItem
      */
      private EntityItem getNDNitem(EntityItem theItem,String etype){
  		for (int i=0; i<theItem.getDownLinkCount(); i++){
  			EntityItem ent = (EntityItem)theItem.getDownLink(i);
  			if (ent.getEntityType().equals(etype)){
  				return ent;
  			}
  		}
  		for (int i=0; i<theItem.getUpLinkCount(); i++){
  			EntityItem ent = (EntityItem)theItem.getUpLink(i);
  			if (ent.getEntityType().equals(etype)){
  				return ent;
  			}
  		}
  		return null;
  	}
    //===================================================================================================
    //===================================================================================================
    /**************
     *  B.	Create AVAIL
In the sections below, an AVAIL created by the execution of this ABR with the following 
attribute values is referenced. If the ABR has not already created one with the following attribute values, 
then create one and reference it:

AttributeCode	From		Value
COMNAME			AUTOAVAIL	COMNAME
AVAILANNTYPE				"RFA"
ANNCODENAME		ANNOUNCEMENT	ANNCODENAME
AVAILTYPE		AUTOAVAIL	AVAILTPE
EFFECTIVEDATE	AUTOAVAIL	EFFECTIVEDATE
ORDERSYSNAME	AUTOAVAIL	ORDERSYSNAME
PDHDOMAIN		AUTOAVAIL	PDHDOMAIN
GENAREASELECTION AUTOAVAIL	GENAREASELECTION
COUNTRYLIST		AUTOAVAIL	COUNTRYLIST
DATAQUALITY					Default
STATUS						Default
AVAILABRSTATUS				Default

Note: For each AUTOAVAIL, only one AVAIL is created.
     */
    private class AttrSet{
		private Vector attrCodeVct = new Vector();
		private Hashtable attrValTbl = new Hashtable();
	
		void addSingle(EntityItem item, String attrCode){
			String value = PokUtils.getAttributeFlagValue(item, attrCode);
			if (value==null){
				value = "";
			}
			attrCodeVct.addElement(attrCode);
			attrValTbl.put(attrCode, value);
		}
	
		void addText(EntityItem item, String attrCode){
			String 	value = PokUtils.getAttributeValue(item, attrCode, "", "", false);
			attrCodeVct.addElement(attrCode);
			attrValTbl.put(attrCode, value);
		}
		
		void addMult(ArrayList list, String attrCode){
			Vector tmp = new Vector(list);
			if (!attrCodeVct.contains(attrCode)){
				attrCodeVct.addElement(attrCode);
			}
			attrValTbl.put(attrCode, tmp);
		}
		 
		void addMult(EntityItem item, String attrCode){
			String value = PokUtils.getAttributeFlagValue(item, attrCode);
			if (value==null){
				value = "";
			}
			String flagArray[] = PokUtils.convertToArray(value);
			Vector tmp = new Vector(flagArray.length);
			for (int i=0; i<flagArray.length; i++){
				tmp.addElement(flagArray[i]);
			}
			if (!attrCodeVct.contains(attrCode)){
				attrCodeVct.addElement(attrCode);
			}
			attrValTbl.put(attrCode, tmp);
		}
		AttrSet(EntityItem autoEntity, String annCodeName){ 
			//ANNCODENAME		ANNOUNCEMENT	ANNCODENAME
			attrCodeVct.addElement("ANNCODENAME");
			attrValTbl.put("ANNCODENAME", annCodeName); 
			//AVAILANNTYPE				"RFA"
			attrCodeVct.addElement("AVAILANNTYPE");
			attrValTbl.put("AVAILANNTYPE", "RFA"); 

			for (int i=0; i<AALIST_ATTR.length; i++){
				EANMetaAttribute ma = autoEntity.getEntityGroup().getMetaAttribute(AALIST_ATTR[i]);
				if (ma==null){
					continue;
				}
				if(ma.getAttributeType().equals("F")){
					addMult(autoEntity, AALIST_ATTR[i]);
				}else if(ma.getAttributeType().equals("U")){
					addSingle(autoEntity, AALIST_ATTR[i]);
				}else {
					addText(autoEntity, AALIST_ATTR[i]);
				}
			}
		}
		Vector getAttrCodes() { return attrCodeVct;}
		Hashtable getAttrValues() { return attrValTbl; }

		void dereference(){
			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
			attrCodeVct = null;
			attrValTbl = null;
		}
    }
    // used to support getting navigation display name when other entities are needed
    private static class NDN {
		private String etype, tag;
		private NDN next;
		private Vector attrVct = new Vector();
		NDN(String t,String s){
			etype = t;
			tag = s;
		}
		String getTag() { return tag;}
		String getEntityType() { return etype;}
		Vector getAttr(){ return attrVct;}
		void addAttr(String a){
			attrVct.addElement(a);
		}
		void setNext(NDN n) { next = n;}
		NDN getNext() { return next;}
	}
}
