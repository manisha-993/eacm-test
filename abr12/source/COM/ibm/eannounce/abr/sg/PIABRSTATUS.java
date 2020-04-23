package COM.ibm.eannounce.abr.sg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class PIABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private String fileprefix = null;
	private String ffFileName = null; // CQ00016165
	private String ffPathName = null;
	private Hashtable metaTbl = new Hashtable();
	public final static String RPTPATH = "_rptpath";
	public final static String GEOS = "_geos";
	public final static String ISLMPRN = "ISLMPRN";
	public final static String MKTGNAME = "MKTGNAME";
	public final static String BLANKCHARACTER = ",";
	private static final Hashtable COLUMN_LENGTH;
	String geoStr = null;
	boolean hasData = false;

	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(ISLMPRN, "14");
		COLUMN_LENGTH.put(MKTGNAME, "254");
	}

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
		boolean creatFile = true;
		boolean sentFile = true;

		MessageFormat msgf;
		String abrversion = "";
		String rootDesc = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			setDGTitle("PIABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("PIABRSTATUS"); // Set the report name
			setDGRptClass("PIABRSTATUS"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(false); // pull the VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			m_elist = getEntityList(getVEName());
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

			generateFlatFile(rootEntity);
			sendMail(ffPathName);
			// exeFtpShell(ffPathName);
			// ftpFile();

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
			creatFile = false;
			// sentFile=exeFtpShell(ffPathName);
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append(creatFile ? "generated the Pipackage report file successful "
					: "generated the Pipackage report file faild");
			sb.append(",");
			if (!hasData)
				sb.append("No EMEA Data!");
			else
				sb.append("Generated the Pipackage report file successful ");
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();

			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	/*
	 * CQ00016165 The Files will be named as follows: 1. 'EACMFEEDQSMABR_' 2.
	 * The DB2 DTS for T2 with spaces and special characters replaced with an
	 * underscore 3. '.txt'
	 */
	private void setFileName() {
		// FILE_PREFIX=EACMFEEDQSMABR_
		fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".csv");
		String dir = ABRServerProperties.getValue(m_abri.getABRCode(), RPTPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
		addDebug("**** mmiotto ffPathName: " + ffPathName + " ffFileName: " + ffFileName);
	}

	private void generateFlatFile(EntityItem rootEntity) throws IOException {
		// TODO Auto-generated method stub
		setFileName();
		FileOutputStream fos = new FileOutputStream(ffPathName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
		StringBuffer sb = new StringBuffer();
		String strEmeabrandcd = "";
		String strFSLMCPU = "";
		String strModelSysdUnit = "";
		String strGeoModIntegratedModel = "";
		String strDescription = "";
		String strModelSpecModDesgn = "";
		String strAcronym = "";
		String latestDate = null;
		String strPdCategory = "Product Categories:" + NEWLINE;
		String strAvail = "General Availability Date:" + NEWLINE;
		String strAnn = "RFA Number:" + PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", ",", "", false) + NEWLINE;
		strAnn += "Annoucement Date:" + NEWLINE;
		strPdCategory += "\"" + PokUtils.getAttributeValue(rootEntity, "PRODCATEGORY", ",", "", false) + "\"" + NEWLINE;
		strAnn += PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false) + NEW_LINE;
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		String strHeader = "PiPackage Notes:\nPRODUCTS/MATERIALS/UPGRADES:\nMaterial/Product,Description,CPU-Indicator (for Models only)\n";
		StringBuffer strModel = new StringBuffer("Models:,MKTGNAME\n");
		StringBuffer strFeature = new StringBuffer("Features:,MKTGNAME\n");
		StringBuffer strModelCovert = new StringBuffer("Type Model Upgrades:\n");
		addDebug("*****mlm availGrp.getEntityItemCount() = " + availGrp.getEntityItemCount());
		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			EntityItem availEI = availGrp.getEntityItem(availI);
			String strAvailGenAreaSel = PokUtils.getAttributeValue(availEI, "GENAREASELECTION", "", "");
			addDebug("*****mlm strAvailGenAreaSel:" + strAvailGenAreaSel);
			if (fliter(strAvailGenAreaSel)) {
				if (latestDate == null)
					latestDate = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
				else
					latestDate = getEarliestDate(latestDate,
							PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false));

				hasData = true;
				Vector modelVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");
				addDebug("*****mlm modelVect = " + modelVect);
				addDebug("*****mlm modelVect Size = " + modelVect.size());
				for (int i = 0; i < modelVect.size(); i++) {

					// sb.append(getValue(ISLMRFA,
					// PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", ",",
					// "",
					// false)));

					EntityItem eiModel = (EntityItem) modelVect.elementAt(i);
					String machtype = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
					String modelart = PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");

					strModel.append(getValue(ISLMPRN, machtype + modelart));
					strModel.append(BLANKCHARACTER);
					String mktgname = getValue(MKTGNAME, PokUtils.getAttributeValue(eiModel, "MKTGNAME", "", ""));
					strModel.append(mktgname);

					strModelSysdUnit = PokUtils.getAttributeValue(eiModel, "SYSIDUNIT", "", "");
					if (strModelSysdUnit != null && strModelSysdUnit.equals("SIU-CPU")) {
						strModel.append(BLANKCHARACTER);
						strModel.append("Y");
					}
					strModel.append(NEWLINE);
					addDebug("*****mlm eimodel = " + eiModel);
					addDebug("*****mlm eimodel.entityid= " + eiModel.getEntityID());
					addDebug("*****mlm eimodel.entitytype = " + eiModel.getEntityType());
					Vector featureVect = PokUtils.getAllLinkedEntities(eiModel, "PRODSTRUCT", "FEATURE");
					addDebug("*****mlm featureVect = " + featureVect);
					addDebug("******mlm featureVect.size() = " + featureVect.size());
					String featureCode = null;
					for (int j = 0; j < featureVect.size(); j++) {
						EntityItem eiFeature = (EntityItem) featureVect.get(j);

						featureCode = getValue(ISLMPRN,
								PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "", false));
						String featureMktgName = getValue(MKTGNAME,
								PokUtils.getAttributeValue(eiFeature, "MKTGNAME", "", ""));
						String fctype  = getValue(ISLMPRN,
								PokUtils.getAttributeValue(eiFeature, "FCTYPE", "", "", false));
						if (featureCode == null || featureCode.trim().equals("") || featureCode.startsWith("8P")
								|| featureMktgName == null || featureMktgName.trim().equals("") || fctype == null
								|| fctype.trim().equals("Secondary FC")||fctype.trim().equals("Secondary FC")||fctype.trim().equals("RPQ-ILISTED")||fctype.trim().equals("RPQ-PLISTED")||fctype.trim().equals("RPQ-RLISTED"))
							continue;

						strFeature.append(machtype + featureCode);
						strFeature.append(BLANKCHARACTER);
						strFeature.append(featureMktgName);
						strFeature.append(NEWLINE);
						addDebug("*****mlm eiFeature = " + eiFeature);
						addDebug("*****mlm eiFeature.entityid= " + eiFeature.getEntityID());
						addDebug("*****mlm eiFeature.entitytype = " + eiFeature.getEntityType());
					}

				}
				Vector modelConvertVect = PokUtils.getAllLinkedEntities(availEI, "MODELCONVERTAVAIL", "MODELCONVERT");
				addDebug("*****mlm modelconvertvect = " + modelConvertVect);
				addDebug("******mlm modelconvertvect.size() = " + modelConvertVect.size());
				for (int i = 0; i < modelConvertVect.size(); i++) {
					EntityItem eiModelConvert = (EntityItem) modelConvertVect.elementAt(i);
					addDebug("*****mlm modelconvert.entityid= " + eiModelConvert.getEntityID());
					addDebug("*****mlm modelconvert.entitytype = " + eiModelConvert.getEntityType());

					strDescription = "";
					strModelSpecModDesgn = "";
					strAcronym = "";
					strDescription = PokUtils.getAttributeValue(eiModelConvert, "FROMMACHTYPE", "", "", false);
					strDescription += PokUtils.getAttributeValue(eiModelConvert, "FROMMODEL", "", "", false);
					strModelCovert.append(getValue(ISLMPRN, strDescription));
					strModelCovert.append(BLANKCHARACTER);
					strDescription = PokUtils.getAttributeValue(eiModelConvert, "TOMACHTYPE", "", "", false);
					strDescription += PokUtils.getAttributeValue(eiModelConvert, "TOMODEL", "", "", false);
					strModelCovert.append(getValue(ISLMPRN, strDescription));
					strModelCovert.append(NEWLINE);
				}
				addDebug("*****SB***:" + sb.toString());
			}
		}
		strAvail += latestDate + NEWLINE;
		sb.append(strAnn);
		sb.append(strAvail);
		sb.append(strPdCategory);
		sb.append(strHeader);
		sb.append(strModel);
		sb.append(strFeature);
		sb.append(strModelCovert);
		wOut.write(sb.toString());
		wOut.flush();
	}

	private String getVEName() {
		// TODO Auto-generated method stub
		return "EXTPIVE";
	}

	public boolean fliter(String geos) {
		addDebug("geos:" + geos);
		if (geoStr == null) {
			geoStr = ABRServerProperties.getValue(m_abri.getABRCode(), GEOS, "ALL");
			addDebug("geoStr:" + geoStr);
		}
		if (geos.equals("ALL"))
			return true;

		return geoStr.indexOf(geos) > -1;
	}

	public String getEarliestDate(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return DATE2;
			} else {

				return DATE1;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DATE1;
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

	/******************************************
	 * get entitylist used for compares
	 */
	private EntityList getEntityList(String veName)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {
		addDebug("*****mlmVE name is " + m_abri.getVEName());
		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, veName);

		addDebug("*****mlmCreating Entity List");
		addDebug("*****mlmProfile is " + m_prof);
		addDebug("*****mlmRole is " + m_prof.getRoleCode() + " : " + m_prof.getRoleDescription());
		addDebug("*****mlmExtract action Item is" + eaItem);
		EntityList list = m_db.getEntityList(m_prof, eaItem,
				new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

		// debug display list of groups
		addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName + " contains the following entities: \n"
				+ PokUtils.outputList(list));

		return list;
	}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
	}

	protected String getValue(String column, String columnValue) {
		if (columnValue == null)
			columnValue = "";
		if (columnValue.indexOf(",") > -1) {
			columnValue = "\"" + columnValue + "\"";
		}
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnLength = Integer.parseInt(COLUMN_LENGTH.get(column).toString());
		if (columnValueLength == columnLength)
			return columnValue;
		if (columnValueLength > columnLength)
			return columnValue.substring(0, columnLength);

		return columnValue;
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}

	// Send mail

	// EXTPIVE
	public void sendMail(String file) throws Exception {

		FileInputStream fisBlob = null;
		try {
			fisBlob = new FileInputStream(file);
			int iSize = fisBlob.available();
			byte[] baBlob = new byte[iSize];
			fisBlob.read(baBlob);
			setAttachByteForDG(baBlob);
			getABRItem().setFileExtension("csv");
			addDebug("Sending mail for file " + file);
		} catch (IOException e) {
			// TODO: handle exception
			addDebug("sendMail IO Exception");
		}

		finally {
			if (fisBlob != null) {
				fisBlob.close();
			}
		}

	}
	/*
	 * 
	 * Vector annGenAreaVec = PokUtils.getAllLinkedEntities(rootEntity,
	 * "ANNGAA", "GENERALAREA"); for (int a = 0; a < annGenAreaVec.size(); a++){
	 * EntityItem annGenAreaEI = (EntityItem) annGenAreaVec.elementAt(a); String
	 * geo = PokUtils.getAttributeValue(annGenAreaEI, "RFAGEO", "", "");
	 * addDebug("*****mlm feature RFAGEO=" + geo); if (geo.equals("AP")){ ap =
	 * "Y"; } else if (geo.equals("CCN")) { cn = "Y"; } else if
	 * (geo.equals("EMEA")) { emea = "Y"; } else if (geo.equals("LA")) { la =
	 * "Y"; } else if (geo.equals("US")) { us = "Y"; } }
	 */
}
