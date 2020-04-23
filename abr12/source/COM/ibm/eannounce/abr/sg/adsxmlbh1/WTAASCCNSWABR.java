package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import COM.ibm.eannounce.abr.sg.adsxmlbh1.WTAASException;

import com.ibm.transform.oim.eacm.util.PokUtils;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WTAASCCNSWABR extends PokBaseABR{
	private ResourceBundle rsBundle = null;
	private String annNumber = "";
	private String annDate = "";
	
	private File zipFile = null; 
	private HashMap prodInfo = new HashMap();
	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer xmlgenSb = new StringBuffer();
	private StringBuffer userxmlSb = new StringBuffer();
	private String t2DTS = "&nbsp;"; // T2
	private Object[] args = new String[10];
	public final static String RPTPATH = "_rptpath";	
	private static final char[] FOOL_JTEST = { '\n' };
	protected static final String NEWLINE = new String(FOOL_JTEST);
	private static int DEBUG_LVL = ABRServerProperties
			.getABRDebugLevel("WTAASCCNSWABR");
	

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
			rsBundle = ResourceBundle.getBundle(getClass().getName(),
					ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			// Default set to pass
			setReturnCode(PASS);

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls		
			
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null,
					m_db, m_prof, "dummy"), new EntityItem[] { new EntityItem(
					null, m_prof, getEntityType(), getEntityID()) });

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			//addDebug("DEBUG: rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			
			String docNum = getAttributeValue(rootEntity, "CDOCNO");
			addDebug("ANNOUNCEMENT DOCNUM (CDOCNO): " + docNum);
			
			annDate = getAttributeValue(rootEntity, "ANNDATE");
			addDebug("ANNOUNCEMENT ANNDATE: " + annDate);
			
			annNumber = getAttributeValue(rootEntity, "ANNNUMBER");
			addDebug("RFA number: " + annNumber);
			// NAME is navigate attributes
			String annType = getAttributeValue(rootEntity, "ANNTYPE");
			addDebug("ANNOUNCEMENT type: " + annType);
						
			navName = getNavigationName(rootEntity);
			if (getReturnCode() == PASS) {
				processThis(this, m_prof, rootEntity);
				// generate file
				boolean createfile=generateOutputFile(prodInfo);
				if (createfile) {
					//send mail
					sendMail(zipFile);
					
					// check keep file
					if (!getABRItem().getKeepFile()){
						if (zipFile.exists()) {
							zipFile.delete();
							addDebug("Check the keep file is false, delete the zip file");
						}
					}
				}else{
					setReturnCode(FAIL);
				}
				
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
			if (("&nbsp;").equals(t2DTS)) {
				t2DTS = getNow();
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
		String header2 = buildAbrHeader();
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
	
	/**
	 * get ANN related info
	 * 
	 * @param abr
	 * @param profileT2
	 * @param rootEntity
	 * @return 
	 * @throws TransformerConfigurationException
	 * @throws SAXException
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws IOException
	 */
	public void processThis(WTAASCCNSWABR abr, Profile profileT2, EntityItem rootEntity) throws TransformerConfigurationException,
			SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
		
			boolean isGetModelInfo = getModelInfo(rootEntity);
			if(isGetModelInfo){
				getSWFeatureInfo(rootEntity);
			}else{
				getProdInfo(rootEntity);
			}
			
			// get related price info
			long startTime = System.currentTimeMillis();			
			getPriceInfo(prodInfo);
			addDebug("get price info Time: "
					+ Stopwatch.format(System.currentTimeMillis() - startTime));											
			
		
		
	}

	/**
	 * Extract data using ann-->avail-->swtmf-->model&swfeature
	 * @param rootEntity
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void getProdInfo(EntityItem rootEntity) throws SQLException, MiddlewareException{
		String key = null;
		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		try {
			statement = m_db.getPDHConnection().prepareStatement(
					queryForTMFInfo(rootEntity));
			result = statement.executeQuery();
			while (result.next()) {
				String availId = result.getString(6);
				String modelId = result.getString(8);

				String docNum = result.getString(1);
				String annDate = result.getString(2);
				String annNumber = result.getString(3);
				String annType = result.getString(4);
				String rfaShortTitle = result.getString(5);
				
				String effectiveDate = result.getString(7);
				String productVrm = result.getString(9);
				String modelAtr = result.getString(10);
				String machtypeAtr = result.getString(11);
				String invName = result.getString(12);
				String modmktgDesc = result.getString(13);
				String sftWarry = result.getString(14);
				String usgliApp = result.getString(15);						
				String volumedis = result.getString(16);
				String keyLock = result.getString(17);
				String educAllow = result.getString(18);
				
				String swfeatureId = result.getString(19);
				String featureCode = result.getString(20);
				String pricedFeature = result.getString(21);
				String chargeOption = result.getString(22);
				String swfeatdesc = result.getString(23);
				
				
				key = availId + "_" + modelId;
				if(prodInfo.get(key)!=null){
					SWFEATURE swFeature = new SWFEATURE();
					swFeature.setSwFeatureId(swfeatureId);
					swFeature.setFEATURECODE(featureCode);
					swFeature.setPRICEDFEATURE(pricedFeature);
					swFeature.setCHARGEOPTION(chargeOption);
					swFeature.setSFINVNAME(swfeatdesc);	
					WTAASCCNSWENTITY w = (WTAASCCNSWENTITY)prodInfo.get(key);
					ArrayList ar = (ArrayList)w.getSWFEATURES();
					ar.add(swFeature);
					
				}
				else{
					WTAASCCNSWENTITY entity = new WTAASCCNSWENTITY();
					entity.setCDOCNO(docNum);
					entity.setANNDATE(annDate);
					entity.setANNNUMBER(annNumber);
					entity.setANNTYPE(annType);
					entity.setRFASHRTTITLE(rfaShortTitle);
					entity.setAVAILID(availId);
					entity.setEFFECTIVEDATE(effectiveDate);
					entity.setMODELID(modelId);
					entity.setPRODUCTVRM(productVrm);
					entity.setMODELATR(modelAtr);
					entity.setMACHTYPEATR(machtypeAtr);
					entity.setINVNAME(invName);
					entity.setMODMKTGDESC(modmktgDesc);
					entity.setUSGLIAPP(usgliApp);
					entity.setSFTWARRY(sftWarry);
					entity.setVOLUMEDISCOUNTELIG(volumedis);
					entity.setEDUCALLOWMHGHSCH(educAllow);
					entity.setKEYLCKPROTCT(keyLock);

					SWFEATURE swFeature = new SWFEATURE();
					swFeature.setSwFeatureId(swfeatureId);
					swFeature.setFEATURECODE(featureCode);
					swFeature.setPRICEDFEATURE(pricedFeature);
					swFeature.setCHARGEOPTION(chargeOption);
					swFeature.setSFINVNAME(swfeatdesc);
					
					ArrayList swFeatures= new ArrayList();					
					swFeatures.add(swFeature);
					entity.setSWFEATURES(swFeatures);
					
					prodInfo.put(key, entity);								
				}
			}
		}finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

			
	}

	/**
	 * Extract data using ann-->avail-->model
	 * @param rootEntity
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private boolean getModelInfo(EntityItem rootEntity) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		boolean hasModelInfo = false;
		  
		try {
			Connection conn = m_db.getPDHConnection();
				
			statement = conn.prepareStatement(queryForModelInfo(rootEntity));
			result = statement.executeQuery();		
											
			while(result.next()){
				String availId = result.getString(6);
				String modelId = result.getString(8);

				String docNum = result.getString(1);
				String annDate = result.getString(2);
				String annNumber = result.getString(3);
				String annType = result.getString(4);
				String rfaShortTitle = result.getString(5);
				
				String effectiveDate = result.getString(7);
				String productVrm = result.getString(9);
				String modelAtr = result.getString(10);
				String machtypeAtr = result.getString(11);
				String invName = result.getString(12);
				String modmktgDesc = result.getString(13);
				String sftWarry = result.getString(14);	
				String usgliApp = result.getString(15);
				String volumedis = result.getString(16);
				String keyLock = result.getString(17); 
				String educAllow = result.getString(18);
				
				
				WTAASCCNSWENTITY entity = new WTAASCCNSWENTITY();
				entity.setCDOCNO(docNum);
				entity.setANNDATE(annDate);
				entity.setANNNUMBER(annNumber);
				entity.setANNTYPE(annType);
				entity.setRFASHRTTITLE(rfaShortTitle);
				entity.setAVAILID(availId);
				entity.setEFFECTIVEDATE(effectiveDate);
				entity.setMODELID(modelId);
				entity.setPRODUCTVRM(productVrm);
				entity.setMODELATR(modelAtr);
				entity.setMACHTYPEATR(machtypeAtr);
				entity.setINVNAME(invName);
				entity.setMODMKTGDESC(modmktgDesc);
				entity.setUSGLIAPP(usgliApp);
				entity.setSFTWARRY(sftWarry);
				entity.setVOLUMEDISCOUNTELIG(volumedis);
				entity.setEDUCALLOWMHGHSCH(educAllow);
				entity.setKEYLCKPROTCT(keyLock);
				
				String key = availId + "_" + modelId;
				prodInfo.put(key, entity);							
				hasModelInfo=true;
			}
			
		}finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return hasModelInfo;
	}

	/**
	 * Extract data based on the model info, ann-->avail-->swtmf-->swfeature
	 * @param rootEntity
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void getSWFeatureInfo(EntityItem rootEntity) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		  
		try {
			Connection conn = m_db.getPDHConnection();
			Iterator entries = prodInfo.entrySet().iterator();
			while(entries.hasNext()){
				Map.Entry entry = (Map.Entry) entries.next();
				WTAASCCNSWENTITY entity = (WTAASCCNSWENTITY) entry.getValue();
				String modelid = entity.getMODELID();
				
				ArrayList swFeatures= new ArrayList();	
									
				statement = conn.prepareStatement(queryForFeatureInfo(modelid, rootEntity));
				result = statement.executeQuery();		
												
				while(result.next()){
					String swfeatureId = result.getString(1);	
					String pricedFeature = result.getString(2);
					String chargeOption = result.getString(3);		
					String swfeatdesc = result.getString(4);
					String featureCode = result.getString(5);
					
					SWFEATURE swFeature = new SWFEATURE();
					swFeature.setSwFeatureId(swfeatureId);
					swFeature.setFEATURECODE(featureCode);
					swFeature.setPRICEDFEATURE(pricedFeature);
					swFeature.setCHARGEOPTION(chargeOption);
					swFeature.setSFINVNAME(swfeatdesc);
														
					swFeatures.add(swFeature);
							
				}
				entity.setSWFEATURES(swFeatures);
			}			
			
		}finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	/**
	 * select price info from ods db's price.price table
	 * 
	 * @param entities
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void getPriceInfo(HashMap entities) throws SQLException,
			MiddlewareException {

		ResultSet result = null;
		PreparedStatement statement = null;
		Iterator entries = entities.entrySet().iterator();
		  
		try {
			Connection conn = m_db.getODSConnection();
			while(entries.hasNext()){
				Map.Entry entry = (Map.Entry) entries.next();
				WTAASCCNSWENTITY entity = (WTAASCCNSWENTITY) entry.getValue();
				ArrayList featureList = entity.getSWFEATURES();
				String modelatr = entity.getMODELATR();
				String machtypeatr = entity.getMACHTYPEATR();
				for(int i = 0; i < featureList.size(); i++){
					SWFEATURE swfeature = (SWFEATURE) featureList.get(i);	
					String featureCode = swfeature.getFEATURECODE();
					String query = queryForFeaturePrice(modelatr, machtypeatr, featureCode);
					
					statement = conn.prepareStatement(query);
					result = statement.executeQuery();
					String priceValue=null;
					int count = 0;
					while(result.next()){
						priceValue = result.getString(1);
						
						addDebug("price info: price_value " + priceValue );
						swfeature.setPRICEVALUE(covertPrice(priceValue,7));	
						
						count ++ ;
					}
					if(count == 0) {
						priceValue = "0      ";
						
						addDebug("price info: price_value " + priceValue);
						swfeature.setPRICEVALUE(priceValue);				
					}
				}
				
			}
			
		}finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Take integer part of price value, also keep length 
	 * @param priceValue
	 * @param len
	 * @return
	 */
	private String covertPrice(String priceValue, int len) {
		String[] arr = splitStr(priceValue, ".");
		String term = arr[0];
		for(int i=term.length();i<len;i++){
			term += " ";
		}
		return term;
	}

	/**
	 * 
	 * @param value
	 * @param len
	 * @param slide
	 * @return
	 */
	private String fixLength(String value, int len, String slide){
		String term = null;
		if(value == null){
			for(int i=4;i<len;i++){
				value += " ";
			}
			term = value;
		}else if("R".equals(slide)){
			for(int i=value.length();i<len;i++){
				value += " ";
			}
			term = value;
		}else if("L".equals(slide)){
			for(int i=value.length();i<len;i++){
				term += " ";
			}
			term += value;
		}
		return term;		
	}
	/**
	 * build SQL for PRODSTRUCT Info
	 * @param rootEntity
	 * @return
	 */
	private String queryForTMFInfo(EntityItem rootEntity){
		StringBuffer strbSQL = new StringBuffer();
		addDebug("Starting process data from DB");
		
		strbSQL.append("select ann.CDOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.ANNTYPE, ann.RFASHRTTITLE, f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.PRODUCTVRM, m.MODELATR, m.MACHTYPEATR, m.INVNAME, m.MODMKTGDESC, m.SFTWARRY, m.USGLIAPP, m.VOLUMEDISCOUNTELIG, m.KEYLCKPROTCT, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid, sf.FEATURECODE, sf.PRICEDFEATURE, sf.CHARGEOPTION, sf.INVNAME from price.announcement ann ");
		strbSQL.append("join price.avail a1 on a1.STATUS='Final' and a1.ANNCODENAME=ann.ANNCODENAME and a1.availType in ('Planned Availability','First Order')");
		strbSQL.append("join opicm.flag f on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		strbSQL.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=f.entityid ");
		strbSQL.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' ");
		strbSQL.append("join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
		strbSQL.append("join price.model m on m.entityid=sw.id2 and m.STATUS='Final' and m.COFCAT='Software' ");
		strbSQL.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' ");
		strbSQL.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' ");
		strbSQL.append("where ann.entityid=");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 and r1.valto>current timestamp and r1.effto>current timestamp order by modelid,availid,featureid with ur");
				
		addDebug("SQL:" + strbSQL);
		return strbSQL.toString();
	}
	/**
	 * build SQL for Model Info
	 * @param rootEntity
	 * @return
	 */
	private String queryForModelInfo(EntityItem rootEntity){
		addDebug("Get ANN related MODEL info---------------");
		StringBuffer strbSQL = new StringBuffer();
		//
		strbSQL.append("select ann.CDOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.ANNTYPE, ann.RFASHRTTITLE, f.entityid as availid,a1.EFFECTIVEDATE,m.entityid as modelid,m.PRODUCTVRM,m.MODELATR,m.MACHTYPEATR,m.INVNAME,m.MODMKTGDESC, m.SFTWARRY, m.USGLIAPP, m.VOLUMEDISCOUNTELIG, m.KEYLCKPROTCT, t.attributevalue as EDUCALLOWMHGHSCH from price.announcement ann  ");
		strbSQL.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
		strbSQL.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		strbSQL.append("join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=f.entityid and r.valto>current timestamp and r.effto>current timestamp ");
		strbSQL.append("join price.model m on m.entityid=r.entity1id and m.STATUS='Final' and m.COFCAT='Software' ");
		strbSQL.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' ");
		strbSQL.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' ");
		strbSQL.append("where ann.entityid = ");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by modelid,availid with ur");

		addDebug("SQL:" + strbSQL);	
		return strbSQL.toString();
	}
	/**
	 * build SQL for SWFEATURE Info
	 * @param modelid
	 * @param rootEntity
	 * @return
	 */
	private String queryForFeatureInfo(String modelid, EntityItem rootEntity){
		addDebug("Get ANN related Feature info---------------");
		StringBuffer strbSQL = new StringBuffer();
		strbSQL.append("select sf.entityid,sf.PRICEDFEATURE,sf.CHARGEOPTION,sf.INVNAME,sf.FEATURECODE from price.announcement ann ");
		strbSQL.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
		strbSQL.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		strbSQL.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=a1.entityid and r1.valto>current timestamp and r1.effto>current timestamp ");
		strbSQL.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' and sw.id2 = ");
		strbSQL.append(modelid);
		strbSQL.append(" join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
		strbSQL.append("where ann.entityid= ");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 with ur");

		addDebug("SQL:" + strbSQL);	
		return strbSQL.toString();
	}
	
	/**
	 * build SQL for SWFEATURE price
	 * @param entity
	 * @return
	 */
	private String queryForFeaturePrice(String modelatr, String machtypeatr, String featureCode) {
		StringBuffer strbSQL = new StringBuffer();
		addDebug("Get SWFEATURE price info---------------");
		strbSQL.append("select  price_value from price.price where action='I' and currency ='CAD' and offering_type='SWFEATURE' and machtypeatr='");
		strbSQL.append(machtypeatr);
		strbSQL.append("' and featurecode='");
		strbSQL.append(featureCode);
		strbSQL.append("' and start_date <= '");
		strbSQL.append(annDate);
		strbSQL.append("' and end_date >'");
		strbSQL.append(annDate);
		strbSQL.append("' and offering = '");
		strbSQL.append(machtypeatr + modelatr);
		strbSQL.append("' and PRICE_POINT_VALUE = '");
		strbSQL.append(featureCode);
		strbSQL.append("' and price_point_type in ('SWF','WSF') and country = 'CA' and onshore='true' with ur");
				
		addDebug("SQL:" + strbSQL);
		addDebug("SWFEATURE info: machtypeatr:" + machtypeatr + ",featurecode:" + featureCode);
		return strbSQL.toString();
		
	}

	/**
	 * Extract ReleaseNum from PRODUCTVRM
	 * @param productVrm
	 * @return
	 * @throws WTAASException
	 */
	private String getReleaseNum(String productVrm) {
		String releaseNum = "";
		if(productVrm == null ||"".equals(productVrm)){
			releaseNum = "0100";
		}else{
			String[] arr = splitStr(productVrm, ".");
			if(arr.length > 1){
				for(int i=1;i<arr.length;i++){
					String term = arr[i].trim();
					if(term.length() < 2){
						term = "0" + term;	
					}else{
						term = term.substring(0,2);
					}
					releaseNum += term;
				}
				if(releaseNum.length() > 4){
					releaseNum = releaseNum.substring(0,4);
				}else if(releaseNum.length() == 2){
					releaseNum += "00";
				}
			}else if (arr.length == 1){
				releaseNum = "0100";
			}
		}
		return releaseNum;
	}

	/**
	 * Extract VersionNum from PRODUCTVRM
	 * @param productVrm
	 * @return
	 * @throws WTAASException
	 */
	private String getVersionNum(String productVrm) {
		String versionNum = "";
		if(productVrm == null ||"".equals(productVrm)){
			versionNum = "01";
		}else{
			
			String[] arr = splitStr(productVrm, ".");
			String term = arr[0].trim();
			if(term.startsWith("V")){
				term = term.substring(1,term.length()).trim();
			}
			if(term.length() < 2){
				versionNum = "0" + term;
			}else if(term.length() == 2){
				versionNum = term;
			}else{
				versionNum = term.substring(0,2);
			}
						
		}	
		return versionNum;
	}
	
	/**
	 * set .wrksht file name
	 * 
	 * @param filename
	 */
	private String setFileName(String filename) {
		StringBuffer sb = new StringBuffer(filename.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".cdata");
		String dir = ABRServerProperties.getValue(m_abri.getABRCode(), RPTPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		String pathName = dir + sb.toString();
	
		addDebug("**** ffPathName: " + pathName + " ffFileName: " + sb.toString());
		return pathName;
	}
	
	/**
	 * generate report
	 * @param map
	 * @return
	 */
	private boolean generateOutputFile(HashMap map) {
		if(map.size()==0){
			userxmlSb.append("File generate failed, for there is no qualified data related, please check \n");
			return false;
		}
		else{
		
			try{
				Iterator entries = map.entrySet().iterator();
				Vector files = new Vector(1);
				int count=1;
				while(entries.hasNext()){
					Map.Entry entry = (Map.Entry) entries.next();
					WTAASCCNSWENTITY entity = (WTAASCCNSWENTITY) entry.getValue();
					String modelatr = entity.getMODELATR();
					String machtypeatr = entity.getMACHTYPEATR();
					String fileName = setFileName(machtypeatr + "-" + modelatr + "(" + count + ")" );					
					
					generateFile(fileName, entity);																	
					files.add(fileName);
					count++;
				}
				// generate Zip file
				generateZipfile(files);				
				return true;
				
			}catch(Exception e){			
				e.printStackTrace();
				return false;
			}
		}	
				
	}

	private void generateZipfile(Vector files) {
		try{
			FileOutputStream fos = null;
			ZipOutputStream zos = null;
			String dts = getNow();
	        String zipFileName = ABRServerProperties.getOutputPath()+ annNumber + dts +".zip";
	        zipFile = new File(zipFileName);
			try {
				int buffer = 2048;
				fos = new FileOutputStream(zipFile);
				zos = new ZipOutputStream(fos);
				for (int i = 0; i < files.size(); i++) {
					String fileName = (String)files.get(i);
					File outFile = new File(fileName);
					if (outFile.exists()) {
						zos.putNextEntry(new ZipEntry(outFile.getName()));
						BufferedInputStream bis = null;
						try {
							bis = new BufferedInputStream(new FileInputStream(outFile));
							int count;
							byte data[] = new byte[buffer];  
					        while ((count = bis.read(data, 0, buffer)) != -1) {  
					        	zos.write(data, 0, count);  
					        }
					        zos.closeEntry();
					        zos.flush();
					        addDebug("Zip file " + fileName + " successfully");
					        
						} finally {
							if (bis != null) {
								bis.close(); 
							}
						}
						// delete wrksht file
						outFile.delete();
					} else {
						addError("Zip file..., Missing file " + fileName);
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
			files.clear();
			files = null;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void writeln(OutputStreamWriter buffer, String text) throws IOException{
		buffer.write(text+"\r\n");
		//buffer.newLine();
	} 

	/**
	 * generate .wrksht file for each PID
	 * @param fileName
	 * @param entity
	 * @throws Exception
	 */
	private void generateFile(String fileName, WTAASCCNSWENTITY entity) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
		try{		
			String effectiveDate = entity.getEFFECTIVEDATE();			
			String pidNum = entity.getMACHTYPEATR() + "-" + entity.getMODELATR() ;
			String annDate = entity.getANNDATE();
			String productVrm = entity.getPRODUCTVRM();
			String versionNum = getVersionNum(productVrm);
			String releaseNum = getReleaseNum(productVrm);
			String cdocno = fixLength(entity.getCDOCNO(),8,"R");
			String rfaLevel="",availDate="",rfaTitle="",prog="",terms="",release="",progact1="",progact2="",function1="",function2="",function3="",featurePidNew="";
			rfaLevel = 	"RFA       :"+ entity.getANNNUMBER() + "\r\n" +
						"DOCNUM    :"+ cdocno + "\r\n" +
						"SECTION   :"+ rsBundle.getString("SECTION") + "\r\n" +
						"DATE      :"+ annDate + "\r\n" +
						"REVDATE   :"+ annDate + "\r\n" +
						"ACTION    :"+ rsBundle.getString("ACTION") + "\r\n" +
						"CUSTOMER  :"+ rsBundle.getString("CUSTOMER") + "\r\n" +
						"ALIAS     :"+ rsBundle.getString("ALIAS") + "\r\n" +
						"TYPE      :"+ rsBundle.getString("TYPE") + "\r\n" +
						"ESW       :"+ rsBundle.getString("ESW") + "";
			availDate=	"AVAILDATE :"+ effectiveDate;
			rfaTitle =	"TITLE     :"+ pidNum + "     " + getValue(entity.getRFASHRTTITLE());		
			
			String keyLock = entity.getKEYLCKPROTCT();
			if("Yes".equals(keyLock)){
				keyLock = "Y";
			}else{
				keyLock = "N";
			}
			prog = 		"PROG      :"+ pidNum + "     " + versionNum + " PG Y " + keyLock + " " + keyLock + " US " + fixLength(getValue(entity.getINVNAME()),28);
			int gap = 107 - prog.length();
			for(int i=1;i< gap;i++){
				prog +=" ";				
			}
			prog += "\\\\ " + fixLength(getValue(entity.getINVNAME()),28);
			
			String sftWarry = entity.getSFTWARRY();
			if("No".equals(sftWarry)){
				sftWarry = "N";
			}else{
				sftWarry = "Y";
			}
			String usgliApp = entity.getUSGLIAPP();
			if("Yes".equals(usgliApp)){
				usgliApp = "Y";
			}else{
				usgliApp = "N";
			}
			
			String educallow = entity.getEDUCALLOWMHGHSCH();
			if(educallow == null || "".equals(educallow)){
				educallow = "00";
			}
			
			String volumeDis= entity.getVOLUMEDISCOUNTELIG();
			if("No".equals(volumeDis)){
				volumeDis = "N";
			}else{
				volumeDis = "Y";
			}
			
			terms 	= "TERMS     :" + pidNum + "     IPL N N N N " + usgliApp + " N N N 00 " + sftWarry + " Y N N N 6 " + educallow + " " + educallow + " " + volumeDis + " N PLA B 40";
			release = "RELEASE   :" + pidNum + "     " + releaseNum;
			progact1= "PROGACT   :" + pidNum + "     " + releaseNum + " AV " + effectiveDate + " ENABLESW " + cdocno + " " + annDate + " " + entity.getANNNUMBER();
			progact2= "PROGACT   :" + pidNum + "     " + releaseNum + " AN " + annDate + " ENABLESW " + cdocno + " " + annDate + " " + entity.getANNNUMBER();
			
			function1="FUNCTION  :" + pidNum + "     " + releaseNum + " X BAS REGISTRATION";
			function2="FUNCTION  :" + pidNum + "     " + releaseNum + " Z PRC PROCESS FEATURES";
			function3="FUNCTION  :" + pidNum + "     " + releaseNum + " A BAS BASE FUNCTION";
			
			featurePidNew =	"FEATURE   :" + pidNum + "     " + releaseNum + " 3444 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Serial Number Only\r\n"+
					  		"FEATURE   :" + pidNum + "     " + releaseNum + " 3470 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Media Only\r\n"+
					  		"FEATURE   :" + pidNum + "     " + releaseNum + " 3471 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Doc Only\r\n"+
					  		"FEATURE   :" + pidNum + "     " + releaseNum + " 3480 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Media Updates Only\r\n"+
					  		"FEATURE   :" + pidNum + "     " + releaseNum + " 3481 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Doc Updates Only\r\n"+
					  		"FEATURE   :" + pidNum + "     " + releaseNum + " 3482 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Advanced Documentation";
			ArrayList features=entity.getSWFEATURES();
			StringBuffer featureBuffer = new StringBuffer();
			for(int i = 0; i < features.size(); i++){				
				SWFEATURE swFeature = (SWFEATURE) features.get(i);
				String priceindic = null;
				String pricedFeature = swFeature.getPRICEDFEATURE();
				String chargeOption = swFeature.getCHARGEOPTION();
				featureBuffer.append("\r\n");
				if("No".equals(pricedFeature)){
					priceindic = "NC";
					featureBuffer.append("FEATURE   :"+ pidNum +"     "+ releaseNum +" "+ swFeature.getFEATURECODE() +" Z OT -    BASE  OTHER    -   -    N   N  " + priceindic + " N "+ swFeature.getPRICEVALUE() +"   "+ swFeature.getSFINVNAME());
				}else if("Yes".equals(pricedFeature)){
					if(chargeOption == null || "".equals(chargeOption)){
						}else{
							int a = chargeOption.indexOf("-");
							priceindic = chargeOption.substring(0, a-1);
							featureBuffer.append("FEATURE   :"+ pidNum +"     "+ releaseNum +" "+ swFeature.getFEATURECODE() +" A LC -    BASE  -        -   -    N   N  " + priceindic + " N "+ swFeature.getPRICEVALUE() +"   "+ swFeature.getSFINVNAME());												
						}
				}	
				
			}

			StringBuffer featactBuffer = new StringBuffer();
			for(int i = 0; i < features.size(); i++){				
				SWFEATURE swFeature = (SWFEATURE) features.get(i);	
//				addDebug("-------FEATACT ");
//				addDebug("FEATACT   :"+ pidNum +"     " + swFeature.getFEATURECODE() +" AV " + entity.getEFFECTIVEDATE() + " ENABLESW "+ cdocno +" "+ annDate +" "+ entity.getANNNUMBER());
				featactBuffer.append("\r\n");
				featactBuffer.append("FEATACT   :"+ pidNum +"     " + swFeature.getFEATURECODE() +" AV " + entity.getEFFECTIVEDATE() + " ENABLESW "+ cdocno +" "+ annDate +" "+ entity.getANNNUMBER());	
				
			}
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());						   
			int len = effectiveDate.compareTo(date);
			
			if(len >= 0){
				writeln(wOut,rfaLevel);
				writeln(wOut,availDate);
				writeln(wOut,rfaTitle);
				writeln(wOut,prog);
				writeln(wOut,terms);
				writeln(wOut,release);
				writeln(wOut,progact1);
				writeln(wOut,progact2);
				writeln(wOut,function1);
				writeln(wOut,function2);
				writeln(wOut,function3);
				wOut.write(featurePidNew);
				wOut.write(featureBuffer.toString());
				
			}else{
				writeln(wOut,rfaLevel);
				writeln(wOut,rfaTitle);
				writeln(wOut,prog);
				writeln(wOut,release);
				writeln(wOut,progact2);
				writeln(wOut,function1);
				writeln(wOut,function2);
				wOut.write(function3);
				wOut.write(featureBuffer.toString());
				wOut.write(featactBuffer.toString());	
			}				
			
			userxmlSb.append(entity.getMACHTYPEATR() + "-" + entity.getMODELATR() +" File generate success \n");
		} catch(IOException e) {
			
			throw new Exception("File create failed " + fileName);
		} finally {
			wOut.flush();
			wOut.close();
			try {
				if (wOut != null)
					wOut.close();
					
			} catch (Exception e) {
				throw new Exception("File create failed " + fileName);
			}
			
		}
		
	}
		
	/**
	 * Trim down a Text to certain size.
	 * @param text
	 * @param width
	 * @return
	 */
	private String fixLength(String text, int width) {
		if(text.length() >= width){
			return text.substring(0, width);
		}else{
			String term = "";
			for(int i=0;i<width-text.length();i++){
				term += " ";
			}
			text = text + term;
		}
		return text;
	}

	public String getValue(String value){
		if(value==null){
			return "";
		}else{
			return value;
		}
	}
	
	/**
	 * parse date 'yyyy-mm-dd' to 'dd/mm/yyyy'
	 * 
	 * @param date
	 * @return
	 */
	public String dateFormat(String date) {
		SimpleDateFormat in = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");
		String dd = null;
		try {
			dd = in.format(out.parse(date));
		} catch (ParseException e) {
			addDebug("date parse Exception: " + e);
			e.printStackTrace();
		}
		return dd;
	}

	/**
	 * split string
	 * @param str
	 * @param delim
	 * @return
	 */
	public String[] splitStr(String str, String delim) {
        StringTokenizer stringTokenizer = new StringTokenizer( str, delim );
        String[] strArr = new String[stringTokenizer.countTokens()];
        int i = 0;
        while( stringTokenizer.hasMoreTokens() ) {
            strArr[i] = stringTokenizer.nextToken();
            i++ ;
        }
        return strArr;
    } 

	/**
	 * send mail with file generated
	 * 
	 * @param zipFile2
	 * @throws Exception
	 */
	public void sendMail(File zipFile2) throws Exception {
		
		FileInputStream fisBlob = null;
		try {
			fisBlob = new FileInputStream(zipFile2);
			int iSize = fisBlob.available();
			byte[] baBlob = new byte[iSize];
			fisBlob.read(baBlob);
			setAttachByteForDG(baBlob);
			getABRItem().setFileExtension("zip");
			addDebug("Sending mail for file " + zipFile2);
		} catch (IOException e) {
			addDebug("sendMail IO Exception");
		}

		finally {
			if (fisBlob != null) {
				fisBlob.close();
			}
		}

	}

	/**
	 * 
	 * @param item
	 * @param attrCode
	 * @return
	 */
	protected String getAttributeFlagValue(EntityItem item, String attrCode) {
		return PokUtils.getAttributeFlagValue(item, attrCode);
	}

	public String getDescription() {
		return "WTAASSTATUSABR";
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
	 * add msg as an html comment if meets debuglevel set in
	 * abr.server.properties
	 * 
	 * @param debuglvl
	 * @param msg
	 */
	protected void addDebugComment(int debuglvl, String msg) {
		if (debuglvl <= DEBUG_LVL) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}

	/**
	 * 
	 * @param prof
	 * @param veName
	 * @param item
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected EntityList getEntityList(Profile prof, String veName,
			EntityItem item) throws MiddlewareRequestException, SQLException,
			MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null,
				m_db, prof, veName), new EntityItem[] { new EntityItem(null,
				prof, item.getEntityType(), item.getEntityID()) });
		return list;
	}

	/**
	 * search action only returns navigate attributes, pull a full extract on
	 * the EntityItems returned from search to see the other attributes
	 * 
	 * @param prof
	 * @param item
	 * @return EntityItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected EntityItem getEntityItem(Profile prof, EntityItem item)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null,
				m_db, prof, "dummy"), new EntityItem[] { new EntityItem(null,
				prof, item.getEntityType(), item.getEntityID()) });
		return list.getParentEntityGroup().getEntityItem(0);
	}

	/**
	 * search action only returns navigate attributes, pull a full extract on
	 * the EntityItems returned from search to see the other attributes
	 *
	 * @param prof
	 * @param entityType
	 * @param entityId
	 * @return EntityItem
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected EntityItem getEntityItem(Profile prof, String entityType,
			int entityId) throws MiddlewareRequestException, SQLException,
			MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null,
				m_db, prof, "dummy"), new EntityItem[] { new EntityItem(null,
				prof, entityType, entityId) });
		return list.getParentEntityGroup().getEntityItem(0);
	}

	/**
	 * get database
	 */
	protected Database getDB() {
		return m_db;
	}

	protected String getABRTime() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * add msg to report output
	 */
	private void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**
	 * EACM to WATTS Triggered ABRs
	 *
	 * The Report should identify: - USERID (USERTOKEN) - Role - Workgroup -
	 * Date/Time - Action Taken
	 */
	private String buildAbrHeader() {
		String HEADER2 = "<table>" + NEWLINE
				+ "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE
				+ "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE
				+ "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE
				+ "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
		MessageFormat msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = t2DTS;
		args[4] = "WATTS CCN SW ABR feed trigger<br/>" + xmlgenSb.toString();
		args[5] = getABRVersion();
		return msgf.format(args);
	}

	/**
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem)
			throws SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg = new EntityGroup(null, m_db, m_prof,
				theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain
													// navigate order
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem,
					ma.getAttributeCode(), ", ", "", false));
			navName.append(" ");
		}
		return navName.toString();
	}

}

