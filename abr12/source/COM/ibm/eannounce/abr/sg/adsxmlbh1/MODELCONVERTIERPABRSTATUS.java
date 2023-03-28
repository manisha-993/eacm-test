package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.sg.rfc.ChwBomCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwBomMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwModelConvertMtc;
import COM.ibm.eannounce.abr.sg.rfc.ChwModelConvertUpg;
import COM.ibm.eannounce.abr.sg.rfc.ChwReadSalesBom;
import COM.ibm.eannounce.abr.sg.rfc.MODEL;
import COM.ibm.eannounce.abr.sg.rfc.MODELCONVERT;
import COM.ibm.eannounce.abr.sg.rfc.MTCYMDMFCMaint;
import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

public class MODELCONVERTIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODELCONVERT' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	String xml = null;



	public String getDescription() {
		// TODO Auto-generated method stub
		return "PIABRSTATUS";
	}

	public String getABRVersion() {
		// TODO Auto-generated method stub

		return "1.0";
	}

	public void execute_run() {
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
				+ NEWLINE;

		String header1 = "";


		MessageFormat msgf;
		String abrversion = "";
		String rootDesc = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			setDGTitle("FEATUREIERPABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("FEATUREIERPABRSTATUS"); // Set the report name
			setDGRptClass("FEATUREIERPABRSTATUS"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(false); // pull the VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof,
					new ExtractActionItem(null, m_db, m_prof,"dummy"),
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			/*
			 * m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db,
			 * m_prof,"dummy"), new EntityItem[] { new EntityItem(null, m_prof,
			 * getEntityType(), getEntityID()) });
			 */

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			// NAME is navigate attributes - only used if error rpt is generated
			navName = getNavigationName();
			rootDesc = m_elist.getParentEntityGroup().getLongDescription();
			addDebug("navName=" + navName);
			addDebug("rootDesc" + rootDesc);
			// build the text file

			Connection connection = m_db.getODSConnection();
			PreparedStatement statement = connection.prepareStatement(CACEHSQL);
			statement.setInt(1, rootEntity.getEntityID());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				xml = resultSet.getString("XMLMESSAGE");
			}
			if (xml != null) {

				MODELCONVERT modelconvert = XMLParse.getObjectFromXml(xml, MODELCONVERT.class);
				String modelXML = getModelFromXML(modelconvert.getTOMACHTYPE(), modelconvert.getTOMODEL(), connection);
				//addOutput("MODEL xml:"+convertToHTML(modelXML));
				if(modelXML==null) {
					this.addOutput("MODEL xml not found in cache fro MODEL:"+modelconvert.getTOMODEL()+" MACHTYPE:"+modelconvert.getTOMACHTYPE());
					return;
				}
				MODEL model = XMLParse.getObjectFromXml(modelXML,MODEL.class );
				//addDebug("Model:"+model);
				if(model==null){
					this.addOutput("Not find the Model with the modelxml");
					return;
				}
				String flag = modelconvert.getFROMMACHTYPE().equals(modelconvert.getTOMACHTYPE())?"UPG":"MTC";
				if (modelconvert.getFROMMACHTYPE().equals(modelconvert.getTOMACHTYPE())) {
					ChwModelConvertUpg mUpg = new ChwModelConvertUpg(model,modelconvert,m_db.getPDHConnection(),connection);
					try{
						mUpg.execute();
						addOutput(mUpg.getRptSb().toString());
					}catch (Exception e) {
						// TODO: handle exception
						addError(mUpg.getRptSb().toString());
						throw e;
					}
				}else {
					ChwModelConvertMtc mtc = new ChwModelConvertMtc(model,modelconvert,m_db.getPDHConnection(),connection);
					try {
						mtc.execute();
						addOutput(mtc.getRptSb().toString());
					} catch (Exception e) {
						// TODO: handle exception
						addError(mtc.getRptSb().toString());
						throw e;
					}
				}
				 MTCYMDMFCMaint maint = new MTCYMDMFCMaint(modelconvert);
				 
				 this.addDebug("Calling " + maint.getRFCName());
				 if(maint.getTbl_model().size()>0) {
					 maint.execute();
					 this.addDebug(maint.createLogEntry());
					 if (maint.getRfcrc() == 0) {
							this.addOutput(maint.getRFCName() + " called successfully!");
					} else {
							this.addOutput(maint.getRFCName() + " called  faild!");
							this.addOutput(maint.getError_text());
					}
				 }else {
					 addOutput("No Tbl_model in the MTCYMDMFCMaint, will not call the RFC");
				 }


				//MTCYMDMFCMa
				//ChwMachTypeMtc
		
				/*	this.addDebug("Calling " + prod.getRFCName());
					prod.execute();
					this.addDebug(prod.createLogEntry());
					if (prod.getRfcrc() == 0) {
						this.addOutput(prod.getRFCName() + " called successfully!");
					} else {
						this.addOutput(prod.getRFCName() + " called  faild!");
						this.addOutput(prod.getError_text());
					}
					*/

				//create Sales Bom
				String bomFlag = modelconvert.getFROMMACHTYPE().equals(modelconvert.getTOMACHTYPE())?"BOMUPG":"BOMMTC";
				List<MODEL> models = getMODEL(modelconvert.getTOMACHTYPE(), modelconvert.getPDHDOMAIN());
				Set<String> plnts = RFCConfig.getBHPlnts();
				this.addOutput("Start Bom Processing!");
				updateSalesBom(modelconvert,flag,bomFlag,plnts,models);
				this.addOutput("Bom Processing Finished!");
				UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP",modelconvert.getTOMACHTYPE()+bomFlag);
				this.addDebug("Calling "+updateParkStatus.getRFCName());
				try {
					updateParkStatus.execute();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				this.addDebug(updateParkStatus.createLogEntry());
				if (updateParkStatus.getRfcrc() == 0) {
					this.addOutput("Parking records updated successfully for ZDMRELNUM="+modelconvert.getTOMACHTYPE()+bomFlag);
				} else {
					this.addOutput(updateParkStatus.getRFCName() + " called faild!");
					this.addOutput(updateParkStatus.getError_text());
				}
				// Call UpdateParkStatus
				String rfaNum= modelconvert.getFROMMACHTYPE()+modelconvert.getFROMMODEL()+modelconvert.getTOMACHTYPE()+modelconvert.getTOMODEL();
				updateParkStatus = new UpdateParkStatus("MD_CHW_IERP",rfaNum);
				this.addDebug("Calling "+updateParkStatus.getRFCName());
				updateParkStatus.execute();
				this.addDebug(updateParkStatus.createLogEntry());
				if (updateParkStatus.getRfcrc() == 0) {
					this.addOutput("Parking records updated successfully for ZDMRELNUM="+rfaNum);
				} else {
					this.addOutput(updateParkStatus.getRFCName() + " called faild!");
					this.addOutput(updateParkStatus.getError_text());
				}
			} else {
				this.addOutput("XML file not exeit in cache,RFC caller not called!");
				//return;
			}



			// exeFtpShell(ffPathName);
			// ftpFile();
			/*
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); this.addError(e.getMessage()); setReturnCode(FAIL); }
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);

			// sentFile=exeFtpShell(ffPathName);
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();
			rptSb.insert(0, convertToHTML(xml)+NEW_LINE);
			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}


	private List<MODEL> getMODEL(String toMachtype, String domain) throws Exception{
		List<MODEL> models= new ArrayList();

		String sql = "SELECT distinct t2.ATTRIBUTEVALUE as MACHTYPEATR,substr(T1.ATTRIBUTEVALUE,1,3) as MODELATR FROM OPICM.flag F "
				+ "INNER JOIN OPICM.FLAG t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MACHTYPEATR' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
				+ "INNER JOIN OPICM.text t1 ON t1.ENTITYID =t2.ENTITYID AND t1.ENTITYTYPE =t2.ENTITYTYPE AND t1.ATTRIBUTECODE ='MODELATR' and T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP AND T1.NLSID=1 "
				+ "INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
				+ "INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
				+ "WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELIERPABRSTATUS') AND F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? WITH UR";
		Connection connection = m_db.getPDHConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, toMachtype);
		statement.setString(2, domain);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			MODEL model = new MODEL();
			model.setMACHTYPE(resultSet.getString("MACHTYPEATR"));
			model.setMODEL(resultSet.getString("MODELATR"));
			models.add(model);
		}

		return models;
	}

	public void updateSalesBom(MODELCONVERT modelconvert, String flag, String bomFlag,Set<String> plnts, List<MODEL> models) throws Exception {
		for(String plant : plnts) {
			//call ChwBomCreate 
			ChwBomCreate chwBomCreate = new ChwBomCreate(modelconvert.getTOMACHTYPE()+flag,modelconvert.getTOMACHTYPE()+bomFlag, plant);
			this.addDebug("Calling " + "ChwBomCreate");
			this.addDebug(chwBomCreate.generateJson());
			try{
				chwBomCreate.execute();
				this.addDebug(chwBomCreate.createLogEntry());
			}catch(Exception e) {
				this.addOutput(e.getMessage());
				continue;
			}
			// start lock
			String fileName = "./locks/MODELCONVERT" + modelconvert.getTOMACHTYPE() + flag + plant + ".lock";
			File file = new File(fileName);
			new File(file.getParent()).mkdirs();
			try (FileOutputStream fos = new FileOutputStream(file); FileChannel fileChannel = fos.getChannel()) {
				while (true) {
					try {
						FileLock fileLock = fileChannel.tryLock();
						if (fileLock != null) {
							this.addDebug("Start lock, lock file " + fileName);
							// lock content
							//call ChwReadSalesBom
							ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom(modelconvert.getTOMACHTYPE()+flag, plant);
							this.addDebug("Calling " + "ChwReadSalesBom");
							this.addDebug(chwReadSalesBom.generateJson());
							try{
								chwReadSalesBom.execute();
								this.addDebug(chwReadSalesBom.createLogEntry());
							}catch(Exception e) {
								if(e.getMessage().contains("exists in Mast table but not defined to Stpo table")){

								} else{
									this.addOutput(e.getMessage());
									break;
								}
							}
							this.addDebug("Bom Read result:"+chwReadSalesBom.getRETURN_MULTIPLE_OBJ().toString());
							List<HashMap<String, String>> componmentList = chwReadSalesBom.getRETURN_MULTIPLE_OBJ().get("stpo_api02");
							if (componmentList != null && componmentList.size() > 0) {
								String POSNR = getMaxItemNo(componmentList);
								for(MODEL model : models) {
									String componment = model.getMACHTYPE() + model.getMODEL();
									if (hasMatchComponent(componmentList, componment)) {
										this.addDebug("updateSalesBom exist component " + componment);
									}else {
										POSNR=generateItemNumberString(POSNR);
										//call ChwBomMaintain
										ChwBomMaintain chwBomMaintain = new ChwBomMaintain(model.getMACHTYPE()+flag,modelconvert.getTOMACHTYPE()+bomFlag, plant, model.getMACHTYPE()+model.getMODEL(),POSNR,"SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL());
										this.addDebug("Calling " + "chwBomMaintain");
										this.addDebug(chwBomMaintain.generateJson());
										try {
											chwBomMaintain.execute();
											this.addDebug(chwBomMaintain.createLogEntry());
										}catch(Exception e) {
											this.addOutput(e.getMessage());
											POSNR = getMaxItemNo(componmentList);
											continue;
										}
									}
								}
							}else {
								//call ChwBomMaintain
								String POSNR ="0005";
								//call ChwBomMaintain
								for(MODEL model : models) {
									//call ChwBomMaintain
									ChwBomMaintain chwBomMaintain = new ChwBomMaintain(model.getMACHTYPE()+flag,modelconvert.getTOMACHTYPE()+bomFlag, plant, model.getMACHTYPE()+model.getMODEL(),POSNR,"SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL());
									this.addDebug("Calling " + "chwBomMaintain");
									this.addDebug(chwBomMaintain.generateJson());
									try {
										chwBomMaintain.execute();
										this.addDebug(chwBomMaintain.createLogEntry());
									}catch(Exception e) {
										this.addOutput(e.getMessage());
										continue;
									}
									POSNR=generateItemNumberString(POSNR);
								}
							}
							// end lock content
							break;
						} else {
							this.addDebug("fileLock == null");
							Thread.sleep(5000);
						}
					} catch (OverlappingFileLockException e1) {
						this.addDebug("other abr is running createSalesBOMforType" + flag);
						Thread.sleep(5000);
					}
				}
			}
			// end lock
		}
	}

	private String getMaxItemNo(List<HashMap<String, String>> componmentList) {
		List<Integer> itemNo = new ArrayList<Integer>();
		for (int i = 0; i < componmentList.size(); i++) {
			String rev = componmentList.get(i).get("ITEM_NO");
			itemNo.add(Integer.parseInt(rev));
		}
		int maxItemNo = Collections.max(itemNo);
		return String.format("%04d", maxItemNo);
	}

	private String generateItemNumberString(String posnr) {
		int tempValue = Integer.parseInt(posnr)+5;
		return String.format("%04d", tempValue);
	}

	private boolean hasMatchComponent(List<HashMap<String, String>> bom, String componment){
		for (int i = 0; i < bom.size(); i++) {
			String rev = bom.get(i).get("COMPONENT");
			if (rev.trim().equals(componment)){
				return true;
			}
		}
		return false;
	}

	private String getModelFromXML(String TOMACHTYPE, String TOMODEL,Connection odsConnection) throws SQLException {
		/**
		 *
		 * select XMLMESSAGE from cache.XMLIDLCACHE
		 * where XMLCACHEVALIDTO > current timestamp
		 * and  XMLENTITYTYPE = 'MODEL'
		 * and xmlexists('declare default element namespace "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE"; $i/MODEL_UPDATE[MACHTYPE/text() = "7954" and MODEL/text() ="24X"]' passing cache.XMLIDLCACHE.XMLMESSAGE as "i")
		 * with ur;
		 */
		String cacheSql = "select XMLMESSAGE from cache.XMLIDLCACHE "
				+ " where XMLCACHEVALIDTO > current timestamp "
				+ " and  XMLENTITYTYPE = 'MODEL'"
				+ " and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\"; "
				+ " $i/MODEL_UPDATE[MACHTYPE/text() = \""+TOMACHTYPE+"\" and MODEL/text() =\""+TOMODEL+"\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\")"
				+ " FETCH FIRST 1 ROWS ONLY with ur";
		addDebug("getModelFromXML cacheSql" + cacheSql);
		PreparedStatement statement = odsConnection.prepareStatement(cacheSql);
		ResultSet resultSet = statement.executeQuery();
		String xml = "";
		if (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
			addDebug("getModelFromXML xml");
		}
		return xml;
	}

	/*
	 * Get Name based on navigation attributes for root entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
			// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}
		return navName.toString();
	}


	/********************************************************************************
	 * Convert string into valid html.  Special HTML characters are converted.
	 *
	 * @param txt    String to convert
	 * @return String
	 */
	protected static String convertToHTML(String txt)
	{
		String retVal="";
		StringBuffer htmlSB = new StringBuffer();
		StringCharacterIterator sci = null;
		char ch = ' ';
		if (txt != null) {
			sci = new StringCharacterIterator(txt);
			ch = sci.first();
			while(ch != CharacterIterator.DONE)
			{
				switch(ch)
				{
					case '<':
						htmlSB.append("&lt;");
						break;
					case '>':
						htmlSB.append("&gt;");
						break;
					case '"':
						// double quotation marks could be saved as &quot; also. this will be &#34;
						// this should be included too, but left out to be consistent with west coast
						htmlSB.append("&quot;");
						break;
					case '\'':
						//IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
						htmlSB.append("&#"+((int)ch)+";");
						break;
					//case '&':
					// ignore entity references such as &lt; if user typed it, user will see it
					// could be saved as &amp; also. this will be &#38;
					//htmlSB.append("&#"+((int)ch)+";");
					//  htmlSB.append("&amp;");
					//    break;
					default:
						htmlSB.append(ch);
						break;
				}
				ch = sci.next();
			}
			retVal = htmlSB.toString();
		}

		return retVal;
	}

	protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}


	/**********************************
	 * add debug info as html comment
	 *    EBUG_ERR = 0;
	 EBUG_WARN = 1;
	 EBUG_INFO = 2;
	 EBUG_DETAIL = 3;
	 EBUG_SPEW = 4
	 */

	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= abr_debuglvl) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
	/**********************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}


}
