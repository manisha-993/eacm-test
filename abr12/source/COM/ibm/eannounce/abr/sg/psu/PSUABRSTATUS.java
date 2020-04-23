//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.psu;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

/****
 * BH FS ABR PDH Update 20130225.doc
 *
 * V.	Design Considerations
 * Middleware will be used to make all updates to the PDH. Middleware will also be used to obtain data 
 * from the PDH and there will be a need to access an Operational Data Store (ODS - price schema).
 * 
 * It is also necessary to provide a method of limiting the amount of churn (e.g. XML sent downstream) created 
 * for a single invocation of the ABR. Chunking updates (e.g. 1,000 limit per invocation) will be used to 
 * control this by specifying the maximum number of updates per invocation.
 * 
 * VI.	SETUP Data

A new entity type "PDH Update" (PDHUPDATE) will be used to define the requirements of the update 
and to track the new ABR. Another new entity type "PDH Update Action" (PDHUPDATEACT), a child of 
"PDH Update" will specify update actions.

“PDH Update” (PDHUPDATE)

AttributeCode	Type	LongDescription
PSUDESCRIPTION	T	Description of Update
CAT2			F	Subscription / Notification CAT2
PSUDBTYPE		U	Database Type
PSUCRITERIA		U	Criteria
PSUVIEW			T	DB2 VIEW
PSULAST			T	Last Processed
PSUMAX			T	Maximum # per Pass
PSUHIGHENTITYTYPE	T	High Entity Type Processed
PSUHIGHENTITYID	T	High Entity ID Processed
PSUPROGRESS		U	The status of this request
PSUABRSTATUS	A	PDH Update ABR
PDHDOMAIN		F	Domains

The attributes are used as follows:
1.	PSUDESCRIPTION – used only by the user as a reminder of the purpose of this setup data.
2.	CAT2 – if specified in PDHUPDATE, then it is used as the value for CAT2 in subscription / notification.
3.	PSUDBTYPE – type “U”, Required (EXIST), values = “PDH” | “ODS”
4.	PSUCRITERIA – Required - indicates the form of the criteria (children)
•	LIST – the children specify everything required to define the update. There may be one more children of entity type “PDH Update Action” (PDHUPDATEACT). The list will support PSUCLASS IN {Update | Reference}.
•	VIEW – there may be one or more children of entity type “PDH Update Action” (PDHUPDATEACT). There will be exactly one where PSUCLASS IN {Update | Reference}. In this case, the values are the column names that have the values. 
5.	PSUVIEW – applicable only if PSUCRITERIA = VIEW. It is the name of the DB2 VIEW including the name of the schema.
e.g. schema.nameofview
6.	PSULAST – Integer: the default value is zero. It is updated by this ABR to be the number of root entities updated (i.e. PSUENTITYTYPE in “PDH Update Action” (PDHUPDATEACT). Each pass will add to this value. For example (1650 updates required), if the first invocation is limited to 1,000 then at the completion of the 1,000 entity updates, this is set to 1,000. The second invocation would update 650 (the rest) and the ABR would add this to the current value giving a new value of 1,650.
7.	PSUMAX – Integer: is the maximum number of PSUENTITYTYPEs updated in a single invocation of this ABR. If empty, then there is not limit (Maximum).
8.	PSUHIGHENTITYTYPE – This is the last entity type processed.
9.	PSUHIGHENTITYID – Integer saved as TEXT: this is the last entity id processed
10.	PSUPROGRESS – indicates the status or progress of this request.
•	Not Started – default value - the ABR has never ran
•	Chunking – the ABR ran but did not process all requests due to PSUMAX limit
•	Complete – the ABR completed all requests specified by the VIEW (or LIST)
11.	PSUABRSTATUS – defaults to “Untried” (0010). It is set via a Workflow action to “Queued” (0020). Taskmaster and the ABR update this attribute to reflect the state of the ABR.
12.	PDHDOMAIN – used for instance based security


“PDH Update Action” (PDHUPDATEACT)

AttributeCode	Type	LongDescription		Applicable
PSUCLASS		U	Update Class			A		
PSUENTITYTYPE	T	Entity Type				U R D
PSUENTITYID		T	Entity ID				U R D
PSUATTRIBUTE	T	Update Attribute Code	U R
PSUATTRTYPE		T	Attribute Type			U R
PSUATTRACTION	T	Attribute Update Action	U R D
PSUATTRVALUE	T	Update Attribute Value	U R
PSUENTITYTYPEREF	T	Entity Type Referenced	R
PSUENTITYIDREF	T	Entity ID Referenced	R
PSURELATORTYPE	T	Relator Type			R
PSURELATORACTION	T	Relator Action		R D
PDHDOMAIN		F	Domains					A

Applicable:
•	A : Always
•	U : when PSUCLASS = Update
•	R : when PSUCLASS = Reference and PSUATTRACTION = N
•	D : when PSUCLASS = Reference and PSUATTRACTION = D

The attributes are used as follows:
1.	PSUCLASS
•	Update – a DB2 view is used to apply the criteria and identify the data that needs to be updated
•	Reference - a DB2 view is used to apply the criteria and identify the data that needs to be related 
•	Other values (function) will be specified (developed) over time.

Note: 
The following columns are based on the value of PSUCRITERIA.
•	LIST – the contents specify the value.
•	VIEW – the contents are the name of the column in the view that specifies the value.

2.	PSUENTITYTYPE – The PDH Entity Type that will be updated or the relator entitytype being deactivated. 
3.	PSUENTITYID – The PDH Entity ID that will be updated or the relator id being deactivated.
4.	PSUATTRIBUTE – The PDH Attribute Code that will be updated.
5.	PSUATTRTYPE – The attribute type being updated
•	T – Text
•	U – Unique Flag
•	F – Multi-Value Flag
•	A – ABR Flag
•	S – Status Flag
or column name
6.	PSUATTRACTION – The requested action
•	N – add this New value
•	D - Deactivate this value
or column name
7.	PSUATTRVALUE – The PDH Attribute Value that will be used for the update. 
If PSUATTRACTION = D, then this column is not applicable.
•	For PSUATTRTYPE “T”, this is a text string
•	For all other types, it is text specifying the Description Class (Flag Code) for the FLAG.
8.	PSUENTITYTYPEREF – The target entity type of the reference (relator) being created. 
9.	PSURELATORENTITYID - The entityid of entity being referenced.
10.	PSURELATORTYPE – The Relator Entity Type used to create the reference.
12.	PSURELATORACTION – This is the name of the action defined in the PDH via Meta Data to create or deactivate the relator. 
13.	PDHDOMAIN – used for instance based security


VIII.	User Interface
The Java User Interface will be updated via meta data for the ROLE = SYSFEEDADMIN and available in the 
WORKGROUP = ISG.
An authorized user will be able to create the setup data and queue the ABR.
Development needs to create the DB2 VIEW based on the data required to be updated prior to the ABR being invoked.
There is a 1 hour delay before the ODS is updated for the SETUP Entity and the updated data. It is recommended that 
the user wait 2 hours after the data is setup and / between invocations of the ABR (chunking).

The subscription information for the Reports will be:
CAT1= PDHUPDATE
CAT2= CAT2 from PDHUPDATE if specified
CAT3= TASKSTATUS
SUBSCRVE= PDHABR

PSUABRSTATUS_class=COM.ibm.eannounce.abr.sg.psu.PSUABRSTATUS
PSUABRSTATUS_enabled=true
PSUABRSTATUS_idler_class=D
PSUABRSTATUS_keepfile=true
PSUABRSTATUS_read_only=false
PSUABRSTATUS_vename=EXTPSUVE
PSUABRSTATUS_debugLevel=0
PSUABRSTATUS_CAT1=PDHUPDATE
PSUABRSTATUS_CAT3=TASKSTATUS
PSUABRSTATUS_SUBSCRVE=PDHABR
 */
//$Log: PSUABRSTATUS.java,v $
//Revision 1.2  2014/01/13 13:53:48  wendy
//migration to V17
//
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
public class PSUABRSTATUS extends PokBaseABR {
	private static final int MAXFILE_SIZE=5000000;
	private static final char[] FOOL_JTEST = {'\n'};

	static final int UPDATE_SIZE =
		Integer.parseInt(COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("PSUABRSTATUS" ,
				"_UpdateSize","200"));

	static final String UPDATE_CLASS = "Update Class";
	static final String UPDATE_ENTITYTYPE = "Entity Type";
	static final String UPDATE_ENTITYID = "Entity ID";
	static final String UPDATE_ATTRCODE = "Update Attribute Code";
	static final String UPDATE_ATTRTYPE = "Attribute Type";
	static final String UPDATE_ATTRACT = "Attribute Update Action";
	static final String UPDATE_ATTRVAL = "Update Attribute Value";
	static final String UPDATE_REF_ENTITYTYPE = "Entity Type Referenced";
	static final String UPDATE_REF_ENTITYID = "Entity ID Referenced";
	static final String UPDATE_RELTYPE = "Relator";
	static final String UPDATE_RELACT = "Relator Action";


	//PSUPROGRESS	PSUPRG1		Not Started 
	//PSUPROGRESS	PSUPRG2		Chunking
	//PSUPROGRESS	PSUPRG3		Complete
	private static final String PSUPROGRESS_Chunking = "PSUPRG2";
	private static final String PSUPROGRESS_Complete = "PSUPRG3";

	//PSUCRITERIA	LIST		List
	//PSUCRITERIA	VIEW		View
	private static final String PSUCRITERIA_View = "VIEW";
	private static final String PSUCRITERIA_List = "LIST";

	//PSUCLASS	PSUC1		Update
	//PSUCLASS	PSUC2		Reference
	static final String PSUCLASS_Reference = "PSUC2";
	static final String PSUCLASS_Update = "PSUC1";

	static final String PSUATTRACTION_N = "N";  //add this New value or create relator
	static final String PSUATTRACTION_D = "D";  //Deactivate this value or relator

	private static final String[] PDHUPDATE_ATTRS = new String[]{
		"PSUDESCRIPTION","CAT2","PSUCRITERIA","PSUVIEW","PSUDBTYPE",
		"PSULAST","PSUMAX",	"PSUHIGHENTITYTYPE",
		"PSUHIGHENTITYID","PSUPROGRESS","PDHDOMAIN"};


	static final String NEWLINE = new String(FOOL_JTEST);

	private StringBuffer rptSb = new StringBuffer();
	private Object[] args = new String[10];

	private ResourceBundle rsBundle = null;
	private Hashtable navMetaTbl = new Hashtable();
	private StringBuffer updatedSB = new StringBuffer();
	private String navName = "";
	private String cat2 = null;
	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private String psucriteria ="";
	private int dbgLen=0;
	private int abr_debuglvl=D.EBUG_ERR;
	private ControlBlock cbOff = null;
	private Vector vctReturnsEntityKeys = new Vector();
	private Hashtable m_typeTbl = new Hashtable();// need attr type for user msg and comparator
	private Hashtable actionItemTbl = null;
	private String currentPSUHIGHENTITYTYPE; // dont set same value over and over
	private String currentPSUPROGRESS;
	private int currentPSUHIGHENTITYID;
	private Profile poweruserProf = null;
	private Hashtable updatedRootTbl = new Hashtable();
	private ExtractActionItem xai = null;
	private int psuLast = 0;
	private AttrComparator attrComp = new AttrComparator();

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
	 *  
	 *  VII.	Processor
	 *  The ABR is invoked for the root entity "PDH Update" (PDHUPDATE). The ABR will obtain the children 
	 *  "PDH Update Action" (PDHUPDATEACT) via an extract action.
	 *  
	 *  The Select from the DB2 VIEW will need to be sorted (ordered by) in ascending order by Entity Type and 
	 *  Entity ID. 
	 *  
	 *  The LIST will be sorted (ordered by) in ascending order by Entity Type and Entity ID.
	 *  
	 *  The "Maximum # per Pass" (PSUMAX) specifies the maximum number of entities (Entity Type / Entity ID) 
	 *  that should be updated per invocation of this ABR. Note that this is not the number of rows since the 
	 *  same Entity type / Entity ID) may have multiple rows (updates).
	 *  
	 *  Since there may be multiple instances of "PDH Update Action" (PDHUPDATEACT) for the same entity, all 
	 *  instances should be processed together. In other words, all updates to a specific Entity Type Entity ID 
	 *  will be made in a single pass. 
	 *  
	 *  The first time this ABR runs, it will set PSUPROGRESS to "Chunking". However, if all rows in the VIEW 
	 *  are complete, then PSUPROGRESS is set to "Complete". If at startup, this value is "Complete", then the 
	 *  ABR should not run.
	 *  
	 *  The ABR should count the number of Entity Type / Entity IDs (instances of the entity type) updated and 
	 *  add this to the initial PSULAST value when the ABR has finished processing.
	 *  
	 *  The ABR should update PSUHIGHENTITYTYPE and PSUHIGHENTITYID for the last instance of the entity processed. 
	 *  For subsequent invocations, the ABR will start with the next row in the LIST or VIEW.
	 *  
	 *  The ABR will always add relators. If duplicate relators are not desired, the LIST and VIEW must ensure 
	 *  that duplicates are not requested
	 *  
	 */
	public void execute_run()
	{
		/*
		 The Report should contain the following information:
			"PDH Update" (PDHUPDATE)
			For all attributes
			-	Long Description
			-	Attribute Value(s)

			A list of the updates:
			Entity Type
			Entity ID
			Update Attribute Code
			Attribute Type
			Attribute Update Action
			Update Attribute Value
			Entity Type Referenced
			Entity ID Referenced
			Relator Type
			Relator Action
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
		EntityItem rootEntity=null;

		println(EACustom.getDocTypeHtml()); //Output the doctype and html

		try	{
			long startTime = System.currentTimeMillis();
			start_ABRBuild(); // pull VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

			setupPrintWriter();
			// force nlsid=1
			m_prof.setReadLanguage(Profile.ENGLISH_LANGUAGE);

			//get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			// get root from VE
			rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			// debug display list of groups
			addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " +rootEntity.getKey()+
					" extract: "+m_abri.getVEName()+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			//NAME is navigate attributes
			navName = getNavigationName(rootEntity);
			//2.	CAT2 – if specified in PDHUPDATE, then it is used as the value for CAT2 in subscription / notification.
			cat2 = PokUtils.getAttributeValue(rootEntity, "CAT2", ",", null, false);

			psuLast = Integer.parseInt(PokUtils.getAttributeValue(rootEntity, "PSULAST", "", "0", false));
			
			//Default set to pass
			setReturnCode(PASS);

//			FIXME remove this.. avoid msgs to userid for testing
//			setCreateDGEntity(false);

			currentPSUHIGHENTITYTYPE = PokUtils.getAttributeValue(rootEntity, "PSUHIGHENTITYTYPE", "", null, false);
			currentPSUPROGRESS = PokUtils.getAttributeFlagValue(rootEntity, "PSUPROGRESS");
			currentPSUHIGHENTITYID = Integer.parseInt(PokUtils.getAttributeValue(rootEntity, "PSUHIGHENTITYID", "", 
					"-1", false));

			addDebug(rootEntity.getKey()+" PSUPROGRESS: "+currentPSUPROGRESS+
					" PSUHIGHENTITYTYPE: "+currentPSUHIGHENTITYTYPE+
					" PSUHIGHENTITYID: "+currentPSUHIGHENTITYID+" CAT2: "+cat2+" psuLast "+psuLast);

			if(PSUPROGRESS_Complete.equals(currentPSUPROGRESS)){
				//If at startup, this value is "Complete", then the  ABR should not run.
				//COMPLETED_ERR = Nothing to do. {0}
				args[0] = getLD_Value(rootEntity, "PSUPROGRESS");
				addMessage("","COMPLETED_ERR",args);
			}else {
				// is this getting updates from view or list?
				psucriteria = PokUtils.getAttributeFlagValue(rootEntity, "PSUCRITERIA");
				addDebug(rootEntity.getKey()+" PSUCRITERIA: "+psucriteria);
				if(psucriteria==null){
					//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
					args[0] = rootEntity.getEntityGroup().getLongDescription();
					args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "PSUCRITERIA", "PSUCRITERIA");
					addError("REQ_NOTPOPULATED_ERR",args);
				} else{ 
					// is this getting updates from view or list
					if(PSUCRITERIA_View.equals(psucriteria)){
						// get updates from view
						PSUView psuView = new PSUView(this,rootEntity);
						psuView.execute(m_elist.getEntityGroup("PDHUPDATEACT"));
						psuView.dereference();
					}else if(PSUCRITERIA_List.equals(psucriteria)){
						// get updates from list
						PSUList psuList = new PSUList(this,rootEntity);
						psuList.execute(m_elist.getEntityGroup("PDHUPDATEACT"));
						psuList.dereference();
					}else{
						//CRITERIA_NOTSUPP_ERR = {0} is not supported.
						args[0] = getLD_Value(rootEntity, "PSUCRITERIA");
						addError("CRITERIA_NOTSUPP_ERR",args);
					}
				}
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

			rptSb.append(getAllUpdateInfo()); // put all updates before err msgs

			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
		}
		finally	{
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			//2.	CAT2 – if specified in PDHUPDATE, then it is used as the value for CAT2 in subscription / notification.
			if(cat2 !=null) {
				// CAT2 is multiflag, parse it now
				StringTokenizer st = new StringTokenizer(cat2,",");
				String []cat2Array = new String[st.countTokens()];
				int cnt=0;
				while(st.hasMoreTokens()){
					cat2Array[cnt] = st.nextToken();
				}
				setDGCat2(cat2Array);
			}
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

		rptSb.append(getAllUpdateInfo()); // put all updates after err msgs

		restoreXtraContent();

		rptSb.insert(0, header1+msgf.format(args) + NEWLINE+getRequestInfo(rootEntity));

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		navMetaTbl.clear();
	}
	/**
	 * output the root entity info 
	 * "PDH Update" (PDHUPDATE)
	 * For all attributes
	 * -	Long Description
	 * -	Attribute Value(s)
	 * @param rootEntity
	 */
	private String getRequestInfo(EntityItem rootEntity) {
		if(rootEntity==null){
			return "";
		}
		StringBuffer sb = new StringBuffer("<table width='600'>"+NEWLINE);
		sb.append("<tr><th>Attribute</th><th>Entry Value</th><th>Exit Value</th></tr>"+NEWLINE);
		sb.append("<tr><td colspan='3'><b>"+rootEntity.getEntityGroup().getLongDescription()+": "+
				navName+"</b></td></tr>"+NEWLINE);
		// add attributes
		for (int i=0;i<PDHUPDATE_ATTRS.length;i++){
			String attrcode = PDHUPDATE_ATTRS[i];
			String b4Value = PokUtils.getAttributeValue(rootEntity, attrcode, ", ", PokUtils.DEFNOTPOPULATED);
			sb.append("<tr><td>"+
					PokUtils.getAttributeDescription(rootEntity.getEntityGroup(),attrcode , attrcode)+
					": </td><td>"+b4Value+"</td><td>"+getExitValue(b4Value,attrcode)+
					"</td></tr>"+NEWLINE);
		}

		sb.append("</table><br />"+NEWLINE);

		return sb.toString();
	}

	/**
	 * add one row for each set of updates
	 * A list of the updates:
Entity Type
Entity ID
Update Attribute Code
Attribute Type
Attribute Update Action
Update Attribute Value
Entity Type Referenced
Entity ID Referenced
Relator Type
Relator Action
	 * @param item
	 */
	void addUpdateInfo(Hashtable infoTbl){
		// append a row for each update
		updatedSB.append("<tr><td>"+getUpdateValue(infoTbl,UPDATE_CLASS)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_ENTITYTYPE)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_ENTITYID)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_ATTRCODE)+"</td>");
		updatedSB.append("<td>"+getUpdateValue(infoTbl,UPDATE_ATTRTYPE)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_ATTRACT)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_ATTRVAL)+"</td>");
		updatedSB.append("<td>"+getUpdateValue(infoTbl,UPDATE_REF_ENTITYTYPE)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_REF_ENTITYID)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_RELTYPE)+
				"</td><td>"+getUpdateValue(infoTbl,UPDATE_RELACT)+"</td></tr>"+NEWLINE);
		infoTbl.clear();
	}
	/**
	 * get value for key, if null, return nbsp
	 * @param infoTbl
	 * @param key
	 * @return
	 */
	private String getUpdateValue(Hashtable infoTbl, String key){
		String val = "&nbsp;";
		if(infoTbl.containsKey(key)){
			String value = infoTbl.get(key).toString();
			if(value.trim().length()>0){
				val = value;
			}  
		}

		return val;
	}
	/**
	 * get value for attr, if null, return root value
	 * @param defvalue
	 * @param attrcode
	 * @return
	 */
	private String getExitValue(String defvalue,String attrcode){
		String val = defvalue;
		if(updatedRootTbl.containsKey(attrcode)){
			String value = updatedRootTbl.get(attrcode).toString();
			if(value.trim().length()>0){
				val = value;
			}  
		}

		return val;
	}
	/**
	 * get all updates made in this execution
	 * @return
	 */
	private String getAllUpdateInfo(){
		StringBuffer sb = new StringBuffer();
		if(updatedSB.length()>0){
			// build table
			sb.append("<table><tr><th>"+UPDATE_CLASS+"</th><th>"+UPDATE_ENTITYTYPE+"</th><th>"+UPDATE_ENTITYID+"</th><th>"+UPDATE_ATTRCODE+"</th>");
			sb.append("<th>"+UPDATE_ATTRTYPE+"</th><th>"+UPDATE_ATTRACT+"</th><th>"+UPDATE_ATTRVAL+"</th>");
			sb.append("<th>"+UPDATE_REF_ENTITYTYPE+"</th><th>"+UPDATE_REF_ENTITYID+"</th><th>"+UPDATE_RELTYPE+"</th><th>"+UPDATE_RELACT+"</th></tr>"+NEWLINE);
			// append a row for each update
			sb.append(updatedSB.toString());
			//end table
			sb.append("</table>"+NEWLINE);
			updatedSB.setLength(0);
		}

		return sb.toString();
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
	 * do the actual updates in the pdh
	 * @param rootEntity
	 * @param allProcessed
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws RemoteException 
	 * @throws WorkflowException 
	 * @throws LockException 
	 */
	void doUpdates(EntityItem rootEntity, OPICMList psuUpdateList, boolean allProcessed) throws RemoteException, SQLException, MiddlewareException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException
	{
		int delRelatorCnt =0; // deactivate relators of the same type are grouped together, keep total count

		for (int i=0; i<psuUpdateList.size(); i++){
			PSUUpdateData psuData = (PSUUpdateData)psuUpdateList.getAt(i);
			addDebug(D.EBUG_DETAIL,"doUpdates["+i+"]: "+psuData.hashkey());

			if(psuData instanceof PSUDeleteData){
				delRelatorCnt += deleteData(((PSUDeleteData)psuData));
				psuData.outputUserInfo();
			}else if(psuData instanceof PSULinkData){
				psuData.outputUserInfo(); // output first, newly created relator replaces child so attr can get updated
				createLinks(((PSULinkData)psuData));
			}else{
				psuData.outputUserInfo();
				psuData.removeAttrs();  // user info was output, remove invalid attrs now
				if (psuData.rek.m_vctAttributes!=null && psuData.rek.m_vctAttributes.size()>0){
					// sort the attributes so that S and A type are last
					Collections.sort(psuData.rek.m_vctAttributes, attrComp);
					
					// update attributes
					vctReturnsEntityKeys.addElement(psuData.rek);
				}
			}
		}

		String highType=null;
		int highId = 0;
		if(psuUpdateList.size()>0){
			PSUUpdateData psuData = (PSUUpdateData)psuUpdateList.getAt(psuUpdateList.size()-1);
			highType =psuData.getEntityType();
			highId = psuData.getHighEntityId();
		}
		// update "PSUHIGHENTITYTYPE" with last processed type
		// update "PSUHIGHENTITYID" with last processed id
		saveHighEntity(rootEntity,highType,highId,
				allProcessed,psuUpdateList.size()+delRelatorCnt);

		updatePDH();

		// these have been handled, remove them
		while(psuUpdateList.size()>0){
			PSUUpdateData psu = (PSUUpdateData)psuUpdateList.remove(0);
			psu.dereference();
		}
	}

	/**
	 * link parent to children and set any attributes
	 * @param psulink
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private void createLinks(PSULinkData psulink) 
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException 	
	{
		addDebug(D.EBUG_SPEW,"createLinks: entered psulink: "+psulink);

		if(actionItemTbl==null){
			actionItemTbl = new Hashtable();
		}
		EntityItem parentArray[] = new EntityItem[]{
				new EntityItem(null, m_prof, psulink.getEntityType(),psulink.getEntityId())};

		// must loop and look at all sets of children, each set is the same entitytype and linkaction
		for(int y=0;y<psulink.getChildrenList().size();y++){
			PSUChildList psulist = (PSUChildList)psulink.getChildrenList().getAt(y);
			LinkActionItem lai = (LinkActionItem)actionItemTbl.get(psulist.getActionName());
			if (lai ==null){
				lai = new LinkActionItem(null, m_db,m_prof,psulist.getActionName());
				if(lai.getMetaLink()==null){
					// this is an invalid link action, meta may be missing
					throw new MiddlewareException("Linkaction "+psulist.getActionName()+" is undefined.");
				}
				actionItemTbl.put(psulist.getActionName(), lai);
			}

			EntityItem childArray[] = new EntityItem[psulist.getChildrenList().size()];

			// create a new relator
			for(int x=0;x<psulist.getChildrenList().size();x++){
				PSUUpdateData psuchild = (PSUUpdateData)psulist.getChildrenList().getAt(x);
				childArray[x] = new EntityItem(null, m_prof, psuchild.getEntityType(),psuchild.getEntityId());
				addDebug(D.EBUG_SPEW,"createLinks: psuchild: "+psuchild+
						" psuRelatorAct: "+psulist.getActionName());
			}

			// do the link	
			if(lai.isOppSelect()){
				lai.setParentEntityItems(childArray);
				lai.setChildEntityItems(parentArray);
			}else{
				lai.setParentEntityItems(parentArray);     
				lai.setChildEntityItems(childArray);
			}
			Vector result = lai.executeLink(m_db, m_prof);
			OPICMList resultlist = (OPICMList)result.elementAt(1);
			for(int i=0; i<resultlist.size(); i++){
				ReturnRelatorKey rrk = (ReturnRelatorKey)resultlist.getAt(i);
				addDebug(D.EBUG_SPEW,"createLinks:  ReturnRelatorKey["+i+"]: "+rrk);
				String childkey = rrk.m_strEntity2Type+rrk.m_iEntity2ID;
				if(lai.isOppSelect()){
					childkey = rrk.m_strEntity1Type+rrk.m_iEntity1ID;
				}
				PSUUpdateData psuchild = (PSUUpdateData)psulist.getChildrenList().get(childkey);
				if(psuchild.rek !=null && psuchild.rek.m_vctAttributes!=null){
					// make the new relator the owner of the REK and its attrs
					psuchild.rek.m_iEntityID = rrk.getReturnID();
					psuchild.rek.m_strEntityType = rrk.getEntityType();
					psuchild.setRelatorKey(rrk.getEntityType()+rrk.getReturnID());
					for(int a=0;a<psuchild.rek.m_vctAttributes.size();a++){
						Attribute attr = (Attribute)psuchild.rek.m_vctAttributes.elementAt(a);
						attr.m_iEntityID = rrk.getReturnID();
						attr.m_strEntityType = rrk.getEntityType();
					}
					// sort the attributes so that S and A type are last
					if(psuchild.rek.m_vctAttributes.size()>0){
						Collections.sort(psuchild.rek.m_vctAttributes, attrComp);
					}
					addDebug(D.EBUG_SPEW,"createLinks: new relator: "+psuchild);
					this.vctReturnsEntityKeys.addElement(psuchild.rek);
				}
			}
		}
		psulink.outputUserInfoWithRelator();
	}

	/**
	 * deactivate the relators
	 * @param psudelete
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws LockException 
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws WorkflowException
	 * @throws RemoteException
	 */
	private int deleteData(PSUDeleteData psudelete) throws MiddlewareRequestException, 
	SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, 
	EANBusinessRuleException	
	{
		addDebug(D.EBUG_SPEW,"deleteData: entered psudelete: "+psudelete);

		if(actionItemTbl==null){
			actionItemTbl = new Hashtable();
		}
		DeleteActionItem dai = (DeleteActionItem)actionItemTbl.get(psudelete.actionName);
		if (dai ==null){
			dai = new DeleteActionItem(null, m_db,m_prof,psudelete.actionName);
			if(dai.getEntityType()==null){
				// this is an invalid delete action, meta may be missing
				throw new MiddlewareException("Deleteaction "+psudelete.actionName+" is undefined.");
			}

			actionItemTbl.put(psudelete.actionName, dai);
		}
		EntityItem eia[] = new EntityItem[psudelete.idVct.size()];
		for(int i=0; i<psudelete.idVct.size(); i++){
			eia[i]= new EntityItem(null, m_prof, psudelete.getEntityType(),	
					((Integer)psudelete.idVct.elementAt(i)).intValue());
		}

		try{
			dai.setEntityItems(eia);
			// deactivate relators
			dai.executeAction(m_db, m_prof);
		} finally {
			// do the commit here
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending("finally after deleteData");
		}

		return psudelete.idVct.size()-1; // minus 1 to account for this psudeletedata in the total count
	}

	/**
	 * save highentity id and type
	 * update PSUHIGHENTITYTYPE with last processed type
	 * update PSUHIGHENTITYID with last processed id
	 * update PSULAST with numProcessed
	 * update PSUPROGRESS with completed or chunking if not all children were processed
	 * @param root
	 * @param prevType
	 * @param prevId
	 * @param allProcessed
	 */
	private void saveHighEntity(EntityItem root,String prevType,int prevId, 
			boolean allProcessed, int numProcessed){
		addDebug(D.EBUG_DETAIL,"saveHighEntity: highEntityType: "+
				prevType+" highEntityId: "+prevId+" numProcessed: "+numProcessed+" allProcessed: "+allProcessed);

		if(m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}

		if(prevType != null && !prevType.equals(currentPSUHIGHENTITYTYPE)){
			setTextValue(root,"PSUHIGHENTITYTYPE",prevType);
			updatedRootTbl.put("PSUHIGHENTITYTYPE",prevType);
			
			currentPSUHIGHENTITYTYPE = prevType;
		}
		
		if(prevId>0){
			setTextValue(root,"PSUHIGHENTITYID",""+prevId);
			updatedRootTbl.put("PSUHIGHENTITYID",""+prevId);
		}

		// The first time this ABR runs, it will set PSUPROGRESS to "Chunking". However, if all rows in the VIEW 
		// are complete, then PSUPROGRESS is set to "Complete". 
		String progress = allProcessed?PSUPROGRESS_Complete:PSUPROGRESS_Chunking;
		if(!progress.equals(currentPSUPROGRESS)){
			setUniqueFlagValue(root, "PSUPROGRESS", progress);
			currentPSUPROGRESS = progress;
			updatedRootTbl.put("PSUPROGRESS",allProcessed?"Complete":"Chunking");
		}

		//PSULAST is updated by this ABR to be the number of root entities updated. 
		if(numProcessed>0){
			addDebug(D.EBUG_DETAIL,"saveHighEntity: before psuLast: "+psuLast);

			psuLast+=numProcessed;
			
			setTextValue(root,"PSULAST",""+psuLast);
			updatedRootTbl.put("PSULAST",""+psuLast);

		}
	}

	/**
	 * The ABR should update PSUHIGHENTITYTYPE and PSUHIGHENTITYID for the last instance of the entity processed. 
	 * For subsequent invocations, the ABR will start with the next row in the LIST or VIEW. Check to see if this
	 * type and id were already processed
	 * 
	 * @param psuEntityType
	 * @param psuEntityId
	 * @return
	 */
	boolean wasPreviouslyProcessed(String psuEntityType, int psuEntityId){
		if(currentPSUHIGHENTITYTYPE==null){
			return false;
		}

		// check to see if these types and ids were already processed in a previous execution of the abr
		if(psuEntityType.compareTo(currentPSUHIGHENTITYTYPE)<0){
			addDebug(D.EBUG_SPEW,"wasPreviouslyProcessed psuEntityType: "+psuEntityType+" is before highEntityType: "+currentPSUHIGHENTITYTYPE);
			return true;
		}
		if(psuEntityType.equals(currentPSUHIGHENTITYTYPE) && psuEntityId <=currentPSUHIGHENTITYID){
			addDebug(D.EBUG_SPEW,"wasPreviouslyProcessed psuEntityId: "+psuEntityId+" is before or equal highEntityId: "+currentPSUHIGHENTITYID);
			return true;
		}

		return false;
	}

	/**
	 * get attribute type
	 * @param attrcode
	 * @return
	 */
	String getAttrType(String attrcode){
		return (String)m_typeTbl.get(attrcode);
	}

	/**
	 * get the action for this attribute
	 * @param cb
	 * @return
	 */
	String getAttrAction(ControlBlock cb){
		return cb==m_cbOn?PSUATTRACTION_N:PSUATTRACTION_D;
	}
	
	/**
	 * get the current values for these entitys - only needed when an attribute will be deactivated, 
	 * otherwise the value will be specified, multiflag must specify the flag to turnoff
	 * @param needValuesList - all will be the same entitytype
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	void getCurrentValues(OPICMList needValuesList) throws MiddlewareRequestException, SQLException, MiddlewareException{
		
		Vector tmp = new Vector();

		Profile prof = m_prof;
		if(poweruserProf==null){
			getPowerUserRole();
		}
		if(poweruserProf!=null){
			prof = poweruserProf;
		}

		if(xai==null){
			xai = new ExtractActionItem(null, m_db, prof, "dummy"){//do once
				private static final long serialVersionUID = 1L;
				public String getPurpose() {
					return "Edit"; // hack to prevent getting allnls, only need nlsid=1
				}
			}; 
		}
		for (int x=0;x<needValuesList.size(); x++){
			PSUUpdateData psuUpdate = (PSUUpdateData)needValuesList.getAt(x);
			addDebug(D.EBUG_DETAIL,"getCurrentValues: needed["+x+"] "+psuUpdate);
			tmp.add(new EntityItem(null, prof, psuUpdate.getEntityType(),	psuUpdate.getEntityId()));
		}

		EntityItem[] eai = new EntityItem[tmp.size()];
		tmp.copyInto(eai);

		EntityList list = m_db.getEntityList(prof, xai, eai);
		EntityGroup eg = list.getParentEntityGroup();
		
		// now get the current values and put them into the attr to turn off
		for (int x=0;x<needValuesList.size(); x++){
			PSUUpdateData currData = (PSUUpdateData)needValuesList.getAt(x);
			EntityItem curItem = eg.getEntityItem(currData.getEntityType()+currData.getEntityId());

			for(int a=0; a<currData.rek.m_vctAttributes.size();a++){
				Attribute attr = (Attribute)currData.rek.m_vctAttributes.elementAt(a);
				if(attr.m_cbControlBlock==cbOff){
					String psuAttr = attr.getAttributeCode();
					String psuAttrType = getAttrType(psuAttr);

					String curValue = null;
					char type = psuAttrType.toUpperCase().charAt(0);
					switch(type){
					case 'U':
					case 'A':
					case 'S':
						curValue = PokUtils.getAttributeFlagValue(curItem, psuAttr);
						break;
					case 'F':
						Set testSet = new HashSet();
						testSet.add(attr.getAttributeValue());
						if(PokUtils.contains(curItem, psuAttr, testSet)){
							curValue = attr.getAttributeValue(); // just use the value, cant turn off others if this is wrong
						}else{
							curValue = null; // wasnt set so dont deactivate it					
						}
						testSet.clear();
						break;
					case 'T':
			            EANAttribute eanattr = curItem.getAttribute(psuAttr);
			            if (eanattr != null) {          
			            	curValue = eanattr.get().toString();
			            }
						break;
					}

					if(curValue==null){
						 EANMetaAttribute metaAttr = curItem.getEntityGroup().getMetaAttribute(psuAttr);
						 if(metaAttr == null){
							 curValue="Not found in "+currData.getEntityType()+" meta";
						 }else{
							 curValue="Not populated";
						 }
						 currData.addRemoveAttr(attr);
					}
					
					attr.m_strAttributeValue = curValue;
					addDebug(D.EBUG_DETAIL,"getCurrentValues: psuEntityType: "+
							currData.getEntityType()+" psuEntityId: "+currData.getEntityId()+
							" psuAttr: "+psuAttr+" psuAttrType: "+psuAttrType+
							" curValue: "+curValue);
				}
			}
		}

		list.dereference();
		list = null;
		tmp.clear();
		tmp = null;
	}

	// role must have access to all attributes
	private void getPowerUserRole() {
		String roleCode = "POWERUSER";
		try{
			poweruserProf = m_prof.getProfileForRoleCode(m_db, roleCode, roleCode, 1);
			if (poweruserProf==null) {
				//ROLE_ERR = Could not switch to {0} role
				args[0] = roleCode;
				addError("ROLE_ERR",args);
			}else {
				addDebug("Switched role from "+m_prof.getRoleCode()+" to "+poweruserProf.getRoleCode());
				poweruserProf.setReadLanguage(0);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * add this attribute to the current PSUUpdateData, set it
	 * @param psuAttrType
	 * @param currPSUdata
	 * @param psuAttr
	 * @param psuAttrValue
	 */
	void setAttribute(String psuAttrType,PSUUpdateData currPSUdata,String psuAttr,String psuAttrValue) 
	{
		if(m_cbOn==null){
			setControlBlock(); // needed for attribute updates
		}
		setAttribute(psuAttrType,currPSUdata, psuAttr,psuAttrValue,	m_cbOn);
	}
	
	/**
	 * add this attribute to the current PSUUpdateData, deactivate it
	 * @param psuAttrType
	 * @param currPSUdata
	 * @param psuAttr
	 * @param psuAttrValue
	 */
	void deactivateAttribute(String psuAttrType,PSUUpdateData currPSUdata,String psuAttr,String psuAttrValue) 
	{
		if(cbOff==null){
			cbOff = new ControlBlock(Profile.EPOCH, Profile.EPOCH, Profile.EPOCH, Profile.EPOCH, m_prof.getOPWGID());
		}
		setAttribute(psuAttrType,currPSUdata, psuAttr,psuAttrValue,	cbOff);
	}
	/**
	 * add this attribute to the current PSUUpdateData
	 * @param psuAttrType
	 * @param currPSUdata
	 * @param psuAttr
	 * @param psuAttrValue
	 * @param cb
	 */ 
	private void setAttribute(String psuAttrType,PSUUpdateData currPSUdata,String psuAttr,String psuAttrValue, 
			ControlBlock cb) 
	{
		m_typeTbl.put(psuAttr, psuAttrType); // hang onto attr types for user msg and sort

		char type = psuAttrType.toUpperCase().charAt(0);
		switch(type){
		case 'U':
		case 'A':
		case 'S':
			currPSUdata.setUniqueFlagValue(psuAttr, psuAttrValue,cb);
			break;
		case 'F':
			//assumes just turning a single flag on/off 
			currPSUdata.setMultiFlagValue(psuAttr, psuAttrValue,cb);
			break;
		case 'T':
			currPSUdata.setTextValue(psuAttr, psuAttrValue,cb);
			break;
		}
	}
	/***********************************************
	 * Sets the specified Text Attribute on the specified entity
	 *
	 * @param item
	 * @param attrcode
	 * @param attrvalue
	 */
	private void setTextValue(EntityItem item,String attrcode, String attrvalue)
	{
		addDebug(D.EBUG_SPEW,"setTextValue entered for "+item.getKey()+" "+attrcode+
				" set to: " + attrvalue);

		//get the current value
		String curval = PokUtils.getAttributeValue(item, attrcode, "", "", false);
		if (attrvalue.equalsIgnoreCase(curval)){ 
			addDebug("setTextValue: "+attrcode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
			logMessage("setTextValue: "+attrcode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
			return;
		}

		Vector vctAtts = null;
		// look at each key to see if this item is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == item.getEntityID() &&
					rek.getEntityType().equals(item.getEntityType())){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(), item.getEntityID(), true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}

		COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(m_prof.getEnterprise(),
				item.getEntityType(), item.getEntityID(), attrcode, attrvalue, 1, m_cbOn);
		vctAtts.addElement(sf);
	}

	/***********************************************
	 *  Sets the specified Unique Flag Attribute on the specified Entity
	 *
	 * @param item
	 * @param attrcode
	 * @param attrvalue
	 */
	private void setUniqueFlagValue(EntityItem item, String attrcode, String attrvalue)
	{
		addDebug(D.EBUG_SPEW,"setUniqueFlagValue entered for "+item.getKey()+" "+attrcode+
				" set to: " + attrvalue);

		//get the current value
		String curval = PokUtils.getAttributeFlagValue(item,attrcode);
		if (attrvalue.equalsIgnoreCase(curval)){ 
			addDebug("setUniqueFlagValue: "+attrcode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
			logMessage("setUniqueFlagValue: "+attrcode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
			return;
		}

		Vector vctAtts = null;
		// look at each key to see if root is there yet
		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
			if (rek.getEntityID() == item.getEntityID() &&
					rek.getEntityType().equals(item.getEntityType())){
				vctAtts = rek.m_vctAttributes;
				break;
			}
		}
		if (vctAtts ==null){
			ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
			vctAtts = new Vector();
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
		}

		SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), item.getEntityType(), item.getEntityID(),
				attrcode, attrvalue, 1, m_cbOn);

		vctAtts.addElement(sf);
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 */
	void updatePDH()
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug(D.EBUG_SPEW,"updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys.size());

		if(vctReturnsEntityKeys.size()>0) {
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

	/******
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){
		super.dereference();

		if(xai !=null){
			xai.dereference();
			xai = null;
		}
		
		attrComp=null;
		poweruserProf = null;
		rsBundle = null;
		rptSb = null;
		args = null;

		cbOff = null;
		navMetaTbl = null;
		navName = null;
		psucriteria = null;
		currentPSUHIGHENTITYTYPE = null;
		currentPSUPROGRESS = null;

		updatedRootTbl.clear();
		updatedRootTbl=null;

		if(actionItemTbl !=null){
			for (Enumeration e = actionItemTbl.keys(); e.hasMoreElements();) {
				EANActionItem item = (EANActionItem)actionItemTbl.get(e.nextElement());
				if(item instanceof LinkActionItem){
					LinkActionItem lai = (LinkActionItem)item;
					lai.dereference();
				}else if(item instanceof DeleteActionItem){
					DeleteActionItem lai = (DeleteActionItem)item;
					lai.dereference();
				}
			}
			actionItemTbl.clear();
			actionItemTbl = null;
		}
		m_typeTbl.clear();
		m_typeTbl=null;

		vctReturnsEntityKeys.clear();
		vctReturnsEntityKeys = null;

		dbgPw=null;
		dbgfn = null;
		updatedSB = null;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.2 $";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "PSUABRSTATUS";
	}
	/************************************
	 * @param item
	 * @param attrCode
	 * @return
	 */
	String getLD_Value(EntityItem item, String attrCode)   {
		return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
		PokUtils.getAttributeValue(item, attrCode, ",", PokUtils.DEFNOTPOPULATED, false);
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
	void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			addDebug(msg);
		}
	}
	/**********************************
	 * add debug info as html comment
	 * @param msg 
	 */
	void addDebug(String msg) { 
		if(dbgPw!=null){
			dbgLen+=msg.length();
			dbgPw.println(msg);
			dbgPw.flush();
		}else{
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}

	/**********************************
	 * used for error output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	void addError(String errCode, Object args[])
	{
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error:  reduce size of output, do not prepend root info
		addMessage(rsBundle.getString("ERROR_PREFIX"), errCode, args);
	} 

	/**********************************
	 * used for warning or error output
	 *
	 */
	void addMessage(String msgPrefix, String errCode, Object args[])
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
	String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();

		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)navMetaTbl.get(theItem.getEntityType());
		if (metaList==null)	{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			navMetaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}

		return navName.toString().trim();
	}
    /**********************************************************************************
     * This class is used to sort Attribute based on type - want A or S to be last
     */
    private class AttrComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2) {
            Attribute attr1 = (Attribute)o1;
            Attribute attr2 = (Attribute)o2;
            String type1 = (String)m_typeTbl.get(attr1.getAttributeCode());
            String type2 = (String)m_typeTbl.get(attr2.getAttributeCode());
            if(type1.charAt(0)=='A'){
            	type1="Z"; // make it last
            }
            if(type2.charAt(0)=='A'){
            	type2="Z"; // make it last
            }
            if(type1.charAt(0)=='S'){
            	type1="Y"; // make it next to last
            }
            if(type2.charAt(0)=='S'){
            	type2="Y"; // make it next to last
            }
            return type1.compareTo(type2); // in descending order
        }
    }
}
