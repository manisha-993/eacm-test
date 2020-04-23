// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.translation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.objects.ControlBlock;

/**
 * EntityHandler is a convenience class to make easier to collect and handle
 * entities. It uses direct GBL calls and don't require EntityItem instances.
 * 
 * @author lucasrg
 */
public class EntityHandler {

	private Database database;

	private Profile profile;

	private ControlBlock controlBlock;

	private String entityType;

	private int entityID;
	
	private int nlsID;
	
	public EntityHandler(Database database, Profile profile,
			ControlBlock controlBlock, String entityType, int entityID, int nlsID) {
		this.database = database;
		this.profile = profile;
		this.controlBlock = controlBlock;
		this.entityType = entityType;
		this.entityID = entityID;
		this.nlsID = nlsID;
	}

	public int getEntityID() {
		return entityID;
	}

	public String getEntityType() {
		return entityType;
	}

	/**
	 * Convenience method to call getAttributes by creating a map 
	 * from an array of strings
	 * @param attributeCodes
	 * @return Map of attributeCodes/attributeValues
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	public Map getAttributes(String[] attributeCodes) throws MiddlewareException, SQLException {
		Map map = new HashMap();
		for (int i = 0; i < attributeCodes.length; i++) {
			map.put(attributeCodes[i], null);
		}
		getAttributes(map);
		return map;
	}
	
	/**
	 * Uses GBL7545 to get all attributes from the entity,
	 * filtering by the attributeMap keys.<br>
	 * It stores the attribute value related to the atribute code
	 * found in the map keys
	 * @param attributeMap
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	public void getAttributes(Map attributeMap) throws MiddlewareException, SQLException {
		String enterprise = profile.getEnterprise();
		ReturnStatus returnStatus = new ReturnStatus(-1);
		int resultCount = attributeMap.size();
		ResultSet rs = database.callGBL7545(returnStatus, enterprise, entityType, 
				entityID, profile.getValOn(), profile.getEffOn());
		try {
			while (rs.next()) {
				String code = rs.getString(1); //AttributeCode
				if (attributeMap.containsKey(code)) {
					String value = rs.getString(3); //AttributeValue
					attributeMap.put(code, value);
					resultCount--;
					//Filled all
					if (resultCount <= 0)
						break;
				}
			}
		} finally {
			rs.close();
			database.freeStatement();
			database.isPending();
		}
	}

	/**
	 * Set the text attribute value of this entity
	 * @param attributeCode
	 * @param attributeValue
	 * @throws MiddlewareException
	 */
	public void setTextAttribute(String attributeCode, String attributeValue) throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();
		int openID = controlBlock.getOPENID();
		int tranID = controlBlock.getTranID();
		String effFrom = controlBlock.getEffFrom();
		String effTo = controlBlock.getEffTo();
		database.callGBL2091(returnStatus, openID, enterprise, entityType,
				new ReturnID(entityID), 
				attributeCode, attributeValue, 
				nlsID, tranID, effFrom, effTo);
		database.freeStatement();
		database.isPending();
	}

	/**
	 * Set the single flag value for this entity 
	 * @param attributeCode
	 * @param attributeValue
	 * @throws MiddlewareException
	 */
	public void setFlagAttribute(String attributeCode, String attributeValue) throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();
		int openID = controlBlock.getOPENID();
		int tranID = controlBlock.getTranID();
		String effFrom = controlBlock.getEffFrom();
		String effTo = controlBlock.getEffTo();
		database.callGBL2265(returnStatus, openID, enterprise,
				entityType, new ReturnID(entityID), 
				attributeCode, attributeValue, 
				tranID, effFrom, effTo, nlsID);
		database.freeStatement();
		database.isPending();
	}

	/**
	 * Loops thru all entities of type "entityType" using a callback object.
	 * 
	 * @param database
	 * @param profile
	 * @param entityType
	 * @param callback
	 * @throws Exception
	 */
	public static void withAllEntitiesDo(Database database, Profile profile,
			ControlBlock controlBlock, String entityType, int nlsID, Callback callback) throws Exception {
		String enterprise = profile.getEnterprise();

		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = database.callGBL0007(returnStatus, enterprise,
				entityType);
		
		Vector list = new Vector();
		try {
			while (rs.next()) {
				int entityID = rs.getInt("entityid");
				EntityHandler entityHandler = new EntityHandler(database,
						profile, controlBlock, entityType, entityID, nlsID);
				list.add(entityHandler);
			}

		} finally {
			rs.close();
			database.freeStatement();
			database.isPending();
		}
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			EntityHandler element = (EntityHandler) iter.next();
			callback.process(element);
		}
		list.clear();
	}

	/**
	 * Loops thru all child related entities using a callback to process each one
	 * @param relatorType
	 * @param callback
	 * @throws Exception
	 */
	public void withChildEntitiesDo(String relatorType, Callback callback)
			throws Exception {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();
		int openID = controlBlock.getOPENID();
		
		ResultSet rs = database.callGBL2054(returnStatus, openID,
				enterprise, relatorType, entityType, entityID,
				profile.getValOn(), profile.getEffOn());
		
		Vector list = new Vector();
		try {
			while (rs.next()) {
				int childEntityID = rs.getInt("EntityID");
				String childEntityType = rs.getString("EntityType");
				EntityHandler entityHandler = new EntityHandler(database,
						profile, controlBlock, childEntityType, childEntityID, nlsID);
				list.add(entityHandler);
			}
		} finally {
			rs.close();
			database.freeStatement();
			database.isPending();
		}
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			EntityHandler element = (EntityHandler) iter.next();
			callback.process(element);
		}
		list.clear();
	}
	

	/**
	 * This method should wait for callGBL2055 method in Middleware
	 * @param relatorType
	 * @param callback
	 * @throws Exception
	 */
	public void withParentEntitiesDo(String relatorType, Callback callback)
			throws Exception {
		/*ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();

		ResultSet rs = database.callGBL2055(returnStatus, enterprise,
				relatorType, entityType, entityID, 
				profile.getValOn(), profile.getEffOn());

		Vector list = new Vector();
		try {
			while (rs.next()) {
				String parentEntityType = rs.getString(1);
				int parentEntityID = rs.getInt(2);
				EntityHandler parentEntityHandler = new EntityHandler(database,
						profile, controlBlock, parentEntityType, parentEntityID,
						nlsID);
				list.add(parentEntityHandler);
			}
		} finally {
			rs.close();
			database.freeStatement();
			database.isPending();
		}

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			EntityHandler element = (EntityHandler) iter.next();
			callback.process(element);
		}
		list.clear();*/
	}

	
	public interface Callback {
		void process(EntityHandler entityHandler) throws Exception;
	}

	/**
	 * Insert this entity in the database and set the new entity ID 
	 * @throws MiddlewareException
	 * @throws SQLException 
	 */
	public ReturnStatus insert()
			throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();
		int openID = controlBlock.getOPENID();
		int tranID = controlBlock.getTranID();
		String effFrom = controlBlock.getEffFrom();
		String effTo = controlBlock.getEffTo();
		ReturnID returnID = new ReturnID(-1);
		returnID = database.callGBL2092(returnStatus, openID,
				profile.getSessionID(), enterprise, entityType, returnID,
				tranID, effFrom, effTo, nlsID);
		database.freeStatement();
		database.isPending();
		entityID = returnID.intValue();
		try {
			database.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnStatus;
	}

	/**
	 * Add a relator from this entity to "entityTo" with "relatorType" as relator's entityType 
	 * @param relatorType
	 * @param entityToType
	 * @param entityToID
	 * @throws MiddlewareException
	 * @throws SQLException 
	 */
	public ReturnStatus addRelator(String relatorType, String entityToType, int entityToID) throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = profile.getEnterprise();
		int openID = controlBlock.getOPENID();
		int tranID = controlBlock.getTranID();
		String effFrom = controlBlock.getEffFrom();
		String effTo = controlBlock.getEffTo();
		int sessionID = profile.getSessionID();
		database.callGBL2098(returnStatus, openID, sessionID,
				enterprise, relatorType,
				new ReturnID(), entityType, entityID,
				entityToType, entityToID, 
				tranID, effFrom, effTo,	nlsID);
		database.freeStatement();
		database.isPending();
		try {
			database.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnStatus;
	}
	
}

/*
Do you think it's worthy to create a SP similar to 2054, but using the entity2id and type as parameters? 
for this case we have these parameters:
RELATORTYPE, ENTITY1TYPE, ENTITY2TYPE, ENTITY2ID

And the return values should be:
ENTITY1TYPE, ENTITY1ID

Example:
RELATORTYPE = WWSEOLSEO
ENTITY1TYPE = WWSEO
ENTITY2TYPE = LSEO
ENTITY2ID = 656

Returns:
ENTITY1TYPE = WWSEO
ENTITY1ID = 657
*/