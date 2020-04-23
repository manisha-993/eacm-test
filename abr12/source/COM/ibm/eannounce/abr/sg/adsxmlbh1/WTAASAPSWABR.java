package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.TransformerConfigurationException;




import org.xml.sax.SAXException;

import com.ibm.transform.oim.eacm.util.PokUtils;

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

public class WTAASAPSWABR extends PokBaseABR{
	
	private ResourceBundle rsBundle = null;
	private String annNumber = "";
	private String annDate = "";
	private File zipFile = null; 
	private HashMap prodInfo = new HashMap();
	private Set modelSet = new HashSet();
	private Set featureSet = new HashSet();
	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer xmlgenSb = new StringBuffer();
	private StringBuffer userxmlSb = new StringBuffer();
	private String t2DTS = "&nbsp;"; // T2
	private SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
	private Object[] args = new String[10];
	public final static String RPTPATH = "_rptpath";	
	private static final char[] FOOL_JTEST = { '\n' };
	protected static final String NEWLINE = new String(FOOL_JTEST);
	private static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
			.getABRDebugLevel("WTAASAPSWABR");
	
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
			addDebug("DEBUG: rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			
			String docNum = getAttributeValue(rootEntity, "ADOCNO");
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
	
	public void processThis(WTAASAPSWABR abr, Profile profileT2, EntityItem rootEntity) throws TransformerConfigurationException,SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {

		boolean isGetModelInfo = getModelInfo(rootEntity);
		if(isGetModelInfo){
			getSWFeatureInfo(rootEntity);
		}else{
			getProdInfo(rootEntity);
		}
	
		// get related price info
		long startTime = System.currentTimeMillis();			
		//getPriceInfo(prodInfo);
		addDebug("get price info Time: "
				+ Stopwatch.format(System.currentTimeMillis() - startTime));											
	
	}
	/**
	 * store data into prodInfo using queryForPROD(rootEntity) 
	 * @param rootEntity
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void getProdInfo(EntityItem rootEntity) throws SQLException, MiddlewareException{
		String key1,key2;
		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		try {
			statement = m_db.getPDHConnection().prepareStatement(
					queryForPROD(rootEntity));
			result = statement.executeQuery();
			while (result.next()) {
				String availId = result.getString(5);
				String modelId = result.getString(7);

				String aletNum = result.getString(1);
				String annDate = result.getString(2);
				String annNumber = result.getString(3);
				String rfaShortTitle = result.getString(4);
				
				String effectiveDate = result.getString(6);
				String productVrm = result.getString(8);
				String modelAtr = result.getString(9);
				String machtypeAtr = result.getString(10);				
				String minvname = result.getString(11);				
				String educAllow = result.getString(12);
				
				String swfeatureId = result.getString(13);
				String featureCode = result.getString(14);
				String pricedFeature = result.getString(15);
				String chargeOption = result.getString(16);
				String swfeatdesc = result.getString(17);
				
								
				key1 = availId ;
				key2 = machtypeAtr ;
				
				if(prodInfo.get(key1)!=null){						
					WTAASAPSWENTITY w = (WTAASAPSWENTITY)prodInfo.get(key1);
					
					HashMap map = (HashMap)w.getPROD();
					if (map.get(key2)!=null){
						PRODINFO prod = new PRODINFO();
						prod.setPRODUCTVRM(productVrm);
						prod.setMACHTYPEATR(machtypeAtr);
						prod.setMODELATR(modelAtr);
						prod.setMODELID(modelId);
						prod.setMINVNAME(minvname);
						prod.setEDUCALLOWMHGHSCH(educAllow);
						
						prod.setSwFeatureId(swfeatureId);
						prod.setFEATURECODE(featureCode);
						prod.setPRICEDFEATURE(pricedFeature);
						prod.setCHARGEOPTION(chargeOption);
						prod.setSFINVNAME(swfeatdesc);
						
						ArrayList ar = (ArrayList) map.get(key2);
						ar.add(prod);
					}else{
						PRODINFO prod = new PRODINFO();
						prod.setPRODUCTVRM(productVrm);
						prod.setMACHTYPEATR(machtypeAtr);
						prod.setMODELATR(modelAtr);
						prod.setMODELID(modelId);
						prod.setMINVNAME(minvname);
						prod.setEDUCALLOWMHGHSCH(educAllow);
						
						prod.setSwFeatureId(swfeatureId);
						prod.setFEATURECODE(featureCode);
						prod.setPRICEDFEATURE(pricedFeature);
						prod.setCHARGEOPTION(chargeOption);
						prod.setSFINVNAME(swfeatdesc);
						
						ArrayList ar = new ArrayList();
						ar.add(prod);
						map.put(key2, ar);
					}					
				}
				else{
					WTAASAPSWENTITY entity = new WTAASAPSWENTITY();
					entity.setADOCNO(aletNum);
					entity.setANNDATE(annDate);
					entity.setANNNUMBER(annNumber);
					entity.setRFASHRTTITLE(rfaShortTitle);
					entity.setAVAILID(availId);
					entity.setEFFECTIVEDATE(effectiveDate);
					
					PRODINFO prod = new PRODINFO();					
					prod.setPRODUCTVRM(productVrm);
					prod.setMODELID(modelId);
					prod.setMACHTYPEATR(machtypeAtr);
					prod.setMODELATR(modelAtr);
					prod.setMINVNAME(minvname);
					prod.setEDUCALLOWMHGHSCH(educAllow);
					
					prod.setSwFeatureId(swfeatureId);
					prod.setFEATURECODE(featureCode);
					prod.setPRICEDFEATURE(pricedFeature);
					prod.setCHARGEOPTION(chargeOption);
					prod.setSFINVNAME(swfeatdesc);	
										
					ArrayList prods= new ArrayList();					
					prods.add(prod);
					HashMap map = new HashMap();
					map.put(key2, prods);
					entity.setPROD(map);
					
					prodInfo.put(key1, entity);								
				}
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	/**
	 * store data into prodInfo using queryForMODEL(rootEntity)
	 * @param rootEntity
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private boolean getModelInfo(EntityItem rootEntity) throws MiddlewareException, SQLException {
		String key1,key2;
		ResultSet result = null;
		PreparedStatement statement = null;
		boolean hasModelInfo = false;
		  
		try {
			Connection conn = m_db.getPDHConnection();
				
			statement = conn.prepareStatement(queryForMODEL(rootEntity));
			result = statement.executeQuery();		
											
			while(result.next()){
				String availId = result.getString(5);
				String modelId = result.getString(7);

				String aletNum = result.getString(1);
				String annDate = result.getString(2);
				String annNumber = result.getString(3);
				String rfaShortTitle = result.getString(4);
				
				String effectiveDate = result.getString(6);				
				String machtypeAtr = result.getString(8);
				
				key1 = availId;
				key2 = machtypeAtr;
				
				if(prodInfo.get(key1)!=null){
					WTAASAPSWENTITY w = (WTAASAPSWENTITY)prodInfo.get(key1);
					
					HashMap map = (HashMap)w.getPROD();	
					
					PRODINFO prod = new PRODINFO();																		
					prod.setMODELID(modelId);
					
					if (map.get(key2)!=null){												
						ArrayList ar = (ArrayList) map.get(key2);
						if(ar.contains(prod)){
							
						}else{
							ar.add(prod);
						}												
					}else{
						ArrayList ar = new ArrayList();
						ar.add(prod);
						map.put(key2, ar);
					}										
					w.setPROD(map);
				}else{
					WTAASAPSWENTITY entity = new WTAASAPSWENTITY();
					entity.setADOCNO(aletNum);
					entity.setANNDATE(annDate);
					entity.setANNNUMBER(annNumber);
					entity.setRFASHRTTITLE(rfaShortTitle);
					entity.setAVAILID(availId);
					entity.setEFFECTIVEDATE(effectiveDate);
					
					HashMap map = new HashMap();
					ArrayList ar = new ArrayList();
					PRODINFO prod = new PRODINFO();
					prod.setMODELID(modelId);
					ar.add(prod);
					map.put(machtypeAtr, ar);
					entity.setPROD(map);
														
					prodInfo.put(key1, entity);	
				}						
				hasModelInfo=true;
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return hasModelInfo;
	
	}
	/**
	 * store data into prodInfo using queryForSWFC(rootEntity)
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
				WTAASAPSWENTITY entity = (WTAASAPSWENTITY) entry.getValue();
				Iterator prodIts = entity.getPROD().entrySet().iterator();
				while(prodIts.hasNext()){
					Map.Entry prodMap = (Map.Entry) prodIts.next();
					ArrayList prods = (ArrayList) prodMap.getValue();
					int count = prods.size();
					for(int i=0;i<count;i++){
						PRODINFO prod = (PRODINFO) prods.get(i);	
						String modelid = prod.getMODELID();
						
						statement = conn.prepareStatement(queryForSWFC(rootEntity, modelid));
						result = statement.executeQuery();		
														
						while(result.next()){
							String productVrm = result.getString(1);
							String machtpeAtr = result.getString(2);
							String modelAtr = result.getString(3);
							String minvname = result.getString(4);				
							String educAllow = result.getString(5);
							String swfeatureId = result.getString(6);	
							String pricedFeature = result.getString(7);
							String chargeOption = result.getString(8);		
							String swfeatdesc = result.getString(9);
							String featureCode = result.getString(10);
								
							PRODINFO prodf = new PRODINFO();
							prodf.setPRODUCTVRM(productVrm);
							prodf.setMODELATR(modelAtr);
							prodf.setMACHTYPEATR(machtpeAtr);
							prodf.setMINVNAME(minvname);
							prodf.setEDUCALLOWMHGHSCH(educAllow);
							prodf.setMODELID(modelid);
							
							prodf.setSwFeatureId(swfeatureId);
							prodf.setFEATURECODE(featureCode);
							prodf.setPRICEDFEATURE(pricedFeature);
							prodf.setCHARGEOPTION(chargeOption);
							prodf.setSFINVNAME(swfeatdesc);
								
							prods.add(prodf);
																	
						}
						prod.setMODELID(null);
					}
				}
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	/**
	 * SQL for extract date from ANN->AVAIL->SWPRODSTRUCT->MODEL&SWFEATURE
	 * @param rootEntity
	 * @return
	 */
	private String queryForPROD(EntityItem rootEntity){
		StringBuffer strbSQL = new StringBuffer();
		addDebug("Get ANN related PRODSTRUCT info");
		
		strbSQL.append("SELECT ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),AVAILID,EFFECTIVEDATE,MODELID,PRODUCTVRM,MODELATR,MACHTYPEATR,INVNAME,EDUCALLOWMHGHSCH,FEATUREID,FEATURECODE,PRICEDFEATURE,CHARGEOPTION,SFINVNAME FROM  ");
		strbSQL.append("(select ann.ADOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.RFASHRTTITLE,f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.PRODUCTVRM, m.MODELATR, m.MACHTYPEATR, m.INVNAME, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid, sf.FEATURECODE, sf.PRICEDFEATURE, sf.CHARGEOPTION, sf.INVNAME as SFINVNAME from price.announcement ann ");
		strbSQL.append("join price.avail a1 on a1.STATUS='Final' and a1.ANNCODENAME=ann.ANNCODENAME and a1.availType in ('Planned Availability','First Order')  ");
		strbSQL.append("join opicm.flag f on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		
		strbSQL.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=f.entityid and r1.valto>current timestamp and r1.effto>current timestamp  ");
		strbSQL.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' ");
		strbSQL.append("join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
		strbSQL.append("join price.model m on m.entityid=sw.id2 and m.STATUS='Final' and m.COFCAT='Software' ");
		strbSQL.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
		strbSQL.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
		strbSQL.append("where ann.entityid=");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by m.MACHTYPEATR,m.MODELATR,availid,featureid) ");
		strbSQL.append("GROUP BY AVAILID,ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),EFFECTIVEDATE,MODELID,PRODUCTVRM,MODELATR,MACHTYPEATR,INVNAME,EDUCALLOWMHGHSCH,FEATUREID,FEATURECODE,PRICEDFEATURE,CHARGEOPTION,SFINVNAME WITH UR ");
		
		addDebug("SQL:" + strbSQL);
		return strbSQL.toString();
	}
	/**
	 * SQL for extract data from ANN->AVIAL->MODEL
	 * @param rootEntity
	 * @return
	 */
	private String queryForMODEL(EntityItem rootEntity){
		addDebug("Get ANN related MODEL info---------------");
		StringBuffer strbSQL = new StringBuffer();
		
		strbSQL.append("SELECT ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),AVAILID,EFFECTIVEDATE,MODELID,MACHTYPEATR FROM  ");		
		strbSQL.append("(select ann.ADOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.RFASHRTTITLE, f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.MACHTYPEATR from price.announcement ann  ");
		strbSQL.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
		strbSQL.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		strbSQL.append("join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=f.entityid and r.valto>current timestamp and r.effto>current timestamp ");
		strbSQL.append("join price.model m on m.entityid=r.entity1id and m.STATUS='Final' and m.COFCAT='Software' ");
		strbSQL.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
		strbSQL.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
		strbSQL.append("where ann.entityid = ");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by m.MACHTYPEATR,modelid,availid) ");
		strbSQL.append("GROUP BY AVAILID,ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),EFFECTIVEDATE,MODELID,MACHTYPEATR WITH UR ");
		
		addDebug("SQL:" + strbSQL);	
		return strbSQL.toString();
	}
	/**
	 * SQL for extract data from ANN->AVAIL->SWPRODSTRUCT->SWFEATURE
	 * @param rootEntity
	 * @param modelid
	 * @return
	 */
	private String queryForSWFC(EntityItem rootEntity, String modelid){
		
	
		addDebug("Get ANN related SWFeature info---------------");
		StringBuffer strbSQL = new StringBuffer();
		strbSQL.append("SELECT PRODUCTVRM,MACHTYPEATR,MODELATR,INVNAME,CAST(EDUCALLOWMHGHSCH AS VARCHAR(500)),FEATUREID,PRICEDFEATURE,CHARGEOPTION,SFINVNAME,FEATURECODE FROM  ( ");
		strbSQL.append("select m.PRODUCTVRM, m.MACHTYPEATR, m.MODELATR, m.INVNAME, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid,sf.PRICEDFEATURE,sf.CHARGEOPTION,sf.INVNAME as SFINVNAME,sf.FEATURECODE from price.announcement ann ");
		strbSQL.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
		strbSQL.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
		strbSQL.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=a1.entityid and r1.valto>current timestamp and r1.effto>current timestamp ");
		strbSQL.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' and sw.id2 = ");
		strbSQL.append(modelid);
		strbSQL.append(" join price.MODEL m on m.entityid=sw.id2 and m.STATUS='Final' ");
		strbSQL.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
		strbSQL.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
		strbSQL.append(" join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
		strbSQL.append("where ann.entityid= ");
		strbSQL.append(rootEntity.getEntityID());
		strbSQL.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by featureid ) ");
		strbSQL.append("GROUP BY PRODUCTVRM,MACHTYPEATR,MODELATR,INVNAME,CAST(EDUCALLOWMHGHSCH AS VARCHAR(500)),FEATUREID,PRICEDFEATURE,CHARGEOPTION,SFINVNAME,FEATURECODE WITH UR ");
		
		addDebug("SQL:" + strbSQL);
		
		return strbSQL.toString();	
			
	}
	/**
	 * ModifyNum is productVrm's two bits after second decimal points, default '00'
	 * @param productVrm
	 * @return
	 */
	private String getModifyNum(String productVrm) {
		String modifyNum = null;
		if(productVrm == null ||"".equals(productVrm)){
			modifyNum = "00";
		}else{
			String[] arr = splitStr(productVrm, ".");
			if(arr.length > 2){
				String term = arr[2].trim();
				if(term.length() < 2){
					modifyNum = "0" + term ;
				}else if(term.length() == 2){
					modifyNum = term;
				}else{
					modifyNum = term.substring(0,2);
				}		
			}else{
				modifyNum = "00";
			}		
		}
		return modifyNum;
	}
	/**
	 * ReleaseNum is productVrm's two bits after first decimal points, default '00'
	 * @param productVrm
	 * @return
	 */
	private String getReleaseNum(String productVrm) {
		String releaseNum = null;
		if(productVrm == null ||"".equals(productVrm)){
			releaseNum = "01";
		}else{
			String[] arr = splitStr(productVrm, ".");
			if(arr.length > 1){
				String term = arr[1].trim();
				if(term.length() < 2){
					releaseNum = "0" + term ;
				}else if(term.length() == 2){
					releaseNum = term;
				}else{
					releaseNum = term.substring(0,2).trim();
				}		
			}else if (arr.length == 1){

				releaseNum = "00";
			}		
		}
		return releaseNum;
	}

	/**
	 * VersionNum is productVrm's two bits before first decimal points, default '00'
	 * @param productVrm
	 * @return
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
			}
						
		}	
		return versionNum;
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
	 * set . file name
	 * 
	 * @param filename
	 */
	private String setFileName(String filename) {
		StringBuffer sb = new StringBuffer(filename.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".SCRIPT");
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
			userxmlSb.append("File generate failed, for there is no qualified data related, please check ");
			return false;
		}
		else{
		
			try{
				Iterator entrie1 = map.entrySet().iterator();
				Vector files = new Vector(1);
				int count=1;
				while(entrie1.hasNext()){
					Map.Entry entry1 = (Map.Entry) entrie1.next();
					WTAASAPSWENTITY entity = (WTAASAPSWENTITY) entry1.getValue();					
					String aletNum = entity.getADOCNO();
					aletNum = (aletNum != null? aletNum : "");
					//file name need change
					String fileName = setFileName("FP"+aletNum+count);					
					
					boolean createfile=generateFile(fileName, entity);
					if (createfile){
						files.add(fileName);
						count++;
					}
				}
				// generate Zip file
				generateZipfile(files);
				userxmlSb.append("File generate success");
				return true;
				
			}catch(Exception e){			
				e.printStackTrace();
				return false;
			}
		}	
				
	}
	/**
	 * generate file for each announcement associate avail
	 * @param fileName
	 * @param entity
	 * @throws Exception
	 */
	private boolean generateFile(String fileName, WTAASAPSWENTITY entity) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
		try{
			String fileContent = "";
			int factNumber=1;
			List listFactSection = new ArrayList();
			String rfa = annNumber;
			
			String factSection;
			Iterator it = entity.getPROD().entrySet().iterator();
			while (it.hasNext()){
				Map.Entry entry = (Entry) it.next();
				String type = (String) entry.getKey();
				
				factSection = generateFactSection(rfa, type, entity, factNumber);
				if(factSection != null ){ // FACT Empty -> Skip
					listFactSection.add(factSection);
					factNumber++;
				}			
			}
			
			String user_CN = "IJ";
			fileContent += generateHeadSection(rfa, entity, 'P',user_CN) +"\r\n";
			
			factNumber=1;
			for(int i=0; i<listFactSection.size();i++){
				String fact = (String) listFactSection.get(i);
				fileContent += 
					"  ---------------------------- FACT " + factNumber + " OF " + listFactSection.size() + " -------------------------------      " + "\r\n" +
					fact;
				factNumber++;
			}
			
			wOut.write(fileContent);
			userxmlSb.append(rfa + entity.getAVAILID() +" File generate success \n");
			return true;
		}catch(IOException e){
			userxmlSb.append(e);
			throw new Exception("File create failed " + fileName);
		}catch (WTAASException ex){
			userxmlSb.append(ex);
			return false;
		}finally {
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
	public String getValue(String value){
		if(value==null){
			return "";
		}else{
			return value;
		}
	}
	/**
	 * generate common head part
	 * @param rfa
	 * @param entity
	 * @param flag
	 * @param userCn
	 * @return
	 * @throws Exception
	 */
	private String generateHeadSection(String rfa,WTAASAPSWENTITY entity,char flag,String userCn) throws Exception{
		Map conditions = new HashMap();
		conditions.put("PANNRFA",rfa);
		
		String DOCID 		= formatValue(entity.getADOCNO(),"DOCID");
		String LETNO 		= formatValue(entity.getADOCNO(),"LETNO");
		String ANNDATE 		= formatDate(entity.getANNDATE(),"yyyy-MM-dd","yyyyMMdd");
		//String EFFECTIVED	= formatDate(entity.getEFFECTIVEDATE(),"yyyy-MM-dd","yyyyMMdd");
		String TITLE 		= fixLength(entity.getRFASHRTTITLE(), 69);
		String TITLE2 		= fixLength(entity.getRFASHRTTITLE(), 78);
		String currentDate  = sdf.format(new Date());
		String DATE 		= fixLength(formatDate(entity.getANNDATE(),"yyyy-MM-dd","EEEEE, MMMMM dd, yyyy").toUpperCase(),39);
		String RELDATE 		= fixLength(formatDate(entity.getANNDATE(),"yyyy-MM-dd","MMMMM dd, yyyy").toUpperCase(),55);
		String 	
		   head = 
				".*DOCID    F" + flag + "" + DOCID + "" + "\r\n" +
				".*LETNO    F" + flag + "" + LETNO + "" + "\r\n" +
				".*DATE     " + ANNDATE + "" + "\r\n" +
				".*REVISED  " + ANNDATE+ "" + "\r\n" +
				".*TITLE    " + TITLE + "                                       " +"\r\n"+
				".ss                                                                           "+"\r\n";	
	
		head += 
			    ".*  Modified on "+currentDate+" by "+userCn+"\r\n" +
			    ".fo on;.sk;.sv on;.tm 0;.bm 0;.pn off;.hy off;.ju off;.ti ?05               " + "\r\n" +
				".pl 62;.ll 69;.co;.tr ! 40;.rf cancel;.rc 9 0;.rc 1 1                         " + "\r\n" +
				".rc 2 2;.rc 3 3;.rc 4 4;.rc 5 5;.rc 6 6;.rc 7 7;.rc 8 8                       " + "\r\n" +
				".fo off                                                                       " + "\r\n" +
				"CONFIDENTIAL UNTIL 8 AM NEW YORK TIME, " + DATE + "\r\n" +
				".fo off                                                                       " + "\r\n" +
				".*DIST                                                                        " + "\r\n" +
				":H2.AP Distribution:                                                          " + "\r\n" +
				".sk                                                                           " + "\r\n" +
				"Refer to Fact Sheet details.                                                  " + "\r\n" +
				".fo off;.sk                                                                   " + "\r\n" +
				":H2.AP-RELEASE DATE:  " + RELDATE + "                                         "+ "\r\n" +
				".fo off;.sk                                                                   " + "\r\n" +
				"THIS SECTION IS CLASSIFIED INTERNAL USE ONLY.                                 " + "\r\n" +
				".in                                                                           " + "\r\n" +
				".*LETTERNO                                                                    " + "\r\n" +
				":H2.FACT SHEET NO. F" + flag + "" + DOCID + "" + "\r\n" +
				".*ELETTERNO                                                                   " + "\r\n" +
				"PROGRAMMING ANNOUNCEMENT                                                      " + "\r\n" +
				".in                                                                           " +"\r\n"+
				"WORLD TRADE ASIA PACIFIC                                                      " + "\r\n" +
				".sk;.in                                                                       " + "\r\n"+
				"RFA NUMBER:      " + rfa + "                                                        " + "\r\n" +
				".sp                                                                           " + "\r\n" +
				".fo left                                                                      " + "\r\n" +
				".*TITLE                                                                       " + "\r\n" +
				":H2.TITLE:                                                                    " + "\r\n" +
				".br                                                                           " + "\r\n" +
				"" + TITLE2 + "\r\n" +
				".*ETITLE                                                                      " + "\r\n" +
				":h2.Fact sheet                                                                " + "\r\n" +
				".sk;.fo off                                                                   " + "\r\n" +
				" ";
		return head;
	
	}
	
	private String formatValue(String number,String flag) throws WTAASException {

		String value = "      ";
		if(number==null){
			return value;
		}else if(number.length()<9){
			throw new WTAASException("ADOC Number length is short than 9, please check");
		}else if ("DOCID".equals(flag)){
			value = number.substring(2, 4)+number.substring(5, 9);
			return value;
		}else if ("LETNO".equals(flag)){
			value = number.substring(2);
			return value;
		}
		return value;
			
	}

	private String generateFactSection(String rfa, String type, WTAASAPSWENTITY entity, int factNumber) throws Exception{
		

		String ANNDATE		= formatDate(entity.getANNDATE(),"yyyy-MM-dd","dd/MM/yy");
		String ALETNUM 		= entity.getADOCNO();
		String REMCOMMENT	= "  REMARKS:                                                                      \r\n";
		String modelSection = null;
		Vector modelVct = new Vector();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());	

		int len = entity.getEFFECTIVEDATE().compareTo(date);
		if(len > 0){
			modelSection = generateModelSection(rfa, type, entity, modelVct);
		}
		 
		String featureSection = generateFeatureSection(rfa, type, factNumber, entity);		
		
		if(modelSection == null && featureSection == null){
			return null;
		}
		if(modelSection == null){
			modelSection =
			"  MAJOR TYPE/MODEL BM FILE UPDATE                                               " + "\r\n" +
			"                                                                                " + "\r\n" +
			"  ADDITIONAL TYPE AND/OR MODELS                                                 " + "\r\n" +
			"                                     1ST DEL                                    " + "\r\n" +
			"  DESCRIPTION (ENGLISH/LOCAL)        DATE      MV       TYPE-MDL                " + "\r\n" +
			"                                                                                " + "\r\n" +
			"  END OF ADDITIONAL TYPE AND/OR MODELS                                          " ;
		}
		if(featureSection == null){
			featureSection = 
				"  MAJOR TYPE/FEATURE BF FILE UPDATE                                             " + "\r\n" +
				"                                                                                " + "\r\n" + 
				"                                                                                " + "\r\n" + 
				"  ADDITIONAL FEATURES                                                           " + "\r\n" +
				"                                                                                " + "\r\n" +
				"  Product Type: " + type + "                                                            " + "\r\n" +
				"                                        D                                       " + "\r\n" +
				"                                  ME O  S                                       " + "\r\n" +
				"                                   D T  L  1st DEL/                             " + "\r\n" +
				"  DESCRIPTION (ENGLISH/LOCAL)     IA C  O  PROC GROUP    MV    FEAT    P/N      " + "\r\n" +
				"                                                                                " + "\r\n" +
				"  END OF ADDITIONAL FEATURES                                                    " ;	
		}
		
		String fact = 
			"                                                                                " + "\r\n" +
			"              LICENSED PROGRAM ADMINISTRATIVE FACT SHEET                        " + "\r\n" +
			"                                                                                " + "\r\n" +
			"                    IBM CONFIDENTIAL UNTIL ANNOUNCED                            " + "\r\n" +
			"                                                                                " + "\r\n" +
			"  REFERENCE RFA/LA NO.: " + rfa + "          PROGRAM ANNOUNCEMENT: " + ALETNUM + "          " + "\r\n" +
			"  PUBLICATION DATE: " + ANNDATE + "                                                    " + "\r\n" +
			"                                                                                " + "\r\n" +
			"  RELEASED TO:  ALL AP ( X )    ASPA ONLY (  )    JAPAN ONLY (  )               " + "\r\n" +
			"                COUNTRY ONLY (SPECIFY)                                          " + "\r\n" +
			"                                                                                " + "\r\n" +
			REMCOMMENT +
			"                                                                                " + "\r\n" +
			"                                Distribution: BTO                               " + "\r\n" +
			"                                                                                " + "\r\n" +
			modelSection + "\r\n" +
			"                                                                                " + "\r\n" +
			featureSection + "\r\n" +
			"                                                                                " + "\r\n" +
			"  END OF FACT SHEET                                                             " + "\r\n" +
			"                                                                                " + "\r\n" ;
		
		return fact;
		
	}
	/**
	 * generate model info into report
	 * @param rfa
	 * @param type
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private String generateModelSection(String rfa, String type, WTAASAPSWENTITY entity, Vector modelVct) throws Exception{
	
		String majorModel = "";
		String additionalModel = "";
		HashMap map = entity.getPROD();
		ArrayList prods = (ArrayList) map.get(type);
		for(int i=0;i<prods.size();i++){
			PRODINFO prod = (PRODINFO) prods.get(i);
			if(prod.getMODELID()==null){
				continue;
			}
			String GENAVAILDT 	= formatDate(entity.getEFFECTIVEDATE(),"yyyy-MM-dd","MMyy");
			String PRODWAR 		= "N";
			String PPEPEDU		= prod.getEDUCALLOWMHGHSCH() == null ||  prod.getEDUCALLOWMHGHSCH().trim().length() == 0 
									? "000000" 
									:  matchformat(prod.getEDUCALLOWMHGHSCH());
			String PPCKPID = "N";
			
				String TYPE = prod.getMACHTYPEATR();
				String MODEL = prod.getMODELATR();
				String ANNDATE = formatDate(entity.getANNDATE(),"yyyy-MM-dd","ddMMyy");
				String productvrm = prod.getPRODUCTVRM();
				String VER = getVersionNum(productvrm);
				String REL = getReleaseNum(productvrm);
				String MOD = getModifyNum(productvrm);
				String description = getValue(prod.getMINVNAME());				
				
				String DSLOELIG = "N";
				
				String LOCATIONLC = "N";
				String INST_LIC	= "N";
				String FPEPRCT = "Y";				
				String POFFTIT=fixLength(description,35);
				
				if(modelSet.add(prod.getMACHTYPEATR())){ // if this machtype is not first print.
				
					//modelSet.add(MODEL);
					POFFTIT = fixLength(description,56);
					
					majorModel = 
						"  Program No: " + TYPE + "-" + MODEL + "          Ver " + VER + "   Rel " + REL + "  Mod " + MOD + "                         " + "\r\n" +
						"  Description (English) " + description + "\r\n" +
						"  Description (Local)                                                           " + "\r\n" +
						"  PP: Y PN:   PRPQ:   PO:   Media Feature: " + PPCKPID + "             Use in Cty: Y          " + "\r\n" +
						"  Orders Rejected: N        DP Install,Post Order: NN    Price Print: Y         " + "\r\n" +
						"  Call Off: N               License,Install,Usage: " + LOCATIONLC + "" + INST_LIC + "" + FPEPRCT + "   S/W Warranty: " + PRODWAR + "        " + "\r\n" +
						"  Auto Sftw Sched: Y        Mat Check,Pass: NN           DSLO Elig: " + DSLOELIG + "           " + "\r\n" +
						"  Restricted Material: N                                                        " + "\r\n" +
						"                                                                                " + "\r\n" +
						"  Product ID Code: 5             First Del Date: " + GENAVAILDT + "/                          " + "\r\n" +
						"  Program Lib Code: 88/          Ser Support ID Code: M                         " + "\r\n" +
						"  Default Order Sys:             Education Allowance: " + PPEPEDU + "                    " + "\r\n" +
						"  Pre-Install Test: 00/000       Limited Billing Period (leave blank):   /      " + "\r\n" +
						"  Central Ser Code: 0            Central End Support:                           " + "\r\n" +
						"  A - B - C: A                   Local Ser Code:                                " + "\r\n" +
						"  Local Service:                 Order Confirmation: 05                         " + "\r\n" +
						"  Normal Billing Period: 0101    Product Lead:                                  " + "\r\n" +
						"  Normal M/A / ITC Code: T/B                                                    " + "\r\n" +
						"                                                                                " + "\r\n" +
						"  Points Effective Date: " + ANNDATE + "     Points (MV): 000000*                        " + "\r\n" +
						"  Values Effective Date: " + ANNDATE + "     Monthly Rental Value: 000                   " + "\r\n" +
						"  Initial Charge Value: 000                                                     " + "\r\n" +
						"                                                                                " + "\r\n" +
						"  BL FILE UPDATE                                                                " + "\r\n" +
						"                                                                                " + "\r\n" +
						"  Price Code: 0                   Announcement Date: " + ANNDATE + "                     " + "\r\n" +
						"  Monthly Charge/SUC/OTC: 000                                                   " + "\r\n" +
						"                                                                                " + "\r\n";
					modelVct.add(prod.getMODELID());	
					continue;			
				}
				//if(!modelSet.add(MODEL)){ // if this model has been printed, not print again
					//continue;
				//}
				// The 2nd and next Features are used as 'ADDITIONAL FEATURES'
				if(!modelVct.contains(prod.getMODELID())){
				additionalModel += 
					"  " + POFFTIT + GENAVAILDT + "    000000*    " + TYPE + "-" + MODEL + "                " + "\r\n" +
					"                                                                                " + "\r\n" ;
				modelVct.add(prod.getMODELID());
				}
			
		}
		
		if(additionalModel.trim().length() == 0)
			additionalModel = "                                                                                \r\n";
		
		String modelSection =
			"  MAJOR TYPE/MODEL BM FILE UPDATE                                               " + "\r\n" +
			"                                                                                " + "\r\n" +
			majorModel +
			"  ADDITIONAL TYPE AND/OR MODELS                                                 " + "\r\n" +
			"                                     1ST DEL                                    " + "\r\n" +
			"  DESCRIPTION (ENGLISH/LOCAL)        DATE      MV       TYPE-MDL                " + "\r\n" +
			additionalModel +
			"  END OF ADDITIONAL TYPE AND/OR MODELS                                          " ;
			
		return modelSection;
		
	}
	
	private String generateFeatureSection(String rfa, String type, int factNumber, WTAASAPSWENTITY entity) throws Exception{
	
		String TYPE="";
		String majorFeature = "";
		String additionalFeatures = "";
		String featuresSection ="";
		
		// Get only the NEW features for the current FACT
		// SELECT * FROM GSALTDATA.GSALTLFT01 WHERE PANNRFA LIKE <rfa> AND ONLYMODEL LIKE 'No' AND PACHAFF LIKE <type> AND ...another filter

		HashMap map = entity.getPROD();
		ArrayList prods = (ArrayList) map.get(type);
		if(prods.size() > 0){
			
			String GENAVAILDT 	= formatDate(entity.getEFFECTIVEDATE(),"yyyy-MM-dd","MMyy");
			
			String []retorno = featureTreatment(entity, type, GENAVAILDT, factNumber);
			majorFeature = retorno[0];
			additionalFeatures = retorno[1];
			TYPE = retorno[2];
			
		}
		else{ // if not exist any Feature for print in mayor section
			return null;
		}
			
		if(additionalFeatures.trim().length() == 0){
			additionalFeatures = "                                                                                \r\n";
		}
		
		featuresSection =
				"  MAJOR TYPE/FEATURE BF FILE UPDATE                                             " + "\r\n" +
				"                                                                                " + "\r\n" +
				majorFeature +
				"                                                                                " + "\r\n" + 
				"  ADDITIONAL FEATURES                                                           " + "\r\n" +
				"                                                                                " + "\r\n" +
				"  Product Type: " + fixLength(TYPE,4) + "                                                            " + "\r\n" +
				"                                        D                                       " + "\r\n" +
				"                                  ME O  S                                       " + "\r\n" +
				"                                   D T  L  1st DEL/                             " + "\r\n" +
				"  DESCRIPTION (ENGLISH/LOCAL)     IA C  O  PROC GROUP    MV    FEAT    P/N      " + "\r\n" +
				additionalFeatures +
				"  END OF ADDITIONAL FEATURES                                                    " ;
							
		return featuresSection;
	}
	
	/**
	 * generate feature part
	 * @param entity
	 * @param type
	 * @param GENAVAILDT
	 * @param factNumber
	 * @return
	 * @throws Exception
	 */
private String[] featureTreatment(WTAASAPSWENTITY entity,String type, String GENAVAILDT, int factNumber)throws Exception{
		
		String DESCRIPTION; // "SUBTITLE/MODEL"
		String TYPE = "";
		String MODEL;
		String FEATURECODE;
		String PPCKPID;
		String FEATUREDES;
		String ANNDATE;
		String POINTS;
		String MNTHRENTVL;
		String MNTHCHRG;
		String DSLOELIG;
		String OTC;
		String CHARGEOPTION;
		String majorFeature="";
		String additionalFeatures="";
		
		HashMap map = entity.getPROD();
		ArrayList prods = (ArrayList) map.get(type);
		for(int i=0;i<prods.size();i++){
			
			PPCKPID = "N";
			PRODINFO prod = (PRODINFO) prods.get(i);
			if(prod.getMODELID()==null){
				continue;
			}
			TYPE 		= prod.getMACHTYPEATR();
			MODEL 		= prod.getMODELATR();
			FEATURECODE = prod.getFEATURECODE();
			FEATUREDES 	= getValue(prod.getSFINVNAME());
			ANNDATE 	= formatDate(entity.getANNDATE(),"yyyy-MM-dd","ddMMyy");//
			//
			DSLOELIG 	= "N";
			if("Yes".equals(prod.getPRICEDFEATURE())){
				OTC = "Y";
				POINTS = "000001";
				MNTHCHRG = "100";
				CHARGEOPTION = "OTC";
				MNTHRENTVL 	= "100";
			}else{
				OTC = "N";
				POINTS = "000000";
				MNTHCHRG = "000";
				CHARGEOPTION = "NC";
				MNTHRENTVL 	= "000";
			}
			
			// The 1st Feature in the DB is used as 'MAJOR TYPE/FEATURE'
			if(!featureSet.contains(TYPE)){
			
				if(("OTC".equals(CHARGEOPTION)||majorFeature.equals("")&&i==prods.size()-1)){
					
					DESCRIPTION =  fixLength(CHARGEOPTION+" "+FEATUREDES + "/" + MODEL, 56); 
								
					String PPEPEDU		= prod.getEDUCALLOWMHGHSCH() == null ||  prod.getEDUCALLOWMHGHSCH().trim().length() == 0 
							? "000000" 
							:  matchformat(prod.getEDUCALLOWMHGHSCH());
				
					majorFeature = 
							"  Type/Model/Feature No.: " + TYPE + "-" + MODEL + "/" + FEATURECODE + "      Part Number:                       " + "\r\n" +
							"  Description (English) " + DESCRIPTION + "\r\n" +
							"  Description (Local)                                                           " + "\r\n" + 
							"  Media Feature: " + PPCKPID + "      Orders Rejected: N      Price Print: Y                  " + "\r\n" +
							"  Usable in Cty: Y      One Time Charge: " + OTC + "      DP Install Program: N           " + "\r\n" +
							"  Specify Feature: N    DSLO: N                 Restricted Material: N          " + "\r\n" +
							"                                                                                " + "\r\n" +
							"  1st Del Date/Min&Max Proc Grp: " + GENAVAILDT + "/         Education Allowance: " + PPEPEDU + "      " + "\r\n" +
							"  Service Support ID Code: P/                                                   " + "\r\n" +
							"  Product Lead Time:             Pre-Install Test/Inter-Co Chg Code: 00/B       " + "\r\n" +
							"                                                                                " + "\r\n" +
							"  Points Effective Date: " + ANNDATE + "  Points: " + POINTS + "*                                " + "\r\n" +
							"  Values Effective Date: " + ANNDATE + "  Monthly Rental Value: " + MNTHRENTVL + "                      " + "\r\n" +
							"  Initial Charge Value: 000                                                     " + "\r\n" +
							"                                                                                " + "\r\n" +
							"  BL FILE UPDATE                                                                " + "\r\n" +
							"                                                                                " + "\r\n" +
							"  Price Code: 0                   Announcement Date: " + ANNDATE + "                     " + "\r\n" +
							"  Monthly Charge/SUC/OTC: " + MNTHCHRG + "                                                   " + "\r\n";
				
				
					featureSet.add(TYPE);	
				}
				continue;
			}
			
			// The 2nd and next Features are used as 'ADDITIONAL FEATURES'
			
			DESCRIPTION  =  fixLength(CHARGEOPTION + " " + FEATUREDES, 26)+ "/" + MODEL;
			
			additionalFeatures += 
				"  " + DESCRIPTION + "  " + PPCKPID + "  " + OTC + "  " + DSLOELIG + "  " + GENAVAILDT + "/      " + POINTS + "*  " + FEATURECODE + "             " + "\r\n" +
				"                                                                                " + "\r\n" ; 			
		}
		
		return new String[]{majorFeature,additionalFeatures,TYPE};
	}

	private String matchformat(String educallowmhghsch) {
		String result = "";
		if (educallowmhghsch.length()==1){
			educallowmhghsch = "0" + educallowmhghsch;
		}else if(educallowmhghsch.length()>2){
			educallowmhghsch = educallowmhghsch.substring(0, 2);
		}		
		for(int i=0;i<3;i++){
			result+=educallowmhghsch;
		}		
		return result;
	}

	/**
	 * Format a date with a input Format to a date with certain output Value.
	 * @param date : Valid date
	 * @param inputFormat : input Pattern Format {@link java.text.SimpleDateFormat}.
	 * @param outputFormat : output Pattern Format {@link java.text.SimpleDateFormat}.
	 * @return String with the date formated.
	 * @throws Exception : When the date does not exist or when the some Pattern Format is invalid.
	 */
	public static String formatDate(String date, String inputFormat, String outputFormat) throws Exception{
		try{
			// Create a temporal Date object
			SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat,Locale.US);		
			Date objDate = inputDateFormat.parse(date);

			// SimpleDateFormat parse any date, inclusive if the date does not exist.
			// For make sure than the date is valid, parse and un-parse should to return the same date
			if(!date.equals(inputDateFormat.format(objDate))){
				throw new Exception("Invalid date");
			}

			// Composite the output date.
			SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat,Locale.US);
			return outputDateFormat.format(objDate);
		}catch (ParseException e){
			throw new Exception("Invalid format", e.getCause());
		}
	}
	/**
	 * Fill o Trim down  a Text to certain size.
	 * @param text : Text to format
	 * @param width : Expected size
	 * @param side : L/R. Indicate the side where fill o trim
	 * @return : Fixed text
	 */
	public String fixLength(String text, int width, String side){
		text= (text != null ? text : "");
		if(side.equalsIgnoreCase("R")){ // Left-to-Right
			if(text.length() >= width){
				return text.substring(0, width);
			}else{
				String term = "";
				for(int i=0;i<width-text.length();i++){
					term += " ";
				}
				text = text + term;
			}
		}else if(side.equalsIgnoreCase("L")){ // Right-to-Left
			if(text.length() >= width){
				return text.substring(text.length() - width, text.length());
			}else{
				String term = "";
				for(int i=0;i<width-text.length();i++){
					term += " ";
				}
				text = term + text;
			}
		}
		return text;
	}
	 
	/**
	 * Fill o Trim  a Text to certain size. By default trim/ fill at Right
	 * @param text : Text to format
	 * @param width : Expected size
	 * @return : Fix text
	 */
	public String fixLength(String text, int width){
		return fixLength(text, width, "R");
	}
	
	
	/**
	 * generate all file into one zip file
	 * @param files
	 */
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
	/**
	 * send mail with zip file generated
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
		return "WTAASAPSWABR";
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
		args[4] = "WATTS AP SW ABR feed trigger<br/>" + xmlgenSb.toString();
		args[5] = getABRVersion();
		return msgf.format(args);
	}

	/**
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem)
			throws java.sql.SQLException, MiddlewareException {
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
