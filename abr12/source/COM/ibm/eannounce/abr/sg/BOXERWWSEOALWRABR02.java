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
 * VI.	WWSEO wo CDG ALWR
 *
 * This ABR creates LSEOs based on setup data associated with the WWSEO. This data is created via the
 * User Interface (JUI or BUI) in a manner similar to creating and editing offering information.
 * This ABR considers the WWSEO directly related to the selected WWSEOALWR via WWSEOWWSEOALWR.
 * 
 * 
 * Note:  this ABR’s attribute is WWSEOALWR.WWSEOALWRABR02
 * This will create or update one LSEO.
 *
 */
//BOXERWWSEOALWRABR02.java,v
//Revision 1.3  2009/04/01 13:02:56  wendy
//Handle PDHDOMAIN meta chg from F to U
//
//Revision 1.2  2009/02/17 21:25:50  wendy
//Use different VE to get current data
//
//Revision 1.1  2009/02/17 20:51:16  wendy
//CQ00007061 Boxer
//
//
public class BOXERWWSEOALWRABR02 extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private Object[] args = new String[10];

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private EntityItem modelItem, wwseoItem;
    private Vector createdLseoVct = new Vector(1);
    private Vector updatedLseoVct = new Vector(1);
    private LinkActionItem lai = null;
    private DeleteActionItem dai = null;
    private Hashtable fcodePsTbl = new Hashtable();
    private String seoid;
    private Vector fcQtyVct = new Vector(1);
    
    private static final String LSEO_CREATEACTION_NAME = "CRPEERLSEO";
    private static final String LSEO_SRCHACTION_NAME = "SRDLSEO4";//"SRDLSEO3"; // need search w/o domain restrictions
    private static final String LSEOPS_LINKACTION_NAME = "LINKPRODSTRUCTLSEO";
    private static final String LSEOPS_DELETEACTION_NAME = "DELLSEOPRODSTRUCT";
    private static final String STATUS_FINAL = "0020";

    private static final String[] FCLIST_ATTR = {"CTRYPACKFCLIST","LANGPACKFCLIST","OTHERFCLIST",
    	"PACKAGINGFCLIST","PUBFCLIST","LINECORDFCLIST","KEYBRDFCLIST","POINTDEVFCLIST"}; 

    private static final String[] REQ_WWSEOALWR_ATTR = {"SEOID","COUNTRYLIST","GENAREASELECTION"}; 
    private static final String[] REQ_WWSEO_ATTR = {"COMNAME"};

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
            start_ABRBuild(); // dont pull VE yet, have to get name based on roottype

            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
            // get root from VE
            EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
                " extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            //Default set to pass
            setReturnCode(PASS);
//fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false);

            //NAME is navigate attributes
            navName = getNavigationName(rootEntity);
            rootDesc = m_elist.getParentEntityGroup().getLongDescription()+" &quot;"+navName+"&quot;";

            modelItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);

            wwseoItem = m_elist.getEntityGroup("WWSEO").getEntityItem(0);

            if (wwseoItem==null){
        		//NOT_FOUND_ERR = Error: No {0} found.
    			args[0] =m_elist.getEntityGroup("WWSEO").getLongDescription();
        		addError("NOT_FOUND_ERR",args);
            }else{
            	verifyEntity(wwseoItem,REQ_WWSEO_ATTR); 
            	rootDesc = rootDesc+"<br /> for "+m_elist.getEntityGroup("WWSEO").getLongDescription()+" &quot;"+getNavigationName(wwseoItem)+"&quot;";
            }
            if (modelItem==null){
        		//NOT_FOUND_ERR = Error: No {0} found.
    			args[0] =m_elist.getEntityGroup("MODEL").getLongDescription();
        		addError("NOT_FOUND_ERR",args);
            }
  
            if(getReturnCode()== PokBaseABR.PASS){
            	// validate the WWSEOALWR and accumulate the list of features for each
            	EntityItem lseoItem = checkAlwr(rootEntity);
            	
            	if(getReturnCode()== PokBaseABR.PASS){
            		if (lseoItem==null){
            			createLSEO(rootEntity);
            		}else{
            			updateLSEO(rootEntity, lseoItem);
            		}

            		if (createdLseoVct.size()>0){
            			// CREATED_MSG = Created LSEO: {1}
            			args[0]="";
            			for (int i=0; i<createdLseoVct.size(); i++){
            				args[0] = args[0]+createdLseoVct.elementAt(i).toString();
            			}
            			msgf = new MessageFormat(rsBundle.getString("CREATED_MSG"));
            			addOutput(msgf.format(args));
            		}
            		if (updatedLseoVct.size()>0){
            			// UPDATED_MSG = Updated LSEO: {1}
            			args[0]="";
            			for (int i=0; i<updatedLseoVct.size(); i++){
            				args[0] = args[0]+updatedLseoVct.elementAt(i).toString();
            			}
            			msgf = new MessageFormat(rsBundle.getString("UPDATED_MSG"));
            			addOutput(msgf.format(args));
            		}
            	}
            	if (createdLseoVct.size()==0 &&updatedLseoVct.size()==0){
            		//NO_CHGS=No changes made.
            		addOutput(rsBundle.getString("NO_CHGS"));
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
     * Entity Type:  WWSEOALWR
     * Attributes:
     * AttributeCode	AttributeType	LongDescription
     * COUNTRYLIST		F	Country List *
     * CTRYPACKFCLIST	T	Country Pack FC List
     * GENAREASELECTION	F	General Area Selection *
     * LANGUAGES		U	Language
     * LANGPACKFCLIST	T	Language Pack FC List
     * OFFCOUNTRY		U	Offering Country
     * OTHERFCLIST		T	Other FC List
     * LINECORDFCLIST	T	Line Cord FC List
     * KEYBRDFCLIST		T	Keyboard FC List
     * POINTDEVFCLIST	T	Pointing Device FC List
     * PACKAGINGFCLIST	T	Packaging FC List
     * PUBFCLIST		T	Publication FC List
     * SEOID			T	SEO ID *
     * WWSEOALWRABR02	A	WWSEO ALWR No CD ABR
     * 
     * Notes:
     *  1.	There may be multiple instances of WWSEOALWR; however, the ABR will be queued once per WWSEOLAWR.
     *  2.	The applicable attributes are shown above.
     *  3.	Long Description * indicates a local rule will require the attribute
     *  4.	The FC Lists are lists of Feature Codes separated by a comma. The optional quantity is separated 
     *  from Feature Code by a colon. The default quantity is one. Spaces are ignored.
     *  Format:  featurecode or featurecode:quantity
     *  e.g. 1234, 4567:2, 7654:1
     *  5.	There are eight lists of FC to facilitate data entry. From an ABR point of view, the eight lists 
     *  could be concatenated using a comma between the lists. The ABR should not treat the lists differently.
     *  6.	If a Feature Code is duplicated either within a list or in different lists:
     *  a.	If the quantity is identical for all instances, then process one of them with a warning message; 
     *  however, do NOT set WWSEOALWRABR02 = Failed (0040).
     *  b.	If the quantity is not identical for all instances, report the error, and set WWSEOALWRABR02 = Failed (0040)
     *  
     *  B.	Processing
     *  
     *  The ABR must be able to be re-run. Every execution of the ABR determines a  LSEO that is needed. 
     *  It then compares the needed LSEO to the child LSEO for the WWSEO. There are four possibilities:
     *  1.	There exists a LSEO with matching SEOID to one that is needed. Use a search without PDHDOMAIN 
     *  for LSEO using the SEOID. If the LSEO does not have the same WWSEO as a parent as the WWSEOALWR, 
     *  then report an error for this WWSEOALWR indicating the parent WWSEO, the WWSEOALWR and the SEOID 
     *  that is duplicate to data not managed under this WWSEO and set WWSEOALWRABR02 = Failed (0040) and exit.
     *  a.	Everything matches – nothing to do.
     *  b.	Not an exact match (e.g. list of features do not match)
     *  i.	If the LSEO STATUS = 0020 (Final), then do NOT update the LSEO and report an Error for this 
     *  WWSEOALWR indicating the WWSEO, the LSEO, and the WWSEOALWR.
     *  ii.	If the LSEO STATUS <> 0020 (Final), then update the LSEO to match the requirement.
     *  2.	No current LSEO – create the LSEO.
     *
     * @param rootEntity
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private EntityItem checkAlwr(EntityItem rootEntity)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
    	EntityItem lseoItem = null;

    	//this will create a single LSEO with one or more lseoprodstruct to existing prodstruct
    	seoid = PokUtils.getAttributeValue(rootEntity, "SEOID", "", "", false);
    	addDebug("Checking "+rootEntity.getKey()+" seoid "+seoid);
    	// verify the WWSEOALWR attributes
    	if(verifyEntity(rootEntity,REQ_WWSEOALWR_ATTR)){
    		boolean featureError = false;
    		Vector fcmsgVct = new Vector(1); //hang onto fc checked, so dont have same missing msg over and over
    		// accumulate all features and quantity
    		for (int a=0; a<FCLIST_ATTR.length; a++){
    			String fclist = PokUtils.getAttributeValue(rootEntity, FCLIST_ATTR[a], "", "", false);
    			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(FCLIST_ATTR[a]);
    			if (metaAttr==null) {
    				setReturnCode(FAIL);
    				addOutput(fclist);
    				continue;
    			}
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
    						int fcqtyId = fcQtyVct.indexOf(fcqty);
    						if (fcqtyId!= -1){ // matched on fcode
    							FCQty fcq1 = (FCQty) fcQtyVct.elementAt(fcqtyId);
    							//b.If the quantity is not identical for all instances, report the error, 
    							//set WWSEOALWRABR02 = Failed (0040)
    							if (!fcq1.qty.equals(fcqty.qty)){
    								//DUPLICATE_FCQTY_ERR = Error: Found duplicate Features with conflicting quantity values. Features are {0}
    								args[0]=fcq1+" "+fcqty;
    								addError("DUPLICATE_FCQTY_ERR",args);
    								featureError=true;
    							}else{
    								//a.If the quantity is identical for all instances, then process one of them with a warning message;
    								//however, do NOT set WWSEOALWRABR02 = Failed (0040).
    								//DUPLICATE_FC_MSG = Warning: Found duplicate Feature code {0}.
    								args[0] = fcode;
    								addOutput(getResourceMsg("DUPLICATE_FC_MSG", args));
    							}
    							fcqty.dereference();
    						}else{
    							fcQtyVct.addElement(fcqty);
    						}
    					}
    				} // end has more tokens
    			} // endof featurelist[a] has a value
    		} // end of feature list attributes

    		if(!featureError){    			
    			//Note:  at least one of the lists has to identify at least one Feature Code. Report the selected 
    			//WWSEO, and the WWSEOALWR. Set WWSEOABRALWR02  = Failed (0040).
    			if (fcQtyVct.size()>0){
    				// look for the LSEO matching this seoid with this WWSEO parent
    				lseoItem = findLseo();
    				if (lseoItem!= null){
    					String statusFlag = PokUtils.getAttributeFlagValue(lseoItem, "STATUS");
    					addDebug(lseoItem.getKey()+" STATUS: "+PokUtils.getAttributeValue(lseoItem, "STATUS",", ", "", false)+" ["+statusFlag+"] ");

    					if(null == statusFlag || statusFlag.length()==0)  {
    						statusFlag = STATUS_FINAL; // default to final
    					}
    					//i.If the LSEO STATUS = 0020 (Final), then do NOT update the LSEO and report an Error for this 
    					//WWSEOALWR indicating the WWSEO, the LSEO, and the WWSEOALWR.
    					if (statusFlag.equals(STATUS_FINAL)){
    						//LSEO_FINAL_ERR = Error: LSEO &quot;{0}&quot; is Final
    						args[0] = getNavigationName(lseoItem);
    						addError("LSEO_FINAL_ERR",args);
    					}
    				}else{
    					// look in PDH to make sure this seoid does not exist, error if it does
    					// Report the selected WWSEO, and the referenced WWSEOALWR. Set WWSEOABRALWR02  = Failed (0040).
    					lseoItem = searchForLSEO();
    					if (lseoItem!= null){
    						//LSEO_DUPLICATE_ERR = Error: LSEO with SEOID:{0} already exists in data not managed under this WWSEO
    						args[0] = seoid;
    						addError("LSEO_DUPLICATE_ERR",args);
    					}
    				}
    			}else{
    				//NO_VALIDFC_ERR = Error: No Valid Features listed for {0} &quot;{1}&quot;.
    				args[0] = rootEntity.getEntityGroup().getLongDescription();
    				args[1] = getNavigationName(rootEntity);
    				addError("NO_VALIDFC_ERR",args);    				
    			}
    		}
    	} // end verifyEntity ok    

    	return lseoItem;
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
     * @return
     * @throws MiddlewareShutdownInProgressException
     * @throws MiddlewareException
     * @throws SQLException
     */
    private EntityItem findLseo() throws SQLException, MiddlewareException,
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
    		addDebug("findLseo checking "+item.getKey()+" sid "+sid+" seoid: "+seoid);
    	}

    	return lseoItem;
    }
	/*****************************************
	 * Search for LSEO using:
	 * -	<SEOID>
	 *
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem searchForLSEO()
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
     * 
     * @param cdeItem
     * @param attrlist
     * @return
     * @throws SQLException
     * @throws MiddlewareException
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
     * LSEO Creation
     *
     * If invoked at a WWSEO, then for each instance of CDENTITY (WWSEO -> CDG -> CDENTITY), create
     * a LSEO as a child of the selected WWSEO.
     *
     * If invoked at a LSEO, then for each instance of CDENTITY (LSEO -> CDG -> CDENTITY), create a
     * LSEO as a peer of the selected LSEO (i.e. the selected LSEO's parent WWSEO).
     *
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
    private void createLSEO(EntityItem rootEntity)
    throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException,
    EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
    	addDebug("createLSEO entered for SEOID "+seoid);
    	// create the lseo with wwseo as parent
		EntityItem lseoItem = null;
		AttrSet attrSet = new AttrSet(rootEntity);
		
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
			args[0] = seoid;
			addError("LSEO_CREATE_ERR",args);
		}else{
			createdLseoVct.addElement(new StringBuffer(getNavigationName(lseoItem)));
			// link all prodstructs to this lseo thru lseoprodstruct and set the qty
			createFeatureRefs(lseoItem);
		}
    }

    /*********************
     * Update an existing LSEO if needed
     * all attributes must match
     * all referenced features must match and their quantity
     * extra lseoprodstruct must be deleted
     * @param rootEntity
     * @param lseoItem
     * @throws MiddlewareBusinessRuleException
     * @throws RemoteException
     * @throws EANBusinessRuleException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws SQLException
     * @throws LockException
     * @throws WorkflowException
     */
    private void updateLSEO(EntityItem rootEntity, EntityItem lseoItem)
    throws MiddlewareBusinessRuleException, RemoteException, EANBusinessRuleException,
    MiddlewareException, MiddlewareShutdownInProgressException, SQLException, LockException, WorkflowException
    {
    	addDebug("updateLSEO entered for SEOID "+seoid+" "+lseoItem.getKey());
    	// make sure all attributes match
    	boolean chgsMade = updateLSEOAttributes(rootEntity, lseoItem);

    	// link missing prodstructs
    	// remove extra lseoprodstructs
    	String updatemsg = updateFeatureRefs(lseoItem);
    	if (chgsMade || updatemsg.length()>0){
    		updatedLseoVct.addElement(getNavigationName(lseoItem)+updatemsg);
    	}
    }
    /**************
     * make sure this lseo has the specified attribute values
     *
     * @param rootEntity
     * @param lseoItem
     * @return
     * @throws EANBusinessRuleException
     * @throws MiddlewareBusinessRuleException
     * @throws RemoteException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws SQLException
     */
    private boolean updateLSEOAttributes(EntityItem rootEntity, EntityItem lseoItem)
    throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
    {
    	AttrSet attrSet = new AttrSet(rootEntity);
    	addDebug("updateLSEOAttributes entered for "+lseoItem.getKey());
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
	private void createFeatureRefs(EntityItem lseoItem)
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException,
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException
	{
		String linkAction = LSEOPS_LINKACTION_NAME;
		if (lai ==null){
			lai = new LinkActionItem(null, m_db,m_prof,linkAction);
		}
		EntityItem parentArray[] = new EntityItem[]{lseoItem};
		EntityItem childArray[] = new EntityItem[fcQtyVct.size()];

		Hashtable psFcqTbl = new Hashtable();
		// get each prodstruct
		for (int i=0; i<fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)fcQtyVct.elementAt(i);
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
	private String updateFeatureRefs(EntityItem lseoItem)
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException,
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException
	{
		StringBuffer refSb = new StringBuffer();
		Hashtable psFcqTbl = new Hashtable();
		// get each prodstruct and its qty
		for (int i=0; i<fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)fcQtyVct.elementAt(i);
			psFcqTbl.put(fcq.prodItem.getKey(), fcq);
		}
		// pull all LSEOPRODSTRUCT for current LSEO 
		EntityItem eiArray[] = new EntityItem[]{lseoItem};
		EntityList lseoCurList = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db,m_prof, "BOXERLSEO3"),
				eiArray);
		addDebug("current lseoprodstruct using VE BOXERLSEO3: "+PokUtils.outputList(lseoCurList));
		lseoItem = lseoCurList.getParentEntityGroup().getEntityItem(0); // this one has links
		
		// verify referenced features and quantity
		Vector origPsVct = PokUtils.getAllLinkedEntities(lseoItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
		addDebug("updateFeatureRefs origPsVct "+origPsVct.size());
		for (int i=0; i<origPsVct.size(); i++){
			addDebug("updateFeatureRefs origPsVct["+i+"] "+((EntityItem)origPsVct.elementAt(i)).getKey());
		}

		// get all required prodstructs
		Vector missingPsVct = new Vector();
		for (int i=0; i<fcQtyVct.size(); i++){
			FCQty fcq = (FCQty)fcQtyVct.elementAt(i);
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
		lseoCurList.dereference();
		return refSb.toString();
	}
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
    	super.dereference();

    	for (int i=0;i<fcQtyVct.size(); i++){
			FCQty fcq1 = (FCQty) fcQtyVct.elementAt(i);
			fcq1.dereference();
		}
		fcQtyVct.clear();
		fcQtyVct = null;

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
    }
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "1.3";
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
     * COUNTRYLIST		F		WWSEOALWR.COUNTRYLIST
     * GENAREASELECTION	F		WWSEOALWR.GENAREASELECTION
     * LANGUAGES		U		WWSEOALWR.LANGUAGES*
     * OFFCOUNTRY		U		WWSEOALWR.OFFCOUNTRY*
     * SEOID			T		WWSEOALWR.SEOID
     * COMNAME			T		WWSEO.COMNAME
     * LSEOMKTGDESC		T		WWSEO.MKTGNAME*
     * PDHDOMAIN		F		WWSEO.PDHDOMAIN
     * PRODHIERCD		T		WWSEO.PRODHIERCD*
     * 
     * Note:  Those attributes with a '**' at the end of the Description are not required.
     * 
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
		AttrSet(EntityItem rootEntity){
			// ACCTASGNGRP     	U       '01' (01 - 3000100000)
			attrCodeVct.addElement("ACCTASGNGRP");
			attrValTbl.put("ACCTASGNGRP", "01"); //ACCTASGNGRP	U	Account Assignment Group	"01 - 3000100000" =>ACCTASGNGRP	01	01 - 3000100000
			// AUDIEN          	F
			attrCodeVct.addElement("AUDIEN");
			attrValTbl.put("AUDIEN", AUDIEN_VCT);
			
			//GENAREASELECTION    F       CDENTITY.GENAREASELECTION
			addMult(rootEntity, "GENAREASELECTION");
		    //COUNTRYLIST		F		WWSEOALWR.COUNTRYLIST
			addMult(rootEntity, "COUNTRYLIST");
		    //LANGUAGES		U		WWSEOALWR.LANGUAGES
		    addSingle(rootEntity, "LANGUAGES");
		    //OFFCOUNTRY		U		WWSEOALWR.OFFCOUNTRY
		    addSingle(rootEntity, "OFFCOUNTRY");
		    //SEOID			T		WWSEOALWR.SEOID
		    attrCodeVct.addElement("SEOID");
			attrValTbl.put("SEOID", seoid);
		    //COMNAME			T		WWSEO.COMNAME
		    addText(wwseoItem, "COMNAME");
		    //LSEOMKTGDESC    	T       WWSEO: WWSEO.MKTGNAME
	    	addText(wwseoItem, "MKTGNAME","LSEOMKTGDESC");
			//PRODHIERCD      	T       WWSEO: WWSEO.PRODHIERCD
    		addText(wwseoItem, "PRODHIERCD");
			//PDHDOMAIN       	F       WWSEO: WWSEO.PDHDOMAIN
    		//Entity/Attribute	LSEO	PDHDOMAIN	SetAttributeType	U ->now overrides F
    		// Entity/Attribute	WWSEO	PDHDOMAIN	SetAttributeType	U ->now overrides F
    		EANMetaAttribute ma = wwseoItem.getEntityGroup().getMetaAttribute("PDHDOMAIN");
    		if(ma.getAttributeType().equals("F")){
    			addMult(wwseoItem, "PDHDOMAIN");
    		}else{
    			addSingle(wwseoItem, "PDHDOMAIN");
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
