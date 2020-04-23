package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.DateUtility;
import com.ibm.rdh.chw.entity.*;
import com.ibm.rdh.rfc.proxy.RdhRestProxy;
import com.ibm.rdh.rfc.proxy.RetriableRfcProxy;
import com.ibm.rdh.rfc.proxy.RfcProxy;
import com.ibm.transform.oim.eacm.util.PokUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//$Log: RfcAbrAdapter.java,v $
//Revision 1.58  2019/09/19 07:48:20  dlwlan
//Defect 2025373: Fix null pointer exception for Story 1969055
//
//Revision 1.57  2019/07/09 12:09:41  dlwlan
//Story 1985938? Add withdraw process for caller 102 117.
//
//Revision 1.56  2019/04/04 06:55:08  dlwlan
//Story 1969055: RFCABR Failed- RFCABR tries to create YA and Z0 records with same DATBI value- Development
//
//Revision 1.55  2018/06/21 01:42:30  dlwlan
//Finished changes regards Unique RFA number
//
//Revision 1.54  2018/03/12 08:36:13  wangyul
//Story 1821322 if data issue for eCOD test, duplicate Z0(ANNOUNCE) issue
//
//Revision 1.53  2018/03/06 07:39:21  wangyul
//Story 1816333 RFCABR report TYPERANGE value is too long than our column length
//
//Revision 1.52  2018/01/17 13:05:48  wangyul
//Story 1796482: Fix MM_BTPRODUCTS field for RFCABR and caller of Z_DM_SAP_CLASSIFICATION_MAINT
//
//Revision 1.51  2017/08/04 09:41:38  wangyul
//Defect 1735504	 - RPQ TMF RFCABR support old MODEL without plan avail
//
//Revision 1.50  2017/08/01 13:35:16  wangyul
//Defect1734655 - Defect MODEL RFCABR created duplicate components of sales BOM in RDH
//
//Revision 1.49  2017/08/01 12:48:45  wangyul
//Defect 1733387 - MODEL RFCABR can not process error Material exists in Mast table but not defined to Stpo table
//
//Revision 1.48  2017/06/01 12:31:14  wangyul
//Defect 1704595 EACM Prod: unable to resend model data
//
//Revision 1.47  2017/05/18 12:23:41  wangyul
//1701370: TMF and SWPRODSTRUCT RFCABR performance issue
//
//Revision 1.46  2017/04/18 07:49:58  wangyul
//[Work Item 1681790] New: ESW - unique CLASS (range) and featurenaming support needed for CHW EACM HIPO materials
//
//Revision 1.45  2017/03/16 12:39:04  wangyul
//1642763: Tax support for US when product is set for ZA(south Africa)
//
//Revision 1.44  2017/03/09 09:26:44  wangyul
// [Work Item 1658567] RFCABR support HIPO
//
//Revision 1.43  2017/02/14 09:31:37  wangyul
//Story1635034: OIMS CHW Simplifications - Amortization Start and Length can be blank
//
//Revision 1.42  2017/01/23 06:37:15  wangyul
//[Work Item 1642728] CHW Simplification - Need to support Non RFA Avail while deriving the announcement dates
//
//Revision 1.41  2017/01/16 06:58:15  wangyul
//Defect 1634558 - a problem in RFCABR on how to check on return back from BOM read RFC to determine if a BOM exists or not
//
//Revision 1.40  2017/01/05 14:08:04  wangyul
//Story1635023 - OIMS CHW Simplification - RESEND function for RFC ABRs- MODEL,MODELCONVERT,TMFs
//
//Revision 1.39  2016/12/28 12:51:14  wangyul
//[Work Item 1612648] Update to Z_DM_SAP_BOM_MAINTAIN RFC to prevent BOM update issue for CHW and ESW products
//
//Revision 1.38  2016/11/09 13:34:38  wangyul
//[Work Item 1617881] New: Configurable option to move parking table records to R status or leave them in H status
//
//Revision 1.37  2016/11/03 07:28:48  wangyul
//IN8581284 ==> Inconsistent RPQ approval setting for 8S1509 machine 2078/124 between CBS and AAS
//
//Revision 1.36  2016/09/24 03:31:59  guobin
//fix defect 	Defect 1598194: UAT - 2964 models CHRMAS IDOCs failing to be built due to duplicate CABN rows	Help
//
//Revision 1.35  2016/09/14 12:21:51  guobin
//Add file  access lock on r130,r131 and r176 Defect 1590848	Multiple rows were created in KLAH table for the same pair of class and klart.
//
//Revision 1.34  2016/08/25 08:06:46  wangyul
//Defect 1580618 - IDOCs in parking table left in H status for 8828 run
//
//Revision 1.33  2016/08/17 07:06:11  wangyul
//Story 1577318 - OIM CHW Simlifications-  Last Order effective date
//
//Revision 1.32  2016/08/15 07:45:53  wangyul
//Defect 1568131 - MLAN table not populated like production MMLC
//
//Revision 1.31  2016/08/10 08:18:01  wangyul
//Story1556953 - OIM CHW Simplifications : AUO Material and REV profile process
//
//Revision 1.30  2016/08/09 12:37:02  wangyul
//Replace INVNAME to TypeModel description value
//
//Revision 1.29  2016/08/04 07:35:32  wangyul
//replace chinese ? to :
//
//Revision 1.28  2016/08/01 09:03:27  wangyul
//Story1557274 - Request the number of times to retry a RFC when in error - Get property value
//
//Revision 1.27  2016/07/28 12:13:35  wangyul
//Defect1567303 - RFC Caller R101 and R116 derive value from LADGR from LODGINGGROUP filed
//
//Revision 1.26  2016/07/27 15:49:11  wangyul
//update the method of isRevProfOrAuoMtrlChanged
//
//Revision 1.25  2016/07/27 15:23:24  wangyul
//Story 1463325 - * OIM CHW Simplification new ABR for update revenue profile BOM, update Ann LifeCycle, Pland sale fix isRevProfOrAuoMtrlChanged
//
//Revision 1.24  2016/07/26 09:23:09  wangyul
//Story1557274 - Request the number of times to retry a RFC when in error
//
//Revision 1.23  2016/07/21 15:44:04  wangyul
//Story1540097 - Feature Range calculations for over 999 RPQs and other types
//
//Revision 1.22  2016/07/15 08:18:31  wangyul
//Story 1463325 - * OIM CHW Simplification new ABR for update revenue profile BOM, update Ann LifeCycle, Pland sale
//
//Revision 1.21  2016/07/14 09:16:04  wangyul
//change the method of isRevProfOrAuoMtrlChanged
//
//Revision 1.20  2016/07/14 08:17:24  wangyul
//Story 1463325 - * OIM CHW Simplification new ABR for update revenue profile BOM, update Ann LifeCycle, Pland sale
//
//Revision 1.19  2016/07/13 08:35:37  wangyul
//add the variable for the RFCFCTRANSACTIONABR
//
//Revision 1.18  2016/07/11 07:32:11  wangyul
//Story1545764 - OIM Simplification- WDFM Announcement  promote- Code promote
//
//Revision 1.17  2016/07/08 08:34:54  wangyul
//change the log for the method of getCompentsintypeNEW
//
//Revision 1.16  2016/07/06 14:15:08  wangyul
//update the method of getRevProfile
//
//Revision 1.15  2016/07/04 08:56:22  wangyul
//1. Promote WDFM Announcement for MODELCONVERT Withdraw From Market
//2. Use all salesOrg instead of 0147
//
//Revision 1.14  2016/06/28 08:29:19  wangyul
//update the code for get the profile
//

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class RfcAbrAdapter implements RfcAbr {

	// SPLIT
	protected static final String SPLIT = "-";
	// Entity
	protected static final String MODEL = "MODEL";
	protected static final String FEATURE = "FEATURE";
	protected static final String SWFEATURE = "SWFEATURE";
	protected static final String TMF = "PRODSTRUCT";
	protected static final String FCTRANSACTION = "FCTRANSACTION";
	protected static final String MODELCONVERT = "MODELCONVERT";
	protected static final String AVAIL = "AVAIL";
	protected static final String ANNOUNCEMENT = "ANNOUNCEMENT";
	protected static final String MACHTYPE = "MACHTYPE";
	protected static final String GENERALAREA = "GENERALAREA";
	protected static final String GBT = "GBT";
	protected static final String TAXCATG = "TAXCATG";
	protected static final String MODTAXRELEVANCE = "MODTAXRELEVANCE";
	protected static final String SGMNTACRNYM = "SGMNTACRNYM";
	protected static final String REVPROF = "REVPROF";
	protected static final String AUOMTRL = "AUOMTRL";
	protected static final String REVPROFAUOMTRL = "REVPROFAUOMTRL";

	// Entity attribute
	protected static final String MACHTYPE_PROMOTED = "PROMOTED";
	protected static final String MACHTYPE_MACHTYPEATR = "MACHTYPEATR";
	protected static final String MACHTYPE_RPQRANGE = "RPQRANGE";
	protected static final String MACHTYPE_RANGENAME = "RANGENAME";
	protected static final String MACHTYPE_FEATURECOUNT = "FEATURECOUNT";
	protected static final String MODEL_MACHTYPEATR = "MACHTYPEATR";
	protected static final String MODEL_COFCAT = "COFCAT";
	protected static final String MODEL_MODELATR = "MODELATR";
	protected static final String MODEL_MKTGNAME = "MKTGNAME";
	protected static final String MODEL_INVNAME = "INVNAME";
	protected static final String MODEL_ACCTASGNGRP = "ACCTASGNGRP";
	protected static final String MODEL_PRFTCTR = "PRFTCTR";
	protected static final String MODEL_INSTALL = "INSTALL";
	protected static final String MODEL_LICNSINTERCD = "LICNSINTERCD";
	protected static final String MODEL_SYSTEMTYPE = "SYSTEMTYPE";
	protected static final String MODEL_SYSIDUNIT = "SYSIDUNIT";
	protected static final String MODEL_MODELORDERCODE = "MODELORDERCODE";
	protected static final String MODEL_ANNDATE = "ANNDATE";
	protected static final String FEATURE_FEATURECODE = "FEATURECODE";
	protected static final String FEATURE_INVNAME = "INVNAME";
	protected static final String FEATURE_HWFCCAT = "HWFCCAT";
	protected static final String FEATURE_PRICEDFEATURE = "PRICEDFEATURE";
	protected static final String FEATURE_ZEROPRICE = "ZEROPRICE";
	protected static final String FEATURE_FCTYPE = "FCTYPE";
	protected static final String FIRSTANNDATE = "FIRSTANNDATE";
	protected static final String FEATURE_TYPERANGE = "TYPERANGE";
	protected static final String TMF_INSTALL = "INSTALL";
	protected static final String TMF_REMOVEPRICE = "REMOVEPRICE";
	protected static final String TMF_RETURNEDPARTS = "RETURNEDPARTS";
	protected static final String TMF_ANNDATE = "ANNDATE";
	protected static final String TMF_WITHDRAWDATE = "WITHDRAWDATE";
	protected static final String MODELCONVERT_FROMMACHTYPE = "FROMMACHTYPE";
	protected static final String MODELCONVERT_FROMMODEL = "FROMMODEL";
	protected static final String MODELCONVERT_TOMACHTYPE = "TOMACHTYPE";
	protected static final String MODELCONVERT_TOMODEL = "TOMODEL";
	protected static final String AVAIL_COUNTRYLIST = "COUNTRYLIST";
	protected static final String AVAIL_AVAILTYPE = "AVAILTYPE";
	protected static final String AVAIL_EFFECTIVEDATE = "EFFECTIVEDATE";

	protected static final String FCTRANSACTION_WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";

	protected static final String ANNOUNCEMENT_ANNTYPE = "ANNTYPE";
	protected static final String ANNDATE = "ANNDATE";
	protected static final String PDHDOMAIN = "PDHDOMAIN";
	protected static final String TYPERANGEL = "TYPERANGEL";

	protected static final String GENERALAREA_SLEORG = "SLEORG";
	protected static final String GENERALAREA_GENAREACODE = "GENAREACODE";
	protected static final String GENERALAREA_GENAREANAME = "GENAREANAME";
	protected static final String GENERALAREA_GENAREAPARENT = "GENAREAPARENT";
	protected static final String GENERALAREA_CBSLEGACYPLNTCD = "CBSLEGACYPLNTCD";
	protected static final String GENERALAREA_CBSRETURNPNTCD = "CBSRETURNPNTCD";

	protected static final String GBT_SAPPRIMBRANDCD = "SAPPRIMBRANDCD";
	protected static final String GBT_SAPPRODFMLYCD = "SAPPRODFMLYCD";

	protected static final String TAXCATG_COUNTRYLIST = "COUNTRYLIST";
	protected static final String TAXCATG_TAXCATGATR = "TAXCATGATR";
	protected static final String MODTAXRELEVANCE_TAXCLS = "TAXCLS";

	protected static final String SGMNTACRNYM_DIV = "DIV";
	// Attribute
	protected static final String ATTR_SYSFEEDRESEND = "SYSFEEDRESEND";
	protected static final String ATTR_STATUS = "STATUS";
	protected static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";

	// Attribute value
	protected static final String ANNTYPE_lONG_NEW = "New";
	protected static final String MACHTYPE_PROMOTED_YES = "PRYES";
	protected static final String MACHTYPE_RPQRANGE_YES = "Yes";
	protected static final String PLANNEDAVAIL = "146";
	protected static final String LASTORDERAVAIL = "149";
	protected static final String EOMAVAIL = "200";
	protected static final String STATUS_PASSED = "0030";
	protected static final String HARDWARE = "100";
	protected static final String SYSFEEDRESEND_YES = "Yes";
	protected static final String SYSFEEDRESEND_NO = "No";
	protected static final String STATUS_FINAL = "0020";
	protected static final String GENAREANAME_US = "1652";
	protected static final String GENAREANAME_ZA = "1624"; // South Africa
	protected static final String NETPRICEMES_YES = "NETPRICEMESYES";
	// Attribute type
	protected static final String ATTR_FLAG = "F";
	protected static final String ATTR_TEXT = "T";
	protected static final String ATTR_MULTI_FLAG = "MF";
	protected static final String ATTR_MULTI_TEXT = "MT";

	public static final String LOCAL_REAL_PATH = "./properties/dev";

	public static final String STRING_SEPARATOR = "-";

	public static final String ROLE_CODE = "BHFEED";

	protected static final String RFCABRSTATUS = "RFCABRSTATUS";

	public String uniqueID = null;
	protected String m_strEpoch = "1980-01-01-00.00.00.000000";

	public static String PREANNOUNCE = "YA";
	public static String ANNOUNCE = "Z0";
	public static String WDFM = "ZJ";
	/*
	 * Check required attribute for entities when call getAttribute() method
	 */
	private static final String PRIMARY_FC = "100";
	private static final String SECONDARY_FC = "110";
	protected static final String FCTYPE_RPQ_ILISTED = "120";
	protected static final Set FCTYPE_SET;
	static {
		FCTYPE_SET = new HashSet();
		FCTYPE_SET.add(PRIMARY_FC);
		FCTYPE_SET.add(SECONDARY_FC);
	}

	private static Hashtable<String, List<String>> requiredTypeAttrsTbl = new Hashtable<String, List<String>>();
	static {
		requiredTypeAttrsTbl.put(MODEL,
				new ArrayList<String>(Arrays.asList("MACHTYPEATR", "MKTGNAME", "BHPRODHIERCD", "INVNAME", ANNDATE)));
		requiredTypeAttrsTbl.put(MODELCONVERT, new ArrayList<String>(Arrays.asList(ANNDATE)));
		requiredTypeAttrsTbl.put(ANNOUNCEMENT, new ArrayList<String>(Arrays.asList("ANNNUMBER", "ANNTYPE", ANNDATE)));
		requiredTypeAttrsTbl.put(AUOMTRL, new ArrayList<String>(Arrays.asList("MATERIAL", "AUODESC")));
		requiredTypeAttrsTbl.put(REVPROFAUOMTRL, new ArrayList<String>(Arrays.asList("PERCENTAGE")));
	}

	protected RfcProxy rdhRestProxy;
	protected RFCABRSTATUS abr;
	protected EntityList entityList;
	protected ConfigManager configManager;

	public RfcAbrAdapter(RFCABRSTATUS rfcAbrStatus)
			throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException,
			EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		// Add all config files first, then all the funciton can use it to get the
		// property.
		configManager = ConfigManager.getConfigManager();
		configManager.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, LOCAL_REAL_PATH);
		configManager.addAllConfigFiles();

		abr = rfcAbrStatus;
		BasicRfcLogger rfcLogger = new BasicRfcLogger(rfcAbrStatus);
		rdhRestProxy = new RetriableRfcProxy(new RdhRestProxy(rfcLogger), rfcLogger).getRfcProxy();

		String t2DTS = abr.getCurrentTime();
		Profile profileT2 = abr.switchRole(ROLE_CODE);
		profileT2.setValOnEffOn(t2DTS, t2DTS);
		profileT2.setEndOfDay(t2DTS);
		profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
		profileT2.setLoginTime(t2DTS);
		entityList = getEntityList(abr.getDatabase(), profileT2, getVeName(), abr.getEntityType(), abr.getEntityID());
		abr.addDebug("EntityList for " + abr.getProfile().getValOn() + " extract " + getVeName()
				+ " contains the following entities: \n" + PokUtils.outputList(entityList));
	}

	public String getVeName() {
		return "dummy";
	}

	protected EntityList getEntityList(Database m_db, Profile prof, String veName, String entityType, int entityId)
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, veName),
				new EntityItem[] { new EntityItem(null, prof, entityType, entityId) });
		return list;
	}

	protected EntityList getEntityList(Profile prof)
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		return getEntityList(abr.getDatabase(), prof, getVeName(), abr.getEntityType(), abr.getEntityID());
	}

	protected EntityItem getRooEntityItem() {
		return entityList.getParentEntityGroup().getEntityItem(0);
	}

	protected EntityItem[] getEntityItems(String entityType) {
		EntityItem[] entityItems = null;
		EntityGroup entityGroup = entityList.getEntityGroup(entityType);
		if (entityGroup != null) {
			entityItems = entityGroup.getEntityItemsAsArray();
		}
		return entityItems;
	}

	protected EntityItem[] getEntityItems(EntityList entityList, String entityType) {
		EntityItem[] entityItems = null;
		EntityGroup entityGroup = entityList.getEntityGroup(entityType);
		if (entityGroup != null) {
			entityItems = entityGroup.getEntityItemsAsArray();
		}
		return entityItems;
	}

	protected List<EntityItem> getLinkedRelator(EntityItem entityItem, String linkType) {
		List<EntityItem> items = new ArrayList<>();
		for (int i = 0; i < entityItem.getUpLinkCount(); i++) {
			EANEntity entityLink = entityItem.getUpLink(i);
			if (entityLink.getEntityType().equals(linkType)) {
				items.add((EntityItem) entityLink);
			}
		}
		for (int i = 0; i < entityItem.getDownLinkCount(); i++) {
			EANEntity entityLink = entityItem.getDownLink(i);
			if (entityLink.getEntityType().equals(linkType)) {
				items.add((EntityItem) entityLink);
			}
		}
		return items;
	}

	/**
	 * Get the current Flag code Value for the specified attribute, null if not set
	 *
	 * @param item
	 *            EntityItem
	 * @param attrCode
	 *            String attribute code to get value for
	 * @return String attribute flag code
	 * @throws RfcAbrException
	 */
	protected String getAttributeFlagValue(EntityItem item, String attrCode) throws RfcAbrException {
		String attrValue = PokUtils.getAttributeFlagValue(item, attrCode);
		List<String> requiredAttrs = requiredTypeAttrsTbl.get(item.getEntityType());
		if (requiredAttrs != null && requiredAttrs.contains(attrCode)) {
			if (attrValue == null || "".equals(attrValue)) {
				throw new RfcAbrException("For entity:" + item.getKey() + ", " + attrCode + " value can not be empty");
			}
		}
		if (attrValue == null) {
			attrValue = "";
		}
		return attrValue;
	}

	/**
	 * Get attribute short value
	 * 
	 * @param item
	 * @param attrCode
	 * @return
	 */
	protected String getAttributeShortValue(EntityItem item, String attrCode) {
		String attrValue = "";
		EntityGroup egrp = item.getEntityGroup();
		EANMetaAttribute metaAttr = egrp.getMetaAttribute(attrCode);
		if (metaAttr == null) {
			attrValue = "Error: Attribute " + attrCode + " not found in " + item.getEntityType() + " META data.";
		} else {
			if (metaAttr.getAttributeType().equals("U") || metaAttr.getAttributeType().equals("S")) {
				EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(attrCode);
				if (fAtt != null && fAtt.toString().length() > 0) {
					// Get the selected Flag code
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i = 0; i < mfArray.length; i++) {
						// get selection
						if (mfArray[i].isSelected()) {
							attrValue = mfArray[i].getShortDescription();
							break;
						} // metaflag is selected
					} // end of flagcodes
				}
			} else if (metaAttr.getAttributeType().equals("F")) { // MultiFlagAttribute
				// get attr, it is F
				EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(attrCode);
				if (fAtt != null && fAtt.toString().length() > 0) {
					StringBuffer sb = new StringBuffer();
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i = 0; i < mfArray.length; i++) {
						// get selection
						if (mfArray[i].isSelected()) {
							if (sb.length() > 0) {
								sb.append(PokUtils.DELIMITER);
							}
							sb.append(mfArray[i].getShortDescription());
						}
					}
					if (sb.length() > 0) {
						attrValue = sb.toString();
					}
				}
			}
		}
		return attrValue;
	}

	protected String getAttributeValue(EntityItem item, String attrCode) throws RfcAbrException {
		String attrValue = PokUtils.getAttributeValue(item, attrCode, PokUtils.DELIMITER, "", false);
		List<String> requiredAttrs = requiredTypeAttrsTbl.get(item.getEntityType());
		if (requiredAttrs != null && requiredAttrs.contains(attrCode)) {
			if (attrValue == null || "".equals(attrValue)) {
				throw new RfcAbrException("For entity:" + item.getKey() + ", " + attrCode + " value can not be empty");
			}
		}
		return attrValue;
	}

	protected Vector getAttributeMultiValue(EntityItem item, String attrCode) throws RfcAbrException {
		Vector attrValues = new Vector();
		String attrValue = getAttributeValue(item, attrCode);
		if (attrValue != null) {
			StringTokenizer st = new StringTokenizer(attrValue, PokUtils.DELIMITER);
			while (st.hasMoreTokens()) {
				attrValues.add(st.nextToken());
			}
		}
		return attrValues;
	}

	protected Vector getAttributeMultiFlagValue(EntityItem item, String attrCode) throws RfcAbrException {
		Vector attrValues = new Vector();
		String attrValue = getAttributeFlagValue(item, attrCode);
		if (attrValue != null) {
			StringTokenizer st = new StringTokenizer(attrValue, PokUtils.DELIMITER);
			while (st.hasMoreTokens()) {
				attrValues.add(st.nextToken());
			}
		}
		return attrValues;
	}

	protected void setFlagValue(String strAttributeCode, String strAttributeValue, EntityItem item)
			throws SQLException, MiddlewareException {
		abr.setFlagValue(strAttributeCode, strAttributeValue, item);
	}

	protected void setTextValue(String strAttributeCode, String strAttributeValue, EntityItem item)
			throws SQLException, MiddlewareException {
		abr.setTextValue(strAttributeCode, strAttributeValue, item);
	}

	protected void setLongTextValue(String strAttributeCode, String strAttributeValue, EntityItem item)
			throws SQLException, MiddlewareException {
		abr.setLongTextValue(strAttributeCode, strAttributeValue, item);
	}

	protected AttributeChangeHistoryGroup getAttributeHistory(EntityItem item, String attrCode)
			throws MiddlewareRequestException {
		EANAttribute att = item.getAttribute(attrCode);
		if (att != null) {
			return new AttributeChangeHistoryGroup(abr.getDatabase(), abr.getProfile(), att);
		} else {
			abr.addDebug(attrCode + " of " + item.getKey() + "  was null");
			return null;
		}
	}

	protected boolean isDiff(EntityItem t1Item, EntityItem t2Item, List<String> attrList) throws RfcAbrException {
		for (String attr : attrList) {
			String value1 = getAttributeValue(t1Item, attr);
			String value2 = getAttributeValue(t2Item, attr);
			if (!value1.equals(value2)) {
				abr.addDebug("isDiff " + t1Item.getKey() + " Attribute " + attr + " value " + value1
						+ " is different with " + t2Item.getKey() + " value " + value2);
				return true;
			}
		}
		return false;
	}

	/**
	 * checking whether has passed queue in RFCABRSTATUS
	 */
	protected boolean existBefore(AttributeChangeHistoryGroup achg, String value) {
		if (achg != null) {
			for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);
				if (achi.getFlagCode().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get latest valfrom value for attribute value
	 * 
	 * @param history
	 * @param attributeValue
	 * @return
	 * @throws RfcAbrException
	 */
	protected String getLatestValFromForAttributeValue(AttributeChangeHistoryGroup history, String attributeValue)
			throws RfcAbrException {
		String DTS = null;
		String temp = "";
		if (history != null && history.getChangeHistoryItemCount() >= 1) {
			int iHistoryItemCount = history.getChangeHistoryItemCount();
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) history.getChangeHistoryItem(i);
				if (achi != null) {
					if (attributeValue.equals(achi.getFlagCode())) {
						temp = achi.getChangeDate();
						if (DTS == null) {
							DTS = temp;
						} else {
							if (temp.compareTo(DTS) > 0) {
								DTS = temp;
							}
						}
					}
				}
			}
			return DTS;
		} else {
			throw new RfcAbrException("Error: A request is not valid since there is no history");
		}
	}

	protected boolean isXccOnlyDiv(String div) {
		String xccOnlyDiv = configManager.getString(PropertyKeys.KEY_XCC_ONLY_DIV, true);
		StringTokenizer stoken = new StringTokenizer(xccOnlyDiv, ",");
		while (stoken.hasMoreElements()) {
			String xccDiv = stoken.nextToken();
			if ((div != null) && (div.equalsIgnoreCase(xccDiv))) {
				return true;
			}
		}
		return false;
	}

	protected String getOpwgId() {
		return Integer.toString(abr.getProfile().getOPWGID());
	}

	/**
	 * Get same entity item at t1
	 * 
	 * @param t1Items
	 * @param t2Item
	 * @return
	 */
	protected EntityItem getEntityItemAtT1(Vector t1Items, EntityItem t2Item) {
		for (int i = 0; i < t1Items.size(); i++) {
			EntityItem item = (EntityItem) t1Items.get(i);
			if (item.getKey().equals(t2Item.getKey())) {
				return item;
			}
		}
		return null;
	}

	@SuppressWarnings("resource")
	protected void updateAnnLifecyle(String varCond, String material, Date annDate, String annDocNo,
			String announcement, String pimsIdentity, String salesOrg) throws Exception {
		// Lock file
		FileChannel fileChannel = null;
		FileLock lock = null;

		try {
			File file = new File("./locks/UpdateAnnLifecyle" + varCond + ".lock");
			new File(file.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fileChannel = fos.getChannel();
			while (true) {
				FileLock fileLock = fileChannel.tryLock();
				if (fileLock != null) {
					abr.addDebug("updateAnnLifecyle get lock");

					// ------------------------------------- Start
					// -------------------------------------
					// Note from from an error restart standpoint, this function will be
					// treated as one transaction. Since it uses r200 that queries
					// SAP it will not run the same number of RFCs each time. Care must be
					// made to ensure no null pointer are encountered. And that
					// this routine will work if rerun if it fails at any point.

					// r200, r199, r198, and r197 have been updated to not increment the
					// trans counter

					// Increment the transaction counter once to represent this function.
					// hwpimsErrorInformation.incrTransactionCounter();

					LifecycleData lcd;

					Date today = new Date();

					if (!announcement.equals("MIG")) {
						// Checked with Rupal, Please use OPENID from the PDH that represents user id in
						// EACM. OPENID is entityid for OPWG. OPWG.NAME. I am not sure what is it used
						// for but we can use that.
						String user = getOpwgId();
						lcd = rdhRestProxy.r200(material, varCond, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addOutput("[R200] Read lifecycle row PreAnn for sales " + salesOrg);

						abr.addDebug("updateAnnLifecyle getPreAnnounceValidFrom:" + lcd.getPreAnnounceValidFrom()
								+ " getAnnounceValidFrom:" + lcd.getAnnounceValidFrom() + " getWdfmValidFrom:"
								+ lcd.getWdfmValidFrom() + " getAnnounceValidTo:" + lcd.getAnnounceValidTo());

						if (lcd.getPreAnnounceValidFrom() == null && lcd.getAnnounceValidFrom() == null
								&& lcd.getWdfmValidFrom() == null) {
							// New object
							if (DateUtility.isAfterToday(annDate)) {
								rdhRestProxy.r197(material, varCond, PREANNOUNCE, today,
										DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity,
										salesOrg);
								abr.addOutput(
										"[R197] Create lifecycle row for PreAnn status valid from today to AnnDate - 1");
								rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(),
										user, annDocNo, "ann", pimsIdentity, salesOrg);
								abr.addOutput(
										"[R197] Create lifecycle row for Announced status valid from today to 99991231");
							} else { // today or before
								rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(),
										user, annDocNo, "ann", pimsIdentity, salesOrg);
								abr.addOutput(
										"[R197] Create lifecycle row for Announced status valid from today to 99991231");
							}
						} else { // Already exists
							// Note that this does handle rare case that pre-ann is not null
							// but ann is (maybe if withdrawn on
							// same day and unwithdrawn or error in this function
							if (lcd.getPreAnnounceValidFrom() != null && (lcd.getAnnounceValidFrom() == null
									|| DateUtility.isSameDay(annDate, lcd.getAnnounceValidFrom()) == false)) {
								if (DateUtility.dateIsTodayOrBefore(annDate, lcd.getPreAnnounceValidFrom())) { // AnnDate
																												// <=
																												// PreAnn
																												// Valid
																												// From
									rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user,
											annDocNo, "ann", pimsIdentity, salesOrg);
									abr.addOutput("[R199] Delete lifecycle row for PreAnn status");
								} else {
									// Update PreAnn status valid to date to AnnDate
									rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user,
											annDocNo, "ann", pimsIdentity, salesOrg);
									abr.addOutput("[R199] Delete lifecycle row for PreAnn status");
									rdhRestProxy.r197(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidFrom(),
											DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity,
											salesOrg);
									abr.addOutput(
											"[R197] Create lifecycle row for PreAnn status valid from prior date to AnnDate - 1");
								}
							}
							abr.addDebug("updateAnnLifecyle annDate " + annDate + " annValudFrom"
									+ lcd.getAnnounceValidFrom()
									+ "DateUtility.isSameDay(lcd.getAnnounceValidFrom(), annDate)"
									+ DateUtility.isSameDay(lcd.getAnnounceValidFrom(), annDate));
							if (!DateUtility.isSameDay(lcd.getAnnounceValidFrom(), annDate)) {
								// Handle reintroduce if Ann and WDFM on same day and Ann
								// row is not there)
								Date annValidTo = null;
								if (lcd.getAnnounceValidTo() == null) {
									if (lcd.getWdfmValidFrom() != null) {
										annValidTo = DateUtility.getDateMinusOne(lcd.getWdfmValidFrom());
									} else {
										annValidTo = DateUtility.getInfinityDate();
									}
									
									if(lcd.getWdfmValidFrom() != null && !(lcd.getWdfmValidFrom()).equals(annDate)) {
										rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, annValidTo, user, annDocNo, "ann", pimsIdentity, salesOrg);
										abr.addOutput("[R197] Create lifecycle row for Announced status valid from date to AnnDate");
									}

								} else {
									rdhRestProxy.r198(material, varCond, ANNOUNCE, annDate, lcd.getAnnounceValidTo(),
											user, annDocNo, "ann", pimsIdentity, salesOrg);
									abr.addOutput(
											"[R198] Update lifecycle row for Announced status valid from date to AnnDate");
								}
								if (DateUtility.isAfterToday(annDate) && lcd.getPreAnnounceValidFrom() == null) {
									rdhRestProxy.r197(material, varCond, PREANNOUNCE, today,
											DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity,
											salesOrg);
									abr.addOutput(
											"[R197] Create lifecycle row for PreAnn status valid from today to AnnDate - 1");
								}
							}
						}
					}
					// ------------------------------------- END
					// -------------------------------------
					break;
				} else {
					abr.addDebug("updateAnnLifecyle fileLock == null");
					Thread.sleep(5000);
				}

			}
		} catch (OverlappingFileLockException e1) {
			abr.addDebug("updateAnnLifecyle other ABR is running updateAnnLifecyle");
			Thread.sleep(5000);
		} catch (FileNotFoundException e) {
			abr.addDebug("updateAnnLifecyle file not found!");
		} catch (IOException e) {
			abr.addDebug("updateAnnLifecyle IOException");
		} catch (InterruptedException e) {
			abr.addDebug("updateAnnLifecyle IOException");
		} finally {
			if (lock != null) {
				try {
					lock.release();
					lock = null;
				} catch (IOException e) {
					abr.addDebug("file lock release IOException");
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
					fileChannel = null;
				} catch (IOException e) {
					abr.addDebug("file channel release IOException");
				}
			}
		}
	}

	protected void callr130WithAccessAuthority(String type, String thisRange, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		callr130WithAccessAuthority(type, null, thisRange, chwA, pimsIdentity);
	}

	@SuppressWarnings("resource")
	protected void callr130WithAccessAuthority(String type, String model, String thisRange, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		FileChannel fileChannel = null;
		FileLock lock = null;
		try {
			File file = new File("./locks/R130" + type + thisRange + ".lock");
			new File(file.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fileChannel = fos.getChannel();
			while (true) {
				FileLock fileLock = fileChannel.tryLock();
				if (fileLock != null) {
					abr.addDebug("call SAP_Class_Maintain get lock");
					rdhRestProxy.r130(type, model, thisRange, chwA, pimsIdentity);
					break;
				} else {
					abr.addDebug("R130" + type + thisRange + " fileLock == null");
					Thread.sleep(5000);
				}
			}
		} catch (OverlappingFileLockException e1) {
			abr.addDebug("SAP_Class_Maintain other ABR is running callr130WithAccessAuthority");
			Thread.sleep(5000);
		} catch (FileNotFoundException e) {
			abr.addDebug("SAP_Class_Maintain lock file not found!");
		} catch (IOException e) {
			abr.addDebug("SAP_Class_Maintain IOException");
		} catch (InterruptedException e) {
			abr.addDebug("SAP_Class_Maintain InterruptedException");
		} finally {
			if (lock != null) {
				try {
					lock.release();
					lock = null;
				} catch (IOException e) {
					abr.addDebug("file lock release IOException");
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
					fileChannel = null;
				} catch (IOException e) {
					abr.addDebug("file channel release IOException");
				}
			}
		}
	}

	@SuppressWarnings("resource")
	protected void callr127WithAccessAuthority(TypeFeature tfObj, String thisRange, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		FileChannel fileChannel = null;
		FileLock lock = null;
		try {
			File file = new File("./locks/R127" + tfObj.getType() + thisRange + ".lock");
			new File(file.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fileChannel = fos.getChannel();
			while (true) {
				FileLock fileLock = fileChannel.tryLock();
				if (fileLock != null) {
					abr.addDebug("call SAP_Class_Maintain get lock");
					rdhRestProxy.r127(tfObj, thisRange, chwA, pimsIdentity);
					break;
				} else {
					abr.addDebug("R127" + tfObj.getType() + thisRange + " fileLock == null");
					Thread.sleep(5000);
				}
			}
		} catch (OverlappingFileLockException e1) {
			abr.addDebug("SAP_Class_Maintain other ABR is running callr127WithAccessAuthority");
			Thread.sleep(5000);
		} catch (FileNotFoundException e) {
			abr.addDebug("SAP_Class_Maintain lock file not found!");
		} catch (IOException e) {
			abr.addDebug("SAP_Class_Maintain lock IOException");
		} catch (InterruptedException e) {
			abr.addDebug("SAP_Class_Maintain InterruptedException");
		} finally {
			if (lock != null) {
				try {
					lock.release();
					lock = null;
				} catch (IOException e) {
					abr.addDebug("file lock release IOException");
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
					fileChannel = null;
				} catch (IOException e) {
					abr.addDebug("file channel release IOException");
				}
			}
		}
	}

	@SuppressWarnings("resource")
	protected void callr131WithAccessAuthority(String type, String thisRange, CHWAnnouncement chwA, String pimsIdentity)
			throws Exception {
		FileChannel fileChannel = null;
		FileLock lock = null;
		try {
			File file = new File("./locks/R131" + type + thisRange + ".lock");
			new File(file.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fileChannel = fos.getChannel();
			while (true) {
				FileLock fileLock = fileChannel.tryLock();
				if (fileLock != null) {
					abr.addDebug("Call SAP_Class_Maintain get lock");
					rdhRestProxy.r131(type, thisRange, chwA, pimsIdentity);
					break;
				} else {
					abr.addDebug("R131" + type + thisRange + " fileLock == null");
					Thread.sleep(5000);
				}
			}
		} catch (OverlappingFileLockException e1) {
			abr.addDebug("Call SAP_Class_Maintain other ABR is calling callr131WithAccessAuthority");
			Thread.sleep(5000);
		} catch (FileNotFoundException e) {
			abr.addDebug("Call SAP_Class_Maintain lock_file not found!");
		} catch (IOException e) {
			abr.addDebug("Call SAP_Class_Maintain lock IOException");
		} catch (InterruptedException e) {
			abr.addDebug("Call SAP_Class_Maintain lock InterruptedException");
		} finally {
			if (lock != null) {
				try {
					lock.release();
					lock = null;
				} catch (IOException e) {
					abr.addDebug("Call SAP_Class_Maintain file lock release IOException");
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
					fileChannel = null;
				} catch (IOException e) {
					abr.addDebug("Call SAP_Class_Maintain file channel release IOException");
				}
			}
		}
	}

	protected void updateWDFMLifecyle(LifecycleData lcd, String varCond, String material, Date wdfmDate,
			String annDocNo, String pimsIdentity, String salesOrg) throws Exception {
		if (wdfmDate == null && lcd.getWdfmValidFrom() != null) {
			// Delete WDFM Date
			rdhRestProxy.r199(material, varCond, WDFM, lcd.getWdfmValidTo(), getOpwgId(), annDocNo, "wdfm",
					pimsIdentity, salesOrg);
			abr.addOutput("[R199] Delete lifecycle row for WDFM");
			if (lcd.getAnnounceValidFrom() == null) {
				rdhRestProxy.r197(material, varCond, ANNOUNCE, lcd.getWdfmValidFrom(), DateUtility.getInfinityDate(),
						getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
				abr.addOutput(
						"[R197] Create lifecycle row for Announced valid from WDFM valid from (before delete) to 99991231");
			}
			// Update Announced status valid to date to 99991231
			if (lcd.getAnnounceValidFrom() != null) {
				// rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(),
				// getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
				// abr.addOutput("[R199] Delete lifecycle row for Announced");
				deleteAnnouncedDate(lcd, varCond, material, annDocNo, pimsIdentity, salesOrg);
				rdhRestProxy.r197(material, varCond, ANNOUNCE, lcd.getAnnounceValidFrom(),
						DateUtility.getInfinityDate(), getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
				abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to 99991231");
			}
		} else if (wdfmDate != null && lcd.getWdfmValidFrom() == null) {
			if (lcd.getAnnounceValidFrom() != null && wdfmDate.equals(lcd.getAnnounceValidFrom())) {
				// rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(),
				// getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
				// abr.addOutput("[R199] Delete lifecycle row for Announced");
				deleteAnnouncedDate(lcd, varCond, material, annDocNo, pimsIdentity, salesOrg);
				rdhRestProxy.r197(material, varCond, WDFM, wdfmDate, DateUtility.getInfinityDate(), getOpwgId(),
						annDocNo, "wdfm", pimsIdentity, salesOrg);
				abr.addOutput("[R197] Create lifecycle row for WDFM valid from WDFMDate to 99991231");
			} else {
				if (lcd.getAnnounceValidFrom() != null) {
					// Update Announced status valid to date to WDFM Date
					// rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(),
					// getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
					// abr.addOutput("[R199] Delete lifecycle row for Announced");
					deleteAnnouncedDate(lcd, varCond, material, annDocNo, pimsIdentity, salesOrg);
					rdhRestProxy.r197(material, varCond, ANNOUNCE, lcd.getAnnounceValidFrom(),
							DateUtility.getDateMinusOne(wdfmDate), getOpwgId(), annDocNo, "wdfm", pimsIdentity,
							salesOrg);
					abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to WDFMDate - 1");
				}
				// Create WDFM status
				rdhRestProxy.r197(material, varCond, WDFM, wdfmDate, DateUtility.getInfinityDate(), getOpwgId(),
						annDocNo, "wdfm", pimsIdentity, salesOrg);
				abr.addOutput("[R197] Create lifecycle row for WDFM valid from WDFMDate to 99991231");
			}
		} else if (wdfmDate != null && lcd.getWdfmValidFrom() != null) {
			if (!wdfmDate.equals(lcd.getWdfmValidFrom())) {
				// New Code Goes Here .
				if (lcd.getAnnounceValidFrom() == null) {
					if (DateUtility.dateIsAfter(wdfmDate, lcd.getWdfmValidFrom())) {
						rdhRestProxy.r197(material, varCond, ANNOUNCE, lcd.getWdfmValidFrom(),
								DateUtility.getDateMinusOne(wdfmDate), getOpwgId(), annDocNo, "wdfm", pimsIdentity,
								salesOrg);
						abr.addOutput(
								"[R197] Create lifecycle row for Announce from WDFM valid from (before change) to WDFMDate - 1");
					}
					rdhRestProxy.r198(material, varCond, WDFM, wdfmDate, DateUtility.getInfinityDate(), getOpwgId(),
							annDocNo, "wdfm", pimsIdentity, salesOrg);
					abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
					if (lcd.getPreAnnounceValidFrom() != null) {
						
						rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), getOpwgId(),
								annDocNo, "wdfm", pimsIdentity, salesOrg);
						abr.addOutput("[R199] Delete lifecycle row for Preannounced");
						if (DateUtility.dateIsBefore(lcd.getPreAnnounceValidFrom(), wdfmDate)) {
							rdhRestProxy.r197(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidFrom(),
									DateUtility.getDateMinusOne(lcd.getWdfmValidFrom()), getOpwgId(), annDocNo, "wdfm",
									pimsIdentity, salesOrg);
							abr.addOutput(
									"[R197] Create lifecycle row for Preannounced valid from current Preannounced valid from to WDFMDate - 1");
						}
					}
				} else if (lcd.getAnnounceValidFrom() != null
						&& wdfmDate.equals(lcd.getAnnounceValidFrom())) {
					// rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(),
					// getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
					// abr.addOutput("[R199] Delete lifecycle row for Announced");
					deleteAnnouncedDate(lcd, varCond, material, annDocNo, pimsIdentity, salesOrg);
					rdhRestProxy.r198(material, varCond, WDFM, wdfmDate, DateUtility.getInfinityDate(), getOpwgId(),
							annDocNo, "wdfm", pimsIdentity, salesOrg);
					abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
				} else {
					if (lcd.getAnnounceValidFrom() != null) {
						// Update Announced Valid to date to WDFMDate - 1
						// rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(),
						// getOpwgId(), annDocNo, "wdfm", pimsIdentity, salesOrg);
						// abr.addOutput("[R199] Delete lifecycle row for Announced");
						deleteAnnouncedDate(lcd, varCond, material, annDocNo, pimsIdentity, salesOrg);
						rdhRestProxy.r197(material, varCond, ANNOUNCE, lcd.getAnnounceValidFrom(),
								DateUtility.getDateMinusOne(wdfmDate), getOpwgId(), annDocNo, "wdfm", pimsIdentity,
								salesOrg);
						abr.addOutput("[R197] Create lifecycle row for Announced valid from prior date to WDFMDate");
					}
					// Update WDFM Date
					rdhRestProxy.r198(material, varCond, WDFM, wdfmDate, DateUtility.getInfinityDate(), getOpwgId(),
							annDocNo, "wdfm", pimsIdentity, salesOrg);
					abr.addOutput("[R198] Update lifecycle row for WDFM status valid from WDFMDate to 99991231");
				}
			} // else no change needed
		}
	}

	/**
	 * Fix the RDH duplicate Z0(ANNOUNCE) records issue when update WDFM announce
	 * lifecycle
	 *
	 * @param lcd
	 * @param varCond
	 * @param material
	 * @param annDocNo
	 * @param pimsIdentity
	 * @param salesOrg
	 * @throws Exception
	 */
	private void deleteAnnouncedDate(LifecycleData lcd, String varCond, String material, String annDocNo,
			String pimsIdentity, String salesOrg) throws Exception {
		if (lcd.getAnnounceDataList().size() > 1) {
			for (LifecycleAnnounceData lcad : lcd.getAnnounceDataList()) {
				rdhRestProxy.r199(material, varCond, ANNOUNCE, lcad.getValidTo(), getOpwgId(), annDocNo, "wdfm",
						pimsIdentity, salesOrg);
				abr.addOutput("Warning delete duplicate Z0(ANNOUNCE) record. [R199] Delete lifecycle row for Announced "
						+ material + "/" + varCond + "/" + lcad.getValidTo() + "/" + salesOrg);
			}
		} else {
			rdhRestProxy.r199(material, varCond, ANNOUNCE, lcd.getAnnounceValidTo(), getOpwgId(), annDocNo, "wdfm",
					pimsIdentity, salesOrg);
			abr.addOutput("[R199] Delete lifecycle row for Announced");
		}
	}

	protected List<SalesOrgPlants> getAllSalesOrgPlant(Vector generalareaVct) throws RfcAbrException {
		List<SalesOrgPlants> salesorgPlantsVect = new ArrayList<>();
		for (int i = 0; i < generalareaVct.size(); i++) {
			EntityItem generalarea = (EntityItem) generalareaVct.get(i);
			String salesOrg = getAttributeValue(generalarea, GENERALAREA_SLEORG);
			if (salesOrg != null && !"".equals(salesOrg)) {
				SalesOrgPlants salesorgPlants = new SalesOrgPlants();
				salesorgPlants.setGenAreaCode(getAttributeValue(generalarea, GENERALAREA_GENAREACODE));
				salesorgPlants.setGenAreaName(getAttributeFlagValue(generalarea, GENERALAREA_GENAREANAME));
				salesorgPlants.setGeo(getAttributeValue(generalarea, GENERALAREA_GENAREAPARENT)); //
				salesorgPlants.setSalesorg(salesOrg);
				salesorgPlants.setPlants(getAttributeMultiFlagValue(generalarea, GENERALAREA_CBSLEGACYPLNTCD));
				salesorgPlants.setReturnPlants(getAttributeMultiFlagValue(generalarea, GENERALAREA_CBSRETURNPNTCD));
				salesorgPlantsVect.add(salesorgPlants);
			} else {
				abr.addDebug("getAllSalesOrgPlant SalesOrg value is null for " + generalarea.getKey());
			}
		}
		abr.addDebug("getAllSalesOrgPlant GENERALAREA size: " + generalareaVct.size() + " SalesorgPlants size: "
				+ salesorgPlantsVect.size());
		return salesorgPlantsVect;
	}

	protected List<SalesOrgPlants> getAllSalesOrgPlantByCountryList(List<SalesOrgPlants> salesOrgPlnatList,
			List<String> countryList) throws RfcAbrException {
		List<SalesOrgPlants> salesorgPlantsVect = new ArrayList<>();
		for (SalesOrgPlants salesOrgPlants : salesOrgPlnatList) {
			if (countryList.contains(salesOrgPlants.getGenAreaName())) {
				salesorgPlantsVect.add(salesOrgPlants);
			}
		}
		abr.addDebug("getAllSalesOrgPlantByCountryList SalesOrgPlants size: " + salesorgPlantsVect.size()
				+ " for country list: " + countryList);
		return salesorgPlantsVect;
	}

	protected Set<String> getAllPlant(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> plants = new HashSet<>();
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			Vector<String> tmpPlants = salesorgPlants.getPlants();
			for (String plant : tmpPlants) {
				plants.add(plant);
			}
			if (tmpPlants.size() == 0) {
				abr.addDebug("getAllPlant No plant found for country code: " + salesorgPlants.getGenAreaCode());
			}
		}
		abr.addDebug("getAllPlant plants size: " + plants.size() + " values: " + plants);
		return plants;
	}

	// protected void updateMtcBomType(String mtcBomType, CHWAnnouncement
	// announcement, String pimsIdentity) {
	// if (mtcBomType != null && !"".equals(mtcBomType)) {
	// String mtctype = mtcBomType;
	// abr.addDebug("Value of Type " + mtctype);
	// boolean typeMTCExists = rdhRestProxy.r204(mtctype + "MTC");
	// if (typeMTCExists) {
	// abr.addDebug("Value of the Type ******" + mtctype);
	// List<String> plantList = new ArrayList<>();
	// plantList.add("1222");
	// plantList.add("1999");
	// for (int p = 0; p < plantList.size(); p++) {
	// String _plant = plantList.get(p);
	// Vector typeNEW = rdhRestProxy.r210(mtctype, "NEW", _plant);// null handle
	// suchit
	// if (typeNEW == null) {
	// continue;
	// }
	// abr.addDebug("Reading Sales Bom for TypeNEW: " + typeNEW.size());
	// DepData res_Vector = new DepData();
	// for (int i = 0; i < typeNEW.size(); i++) {
	// res_Vector = (DepData) typeNEW.elementAt(i);
	// }// end for
	// Vector typeMTC = rdhRestProxy.r210(mtctype, "MTC", _plant);
	// if (typeMTC != null) {
	// abr.addDebug("Reading Sales Bom for TypeMTC: " + typeMTC.size());
	// for (int i = 0; i < typeMTC.size(); i++) {
	// res_Vector = (DepData) typeMTC.elementAt(i);
	// }// end for
	// }// end if
	// if (typeMTC == null) {
	// rdhRestProxy.r211(mtctype, _plant, typeNEW, "MTC", announcement,
	// pimsIdentity);
	// }// end if
	// else {
	// Vector componentstoDeleteTypeMTC = getComponentsintypeMTCwithtypeNEW(typeMTC,
	// typeNEW);
	// if (componentstoDeleteTypeMTC != null) {
	// DepData res_Vector1 = new DepData();
	// for (int i = 0; i < componentstoDeleteTypeMTC.size(); i++) {
	// res_Vector1 = (DepData) componentstoDeleteTypeMTC.elementAt(i);
	// }// end for
	// }// end else
	//
	// if (componentstoDeleteTypeMTC != null) {
	// rdhRestProxy.r212(mtctype, _plant, componentstoDeleteTypeMTC, "MTC",
	// announcement, pimsIdentity);
	// }// end if
	//
	// Vector updatecomponentsforTypeMTC = getComponentsintypeNEW(typeMTC, typeNEW);
	//
	// if (updatecomponentsforTypeMTC != null) {
	// rdhRestProxy.r213(mtctype, _plant, updatecomponentsforTypeMTC, "MTC",
	// announcement, pimsIdentity);
	// } // end if
	// }// end else
	// }
	// }
	// }
	// }

	/**
	 * Re: Question for RFC call r117 05/06/2016 08:48 PM Here's the mapping we can
	 * use PDHDOMAIN for this. This field stores the name of the business unit where
	 * the revenue will be allocated if the product is sold. Select the appropriate
	 * value from the list. Examples: " AS4 i-Series " RS6 p-Series " LSC z-Series
	 * This field is sent to MMLC.
	 * 
	 * @param entity
	 * @return
	 * @throws RfcAbrException
	 */
	protected String getSegmentAcronymForAnn(EntityItem entity) throws RfcAbrException {
		String segmentAcronym = "";
		String domain = getAttributeValue(entity, PDHDOMAIN);
		if ("iSeries".equals(domain)) {
			segmentAcronym = "AS4";
		} else if ("pSeries".equals(domain)) {
			segmentAcronym = "RS6";
		} else if ("zSeries".equals(domain)) {
			segmentAcronym = "LSC";
		}
		return segmentAcronym;
	}

	/**
	 * Get new countries for avail at T2
	 * 
	 * @param t1Avails
	 * @param t2Avail
	 * @return
	 * @throws RfcAbrException
	 */
	protected List<String> getNewCountries(Vector t1Avails, EntityItem t2Avail) throws RfcAbrException {
		List<String> t1Countries = getEntitiesAttributeValues(t1Avails, AVAIL_COUNTRYLIST, ATTR_MULTI_FLAG);
		List<String> countryVct = getEntitiyAttributeValues(t2Avail, AVAIL_COUNTRYLIST, ATTR_MULTI_FLAG);
		abr.addDebug("isTypeModelGeoPromoted T1 all Country size:" + t1Countries.size() + " values: " + t1Countries);
		abr.addDebug("isTypeModelGeoPromoted T2 avail Country size:" + countryVct.size() + " values: " + countryVct);
		countryVct.removeAll(t1Countries);
		abr.addDebug("isTypeModelGeoPromoted new county size:" + countryVct.size() + " values:" + countryVct);
		return countryVct;
	}

	protected String getDiv(EntityItem modelItem) throws RfcAbrException {
		String div = null;
		Vector sgmItems = PokUtils.getAllLinkedEntities(modelItem, "MODELSGMTACRONYMA", SGMNTACRNYM);
		for (int i = 0; i < sgmItems.size(); i++) {
			EntityItem sgmItem = (EntityItem) sgmItems.get(i);
			div = getAttributeFlagValue(sgmItem, SGMNTACRNYM_DIV);
			if (div != null && !"".equals(div)) {
				break;
			}
		}
		// div is required
		if (div == null || "".equals(div)) {
			throw new RfcAbrException("Can not extract DIV value, but it is a required value for web service");
		}
		return div;
	}

	/**
	 * Re: blockers of CHW Promote Type Model logic 05/10/2016 12:27 AM PRODHIERCD
	 * -deviation logic changed We have to use MODEL.WWOCCODE -->
	 * GBT.WWOCCODE-->GBT.SAPPRIMBRANDCD+GBT.SAPPRODFMLYCD
	 * 
	 * @param mdlItem
	 * @return
	 * @throws RfcAbrException
	 */
	@SuppressWarnings({ "unused" })
	protected String getProdHireCd(EntityItem mdlItem) throws RfcAbrException {
		String prodHireCd = "";
		Vector gbts = PokUtils.getAllLinkedEntities(mdlItem, "MODELGBTA", GBT);
		for (int i = 0; i < gbts.size(); i++) {
			EntityItem gbtItem = (EntityItem) gbts.get(i);
			prodHireCd = getAttributeValue(gbtItem, GBT_SAPPRIMBRANDCD) + getAttributeValue(gbtItem, GBT_SAPPRODFMLYCD);
			break;
		}
		return prodHireCd;
	}

	protected Vector getTaxListBySalesOrgPlants(SalesOrgPlants salesOrgPlants) {
		Vector tmpTax = new Vector();
		String salesOrg = salesOrgPlants.getSalesorg();
		String country = salesOrgPlants.getGenAreaCode();
		String taxCatg = getTaxCatgBySalesOrgAndCountry(salesOrg, country);
		if (taxCatg != null) {
			CntryTax cntryTax = new CntryTax();
			cntryTax.setCountry(country);
			cntryTax.setTaxCategory(taxCatg);
			String taxCls = "US".equals(country) ? "H" : "1";
			cntryTax.setClassification(taxCls);
			tmpTax.add(cntryTax);
			// 1642763: Tax support for US when product is set for ZA(south Africa)
			// if ("ZA".equals(country)) {
			// taxCatg = getTaxCatgBySalesOrgAndCountry(salesOrg, "US");
			// abr.addDebug("getTaxListBySalesOrgPlants product is set for ZA, add tax for
			// US. taxCatg: " + taxCatg);
			// if (taxCatg != null) {
			// cntryTax = new CntryTax();
			// cntryTax.setCountry("US");
			// cntryTax.setTaxCategory(taxCatg);
			// cntryTax.setClassification("H");
			// tmpTax.add(cntryTax);
			// }
			// }
		}
		abr.addDebug("getTaxListBySalesOrgPlants TaxList size: " + tmpTax.size() + " for country: " + country
				+ " salesOrg: " + salesOrg);
		return tmpTax;
	}

	protected String getTaxCatgBySalesOrgAndCountry(String salesOrg, String country) {
		String key = "eacm.salesOrgCountry" + "_" + salesOrg + "_" + country;
		abr.addDebug("getTaxCatgBySalesOrgAndCountry key " + key);
		return configManager.getString(key);
	}

	/*
	 * Get all values for attribute of the entity vector
	 */
	protected List<String> getEntitiesAttributeValues(Vector entities, String attrCode, String attrType)
			throws RfcAbrException {
		List<String> values = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			EntityItem item = (EntityItem) entities.get(i);
			values.addAll(getEntitiyAttributeValues(item, attrCode, attrType));
		}
		return values;
	}

	/**
	 * 1. read revData by r193 a. if return message contain "not found in MAST
	 * table", call r194 to create all auoMaterials from MODEL, exit b. if a row is
	 * not found in revData, call r196 to update all auoMaterials from MODEL c. if
	 * rows are returned from revData i. get all auoMaterials from MODEL and exist
	 * in revData, combine auoMaterials deleted in T2 and exist in revData, call
	 * r195 to delete the auoMaterials ii. call r196 to update all auoMaterials from
	 * MODEL
	 * 
	 * @param type
	 * @param annDocNo
	 * @param auoMaterials
	 * @param typeModelRevs
	 * @param revProfileName
	 * @param newFlag
	 * @param user
	 * @param pimsIdentity
	 * @param _plant
	 * @param deletedAuoMaterials
	 * @throws Exception
	 */
	protected void updateRevenueProfileBom(String type, String annDocNo, Vector auoMaterials, Vector typeModelRevs,
			String revProfileName, String newFlag, String user, String pimsIdentity, String _plant,
			Vector deletedAuoMaterials) throws Exception {
		try {
			Vector revData = rdhRestProxy.r193(type, newFlag, _plant);
			abr.addOutput("[R193] Read revenue profile BOM for plant " + _plant + " type " + newFlag);
			if (revData == null || revData.size() == 0) {
				abr.addDebug("updateRevenueProfileBom exist in MAST but no conponent data return from r193");
				// R196UpdateRevenueProfile Z_DM_SAP_BOM_MAINTAIN
				rdhRestProxy.r196(type, annDocNo, auoMaterials, typeModelRevs, revProfileName, newFlag, pimsIdentity,
						_plant);
				abr.addOutput("[R196] Update revenue profile BOM for plant " + _plant);
			} else {
				/**
				 * 1. call r195 to delete all exist and deleted AUOMTRLs in T2 2. call r196 to
				 * add the all exist AUOMTRLs back in T2
				 */
				Vector existInRDHAuoMaterials = getAuoMaterailIntersection(revData, auoMaterials);
				abr.addDebug("updateRevenueProfileBom return data size: " + revData.size()
						+ " from r193, AuoMaterials size: " + auoMaterials.size());
				if (deletedAuoMaterials != null && deletedAuoMaterials.size() > 0) {
					Vector existInRDHDeletedInEACMAuoMaterials = getAuoMaterailIntersection(revData,
							deletedAuoMaterials);
					abr.addDebug("updateRevenueProfileBom deleted AuoMaterials size: " + deletedAuoMaterials.size()
							+ ", existInRDHDeletedInEACMAuoMaterials size: "
							+ existInRDHDeletedInEACMAuoMaterials.size());
					existInRDHAuoMaterials.addAll(existInRDHDeletedInEACMAuoMaterials);
				}
				// R195DeleteRevenueProfile Z_DM_SAP_BOM_MAINTAIN
				rdhRestProxy.r195(type, annDocNo, existInRDHAuoMaterials, typeModelRevs, revProfileName, newFlag,
						pimsIdentity, _plant);
				abr.addOutput("[R195] Delete revenue profile BOM components for plant " + _plant);
				rdhRestProxy.r196(type, annDocNo, auoMaterials, typeModelRevs, revProfileName, newFlag, pimsIdentity,
						_plant);
				abr.addOutput("[R196] Update revenue profile BOM for plant " + _plant);
			}
		} catch (HWPIMSNotFoundInMastException e) {
			abr.addDebug("updateRevenueProfileBom not found in MAST table");
			// R194CreateRevenueProfile Z_DM_SAP_BOM_CREATE
			rdhRestProxy.r194(type, annDocNo, auoMaterials, typeModelRevs, revProfileName, newFlag, pimsIdentity,
					_plant);
			abr.addOutput("[R194] Create revenue profile BOM for plant " + _plant);
		}
	}

	private Vector getAuoMaterailIntersection(Vector revDatas, Vector auoMaterials) {
		Vector resultVector = new Vector();
		if (revDatas != null && auoMaterials != null) {
			for (Object revData : revDatas) {
				RevData rData = (RevData) revData;
				for (Object material : auoMaterials) {
					AUOMaterial aouMaterial = (AUOMaterial) material;
					// Defect1734655 - Defect MODEL RFCABR created duplicate components of sales BOM
					// in RDH
					// trim the RDH returned result, we met an issue because the component will
					// append blank spaces
					if (rData.getComponent().trim().equals(aouMaterial.getMaterial())) {
						resultVector.add(revData);
						break;
					}
				}
			}
		}
		return resultVector;
	}

	/*
	 * Get all values for attribute of the entity
	 */
	protected List<String> getEntitiyAttributeValues(EntityItem item, String attrCode, String attrType)
			throws RfcAbrException {
		List<String> values = new ArrayList<>();
		if (ATTR_FLAG.equals(attrType)) {
			String value = getAttributeFlagValue(item, attrCode);
			values.add(value);
		} else if (ATTR_TEXT.equals(attrType)) {
			String value = getAttributeValue(item, attrCode);
			values.add(value);
		} else if (ATTR_MULTI_FLAG.equals(attrType)) {
			Vector tmpValues = getAttributeMultiFlagValue(item, attrCode);
			for (int j = 0; j < tmpValues.size(); j++) {
				String tmpValue = (String) tmpValues.get(j);
				values.add(tmpValue);
			}
		} else if (ATTR_MULTI_TEXT.equals(attrType)) {
			Vector tmpValues = getAttributeMultiValue(item, attrCode);
			for (int j = 0; j < tmpValues.size(); j++) {
				String tmpValue = (String) tmpValues.get(j);
				values.add(tmpValue);
			}
		} else {
			throw new RfcAbrException("Unknow attribute type:" + attrType);
		}
		return values;
	}

	@SuppressWarnings("unused")
	private Vector getComponentsintypeMTCwithtypeNEW(Vector typeModelDeleted, Vector hashtypemodelbom) {

		Vector notmatch = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = hashtypemodelbom.elements();
		Enumeration dapiter = typeModelDeleted.elements();
		abr.addDebug("Vecor Size for typeMTC" + typeModelDeleted.size());
		abr.addDebug("Vecor Size for typeNEW" + hashtypemodelbom.size());

		DepData dDataNEW = new DepData();
		while (sapiter.hasMoreElements()) {
			dDataNEW = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value" + dDataNEW.getComponent());
			vect1.addElement(newtypecomponet);
		}

		// for (int i=0;i<=typeModelDeleted.size();i++)
		// {
		while (dapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) dapiter.nextElement();
			String component = dDataMTC.getComponent();
			abr.addDebug("vector typeMTC component value" + dDataMTC.getComponent());
			if ((vect1.contains(component))) {
				if (notmatch != null && notmatch.size() == 0)
					notmatch = null;
			} else {
				if (notmatch == null) {
					notmatch = new Vector();
				}
				notmatch.add(dDataMTC);
			}
		}
		return notmatch;
	}

	@SuppressWarnings({ "unused" })
	private Vector getComponentsintypeNEW(Vector typeModelDeleted, Vector hashtypemodelbom) {

		abr.addDebug("Vecor Size for typeMTC" + typeModelDeleted.size());
		abr.addDebug("Vecor Size for typeNEW" + hashtypemodelbom.size());
		Vector updatemtc = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = typeModelDeleted.elements();
		Enumeration dapiter = hashtypemodelbom.elements();
		// for (int k=0;k<=typeModelDeleted.size();k++){
		while (sapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataMTC.getComponent();
			vect1.addElement(newtypecomponet);
			abr.addDebug("vector typeMTC component value" + dDataMTC.getComponent());
		}

		while (dapiter.hasMoreElements()) {
			DepData dDataNEW = (DepData) dapiter.nextElement();
			String component = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value" + dDataNEW.getComponent());
			if ((vect1.contains(component))) {
				if (updatemtc != null && updatemtc.size() == 0)
					updatemtc = null;
			} else {
				// if(!(updatemtc.contains(component))){
				if (updatemtc == null) {
					updatemtc = new Vector();
				}
				updatemtc.add(dDataNEW);
			}
		}
		return updatemtc;
	}

	/**
	 * Generate RevProfile extract values from REVPROF and AUOMTRL of MODEL
	 * 
	 * @param modelEntityItem
	 * @return RevProfile
	 * @throws RfcAbrException
	 */
	protected RevProfile getRevProfile(EntityItem modelEntityItem) throws RfcAbrException {
		// add REVPROFILE and AUOMTRL
		RevProfile revProfile = new RevProfile();
		Vector revprofileItems = PokUtils.getAllLinkedEntities(modelEntityItem, "MODREVPROFILE", REVPROF);
		abr.addDebug("vector size of revprofileItems=" + revprofileItems.size());
		EntityItem revprofileItem = null;
		EntityItem auoMaterialItem = null;
		Vector auoMaterialsVector = new Vector();
		if (revprofileItems != null && revprofileItems.size() > 0) {
			revprofileItem = (EntityItem) revprofileItems.get(0);
			String revFile = getAttributeValue(revprofileItem, "REVPROFILE");
			abr.addDebug("revFile= " + revFile + "REVPROFILE=" + revprofileItem.getEntityID());
			revProfile.setRevenueProfile(revFile);

			List<EntityItem> REVPROFAUOMTRLItems = getLinkedRelator(revprofileItem, "REVPROFAUOMTRL");
			for (EntityItem REVPROFAUOMTRLItem : REVPROFAUOMTRLItems) {
				AUOMaterial autoMaterial = null;
				String sPERCENTAGE = getAttributeValue(REVPROFAUOMTRLItem, "PERCENTAGE");
				auoMaterialItem = (EntityItem) REVPROFAUOMTRLItem.getDownLink(0);
				String sMATERIAL = getAttributeValue(auoMaterialItem, "MATERIAL");
				abr.addDebug("MATERIAL= " + sMATERIAL + ";PERCENTAGE=" + sPERCENTAGE);
				autoMaterial = new AUOMaterial(sMATERIAL, sPERCENTAGE);
				auoMaterialsVector.add(autoMaterial);
			}
			revProfile.setAuoMaterials(auoMaterialsVector);
		}
		return revProfile;
	}

	protected List<EntityItem> getMACHTYPEsByType(String type)
			throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		List<EntityItem> machTypeItems = new ArrayList<>();
		String searchAction = "SRDMACHTYPE1";
		String srchType = MACHTYPE;
		StringBuffer debugSb = new StringBuffer();
		Vector attrVct = new Vector();
		Vector valVct = new Vector();
		attrVct.add(MACHTYPE_MACHTYPEATR);
		valVct.add(type);
		abr.addDebug("getMachtypesByType searchAction " + searchAction + " srchType " + srchType + " MACHTYPEATR "
				+ type + " with role=" + abr.getProfile().getRoleCode());
		EntityItem[] items = ABRUtil.doSearch(abr.getDatabase(), abr.getProfile(), searchAction, srchType, false,
				attrVct, valVct, debugSb);
		abr.addDebug("getMachtypesByType " + debugSb.toString());
		if (items != null) {
			for (EntityItem item : items) {
				machTypeItems.add(item);
			}
		}
		abr.addDebug("getMachtypesByType MACHTYPE size " + machTypeItems.size());
		return machTypeItems;
	}

	/**
	 * Get Loading Group value for MODEL entity item
	 * 
	 * Reference PlantOfMan.xls mapping, first column means GEOMOD.PLNTOFMFR. As
	 * ePims code loading group is a single value, so only get first GEOMOD. Default
	 * value "B001" when null
	 * 
	 * @param mdlItem
	 * @return
	 * @throws RfcAbrException
	 */
	protected String getLoadingGroup(EntityItem mdlItem) throws RfcAbrException {
		String ladgr = null;
		Vector geoMods = PokUtils.getAllLinkedEntities(mdlItem, "MODELGEOMOD", "GEOMOD");
		if (geoMods.size() > 0) {
			EntityItem geoMod = (EntityItem) geoMods.get(0);
			String plantOfMfr = getAttributeShortValue(geoMod, "PLNTOFMFR");
			ladgr = getLoadingGroupByPlantOfMfr(plantOfMfr);
			abr.addDebug("getLoadingGroup ladgr " + ladgr + " for PLNTOFMFR " + plantOfMfr + " of " + geoMod.getKey()
					+ " " + mdlItem.getKey());
		}
		if (ladgr == null || "".equals(ladgr.trim())) {
			ladgr = "H001";
			abr.addDebug("getLoadingGroup ladgr is null, set H001 as default");
		}
		return ladgr;
	}

	/**
	 * Get loading group from property file.
	 * 
	 * @param plantOfMfr
	 * @return
	 */
	private String getLoadingGroupByPlantOfMfr(String plantOfMfr) {
		String key = PropertyKeys.KEY_EACM_PLNTOFMFR_LADGR_PREFIX + plantOfMfr;
		abr.addDebug("getLoadingGroupByPlantOfMfr key " + key);
		return configManager.getString(key);
	}

	protected boolean isRevProfOrAuoMtrlChanged(EntityItem t1MdlItem, EntityItem t2MdlItem) throws RfcAbrException {
		List<String> rev1 = new ArrayList<>();
		List<String> rev2 = new ArrayList<>();
		RevProfile revProf1 = getRevProfile(t1MdlItem);
		RevProfile revProf2 = getRevProfile(t2MdlItem);
		// Check RevenueProfile
		String revProfAttr1 = revProf1.getRevenueProfile();
		String revProfAttr2 = revProf2.getRevenueProfile();
		Vector auoMtrl1 = revProf1.getAuoMaterials();
		if (auoMtrl1 != null && auoMtrl1.size() > 0) {
			for (int i = 0; i < auoMtrl1.size(); i++) {
				AUOMaterial auo = (AUOMaterial) auoMtrl1.get(i);
				String key = revProfAttr1 + "|" + auo.getPercentage() + "|" + auo.getMaterial();
				rev1.add(key);
			}
		} else {
			rev1.add(revProfAttr1);
		}
		Vector auoMtrl2 = revProf2.getAuoMaterials();
		if (auoMtrl2 != null && auoMtrl2.size() > 0) {
			for (int i = 0; i < auoMtrl2.size(); i++) {
				AUOMaterial auo = (AUOMaterial) auoMtrl2.get(i);
				String key = revProfAttr2 + "|" + auo.getPercentage() + "|" + auo.getMaterial();
				rev2.add(key);
			}
		} else {
			rev2.add(revProfAttr2);
		}
		abr.addDebug("isRevProfOrAuoMtrlChanged T1 " + rev1);
		abr.addDebug("isRevProfOrAuoMtrlChanged T2 " + rev2);
		boolean isRevProfOrAuoMtrlChanged = !(rev1.containsAll(rev2) && rev2.containsAll(rev1));
		abr.addDebug("isRevProfOrAuoMtrlChanged  " + isRevProfOrAuoMtrlChanged);
		return isRevProfOrAuoMtrlChanged;
	}

	protected boolean needReleaseParkingTable() {
		if ("n".equalsIgnoreCase(configManager.getString("eacm.releaseParkingTable_" + abr.getEntityType()))) {
			abr.addDebug("needReleaseParkingTable  false");
			return false;
		}
		abr.addDebug("needReleaseParkingTable  true");
		return true;
	}

	/**
	 * Get materials exist in t1 but not in t2, these materials was deleted in t2
	 * 
	 * @param t1materials
	 * @param t2Materials
	 * @return
	 */
	protected Vector getDeletedAuoMaterials(Vector t1materials, Vector t2Materials) {
		Vector deletedMaterials = new Vector();
		if (t1materials != null && t1materials.size() > 0) {
			for (Object auo : t1materials) {
				deletedMaterials.add(auo);
			}
			for (Object auo : t1materials) {
				AUOMaterial t1AuoMaterial = (AUOMaterial) auo;
				if (t2Materials != null && t2Materials.size() > 0) {
					for (Object auo2 : t2Materials) {
						AUOMaterial t2AuoMaterial = (AUOMaterial) auo2;
						String t1Material = t1AuoMaterial.getMaterial();
						String t2Material = t2AuoMaterial.getMaterial();
						String t1Percentage = t1AuoMaterial.getPercentage();
						String t2Percentage = t2AuoMaterial.getPercentage();
						if (isSameString(t1Material, t2Material) && isSameString(t1Percentage, t2Percentage)) {
							deletedMaterials.remove(auo);
							break;
						}
					}
				}
			}
		}
		return deletedMaterials;
	}

	/**
	 * Search all(world wide) GENERALAREAs
	 * 
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 */
	protected Vector searchAllGeneralAreas() throws MiddlewareRequestException, SQLException, MiddlewareException {
		Vector generalAreaVct = new Vector();
		List<Integer> entityIdList = searchAllGeneralAreaEntityIds();
		for (Integer entityId : entityIdList) {
			if (entityId != null && entityId > 0) {
				EntityItem gaItem = getEntityList(abr.getDatabase(), abr.getProfile(), "dummy", GENERALAREA, entityId)
						.getParentEntityGroup().getEntityItem(0);
				generalAreaVct.add(gaItem);
			}
		}
		abr.addDebug("searchAllGeneralAreas GENERALAREA size " + generalAreaVct.size());
		return generalAreaVct;
	}

	/**
	 * Search all available entityids of entity GENERALAREA
	 * 
	 * @return
	 */
	private List<Integer> searchAllGeneralAreaEntityIds() {
		List<Integer> idList = new ArrayList<Integer>();
		PreparedStatement ps = null;
		String sql = "SELECT entityid FROM opicm.entity WHERE entitytype='GENERALAREA' and valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP WITH UR";
		try {
			ps = abr.getDatabase().getPDHConnection().prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				idList.add(result.getInt(1));
			}
		} catch (MiddlewareException e) {
			e.printStackTrace();
			abr.addDebug("searchAllGENERALAREAEntityIds MiddlewareException on " + e.getMessage());
		} catch (SQLException rx) {
			rx.printStackTrace();
			abr.addDebug("searchAllGENERALAREAEntityIds SQLException on " + rx.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		abr.addDebug("searchAllGENERALAREAEntityIds GENERALAREA id size " + idList.size());
		return idList;
	}

	private boolean isSameString(String str1, String str2) {
		boolean isSame = false;
		if (str1 == null && str2 == null) {
			isSame = true;
		} else if (str1 != null && str1.equals(str2)) {
			isSame = true;
		} else if (str2 != null && str2.equals(str1)) {
			isSame = true;
		}
		return isSame;
	}

	/**
	 * Check if the GENERALAREA contains ZA but no US
	 * 
	 * @param genAreaItemVct
	 * @return
	 * @throws RfcAbrException
	 */
	protected boolean isGENERALAREAContainsZAButNoUS(Vector genAreaItemVct) throws RfcAbrException {
		boolean containZA = false;
		boolean containUS = false;
		for (int i = 0; i < genAreaItemVct.size(); i++) {
			EntityItem genAreaItem = (EntityItem) genAreaItemVct.get(i);
			String genName = getAttributeFlagValue(genAreaItem, GENERALAREA_GENAREANAME);
			if (GENAREANAME_ZA.equals(genName)) {
				containZA = true;
			}
			if (GENAREANAME_US.equals(genName)) {
				containUS = true;
			}
		}
		if (containZA && !containUS) {
			return true;
		}
		return false;
	}

	/**
	 * 1642763: Tax support for US when product is set for ZA(south Africa) EACM is
	 * only supporting AVAIL.Countrylist to derive the sales org that defaults Tax
	 * Code. We need to support a scenario where if Product is set for South
	 * Africa=ZA we need to set Tax Code for US as well.
	 * 
	 * @param typeModel
	 * @param chwA
	 * @param chwAg
	 * @param pimsIdentity
	 * @param flfilcd
	 * @throws Exception
	 */
	protected void processTaxSupportForUSWhenProductIsSetForZA(TypeModel typeModel, CHWAnnouncement chwA,
			CHWGeoAnn chwAg, String pimsIdentity, String flfilcd, boolean isMTC, TypeModelUPGGeo tmUPGObj,
			String fromToType, String callerName, PlannedSalesStatus ps) throws Exception {
		Integer entityId = getGENERALAREAEntityIdByGENAREANAME(GENAREANAME_US);
		if (entityId != null) {
			EntityItem genAreaItem = getEntityList(abr.getDatabase(), abr.getProfile(), "dummy", GENERALAREA, entityId)
					.getParentEntityGroup().getEntityItem(0);
			Vector _plantVct = getAttributeMultiFlagValue(genAreaItem, GENERALAREA_CBSLEGACYPLNTCD);
			String salesOrg = getAttributeValue(genAreaItem, GENERALAREA_SLEORG);
			String genAreaCode = getAttributeValue(genAreaItem, GENERALAREA_GENAREACODE);
			SalesOrgPlants salesOrgPlants = new SalesOrgPlants();
			salesOrgPlants.setSalesorg(salesOrg);
			salesOrgPlants.setGenAreaCode(genAreaCode);
			Vector vectTaxList = getTaxListBySalesOrgPlants(salesOrgPlants);
			for (int i = 0; i < _plantVct.size(); i++) {
				String _plant = (String) _plantVct.get(i);
				abr.addDebug(
						"processTaxSupportForUSWhenProductIsSetForZA for plant: " + _plant + " salesOrg: " + salesOrg);
				if (_plant.equals("1999")) {
					abr.addDebug("processTaxSupportForUSWhenProductIsSetForZA skip plant for 1999");
					continue;
				}
				if ("r117".equalsIgnoreCase(callerName)) {
					rdhRestProxy.r117(chwA, typeModel.getType() + typeModel.getModel(), typeModel.getDiv(),
							typeModel.getAcctAsgnGrp(), ps, true, pimsIdentity, flfilcd, salesOrg,
							typeModel.getProductHierarchy(), vectTaxList, _plant, chwAg);
					abr.addOutput("[R177] Create 300 classification for type UF for UPG for plant " + _plant);
				} else {
					if (isMTC) {
						rdhRestProxy.r102(chwA, typeModel, _plant, "MTC", tmUPGObj, fromToType, pimsIdentity, flfilcd,
								salesOrg, vectTaxList, chwAg, null); // MTC
						abr.addOutput("[R102] Create generic plant " + _plant + " view for type MTC material");
					} else {
						rdhRestProxy.r102(chwA, typeModel, _plant, "NEW", null, null, pimsIdentity, flfilcd, salesOrg,
								vectTaxList, chwAg, null); // NEW
						rdhRestProxy.r102(chwA, typeModel, _plant, "UPG", null, null, pimsIdentity, flfilcd, salesOrg,
								vectTaxList, chwAg, null); // UPG
						abr.addOutput("[R102] Create generic plant " + _plant + " view for type NEW/UPG material");
					}
				}
			}
		}
	}

	/**
	 * Get GENERALAREA ENTITYID by GENAREANAME attribute value
	 * 
	 * @param code
	 *            GENAREANAME, like US the code is 1652
	 * @return GENERALAREA.ENTITYID
	 */
	private Integer getGENERALAREAEntityIdByGENAREANAME(String code) {
		Integer entityId = null;
		Statement st = null;
		String sql = "select entityid from opicm.flag where entitytype = 'GENERALAREA' AND attributecode='GENAREANAME' AND ATTRIBUTEVALUE='"
				+ code + "' and valto > current timestamp and effto > current timestamp with ur";
		try {
			st = abr.getDatabase().getPDHConnection().createStatement();
			ResultSet result = st.executeQuery(sql);
			if (result.next()) {
				entityId = result.getInt(1);
			}
		} catch (MiddlewareException e) {
			abr.addDebug("MiddlewareException on ? " + e);
			e.printStackTrace();
		} catch (SQLException rx) {
			abr.addDebug("SQLException on ? " + rx);
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
		abr.addDebug("getGENERALAREAEntityIdByGENAREANAME entityid: " + entityId + " GENAREANAME: " + code);
		return entityId;
	}

	/**
	 * Check if the type model is a HIPO MODEL
	 * 
	 * @param type
	 * @param model
	 * @return
	 */
	protected boolean isHIPOModel(String type, String model) {
		boolean isHIPO = false;
		if (("5313".equals(type) && "HPO".equals(model)) || ("5372".equals(type) && "IS5".equals(model))) {
			isHIPO = true;
		}
		return isHIPO;
	}

	protected boolean isNetPriceMES(EntityItem mdlItem) throws RfcAbrException {
		boolean isNetPriceMES = false;
		String netPriceMES = getAttributeFlagValue(mdlItem, "NETPRICEMES");
		if (NETPRICEMES_YES.equals(netPriceMES)) {
			isNetPriceMES = true;
		}
		abr.addDebug("isNetPriceMES NETPRICEMES: " + netPriceMES + " isNetPriceMES: " + isNetPriceMES + " for "
				+ mdlItem.getKey());
		return isNetPriceMES;
	}

	protected String getUniqueId(String type) {
		if (uniqueID == null)
			uniqueID = UniqueIdGenerator.getUniqueIdGenerator(type).getUniqueID(abr);
		return uniqueID;
	}
}
