// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

// $Log: AITFEEDABRSTATUS.java,v $
// Revision 1.3  2017/09/06 12:27:26  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.2  2015/10/14 07:06:27  wangyul
// Defect 1409342 Block zSeries Feature Conversions for AIT Feed
//
// Revision 1.1  2015/08/05 09:27:43  wangyul
// EACM to AIT feed
//
public class AITFEEDABRSTATUS extends PokBaseABR {
	private ResourceBundle rsBundle = null;
	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer xmlgenSb = new StringBuffer();
	private StringBuffer userxmlSb= new StringBuffer();
    private String t2DTS = "&nbsp;";  // T2
    private Object[] args = new String[10];
    private String uniqueKey = String.valueOf(System.currentTimeMillis());
    private Hashtable countryCodeMapping = null;
    private boolean isZipMailNeeded = true; 
   
	private static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel("AITFEEDABRSTATUS");
	private static final String NOT_GENERATE_FC_XML_DOMAINS_STRING = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("AITFEEDABRSTATUS", "_notGenerateANNFCTRANSACTION_Domains");
    private static final char[] FOOL_JTEST = {'\n'};
    protected static final String NEWLINE = new String(FOOL_JTEST);
    private static final Hashtable READ_LANGS_TBL;  // tbl of NLSitems, 1 for each profile language
	private static final String[] AITFEEDABR_STRINGS = new String[] {
		"COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNTMFABR",
		"COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNFCTRANSACTIONABR",
		"COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNMODELCONVERTABR",
		"COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNCOMPATABR"
	};
	
	private static final String ANNFCTRANSACTION_ABR_CLASSNAME = "COM.ibm.eannounce.abr.sg.adsxmlbh1.AITFEEDANNFCTRANSACTIONABR";
	
	private static final Vector ANNTYPE_FOR_XML;
	static {
		ANNTYPE_FOR_XML = new Vector();
		ANNTYPE_FOR_XML.add("19");	// New
		ANNTYPE_FOR_XML.add("14");	// End of Life - Withdrawal from Marketing
	}
    static {
        READ_LANGS_TBL = new Hashtable();
        // fill in with all languages defined in profile, actual languages used is based on properties file
        READ_LANGS_TBL.put(""+Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
    }

	/**
	 * Execute ABR
	 */
	public void execute_run() {
		String navName = "";
		MessageFormat msgf;
		
        try {
        	long startTime = System.currentTimeMillis();
        	
			start_ABRBuild(false); // don't pull VE yet

			// get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			// Default set to pass
            setReturnCode(PASS);
            
			// get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof, "dummy"), 
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			
			// get root ANNOUNCEMENT from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			
			// Generate a unique key for the xml file, avoid name impact
			uniqueKey = rootEntity.getKey() + String.valueOf(System.currentTimeMillis());
			
			// Domain filter when we send feature transaction
			String domainValue = PokUtils.getAttributeFlagValue(rootEntity, "PDHDOMAIN");
			addDebug("RootEntity:" + rootEntity.getKey() + " PDHDOMAIN:" + domainValue);
			addDebug("RootEntity:" + rootEntity.getKey() + " Not generate feature transaction xml domians:" + NOT_GENERATE_FC_XML_DOMAINS_STRING);
			Vector notGenerateFCXMLDomainVector = getDomainVector(NOT_GENERATE_FC_XML_DOMAINS_STRING);
			
			// Check anntype, only New(19) and End of Life - Withdrawal from Marketing(14) will generate xml
			String annType = PokUtils.getAttributeFlagValue(rootEntity, "ANNTYPE");
			addDebug("RootEntity:" + rootEntity.getKey() + " ANNTYPE:" + annType);
			String errmsg = rootEntity.getKey();
			if (ANNTYPE_FOR_XML.contains(annType)) {
				Vector xmlFiles = new Vector(1);
				for (int i = 0; i < AITFEEDABR_STRINGS.length; i++) {
					long abrStartTime = System.currentTimeMillis();
					String clsname = AITFEEDABR_STRINGS[i];
					// The FCTRANSACTION has huge data for zSeries, and AIT don't need them, so we add this filter in properties file
					if (ANNFCTRANSACTION_ABR_CLASSNAME.equals(clsname) && notGenerateFCXMLDomainVector.contains(domainValue)) {
						addOutput("Not generate ANNFCTRANSACTION xml for domian : " + domainValue);
						addDebug("Not generate ANNFCTRANSACTION xml for domian : " + domainValue);
					} else {
						addDebug("creating instance of AIT feed ABR  = " + clsname);
						
						AITFEEDXML mqAbr = (AITFEEDXML) Class.forName(clsname).newInstance();
						addDebug(getShortClassName(mqAbr.getClass()) + " " + mqAbr.getVersion());
						
//						setT2DTS(rootEntity);

			            if (getReturnCode() == PASS) {
			            	// switch the role and use the time2 DTS (current change)
//			                Profile profileT2 = switchRole(mqAbr.getRoleCode());
//			                addDebug(rootEntity.getKey() + " T2=" + t2DTS);
//			                profileT2.setValOnEffOn(t2DTS, t2DTS);
//			                profileT2.setEndOfDay(t2DTS); // used for notification time
//			                profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
			                
	                        try {
								mqAbr.processThis(this, m_prof, rootEntity);
								xmlFiles.add(mqAbr.getXMLFileName());
							} catch (FileNotFoundException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (TransformerConfigurationException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (MiddlewareRequestException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (SAXException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (SQLException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (MiddlewareException e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							} catch (Exception e) {
								addXMLGenMsg("FAILED", errmsg);
								throw e;
							}
			            }
			            addOutput("Generate XML " + mqAbr.getXMLFileName() + " successfully");
			            addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - abrStartTime) + " for abr:" + clsname);
					}
				}
				
				// zip xmls
				FileOutputStream fos = null;
				ZipOutputStream zos = null;
				String fn = m_abri.getFileName();
		        int extid = fn.lastIndexOf(".");
		        String zipFileName = fn.substring(0,extid+1)+"zip";
		        File zipFile = new File(zipFileName);
				try {
					int buffer = 2048;
					fos = new FileOutputStream(zipFile);
					zos = new ZipOutputStream(fos);
					for (int i = 0; i < xmlFiles.size(); i++) {
						String fileName = (String)xmlFiles.get(i);
						File xmlFile = new File(ABRServerProperties.getOutputPath() + fileName + getABRTime());
						if (xmlFile.exists()) {
							zos.putNextEntry(new ZipEntry(fileName));
							BufferedInputStream bis = null;
							try {
								bis = new BufferedInputStream(new FileInputStream(xmlFile));
								int count;
								byte data[] = new byte[buffer];  
						        while ((count = bis.read(data, 0, buffer)) != -1) {  
						        	zos.write(data, 0, count);  
						        }
						        zos.closeEntry();
						        zos.flush();
						        addDebug("Zip file " + fileName + getABRTime() + " successfully");
							} finally {
								if (bis != null) {
									bis.close(); 
								}
							}
							// delete xml
							xmlFile.delete();
						} else {
							addError("Zip file..., Missing XML file " + fileName);
							isZipMailNeeded = false;
						}
					}
				} finally {
					if (zos != null) {
						zos.flush();
						zos.close();
					}
					if (fos != null) {
						fos.flush();
						fos.close();
					}
				}
				xmlFiles.clear();
				xmlFiles = null;
				
				// Send mail
				if (isZipMailNeeded) {
					FileInputStream fisBlob = null;
					try {
						fisBlob = new FileInputStream(zipFile);
						int iSize = fisBlob.available();
						byte[] baBlob = new byte[iSize];
						fisBlob.read(baBlob);
						setAttachByteForDG(baBlob);
						getABRItem().setFileExtension("zip");
					} finally {
						if (fisBlob != null) {
							fisBlob.close();
						}
					}
				}
				
				// check keep file
				if (!getABRItem().getKeepFile()){
					if (zipFile.exists()) {
						zipFile.delete();
						addDebug("Check the keep file is false, delete the zip file");
					}
				}
			} else {
				// not generate xml
				addXMLGenMsg("ANNTYPE_NOT_LISTED", errmsg);
			}
				
            //NAME is navigate attributes
            navName = getNavigationName(rootEntity);
            addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - startTime));
		} catch (Throwable exc) {
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
		} finally {
			if (t2DTS.equals("&nbsp;")){
            	t2DTS= getNow();
            }
			setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
		}
        
		// Print everything up to </html>
		// Insert Header into beginning of report
        println(EACustom.getDocTypeHtml());
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
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		String header2 = buildAitAbrHeader();
		// XML_MSG= XML Message
		String info = header1 + header2 + "<pre>"
				+ rsBundle.getString("RESULT_MSG") + "<br />"
				+ userxmlSb.toString() + "</pre>" + NEWLINE;
		rptSb.insert(0, info);
		println(rptSb.toString());
		printDGSubmitString();
		println(EACustom.getTOUDiv());
        buildReportFooter();
		
		// release memory
		m_elist.dereference();
		m_elist = null;
		rsBundle = null;
		args = null;
		msgf = null;
		userxmlSb = null;
		rptSb = null;
		xmlgenSb = null;
	}
	

    
	public String getDescription() {
		return "AITFEEDABRSTATUS";
	}

	public String getABRVersion() {
		return "1.0";
	}
	
	/**
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }
    
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= DEBUG_LVL) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
    
	/**
	 * add msg as an html comment if meets debuglevel set in abr.server.properties
	 * @param debuglvl
	 * @param msg
	 */
	protected void addDebugComment(int debuglvl, String msg){
		if (debuglvl <= DEBUG_LVL) {
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}
    
    protected EntityList getEntityList(Profile prof, String veName, EntityItem item) throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, veName), 
				new EntityItem[] { new EntityItem(null, prof, item.getEntityType(), item.getEntityID()) });
		return list;
    }
    
    /**
     * search action only returns navigate attributes, pull a full extract on the 
     * EntityItems returned from search to see the other attributes 
     * @param prof
     * @param item
     * @return EntityItem
     * @throws MiddlewareRequestException
     * @throws SQLException
     * @throws MiddlewareException
     */
    protected EntityItem getEntityItem(Profile prof, EntityItem item) throws MiddlewareRequestException, SQLException, MiddlewareException {
    	EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, "dummy"), 
				new EntityItem[] { new EntityItem(null, prof, item.getEntityType(), item.getEntityID()) });
    	return list.getParentEntityGroup().getEntityItem(0);
	}

	/**
	 * search action only returns navigate attributes, pull a full extract on the
	 * EntityItems returned from search to see the other attributes
	 *
	 * @param prof
	 * @param entityType
	 * @param entityId
	 * @return EntityItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected EntityItem getEntityItem(Profile prof, String entityType, int entityId) throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, "dummy"),
				new EntityItem[] { new EntityItem(null, prof, entityType, entityId) });
		return list.getParentEntityGroup().getEntityItem(0);
	}
    
	/**
	 * get database
	 */
	protected Database getDB() {
		return m_db;
	}
	
	protected String getABRTime() {
		return uniqueKey;
	}
	
	protected String getGenareaName(String countryCode) {
		if (countryCodeMapping == null) {
			countryCodeMapping = getCountryCodeMapping();
		} 
		return (String)countryCodeMapping.get(countryCode);
	}
	
	/**
	 * add msg to report output
	 */
	private void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**
	 * build xml generation msg
	 */
	private void addXMLGenMsg(String rsrc, String info) {
		MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
		Object args[] = new Object[] { info };
		xmlgenSb.append(msgf.format(args) + "<br />");
	}
	
	/**
	 * EACM to AIT Triggered ABRs
	 *
	 * The Report should identify: 
	 * - USERID (USERTOKEN) 
	 * - Role 
	 * - Workgroup 
	 * - Date/Time
	 * - Action Taken
	 */
	private String buildAitAbrHeader() {
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date/Time: </th><td>{3}</td></tr>"+NEWLINE +
        "<tr><th>Action Taken: </th><td>{4}</td></tr>"+NEWLINE+
        "</table>"+NEWLINE+
        "<!-- {5} -->" + NEWLINE;
        MessageFormat msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = t2DTS;
        args[4] = "AIT feed trigger<br/>" + xmlgenSb.toString();
        args[5] = getABRVersion();
        return msgf.format(args);
	}
	
	/**
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			navName.append(" ");
		}
		return navName.toString();
	}
	
	private Hashtable getCountryCodeMapping() {
		Hashtable countryCodeMapping = new Hashtable();
		Statement st = null;
		String sql = "SELECT genareaname_fc,genareacode FROM price.generalarea WHERE genareatype='Country' AND nlsid=1 WITH UR";
		try {
			st = m_db.getPDHConnection().createStatement();
			ResultSet result = st.executeQuery(sql);
			while(result.next()) {
				countryCodeMapping.put(result.getString(1).trim(), result.getString(2).trim());
			}
		} catch (MiddlewareException e){
			addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		} catch (SQLException rx) {
			addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		addDebug("Get country mapping records:" + countryCodeMapping.size());
		return countryCodeMapping;
	}
	
	private Vector getDomainVector(String domainsString) {
		Vector domainVector = new Vector();
		StringTokenizer st = new StringTokenizer(domainsString, ",");
		while (st.hasMoreTokens()) {
			String domain = st.nextToken().trim();
			domainVector.add(domain);
		}
		return domainVector;
	}
}
