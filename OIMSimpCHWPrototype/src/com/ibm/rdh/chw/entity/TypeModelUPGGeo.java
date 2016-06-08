package com.ibm.rdh.chw.entity;

import org.apache.log4j.Logger;
//import com.ibm.pprds.epimshw.revenue.RevProfile;
//import com.ibm.pprds.epimshw.revenue.TypeModelRev;
//import com.ibm.pprds.epimshw.util.ConfigManager;

import com.ibm.pprds.epimshw.util.LogManager;
//import com.ibm.pprds.epimshw.wdfm.TypeModelUPGGeoWDFM;
/**
 * Insert the type's description here.
 * Creation date: (4/19/2001 11:50:17 AM)
 * @author: Lizsolette Williams
 */
public class TypeModelUPGGeo {
	
	public static Logger logger = LogManager.getLogManager().getPromoteLogger();
	
	private java.lang.String model;
	private java.lang.String fromModel;
	private java.lang.String fromType;
	private boolean promoted=false;
	private java.lang.String type;
	private java.lang.String Geography;
	private java.lang.String AnnDocNo;
	private boolean deleted=false;
	private java.lang.String description;
	private java.lang.String productHierarchy;
	private java.lang.String loadingGroup;
	
	private boolean isFromTMRevProfileExist;
	private boolean isToTMRevProfileExist;

//	private TypeModelRev FromTmRev;
//	private TypeModelRev ToTmRev;
//	
//	public TypeModelRev getFromTmRev() {
//		return FromTmRev;
//	}
//	
//	public void setFromTmRev(TypeModelRev fromnewtmRev) {
//		FromTmRev = fromnewtmRev;
//	}
//	
//	public TypeModelRev getToTmRev() {
//		return ToTmRev;
//	}
//	
//	public void setToTmRev(TypeModelRev tonewtmRev) {
//		ToTmRev = tonewtmRev;
//	}


/**
 * TypeModelUPG constructor comment.
 */
public TypeModelUPGGeo() {
	super();
}

//	/**
//	 * Create a Type Model Upgrade from the current row in a result set.
//	 */
//	public TypeModelUPGGeo(ResultSet rs) throws HWPIMSAbnormalException
//	{
//		this();
//		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs2 = null;
//		try
//		{	
//		 	String type =rs.getString("TYPE");
//		 	String model =rs.getString("MODEL");
//		 	String dbgeo=rs.getString("GEOGRAPHY");
//
//			setAnnDocNo(ConversionUtility.convertNull(rs.getString("ANNDOCNO")));
//			setFromModel(ConversionUtility.convertNull(rs.getString("FROMMODEL")));
//			setFromType(ConversionUtility.convertNull(rs.getString("FROMTYPE")));
//			setType(ConversionUtility.convertNull(type));
//			setModel(ConversionUtility.convertNull(model));
//			setGeography(ConversionUtility.convertNull(dbgeo));
//			 
//			if ( 1 == rs.getInt("PROMOTED")) setPromoted(true);
//			if ( 1 == rs.getInt("DELETED"))setDeleted(true);
//			 
//			conn = DBConnectionManager.getInstance().getConnection();			 
//		    stmt = conn.createStatement(); 
//			rs2 = stmt.executeQuery("SELECT PRODUCTHIERARCHY,DESCRIPTION FROM "
//			 	 + schema + ".TYPE_MODEL"
//			 	 + " WHERE TYPE ='" + type +"' and MODEL='"+ model +"'");		     
//			if(rs2.next())
//			{
//			 	setProductHierarchy(rs2.getString("PRODUCTHIERARCHY"));
//			 	setDescription(rs2.getString("DESCRIPTION"));
//			}
//			rs2.close();
//			stmt.close();
//			 
//			stmt = conn.createStatement(); 
//			rs2 = stmt.executeQuery("select a.LOADINGGROUP from " + schema +".PLANT a ," 
//				+ schema +".TYPE_MODEL_GEO b"
//				+ " where b.type = '" + type +"' and b.model = '" + model +"'"
//				+ " and a.plant = b.plant" 
//				+ " and GEOGRAPHY= '"+ dbgeo +"'");
//			if(rs2.next())
//			{
//				String loadgrp = rs2.getString("LOADINGGROUP");
//				if(loadgrp != null)
//				{
//					setLoadingGroup(loadgrp);
//				}
//				else
//				{
//					setLoadingGroup("H001");
//				}		
//			}	
//			retrieveFromRefProfile(conn, schema, this, AnnDocNo) ;	
//			retrieveToRefProfile(conn, schema, this, AnnDocNo) ;
//		}
//		catch (SQLException ex)
//		{
//			String msg = "Unable to create TypeModelUPGGeo from a result set.";
//			HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(ex),"E");
//			throw new HWPIMSAbnormalException(msg, ex);
//		}
//		finally
//		{
//			DBConnectionManager.closeRSAndStatement(rs2, stmt);
//			DBConnectionManager.closeConnection(conn);
//		}
//	}



/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:06:02 AM)
 * @return java.lang.String
 */
public java.lang.String getAnnDocNo() {
	return AnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:09 AM)
 * @return java.lang.String
 */
public java.lang.String getFromModel() {
	return fromModel;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:27 AM)
 * @return java.lang.String
 */
public java.lang.String getFromType() {
	return fromType;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:05:30 AM)
 * @return java.lang.String
 */
public java.lang.String getGeography() {
	return Geography;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:50:51 AM)
 * @return java.lang.String
 */
public java.lang.String getModel() {
	return model;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:51 AM)
 * @return java.lang.Boolean
 */
public boolean getPromoted() {
	return promoted;
}

public boolean getDeleted() {
	return deleted;
}

public java.lang.String getProductHierarchy() {
	return productHierarchy;
}

public java.lang.String getDescrption() {
	return description;
}

public java.lang.String getLoadingGroup() {
	return loadingGroup;
}

/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:57:20 AM)
 * @return java.lang.String
 */
public java.lang.String getType() {
	return type;
}


public boolean isMTC() {
	boolean ans = false;
	
	if (this.getFromType().equals(this.getType())) {
		ans = false;
	} else {
		ans = true;
	}
	return ans;
}


/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:06:02 AM)
 * @param newAnnDocNo java.lang.String
 */
public void setAnnDocNo(java.lang.String newAnnDocNo) {
	AnnDocNo = newAnnDocNo;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:09 AM)
 * @param newFromModel java.lang.String
 */
public void setFromModel(java.lang.String newFromModel) {
	fromModel = newFromModel;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:27 AM)
 * @param newFromType java.lang.String
 */
public void setFromType(java.lang.String newFromType) {
	fromType = newFromType;
}
/**
 * Insert the method's description here.
 * Creation date: (4/30/2001 10:05:30 AM)
 * @param newGeography java.lang.String
 */
public void setGeography(java.lang.String newGeography) {
	Geography = newGeography;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:50:51 AM)
 * @param newModel java.lang.String
 */
public void setModel(java.lang.String newModel) {
	model = newModel;
}
/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:51:51 AM)
 * @param newPromoted java.lang.Boolean
 */
public void setPromoted(boolean newPromoted) {
	promoted = newPromoted;
}


public void setDeleted(boolean newDeleted) {
	deleted = newDeleted;
}

public void setProductHierarchy(java.lang.String productHiear) {
	productHierarchy = productHiear;
}

public void setDescription(java.lang.String descrp) {
	description = descrp;
}

public void setLoadingGroup(java.lang.String loadinggrp) {
	loadingGroup = loadinggrp;
}


/**
 * Insert the method's description here.
 * Creation date: (4/19/2001 11:57:20 AM)
 * @param newType java.lang.String
 */
public void setType(java.lang.String newType) {
	type = newType;
}


public boolean isFromTMRevProfileExist() {
	return isFromTMRevProfileExist;
}

public void setFromTMRevProfileExist(boolean isFromTMRevProfileExist) {
	this.isFromTMRevProfileExist = isFromTMRevProfileExist;
}

public boolean isToTMRevProfileExist() {
	return isToTMRevProfileExist;
}

public void setToTMRevProfileExist(boolean isToTMRevProfileExist) {
	this.isToTMRevProfileExist = isToTMRevProfileExist;
}

/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
public String toString() {
	
	StringBuffer s= new StringBuffer(300);

	s.append("model >> " + model+"\n");
	s.append("fromModel >> " + fromModel+"\n");
	s.append("fromType >> "+fromType+"\n");
	s.append("promoted >> "+ promoted+"\n");
	s.append("type >> "+ type+"\n");
	s.append("Geography >> " + Geography+"\n");
	s.append("AnnDocNo >> " + AnnDocNo+"\n");
	s.append("Deleted >> " + deleted+"\n");
	s.append("Loading Group >> " +loadingGroup+"\n");
	s.append("Description >> " +description+"\n");
	s.append("Product Hierarchy >> " +productHierarchy+"\n");
	s.append("From TypeModel RevProfile Exist >> " +isFromTMRevProfileExist+"\n");
	s.append("To TypeModel RevProfile Exist >> " +isToTMRevProfileExist+"\n");
	
	return s.toString();
}


	/**
	 * Get the associated WDFM TMUG object.
	 * 
	 * @return the associated WDFM TMUG object.  
	 * Null if is does not exist.
	 */ 	
//	public TypeModelUPGGeoWDFM getWdfmTmug()
//		throws HWPIMSAbnormalException
//	{
//		TypeModelUPGGeoWDFM obj = TypeModelUPGGeoWDFM.getObject(getFromType(), getFromModel(), getType(), getModel(), getGeography());
//		return obj;
//	}			


//	public static TypeModelUPGGeo getObject(String fromType, String fromModel, String type, String model, String geo)
//		throws HWPIMSAbnormalException
//	{
//		TypeModelUPGGeo obj = null;
//		
//		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//		
//		java.sql.Connection db2con = null;
//		java.sql.Statement stmt = null;
//		java.sql.ResultSet rs = null;
//		String sql = "SELECT * FROM " + schema + ".TYPE_MOD_UPG_GEO_W"
//			+ " WHERE FROMTYPE ='" + fromType +"'"
//			+ " AND FROMMODEL ='" + fromModel +"'"
//			+ " AND TYPE ='" + type +"'"
//			+ " AND MODEL ='" + model +"'"
//			+ " AND GEOGRAPHY ='" + geo +"'";
//		
//		try 
//		{	
//			db2con = DBConnectionManager.getInstance().getConnection();
//			stmt = db2con.createStatement();
//			rs = stmt.executeQuery(sql);
//			if (rs.next()) 
//			{
//				obj = new TypeModelUPGGeo(rs);
//			}
//		}	
//		catch (SQLException e)
//		{
//			String msg = "Unable to retrieve type model upgrade geo via SQL: " + sql;
//			HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
//			throw new HWPIMSAbnormalException(msg, e);
//		}	
//		finally
//		{
//			DBConnectionManager.closeRSAndStatement(rs, stmt);
//			DBConnectionManager.closeConnection(db2con);
//		}	
//		return obj;
//	}


//	public static void retrieveFromRefProfile(Connection db2con, String schema, TypeModelUPGGeo tmugeo, String annDocNo)
//		throws HWPIMSAbnormalException
//	{
//		String revProfileString;
//		TypeModelRev typeModelRev = null ;
//		Statement stmt2 = null ;
//		ResultSet rs2 = null ;
//		Hashtable revprofilesHashtable = new Hashtable();
//		RevProfile revProfile  = null ;
//		try
//		{
//			stmt2 = db2con.createStatement();
//			rs2 = stmt2.executeQuery("SELECT RevenueProfile FROM " + schema 
//					+ ".REV_PROF_MATS WHERE TYPE = '"+tmugeo.getFromType() 
//					+ "'AND (ALLMODELS=1 OR MODEL='"+tmugeo.getFromModel()+"' )");	 
//			 // Do we need this ....	
//
//			 typeModelRev = new TypeModelRev(tmugeo.getFromType(),tmugeo.getFromModel(),annDocNo);
//			 
//			  while (rs2.next()) 
//			  {
//				  revProfileString = rs2.getString("REVENUEPROFILE");
//				  logger.debug("From Type **** Revenue Profile String *****"+revProfileString);
//				  revProfile = new RevProfile(revProfileString);
//				  typeModelRev.setRevProfile(revProfile) ;
//  			      logger.debug("typeModelRev.getRevProfile  From Type **** "+typeModelRev.getRevProfile().getRevenueProfile());
//			  }  	
//	
//			 tmugeo.setFromTmRev(typeModelRev) ;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace() ;
//			throw new HWPIMSAbnormalException(e.getMessage()) ;
//		}
//		finally
//		{
//			DBConnectionManager.closeRSAndStatement(rs2, stmt2);
//		}
//
//	}
//
//public static void retrieveToRefProfile(Connection db2con, String schema, TypeModelUPGGeo tmugeo, String annDocNo)
//		throws HWPIMSAbnormalException
//	{
//		String revProfileString;
//		TypeModelRev typeModelRev = null ;
//		Statement stmt2 = null ;
//		ResultSet rs2 = null ;
//		Hashtable revprofilesHashtable = new Hashtable();
//		RevProfile revProfile  = null ;
//		try
//		{
//			stmt2 = db2con.createStatement();
//			rs2 = stmt2.executeQuery("SELECT RevenueProfile FROM " + schema 
//										     	+ ".REV_PROF_MATS WHERE TYPE = '"+tmugeo.getType() +
//										     	"'AND (ALLMODELS=1 OR MODEL='"+tmugeo.getModel()+"' )");	 
//			 // Do we need this ....	
//
//			 typeModelRev = new TypeModelRev(tmugeo.getType(),tmugeo.getModel(),annDocNo);
//			 
//			  while (rs2.next()) 
//			  {
//				 			 
//				  revProfileString = rs2.getString("REVENUEPROFILE");
//				  logger.debug("To Type *****  Revenue Profile String *****"+revProfileString);
//				  
//				  revProfile = new RevProfile(revProfileString);
//				  typeModelRev.setRevProfile(revProfile) ;
//				  logger.debug("typeModelRev.getRevProfile  To Type **** "+typeModelRev.getRevProfile());
//			  }  	
//	
//			 tmugeo.setToTmRev(typeModelRev) ;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace() ;
//			throw new HWPIMSAbnormalException(e.getMessage()) ;
//		}
//		finally
//		{
//			DBConnectionManager.closeRSAndStatement(rs2, stmt2);
//		}
//
//	}

}
