package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.MQUsage;
import COM.ibm.eannounce.objects.PDGUtility;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.transactions.OPICMList;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*
 The ABR will be queued for an instance of Delete XML Reports by setting ADSDELXMLABR = 'queued' (0020). The value of this attribute is changed to 'In Process'(0050) by Task Master at the start of execution of this ABR. 
 I 	T1 = ADSDTS or, if empty use '1980-01-01 00:00:00.000000'
 II	T2 = VALFROM for ADSDELXMLABR most recent value of Queued(0020)

 The selection criterion of entries from the database is:
 T1 < ADSDTS <=  T2, use VALFROM of the meta data


 An XML Report Message is created for the result of the preceding selection criteria.

 The XML Report Message is sent to the system (MQ Series Queue) identified in the Property File named in the MQPROPFILE. 

 Upon successful completion of this ABR (i.e. after the report XML is placed on the MQ Series queue), set ADSDTS = T2.

 Each XMLENTITYTYPEDEL produces one or more instances <ENTITYELEMENT> and each row selected from the XML Message Log for the XMLENTITYTYPEDEL produces one or more instances of <ENTITYLIST>.



 IX.	XML Report Message

 <TMF_DELETE xmlns="http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/TMF_DELETE">
 <DTSOFMSG></DTSOFMSG>
 <FROMMSGDTS></FROMMSGDTS>
 <TOMSGDTS></TOMSGDTS>
 <SETUPENTITYTYPE></SETUPENTITYTYPE>
 <SETUPENTITYID></SETUPENTITYID>
 <PDHDOMAIN></PDHDOMAIN>
 <ENTITYLIST>
 <ENTITYELEMENT>                
 <ENTITYTYPE></ENTITYTYPE>
 <ENTITYID></ENTITYID>
 <ACTIVITY></ACTIVITY>
 <MACHTYPE></MACHTYPE>
 <MODEL></MODEL>
 <FEATURECODE></FEATURECODE>
 </ENTITYELEMENT>
 </ENTITYLIST>     
 </TMF_DELETE>

 */
public class ADSDELXMLABR extends PokBaseABR {

	private static final int MAXFILE_SIZE = 5000000;
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private StringBuffer xmlgenSb = new StringBuffer();
	private Object[] args = new String[10];

	private Hashtable metaTbl = new Hashtable();
	private ResourceBundle rsBundle = null;
	private String attrXMLABRPROPFILE = "XMLABRPROPFILE"; // attr to put xml mq
															// info into
	private String abrName = "ADSDELXMLABR";
	private String rootEntityType = "ADSDELXMLSETUP";
	private String entityType = "";
	private String navName = "";
	private String pdhdomain = "";
	private String actionTaken = "";
	private int dbgLen = 0;
	private PrintWriter dbgPw = null;
	private String dbgfn = null;
	private PrintWriter userxmlPw = null;
	private String userxmlfn = null;
	private StringBuffer userxmlSb = new StringBuffer();
	private String t2DTS = "&nbsp;"; // T2
	private String t1DTS = "&nbsp;"; // T1
	protected static final String STATUS_QUEUE = "0020";
	protected static final String CHEAT = "@@";

	/**********************************
	 * Execute ABR.
	 */
	public void execute_run() {
		/*
		 * The Report should identify: USERID (USERTOKEN) Role Workgroup
		 * Date/Time EntityType LongDescription Any errors or list delete xml
		 */
		// must split because too many arguments for messageformat, max of 10..
		// this was 11
		String HEADER = "<head>"
				+ EACustom.getMetaTags(getDescription())
				+ NEWLINE
				+ EACustom.getCSS()
				+ NEWLINE
				+ EACustom.getTitle("{0} {1}")
				+ NEWLINE
				+ "</head>"
				+ NEWLINE
				+ "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv()
				+ NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>"
				+ NEWLINE;
		String HEADER2 = "<table>" + NEWLINE
				+ "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE
				+ "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE
				+ "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Prior feed Date/Time: </th><td>{4}</td></tr>"
				+ NEWLINE + "<tr><th>Description: </th><td>{5}</td></tr>"
				+ NEWLINE + "<tr><th>Return code: </th><td>{6}</td></tr>"
				+ NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>"
				+ NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;

		MessageFormat msgf;
		String abrversion = "";

		println(EACustom.getDocTypeHtml()); // Output the doctype and html

		try {
			start_ABRBuild(false); // pull dummy VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			setupPrintWriter();

			// get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(),
					ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));
			// get the root entity using current timestamp, need this to get the
			// timestamps
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null,
					m_db, m_prof, "dummy"), new EntityItem[] { new EntityItem(
					null, m_prof, getEntityType(), getEntityID()) });
			long startTime = System.currentTimeMillis();
			// get root from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup()
					.getEntityItem(0);
			// debug display list of groups
			addDebug("DEBUG: " + getShortClassName(getClass())
					+ " entered for " + rootEntity.getKey() + " extract: "
					+ m_abri.getVEName() + " using DTS: " + m_prof.getValOn()
					+ NEWLINE + PokUtils.outputList(m_elist));

			// Default set to pass
			setReturnCode(PASS);

			// NAME is navigate attributes
			navName = getNavigationName(rootEntity);

			// get Entitytype, it is U
			entityType = PokUtils.getAttributeValue(rootEntity,
					"XMLENTITYTYPEDEL", "", null, false);
			addDebug("Executing for entityType: " + entityType);
			// get pdhdomain
			pdhdomain = PokUtils.getAttributeValue(rootEntity, "PDHDOMAIN", "",
					null, false);
			addDebug("Entity's PDHDOMAIN: " + pdhdomain);

			if (entityType == null) {
				// INVALID_ATTR_ERR = {0} does not have a value
				args[0] = PokUtils.getAttributeDescription(
						rootEntity.getEntityGroup(), "XMLENTITYTYPEDEL",
						"XMLENTITYTYPEDEL");
				addError("INVALID_ATTR_ERR", args);
			}
			addDebug("getT1 entered for Periodic ADSDELXMLSETUP "
					+ rootEntity.getKey());
			// get it from the attribute
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup()
					.getMetaAttribute("ADSDTS");
			if (metaAttr == null) {
				throw new MiddlewareException(
						"ADSDTS not in meta for Periodic ABR "
								+ rootEntity.getKey());
			}
			metaAttr = rootEntity.getEntityGroup().getMetaAttribute(
					"XMLABRPROPFILE");
			if (metaAttr == null) {
				throw new MiddlewareException(
						"XMLABRPROPFILE not in meta for Periodic ABR "
								+ rootEntity.getKey());
			}
			String propfile = PokUtils.getAttributeFlagValue(rootEntity,
					attrXMLABRPROPFILE);
			if (propfile == null) {
				addError(rootEntityType
						+ ": No MQ properties files, nothing will be generated.");

				// NOT_REQUIRED = Not Required for {0}.
				addXMLGenMsg("NOT_REQUIRED", rootEntityType);
			}
			t1DTS = PokUtils.getAttributeValue(rootEntity, "ADSDTS", ", ",
					m_strEpoch, false);
			boolean istimestamp = isTimestamp(t1DTS);
			if (istimestamp && getReturnCode() == PASS) {
				AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(abrName);
				setT2DTS(statusHistory);
				processThis(rootEntity, propfile);
			} else if (!istimestamp) {
				addError("Invalid DateTime Stamp for ADSDTS, please put format: yyyy-MM-dd-HH.mm.ss.SSSSSS ");
			}
			if (getReturnCode() == PASS) {
				PDGUtility pdgUtility = new PDGUtility();
				OPICMList attList = new OPICMList();
				attList.put("ADSDTS", "ADSDTS=" + t2DTS);
				pdgUtility.updateAttribute(m_db, m_prof, rootEntity, attList);
			}
			addDebug("Total Time: "
					+ Stopwatch.format(System.currentTimeMillis() - startTime));

		} catch (Throwable exc) {
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = exc.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + exc.getMessage());
			logError(exBuf.getBuffer().toString());
		} finally {
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriter();
		}

		// Print everything up to </html>
		// Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getDescription();
		args[1] = navName;
		String header1 = msgf.format(args);
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		args[4] = t1DTS;
		args[5] = navName;
		args[6] = (this.getReturnCode() == PokBaseABR.PASS ? "Passed"
				: "Failed");
		args[7] = actionTaken + "<br />" + xmlgenSb.toString();
		args[8] = abrversion + " " + getABRVersion();

		restoreXtraContent();
		String info = header1 + msgf.format(args) + "<pre>"
				+ rsBundle.getString("XML_MSG") + "<br />"
				+ userxmlSb.toString() + "</pre>" + NEWLINE;
		rptSb.insert(0, info + NEWLINE);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		metaTbl.clear();
	}

	private void setupPrintWriter() {
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0, extid + 1) + "dbg";
		userxmlfn = fn.substring(0, extid + 1) + "userxml";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
		try {
			userxmlPw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(userxmlfn, true), "UTF-8"));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, "trouble creating xmlgen PrintWriter " + x);
		}
	}

	private void closePrintWriter() {
		if (dbgPw != null) {
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
		if (userxmlPw != null) {
			userxmlPw.flush();
			userxmlPw.close();
			userxmlPw = null;
		}
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem)
			throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();

		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof,
					theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
												// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem,
					ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}

		return navName.toString().trim();
	}

	/**********************************************************************************
	 * Get the history of the ABR (ADSDELXMLABR) in VALFROM order The current
	 * value should be 'queued' (0020) TQ = VALFROM of this row. T2 = TQ
	 * 
	 * @throws MiddlewareException
	 */
	private void setT2DTS(AttributeChangeHistoryGroup xmlrecrptabrstatus)
			throws MiddlewareException {

		if (xmlrecrptabrstatus != null
				&& xmlrecrptabrstatus.getChangeHistoryItemCount() > 1) {
			// get the historyitem count.
			int i = xmlrecrptabrstatus.getChangeHistoryItemCount();
			// Find the time stamp for "Queued" Status. Notic: last
			// chghistory is the current one(in process),-2 is queued.
			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) xmlrecrptabrstatus
					.getChangeHistoryItem(i - 2);
			if (achi != null) {
				addDebug("getT2Time [" + (i - 2) + "] isActive: "
						+ achi.isActive() + " isValid: " + achi.isValid()
						+ " chgdate: " + achi.getChangeDate() + " flagcode: "
						+ achi.getFlagCode());
				if (achi.getFlagCode().equals(STATUS_QUEUE)) {
					t2DTS = achi.getChangeDate();
				} else {
					addDebug("getT2Time for the value of "
							+ achi.getFlagCode()
							+ "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
					t2DTS = getNow();
				}
			}
		} else {
			t2DTS = getNow();
			addDebug("getT2Time for "
					+ abrName
					+ " changedHistoryGroup has no history, set getNow to t2DTS");
		}

	}

	/**
	 * set instance variable ADSDELXMLABR History
	 * 
	 * @param attCode
	 * @return
	 * @throws MiddlewareException
	 */
	private AttributeChangeHistoryGroup getSTATUSHistory(String attCode)
			throws MiddlewareException {
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			return new AttributeChangeHistoryGroup(m_db, m_prof, att);
		} else {
			addDebug(attCode + " of " + rootEntity.getKey() + "  was null");
			return null;
		}
	}

	/**
	 * CHECK whether is DateTime Stamp
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isTimestamp(String dateString) {
		boolean isValid = false;
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS",
				Locale.ENGLISH);
		try {
			dateFormat.parse(dateString);
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}
		return isValid;
	}

	/******************************************
	 * build xml generation msg
	 */
	protected void addXMLGenMsg(String rsrc, String info) {
		MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
		Object args[] = new Object[] { info };
		xmlgenSb.append(msgf.format(args) + "<br />");
	}

	/**********************************
	 * add debug info as html comment
	 * 
	 * @param msg
	 */
	protected void addDebug(String msg) {
		dbgLen += msg.length();
		dbgPw.println(msg);
		dbgPw.flush();
		// rptSb.append("<!-- "+msg+" -->"+NEWLINE);
	}

	/**********************
	 * support conditional msgs
	 * 
	 * @param level
	 * @param msg
	 */
	protected void addDebug(int level, String msg) {
		if (level <= abr_debuglvl) {
			addDebug(msg);
		}
	}

	/**********************************
	 * used for error output Prefix with LD(EntityType) NDN(EntityType) of the
	 * EntityType that the ABR is checking (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	protected void addError(String errCode, Object args[]) {
		setReturnCode(FAIL);

		// ERROR_PREFIX = Error: reduce size of output, do not prepend root info
		addMessage(rsBundle.getString("ERROR_PREFIX"), errCode, args);
	}

	/**********************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[]) {
		String msg = rsBundle.getString(errCode);
		// get message to output
		if (args != null) {
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix + " " + msg);
	}

	/**********************************
	 * add msg to report output
	 * 
	 * @param msg
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**
	 * merge debug info into html rpt if possible
	 */
	private void restoreXtraContent() {
		// if written to file and still small enough, restore debug and xmlgen
		// to the abr rpt and delete the file
		if (rptSb.length() < MAXFILE_SIZE) {
			// read the file in and put into the stringbuffer
			InputStream is = null;
			FileInputStream fis = null;
			BufferedReader rdr = null;
			try {
				fis = new FileInputStream(userxmlfn);
				is = new BufferedInputStream(fis);

				String s = null;
				rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// append lines until done
				while ((s = rdr.readLine()) != null) {
					userxmlSb.append(ADSABRSTATUS.convertToHTML(s) + NEWLINE);
				}
				// remove the file
				File f1 = new File(userxmlfn);
				if (f1.exists()) {
					f1.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
			}
		} else {
			userxmlSb.append("XML generated was too large for this file");
		}
		// if written to file and still small enough, restore debug and xmlgen
		// to the abr rpt and delete the file
		if (dbgLen + userxmlSb.length() + rptSb.length() < MAXFILE_SIZE) {
			// read the file in and put into the stringbuffer
			InputStream is = null;
			FileInputStream fis = null;
			BufferedReader rdr = null;
			try {
				fis = new FileInputStream(dbgfn);
				is = new BufferedInputStream(fis);

				String s = null;
				StringBuffer sb = new StringBuffer();
				rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// append lines until done
				while ((s = rdr.readLine()) != null) {
					sb.append(s + NEWLINE);
				}
				rptSb.append("<!-- " + sb.toString() + " -->" + NEWLINE);

				// remove the file
				File f1 = new File(dbgfn);
				if (f1.exists()) {
					f1.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	public List getDeleteEntity() throws SQLException, MiddlewareException {
		List entityList = new ArrayList();
		StringBuffer strbSQL;
		addDebug(abrName + ".processThis checking between " + t1DTS + " and "
				+ t2DTS);
		addDebug("get all detlete entitys---------------");
		strbSQL = new StringBuffer();
		strbSQL.append("select e.entityid as id, e.entitytype as type from opicm.entity e where e.valto>current timestamp");
		strbSQL.append(" and e.valfrom BETWEEN '" + t1DTS + "' AND '" + t2DTS
				+ "' and e.effto<current timestamp");
		strbSQL.append(" and EXISTS (select entityid from opicm.flag f1 where f1.entityid=e.entityid and f1.entitytype=e.entitytype and f1.attributecode='ADSABRSTATUS' and f1.attributevalue='0030')");
		strbSQL.append(" and EXISTS (select entityid from opicm.flag f2 where f2.entityid=e.entityid and f2.entitytype=e.entitytype and f2.attributecode='STATUS' and f2.attributevalue in ('0020','0040')) ");
		addDebug("SQL:" + strbSQL);
		ResultSet result = null;
		PreparedStatement statement = null;
		try {
			statement = m_db.getPDHConnection().prepareStatement(
					strbSQL.toString());
			result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt(1);
				String type = result.getString(2);
				ADSDELENTITY entity = new ADSDELENTITY();
				entity.setId(id);
				entity.setType(type);
				entityList.add(entity);
			}
			return entityList;

		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 
	 * @param rootEntity
	 * @param propfile
	 * @throws Exception
	 */
	public void processThis(EntityItem rootEntity, String propfile)
			throws Exception {
		HashMap rootmap = new HashMap();
		List entitys = getDeleteEntity();
		int counter = 0;
		try {
			for (int i = 0; i < entitys.size(); i++) {
				ADSDELENTITY entity = (ADSDELENTITY) entitys.get(i);
				if (entityType.equals("PRODSTRUCT")
						&& entity.getType().equals("PRODSTRUCT")) {
					counter++;
					String key = entity.getType();
					if (!rootmap.containsKey(key)) {
						Vector entitylist_xml = new Vector();
						entitylist_xml.add(getDelTMFInfo(entity.getType(),
								entity.getId()));
						rootmap.put(key, entitylist_xml);						
					} else {
						Vector xmls = (Vector) rootmap.get(key);
						xmls.add(getDelTMFInfo(entity.getType(), entity.getId()));
					}
					delCache(entity.getType(),entity.getId());
				}
			}

			sentToMQ(rootmap, rootEntity);
			addOutput("The total number is " + counter + " entities");

		} catch (RuntimeException rx) {
			addXMLGenMsg("FAILED", rootEntity.getKey());
			addDebug("RuntimeException on ? " + rx);
			rx.printStackTrace();
			throw rx;
		} catch (Exception x) {
			addXMLGenMsg("FAILED", rootEntity.getKey());
			addDebug("Exception on ? " + x);
			x.printStackTrace();
			throw x;
		}
	}

	/**
	 * 
	 * @param type
	 * @param id
	 * @return DelProdstruct
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private ADSDELPRODSTRUCT getDelTMFInfo(String type, int id)
			throws SQLException, MiddlewareException {

		ADSDELPRODSTRUCT tmfInfo = new ADSDELPRODSTRUCT();
		StringBuffer strbSQL;
		addDebug("Get Delete PRODSTRUCT info---------------");
		strbSQL = new StringBuffer();
		strbSQL.append("select r.entity2id as MODELID, r.entity2type as MODELTYPE,"
				+ "(select f.attributevalue from opicm.flag f where f.entitytype=r.entity2type and f.entityid=r.entity2id and f.attributecode='MACHTYPEATR' order by valfrom desc fetch first 1 row only ) as MACHTYPEATR ,"
				+ "(select t1.attributevalue from opicm.text t1 where t1.entitytype=r.entity2type and t1.entityid=r.entity2id and t1.attributecode='MODELATR' order by valfrom desc fetch first 1 row only ) as MODELATR , "
				+ " r.entity1id as FEATUREID, r.entity1type as FEATURETYPE, "
				+ "(select t2.attributevalue from opicm.text t2 where t2.entitytype=r.entity1type and t2.entityid=r.entity1id and t2.attributecode='FEATURECODE' order by valfrom desc fetch first 1 row only ) as FEATURECODE "
				+ " from opicm.relator r where r.entitytype='"
				+ type
				+ "' and r.entityid="
				+ id
				+ " and r.valfrom>'"
				+ t1DTS
				+ "' and r.effto<current timestamp");
		addDebug("SQL:" + strbSQL);
		ResultSet result = null;
		PreparedStatement statement = null;
		try {
			statement = m_db.getPDHConnection().prepareStatement(
					strbSQL.toString());
			result = statement.executeQuery();
			while (result.next()) {

				int modelId = result.getInt(1);
				String modelType = result.getString(2);
				String machtypeatr = result.getString(3);
				String modelatr = result.getString(4);
				int featureId = result.getInt(5);
				String featureType = result.getString(6);
				String featureCode = result.getString(7);

				addDebug("DelProdStructInfo MODELENTITYID:" + modelId
						+ " MODELENTITYTYPE:" + modelType + " MACHTYPE:"
						+ machtypeatr + " MODEL:" + modelatr
						+ " FEATUREENTITYTYPE:" + featureType
						+ " FEATUREENTITYID:" + featureId + " FEATURECODE:"
						+ featureCode);

				tmfInfo.setEntityType(type);
				tmfInfo.setEntityId("0".equals(Integer.toString(id)) ? CHEAT
						: Integer.toString(id));
				tmfInfo.setModelType(modelType);
				tmfInfo.setModelId("0".equals(Integer.toString(id)) ? CHEAT
						: Integer.toString(modelId));
				tmfInfo.setMachtypeatr(machtypeatr);
				tmfInfo.setModelatr(modelatr);
				tmfInfo.setFeatureType(featureType);
				tmfInfo.setFeatureId("0".equals(Integer.toString(id)) ? CHEAT
						: Integer.toString(featureId));
				tmfInfo.setFeatureCode(featureCode);
			}
			return tmfInfo;

		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 
	 * @param type
	 * @param id
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void delCache(String type, int id)
			throws SQLException, MiddlewareException {

		StringBuffer strbSQL;
		addDebug("update cache table---------------");
		strbSQL = new StringBuffer();
		strbSQL.append("update cache.xmlidlcache set xmlcachevalidto=current timestamp where xmlentitytype ='"
				+type+"' and xmlentityid="+id
				+" and xmlcachevalidto>current timestamp");
		addDebug("SQL:" + strbSQL);
		PreparedStatement statement = null;
		try {
			statement = m_db.getODSConnection().prepareStatement(
					strbSQL.toString());
			int row =statement.executeUpdate();
			if(row==1){
				addDebug("cache table update success");
			}else if(row==0){
				addDebug("No row was found for the xml in cache table");
			}else{
				addDebug("cache table update failed");
			}
			
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 
	 * @param xmlmsgMap
	 * @param rootEntity
	 * @throws DOMException
	 * @throws MissingResourceException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private void sentToMQ(HashMap xmlmsgMap, EntityItem rootEntity)
			throws DOMException, MissingResourceException,
			ParserConfigurationException, TransformerException {
		String val = PokUtils.getAttributeFlagValue(rootEntity,
				attrXMLABRPROPFILE);
		Vector vct = new Vector();
		if (val != null) {
			// parse the string into substrings
			StringTokenizer st = new StringTokenizer(val, PokUtils.DELIMITER);
			while (st.hasMoreTokens()) {
				vct.addElement(st.nextToken());
			}
		}

		if (xmlmsgMap.size() == 0) {
			processMQZero(xmlmsgMap, vct);
			addDebug("send zero report when xmlmsgMap.size is 0");
		} else {
			addDebug("send normal report when xmlmsgMap.size > 0");
			processMQ(xmlmsgMap, vct);
		}

		// release memory
		xmlmsgMap.clear();
	}

	/**
	 * @param xmlmsgMap
	 * @param mqVct
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void processMQZero(HashMap xmlmsgMap, Vector mqVct)
			throws ParserConfigurationException, DOMException,
			TransformerException, MissingResourceException {
		if (mqVct == null) {
			addDebug(rootEntityType
					+ ": No MQ properties files, nothing will be generated.");
			// NOT_REQUIRED = Not Required for {0}.
			addXMLGenMsg("NOT_REQUIRED", rootEntityType);
		} else {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument(); // Create
			String nodeName = "DELETEXML_MSGS";
			String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/"
					+ nodeName;

			Element parent = (Element) document
					.createElementNS(xmlns, nodeName);
			parent.appendChild(document.createComment("DELETEXML_MSGS Version "
					+ XMLMQAdapter.XMLVERSION10 + " Mod "
					+ XMLMQAdapter.XMLMOD10));
			// create the root
			document.appendChild(parent);
			parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
					xmlns);

			Element elem = (Element) document.createElement("DTSOFMSG");
			elem.appendChild(document.createTextNode(getNow()));
			parent.appendChild(elem);
			elem = (Element) document.createElement("FROMMSGDTS");
			elem.appendChild(document.createTextNode(t1DTS));
			parent.appendChild(elem);
			elem = (Element) document.createElement("TOMSGDTS");
			elem.appendChild(document.createTextNode(t2DTS));
			parent.appendChild(elem);
			elem = (Element) document.createElement("PDHDOMAIN");
			elem.appendChild(document.createTextNode(pdhdomain));
			parent.appendChild(elem);
			Element msglist = (Element) document.createElement("ENTITYLIST");
			parent.appendChild(msglist);

			String xml = transformXML(document);
			addDebug("the final xml:" + xml);
			boolean ifpass = false;

			String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getValue(abrName, "_" + rootEntityType + "_XSDNEEDED",
							"NO");
			if ("YES".equals(ifNeed.toUpperCase())) {
				String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
						.getValue(abrName, "_" + rootEntityType + "_XSDFILE",
								"NONE");
				if ("NONE".equals(xsdfile)) {
					addError("there is no xsdfile for " + rootEntityType
							+ " defined in the propertyfile ");
				} else {
					long rtm = System.currentTimeMillis();
					Class cs = this.getClass();
					StringBuffer debugSb = new StringBuffer();
					ifpass = ABRUtil.validatexml(cs, debugSb, xsdfile, xml);
					if (debugSb.length() > 0) {
						String s = debugSb.toString();
						if (s.indexOf("fail") != -1)
							addError(s);
						addOutput(s);
					}
					long ltm = System.currentTimeMillis();
					addDebug(
							D.EBUG_DETAIL,
							"Time for validation: "
									+ Stopwatch.format(ltm - rtm));
					if (ifpass) {
						addDebug("the xml for " + rootEntityType
								+ " passed the validation");
					}
				}
			} else {
				addOutput("the xml for " + rootEntityType
						+ " doesn't need to be validated");
				ifpass = true;
			}

			if (xml != null && ifpass) {
				notify(rootEntityType, xml, mqVct);
			}
			document = null;
		}
	}

	/**
	 * @param xmlmsgMap
	 * @param mqVct
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void processMQ(HashMap xmlmsgMap, Vector mqVct)
			throws ParserConfigurationException, DOMException,
			TransformerException, MissingResourceException {
		if (mqVct == null) {
			addDebug(rootEntityType
					+ ": No MQ properties files, nothing will be generated.");
			// NOT_REQUIRED = Not Required for {0}.
			addXMLGenMsg("NOT_REQUIRED", rootEntityType);
		} else {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument(); // Create
			String nodeName = "DELETEXML_MSGS";
			String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/"
					+ nodeName;

			// Element parent = (Element)
			// document.createElement("WWCOMPAT_UPDATE");
			Element parent = (Element) document
					.createElementNS(xmlns, nodeName);
			parent.appendChild(document.createComment("DELETEXML_MSGS Version "
					+ XMLMQAdapter.XMLVERSION10 + " Mod "
					+ XMLMQAdapter.XMLMOD10));
			// create the root
			document.appendChild(parent);
			parent.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
					xmlns);

			Element elem = (Element) document.createElement("DTSOFMSG");
			elem.appendChild(document.createTextNode(getNow()));
			parent.appendChild(elem);
			elem = (Element) document.createElement("FROMMSGDTS");
			elem.appendChild(document.createTextNode(t1DTS));
			parent.appendChild(elem);
			elem = (Element) document.createElement("TOMSGDTS");
			elem.appendChild(document.createTextNode(t2DTS));
			parent.appendChild(elem);
			elem = (Element) document.createElement("PDHDOMAIN");
			elem.appendChild(document.createTextNode(pdhdomain));
			parent.appendChild(elem);

			Element entitylist = (Element) document.createElement("ENTITYLIST");
			parent.appendChild(entitylist);
			if (xmlmsgMap.size() > 0) {
				if (xmlmsgMap.containsKey("PRODSTRUCT")) {
					Vector entitylist_xml = (Vector) xmlmsgMap
							.get("PRODSTRUCT");
					for (int i = 0; i < entitylist_xml.size(); i++) {
						ADSDELPRODSTRUCT entitymsg = (ADSDELPRODSTRUCT) entitylist_xml
								.elementAt(i);

						Element entityelement = (Element) document
								.createElement("ENTITYELEMENT");
						entitylist.appendChild(entityelement);

						elem = (Element) document.createElement("ENTITYTYPE");
						elem.appendChild(document.createTextNode(entitymsg
								.getEntityType()));
						entityelement.appendChild(elem);
						elem = (Element) document.createElement("ENTITYID");
						elem.appendChild(document.createTextNode(entitymsg
								.getEntityId()));
						entityelement.appendChild(elem);
						elem = (Element) document.createElement("ACTIVITY");
						elem.appendChild(document.createTextNode("Delete"));
						entityelement.appendChild(elem);
						elem = (Element) document
								.createElement("MODELENTITYTYPE");
						elem.appendChild(document.createTextNode(entitymsg
								.getModelType()));
						entityelement.appendChild(elem);
						elem = (Element) document
								.createElement("MODELENTITYID");
						elem.appendChild(document.createTextNode(entitymsg
								.getModelId()));
						entityelement.appendChild(elem);
						elem = (Element) document.createElement("MACHTYPE");
						elem.appendChild(document.createTextNode(entitymsg
								.getMachtypeatr()));
						entityelement.appendChild(elem);
						elem = (Element) document.createElement("MODEL");
						elem.appendChild(document.createTextNode(entitymsg
								.getModelatr()));
						entityelement.appendChild(elem);
						elem = (Element) document
								.createElement("FEATUREENTITYTYPE");
						elem.appendChild(document.createTextNode(entitymsg
								.getFeatureType()));
						entityelement.appendChild(elem);
						elem = (Element) document
								.createElement("FEATUREENTITYID");
						elem.appendChild(document.createTextNode(entitymsg
								.getFeatureId()));
						entityelement.appendChild(elem);
						elem = (Element) document.createElement("FEATURECODE");
						elem.appendChild(document.createTextNode(entitymsg
								.getFeatureCode()));
						entityelement.appendChild(elem);
					}
				}
				// release memory
				// xmlmsginfo.dereference();
			}

			String xml = transformXML(document);
			// xsd validation
			boolean ifpass = false;

			String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getValue(abrName, "_" + rootEntityType + "_XSDNEEDED",
							"NO");
			if ("YES".equals(ifNeed.toUpperCase())) {
				String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
						.getValue(abrName, "_" + rootEntityType + "_XSDFILE",
								"NONE");
				if ("NONE".equals(xsdfile)) {
					addError("there is no xsdfile for " + rootEntityType
							+ " defined in the propertyfile ");
				} else {
					long rtm = System.currentTimeMillis();
					Class cs = this.getClass();
					StringBuffer debugSb = new StringBuffer();
					ifpass = ABRUtil.validatexml(cs, debugSb, xsdfile, xml);
					if (debugSb.length() > 0) {
						String s = debugSb.toString();
						if (s.indexOf("fail") != -1)
							addError(s);
						addOutput(s);
					}
					long ltm = System.currentTimeMillis();
					addDebug(
							D.EBUG_DETAIL,
							"Time for validation: "
									+ Stopwatch.format(ltm - rtm));
					if (ifpass) {
						addDebug("the xml for " + rootEntityType
								+ " passed the validation");
					}
				}
			} else {
				addOutput("the xml for " + rootEntityType
						+ " doesn't need to be validated");
				ifpass = true;
			}

			if (xml != null && ifpass) {
				notify(rootEntityType, xml, mqVct);
			}
			document = null;
		}
	}

	/**********************************
	 * generate the xml
	 */
	protected String transformXML(Document document)
			throws ParserConfigurationException,
			javax.xml.transform.TransformerException {
		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		// OIDH can't handle whitespace..
		// trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "no");
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		// create string from xml tree
		java.io.StringWriter sw = new java.io.StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(document);
		trans.transform(source, result);
		String xmlString = XMLElem.removeCheat(sw.toString());

		// (ADSABRSTATUS)mqAbr.addDebug
		// transform again for user to see in rpt
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		sw = new java.io.StringWriter();
		result = new StreamResult(sw);
		trans.transform(source, result);
		addUserXML(XMLElem.removeCheat(sw.toString()));

		return xmlString;
	}

	/**********************************
	 * feed the xml
	 */
	protected void notify(String rootInfo, String xml, Vector mqVct)
			throws MissingResourceException {
		MessageFormat msgf = null;
		// Vector mqVct = mqAbr.getMQPropertiesFN();
		int sendCnt = 0;
		boolean hasFailure = false;

		// write to each queue, only one now, but leave this just in case
		for (int i = 0; i < mqVct.size(); i++) {

			String mqProperties = (String) mqVct.elementAt(i);
			addDebug("in notify looking at prop file " + mqProperties);
			try {
				ResourceBundle rsBundleMQ = ResourceBundle.getBundle(
						mqProperties, getLocale(getProfile().getReadLanguage()
								.getNLSID()));
				Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
				boolean bNotify = ((Boolean) ht.get(MQUsage.NOTIFY))
						.booleanValue();
				ht.put(MQUsage.MQCID, getMQCID()); // add to hashtable for CID
													// to MQ
				ht.put(MQUsage.XMLTYPE, "ADS"); // add to hashtable to indicate
												// ADS msg
				Hashtable userProperties = MQUsage.getUserProperties(
						rsBundleMQ, getMQCID());
				if (bNotify) {
					try {
						addDebug("User infor " + userProperties);
						MQUsage.putToMQQueueWithRFH2(
								"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
										+ xml, ht, userProperties);
						// SENT_SUCCESS = XML was generated and sent
						// successfully for {0} {1}.
						msgf = new MessageFormat(
								rsBundle.getString("SENT_SUCCESS"));
						args[0] = mqProperties;
						args[1] = rootInfo;
						addOutput(msgf.format(args));
						sendCnt++;
						if (!hasFailure) { // dont overwrite a failed notice
							// xmlgen =
							// rsBundle.getString("SUCCESS");//"Success";
							addXMLGenMsg("SUCCESS", rootInfo);
							addDebug("sent successfully to prop file "
									+ mqProperties);
						}
					} catch (com.ibm.mq.MQException ex) {
						// MQ_ERROR = Error: An MQSeries error occurred for {0}:
						// Completion code {1} Reason code {2}.
						// FAILED = Failed sending {0}
						addXMLGenMsg("FAILED", rootInfo);
						hasFailure = true;
						msgf = new MessageFormat(rsBundle.getString("MQ_ERROR"));
						args[0] = mqProperties + " " + rootInfo;
						args[1] = "" + ex.completionCode;
						args[2] = "" + ex.reasonCode;
						addError(msgf.format(args));
						ex.printStackTrace(System.out);
						addDebug("failed sending to prop file " + mqProperties);
					} catch (java.io.IOException ex) {
						// MQIO_ERROR = Error: An error occurred when writing to
						// the MQ message buffer for {0}: {1}
						addXMLGenMsg("FAILED", rootInfo);
						hasFailure = true;
						msgf = new MessageFormat(
								rsBundle.getString("MQIO_ERROR"));
						args[0] = mqProperties + " " + rootInfo;
						args[1] = ex.toString();
						addError(msgf.format(args));
						ex.printStackTrace(System.out);
						addDebug("failed sending to prop file " + mqProperties);
					}
				} else {
					// NO_NOTIFY = XML was generated but NOTIFY was false in the
					// {0} properties file.
					msgf = new MessageFormat(rsBundle.getString("NO_NOTIFY"));
					args[0] = mqProperties;
					addError(msgf.format(args));
					// {0} "Not sent";
					addXMLGenMsg("NOT_SENT", rootInfo);
					addDebug("not sent to prop file " + mqProperties
							+ " because Notify not true");

				}
			} catch (MissingResourceException mre) {
				addXMLGenMsg("FAILED", mqProperties + " " + rootInfo);
				hasFailure = true;
				addError("Prop file " + mqProperties + " " + rootInfo
						+ " not found");
			}

		} // end mq loop
		if (sendCnt > 0 && sendCnt != mqVct.size()) { // some went but not all
			addXMLGenMsg("ALL_NOT_SENT", rootInfo);// {0} "Not sent to all";
		}
	}

	/**********************************************************************************
	 * Get Locale based on NLSID
	 *
	 * @return java.util.Locale
	 */
	public static Locale getLocale(int nlsID) {
		Locale locale = null;
		switch (nlsID) {
		case 1:
			locale = Locale.US;
			break;
		case 2:
			locale = Locale.GERMAN;
			break;
		case 3:
			locale = Locale.ITALIAN;
			break;
		case 4:
			locale = Locale.JAPANESE;
			break;
		case 5:
			locale = Locale.FRENCH;
			break;
		case 6:
			locale = new Locale("es", "ES");
			break;
		case 7:
			locale = Locale.UK;
			break;
		default:
			locale = Locale.US;
			break;
		}
		return locale;
	}

	protected void addUserXML(String s) {
		if (userxmlPw != null) {
			userxmlPw.println(s);
			userxmlPw.flush();
		} else {
			userxmlSb.append(ADSABRSTATUS.convertToHTML(s) + NEWLINE);
		}
	}

	/**********************************
	 * MQ-Series CID
	 */
	public String getMQCID() {
		return "DELETEXML_MSGS";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "ADSDELXMLABR";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.4 $";
	}

}

class ADSDELENTITY {
	private int id;
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
