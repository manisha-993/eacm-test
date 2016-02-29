package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.SingleFlag;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.rfc.proxy.RdhRestProxy;
import com.ibm.transform.oim.eacm.util.PokUtils;

public abstract class RfcAbrAdapter implements RfcAbr {
	
	public static final String LOCAL_REAL_PATH = "./properties/dev";
	
	public static final String MACHTYPE_PROMOTED = "";
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
	
	protected void setFlagValue(String strAttributeCode, String strAttributeValue, EntityItem item) {
		abr.setFlagValue(strAttributeCode, strAttributeValue, item);
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
}
