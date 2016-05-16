package com.ibm.rdh.chw.entity;

//import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
//import com.ibm.pprds.epimshw.HWPIMSLog;
//import com.ibm.pprds.epimshw.PropertyKeys;
//import com.ibm.pprds.epimshw.util.ConfigManager;
import java.util.Vector;

/**
 * Insert the type's description here. Creation date: (4/19/2001 11:46:38 AM)
 * 
 * @author: Lizsolette Williams
 */
public class TypeFeature {

	private java.lang.String feature;
	private java.lang.String type;
	private java.lang.String annDocNo;
	private java.lang.String modificationReason;
	private boolean customerSetup = false;
	private java.lang.String featureID;

	// RQ5437 - Remove attribute perCallClass
	// private java.lang.String perCallClass;

	private java.lang.String specFeatType;
	private java.lang.String description;
	private boolean maintEligible = false;
	private boolean noChargeRental = false;
	private boolean noChargePurchase = false;
	private boolean netPriceMES = false;
	private boolean superComputer = false;
	private boolean ea = false;
	private boolean stockOrderable = false;
	private boolean plantInstallOnly = false;

	private boolean lowEnd = false;
	private boolean itemReturned = false;

	private boolean Changed = false;
	private boolean Deleted = false;
	// private TypeFeatureGeoCollection tfgCollection;
	// private TypeFeatureSalesOrgCollection tfsoCollection;
	private boolean AASOrderable = false;
	private boolean UFLinked = false;
	private boolean RemovalCharge = false;

	private boolean CustomerStop = false;
	private boolean ApprovalRPQ = false;
	private java.lang.String warrantyPer;
	private boolean capOnDemand = false;
	private boolean hasTypeFeaturePromoted = false;
	private boolean hasTypeFeaturePromotedAnyAnn = false;
	private boolean hasTypeFeaturePromotedAndChanged = false;
	private boolean hasTypeFeatureDeleted = false;
	private boolean hasTypeFeatureDeletedAnyOtherAnn = false;

	private Vector Flfil = new Vector();
	private Vector typefeatures = new Vector();

	/*****
	 * If TypeFeature.FeatureID = 'Q' then Range = 'RPQ' Else if
	 * TypeFeature.Feature first character is alphabetic (not numeric) then
	 * Range = "" Else Range = first 2 characters of TypeFeature.Feature + '00'
	 * (Example if Feature = 1234, range = 1200) End if
	 ******/

	/**
	 * TypeFeature constructor comment.
	 */
	public TypeFeature() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 4:18:18
	 * PM)
	 * 
	 * @param type
	 *            java.lang.String
	 * @param feature
	 *            java.lang.String
	 */
	// public TypeFeature(String go, String typ, String feat, String
	// anndocno,boolean mmlcPromoteSelect) throws HWPIMSAbnormalException{
	//
	// if(!mmlcPromoteSelect)
	// {
	// setTfgCollection(new TypeFeatureGeoCollection(go, typ, feat,
	// anndocno,mmlcPromoteSelect));
	// }
	// else
	// {
	// setTfgCollection(new TypeFeatureGeoCollection(go, typ, feat,
	// anndocno,mmlcPromoteSelect));
	// }
	//
	//
	// }

	/**
	 * Construct a Type Feature from a Type Feature ResultSet row.
	 * 
	 * @param rs
	 *            - the result set positioned to the proper row.
	 */
	// public TypeFeature (ResultSet rs)
	// throws HWPIMSAbnormalException
	// {
	// this();
	//
	// try
	// {
	// String type=convertNull(rs.getString("TYPE"));
	// String feature=convertNull(rs.getString("FEATURE"));
	//
	// setType(convertNull(rs.getString("TYPE")));
	// setFeature(convertNull(rs.getString("FEATURE")));
	// setAnnDocNo(convertNull(rs.getString("ANNDOCNO")));
	// setModificationReason(convertNull(rs.getString("MODIFICATIONREASON")));
	// if (1 == rs.getInt("CUSTOMERSETUP"))
	// {
	// setCustomerSetup(true);
	// }
	// else
	// {
	// setCustomerSetup(false);
	// }
	// if (1 == rs.getInt("CAPONDEMAND")) setCapOnDemand(true);
	// setFeatureID(convertNull(rs.getString("FEATUREID")));
	//
	// //RQ5437 - Remove attribute perCallClass
	// //setPerCallClass(convertNull(rs.getString("PERCALLCLASS")));
	//
	// setSpecFeatType(convertNull(rs.getString("SPECFEATTYPE")));
	// setDescription(convertNull(rs.getString("DESCRIPTION")));
	// if (1 == rs.getInt("MAINTELIGIBLE")) setMaintEligible(true);
	// if (1 == rs.getInt("NOCHARGERENTAL")) setNoChargeRental(true);
	// if (1 == rs.getInt("NOCHARGEPURCHASE")) setNoChargePurchase(true);
	//
	// if (1 == rs.getInt("NETPRICEMES")) setNetPriceMES(true);
	// if (1 == rs.getInt("SUPERCOMPUTER")) setSuperComputer(true);
	//
	// if (1 == rs.getInt("EA")) setEa(true);
	//
	// if (1 == rs.getInt("STOCKORDERABLE")) setStockOrderable(true);
	// if (1 == rs.getInt("PLANTINSTALLONLY")) setPlantInstallOnly(true);
	//
	// if (1 == rs.getInt("LOWEND")) setLowEnd(true);
	// if (1 == rs.getInt("ITEMRETURN")) setItemReturned(true);
	//
	// if (1 == rs.getInt("AASORDERABLE")) setAASOrderable(true);
	// if (1 == rs.getInt("CHANGED")) setChanged(true);
	//
	// //if (1 == rs.getInt("DELETED")) typefeature.setChanged(true);
	//
	// if (1 == rs.getInt("REMOVALCHARGE")) setRemovalCharge(true);
	// if (1 == rs.getInt("APPROVALRPQ")) setApprovalRPQ(true);
	//
	// if (1 == rs.getInt("UFLINKED")) setUFLinked(true);
	// }
	// catch (SQLException ex)
	// {
	// String msg = "Unable to populate Type Feature from ResultSet.";
	// throw new HWPIMSAbnormalException(msg, ex);
	// }
	//
	//
	// }

	/**
	 * Create a Type Feature from data retrieved from the data base.
	 */
	// public static TypeFeature getObject(String type, String feature, boolean
	// retrieveCollection)
	// throws HWPIMSAbnormalException
	// {
	// TypeFeature obj = null;
	// String schema =
	// ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
	// Connection db2con = null;
	// Statement stmt = null;
	// ResultSet rs = null;
	// String sql = "SELECT * FROM " + schema + ".TYPE_FEATURE"
	// + " WHERE Type = '"+ type + "'"
	// + " AND FEATURE ='" + feature + "'";
	//
	// try
	// {
	// db2con = DBConnectionManager.getInstance().getConnection();
	// stmt = db2con.createStatement();
	// rs = stmt.executeQuery(sql);
	// if (rs.next())
	// {
	// obj = new TypeFeature(rs);
	// }
	// }
	// catch (SQLException e)
	// {
	// String msg = "Unable to create Type Model from Db entry via SQL: " + sql;
	// HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
	// throw new HWPIMSAbnormalException(msg, e);
	// }
	// finally
	// {
	// DBConnectionManager.closeRSAndStatement(rs, stmt);
	// DBConnectionManager.closeConnection(db2con);
	// }
	// return obj;
	// }

	/**
	 * Insert the method's description here. Creation date: (5/24/2001 11:09:42
	 * AM)
	 * 
	 * @return java.lang.String
	 * @param tfObj
	 *            com.ibm.pprds.epimshw.TypeFeature
	 */
	public String calculateRange100() {
		String range;
		if (this.getFeatureID().equals("Q")) {
			range = "RPQ";
			return range;
		} else {
			int strLen = this.getFeature().length();
			for (int i = 0; i < strLen; i++) {
				if (Character.isLetter(this.getFeature().charAt(i))) {
					range = "";
					return range;
				}
			}
		}
		range = this.getFeature().substring(0, 1)
				+ this.getFeature().substring(1, 2) + "00";
		return range;
	}

	/**
	 * Insert the method's description here. Creation date: (5/24/2001 11:09:42
	 * AM)
	 * 
	 * @return java.lang.String
	 * @param tfObj
	 *            com.ibm.pprds.epimshw.TypeFeature
	 */
	// public String calculateRange1000() throws HWPIMSAbnormalException{
	// String range;
	// if (this.getFeatureID().equals("Q")) {
	// range = calculateRPQFeatureRange();
	// return range;
	// } else {
	// // RCQ00055238 changes - Ashutosh, Saurabh start
	//
	// /*int strLen = this.getFeature().length();
	// for (int i = 0; i < strLen; i++) {
	// if (Character.isLetter(this.getFeature().charAt(i))) {
	// range = "";
	// return range;
	// }
	// }*/
	//
	// if(ifAlphaNumeric()){
	// range = calculateAlphanumericFeatureRange();
	// return range;
	// }
	// // RCQ00055238 changes - Ashutosh, Saurabh end
	// }
	//
	// range=this.getFeature().substring(0,1)+"000";
	// return range;
	//
	//
	// }

	// RCQ00055238 changes - Ashutosh, Saurabh start
	/**
	 * checks whether the Feature Code contains any alphanumeric character or
	 * not and returns the boolean value accordingly.
	 * 
	 * @return boolean
	 */
	public boolean ifAlphaNumeric() {
		boolean isAlphaNumeric = false;

		int strLen = this.getFeature().length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isLetter(this.getFeature().charAt(i))) {
				isAlphaNumeric = true;
				return isAlphaNumeric;
			}
		}
		return isAlphaNumeric;
	}

	/**
	 * Calculates the range based on teh Feature Code (Alphanumeric or Numeric)
	 * 
	 * @return String
	 * @throws HWPIMSAbnormalException
	 */
	// private String calculateAlphanumericFeatureRange() throws
	// HWPIMSAbnormalException{
	// String range = "";
	// int recordCount = 0;
	//
	// String type = this.getType();
	// String feature = this.getFeature();
	//
	// String schema =
	// ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
	// Connection db2con = null;
	// Statement stmt = null;
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	//
	// // Get the range for the given Type and Feature
	// String sql = "SELECT * FROM " + schema + ".TYPE_FEAT_RANGE_LINK"
	// + " WHERE Type = '"+ type + "'"
	// + " AND FEATURE ='" + feature + "'";
	//
	// try
	// {
	// db2con = DBConnectionManager.getInstance().getConnection();
	//
	// stmt = db2con.createStatement();
	//
	// rs = stmt.executeQuery(sql);
	//
	// /* If the range is available for the given Type and Feature combination
	// Use the same as a Range */
	// if (rs.next())
	// {
	// range = rs.getString("range");
	// }else{
	// // Get range for given Type
	// sql = "SELECT type,range,count(*) as rangeCount FROM " + schema +
	// ".TYPE_FEAT_RANGE_LINK"
	// + " WHERE Type = '"+ type + "'" + " and length(feature)=4 "
	// + " group by type,range";
	//
	// rs = stmt.executeQuery(sql);
	//
	// while(rs.next()){
	// recordCount = rs.getInt(3);
	// range = rs.getString("range");
	// }
	//
	// // For the very first record in the Table
	// if(recordCount == 0){
	// range = "A000";
	// }
	//
	// // If there are 1000 records in the bucket for given Type
	// else if(recordCount>=999){
	// if(range.substring(1,4).equals("999")){
	// range = Character.toString((char)(range.charAt(0)+1)) + "000";
	// }else{
	// range = range.substring(0,1) +
	// getFOrmattedRange(Integer.toString(Integer.parseInt(range.substring(1,4))
	// + 1));;
	// }
	// }
	//
	// sql = "INSERT INTO " + schema + ".TYPE_FEAT_RANGE_LINK"
	// + "(TYPE, FEATURE, RANGE) VALUES('" + type + "', '" + feature + "', '" +
	// range + "')";
	//
	//
	// stmt.executeUpdate(sql);
	// }
	// }
	// catch (SQLException e)
	// {
	// String msg = "Unable to create Type Fature from Db entry via SQL: " +
	// sql;
	// HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
	// throw new HWPIMSAbnormalException(msg, e);
	// }
	// finally
	// {
	// DBConnectionManager.closeRSAndStatement(rs, stmt);
	// DBConnectionManager.closeConnection(db2con);
	// }
	//
	//
	// return range;
	//
	// }

	/**
	 * Calculates the range based on teh Feature Code (Alphanumeric or Numeric)
	 * 
	 * @return String
	 * @throws HWPIMSAbnormalException
	 */
	// private String calculateRPQFeatureRange() throws HWPIMSAbnormalException{
	// String range = "";
	// int recordCount = 0;
	//
	// String type = this.getType();
	// String feature = this.getFeature();
	//
	// String schema =
	// ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
	// Connection db2con = null;
	// Statement stmt = null;
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	//
	// // Get the range for the given Type and Feature
	// String sql = "SELECT * FROM " + schema + ".TYPE_FEAT_RANGE_LINK"
	// + " WHERE Type = '"+ type + "'"
	// + " AND FEATURE ='" + feature + "'";
	//
	// try
	// {
	// db2con = DBConnectionManager.getInstance().getConnection();
	//
	// stmt = db2con.createStatement();
	//
	// rs = stmt.executeQuery(sql);
	//
	// /* If the range is available for the given Type and Feature combination
	// Use the same as a Range */
	// if (rs.next())
	// {
	// range = rs.getString("range");
	// }else{
	// // Get range for given Type
	// sql = "SELECT type,range,count(*) as rangeCount FROM " + schema +
	// ".TYPE_FEAT_RANGE_LINK"
	// + " WHERE Type = '"+ type + "'" + " and length(feature)=6 "
	// + " group by type,range";
	//
	// rs = stmt.executeQuery(sql);
	//
	// while(rs.next()){
	// recordCount = rs.getInt(3);
	// range = rs.getString("range");
	// }
	//
	// // For the very first record in the Table
	// if(recordCount == 0){
	// range = "RPQ";
	// }
	//
	// // If there are 1000 records in the bucket for given Type
	// else if(recordCount>=999){
	// if(range.equalsIgnoreCase("RPQ")){
	//
	// range = Character.toString((char)('A')) + "000";
	// }else if(range.substring(1,4).equals("999"))
	//
	// range = Character.toString((char)(range.charAt(0)+1)) + "000";
	//
	// else
	// range = range.substring(0,1) +
	// getFOrmattedRange(Integer.toString(Integer.parseInt(range.substring(1,4))
	// + 1));;
	// }
	//
	// sql = "INSERT INTO " + schema + ".TYPE_FEAT_RANGE_LINK"
	// + "(TYPE, FEATURE, RANGE) VALUES('" + type + "', '" + feature + "', '" +
	// range + "')";
	//
	//
	// stmt.executeUpdate(sql);
	// }
	// }
	// catch (SQLException e)
	// {
	// String msg = "Unable to create Type Fature from Db entry via SQL: " +
	// sql;
	// HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
	// throw new HWPIMSAbnormalException(msg, e);
	// }
	// finally
	// {
	// DBConnectionManager.closeRSAndStatement(rs, stmt);
	// DBConnectionManager.closeConnection(db2con);
	// }
	//
	//
	// return range;
	//
	// }

	/**
	 * Returns the formatted Range to the calling function
	 * 
	 * @return String
	 */
	private String getFOrmattedRange(String range) {
		if ((Integer.parseInt(range) / 100) >= 1) {
			return range;
		} else if ((Integer.parseInt(range) / 10) >= 1) {
			return "0" + range;
		} else {
			return "00" + range;
		}
	}

	// RCQ00055238 changes - Ashutosh, Saurabh end

	/**
	 * Insert the method's description here. Creation date: (5/2/2001 11:29:29
	 * AM)
	 * 
	 * @return int
	 */
	public boolean getAASOrderable() {
		return AASOrderable;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:07:40
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAnnDocNo() {
		return annDocNo;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 9:38:09
	 * AM)
	 * 
	 * @return boolean
	 */
	public boolean getApprovalRPQ() {
		return ApprovalRPQ;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:11:01
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getCustomerSetup() {
		return customerSetup;
	}

	public boolean getCapOnDemand() {
		return capOnDemand;
	}

	public boolean getChanged() {
		return Changed;
	}

	public boolean getDeleted() {
		return Deleted;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:56
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:56:52
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getEa() {
		return ea;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:37
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFeature() {
		return feature;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:49:16
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFeatureID() {
		return featureID;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:58:49
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getItemReturned() {
		return itemReturned;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:58:33
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getLowEnd() {
		return lowEnd;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:16:24
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getMaintEligible() {
		return maintEligible;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:09:48
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getModificationReason() {
		return modificationReason;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:17:57
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getNetPriceMES() {
		return netPriceMES;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:17:29
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getNoChargePurchase() {
		return noChargePurchase;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:16:57
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getNoChargeRental() {
		return noChargeRental;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:12:33
	 * PM)
	 * 
	 * @return java.lang.String
	 */

	// RQ5437 - Remove attribute perCallClass

	/*
	 * public java.lang.String getPerCallClass() { return perCallClass; }
	 */

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:57:52
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getPlantInstallOnly() {
		return plantInstallOnly;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 9:37:36
	 * AM)
	 * 
	 * @return boolean
	 */
	public boolean getRemovalCharge() {
		return RemovalCharge;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:15:17
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSpecFeatType() {
		return specFeatType;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:57:25
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getStockOrderable() {
		return stockOrderable;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:18:27
	 * PM)
	 * 
	 * @return int
	 */
	public boolean getSuperComputer() {
		return superComputer;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 2:00:10
	 * PM)
	 * 
	 * @return com.ibm.pprds.epimshw.TypeFeatureGeoCollection
	 */
	// public TypeFeatureGeoCollection getTfgCollection() {
	// return tfgCollection;
	// }
	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:09
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Insert the method's description here. Creation date: (5/23/2001 5:50:40
	 * PM)
	 * 
	 * @return boolean
	 */
	public boolean getUFLinked() {
		return UFLinked;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 11:58:59
	 * AM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getWarrantyPer() {
		return warrantyPer;
	}

	public boolean isRPQ() {
		boolean ans = false;

		if (this.getFeatureID().equals("Q")) {
			ans = true;
		} else {
			ans = false;
		}

		return ans;
	}

	/**
	 * Insert the method's description here. Creation date: (5/2/2001 11:29:29
	 * AM)
	 * 
	 * @param newAASOrderable
	 *            int
	 */
	public void setAASOrderable(boolean newAASOrderable) {
		AASOrderable = newAASOrderable;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:07:40
	 * PM)
	 * 
	 * @param newAnnDocNo
	 *            java.lang.String
	 */
	public void setAnnDocNo(java.lang.String newAnnDocNo) {
		annDocNo = newAnnDocNo;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 9:38:09
	 * AM)
	 * 
	 * @param newApprovalRPQ
	 *            boolean
	 */
	public void setApprovalRPQ(boolean newApprovalRPQ) {
		ApprovalRPQ = newApprovalRPQ;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:11:01
	 * PM)
	 * 
	 * @param newCustomerSetup
	 *            int
	 */
	public void setCustomerSetup(boolean newCustomerSetup) {
		customerSetup = newCustomerSetup;
	}

	public void setCapOnDemand(boolean newCapOnDemand) {
		capOnDemand = newCapOnDemand;
	}

	public void setChanged(boolean newChanged) {
		Changed = newChanged;
	}

	public void setDeleted(boolean newDeleted) {
		Deleted = newDeleted;
	}

	public boolean gethasTypeFeaturePromotedAnyotherAnn() {
		return hasTypeFeaturePromotedAnyAnn;
	}

	public void sethasTypeFeaturePromotedAnyotherAnn(boolean hasPromotedAny) {
		this.hasTypeFeaturePromotedAnyAnn = hasPromotedAny;
	}

	public boolean gethasTypeFeaturePromoted() {
		return hasTypeFeaturePromoted;
	}

	public void sethasTypeFeaturePromoted(boolean hasPromoted) {
		this.hasTypeFeaturePromoted = hasPromoted;
	}

	public boolean gethasTypeFeaturePromotedAndChanged() {
		return hasTypeFeaturePromoted;
	}

	public void sethasTypeFeaturePromotedAndChanged(boolean hasChanged) {
		this.hasTypeFeaturePromotedAndChanged = hasChanged;
	}

	public boolean gethasTypeFeatureDeletedAnyotherAnn() {
		return hasTypeFeatureDeletedAnyOtherAnn;
	}

	public void sethasTypeFeatureDeletedAnyotherAnn(boolean hasDeletedAny) {
		this.hasTypeFeatureDeletedAnyOtherAnn = hasDeletedAny;
	}

	public boolean gethasTypeFeatureDeleted() {
		return hasTypeFeatureDeleted;
	}

	public void sethasTypeFeatureDeleted(boolean hasDeleted) {
		this.hasTypeFeatureDeleted = hasDeleted;
	}

	public Vector getFlfilCol() {
		return Flfil;
	}

	public void setFlfilCol(Vector vecFifl) {
		this.Flfil = vecFifl;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:56
	 * AM)
	 * 
	 * @param newDescription
	 *            java.lang.String
	 */
	public void setDescription(java.lang.String newDescription) {
		description = newDescription;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:56:52
	 * PM)
	 * 
	 * @param newEa
	 *            int
	 */
	public void setEa(boolean newEa) {
		ea = newEa;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:37
	 * AM)
	 * 
	 * @param newFeature
	 *            java.lang.String
	 */
	public void setFeature(java.lang.String newFeature) {
		feature = newFeature;
	}

	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:49:16
	 * AM)
	 * 
	 * @param newFeatureID
	 *            java.lang.String
	 */
	public void setFeatureID(java.lang.String newFeatureID) {
		featureID = newFeatureID;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:58:49
	 * PM)
	 * 
	 * @param newItemReturned
	 *            int
	 */
	public void setItemReturned(boolean newItemReturned) {
		itemReturned = newItemReturned;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:58:33
	 * PM)
	 * 
	 * @param newLowEnd
	 *            int
	 */
	public void setLowEnd(boolean newLowEnd) {
		lowEnd = newLowEnd;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:16:24
	 * PM)
	 * 
	 * @param newMaintEligible
	 *            int
	 */
	public void setMaintEligible(boolean newMaintEligible) {
		maintEligible = newMaintEligible;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:09:48
	 * PM)
	 * 
	 * @param newModificationReason
	 *            java.lang.String
	 */
	public void setModificationReason(java.lang.String newModificationReason) {
		modificationReason = newModificationReason;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:17:57
	 * PM)
	 * 
	 * @param newNetPriceMES
	 *            int
	 */
	public void setNetPriceMES(boolean newNetPriceMES) {
		netPriceMES = newNetPriceMES;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:17:29
	 * PM)
	 * 
	 * @param newNoChargePurchase
	 *            int
	 */
	public void setNoChargePurchase(boolean newNoChargePurchase) {
		noChargePurchase = newNoChargePurchase;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:16:57
	 * PM)
	 * 
	 * @param newNoChargeRental
	 *            int
	 */
	public void setNoChargeRental(boolean newNoChargeRental) {
		noChargeRental = newNoChargeRental;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:12:33
	 * PM)
	 * 
	 * @param newPerCallClass
	 *            java.lang.String
	 */

	// RQ5437 - Remove attribute perCallClass

	/*
	 * public void setPerCallClass(java.lang.String newPerCallClass) {
	 * perCallClass = newPerCallClass; }
	 */

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:57:52
	 * PM)
	 * 
	 * @param newPlantInstallOnly
	 *            int
	 */
	public void setPlantInstallOnly(boolean newPlantInstallOnly) {
		plantInstallOnly = newPlantInstallOnly;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 9:37:36
	 * AM)
	 * 
	 * @param newRemovalCharge
	 *            boolean
	 */
	public void setRemovalCharge(boolean newRemovalCharge) {
		RemovalCharge = newRemovalCharge;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:15:17
	 * PM)
	 * 
	 * @param newSpecFeatType
	 *            java.lang.String
	 */
	public void setSpecFeatType(java.lang.String newSpecFeatType) {
		specFeatType = newSpecFeatType;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:57:25
	 * PM)
	 * 
	 * @param newStockOrderable
	 *            int
	 */
	public void setStockOrderable(boolean newStockOrderable) {
		stockOrderable = newStockOrderable;
	}

	/**
	 * Insert the method's description here. Creation date: (4/26/2001 2:18:27
	 * PM)
	 * 
	 * @param newSuperComputer
	 *            int
	 */
	public void setSuperComputer(boolean newSuperComputer) {
		superComputer = newSuperComputer;
	}

	/**
	 * Insert the method's description here. Creation date: (4/30/2001 2:00:10
	 * PM)
	 * 
	 * @param newTfgCollection
	 *            com.ibm.pprds.epimshw.TypeFeatureGeoCollection
	 */
	// public void setTfgCollection(TypeFeatureGeoCollection newTfgCollection) {
	// tfgCollection = newTfgCollection;
	// }
	//
	// public void setTfsoCollection(TypeFeatureSalesOrgCollection
	// newTfgsoCoolection){
	// tfsoCollection = newTfgsoCoolection;
	// }
	/**
	 * Insert the method's description here. Creation date: (4/19/2001 11:47:09
	 * AM)
	 * 
	 * @param newType
	 *            java.lang.String
	 */
	public void setType(java.lang.String newType) {
		type = newType;
	}

	/**
	 * Insert the method's description here. Creation date: (5/23/2001 5:50:40
	 * PM)
	 * 
	 * @param newUFLinked
	 *            boolean
	 */
	public void setUFLinked(boolean newUFLinked) {
		UFLinked = newUFLinked;
	}

	/**
	 * Insert the method's description here. Creation date: (5/30/2001 11:58:59
	 * AM)
	 * 
	 * @param newWarrantyPer
	 *            java.lang.String
	 */
	public void setWarrantyPer(java.lang.String newWarrantyPer) {
		warrantyPer = newWarrantyPer;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString() {

		StringBuffer s = new StringBuffer(1200);

		s.append("type >> " + type + "\n");
		s.append("feature >> " + feature + "\n");
		s.append("annDocNo >> " + annDocNo + "\n");
		s.append("modificationReason >> " + modificationReason + "\n");
		s.append("customerSetup >> " + customerSetup + "\n");
		s.append("featureID >> " + featureID + "\n");

		// RQ5437 - Remove attribute perCallClass
		// s.append("perCallClass >> " + perCallClass+"\n");

		s.append("specFeatType >> " + specFeatType + "\n");

		s.append("description >> " + description + "\n");
		s.append("maintEligible >> " + maintEligible + "\n");
		s.append("noChargeRental >> " + noChargeRental + "\n");
		s.append("noChargePurchase >> " + noChargePurchase + "\n");
		s.append("netPriceMES >> " + netPriceMES + "\n");
		s.append("superComputer >> " + superComputer + "\n");
		s.append("ea >> " + ea + "\n");
		s.append("stockOrderable >> " + stockOrderable + "\n");
		s.append("plantInstallOnly >> " + plantInstallOnly + "\n");

		s.append("lowEnd >> " + lowEnd + "\n");
		s.append("itemReturned >> " + itemReturned + "\n");
		// s.append("descrChanged >> "+ descrChanged+"\n");

		s.append("Changed >> " + Changed + "\n");

		s.append("AASOrderable >> " + AASOrderable + "\n");

		s.append("warrantyPeriod >> " + warrantyPer + "\n");

		// s.append(tfgCollection.toString());

		return s.toString();
	}

	public void cleanup() {
		// setTfgCollection(null);
	}

	/**
	 * convertNull converts the input object to an empty String if it is the
	 * null object Creation date: (5/9/00 9:04:25 PM)
	 * 
	 * @return java.lang.String
	 * @param input
	 *            java.lang.String
	 */
	public String convertNull(String input) {
		if (input == null) {
			return "";
		} else {
			return input;
		}
	}

	// public boolean getAnyGeoNew( Vector tfGeos) throws
	// HWPIMSAbnormalException
	// {
	// boolean isPromoted=false;
	// int tfgSize = tfGeos.size();
	// for (int j = 0; j < tfgSize; j++) {
	// TypeFeatureGeo tfGeoObj = (TypeFeatureGeo) tfGeos.elementAt(j);
	// if(!tfGeoObj.getPromoted()){
	// isPromoted = false;
	// break;
	// }
	// else{
	// isPromoted = true;
	// }
	// }
	// return isPromoted;
	// }
	//
	// public boolean getAnyGeoChanged( Vector tfGeos) throws
	// HWPIMSAbnormalException
	// {
	// boolean isChanged=false;
	// int tfgSize = tfGeos.size();
	// for (int j = 0; j < tfgSize; j++) {
	// TypeFeatureGeo tfGeoObj = (TypeFeatureGeo) tfGeos.elementAt(j);
	// if(tfGeoObj.isChanged()){
	// isChanged = true;
	// break;
	// }
	// else{
	// isChanged = false;
	// }
	// }
	// return isChanged;
	// }
	//
	// public boolean getAnyGeoDeleted( Vector tfGeos) throws
	// HWPIMSAbnormalException
	// {
	// boolean isDeleted=false;
	// int tfgSize = tfGeos.size();
	// for (int j = 0; j < tfgSize; j++) {
	// TypeFeatureGeo tfGeoObj = (TypeFeatureGeo) tfGeos.elementAt(j);
	// if(tfGeoObj.getDeleted()){
	// isDeleted = true;
	// break;
	// }
	// else{
	// isDeleted = false;
	// }
	// }
	// return isDeleted;
	// }

	public Vector getTfCollection() {
		return typefeatures;
	}

	// public boolean getAllGeosDeleted( Vector tfGeos) throws
	// HWPIMSAbnormalException
	// {
	// boolean isAllGeosDeleted=false;
	// int tfgSize = tfGeos.size();
	// for (int j = 0; j < tfgSize; j++) {
	// TypeFeatureGeo tfGeoObj = (TypeFeatureGeo) tfGeos.elementAt(j);
	// if(!tfGeoObj.getDeleted()){
	// isAllGeosDeleted = false;
	// break;
	// }
	// else{
	// isAllGeosDeleted = true;
	// }
	// }
	// return isAllGeosDeleted;
	// }
	//
	// public boolean getAllGeosPromoted( Vector tfGeos) throws
	// HWPIMSAbnormalException
	// {
	// boolean isAllGeosPromoted=false;
	// int tfgSize = tfGeos.size();
	// for (int j = 0; j < tfgSize; j++) {
	// TypeFeatureGeo tfGeoObj = (TypeFeatureGeo) tfGeos.elementAt(j);
	// if(!tfGeoObj.getPromoted()){
	// isAllGeosPromoted = false;
	// break;
	// }
	// else{
	// isAllGeosPromoted = true;
	// }
	// }
	// return isAllGeosPromoted;
	// }

}
