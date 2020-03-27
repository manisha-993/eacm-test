package com.ibm.eannounce.miw;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LoginException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.LongText;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

public class MIWEntityManager {

	private static final String QUEUED = "0020";

	private static final int NLSID = 1;

	private static final String ENTITY_TYPE = "MIWXML";

	static final String TAG = "MW";

	private Properties properties;

	private Database database;

	private String now;

	private String timeStampForever;

	private Profile profile;

	private String enterprise;

	private String pdhDomain;
	
	private boolean offlineMode;

	public MIWEntityManager(Properties properties) {
		this.properties = properties;
		offlineMode = "true".equalsIgnoreCase(properties.getProperty(Keys.MW_OFFLINE, "false"));
	}

	public void connect() {
		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (connect)");
			return;
		}
		
		Log.i(TAG, "Connecting to PDH...");
		try {
			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
		} catch (Throwable e) {
			Log.e(TAG, "Unable to access DB2 driver: COM.ibm.db2.jdbc.app.DB2Driver", e);
			throw new IllegalStateException(
					"Unable to access DB2 driver: COM.ibm.db2.jdbc.app.DB2Driver");
		}
		database = new Database();
		try {
			database.connect();
		} catch (Exception e) {
			Log.e(TAG, "Unable to connect to PDH: " + database, e);
			throw new IllegalStateException("Unable to connect to PDH: " + database);
		}
		if (database == null) {
			Log.e(TAG, "Unable to connect to PDH: " + database);
			throw new IllegalStateException("Unable to connect to PDH: " + database);
		}
		try {
			database.getNow();
			DatePackage datepackage = database.getDates();
			now = datepackage.getNow();
			timeStampForever = datepackage.getForever();
			String ldapId = properties.getProperty(Keys.MW_USER);
			String ldapPassword = properties.getProperty(Keys.MW_PASSWORD);
			String OPICMVersion = properties.getProperty(Keys.MW_OPICMVERSION);
			String roleCode = properties.getProperty(Keys.MW_ROLECODE);
			enterprise = properties.getProperty(Keys.MW_ENTERPRISE);
			pdhDomain = properties.getProperty(Keys.MW_PDHDOMAIN);
			ProfileSet profileSet = database.login(ldapId, ldapPassword, OPICMVersion);
			if (profileSet == null) {
				Log.d(TAG, "DB settings:" + ldapId + ", " + ldapPassword + ", " + OPICMVersion);
				Log.e(TAG, "Unable to login: " + database);
				throw new LoginException("Unable to login: " + database);
			}
			Log.d(TAG, "Logged in via ldap... Profile set size is " + profileSet.size());
			for (int i = 0; i < profileSet.size(); i++) {
				profile = profileSet.elementAt(i);
				if (profile.getRoleCode().trim().equals(roleCode)
						&& profile.getEnterprise().trim().equals(enterprise)) {
					profileSet.setActiveProfile(i);
					profile = profileSet.getActiveProfile();
					profile.setValOn(now);
					profile.setEffOn(now);
					break;
				}
			}
			if (profile == null) {
				throw new Exception("Profile not found for "+ldapId);
			}
			Log.i(TAG, "Logged in via ldap: " + ldapId + ", role: " + profile.getRoleCode()
					+ ", OPWGID: " + profile.getOPWGID() + ", OP: " + profile.getOPID()
					+ ", enterprise: " + profile.getEnterprise());
			Log.d(TAG, "TimeStampNow is " + now + ", TimeStampForever is " + timeStampForever);

		} catch (Exception e) {
			Log.e(TAG, "Unable to login", e);
			throw new IllegalStateException("Unable to login");
		}
	}

	public void disconnect() {
		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (disconnect)");
			return;
		}
		if (database != null) {
			try {
				Log.i(TAG, "Closing Connection");
				database.close();
			} catch (Exception e) {
				Log.e(TAG, "Error on close connection", e);
			}
			database = null;
		}
	}

	public void createEntity(MIWModel model, String message) throws MiddlewareException,
			SQLException {
		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (createEntity): "+model);
			return;
		}
		
		Log.d(TAG, "Creating " + ENTITY_TYPE + " entity");
		ControlBlock ctrl = new ControlBlock(now, timeStampForever, now, timeStampForever, profile
				.getOPWGID(), profile.getTranID());
		EntityGroup eg = new EntityGroup(profile, ENTITY_TYPE, null);
		EntityItem ei = new EntityItem(eg, profile, ENTITY_TYPE, 0);
		Log.d(TAG, "entityItem:" + ei.getKey());
		ReturnEntityKey returnEntityKey = new ReturnEntityKey(ENTITY_TYPE, 0, true);
		Log.d(TAG, "returnEntityKey:" + returnEntityKey);

		String eType = ei.getEntityType();
		int eID = ei.getEntityID();
		Vector attrs = new Vector();
		//Entity attribute mapping
		attrs.add(new Text(enterprise, eType, eID, "MXDTS", now, NLSID, ctrl));
		attrs.add(new Text(enterprise, eType, eID, "MXMSGDTS", model.DTSOFMSG, NLSID, ctrl));
		attrs.add(new Text(enterprise, eType, eID, "MXMIWCDTS", model.DTSMIWCREATE, NLSID, ctrl));
		attrs.add(new Text(enterprise, eType, eID, "MXTYPE", model.TYPE, NLSID, ctrl));
		attrs.add(new Text(enterprise, eType, eID, "MXPRODUCTID", model.PRODUCTID, NLSID, ctrl));
		if ("REFOFERFEAT".equals(model.TYPE)) {
			attrs.add(new Text(enterprise, eType, eID, "MXFEATID", model.FEATID, NLSID, ctrl));
		}
		attrs.add(new SingleFlag(enterprise, eType, eID, "MXABRSTATUS", QUEUED, NLSID, ctrl));
		attrs.add(new LongText(enterprise, eType, eID, "MXMSG", message, NLSID, ctrl));
		attrs.add(new SingleFlag(enterprise, eType, eID, "PDHDOMAIN", pdhDomain, NLSID, ctrl));

		Vector transactions = new Vector();
		returnEntityKey.m_vctAttributes = attrs;
		Log.d(TAG, "returnEntityKey.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
		transactions.addElement(returnEntityKey);
		database.update(profile, transactions, false, false);
		database.commit();
	}

}
