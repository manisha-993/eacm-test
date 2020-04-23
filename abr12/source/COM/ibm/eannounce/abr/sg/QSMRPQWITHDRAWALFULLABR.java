
package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

/**
 * @author whwyue QSM RPQ withdrawal ABR
 *
 */
//$Log: QSMRPQWITHDRAWALFULLABR.java,v $
//Revision 1.5  2018/09/19 12:50:14  whwyue
//Update the IFTYPE
//
//Revision 1.4  2018/09/18 10:03:44  whwyue
//Update feature logic
//
//Revision 1.3  2018/09/18 09:51:08  whwyue
//RPQ abr for withdrawal full file
//
public class QSMRPQWITHDRAWALFULLABR extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);

	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";

	private String ffFileName = null;
	private String ffPathName = null;
	private String ffFTPPathName = null;
	private String dir = null;
	private String dirDest = null;
	private final String QSMRPTPATH = "_rptpath";
	private final String QSMGENPATH = "_genpath";
	private final String QSMFTPPATH = "_ftppath";
	private int abr_debuglvl = D.EBUG_ERR;
	private String abrcode = "";

	private static final String CREFINIPATH = "_inipath";
	private static final String FTPSCRPATH = "_script";
	private static final String TARGETFILENAME = "_wdtargetfilename";
	private static final String LOGPATH = "_logpath";
	private static final String BACKUPPATH = "_backuppath";
	private static final String FILEPREFIX = "_wdfilePrefix";
	private String lineStr = "";
	private EntityItem rootEntity;
	private static final String FAILD = "FAILD";
	private static final String IFTYPE = "IFTYPE";
	private static final String IOPUCTY = "IOPUCTY";
	private static final String ISLMPAL = "ISLMPAL";
	private static final String ISLMRFA = "ISLMRFA";
	private static final String ISLMPRN = "ISLMPRN";
	private static final String DSLMWDN = "DSLMWDN";
	private static final String ISLMTYP = "ISLMTYP";
	private static final String ISLMMOD = "ISLMMOD";
	private static final String ISLMFTR = "ISLMFTR";
	private static final Hashtable COLUMN_LENGTH;

	private static final List geoWWList = Collections.unmodifiableList(new ArrayList() {
		{
			add("Asia Pacific");
			add("Canada and Caribbean North");
			add("Europe/Middle East/Africa");
			add("Latin America");
			add("US Only");
		}
	});

	/**
	 * Record format defintion Form (record name), (record length), <starting
	 * postion>, <type>
	 */
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(IFTYPE, "20");
		COLUMN_LENGTH.put(IOPUCTY, "3");
		COLUMN_LENGTH.put(ISLMPAL, "8");
		COLUMN_LENGTH.put(ISLMRFA, "6");
		COLUMN_LENGTH.put(ISLMPRN, "14");
		COLUMN_LENGTH.put(DSLMWDN, "10");
		COLUMN_LENGTH.put(ISLMTYP, "4");
		COLUMN_LENGTH.put(ISLMMOD, "3");
		COLUMN_LENGTH.put(ISLMFTR, "6");
	}

	/**********************************
	 * get the resource bundle
	 */
	protected ResourceBundle getBundle() {
		return rsBundle;
	}

	/**********************************
	 * Execute ABR.
	 *
	 */
	public void processThis(QSMRPQFULLABRSTATUS abr, Profile m_prof, Database m_db, String abri, StringBuffer rpt,
			EntityItem rootEntity) throws Exception {
		// String header1 = "";
		// boolean creatFile = true;
		// boolean sentFile = true;

		// MessageFormat msgf;
		// String abrversion="";

		// Object[] args = new String[10];

		try {
			this.m_prof = m_prof;
			this.m_db = m_db;
			this.rootEntity = rootEntity;
			abrcode = abri;
			rptSb = rpt;

			m_elist = getEntityList(getFeatureVEName());

			if (m_elist.getEntityGroupCount() > 0) {
//				setDGTitle("QSMRPQWITHDRWALFULLABR report");
//				setDGString(getABRReturnCode());
//				setDGRptName("QSMRPQWITHDRWALFULLABR"); // Set the report name
//				setDGRptClass("QSMRPQWITHDRWALFULLABR"); // Set the report class
//				navName = getNavigationName();
				generateFlatFile(rootEntity);
				exeFtpShell(ffPathName);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setFileName(EntityItem rootEntity) {
		String filePrefix = ABRServerProperties.getValue(abrcode, FILEPREFIX, null);
		StringBuffer sb = new StringBuffer(filePrefix.trim());
		DatePackage dbNow;
		try {
			dbNow = m_db.getDates();
			String dts = dbNow.getNow();
			// replace special characters
			dts = dts.replace(' ', '_');
			sb.append(rootEntity.getEntityType() + rootEntity.getEntityID() + "_");
			sb.append(dts + ".txt");
			dir = ABRServerProperties.getValue(abrcode, QSMGENPATH, "/Dgq");
			if (!dir.endsWith("/")) {
				dir = dir + "/";
			}
			ffFileName = sb.toString();
			ffPathName = dir + ffFileName;
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/********************************************
	 * Build a text file using all The records are data based on the type of
	 * data described above.
	 * 
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void generateFlatFile(EntityItem rootEntity)
			throws IOException, SQLException, MiddlewareException, ParseException {

		FileChannel sourceChannel = null;
		FileChannel destChannel = null;

		// generate file name
		setFileName(rootEntity);

		FileOutputStream fos = new FileOutputStream(ffPathName);
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");

		createT006Feature(rootEntity, wOut);
		createT632TypeModelFeatureRelation(rootEntity, wOut);

		wOut.close();

		dirDest = ABRServerProperties.getValue(abrcode, QSMFTPPATH, "/Dgq");
		if (!dirDest.endsWith("/")) {
			dirDest = dirDest + "/";
		}

		ffFTPPathName = dirDest + ffFileName;

		try {
			sourceChannel = new FileInputStream(ffPathName).getChannel();
			destChannel = new FileOutputStream(ffFTPPathName).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}

	}

	/*
	 * Create records for T006 FEATURE
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006Feature(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute featGeoList;

		featGeoList = null;

		EntityGroup prodstructGrp = m_elist.getEntityGroup("PRODSTRUCT");

		for (int pI = 0; pI < prodstructGrp.getEntityItemCount(); pI++) {

			EntityItem eiProdstruct = prodstructGrp.getEntityItem(pI);

			featGeoList = (EANFlagAttribute) rootEntity.getAttribute("GENAREASELECTION");
			if (featGeoList != null) {
				if (featGeoList.isSelected("1999")) {
					for (int i = 0; i < geoWWList.size(); i++) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, (String) geoWWList.get(i));
					}
				} else {
					if (featGeoList.isSelected("6199")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Asia Pacific");
					}
					if (featGeoList.isSelected("6200")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Canada and Caribbean North");
					}
					if (featGeoList.isSelected("6198")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Europe/Middle East/Africa");
					}
					if (featGeoList.isSelected("6204")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Latin America");
					}
					if (featGeoList.isSelected("6221")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "US Only");
					}
				}
			}
		}
	}

	/*
	 * Create records for T006 FEATURE
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006FeatureRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem eiProdstruct,
			String strRootGenAreaSel) throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strIOPUCTY;
		String strDSLMWDN;
		String strISLMPAL;

		Vector modelVect = PokUtils.getAllLinkedEntities(rootEntity, "PRODSTRUCT", "MODEL");

		for (int i = 0; i < modelVect.size(); i++) {
			sb = new StringBuffer();
			EntityItem eiModel = (EntityItem) modelVect.elementAt(i);
			sb = new StringBuffer();
			strIOPUCTY = "";
			strDSLMWDN = "";
			strISLMPAL = "";
			strDSLMWDN = "";

			sb.append(getValue(IFTYPE, "F=CHKT631&UPGT005"));

			if (strRootGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			}

			sb.append(getValue(IOPUCTY, strIOPUCTY));
			sb.append(getValue(ISLMPAL, strISLMPAL));
			sb.append(getValue(ISLMRFA, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
			String strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", ",", "", false);
			strISLMPRN += PokUtils.getAttributeValue(rootEntity, "FEATURECODE", ",", "", false);
			sb.append(getValue(ISLMPRN, strISLMPRN));

			strDSLMWDN = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATEEFF_T", ",", "", false);
			if (strDSLMWDN.equals("")) {
				strDSLMWDN = "2050-12-31";
			}
			sb.append(getValue(DSLMWDN, strDSLMWDN));

			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();

		}
	}

	/******
	 * Create T632 records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void createT632TypeModelFeatureRelation(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strDSLMWDN;

		m_elist = getEntityList(getFeatureVEName());

		// Create T632 records only for US ONLY and WorldWide GEOs
		EANFlagAttribute featGenAreaList = (EANFlagAttribute) rootEntity.getAttribute("GENAREASELECTION");
		if (featGenAreaList != null) {
			if (featGenAreaList.isSelected("6221") || featGenAreaList.isSelected("1999")) {

				EntityGroup prodstructGrp = m_elist.getEntityGroup("PRODSTRUCT");

				for (int i = 0; i < prodstructGrp.getEntityItemCount(); i++) {
					sb = new StringBuffer();

					EntityItem eiProdstruct = prodstructGrp.getEntityItem(i);

					Vector modelVect = eiProdstruct.getDownLink();

					for (int im = 0; im < modelVect.size(); im++) {

						EntityItem eiModel = (EntityItem) modelVect.elementAt(im);

						sb = new StringBuffer();
						strDSLMWDN = "";

						sb.append(getValue(IFTYPE, "T=(CHK&UPG)T631"));
						sb.append(getValue(IOPUCTY, "897"));
						sb.append(getValue(ISLMPAL, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMRFA, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMTYP, PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "")));
						sb.append(getValue(ISLMMOD, PokUtils.getAttributeValue(eiModel, "MODELATR", "", "")));
						sb.append(getValue(ISLMFTR, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						strDSLMWDN = PokUtils.getAttributeValue(eiProdstruct, "WTHDRWEFFCTVDATE", "", "");
						if (strDSLMWDN.equals("")) {
							strDSLMWDN = "2050-12-31";
						}

						sb.append(getValue(DSLMWDN, strDSLMWDN));
	
						sb.append(NEWLINE);
						wOut.write(sb.toString());
						wOut.flush();

					}
				}
			}
		}
	}

	protected String getValue(String column, String columnValue) {
		if (columnValue == null)
			columnValue = "";
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnLength = Integer.parseInt(COLUMN_LENGTH.get(column).toString());
		if (columnValueLength == columnLength)
			return columnValue;
		if (columnValueLength > columnLength)
			return columnValue.substring(0, columnLength);

		return columnValue + getBlank(columnLength - columnValueLength);
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}

	/******************************************
	 * get entitylist used for compares
	 */
	private EntityList getEntityList(String veName)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {
		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, veName);

		EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] {
				new EntityItem(null, m_prof, rootEntity.getEntityType(), rootEntity.getEntityID()) });

		// debug display list of groups
		addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName + " contains the following entities: \n"
				+ PokUtils.outputList(list));

		return list;
	}

	/**********************************
	 * get the name of the VE that returns all data
	 * 
	 * @return java.lang.String
	 */
	public String getFeatureVEName() {
		return "QSMRPQFULLVE1";
	}

	public boolean exeFtpShell(String fileName) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(abrcode, FTPSCRPATH, null) + " -f " + fileName;
		String ibiinipath = ABRServerProperties.getValue(abrcode, CREFINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		if (dir != null)
			cmd += " -d " + dir;
		String filePrefix = ABRServerProperties.getValue(abrcode, FILEPREFIX, null);
		if (filePrefix != null)
			cmd += " -p " + filePrefix;
		String targetFilePath = ABRServerProperties.getValue(abrcode, TARGETFILENAME, null);
		if (targetFilePath != null)
			cmd += " -t " + targetFilePath;
		String logPath = ABRServerProperties.getValue(abrcode, LOGPATH, null);
		if (logPath != null)
			cmd += " -l " + logPath;
		String backupPath = ABRServerProperties.getValue(abrcode, BACKUPPATH, null);
		if (backupPath != null)
			cmd += " -b " + backupPath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		addDebug("cmd:" + cmd);
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
				if (lineStr.indexOf(FAILD) > -1) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result == null ? false : true;
	}

	/**********************************
	 * add msg to report output
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**********************************
	 * used for output
	 *
	 */
	protected void addOutput(String resrcCode, Object args[]) {
		String msg = getBundle().getString(resrcCode);
		if (args != null) {
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msg);
	}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
	}

	/**********************************
	 * add error info and fail abr
	 */
	protected void addError(String msg) {
		addOutput(msg);
		setReturnCode(FAIL);
	}

	/**********************************
	 * used for error output Prefix with LD(EntityType) NDN(EntityType) of the
	 * EntityType that the ABR is checking (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	protected void addError(String errCode, Object args[]) {
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		// ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	}

	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[]) {
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args != null) {
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix + " " + msg);
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for root entity
	 *
	 * @return java.lang.String
	 */
//	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
//		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
//	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */
//	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
//		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
//		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
//		if (metaList == null) {
//			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
//			metaList = eg.getMetaAttribute(); // iterator does not maintain
//												// navigate order
//			metaTbl.put(theItem.getEntityType(), metaList);
//		}
//		for (int ii = 0; ii < metaList.size(); ii++) {
//			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
//			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
//			if (ii + 1 < metaList.size()) {
//				navName.append(" ");
//			}
//		}

//		return navName.toString();
//	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.0";
	}

	/***********************************************
	 * Get ABR description
	 *
	 * @return java.lang.String
	 */
	public String getDescription() {
		return "QSMRPQWITHDRAWALFULLABR";
	}

}
