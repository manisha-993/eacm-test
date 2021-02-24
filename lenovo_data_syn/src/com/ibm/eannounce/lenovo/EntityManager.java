package com.ibm.eannounce.lenovo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.ABRUtil;
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
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

public class EntityManager {

	private static final String PDHDOMAIN = "LENOVO";

	private static final String QUEUED = "0020";

	private static final int NLSID = 1;

	private static final String ENTITY_TYPE = "REFOFER";

	static final String TAG = "Lenovo";

	private Properties properties;

	private Database database;

	private String now;

	private String timeStampForever;

	private Profile profile;

	private String enterprise;

	private String pdhDomain;

	private boolean offlineMode;

	public EntityManager(Properties properties) {
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
			throw new IllegalStateException("Unable to access DB2 driver: COM.ibm.db2.jdbc.app.DB2Driver");
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
				throw new Exception("Profile not found for " + ldapId);
			}
			Log.i(TAG, "Logged in via ldap: " + ldapId + ", role: " + profile.getRoleCode() + ", OPWGID: "
					+ profile.getOPWGID() + ", OP: " + profile.getOPID() + ", enterprise: " + profile.getEnterprise());
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

	public void createEntity(MIWModel model) throws MiddlewareException, SQLException {
		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (createEntity): " + model);
			return;
		}

//		if ("Update".equalsIgnoreCase(model.getACTIVITY())) {
//			if (refofer == null) {
//				// REFOFER not found, create a new one
//				EntityGroup eg = new EntityGroup(profile, ENTITY_TYPE, null);
//				refofer = new EntityItem(eg, profile, "REFOFER", 0);
//				Log.i(TAG, "New REFOFER created");
//			}
//			// Update attributes
//			refoferWrapper = new EntityWrapper(refofer);
//			refoferWrapper.text("PRODUCTID", model.getPRODUCTID());
//			refoferWrapper.text("DTSMIWCREATE", refoferModel.DTSMIWCREATE);
//			refoferWrapper.flag("PDHDOMAIN", MIW_PDHDOMAIN);
//			refoferWrapper.text("MFRPRODTYPE", refoferModel.MFRPRODTYPE, 30);
//			refoferWrapper.text("MFRPRODDESC", refoferModel.MFRPRODDESC, 32);
//			refoferWrapper.text("MKTGDIV", refoferModel.MKTGDIV, 2);
//			refoferWrapper.flag("PRFTCTR", refoferModel.PRFTCTR);
//			refoferWrapper.text("CATGSHRTDESC", refoferModel.CATGSHRTDESC, 30);
//			refoferWrapper.text("STRTOFSVC", refoferModel.STRTOSVC);
//			refoferWrapper.text("ENDOFSVC", refoferModel.ENDOFSVC);
//			refoferWrapper.text("VENDNAM", refoferModel.VENDNAM, 30);
//			refoferWrapper.text("MACHRATECATG", refoferModel.MACHRATECATG, 1);
//			refoferWrapper.text("CECSPRODKEY", refoferModel.CECSPRODKEY, 1);
//			// FLAG N = MAINN / Y = MAINY
//			refoferWrapper.flag("MAINTANNBILLELIGINDC",
//					"Y".equals(refoferModel.MAINTANNBILLELIGINDC) ? "MAINY" : "MAINN");
//			// REFOFER_DATA/FSLMCPU -> SYSIDUNIT
//			// If input='Y', value is "SIU-CPU" (S00010); if input='N', value is "SIU-Non
//			// CPU" (S00020)
//			refoferWrapper.flag("SYSIDUNIT", "Y".equals(refoferModel.FSLMCPU) ? "S00010" : "S00020");
//			refoferWrapper.text("PRODSUPRTCD", refoferModel.PRODSUPRTCD, 3);
//			refoferWrapper.flag("DATAQUALITY", DQ_FINAL);
//			refoferWrapper.flag("STATUS", STATUS_FINAL);
//			refoferWrapper.flag("ADSABRSTATUS", ABR_QUEUE);
//			refoferWrapper.end();
//			setReturnCode(RETURNCODE_SUCCESS);
//			Log.i(TAG, "REFOFER attributes updated");
//		}

		try {
			EntityItem refofer = findRefofer(model.getPRODUCTID());

			if (refofer != null) {
				Log.i(TAG, "REFOFER found: " + refofer.getKey());
			} else {
				Log.i(TAG, "REFOFER not found!");
			}

			if ("Update".equalsIgnoreCase(model.getACTIVITY())) {
				if (refofer == null) {
					// REFOFER not found, create a new one
					EntityGroup eg = new EntityGroup(profile, ENTITY_TYPE, null);
					refofer = new EntityItem(eg, profile, "REFOFER", 0);
					Log.i(TAG, "New REFOFER created");
				}

				ControlBlock ctrl = new ControlBlock(now, timeStampForever, now, timeStampForever, profile.getOPWGID(),
						profile.getTranID());

				Log.d(TAG, "entityItem:" + refofer.getKey());
				ReturnEntityKey returnEntityKey = new ReturnEntityKey(ENTITY_TYPE, 0, true);
				Log.d(TAG, "returnEntityKey:" + returnEntityKey);

				String eType = refofer.getEntityType();
				int eID = refofer.getEntityID();
				Vector attrs = new Vector();
				// Entity attribute mapping
				attrs.add(new Text(enterprise, eType, eID, "DTSOFMSG", model.getDTSOFMSG(), NLSID, ctrl));
				attrs.add(new Text(enterprise, eType, eID, "ACTIVITY", model.getACTIVITY(), NLSID, ctrl));
				attrs.add(new Text(enterprise, eType, eID, "DTSMIWCREATE", now, NLSID, ctrl));
				attrs.add(new Text(enterprise, eType, eID, "PRODUCTID", model.getPRODUCTID(), NLSID, ctrl));

				if (model.getMFRPRODTYPE() != null && !"".equals(model.getMFRPRODTYPE())) {
					attrs.add(new Text(enterprise, eType, eID, "MFRPRODTYPE", model.getMFRPRODTYPE(), NLSID, ctrl));
				}
				if (model.getMFRPRODDESC() != null && !"".equals(model.getMFRPRODDESC())) {
					attrs.add(new Text(enterprise, eType, eID, "MFRPRODDESC", model.getMFRPRODDESC(), NLSID, ctrl));
				}
				if (model.getMKTGDIV() != null && !"".equals(model.getMKTGDIV())) {
					attrs.add(new Text(enterprise, eType, eID, "MKTGDIV", model.getMKTGDIV(), NLSID, ctrl));
				}
				if (model.getCATGSHRTDESC() != null && !"".equals(model.getCATGSHRTDESC())) {
					attrs.add(new Text(enterprise, eType, eID, "CATGSHRTDESC", model.getCATGSHRTDESC(), NLSID, ctrl));
				}
				if (model.getSTRTOSVC() != null && !"".equals(model.getSTRTOSVC())) {
					attrs.add(new Text(enterprise, eType, eID, "STRTOSVC", model.getSTRTOSVC(), NLSID, ctrl));
				}
				if (model.getENDOFSVC() != null && !"".equals(model.getENDOFSVC())) {
					attrs.add(new Text(enterprise, eType, eID, "ENDOFSVC", model.getENDOFSVC(), NLSID, ctrl));
				}

				attrs.add(new Text(enterprise, eType, eID, "VENDNAM", model.getVENDNAM(), NLSID, ctrl));
//				attrs.add(new Text(enterprise, eType, eID, "MACHRATECATG", "", NLSID, ctrl));
				attrs.add(new Text(enterprise, eType, eID, "CECSPRODKEY", model.getCECSPRODKEY(), NLSID, ctrl));
				attrs.add(new SingleFlag(enterprise, eType, eID, "MAINTANNBILLELIGINDC",
						"Y".equals(model.getMAINTANNBILLELIGINDC()) ? "MAINY" : "MAINN", NLSID, ctrl));
				attrs.add(new SingleFlag(enterprise, eType, eID, "SYSIDUNIT",
						"Y".equals(model.getFSLMCPU()) ? "S00010" : "S00020", NLSID, ctrl));
//				attrs.add(new Text(enterprise, eType, eID, "PRODSUPRTCD", , NLSID, ctrl));
//				attrs.add(new SingleFlag(enterprise, eType, eID, "PRFTCTR", , NLSID, ctrl));
				attrs.add(new SingleFlag(enterprise, eType, eID, "PDHDOMAIN", PDHDOMAIN, NLSID, ctrl));

				attrs.add(new SingleFlag(enterprise, eType, eID, "DATAQUALITY", "FINAL", NLSID, ctrl));
				attrs.add(new SingleFlag(enterprise, eType, eID, "STATUS", "0020", NLSID, ctrl));
				attrs.add(new SingleFlag(enterprise, eType, eID, "ADSABRSTATUS", QUEUED, NLSID, ctrl));

				Vector transactions = new Vector();
				returnEntityKey.m_vctAttributes = attrs;
				Log.d(TAG, "returnEntityKey.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
				transactions.addElement(returnEntityKey);
				database.update(profile, transactions, false, false);
				database.commit();

				Log.i(TAG, "REFOFER " + eID + " attributes updated");
			}

		} catch (Exception e) {
			Log.e(TAG, "Create Entity Exception:" + e);
			throw e;
		}
	}

	private EntityItem findRefofer(String productID) {
		// Search all REFOFER entity ids for ProductID and PDHDOMAIN = 'MIW'
		Vector attrs = new Vector();
		attrs.addElement("PRODUCTID");
		Vector vals = new Vector();
		vals.addElement(productID);
		StringBuffer debugSb = new StringBuffer();
		try {
			EntityItem[] list = ABRUtil.doSearch(database, profile, "SRDREFOFER1", "REFOFER", false, attrs, vals,
					debugSb);
			for (int i = 0; i < list.length; i++) {
				EntityItem ei = list[i];
				String id = PokUtils.getAttributeValue(ei, "PRODUCTID", ",", "", false);
				String domain = PokUtils.getAttributeFlagValue(ei, "PDHDOMAIN");
				Log.d(TAG, "Looking at " + ei.getKey() + " [" + id + "," + domain + "]");
				if (productID.equalsIgnoreCase(id) && "LENOVO".equals(domain)) {
					// Found REFOFERFEAT
					return ei;
				}
			}
		} catch (Exception e) {
			Log.d(TAG, debugSb.toString());
			Log.e(TAG, "Search Exits entity Exception:" + e);
//			throw e;
		}
		return null;
	}

	public List getRecords(String T1) throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (getRecords): MTYPE");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE");
		String sql = null;
		boolean isDelta = false;
		if ("1980-01-01 00:00:00.000000".equals(T1)) {
			sql = "select MTYPE,UPD_DT,FAMILYNAME,SERIESNAME,DIVISION,BRAND,ANNOUNCE_DATE from opicm.EACM_MTYPE with ur";

		} else {
			sql = "select MTYPE,UPD_DT,FAMILYNAME,SERIESNAME,DIVISION,BRAND,ANNOUNCE_DATE,ACTION_TYPE from opicm.EACM_MTYPE_LOG t1 "
					+ "where ACTION_TIME=(select max(ACTION_TIME) from opicm.EACM_MTYPE_LOG where ACTION_TIME between '"
					+ T1 + "' and current timestamp and t1.mtype= mtype Group by mtype) with ur";
			isDelta = true;
		}
		Log.d(TAG, "Extract SQL:" + sql);
		List entitys = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				MIWModel model = new MIWModel();
				model.setDTSOFMSG(rs.getString("UPD_DT"));
				if (isDelta) {
					model.setACTIVITY(getValue(rs.getString("ACTION_TYPE")));
				} else {
					model.setACTIVITY("Update");
				}
				model.setPRODUCTID(rs.getString("MTYPE") + "DUM");
				model.setMFRPRODTYPE(rs.getString("MTYPE") + "-" + "DUM");
				model.setMFRPRODDESC(rs.getString("FAMILYNAME") + " - " + rs.getString("SERIESNAME"));
				model.setMKTGDIV(rs.getString("DIVISION"));
				model.setCATGSHRTDESC(rs.getString("BRAND"));
				model.setSTRTOSVC(rs.getString("ANNOUNCE_DATE"));
				model.setENDOFSVC("9999-12-31");
				model.setVENDNAM("LENOVO");

				model.setCECSPRODKEY("3");
				model.setMAINTANNBILLELIGINDC("N");
				model.setFSLMCPU("N");

				entitys.add(model);
			}

		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return entitys;
	}

	private String getValue(String string) {
		if ("D".equals(string)) {
			return "Delete";
		} else {
			return "Update";
		}
	}

}
