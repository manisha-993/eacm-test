package com.ibm.pprds.epimshw.revenue;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author laxmi
 *
 */

public class RevProfile extends RevenueObject implements Serializable
{
	
	public String _revenueProfile;
	public Vector _auoMaterials = new Vector();
	public Vector _typeModelRevs = new Vector();

	public RevProfile() {
		super();
	}
	
//	public RevProfile(String revProfileString)  
//		throws HWPIMSAbnormalException 
//	{
//		this(revProfileString, false);
//	}
	
//	public RevProfile(String revProfileString, boolean refresh)  
//		throws HWPIMSAbnormalException 
//	{
//		this.setRevenueProfile(revProfileString) ;
//		if(! refresh)
//		{
//			loadAuoMaterialData(revProfileString);	
//		}
//		else
//		{
//			getLogger().debug("Loading Auo Materials and Type Model Data");
//			loadAuoMaterialData(revProfileString);	
//			loadTypeModelData(revProfileString);	
//		}
//	}

	/**
	 * Retrieve and populate the AUO Material data for this profile.	
	 * 
	 * @param revProfileString - the revenue profile name.
	 */
//	private void loadAuoMaterialData(String revProfileString) 
//		throws HWPIMSAbnormalException
//	{
//		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//
//		Connection db2con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			db2con = DBConnectionManager.getInstance().getConnection();
//			  stmt = db2con.createStatement();
//			  //getLogger().debug("Before AUO Material Result Set " + revProfileString);
//			  rs = stmt.executeQuery("SELECT  MATERIAL, PERCENTAGE"
//			  	+ " FROM " + schema + ".REV_PROF_SPLIT"
//			  	+ " WHERE REVENUEPROFILE ='"+revProfileString
//			  	+"' order by MATERIAL");
//			 // display the result set
//			 while (rs.next()) 
//			 {
//				 AUOMaterial auoMaterial =  new AUOMaterial(rs.getString(1), rs.getString(2)) ;
//				 _auoMaterials.add(auoMaterial) ;
//				 getLogger().debug("Add AUO Material: " + auoMaterial);
//			 }
//			 this.setAUOMaterial(_auoMaterials) ;
//		}	
//		catch (SQLException e) 
//		{
//			HWPIMSLog.Write("Error - SQL exception while getting data from REV_PROF_SPLIT. \n"
//					+ ExceptionUtility.getStackTrace(e),"E");
//			throw new HWPIMSAbnormalException("Error - SQL exception while getting data from REV_PROF_SPLIT. \n",e);
//		}
//		finally
//		{
//		
//			DBConnectionManager.closeRSAndStatement(rs, stmt);
//			DBConnectionManager.closeConnection(db2con);
//		}
//	}


	/**
	 * Retrieve and populate the Type Model
	 * Queries DB for Type Model information for the revenue profile
	 * and loads object data appropriately.
	 * 
	 * @param revProfileString - the revenue profile name.
	 */
//	private void loadTypeModelData(String revProfileString) 
//		throws HWPIMSAbnormalException
//	{
//		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//
//		Connection db2con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String query = null;
//		try {
//			db2con = DBConnectionManager.getInstance().getConnection();
//			  stmt = db2con.createStatement();
//			  String basicQuery = "select b.type , b.model, a.allmodels"
//			  		+ " from " + schema + ".REV_PROF_MATS a ," 
//			  		+ schema + ".type_model_geo b "
//			  		+ "where a.type = b.type"
//			  		+ " and b.promoted = 1"
//			  		+ " and geography = 'US'" 
//			  		+ " and a.RevenueProfile = '" + revProfileString+ "'";
//			  query = basicQuery 	
//			  		+ " and a.Allmodels = 1 " 
//			  		+ "union " 
//			  		+ basicQuery
//			  		+ " and a.model = b.model"
//			  		+ " and a.allmodels = 0" ;
//			  getLogger().debug("Type Model Rev Query: " + query);
//			  rs = stmt.executeQuery(query) ;
//			/*
//			 * Add data to typeModelRev vector for each row
//			 */			  
//			 while (rs.next()) 
//			 {
//				 TypeModelRev typeModelRev =  new TypeModelRev(rs.getString(1), rs.getString(2),null, rs.getString(3)) ;
//				 _typeModelRevs.add(typeModelRev) ;
//				 getLogger().debug("Add element to Type Model Revs vector: " + typeModelRev);
//			 }
//			 this.setTypeModelRev(_typeModelRevs) ;
//		}	
//		
//		catch (SQLException e) {
//			HWPIMSLog.Write("Error - SQL exception while retrieveing Type Model Rev. \n"
//					+ " SQL: " + query
//					+ ExceptionUtility.getStackTrace(e),"E");
//			throw new HWPIMSAbnormalException("Error - SQL exception while retrieveing Type Model Rev. \n",e);
//		}
//		finally
//		{
//		
//			DBConnectionManager.closeRSAndStatement(rs, stmt);
//			DBConnectionManager.closeConnection(db2con);
//		}
//		
//	}


	public void setRevenueProfile ( String value )
	{
		_revenueProfile = value ;
	}

	public String getRevenueProfile()
	{
		return _revenueProfile ;
	}

	public void setAUOMaterial(Vector auoMaterials)
	{
		_auoMaterials = auoMaterials ;
	}

	public Vector getAUOMaterial()
	{
		return _auoMaterials ;
	}

	public void setTypeModelRev(Vector typeModelRevs)
	{
		_typeModelRevs = typeModelRevs ;
	}

	public Vector getTypeModelRev()
	{
		return _typeModelRevs ;
	}
}
