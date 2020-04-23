// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import com.ibm.transform.oim.eacm.util.PokUtils;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;


public class WATTSCCNHWABR extends PokBaseABR {

	private ResourceBundle rsBundle = null;
	private String annDate = "";
	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer xmlgenSb = new StringBuffer();
	private StringBuffer userxmlSb = new StringBuffer();
	private String t2DTS = "&nbsp;"; // T2
	private Object[] args = new String[10];
	private String pathName=null;
	private Vector finalEntities=new Vector();
	public final static String RPTPATH = "_rptpath";
	public final static String STATUS_FINAL = "0020";
	public final static String MODEL_HARDWARE = "100";
	public final static CharSequence  GEO_CCN = "1464";
	private static final char[] FOOL_JTEST = { '\n' };
	protected static final String NEWLINE = new String(FOOL_JTEST);
	private static int DEBUG_LVL = ABRServerProperties
			.getABRDebugLevel("WATTSCCNHWABR");
	protected static final Vector AVAILTYPE_FILTER;
	static {
		AVAILTYPE_FILTER = new Vector(2);
		AVAILTYPE_FILTER.add("146"); // Planned Availability
		AVAILTYPE_FILTER.add("143"); // First Order
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
			rsBundle = ResourceBundle.getBundle(getClass().getName(),
					ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			// Default set to pass
			setReturnCode(PASS);

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getVeName());
			m_elist = m_db.getEntityList(m_prof, eaItem,
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			addDebug("DEBUG: rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			String rfaNumber = getAttributeValue(rootEntity, "ANNNUMBER");
			addDebug("RFA number: " + rfaNumber);
			// NAME is navigate attributes
			navName = getNavigationName(rootEntity);

			if (getReturnCode() == PASS) {
				processThis(this, m_prof, rootEntity);
				// generate txt file
				boolean createfile=generateTXTFile(rfaNumber, finalEntities);
				if (createfile) {
					//send mail
					sendMail(pathName);
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
	 * get VE related entities
	 * 
	 * @param abr
	 * @param profileT2
	 * @param rootEntity
	 * @throws TransformerConfigurationException
	 * @throws SAXException
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws IOException
	 */
	public void processThis(WATTSCCNHWABR abr, Profile profileT2, EntityItem rootEntity) throws TransformerConfigurationException,
			SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
		Vector availResultVct = new Vector();
		Vector resultVct = new Vector();
		Vector modelIdVct = new Vector();
		Vector tmfIdVct = new Vector();
		Vector modelconIdVct = new Vector();

		try {
			addDebug("Starting process data from VE");
			// get root ANNOUNCEMENTS from VE
			EntityList entityList = abr.getEntityList(profileT2, getVeName(), rootEntity);
			addDebug("entityList: " + entityList);
			EntityItem annEntity = entityList.getParentEntityGroup().getEntityItem(0);// ANN
			// get Domain info
			String domainValue = PokUtils.getAttributeFlagValue(annEntity, "PDHDOMAIN");
			addDebug("RootEntity:" + annEntity.getKey() + " PDHDOMAIN:" + domainValue);
			// ann filter, Only status is Final 
			String annStatus = PokUtils.getAttributeFlagValue(annEntity, "ANNSTATUS");
			if (!STATUS_FINAL.equals(annStatus)) {
				addDebug("skip " + annEntity.getKey() + " for entity status is not Final");
			}else{
				// get announcement date info
				annDate = getAttributeValue(annEntity, "ANNDATE");
				addDebug("RootEntity:" + annEntity.getKey() + " ANNDATE:" + annDate);

				Vector availVct = PokUtils.getAllLinkedEntities(annEntity, "ANNAVAILA", "AVAIL"); // ANN - AVAIL
				addDebug("avail size " + availVct.size());
				for (int k = 0; k < availVct.size(); k++) {
					EntityItem availEntity = (EntityItem) availVct.get(k); // AVAIL
					addDebug("avail number" + k + 1);
					// avail filter, Only Plan and First Order avail
					String availType = getAttributeFlagValue(availEntity, "AVAILTYPE");
					if (!AVAILTYPE_FILTER.contains(availType)) {
						addDebug("skip " + availEntity.getKey() + " for AVAILTYPE " + availType);
						continue;
					}
					// avail filter, Only contains CCN GEO avail
					String availGeo = getAttributeFlagValue(availEntity, "COUNTRYLIST");
					String[] strArray = splitStr(availGeo , "|");
					Vector sourceStrArray=new Vector();
					for (int i=0;i<strArray.length;i++){
						sourceStrArray.add(strArray[i]);
					}
					
					if (!sourceStrArray.contains(GEO_CCN)) {
						addDebug("skip " + availEntity.getKey() + " for don't have Canada Geo in CountryList");
						continue;
					}
					// avail filter, Only status is Final 
					String availStatus = getAttributeFlagValue(availEntity, "STATUS");
					if (!STATUS_FINAL.equals(availStatus)) {
						addDebug("skip " + availEntity.getKey() + " for entity status is not Final");
					}
					// Get MODELs for path: ANN - AVAIL - MODEL
					Vector modelVct = PokUtils.getAllLinkedEntities(availEntity, "MODELAVAIL", "MODEL");
					addDebug("model size " + modelVct.size());
					// Get PRODSTRUCTs for path: ANN - AVAIL - PRODSTRUCT
					Vector tmfVct = PokUtils.getAllLinkedEntities(availEntity, "OOFAVAIL", "PRODSTRUCT");
					addDebug("tmf size " + tmfVct.size());
					// Get MODELCONVERTs for path: ANN - AVAIL - MODELCONVERT
					Vector modelconVct = PokUtils.getAllLinkedEntities(availEntity, "MODELCONVERTAVAIL", "MODELCONVERT");
					addDebug("modelconvert size " + modelconVct.size());

					if (modelVct.size() > 0 || tmfVct.size() > 0 || modelconVct.size() > 0) { // Not set the avail, if didn't link other entities
						availResultVct.add(availEntity);
					} else {
						addDebug("The " + availEntity.getKey() + " didn't link model and tmf and modelconvert");
					}

					for (int j = 0; j < modelVct.size(); j++) {
						EntityItem modelEntity = (EntityItem) modelVct.get(j);
						// model filter, only HW Model and status is Final
						String modelType = getAttributeFlagValue(modelEntity, "COFCAT");
						String modelStatus = getAttributeFlagValue(modelEntity, "STATUS");
						if (MODEL_HARDWARE.equals(modelType) && STATUS_FINAL.equals(modelStatus)) {
							if (!modelIdVct.contains("" + modelEntity.getEntityID())) {
								modelIdVct.add("" + modelEntity.getEntityID());

								addDebug("HW Model: " + modelEntity.getKey());
								WATTSCCNHWENTITY entity = new WATTSCCNHWENTITY();
								entity.setType("MODEL");
								//get model related attribute value
								entity.setMachtype(getAttributeFlagValue(modelEntity, "MACHTYPEATR"));
								addDebug("MODEL MACHTYPEATR: "
										+ getAttributeFlagValue(modelEntity, "MACHTYPEATR"));
								entity.setModel(getAttributeValue(modelEntity, "MODELATR"));
								addDebug("MODEL MACHTYPEATR: "
										+ getAttributeValue(modelEntity, "MODELATR"));
								entity.setAnnDate(dateFormat(annDate));

								resultVct.add(entity);
							} else {
								addDebug("duplicate " + modelEntity.getKey());
							}
						} else {
							addDebug("skip " + modelEntity.getKey() + " for not HW��MODEL or status is not final");
							continue;
						}
					}

					for (int j = 0; j < tmfVct.size(); j++) {
						EntityItem tmfEntity = (EntityItem) tmfVct.get(j);
						// TMF filter, only status is Final
						String tmfStatus = getAttributeFlagValue(tmfEntity, "STATUS");
						if (STATUS_FINAL.equals(tmfStatus)) {
							if (!tmfIdVct.contains("" + tmfEntity.getEntityID())) {
								tmfIdVct.add("" + tmfEntity.getEntityID());
								addDebug("Prodstruct: " + tmfEntity.getKey());
								// Get MODELs for path: ANN - AVAIL - PRODSTRUCT - MODEL
								EntityItem modelEntity = getModelEntityFromTmf(tmfEntity);
								String stautsModel=getAttributeFlagValue(modelEntity, "STATUS");
								// Get FEATUREs for path: ANN - AVAIL - PRODSTRUCT - FEATURE
								EntityItem featureEntity = getFeatureEntityFromTmf(tmfEntity);
								String stautsFeature=getAttributeFlagValue(featureEntity, "STATUS");
								
								if(STATUS_FINAL.equals(stautsFeature) && STATUS_FINAL.equals(stautsModel)){
									WATTSCCNHWENTITY entity = new WATTSCCNHWENTITY();
									entity.setType("FEATURE");
									//get feature related attribute value
									entity.setMachtype(getAttributeFlagValue(modelEntity, "MACHTYPEATR"));
									addDebug("FEATURE MACHTYPEATR: " + getAttributeFlagValue(modelEntity, "MACHTYPEATR"));
									entity.setFeatureCode(getAttributeValue(featureEntity, "FEATURECODE"));
									addDebug("FEATURE FEATURECODE: " + getAttributeValue(featureEntity, "FEATURECODE"));
									entity.setAnnDate(dateFormat(annDate));
									resultVct.add(entity);
								}else{
									addDebug("skip " + tmfEntity.getKey() + " for linked MODEL and FEATURE status is not all final");
								}
							} else {
								addDebug("duplicate " + tmfEntity.getKey());
							}
						} else {
							addDebug("skip " + tmfEntity.getKey() + " for status is not final");
							continue;
						}
					}

					for (int j = 0; j < modelconVct.size(); j++) {
						EntityItem modelconEntity = (EntityItem) modelconVct.get(j);
						// modelconvert filter, only status is Final
						String modelconStatus = getAttributeFlagValue(modelconEntity, "STATUS");
						if (STATUS_FINAL.equals(modelconStatus)) {
							if (!modelconIdVct.contains(""
									+ modelconEntity.getEntityID())) {
								modelconIdVct.add("" + modelconEntity.getEntityID());

								addDebug("Modelconvert: " + modelconEntity.getKey());
								WATTSCCNHWENTITY entity = new WATTSCCNHWENTITY();
								entity.setType("MODELCONVERT");
								//get modelconvert related attribute value
								entity.setMachtype(getAttributeValue(modelconEntity, "FROMMACHTYPE"));
								addDebug("MODELCONVERT FROMMACHTYPE: " + getAttributeValue(modelconEntity, "FROMMACHTYPE"));
								entity.setModel(getAttributeValue(modelconEntity, "FROMMODEL"));
								addDebug("MODELCONVERT FROMMODEL: " + getAttributeValue(modelconEntity, "FROMMODEL"));
								entity.setToMachtype(getAttributeValue(modelconEntity, "TOMACHTYPE"));
								addDebug("MODELCONVERT TOMACHTYPE: " + getAttributeValue(modelconEntity, "TOMACHTYPE"));
								entity.setToModel(getAttributeValue(modelconEntity, "TOMODEL"));
								addDebug("MODELCONVERT TOMODEL: " + getAttributeValue(modelconEntity, "TOMODEL"));
								entity.setAnnDate(dateFormat(annDate));

								resultVct.add(entity);
							} else {
								addDebug("duplicate " + modelconEntity.getKey());
							}
						} else {
							addDebug("skip " + modelconEntity.getKey() + " for status is not final");
							continue;
						}
					}

				}
				// get related price info
				long startTime = System.currentTimeMillis();
				finalEntities = getPriceInfo(resultVct);
				addDebug("get price info Time: "
						+ Stopwatch.format(System.currentTimeMillis() - startTime));								
			}
		} finally {
			// release memory
			availResultVct.clear();
			availResultVct = null;
			resultVct.clear();
			resultVct = null;
			modelIdVct.clear();
			modelIdVct = null;
			tmfIdVct.clear();
			tmfIdVct = null;
			modelconIdVct.clear();
			modelconIdVct = null;
		}

	}
	
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
	 * set txt file name
	 * 
	 * @param filename
	 */
	private String setFileName(String filename) {
		StringBuffer sb = new StringBuffer(filename.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".txt");
		String dir = ABRServerProperties.getValue(m_abri.getABRCode(), RPTPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		pathName = dir + sb.toString();

		addDebug("**** ffPathName: " + pathName + " ffFileName: " + sb.toString());
		return pathName;
	}

	/**
	 * generate txt file
	 * 
	 * @param filename
	 * @param entities
	 * @throws IOException
	 */
	private boolean generateTXTFile(String filename, Vector entities)
			throws IOException {
		try{
			setFileName(filename);
			FileOutputStream fos = new FileOutputStream(pathName);
			OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < entities.size(); i++) {
				WATTSCCNHWENTITY entity = (WATTSCCNHWENTITY) entities.elementAt(i);
				String priceValue = doubleFormat(entity.getPriceValue());
				String priceType=entity.getPriceType();
				if(priceType == null || priceType.length() <= 0){
					addDebug("filter unhandled pricetype");
				}else{
					if (("MODEL").equals(entity.getType())) {
						sb.append(getValue(entity.getMachtype() , 4));//4 digit
						sb.append(" ");
						sb.append(getValue(entity.getModel() , 3));//3 digit 
						// Fill in 12 blanks,ensure correct file format
						sb.append("            ");
						
					} else if (("FEATURE").equals(entity.getType())) {
						sb.append(getValue(entity.getMachtype() , 4));//4 digit
						sb.append(" ");
						sb.append(getValue(entity.getFeatureCode() , 4));//4 digit 
						// Fill in 11 blanks,ensure correct file format
						sb.append("           ");
						
					} else if (("MODELCONVERT").equals(entity.getType())) {
						sb.append(getValue(entity.getMachtype() , 4));//4 digit
						sb.append(" ");
						sb.append(getValue(entity.getModel() , 3));//3 digit
						sb.append(" ");
						sb.append(getValue(entity.getToMachtype() , 4));//4 digit
						sb.append(" ");
						sb.append(getValue(entity.getToModel() , 3));//3 digit
						sb.append("   ");
						
					} else {
						addDebug("Entity type not processed");
					}
					
					sb.append(getValue(entity.getPriceType() , 3));//3 digit
					sb.append("   ");
					
					//price value has 12 digit, Contains max 9 bit integers, 2 decimal places
					sb.append(getValue(priceValue , 12));					
					sb.append("   ");
					sb.append(entity.getAnnDate());
					sb.append("\r\n");
				}
			}
			wOut.write(sb.toString());
			wOut.flush();
			wOut.close();
			userxmlSb.append("File generate success");
			return true;
	
		}catch(WATTSException e){
			userxmlSb.append(e);
			return false;
		}			
		
	}
	
	/**
	 * ensure correct file format
	 * @param value
	 * @param length
	 * @return
	 * @throws MATTSException
	 */
	public String getValue(String value, int length) throws WATTSException{
		StringBuffer sb = new StringBuffer();
		if(value.length()==length){
			return value;
		} else if(value.length() > length) {
			throw new WATTSException(value + " length too long");
		} else {
			for (int j = 0; j < length - value.length(); j++) {
				sb.append(" ");
			}
			sb.append(value);
			return sb.toString();
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
	 * parse value to Specified format
	 * 
	 * @param value
	 * @return
	 */
	public String doubleFormat(String value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		addDebug(value+" kep 2 decimal "+bd.toString());
		return bd.toString();
	}

	/**
	 * send mail with txt file generated
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void sendMail(String file) throws Exception {

		FileInputStream fisBlob = null;
		try {
			fisBlob = new FileInputStream(file);
			int iSize = fisBlob.available();
			byte[] baBlob = new byte[iSize];
			fisBlob.read(baBlob);
			setAttachByteForDG(baBlob);
			getABRItem().setFileExtension("txt");
			addDebug("Sending mail for file " + file);
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
	 * get TMF related Model entity
	 * 
	 * @param tmfEntity
	 * @return
	 */
	private EntityItem getModelEntityFromTmf(EntityItem tmfEntity) {
		EntityItem modelItem = null;
		Vector linkVct = tmfEntity.getDownLink();
		if (linkVct != null && linkVct.size() > 0) {
			for (int k = 0; k < linkVct.size(); k++) {
				EntityItem item = (EntityItem) linkVct.get(k);
				if ("MODEL".equals(item.getEntityType())) {
					modelItem = item;
					break;
				}
			}
		}
		return modelItem;
	}

	/**
	 * get TMF related Feature entity
	 * 
	 * @param tmfEntity
	 * @return
	 */
	private EntityItem getFeatureEntityFromTmf(EntityItem tmfEntity) {
		EntityItem featureItem = null;
		Vector featureVct = tmfEntity.getUpLink();
		if (featureVct != null && featureVct.size() > 0) {
			for (int k = 0; k < featureVct.size(); k++) {
				EntityItem item = (EntityItem) featureVct.get(k);
				if ("FEATURE".equals(item.getEntityType())) {
					featureItem = item;
					break;
				}
			}
		}
		return featureItem;
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

	/**
	 * return ve name
	 * 
	 * @return
	 */
	public String getVeName() {
		return "CCNWATTSHWANN";
	}

	/**
	 * select price info from ods db's price.price table
	 * 
	 * @param entities
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private Vector getPriceInfo(Vector entities) throws SQLException,
			MiddlewareException {

		Vector finalEntities = new Vector();
		ResultSet result = null;
		PreparedStatement statement = null;
		try {
			Connection conn = m_db.getODSConnection();
			for (int i = 0; i < entities.size(); i++) {
				WATTSCCNHWENTITY entity = (WATTSCCNHWENTITY) entities
						.elementAt(i);
				StringBuffer strbSQL = new StringBuffer();
				if ("MODEL".equals(entity.getType())) {
					addDebug("Get MODEL price info---------------");
					strbSQL.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='MODEL' and price_point_type='MOD' and machtypeatr='");
					strbSQL.append(entity.getMachtype());
					strbSQL.append("' and modelatr='");
					strbSQL.append(entity.getModel());
					strbSQL.append("' and start_date <= '");
					strbSQL.append(annDate);
					strbSQL.append("' and end_date >'");
					strbSQL.append(annDate);
					strbSQL.append("'");
							
					addDebug("SQL:" + strbSQL);
					addDebug("MODEL info: machtypeatr:" + entity.getMachtype() + ",modelatr:" + entity.getModel());
				} else if ("FEATURE".equals(entity.getType())) {
					addDebug("Get FEATURE price info---------------");
					strbSQL.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='FEATURE' and machtypeatr='");
					strbSQL.append(entity.getMachtype());
					strbSQL.append("' and featurecode='");
					strbSQL.append(entity.getFeatureCode());
					strbSQL.append("' and start_date <= '");
					strbSQL.append(annDate);
					strbSQL.append("' and end_date >'");
					strbSQL.append(annDate);
					strbSQL.append("'");
							
					addDebug("SQL:" + strbSQL);
					addDebug("FEATURE info: machtypeatr:" + entity.getMachtype() + ",featurecode:" + entity.getFeatureCode());
				} else if ("MODELCONVERT".equals(entity.getType())) {
					addDebug("Get MODELCONVERT price info---------------");
					strbSQL.append("select  price_value_usd, price_type from price.price where action='I' and currency ='USD' and offering_type='MODELCONVERT' and machtypeatr='");
					strbSQL.append(entity.getToMachtype());		 
					strbSQL.append("' and modelatr='");
					strbSQL.append(entity.getToModel());
					strbSQL.append("' and from_machtypeatr='");
					strbSQL.append(entity.getMachtype());
					strbSQL.append("' and from_modelatr='");
					strbSQL.append(entity.getModel());
					strbSQL.append("' and start_date <= '");
					strbSQL.append(annDate);
					strbSQL.append("' and end_date >'");
					strbSQL.append(annDate);
					strbSQL.append("'");
						
					addDebug("SQL:" + strbSQL);
					addDebug("MODELCONVERT info: from_machtypeatr:" + entity.getMachtype() + ",from_model:"
							+ entity.getModel() + ",to_machtypeatr:" + entity.getToMachtype() + ",to_modelatr:"
							+ entity.getToModel());
				} else {
					addDebug("can not handle entitytype "+entity.getType());
					continue;
				}

				statement = conn.prepareStatement(strbSQL.toString());
				result = statement.executeQuery();
				String priceValue=null;
				String priceType=null;
				if (result.next()) {					 	
					priceValue = result.getString(1);
					priceType = convertPriceType(result.getString(2));									
				} else {
					priceValue = "0.00";
					priceType = "  5";					
				}
				addDebug("price info: price_value " + priceValue + " price_type " + priceType);

				entity.setPriceValue(priceValue);
				entity.setPriceType(priceType);
				finalEntities.add(entity);
				
				strbSQL = null;
			}

			return finalEntities;

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
	 * convert WWPRT price_type to WTAAS��price_type
	 * 
	 * @param string
	 * @return
	 */
	private String convertPriceType(String string) {
		
		HashMap priceType=new HashMap();
		priceType.put("PUR", "  5");
		priceType.put("MES", "  5");
		priceType.put("WU3", "NRR");
		priceType.put("WU4", "OR5");
		priceType.put("WU1", "OR7");
		priceType.put("EMC", "0BC");
		priceType.put("EMG", "0HC");
		priceType.put("EMF", "0MC");
		priceType.put("EMD", "NOR");
		priceType.put("EMH", "0RD");
		priceType.put("EMS", "P01");
		priceType.put("EMM", "P02");
		
		if (priceType.containsKey(string)) {
			addDebug("convert WWPRT price type " + string + " to WTAAS price type " + priceType.get(string));
			return (String) priceType.get(string);
	    } else {
	        return null;
	    }
	}

	public String getDescription() {
		return "WATTSSTATUSABR";
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
		args[4] = "WATTS CCN HW ABR feed trigger<br/>" + xmlgenSb.toString();
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

class WATTSException extends Exception
{
	public WATTSException(String msg)
	{
		super(msg);
	}
} 

