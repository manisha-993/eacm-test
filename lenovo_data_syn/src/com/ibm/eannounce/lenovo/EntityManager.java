package com.ibm.eannounce.lenovo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

				refoferWrapper.text("MFRPRODTYPE", model.getMFRPRODTYPE(), ctrl);
				refoferWrapper.text("MFRPRODDESC", model.getMFRPRODDESC(), ctrl);
				refoferWrapper.text("MKTGDIV", model.getMKTGDIV(), ctrl);
				refoferWrapper.text("CATGSHRTDESC", model.getCATGSHRTDESC(), ctrl);
				refoferWrapper.text("STRTOFSVC", model.getSTRTOFSVC(), ctrl);
				refoferWrapper.text("ENDOFSVC", model.getENDOFSVC(), ctrl);

				refoferWrapper.text("VENDNAM", model.getVENDNAM(), ctrl);
//				refoferWrapper.text("MACHRATECATG", "", ctrl);
				refoferWrapper.text("CECSPRODKEY", model.getCECSPRODKEY(), ctrl);
				refoferWrapper.flag("MAINTANNBILLELIGINDC",
						"Y".equals(model.getMAINTANNBILLELIGINDC()) ? "MAINY" : "MAINN", ctrl);
				refoferWrapper.flag("SYSIDUNIT", "Y".equals(model.getFSLMCPU()) ? "S00010" : "S00020", ctrl);
//				refoferWrapper.text("PRODSUPRTCD", , ctrl);
//				refoferWrapper.text("PRFTCTR", , ctrl);
				refoferWrapper.flag("PDHDOMAIN", PDHDOMAIN, ctrl);
				refoferWrapper.flag("DCG", model.getDCG(), ctrl);
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
		Vector attrs = new Vector<String>();
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

	public List getTypeRecords(String T1) throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE and MTM");
		String sql = null;

		sql = "select distinct t2.MTYPE,t2.DCG from opicm.EACM_MTYPE_LOG t2 where t2.ACTION_TIME=(select max(t1.ACTION_TIME) from opicm.EACM_MTYPE_LOG t1 where t1.ACTION_TIME between '"
				+ T1 + "' and current timestamp and t1.mtype= t2.mtype Group by t1.mtype) with ur";

		Log.d(TAG, "Extract SQL:" + sql);
		List allType = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Map data = new HashMap();
				data.put("MTYPE", rs.getString("MTYPE").trim());
				data.put("DCG", rs.getString("DCG").trim());
				allType.add(data);
			}

			Log.i(TAG, "get change MTYPE: " + allType.toString());
		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return allType;
	}

	public List getDCGRecords1(String T1, String DCGtype) throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE and MTM");
		String sql = null;

		sql = "select distinct t1.MTYPE as TYPE,t2.MACHINE_MODEL as model,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.DCG from opicm.EACM_MTYPE_LOG t1 "
				+ "join opicm.EACM_MTM_LOG t2 on t1.MTYPE=t2.MACHINE_TYPE where t1.ACTION_TIME=(select max(ACTION_TIME) from opicm.EACM_MTYPE_LOG where ACTION_TIME between '"
				+ T1
				+ "' and current timestamp and t1.MTYPE= mtype Group by mtype) and t1.DCG='Y' and t2.MACHINE_MODEL is not null and t1.MTYPE in ("
				+ DCGtype + ") with ur";

		Log.d(TAG, "Extract SQL:" + sql);
		List entitys = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				MIWModel model = new MIWModel();
				model.setDTSOFMSG(processValue(rs.getString("UPD_DT")));
				model.setACTIVITY("Update");

				model.setPRODUCTID(processValue(rs.getString("TYPE")) + processValue(rs.getString("MODEL")));
				
				model.setMFRPRODTYPE(processValue(rs.getString("TYPE")) + "-" + processValue(rs.getString("MODEL")));
				model.setMFRPRODDESC(
						processValue(rs.getString("FAMILYNAME")) + " - " + processValue(rs.getString("SERIESNAME")));
				
				model.setMKTGDIV(processValue(rs.getString("DIVISION")));
				model.setCATGSHRTDESC(processValue(rs.getString("BRAND")));
				model.setSTRTOFSVC(processValue(rs.getString("ANNOUNCE_DATE")));
				model.setENDOFSVC("9999-12-31");
				model.setVENDNAM("LENOVO");

				model.setCECSPRODKEY("3");
				model.setMAINTANNBILLELIGINDC("N");
				model.setFSLMCPU("N");
				model.setDCG(processValue(rs.getString("DCG")));

				entitys.add(model);
			}

		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return entitys;
	}

	public List getDCGRecords2(String T1) throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE and MTM");
		String sql = null;

		sql = "select distinct t1.MTYPE as TYPE,t2.MACHINE_MODEL as model,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.DCG from opicm.EACM_MTYPE_LOG t1 "
				+ "join opicm.EACM_MTM_LOG t2 on t1.MTYPE=t2.MACHINE_TYPE "
				+ "where t2.ACTION_TIME=(select max(t3.ACTION_TIME) from opicm.EACM_MTM_LOG t3 where t3.ACTION_TIME between '"
				+ T1 + "' and current timestamp and t2.product_id= t3.product_id Group by t3.product_id) "
				+ "and t1.DCG='Y' and t2.MACHINE_MODEL is not null with ur";

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
				model.setACTIVITY("Update");

				model.setPRODUCTID(processValue(rs.getString("TYPE")) + processValue(rs.getString("MODEL")));
				
				model.setMFRPRODTYPE(processValue(rs.getString("TYPE")) + "-" + processValue(rs.getString("MODEL")));
				model.setMFRPRODDESC(
						processValue(rs.getString("FAMILYNAME")) + " - " + processValue(rs.getString("SERIESNAME")));
				
				model.setMKTGDIV(processValue(rs.getString("DIVISION")));
				model.setCATGSHRTDESC(processValue(rs.getString("BRAND")));
				model.setSTRTOFSVC(processValue(rs.getString("ANNOUNCE_DATE")));
				model.setENDOFSVC("9999-12-31");
				model.setVENDNAM("LENOVO");

				model.setCECSPRODKEY("3");
				model.setMAINTANNBILLELIGINDC("N");
				model.setFSLMCPU("N");
				model.setDCG(processValue(rs.getString("DCG")));

				entitys.add(model);
			}

		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return entitys;
	}

	public String processValue(String s) {

		if (s != null && !"".equals(s)) {
			return s.trim();
		} else {
			return " ";
		}
	}

	public List getNotDCGRecords(String T1, String noDCGtype) throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode");
			return null;
		}

		Log.d(TAG, "get Records From table MTYPE and MTM");
		String sql = null;

		sql = "select distinct t1.MTYPE,t1.UPD_DT,t1.FAMILYNAME,t1.SERIESNAME,t1.DIVISION,t1.BRAND,t1.ANNOUNCE_DATE,t1.DCG from opicm.EACM_MTYPE_LOG t1 "
				+ "where t1.ACTION_TIME=(select max(ACTION_TIME) from opicm.EACM_MTYPE_LOG where ACTION_TIME between '"
				+ T1 + "' and current timestamp and t1.mtype= mtype Group by mtype) and t1.MTYPE in (" + noDCGtype
				+ ") and t1.DCG='N' with ur";

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
				model.setACTIVITY("Update");

				model.setPRODUCTID(processValue(rs.getString("MTYPE")));
				model.setMFRPRODTYPE(processValue(rs.getString("MTYPE")));
				model.setMFRPRODDESC(
						processValue(rs.getString("FAMILYNAME")) + " - " + processValue(rs.getString("SERIESNAME")));
				model.setMKTGDIV(processValue(rs.getString("DIVISION")));
				model.setCATGSHRTDESC(processValue(rs.getString("BRAND")));
				model.setSTRTOFSVC(processValue(rs.getString("ANNOUNCE_DATE")));
				model.setENDOFSVC("9999-12-31");
				model.setVENDNAM("LENOVO");

				model.setCECSPRODKEY("3");
				model.setMAINTANNBILLELIGINDC("N");
				model.setFSLMCPU("N");
				model.setDCG(processValue(rs.getString("DCG")));

				entitys.add(model);
			}

		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return entitys;
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
				types.add(rs.getString(1).trim());
			}

			Log.i(TAG, "get IBM Type: " + types.toString());
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
		String sql = "select distinct substring(t.attributevalue,0,5) from opicm.text t "
				+ "join opicm.flag f on f.entitytype=t.entitytype and f.entityid=t.entityid and f.attributecode='PDHDOMAIN' and f.attributevalue='MIW' "
				+ "where t.entitytype='REFOFER' and t.attributecode='PRODUCTID' and t.valto>current timestamp and t.effto>current timestamp and f.valto>current timestamp and f.effto>current timestamp with ur";
		Log.d(TAG, "Extract SQL:" + sql);
		List types = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				types.add(rs.getString(1).trim());
			}
			Log.i(TAG, "get IBM pseudo Type: " + types.toString());
		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return types;
	}

	private List getMIWType() {
		List types = new ArrayList();
		File file = new File("LENOVOEXCLUDED.TXT");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			reader.readLine();
			String line = null;
			while ((line = reader.readLine()) != null) {
				types.add(line.trim());
			}
			Log.i(TAG, "get MIW type from file: " + types.toString());
			reader.close();
		} catch (IOException e) {
			Log.e(TAG, "get MIW type from file: " + e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return types;
	}

	public List filterType(List types, List ibmType, List ibmPseudoType) {
		List newType = new ArrayList();
		int length = types.size();
		for (int i = 0; i < length; i++) {
			String type = (String) ((Map) types.get(i)).get("MTYPE");
			if (ibmType.contains(type) || ibmPseudoType.contains(type)) {

			} else {
				newType.add(types.get(i));
			}
		}

		Log.i(TAG, "After Filter MTYPE: " + newType.toString());
		return newType;
	}

	public List filterDCGModel(List types) {

		List newType = new ArrayList();
		int length = types.size();

		List MIWType = getMIWType();
		for (int i = 0; i < length; i++) {
			String type = (String) types.get(i);
			/**
			 * Business rule 1: If the machine type is DCG (business rule 3) AND in the set
			 * that was made available for Lenovo (business rule 4) AND type is alpha
			 * numeric AND type on the approved list from MIW AND there is a model in MTM
			 * (model not blank) THEN it is a valid one to be used in IBM (can be used in
			 * CHIS/CONGA for contract). Business rule 2: If the machine type is DCG
			 * (business rule 3) AND in the set that was made available for Lenovo (business
			 * rule 4) AND type numeric only AND type not matching with an IBM type
			 * (business rule 8) or an IBM pseudo type (business rule 7) AND there is a
			 * model (model not blank) in MTM THEN it is valid to be used in IBM (can be
			 * used in CHIS/CONGA for a contract). Business rule 3: a type is DCG if the
			 * product division is 4S, Z3, 13, Y1 or G9 Business rule 4: the set of types
			 * that was made available for Lenovo type model have a type that start with
			 * 1,2,3,4,5,6,7,8,9 Business rule 5: If the machine type is NOT DCG (NOT
			 * business rule 3) AND in the set that was made available for Lenovo (business
			 * rule 4) AND type numeric only AND type not the same as an IBM type (business
			 * rule 8) or an IBM pseudo type (business rule 7) THEN it is valid to be used
			 * in IBM for Delivery (available in FedCat but not to be used in CHIS/CONGA
			 * contracts) Business rule 6: If a machine type is NOT DCG (NOT business rule
			 * 3) AND in the set that was made available for Lenovo (business rule 4) AND
			 * type alpha numeric AND type not the same as an IBM type (business rule 8) or
			 * an IBM pseudo type (business rule 7) THEN it is valid to be used in IBM for
			 * Delivery (available in FedCat but not to be used by CHIS/CONGA contracts)
			 * Business rule 7: all valid IBM pseudo types are registered in MIW Business
			 * rule 8: all valid IBM types are registered in EACM (edited) Business rule 9:
			 * If the machine type is DCG it needs to have a model. If the machine is NOT
			 * DCG it should be kept type only and not be enriched with a model.
			 */

//			Log.i(TAG, "Filter DCG MTYPE: " + type);
			if (normalstr(type)) {
				if (type.matches("[0-9]+")) {
					newType.add(type);
				}
				// if on the MIW list
				if (MIWType.contains(type)) {
					newType.add(type);
				} else {
					Log.i(TAG, "Filter DCG MODEL not on MIW List : " + type.toString());
				}
			} else {
				Log.i(TAG, "Filter DCG MODEL not alpha numeric : " + type.toString());
			}
		}
		return newType;
	}

	/**
	 * check type is alpha numeric
	 * 
	 * @param s
	 * @return
	 */
	public boolean normalstr(String s) {
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (!Character.isLetterOrDigit(ch)) {
				return false;
			}
		}
		return true;
	}

	public List filterRecords(String T1) {

		List entities = new ArrayList();

		StringBuffer noDCG = new StringBuffer();
		StringBuffer DCG = new StringBuffer();

		try {
			List ibmType = getIbmType();
			List ibmPseudoType = getIbmPseudoType();
			List all = filterType(getTypeRecords(T1), ibmType, ibmPseudoType);
			List DCGtype = new ArrayList();
			List noDCGtype = new ArrayList();
			for (int i = 0; i < all.size(); i++) {
				String dCG = (String) ((Map) all.get(i)).get("DCG");
				String type = (String) ((Map) all.get(i)).get("MTYPE");
				if ("Y".equals(dCG)) {
					DCGtype.add(type);
				} else {
					noDCGtype.add(type);

				}
			}
			if (DCGtype.size() > 0)
				DCGtype = filterDCGModel(DCGtype);

			for (int i = 0; i < DCGtype.size(); i++) {
				DCG.append("'" + DCGtype.get(i) + "'");
				if (i != DCGtype.size() - 1)
					DCG.append(",");
			}

			for (int i = 0; i < noDCGtype.size(); i++) {
				noDCG.append("'" + noDCGtype.get(i) + "'");
				if (i != noDCGtype.size() - 1)
					noDCG.append(",");
			}
			Log.i(TAG, "DCG Type: " + DCG.toString());
			Log.i(TAG, "not DCG Type: " + noDCG.toString());

			ibmType = null;
			ibmPseudoType = null;
			all = null;
			DCGtype = null;
			noDCGtype = null;
			System.gc();

			if (noDCG.toString() != null && !"".equals(noDCG.toString())) {
				entities.add(getNotDCGRecords(T1, noDCG.toString()));
			} else {
				entities.add(new ArrayList());
			}
			if (DCG.toString() != null && !"".equals(DCG.toString())) {
				entities.add(getDCGRecords1(T1, DCG.toString()));
			} else {
				entities.add(new ArrayList());
			}
			List l2 = getDCGRecords2(T1);
			if (l2.size() > 0) {
				entities.add(filterType2(l2, getAllType()));
			} else {
				entities.add(new ArrayList());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Filter Exception:" + e);
		}
		return entities;
	}

	private List filterType2(List l2, List allType) {
		List newType = new ArrayList();
		int length = l2.size();
		for (int i = 0; i < length; i++) {
			MIWModel model = (MIWModel) l2.get(i);
			String type = model.getPRODUCTID();
			if (allType.contains(type.substring(0, 4))) {
				newType.add(model);
			}
		}

		Log.i(TAG, "After Filter: " + newType.toString());
		return newType;
	}

	private List getAllType() throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode: get exits Type");
			return null;
		}
		Log.d(TAG, "get all LENOVO Type From text table");
		String sql = "select distinct substring(t.attributevalue,0,5) from opicm.text t "
				+ "join opicm.flag f on f.entitytype=t.entitytype and f.entityid=t.entityid and f.attributecode='PDHDOMAIN' and f.attributevalue='LENOVO' "
				+ "where t.entitytype='REFOFER' and t.attributecode='PRODUCTID' and t.valto>current timestamp and t.effto>current timestamp and f.valto>current timestamp and f.effto>current timestamp with ur";
		Log.d(TAG, "Extract SQL:" + sql);
		List types = new ArrayList();
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				types.add(rs.getString(1).trim());
			}
			Log.i(TAG, "get exits LENOVO Type: " + types.toString());
		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return types;
	}

	public String getCurrentTime() throws Exception {

		if (offlineMode) {
			Log.i(TAG, "PDH is in Offline mode: get current time");
			return null;
		}
		Log.d(TAG, "get current time From table");
		String sql = "select current timestamp from opicm.EACM_MTYPE_LOG fetch first 1 row only with ur";
		Log.d(TAG, "Extract SQL:" + sql);
		String time = "";
		try {
			Connection conn = database.getPDHConnection();
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				time = rs.getString(1);
			}

		} catch (SQLException | MiddlewareException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "read records from table Exception:" + e);
			throw e;
		}
		return time;
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
