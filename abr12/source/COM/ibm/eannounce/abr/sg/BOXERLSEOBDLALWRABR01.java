//Licensed Materials -- Property of IBM
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
 * VII. LSEOBUNDLE ALWR
 * This ABR creates LSEOBUNDLE based on setup data related to the LSEOBUNDLE found in LSEOBDLALWR. 
 * This data is created via the User Interface (JUI or BUI) in a manner similar to creating and 
 * editing offering information.
 * ABR attribute is LSEOBDLALWR.LSEOBDLALWRABR01
 * It will create an LSEOBUNDLE if one with the specified SEOID does not exist in the PDH.  It will link the LSEOs
 * from the same pdhdomain as the parent LSEOBUNDLE.
 * If the LSEOBUNDLE exists, LSEOs will be linked if the LSEOBUNDLE is not Final and is in the same pdhdomain
 * as the parent LSEOBUNDLE.
 */
//BOXERLSEOBDLALWRABR01.java,v
//Revision 1.3  2009/04/01 13:02:56  wendy
//Handle PDHDOMAIN meta chg from F to U
//
//Revision 1.2  2009/02/18 13:06:43  wendy
//Changed LSEOBUNDLE search action
//
//Revision 1.1  2009/02/17 19:08:44  wendy
//CQ00007061 Boxer
//
public class BOXERLSEOBDLALWRABR01 extends PokBaseABR 
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private Object[] args = new String[10];

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private LinkActionItem lai = null;
    private DeleteActionItem dai = null;
    private Vector createdLseoBdlVct = new Vector(1);
    private Vector updatedLseoBdlVct = new Vector(1);
    private String bdlseoid;
    private Vector lseoQtyVct = new Vector();
    private Hashtable lseoTbl = new Hashtable(); // hang onto lseo when found
    
    private static final String LSEOBDL_SRCHACTION_NAME = "SRDLSEOBDL4";//"SRDLSEOBUNDLE1"; //need srch wo domain restrictions 
    private static final String LSEOBDL_CREATEACTION_NAME = "CRLSEOBUNDLE"; 
    private static final String LSEOBDLLSEO_LINKACTION_NAME = "LINKLSEOLSEOBUNDLE";
    private static final String LSEO_SRCHACTION_NAME = "SRDLSEO3"; // need search w domain restrictions
    private static final String LSEOBDLLSEO_DELETEACTION_NAME = "DELLSEOBUNDLELSEO";
    private static final String STATUS_FINAL = "0020";
  
    private static final String[] LSEOLIST_ATTR = {"HIPOLSEOLIST","HWLSEOLIST","SERVLSEOLIST","SWLSEOLIST"};

    private static final String[] REQ_ALWR_ATTR = {"COUNTRYLIST","CD","XXPARTNO"}; //should have a local rule but chk anyway
    
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
            Any errors or list LSEOBUNDLE created or changed
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
            start_ABRBuild();
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
            rootDesc = PokUtils.getAttributeDescription(m_elist.getParentEntityGroup(), 
            		m_abri.getABRCode(), m_abri.getABRCode())+" on "+
            	m_elist.getParentEntityGroup().getLongDescription()+" &quot;"+navName+"&quot; ";
          
            EntityGroup bdlGrp = m_elist.getEntityGroup("LSEOBUNDLE");
            EntityItem lseoBundleItem=null; // this must exist and is the parent to the LSEOBDLALWR
            if (bdlGrp.getEntityItemCount()==0){
                //NOT_FOUND_ERR = Error: No {0} found.
                args[0] =bdlGrp.getLongDescription();
                addError("NOT_FOUND_ERR",args);
            }else{
            	lseoBundleItem = bdlGrp.getEntityItem(0);
            	rootDesc = rootDesc+"<br />for "+bdlGrp.getLongDescription()+" &quot;"+getNavigationName(lseoBundleItem)+"&quot;";
            }
            if(getReturnCode()== PokBaseABR.PASS){
                // validate the LSEOBDLALWR and accumulate the list of lseo 
                EntityItem otherBdlItem = checkAlwr(rootEntity,lseoBundleItem);
                if(getReturnCode()== PokBaseABR.PASS){
                	if (otherBdlItem==null){
                		createLSEOBundle(rootEntity,lseoBundleItem);
                	}else{
                	    // link missing lseo
                        // remove extra lseobundlelseo  
                        String updatemsg = updateLseoRefs(otherBdlItem);
                        if (updatemsg.length()>0){
                            updatedLseoBdlVct.addElement(getNavigationName(otherBdlItem)+updatemsg);
                        }
                	}

                	if (createdLseoBdlVct.size()>0){
                		// CREATED_MSG = Created LSEOBUNDLE: {0}
                		args[0]="";
                		for (int i=0; i<createdLseoBdlVct.size(); i++){
                			args[0] = args[0]+createdLseoBdlVct.elementAt(i).toString();
                		}
                		msgf = new MessageFormat(rsBundle.getString("CREATED_MSG"));
                		addOutput(msgf.format(args));
                	}
                	if (updatedLseoBdlVct.size()>0){
                		// UPDATED_MSG = Updated LSEOBUNDLE: {0}
                		args[0]="";
                		for (int i=0; i<updatedLseoBdlVct.size(); i++){
                			args[0] = args[0]+updatedLseoBdlVct.elementAt(i).toString();
                		}
                		msgf = new MessageFormat(rsBundle.getString("UPDATED_MSG"));
                		addOutput(msgf.format(args));
                	}
                	if (createdLseoBdlVct.size()==0 && updatedLseoBdlVct.size()==0){
                		//NO_CHGS=No changes made.
                		addOutput(rsBundle.getString("NO_CHGS"));
                	}
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
     * Entity Type:  LSEOBDLALWR
     * Attributes:
     * Attributecode	Type	LONGDESCRIPTION
     * CD				T		Country Designator *
     * COUNTRYLIST		F		Country List *
     * HIPOLSEOLIST		T		HIPO LSEO List
     * HWLSEOLIST		T		HW LSEO List
     * LANGUAGES		U		Language
     * LSEOBDLALWRABR01	A		LSEOBUNDLE ALWR With CD ABR
     * OFFCOUNTRY		U		Offering Country
     * SERVLSEOLIST		T		Services LSEO List
     * SWLSEOLIST		T		SW LSEO List
     * XXPARTNO			T		XX Part Number *
     * 
     * Notes:
     * 1.	Long Description * indicates a local rule will require the attribute
     * 2.	Left(LSEOBDLALWR.XXPARTNO,5) && LSEOBDLALWR.CD is the SEO ID of the LSEOBUNDLE required.
     * If the length of CD <> 2, then report an error and set LSEOBDLALWRABR01= Failed (0040).
     * Note: a local rule requires the length to be either 1 or 2 characters in length since the attribute is shared.
     * 3.	There are four lists of LSEOs
     * a.	HIPO
     * b.	HW (hardware)
     * c.	Services
     * d.	SW (software)
     * 4.	The LSEO Lists are lists of LSEOs (seoid) separated by a comma. The optional quantity is
     * separated from SEOID by a colon. The default quantity is one. Spaces are ignored.
     * Format:  seoid or seoid:quantity
     * e.g. 1234567, 3456789:2, 7654321:1
     * 5.	There are four lists of LSEOs to facilitate data entry. From an ABR point of view, the four
     * lists could be concatenated using a comma between the lists. The ABR should not treat the lists differently.
     * 6.	If a LSEO is duplicated either within a list or in different lists:
     * a.	If the quantity is identical for all instances, then process one of them with a warning message; however,
     * do NOT set LSEOBDLALWRABR01= Failed (0040).
     * b.	If the quantity is not identical for all instances, report the error, and set LSEOBDLALWRABR01= Failed (0040)
     * 
     * B.	Processing
     * 1.	Current LSEOBUNDLE has a matching SEOID to one that is needed. A search without PDHDOMAIN 
     * for a LSEOBUNDLE using SEOID should be used. If the found LSEOBUNDLE’s PDHDOMAIN does not match 
     * the PDHDOMAIN of the Workgroup (WG), then report an error for this LSEOBDLALWR indicating the 
     * parent LSEOBUNDLE, the LSEOBDLALWR and the SEOID that is duplicate to data not managed by this 
     * Workgroup and set LSEOBDLALWRABR01= Failed (0040) and exit.
     * a.	Everything matches (i.e. list of LSEOs match – nothing to do.)
     * b.	Not an exact match (i.e.. list of LSEOs do not match) 
     * i.	If the needed LSEOBUNDLE STATUS = 0020 (Final), then do NOT update the LSEOBUNDLE and report 
     * an Error for this LSEOBDLALWR indicating the parent LSEOBUNDLE, the needed/child LSEOBUNDLE, and the LSEOBDLALWR.
     * ii.	If the needed LSEOBUNDLE STATUS <> 0020 (Final), then update the needed LSEOBUNDLE’s references 
     * to LSEOs to match the requirement; however, do NOT update the attributes of the LSEOBUNDLE. 
     * 2.	No current LSEOBUNDLE – create the LSEOBUNDLE
     * 
     * E.	Error Messages
     * 
     * 1.	An instance of LSEOBDLALWR has no LSEOs identified. List the instance that is incomplete.
     * Note:  at least one of the lists has to identify at least one LSEO.
     * 2.	One of the LSEO lists identifies a LSEO that is not available (i.e. the LSEO must exist within the PDHDOMAIN).
     * 
     * @param alwrItem
     * @param origBdlItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private EntityItem checkAlwr(EntityItem alwrItem, EntityItem origBdlItem) 
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
        EntityItem lseoBdl = null;

        //look at LSEOBDLALWR and validate
        // verify the LSEOBDLALWR attributes
        if(verifyEntity(alwrItem,REQ_ALWR_ATTR)){
        	//2. If the length of CD <> 2, then report an error and set LSEOBDLALWRABR01= Failed (0040).
        	String cd = PokUtils.getAttributeValue(alwrItem, "CD", "", "", false);
        	addDebug("Checking "+alwrItem.getKey()+" CD: "+cd);
        	int cdlen = cd.length();
        	if (cdlen!=2){ 
        		//INVALID_CD_ERR = Error: {0} &quot;{1}&quot; has invalid {2} = &quot;{3}&quot;
    			args[0] = alwrItem.getEntityGroup().getLongDescription();
    			args[1] = getNavigationName(alwrItem);
        		args[2]=PokUtils.getAttributeDescription(alwrItem.getEntityGroup(), "CD", "CD");
        		args[3] = cd; 
        		addError("INVALID_CD_ERR",args);
        		return lseoBdl;
        	}

        	//Left(LSEOBDLALWR.XXPARTNO,5) && LSEOBDLALWR.CD is the SEO ID of the LSEOBUNDLE required.
        	bdlseoid = PokUtils.getAttributeValue(alwrItem, "XXPARTNO", "", "", false);
        	if (bdlseoid.length()>5){
        		bdlseoid=bdlseoid.substring(0, 5);
        	}
        	bdlseoid = bdlseoid+cd;
        	addDebug("derived bdlseoid "+bdlseoid);

        	Vector lseomsgVct = new Vector(1); //hang onto lseo checked, so dont have same missing msg over and over
        	boolean lseoError = false;
        	// accumulate all lseo and quantity
        	for (int a=0; a<LSEOLIST_ATTR.length; a++){
        		String lseolist = PokUtils.getAttributeValue(alwrItem, LSEOLIST_ATTR[a], "", "", false);
        		addDebug(LSEOLIST_ATTR[a]+" "+lseolist);
        		if (lseolist.length()>0){
        			StringTokenizer st = new StringTokenizer(lseolist,",");
        			while(st.hasMoreTokens()) {
        				String qty = "1";
        				String lseoid = st.nextToken().trim();
        				// check for qty
        				int id = lseoid.indexOf(":");
        				if (id != -1){
        					qty = lseoid.substring(id+1).trim();
        					lseoid = lseoid.substring(0,id).trim();
        				}

        				//2.One of the LSEO lists identifies a LSEO that is not available (i.e. the LSEO must exist within the PDHDOMAIN)
        				EntityItem lseoItem = searchForLSEO(lseoid); // need domain restricted search here
        				if (lseoItem==null){
        					if (!lseomsgVct.contains(lseoid)){
        						//LSEO_NOTFOUND_ERR = Error: LSEO SEOID: {0} does not exist within the {1} domain.
        						args[0] = lseoid;
        						args[1] = PokUtils.getAttributeValue(origBdlItem, "PDHDOMAIN", ", ", "", false);
        						addError("LSEO_NOTFOUND_ERR",args);
        						lseomsgVct.addElement(lseoid);
        					}
        					lseoError = true;
        				}else{
        					LseoQty lseoQty = new LseoQty(lseoid, qty,lseoItem);
        					int lseoQtyId = lseoQtyVct.indexOf(lseoQty);
        					if (lseoQtyId!= -1){ // matched on seoid
        						LseoQty lsq1 = (LseoQty) lseoQtyVct.elementAt(lseoQtyId);
        						//b.If the quantity is not identical for all instances, report the error, and set LSEOBDLALWRABR01= Failed (0040)
        						if (!lsq1.qty.equals(lseoQty.qty)){
        							//DUPLICATE_LSEOQTY_ERR = Error: Found duplicate LSEO with conflicting quantity values for {0} &quot;{1}&quot;. LSEO are {2}
        							args[0] = alwrItem.getEntityGroup().getLongDescription();
        							args[1] = getNavigationName(alwrItem);
        							args[2]=lsq1+" "+lseoQty;
        							addError("DUPLICATE_LSEOQTY_ERR",args);
        							lseoError=true;
        						}else{
        							//a.If the quantity is identical for all instances, then process one of them with a warning message; 
        							//however, do NOT set LSEOBDLALWRABR01= Failed (0040).
        							//DUPLICATE_LSEO_MSG = Warning: Found duplicate LSEO SEOID: {0} in {1} &quot;{2}&quot;.
        							args[0] = lseoid;
        							args[1] = alwrItem.getEntityGroup().getLongDescription();
        							args[2] = getNavigationName(alwrItem);
        							addOutput(getResourceMsg("DUPLICATE_LSEO_MSG", args));
        						}
        						lseoQty.dereference();
        					}else{
        						lseoQtyVct.addElement(lseoQty);
        					}
        				}
        			} // end has more tokens
        		} // endof lseolist[a] has a value
        	} // end of list attributes

        	if (!lseoError){
        		//1.An instance of LSEOBDLALWR has no LSEOs identified. List the instance that is incomplete.
        		//Note:  at least one of the lists has to identify at least one LSEO.
        		if (lseoQtyVct.size()>0){
        			// search for the LSEOBUNDLE matching this bdlseoid
        			lseoBdl = searchForLseoBundle();
        			if (lseoBdl!= null){
        				String statusFlag = PokUtils.getAttributeFlagValue(lseoBdl, "STATUS");
        				addDebug(lseoBdl.getKey()+" STATUS: "+PokUtils.getAttributeValue(lseoBdl, "STATUS",", ", "", false)+" ["+statusFlag+"] ");

        				if(null == statusFlag || statusFlag.length()==0)  {
        					statusFlag = STATUS_FINAL; // default to final 
        				}
        				if (statusFlag.equals(STATUS_FINAL)){
        					//LSEOBDL_FINAL_ERR = Error: LSEOBUNDLE with SEOID:{0} is Final. 
        					args[0] = bdlseoid;
        					addError("LSEOBDL_FINAL_ERR",args);  
        				}else{
        					// check pdhdomain - must allow for multiple values
        					String origPdhDomain[] = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(origBdlItem, "PDHDOMAIN"));
        					String pdhDomain[] = PokUtils.convertToArray(PokUtils.getAttributeFlagValue(lseoBdl, "PDHDOMAIN"));
        					Vector origVct = new Vector();
        					Vector vct = new Vector();
        					for (int i=0; i<pdhDomain.length; i++){
        						vct.addElement(pdhDomain[i]);
        					}
        					for (int i=0; i<origPdhDomain.length; i++){
        						origVct.addElement(origPdhDomain[i]);
        					}
        					if (origVct.containsAll(vct)&&vct.containsAll(origVct)){}
        					else{
        						//parent LSEOBUNDLE, the LSEOBDLALWR and the SEOID that is duplicate to data not managed by this Workgroup
        						//LSEOBDL_EXISTS_ERR= The LSEOBUNDLE SEOID {0} already exists in data not managed by this Workgroup.
        	        			args[0] = bdlseoid;
        	        			addError("LSEOBDL_EXISTS_ERR",args);
        					}
        				}
        			}
        		}else{
        			//NO_ALWRLSEO_ERR = Error: No Valid LSEO listed for {0} &quot;{1}&quot;.
        			args[0] = alwrItem.getEntityGroup().getLongDescription();
        			args[1] = getNavigationName(alwrItem);
        			addError("NO_ALWRLSEO_ERR",args);
        		}
        	}  // end no lseo errors
        }// end verifyEntity ok
        
        return lseoBdl;
    }
  
    /*****************************************
     * Search for LSEOBUNDLE using:
     * -    <SEOID>
     * 
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private EntityItem searchForLseoBundle() 
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
        EntityItem lseobdl = null;

        // check if it is the rootentity first
        EntityGroup eGrp = m_elist.getParentEntityGroup();
        for (int i=0; i<eGrp.getEntityItemCount(); i++){
            EntityItem item = eGrp.getEntityItem(i);
            String fc = PokUtils.getAttributeValue(item, "SEOID", "", "", false);
            if (fc.equals(bdlseoid)){
                lseobdl = item;
                addDebug("searchForLseoBundle using "+lseobdl.getKey());
                break;
            }
        }
        if (lseobdl==null){
            Vector attrVct = new Vector(1);
            attrVct.addElement("SEOID");
            Vector valVct = new Vector(1);
            valVct.addElement(bdlseoid);

            EntityItem eia[]= null;
            try{
                StringBuffer debugSb = new StringBuffer();
                eia= ABRUtil.doSearch(m_db, m_prof, 
                        LSEOBDL_SRCHACTION_NAME, "LSEOBUNDLE", false, attrVct, valVct, debugSb);
                if (debugSb.length()>0){
                    addDebug(debugSb.toString());
                }
            }catch(SBRException exc){
                // these exceptions are for missing flagcodes or failed business rules, dont pass back
                java.io.StringWriter exBuf = new java.io.StringWriter();
                exc.printStackTrace(new java.io.PrintWriter(exBuf));
                addDebug("searchForLseoBundle SBRException: "+exBuf.getBuffer().toString());
            }
            if (eia!=null && eia.length > 0){           
                for (int i=0; i<eia.length; i++){
                    addDebug("searchForLseoBundle found "+eia[i].getKey());
                }

                lseobdl = eia[0];
            }
            attrVct.clear();
            valVct.clear();
        }   
        
        return lseobdl;
    }    
    /*****************************************
     * Search for LSEO using:
     * -    <SEOID>
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
        EntityItem lseo = (EntityItem)lseoTbl.get(seoid); // check if already found
        if (lseo==null){
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
                lseoTbl.put(seoid,lseo);
            }
            attrVct.clear();
            valVct.clear();
        }
            
        return lseo;
    }    
    /**************
     * Check for required attributes
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
     * LSEOBundle Creation
     *   
     * @param rootEntity
     * @param curlseoBundleItem
     * @throws MiddlewareRequestException
     * @throws RemoteException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException
     * @throws LockException
     * @throws WorkflowException
     */
    private void createLSEOBundle(EntityItem rootEntity,EntityItem curlseoBundleItem) 
    throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
    {
        addDebug("createLSEOBundle entered for SEOID "+bdlseoid+" curlseoBundleItem "+curlseoBundleItem.getKey());
        // create the lseobundle with wg as parent
        EntityItem lseobdlItem = null;
        AttrSet attrSet = new AttrSet(rootEntity,curlseoBundleItem);
        
        StringBuffer debugSb = new StringBuffer();
        EntityItem wgItem = new EntityItem(null, m_prof, "WG", m_prof.getWGID());
        lseobdlItem = ABRUtil.createEntity(m_db, m_prof, LSEOBDL_CREATEACTION_NAME, wgItem,  
                "LSEOBUNDLE", attrSet.getAttrCodes(), attrSet.getAttrValues(), debugSb); 
        if (debugSb.length()>0){
            addDebug(debugSb.toString());
        }
    
        // release memory
        attrSet.dereference();  
        
        if (lseobdlItem==null){
            //LSEOBDL_CREATE_ERR = Error: Can not create LSEOBUNDLE entity for SEOID:{0}
            args[0] = bdlseoid;
            addError("LSEOBDL_CREATE_ERR",args);
        }else{      
            createdLseoBdlVct.addElement(new StringBuffer(getNavigationName(lseobdlItem)));
            // link all lseo to this lseobundle and set the qty
            createLseoRefs(lseobdlItem);
        }   
    }

    /**
     * link the LSEO to the newly created LSEOBUNDLE
     * @param lseobdlItem
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws LockException
     * @throws MiddlewareShutdownInProgressException
     * @throws EANBusinessRuleException
     * @throws WorkflowException
     * @throws RemoteException
     */
    private void createLseoRefs(EntityItem lseobdlItem) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
    MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException     
    {
        String linkAction = LSEOBDLLSEO_LINKACTION_NAME;
        if (lai ==null){
            lai = new LinkActionItem(null, m_db,m_prof,linkAction);
        }
        EntityItem parentArray[] = new EntityItem[]{lseobdlItem};
        EntityItem childArray[] = new EntityItem[lseoQtyVct.size()];

        Hashtable lseoFcqTbl = new Hashtable();
        // get each lseo
        for (int i=0; i<lseoQtyVct.size(); i++){
            LseoQty fcq = (LseoQty)lseoQtyVct.elementAt(i);
            childArray[i] = fcq.lseoItem;
            lseoFcqTbl.put(childArray[i].getKey(), fcq);
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
        // VE for lseobundle to each lseo BOXERLSEOBDL2
        EntityList list = m_db.getEntityList(profile, 
                new ExtractActionItem(null, m_db,profile, "BOXERLSEOBDL2"), 
                parentArray);

        addDebug("createLseoRefs list using VE BOXERLSEOBDL2 after linkaction: "+
                linkAction+"\n"+PokUtils.outputList(list));
        EntityGroup lseoGrp = list.getEntityGroup("LSEO");
        StringBuffer refSb = new StringBuffer();
        for (int x=0; x<lseoGrp.getEntityItemCount(); x++){
            EntityItem lseoitem = lseoGrp.getEntityItem(x);
            LseoQty fcq = (LseoQty)lseoFcqTbl.get(lseoitem.getKey());
            String qty = fcq.qty;
            //ADDED_REF_MSG=<br />&nbsp;&nbsp;Adding reference to LSEO {0} with quantity {1}
            refSb.append(getResourceMsg("ADDED_REF_MSG", new Object[]{fcq.seoid,fcq.qty}));
            EntityItem lseobdllseoitem = (EntityItem)lseoitem.getUpLink(0);  // get the new relator
            addDebug(lseoitem.getKey()+" use qty: "+qty+" on "+lseobdllseoitem.getKey());
            if (qty!= null && !qty.equals("1")){  // 1 is default so nothing needed
                StringBuffer debugSb = new StringBuffer();
                // save the qty attribute
                ABRUtil.setText(lseobdllseoitem,"LSEOQTY", qty, debugSb); 
                if (debugSb.length()>0){
                    addDebug(debugSb.toString());
                }
                // must commit changed entity to the PDH 
                lseobdllseoitem.commit(m_db, null); 
            }
        }
        StringBuffer lseoinfo = (StringBuffer)createdLseoBdlVct.lastElement();
        lseoinfo.append(refSb.toString());
        
        lseoFcqTbl.clear();
        list.dereference();
    }
    
    /**********************
     *  link missing lseos, remove extra lseobundlelseo, update qty on existing lseobundlelseo
     *  if needed
     * @param lseobdlItem
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
    private String updateLseoRefs(EntityItem lseobdlItem) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
    MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException     
    {
        StringBuffer refSb = new StringBuffer();
        Hashtable lseoFcqTbl = new Hashtable();
        // get each lseo and its qty
        for (int i=0; i<lseoQtyVct.size(); i++){
            LseoQty fcq = (LseoQty)lseoQtyVct.elementAt(i);
            lseoFcqTbl.put(fcq.lseoItem.getKey(), fcq);
        }   
        
        EntityItem parentArray[] = new EntityItem[]{lseobdlItem};
        
        // VE for lseobundle to each lseo BOXERLSEOBDL2
        EntityList curlist = m_db.getEntityList(m_prof, 
                new ExtractActionItem(null, m_db,m_prof, "BOXERLSEOBDL2"), 
                parentArray);
        addDebug("updateLseoRefs list using VE BOXERLSEOBDL2 current list: \n"
                +PokUtils.outputList(curlist));
        lseobdlItem = curlist.getParentEntityGroup().getEntityItem(0); // get one with links
        // verify referenced lseo and quantity
        Vector origLsVct = PokUtils.getAllLinkedEntities(lseobdlItem, "LSEOBUNDLELSEO", "LSEO");
        addDebug("updateLseoRefs origLsVct "+origLsVct.size());
        for (int i=0; i<origLsVct.size(); i++){
            addDebug("updateLseoRefs origLsVct["+i+"] "+((EntityItem)origLsVct.elementAt(i)).getKey());
        }
        
        // get all required lseo
        Vector missingLsVct = new Vector();
        for (int i=0; i<lseoQtyVct.size(); i++){
            LseoQty fcq = (LseoQty)lseoQtyVct.elementAt(i);
            boolean found = false;
            Iterator itr = origLsVct.iterator();
            while (itr.hasNext()){
                EntityItem psItem = (EntityItem)itr.next();
                if (psItem.getKey().equals(fcq.lseoItem.getKey())){
                    addDebug("updateLseoRefs already exists "+fcq.lseoItem.getKey());
                    found = true;
                    itr.remove();
                    break;
                }
            }
            if (!found){
                addDebug("updateLseoRefs missing "+fcq.lseoItem.getKey());
                missingLsVct.add(fcq.lseoItem);
                //ADDED_REF_MSG=<br />&nbsp;&nbsp;Adding reference to LSEO {0} with quantity {1}
                refSb.append(getResourceMsg("ADDED_REF_MSG", new Object[]{fcq.seoid, fcq.qty}));
            }
        }
        
        String linkAction = LSEOBDLLSEO_LINKACTION_NAME;
        // add any missing LSEOBUNDLELSEO
        if (missingLsVct.size()>0){
            addDebug("updateLseoRefs  missingLsVct "+missingLsVct.size());
            for (int i=0; i<missingLsVct.size(); i++){
                addDebug("updateLseoRefs missingLsVct["+i+"] "+((EntityItem)missingLsVct.elementAt(i)).getKey());
            }
            if (lai ==null){
                lai = new LinkActionItem(null, m_db,m_prof,linkAction);
            }

            EntityItem childArray[] = new EntityItem[missingLsVct.size()];
            missingLsVct.copyInto(childArray);

            // do the link  
            lai.setParentEntityItems(parentArray);     
            lai.setChildEntityItems(childArray);
            m_db.executeAction(m_prof, lai);
        }
        // remove any extra LSEOBUNDLELSEO
        String deleteAction =LSEOBDLLSEO_DELETEACTION_NAME;
        if (origLsVct.size()>0){
            addDebug("updateLseoRefs unneeded cnt "+origLsVct.size());
            if (dai ==null){
                dai = new DeleteActionItem(null, m_db,m_prof,deleteAction);
            }
            EntityItem childArray[] = new EntityItem[origLsVct.size()];
            for (int i=0; i<origLsVct.size(); i++){
                EntityItem lseoitem = (EntityItem)origLsVct.elementAt(i);
                addDebug("updateLseoRefs unneeded ["+i+"] "+lseoitem.getKey());
                //DELETED_REF_MSG=<br />&nbsp;&nbsp;Deleting reference to LSEO {0}
                refSb.append(getResourceMsg("DELETED_REF_MSG", 
                        new Object[]{PokUtils.getAttributeValue(lseoitem, "SEOID", "", "", false)}));
                childArray[i]=(EntityItem) lseoitem.getUpLink(0);   
            }
            
            // do the delete    
            dai.setEntityItems(childArray);
            m_db.executeAction(m_prof, dai);
            origLsVct.clear();
        }       
            
        // extract and update QTY
        //update dts in profile
        Profile profile = m_prof.getNewInstance(m_db);
        String now = m_db.getDates().getNow();
        profile.setValOnEffOn(now, now);
        // VE for lseobundle to each lseo BOXERLSEOBDL2
        EntityList list = m_db.getEntityList(profile, 
                new ExtractActionItem(null, m_db,profile, "BOXERLSEOBDL2"), 
                parentArray);

        addDebug("updateLseoRefs list using VE BOXERLSEOBDL2 after linkaction: "+
                linkAction+" and deleteaction: "+deleteAction+"\n"+PokUtils.outputList(list));
        EntityGroup lseoGrp = list.getEntityGroup("LSEO");
        
        for (int x=0; x<lseoGrp.getEntityItemCount(); x++){
            EntityItem lseoitem = lseoGrp.getEntityItem(x);
            LseoQty fcq = (LseoQty)lseoFcqTbl.get(lseoitem.getKey());
            String qty = fcq.qty;
            EntityItem lseobdllseoitem = (EntityItem)lseoitem.getUpLink(0);  // get the relator
            String qtyValue = PokUtils.getAttributeValue(lseobdllseoitem, "LSEOQTY", "", "", false);
            addDebug(lseoitem.getKey()+" needs qty: "+qty+" on "+lseobdllseoitem.getKey()+" has qty: "+qtyValue);
            if (qty!= null && !qty.equals(qtyValue)){  //must match
                StringBuffer debugSb = new StringBuffer();
                // save the qty attribute
                ABRUtil.setText(lseobdllseoitem,"LSEOQTY", qty, debugSb); 
                if (debugSb.length()>0){
                    addDebug(debugSb.toString());
                }
                if (!missingLsVct.contains(lseoitem)){ // this lseobdl->lseo already existed
                    //UPDATED_REF_MSG=<br />&nbsp;&nbsp;Updating reference to LSEO {0} with quantity {1}
                    refSb.append(getResourceMsg("UPDATED_REF_MSG", new Object[]{fcq.seoid,fcq.qty}));
                }
                // must commit changed entity to the PDH 
                lseobdllseoitem.commit(m_db, null); 
            }
        }
        
        lseoFcqTbl.clear();
        missingLsVct.clear();
        list.dereference();
        curlist.dereference();
        return refSb.toString();
    }
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
        super.dereference();
 
        for (int v=0; v<lseoQtyVct.size(); v++){
        	LseoQty sma = (LseoQty)lseoQtyVct.elementAt(v);
            sma.dereference();
        }
        lseoQtyVct.clear();
        lseoQtyVct = null;
        
        createdLseoBdlVct.clear();
        createdLseoBdlVct = null;
        updatedLseoBdlVct.clear();
        updatedLseoBdlVct = null;
        
        rsBundle = null;
        lai = null;
        dai = null;
        
        rptSb = null;
        args = null;
        bdlseoid = null;

        metaTbl = null;
        navName = null;
        lseoTbl.clear();
        lseoTbl = null;
    }
    public String getABRVersion() {
        return "1.3";
    }

    public String getDescription() {
        return "LSEOBDLALWRABR01";
    }
    /**********************************
     * add msg to report output
     */
    private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

    /**********************************
     * add debug info as html comment
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
     * For the instance of LSEOBDLALWR, create a LSEOBUNDLE. 
     * 
     * The LSEOBUNDLE attributes are as follows:
     * Attributecode	Type	Derivation
     * ACCTASGNGRP		U		Selected LSEOBUNDLE
     * AUDIEN			F		Selected LSEOBUNDLE
     * BUNDLETYPE		F		Selected LSEOBUNDLE
     * BUNDLMKTGDESC	T		Selected LSEOBUNDLE
     * BUNDLPUBDATEMTRGT	T	Selected LSEOBUNDLE
     * BUNDLUNPUBDATEMTRGT	T	Selected LSEOBUNDLE
     * CNTRYOFMFR		F		Selected LSEOBUNDLE
     * COMNAME			T		Selected LSEOBUNDLE
     * COUNTRYLIST		F		Selected LSEOBUNDLE
     * EANCD			T		Selected LSEOBUNDLE
     * FLFILSYSINDC		F		Selected LSEOBUNDLE
     * FUNCCLS			T		Selected LSEOBUNDLE
     * GENAREASELECTION	F		Selected LSEOBUNDLE
     * INDDEFNCATG		U		Selected LSEOBUNDLE
     * JANCD			T		Selected LSEOBUNDLE
     * KANGCD			U		Selected LSEOBUNDLE
     * LANGUAGES		U		LSEOBDLALWR.LANGUAGES
     * OFFCOUNTRY		U		LSEOBDLALWR.OFFCOUNTRY
     * OSLEVEL			F		Selected LSEOBUNDLE
     * PDHDOMAIN		F		Selected LSEOBUNDLE
     * PLANRELEVANT		U		Selected LSEOBUNDLE
     * PRCFILENAM		T		Selected LSEOBUNDLE
     * PRODHIERCD		T		Selected LSEOBUNDLE
     * PRODID			T		Selected LSEOBUNDLE
     * PROJCDNAM		U		Selected LSEOBUNDLE
     * PROMO			U		Selected LSEOBUNDLE
     * RPTEXCELVE		U		Selected LSEOBUNDLE
     * SAPASSORTMODULE	T		Selected LSEOBUNDLE
     * SEOID			T		Left(LSEOBDLALWR.XXPARTNO,5)&& LSEOBDLALWR.CD
     * SHIPGRPDESCLNGTXT	L	Selected LSEOBUNDLE
     * SPECBID			U		Selected LSEOBUNDLE
     * SPECMODDESGN		U		Selected LSEOBUNDLE
     * TAXCD			T		Selected LSEOBUNDLE
     * UPCCD			T		Selected LSEOBUNDLE
     * XXPARTNO			T		LSEOBDLALWR.XXPARTNO                   
     */   
    private class AttrSet{
		private Vector attrCodeVct = new Vector();
		private Hashtable attrValTbl = new Hashtable();
		protected void addSingle(EntityItem item, String attrCode){
			String value = PokUtils.getAttributeFlagValue(item, attrCode);
			if (value==null){
				value = "";
			}
			attrCodeVct.addElement(attrCode);
			attrValTbl.put(attrCode, value);
		}
		protected void addText(EntityItem item, String attrCode){
			String 	value = PokUtils.getAttributeValue(item, attrCode, "", "", false);
			attrCodeVct.addElement(attrCode);
			attrValTbl.put(attrCode, value);
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
        AttrSet(EntityItem alwrItem, EntityItem curlseoBundleItem){
        	//ACCTASGNGRP		U		Selected LSEOBUNDLE
            addSingle(curlseoBundleItem, "ACCTASGNGRP");
            //AUDIEN            F       Selected LSEOBUNDLE 
            addMult(curlseoBundleItem, "AUDIEN");
        	//BUNDLETYPE		F		Selected LSEOBUNDLE
        	addMult(curlseoBundleItem, "BUNDLETYPE");
            //BUNDLMKTGDESC       T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "BUNDLMKTGDESC");
            //BUNDLPUBDATEMTRGT   T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "BUNDLPUBDATEMTRGT");
            //BUNDLUNPUBDATEMTRGT T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "BUNDLUNPUBDATEMTRGT");
            //CNTRYOFMFR          F       Selected LSEOBUNDLE
            addMult(curlseoBundleItem, "CNTRYOFMFR");
            //COMNAME             T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "COMNAME");
            //GENAREASELECTION    F       Selected LSEOBUNDLE
            addMult(curlseoBundleItem, "GENAREASELECTION");
            //COUNTRYLIST         F       Selected LSEOBUNDLE
            addMult(curlseoBundleItem, "COUNTRYLIST");
            //EANCD               T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "EANCD");
            //FLFILSYSINDC        F       Selected LSEOBUNDLE
            addMult(curlseoBundleItem, "FLFILSYSINDC");
        	//FUNCCLS			T		Selected LSEOBUNDLE
        	addText(curlseoBundleItem, "FUNCCLS");
        	//INDDEFNCATG		U		Selected LSEOBUNDLE
        	addSingle(curlseoBundleItem, "INDDEFNCATG");
        	//JANCD			T		Selected LSEOBUNDLE
        	addText(curlseoBundleItem, "JANCD");
        	//KANGCD			U		Selected LSEOBUNDLE
        	addSingle(curlseoBundleItem, "KANGCD");
        	//OSLEVEL			F		Selected LSEOBUNDLE
        	addMult(curlseoBundleItem, "OSLEVEL");
            //PDHDOMAIN           F       Selected LSEOBUNDLE
        	//Entity/Attribute	LSEOBUNDLE	PDHDOMAIN	SetAttributeType	U ->now overrides F
    		EANMetaAttribute ma = curlseoBundleItem.getEntityGroup().getMetaAttribute("PDHDOMAIN");
    		if(ma.getAttributeType().equals("F")){
    			addMult(curlseoBundleItem, "PDHDOMAIN");
    		}else{
    			addSingle(curlseoBundleItem, "PDHDOMAIN");
    		}
            //PLANRELEVANT        U       Selected LSEOBUNDLE
            addSingle(curlseoBundleItem, "PLANRELEVANT");
        	//PRCFILENAM		T		Selected LSEOBUNDLE
        	addText(curlseoBundleItem, "PRCFILENAM");
            //PRODHIERCD          T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "PRODHIERCD");
        	//PRODID			T		Selected LSEOBUNDLE
        	addText(curlseoBundleItem, "PRODID");
            //PROJCDNAM           U       Selected LSEOBUNDLE
            addSingle(curlseoBundleItem, "PROJCDNAM");
            //PROMO               U       Selected LSEOBUNDLE
            addSingle(curlseoBundleItem, "PROMO");
        	//RPTEXCELVE		U		Selected LSEOBUNDLE
        	addSingle(curlseoBundleItem, "RPTEXCELVE");
            //SAPASSORTMODULE     T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "SAPASSORTMODULE");
            //SHIPGRPDESCLNGTXT   L       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "SHIPGRPDESCLNGTXT");
        	//SPECBID			U		Selected LSEOBUNDLE
        	addSingle(curlseoBundleItem, "SPECBID");
            //SPECMODDESGN        U       Selected LSEOBUNDLE
            addSingle(curlseoBundleItem, "SPECMODDESGN");
            //TAXCD               T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "TAXCD");
            //UPCCD               T       Selected LSEOBUNDLE
            addText(curlseoBundleItem, "UPCCD");       
            //LANGUAGES           U       LSEOBDLALWR.LANGUAGES
            addSingle(alwrItem, "LANGUAGES");
            //OFFCOUNTRY          U       LSEOBDLALWR.OFFCOUNTRY
            addSingle(alwrItem, "OFFCOUNTRY");            
            //SEOID               T       Left(LSEOBDLALWR.XXPARTNO,5) && LSEOBDLALWR.CD
            attrCodeVct.addElement("SEOID");
            attrValTbl.put("SEOID", bdlseoid);
            //XXPARTNO            T       LSEOBDLALWR.XXPARTNO
            addText(alwrItem, "XXPARTNO");
        }
        Vector getAttrCodes() { return attrCodeVct;}
        Hashtable getAttrValues() { return attrValTbl; }
        
        void dereference(){
            // release memory
            attrCodeVct.clear();
            attrValTbl.clear();
        }
    }

    private class LseoQty {
    	private String seoid;
    	private String qty;
    	private EntityItem lseoItem;
   
        LseoQty(String fc, String q, EntityItem ei){
            seoid = fc;
            qty = q;
            lseoItem = ei;
        }

        public boolean equals(Object obj) // used by Vector.contains()
        {
            LseoQty pn = (LseoQty)obj;
            return (seoid.equals(pn.seoid));
        }
        public String toString(){
            if (qty.equals("1")){
                return seoid;
            }
            return seoid+":"+qty;
        }
        void dereference(){
            seoid=null;
            qty=null;
            lseoItem = null;
        }
    }
}
