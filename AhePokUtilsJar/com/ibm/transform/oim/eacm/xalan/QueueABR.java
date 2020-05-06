package com.ibm.transform.oim.eacm.xalan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;

/**
 * Based on a provided query which returns the entitytype, entityid,
 * attributecode of ABR's that should be queued This ABR will queue them all.
 * 
 * @author cstolpe
 * 
 */
public class QueueABR implements Log, Data, JDBCConnection, PDHAccess {
	private Database db = null;

	private DataView dv = new DataView();

	private Hashtable jdbcCon = new Hashtable();

	private Logger log = new Logger();

	private Profile prof = null;

	private String query = "";

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#dereference()
	 */
	public boolean dereference() {
		return false;
	}

	/**
	 * @return
	 */
	private Map getABRtoQueue() {
		Map entityTypes = new HashMap();
		Connection con = (Connection) jdbcCon.get("PDH");
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String entityType = resultSet.getString("ENTITYTYPE");
				int entityID = resultSet.getInt("ENTITYID");
				String attributeCode = resultSet.getString("ATTRIBUTECODE");
				if (entityType != null && entityID > 0 && attributeCode != null) {
					Map attributeCodes = null;
					Vector ids = null;
					// EntityType maps to AttributeCode
					if (entityTypes.containsKey(entityType)) {
						attributeCodes = (Map) entityTypes.get(entityType);
					} else {
						attributeCodes = new HashMap();
					}

					// AttributeCode maps to entity ID's
					if (attributeCodes.containsKey(attributeCode)) {
						ids = (Vector) attributeCodes.get(attributeCode);
					} else {
						ids = new Vector();
						attributeCodes.put(attributeCode, ids);
					}

					ids.add(new Integer(entityID));
				}
			}
			// Now have all types and attributes to be queued
			// Use middleware API to queue everything
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entityTypes;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.Data#getDataView()
	 */
	public DataView getDataView() {
		return dv;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.util.Log#getIdentifier()
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}

	/**
	 * @param entityTypes
	 * @return
	 */
	private boolean queue(Map entityTypes) {
		final String signature = ".queue(Map): ";
		boolean result = false;
		Map updates = new HashMap();
		try {
			DatePackage dpNow = db.getDates();
			String strNow = dpNow.getNow();
			String strForever = dpNow.getForever();
			ControlBlock cbOn = 
				new ControlBlock(
						strNow, 
						strForever, 
						strNow,
						strForever, 
						prof.getOPWGID(), 
						prof.getTranID());
			Iterator types = entityTypes.keySet().iterator();
			while (types.hasNext()) {
				String entityType = (String) types.next();
				Map attributeCodes = (Map) entityTypes.get(entityType);
				Iterator codes = attributeCodes.keySet().iterator();
				while (codes.hasNext()) {
					String attributeCode = (String) codes.next();
					Vector entityIDs = (Vector) 
						attributeCodes.get(attributeCode);
					Iterator ids = entityIDs.iterator();
					while (ids.hasNext()) {
						Integer entityID = (Integer) ids.next();
						ReturnEntityKey rek = (ReturnEntityKey) 
							updates.get(entityType + entityID);
						if (rek == null) {
							rek = 
								new ReturnEntityKey(
										entityType, 
										entityID.intValue(), 
										true);
							rek.m_vctAttributes = new Vector();
							updates.put(rek.hashkey(), rek);
						}
						rek.m_vctAttributes.add(
								new SingleFlag(
										prof.getEnterprise(), 
										entityType, 
										entityID.intValue(), 
										attributeCode, 
										"0020", 
										1, 
										cbOn));

					}
				}
			}
			db.update(prof, (Vector) updates.values());
			db.commit();
			result = true;
		} catch (MiddlewareException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("MiddlewareException: ");
			log.println(e.getMessage());
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("SQLException: ");
			log.println(e.getMessage());
		} finally {
			db.freeStatement();
			db.isPending();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.JDBCConnection#setConnection(java.sql.Connection, java.lang.String)
	 */
	public boolean setConnection(Connection con, String name) {
		boolean result = con != null && name != null;
		if (result) {
			jdbcCon.put(name, con);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#setDatabase(COM.ibm.opicmpdh.middleware.Database)
	 */
	public boolean setDatabase(Database database) {
		db = database;
		return db != null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.Data#setDataView(com.ibm.transform.oim.eacm.xalan.DataView)
	 */
	public boolean setDataView(DataView aDataView) {
		boolean result = aDataView != null;
		if (result) {
			dv = aDataView;
			dv.addGroupToData(dv.getEntityType());
			result &= queue(getABRtoQueue());

		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.util.Log#setIdentifier(java.lang.String)
	 */
	public boolean setIdentifier(String anIdentifier) {
		return log.setIdentifier(anIdentifier);
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#setProfile(COM.ibm.opicmpdh.middleware.Profile)
	 */
	public boolean setProfile(Profile profile) {
		prof = profile;
		return prof != null;
	}

	/**
	 * @param sql
	 * @return
	 */
	public boolean setQuery(String sql) {
		boolean result = sql != null;
		if (result) {
			query = sql;
		}
		return result;
	}
}
