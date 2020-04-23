//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;


/**************************************
 * BH FS zSeries FC Transactions 20110713.doc 
 * chg XMLIDLABRSTATUS to ADSABRSTATUS
 * 
 * BH FS zSeries FC Transactions 20110525.doc
 * 
 * need FCTSTATUS attr and flag values - if that attr is Draft or Final do original process, and set new and prev
 * generated FCTRANSACTION.STATUS and DQ to that value - if final, queue ADSABRSTATUS
 * if it is 'set to draft' or 'set to final' only update prev generated and advance this attr to draft or final
 * prev generated are found thru the FTSETUPFCTRAN relator
 * 
  * BH FS zSeries FC Transactions 20100412.doc updates require
 * 		new VE - EXFTMDLCSET
 * 		new meta - 
 * 			(HWFCCAT) add flag “Capacity Settings” (305)
 * 			new attribute FCTRANAPP on FTSETUP and FEATURE
 * 
 * From BH FS zSeries FC Transactions 20090827.doc
 * 
 *V.	Feature Transaction Setup 

The Feature Transaction Setup (FTSETUP) entity is used to describe a set of Feature Transaction 
(FCTRANSACTION). The FT Setup ABR (FTSETUPABR) is run for each instance of FTSETUP that the user
wants the data created / updated.

The Feature Transaction Setup data is created via the User Interface (JUI or BUI) in a manner similar 
to creating and editing offering information. New actions will be provided by the OIM BAs via the 
standard metadata spreadsheet update process.

The user will create a Feature Transaction Setup (FTSETUP) for the Workgroup along with optional 
Availability (AVAIL).  The ABR is queued via a workflow action in the UI. The workflow action sets 
FT Setup ABR (FTSETUPABR) to Queued (0020). The FT Setup ABR then processes the request described by 
the Feature Transaction Setup data and generates and/or updates the children Feature Transactions.

The ABR will use the Feature Transaction Setup data to identify (derive) the valid set of feature 
conversions. There are several different yet very similar methods that are used to accomplish this. 
In every case, the From and To Machine Type, Model, and Feature must be valid.

F.	Summary Messages

On successful completion, set the FT Setup ABR attribute (FTSETUPABR) to Passed (0030). 
If the ABR finds one of the Errors in the prior section, set the FT Setup ABR attribute to Failed (0040).

List the following counts for Feature Transactions:
•	Created
•	Updated
•	Deleted
•	Skipped
o	Status = Final
o	Feature Transaction Class = Manual
o	FT Active = No

•	Error Messages

1.	Cannot find any From Machine Type Models
2.	Cannot find any To Machine Type Models
3.	No Features found for the specified Hardware Feature Category
4.	There are no valid Feature Transactions
5.	Invalid Hardware Feature Code Category / Feature Transaction Rule combination
6.	Unable to generate / update required Feature Transaction

 */
//FTSETUPABR.java,v
//Revision 1.6  2011/07/22 15:40:14  wendy
//Update user messages
//
//Revision 1.3  2010/04/30 12:46:30  wendy
//BH FS zSeries FC Transactions 20100412.doc updates
//
//Revision 1.2  2009/09/25 18:27:32  wendy
//Allow for no AVAILs on FTSETUP
//
//Revision 1.1  2009/09/01 15:55:55  wendy
//init for SR5
//
//
public class FTSETUPABR extends PokBaseABR
{
	private static final int MAXFILE_SIZE=5000000;
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    private Object[] args = new String[10];

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private Hashtable fcTransComboTbl;
    private int skippedFinalCnt = 0;
    private int skippedManualCnt = 0;
    private int skippedNotActiveCnt = 0;
    private int createdCnt = 0;
    private int updatedCnt = 0;
    private int notUpdatedCnt = 0;
    private int deletedCnt = 0;
    private int statusSetCnt = 0;
    private int xmlabrSetCnt = 0;
    private String xmlabrSetTo = "Queued";
    private int abr_debuglvl=0;
    private boolean metaError = false; // during test dont delete all fctrans if there is missing meta
    
    private String fctstatus = null; 
    
    //private static final String PS_SRCHACTION_NAME = "SRDPRODSTRUCTV"; 
    //so - you search for the TO MODEL - domain restricted - and if not found, then error
    //you search for the FROM MODEL - not restricted - and if not found, then error
    private static final String TO_MODEL_SRCHACTION_NAME = "SRDMODEL4"; // SRDMODEL06 - domain restricted 
    private static final String FROM_MODEL_SRCHACTION_NAME = "SRDMODEL6"; // SRDMODEL06 - NOT domain restricted 
    
    private static final String FCTRANSAVAIL_DELETEACTION_NAME  = "DELFCTRANSACTIONAVAIL";
    private static final String FCTRANSAVAIL_LINKACTION_NAME  = "LINKAVAILFCTRANSACTION2";
    private static final String FCTRANS_DELETEACTION_NAME  = "DELFCTRANSACTION"; 
    private static final String FCTRANS_CREATEACTION_NAME  = "CRFCTRANSACTION2";  
	
    //FTCLASS	GEN	Generated
    //FTCLASS	MAN	Manual
    private static final String FTCLASS_Manual = "MAN";

    //FTVACTIVE	N	No	No
    //FTVACTIVE	Y	Yes	Yes
    private static final String FTVACTIVE_No = "N";

	private static final int UPDATE_SIZE;// get this from properties
	static{
		String velimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(
				"FTSETUPABR","_updatesize","500");
		UPDATE_SIZE = Integer.parseInt(velimit);
	}
	
    //CAPUNIT	C0010	MB
    //CAPUNIT	C0040	GB
    //CAPUNIT	C0050	kB
    private static final String CAPUNIT_MB = "C0010";
    private static final String CAPUNIT_GB = "C0040";
    
    //FCTSTATUS	FCT1	Set to Draft
    //FCTSTATUS	FCT2	Draft
    //FCTSTATUS	FCT3	Set to Final
    //FCTSTATUS	FCT4	Final
    private static final String FCTSTATUS_Set2Draft = "FCT1";
    private static final String FCTSTATUS_Draft = "FCT2";
    //private static final String FCTSTATUS_Set2Final = "FCT3";
    private static final String FCTSTATUS_Final = "FCT4";
    
    //HWFCCAT	202		Capacity on Demand
    //HWFCCAT	282		I/O
    //HWFCCAT	213		Memory
    //HWFCCAT	219		Processor
    private static final String HWFCCAT_Memory = "213";
   // private static final String HWFCCAT_COD = "202";
    private static final String HWFCCAT_CSET = "305";
    
    //FCTRANAPP	FCN	No
    //FCTRANAPP	FCY	Yes
    private static final String FCTRANAPP_Yes = "FCY";
    
    //FTRULE	10	All
    //FTRULE	20	All But Self
    //FTRULE	30	Only Greater
    //FTRULE	40	Equal or Greater
    private static final String FTRULE_All = "10";
    private static final String FTRULE_AllButSelf = "20";
    private static final String FTRULE_OnlyGreater = "30";
    private static final String FTRULE_EqualGreater = "40";
    
	private static final String ABR_QUEUED = "0020";
	
    private static final Hashtable HWFCCAT_VE_VCT;
    static {
    	HWFCCAT_VE_VCT = new Hashtable(); 
    	HWFCCAT_VE_VCT.put(HWFCCAT_Memory, "EXFTMDLMEM");
    //	HWFCCAT_VE_VCT.put(HWFCCAT_COD, "EXFTMDLCOD");
    	HWFCCAT_VE_VCT.put(HWFCCAT_CSET, "EXFTMDLCSET");
    }
    
    private static final String[] FCTRANS_KEYS = new String[] {
    	"FROMMACHTYPE","FROMMODEL","FROMFEATURECODE","TOMACHTYPE","TOMODEL","TOFEATURECODE","COMMENT"};    
    
    private static final String STATUS_FINAL = "0020";
    private static final String STATUS_DRAFT = "0010";
	private static final String DQ_DRAFT = "DRAFT";
	private static final String DQ_FINAL = "FINAL";
	private static final String LIFECYCLE_Develop	= "LF02";// LIFECYCLE	=	"Develop" (LF02)
	private static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan
	private static final String LIFECYCLE_Launch = "LF03";// LIFECYCLE	=	LF01	Plan

    // attributes to copy from ftsetup to fctrans, also use this list for checking for updates needed
    private static final String[] FCTRANSLIST_ATTR = {"ANNDATE","BOXSWAP","FTCAT","GENAVAILDATE",
    	"INSTALL","PARTSSHIPPED","RETURNEDPARTS","UPGRADETYPE","WITHDRAWDATE",
    	"WTHDRWEFFCTVDATE","ZEROPRICE"}; 

    private static final String XMLATTRCODE="ADSABRSTATUS";
	private boolean outputMetaMsg = false;
	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private int dbgLen=0;
	
	private void setupPrintWriter(){
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0,extid+1)+"dbg";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			dbgfn = null;
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
	}
	private void closePrintWriter() {
		if (dbgPw != null){
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
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
        	long startTime = System.currentTimeMillis();
            start_ABRBuild(); // pull VE

            abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());
            
    		setupPrintWriter();
    		
            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
            // get root from VE
            EntityItem ftsetupEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +ftsetupEntity.getKey()+
                " extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            addDebug("Time to pull root VE: "+Stopwatch.format(System.currentTimeMillis()-startTime));

            //Default set to pass
            setReturnCode(PASS);
//fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false); 

            //NAME is navigate attributes
            navName = getNavigationName(ftsetupEntity);
            rootDesc = m_elist.getParentEntityGroup().getLongDescription()+" &quot;"+navName+"&quot;";
            
            // look at FCTSTATUS to determine action
            fctstatus = PokUtils.getAttributeFlagValue(ftsetupEntity, "FCTSTATUS");
            if(fctstatus==null){
            	fctstatus = FCTSTATUS_Draft; // default to original behavior
            }
            addDebug(ftsetupEntity.getKey()+" fctstatus "+fctstatus);
              
            if(fctstatus.equals(FCTSTATUS_Draft) || fctstatus.equals(FCTSTATUS_Final)){
            	// look at the FCTRANS and see if any need to be ignored
            	Vector skippedKeysVct = avoidInactiveFCTRANS();
        		addDebug("skippedKeysVct: "+skippedKeysVct);
        		
            	//determine sets of MT and FC 
            	validateSetup(ftsetupEntity,skippedKeysVct);
            	
            	skippedKeysVct.clear();

            	if(getReturnCode()== PokBaseABR.PASS){   
            		LinkActionItem lai = new LinkActionItem(null, m_db,m_prof,FCTRANSAVAIL_LINKACTION_NAME);
            		Vector ftsetupAvailVct = PokUtils.getAllLinkedEntities(ftsetupEntity, "FTSETUPAVAIL", "AVAIL");
            		// compare joined sets against current fctrans, update or delete
            		Vector curFctransIdVct = checkCurrentFctrans(ftsetupEntity,ftsetupAvailVct,lai);

            		System.gc();

            		// then create new ones and add AVAILs to new FCTRANS
            		createFctrans(ftsetupEntity,ftsetupAvailVct,lai);

            		ftsetupAvailVct.clear();
            		
            		// must handle this as post process because putattribute will fail for DQ and STATUS=Final
            		if(getReturnCode()== PokBaseABR.PASS && fctstatus.equals(FCTSTATUS_Final)){
            			moveToFinalAndQueue(curFctransIdVct,null);
            		}
        			curFctransIdVct.clear();
        			curFctransIdVct = null;
            	}else{ // something in FTSETUP was wrong or missing, any FCTRANS previously created will no longer match
            		if(!metaError){
            			// any current FCTRANSACTION that are not ignored need to be deleted, they can no longer match
            			EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
            			if (fctransGrp.getEntityItemCount()>0){
            				Vector deleteAllVct = new Vector();
            				for (int i=0; i<fctransGrp.getEntityItemCount(); i++){
            					deleteAllVct.add(fctransGrp.getEntityItem(i));
            				}
            				deleteFctrans(deleteAllVct);
            				deleteAllVct.clear();
            			}
            		}
            	}   
            } // end original mode
            else{ // handle 'Set to' mode
            	handleSetTo(ftsetupEntity);
            } // end 'Set to' mode
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
    		closePrintWriter();
        }

        // insert count msgs
        rptSb.insert(0, getCountOutput() + NEWLINE);
        
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

		restoreXtraContent();
		
        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>

        metaTbl.clear();
    }

	private void restoreXtraContent(){
		// if written to file and still small enough, restore debug to the abr rpt and delete the file
		if (dbgfn !=null && dbgLen+rptSb.length()<MAXFILE_SIZE){
			// read the file in and put into the stringbuffer
			InputStream is = null;
			FileInputStream fis = null;
			BufferedReader rdr = null;
			try{
				fis = new FileInputStream(dbgfn);
				is = new BufferedInputStream(fis);

				String s=null;
				StringBuffer sb = new StringBuffer();
				rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// append lines until done
				while((s=rdr.readLine()) !=null){
					sb.append(s+NEWLINE);
				}
				rptSb.append("<!-- "+sb.toString()+" -->"+NEWLINE);

				// remove the file
				File f1 = new File(dbgfn);
				if (f1.exists()) {
					f1.delete();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if (is!=null){
					try{
						is.close();
					}catch(Exception x){
						x.printStackTrace();
					}
				}
				if (fis!=null){
					try{
						fis.close();
					}catch(Exception x){
						x.printStackTrace();
					}
				}
			}
		}
	}
	
    /*************
     * build count output information
     * @return
     */
    private String getCountOutput(){
    	StringBuffer cntSb = new StringBuffer();
    	if(fctstatus.equals(FCTSTATUS_Draft) || fctstatus.equals(FCTSTATUS_Final)){
    		cntSb.append("<table><tr><th>Skipped: </th><td colspan='2'>&nbsp;</td></tr>");
    		cntSb.append("<tr><td colspan='2'>&nbsp;&nbsp;Status = Final:</td><td> "+skippedFinalCnt+"</td></tr>"+
    				"<tr><td colspan='2'>&nbsp;&nbsp;Feature Transaction Class = Manual:</td><td> "+skippedManualCnt+"</td></tr>"+
    				"<tr><td colspan='2'>&nbsp;&nbsp;FT Active = No:</td><td> "+skippedNotActiveCnt+"</td></tr>");
    		cntSb.append("<tr><th>Created: </th><td>"+createdCnt+"</td><td>&nbsp;</td></tr>");
    		cntSb.append("<tr><th>Updated data attributes: </th><td>"+updatedCnt+"</td><td>&nbsp;</td></tr>");
    		cntSb.append("<tr><th>No Update to data attributes needed: </th><td>"+notUpdatedCnt+"</td><td>&nbsp;</td></tr>");
    		if(fctstatus.equals(FCTSTATUS_Final)){
    			String xmlabrDesc = "ADS XML FEED ABR";
    			// get the meta attribute
    		    EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
    		    if(fctransGrp!=null){
    		    	xmlabrDesc = PokUtils.getAttributeDescription(fctransGrp, XMLATTRCODE, XMLATTRCODE);
    		    }
         		cntSb.append("<tr><th>Set "+xmlabrDesc+" to "+xmlabrSetTo+": </th><td>"+xmlabrSetCnt+"</td></tr>");
    			cntSb.append("<tr><th>Set Status to Final: </th><td>"+statusSetCnt+"</td></tr>");
    		}
    		cntSb.append("<tr><th>Deleted: </th><td>"+deletedCnt+"</td><td>&nbsp;</td></tr></table>");
    	}else{
    		cntSb.append("<table>");
    		if(fctstatus.equals(FCTSTATUS_Set2Draft)){
    	   		cntSb.append("<tr><th>Set Status to Draft: </th><td>"+statusSetCnt+"</td></tr></table>");
    		}else{
    			String xmlabrDesc = "ADS XML FEED ABR";
    			// get the meta attribute
    		    EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
    		    if(fctransGrp!=null){
    		    	xmlabrDesc = PokUtils.getAttributeDescription(fctransGrp, XMLATTRCODE, XMLATTRCODE);
    		    }
         		cntSb.append("<tr><th>Set "+xmlabrDesc+" to "+xmlabrSetTo+": </th><td>"+xmlabrSetCnt+"</td></tr>");
        		cntSb.append("<tr><th>Set Status to Final: </th><td>"+statusSetCnt+"</td></tr></table>");
    		}
        }
    	
        return cntSb.toString();
    }
    /**********************************
     * look at the FCTRANS and see if any need to be ignored
     * C.	Updating Feature Transactions

The ABR will skip or ignore Feature Transactions under any one of the following conditions:
1.	Status (STATUS) = Final (0020) unless the value of value of FCSTATUS is “Set to Final” in which case 
only STATUS, DATAQUALITY, and ADSABRSTATUS are updated.
2.	Feature Transaction Class (FTCLASS) = Manual (MAN)
3.	FT Active (FTACTIVE) = No (NO)

     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private Vector avoidInactiveFCTRANS() throws SQLException, MiddlewareException 
    { 
    	Vector skippedKeysVct = new Vector();
    	EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
    	if (fctransGrp.getEntityItemCount()>0){
    		EntityItem eiArray[] = fctransGrp.getEntityItemsAsArray();
    		for (int i=0; i<eiArray.length; i++){
    			EntityItem fctrans = eiArray[i];
    			String ftclass = PokUtils.getAttributeFlagValue(fctrans, "FTCLASS");
    			String ftvactive = PokUtils.getAttributeFlagValue(fctrans, "FTVACTIVE");
    			String status = PokUtils.getAttributeFlagValue(fctrans, "STATUS");
    			addDebug("avoidInactiveFCTRANS Checking "+fctrans.getKey()+" FTCLASS: "+
    					ftclass+" FTVACTIVE:"+ftvactive+" STATUS:"+status);

    			if (FTVACTIVE_No.equals(ftvactive)){
    				addDebug("avoidInactiveFCTRANS Removing "+fctrans.getKey()+" from group, it is marked inactive");
        			removeItems(fctrans, true);
        			skippedKeysVct.add(buildKey(fctrans));
    				skippedNotActiveCnt++;
    				continue;
    			}
    			if (FTCLASS_Manual.equals(ftclass)){
    				addDebug("avoidInactiveFCTRANS Removing "+fctrans.getKey()+" from group, it is marked manual");
        			removeItems(fctrans, true);
        			skippedKeysVct.add(buildKey(fctrans));
    				skippedManualCnt++;
       				continue;
    			}
    			if (STATUS_FINAL.equals(status)){
    				addDebug("avoidInactiveFCTRANS Removing "+fctrans.getKey()+" from group, status is final");
        			removeItems(fctrans, true);
        			skippedKeysVct.add(buildKey(fctrans));
    				skippedFinalCnt++;
    			}
    		}
    	}
    	return skippedKeysVct;
    }

    /**
     * A.	Setup Data
     * 
     * The action (function) of this ABR based on the value of FCTSTATUS:
     * 3.	Draft
     * Proceed with the next section
     * This ABR will look at existing FCTRANSACTIONs.  It will skip any that have STATUS=Final, FTCLASS=Manual or 
     * FTACTIVE = Inactive.
     * Newly created FCTRANSACTION will have STATUS=Draft and DATAQUALITY=Draft. “Life Cycle” (LIFECYCLE) will 
     * have the default value based on meta data.
     * 4.	Final
     * Proceed with the next section
     * This ABR will look at existing FCTRANSACTIONs.  It will skip any that have STATUS=Final, FTCLASS=Manual or 
     * FTACTIVE=Inactive.
     * Newly created FCTRANSACTION will have STATUS=Final and DATAQUALITY=Final and ADSABRSTATUS = &ADSFEED.
     * •	Set FCTRANSACTION.LIFECYCLE  = "Launch" (LF03)
     * Existing FCTRANSACTION that were not filtered out will have STATUS=Final and DATAQUALITY=Final and 
     * ADSABRSTATUS = &ADSFEED.
     * •	If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
     * 
     * @param curFctransIdVct
     * @param createdFctransIdVct
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws EANBusinessRuleException 
     * @throws MiddlewareShutdownInProgressException 
     * @throws RemoteException 
     */
    private void moveToFinalAndQueue(Vector curFctransIdVct,Vector createdFctransIdVct) 
    throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, 
    EANBusinessRuleException
    {
    	addDebug(D.EBUG_ERR,"moveToFinalAndQueue entered curFctransIdVct: "+
    			(curFctransIdVct==null?"null":""+curFctransIdVct.size())+
    			" createdFctransIdVct: "+(createdFctransIdVct==null?"null":""+createdFctransIdVct.size()));
    	
    	String xmlqueuedValue =getQueuedValueForItem(XMLATTRCODE);
    	if(!xmlqueuedValue.equals(ABR_QUEUED)){
    		xmlabrSetTo = xmlqueuedValue; // put in msg
    	}
 
     	EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
     	
    	Vector vctReturnsEntityKeys = new Vector();
    	
		if (m_cbOn==null){	// needed for attribute updates
			DatePackage dbNow = m_db.getDates();
			m_strNow = dbNow.getNow(); // use current time, after creation timestamp
			m_cbOn = new ControlBlock(m_strNow,m_strForever,m_strNow,m_strForever, m_prof.getOPWGID(), m_prof.getTranID());
		}

       	m_prof.setValOnEffOn(m_strNow, m_strNow); // use dts from controlblock
       	
    	// these previously existed but are not final
        // Existing FCTRANSACTION that were not filtered out will have STATUS=Final and DATAQUALITY=Final and 
        // ADSABRSTATUS = &ADSFEED.
        // •	If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
       	if(curFctransIdVct!=null){
       		for(int i=0; i<curFctransIdVct.size(); i++){
       			int eid = ((Integer)curFctransIdVct.elementAt(i)).intValue();
       			EntityItem fctrans = fctransGrp.getEntityItem("FCTRANSACTION"+eid);

       			String status = PokUtils.getAttributeFlagValue(fctrans, "STATUS");
       			addDebug(D.EBUG_INFO,"moveToFinalAndQueue Checking "+fctrans.getKey()+" STATUS:"+status);

       			if (!STATUS_FINAL.equals(status)){
       				setFlagValue("STATUS", STATUS_FINAL, eid, vctReturnsEntityKeys);
       				setFlagValue("DATAQUALITY", DQ_FINAL, eid, vctReturnsEntityKeys);	
       				statusSetCnt++;
       			}

       			if(setFlagValue(XMLATTRCODE, xmlqueuedValue, eid, vctReturnsEntityKeys)){
       				xmlabrSetCnt++;
       			}

       			String lifecycle = PokUtils.getAttributeFlagValue(fctrans, "LIFECYCLE");
       			addDebug(D.EBUG_INFO,"moveToFinalAndQueue "+fctrans.getKey()+" LIFECYCLE: "+lifecycle);
       			if (lifecycle==null || lifecycle.length()==0){ 
       				lifecycle = LIFECYCLE_Plan;
       			}

       			//If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
       			if(LIFECYCLE_Plan.equals(lifecycle) || LIFECYCLE_Develop.equals(lifecycle)){
       				setFlagValue("LIFECYCLE", LIFECYCLE_Launch,	eid, vctReturnsEntityKeys);
       			}

       			if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
       				updatePDH(vctReturnsEntityKeys);
       			}
       		}
       	}
    	// these are new
        // Newly created FCTRANSACTION will have STATUS=Final and DATAQUALITY=Final and ADSABRSTATUS = &ADSFEED.
        // •	Set FCTRANSACTION.LIFECYCLE  = "Launch" (LF03)
       	if(createdFctransIdVct!=null){
       		for(int i=0; i<createdFctransIdVct.size(); i++){
       			int eid = ((Integer)createdFctransIdVct.elementAt(i)).intValue();
       			setFlagValue("STATUS", STATUS_FINAL, eid, vctReturnsEntityKeys);
       			statusSetCnt++;

       			setFlagValue("DATAQUALITY", DQ_FINAL, eid, vctReturnsEntityKeys);
       			if(setFlagValue(XMLATTRCODE, xmlqueuedValue, eid, vctReturnsEntityKeys)){
       				xmlabrSetCnt++;
       			}
       			setFlagValue("LIFECYCLE", LIFECYCLE_Launch, eid, vctReturnsEntityKeys);
       			if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
       				updatePDH(vctReturnsEntityKeys);
       			}
       		}
       	}
       	
    	if(vctReturnsEntityKeys.size()>0){
    		updatePDH(vctReturnsEntityKeys);
    	}
    }

	/**
	 * move all DQ and STATUS to Final,queue xml and advance FCTSTATUS to Final
	 * If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
	 * 
	 * @param ftsetupEntity
	 * @param finalVct
	 * @param otherVct
	 * @param lifecycleVct
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	private void setToFinal(EntityItem ftsetupEntity,Vector finalVct, Vector otherVct,
			Vector lifecycleVct) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException 
	{		
    	String xmlqueuedValue =getQueuedValueForItem(XMLATTRCODE);
    	if(!xmlqueuedValue.equals(ABR_QUEUED)){
    		xmlabrSetTo = xmlqueuedValue; // put in msg
    	}
    	
    	Vector vctReturnsEntityKeys = new Vector();
    	
    	// these are already final so just queue
    	for(int i=0; i<finalVct.size(); i++){
    		Integer eint = ((Integer)finalVct.elementAt(i));
    		int eid = eint.intValue();
    		if(setFlagValue(XMLATTRCODE, xmlqueuedValue, eid, vctReturnsEntityKeys)){
    			xmlabrSetCnt++;
    		}
    		//If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
    		if(lifecycleVct.contains(eint)){
    			setFlagValue("LIFECYCLE", LIFECYCLE_Launch,	eid, vctReturnsEntityKeys);
    		}
    		if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
    	       	updatePDH(vctReturnsEntityKeys);
    		}
    	}
    	// these previously existed but are not final
       	for(int i=0; i<otherVct.size(); i++){
    		Integer eint = ((Integer)otherVct.elementAt(i));
    		int eid = eint.intValue();
    		setFlagValue("STATUS", STATUS_FINAL, eid, vctReturnsEntityKeys);
    		setFlagValue("DATAQUALITY", DQ_FINAL, eid, vctReturnsEntityKeys);
    		if(setFlagValue(XMLATTRCODE, xmlqueuedValue, eid, vctReturnsEntityKeys)){
    			xmlabrSetCnt++;
    		}
    		//If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
       		if(lifecycleVct.contains(eint)){
    			setFlagValue("LIFECYCLE", LIFECYCLE_Launch,	eid, vctReturnsEntityKeys);
    		}
    		if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
    	       	updatePDH(vctReturnsEntityKeys);
    		}
    	}
       	
       	// advance FCTSTATUS
       	advanceFCTSTATUS(ftsetupEntity,FCTSTATUS_Final,vctReturnsEntityKeys);
	}
	
	/**
	 * update FCTSTATUS
	 * @param ftsetupEntity
	 * @param fctvalue
	 * @param vctReturnsEntityKeys
	 * @throws MiddlewareException 
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws SQLException 
	 * @throws RemoteException 
	 */
	private void advanceFCTSTATUS(EntityItem ftsetupEntity,String fctvalue, Vector vctReturnsEntityKeys)
	throws MiddlewareException, RemoteException, SQLException, MiddlewareShutdownInProgressException, EANBusinessRuleException
	{
		ReturnEntityKey rek = new ReturnEntityKey(this.getEntityType(),this.getEntityID(), true);
		rek.m_vctAttributes = new Vector();
		vctReturnsEntityKeys.addElement(rek);
		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), getEntityType(), getEntityID(),
				"FCTSTATUS", fctvalue, 1, m_cbOn);
		rek.m_vctAttributes.addElement(sf);

		updatePDH(vctReturnsEntityKeys);

		//ATTR_SET = {0} was set to {1} for {2} {3}
		MessageFormat msgf = new MessageFormat(rsBundle.getString("ATTR_SET"));
		args[0] = PokUtils.getAttributeDescription(ftsetupEntity.getEntityGroup(), "FCTSTATUS", "FCTSTATUS");

		args[1] = fctvalue;
		// get flag description
		EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) ftsetupEntity.getEntityGroup().getMetaAttribute("FCTSTATUS");
		if (mfa!=null){
			MetaFlag mf = mfa.getMetaFlag(fctvalue);
			if (mf!=null){
				args[1] = mf.toString();
			}
		}
		args[2] = ftsetupEntity.getEntityGroup().getLongDescription();
		args[3] = getNavigationName(ftsetupEntity);
		addOutput(msgf.format(args));	
	}
	/**
	 * move all DQ and STATUS to Draft and advance FCTSTATUS to Draft
	 * @param ftsetupEntity
	 * @param otherVct
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	private void setToDraft(EntityItem ftsetupEntity,Vector otherVct) throws SQLException, MiddlewareException, RemoteException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException 
	{
    	Vector vctReturnsEntityKeys = new Vector();

    	// these previously existed but are not draft
       	for(int i=0; i<otherVct.size(); i++){
    		int eid = ((Integer)otherVct.elementAt(i)).intValue();
    		setFlagValue("STATUS", STATUS_DRAFT, eid, vctReturnsEntityKeys);
    		setFlagValue("DATAQUALITY", DQ_DRAFT, eid, vctReturnsEntityKeys);
    		if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
    	       	updatePDH(vctReturnsEntityKeys);
    		}
    	}
       	
       	// advance FCTSTATUS
       	advanceFCTSTATUS(ftsetupEntity,FCTSTATUS_Draft,vctReturnsEntityKeys);
	}
	
	/***********************************************
	 * Update the PDH with the values in the vector, do all in a set at once
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

		if(vctReturnsEntityKeys.size()>0){
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
	}
	/**
	 * @param attrcode
	 * @return
	 */
	private String getQueuedValueForItem(String attrcode){
		// get queued value from properties for that
		String abrAttrCode = "FCTRANABRSTATUS";
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.QUEUEDVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,ABR_QUEUED);
	}

	/***********************************************
	 *  Sets the specified Flag Attribute on the specified Entity
	 *
	 * @param strAttributeCode
	 * @param strAttributeValue
	 * @param entityid
	 * @param vctReturnsEntityKeys
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private boolean setFlagValue(String strAttributeCode, String strAttributeValue,
			int entityid, Vector vctReturnsEntityKeys) throws SQLException, MiddlewareException
	{
		addDebug(D.EBUG_INFO,"setFlagValue entered FCTRANSACTION"+entityid+" for "+strAttributeCode+" set to: " +
				strAttributeValue);

		if(strAttributeValue==null || strAttributeValue.trim().length()==0){
			addDebug(D.EBUG_ERR,"setFlagValue "+strAttributeCode+
					" value was empty for FCTRANSACTION"+entityid+". nothing to do");
			return false;
		}
		
		// get the meta attribute
    	EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
		EANMetaAttribute ma = fctransGrp.getMetaAttribute(strAttributeCode);
		if (ma==null) {
			if(!outputMetaMsg){
				//META_ERR = MetaAttribute {0} cannot be found for {1}
				args[0] = strAttributeCode;
				args[1] = fctransGrp.getEntityType();
				addWarning("META_ERR",args);
				outputMetaMsg = true;
			}
			return false;
		}
		
		Vector vctAtts = null;
		// look at each key to see if root is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == entityid &&
					rek.getEntityType().equals("FCTRANSACTION")){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey("FCTRANSACTION",entityid, true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}
		
		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), "FCTRANSACTION", entityid,
				strAttributeCode, strAttributeValue, 1, m_cbOn);
		
		vctAtts.addElement(sf);
		
		return true;
	}
	
	/**
	 * The attribute “Status” (FCTSTATUS) has the following values and the action to be taken by this ABR:
	 * 
	 * Attribute	Code	Description
	 * FCTSTATUS	FCT1	Set to Draft
	 * FCTSTATUS	FCT2	Draft
	 * FCTSTATUS	FCT3	Set to Final
	 * FCTSTATUS	FCT4	Final
	 * 
	 * The action (function) of this ABR based on the value of FCTSTATUS:
	 * 1.	Set to Draft
	 * Update DATAQUALITY and STATUS on all FCTRANSACTIONs previously generated by this ABR based on the 
	 * Long Description of FCSTATUS.
	 * This will update all FCTRANSACTION linked to the FTSETUP entity. It will set FCTRANSACTION.DATAQUALITY and 
	 * FCTRANSACTION.STATUS to Draft. FCTRANSACTION.LIFECYCLE will not be modified.
	 * 
	 * 2.	Set to Final 
	 * Update DATAQUALITY and STATUS on all FCTRANSACTIONs previously generated by this ABR based on the 
	 * Long Description of FCSTATUS.
	 * Update “ADS XML FEED ABR” (ADSABRSTATUS) to &ADSFEED as defined in the properties file for this ABR.
	 * If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
	 * This will update all FCTRANSACTION linked to the FTSETUP entity. It will set FCTRANSACTION.DATAQUALITY and 
	 * FCTRANSACTION.STATUS to Final. 
	 * It will set FCTRANSACTION.ADSABRSTATUS to &ADSFEED.  FCTRANSACTION.LIFECYCLE may be set to “Launch”.
	 * 
	 * D.	Updating Feature Transaction Setup
	 * The “Status” (FCTSTATUS) is updated if the ABR is successful based on the current value:
	 * 1.	Set to Draft – update to Draft
	 * There is no need to match feature Transactions nor are any Feature Transactions skipped.
	 * 2.	Draft – do not update
	 * 3.	Set to Final – update to Final
	 * There is no need to match feature Transactions nor are any Feature Transactions skipped.
	 * 4.	Final – do not update
	 * @param ftsetupEntity
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	private void handleSetTo(EntityItem ftsetupEntity) throws RemoteException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException{
		if (m_cbOn==null){	// needed for attribute updates
			setControlBlock();
		}

		EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
    	if(fctstatus.equals(FCTSTATUS_Set2Draft)){
    		Vector otherVct = new Vector();
    		for (int i=0; i<fctransGrp.getEntityItemCount(); i++){
    			EntityItem fctrans = fctransGrp.getEntityItem(i);
    			String status = PokUtils.getAttributeFlagValue(fctrans, "STATUS");
    			addDebug(D.EBUG_INFO,"handleSetTo Draft Checking "+fctrans.getKey()+" STATUS:"+status);

    			if (!STATUS_DRAFT.equals(status)){
    				otherVct.add(new Integer(fctrans.getEntityID()));
    				statusSetCnt++;
    			}
    		}
    		// move to all Draft and advance FCTSTATUS to Draft
    		setToDraft(ftsetupEntity,otherVct);
    		
    		otherVct.clear();
    	}else{ // must be set to final
    		Vector finalVct = new Vector();
    		Vector otherVct = new Vector();
    		Vector lifecycleVct = new Vector();
    		for (int i=0; i<fctransGrp.getEntityItemCount(); i++){
    			EntityItem fctrans = fctransGrp.getEntityItem(i);
    			String status = PokUtils.getAttributeFlagValue(fctrans, "STATUS");
    			String lifecycle = PokUtils.getAttributeFlagValue(fctrans, "LIFECYCLE");
    			addDebug(D.EBUG_INFO,"handleSetTo Final Checking "+fctrans.getKey()+" STATUS:"+
    					status+" LIFECYCLE: "+lifecycle);
    			if (lifecycle==null || lifecycle.length()==0){ 
    				lifecycle = LIFECYCLE_Plan;
    			}
    			Integer eidint = new Integer(fctrans.getEntityID());
    			//If “Life Cycle” (LIFECYCLE) = "Plan" (LF01) or "Develop" (LF02), then set LIFECYCLE = "Launch" (LF03)
    			if(LIFECYCLE_Plan.equals(lifecycle) || LIFECYCLE_Develop.equals(lifecycle)){
    				lifecycleVct.add(eidint);
    			}
    			if (STATUS_FINAL.equals(status)){
    				finalVct.add(eidint);
    			}else{
    				otherVct.add(eidint);
    				statusSetCnt++;
    			}
    		}
    		
    		//move all DQ and STATUS to Final,queue xml and advance FCTSTATUS to Final
    		setToFinal(ftsetupEntity,finalVct,otherVct,lifecycleVct);
    		
    		finalVct.clear();
    		otherVct.clear();
    		lifecycleVct.clear();
    	}
	}
	
    /******************************
     * Check all current FCTRANSACTIONs against the derived fctrans
     * Update any that match
     * Delete any that dont
     * 
     * @param ftsetupEntity
     * @param ftsetupAvailVct
     * @param lai
     * @return Vector of current ids
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws LockException
     * @throws MiddlewareShutdownInProgressException
     * @throws EANBusinessRuleException
     * @throws RemoteException
     * @throws WorkflowException
     */
    private Vector checkCurrentFctrans(EntityItem ftsetupEntity,Vector ftsetupAvailVct,LinkActionItem lai) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
    MiddlewareShutdownInProgressException, EANBusinessRuleException, RemoteException, WorkflowException
    {
    	Vector updateVct = new Vector(1);
    	Vector deleteVct = new Vector(1);
		Vector curIdVct = new Vector(0);
		
    	EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
    	if (fctransGrp.getEntityItemCount()>0){
    		EntityItem eiArray[] = fctransGrp.getEntityItemsAsArray();
    		for (int i=0; i<eiArray.length; i++){
    			EntityItem fctrans = eiArray[i];
    			String key = buildKey(fctrans);
    			if (fcTransComboTbl.containsKey(key)){
    				addDebug("checkCurrentFctrans "+key+" matches "+fctrans.getKey());
    				updateVct.add(fctrans);
    				curIdVct.add(new Integer(fctrans.getEntityID()));
    				fcTransComboTbl.remove(key); // dont need to create this one
    			}else{
    				addDebug("checkCurrentFctrans NO match for "+key+" deleting "+fctrans.getKey());
    				deleteVct.add(fctrans);
    				removeItems(fctrans, false); // dont remove the uplink, needed for delete
    			}
    		}
    		
    		deleteFctrans(deleteVct);
    		updateFctrans(updateVct,ftsetupEntity);
    	}
    	
        deleteVct.clear();
        updateVct.clear();
        
    	addDebug("checkCurrentFctrans:  List after checks: "+PokUtils.outputList(m_elist));
    	
        // look at AVAILs on current FCTRANS
        updateFCTRANSAvails(ftsetupEntity,ftsetupAvailVct,lai);
        
        return curIdVct;
    } 

	/***************************
	 * Delete these FCTRANS, they no longer match 
	 * @param deleteVct
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws LockException 
	 */
    private void deleteFctrans(Vector deleteVct) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
    MiddlewareShutdownInProgressException, EANBusinessRuleException
    {
    	addDebug("deleteFctrans cnt "+deleteVct.size());
    	if(deleteVct.size()==0){
    		return;
    	}
    	
    	DeleteActionItem dai = new DeleteActionItem(null, m_db,m_prof,FCTRANS_DELETEACTION_NAME);
  
    	EntityItem childArray[] = new EntityItem[deleteVct.size()];
    	for (int i=0; i<deleteVct.size(); i++){
    		EntityItem fctrans = (EntityItem)deleteVct.elementAt(i);
    		// get the relator
    		childArray[i] = (EntityItem)fctrans.getUpLink(0);
    		addDebug("deleteFctrans "+fctrans.getKey()+" "+childArray[i].getKey());
    	}
    	long startTime = System.currentTimeMillis();
    	
    	// do the delete
    	dai.setEntityItems(childArray);
    	m_db.executeAction(m_prof, dai);
    	deletedCnt=childArray.length;
    	
	    addDebug("Time to delete unmatched fctrans: "+Stopwatch.format(System.currentTimeMillis()-startTime));
    }
    
	/***************************
	 * Look at current FCTRANSACTION and see if updates are needed
	 * @param updateVct
	 * @param ftsetupEntity
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void updateFctrans(Vector updateVct,EntityItem ftsetupEntity) 
	throws SQLException, MiddlewareException, EANBusinessRuleException, 
	RemoteException, MiddlewareShutdownInProgressException	
	{
		addDebug("updateFctrans cnt "+updateVct.size());
    	if(updateVct.size()==0){
    		return;
    	}
    	AttrSet attrSet = new AttrSet(ftsetupEntity, false);
    	
    	int commitNeededCnt = 0;
    	for (int i=0; i<updateVct.size(); i++){
    		EntityItem fctrans = (EntityItem)updateVct.elementAt(i);
    		// look to see if any changes are needed
    		boolean chgsMade = updateFctrans(fctrans,attrSet.getAttrCodes(),attrSet.getAttrValues());
    		if(chgsMade){
    			addDebug("updateFctrans "+fctrans.getKey()+" has changes");
    			commitNeededCnt++;
    		}else{
    			addDebug("updateFctrans "+fctrans.getKey()+" does not need changes, but must check avails");
    			notUpdatedCnt++;
    		}	
    	}
	
    	if (commitNeededCnt>0){
    		long startTime = System.currentTimeMillis();
    		// extract has bypass specified to avoid commit checks.. terrible perf
    		m_elist.getEntityGroup("FTSETUPFCTRAN").commit(m_db, null); // this commits all changed items
    		updatedCnt = commitNeededCnt;
    		addDebug("Time to commit changed fctrans: "+Stopwatch.format(System.currentTimeMillis()-startTime));
    	}

       	attrSet.dereference();
	}
	/***************************
	 * Remove this item and its relator from the groups, group will be used for commit
	 * @param fctrans
	 * @param removeUplink
	 */
	private void removeItems(EntityItem fctrans, boolean removeUplink){
		fctrans.getEntityGroup().removeEntityItem(fctrans); // wont need to commit this one
		EntityItem relator = (EntityItem)fctrans.getUpLink(0);
		relator.getEntityGroup().removeEntityItem(relator);
		if (removeUplink){
			fctrans.removeUpLink(relator);
		}
	}

	/*************************
	 * Update attributes on a current or new FCTRANSACTION entity
     * @param fctrans
     * @param attrCodeVct
     * @param attrValuesTbl
     * @return
     * @throws MiddlewareRequestException
     * @throws EANBusinessRuleException
	 * 
The following attributes are optional. Changes to any of these attributes results in the Feature 
Transaction data being updated to match these values.
10.	ANNDATE must be a valid date (format YYYY MM DD )
11.	BOXSWAP has a list of allowed values, select one
12.	GENAVAILDATE must be a valid date
13.	INSTALL has a list of allowed values, select one
14.	PARTSSHIPPED has a list of allowed values, select one
15.	RETURNEDPARTS has a list of allowed values, select one
16.	UPGRADETYPE has a list of allowed values, select one
17.	WITHDRAWDATE must be a valid date  
18.	WTHDRWEFFCTVDATE must be a valid  
19.	ZEROPRICE has the following allowed values:
•	Yes
•	No

	 */
    private boolean updateFctrans(EntityItem fctrans,Vector attrCodeVct, Hashtable attrValuesTbl) 
    throws MiddlewareRequestException, EANBusinessRuleException  
    {
		boolean commitNeeded = false;
		boolean isNew = fctrans.getEntityID()<0;

    	// make sure all attributes match
		for (int i=0; i<attrCodeVct.size(); i++){
			String attrCode = (String)attrCodeVct.elementAt(i);
			StringBuffer debugSb= new StringBuffer();
			// get the meta attribute
			EANMetaAttribute ma = fctrans.getEntityGroup().getMetaAttribute(attrCode);
			if (ma==null) {
				addDebug("MetaAttribute cannot be found "+fctrans.getEntityGroup().getEntityType()+"."+attrCode+"\n");
				continue;
			}
			Object value = attrValuesTbl.get(attrCode);
			switch (ma.getAttributeType().charAt(0))
			{
			case 'T':
			case 'L':
			case 'X':
			{
				// check the Text attributes
				String curVal = "";
				if (!isNew) {
					curVal = PokUtils.getAttributeValue(fctrans, attrCode, "", "", false);
				}
				if (!value.equals(curVal)){
					if (!isNew) {
						addDebug(D.EBUG_INFO,"updateFctrans "+fctrans.getKey()+" Updating "+attrCode+" was: "+curVal+" newval "+value);
					}
					// save the Text attributes
					ABRUtil.setText(fctrans,attrCode, (String)value, debugSb);
					commitNeeded = true;
				}
				break;
			}
			case 'U':
			{
				String curVal = "";
				if (!isNew) {
					curVal = PokUtils.getAttributeFlagValue(fctrans, attrCode);
				}
				if (!value.equals(curVal)){
					if (curVal==null && value.equals("")){
						continue;
					}
					if (!isNew) {
						addDebug(D.EBUG_INFO,"updateFctrans "+fctrans.getKey()+" Updating "+attrCode+" was: "+curVal+" newval "+value);
					}
					ABRUtil.setUniqueFlag(fctrans,attrCode, (String)value,debugSb);
					commitNeeded = true;
				}
				break;
			}
			case 'F':
			{
				String curVal = "";
				if (!isNew) {
					curVal = PokUtils.getAttributeFlagValue(fctrans, attrCode);
				}
				boolean updateNeeded = false;
				if (curVal==null){
					if ((value instanceof String) && value.equals("")){
						continue;
					}
					if (!isNew) {
						addDebug(D.EBUG_INFO,"updateFctrans "+fctrans.getKey()+" Updating "+attrCode+" was: "+curVal+" newval "+value);
					}
					updateNeeded = true;
				}else if (value instanceof String){
					if (!value.equals(curVal)){
						if (!isNew) {
							addDebug(D.EBUG_INFO,"updateFctrans "+fctrans.getKey()+" "+attrCode+" needs to be updated, "+curVal+" newval "+value);
						}
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
						if (!isNew) {
							addDebug(D.EBUG_INFO,"updateFctrans "+fctrans.getKey()+" "+attrCode+" needs to be updated");
						}
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

					ABRUtil.setMultiFlag(fctrans,attrCode,tmp,debugSb); // make sure flagcodes are passed in
					commitNeeded = true;
				}
				break;
			}
			default:
			{
				addDebug("MetaAttribute Type="+ma.getAttributeType()+
						" is not supported yet "+fctrans.getEntityGroup().getEntityType()+"."+attrCode+"\n");
				// could not get anything
				break;
			}
			}
			if (debugSb.length()>0){
				addDebug(D.EBUG_INFO,debugSb.toString());
			}
		}

		return commitNeeded;
    }

    /******************************************
     * Create FCTRANSACTIONs for the FROM-TO sets that were created based on FTRULE
     * 
	 * @param ftsetupEntity
	 * @param ftsetupAvailVct
	 * @param lai
	 * @return Vector of created entityids
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private void createFctrans(EntityItem ftsetupEntity,Vector ftsetupAvailVct,LinkActionItem lai) 
	throws RemoteException, SQLException, MiddlewareException, 
	EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException
	{
		addDebug("createFctrans cnt "+fcTransComboTbl.size());

		if (fcTransComboTbl.size()>0){
			AttrSet attrSet = new AttrSet(ftsetupEntity, true);
			// get all newly created items at once - do it in subsets
			CreateActionItem cai = new CreateActionItem(null,m_db, m_prof, FCTRANS_CREATEACTION_NAME);

			Vector fcTransVct = new Vector();
			// loop thru fcTransComboTbl using subsets
			for (Enumeration eNum = fcTransComboTbl.elements(); eNum.hasMoreElements();)  {
				FctransSet fcs = (FctransSet)eNum.nextElement();
				fcTransVct.add(fcs);

				if(fcTransVct.size()==UPDATE_SIZE || !eNum.hasMoreElements()) {
					Vector fctransIdVct = createAndCommitFctrans(ftsetupEntity,attrSet,fcTransVct, ftsetupAvailVct,lai,cai);
					createdCnt +=fctransIdVct.size();

					fcTransVct.clear();
				}
			}
			
			attrSet.dereference();
		}
	}

	/**
	 * create and commit subsets of FCTRANSACTION entities and relators
	 * @param ftsetupEntity
	 * @param attrSet
	 * @param fcTransVct
	 * @param ftsetupAvailVct
	 * @param lai
	 * @param cai
	 * @return
	 * @throws EANBusinessRuleException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private Vector createAndCommitFctrans(EntityItem ftsetupEntity,AttrSet attrSet,
			Vector fcTransVct, Vector ftsetupAvailVct,LinkActionItem lai,CreateActionItem cai) 
	throws EANBusinessRuleException, SQLException, MiddlewareException, RemoteException, 
	MiddlewareShutdownInProgressException, LockException, WorkflowException
	{
		addDebug("createAndCommitFctrans cnt "+fcTransVct.size());
		Vector fctransIdVct = new Vector(0);
		cai.setNumberOfItems(fcTransVct.size());
		long enterTime = System.currentTimeMillis();
		EntityList createList = createFctrans(ftsetupEntity,cai); 
		addDebug("Time to get entitylist "+Stopwatch.format(System.currentTimeMillis()-enterTime));

		EntityGroup eGrp = createList.getEntityGroup("FCTRANSACTION");
		if (eGrp!= null && eGrp.getEntityItemCount()>0)	{
			addDebug("createFctrans set cnt "+fcTransVct.size()+" new item cnt:"+eGrp.getEntityItemCount());
			// if they dont match something is wrong!!!
			// write the attributes
			int tmpCreateCnt = 0;
			//write the attributes for each FCTRANSACTION 
			for (int i=0; i< fcTransVct.size(); i++)  {
				FctransSet fcs = (FctransSet)fcTransVct.elementAt(i);
				// create the fctrans with ftsetup as parent
				Vector attrCodesVct = new Vector(attrSet.getAttrCodes().size()+FCTRANS_KEYS.length);
				Hashtable attrValsTbl = new Hashtable(attrSet.getAttrValues().size()+FCTRANS_KEYS.length);
				attrCodesVct.addAll(attrSet.getAttrCodes());
				attrValsTbl.putAll(attrSet.getAttrValues());    

				for (int a=0; a<FCTRANS_KEYS.length; a++){
					attrCodesVct.add(FCTRANS_KEYS[a]);
					attrValsTbl.put(FCTRANS_KEYS[a],fcs.get(FCTRANS_KEYS[a]));
				}

				EntityItem fctransItem = eGrp.getEntityItem(tmpCreateCnt);
				if (fctransItem==null){
					addDebug("createFctrans: CODE ERROR fctransItem was null for tmpCreateCnt: "+tmpCreateCnt);
					break;
				}
				addDebug(D.EBUG_SPEW,"createFctrans: setting attributes for "+fctransItem.getKey());
				updateFctrans(fctransItem,attrCodesVct,attrValsTbl);
				tmpCreateCnt++;
			}

			if (tmpCreateCnt>0){
				// commit relators and children
				try{
					long startTime = System.currentTimeMillis();	    		
					createList.getEntityGroup("FTSETUPFCTRAN").commit(m_db, null); 
					//createdCnt = tmpCreateCnt;
					addDebug("createFctrans: created List after commit: "+PokUtils.outputList(createList));
		    		addDebug("Time to commit "+tmpCreateCnt+" new fctrans: "+Stopwatch.format(System.currentTimeMillis()-startTime));
		    		if(ftsetupAvailVct.size()>0){
		    			linkAvails(eGrp,ftsetupAvailVct,lai);
		    		}else{ 
		    			addDebug("WARNING: no AVAILs linked to "+ftsetupEntity.getKey());
		    		}
		    		EntityGroup eg = createList.getEntityGroup("FCTRANSACTION");
		    		// get all newly created ids
		        	for (int i=0; i<eg.getEntityItemCount(); i++){
		        		EntityItem fctrans = eg.getEntityItem(i);
		        		addDebug(D.EBUG_SPEW,"createFctrans created "+fctrans.getKey());
		        		fctransIdVct.add(new Integer(fctrans.getEntityID()));
		        	}
					// must handle this as post process because putattribute will fail for DQ and STATUS=Final
					if(fctstatus.equals(FCTSTATUS_Final)){
						moveToFinalAndQueue(null,fctransIdVct);
					}
					
					addDebug("Time to create "+fctransIdVct.size()+" fctrans and relators: "+Stopwatch.format(System.currentTimeMillis()-enterTime));	
				}catch(MiddlewareBusinessRuleException mbre){
					//MBRE_ERR = {0}
					args[0] = PokUtils.convertToHTML(mbre.toString());
					addError("MBRE_ERR",args);
				}
			}
			
			createList.dereference();
		}else{
			// CREATE_ERR = Unable to generate / update required {0}
			args[0] = m_elist.getEntityGroup("FCTRANSACTION").getLongDescription();
			addError("CREATE_ERR",args);
		}
		
		return fctransIdVct;
	}
	/***********************
	 * Get a set of new entity items, not one at a time
	 * commit all at once after attr are filled in
	 * @param parentItem
	 * @param cai
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private EntityList createFctrans(EntityItem parentItem,CreateActionItem cai) 
	throws SQLException, MiddlewareException 
	{
		// create the entities
		EntityList list = new EntityList(m_db, m_prof, cai, new EntityItem[] {parentItem}); 
		addDebug("createFctrans create List: "+PokUtils.outputList(list));
		return list;
	}
    
    /*********************
     * build a matching key for this to the template fctransset
     * @param fctrans
     * @return
     */
    private String buildKey(EntityItem fctrans){ 
    	StringBuffer sb = new StringBuffer();
    	for (int i=0; i<FCTRANS_KEYS.length; i++){
    		// skip comment it is not in part of uniqueness- dont add it to the key
    		if("COMMENT".equals(FCTRANS_KEYS[i])){
    			continue;
    		}
    		if (i>0){
    			sb.append(":");
    		}
    		sb.append(PokUtils.getAttributeValue(fctrans, FCTRANS_KEYS[i], "", "", false));
    	}
    	return sb.toString();
    }
    
    /*************************
     * Verify valid values are in the setup entity
     * 
A.	Setup Data

Entity Type:  Feature Transaction Setup (FTSETUP)
Attributes:  
AttributeCode	Type	Description
ANNDATE	T	Announce Date
BOXSWAP	U	Box swap required for Upgrades
FROMFC	T	From Feature Code
FROMMDL	T	From Model
FROMMT	T	From Machine Type
FTRULE	U	Feature Transation Rule
FTSETUPABR	A	FT Setup ABR
GENAVAILDATE	T	General Availability Date
HWFCCAT	U	HW Feature Category
HWFCSUBCAT	U	HW Feature Subcategory
INSTALL	U	Customer Setup
PARTSSHIPPED	U	Parts Shipped Indicator
RETURNEDPARTS	U	Returned Parts MES
TOFC	T	To Feature Code
TOMDL	T	To Model
TOMT	T	To Machine Type
UPGRADETYPE	U	Upgrade Type
WITHDRAWDATE	T	Withdraw Date
WTHDRWEFFCTVDATE	T	Withdrawal Effective Date
ZEROPRICE	U	Zero Priced Indicator

The following attributes are used to find matching Feature Transactions:
1.	TOMT must be a valid Machine Type (MACHTYPEATR)
2.	TOMDL is optional; however, if supplied, it must be 1 to 3 characters that meet all of the other rules for Model (MODELATR). 
3.	TOFC is optional; however, if supplied, it must be 1 to 6 characters that meet all of the other rules for Feature Code (FEATURECODE).
4.	FROMMT must be a valid Machine Type (MACHTYPEATR)
5.	FROMMDL is optional; however, if supplied, it must be 1 to 3 characters that meet all of the other rules for Model (MODELATR). 
6.	FROMFC is optional; however, if supplied, it must be 1 to 6 characters that meet all of the other rules for Feature Code (FEATURECODE).
 
The following attributes are used to determine Features to be considered when generating Feature Transactions:
7.	HWFCCAT is required; identical to the attribute on MODEL
8.	old HWFCSUBCAT is optional; identical to the attribute on MODEL
8.	FCTRANAPP is optional; a value of “No” means that this does not apply; a value of “Yes” requires a FEATURE to 
have a value of “Yes” for this attribute in order to be considered. If a FEATURE does not have a value for FCTRANAPP, 
then assume a value of “No”.
9.	FTRULE is required; has the following allowed values:
•	All
•	All But Self
•	Only Greater
•	Equal or Greater

The following attributes are optional. Changes to any of these attributes results in the Feature Transaction data being updated to match these values.
10.	ANNDATE must be a valid date (format YYYY MM DD )
11.	BOXSWAP has a list of allowed values, select one
12.	GENAVAILDATE must be a valid date
13.	INSTALL has a list of allowed values, select one
14.	PARTSSHIPPED has a list of allowed values, select one
15.	RETURNEDPARTS has a list of allowed values, select one
16.	UPGRADETYPE has a list of allowed values, select one
17.	WITHDRAWDATE must be a valid date  
18.	WTHDRWEFFCTVDATE must be a valid  
19.	ZEROPRICE has the following allowed values:
•	Yes
•	No

The following attribute is used by the system to run and track this ABR. It cannot be edited by the user.
20.	FTSETUPABR is informational and not directly updateable by the user.

Entity Type: Availability (AVAIL)
The user interface supports either the creation of Availability or the referencing of existing Availability. There are no data model changes to Availability (AVAIL). 

     * @param ftsetupEntity
     * @param skippedKeysVct
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws EANBusinessRuleException
     * @throws MiddlewareShutdownInProgressException
     */
    private void validateSetup(EntityItem ftsetupEntity,Vector skippedKeysVct) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, 
    EANBusinessRuleException, MiddlewareShutdownInProgressException
    {
    	// get the set of valid MACHTYPEATRs
        EntityGroup eg = new EntityGroup(null, m_db, m_prof, "MODEL", "Edit");
    
        EANMetaFlagAttribute mAttr = (EANMetaFlagAttribute)eg.getMetaAttribute("MACHTYPEATR");
        String tomt = PokUtils.getAttributeValue(ftsetupEntity, "TOMT", "", "", false);
        String frommt = PokUtils.getAttributeValue(ftsetupEntity, "FROMMT", "", "", false);
        addDebug("validateSetup "+ftsetupEntity.getKey()+" TOMT:"+tomt+" FROMMT:"+frommt);
        if (!mAttr.containsMetaFlag(tomt)){
        	//INVALID_MT_ERR = Error: Invalid {0} specified, {1}
        	args[0] = PokUtils.getAttributeDescription(ftsetupEntity.getEntityGroup(), "TOMT", "TOMT");
			args[1] = tomt;
			addError("INVALID_MT_ERR",args);
        }
        if (!mAttr.containsMetaFlag(frommt)){
        	//INVALID_MT_ERR = Error: Invalid {0} specified, {1}
        	args[0] = PokUtils.getAttributeDescription(ftsetupEntity.getEntityGroup(), "FROMMT", "FROMMT");
			args[1] = frommt;
			addError("INVALID_MT_ERR",args);
        }
        // do any other checks
        String ftrule = PokUtils.getAttributeFlagValue(ftsetupEntity, "FTRULE");
        if (ftrule==null){ //this shouldnt happen
        	//NO_VALUE_ERR = Error: No value specified for {0}.  Execution is terminating.
        	args[0] = PokUtils.getAttributeDescription(ftsetupEntity.getEntityGroup(), "FTRULE", "FTRULE");
			addError("NO_VALUE_ERR",args);
        }
        String hwfccat = PokUtils.getAttributeFlagValue(ftsetupEntity, "HWFCCAT");
        if (hwfccat==null){ //this shouldnt happen
        	//NO_VALUE_ERR = Error: No value specified for {0}.  Execution is terminating.
        	args[0] = PokUtils.getAttributeDescription(ftsetupEntity.getEntityGroup(), "HWFCCAT", "HWFCCAT");
			addError("NO_VALUE_ERR",args);
        }
       
        /*
So the only valid combinations are 1 and 4.  Everything else is an error.
1.	Capacity on Demand (202) 
Rule: All But Self
4.	Memory (213)
Rule: Equal or Greater
         */
        
        if ((HWFCCAT_Memory.equals(hwfccat)&&FTRULE_EqualGreater.equals(ftrule)) ||
        //	 	HWFCCAT_COD.equals(hwfccat)&&FTRULE_AllButSelf.equals(ftrule)){	
        		HWFCCAT_CSET.equals(hwfccat)&&FTRULE_AllButSelf.equals(ftrule)){	
        }else{
        //5.	Invalid Hardware Feature Code Category / Feature Transaction Rule combination
        //INVALID_COMBO_ERR=Invalid {0} / {1} combination
        	args[0] = getLD_Value(ftsetupEntity, "HWFCCAT");
        	args[1] = getLD_Value(ftsetupEntity, "FTRULE");
			addError("INVALID_COMBO_ERR",args);
        }
        
		for (int i=0; i<FCTRANSLIST_ATTR.length; i++){
			EANMetaAttribute ma = ftsetupEntity.getEntityGroup().getMetaAttribute(FCTRANSLIST_ATTR[i]);
			if (ma==null){
				metaError = true;
				addOutput("ERROR: "+FCTRANSLIST_ATTR[i]+" not found in "+ftsetupEntity.getEntityGroup()+" meta!");
				setReturnCode(FAIL);
			}
		}
		
        if(getReturnCode()!= PokBaseABR.PASS){
        	return;
        }
        
        String tomdl = PokUtils.getAttributeValue(ftsetupEntity, "TOMDL", "", "", false);
        String frommdl = PokUtils.getAttributeValue(ftsetupEntity, "FROMMDL", "", "", false);
        String tofc = PokUtils.getAttributeValue(ftsetupEntity, "TOFC", "", "", false);
        String fromfc = PokUtils.getAttributeValue(ftsetupEntity, "FROMFC", "", "", false);

        addDebug("validateSetup "+ftsetupEntity.getKey()+" TOMDL:"+tomdl+" FROMMDL:"+frommdl+
        		" TOFC:"+tofc+" FROMFC:"+fromfc+" HWFCCAT:"+hwfccat);
        
        long startTime = System.currentTimeMillis();
        // find models based on mt (and model if set)
        EntityItem toModelArray[] = searchForModels(tomt, tomdl,TO_MODEL_SRCHACTION_NAME);
        EntityItem fromModelArray[] = searchForModels(frommt, frommdl,FROM_MODEL_SRCHACTION_NAME);
        addDebug("Time to do searches: "+Stopwatch.format(System.currentTimeMillis()-startTime));
        
        if (toModelArray==null || toModelArray.length==0){
        	//MDL_NOTFOUND_ERR = Cannot find any {0} and {1}
        	args[0] = getLD_Value(ftsetupEntity, "TOMT");
			args[1] = getLD_Value(ftsetupEntity, "TOMDL");
			addError("MDL_NOTFOUND_ERR",args);
        }
        if (fromModelArray==null || fromModelArray.length==0){
        	//MDL_NOTFOUND_ERR = Cannot find any {0} and {1}
        	args[0] = getLD_Value(ftsetupEntity, "FROMMT");
			args[1] = getLD_Value(ftsetupEntity, "FROMMDL");
			addError("MDL_NOTFOUND_ERR",args);
        }
        
        if(getReturnCode()!= PokBaseABR.PASS){
        	return;
        }
        
        String veName = (String)HWFCCAT_VE_VCT.get(hwfccat);
        if (veName==null){
        	veName = "EXFTMODEL";// no filters, just mdl-ps-feature
        }
      
        startTime = System.currentTimeMillis();
        
        String fctranapp = PokUtils.getAttributeFlagValue(ftsetupEntity, "FCTRANAPP");
        addDebug("validateSetup FCTRANAPP "+fctranapp);
        boolean filterOnFCTRANAPP = FCTRANAPP_Yes.equals(fctranapp);
     
        // pull extract based on type of check. (use filtering)
        EntityList toList = m_db.getEntityList(m_prof,
        		new ExtractActionItem(null, m_db,m_prof, veName), toModelArray);
        addDebug("validateSetup "+veName+" toList: "+PokUtils.outputList(toList));
        addDebug("Time to pull TO VE: "+Stopwatch.format(System.currentTimeMillis()-startTime));

        // build to and from sets based on type of check
        Vector toVct = buildMTMFCSet(toList, "TO", tofc,hwfccat,filterOnFCTRANAPP);
        addDebug("toVct: "+toVct);
    	toList.dereference(); // release as soon as possible
        if (toVct.size()==0){
        	String errcode = "HWFCCAT_NOTFOUND_ERR";
        	//HWFCCAT_NOTFOUND_ERR = No Features found for the specified {0} and {1}
        	//3.	No Features found for the specified Hardware Feature Category
			args[0] = getLD_Value(ftsetupEntity, "HWFCCAT");
			args[1] = getLD_Value(ftsetupEntity, "TOFC");
			if(filterOnFCTRANAPP){
				//HWFCCAT_FCTRANAPP_NOTFOUND_ERR = No Features found for the specified {0}, {1} and {2}
				args[2] = getLD_Value(ftsetupEntity, "FCTRANAPP");
				errcode = "HWFCCAT_FCTRANAPP_NOTFOUND_ERR";
			}
			addError(errcode,args);        	
        }
        
        startTime = System.currentTimeMillis();
        EntityList fromList = m_db.getEntityList(m_prof,
        		new ExtractActionItem(null, m_db,m_prof, veName), fromModelArray);
        addDebug("validateSetup "+veName+" fromList: "+PokUtils.outputList(fromList));
        addDebug("Time to pull FROM VE: "+Stopwatch.format(System.currentTimeMillis()-startTime));     
        
        Vector fromVct = buildMTMFCSet(fromList, "FROM",fromfc,hwfccat,filterOnFCTRANAPP);
        addDebug("fromVct: "+fromVct);
    	fromList.dereference();
        if (fromVct.size()==0){
        	String errcode = "HWFCCAT_NOTFOUND_ERR";
         	//HWFCCAT_NOTFOUND_ERR = No Features found for the specified {0} and {1}
        	//3.	No Features found for the specified Hardware Feature Category
			args[0] = getLD_Value(ftsetupEntity, "HWFCCAT");
			args[1] = getLD_Value(ftsetupEntity, "FROMFC");
			if(filterOnFCTRANAPP){
				//HWFCCAT_FCTRANAPP_NOTFOUND_ERR = No Features found for the specified {0}, {1} and {2}
				args[2] = getLD_Value(ftsetupEntity, "FCTRANAPP");
				errcode = "HWFCCAT_FCTRANAPP_NOTFOUND_ERR";
			}
			addError(errcode,args);  
        }
        
        if(getReturnCode()!= PokBaseABR.PASS){
        	return;
        }
        
        // join them based on FTRULE
        fcTransComboTbl = new Hashtable();

    	if (FTRULE_EqualGreater.equals(ftrule)){
    		joinEqualOrGreater(toVct, fromVct);
    	}else if (FTRULE_OnlyGreater.equals(ftrule)){
    		joinOnlyGreater(toVct, fromVct);
    	}else if (FTRULE_All.equals(ftrule)){
    		joinAll(toVct, fromVct);
    	}else if (FTRULE_AllButSelf.equals(ftrule)){
    		joinAllButSelf(toVct, fromVct);
    	}
        
    	addDebug("fcTransComboTbl "+fcTransComboTbl);
    	if (fcTransComboTbl.size()==0){
    		//NOTFOUND_ERR = There are no valid Feature Transactions between {0}, {1}, {2} and {3}, {4}, {5}
    		args[0] = getLD_Value(ftsetupEntity, "FROMMT");
			args[1] = getLD_Value(ftsetupEntity, "FROMMDL");
			args[2] = getLD_Value(ftsetupEntity, "FROMFC");
    		args[3] = getLD_Value(ftsetupEntity, "TOMT");
    		args[4] = getLD_Value(ftsetupEntity, "TOMDL");
    		args[5] = getLD_Value(ftsetupEntity, "TOFC");
    		addError("NOTFOUND_ERR",args);        	
    	}
    	// remove any keys that were skipped, dont attempt to create them, they exist
    	for (int i=0; i<skippedKeysVct.size(); i++){
    		String key = skippedKeysVct.elementAt(i).toString();
    		if (fcTransComboTbl.containsKey(key)){
    			fcTransComboTbl.remove(key);
    			addDebug("validateSetup: removing "+key+" from fcTransComboTbl because it exists and will be skipped");
    		}
    	}

        toVct.clear();
        fromVct.clear();
    }
 /********************
 B.	Feature Transaction Rule

Each instance of Feature Transaction Setup (FTSETUP) specifies a Feature Transaction Rule (FTRULE) 
that the ABR will use to generate / update children Feature Transactions (FCTRANSACTION).

Consider a matrix where the rows correspond to the Feature Codes a From Machine Type Model and the 
Columns correspond to the Feature Codes of a To Machine Type Model. The rules specify how the cells 
of the matrix apply (i.e. a valid conversion or not). The rule type and data used for the comparison 
is based on HW Feature Category (HWFCCAT):
1.	Books
Rule: Only Greater

After review with zSeries, it was agreed that these will be handled via data input using the 
User Interface since there are a small number and there does not appear to be a well defined rule for generating them.
 
2.	Capacity on Demand (202) 
Rule: All But Self

All Features (FEATURE) for the Type Model (MODEL) where HW Feature Category (HWFCCAT) is “Capacity on Demand” (202) 
are considered (i.e. PRODSTRUCT).

Every valid combination of From / To Features except for identical Feature Codes are used to generate 
Feature Conversions (aka Feature Transactions).

Example:
MT:  2098
MDL: ABC and DEF
Features: 5013 thru 5021

Setup:
 

Result:
 

3.	I/O (282)
Rule: Only Greater

All Features (FEATURE) for the Type Model (MODEL) where HW Feature Category (HWFCCAT) is “I/O” (282) 
are considered (i.e. PRODSTRUCT).

After review with zSeries, it was agreed that these will be handled via data input using the User Interface 
since there are a small number and there does not appear to be a well defined rule for generating them.

 
4.	Memory (213)
Rule: Equal or Greater

All Features (FEATURE) for the Type Model (MODEL) where HW Feature Category (HWFCCAT) is “Memory” (213) 
are considered (i.e. PRODSTRUCT). They are ordered by Capacity Value. All combinations where the Capacity Value 
of the To Feature are greater than the Capacity Value of the From Feature are valid.

These Features have a Child Entity Type: 
Memory (MEMORY)
that have Attribute Codes:
Memory Capacity (MEMRYCAP) 
Capacity Units (CAPUNIT)
where:
Memory Capacity is an Integer and a maximum of 5 digits
Capacity Units allowed values:
KB (C0050) = 10**3
MB (C0010) = 10**6
GB (C0040) = 10**9

Example:
From Machine Type:	2098
To Machine Type: 2098
Feature Codes
FC	Capacity	Units	Capacity Value
2401	4	GB	4,000,000,000
2402	8	GB	8,000,000,000
2403	12	GB	12,000,000,000

Model ABC has Features 2401 and 2402
Model DEF has Features 2402 and 2403

Then valid conversions are:
 

5.	Processor (219)
Rule: TBD

All Features (FEATURE) for the Type Model (MODEL) where HW Feature Category (HWFCCAT) is “Processor” 
(219) are considered (i.e. PRODSTRUCT).

Child Entity Type: Processor (PROC)
Attribute Code: Commercial MIPS=3.9xSPPR (MIPS)

After review with zSeries, it was agreed that these will be handled via data input using the User Interface 
since there are a small number and there does not appear to be a well defined rule for generating them.

  */
    private void joinEqualOrGreater(Vector toVct, Vector fromVct){
    	addDebug("Joining equal or greater");
    	for (int f=0; f<fromVct.size(); f++){
    		MTMFCSet fromSet = (MTMFCSet)fromVct.elementAt(f);
    		for (int t=0; t<toVct.size(); t++){
        		MTMFCSet toSet = (MTMFCSet)toVct.elementAt(t);
        		if (toSet.getCapacity() >= fromSet.getCapacity()){
        			FctransSet fcs = new FctransSet(fromSet,toSet);
        			fcTransComboTbl.put(fcs.getKey(),fcs);
        		}
        	}
    	}
    }
    private void joinOnlyGreater(Vector toVct, Vector fromVct){
    	addDebug("Joining only greater");
    	for (int f=0; f<fromVct.size(); f++){
    		MTMFCSet fromSet = (MTMFCSet)fromVct.elementAt(f);
    		for (int t=0; t<toVct.size(); t++){
        		MTMFCSet toSet = (MTMFCSet)toVct.elementAt(t);
        		if (toSet.getCapacity() > fromSet.getCapacity()){
        			FctransSet fcs = new FctransSet(fromSet,toSet);
        			fcTransComboTbl.put(fcs.getKey(),fcs);
        		}
        	}
    	}
    }
    private void joinAll(Vector toVct, Vector fromVct){
    	addDebug("Joining all ");
    	for (int f=0; f<fromVct.size(); f++){
    		MTMFCSet fromSet = (MTMFCSet)fromVct.elementAt(f);
    		for (int t=0; t<toVct.size(); t++){
        		MTMFCSet toSet = (MTMFCSet)toVct.elementAt(t);
        		FctransSet fcs = new FctransSet(fromSet,toSet);
    			fcTransComboTbl.put(fcs.getKey(),fcs);
        	}
    	}
    }
    private void joinAllButSelf(Vector toVct, Vector fromVct){
    	addDebug("Joining all but self ");
    	for (int f=0; f<fromVct.size(); f++){
    		MTMFCSet fromSet = (MTMFCSet)fromVct.elementAt(f);
    		for (int t=0; t<toVct.size(); t++){
        		MTMFCSet toSet = (MTMFCSet)toVct.elementAt(t);
        		if (toSet.fcode.equals(fromSet.fcode)){
        			continue;
        		}
        		FctransSet fcs = new FctransSet(fromSet,toSet);
    			fcTransComboTbl.put(fcs.getKey(),fcs);
        	}
    	}
    }
    /*******************
     * Build the set of TO or FROM model and features 
     * @param list
     * @param type
     * @param fcode
     * @param hwfccat
     * @param filterOnFCTRANAPP
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    private Vector buildMTMFCSet(EntityList list, String type, String fcode, String hwfccat,boolean filterOnFCTRANAPP) 
    throws SQLException, MiddlewareException
    {            
    	Vector mtmVct = new Vector();
    	EntityGroup mdlGrp = list.getParentEntityGroup();
    	for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
    		EntityItem mdlItem = mdlGrp.getEntityItem(i);
    		Vector featVct = PokUtils.getAllLinkedEntities(mdlItem, "PRODSTRUCT", "FEATURE");
    		for (int f=0; f<featVct.size(); f++){
    			EntityItem featItem = (EntityItem)featVct.elementAt(f);

    			if(filterOnFCTRANAPP){
    				String fctranapp = PokUtils.getAttributeFlagValue(featItem, "FCTRANAPP");
    				addDebug("buildMTMFCSet "+featItem.getKey()+" FCTRANAPP "+fctranapp);
    				if(!FCTRANAPP_Yes.equals(fctranapp)){
    					addDebug("buildMTMFCSet: Skipping "+featItem.getKey()+" because FCTRANAPP must be Yes");
    					continue;
    				}
    			}  
    			// check for FC filter
    			if (fcode.length()>0){
    				String fc= PokUtils.getAttributeValue(featItem, "FEATURECODE", "", "", false);
    				if (!fc.startsWith(fcode)){
    					addDebug("buildMTMFCSet: Skipping "+featItem.getKey()+" fc "+fc+" doesnt match "+type+
    							" "+fcode+" filter");
    					continue;
    				}
    			}
    			MTMFCSet mtmfcSet = null;
    	         
    			//build based on check type
    	        if (hwfccat.equals(HWFCCAT_Memory)){
    	        	// get MEMORY children, may be linked to same one more than once
    	        	Vector memVct = PokUtils.getAllLinkedEntities(featItem, "FEATUREMEMORY", "MEMORY");
    	        	if (memVct.size()>0){
    	        		Vector featMemVct = new Vector();
    	        		for (int m=0; m<featItem.getDownLinkCount();m++){
    	        			EANEntity rel = featItem.getDownLink(m);
    	        			if (rel.getEntityType().equals("FEATUREMEMORY")){
    	        				featMemVct.add(rel);
    	        			}
    	        		}
    	        		mtmfcSet = new MTMFCMemSet(mdlItem, featItem, featMemVct);
    	        		featMemVct.clear();
    	        	}else{
    	            	//MEM_NOTFOUND_ERR = Feature of type Memory is missing Memory child element
    	    			addError("MEM_NOTFOUND_ERR",null); 
    	    			continue;
    	        	}
    	        }else {//if (hwfccat.equals(HWFCCAT_CSET)){
    	        	mtmfcSet = new MTMFCSet(mdlItem, featItem);
    	        }
    	            
    	        mtmVct.add(mtmfcSet);
    		}
    		featVct.clear();
    	}
    	
        // sort them by criteria
    	Collections.sort(mtmVct);
    	
    	return mtmVct;
    }

	/*************************************
	 * @param machtype
	 * @param modelatr
	 * @param actionName
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem[] searchForModels(String machtype, String modelatr, String actionName) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		addDebug("searchForModels entered machtype "+machtype+" modelatr "+modelatr);
		EntityItem eia[]= null;
		Vector attrVct = new Vector(2);
		attrVct.addElement("MACHTYPEATR");
		if (modelatr.length()>0){
			attrVct.addElement("MODELATR");
		}
		Vector valVct = new Vector(2);
		valVct.addElement(machtype);
		if (modelatr.length()>0){
			valVct.addElement(modelatr);
		}

		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					actionName, "MODEL", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForModels SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug("searchForModels found "+eia[i].getKey());
			}
		}
		attrVct.clear();
		valVct.clear();
		return eia;
	}
	/*****************************
	 * Look for Model and Feature based on machtype, model and featurecode
	 * use this for case 6, wont have filter for VE
	 * @param mt
	 * @param mdl
	 * @param fcode
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * /
	private EntityList searchForMTM(String mt, String mdl, String fcode) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{		
		EntityList psList = null;
		Vector attrVct = new Vector(3);
		attrVct.addElement("MODEL:MACHTYPEATR");
		if(mdl.length()>0){
			attrVct.addElement("MODEL:MODELATR");
		}
		if(fcode.length()>0){
			attrVct.addElement("FEATURE:FEATURECODE");
		}
		
		Vector valVct = new Vector(3);
		valVct.addElement(mt);
		if(mdl.length()>0){
			valVct.addElement(mdl);
		}
		if(fcode.length()>0){
			valVct.addElement(fcode);
		}

		addDebug("searchForMTM attrVct "+attrVct+" valVct "+valVct);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					PS_SRCHACTION_NAME, "PRODSTRUCT", true, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForMTM SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			psList = eia[0].getEntityGroup().getEntityList();
		}
		
		attrVct.clear();
		valVct.clear();
		return psList;
	}*/
	
    /**********************************
     * Add or remove AVAILs from the current FCTRANS items based on those linked to the setup entity
     * @param ftsetupEntity
     * @param ftsetupAvailVct
     * @param lai
     * @throws SQLException
     * @throws MiddlewareException
     * @throws LockException
     * @throws MiddlewareShutdownInProgressException
     * @throws EANBusinessRuleException
     * @throws RemoteException
     * @throws WorkflowException
     */
    private void updateFCTRANSAvails(EntityItem ftsetupEntity,Vector ftsetupAvailVct,LinkActionItem lai) 
    throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, 
    EANBusinessRuleException, RemoteException, WorkflowException 
    {
    	Hashtable missingAvailTbl = new Hashtable();  // key is fctrans, value is vector of missing avails
    	Vector extraFcTransAvailVct = new Vector();  // FEATURETRNAVAIL items
    	EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
 
    	// each current FCTRANS must be linked to these AVAILS and only these AVAILS
    	for (int i=0; i<fctransGrp.getEntityItemCount(); i++){
    		EntityItem fctransItem = fctransGrp.getEntityItem(i);
    		Vector fctransAvailVct = PokUtils.getAllLinkedEntities(fctransItem, "FEATURETRNAVAIL", "AVAIL");
    		if (fctransAvailVct.containsAll(ftsetupAvailVct) && ftsetupAvailVct.containsAll(fctransAvailVct)){
    			addDebug("updateFCTRANSAvails all avails match for "+fctransItem.getKey());
    			continue;
    		}
    		//look for missing AVAILs
    		for (int a=0; a<ftsetupAvailVct.size(); a++){
        		EntityItem ftsetupAvailItem = (EntityItem)ftsetupAvailVct.elementAt(a);
        		if (!fctransAvailVct.contains(ftsetupAvailItem)){
        			addDebug("updateFCTRANSAvails  "+fctransItem.getKey()+" is missing "+ftsetupAvailItem.getKey());
        			Vector vct = (Vector)missingAvailTbl.get(fctransItem.getKey());
        			if (vct==null){
        				vct = new Vector();
        				missingAvailTbl.put(fctransItem.getKey(),vct);
        			}
        			vct.add(ftsetupAvailItem);
        		}
    		}
		
    		//look for extra AVAILs
    		for (int a=0; a<fctransAvailVct.size(); a++){
        		EntityItem fctransAvailItem = (EntityItem)fctransAvailVct.elementAt(a);
        		if (!ftsetupAvailVct.contains(fctransAvailItem)){
        			addDebug("updateFCTRANSAvails  "+fctransItem.getKey()+" has extra "+fctransAvailItem.getKey()+
        					" will remove "+fctransAvailItem.getUpLink(0).getKey());
        			extraFcTransAvailVct.add(fctransAvailItem.getUpLink(0));
        		}
    		}
 
    		fctransAvailVct.clear();
    	} // end fctrans group

    	// remove extra links
    	if (extraFcTransAvailVct.size()>0){
    		// unlink these
    		unlinkAvails(extraFcTransAvailVct);
        	extraFcTransAvailVct.clear();
    	}
    	
    	// add missing links
    	if (missingAvailTbl.size()>0){
    		linkAvails(missingAvailTbl, ftsetupAvailVct,lai);
    	}
    	
    	// release memory
    	missingAvailTbl.clear();
    }
    
    /**************************************
     * Get long description and navigation name for specified entity
     * @param item
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * /
    private String getLD_NDN(EntityItem item) throws SQLException, MiddlewareException    {
    	return item.getEntityGroup().getLongDescription()+" &quot;"+getNavigationName(item)+"&quot;";
    }*/
    /************************************
     * @param item
     * @param attrCode
     * @return
     */
    private String getLD_Value(EntityItem item, String attrCode)   {
    	return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
    		PokUtils.getAttributeValue(item, attrCode, ",", "", false);
    }
    /***********************
     * unlink these avails from the fctransactions
     * @param availRelVct
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws LockException
     * @throws MiddlewareShutdownInProgressException
     * @throws EANBusinessRuleException
     */
    private void unlinkAvails(Vector availRelVct) 
    throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException
    {
    	long startTime = System.currentTimeMillis();
    	DeleteActionItem dai = new DeleteActionItem(null, m_db,m_prof,FCTRANSAVAIL_DELETEACTION_NAME);
    	addDebug("unlinkAvails cnt "+availRelVct.size());
  
    	EntityItem childArray[] = new EntityItem[availRelVct.size()];
    	availRelVct.copyInto(childArray);
    	// do the delete
    	dai.setEntityItems(childArray);
    	m_db.executeAction(m_prof, dai);
    	
	    addDebug("Time to unlink existing fctrans from avail: "+Stopwatch.format(System.currentTimeMillis()-startTime));
    }
    
	/************************
	 * link missing avails to the current set of fctransactions
	 *  
	 * @param missingAvailTbl - key current fctrans, value vector of missing avail items
	 * @param ftsetupAvailVct - vector of avails linked to the setup entity
	 * @param lai
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private void linkAvails(Hashtable missingAvailTbl, Vector ftsetupAvailVct,LinkActionItem lai)
	throws SQLException, MiddlewareException, LockException,
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException
	{
	    long startTime = System.currentTimeMillis();
		EntityGroup fctransGrp = m_elist.getEntityGroup("FCTRANSACTION");
		
		// add missing avails to current fctrans, cant do a mass thing here, dupes are created
		for (Enumeration e = missingAvailTbl.keys(); e.hasMoreElements();)
		{
			String fctranskey = (String)e.nextElement();
			Vector availVct = (Vector)missingAvailTbl.get(fctranskey);
			EntityItem fctrans = fctransGrp.getEntityItem(fctranskey);
			EntityItem parentArray[] = new EntityItem[] {fctrans};
			EntityItem childArray[] = new EntityItem[availVct.size()];
			availVct.copyInto(childArray);

			// do the link
			lai.setParentEntityItems(parentArray);
			lai.setChildEntityItems(childArray);
			m_db.executeAction(m_prof, lai);
			
			StringBuffer availSb = new StringBuffer();
			for (int a=0; a<childArray.length; a++){
				if (a>0){
					availSb.append(",");
				}
				availSb.append(childArray[a].getKey());
			}
			addDebug("linkAvails: linked "+fctrans.getKey()+" to "+availSb.toString());
					
			availVct.clear();
		}
	    addDebug("Time to link existing fctrans to avail: "+Stopwatch.format(System.currentTimeMillis()-startTime));
	}
   
	/*****************************
	 * link all avails to the new set of fctransactions
	 * @param fctransGrp
	 * @param ftsetupAvailVct
	 * @param lai
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private void linkAvails(EntityGroup fctransGrp, Vector ftsetupAvailVct,LinkActionItem lai)
	throws SQLException, MiddlewareException, LockException,
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException
	{
	    long startTime = System.currentTimeMillis();
		EntityItem parentArray[] = fctransGrp.getEntityItemsAsArray();
		EntityItem childArray[] = new EntityItem[ftsetupAvailVct.size()];
		ftsetupAvailVct.copyInto(childArray);

		// do the link
		lai.setParentEntityItems(parentArray);
		lai.setChildEntityItems(childArray);
		m_db.executeAction(m_prof, lai);

        addDebug("Time to link new fctrans to avail: "+Stopwatch.format(System.currentTimeMillis()-startTime));

		StringBuffer fctransSb = new StringBuffer();
		for (int a=0; a<parentArray.length; a++){
			if (a>0){
				fctransSb.append(",");
			}
			fctransSb.append(parentArray[a].getKey());
		}
		StringBuffer availSb = new StringBuffer();
		for (int a=0; a<childArray.length; a++){
			if (a>0){
				availSb.append(",");
			}
			availSb.append(childArray[a].getKey());
		}
		addDebug("linkAvails: linked "+fctransSb.toString()+" to "+availSb.toString());
	}
   
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
    	super.dereference();

    	rsBundle = null;
    	if (fcTransComboTbl!= null){
    		for (Enumeration eNum = fcTransComboTbl.elements(); eNum.hasMoreElements();)  {
    			FctransSet fcs = (FctransSet)eNum.nextElement();
    			fcs.dereference();
    		}
 
    		fcTransComboTbl.clear();
    		fcTransComboTbl = null;
    	}
    	
    	rptSb = null;
        args = null;
    	dbgPw=null;
		dbgfn = null;
		
        metaTbl = null;
        navName = null;
    }
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "1.6";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "FTSETUPABR";
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
	private void addDebug(String msg) { 
		if(dbgPw!=null){
			dbgLen+=msg.length();
			dbgPw.println(msg);
			dbgPw.flush();
		}else{
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}
	/**********************
	 * support conditional msgs
	 * @param level
	 * @param msg
	 */
	private void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			addDebug(msg);
		}
	}

	
	/**********************************
	 * get message using resource
	 *
	 * @param resrcCode
	 * @param args
	 * @return
	 * /
	private String getResourceMsg(String resrcCode, Object args[])
	{
		String msg = rsBundle.getString(resrcCode);
		if (args != null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		return msg;
	}*/
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
	 * used for warning output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Warning: '
	 *
	 */
	private void addWarning(String errCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();

		//WARNING_PREFIX = Warning: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(rsBundle.getString("WARNING_PREFIX"));
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
     * B.	Generating Feature Transactions
     * Default and fixed values are as follows:
AttributeCode	Value
COMMENT 	see below
DATAQUALITY	default
FTCAT	"Feature Conversion" (406) - not true any more, user value will override
FTCLASS	Generated (GEN)
FTSUBCAT	"N/A" (010)
FTVACTIVE	Yes (YES)
PDHDOMAIN	default for workgroup
STATUS	default
TRANSACTQTY	"1"
COMMENT is generated by concatenating the following in the order indicated (from left to right):
1.	MKTGNAME - taken from the “From Feature Code”
2.	“ To “
3.	MKTGNAME - taken from the “To Feature Code”

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
		AttrSet(EntityItem ftsetupEntity, boolean isCreate){ 
			if (isCreate){
				// fixed values
				// 	FTCAT	"Feature Conversion" (406)
				attrCodeVct.addElement("FTCAT");
				attrValTbl.put("FTCAT", "406"); // user will override this, leave here in case EXISTS rule is removed
				//  FTSUBCAT	"N/A" (010)
				attrCodeVct.addElement("FTSUBCAT");
				attrValTbl.put("FTSUBCAT", "010"); 
				//  FTCLASS	Generated (GEN)
				attrCodeVct.addElement("FTCLASS");
				attrValTbl.put("FTCLASS", "GEN"); 
				//  FTVACTIVE	Yes (YES)
				attrCodeVct.addElement("FTVACTIVE");
				attrValTbl.put("FTVACTIVE", "Y"); 
				//  TRANSACTQTY	"1"
				attrCodeVct.addElement("TRANSACTQTY");
				attrValTbl.put("TRANSACTQTY", "1"); 
			}
			
			for (int i=0; i<FCTRANSLIST_ATTR.length; i++){
				EANMetaAttribute ma = ftsetupEntity.getEntityGroup().getMetaAttribute(FCTRANSLIST_ATTR[i]);
				if (ma==null){
					continue;
				}
				//addDebug("ADDING "+FCTRANSLIST_ATTR[i]);
	    		if(ma.getAttributeType().equals("F")){
	    			addMult(ftsetupEntity, FCTRANSLIST_ATTR[i]);
	    		}else if(ma.getAttributeType().equals("U")){
	    			addSingle(ftsetupEntity, FCTRANSLIST_ATTR[i]);
	    		}else {
	    			addText(ftsetupEntity, FCTRANSLIST_ATTR[i]);
	    		}
			}
			
			addDebug((isCreate?"Create":"Update")+" attrCodeVct "+attrCodeVct);
			addDebug((isCreate?"Create":"Update")+" attrValTbl "+attrValTbl);
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
 
    private class MTMFCSet implements Comparable{
    	protected String machtype;
    	protected String model;
    	protected String fcode;
    	protected String mktgname;

    	MTMFCSet(EntityItem mdlItem, EntityItem featItem){
    		machtype = PokUtils.getAttributeValue(mdlItem, "MACHTYPEATR", "", "", false);
    		model = PokUtils.getAttributeValue(mdlItem, "MODELATR", "", "", false);
   			fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE", "", "", false);
   			mktgname = PokUtils.getAttributeValue(featItem, "MKTGNAME", "", "", false);
   			addDebug(D.EBUG_INFO,"MTMFCSet "+mdlItem.getKey()+" mt:"+machtype+" mdl:"+model+" "+featItem.getKey()+
   					" fc:"+fcode+" mktgname:"+mktgname);
    	}

    	String getKey() {return machtype+":"+model+":"+fcode;}
        public String toString(){
        	return machtype+":"+model+":"+fcode;
        }
        long getCapacity() { return 1;}
        void dereference(){
        	machtype = null;
        	model = null;
        	fcode=null;
        	mktgname = null;
        }

		public int compareTo(Object o) {
			MTMFCSet pn = (MTMFCSet)o;
			return toString().compareTo(pn.toString());
		}
    }
    /**************
     * 
     * These Features must have 1 - N Child Entity Type: 
Memory (MEMORY)
that have Attribute Codes:
Memory Capacity (MEMRYCAP) 
Capacity Units (CAPUNIT)
where:
Memory Capacity is an Integer and a maximum of 5 digits
Capacity Units allowed values:
KB (C0050) = 10**3
MB (C0010) = 10**6
GB (C0040) = 10**9
Capacity
	Is the sum of the child entity’s capacity of type Memory

     *
     */
    private class MTMFCMemSet extends MTMFCSet {
    	protected long memory;
    	 
    	/**
    	 * @param mdlItem
    	 * @param featItem
    	 * @param featmemVct - Vector of "FEATUREMEMORY" relators
    	 */
    	MTMFCMemSet(EntityItem mdlItem, EntityItem featItem, Vector featmemVct){
    		super(mdlItem,featItem);
    		long tmpmem = 0;
    		// must look at every relator, may be linked to same memory more than once
    		for (int i=0; i<featmemVct.size(); i++){
    			EANEntity fmRel = (EANEntity)featmemVct.elementAt(i);
    			EntityItem memItem = (EntityItem)fmRel.getDownLink(0);
    			String capunits = PokUtils.getAttributeFlagValue(memItem, "CAPUNIT");
    			String memcap = PokUtils.getAttributeValue(memItem, "MEMRYCAP","","1",false);
    			long multiplier=1;
    			if (CAPUNIT_MB.equals(capunits)){
    				multiplier = 1000;
    			}else if (CAPUNIT_GB.equals(capunits)){
    				multiplier = 1000000;
    			}
    			tmpmem += Long.parseLong(memcap)*multiplier;
    			addDebug(D.EBUG_INFO,"MTMFCMemSet "+memItem.getKey()+" capunits:"+capunits+" memcap:"+memcap+" memory:"+tmpmem);
    		}
    		memory = tmpmem;
    		addDebug(D.EBUG_INFO,"MTMFCMemSet memory:"+memory);
    	}
        public String toString(){
        	return machtype+":"+model+":"+fcode+":"+memory;
        }
        long getCapacity() { return memory;}
    }
    private class FctransSet {
    	protected MTMFCSet fromSet, toSet;
    	FctransSet(MTMFCSet from,MTMFCSet to){
    		fromSet = from;
    		toSet = to;
    	}  
    	
    	String get(String fctransAttr){
    		if ("FROMMACHTYPE".equals(fctransAttr)){
    			return fromSet.machtype;
    		}
    		if ("FROMMODEL".equals(fctransAttr)){
    			return fromSet.model;
    		}
    		if ("FROMFEATURECODE".equals(fctransAttr)){
    			return fromSet.fcode;
    		}
    		if ("TOMACHTYPE".equals(fctransAttr)){
    			return toSet.machtype;
    		}
    		if ("TOMODEL".equals(fctransAttr)){
    			return toSet.model;
    		}
    		if ("TOFEATURECODE".equals(fctransAttr)){
    			return toSet.fcode;
    		}
    		//COMMENT is generated by concatenating the following in the order indicated (from left to right):
    		//1.	MKTGNAME - taken from the “From Feature Code”
    		//2.	“ To “
    		//3.	MKTGNAME - taken from the “To Feature Code”
    		if ("COMMENT".equals(fctransAttr)){
    			return fromSet.mktgname+" To "+toSet.mktgname;
    		}
    		return null;
    	}
   
    	public String toString(){
    		return fromSet.toString()+":"+toSet.toString();
    	}
    	String getKey() {return fromSet.getKey()+":"+toSet.getKey(); }
    	void dereference(){
    		fromSet.dereference();
    		toSet.dereference();
    	}
    }
}
