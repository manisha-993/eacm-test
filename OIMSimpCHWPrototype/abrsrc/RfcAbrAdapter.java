package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANEntity;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.DateUtility;
import com.ibm.rdh.chw.entity.DepData;
import com.ibm.rdh.chw.entity.LifecycleData;
import com.ibm.rdh.rfc.proxy.RdhRestProxy;
import com.ibm.transform.oim.eacm.util.PokUtils;

public abstract class RfcAbrAdapter implements RfcAbr {
	
	// Entity 
	protected static final String MODEL = "MODEL";
	protected static final String AVAIL = "AVAIL";
	protected static final String ANNOUNCEMENT = "ANNOUNCEMENT";
	protected static final String MACHTYPE = "MACHTYPE";
	protected static final String GENERALAREA = "GENERALAREA";
	protected static final String GBT = "GBT";
	protected static final String TAXCATG = "TAXCATG";
	protected static final String MODTAXRELEVANCE = "MODTAXRELEVANCE";
	protected static final String SGMNTACRNYM = "SGMNTACRNYM";
	// Entity attribute
	protected static final String MACHTYPE_PROMOTED = "PROMOTED";
	protected static final String MODEL_MACHTYPEATR = "MACHTYPEATR";
	protected static final String MODEL_MODELATR = "MODELATR";
	protected static final String MODEL_MKTGNAME = "MKTGNAME";
	protected static final String MODEL_ACCTASGNGRP = "ACCTASGNGRP";
	protected static final String MODEL_PRFTCTR = "PRFTCTR";
	protected static final String MODEL_INSTALL = "INSTALL";
	protected static final String MODEL_LICNSINTERCD = "LICNSINTERCD";
	protected static final String MODEL_SYSTEMTYPE = "SYSTEMTYPE";
	protected static final String MODEL_SYSIDUNIT = "SYSIDUNIT";
	protected static final String MODEL_MODELORDERCODE = "MODELORDERCODE";
	
	protected static final String AVAIL_COUNTRYLIST = "COUNTRYLIST";
	
	protected static final String ANNOUNCEMENT_ANNTYPE = "ANNTYPE";
	protected static final String ANNOUNCEMENT_ANNDATE = "ANNDATE";
	protected static final String ANNOUNCEMENT_PDHDOMAIN = "PDHDOMAIN";
	
	protected static final String GENERALAREA_SLEORG = "SLEORG";
	protected static final String GENERALAREA_GENAREACODE = "GENAREACODE";
	protected static final String GENERALAREA_GENAREANAME = "GENAREANAME";
	protected static final String GENERALAREA_GENAREAPARENT = "GENAREAPARENT";
	protected static final String GENERALAREA_CBSLEGACYPLNTCD = "CBSLEGACYPLNTCD";
	
	protected static final String GBT_SAPPRIMBRANDCD = "SAPPRIMBRANDCD";
	protected static final String GBT_SAPPRODFMLYCD = "SAPPRODFMLYCD";
	
	protected static final String TAXCATG_COUNTRYLIST = "COUNTRYLIST";
	protected static final String TAXCATG_TAXCATGATR = "TAXCATGATR";
	protected static final String MODTAXRELEVANCE_TAXCLS = "TAXCLS";
	
	protected static final String SGMNTACRNYM_DIV = "DIV";
	// Attribute value
	public static final String MACHTYPE_PROMOTED_YES = "PRYES";
	protected static final String PLANNEDAVAIL = "146";
	protected static final String STATUS_PASSED = "0030";

	public static final String LOCAL_REAL_PATH = "./properties/dev";
	
	public static final String STRING_SEPARATOR = "|";
	
	public static final String ROLE_CODE = "BHFEED";
	
	protected static final String RFCABRSTATUS = "RFCABRSTATUS";
	
	
	protected String m_strEpoch = "1980-01-01-00.00.00.000000";
	
	public static String PREANNOUNCE = "YA";
	public static String ANNOUNCE = "Z0";
	/*
	 * Check required attribute for entities when call getAttribute() method
	 */
	private static final String PRIMARY_FC = "100";
	private static final String SECONDARY_FC = "110";
	protected static final Set FCTYPE_SET;
	static{
		FCTYPE_SET = new HashSet();
		FCTYPE_SET.add(PRIMARY_FC);
		FCTYPE_SET.add(SECONDARY_FC);
	}
	
	private static Vector<String> modelRequiredAttrsVct = new Vector<String>();
	private static Vector<String> annRequiredAttrsVct = new Vector<String>();
	private static Hashtable<String, Vector<String>> requiredTypeAttrsTbl = new Hashtable<String, Vector<String>>();
	static {
		modelRequiredAttrsVct.add("MACHTYPEATR");
		modelRequiredAttrsVct.add("MKTGNAME");
		modelRequiredAttrsVct.add("BHPRODHIERCD");
		
		annRequiredAttrsVct.add("ANNNUMBER");
		annRequiredAttrsVct.add("ANNTYPE");
		annRequiredAttrsVct.add("ANNDATE");
		
		requiredTypeAttrsTbl.put("MODEL", modelRequiredAttrsVct);
		requiredTypeAttrsTbl.put("ANNOUNCEMENT", annRequiredAttrsVct);
	}

	protected RdhRestProxy rdhRestProxy;
	protected RFCABRSTATUS abr;
	protected EntityList entityList;
	protected ConfigManager configManager;
	
	public RfcAbrAdapter(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		abr = rfcAbrStatus;
		rdhRestProxy = new RdhRestProxy(new BasicRfcLogger(rfcAbrStatus));
	
		String t2DTS = abr.getCurrentTime();
		Profile profileT2 = abr.switchRole(ROLE_CODE);
		profileT2.setValOnEffOn(t2DTS, t2DTS);
        profileT2.setEndOfDay(t2DTS); 
        profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
        profileT2.setLoginTime(t2DTS); 
		entityList = getEntityList(abr.getDatabase(), profileT2, getVeName(), abr.getEntityType(), abr.getEntityID());
		abr.addDebug("EntityList for " + abr.getProfile().getValOn() + " extract " + getVeName() + " contains the following entities: \n" +
                PokUtils.outputList(entityList));
		
		configManager = ConfigManager.getConfigManager();
		configManager.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, LOCAL_REAL_PATH);
		configManager.addAllConfigFiles();
	}
	
	
	public String getVeName() {
		return "dummy";
	} 
	
	private EntityList getEntityList(Database m_db, Profile prof, String veName, String entityType, int entityId) 
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, veName), 
				new EntityItem[] { new EntityItem(null,	prof, entityType, entityId) });
		return list;
	}
	
	protected EntityList getEntityList(Profile prof) throws MiddlewareRequestException, SQLException, MiddlewareException {
		return getEntityList(abr.getDatabase(), prof, getVeName(), abr.getEntityType(), abr.getEntityID());
	}
	
	protected EntityItem getRooEntityItem() {
		return entityList.getParentEntityGroup().getEntityItem(0);
	}
	
	protected EntityItem[] getEntityItems(String entityType) {
		EntityItem[] entityItems = null;
		EntityGroup entityGroup = entityList.getEntityGroup(entityType);
		if (entityGroup != null) {
			entityItems  = entityGroup.getEntityItemsAsArray();
		}
		return entityItems;
	}
	
	protected EntityItem[] getEntityItems(EntityList entityList, String entityType) {
		EntityItem[] entityItems = null;
		EntityGroup entityGroup = entityList.getEntityGroup(entityType);
		if (entityGroup != null) {
			entityItems  = entityGroup.getEntityItemsAsArray();
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
	 * @param entityItem EntityItem
	 * @param attrCode String attribute code to get value for
	 * @return String attribute flag code
	 * @throws RfcAbrException 
	 */
	protected String getAttributeFlagValue(EntityItem item, String attrCode) throws RfcAbrException {
		String attrValue = PokUtils.getAttributeFlagValue(item, attrCode);
		Vector<String> requiredAttrs = requiredTypeAttrsTbl.get(item.getEntityType());
		if(requiredAttrs != null && requiredAttrs.contains(attrCode)) {
			if(attrValue == null || "".equals(attrValue)) {
				throw new RfcAbrException("For entity:" + item.getKey() + ", " + attrCode + " value can not be empty" );
			}
		}
		if (attrValue == null) {
			attrValue = "";
		}
		return attrValue;
	}
		 
	protected String getAttributeValue(EntityItem item, String attrCode) throws RfcAbrException {
		String attrValue = PokUtils.getAttributeValue(item, attrCode, PokUtils.DELIMITER, "", false);
		Vector<String> requiredAttrs = requiredTypeAttrsTbl.get(item.getEntityType());
		if(requiredAttrs != null && requiredAttrs.contains(attrCode)) {
			if(attrValue == null || "".equals(attrValue)) {
				throw new RfcAbrException("For entity:" + item.getKey() + ", " + attrCode + " value can not be empty" );
			}
		}
		return attrValue;
	}
	
	protected Vector getAttributeMultiValue(EntityItem item, String attrCode) throws RfcAbrException {
		Vector attrValues = new Vector();
		String attrValue = getAttributeValue(item, attrCode);
		if (attrValue != null) {
			StringTokenizer st = new StringTokenizer(attrValue, PokUtils.DELIMITER);
			while(st.hasMoreTokens()){	
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
			while(st.hasMoreTokens()){	
				attrValues.add(st.nextToken());
			}
		}
		return attrValues;
	}
	
	protected void setFlagValue(String strAttributeCode, String strAttributeValue, EntityItem item) throws SQLException, MiddlewareException {
		abr.setFlagValue(strAttributeCode, strAttributeValue, item);
	}
	
	protected AttributeChangeHistoryGroup getAttributeHistory(EntityItem item, String attrCode) throws MiddlewareRequestException {
		EANAttribute att = item.getAttribute(attrCode);
        if (att != null) {
        	return new AttributeChangeHistoryGroup(abr.getDatabase(), abr.getProfile(), att);     
        } else {
            abr.addDebug( attrCode + " of "+item.getKey()+ "  was null");
            return null;
        }	
	}
	
	protected boolean isDiff(EntityItem t1Item, EntityItem t2Item, List<String> attrList) throws RfcAbrException {		
		for (String attr : attrList) {
			String value1 = getAttributeValue(t1Item, attr);
			String value2 = getAttributeValue(t1Item, attr);
			if (!value1.equals(value2)) {
				abr.addDebug(t1Item.getKey() + " Attribute " + attr + " value " + value1 + " is different with " + t2Item.getKey() + " value " + value2);
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
	 * @param attributeCode
	 * @param attributeValue
	 * @return
	 * @throws RfcAbrException 
	 */
	protected String getLatestValFromForAttributeValue(AttributeChangeHistoryGroup history, String attributeValue) throws RfcAbrException {
		String DTS = null;
		String temp = "";
		if(history != null && history.getChangeHistoryItemCount()>= 1){
			int iHistoryItemCount = history.getChangeHistoryItemCount();
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) history.getChangeHistoryItem(i);
				if (achi != null) {
					if(attributeValue.equals(achi.getFlagCode())){
						temp = achi.getChangeDate();
						if(DTS == null){
							DTS = temp;
						}else{
							if (temp.compareTo(DTS) > 0 ) {
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
	
	protected boolean isXccOnlyDiv(String div){
		String xccOnlyDiv = configManager.getString(PropertyKeys.KEY_XCC_ONLY_DIV, true);
		StringTokenizer stoken = new StringTokenizer(xccOnlyDiv,",");
		while(stoken.hasMoreElements()) {
			String xccDiv = stoken.nextToken();
			if((div!=null) && (div.equalsIgnoreCase(xccDiv))){
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
	 * @param t1ItemS
	 * @param t2Item
	 * @return
	 */
	protected EntityItem getEntityItemAtT1(Vector t1Items, EntityItem t2Item) {
		for (int i = 0; i < t1Items.size(); i++) {
			EntityItem item = (EntityItem)t1Items.get(i);
			if (item.getKey().equals(t2Item.getKey())) {
				return item;
			}
		}
		return null;
	}
	
	protected void updateAnnLifecyle(String varCond, String material,
			Date annDate, String annDocNo, String announcement,
			String pimsIdentity, String salesOrg) throws Exception {

		// Note from from an error restart standpoint, this function will be
		// treated as one transaction. Since it uses r200 that queries
		// SAP it will not run the same number of RFCs each time. Care must be
		// made to ensure no null pointer are encountered. And that
		// this routine will work if rerun if it fails at any point.

		// r200, r199, r198, and r197 have been updated to not increment the
		// trans counter

		// Increment the transaction counter once to represent this function.
//		hwpimsErrorInformation.incrTransactionCounter();

		LifecycleData lcd;

		Date today = new Date();

		if (!announcement.equals("MIG")) {
			// Checked with Rupal, Please use OPENID from the PDH that represents user id in EACM. OPENID is entityid for OPWG. OPWG.NAME. I am not sure what is it used for but we can use that.
			String user = getOpwgId();
//			try {
				lcd = rdhRestProxy.r200(material, varCond, annDocNo, "ann", pimsIdentity, salesOrg);
				abr.addDebug("Call R200 successfully");
//			} catch (HWPIMSAbnormalException e) {
//				lcd = new LifecycleData();
//			}

			if (lcd.getPreAnnounceValidFrom() == null && lcd.getAnnounceValidFrom() == null && lcd.getWdfmValidFrom() == null) {
				// New object				
				if (DateUtility.isAfterToday(annDate)) {
					rdhRestProxy.r197(material, varCond, PREANNOUNCE, today, DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
					abr.addDebug("Call R197 successfully for PREANNOUNCE");
					rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(), user, annDocNo, "ann", pimsIdentity, salesOrg);
					abr.addDebug("Call R197 successfully for ANNOUNCE");
				} else { //  today or before
					rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(), user, annDocNo, "ann", pimsIdentity, salesOrg);
					abr.addDebug("Call R197 successfully for ANNOUNCE");
				}
			} else { // Already exists
				// Note that this does handle rare case that pre-ann is not null
				// but ann is (maybe if withdrawn on
				// same day and unwithdrawn or error in this function
				if (lcd.getPreAnnounceValidFrom() != null && (lcd.getAnnounceValidFrom() == null || DateUtility.isSameDay(annDate, lcd.getAnnounceValidFrom()) == false)) {
					if (DateUtility.dateIsTodayOrBefore(annDate, lcd.getPreAnnounceValidFrom())) { // AnnDate <= PreAnn Valid From
						rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R199 successfully for PREANNOUNCE");
					} else {
						// Update PreAnn status valid to date to AnnDate
						rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R199 successfully for PREANNOUNCE");
						rdhRestProxy.r197(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidFrom(), DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R197 successfully for PREANNOUNCE");
					}
				}
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
						rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, annValidTo, user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R197 successfully for ANNOUNCE");
					} else {
						rdhRestProxy.r198(material, varCond, ANNOUNCE, annDate, lcd.getAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R198 successfully for ANNOUNCE");
					}
					if (DateUtility.isAfterToday(annDate) && lcd.getPreAnnounceValidFrom() == null) {
						rdhRestProxy.r197(material, varCond, PREANNOUNCE, today, DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
						abr.addDebug("Call R197 successfully for PREANNOUNCE");
					}
				}
			}
		}
	}
	
	protected List<SalesOrgPlants> getAllSalesorgPlants(Vector generalareaVct) throws RfcAbrException {
		List<SalesOrgPlants> salesorgPlantsVect = new ArrayList<>();			
		for (int i = 0; i < generalareaVct.size(); i++) {
			EntityItem generalarea = (EntityItem)generalareaVct.get(i);
			String salesOrg = getAttributeValue(generalarea, GENERALAREA_SLEORG);
			if (salesOrg != null && !"".equals(salesOrg)) {
				SalesOrgPlants salesorgPlants = new SalesOrgPlants();
				salesorgPlants.setGenAreaCode(getAttributeValue(generalarea, GENERALAREA_GENAREACODE));
				salesorgPlants.setGenAreaName(getAttributeFlagValue(generalarea, GENERALAREA_GENAREANAME));
				salesorgPlants.setGeo(getAttributeValue(generalarea, GENERALAREA_GENAREAPARENT)); // 
				salesorgPlants.setSalesorg(salesOrg);
				salesorgPlants.setPlants(getAttributeMultiFlagValue(generalarea, GENERALAREA_CBSLEGACYPLNTCD));
				salesorgPlantsVect.add(salesorgPlants);
			} else {
				abr.addDebug("SalesOrg value is null for " + generalarea.getKey());
			}		
		}
		return salesorgPlantsVect;		
	}
	
	protected Set<String> getAllPlants(List<SalesOrgPlants> salesorgPlantsVect) {
		Set<String> plants = new HashSet<>();			
		for (SalesOrgPlants salesorgPlants : salesorgPlantsVect) {
			Vector<String> tmpPlants = salesorgPlants.getPlants();			
			for (String plant : tmpPlants) {
				plants.add(plant);
			}
			if (tmpPlants.size() == 0) {
				abr.addDebug("No plant found for country code： " + salesorgPlants.getGenAreaCode());
			}				
		}			
		return plants;		
	}
	
//	protected void updateMtcBomType(String mtcBomType, CHWAnnouncement announcement, String pimsIdentity) {
//		if (mtcBomType != null && !"".equals(mtcBomType)) {
//			String mtctype = mtcBomType;
//			abr.addDebug("Value of Type  " + mtctype);
//			boolean typeMTCExists = rdhRestProxy.r204(mtctype + "MTC");
//			if (typeMTCExists) {
//				abr.addDebug("Value of the Type ******" + mtctype);
//				List<String> plantList = new ArrayList<>();
//				plantList.add("1222");
//				plantList.add("1999");
//				for (int p = 0; p < plantList.size(); p++) {
//					String _plant = plantList.get(p);
//					Vector typeNEW = rdhRestProxy.r210(mtctype, "NEW", _plant);// null handle suchit
//					if (typeNEW == null) {
//						continue;
//					}
//					abr.addDebug("Reading Sales Bom for TypeNEW: " + typeNEW.size());
//					DepData res_Vector = new DepData();
//					for (int i = 0; i < typeNEW.size(); i++) {
//						res_Vector = (DepData) typeNEW.elementAt(i);
//					}// end for
//					Vector typeMTC = rdhRestProxy.r210(mtctype, "MTC", _plant);
//					if (typeMTC != null) {
//						abr.addDebug("Reading Sales Bom for TypeMTC: " + typeMTC.size());
//						for (int i = 0; i < typeMTC.size(); i++) {
//							res_Vector = (DepData) typeMTC.elementAt(i);
//						}// end for
//					}// end if
//					if (typeMTC == null) {
//						rdhRestProxy.r211(mtctype, _plant, typeNEW, "MTC", announcement, pimsIdentity);
//					}// end if
//					else {
//						Vector componentstoDeleteTypeMTC = getComponentsintypeMTCwithtypeNEW(typeMTC, typeNEW);
//						if (componentstoDeleteTypeMTC != null) {
//							DepData res_Vector1 = new DepData();
//							for (int i = 0; i < componentstoDeleteTypeMTC.size(); i++) {
//								res_Vector1 = (DepData) componentstoDeleteTypeMTC.elementAt(i);
//							}// end for
//						}// end else
//
//						if (componentstoDeleteTypeMTC != null) {
//							rdhRestProxy.r212(mtctype, _plant, componentstoDeleteTypeMTC, "MTC", announcement, pimsIdentity);
//						}// end if
//
//						Vector updatecomponentsforTypeMTC = getComponentsintypeNEW(typeMTC, typeNEW);
//
//						if (updatecomponentsforTypeMTC != null) {
//							rdhRestProxy.r213(mtctype, _plant, updatecomponentsforTypeMTC, "MTC", announcement, pimsIdentity);
//						} // end if
//					}// end else
//				}
//			}
//		}
//	}
	
	/**
	 * Re: Question for RFC call r117 05/06/2016 08:48 PM
	 * Here's the mapping we can use PDHDOMAIN for this.
		This field stores the name of the business unit where the  revenue will be allocated if the product is sold. Select the appropriate value from the list.
		    Examples: 
		" AS4 i-Series
		" RS6 p-Series
		" LSC z-Series
		    This field is sent to MMLC.
	 * @param ann
	 * @return
	 * @throws RfcAbrException 
	 */
	protected String getSegmentAcronymForAnn(EntityItem ann) throws RfcAbrException {
		String segmentAcronym = "";
		String domain = getAttributeValue(ann, ANNOUNCEMENT_PDHDOMAIN);
		if ("iSeries".equals(domain)) {
			segmentAcronym = "AS4";
		} else if ("pSeries".equals(domain)) {
			segmentAcronym = "RS6";
		} else if ("zSeries".equals(domain)) {
			segmentAcronym = "LSC";
		}
		return segmentAcronym;
	}
	
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
			abr.addDebug("vector typeMTC component value"
					+ dDataNEW.getComponent());
			vect1.addElement(newtypecomponet);
		}

		// for (int i=0;i<=typeModelDeleted.size();i++)
		// {
		while (dapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) dapiter.nextElement();
			String component = dDataMTC.getComponent();
			abr.addDebug("vector typeNEW component value"
					+ dDataMTC.getComponent());
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
	
	private Vector getComponentsintypeNEW(Vector typeModelDeleted,
			Vector hashtypemodelbom) {

		Vector updatemtc = new Vector();
		Vector vect1 = new Vector();
		Enumeration sapiter = typeModelDeleted.elements();
		Enumeration dapiter = hashtypemodelbom.elements();
		// for (int k=0;k<=typeModelDeleted.size();k++){
		while (sapiter.hasMoreElements()) {
			DepData dDataMTC = (DepData) sapiter.nextElement();
			String newtypecomponet = dDataMTC.getComponent();
			vect1.addElement(newtypecomponet);
			abr.addDebug("vector typeMTC component value"
					+ dDataMTC.getComponent());
		}
		abr.addDebug("vector size of vect1" + vect1.size());

		while (dapiter.hasMoreElements()) {
			DepData dDataNEW = (DepData) dapiter.nextElement();
			String component = dDataNEW.getComponent();
			abr.addDebug("vector typeNEW component value"
					+ dDataNEW.getComponent());
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
}
