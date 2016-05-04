package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.DateUtility;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.DepData;
import com.ibm.rdh.chw.entity.LifecycleData;
import com.ibm.rdh.rfc.proxy.RdhRestProxy;
import com.ibm.transform.oim.eacm.util.PokUtils;

public abstract class RfcAbrAdapter implements RfcAbr {
	
	public static final String LOCAL_REAL_PATH = "./properties/dev";
	
	public static final String MACHTYPE_PROMOTED = "PRYES";
	
	protected static final String STATUS_PASSED = "0030";
	protected static final String RFCABRSTATUS = "RFCABRSTATUS";
	
	protected String m_strEpoch = "1980-01-01-00.00.00.000000";
	
	public static String PREANNOUNCE = "YA";
	public static String ANNOUNCE = "Z0";
	/*
	 * Check required attribute for entities when call getAttribute() method
	 */
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
	
	public RfcAbrAdapter(RFCABRSTATUS rfcAbrStatus) throws MiddlewareRequestException, SQLException, MiddlewareException {
		abr = rfcAbrStatus;
		rdhRestProxy = new RdhRestProxy(new BasicRfcLogger(rfcAbrStatus));
	
		entityList = getEntityList(abr.getDatabase(), abr.getProfile(), getVeName(), abr.getEntityType(), abr.getEntityID());
		abr.addDebug("EntityList for " + abr.getProfile().getValOn() + " extract " + getVeName() + " contains the following entities: \n" +
                PokUtils.outputList(entityList));
		
		configManager = ConfigManager.getConfigManager();
		configManager.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, LOCAL_REAL_PATH);
		configManager.addAllConfigFiles();
	}
	
	
	public String getVeName() {
		return "RFCABRSTATUS";
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
	
	protected Vector getMultiAttributeValue(EntityItem item, String attrCode) throws RfcAbrException {
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
	
	protected void setFlagValue(String strAttributeCode, String strAttributeValue, EntityItem item) throws SQLException, MiddlewareException {
		abr.setFlagValue(strAttributeCode, strAttributeValue, item);
	}
	
	protected boolean isDiff(EntityItem t1Item, EntityItem t2Item, Vector<String> attrList) throws RfcAbrException {
		if (t1Item == null && t2Item != null) {
			return true;
		}
		if (attrList != null && attrList.size() > 0) {
			for (String attr : attrList) {
				if (!getAttributeValue(t1Item, attr).equals(getAttributeValue(t1Item, attr))) {
					return true;
				}
			}
		}		
		return false;
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
			
			lcd = rdhRestProxy.r200(material, varCond, annDocNo, "ann", pimsIdentity, salesOrg);

			if (lcd.getPreAnnounceValidFrom() == null && lcd.getAnnounceValidFrom() == null && lcd.getWdfmValidFrom() == null) {
				// New object
				if (DateUtility.isAfterToday(annDate)) {
					rdhRestProxy.r197(material, varCond, PREANNOUNCE, today, DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
					rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(), user, annDocNo, "ann", pimsIdentity, salesOrg);
				} else { //  today or before
					rdhRestProxy.r197(material, varCond, ANNOUNCE, annDate, DateUtility.getInfinityDate(), user, annDocNo, "ann", pimsIdentity, salesOrg);
				}
			} else { // Already exists
				// Note that this does handle rare case that pre-ann is not null
				// but ann is (maybe if withdrawn on
				// same day and unwithdrawn or error in this function
				if (lcd.getPreAnnounceValidFrom() != null && (lcd.getAnnounceValidFrom() == null || DateUtility.isSameDay(annDate, lcd.getAnnounceValidFrom()) == false)) {
					if (DateUtility.dateIsTodayOrBefore(annDate, lcd.getPreAnnounceValidFrom())) { // AnnDate <= PreAnn Valid From
						rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
					} else {
						// Update PreAnn status valid to date to AnnDate
						rdhRestProxy.r199(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
						rdhRestProxy.r197(material, varCond, PREANNOUNCE, lcd.getPreAnnounceValidFrom(), DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
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
					} else {
						rdhRestProxy.r198(material, varCond, ANNOUNCE, annDate, lcd.getAnnounceValidTo(), user, annDocNo, "ann", pimsIdentity, salesOrg);
					}
					if (DateUtility.isAfterToday(annDate) && lcd.getPreAnnounceValidFrom() == null) {
						rdhRestProxy.r197(material, varCond, PREANNOUNCE, today, DateUtility.getDateMinusOne(annDate), user, annDocNo, "ann", pimsIdentity, salesOrg);
					}
				}
			}
		}
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
	
	private Vector getComponentsintypeMTCwithtypeNEW(Vector typeModelDeleted,
			Vector hashtypemodelbom) {

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
