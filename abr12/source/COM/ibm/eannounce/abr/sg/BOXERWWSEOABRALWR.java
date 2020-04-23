// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**************************************
 * From SG FS ABR ALWR 20090210.doc
 * V.	WWSEO/LSEO ALWR
 * This ABR is run for a selected WWSEO or LSEO. This ABR creates LSEOs based on setup data 
 * associated with the WWSEO or LSEO. This data is created via the User Interface (JUI or BUI) 
 * in a manner similar to creating and editing offering information. New actions will be provided 
 * by the OIM BAs via the standard metadata spreadsheet update process.
 * 
 * The user will create a Country Designator Group (CDG) at the Workgroup (via WGCDG). A CDG will 
 * have a unique Name (CDGNAME).
 * 
 * The user will create one or more Country Designator (CDENTITY) as children of the CDG (via CDGCDENTITY).
 * 
 * The user will navigate to a list of WWSEOs or LSEOs and then reference (search & link) a single CDG.
 * 
 * The user will use a Workflow action to queue this ABR which will:
 * -	process the selected WWSEO or LSEO (i.e. the ABR’s attribute is on the WWSEO/LSEO)
 * -	find the related CDG and its related CDENTITYs
 * -	create or update an LSEO for each valid CDENTITY
 * 
 * This ABR ignores all WWSEOALWRs that are directly related to the WWSEO via WWSEOWWSEOALWR.
 *
 */
//BOXERWWSEOABRALWR.java,v
//Revision 1.4  2009/04/01 13:02:56  wendy
//Handle PDHDOMAIN meta chg from F to U
//
//Revision 1.3  2009/02/23 14:48:44  wendy
//Added check for multiple CDG
//
//Revision 1.2  2009/02/13 18:36:44  wendy
//add qty to feature msg
//
//Revision 1.1  2009/02/13 16:44:01  wendy
//rename
//
//Revision 1.2  2009/02/13 13:07:47  wendy
//CQ00007061 Boxer
//
public class BOXERWWSEOABRALWR extends PokBaseABR 
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private Object[] args = new String[10];

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private EntityItem modelItem, wwseoItem;
    private Vector cdentityVct = new Vector(); // CDEntityLseo
    private Vector createdLseoVct = new Vector(1);
    private Vector updatedLseoVct = new Vector(1);
    private LinkActionItem lai = null;
    private DeleteActionItem dai = null;
    private Hashtable fcodePsTbl = new Hashtable();
    private Vector seoidVct = new Vector(); // make sure multiple CDENTITY dont conflict and have same CD-seoid
    
    private static final String LSEO_CREATEACTION_NAME = "CRPEERLSEO";
    private static final String LSEO_SRCHACTION_NAME = "SRDLSEO4";//"SRDLSEO3"; // need search w/o domain restrictions
    private static final String LSEOPS_LINKACTION_NAME = "LINKPRODSTRUCTLSEO";
    private static final String LSEOPS_DELETEACTION_NAME = "DELLSEOPRODSTRUCT";
    private static final String STATUS_FINAL = "0020";
  
    private static final String[] FCLIST_ATTR = {"LINECORDFCLIST","KEYBRDFCLIST","POINTDEVFCLIST",
    	"CTRYPACKFCLIST","LANGPACKFCLIST","PACKAGINGFCLIST","PUBFCLIST","OTHERFCLIST"};

    private static final String[] REQ_CDENTITY_ATTR = {"COUNTRYLIST","CD","GENAREASELECTION"}; // should have a local rule but chk anyway
    private static final String[] REQ_WWSEO_ATTR = {"SEOID","XXPARTNO"}; 
    private static final String[] REQ_LSEO_ATTR = {"XXPARTNO"};
    private static final Vector AUDIEN_VCT;
    static {
    	AUDIEN_VCT = new Vector(); // AUDIEN          	F 
    	AUDIEN_VCT.addElement("10046"); // '10046' (Catalog - Business Partner)
    	AUDIEN_VCT.addElement("10048"); // '10048' (Catalog - Indirect/Reseller)
    	AUDIEN_VCT.addElement("10054"); // '10054' (Public)
    	AUDIEN_VCT.addElement("10062"); // '10062' (LE Direct)
    }
    
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
            start_ABRBuild(false); // dont pull VE yet, have to get name based on roottype

            String VEname = "BOXERWWSEO";
            if (getEntityType().equals("LSEO")){
				VEname = "BOXERLSEO";
			}

            // create VE
            m_elist = m_db.getEntityList(m_prof,
                new ExtractActionItem(null, m_db,m_prof,VEname),
                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
            // get root from VE
            EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
                " extract: "+VEname+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            //Default set to pass
            setReturnCode(PASS);
//fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false);
  
            //NAME is navigate attributes
            navName = getNavigationName(rootEntity);
            rootDesc = "&quot;"+m_elist.getParentEntityGroup().getLongDescription()+"&quot; "+navName;
            
            modelItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
        
            if (getEntityType().equals("WWSEO")){ // root is WWSEO
            	wwseoItem = rootEntity;
            	verifyEntity(rootEntity,REQ_WWSEO_ATTR); 
            }else{ // root is LSEO
            	wwseoItem = m_elist.getEntityGroup("WWSEO").getEntityItem(0);
            	verifyEntity(rootEntity,REQ_LSEO_ATTR); 
            }

            if (wwseoItem==null){
        		//NOT_FOUND_ERR = Error: No {0} found.
    			args[0] =m_elist.getEntityGroup("WWSEO").getLongDescription();
        		addError("NOT_FOUND_ERR",args);
            }
            if (modelItem==null){
        		//NOT_FOUND_ERR = Error: No {0} found.
    			args[0] =m_elist.getEntityGroup("MODEL").getLongDescription();
        		addError("NOT_FOUND_ERR",args);
            }
            if (m_elist.getEntityGroup("CDG").getEntityItemCount()==0){
        		//NOT_FOUND_ERR = Error: No {0} found.
    			args[0] =m_elist.getEntityGroup("CDG").getLongDescription();
        		addError("NOT_FOUND_ERR",args);
            }
            if (m_elist.getEntityGroup("CDG").getEntityItemCount()>1){
        		//MULTIPLE_ERR = Error: Multiple {0} found.  Only one is supported.
    			args[0] =m_elist.getEntityGroup("CDG").getLongDescription();
        		addError("MULTIPLE_ERR",args);
            }
            if(getReturnCode()== PokBaseABR.PASS){
                EntityItem cdgItem = m_elist.getEntityGroup("CDG").getEntityItem(0);

                // validate the CDENTITY and accumulate the list of features for each
            	Vector lseoExistVct = checkCDEntity(rootEntity,cdgItem);
            	EntityList lseoList = null;
            	if (lseoExistVct.size()>0){
            		// pull all LSEOPRODSTRUCT for current LSEO at once
            		EntityItem eiArray[] = new EntityItem[lseoExistVct.size()];
            		lseoExistVct.copyInto(eiArray);

            		lseoList = m_db.getEntityList(m_prof, 
            				new ExtractActionItem(null, m_db,m_prof, "BOXERLSEO2"), 
            				eiArray);
            		addDebug("current lseoprodstruct using VE BOXERLSEO2: "+PokUtils.outputList(lseoList));
            		lseoExistVct.clear();
            	}
            	// Errors may have been found, but if any CDEntityLseo exist, run them
            	for (int i=0; i<cdentityVct.size(); i++){
            		CDEntityLseo cde = (CDEntityLseo)cdentityVct.elementAt(i);
            		if (cde.lseoItem==null){
                    	createLSEO(cde, rootEntity);
            		}else{
            			updateLSEO(cde, rootEntity, lseoList);
            		}
                } 	
            	 
            	if (createdLseoVct.size()>0){
            		// CREATED_MSG = Created {0} LSEO: {1}
            		args[0] =""+createdLseoVct.size();
            		args[1]="";
            		for (int i=0; i<createdLseoVct.size(); i++){
            			args[1] = args[1]+createdLseoVct.elementAt(i).toString();
            		}
            		msgf = new MessageFormat(rsBundle.getString("CREATED_MSG"));
            		addOutput(msgf.format(args));
            	}
            	if (updatedLseoVct.size()>0){
            		// UPDATED_MSG = Updated {0} LSEO: {1}
            		args[0] =""+updatedLseoVct.size();
            		args[1]="";
            		for (int i=0; i<updatedLseoVct.size(); i++){
            			args[1] = args[1]+updatedLseoVct.elementAt(i).toString();
            		}
            		msgf = new MessageFormat(rsBundle.getString("UPDATED_MSG"));
            		addOutput(msgf.format(args));
            	}
            	if (createdLseoVct.size()==0 &&updatedLseoVct.size()==0){
            		//NO_CHGS=No changes made.
            		addOutput(rsBundle.getString("NO_CHGS"));
            	}
            	if (lseoList!= null){
            		lseoList.dereference();
            	}
            } // ok to here
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
    /*************
     * A.	Setup Data
     * 
     * Entity Type:  CDG
     * Attributes:  CDGNAME
     * 
     * A WWSEO may have one CDG which may have one or more CDENTITYs.
     * 
     * Entity Type:  CDENTITY
     * Attributes:
     * AttributeCode	Type	LONGDESCRIPTION
     * CD				T	Country Designator*
     * COUNTRYLIST		F	Country List*
     * OFFCOUNTRY		U	Offering Country
     * GENAREASELECTION	F	General Area Selection*
     * LANGUAGES		U	Language
     * LINECORDFCLIST	T	Line Cord FC List
     * KEYBRDFCLIST		T	Keyboard FC List
     * POINTDEVFCLIST	T	Pointing Device
     * CTRYPACKFCLIST	T	Country Pack FC List
     * LANGPACKFCLIST	T	Language Pack FC List
     * PACKAGINGFCLIST	T	Packaging FC List
     * PUBFCLIST		T	Publication FC List
     * OTHERFCLIST		T	Other FC List
     * 
     * Notes:
     * 	1.	CD is 1 - 2 alpha integer upper characters controlled by a local rule.
     * 	2.	Long Description * indicates a local rule will require the attribute
     * 	3.	The FC Lists are lists of Feature Codes separated by a comma. The optional quantity is separated 
     * 	from Feature Code by a colon. The default quantity is one. Spaces are ignored and are not required.
     * 	Format:  featurecode or featurecode:quantity
     * 	e.g. 1234,4567:2, 7654:1
     * 	4.	There are eight lists of FC to facilitate data entry. From an ABR point of view, the eight 
     * 	lists could be concatenated using a comma between the lists. The ABR should not treat the lists differently
     * 	5.	If a Feature Code is duplicated either within a list or in different lists:
     * 		a.	If the quantity is identical for all instances, then process one of them with a
     * 		warning message; however, do NOT set WWSEOABRALWR  = Failed (0040).
     * 		b.	If the quantity is not identical for all instances, report the error, then skip the
     * 		CDENTITY and continue with the next CDENTITY and set WWSEOABRALWR  = Failed (0040)
     *
     * B.	Processing
     * 
     * The ABR must be able to be re-run. Every execution of the ABR creates a set of required LSEOs
     * that are needed. It then compares this list to the current children LSEOs for the WWSEO or LSEO.
     * There are several possibilities:
     * 1.	Current LSEO has a matching SEOID to one that is needed. A search is done that is not 
     * 	limited by PDHDOMAIN. 	If the parent WWSEO is not the WWSEO that was selected for the ABR, 
     * 	do NOT update the LSEO and report an Error for the CDENTITY indicating that the LSEO does not 
     *  have this WWSEO as a parent and proceed to the next CDENTITY.
     *  
     *  If the LSEO STATUS = 0020 (Final), then do NOT update the LSEO and report an Error for the CDENTITY 
     *  indicating that the LSEO (identify the LSEO) is Final and proceed to the next CDENITTY.
     *  
     *  Check the LSEO vs the CDENTITY data
     *  a.	Everything matches – nothing to do.
     *  b.	Not an exact match (e.g. list of features and/or attributes do not match) – update the 
     *  differences (this could be both adds and deletes of features).
     * 2.	No current LSEO – create the LSEO.
     * 
     * @param rootEntity
     * @param cdgItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private Vector checkCDEntity(EntityItem rootEntity,EntityItem cdgItem) 
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
    	Vector lseoUpdateVct = new Vector(1);
    	Vector cdeVct = PokUtils.getAllLinkedEntities(cdgItem, "CDGCDENTITY", "CDENTITY");
    	EntityGroup cdeGrp = m_elist.getEntityGroup("CDENTITY");
    	// if no CDENTITY were found, this is an error
    	if (cdeVct.size()==0){
    		//NOT_FOUND_ERR2 = Error: No {0} found for {1} &quot;{2}&quot;.
			args[0] = cdeGrp.getLongDescription();
			args[1] = cdgItem.getEntityGroup().getLongDescription();
			args[2] = getNavigationName(cdgItem);
    		addError("NOT_FOUND_ERR2",args);
    		return lseoUpdateVct;
    	}
    	//look at each CDENTITY and validate
    	for (int i=0; i<cdeVct.size(); i++){
    		EntityItem cdeItem = (EntityItem)cdeVct.elementAt(i);
    		String seoid;
    		//CDENTITY.CD length 
    		String cd = PokUtils.getAttributeValue(cdeItem, "CD", "", "", false);
    		addDebug("Checking "+cdeItem.getKey()+" CD: "+cd);
    		int cdlen = cd.length();
    		if (rootEntity.getEntityType().equals("WWSEO")){
    			//WWSEO – if the length is greater than 1 character, report the selected WWSEO, 
    			//the referenced CDG and the CDENTITY. Skip the CDENTITY and continue with the next CDENTITY and 
    			//set WWSEOABRALWR  = Failed (0040).
    			if (cdlen!=1){
    				//INVALID_CD_ERR = Error: Invalid {0} = {1} for &quot;{2}&quot;
					args[0]=PokUtils.getAttributeDescription(cdeGrp, "CD", "CD");
					args[1] = cd;
					args[2] = getNavigationName(cdeItem);	
					addError("INVALID_CD_ERR",args);
	    			outputSkipErrMsg(cdeItem, cdgItem);
					addDebug("Skipping "+cdeItem.getKey()+" because wrong CD.len");
					continue;
    			}
    			//WWSEO: Left(WWSEO.SEOID,6)&& CDENTITY.CD
    			seoid = PokUtils.getAttributeValue(rootEntity, "SEOID", "", "", false);
    			if (seoid.length()>6){
    				seoid=seoid.substring(0, 6);
    			}
    			seoid = seoid+cd;
    		}else{
    			//LSEO – if the length is !=2 characters, do not generate the LSEO, report the selected LSEO, 
    			//the referenced CDG and the CDENTITY. Skip the CDENTITY and continue with the next CDENTITY and 
    			//set WWSEOABRALWR  = Failed (0040)
    			if (cdlen!=2){
    				//INVALID_CD_ERR = Error: Invalid {0} = {1} for &quot;{2}&quot;
					args[0]=PokUtils.getAttributeDescription(cdeGrp, "CD", "CD");
					args[1] = cd;
					args[2] = getNavigationName(cdeItem);	
					addError("INVALID_CD_ERR",args);
	    			outputSkipErrMsg(cdeItem, cdgItem);
					addDebug("Skipping "+cdeItem.getKey()+" because wrong CD.len");
    				continue;
    			}

    			//LSEO: Left(LSEO.XXPARTNO,5) && Left(CDENTITY.CD,2)
    			seoid = PokUtils.getAttributeValue(rootEntity, "XXPARTNO", "", "", false);
    			if (seoid.length()>5){
    				seoid=seoid.substring(0, 5);
    			}
    			seoid = seoid+cd;
    		}
    		addDebug("derived seoid "+seoid);
    		if (seoidVct.contains(seoid)){ //supposed to have uniqueness check, but check anyway
    			//DUPLICATE_SEOID_ERR = Error: Duplicate SEOID {0} created by {1} = {2} for &quot;{3}&quot;
				args[0]=seoid;
				args[1]=PokUtils.getAttributeDescription(cdeGrp, "CD", "CD");
				args[2] = cd;
				args[3] = getNavigationName(cdeItem);	
				addError("DUPLICATE_SEOID_ERR",args);
    			outputSkipErrMsg(cdeItem, cdgItem);
				addDebug("Skipping "+cdeItem.getKey()+" because duplicate CD-seoid");
				continue;
    		}
    		seoidVct.addElement(seoid);
    		// verify the CDENTITY attributes
    		if(verifyEntity(cdeItem,REQ_CDENTITY_ATTR)){
    			Vector fcmsgVct = new Vector(1); //hang onto fc checked, so dont have same missing msg over and over
    			CDEntityLseo cdlseo = new CDEntityLseo(cdeItem, seoid); // hang onto info
    			boolean featureError = false;
    			// accumulate all features and quantity
    			for (int a=0; a<FCLIST_ATTR.length; a++){
    				String fclist = PokUtils.getAttributeValue(cdeItem, FCLIST_ATTR[a], "", "", false);
    				addDebug(FCLIST_ATTR[a]+" "+fclist);
    				if (fclist.length()>0){
    					StringTokenizer st = new StringTokenizer(fclist,",");
    					while(st.hasMoreTokens()) {
    						String qty = "1";
    						String fcode = st.nextToken().trim();
    						// check for qty
    						int id = fcode.indexOf(":");
    						if (id != -1){
    							qty = fcode.substring(id+1).trim();
    							fcode = fcode.substring(0,id).trim();
    						}

    						//3. Error	If one of the FEATURE lists identifies a FEATURE Code that is not available 
    						//(i.e. the Feature Code is not related to the parent MODEL via PRODSTRUCT). 
    						EntityItem psItem = findFeatureProdstruct(fcode);
    						if (psItem==null){
    							if (!fcmsgVct.contains(fcode)){
    								//FC_NOTFOUND_ERR = Error: Feature {0} was not related to Model &quot;{1}&quot;
    								args[0] = fcode;
    								args[1] = getNavigationName(modelItem);
    								addError("FC_NOTFOUND_ERR",args);
    								fcmsgVct.addElement(fcode);
    							}
    							featureError = true;
    						}else{
    							FCQty fcqty = new FCQty(fcode, qty,psItem);
    							int fcqtyId = cdlseo.fcQtyVct.indexOf(fcqty);
    							if (fcqtyId!= -1){ // matched on fcode
    								FCQty fcq1 = (FCQty) cdlseo.fcQtyVct.elementAt(fcqtyId);
    								//b.If the quantity is not identical for all instances, report the error, then skip the CDENTITY and 
    								//continue with the next CDENTITY and set WWSEOABRALWR  = Failed (0040)
    								if (!fcq1.qty.equals(fcqty.qty)){
    									//DUPLICATE_FCQTY_ERR = Error: Found duplicate Features with conflicting quantity values for {0} &quot;{1}&quot;. Features are {2}
    									args[0] = cdeGrp.getLongDescription();
    									args[1] = getNavigationName(cdeItem);
    									args[2]=fcq1+" "+fcqty;
    									addError("DUPLICATE_FCQTY_ERR",args);
    									featureError=true;
    								}else{
    									//a.If the quantity is identical for all instances, then process one of them with a 
    									//warning message; however, do NOT set WWSEOABRALWR  = Failed (0040).
    									//DUPLICATE_FC_MSG = Warning: Found duplicate Feature code {0} in {1} &quot;{2}&quot; in {3} &quot;{4}&quot;
    									args[0] = fcode;
    									args[1] = cdeGrp.getLongDescription();
    									args[2] = getNavigationName(cdeItem);
    									args[3] = cdgItem.getEntityGroup().getLongDescription();
    									args[4] = getNavigationName(cdgItem);
    									addOutput(getResourceMsg("DUPLICATE_FC_MSG", args));
    								}
    								fcqty.dereference();
    							}else{
    								cdlseo.addFcQty(fcqty);
    							}
    						}
    					} // end has more tokens
    				} // endof featurelist[a] has a value
    			} // end of feature list attributes
    			
    			if (featureError){
        			outputSkipErrMsg(cdeItem, cdgItem);
					cdlseo.dereference();
					continue;
    			}

    			//2.Error If an instance of CDENTITY has no Feature Codes identified. List the instance that is incomplete.
    			//Note:  at least one of the lists has to identify at least one Feature Code.
    			if (cdlseo.hasFeatures()){
    				// look for the LSEO matching this seoid
    				EntityItem lseo = findLseo(seoid);
    				if (lseo!= null){
    	    	        String statusFlag = PokUtils.getAttributeFlagValue(lseo, "STATUS");
    	    	        addDebug(lseo.getKey()+" STATUS: "+PokUtils.getAttributeValue(lseo, "STATUS",", ", "", false)+" ["+statusFlag+"] ");

    	    	        if(null == statusFlag || statusFlag.length()==0)  {
    	    	            statusFlag = STATUS_FINAL; // default to final 
    	    	        }
    	    	        if (statusFlag.equals(STATUS_FINAL)){
    	    	        	//LSEO_FINAL_ERR = Error: LSEO with SEOID:{0} is Final.
    						args[0] = seoid;
    						addError("LSEO_FINAL_ERR",args);  
    		    			outputSkipErrMsg(cdeItem, cdgItem);
    						cdlseo.dereference();
    	    	        }else{
    	    	        	cdlseo.setLseo(lseo);
        					lseoUpdateVct.addElement(lseo);
        					cdentityVct.add(cdlseo);
    	    	        }
    				}else{
    					// look in PDH to make sure this seoid does not exist, error if it does
    					lseo = searchForLSEO(seoid);
    					if (lseo!= null){
    						//LSEO_DUPLICATE_ERR = Error: LSEO with SEOID:{0} does not have this WWSEO as a parent.
    						args[0] = seoid;
    						addError("LSEO_DUPLICATE_ERR",args);  
    		    			outputSkipErrMsg(cdeItem, cdgItem);
    						cdlseo.dereference();
    					}else{
    						cdentityVct.add(cdlseo);
    					}
    				}
    			}else{
    				//NO_CDENTITYFC_ERR = Error: No Valid Features listed for {0} &quot;{1}&quot;.
    				args[0] = cdeGrp.getLongDescription();
    				args[1] = getNavigationName(cdeItem);
    				addError("NO_CDENTITYFC_ERR",args);   
        			outputSkipErrMsg(cdeItem, cdgItem);
    				cdlseo.dereference();
    			}
    		} // end verifyEntity ok
    		else{
    			outputSkipErrMsg(cdeItem, cdgItem);
    		}
    	} //end CDENTITY loop  
    	
    	return lseoUpdateVct;
    }
    /**
     * output skipping msg
     * @param cdeItem
     * @param cdgItem
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void outputSkipErrMsg(EntityItem cdeItem, EntityItem cdgItem) throws SQLException, MiddlewareException
    {
    	// SKIPPING_MSG = {0} &quot;{1}quot; in {2} &quot;{3}&quot; will be skipped.
		args[0]= cdeItem.getEntityGroup().getLongDescription();
		args[1] = getNavigationName(cdeItem);	
		args[2] = cdgItem.getEntityGroup().getLongDescription();
		args[3] = getNavigationName(cdgItem);
		addError("SKIPPING_MSG",args);
    }
    /******************
     * Find the Feature in the extract and return the prodstruct
     * One of the FEATURE lists identifies a FEATURE Code that is not available 
     * 	(i.e. the Feature Code is not related to the parent MODEL via PRODSTRUCT). 
     * @param fcode
     * @return
     */
    private EntityItem findFeatureProdstruct(String fcode){
    	EntityItem psItem = (EntityItem)fcodePsTbl.get(fcode);
    	if (psItem==null){
    		EntityGroup fcGrp = m_elist.getEntityGroup("FEATURE");
    		for (int i=0; i<fcGrp.getEntityItemCount(); i++){
    			EntityItem featItem = fcGrp.getEntityItem(i);
    			String fc = PokUtils.getAttributeValue(featItem, "FEATURECODE", "", "", false);
    			if (fc.equals(fcode)){
    				psItem = (EntityItem)featItem.getDownLink(0);
    				addDebug("findFeatureProdstruct for "+fcode+" found "+featItem.getKey()+" "+psItem.getKey());
    				if (featItem.getDownLink().size()>1){
    					addDebug("Warning: "+featItem.getKey()+" had multiple downlinks");
    					for (int d=0; d<featItem.getDownLink().size();d++){
    						addDebug("Warning: downlink["+d+"] "+featItem.getDownLink(d).getKey());
    					}
    				}
    				fcodePsTbl.put(fcode,psItem);// hang onto to prevent dupe error msgs
    				break;
    			}
    		}
    	}
    	
    	return psItem;
    }
    /******************
     * Look to see if any LSEO in the extract exists for this seoid
     * 
     * @param seoid
     * @return
     * @throws MiddlewareShutdownInProgressException 
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private EntityItem findLseo(String seoid) throws SQLException, MiddlewareException, 
    MiddlewareShutdownInProgressException
    {
    	EntityItem lseoItem = null;
    	EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
    	for (int i=0; i<lseoGrp.getEntityItemCount(); i++){
    		EntityItem item = lseoGrp.getEntityItem(i);
    		String sid = PokUtils.getAttributeValue(item, "SEOID", "", "", false);
    		if (sid.equals(seoid)){
    			lseoItem = item;
    			addDebug("findLseo for "+seoid+" found "+lseoItem.getKey());
    			break;
    		}
    	}
  
    	return lseoItem;
    }
	/*****************************************
	 * Search for LSEO using:
	 * -	<SEOID>
	 * 
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem searchForLSEO(String seoid) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem lseo = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(m_db, m_prof, 
					LSEO_SRCHACTION_NAME, "LSEO", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForLSEO SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug("searchForLSEO found "+eia[i].getKey());
			}

			lseo = eia[0];
		}
		attrVct.clear();
		valVct.clear();
		return lseo;
	}    
    /**************
     * REQ_CDENTITY_ATTR = {"COUNTRYLIST","CD","GENAREASELECTION"};
     * REQ_WWSEO_ATTR = {"XXPARTNO"};
     * REQ_LSEO_ATTR = {"XXPARTNO"};
     */
    private boolean verifyEntity(EntityItem cdeItem, String attrlist[]) throws SQLException, MiddlewareException{
    	boolean isok = true;
    	for (int i=0; i<attrlist.length; i++){
    		String value = PokUtils.getAttributeValue(cdeItem, attrlist[i], "", null,false);
    		if (value==null){
    			//MISSING_ATTR_ERR = Error: {0} &quot;{1}&quot; does not have a value for {2}.
				args[0] = cdeItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(cdeItem);
				args[2] = PokUtils.getAttributeDescription(cdeItem.getEntityGroup(), attrlist[i], attrlist[i]);
				addError("MISSING_ATTR_ERR",args);    	
				isok=false;
    		}
    	}
    	
    	return isok;
    }
    /**************
     * C.  LSEO Creation
     * 
     * If invoked at a WWSEO, then for each instance of CDENTITY (WWSEO -> CDG -> CDENTITY), create
     * a LSEO as a child of the selected WWSEO.
     * 
     * If invoked at a LSEO, then for each instance of CDENTITY (LSEO -> CDG -> CDENTITY), create a
     * LSEO as a peer of the selected LSEO (i.e. the selected LSEO's parent WWSEO).
     *   
     * @param cde
     * @param rootEntity
     * @throws MiddlewareRequestException
     * @throws RemoteException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException
     * @throws LockException
     * @throws WorkflowException
     */
    private void createLSEO(CDEntityLseo cde, EntityItem rootEntity) 
    throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
    	addDebug("createLSEO entered for SEOID "+cde.seoid);
    	// create the lseo with wwseo as parent
		EntityItem lseoItem = null;
		AttrSet attrSet = null;
		if (rootEntity.getEntityType().equals("WWSEO")){
			attrSet = new WWSEOAttrSet(cde,rootEntity);
		}else{
			attrSet = new LSEOAttrSet(cde,rootEntity);
		}

		StringBuffer debugSb = new StringBuffer();
		lseoItem = ABRUtil.createEntity(m_db, m_prof, LSEO_CREATEACTION_NAME, wwseoItem,  
				"LSEO", attrSet.getAttrCodes(), attrSet.getAttrValues(), debugSb); 
		if (debugSb.length()>0){
			addDebug(debugSb.toString());
		}
		// release memory
		attrSet.dereference();	
		
		if (lseoItem==null){
			//LSEO_CREATE_ERR = Error: Can not create LSEO entity for SEOID:{0}
			args[0] = cde.seoid;
			addError("LSEO_CREATE_ERR",args);
		}else{    	
			createdLseoVct.addElement(new StringBuffer(getNavigationName(lseoItem)));
			// link all prodstructs to this lseo thru lseoprodstruct and set the qty
			createFeatureRefs(cde, lseoItem);
		}	
    }
    
    /*********************
     * Update an existing LSEO if needed
     * all attributes must match
     * all referenced features must match and their quantity
     * extra lseoprodstruct must be deleted
     * @param cde
     * @param rootEntity
     * @param lseoList
     * @throws SQLException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws MiddlewareException 
     * @throws EANBusinessRuleException 
     * @throws RemoteException 
     * @throws MiddlewareBusinessRuleException 
     * @throws WorkflowException 
     * @throws LockException 
     */
    private void updateLSEO(CDEntityLseo cde, EntityItem rootEntity, EntityList lseoList) 
    throws MiddlewareBusinessRuleException, RemoteException, EANBusinessRuleException,
    MiddlewareException, MiddlewareShutdownInProgressException, SQLException, LockException, WorkflowException
    {
    	EntityItem lseoItem = lseoList.getParentEntityGroup().getEntityItem(cde.lseoItem.getKey());
    	addDebug("updateLSEO entered for SEOID "+cde.seoid+" "+lseoItem.getKey());
    	// make sure all attributes match
    	boolean chgsMade = updateLSEOAttributes(cde, rootEntity, lseoItem);
    	
    	// link missing prodstructs
    	// remove extra lseoprodstructs	
    	String updatemsg = updateFeatureRefs(cde, lseoItem);
    	if (chgsMade || updatemsg.length()>0){
    		updatedLseoVct.addElement(getNavigationName(lseoItem)+updatemsg);
    	}
    }
    /**************
     * make sure this lseo has the specified attribute values
     * 
     * @param cdeItem
     * @param rootEntity
     * @param lseoItem
     * @throws EANBusinessRuleException 
     * @throws SQLException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws MiddlewareException 
     * @throws RemoteException 
     * @throws MiddlewareBusinessRuleException 
     */
    private boolean updateLSEOAttributes(CDEntityLseo cde, EntityItem rootEntity, EntityItem lseoItem) 
    throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
    {
    	AttrSet attrSet = null;
    	addDebug("updateLSEOAttributes entered for "+lseoItem.getKey());
		if (rootEntity.getEntityType().equals("WWSEO")){
			attrSet = new WWSEOAttrSet(cde,rootEntity);
		}else{
			attrSet = new LSEOAttrSet(cde,rootEntity);
		}
		boolean commitNeeded = false;
		
    	// make sure all attributes match
		Vector attrCodeVct = attrSet.getAttrCodes(); // all attr
		for (int i=0; i<attrCodeVct.size(); i++){
			String attrCode = (String)attrCodeVct.elementAt(i);
			StringBuffer debugSb= new StringBuffer();
			// get the meta attribute
			EANMetaAttribute ma = lseoItem.getEntityGroup().getMetaAttribute(attrCode);
			if (ma==null) {
				addDebug("MetaAttribute cannot be found "+lseoItem.getEntityGroup().getEntityType()+"."+attrCode+"\n");
				continue;
			}
			Object value = attrSet.getAttrValues().get(attrCode);
			switch (ma.getAttributeType().charAt(0))
			{
			case 'T':
			case 'L':
			case 'X':
			{
				// check the Text attributes
				String curVal = PokUtils.getAttributeValue(lseoItem, attrCode, "", "", false);
				if (!value.equals(curVal)){
					addDebug("Updating "+attrCode+" was: "+curVal+" newval "+value);
					// save the Text attributes 
					ABRUtil.setText(lseoItem,attrCode, (String)value, debugSb); 
					commitNeeded = true;
				}
				break;
			}
			case 'U':
			{
				String curVal = PokUtils.getAttributeFlagValue(lseoItem, attrCode);
				if (!value.equals(curVal)){
					if (curVal==null && value.equals("")){
						continue;
					}
					addDebug("Updating "+attrCode+" was: "+curVal+" newval "+value);
					ABRUtil.setUniqueFlag(lseoItem,attrCode, (String)value,debugSb); 
					commitNeeded = true;
				}
				break;
			}
			case 'F':
			{
				String curVal = PokUtils.getAttributeFlagValue(lseoItem, attrCode);
				boolean updateNeeded = false;
				if (curVal==null){
					if ((value instanceof String) && value.equals("")){
						continue;
					}
					addDebug("Updating "+attrCode+" was: "+curVal+" newval "+value);
					updateNeeded = true;
				}else if (value instanceof String){
					if (!value.equals(curVal)){
						addDebug(attrCode+" needs to be updated, "+curVal+" newval "+value);
						updateNeeded = true;
					}
				}else {
					Vector tmp = (Vector)value;
					String curValArray[] = PokUtils.convertToArray(curVal);
					Vector curVct = new Vector(curValArray.length);
					for (int c=0; c<curValArray.length; c++){
						curVct.addElement(curValArray[c]);						
					}
					if (curVct.containsAll(tmp)&&tmp.containsAll(curVct)){
					}else{
						addDebug(attrCode+" needs to be updated");
						updateNeeded = true;
					}
				}
				if (updateNeeded){
					Vector tmp = null;
					if (value instanceof String){
						tmp = new Vector();
						if (!value.equals("")){
							tmp.addElement(value);
						}
					}else {
						tmp = (Vector)value;
					}

					ABRUtil.setMultiFlag(lseoItem,attrCode,tmp,debugSb); // make sure flagcodes are passed in 
					commitNeeded = true;
				}
				break;
			}
			default:
			{
				addDebug("MetaAttribute Type="+ma.getAttributeType()+
						" is not supported yet "+lseoItem.getEntityGroup().getEntityType()+"."+attrCode+"\n");
				// could not get anything
				break;
			}			         
			}
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}
		if(commitNeeded){
			lseoItem.commit(m_db, null);
		}
		// release memory
		attrSet.dereference();	   
		
		return commitNeeded;
    }

	/**
	 * link lseo to prodstructs
	 * @param cde
	 * @param lseoItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private void createFeatureRefs(CDEntityLseo cde, EntityItem lseoItem) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 	
	{
		String linkAction = LSEOPS_LINKACTION_NAME;
		if (lai ==null){
			lai = new LinkActionItem(null, m_db,m_prof,linkAction);
		}
		EntityItem parentArray[] = new EntityItem[]{lseoItem};
		EntityItem childArray[] = new EntityItem[cde.fcQtyVct.size()];

		Hashtable psFcqTbl = new Hashtable();
		// get each prodstruct
		for (int i=0; i<cde.fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)cde.fcQtyVct.elementAt(i);
			childArray[i] = fcq.prodItem;
			psFcqTbl.put(childArray[i].getKey(), fcq);
		}

		// do the link	
		lai.setParentEntityItems(parentArray);     
		lai.setChildEntityItems(childArray);
		m_db.executeAction(m_prof, lai);

		// extract and update QTY
		//update dts in profile
		Profile profile = m_prof.getNewInstance(m_db);
		String now = m_db.getDates().getNow();
		profile.setValOnEffOn(now, now);
		// VE for lseo to each ps BOXERLSEO2
		EntityList list = m_db.getEntityList(profile, 
				new ExtractActionItem(null, m_db,profile, "BOXERLSEO2"), 
				parentArray);

		addDebug("createFeatureRefs list using VE BOXERLSEO2 after linkaction: "+
				linkAction+"\n"+PokUtils.outputList(list));
		EntityGroup psGrp = list.getEntityGroup("PRODSTRUCT");
		StringBuffer refSb = new StringBuffer();
		for (int x=0; x<psGrp.getEntityItemCount(); x++){
			EntityItem psitem = psGrp.getEntityItem(x);
			FCQty fcq = (FCQty)psFcqTbl.get(psitem.getKey());
			String qty = fcq.qty;
			//ADDED_REF_MSG=<br />&nbsp;&nbsp;Adding reference to Feature {0} with quantity {1}
			refSb.append(getResourceMsg("ADDED_REF_MSG", new Object[]{fcq.fcode,fcq.qty}));
			EntityItem lseopsitem = (EntityItem)psitem.getUpLink(0);  // get the new relator
			addDebug(psitem.getKey()+" use qty: "+qty+" on "+lseopsitem.getKey());
			if (qty!= null && !qty.equals("1")){  // 1 is default so nothing needed
				StringBuffer debugSb = new StringBuffer();
				// save the qty attribute
				ABRUtil.setText(lseopsitem,"CONFQTY", qty, debugSb); 
				if (debugSb.length()>0){
					addDebug(debugSb.toString());
				}
				// must commit changed entity to the PDH 
				lseopsitem.commit(m_db, null);	
			}
		}
		StringBuffer lseoinfo = (StringBuffer)createdLseoVct.lastElement();
		lseoinfo.append(refSb.toString());
		
		psFcqTbl.clear();
		list.dereference();
	}
	
	/**********************
	 *  link missing prodstructs, remove extra lseoprodstructs, update qty on existing lseoprodstructs
	 *  if needed
	 * @param cde
	 * @param lseoItem
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private String updateFeatureRefs(CDEntityLseo cde, EntityItem lseoItem) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 	
	{
		StringBuffer refSb = new StringBuffer();
		Hashtable psFcqTbl = new Hashtable();
		// get each prodstruct and its qty
		for (int i=0; i<cde.fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)cde.fcQtyVct.elementAt(i);
			psFcqTbl.put(fcq.prodItem.getKey(), fcq);
		}	
		
		// verify referenced features and quantity
		Vector origPsVct = PokUtils.getAllLinkedEntities(lseoItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
		addDebug("updateFeatureRefs origPsVct "+origPsVct.size());
		for (int i=0; i<origPsVct.size(); i++){
			addDebug("updateFeatureRefs origPsVct["+i+"] "+((EntityItem)origPsVct.elementAt(i)).getKey());
		}
		
		// get all required prodstructs
		Vector missingPsVct = new Vector();
		for (int i=0; i<cde.fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)cde.fcQtyVct.elementAt(i);
			boolean found = false;
			Iterator itr = origPsVct.iterator();
			while (itr.hasNext()){
				EntityItem psItem = (EntityItem)itr.next();
				if (psItem.getKey().equals(fcq.prodItem.getKey())){
					addDebug("updateFeatureRefs already exists "+fcq.prodItem.getKey());
					found = true;
					itr.remove();
					break;
				}
			}
			if (!found){
				addDebug("updateFeatureRefs missing "+fcq.prodItem.getKey());
				missingPsVct.add(fcq.prodItem);
				//ADDED_REF_MSG=<br />&nbsp;&nbsp;Adding reference to Feature {0} with quantity {1}
				refSb.append(getResourceMsg("ADDED_REF_MSG", new Object[]{fcq.fcode,fcq.qty}));
			}
		}
		
		EntityItem parentArray[] = new EntityItem[]{lseoItem};
		String linkAction = LSEOPS_LINKACTION_NAME;
		// add any missing LSEOPRODSTRUCT
		if (missingPsVct.size()>0){
			addDebug("updateFeatureRefs  missingPsVct "+missingPsVct.size());
			for (int i=0; i<missingPsVct.size(); i++){
				addDebug("updateFeatureRefs missingPsVct["+i+"] "+((EntityItem)missingPsVct.elementAt(i)).getKey());
			}
			if (lai ==null){
				lai = new LinkActionItem(null, m_db,m_prof,linkAction);
			}

			EntityItem childArray[] = new EntityItem[missingPsVct.size()];
			missingPsVct.copyInto(childArray);

			// do the link	
			lai.setParentEntityItems(parentArray);     
			lai.setChildEntityItems(childArray);
			m_db.executeAction(m_prof, lai);
		}
		// remove any extra LSEOPRODSTRUCT
		String deleteAction =LSEOPS_DELETEACTION_NAME;
		if (origPsVct.size()>0){
			addDebug("updateFeatureRefs unneeded cnt "+origPsVct.size());
			for (int i=0; i<origPsVct.size(); i++){
				EntityItem psitem = (EntityItem)origPsVct.elementAt(i);
				addDebug("updateFeatureRefs unneeded ["+i+"] "+psitem.getKey());
				for (int f=0; f<psitem.getUpLinkCount();f++){
					EntityItem featitem = (EntityItem)psitem.getUpLink(f);
					if (featitem.getEntityType().equals("FEATURE")){
						//DELETED_REF_MSG=<br />&nbsp;&nbsp;Deleting reference to Feature {0}
						refSb.append(getResourceMsg("DELETED_REF_MSG", 
								new Object[]{PokUtils.getAttributeValue(featitem, "FEATURECODE", "", "", false)}));
						break;
					}
				}
			}
			if (dai ==null){
				dai = new DeleteActionItem(null, m_db,m_prof,deleteAction);
			}

			EntityItem childArray[] = new EntityItem[origPsVct.size()];
			origPsVct.copyInto(childArray);
			// do the delete	
			dai.setEntityItems(childArray);
			m_db.executeAction(m_prof, dai);
			origPsVct.clear();
		}		
			
		// extract and update QTY
		//update dts in profile
		Profile profile = m_prof.getNewInstance(m_db);
		String now = m_db.getDates().getNow();
		profile.setValOnEffOn(now, now);
		// VE for lseo to each ps BOXERLSEO2
		EntityList list = m_db.getEntityList(profile, 
				new ExtractActionItem(null, m_db,profile, "BOXERLSEO2"), 
				parentArray);

		addDebug("updateFeatureRefs list using VE BOXERLSEO2 after linkaction: "+
				linkAction+" and deleteaction: "+deleteAction+"\n"+PokUtils.outputList(list));
		EntityGroup psGrp = list.getEntityGroup("PRODSTRUCT");
		
		for (int x=0; x<psGrp.getEntityItemCount(); x++){
			EntityItem psitem = psGrp.getEntityItem(x);
			FCQty fcq = (FCQty)psFcqTbl.get(psitem.getKey());
			String qty = fcq.qty;
			EntityItem lseopsitem = (EntityItem)psitem.getUpLink(0);  // get the relator
			String qtyValue = PokUtils.getAttributeValue(lseopsitem, "CONFQTY", "", "", false);
			addDebug(psitem.getKey()+" needs qty: "+qty+" on "+lseopsitem.getKey()+" has qty: "+qtyValue);
			if (qty!= null && !qty.equals(qtyValue)){  //must match
				StringBuffer debugSb = new StringBuffer();
				// save the qty attribute
				ABRUtil.setText(lseopsitem,"CONFQTY", qty, debugSb); 
				if (debugSb.length()>0){
					addDebug(debugSb.toString());
				}
				if (!missingPsVct.contains(psitem)){ // this lseops->ps already existed
					//UPDATED_REF_MSG=<br />&nbsp;&nbsp;Updating reference to Feature {0} with quantity {1}
					refSb.append(getResourceMsg("UPDATED_REF_MSG", new Object[]{fcq.fcode,fcq.qty}));
				}
				// must commit changed entity to the PDH 
				lseopsitem.commit(m_db, null);	
			}
		}
		
		psFcqTbl.clear();
		missingPsVct.clear();
		list.dereference();
		return refSb.toString();
	}
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
    	super.dereference();

    	for (int v=0; v<cdentityVct.size(); v++){
    		CDEntityLseo sma = (CDEntityLseo)cdentityVct.elementAt(v);
    		sma.dereference();
    	}
    	cdentityVct.clear();
    	cdentityVct = null;
 
    	createdLseoVct.clear();
    	createdLseoVct = null;
        updatedLseoVct.clear();
        updatedLseoVct = null;
        
    	rsBundle = null;
    	modelItem = null;
    	wwseoItem = null;
    	lai = null;
    	dai = null;
    	
    	rptSb = null;
        args = null;

        metaTbl = null;
        navName = null;
        fcodePsTbl.clear();
        fcodePsTbl = null;
        seoidVct.clear();
        seoidVct = null;
    }
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "1.4";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "WWSEOABRALWR";
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
	 */
	private void addError(String errCode, Object args[])
	{
		setReturnCode(FAIL);

		String msg =rsBundle.getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msg);
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
    private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
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

        return navName.toString();
    }
    //===================================================================================================
    //===================================================================================================
    /************** 
     * The LSEO attributes are as follows:
     * 
     * AttributeCode   	Type    Derivation
     * ACCTASGNGRP     	U       '01' (01 - 3000100000)
     * AUDIEN          	F       '10046' (Catalog - Business Partner)
     *                          '10048' (Catalog - Indirect/Reseller)
     *                          '10054' (Public)
     *                          '10062' (LE Direct)
     * 
     * COUNTRYLIST     	F       CDENTITY.COUNTRYLIST
     * GENAREASELECTION    F    CDENTITY.GENAREASELECTION
     * LANGUAGES       	U       CDENTITY.LANGUAGES**
     * OFFCOUNTRY      	U       CDENTITY.OFFCOUNTRY**
     * XXPARTNO        	T       WWSEO: WWSEO.XXPARTNO
	 *						    LSEO: LSEO.XXPARTNO
     * 
     * COMNAME         	T       WWSEO: Left(WWSEO.SEOID,6)&& CDENTITY.CD
     *                          LSEO: Left(LSEO.XXPARTNO,5) && Left(CDENTITY.CD,2)
     * SEOID           	T       WWSEO: Left(WWSEO.SEOID,6)&& CDENTITY.CD
     *                          LSEO: Left(LSEO.XXPARTNO,5) && Left(CDENTITY.CD,2)
     * LSEOMKTGDESC    	T       WWSEO: WWSEO.MKTGNAME**
     *                          LSEO: LSEO.LSEOMKTGDESC**
     * LSEOPUBDATEMTRGT    T    WWSEO: null
     *                          LSEO: LSEO. LSEOPUBDATEMTRGT
     * LSEOUNPUBDATEMTRGT  T    WWSEO: null
     *                          LSEO: LSEO. LSEOUNPUBDATEMTRGT
     * PDHDOMAIN       	F       WWSEO: WWSEO.PDHDOMAIN
     *                          LSEO: LSEO.PDHDOMAIN
     * PRODHIERCD      	T       WWSEO: WWSEO. PRODHIERCD**
     *                          LSEO: LSEO.PRODHIERCD**
     * Note:  Those attributes with a '**' at the end of the Description are not required.                    
     */   
    private class AttrSet{
		private Vector attrCodeVct = new Vector();
		private Hashtable attrValTbl = new Hashtable();
		protected void addSingle(EntityItem item, String attrCode){
			addSingle(item, attrCode, attrCode);
		}
		protected void addSingle(EntityItem item, String attrCode, String attrCode2){
			String value = PokUtils.getAttributeFlagValue(item, attrCode);
			if (value==null){
				value = "";
			}
			attrCodeVct.addElement(attrCode2);
			attrValTbl.put(attrCode2, value);
		}
		protected void addText(EntityItem item, String attrCode){
			addText(item, attrCode, attrCode);
		}
		protected void addText(EntityItem item, String attrCode, String attrCode2){
			String 	value = PokUtils.getAttributeValue(item, attrCode, "", "", false);
			attrCodeVct.addElement(attrCode2);
			attrValTbl.put(attrCode2, value);
		}
		protected void addMult(EntityItem item, String attrCode){
			String value = PokUtils.getAttributeFlagValue(item, attrCode);
			if (value==null){
				value = "";
			}
			String flagArray[] = PokUtils.convertToArray(value);
			Vector tmp = new Vector(flagArray.length);
			for (int i=0; i<flagArray.length; i++){
				tmp.addElement(flagArray[i]);
			}
			attrCodeVct.addElement(attrCode);
			attrValTbl.put(attrCode, tmp);
		}
		AttrSet(CDEntityLseo cde){
			// ACCTASGNGRP     	U       '01' (01 - 3000100000)
			attrCodeVct.addElement("ACCTASGNGRP");
			attrValTbl.put("ACCTASGNGRP", "01"); //ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
			// AUDIEN          	F 
			attrCodeVct.addElement("AUDIEN");
			attrValTbl.put("AUDIEN", AUDIEN_VCT); 
            //SEOID           	T       WWSEO: Left(WWSEO.SEOID,6)&& CDENTITY.CD
            //							LSEO: Left(LSEO.XXPARTNO,5) && Left(CDENTITY.CD,2)
			//COMNAME         	T       WWSEO: Left(WWSEO.SEOID,6)&& CDENTITY.CD
            //							LSEO: Left(LSEO.XXPARTNO,5) && Left(CDENTITY.CD,2)
			attrCodeVct.addElement("SEOID");
			attrValTbl.put("SEOID", cde.seoid);
			attrCodeVct.addElement("COMNAME");
			attrValTbl.put("COMNAME", cde.seoid);                             
			//LANGUAGES       	U       CDENTITY.LANGUAGES
			addSingle(cde.cdEntity, "LANGUAGES");
			//OFFCOUNTRY      	U       CDENTITY.OFFCOUNTRY
			addSingle(cde.cdEntity, "OFFCOUNTRY");
			//GENAREASELECTION    F       CDENTITY.GENAREASELECTION
			addMult(cde.cdEntity, "GENAREASELECTION");
			//COUNTRYLIST     	F       CDENTITY.COUNTRYLIST
			addMult(cde.cdEntity, "COUNTRYLIST");
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
    /************
     * XXPARTNO        		T       WWSEO: WWSEO.XXPARTNO
     * LSEOMKTGDESC    		T       WWSEO: WWSEO.MKTGNAME
     * LSEOPUBDATEMTRGT     T       WWSEO: Do NOT set/update
     * LSEOUNPUBDATEMTRGT   T       WWSEO: Do NOT set/update
     * PDHDOMAIN       		F       WWSEO: WWSEO.PDHDOMAIN
     * PRODHIERCD      		T       WWSEO: WWSEO. PRODHIERCD
     *
     */
    private class WWSEOAttrSet extends AttrSet{
    	WWSEOAttrSet(CDEntityLseo cde, EntityItem rootEntity){
    		super(cde);
    		//XXPARTNO        	T       WWSEO: WWSEO.XXPARTNO
    		addText(rootEntity, "XXPARTNO");
    		//LSEOMKTGDESC    	T       WWSEO: WWSEO.MKTGNAME
    		addText(rootEntity, "MKTGNAME","LSEOMKTGDESC");
			//PRODHIERCD      	T       WWSEO: WWSEO. PRODHIERCD
    		addText(rootEntity, "PRODHIERCD");
			//PDHDOMAIN       	F       WWSEO: WWSEO.PDHDOMAIN
    		// Entity/Attribute	WWSEO	PDHDOMAIN	SetAttributeType	U ->now overrides F
    		EANMetaAttribute ma = rootEntity.getEntityGroup().getMetaAttribute("PDHDOMAIN");
    		if(ma.getAttributeType().equals("F")){
    			addMult(rootEntity, "PDHDOMAIN");
    		}else{
    			addSingle(rootEntity, "PDHDOMAIN");
    		}
    	}
    }
    /************
     * XXPARTNO        		T   LSEO: LSEO.XXPARTNO
     * LSEOMKTGDESC    		T	LSEO: LSEO.LSEOMKTGDESC
     * LSEOPUBDATEMTRGT    	T   LSEO: LSEO.LSEOPUBDATEMTRGT
     * LSEOUNPUBDATEMTRGT  	T   LSEO: LSEO.LSEOUNPUBDATEMTRGT
     * PDHDOMAIN       		F   LSEO: LSEO.PDHDOMAIN
     * PRODHIERCD      		T   LSEO: LSEO.PRODHIERCD
     *
     */
    private class LSEOAttrSet extends AttrSet{
    	LSEOAttrSet(CDEntityLseo cde, EntityItem rootEntity){
    		super(cde);
    		//XXPARTNO        	T       LSEO: LSEO.XXPARTNO
    		addText(rootEntity, "XXPARTNO");
    		//LSEOMKTGDESC    	T		LSEO: LSEO.LSEOMKTGDESC
    		addText(rootEntity, "LSEOMKTGDESC");
			//PRODHIERCD      	T       LSEO: LSEO. PRODHIERCD
    		addText(rootEntity, "PRODHIERCD");
			//LSEOPUBDATEMTRGT    	T   LSEO: LSEO. LSEOPUBDATEMTRGT
    		addText(rootEntity, "LSEOPUBDATEMTRGT");
	   	    // LSEOUNPUBDATEMTRGT  	T   LSEO: LSEO. LSEOUNPUBDATEMTRGT
    		addText(rootEntity, "LSEOUNPUBDATEMTRGT");
			//PDHDOMAIN       	F       LSEO: LSEO.PDHDOMAIN
    		// Entity/Attribute	LSEO	PDHDOMAIN	SetAttributeType	U ->now overrides F
    		EANMetaAttribute ma = rootEntity.getEntityGroup().getMetaAttribute("PDHDOMAIN");
    		if(ma.getAttributeType().equals("F")){
    			addMult(rootEntity, "PDHDOMAIN");
    		}else{
    			addSingle(rootEntity, "PDHDOMAIN");
    		} 
    	}
    }
    private class CDEntityLseo{
    	private EntityItem cdEntity;
    	private String seoid;
    	private Vector fcQtyVct = new Vector();  // vector of FCQty
    	private EntityItem lseoItem;
    	CDEntityLseo(EntityItem item, String id){
    		cdEntity = item;
    		seoid=id;
    	}
    	void addFcQty(FCQty fcq){
    		fcQtyVct.add(fcq);
    	}
    	boolean hasFeatures() {return fcQtyVct.size()>0;}
    	void setLseo(EntityItem item){ lseoItem = item;}
    	void dereference(){
    		for (int v=0; v<fcQtyVct.size(); v++){
    			FCQty sma = (FCQty)fcQtyVct.elementAt(v);
    			sma.dereference();
    		}
    		fcQtyVct.clear();
    		fcQtyVct=null;

    		lseoItem = null;
    		cdEntity = null;
    		seoid=null;
    	}
    }
    private class FCQty {
    	private String fcode;
    	private String qty;
    	private EntityItem prodItem; // prodstruct to the feature with this featurecode
   
    	FCQty(String fc, String q, EntityItem ei){
    		fcode = fc;
    		qty = q;
    		prodItem = ei;
    	}

        public boolean equals(Object obj) // used by Vector.contains()
        {
        	FCQty pn = (FCQty)obj;
            return (fcode.equals(pn.fcode));
        }
        public String toString(){
        	if (qty.equals("1")){
        		return fcode;
        	}
        	return fcode+":"+qty;
        }
        void dereference(){
        	fcode=null;
        	qty=null;
        	prodItem = null;
        }
    }
}
