
// QSM withdrawal abr

package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import COM.ibm.opicmpdh.transactions.OPICMABRItem;

/**********************************************************************************
* QSM withdrawal ABR
**********************************************************************************/
//$Log: QSMWITHDRAWALFULLABR.java,v $
//Revision 1.5  2018/09/20 07:28:54  whwyue
//update format for type feature
//
//Revision 1.4  2018/09/19 12:51:32  whwyue
//Update logic for type feature
//
//Revision 1.3  2018/09/18 09:45:21  whwyue
//Update log
//
//
/**
 * @author whwyue
 *
 */
public class QSMWITHDRAWALFULLABR extends PokBaseABR {
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

	private static final String CREFINIPATH = "_inipath";
	private static final String FTPSCRPATH = "_script";
	private static final String TARGETFILENAME = "_wdtargetfilename";
	private static final String LOGPATH = "_logpath";
	private static final String BACKUPPATH = "_backuppath";
	private static final String FILEPREFIX = "_wdfilePrefix";
	private String lineStr = "";
	private static final String FAILD = "FAILD";
	private static final String IFTYPE = "IFTYPE";
	private static final String IOPUCTY = "IOPUCTY";
	private static final String ISLMPRN = "ISLMPRN";
	private static final String DSLMOPD = "DSLMOPD";
	private static final String DSLMWDN = "DSLMWDN";
	private static final String DUSALRW = "DUSALRW";
	private static final String QSMEDMW = "QSMEDMW";
	private static final String CPDAA = "CPDAA";
	private static final String BLANK = "BLANK";
	private static final String BLANK1 = "BLANK1";
	private static final String BLANK2 = "BLANK2";
	private String abrcode = "";
	private EntityItem rootEntity;
	private static final Hashtable COLUMN_LENGTH;

	private static final String ISLMTYP = "ISLMTYP";
	private static final String ISLMMOD = "ISLMMOD";
	private static final String ISLMFTR = "ISLMFTR";
	private static final String ISLMRFA = "ISLMRFA";
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
		COLUMN_LENGTH.put(ISLMPRN, "14");
		COLUMN_LENGTH.put(DSLMOPD, "10");
		COLUMN_LENGTH.put(DSLMWDN, "10");
		COLUMN_LENGTH.put(QSMEDMW, "10");
		COLUMN_LENGTH.put(CPDAA, "1");
		COLUMN_LENGTH.put(DUSALRW, "10");
		COLUMN_LENGTH.put(ISLMTYP, "4");
		COLUMN_LENGTH.put(ISLMMOD, "3");
		COLUMN_LENGTH.put(ISLMFTR, "6");
		COLUMN_LENGTH.put(BLANK, "1");
		COLUMN_LENGTH.put(ISLMRFA, "6");
		COLUMN_LENGTH.put(BLANK1, "32");
		COLUMN_LENGTH.put(BLANK2, "42");
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

	public void processThis(QSMFULLABRSTATUS abr, Profile m_prof, Database m_db, String abri, EntityItem rootEntity,
			StringBuffer rpt) throws Exception {
		try {
			this.m_prof = m_prof;
			this.m_db = m_db;
			this.rootEntity = rootEntity;
			rptSb = rpt;
			abrcode = abri;
			// Default set to pass
			setReturnCode(PASS);

			m_elist = getEntityList(getT002ModelVEName());

			if (m_elist.getEntityGroupCount() > 0) {
				// NAME is navigate attributes - only used if error rpt is
				// generated
//				navName = getNavigationName();
//				setDGTitle(navName);
//				addDebug("Title=" + navName);
//				setDGString(getABRReturnCode());				
//				setDGRptName("QSMWITHDRAWALFULLABR"); // Set the report name
//				setDGRptClass(getABRCode()); // Set the report class
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
		// FILE_PREFIX=EACMFEEDQSMFULLABR_
		String filePrefix = ABRServerProperties.getValue(abrcode, FILEPREFIX, null);
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
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

		// build the text file

		createT002Model(rootEntity, wOut);
		createT006Feature(rootEntity, wOut);
		createT020NPMesUpgrade(rootEntity, wOut);
		createT632TypeModelFeatureRelation(rootEntity, wOut);

		wOut.close();

		dirDest = ABRServerProperties.getValue(abrcode, QSMFTPPATH, "/Dgq");
		if (!dirDest.endsWith("/")) {
			dirDest = dirDest + "/";
		}

		ffFTPPathName = dirDest + ffFileName;
		addDebug("******* " + ffFTPPathName);

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
	 * Create T002 model records
	 * 
	 * @throws IOException
	 */
	private void createT002Model(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException {
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		String strAvailType = "";
		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {

			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			// ******* NEW *******
			if ((strAvailType.equals("Planned Availability")) || (strAvailType.equals("End of Service"))
					|| (strAvailType.equals("Last Order"))) {
				EANFlagAttribute qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");

				if (qsmGeoList != null) {
					if (qsmGeoList.isSelected("6199")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Asia Pacific", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6200")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Canada and Caribbean North", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6198")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Europe/Middle East/Africa", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6204")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Latin America", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6221")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "US Only", strAvailType, isEpic);
					}
				}
			}
		}
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> MODELAVAIL -> MODEL
	 * 
	 * @throws IOException
	 */
	private void createT002ModelRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem availEI,
			String strAvailGenAreaSel, String strMainAvailType, boolean isEpic) throws IOException {

		StringBuffer sb;
		String strIOPUCTY;
		String strQSMEDMW;
		String strDUSALRW;
		String strDSLMWDN;
		String strDSLMOPD;
		String strCPDAA;
		String strWTHDRWEFFCTVDATE;
		String strISLMRFA;

		Vector modelAvailVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");
		addDebug("availmodel="+ availEI);

		for (int i = 0; i < modelAvailVect.size(); i++) {
			sb = new StringBuffer();
			strIOPUCTY = "";
			strQSMEDMW = "";
			strDUSALRW = "";
			strDSLMWDN = "";
			strDSLMOPD = "";
			strCPDAA = "";
			strWTHDRWEFFCTVDATE = "";
			strISLMRFA= "";

			EntityItem eiModel = (EntityItem) modelAvailVect.elementAt(i);

			EntityItem eiSearchAvail = null;

			sb.append(getValue(IFTYPE, "M=(CHK&UPG)T001"));
			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			}
			sb.append(getValue(IOPUCTY, strIOPUCTY));
			String description = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
			description += PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
			sb.append(getValue(ISLMPRN, description));

			strWTHDRWEFFCTVDATE = PokUtils.getAttributeValue(eiModel, "WTHDRWEFFCTVDATE", "", "");

			if (strWTHDRWEFFCTVDATE != null) {
				if (strWTHDRWEFFCTVDATE.equals("")) {
					strDSLMWDN = "2050-12-31";
				} else {
					strDSLMWDN = strWTHDRWEFFCTVDATE;
				}
			} else {
				strDSLMWDN = "2050-12-31";
			}

			eiSearchAvail = searchForAvailType(eiModel, "Last Order");
			if (eiSearchAvail != null) {
				strDSLMOPD = PokUtils.getAttributeValue(eiSearchAvail, "EFFECTIVEDATE", "", "");
				strCPDAA = "O";
			} else {
				strDSLMOPD = "2050-12-31";
				strCPDAA = "N";
			}

			eiSearchAvail = searchForAvailType(eiModel, "End of Service");
			if (eiSearchAvail != null) {
				strQSMEDMW = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
				strDUSALRW = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
			} else {
				strQSMEDMW = "2050-12-31";
				strDUSALRW = "2050-12-31";
			}

			sb.append(getValue(DSLMWDN, strDSLMWDN));
			sb.append(getValue(QSMEDMW, strQSMEDMW));
			sb.append(getValue(DSLMOPD, strDSLMOPD));
			sb.append(getValue(DUSALRW, strDUSALRW));
			sb.append(getValue(CPDAA, strCPDAA));
			sb.append(getValue(BLANK, " "));
			sb.append(getValue(ISLMRFA, strISLMRFA));
			
			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();
		}
	}

	private EntityItem searchForAvailType(EntityItem eiModel, String strSearchAvailType) {

		EntityItem eiReturn = null;
		String strAvailType;

		Vector availVect = PokUtils.getAllLinkedEntities(eiModel, "MODELAVAIL", "AVAIL");

		addDebug("*****mlm searchforavail withdrawalAVAIL " + availVect);

		for (int i = 0; i < availVect.size(); i++) {
			EntityItem eiAvail = (EntityItem) availVect.elementAt(i);

			strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", ",", "", false);
			addDebug("*****mlm searchforavail withdrawal model = " + eiModel.getEntityType() + eiModel.getEntityID()
					+ "avail entity type = " + eiAvail.getEntityType() + " avail type = " + strAvailType);
			if (strSearchAvailType.equals(strAvailType)) {
				eiReturn = eiAvail;
				break;
			}
		}

		return eiReturn;
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> OOFAVAIL -> PRODSTRUCT ->
	 * MODEL
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006Feature(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute qsmGeoList;
		String strAvailType;
		String strAvailAnnType = "";
		boolean isEpic = false;

		m_elist = getEntityList(getT006ProdstructVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		int availg = availGrp.getEntityItemCount();

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			qsmGeoList = null;
			strAvailType = "";

			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			// ******* NEW *******
			if ((strAvailType.equals("Planned Availability")) || (strAvailType.equals("End of Service"))
					|| (strAvailType.equals("Last Order"))) {
				qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");
				if (qsmGeoList != null) {
					if (qsmGeoList.isSelected("6199")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Asia Pacific", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6200")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Canada and Caribbean North", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6198")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Europe/Middle East/Africa", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6204")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Latin America", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6221")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "US Only", strAvailType, isEpic);
					}
				}
			}
		}
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> OOFAVAIL -> PRODSTRUCT ->
	 * MODEL
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006FeatureRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem availEI,
			String strAvailGenAreaSel, String strMainAvailType, boolean isEpic)
			throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strIOPUCTY;
		String strDSLMWDN;
		String strISLMRFA;
		
		Vector prodstructVect = PokUtils.getAllLinkedEntities(availEI, "OOFAVAIL", "PRODSTRUCT");

		for (int i = 0; i < prodstructVect.size(); i++) {
			sb = new StringBuffer();
			EntityItem eiProdstruct = (EntityItem) prodstructVect.elementAt(i);

			ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getT006FeatureVEName());

			EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] {
					new EntityItem(null, m_prof, eiProdstruct.getEntityType(), eiProdstruct.getEntityID()) });

			addDebug("EntityList for " + m_prof.getValOn() + " extract QSMFULL2 contains the following entities: \n"
					+ PokUtils.outputList(list));

			EntityGroup featureGrp = list.getEntityGroup("FEATURE");
			EntityGroup modelGrp = list.getEntityGroup("MODEL");
			EntityItem eiFeature = featureGrp.getEntityItem(0);
			EntityItem eiModel = modelGrp.getEntityItem(0);

			sb = new StringBuffer();
			strIOPUCTY = "";
			strDSLMWDN = "";
			strISLMRFA = "";

			sb.append(getValue(IFTYPE, "F=(CHKT631&UPGT005)"));

			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			}
			sb.append(getValue(IOPUCTY, strIOPUCTY));
			String strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", ",", "", false);
			strISLMPRN += PokUtils.getAttributeValue(eiFeature, "FEATURECODE", ",", "", false);
			sb.append(getValue(ISLMPRN, strISLMPRN));
			sb.append(getValue(BLANK2,""));
			sb.append(getValue(ISLMRFA, strISLMRFA));
//			strDSLMWDN = validateProdstructs(eiFeature);
//			sb.append(getValue(DSLMWDN, strDSLMWDN));
			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();
		}
	}

	/******
	 * Create T020 NP Mes Upgrade records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT020NPMesUpgrade(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute qsmGeoList;

		m_elist = getEntityList(getNPMesUpgradeVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int iAvail = 0; iAvail < availGrp.getEntityItemCount(); iAvail++) {
			qsmGeoList = null;

			EntityItem eiAvail = (EntityItem) availGrp.getEntityItem(iAvail);

			strAvailAnnType = PokUtils.getAttributeValue(eiAvail, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			qsmGeoList = (EANFlagAttribute) eiAvail.getAttribute("QSMGEO");
			if (qsmGeoList != null) {
				if (qsmGeoList.isSelected("6199")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Asia Pacific", isEpic);
				}
				if (qsmGeoList.isSelected("6200")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Canada and Caribbean North", isEpic);
				}
				if (qsmGeoList.isSelected("6198")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Europe/Middle East/Africa", isEpic);
				}
				if (qsmGeoList.isSelected("6204")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Latin America", isEpic);
				}
				if (qsmGeoList.isSelected("6221")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "US Only", isEpic);
				}
			}
		}
	}

	/******
	 * Create T020 NP Mes Upgrade records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT020NPMesUpgradeRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem eiAvail,
			String strAvailGenAreaSel, boolean isEpic) throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strISLMPRN;
		String strIOPUCTY;
		String strISLMRFA;

		sb = new StringBuffer();
		strISLMPRN = "";
		strIOPUCTY = "";
		strISLMRFA = "";

		Vector modelConvertVect = PokUtils.getAllLinkedEntities(eiAvail, "MODELCONVERTAVAIL", "MODELCONVERT");

		for (int indM = 0; indM < modelConvertVect.size(); indM++) {

			EntityItem eiModelConvert = (EntityItem) modelConvertVect.elementAt(indM);

			sb.append(getValue(IFTYPE, "N=(CHK&UPG)T019"));

			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			}
			sb.append(getValue(IOPUCTY, strIOPUCTY));

			strISLMPRN = PokUtils.getAttributeValue(eiModelConvert, "TOMACHTYPE", "", "");
			strISLMPRN += PokUtils.getAttributeValue(eiModelConvert, "TOMODEL", "", "");
			sb.append(getValue(ISLMPRN, strISLMPRN));

			EntityItem searchAvail = searchForAvailTypeLO(eiModelConvert, "Last Order");

			if (searchAvail != null) {
				sb.append(getValue(DSLMWDN, PokUtils.getAttributeValue(searchAvail, "EFFECTIVEDATE", "", "")));
			} else {
				sb.append(getValue(DSLMWDN, "2050-12-31"));
			}
			    sb.append(getValue(BLANK1,""));
			    sb.append(getValue(ISLMRFA, strISLMRFA));
			    

			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();

		}
	}

	private EntityItem searchForAvailTypeLO(EntityItem eiModelConvert, String strSearchAvailType) {

		EntityItem eiReturn = null;
		String strAvailType;

		Vector availVect = PokUtils.getAllLinkedEntities(eiModelConvert, "MODELCONVERTAVAIL", "AVAIL");

		for (int i = 0; i < availVect.size(); i++) {
			EntityItem eiAvail = (EntityItem) availVect.elementAt(i);

			strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", ",", "", false);

			if (strSearchAvailType.equals(strAvailType)) {
				eiReturn = eiAvail;
				break;
			}
		}

		return eiReturn;
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
		String strAvailType;
		String strISLMRFA="";
		String strAvailAnnType="";
		boolean isEpic = false;

		m_elist = getEntityList(getT006ProdstructVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			EntityItem availEI = availGrp.getEntityItem(availI);

			// Create T632 records only for US ONLY and WorldWide GEOs
			EANFlagAttribute qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");
			if (qsmGeoList != null) {
				if (qsmGeoList.isSelected("6221")) {

					Vector prodstructVect = PokUtils.getAllLinkedEntities(availEI, "OOFAVAIL", "PRODSTRUCT");

					strAvailType = "";
					strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
					strAvailAnnType = PokUtils.getAttributeValue(availEI,
					 "AVAILANNTYPE", "", "");
					if (strAvailAnnType.equals("EPIC")){
					 isEpic = true;
					}

					for (int i = 0; i < prodstructVect.size(); i++) {
						sb = new StringBuffer();
						EntityItem eiProdstruct = (EntityItem) prodstructVect.elementAt(i);

						ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getT006FeatureVEName());

						EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] { new EntityItem(null,
								m_prof, eiProdstruct.getEntityType(), eiProdstruct.getEntityID()) });

						EntityGroup featureGrp = list.getEntityGroup("FEATURE");
						EntityGroup modelGrp = list.getEntityGroup("MODEL");
						EntityItem eiFeature = featureGrp.getEntityItem(0);
						EntityItem eiModel = modelGrp.getEntityItem(0);

						sb = new StringBuffer();
						sb.append(getValue(IFTYPE, "T=(CHK&UPG)T631"));
						sb.append(getValue(IOPUCTY, "897"));
						strISLMRFA = getRFANumber(rootEntity, isEpic, availEI);
						sb.append(getValue(ISLMTYP, PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "")));
						sb.append(getValue(ISLMMOD, PokUtils.getAttributeValue(eiModel, "MODELATR", "", "")));
						sb.append(getValue(ISLMFTR, PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "")));
						sb.append(getValue(BLANK, " "));
						if (strAvailType.equals("Last Order")) {
							sb.append(PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false));
						} else {
							sb.append(getValue(DSLMWDN, "2050-12-31"));
						}
						    sb.append(getValue(BLANK1,""));
						    sb.append(getValue(ISLMRFA, strISLMRFA));
						    
						sb.append(NEWLINE);
						wOut.write(sb.toString());
						wOut.flush();

					}
				}
			}
		}
	}
	
	private String getRFANumber(EntityItem rootEntity, boolean isEpic, EntityItem availEI) {
		String strISLMRFA;
		if(isEpic){
			strISLMRFA = PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "");
		}else{
			strISLMRFA = "R"+ PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
		}
		
		//strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "R"+"ANNNUMBER", "", "");
		return strISLMRFA;
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

		addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName + " contains the following entities: \n"
				+ PokUtils.outputList(list));
		return list;
	}

	/**********************************
	 * get the name of the VE to use for MODEL records
	 * 
	 * @return java.lang.String
	 */
	public String getT002ModelVEName() {
		return "QSMFULL";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getT006FeatureVEName() {
		return "QSMFULL2";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getT006ProdstructVEName() {
		return "QSMFULL1";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getNPMesUpgradeVEName() {
		return "QSMFULL4";
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
		addDebug("file="+ filePrefix );
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
		// addDebug("cmd:" + cmd);
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
		return "QSMWITHDRAWALFULLABR";
	}

}
