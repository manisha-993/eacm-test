package com.ibm.eannounce.lenovo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public void createEntity(MIWModel model) throws Exception {
		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode (createEntity): " + model);
			return;
		}

		try {
			EntityItem refofer = findRefofer(model.getPRODUCTID());

			EntityWrapper refoferWrapper = null;

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

				refoferWrapper = new EntityWrapper(refofer);
				// Entity attribute mapping
				refoferWrapper.text("DTSOFMSG", model.getDTSOFMSG(), ctrl);
				refoferWrapper.text("ACTIVITY", model.getACTIVITY(), ctrl);
				refoferWrapper.text("DTSMIWCREATE", now, ctrl);
				refoferWrapper.text("PRODUCTID", model.getPRODUCTID(), ctrl);

				if (model.getMFRPRODTYPE() != null && !"".equals(model.getMFRPRODTYPE())) {
					refoferWrapper.text("MFRPRODTYPE", model.getMFRPRODTYPE(), ctrl);
				}
				if (model.getMFRPRODDESC() != null && !"".equals(model.getMFRPRODDESC())) {
					refoferWrapper.text("MFRPRODDESC", model.getMFRPRODDESC(), ctrl);
				}
				if (model.getMKTGDIV() != null && !"".equals(model.getMKTGDIV())) {
					refoferWrapper.text("MKTGDIV", model.getMKTGDIV(), ctrl);
				}
				if (model.getCATGSHRTDESC() != null && !"".equals(model.getCATGSHRTDESC())) {
					refoferWrapper.text("CATGSHRTDESC", model.getCATGSHRTDESC(), ctrl);
				}
				if (model.getSTRTOFSVC() != null && !"".equals(model.getSTRTOFSVC())) {
					refoferWrapper.text("STRTOFSVC", model.getSTRTOFSVC(), ctrl);
				}
				if (model.getENDOFSVC() != null && !"".equals(model.getENDOFSVC())) {
					refoferWrapper.text("ENDOFSVC", model.getENDOFSVC(), ctrl);
				}

				refoferWrapper.text("VENDNAM", model.getVENDNAM(), ctrl);
//				refoferWrapper.text("MACHRATECATG", "", ctrl);
				refoferWrapper.text("CECSPRODKEY", model.getCECSPRODKEY(), ctrl);
				refoferWrapper.flag("MAINTANNBILLELIGINDC",
						"Y".equals(model.getMAINTANNBILLELIGINDC()) ? "MAINY" : "MAINN", ctrl);
				refoferWrapper.flag("SYSIDUNIT", "Y".equals(model.getFSLMCPU()) ? "S00010" : "S00020", ctrl);
//				refoferWrapper.text("PRODSUPRTCD", , ctrl);
//				refoferWrapper.text("PRFTCTR", , ctrl);
				refoferWrapper.flag("PDHDOMAIN", PDHDOMAIN, ctrl);

				refoferWrapper.flag("DATAQUALITY", "FINAL", ctrl);
				refoferWrapper.flag("STATUS", "0020", ctrl);
				refoferWrapper.flag("ADSABRSTATUS", QUEUED, ctrl);

				refoferWrapper.end();
				Log.d(TAG, model.toString());
				Log.i(TAG, "REFOFER " + refofer.getEntityID() + " attributes updated");
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
			Log.i(TAG, "PDH is in Offline mode");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE and MTM");
		String sql = null;
		boolean isDelta = false;
		if ("1980-01-01 00:00:00.000000".equals(T1)) {
			sql = "select distinct t1.MTYPE,t2.MACHINE_MODEL,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.ACTION_TYPE from opicm.EACM_MTYPE_LOG t1 "
					+ "join opicm.EACM_MTM t2 on t1.Mtype=t2.MACHINE_TYPE where t2.MACHINE_MODEL is not null with ur";

		} else {

			sql = "select distinct t1.MTYPE,t2.MACHINE_MODEL,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.ACTION_TYPE from opicm.EACM_MTYPE_LOG t1 "
					+ "join opicm.EACM_MTM_LOG t2 on t1.Mtype=t2.MACHINE_TYPE where t1.ACTION_TIME=(select max(ACTION_TIME) from opicm.EACM_MTYPE_LOG where ACTION_TIME between'"
					+ T1 + "' and current timestamp and t1.mtype= mtype Group by mtype) and t2.MACHINE_MODEL is not null"
					+ "Union select distinct t1.MTYPE,t2.MACHINE_MODEL,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.ACTION_TYPE from opicm.EACM_MTYPE_LOG t1 "
					+ "join opicm.EACM_MTM_LOG t2 on t1.Mtype=t2.MACHINE_TYPE where t2.ACTION_TIME=(select max(ACTION_TIME) from opicm.EACM_MTM_LOG where ACTION_TIME between '"
					+ T1 + "' and current timestamp and t2.PRODUCT_ID= PRODUCT_ID Group by PRODUCT_ID) and t2.MACHINE_MODEL is not null with ur";

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
				model.setPRODUCTID(rs.getString("MTYPE") + "CTO");
				model.setMFRPRODTYPE(rs.getString("MTYPE") + "-" + "CTO");
				model.setMFRPRODDESC(rs.getString("FAMILYNAME") + " - " + rs.getString("SERIESNAME"));
				model.setMKTGDIV(rs.getString("DIVISION"));
				model.setCATGSHRTDESC(rs.getString("BRAND"));
				model.setSTRTOFSVC(rs.getString("ANNOUNCE_DATE"));
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

	public List getIbmType() throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode: get IBM TYPE");
			return null;
		}
		Log.d(TAG, "get MACHTYPE From metadescription table");
		String sql = "select descriptionclass from opicm.metadescription where descriptiontype='MACHTYPEATR' and nlsid=1 and valto>current timestamp and effto>current timestamp and descriptionclass not in "
				+ "(select linktype2 from opicm.metalinkattr where linktype1='MACHTYPEATR' and linkcode='Expired' and linkvalue='Y' and valto>current timestamp and effto>current timestamp)  with ur";
		Log.d(TAG, "Extract SQL:" + sql);
		List types = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				types.add(rs.getString(1));
			}
		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return types;
	}

	public List getIbmPseudoType() throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode: get IBM pseudo Type");
			return null;
		}
		Log.d(TAG, "get pseudo Type From text table");
		String sql = "\r\n"
				+ "select distinct substring(attributevalue,0,5) from opicm.text where entitytype='REFOFER' and attributecode='PRODUCTID' and valto>current timestamp and effto>current timestamp with ur";
		Log.d(TAG, "Extract SQL:" + sql);
		List types = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				types.add(rs.getString(1));
			}
		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return types;
	}

	class EntityWrapper {

		EntityItem ei;

		ReturnEntityKey rek;

		Vector attrs;
		Map map;

		public EntityWrapper(EntityItem ei) {
			this.ei = ei;
			rek = new ReturnEntityKey(ei.getEntityType(), ei.getEntityID(), true);
			map = new HashMap();
			attrs = new Vector();
		}

		private void put(String key, Object value) {
			Object oldValue = map.get(key);
			if (oldValue != null) {
				// Replace old value
				int index = attrs.indexOf(oldValue);
				if (index >= 0) {
					attrs.remove(index);
					attrs.insertElementAt(value, index);
				}
			} else {
				attrs.add(value);
			}
			map.put(key, value);
		}

		public void flag(String attributeCode, String attributeValue, ControlBlock ctrl) throws Exception {
			SingleFlag sf = new SingleFlag(profile.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
					attributeCode, attributeValue, 1, ctrl);
			put(attributeCode, sf);
		}

		public void text(String attributeCode, String attributeValue, ControlBlock ctrl) {
			Text sf = new Text(profile.getEnterprise(), rek.getEntityType(), rek.getEntityID(), attributeCode,
					attributeValue, 1, ctrl);
			put(attributeCode, sf);
		}

		public void end() throws Exception {
			Vector vctReturnsEntityKeys = new Vector();
			rek.m_vctAttributes = attrs;
			vctReturnsEntityKeys.addElement(rek);
			try {
				database.update(profile, vctReturnsEntityKeys, false, false);
				database.commit();
			} catch (Exception e) {
				throw new Exception("Unable to set text attributes for " + ei.getKey() + ": " + e.getClass().getName()
						+ " " + e.getMessage());

			}
		}

	}

}
