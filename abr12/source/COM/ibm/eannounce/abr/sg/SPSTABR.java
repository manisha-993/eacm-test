/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: SPSTABRInterface.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2013
 *                LICENSED MATERIAL - PROGRAM PROPERTY OF IBM
 *                REFER TO COPYRIGHT INSTRUCTION FORM#G120-2083
 *                RESTRICTED MATERIALS OF IBM
 *                IBM CONFIDENTIAL
 *
 *   Version: 1.0
 *
 *   Functional Description: 
 *
 *   Component : 
 *   Author(s) Name(s): Will
 *   Date of Creation: Nov 22, 2013
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   Nov 22, 2013     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
import COM.ibm.eannounce.objects.EANUtility;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.PDGUtility;
import COM.ibm.eannounce.objects.SBRException;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

//$Log: SPSTABR.java,v $
//Revision 1.9  2014/07/09 03:05:14  liuweimi
//Fix out of memory issue
//
//Revision 1.8  2014/03/31 14:05:21  liuweimi
//add the default value of PRODID on WWSEO
//
//Revision 1.7  2014/03/06 13:48:23  liuweimi
//AVAIL.ORDERSYSNAME needs to be default to SAP(4142) value
//
//Revision 1.6  2014/02/18 07:48:59  liuweimi
//change based on BH FS Inbound Feed SPST20140120.doc.
//Mapping updates for a few items and default values.
//Add TAXCATG relator to service pacs.Check mapping for more details.
//Create new AVAIL existing SEOs/MODELs for the different set of countries.
//Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID.
//Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//
//Revision 1.5  2014/01/07 14:55:15  liuweimi
//3 Open issues - 1. If the first avail fails, continue to process other avails in the xml. This doesn't refer to invalid flag codes or invalid xml format
//2. Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID
//3. Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//

public abstract class SPSTABR {

	private static final String MODEL_SRCHACTION_NAME = "SRDMODEL4";// "SRDMODEL4";
	private static final String WWSEO_SRCHACTION_NAME = "SRDWWSEO5";// "SRDWWSEO5";
	private static final String LSEO_SRCHACTION_NAME = "SRDLSEO3";// "SRDLSEO3";
	private static final String LSEOBUNDLE_SRCHACTION_NAME = "SRDLSEOBUNDLE1";
	
//	Create MODEL:  CRMODEL
	private static final String MODEL_CREATE_ACTION = "CRMODEL";
//	LinkAvail(model): LINKAVAILMODEL
    protected static final String MODELAVAIL_LINK_ACTION  = "LINKAVAILMODEL";
//	Create AVAIL (model path): SRDAVAIL2
	protected static final String MODELAVAIL_CREATE_ACTION = "CRPEERAVAIL";//CRPEERAVAIL
//
//	Create WWSEO: CRPEERWWSEO
	private static final String WWSEO_CREATE_ACTION = "CRPEERWWSEO";
//
//	Create LSEO: CRPEERLSEO
	private static final String LSEO_CREATE_ACTION = "CRPEERLSEO";
//	Create AVAIL(lseo path): CRPEERAVAIL9
	protected static final String LSEOAVAIL_CREATE_ACTION = "CRPEERAVAIL9";
//	LinkAvail (lseo): LINKAVAILLSEO
	protected static final String LSEOAVAIL_LINK_ACTION = "LINKAVAILLSEO";
//
//	Create LSEOBUNDLE: CRLSEOBUNDLE
	private static final String LSEOBUNDLE_CREATE_ACTION = "CRLSEOBUNDLE";
//	Create LSEOBUNDLE(bundle path) CRPEERLSEOBUNDLE
//	Create AVAIL(bundle path) CRPEERAVAIL10 
	protected static final String LSEOBUNDLEAVAIL_CREATE_ACTION = "CRPEERAVAIL10";
//	LinkAvail(lseobundle): LINKAVAILSEOBUNDLE	
	protected static final String LSEOBUNDLEAVAIL_LINK_ACTION = "LINKAVAILLSEOBUNDLE";
	protected static final String LSEOBUNDLELSEO_LINK_ACTION = "LINKLSEOLSEOBUNDLE";
	
	private static final String GENERALAREA_SRCHACTION_NAME = "LDSRDGENAREA";//"SRDGENERALAREA"; 

//	100990	Add	SPST		PROJCDNAM	SPSTIDL	SPST	SPST Inbound Feed	1
	protected static final String SPST_PROJCDNAM = "SPSTIDL";//default value for spst
	protected static final String PDHDOMAIN = "0050";
	
	protected static final String FLAG_COL_DESC_CLASS = "C";
	protected static final String FLAG_COL_DESC_LONG = "L";
	protected static final String FLAG_COL_DESC_SHORT = "S";

	protected Hashtable flag_table = new Hashtable();
//	protected Hashtable prod_coll = new Hashtable();
	private Hashtable SG_GENAREACODE_TBL = null;

	protected SPSTABRSTATUS spstAbr;
	protected Element rootElem;
	
	protected String dtsOfData = null;
	protected String factSheetNum = null;	
	
//	ALL:  will be map to Catalog -Business Parter,Catalog - Indirect/Reseller, LE Direct and Public. ( you already have that)
//	BP Distributor:  Catalog - Indirect/Reseller and Catalog - Business Partner
//	Bp Reseller  Catalog - Indirect/Reseller and Catalog - Business Partner
//	DAC;  DAC/MAX
//	IBM.COM:  Use the same values as ALL
//	LED:  LE Direct	
//	LED Custom Service:  LE Direct
//	NONE:  None
//	Non Sap:  Leave this one blank -Linda's team to manually load them after we create data.
//	1				AUDIEN	10046	Catalog - Business Partner	Catalog - Business Partner	1
//	1				AUDIEN	10048	Catalog - Indirect/Reseller	Catalog - Indirect/Reseller	1
//	1				AUDIEN	10054	Public	Public	1
//	1				AUDIEN	10055	None	None	1
//	1				AUDIEN	10062	LE Direct	LE Direct	1
//	1				AUDIEN	10067	DAC/MAX	DAC/MAX	1
	private static final String TEMP_STR = "@@";
	private static final String NON_SAP = "Non Sap";
	private static final Hashtable AUDIEN_TBL;
	private static final Hashtable TAXCATG_TBL;
	private static final Hashtable SALES_ORG_TBL;
	
	static{
		AUDIEN_TBL = new Hashtable();
		Vector audiences = new Vector();
		audiences.addElement("10046");
		audiences.addElement("10048");
		audiences.addElement("10054");
		audiences.addElement("10062");
		AUDIEN_TBL.put("ALL", audiences);
		Vector bpaudiences = new Vector();
		bpaudiences.addElement("10046");
		bpaudiences.addElement("10048");
		AUDIEN_TBL.put("BP Distributor", bpaudiences);
		AUDIEN_TBL.put("Bp Reseller",bpaudiences);
		Vector dac = new Vector();
		dac.addElement("10067");
		AUDIEN_TBL.put("DAC", dac);
		AUDIEN_TBL.put("IBM.COM", audiences);
		Vector le = new Vector();
		le.addElement("10062");
		AUDIEN_TBL.put("LED", le);
		AUDIEN_TBL.put("LED Custom Service", le);
		Vector none = new Vector();
		none.addElement("10055");
		AUDIEN_TBL.put("NONE", none);
		Vector nonsap = new Vector();
		nonsap.add(TEMP_STR);
		AUDIEN_TBL.put(NON_SAP, nonsap);
		
//		Avail Country		Sales Org			Tax Category Mapping
//		US					US					TAXCATG9
//		Canada				Canada				TAXCATG10
//		China				China-offshore		TAXCATG7
//							China-onshore		TAXCATG2
//		Germany				Germany and Ireland	TAXCATG6
//		United Kingdom		United Kingdom		TAXCATG26
//		Ireland				Ireland				TAXCATG29
//		Taiwan				Taiwan				TAXCATG33
//		Hong Kong			Hong Kong			TAXCATG28
//		Macao				Macao				TAXCATG31
//		Brazil				Brazil				TAXCATG27
		TAXCATG_TBL = new Hashtable();

		TAXCATG_TBL.put("1652", createSimpleVector(new Integer(9)));
		TAXCATG_TBL.put("1464", createSimpleVector(new Integer(10)));
		Vector chv = new Vector();
		chv.add(new Integer(7));
		chv.add(new Integer(2));
		TAXCATG_TBL.put("1470", chv);//TODO may have some problem, we have another country for china-offshore 1667
		TAXCATG_TBL.put("1508", createSimpleVector(new Integer(6)));
		TAXCATG_TBL.put("1651", createSimpleVector(new Integer(26)));
		TAXCATG_TBL.put("1531", createSimpleVector(new Integer(29)));
		TAXCATG_TBL.put("1635", createSimpleVector(new Integer(33)));
		TAXCATG_TBL.put("1524", createSimpleVector(new Integer(28)));
		TAXCATG_TBL.put("1553", createSimpleVector(new Integer(31)));
		TAXCATG_TBL.put("1456", createSimpleVector(new Integer(27)));	
		
		SALES_ORG_TBL = new Hashtable();
//		SALES_ORG_TBL.put("1652", createSimpleVector("SGSUS"));
//		SALES_ORG_TBL.put("1464", createSimpleVector("CA"));//TODO no definition for CA
		Vector chs = new Vector();//TODO confirm that for china we have two values of generaname for china
		chs.add("CON");//CON
		chs.add("COF");//COF
		SALES_ORG_TBL.put("1470", chs);//Have to check if it is an String or vector		
		SALES_ORG_TBL.put("1508", createSimpleVector("GER"));
		SALES_ORG_TBL.put("1651", createSimpleVector("SGUK"));
		SALES_ORG_TBL.put("1531", createSimpleVector("SGIR"));//TODO have another value:SIRE
		SALES_ORG_TBL.put("1635", createSimpleVector("SGTW"));
		SALES_ORG_TBL.put("1524", createSimpleVector("SGHK"));
		SALES_ORG_TBL.put("1553", createSimpleVector("SGMA"));
		SALES_ORG_TBL.put("1456", createSimpleVector("SGBR"));	
	}

	private static Vector createSimpleVector(Object obj) {
		Vector c = new Vector();
		c.add(obj);
		return c;
	}
	
	

	/***********************************************
	 * Validate current data and structure - check for any missing information
	 * 
	 * @param theAbr
	 * @param rootElem
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	void validateData(SPSTABRSTATUS theAbr, Element rootElem)
			throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		spstAbr = theAbr;
		this.rootElem = rootElem;	
//		1	<SPSTAVAILMODEL>		1				
//		1	<DTSOFDATA>	</DTSOFDATA>	2	Date Time Stamp of Data going to Approved	DB2 ISO Format		
//		1	<SPSTSHEETNUM>	</SPSTSHEETNUM>	2		
		dtsOfData = spstAbr.getNodeValue(rootElem,"DTSOFDATA", true);
		factSheetNum = spstAbr.getNodeValue(rootElem,"SPSTSHEETNUM", true);
		
//		1	<AVAILLIST>		2		
		NodeList availList = rootElem.getElementsByTagName("AVAILLIST");		
		spstAbr.verifyChildNodes(rootElem, "AVAILLIST", "AVAILELEMENT", 1);
		for (int x=0; x<availList.getLength(); x++){
			Node node =availList.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){					
				continue;
			}
//				<1..N>	<AVAILELEMENT>		3	
			NodeList availelemlist = availList.item(x).getChildNodes(); // AVAILELEMENT
			for (int e=0; e<availelemlist.getLength(); e++){	
				Node availelemnode = availelemlist.item(e);
				if (availelemnode.getNodeType()!=Node.ELEMENT_NODE){						
					continue;
				}
				Element availElem = (Element)availelemnode;
				String availtype = spstAbr.getNodeValue(availElem, "AVAILTYPE", true);
				String spsttype = rootElem.getNodeName();
//					From Rupal: We should fail the ABR if AVAILTYPE is not new or Withdrawal.
				boolean isNew = "New".equalsIgnoreCase(availtype)&&
						("SPSTAVAILMODEL".equals(spsttype)||"SPSTAVAILBUNDLE".equals(spsttype));
				boolean isWithdrawal = "Withdrawal".equalsIgnoreCase(availtype)&&("SPSTWDAVAIL".equals(spsttype));
				if(!isNew && !isWithdrawal){
					spstAbr.addError("AVAIL_TYPE_WRONG",new String[]{availtype,spsttype});
				}
//					1	<COUNTRYLIST>		4				
//					<1..N>	<COUNTRY>	</COUNTRY>	5	Countr(ies)		AVAIL	COUNTRYLIST
//							</COUNTRYLIST>	4	
				spstAbr.verifyChildNodes(availElem, "COUNTRYLIST", "COUNTRY", 1);
				checkMultiFlagAttr(availElem,"COUNTRYLIST","COUNTRY","COUNTRYLIST");//check flag code for country
				if (spstAbr.getReturnCode()== PokBaseABR.PASS){
					checkRelatedEntities(availElem);
				}
				
			}
		}		
	}
	
	protected abstract void checkRelatedEntities(Element availElem) throws SQLException,MiddlewareException, MiddlewareShutdownInProgressException;
	


	/***********************************************
	 * release memory
	 */
	void dereference(){
		flag_table.clear();
//		prod_coll.clear();
	}

	/***********************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	abstract String getVersion();

	/***********************************************
	 * Get the title
	 * 
	 * @return java.lang.String
	 */
	abstract String getTitle();

	/***********************************************
	 * Get the header
	 * 
	 * @return java.lang.String
	 */
	protected String getHeader(){
		if (dtsOfData==null||factSheetNum==null){
			return "";
		}
		// SPST_COMMON_HEADER = Date/Time = {0}<br />Fact Sheet = {1}<br />
		String header = spstAbr.getResourceMsg("SPST_COMMON_HEADER", new Object[]{dtsOfData,factSheetNum});		
		return header;
	}

	/***********************************************
	 * Get the description
	 * 
	 * @return java.lang.String
	 */
	abstract String getDescription();

	/****************
	 * 1.	Search for MODEL using: 
	 * -	MACHTYPEATR = <MT>
	 * -	MODELATR = <MODEL>
	 * 
	 * 1.	Search (restricted to PDHDOMAIN) for the Model using: 
		�	MACHTYPEATR = <MT>
		�	MODELATR = <MODEL>
	 * @param mt
	 * @param model
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem searchForModel(String mt, String model)
			throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		EntityItem modelItem = null;
		if (mt != null && model != null) {
			Vector attrVct = new Vector(2);
			attrVct.addElement("MACHTYPEATR");
			attrVct.addElement("MODELATR");			
			Vector valVct = new Vector(2);
			valVct.addElement(mt);
			valVct.addElement(model);
			attrVct.addElement("PDHDOMAIN");
			valVct.addElement(PDHDOMAIN);//limit to xseries now

			EntityItem eia[] = null;

			try {
				StringBuffer debugSb = new StringBuffer();
				eia = ABRUtil.doSearch(spstAbr.getDatabase(),
						spstAbr.getProfile(), MODEL_SRCHACTION_NAME, "MODEL",
						false, attrVct, valVct, debugSb);
				if (debugSb.length() > 0) {
					spstAbr.addDebug(debugSb.toString());
				}
			} catch (SBRException exc) {
				// these exceptions are for missing flagcodes or failed business
				// rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				spstAbr.addDebug("searchForModel SBRException: "
						+ exBuf.getBuffer().toString());
			}
			if (eia != null && eia.length > 0) {
					modelItem = eia[0];				
			} // end something found
			attrVct.clear();
			valVct.clear();
		}

		return modelItem;
	}

	protected EntityItem searchForWWSEO(String seoid) throws SQLException,
			MiddlewareException, MiddlewareShutdownInProgressException {
		if(seoid == null || seoid.trim().length() == 0){
			return null;
		}
		EntityItem wwseo = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);
		attrVct.addElement("PDHDOMAIN");
		valVct.addElement(PDHDOMAIN);//limit to xseries now

		EntityItem eia[] = null;
		try {
			StringBuffer debugSb = new StringBuffer();
			eia = ABRUtil.doSearch(spstAbr.getDatabase(), spstAbr.getProfile(),
					WWSEO_SRCHACTION_NAME, "WWSEO", false, attrVct, valVct,
					debugSb);
			if (debugSb.length() > 0) {
				spstAbr.addDebug(debugSb.toString());
			}
		} catch (SBRException exc) {
			// these exceptions are for missing flagcodes or failed business
			// rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			spstAbr.addDebug("searchForWWSEO SBRException: "
					+ exBuf.getBuffer().toString());
		}
		if (eia != null && eia.length > 0) {
			for (int i = 0; i < eia.length; i++) {
				spstAbr.addDebug("SPSTABR.searchForWWSEO found "
						+ eia[i].getKey());
			}
			if (eia.length > 1) {
				StringBuffer sb = new StringBuffer();
				sb.append("More than one WWSEO found for " + seoid);
				for (int i = 0; i < eia.length; i++) {
					sb.append("<br />" + eia[i].getKey() + ":" + eia[i]);
				}
				spstAbr.addError(sb.toString());
			}
			wwseo = eia[0];
		}

		attrVct.clear();
		valVct.clear();
		return wwseo;
	}

	/*****************************************
	 * 3. Search for LSEO using: - <SEOID>
	 * 
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem searchForLSEO(String seoid) throws SQLException,
			MiddlewareException, MiddlewareShutdownInProgressException {
		if(seoid == null || seoid.trim().length() == 0){
			return null;
		}
		EntityItem lseo = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);
		attrVct.addElement("PDHDOMAIN");
		valVct.addElement(PDHDOMAIN);//limit to xseries now

		EntityItem eia[] = null;
		try {
			StringBuffer debugSb = new StringBuffer();
			eia = ABRUtil.doSearch(spstAbr.getDatabase(), spstAbr.getProfile(),
					LSEO_SRCHACTION_NAME, "LSEO", false, attrVct, valVct,
					debugSb);
			if (debugSb.length() > 0) {
				spstAbr.addDebug(debugSb.toString());
			}
		} catch (SBRException exc) {
			// these exceptions are for missing flagcodes or failed business
			// rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			spstAbr.addDebug("searchForLSEO SBRException: "
					+ exBuf.getBuffer().toString());
		}
		if (eia != null && eia.length > 0) {
			for (int i = 0; i < eia.length; i++) {
				spstAbr.addDebug("SPSTABR.searchForLSEO found "
						+ eia[i].getKey());
			}
			if (eia.length > 1) {
				StringBuffer sb = new StringBuffer();
				sb.append("More than one LSEO found for " + seoid);
				for (int i = 0; i < eia.length; i++) {
					sb.append("<br />" + eia[i].getKey() + ":" + eia[i]);
				}
				spstAbr.addError(sb.toString());
			}
			lseo = eia[0];
		}
		attrVct.clear();
		valVct.clear();
		return lseo;
	}

	/*****************************************
	 * 3. Search for LSEOBUNDLE using: - <SEOID>
	 * 
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem searchForLSEOBUNDLE(String seoid) throws SQLException,
			MiddlewareException, MiddlewareShutdownInProgressException {
		if(seoid == null || seoid.trim().length() == 0){
			return null;
		}
		EntityItem lseobundle = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
		Vector valVct = new Vector(1);
		valVct.addElement(seoid);
		attrVct.addElement("PDHDOMAIN");
		valVct.addElement(PDHDOMAIN);//limit to xseries now

		EntityItem eia[] = null;
		try {
			StringBuffer debugSb = new StringBuffer();
			eia = ABRUtil.doSearch(spstAbr.getDatabase(), spstAbr.getProfile(),
					LSEOBUNDLE_SRCHACTION_NAME, "LSEOBUNDLE", false, attrVct,
					valVct, debugSb);
			if (debugSb.length() > 0) {
				spstAbr.addDebug(debugSb.toString());
			}
		} catch (SBRException exc) {
			// these exceptions are for missing flagcodes or failed business
			// rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			spstAbr.addDebug("searchForLSEO SBRException: "
					+ exBuf.getBuffer().toString());
		}
		if (eia != null && eia.length > 0) {
			for (int i = 0; i < eia.length; i++) {
				spstAbr.addDebug("SPSTABR.searchForLSEOBUNDLE found "
						+ eia[i].getKey());
			}
			if (eia.length > 1) {
				StringBuffer sb = new StringBuffer();
				sb.append("More than one LSEOBUNDLE found for " + seoid);
				for (int i = 0; i < eia.length; i++) {
					sb.append("<br />" + eia[i].getKey() + ":" + eia[i]);
				}
				spstAbr.addError(sb.toString());
			}
			lseobundle = eia[0];
		}
		attrVct.clear();
		valVct.clear();
		return lseobundle;
	}	
	
	private String getFlagBaseShortDesc(String attrCode, String attrVal) throws SQLException, MiddlewareException{
		return executeSql("select descriptionclass from opicm.metadescription "
				+ "where DESCRIPTIONTYPE = \'"+attrCode+"\' and SHORTDESCRIPTION=\'"+attrVal+"\'");		
	}

	protected String executeSql(String sql) throws SQLException,
			MiddlewareException {

		Database m_db = spstAbr.getDatabase();
		String flagcode = null;
		PreparedStatement ps = null;
		ResultSet result=null;
		String now = m_db.getNow(0);
		String tail = "  and valto > \'"+now+"\' and effto > \'"+now+"\' with ur";
		spstAbr.addDebug("get the flag code using sql: " + sql+tail);
		try{			
			ps = m_db.getPDHConnection().prepareStatement(sql+tail);
			result = ps.executeQuery();
			while(result.next()) {                  
				flagcode = result.getString(1);
				break;
			}
		}finally{
			if (result!=null){
				try {
					result.close();
				}catch(Exception e){
					System.err.println("getFlagBaseShortDesc(), unable to close result. "+ e);
				}
				result=null;
			}
			if (ps !=null) {
				try {
					ps.close();
				}catch(Exception e){
					System.err.println("getFlagBaseShortDesc(), unable to close ps. "+ e);
				}
				ps=null;
			}
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}
		spstAbr.addDebug("returned flag code is " + flagcode);
		return flagcode;
	}
	private String getFlagBaseDescClass(String attrCode, String attrVal) throws SQLException, MiddlewareException{
		return executeSql("select descriptionclass from opicm.metadescription "
				+ "where DESCRIPTIONTYPE = \'"+attrCode+"\' and descriptionclass=\'"+attrVal+"\'");		
	}
	
	protected boolean isValidNewAvail(EntityItem parent,Vector cntryList, String availtype) throws SQLException, MiddlewareException{
		Vector availCnty = getRelatedAvailCnty(parent,availtype);
		Vector newAvailCnty = new Vector();
		newAvailCnty.addAll(cntryList);
		newAvailCnty.retainAll(availCnty);
		if(newAvailCnty.size() > 0){
			spstAbr.addDebug("There are same country in the existed avail: " + newAvailCnty);
			return false;
		}else{
			return true;
		}
	}
	
	private Vector getRelatedAvailCnty(EntityItem parent,String availtype) throws SQLException, MiddlewareException{
		Vector v = new Vector();
		Database m_db = spstAbr.getDatabase();
		PreparedStatement ps = null;
		ResultSet result=null;
		String now = m_db.getNow(0);
		String sql = "select entityid, attributevalue from opicm.flag where entitytype = \'AVAIL\' "
				+ "and attributecode = \'COUNTRYLIST\' "
				+ "and entityid in (select entityid from opicm.flag where entitytype = \'AVAIL\' "
				+ "and valto > \'"+now+"\' and effto > \'"+now+"\' "
				+ "and attributecode = \'AVAILTYPE\' and attributevalue = \'"
				+ availtype				
				+ "\' and entityid in (select entity2id from opicm.relator where entity1id = "
				+ parent.getEntityID()
				+ " and entity1type = \'"
				+ parent.getEntityType()
				+ "\' and entity2type = \'AVAIL\' and valto > \'"+now+"\' and effto > \'"+now+"\')) "
				+ "and valto> \'"+now+"\' and effto > \'"+now+"\' order by entityid with ur";

		spstAbr.addDebug("get the flag code using sql: " + sql);
		try{			
			ps = m_db.getPDHConnection().prepareStatement(sql);
			result = ps.executeQuery();
			int tempid = -1;
			StringBuffer cntyMsg = new StringBuffer();
			while(result.next()) {                  
				int entityid = result.getInt(1);
				String cnty = result.getString(2);
				if(tempid != entityid){
					spstAbr.addDebug("AVAIL" + entityid + "\'s country : " + cntyMsg.toString());
					cntyMsg = new StringBuffer();
					tempid = entityid;
				}
				cntyMsg.append(" "+ cnty + " ");
				v.add(cnty);				
			}
			spstAbr.addDebug("AVAIL" + tempid + "\'s country : " + cntyMsg.toString());
		}finally{
			if (result!=null){
				try {
					result.close();
				}catch(Exception e){
					spstAbr.addDebug("getRelatedAvailCnty(), unable to close result. "+ e);
				}
				result=null;
			}
			if (ps !=null) {
				try {
					ps.close();
				}catch(Exception e){
					spstAbr.addDebug("getRelatedAvailCnty(), unable to close ps. "+ e);
				}
				ps=null;
			}
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}
		spstAbr.addDebug("returned country list is " + v);
		return v;
	}
	
	/**
	 * check if the flag description has the related flag code in eacm
	 * @param elem
	 * @param tagname
	 * @param attrcode
	 * @param flagColumn TODO
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException 
	 */
	protected void checkSingleFlagAttr(Element elem, String tagname, String attrcode, String flagColumn) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException{
		String nodeValue = spstAbr.getNodeValue(elem,tagname, false);		
		if(nodeValue.trim().length() == 0){
			spstAbr.addDebug("checkSingleFlagAttr: value is empty, do not do any checking for this flag");
			return;
		}
		if(nodeValue != null){		
			if("COUNTRYLIST".equals(attrcode)){//in case: it is a countrylist
				StringBuffer debugSb = new StringBuffer();
				// GENAREACODE
				EntityItem genarea = searchForGENERALAREA(spstAbr.getDatabase(), 
						spstAbr.getProfile(), nodeValue,debugSb);
				if (debugSb.length()>0){
					spstAbr.addDebug(debugSb.toString());
				}
				if (genarea!= null){
					// get genareaname attr flag value
					String ctryflagcode = PokUtils.getAttributeFlagValue(genarea, "GENAREANAME");
					if (ctryflagcode!= null) {
						flag_table.put(tagname+nodeValue, ctryflagcode);
						spstAbr.addDebug("valid country: " + ctryflagcode);
					}else{
						//ERROR_COUNTRY = Country = {0}<br />Country Code not found.
						spstAbr.addError("ERROR_COUNTRY",nodeValue);
					}
				}else{
					spstAbr.addError("GENERALAREA item not found for COUNTRY: "+nodeValue);
				}				
			}else{//Not countrylist
				Object flagcode = flag_table.get(tagname+nodeValue);
				if(flagcode == null){
					if("AUDIENCE".equalsIgnoreCase(tagname)){
						Object audiences = deriveAudien(nodeValue);
						spstAbr.addDebug("checkSingleFlagAttr "+attrcode+" desc : "+nodeValue+" flagcode "+audiences);
						if(audiences != null){
							flag_table.put(tagname+nodeValue, audiences);							
						}//not null, has add error before
						return;
					}					
					// find flag code corresponding to the xml tag value					
					if(FLAG_COL_DESC_SHORT.equals(flagColumn)){
						flagcode = getFlagBaseShortDesc(attrcode, nodeValue);
					}else if(FLAG_COL_DESC_CLASS.equals(flagColumn)){
						flagcode = getFlagBaseDescClass(attrcode, nodeValue);
					}else{//longdescription
						PDGUtility m_utility = new PDGUtility();
						String[] flagArray = m_utility.getFlagCodeForExactDesc(spstAbr.getDatabase(), spstAbr.getProfile(), attrcode, nodeValue);
						if (flagArray != null && flagArray.length > 0) {
							flagcode = flagArray[0];
						}
						m_utility.dereference();
					}
					if(flagcode!= null){
						flag_table.put(tagname+nodeValue, flagcode);
						spstAbr.addDebug("checkSingleFlagAttr "+attrcode+" desc : "+nodeValue+" flagcode "+flagcode);
					}else{
						//spstAbr.addERROR("NO match flagcode found for "+attrcode+" desc : "+nodeValue);
						spstAbr.addError("NO_MATCH_FLAGCODE",new String[]{attrcode,nodeValue});
					}					
				}
			}
		}
	}
	
	/**
	 * check if the mutiple flag such as country, audience has the related flag code in eacm
	 * @param elem
	 * @param parentTag
	 * @param tagName
	 * @param attrcode
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException 
	 */
	protected void checkMultiFlagAttr(Element elem,String parentTag,String tagName, String attrcode) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException{
		NodeList nlist = elem.getElementsByTagName(parentTag);
		for(int i = 0;i<nlist.getLength();i++){
			Node node = nlist.item(i);
			if (node.getNodeType()!=Node.ELEMENT_NODE){				
				continue;
			}
			Element childElem = (Element)node;
			if(childElem.getParentNode()==elem){//check parent to ensure we get the correct elements
				NodeList childlist = childElem.getChildNodes();
				for (int j = 0; j < childlist.getLength(); j++) {
					Node childnode = childlist.item(j);
					if (childnode.getNodeType()!=Node.ELEMENT_NODE){				
						continue;
					}
					checkSingleFlagAttr((Element)childnode,tagName,attrcode, FLAG_COL_DESC_LONG);
				}	
			}
		}
	}
	
	/**
	 * generate a collection for multi flag base on the tag, such as country, audience
	 * @param elem
	 * @param parentTag
	 * @param tagname
	 * @return
	 */	
	protected Vector generateMultiFlags(Element elem,String parentTag,String tagname,StringBuffer sb){
		Set set = new HashSet();	// ensure we didn't have duplicated value, especially for audience	
		NodeList ctrylist = elem.getElementsByTagName(parentTag);
		for (int x=0; x<ctrylist.getLength(); x++){//LIST
			Node node = ctrylist.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){
				continue;
			}

			Element	ctryListElem = (Element)ctrylist.item(x);
			if(ctryListElem.getParentNode()==elem){//check parent
				NodeList ctryelemlist = ctryListElem.getChildNodes(); // COUNTRY
				for (int e=0; e<ctryelemlist.getLength(); e++){	
					node = ctryelemlist.item(e);
					if (node.getNodeType()!=Node.ELEMENT_NODE){
						continue;
					}
					Element ctryElem = (Element)ctryelemlist.item(e);
					String ctryDesc = spstAbr.getNodeValue(ctryElem, tagname, false);
					if(ctryDesc.trim().length() == 0){
						spstAbr.addDebug("ctryDesc is empty");
						continue;
					}
					sb.append(" "+ ctryDesc +" ");
					if("AUDIENCE".equalsIgnoreCase(tagname)){//in this case, it should be vector, not a string
						spstAbr.addDebug("AUDIENCE tag will be processed, it is vector, not string");
						if(NON_SAP.equals(ctryDesc)){
							spstAbr.addDebug("will not deal with Non sap there");
						}else{
							set.addAll((Vector)(flag_table.get(tagname+ctryDesc)));
						}
					}else{
						String flagcode = (String)(flag_table.get(tagname+ctryDesc));//flag_table has been done in checking
						if(flagcode !=null)
							set.add(flagcode);
						else
							spstAbr.addDebug("cannot can get the correct flag code in the hashtable, something wrong when checking");
					}
				}
			}
		}	
		Vector v = new Vector();
		v.addAll(set);
		spstAbr.addDebug("generateMultiFlags() - for  tagname : " + v);
		return v;
	}
	
	protected String getSingleFlag(Element elem,String tagname){
		String nodeValue = spstAbr.getNodeValue(elem,tagname, false);	
		String flagcode = (String)flag_table.get(tagname+nodeValue);//flag_table has been done in checking
		if(flagcode ==null){
			spstAbr.addDebug("cannot can get the correct flag code in the hashtable, something wrong when checking,set it to empty");	
			flagcode = "";
		}
		return flagcode;
	}
	
	/**
	 * create wwseo
	 * default value for some attributes
	 * 		SPECBID	Default	No
			SEOORDERCODE	Default	Initial
			PROJCDNAM	Default	SPST INBOUND IDL
			PDHDOMAIN	Default	xSeries
	 * @param spstAbr
	 * @param modelItem
	 * @param seoid
	 * @param seoTechDesc
	 * @param domain
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem createWWSEO(SPSTABRSTATUS spstAbr, EntityItem modelItem, Vector attrcodes, 
			Hashtable attrvals)
	{
		String seoid = (String)attrvals.get("SEOID");
//		String key = "WWSEO"+seoid;
		EntityItem wwseo = null;
//		EntityItem wwseo = (EntityItem)prod_coll.get(key);
//		if(wwseo != null){
//			spstAbr.addDebug("WWSEO has been created in last loop, just need to return it");
//			return wwseo;
//		}
		
		Vector attrCodeVct = new Vector();
		attrCodeVct.addAll(attrcodes);
		Hashtable attrValTbl = new Hashtable();
		attrValTbl.putAll(attrvals);

		attrCodeVct.addElement("PROJCDNAM");
		attrValTbl.put("PROJCDNAM", SPST_PROJCDNAM); //PROJCDNAM	U	Default	SPST INBOUND IDL
		attrCodeVct.addElement("SEOORDERCODE");
		attrValTbl.put("SEOORDERCODE", "10");//SEOORDERCODE	U	SEO Ordercode	"Initial" =>SEOORDERCODE	10		Initial
		attrCodeVct.addElement("SPECBID");
		attrValTbl.put("SPECBID","11457");//SPECBID	U	Default	No
//		PRODID	Default	O-Other/Options
		attrCodeVct.addElement("PRODID");
		attrValTbl.put("PRODID", "PID5"); 
//		PRCINDC	Default	Yes
//		attrCodeVct.addElement("PRCINDC");
//		attrValTbl.put("PRCINDC","yes");
//		RATECARDCD	Default	ZZZ-0000
		attrCodeVct.addElement("RATECARDCD");
		attrValTbl.put("RATECARDCD","ZZZ-0000");
//		UNSPSCCD	Default	81111812-Computer hardware maintenance support service
		attrCodeVct.addElement("UNSPSCCD");
		attrValTbl.put("UNSPSCCD","95161200");
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		Vector pdhdomain = new Vector();
		pdhdomain.add(PDHDOMAIN);
		attrValTbl.put("PDHDOMAIN", pdhdomain);
				
		StringBuffer debugSb = new StringBuffer();
		try{
			wwseo = ABRUtil.createEntity(spstAbr.getDatabase(), spstAbr.getProfile(), WWSEO_CREATE_ACTION, modelItem,  
				"WWSEO", attrCodeVct, attrValTbl, debugSb); 
			debugSb.append("wwseo was created. WWSEO.getkey() = " + wwseo.getKey());
//			prod_coll.put(key, wwseo);
		}catch(Exception ere){
			debugSb.append( ere.getMessage());
		}finally{
			if (debugSb.length()>0){
				spstAbr.addDebug(debugSb.toString());
			}
			if (wwseo==null){
				spstAbr.addError("ERROR: Can not create WWSEO entity for seoid: "+seoid);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
		
		return wwseo;
	}
	/*****************************************************
	 * 5.	Create LSEO
	 * The attributes of the LSEO 
			PRCINDC	Default	Yes
			ZEROPRICE	Default	No
			PLANRELEVANT	Default	No
			FLFILSYSINDC	Default	BTH
			PDHDOMAIN	Default	xSeries
	 * @param spstAbr
	 * @param wwseoItem
	 * @param seoid
	 * @param pubfrom
	 * @param countryVct
	 * @param domain - comes from Model now
	 * @param audien - derived from XML msg now
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem createLSEO(SPSTABRSTATUS spstAbr, EntityItem wwseoItem, Vector attrcodes, 
			Hashtable attrvalsn)
	{
		String seoid = (String)attrvalsn.get("SEOID");
//		String key = "LSEO"+seoid;
		EntityItem lseo = null;
//				(EntityItem)prod_coll.get(key);
//		if(lseo != null){
//			spstAbr.addDebug("LSEO has been created in last loop, just need to return it");
//			return lseo;
//		}
		
		Vector attrCodeVct = new Vector();
		attrCodeVct.addAll(attrcodes);
		Hashtable attrValTbl = new Hashtable();
		attrValTbl.putAll(attrvalsn);
//		PRCINDC	Default	Yes
//		ZEROPRICE	Default	No
//		PLANRELEVANT	Default	No
//		FLFILSYSINDC	Default	BTH
//		PDHDOMAIN	Default	xSeries
		attrCodeVct.addElement("PRCINDC");
		attrValTbl.put("PRCINDC", "yes");
		attrCodeVct.addElement("ZEROPRICE");
		attrValTbl.put("ZEROPRICE", "120"); 
		attrCodeVct.addElement("PLANRELEVANT");
		attrValTbl.put("PLANRELEVANT", "10"); 
		attrCodeVct.addElement("FLFILSYSINDC");
		attrValTbl.put("FLFILSYSINDC", "F00070");		
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		Vector pdhdomain = new Vector();
		pdhdomain.add(PDHDOMAIN);
		attrValTbl.put("PDHDOMAIN", pdhdomain);
		
		StringBuffer debugSb = new StringBuffer();
		try{
			lseo = ABRUtil.createEntity(spstAbr.getDatabase(), spstAbr.getProfile(), LSEO_CREATE_ACTION, wwseoItem,  
				"LSEO", attrCodeVct, attrValTbl, debugSb); 
			debugSb.append("lseo was created. LSEO.getKey() = " + lseo.getKey());
//			prod_coll.put(key, lseo);
		}catch(Exception ere){
			debugSb.append( ere.getMessage());
		}finally{
			if (debugSb.length()>0){
				spstAbr.addDebug(debugSb.toString());
			}
			if (lseo==null){
				spstAbr.addError("ERROR: Can not create LSEO entity for seoid: "+seoid);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
				
		return lseo;
	}
	
	/**
	 * create MODEL
	 * 		COFCAT	Default	Service
			COFGRP	Default	N/A
			COFSUBGRP	Default	N/A
			SPECBID	Default	No
			PRCINDC	Default	Yes
			ZEROPRICE	Default	Yes
			UNSPSCCD	Default	81111812-Computer hardware maintenance support service
			UNSPSCCDUOM	Default	EA
			RATECARDCD	Default	Cost of shipping included in price of product
			SARINDC	Default	No
			PLANRELEVANT	Default	No
			SYSIDUNIT	Default	Non SIU- CPU
			FLFILSYSINDC	Default	BTH
			PROJCDNAM	Default	SPST INBOUND IDL
			PRODID	Default	O-Other/Options
			PDHDOMAIN	Default	xSeries

	 * @param spstAbr
	 * @param attrcodes
	 * @param attrvals
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem createMODEL(SPSTABRSTATUS spstAbr,  Vector attrcodes, 
			Hashtable attrvals) {	
		
		String mt_model = attrvals.get("MACHTYPEATR")+"-"+attrvals.get("MODELATR");
//		String key = "MODEL"+mt_model;
		EntityItem model = null;
//				(EntityItem)prod_coll.get(key);
//		if(model != null){
//			spstAbr.addDebug("MODEL has been created in last loop, just need to return it");
//			return model;
//		}
		
		Vector attrCodeVct = new Vector();		
		Hashtable attrValTbl = new Hashtable();


//		COFCAT	Default	Service
//		COFGRP	Default	N/A
//		COFSUBGRP	Default	N/A
//		SPECBID	Default	No
//		PRCINDC	Default	Yes
//		ZEROPRICE	Default	Yes

		attrCodeVct.addAll(attrcodes);
		attrValTbl.putAll(attrvals);	
		
		attrCodeVct.addElement("SPECBID");
		attrValTbl.put("SPECBID","11457");//SPECBID	U	Default	No
		attrCodeVct.addElement("PRCINDC");
		attrValTbl.put("PRCINDC", "yes");
		attrCodeVct.addElement("ZEROPRICE");
		attrValTbl.put("ZEROPRICE", "100"); //yes
//		UNSPSCCD	Default	81111812-Computer hardware maintenance support service
		attrCodeVct.addElement("UNSPSCCD");
		attrValTbl.put("UNSPSCCD", "95161200"); 
//		UNSPSCCDUOM	Default	EA
		attrCodeVct.addElement("UNSPSCCDUOM");
		attrValTbl.put("UNSPSCCDUOM", "0010"); 
//		RATECARDCD	Default	Cost of shipping included in price of product
		attrCodeVct.addElement("RATECARDCD");
		attrValTbl.put("RATECARDCD", "ZZZ-0000"); 
//		SARINDC	Default	No
		attrCodeVct.addElement("SARINDC");
		attrValTbl.put("SARINDC", "S00020"); 
//		SYSIDUNIT	Default	Non SIU- CPU
		attrCodeVct.addElement("SYSIDUNIT");
		attrValTbl.put("SYSIDUNIT", "S00030"); 
//		PRODID	Default	O-Other/Options
		attrCodeVct.addElement("PRODID");
		attrValTbl.put("PRODID", "PID5"); 
//		PLANRELEVANT	Default	No
		attrCodeVct.addElement("PLANRELEVANT");
		attrValTbl.put("PLANRELEVANT", "10"); 
//		FLFILSYSINDC	Default	BTH
		attrCodeVct.addElement("FLFILSYSINDC");
		attrValTbl.put("FLFILSYSINDC", "F00070");
//		PROJCDNAM	Default	SPST INBOUND IDL
		attrCodeVct.addElement("PROJCDNAM");
		attrValTbl.put("PROJCDNAM", SPST_PROJCDNAM); //PROJCDNAM	U	Default	SPST INBOUND IDL
//		PDHDOMAIN	Default	xSeries
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		Vector pdhdomain = new Vector();
		pdhdomain.add(PDHDOMAIN);
		attrValTbl.put("PDHDOMAIN", pdhdomain);
				
		StringBuffer debugSb = new StringBuffer();
		try{
			EntityItem wgItem = new EntityItem( null, spstAbr.getProfile(), "WG" , spstAbr.getProfile().getWGID());//set a parent for model
			model = ABRUtil.createEntity(spstAbr.getDatabase(), spstAbr.getProfile(), MODEL_CREATE_ACTION, wgItem,  
				"MODEL", attrCodeVct, attrValTbl, debugSb); 
			debugSb.append("model was created. MODEL.getkey() = " + model.getKey());
			
//			prod_coll.put(key, model);
			
		}catch(Exception ere){
			debugSb.append( ere.getMessage());
		}finally{
			if (debugSb.length()>0){
				spstAbr.addDebug(debugSb.toString());
			}
			if (model==null){
				spstAbr.addError("ERROR: Can not create MODEL entity for MACHTYPEATR-MODELATR: "+mt_model);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
		
		return model;
	}
	
	/**
	 * create lseobundle
	 * @param spstAbr
	 * @param lseoItem
	 * @param attrcodes
	 * @param attrvalsn
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected EntityItem createLSEOBUNDLE(SPSTABRSTATUS spstAbr, Vector attrcodes, 
			Hashtable attrvalsn) 
	{		
		String seoid = (String)attrvalsn.get("SEOID");
//		String key = "LSEOBUNDLE"+seoid;
		EntityItem lseobundle = null;
//				(EntityItem)prod_coll.get(key);
//		if(lseobundle != null){
//			spstAbr.addDebug("LSEOBUNDLE has been created in last loop, just need to return it");
//			return lseobundle;
//		}
				
		Vector attrCodeVct = new Vector();
		attrCodeVct.addAll(attrcodes);
		Hashtable attrValTbl = new Hashtable();
		attrValTbl.putAll(attrvalsn);

//		BUNDLETYPE	Default	Service
//		SVCPACBNDLTYPE	Default	Service
		attrCodeVct.addElement("BUNDLETYPE");
		attrValTbl.put("BUNDLETYPE","102");
//		attrCodeVct.addElement("SVCPACBNDLTYPE");
//		attrValTbl.put("SVCPACBNDLTYPE", "");//as confirmed with Rupal & Praveen: remove the attr 
//		SPECBID	Default	No
//		PRCINDC	Default	Yes
//		ZEROPRICE	Default	No
//		OFERCONFIGTYPE	Default	Fixed Configuration
		attrCodeVct.addElement("SPECBID");
		attrValTbl.put("SPECBID","11457");//SPECBID	U	Default	No
		attrCodeVct.addElement("PRCINDC");
		attrValTbl.put("PRCINDC", "yes");
		attrCodeVct.addElement("ZEROPRICE");
		attrValTbl.put("ZEROPRICE", "120"); //no
		attrCodeVct.addElement("OFERCONFIGTYPE");
		attrValTbl.put("OFERCONFIGTYPE", "FCNFIG"); //
//		PRODID	Default	O-Other/Options
		attrCodeVct.addElement("PRODID");
		attrValTbl.put("PRODID", "PID5"); 
//		PLANRELEVANT	Default	No
		attrCodeVct.addElement("PLANRELEVANT");
		attrValTbl.put("PLANRELEVANT", "10"); 
//		FLFILSYSINDC	Default	BTH
		attrCodeVct.addElement("FLFILSYSINDC");
		attrValTbl.put("FLFILSYSINDC", "F00070");
//		PROJCDNAM	Default	SPST INBOUND IDL
		attrCodeVct.addElement("PROJCDNAM");
		attrValTbl.put("PROJCDNAM", SPST_PROJCDNAM); //PROJCDNAM	U	Default	SPST INBOUND IDL
//		PDHDOMAIN	Default	xSeries
		// PDHDOMAIN was derived
		attrCodeVct.addElement("PDHDOMAIN");
		Vector pdhdomain = new Vector();
		pdhdomain.add(PDHDOMAIN);
		attrValTbl.put("PDHDOMAIN", pdhdomain);
		
		StringBuffer debugSb = new StringBuffer();
		try{
			EntityItem wgItem = new EntityItem( null, spstAbr.getProfile(), "WG" , spstAbr.getProfile().getWGID());//set a parent for lsoebundle
			lseobundle = ABRUtil.createEntity(spstAbr.getDatabase(), spstAbr.getProfile(), LSEOBUNDLE_CREATE_ACTION, wgItem,  
				"LSEOBUNDLE", attrCodeVct, attrValTbl, debugSb); 
			debugSb.append("lseobundle was created. LSEOBUNDLE.getkey() = " + lseobundle.getKey());
//			prod_coll.put(key, lseobundle);
		}catch(Exception ere){
			debugSb.append( ere.getMessage());
		}finally{
			if (debugSb.length()>0){
				spstAbr.addDebug(debugSb.toString());
			}
			if (lseobundle==null){
				spstAbr.addError("ERROR: Can not create LSEOBUNDLE entity for seoid: "+seoid);
			}

			// release memory
			attrCodeVct.clear();
			attrValTbl.clear();
		}
				
		return lseobundle;
	}
	
	protected EntityItem createOrRefAvail(EntityItem parent,EntityItem avail, String actionName,  Vector attrcodes, Hashtable attrvalsn, LinkActionItem lai) 
        {
		EntityItem genAvail = avail;
    	if (genAvail==null){ //in case no avail, create it
    		StringBuffer debugSb = new StringBuffer();
    		try{			
				EntityGroup eg =  new EntityGroup(null,spstAbr.getDatabase(),spstAbr.getProfile(),"AVAIL", "Edit");
				EntityItem entityitem = new EntityItem(eg, spstAbr.getProfile(), spstAbr.getDatabase(), "AVAIL",spstAbr.getDatabase().getNextEntityID(spstAbr.getProfile(), "AVAIL"));

    			ABRUtil.setText(entityitem, "COMNAME", (String) attrvalsn.get("COMNAME"), debugSb);
			 	ABRUtil.setUniqueFlag(entityitem, "AVAILTYPE", (String) attrvalsn.get("AVAILTYPE"), debugSb);
			 	ABRUtil.setText(entityitem, "EFFECTIVEDATE", (String) attrvalsn.get("EFFECTIVEDATE"), debugSb);
			 	ABRUtil.setUniqueFlag(entityitem, "AVAILANNTYPE","NORFA", debugSb);
//			 	AVAIL			ORDERSYSNAME	Default	SAP
			 	ABRUtil.setUniqueFlag(entityitem, "ORDERSYSNAME","4142", debugSb);//set ordersysname to 4142
			 	ABRUtil.setMultiFlag(entityitem, "COUNTRYLIST", (Vector) attrvalsn.get("COUNTRYLIST"), debugSb);
			 	Vector pdhdomain = new Vector();
     			pdhdomain.add(PDHDOMAIN);
    			ABRUtil.setMultiFlag(entityitem, "PDHDOMAIN", pdhdomain, debugSb);
    			Vector salesOrgs = generateSalesOrg((Vector) attrvalsn.get("COUNTRYLIST"));
    			ABRUtil.setMultiFlag(entityitem, "SLEORGGRP", salesOrgs, debugSb);

    			entityitem.commit(spstAbr.getDatabase(),null); 
    			genAvail = entityitem;
//    			attrcodes.addElement("AVAILANNTYPE");
//    			attrvalsn.put("AVAILANNTYPE", "NORFA");//have to set the default value
//    			attrcodes.addElement("PDHDOMAIN");
//    			Vector pdhdomain = new Vector();
//    			pdhdomain.add(PDHDOMAIN);
//    			attrvalsn.put("PDHDOMAIN", pdhdomain);
//	    		genAvail = ABRUtil.createEntity(spstAbr.getDatabase(), spstAbr.getProfile(), actionName, parent,  
//	    				"AVAIL", attrcodes, attrvalsn,  debugSb);
				debugSb.append("Avail was created. AVAIL.getkey() = " + genAvail.getKey());
    		}catch(Exception ere){
    			debugSb.append( ere.getMessage());
    		}finally{
	    		if (debugSb.length()>0){
	    			spstAbr.addDebug(debugSb.toString());
	    		}
	    		if(genAvail==null){
	    			spstAbr.addError("ERROR: Can not create AVAIL entity for COMNAME: "+ attrvalsn.get("COMNAME"));
	    		}
    		}
    	}
    	if(genAvail != null)
    		doReference(parent, lai, genAvail);    	
    	return genAvail;
    }

	protected EntityItem doReference(EntityItem parent, LinkActionItem lai,
			EntityItem child)  {
		if(parent == null || child == null){
			spstAbr.addDebug("doReference failed, parent or child is null");
			return null;
		}
		spstAbr.addDebug("doReference: referencing "+parent.getKey() +" and "+ child.getKey());
		//link the child to the parent entity
		// do the link
		EntityItem item = link(parent,child,lai.getMetaLink().getEntityType());
//			spstAbr.getDatabase().executeAction(spstAbr.getProfile(), lai);
		spstAbr.addDebug("doReference: referenced "+ child.getKey() + " for " +parent.getKey());
		return item;
		
	}
	protected EntityItem link(EntityItem parent, EntityItem child, String linkName) {//link the two entity without any checking
		// Some basic EntityType, EntityID objects
	        String strEntityType = linkName;	        
	        String strEntity1Type = parent.getEntityType();
	        int iEntity1ID = parent.getEntityID();
	        String strEntity2Type = child.getEntityType();
	        int iEntity2ID = child.getEntityID();
	        
	        return link(strEntityType, strEntity1Type, iEntity1ID, strEntity2Type,
					iEntity2ID);
    }

	protected EntityItem link(String strEntityType, String strEntity1Type,
			int iEntity1ID, String strEntity2Type, int iEntity2ID) {
		int iEntityID = 0;	
		EntityItem entityitem = null;
		try{	
			if(!checkExist(strEntity1Type,iEntity1ID)){
				spstAbr.addError("Can not Link:Entity1 is not existed - "+strEntity1Type+iEntity1ID);
				return null;
			}
			if(!checkExist(strEntity2Type,iEntity2ID)){
				spstAbr.addError("Can not Link:Entity2 is not existed - "+strEntity2Type+iEntity2ID);
				return null;
			}
			ReturnStatus returnStatus = new ReturnStatus(-1);
			Database _db = spstAbr.getDatabase();
			DatePackage datep = _db.getDates();
			Profile _prof = spstAbr.getProfile();
			int iOpenID = _prof.getOPWGID();
			int iTranID = _prof.getTranID();
			String strEnterprise = _prof.getEnterprise();
			int iSessionID = _prof.getSessionID();
			int iNLSID = _prof.getReadLanguage().getNLSID();
			//build relator
			ReturnID newid = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, iTranID, datep.getNow(), datep.getForever(),iNLSID);
			spstAbr.addDebug("new link was done: returnid is " + strEntityType+newid.intValue());
			_db.commit();
			_db.freeStatement();
			_db.isPending();
			
			int entityid = newid.intValue();
			//add name for relator in text table
			_db.callGBL3001(returnStatus, strEnterprise, iOpenID, strEntityType, entityid, iTranID);
			_db.commit();
			_db.freeStatement();
			_db.isPending();
			//add relator to entity table
			EANUtility.populateDefaultValues(_db, _prof, null,strEntityType, entityid);
			EntityGroup eg =  new EntityGroup(null,_db,_prof,strEntityType, "Edit");
			entityitem = new EntityItem(eg, _prof, _db, strEntityType,entityid);

		}catch(Exception e){
			e.printStackTrace();
			spstAbr.addError("Error: when do a link(referenced "+ strEntity2Type+iEntity2ID + " for " +strEntity1Type+iEntity1ID+"), some thing wrong - "+ e.getMessage());
		}
		return entityitem;
	}
	
	protected boolean checkExist(String entitytype,int entityid) throws SQLException,
	MiddlewareException {

		Database m_db = spstAbr.getDatabase();
		int count = 0;
		PreparedStatement ps = null;
		ResultSet result=null;
		String now = m_db.getNow(0);
		String sql = "select count(*) from opicm.entity where entitytype=\'"
				+ entitytype
				+ "\' and entityid = "
				+ entityid;

		String tail = "  and valto > \'"+now+"\' and effto > \'"+now+"\' with ur";
		spstAbr.addDebug("get the flag code using sql: " + sql+tail);
		try{			
			ps = m_db.getPDHConnection().prepareStatement(sql+tail);
			result = ps.executeQuery();
			while(result.next()) {                  
				count = result.getInt(1);
				break;
			}
		}finally{
			if (result!=null){
				try {
					result.close();
				}catch(Exception e){
					System.err.println("checkExist(), unable to close result. "+ e);
				}
				result=null;
			}
			if (ps !=null) {
				try {
					ps.close();
				}catch(Exception e){
					System.err.println("checkExist(), unable to close ps. "+ e);
				}
				ps=null;
			}
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}
		spstAbr.addDebug("returned count is " + count);
		return (count > 0);
	}
/**
 * 1.	Create AVAIL
The attributes of the AVAIL  including defaults values are supplied via the XML shown in the SS.Use the derive COMNAME value based in the SS.

The AVAIL’s COUNTRYLIST has one value for each instance of <COUNTRYELEMENT>  in the XML.
 * @throws MiddlewareRequestException
 * @throws SQLException
 * @throws MiddlewareException
 * @throws EANBusinessRuleException
 * @throws RemoteException
 * @throws MiddlewareShutdownInProgressException
 * @throws LockException
 * @throws WorkflowException
 */
	public void execute() throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException {
//		1	<AVAILLIST>		2		
		NodeList availList = rootElem.getElementsByTagName("AVAILLIST");
		for (int x=0; x<availList.getLength(); x++){
			Node node =availList.item(x);
			if (node.getNodeType()!=Node.ELEMENT_NODE){					
				continue;
			}
//				<1..N>	<AVAILELEMENT>		3	
			NodeList availelemlist = availList.item(x).getChildNodes(); // AVAILELEMENT
			for (int e=0; e<availelemlist.getLength(); e++){	
				Node availelemnode = availelemlist.item(e);
				if (availelemnode.getNodeType()!=Node.ELEMENT_NODE){						
					continue;
				}
				Element availElem = (Element)availelemnode;
				Vector availattcodeVct = new Vector();
				Hashtable availattValtab = new Hashtable();
//					6.0	60	1	<COMNAME>	</COMNAME>	4	Group name - RFA RFA#		AVAIL	COMNAME			
//					7.0	70	1	<AVAILTYPE>	</AVAILTYPE>	4	New	New - Planned Availability 	AVAIL	AVAILTYPE			
//					8.0	80	1	<EFFECTIVEDATE>	</EFFECTIVEDATE>	4	Announce date		AVAIL	EFFECTIVEDATE			
				String comname = spstAbr.getNodeValue(availElem, "COMNAME", true);
				String availtype = spstAbr.getNodeValue(availElem, "AVAILTYPE", true);
				if("New".equalsIgnoreCase(availtype)){
					availtype = "146";
				}else if("Withdrawal".equalsIgnoreCase(availtype)){
					availtype = "149";
				}
				availattcodeVct.addElement("COMNAME");
				availattValtab.put("COMNAME",comname);
				availattcodeVct.addElement("AVAILTYPE");
				availattValtab.put("AVAILTYPE",availtype);
				String effdate = spstAbr.getNodeValue(availElem, "EFFECTIVEDATE", false);
				availattcodeVct.addElement("EFFECTIVEDATE");
				availattValtab.put("EFFECTIVEDATE",effdate);
//					1	<COUNTRYLIST>		4				
//					<1..N>	<COUNTRY>	</COUNTRY>	5	Countr(ies)		AVAIL	COUNTRYLIST
//							</COUNTRYLIST>	4		
//				NodeList cntyList = availElem.getElementsByTagName("COUNTRYLIST");
				StringBuffer buffer = new StringBuffer();
				Vector availCntyV = generateMultiFlags(availElem,"COUNTRYLIST","COUNTRY",buffer);
				availattcodeVct.addElement("COUNTRYLIST");
				availattValtab.put("COUNTRYLIST",availCntyV);

				generateEntities(availattcodeVct, availattValtab,
										 availElem, buffer);
				availattcodeVct.clear();
				availattcodeVct = null;
				availattValtab.clear();
				availattValtab = null;
			}
		}
	}
	protected abstract void generateEntities(Vector availattcodeVct,
			Hashtable availattValtab, Element availElem, StringBuffer availCntySb)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException;
	
	/**
	 * check if we have duplicated MODEL, SEO or LSEOBUNLDE in the xml
	 * @param entitytype MODEL, SEO or LSEOBUNDLE
	 * @param partnumber for MODEL, machtype+modelatr; for SEO and LSEOBUNDLE, SEOID
	 * @return
	 */
	protected void checkDuplicatedProd(String entitytype,String partnumber,Vector v){		
		String key = entitytype+partnumber;
		if(v.contains(key)){
			spstAbr.addError("ERROR_DUPLICATE_PROD",new String[]{entitytype,partnumber});
		}else{
			v.add(key);
		}
	}
	
	/***********
	 * search GENERALAREA using GENAREACODE. 
	 * 2 character Country Code found in GENERALAREA.GENAREACODE and used to get
	 *  GENAREANAME which is the same as COUNTRYLIST
	 * @param dbCurrent
	 * @param profile
	 * @param country
	 * @param debugSb
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	public EntityItem searchForGENERALAREA(Database dbCurrent, Profile profile, 
			String country,StringBuffer debugSb) throws MiddlewareRequestException, 
			SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem genarea = null;
		
		// HVEC and SG meta is different for GENAREACODE
		String genAreaCodeFlg = getGenAreaCodeForCtry(dbCurrent, profile,country);
		debugSb.append("SPSTABR.searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg+"\n");
		dbCurrent.debug(D.EBUG_SPEW, "SPSTABR.searchForGENERALAREA country: "+country+" genAreaCodeFlg: "+genAreaCodeFlg);
		if (genAreaCodeFlg!= null){
			Vector attrVct = new Vector(1);
			attrVct.addElement("GENAREACODE");
			Vector valVct = new Vector(1);
			valVct.addElement(genAreaCodeFlg);
			EntityItem eia[]= null;
			try{
				eia= ABRUtil.doSearch(dbCurrent, profile, 
						GENERALAREA_SRCHACTION_NAME, "GENERALAREA", false, attrVct, valVct, debugSb);
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				debugSb.append("searchForGENERALAREA SBRException: "+exBuf.getBuffer().toString()+"\n");
			}
			if (eia!=null && eia.length > 0){			
				for (int i=0; i<eia.length; i++){
					debugSb.append("SPSTABR.searchForGENERALAREA found "+eia[i].getKey()+"\n");
				}
				genarea = eia[0];
			}
			attrVct.clear();
			valVct.clear();
		}else{
			debugSb.append("SPSTABR.searchForGENERALAREA GENAREACODE table: "+SG_GENAREACODE_TBL+"\n");
		}
		
		return genarea;
	}	
	/***************************************
	 * Find the flag code for this flag description for the GENAREACODE attribute
	 * @param dbCurrent
	 * @param profile
	 * @param ctry
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getGenAreaCodeForCtry(Database dbCurrent, Profile profile,String ctry) throws
		MiddlewareRequestException, SQLException, MiddlewareException
	{
		if (SG_GENAREACODE_TBL==null){
			SG_GENAREACODE_TBL = new Hashtable();
			// get meta info
			EntityGroup eg = new EntityGroup(null,dbCurrent, profile, "GENERALAREA", "Edit", false);
			EANMetaFlagAttribute fma =(EANMetaFlagAttribute)eg.getMetaAttribute("GENAREACODE");
			if (fma!=null) {
                for (int i=0; i<fma.getMetaFlagCount(); i++)
                {
                    MetaFlag omf = fma.getMetaFlag(i);
                    if (omf.isExpired()){
                    	dbCurrent.debug(D.EBUG_SPEW, "SPSTABR.getGenAreaCodeForCtry skipping expired flag: "+omf+"["+
                    			omf.getFlagCode()+"] for GENERALAREA.GENAREACODE");
                    	continue;
                    }
                    SG_GENAREACODE_TBL.put(omf.toString(), omf.getFlagCode());
                }
            }
		}

		return (String)SG_GENAREACODE_TBL.get(ctry);
	}
	/**
	 * 
	 * Derivation of Audience (AUDIEN)
			ALL:  will be map to Catalog -Business Parter,Catalog - Indirect/Reseller, LE Direct and Public. ( you already have that)
			BP Distributor:  Catalog - Indirect/Reseller and Catalog - Business Partner
			Bp Reseller  Catalog - Indirect/Reseller and Catalog - Business Partner
			DAC;  DAC/MAX
			IBM.COM:  Use the same values as ALL
			LED:  LE Direct	
			LED Custom Service:  LE Direct
			NONE:  None
			Non Sap:  Leave this one blank -Linda's team to manually load them after we create data.
	 * @param msgAudien
	 * @return
	 */
	protected Object deriveAudien(String spstAud){
		//AUDIEN	F	Audience	"LE Direct"	flag code = 10062
		Object audien = AUDIEN_TBL.get(spstAud);
		if (audien==null){
			spstAbr.addError(spstAud + " is not a valid Audience");
		}else if(NON_SAP.equals(spstAud)){//in case: Non Sap:  Leave this one blank -Linda's team to manually load them after we create data.
			spstAbr.addOutput("Warning: "+spstAud + " will not be set for Audience currently, please take action if need");
		}
		return audien;
	}
	
	private Vector generateSalesOrg(Vector countryList){
		Vector v = new Vector();
		if(countryList == null){
			spstAbr.addDebug("there is no countrylist");
		}
		for (Iterator it = countryList.iterator(); it.hasNext();) {
			String cnty = (String) it.next();
			Vector cntrySalesOrg = (Vector)SALES_ORG_TBL.get(cnty);
			if(cntrySalesOrg != null){
				v.addAll(cntrySalesOrg);
			}else{
				spstAbr.addDebug("there is no sales org to match the country:" + cnty);
			}
		}
		return v;
	}
	
	protected void doReferenceModelTaxCatg(EntityItem item,Vector countryList){		
		
		if(countryList == null){
			spstAbr.addDebug("there is no countrylist");
		}
		for (Iterator it = countryList.iterator(); it.hasNext();) {
			String cnty = (String) it.next();
			Vector cntrySalesOrg = (Vector)TAXCATG_TBL.get(cnty);
			if(cntrySalesOrg != null){
				for (Iterator ite = cntrySalesOrg.iterator(); ite
						.hasNext();) {
					Integer entity2id = (Integer) ite.next();
					link("MODTAXRELEVANCE", item.getEntityType(), item.getEntityID(), "TAXCATG", entity2id.intValue());
				}
			}else{
				spstAbr.addDebug("there is no necessary to match TAXCATG for the country:" + cnty);
			}
		}		
	}
}
