/**
 * (c) Copyright International Business Machines Corporation 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.ChangeHistoryItem;
import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.MetaColumnOrderGroup;
import COM.ibm.eannounce.objects.MetaColumnOrderItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
import COM.ibm.eannounce.objects.MetaTextAttribute;
import COM.ibm.eannounce.objects.SingleFlagAttribute;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.eannounce.objects.WorkflowActionItem;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

/**
 * Frequently only a subset of the entity groups in a VE is needed in the ABR report 
 * and the same goes for the entities in a group. 
 * This class will instanciate a VE and keep track of the groups and entities needed in the report.
 * If the VE name is not set it will create an EntityList, EntityGroup, and EntityItem for the entity type,entity ID
 * @author cstolpe
 *
 */
public class DataView implements Log, Init, EntityParam, PDHAccess {
	private Set entityGroupSubset = new HashSet();
	private int entityID;
	private Map entityKeySubset = new HashMap();
	private String entityType = null;
	private EntityItem ei = null;
	private Database pdh = null;
	private Profile profile = null;
	private EntityList ve = null;
	private String veName = "Entity";
	private boolean useVE = false;
	private String attributeCodeOfABR = "Not Specified";
	private Logger log = new Logger();
	private String delimiter = ";";
	
	/**
	 * This constructor gets the EntityList. 
	 */
	public DataView() {
	}

	/**
     * Adds the EntityItem.getKey() to the subset
     *
     * @return true if param is not null
     * @param anItem 
     */
	public boolean addEntityItemToSubset(EntityItem anItem) {
		boolean result = anItem != null;
		if (result) {
			entityKeySubset.put(anItem.getKey(), anItem.getEntityType());
		}
		return result;
	}
	
	/**
	 * Puts the EntityGroup specified by the string type
	 * @param type
	 * @return the EntityGroup instance
	 */
	public EntityGroup addGroupToData(String type) {
		EntityGroup eg = getEntityGroup(type);
		if (entityType.equals(type)) {
			eg = getParentEntityGroup();
		}
		if (type != null && eg != null) {
			entityGroupSubset.add(type);
			setOrder(eg);
		}
		return eg;
	}
	/********************************************************************************
     * Delete unique flag attribute
     *
     * @param anItem EntityItem to delete attribute from
     * @param ma EANMetaAttribute with flag attribute
     * @return boolean
     */
	public boolean deactivateUniqueFlag(EntityItem anItem, EANMetaAttribute ma) {
		final String signature = ".deactivateUniqueFlag(EntityItem,EANMetaAttribute): ";
		boolean result = false;
		String attrCode = ma.getAttributeCode();
		EANAttribute curValue = anItem.getAttribute(attrCode);
	
		if (curValue != null) {
			//mfParms[0] = ma.getLongDescription();
			//mfParms[1] = "null";
			//MessageFormat msgf = new MessageFormat(bundle==null?ALREADYSET_MSG:bundle.getString("ALREADYSET_MSG"));
			//rptSb.append(msgf.format(mfParms)+NEWLINE);
	
			// delete this attribute
			try {
				anItem.put(anItem.getEntityType() + ":" + attrCode, null);
				anItem.commit(pdh, null);	
				result = true;
			} catch (EANBusinessRuleException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareBusinessRuleException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (RemoteException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareRequestException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareShutdownInProgressException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (SQLException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			}
		}
		else {
			result = true;
		}
		return result;
	}
	
	/**
	 * Implements the dereference method from the Init interface
	 * Dereference the VE EntityList
	 * @return true if it was able to dereference the entity list
	 */
	public boolean dereference() {
		boolean result = false;
		if (ve != null) {
			ve.dereference();
			result = true;
		}
		return result;
	}

	/**
	 * Gets the navigate display name for the ABR
	 * If the abrCode is part of the  navigate attributes
	 * hassPassed is used instead of the attribute
     * @param hasPassed the result of the ABR
     * @return String representation of display name
     */
    public String getDisplayName(boolean hasPassed) {
		final String signature = ".setDisplayName(boolean): ";
		StringBuffer displayName = new StringBuffer();
		// NAME is navigate attributes
		try {
			EntityGroup eg = new EntityGroup(null, pdh, profile, entityType, "Navigate");
			EntityItem eiCurrent = new EntityItem(eg,profile,pdh,entityType, entityID);
			MetaColumnOrderGroup mcog = eg.getMetaColumnOrderGroup(pdh, null);
			int nOrder = mcog.getMetaColumnOrderItemCount();
			for (int i = 0; i < nOrder; i++) {
				MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(i);
				String attCode = mcoi.getAttributeCode();
				if (eg.getMetaAttribute(attCode) != null) {
					if (attCode.equals(attributeCodeOfABR)) {
						// It's actual value is in proccess or queued
						displayName.append((hasPassed) ? "Passed" : "Failed"); //$NON-NLS-1$  //$NON-NLS-2$
					} 
					else {
						EANAttribute navAtt = eiCurrent.getAttribute(attCode);
						if (navAtt != null) {
							Object o = navAtt.get();
							if (o instanceof MetaFlag[]) {
								MetaFlag[] mfa = (MetaFlag[]) o;
								int nSelected = 0;
								for (int j = 0; j < mfa.length; j++) {
									if (mfa[j].isSelected()) {
										nSelected++;
										if (nSelected > 1) {
											displayName.append(",");
										}
										displayName.append(
											mfa[j].getLongDescription());
									}
								}
							} 
							else {
								displayName.append(o);
							}
						} 
						else {
							log.print(getClass().getName());
							log.print(signature);
							log.print(attCode);
							log.println(" Not Populated");
							displayName.append("???");
						}
					}
					displayName.append(" "); //$NON-NLS-1$
				}
			}
			eg = new EntityGroup(null, pdh, profile, entityType, "Edit");
			displayName.append(eg.getMetaAttribute(attributeCodeOfABR).getActualLongDescription());
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getMessage());
		} catch (RemoteException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getMessage());
		} catch (MiddlewareShutdownInProgressException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getMessage());
		}
		return displayName.toString();
	}
	/**
	 * A convenience methos
     * @param rc
     * @return
     */
    public String getDisplayName(ReturnCode rc) {
		return getDisplayName(rc.hasPassed());
	}

	/**
	 * Gets the requested entity type from the VE.
	 * If it is not in the ve it will log a message and return null
	 * @param type
	 * @return EntityGroup instance
	 */
	public EntityGroup getEntityGroup(String type) {
		final String signature = ".getEntityGroup(String): ";
		EntityGroup eg = null;
		if (type != null && ve != null) {
			eg = ve.getEntityGroup(type);
			if (eg == null) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(type);
				log.print(" is missing from the ve ");
				log.println(veName);
			}
		}
		return eg;
	}
	
	/**
	 * Returns all or a subset of the entity item keys for a specific entity type.
	 * It will return an empty set if the addGroupToData method was not called for this type
	 * @param type
	 * @return a set of keys
	 */
	public Set getEntityItemKeys(String type) {
		Set result = new HashSet();
		if (type != null) {
			EntityGroup eg = getEntityGroup(type);
			if (entityType.equals(type)) {
				eg = getParentEntityGroup();
			}
			if (eg != null) {
				// Default to all keys contained in the group
				result.addAll(eg.getEntityItem().keySet());
				if (entityKeySubset.containsValue(type)) {
					result.retainAll(entityKeySubset.keySet());
				}
			}
		}
		return result;
	}
	/**
	 * Gets the set of entity groups available to the report
     * @return Set of String entity type names
     */
    public Set getEntityGroups() {
		return entityGroupSubset;
	}
	/**
	 * Get the entity type the VE was executed on
     * @return String entity type
     */
    public String getEntityType() {
		return entityType;
	}

	/**
	 * Gets the parent entity group  from the VE.
	 * @return EntityGroup instance
	 */
	public EntityGroup getParentEntityGroup() {
		final String signature = ".getParentEntityGroup(): ";
		EntityGroup eg = null;
		if (ve != null) {
			if (useVE) {
				eg = ve.getParentEntityGroup();
			}
			else {
				eg = ve.getActiveEntityGroup();
			}
			if (eg == null) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(entityType);
				log.print(" is missing from the ve ");
				log.println(veName);
			}
		}
		return eg;
	}
	/**
	 * Getter method for the profile field
	 * @return
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * Getter method for the VE name
	 * @return
	 */
	public String getVEName() {
		return veName;
	}
	/**
     * Setter method for the VE name
     *
     * @return true if not null
     * @param name 
     */
	public boolean setVEName(String name) {
		veName = name;
		useVE = true;
		return veName != null;
	}
	
	/**
	 * Getter method for the CSV Delimiter
	 * @return
	 */
	public String getDelimiter() {
		return delimiter;
	}
	/**
     * Setter method for the VE name
     * used for csv generation - RCQ00337768-RQ Report for Price Calc limits to 65K
     * @return true if not null
     * @param delim 
     */
	public void setDelimiter(String delim) {
		if(delim !=null && delim.trim().length()>0){
			delimiter = delim.trim();
		}
	}
	/**
	 * Implements setter method from the Init interface
	 * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
	 */
	public boolean initialize() {
		final String signature = ".initialize(): ";
		boolean result = false;
		try {
			if (useVE) {
				EntityItem[] eiParm = null;
				ExtractActionItem eaItem = null;
				
				ei = new EntityItem(null, profile, entityType, entityID);
				eiParm = new EntityItem[] { ei };
				eaItem = new ExtractActionItem(null, pdh, profile, veName);
				ve = pdh.getEntityList(profile, eaItem, eiParm);
				ei = ve.getParentEntityGroup().getEntityItem(0);
			}
			else {
				EntityGroup eg = new EntityGroup(null, pdh, profile, entityType, "Edit");
				ve = new EntityList(pdh,profile);
				ei = new EntityItem(eg, profile, pdh, entityType, entityID);
				eg.putEntityItem(ei);
				ve.putEntityGroup(eg);
				ve.setActiveEntityGroup(eg);
			}
			result = true;
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		return result;
	}
	/**
     * Implements setter method from the Init interface
     *
     * @param database 
     * @return true if database is not null
     */
	public boolean setDatabase(Database database) {
		pdh = database;
		return pdh != null;
	}

	/**
	 * Implements setter method from EntityParam interface
	 * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
	 */
	public boolean setEntityID(int aID) {
		entityID = aID;
		return entityID > -1;
	}

	/**
     * Implements setter method from EntityParam interface
     *
     * @param aType String EntityType
     * @return true if entityType is not null
     */
	public boolean setEntityType(String aType) {
		entityType = aType;
		return entityType != null;
	}
	/**
	 * Set the attributeCode to the FlagCode
     * @param attributeCode
     * @param strFlagCode
     * @return true if successful
     */
    public boolean setFlagByCode(String attributeCode, String strFlagCode) {
		final String signature = ".setFlagByCode(EntityItem,String,String): ";
		boolean set = false;
		try {
			EntityGroup eg = getParentEntityGroup();
			EANMetaAttribute mAttr = eg.getMetaAttribute(attributeCode);
			
			if (mAttr != null) {
				if (mAttr instanceof MetaSingleFlagAttribute) {
					EANFlagAttribute efa =
						new SingleFlagAttribute(
							ei,
							profile,
							(MetaSingleFlagAttribute) mAttr);
					MetaFlag[] mfa = (MetaFlag[]) efa.get();
					for (int i = 0; i < mfa.length; i++) {
						if (mfa[i].getFlagCode().equals(strFlagCode)) {
							set = true;
							mfa[i].setSelected(true);
						}
					}
					if (set) {
						efa.put(mfa);
						ei.putAttribute(efa);
						ei.commit(pdh,null);
					}
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.print(attributeCode);
					log.println(" is not a MetaSingleFlagAttribute");
				}
			}
			else {
				log.print(getClass().getName());
				log.print(signature);
				log.print("Meta attribute for ");
				log.print(attributeCode);
				log.println(" was not found");
			}
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (EANBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (RemoteException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareShutdownInProgressException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		return set;
	}
	
	/**
	 * Get the prior flag code from the change history
     * @param strAttributeCode
     * @return
     */
    public String getPriorFlagCode(String strAttributeCode) {
		final String signature = ".getPriorFlagCode(String): ";
		String result = null;
		if (strAttributeCode != null) {
			EANAttribute att = ei.getAttribute(strAttributeCode);
			if (att != null) {
				try {
					AttributeChangeHistoryGroup history = att.getChangeHistoryGroup(pdh);
					if (history != null && history.getChangeHistoryItemCount() > 1) {
						int nChanges = history.getChangeHistoryItemCount();
						nChanges -= 2; 
						ChangeHistoryItem chi = history.getChangeHistoryItem(nChanges);
						result = chi.getFlagCode();
					}
				} catch (MiddlewareRequestException e) {
					log.print(getClass().getName());
					log.print(signature);
					log.println(e.getMessage());
				} catch (SQLException e) {
					log.print(getClass().getName());
					log.print(signature);
					log.println(e.getMessage());
				} catch (MiddlewareException e) {
					log.print(getClass().getName());
					log.print(signature);
					log.println(e.getMessage());
				}
			}
		}
		return result;
	}
	/**
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     * @return boolean
     */
	public boolean setFlagValue(
		String strAttributeCode,
		String strAttributeValue) {
		boolean result = false;
		if (strAttributeValue != null && strAttributeCode != null) {
			try {
				EntityItem eiParm =
					new EntityItem(null, profile, entityType, entityID);
				ReturnEntityKey rek =
					new ReturnEntityKey(
						eiParm.getEntityType(),
						eiParm.getEntityID(),
						true);
				DatePackage date = pdh.getDates();
				ControlBlock cbOn =
					new ControlBlock(
						date.getNow(),
						date.getForever(),
						date.getNow(),
						date.getForever(),
						profile.getOPWGID(),
						profile.getTranID());
				SingleFlag sf =
					new SingleFlag(
						profile.getEnterprise(),
						rek.getEntityType(),
						rek.getEntityID(),
						strAttributeCode,
						strAttributeValue,
						1,
						cbOn);
				Vector vctAtts = new Vector();
				Vector vctReturnsEntityKeys = new Vector();

				if (sf != null) {
					vctAtts.addElement(sf);

					rek.m_vctAttributes = vctAtts;
					vctReturnsEntityKeys.addElement(rek);

					pdh.update(profile, vctReturnsEntityKeys, false, false);
					pdh.commit();
					result = true;
				}
			} catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
				log.print(getClass().getName());
				log.print(".setFlagValue: ");
				log.println(e.getMessage());
			} catch (Exception e) {
				log.print(getClass().getName());
				log.print(".setFlagValue: ");
				log.println(e.getMessage());
			}
		}
		return result;
	}
	/**
	 * Sets the order of the attributes based on the meta
	 * @param grp
	 * @return EntityGroup parameter
	 */
	private EntityGroup setOrder(EntityGroup grp) {
		final String signature = ".setOrder(EntityGroup): ";
		MetaColumnOrderGroup mcog = grp.getMetaColumnOrderGroup();
		if (mcog == null && pdh != null) {
			try {
				mcog = grp.getMetaColumnOrderGroup(pdh, null);
			} catch (RemoteException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (MiddlewareShutdownInProgressException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			} catch (SQLException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			}
		}
		return grp;
	}
	/**
     * Implements setter method from the Init interface
     *
     * @param prof
     * @return boolean true if profile is not null
     */
	public boolean setProfile(Profile prof) {
		profile = prof;
		return profile != null;
	}
	/**
	 * Set attributeCode to the value
     * @param attributeCode
     * @param value
     * @return true if successful
     */
    public boolean setText(String attributeCode, String value) {
		final String signature = ".setText(EntityItem,String,String): ";
		boolean set = false;
		try {
			EANMetaAttribute mAttr =
				ei.getEntityGroup().getMetaAttribute(attributeCode);
			if (mAttr != null && mAttr instanceof MetaTextAttribute) {
				EANTextAttribute tAttr =
					new TextAttribute(ei, profile, (MetaTextAttribute) mAttr);
				tAttr.put(value);
				ei.putAttribute(tAttr);
				set = true;
			} 
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (EANBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		return set;
	}
	/********************************************************************************
     * Set text attribute value the old way, bypass all business rule checks
     * Not following business rules or process when updating, some required attributes are not set!
     *
     * @return boolean
     * @param attrCode
     * @param attrValue 
     */
	public boolean setTextDirectly(String attrCode, String attrValue) {
		final String signature = ".setTextDirectly(String,String): ";
		boolean result = false;
		try {
			ReturnEntityKey rek = new ReturnEntityKey(entityType, entityID, true);
			DatePackage dbNow = pdh.getDates();
			String strNow = dbNow.getNow();
			String strForever = dbNow.getForever();
			ControlBlock cbOn =
				new ControlBlock(
					strNow,
					strForever,
					strNow,
					strForever,
					profile.getOPWGID(),
					profile.getTranID());
			Text sf =
				new Text(
					profile.getEnterprise(),
					rek.getEntityType(),
					rek.getEntityID(),
					attrCode,
					attrValue,
					1,
					cbOn);
			Vector vctAtts = new Vector();
			Vector vctReturnsEntityKeys = new Vector();
			//EntityGroup eg;
			//EANMetaAttribute mAttr;

			vctAtts.addElement(sf);
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);
			pdh.update(profile, vctReturnsEntityKeys, false, false);
			pdh.commit();

			//eg = ve.getParentEntityGroup();
			//mAttr = eg.getMetaAttribute(attrCode);
			result = true;
		} catch (java.sql.SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} finally {
			pdh.freeStatement();
			pdh.isPending("finally after update in Text value");
		}
		return result;
	}
	/********************************************************************************
     * Create and/or set unique flag attribute to specified flag code
     *
     * @param anItem EntityItem to add/set attribute to
     * @param ma EANMetaAttribute with flag attribute
     * @param flagCode String with value
     * @return boolean
     */
	public boolean setUniqueFlag(EntityItem anItem, EANMetaAttribute ma, String flagCode) {
		final String signature = ".setUniqueFlag: ";
		boolean result = false;
		String attrCode = ma.getAttributeCode();
		EANAttribute curValue = anItem.getAttribute(attrCode);
		try {
			if (curValue != null && curValue instanceof EANFlagAttribute) {
				EANFlagAttribute faCurrentValue = (EANFlagAttribute) curValue;
				if (!faCurrentValue.isSelected(flagCode)) {
					EANFlagAttribute faNewValue =
							new SingleFlagAttribute(
								anItem,
								profile,
								(MetaSingleFlagAttribute) ma);
					anItem.putAttribute(faNewValue);
					anItem.commit(pdh, null);
					result = true;
				}
			}
			else {
				EANFlagAttribute faNewValue =
						new SingleFlagAttribute(
							anItem,
							profile,
							(MetaSingleFlagAttribute) ma);
				anItem.putAttribute(faNewValue);
				anItem.commit(pdh, null);
				result = true;
			}
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (RemoteException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (EANBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareShutdownInProgressException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
	
		return result;
	}
	/**
	 * Invokes the specified CreateAction
     * @param action
     * @return EntityItem created
     */
    public EntityItem triggerCreateAction(String action) {
		final String signature = ".triggerCreateAction(String): ";
		EntityItem eiDD = null;

		try {
			CreateActionItem cai = new CreateActionItem(null, pdh, profile, action);
			EntityGroup eg = ve.getEntityGroup(entityType);
			EntityItem anItem = eg.getEntityItem(0);
			EntityItem[] aItems = new EntityItem[] { eg.getEntityItem(0) };
			EntityList elDD = new EntityList(pdh, profile, cai, aItems);

			EntityGroup egDD = null;
			
			egDD = elDD.getEntityGroup("DD"); //$NON-NLS-1$
			if (egDD.getEntityItemCount() == 1) {
				eiDD = egDD.getEntityItem(0);
				if (eiDD.hasChanges()) {
					EntityItem eiXXXDD;
					eiDD.commit(pdh, null);
					// Commit the relator too
					eiXXXDD = (EntityItem) anItem.getUpLink(0);
					eiXXXDD.commit(pdh, null);
				}
			} 
			else {
				log.print(getClass().getName());
				log.print(signature);
				log.print("unable to create DD for ");
				log.println(anItem.getKey());
			}
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (RemoteException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (EANBusinessRuleException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareShutdownInProgressException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		return eiDD;
	}
	/**
	 *  Triggers the specified workflow
	 *
	 * @param actionName Name of the workflow action.
	 * @return true if successful
	 */
	public boolean triggerWorkFlow(String actionName) {
		final String signature = ".triggerWorkFlow(String): ";
		boolean result = false;
		EntityGroup eg = ve.getParentEntityGroup();
		EntityItem[] aItems = new EntityItem[1];
		WorkflowActionItem wfai;
		try {
			wfai = new WorkflowActionItem(null, pdh, profile, actionName);
			aItems[0] = eg.getEntityItem(0);
			wfai.setEntityItems(aItems);
			pdh.executeAction(profile, wfai);
			result = true;
		} catch (MiddlewareRequestException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (WorkflowException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		} catch (MiddlewareShutdownInProgressException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		return result;
	}

	/**
     * Implements log interface
     *
     * @param anIdentifyer
     * @return boolean true if anIdentifier is not null
     */
	public boolean setIdentifier(String anIdentifyer) {
		return log.setIdentifier(anIdentifyer);
	}

	/**
	 * Getter method for field ABR attribute code
	 * @return String attribute code
	 */
	public String getAttributeCodeOfABR() {
		return attributeCodeOfABR;
	}

	/**
     * Setter method for field ABR attribute code
     *
     * @param string 
     */
	public void setAttributeCodeOfABR(String string) {
		attributeCodeOfABR = string;
	}

	/**
	 * Getter method of LOG INTERFACE
	 * @return String identifyer
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}

}
