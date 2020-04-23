//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import java.io.*;
import java.rmi.RemoteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.SingleFlag;

/****
 * BH FS ABR BHINVNAME IDL 20101116.doc
 * filter on nlsid, set status to chgreq if len>30, always set value
 * 
 * BH FS ABR BHINVNAME IDL 20100810b.doc
 * needs meta for BHINVNAMSETUP entity
 * 			  for BHINVNAMABRSTATUS and PDHDOMAIN attr
 * needs workflow action to queue abr - WFBHINVNAMSETUP
 * needs actions to create/edit this entity
 * 2 new VEs BHINVNAMEXT, BHINVNAMEXT2
 * needs SRDWG action
 * 
 * The following function is required for R1.0
 * The Business Requirements (BUS880 and BUS881) are identified within the document.
 * 
 * IV.	Overall Design
 * 
 * There are two parts to meeting these business requirements:
 *  - 	The IDL of BHINVNAME
 *  - 	The on-going generation of BHINVNAME
 *  
 *  The on-going support is provided via the Data Quality ABR which will generate a unique BHINVNAME for a 
 *  FEATURE within an INVENTORYGROUP. For a SW FEATURE, BHINVNAME will be unique within a PID (MODEL). 
 *  If the generated BHINVNAME is greater than 30 characters in length, the ABR will fail and STATUS 
 *  will not advance. Furthermore, the XML generation for the feed to SAP will not be queued.
 *  
 *  The IDL will be triggered by an authorized user with in the ISG Workgroup for all the MODELs within 
 *  a specified PDHDOMAIN (i.e. Workgroup). There are a couple of significant differences between the IDL 
 *  and the on-going support. If the generated BHINVNAME is greater than 30 characters, the ABR will create 
 *  BHINVNAME and may set STATUS and DATAQUALILTY. It will report an error. It will not flow downstream to 
 *  SAP. STATUS (and DATAQUALITY) may be modified by this IDL so that BHINVNAME will not flow downstream 
 *  if it is greater than 30 characters.. A user will be able to correct these failures via the User Interface 
 *  and by using the Data Quality ABR.
 *  
 *  This ABR does not need to worry about locks. The ABR should just update BHINVNAME which is not 
 *  currently maintained by users.
 *  
 *  V.	User Interface
 *  
 *  The user interface is via the JUI which allows an authorized user in the ISG workgroup to queue this ABRs 
 *  for a PDHDOMAIN. This allows for an orderly IDL of subsets of data. The SYSFEEDADMIN Role will be used for 
 *  this functionality.
 *  
BHINVNAMABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.BHINVNAMABRSTATUS
BHINVNAMABRSTATUS_enabled=true
BHINVNAMABRSTATUS_idler_class=D
BHINVNAMABRSTATUS_keepfile=true
BHINVNAMABRSTATUS_read_only=false
BHINVNAMABRSTATUS_vename=dummy
BHINVNAMABRSTATUS_debugLevel=0
#BHINVNAMABRSTATUS_velimit=2
 */
//$Log: BHINVNAMABRSTATUS.java,v $
//Revision 1.10  2014/01/13 13:53:29  wendy
//migration to V17
//
//Revision 1.9  2011/08/25 20:52:29  wendy
//Check for null printwriter
//
//Revision 1.8  2011/03/24 21:15:01  wendy
//correct update feature cnt msg
//
//Revision 1.7  2011/03/09 18:12:59  wendy
//add debug level support
//
//Revision 1.6  2010/12/16 17:10:38  wendy
//find FEATURE with STATUS=null
//
//Revision 1.5  2010/12/15 22:36:09  wendy
//Add more debug
//
//Revision 1.4  2010/12/02 21:53:07  wendy
//Trim featurecode, some have trailing blanks
//
//Revision 1.3  2010/12/02 18:13:08  wendy
//prevent exceeding 254 text limit
//
//Revision 1.2  2010/12/01 16:51:44  wendy
//updates for BH FS ABR BHINVNAME IDL 20101116.doc
//
//Revision 1.1  2010/09/07 16:07:58  wendy
//Init for BH FS ABR BHINVNAME IDL 20100810b.doc
//

public class BHINVNAMABRSTATUS extends PokBaseABR {
	private static final int MAXFILE_SIZE=5000000;
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = {'\n'};
	private static final int MAX_LEN = 30;
	private static final int UPDATE_SIZE = 200;
	
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];

	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";
	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private int dbgLen=0;
	private int abr_debuglvl=D.EBUG_ERR;
	  
	private HashSet swfeatSet = new HashSet();

	private Set dupelistedSet = new HashSet();
	private Hashtable invGrpInvnameTbl = new Hashtable(); 
	//key is invgrp, value is hashtable with key = invname  and value =integer count

	private static final String WG_SRCHACTION_NAME = "SRDWG"; 
	//STATUS	0020	Final
	//STATUS	0040	Ready for Review
	//STATUS	0050	Change Request
	private static final String STATUS_FINAL = "0020"; 
	private static final String STATUS_RFR = "0040"; 
	private static final String STATUS_CHGREQ = "0050"; 
	
	//DATAQUALITY	0050	Change Request
	private static final String DQ_CHGREQ = "0050"; 

	//find entities where Value of (BHINVNAME) is Empty (aka Null) or VALFROM(INVNAME) > VALFROM(BHINVNAME)
	/*
	private static final String BHVINVNAME_SQL = 
		"select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, "+
		"f1.attributevalue as INVENTORYGROUP, tf.attributevalue as FEATURECODE  "+
		"from opicm.text tf join "+
		"opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join "+ 
		"opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join "+
		"opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and "+ 
		"t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp "+   
		"where "+ 
		"tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and "+ 
		"p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and "+ 
		"f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and "+
		"tf.valto>current timestamp and tf.effto>current timestamp and "+
		"p.valto>current timestamp and p.effto>current timestamp and "+
		"f1.valto>current timestamp and f1.effto>current timestamp "+
		"and "+ 
		"not exists( "+
		"select tbh.attributevalue from opicm.text tbh where tbh.entitytype=tf.entitytype and tbh.entityid=tf.entityid and "+ 
		"tbh.attributecode='BHINVNAME' and tbh.valto>current timestamp and tbh.effto>current timestamp) "+
		"UNION "+ 
		"select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, "+ 
		"f1.attributevalue as INVENTORYGROUP, tf.attributevalue as FEATURECODE "+ 
		"from opicm.text tf join "+
		"opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join "+ 
		"opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid join "+
		"opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid join "+
		"opicm.text tbh on tf.entitytype=tbh.entitytype and tf.entityid=tbh.entityid "+ 
		"where "+ 
		"tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and "+ 
		"p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and "+ 
		"t.attributecode='INVNAME' and t.enterprise=? and "+ 
		"f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and "+
		"tbh.attributecode='BHINVNAME' and tbh.enterprise=? and "+
		"tf.valto>current timestamp and tf.effto>current timestamp and "+
		"p.valto>current timestamp and p.effto>current timestamp and "+
		"t.valto>current timestamp and t.effto>current timestamp and "+    
		"f1.valto>current timestamp and f1.effto>current timestamp and "+
		"tbh.valto>current timestamp and tbh.effto>current timestamp and t.valfrom > tbh.valfrom "+  
		"order by featurecode asc with ur; ";
*/
	//needed nlsid=1 filtering and return status
	private static final String BHVINVNAME_SQL = 
		"select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, "+
		"f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE,  "+
		"f2.attributevalue as STATUS "+
		"from opicm.text tf join "+
		"opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join "+                
		"opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join "+
		//"opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid left join "+ 
        "opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and "+
        "f2.attributecode='STATUS' and f2.enterprise=? and "+
        "f2.valto>current timestamp and f2.effto>current timestamp left join "+
		"opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and "+ 
		"t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp and "+ 
		"t.nlsid=1 "+  
		"where "+ 
		"tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and "+ 
		"p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and "+ 
		"f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and "+
		//"f2.attributecode='STATUS' and f2.enterprise=? and "+
		"tf.valto>current timestamp and tf.effto>current timestamp and "+
		"p.valto>current timestamp and p.effto>current timestamp and "+
		"f1.valto>current timestamp and f1.effto>current timestamp and "+
		//"f2.valto>current timestamp and f2.effto>current timestamp and "+
		"not exists( "+
		"select tbh.attributevalue from opicm.text tbh where tbh.entitytype=tf.entitytype and tbh.entityid=tf.entityid and "+ 
		"tbh.attributecode='BHINVNAME' and tbh.valto>current timestamp and tbh.effto>current timestamp and tbh.nlsid=1) "+
		"UNION "+ 
		"select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, "+ 
		"f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE, f2.attributevalue as STATUS "+ 
		"from opicm.text tf join "+
		"opicm.flag p on tf.entitytype=p.entitytype and tf.entityid=p.entityid join "+ 
		"opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid join "+
		"opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid join "+
		"opicm.text tbh on tf.entitytype=tbh.entitytype and tf.entityid=tbh.entityid left join "+
		//"opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid "+ 
        "opicm.flag f2 on tf.entitytype=f2.entitytype and tf.entityid=f2.entityid and "+
        "f2.attributecode='STATUS' and f2.enterprise=? and "+
        "f2.valto>current timestamp and f2.effto>current timestamp "+
		"where "+ 
		"tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and "+ 
		"p.attributecode = 'PDHDOMAIN' and p.attributevalue=? and p.enterprise=? and "+ 
		"t.attributecode='INVNAME' and t.enterprise=? and t.nlsid=1 and "+ 
		"f1.attributecode='INVENTORYGROUP' and f1.enterprise=? and "+
		//"f2.attributecode='STATUS' and f2.enterprise=? and "+
		"tbh.attributecode='BHINVNAME' and tbh.enterprise=? and tbh.nlsid=1 and "+
		"tf.valto>current timestamp and tf.effto>current timestamp and "+
		"p.valto>current timestamp and p.effto>current timestamp and "+
		"t.valto>current timestamp and t.effto>current timestamp and "+    
		"f1.valto>current timestamp and f1.effto>current timestamp and "+
		//"f2.valto>current timestamp and f2.effto>current timestamp and "+
		"tbh.valto>current timestamp and tbh.effto>current timestamp and t.valfrom > tbh.valfrom "+  
		"order by featurecode asc with ur; ";
	
	//find all invname for specified invgrp across all domains
	private static final String INVGRP_INVNAME_SQL = 
		"select tf.entityid as entityid, tf.entitytype as entitytype, t.attributevalue as INVNAME, "+
		"f1.attributevalue as INVENTORYGROUP, RTRIM(tf.attributevalue) as FEATURECODE  "+
		"from opicm.text tf join "+
		"opicm.flag f1 on tf.entitytype=f1.entitytype and tf.entityid=f1.entityid left join "+
		"opicm.text t on tf.entitytype=t.entitytype and tf.entityid=t.entityid and tf.nlsid=t.nlsid and "+ 
		"t.attributecode='INVNAME' and t.enterprise=? and t.valto>current timestamp and t.effto>current timestamp "+   
		"where "+ 
		"tf.entitytype='FEATURE' and tf.attributecode ='FEATURECODE' and tf.enterprise=? and tf.nlsid=1 and "+
		"f1.attributecode='INVENTORYGROUP' and f1.attributevalue=? and f1.enterprise=? and "+
		"tf.valto>current timestamp and tf.effto>current timestamp and "+
		"f1.valto>current timestamp and f1.effto>current timestamp order by featurecode asc with ur";

	private static final int MW_VENTITY_LIMIT;// get this from properties
	static{
		String velimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(
				"BHINVNAMABRSTATUS","_velimit","5");
		MW_VENTITY_LIMIT = Integer.parseInt(velimit);
	}

	//PreparedStatements for repeated operations
	private PreparedStatement invgrpPs, bhinvnameStatement;
	private Vector vctReturnsEntityKeys = new Vector();

	private EntityGroup featureGroup = null;
	private Hashtable setInvNameTbl = new Hashtable();
	private StringBuffer updatedSB = new StringBuffer();

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
	 *  VII.	ABR
	 *  
	 *  A user creates an instance of the setup entity BHINVNAMSETUP. The user must specify a single value 
	 *  for PDHDOMAIN.  The user will then use a workflow action to queue the ABR.
	 *  
	 *  The ABR will process NLSID = 1 only.
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
		String abrversion="";

		println(EACustom.getDocTypeHtml()); //Output the doctype and html

		try
		{
			long startTime = System.currentTimeMillis();
			start_ABRBuild(); // pull dummy VE

            abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

			setupPrintWriter();
			// force nlsid=1
	        m_prof.setReadLanguage(Profile.ENGLISH_LANGUAGE);
	        
			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			// get root from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			// debug display list of groups
			addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
					" extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			//Default set to pass
			setReturnCode(PASS);
	        
//			fixme remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			//NAME is navigate attributes
			navName = getNavigationName(rootEntity);

			String pdhdomain = PokUtils.getAttributeFlagValue(rootEntity, "PDHDOMAIN");
			addDebug(rootEntity.getKey()+" pdhdomain: "+pdhdomain);
			if(pdhdomain!=null){
				//The user must specify a single value for PDHDOMAIN. 
				String domains[] = PokUtils.convertToArray(pdhdomain);
				if (domains.length>1){
					//INVALID_DOMAIN_ERR = Invalid {0} specified. Only one must be selected.
					args[0] = getLD_Value(rootEntity, "PDHDOMAIN");
					addError("INVALID_DOMAIN_ERR",args);
				}else{
					pdhdomain = domains[0]; 
				}
			}else{
				//INVALID_DOMAIN_ERR = Invalid {0} specified. Only one must be selected.
				args[0] = getLD_Value(rootEntity, "PDHDOMAIN");
				addError("INVALID_DOMAIN_ERR",args);
			}

			if(getReturnCode()== PokBaseABR.PASS){  
				// reuse these statements for feature
				bhinvnameStatement = m_db.getPDHConnection().prepareStatement(BHVINVNAME_SQL);
				invgrpPs = m_db.getPDHConnection().prepareStatement(INVGRP_INVNAME_SQL);

				processFeature(pdhdomain); 
		
				rptSb.append(updatedSB.toString()); // put all updates after err msgs
				long curtime = System.currentTimeMillis();
				addDebug("Time to process FEATUREs: "+Stopwatch.format(curtime-startTime));

				updatedSB.setLength(0);
				processSWFeature(rootEntity,pdhdomain); 
				addDebug("Time to process SWFEATUREs: "+Stopwatch.format(System.currentTimeMillis()-curtime));
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
			
			rptSb.append(updatedSB.toString()); // put all updates before err msgs
			updatedSB.setLength(0);
			
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
		}
		finally	{
			closeStatements();

			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriter();
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
		args[4] = navName;
		args[5] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
		args[6] = abrversion+" "+getABRVersion();

		rptSb.append(updatedSB.toString()); // put all updates after err msgs

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

	/**
	 * A.	Processing FEATURE
	 * 
	 * For each FEATURE in the specified PDHDOMAIN, perform the following.
	 * If Value of (BHINVNAME) is Empty (aka Null)
	 * OR
	 * if VALFROM(INVNAME) > VALFROM(BHINVNAME)
	 * then Derive New Value for BHINVNAME
	 * 
	 * Derive New Value for BHINVNAME as follows:
	 * SEARCH FEATURE not restricted by PDHDOMAIN based on
	 * •	INVNAME
	 * •	INVENTORYGROUP
	 * 
	 * If the search only finds one FEATURE (i.e. itself),
	 * then
	 * 		BHINVNAME = INVNAME
	 * else
	 * 		BHINVNAME = FEATURECODE & “-“ & INVNAME
	 * 
	 * If length (NewValue) > 30, then set ABR Return Code = Fail and leave BHINVNAME empty.
	 * ErrorMessage LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME)
	 * 'has a derived value longer than 30 characters.'
	 * 
	 * @param pdhdomain
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException 
	 * @throws RemoteException 
	 */
	private void processFeature(String pdhdomain) throws SQLException, 
	MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException,
	RemoteException, EANBusinessRuleException 
	{	
		addDebug("processFeature for pdhdomain "+pdhdomain);
		featureGroup = new EntityGroup(null,m_db, m_prof, "FEATURE", "Edit", false);// needed for meta info
		
		addHeading(3,featureGroup.getLongDescription()+" Checks:");

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = featureGroup.getMetaAttribute("BHINVNAME");
		if (metaAttr==null) {
			// add error, this should not happen
			//META_ERR = {0} was not found in {1} meta.
			args[0] = "BHINVNAME";
			args[1] = featureGroup.getEntityType();
			addError("META_ERR",args);
			return;
		}
		
		long startTime = System.currentTimeMillis();
		// search for null BHINVNAME and VALFROM(INVNAME) > VALFROM(BHINVNAME)
		Vector fcvct = getHWEntities(pdhdomain);
		long curtime = System.currentTimeMillis();
		addDebug("Time to get FEATUREs: "+Stopwatch.format(curtime-startTime)+" fcvct.size "+fcvct.size());
		for (int i=0; i<fcvct.size(); i++){
			EntityInfo einf = (EntityInfo)fcvct.elementAt(i);
			setBHInvnameHW(einf);
			einf.dereference();
			if(vctReturnsEntityKeys.size()>=UPDATE_SIZE){
				// update the pdh with features
				updatePDH();
				vctReturnsEntityKeys.clear();
				long curtime2 = System.currentTimeMillis();
				addDebug("Time to update "+UPDATE_SIZE+" features: "+Stopwatch.format(curtime2-curtime));
				curtime = curtime2;
			}
		}
		fcvct.clear();
		
		if(vctReturnsEntityKeys.size()>0){
			// update the pdh with features
			int size = vctReturnsEntityKeys.size();
			updatePDH();
			addDebug("Time to update "+size+
					" features: "+Stopwatch.format(System.currentTimeMillis()-curtime));
			vctReturnsEntityKeys.clear();
		}
		
		// release memory now
		Iterator itr = invGrpInvnameTbl.keySet().iterator();
		while(itr.hasNext()) {
			String invGrp = (String) itr.next();
			Hashtable infotbl = (Hashtable)invGrpInvnameTbl.get(invGrp);
			Iterator infoitr = infotbl.keySet().iterator();
			while(infoitr.hasNext()) {
				String invname = (String) infoitr.next();
				InvnameInfo info = (InvnameInfo)infotbl.get(invname);
				info.dereference();
			}
			infotbl.clear();
		}
		
		invGrpInvnameTbl.clear();
	}

	/**
	 * Derive New Value for BHINVNAME as follows:
	 * SEARCH FEATURE not restricted by PDHDOMAIN based on
	 * •	INVNAME
	 * •	INVENTORYGROUP
	 * 
	 * If the search only finds one FEATURE (i.e. itself),then
	 * 		BHINVNAME = INVNAME
	 * else
	 * 		BHINVNAME = FEATURECODE & “-“ & INVNAME
	 * 
	 * If length (NewValue) > 30, then set ABR Return Code = Fail and leave BHINVNAME empty.
	 * ErrorMessage LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME)
	 * 'has a derived value longer than 30 characters.'
	 * 
	 * @param einf
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void setBHInvnameHW(EntityInfo einf) throws 
	MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		String fcode = einf.fcode;
		String bhinvname = null;
		String invname = einf.invname;
		String invgrp = einf.invGrp;
		addDebug(D.EBUG_SPEW,"setBHInvnameHW checking "+einf);

		// search for FEATUREs with same INVNAME and INVENTORYGROUP
		//If the search only finds one FEATURE (i.e. itself), 
		//then 
		//	BHINVNAME = INVNAME
		//else
		//	BHINVNAME = FEATURECODE & "-" & INVNAME

		if(!checkDuplicateHWInvname(invgrp, invname)){
			bhinvname = invname;
		}else{
			bhinvname = fcode+"-"+invname;
		}
		
		// protect against exceeding text limit
		//EANMetaAttribute.java(659): public static final int TEXT_MAX_LEN =254
		if(bhinvname.length()>EANMetaAttribute.TEXT_MAX_LEN){
			addDebug(D.EBUG_SPEW,"setBHInvnameHW ERROR "+einf+" bhinvname exceeds text limit "+bhinvname);
			bhinvname = bhinvname.substring(0, EANMetaAttribute.TEXT_MAX_LEN-1);
		}
		
		//always set this now
		setTextValue("BHINVNAME", bhinvname, einf);

		//addDebug("setBHInvnameHW derived bhinvname: "+bhinvname);
		if (bhinvname.length()>MAX_LEN){
			//If length (NewValue) > 30, then set ABR Return Code = Fail
			//ErrorMessage LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME) 
			//'has a derived value longer than 30 characters.'
			//DERIVED_LEN_ERR = {0} {1} &quot;{2}&quot; has a derived value longer than 30 characters.
			EntityItem item = new EntityItem(featureGroup, m_prof, m_db, einf.entityType, einf.entityid);
			args[0]=this.getLD_NDN(item);
			args[1] = PokUtils.getAttributeDescription(featureGroup, "BHINVNAME", "BHINVNAME");
			args[2] = bhinvname;
			addError("DERIVED_LEN_ERR",args);
			// release memory but keep the group
			featureGroup.removeEntityItem(item);
			for (int z = item.getAttributeCount() - 1; z >= 0; z--) {
				EANAttribute att = item.getAttribute(z);
				item.removeAttribute(att);
			}
			item.setParent(null);

			//If STATUS = “Ready for Review” (0040) or “Final” (0020), the ABR will set STATUS = “Change Request” (0050) and DATAQUALITY = “Change Request” (0050) .
			if(STATUS_FINAL.equals(einf.status) || STATUS_RFR.equals(einf.status)){
				setStatusAndDQ(einf);
			}
		}
	}    

	/**
	 * get all invnames for invgrp, check if a duplicate was found across all domains
	 * @param invgrp
	 * @param invname
	 * @return
	 * @throws java.sql.SQLException
	 */
	private boolean checkDuplicateHWInvname(String invgrp, String invname)
	throws java.sql.SQLException
	{
		//addDebug("checkDuplicateInvname: entered invgrp "+invgrp+" invname "+invname);
		ResultSet result=null;
		Hashtable invnameTbl = (Hashtable)invGrpInvnameTbl.get(invgrp);
		if (invnameTbl !=null){
			InvnameInfo info = (InvnameInfo)invnameTbl.get(invname);
			if (info !=null){
				if(info.count>1){
					String key = invgrp+invname;
					if(!dupelistedSet.contains(key)){ // decrease debug output, just list once
						dupelistedSet.add(key);
						addDebug(D.EBUG_SPEW,"found dupe: "+invgrp+": "+invname+": "+info);
					}
					return true;
				}
			}
			return false;
		}

		String enterprise = m_prof.getEnterprise();
		long startTime = System.currentTimeMillis();
		try{
			invgrpPs.clearParameters();
			invgrpPs.setString(1, enterprise);
			invgrpPs.setString(2, enterprise);
			invgrpPs.setString(3, invgrp);
			invgrpPs.setString(4, enterprise);

			result = invgrpPs.executeQuery();
			while(result.next()) {	
				int eid = result.getInt(1);
				//String type = result.getString(2);
				String invname2 = result.getString(3);
				String invgrp2 = result.getString(4);
				String fc = result.getString(5);
				
			//	addDebug("checkDuplicateInvname: Query found "+invgrp2+" "+invname2+" for fc "+fc+" "+type+eid);
				if(invname2==null){
					continue;
				}
				
				invnameTbl = (Hashtable)invGrpInvnameTbl.get(invgrp2);
				if (invnameTbl ==null){
					invnameTbl = new Hashtable();
					invGrpInvnameTbl.put(invgrp2, invnameTbl);
					invnameTbl.put(invname2, new InvnameInfo(eid,fc));
				}else{
					InvnameInfo info = (InvnameInfo)invnameTbl.get(invname2);
					if (info ==null){
						info = new InvnameInfo(eid,fc);
						invnameTbl.put(invname2, info);
					}else{
						info.addId(eid, fc);
						//addDebug("found dupe "+invgrp2+" "+invname2+" "+info);
					}
				}
			}
		}
		catch(SQLException t) {
			throw(t);
		}
		finally{
			if (result!=null){
				result.close();
				result=null;
			}
			m_db.commit();
		    m_db.freeStatement();
		    m_db.isPending();
		}
		addDebug(D.EBUG_SPEW,"Time to get INVNAMEs for "+invgrp+" : "+Stopwatch.format(System.currentTimeMillis()-startTime));
		
		invnameTbl = (Hashtable)invGrpInvnameTbl.get(invgrp);
		if (invnameTbl !=null){
			InvnameInfo info = (InvnameInfo)invnameTbl.get(invname);
			if (info !=null){
				if(info.count>1){
					return true;
				}
			}
		}
		return false;
	}	   

	/**
	 * B.	Processing SW Feature
	 * 
	 * For each SW Feature in the specified PDHDOMAIN, perform the following.
	 * 
	 * If Value of (BHINVNAME) is Empty (aka Null) 
	 * OR
	 * if VALFROM(SWFEATURE.INVNAME) > VALFROM(BHINVNAME)
	 * then Derive New Value for BHINVNAME
	 * 
	 * If SWFEATURE.INVNAME is empty, then 
	 * IF SWPRODSTRUCT.INVNAME (SWPRODSTRUCT for the SWFEATURE if multiple, choose the first SWPRODSTRUCT) is not empty
	 * SWFEATURE.INVNAME = SWPRODSTRUCT.INVNAME
	 * Else
	 * Set the ABR Return Code = Fail with the following:
	 * ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) does not have a LD(INVNAME).
	 * 
	 * Derive New Value for BHINVNAME as follows:
	 * If the SWFEATURE is related to a MODEL(filtered by the COFGRP = “Base”) via SWPRODSTRUCT, then 
	 * Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
	 * •	MACHTYPEATR
	 * •	MODELATR
	 * •	SWFEATURE.INVNAME
	 * •	COFGRP = “Base” (150)
	 * If only one is found (i.e. itself),
	 * then BHINVNAME = SWFEATURE.INVNAME
	 * else BHINVNAME = FEATURECODE & “-“ & SWFEATUE.INVNAME
	 * else
	 * BHINVNAME = SWFEATURE.INVNAME
	 * 
	 * If length (NewValue) > 30, then set ABR Return Code = Fail and leave BHINVNAME empty.
	 * ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME) “has a derived value longer than 30 characters.”
	 * 
	 * 
	 * An alternative is to use an extract action with the Software Models for the Workgroup using 
	 * PDHDOMAIN and a filter of COFCAT = “Software” and COFGRP = “Base”. Then for each Software Model 
	 * as the root of a VE that pulls SWPRODSTRUCT and SWFEATURE. Then you have a list of SWFEATUREs to 
	 * check and can search the result of the extract for SWFEATURE.INVNAME.
	 * 
	 * @param rootEntity
	 * @param pdhdomain
	 * @throws Exception 
	 */
	private void processSWFeature(EntityItem rootEntity, String pdhdomain) throws 
	Exception 
	{
		addDebug("processSWFeature for pdhdomain "+pdhdomain);
		featureGroup = new EntityGroup(null,m_db, m_prof, "SWFEATURE", "Edit", false);// needed for meta info and msgs

		addHeading(3,featureGroup.getLongDescription()+" Checks:");
		
		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = featureGroup.getMetaAttribute("BHINVNAME");
		if (metaAttr==null) {
			// add error, this should not happen
			//META_ERR = {0} was not found in {1} meta.
			args[0] = "BHINVNAME";
			args[1] = featureGroup.getEntityType();
			addError("META_ERR",args);
			return;
		}

		// search for WG with this domain
		EntityItem wgitem = searchForWG(pdhdomain);
		if (wgitem==null){
			//WG_NOTFND = WorkGroup was not found for {0}. - should never happen
			args[0] = this.getLD_Value(rootEntity, "PDHDOMAIN");
			addError("WG_NOTFND",args);
			return;
		}

		// pull extract from WG to MODEL where COFCAT = “Software” and COFGRP = “Base”
		String VeName = "BHINVNAMEXT";//go from wg to model where cofgrp=base (150)

		EntityList mdllist = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db, m_prof, VeName),
				new EntityItem[] {wgitem});
		addDebug("processSWFeature: Extract "+VeName+NEWLINE+PokUtils.outputList(mdllist));

		// pull extract for groups of xx MODEL at a time to SWFEATURE
		EntityGroup mdlGrp = mdllist.getEntityGroup("MODEL");
		// mw has a limit of xx for entity ids.. must break into groups of xx or less
		if (mdlGrp.getEntityItemCount()>MW_VENTITY_LIMIT) {
			int numGrps = mdlGrp.getEntityItemCount()/MW_VENTITY_LIMIT;
			int numUsed=0;
			Vector tmpVct = new Vector(1);
			for (int i=0; i<=numGrps; i++)	{
				tmpVct.clear();
				for (int x=0; x<MW_VENTITY_LIMIT; x++) {
					if (numUsed == mdlGrp.getEntityItemCount()){
						break;
					}
					tmpVct.addElement(mdlGrp.getEntityItem(numUsed++));
				}
				if (tmpVct.size()>0) { // could be 0 if num entities is multiple of limit
					//pull extract to get SWFEATUREs and SWPRODSTRUCTs
					Vector einfVct = new Vector();
					EntityList swfclist = pullSWModelInfo(tmpVct, einfVct);
					setBHInvnameSW(swfclist, einfVct);
					swfclist.dereference();
					einfVct.clear();
				}
			}
			tmpVct.clear();
			tmpVct = null;
		} else {
			if(mdlGrp.getEntityItemCount()>0){
				Vector tmpVct = new Vector(1);
				for (int i=0; i<mdlGrp.getEntityItemCount(); i++)	{
					tmpVct.addElement(mdlGrp.getEntityItem(i));
				}
				// pull extract to get SWFEATUREs and SWPRODSTRUCTs
				Vector einfVct = new Vector();
				EntityList swfclist = pullSWModelInfo(tmpVct, einfVct);
				setBHInvnameSW(swfclist, einfVct);
				swfclist.dereference();
				einfVct.clear();
				tmpVct.clear();
				tmpVct = null;
			}
		}

		mdllist.dereference();
	}

	/**
	 * pull all SWFEATURE for these MODELs
	 * set SWFEATURE.INVNAME if needed, determine if derivation is needed
	 * 
	 * @param mdlvct - Vector of EntityItem
	 * @param einfVct
	 * @return
	 * @throws Exception 
	 */
	private EntityList pullSWModelInfo(Vector mdlvct,Vector einfVct) throws Exception
	{
		String VeName = "BHINVNAMEXT2";//go from model to swfeature
		EntityItem ei[] = new EntityItem[mdlvct.size()];
		mdlvct.copyInto(ei);
		
		EntityList mdllist = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db, m_prof, VeName),
				ei);
		addDebug(D.EBUG_INFO,"pullSWModelInfo: Extract "+VeName+NEWLINE+PokUtils.outputList(mdllist));

		// set INVNAME here if needed
		//If SWFEATURE.INVNAME is empty, then 
		//IF SWPRODSTRUCT.INVNAME (SWPRODSTRUCT for the SWFEATURE if multiple, choose the first SWPRODSTRUCT) is not empty
		//SWFEATURE.INVNAME = SWPRODSTRUCT.INVNAME
		EntityGroup mdlGrp = mdllist.getParentEntityGroup();
		for (int x=0; x<mdlGrp.getEntityItemCount(); x++){
			EntityItem mdlitem = mdlGrp.getEntityItem(x);
			Vector swfcVct = PokUtils.getAllLinkedEntities(mdlitem, "SWPRODSTRUCT", "SWFEATURE");
			for (int f=0; f<swfcVct.size(); f++){
				EntityItem swfcitem = (EntityItem)swfcVct.elementAt(f);
				if(swfeatSet.contains(swfcitem.getKey())){
					addDebug(D.EBUG_SPEW,"pullSWModelInfo: already handled "+swfcitem.getKey());
					continue;
				}
		
				swfeatSet.add(swfcitem.getKey());
				String invname = PokUtils.getAttributeValue(swfcitem, "INVNAME", "", null, false);
				if (invname==null){
					//If SWFEATURE.INVNAME is empty, then 
					//IF SWPRODSTRUCT.INVNAME (SWPRODSTRUCT for the SWFEATURE if multiple, choose the first SWPRODSTRUCT) is not empty
					//SWFEATURE.INVNAME = SWPRODSTRUCT.INVNAME 
					//Else
					//Set the ABR Return Code = Fail with the following:
					//ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) does not have a LD(INVNAME).
					EntityItem swpsitem = (EntityItem)swfcitem.getDownLink(0);
					String swpsinvname = PokUtils.getAttributeValue(swpsitem, "INVNAME", "", null, false);
					addDebug(D.EBUG_INFO,"pullSWModelInfo: "+mdlitem.getKey()+" "+swfcitem.getKey()+
							" invname "+invname+" "+swpsitem.getKey()+" swpsinvname "+swpsinvname);
					if(swpsinvname!=null){
						// derivation will be needed
						EntityInfo einf = new EntityInfo(swfcitem);
						einfVct.add(einf);
						setTextValue("INVNAME", swpsinvname, einf);
						einf.invname = swpsinvname;
						setInvNameTbl.put(swfcitem.getKey(), swpsinvname);
					}else{
						//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
						args[0]=getLD_NDN(swfcitem);
						args[1]=PokUtils.getAttributeDescription(swfcitem.getEntityGroup(), "INVNAME", "INVNAME");
						addError("REQ_NOTPOPULATED_ERR",args); 
					}
				}else{
			    	//If Value of (BHINVNAME) is Empty (aka Null) 
			    	//OR
			    	//if VALFROM(INVNAME) > VALFROM(BHINVNAME)
			    	//then Derive New Value for BHINVNAME
					String bhinvname = PokUtils.getAttributeValue(swfcitem, "BHINVNAME",", ", null, false);
					boolean derive = true;
					if(bhinvname!=null){
						addDebug(D.EBUG_SPEW,"pullSWModelInfo: "+mdlitem.getKey()+" "+swfcitem.getKey()+" invname "+invname+" bhinvname "+bhinvname);
						// get the attributehistory for INVNAME
						String invnameDts = getTimestamp(swfcitem, "INVNAME");
						// get the attributehistory for BHINVNAME
						String bhinvnameDts = getTimestamp(swfcitem, "BHINVNAME");
						addDebug(D.EBUG_SPEW,"pullSWModelInfo:         invnameDts: "+invnameDts+" bhinvnameDts: "+bhinvnameDts);
						derive = bhinvnameDts.compareTo(invnameDts)<0;
					}
					if(derive){
						EntityInfo einf = new EntityInfo(swfcitem);
						einfVct.add(einf);
					}
				}
			}
			swfcVct.clear();
		}

		return mdllist;
	}
	/**
	 *  SEARCH WG  not restricted by PDHDOMAIN based on 
	 *      •	PDHDOMAIN
	 * @param pdhdomain
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem searchForWG(String pdhdomain) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		Vector valVct = new Vector(1);
		attrVct.addElement("PDHDOMAIN");
		valVct.addElement(pdhdomain);
		EntityItem wgitem = null;
		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			addDebug("searchForWG using attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					WG_SRCHACTION_NAME, "WG", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForWG SBRException: "+exBuf.getBuffer().toString());
		}

		if(eia!=null){
			for (int i=0; i<eia.length; i++){
				String wgdomain = PokUtils.getAttributeFlagValue(eia[i], "PDHDOMAIN");
				addDebug("searchForWG found "+eia[i].getKey()+" wgdomain "+wgdomain);
				if (pdhdomain.equals(wgdomain)){ // look for the WG that is an exact match
					wgitem = eia[i];
					break;
				}
			}
		}

		attrVct.clear();
		valVct.clear();
		return wgitem;
	}

	/*****
	 * SetBHInvnameSW
	 * 
	 * If SWFEATURE.INVNAME is empty, then
	 * IF SWPRODSTRUCT.INVNAME (SWPRODSTRUCT for the SWFEATURE if multiple, choose the first SWPRODSTRUCT) is not empty
	 * 		SWFEATURE.INVNAME = SWPRODSTRUCT.INVNAME
	 * Else
	 * 		Set the ABR Return Code = Fail with the following:
	 * 		ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) does not have a LD(INVNAME).
	 * 
	 * Derive New Value for BHINVNAME as follows:
	 * If the SWFEATURE is related to a MODEL(filtered by the COFGRP = “Base”) via SWPRODSTRUCT, then 
	 * 		Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
	 * 		•	MACHTYPEATR
	 * 		•	MODELATR
	 * 		•	SWFEATURE.INVNAME
	 * 		•	COFGRP = “Base” (150)
	 * 		If only one is found (i.e. itself) then 
	 * 			BHINVNAME = SWFEATURE.INVNAME
	 * 		else 
	 * 			BHINVNAME = FEATURECODE & “-“ & SWFEATURE.INVNAME
	 * else
	 * 		BHINVNAME = SWFEATURE.INVNAME
	 * 
	 * If length (NewValue) > 30, then set ABR Return Code = Fail and leave BHINVNAME empty.
	 * ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME) “has a derived value longer than 30 characters.”
	 * 
	 *
	 * @param list
	 * @param einfVct
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws EANBusinessRuleException 
	 * @throws RemoteException 
	 * @throws Exception 
	 */
	private void setBHInvnameSW(EntityList swfclist, Vector einfVct) throws 
	SQLException, MiddlewareException, MiddlewareShutdownInProgressException, 
	RemoteException, EANBusinessRuleException 
	{
		EntityGroup swfcGrp = swfclist.getEntityGroup("SWFEATURE");
		for (int i=0; i<einfVct.size(); i++){ // this is the set of SWFEATURE info that need derivation
			EntityInfo einf = (EntityInfo)einfVct.elementAt(i);
			EntityItem swfcitem = swfcGrp.getEntityItem(einf.entityType+einf.entityid);

			String fcode = einf.fcode;
			String bhinvname = PokUtils.getAttributeValue(swfcitem, "BHINVNAME",", ", null, false);
			String invname = einf.invname; // may have a value now if pulled from SWPRODSTRUCT
			addDebug(D.EBUG_SPEW,"setBHInvnameSW checking "+swfcitem.getKey()+" fcode: "+fcode+
					" invname: "+invname+" bhinvname "+bhinvname+" swps.count "+swfcitem.getDownLinkCount());

			//If the SWFEATURE is related to a MODEL(filtered by the COFGRP = “Base”) via SWPRODSTRUCT, then 
			//Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
			//•	MACHTYPEATR
			//•	MODELATR
			//•	SWFEATURE.INVNAME
			//•	COFGRP = “Base” (150)

			//If only one is found (i.e. itself) then 
			//	BHINVNAME = SWFEATURE.INVNAME
			//else 
			//	BHINVNAME = FEATURECODE & “-“ & SWFEATURE.INVNAME
			if(uniqueSWPS(swfcitem,invname)){
				bhinvname = invname;
			}else{
				bhinvname = fcode+"-"+invname;
			}
			
			// protect against exceeding text limit
			//EANMetaAttribute.java(659): public static final int TEXT_MAX_LEN =254
			if(bhinvname.length()>EANMetaAttribute.TEXT_MAX_LEN){
				addDebug(D.EBUG_SPEW,"setBHInvnameSW ERROR "+swfcitem.getKey()+" bhinvname exceeds text limit "+bhinvname);
				bhinvname = bhinvname.substring(0, EANMetaAttribute.TEXT_MAX_LEN-1);
			}
			
			//always set this now
			setTextValue("BHINVNAME", bhinvname, einf);
			
			//addDebug("setBHInvnameSW derived "+swfcitem.getKey()+" bhinvname: "+bhinvname);
			if (bhinvname.length()>MAX_LEN){
				//If length (NewValue) > 30, then set ABR Return Code = Fail and will set BHINVNAME to the new value. 
				//ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(BHINVNAME) (derived value from above for BHINVNAME) 
				//“has a derived value longer than 30 characters.”

				//DERIVED_LEN_ERR = {0} {1} &quot;{2}&quot; has a derived value longer than 30 characters.
				args[0]=this.getLD_NDN(swfcitem);
				args[1] = PokUtils.getAttributeDescription(swfcitem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
				args[2] = bhinvname;
				addError("DERIVED_LEN_ERR",args);
				
				//If STATUS = “Ready for Review” (0040) or “Final” (0020), the ABR will set STATUS = “Change Request” (0050) and DATAQUALITY = “Change Request” (0050) .
				if(STATUS_FINAL.equals(einf.status) || STATUS_RFR.equals(einf.status)){
					setStatusAndDQ(einf);
				}
			}

			einf.dereference();
		}

		if(vctReturnsEntityKeys.size()>0){
			// update the pdh with swfeatures
			updatePDH();
			vctReturnsEntityKeys.clear();
		}
	}

	/**
	 * using extract to improve performance
	 * 	If the SWFEATURE is related to a MODEL(filtered by the COFGRP = 'Base') via SWPRODSTRUCT, then 
	 *		Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
	 *		•	MACHTYPEATR
	 *		•	MODELATR
	 *		•	SWFEATURE.INVNAME
	 *		•	COFGRP = 'Base' (150)
	 * @param swfeatItem
	 * @param invname
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private boolean uniqueSWPS(EntityItem swfeatItem, String invname) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		boolean unique = true;
		// get all the models for this SWFEATURE
		Vector mdlVct = PokUtils.getAllLinkedEntities(swfeatItem, "SWPRODSTRUCT", "MODEL");
		StringBuffer sb = new StringBuffer();
		for (int m=0; m<mdlVct.size(); m++){
			sb.append(" "+((EntityItem)mdlVct.elementAt(m)).getKey());
		}

		// get all the swfeatures for these models
		Vector swfcVct = PokUtils.getAllLinkedEntities(mdlVct, "SWPRODSTRUCT", "SWFEATURE");
		addDebug(D.EBUG_SPEW,"uniqueSWPS entered  "+swfeatItem.getKey()+" invname "+invname+" with "+sb.toString()+
				" swfcVct.size "+swfcVct.size());
		for(int p=0; p<swfcVct.size(); p++){
			EntityItem swfcitem = (EntityItem)swfcVct.elementAt(p);
			String swfcInvname = PokUtils.getAttributeValue(swfcitem, "INVNAME",", ", null, false);
			if (swfcInvname ==null){
				// was it set earlier?
				swfcInvname = (String)setInvNameTbl.get(swfcitem.getKey());
			}
			if (swfcitem.getKey().equals(swfeatItem.getKey())){ // skip this one
				continue;
			}
			addDebug(D.EBUG_SPEW,"uniqueSWPS checking "+swfcitem.getKey()+" swfcInvname "+swfcInvname);
			if (invname.equals(swfcInvname)){
				unique = false;
				break;
			}
		}

		// release memory
		swfcVct.clear();
		swfcVct= null;
		mdlVct.clear();
		mdlVct = null;
		return unique;
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
		rptSb = null;
		args = null;

		metaTbl = null;
		navName = null;
		vctReturnsEntityKeys.clear();
		vctReturnsEntityKeys = null;

		featureGroup = null;
		
		swfeatSet.clear();
		swfeatSet = null;
		
		dupelistedSet.clear();
		dupelistedSet = null;

		setInvNameTbl.clear();
		setInvNameTbl = null;

		Iterator itr = invGrpInvnameTbl.keySet().iterator();
		while(itr.hasNext()) {
			String invGrp = (String) itr.next();
			Hashtable infotbl = (Hashtable)invGrpInvnameTbl.get(invGrp);
			Iterator infoitr = infotbl.keySet().iterator();
			while(infoitr.hasNext()) {
				String invname = (String) infoitr.next();
				InvnameInfo info = (InvnameInfo)infotbl.get(invname);
				info.dereference();
			}
			infotbl.clear();
		}
		
		invGrpInvnameTbl.clear();
		invGrpInvnameTbl = null;
		
		dbgPw=null;
		dbgfn = null;
		updatedSB = null;
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
		return "BHINVNAMABRSTATUS";
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

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
	/**********************************
	 * stringbuffer used for report output
	 */
	private void addHeading(int level, String msg) { rptSb.append("<h"+level+">"+msg+"</h"+level+">"+NEWLINE);}

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

		//ERROR_PREFIX = Error:  reduce size of output, do not prepend root info
		addMessage(rsBundle.getString("ERROR_PREFIX"), errCode, args);
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

		return navName.toString().trim();
	}

	/***********************************************
	 * Sets the specified Text Attribute on the specified entity
	 *
	 * @param _sAttributeCode
	 * @param _sAttributeValue
	 * @param einf
	 */
	private void setTextValue(String _sAttributeCode, String _sAttributeValue,	EntityInfo einf)
	{
		//addDebug("setTextValue entered for "+einf.entityType+einf.entityid+" "+_sAttributeCode+" set to: " + _sAttributeValue);

		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		Vector vctAtts = null;
		// look at each key to see if this item is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == einf.entityid &&
					rek.getEntityType().equals(einf.entityType)){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey(einf.entityType, einf.entityid, true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}

		COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(m_prof.getEnterprise(),
				einf.entityType, einf.entityid, _sAttributeCode, _sAttributeValue, 1, m_cbOn);
		vctAtts.addElement(sf);
	}
	/***********************************************
	 *  Sets the status and dq to chg req
	 *
	 * @param einf
	 */
	private void setStatusAndDQ(EntityInfo einf)
	{
		addDebug(D.EBUG_SPEW,"setStatusAndDQ for "+einf.entityType+einf.entityid);

		if (m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		Vector vctAtts = null;
		// look at each key to see if root is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == einf.entityid &&
					rek.getEntityType().equals(einf.entityType)){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey(einf.entityType,einf.entityid , true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}

		// update status
		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), einf.entityType, einf.entityid ,
				"STATUS", STATUS_CHGREQ, 1, m_cbOn);

		vctAtts.addElement(sf);

		// update dq
		sf = new SingleFlag (m_prof.getEnterprise(), einf.entityType, einf.entityid ,
				"DATAQUALITY", DQ_CHGREQ, 1, m_cbOn);

		vctAtts.addElement(sf);
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 */
	private void updatePDH()
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug(D.EBUG_SPEW,"updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys.size());

		if(vctReturnsEntityKeys.size()>0) {
			MessageFormat msgf;
			try {
				m_db.update(m_prof, vctReturnsEntityKeys, false, false);

				try{  
					// output a message for each thing set
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
							Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
							String attrCode = attr.getAttributeCode();
							if(attrCode.equals("BHINVNAME") && attr.getAttributeValue().length()<=MAX_LEN){
								//ATTR_SET = {0} was set to {1} for {2} {3}
								msgf = new MessageFormat(rsBundle.getString("ATTR_SET"));
								args[0] = PokUtils.getAttributeDescription(featureGroup, attrCode, attrCode);
								args[1] = attr.getAttributeValue();
								args[2] = featureGroup.getLongDescription();
								args[3] = rek.getEntityType()+rek.getEntityID();

								//addOutput(msgf.format(args));
								updatedSB.append("<p>"+msgf.format(args)+"</p>"+NEWLINE); // group all updates
			 					break; // dont output status and dq updates
							}
						}// end all rek attributes
					}
				}catch(Exception exc){
					exc.printStackTrace();
					addDebug("exception trying to output msg "+exc);
				}
			}
			finally {
				vctReturnsEntityKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}

	/**********************************
	 * get last timestamp for specified attribute
	 */
	private String getTimestamp(EntityItem theItem, String attrCode) throws Exception
	{
		RowSelectableTable itemTable = //theItem.getEntityItemTable(); cant use this because
			// prodstruct can edit parent model and the model isnt in this small extract, so bypass item chks
			new RowSelectableTable(theItem, theItem.getLongDescription());

		String dts = "";
		String keyStr = theItem.getEntityType() + ":" + attrCode;
		int row = itemTable.getRowIndex(keyStr);
		if (row < 0) {
			row = itemTable.getRowIndex(keyStr + ":C");
		}
		if (row < 0) {
			row = itemTable.getRowIndex(keyStr + ":R");
		}
		if (row != -1)
		{
			EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
			if (attStatus != null)
			{	
				AttributeChangeHistoryGroup ahistGrp = m_db.getAttributeChangeHistoryGroup(m_prof, attStatus);
				/*  int cnt = 0;
			  	rptSb.append("<!--"+theItem.getKey()+" "+attrCode+" change history ");
				for (int ci= ahistGrp.getChangeHistoryItemCount()-1; ci>=0; ci--) // go from most recent to earliest
				{
					ChangeHistoryItem chi = ahistGrp.getChangeHistoryItem(ci);
					rptSb.append(NEWLINE+"AttrChangeHistoryItem["+ci+"] chgDate: "+chi.getChangeDate()+
							" value: "+
							chi.get("ATTVAL",true).toString()+
							" flagcode: "+chi.getFlagCode()+
							" user: "+chi.getUser());
					cnt++;
					if (cnt>3) {  // just last 3 is enough to look at
						break;
					}
				} // each history item
				rptSb.append(" -->"+NEWLINE);*/
				if (ahistGrp.getChangeHistoryItemCount()>0){
					int last = ahistGrp.getChangeHistoryItemCount()-1;
					dts = ahistGrp.getChangeHistoryItem(last).getChangeDate();
				}
			}
			else {
				addDebug(D.EBUG_SPEW,"EANAttribute was null for "+attrCode+" in "+theItem.getKey());
			}
		}
		else {
			addDebug("Row for "+attrCode+" was not found for "+theItem.getKey());
		}

		return dts;
	}
	/********************************************************************************
	 * this is used to find FEATURE with matching PDHDOMAIN
	 * extract is used for SWFEATUREs
	 * 
	 * @param pdhdomain
	 * @return
	 * @throws java.sql.SQLException
	 */
	private Vector getHWEntities(String pdhdomain)
	throws java.sql.SQLException
	{
		ResultSet result=null;
		Vector vct = new Vector();
		String enterprise = m_prof.getEnterprise();

		try{
			bhinvnameStatement.clearParameters();
			bhinvnameStatement.setString(1, enterprise);
			bhinvnameStatement.setString(2, enterprise);
			bhinvnameStatement.setString(3, enterprise);
			bhinvnameStatement.setString(4, pdhdomain);
			bhinvnameStatement.setString(5, enterprise);
			bhinvnameStatement.setString(6, enterprise);
			bhinvnameStatement.setString(7, enterprise);
			bhinvnameStatement.setString(8, enterprise);
			bhinvnameStatement.setString(9, pdhdomain);
			bhinvnameStatement.setString(10, enterprise);
			bhinvnameStatement.setString(11, enterprise);
			bhinvnameStatement.setString(12, enterprise);
			bhinvnameStatement.setString(13, enterprise);

			result = bhinvnameStatement.executeQuery();
			while(result.next()) {					
				int eid = result.getInt(1);
				String type = result.getString(2);
				String invname = result.getString(3);
				String invgrp = result.getString(4);
				String fcode = result.getString(5);
				String status = result.getString(6);
				if(invname==null || invgrp==null){
					addDebug(D.EBUG_SPEW,"getEntities: skipping "+type+eid+" with invname "+invname+" invgrp "+invgrp+" fcode "+fcode);
					continue;
				}
				EntityInfo einf = new EntityInfo(eid,//int eid, 
						type,//String etype, 
						invname,//String invnm, 
						invgrp,//String invgrp, 
						fcode, //String fc
						status);//String status
				vct.add(einf);	
				//addDebug("getEntities: Query found "+einf);
			}
		}
		catch(SQLException t) {
			throw(t);
		}
		finally{
			if (result!=null){
				result.close();
				result=null;
			}
			m_db.commit();
		    m_db.freeStatement();
		    m_db.isPending();
		}
		return vct;
	}	

	/********************************************************************************
	 * close the preparedstatements
	 */
	private void closeStatements() 
	{
		try {
			if (invgrpPs !=null) {
				invgrpPs.close();
				invgrpPs=null;
			}
		}catch(Exception e){
			System.err.println("closeStatements(), unable to close invgrpPs. "+ e);
		}
		try {
			if (bhinvnameStatement!=null) {
				bhinvnameStatement.close();
				bhinvnameStatement=null;
			}
		}catch(Exception e){
			System.err.println("closeStatements(), unable to close bhinvnameStatement. "+ e);
		}
	}

	/***********
	 * this class holds the entity id, INVNAME, INVENTORYGROUP and FEATURECODE returned from the queries
	 *
	 */
	private class EntityInfo{
		private String invname = null;
		private int entityid = 0;
		private String entityType=null;
		private String invGrp = null;
		private String fcode = null;
		private String status = null;

		EntityInfo(EntityItem swfcitem){
			invname = PokUtils.getAttributeValue(swfcitem, "INVNAME", "", null, false);
			entityid = swfcitem.getEntityID();
			entityType = swfcitem.getEntityType();
			invGrp = PokUtils.getAttributeFlagValue(swfcitem, "INVENTORYGROUP");
			fcode = PokUtils.getAttributeValue(swfcitem, "FEATURECODE", "", null, false);
			status = PokUtils.getAttributeFlagValue(swfcitem, "STATUS");
		}
		EntityInfo(int eid, String etype, String invnm, String invgrp, String fc, String st){ 
			invname = invnm;
			entityid = eid;
			entityType = etype;
			invGrp = invgrp;
			fcode = fc;
			status = st;
		}

		public String toString(){
			return entityType+entityid+" fcode "+fcode+" invGrp "+invGrp+" invname "+invname+" status "+status;
		}
		void dereference(){
			// release memory
			invname = null;
			invGrp = null;
			fcode = null;
			entityType=null;
			status = null;
		}
	}
	/***********
	 * this class holds the entity id, featurecode,INVNAME from query by invgrp
	 *
	 */
	private class InvnameInfo{
		private String entityids = "";
		private String fcodes = "";
		private int count = 0;

		InvnameInfo(int eid, String fc){ 
			addId(eid, fc);
		}
		void addId(int eid, String fc){ 
			entityids += " "+eid;
			fcodes += " "+fc;
			count++;
		}

		public String toString(){
			return "entityids: "+entityids+" fcodes: "+fcodes;
		}
		void dereference(){
			// release memory
			entityids = null;
			fcodes = null;
		}
	}
}
