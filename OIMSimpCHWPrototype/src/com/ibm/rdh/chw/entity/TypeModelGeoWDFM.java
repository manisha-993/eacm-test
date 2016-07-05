package com.ibm.rdh.chw.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.ExceptionUtility;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.HWPIMSLog;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.ConversionUtility;
/**
 * @author gregj
 *
 */
public class TypeModelGeoWDFM  implements java.io.Serializable {
	
	private String anndocno;
	private String type;
	private String model;
	private String geography;
	private java.util.Date wdfmDate;
	private boolean promoted = false;
	private boolean changed = false;
	private boolean deleted = false;
	private Timestamp qsmUpdateTs = null;
	private String qsmUpdatedBy = null;
	private Date announcementDate = null;
	//RQ0724066720
	//private String segmentAcronym;
	private String div;
	
	/** 
	 * Vector of fulfillment indicator for type model
	 */
	private Vector flfilV= new Vector(2);

	private String _annAnnDocNo = null;	

	public TypeModelGeoWDFM(String theAnndocno, String theType, String theModel,
							String theGeography, java.util.Date theWdfmDate,
							boolean thePromoted, boolean theChanged,
							boolean theDeleted,  Timestamp theQsmUpdateTs,
							String theQsmUpdatedBy, Date theAnnouncementDate,
							String thediv, Vector theFlfilV) throws HWPIMSAbnormalException  {//RQ0724066720
	
		anndocno = theAnndocno;
		type = theType;
		model = theModel;
		geography = theGeography;
		wdfmDate = theWdfmDate;
		promoted = thePromoted;
		changed = theChanged;
		deleted = theDeleted;
		qsmUpdateTs = theQsmUpdateTs;
		qsmUpdatedBy = theQsmUpdatedBy;
		announcementDate =  theAnnouncementDate;
		
		//RQ0724066720
		if (thediv==null) {
//			div = this.retrieveDiv(geography);//RQ0724066720
		} else {
			div = thediv;
		}
		
		if (theFlfilV != null) {

			flfilV = theFlfilV;
		}
		
		
		
	}
	public TypeModelGeoWDFM()
	{
	}
	
	public TypeModelGeoWDFM(ResultSet rs) throws HWPIMSAbnormalException
	{
		try
		{	
			setAnndocno(ConversionUtility.convertNull(rs.getString("ANNDOCNO")));
			setType(ConversionUtility.convertNull(rs.getString("TYPE")));
			setModel(ConversionUtility.convertNull(rs.getString("MODEL")));
			setGeography(ConversionUtility.convertNull(rs.getString("GEOGRAPHY")));
			if (1 == rs.getInt("CHANGED")) setChanged(true);
			if (1 == rs.getInt("DELETED")) setDeleted(true);
			if (1 == rs.getInt("PROMOTED")) setPromoted(true);
			setWDFMDate(rs.getDate("WDFMDATE"));
			setAnnouncementDate(rs.getDate("ANNOUNCEMENTDATE"));
			setQsmUpdateTs(rs.getTimestamp("QSMUPDATETS"));
			setQsmUpdatedBy(rs.getString("QSMUPDATEDBY"));
			
		}
		catch (SQLException ex)
		{
			String msg = "Unable to create TypeModelGeoWDFM from a result set.";
			HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(ex),"E");
			throw new HWPIMSAbnormalException(msg, ex);
		}
	}			 
	
	/**
	 * Returns the SegnmentAcronym.
	 * @return SegnmentAcronym
	 */
	
	/*public String getSegmentAcronym() {
		return segmentAcronym;
	}*/
	
	//RQ0724066720
	public String getDiv(){
		return div;
	}

	/**
	 * Returns the anndocno.
	 * @return String
	 */
	public String getAnndocno() {
		return anndocno;
	}

	/**
	 * Returns the changed.
	 * @return boolean
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Returns the deleted.
	 * @return boolean
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Returns the geography.
	 * @return String
	 */
	public String getGeography() {
		return geography;
	}

	/**
	 * Returns the model.
	 * @return String
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Returns the promoted.
	 * @return boolean
	 */
	public boolean isPromoted() {
		return promoted;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the wDFMDate.
	 * @return java.util.Date
	 */
	public java.util.Date getWDFMDate() {
		return wdfmDate;
	}

	/**
	 * Sets the SegnmentAcronym.
	 * @param SegnmentAcronym The SegnmentAcronym to set
	 */

/*	public void setSegmentAcronym(String segmentAcronym) {
		this.segmentAcronym = segmentAcronym;
	}*/
	
	//RQ0724066720
	
	public void setDiv(String div){
		this.div=div;
	}

	public void setAnndocno(String anndocno) {
		this.anndocno = anndocno;
	}

	/**
	 * Sets the changed.
	 * @param changed The changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * Sets the deleted.
	 * @param deleted The deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Sets the geography.
	 * @param geography The geography to set
	 */
	public void setGeography(String geography) {
		this.geography = geography;
	}

	/**
	 * Sets the model.
	 * @param model The model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Sets the promoted.
	 * @param promoted The promoted to set
	 */
	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the wDFMDate.
	 * @param wDFMDate The wDFMDate to set
	 */
	public void setWDFMDate(java.util.Date wDFMDate) {
		wdfmDate = wDFMDate;
	}

	/**
	 * Get the Announcement Number on which WDFM object was announced.
	 * @return String - the announcement number on which this object was announced.
	 */
//	public String getAnnouncementAnnDocNo() throws HWPIMSAbnormalException
//	{
////		if (_annAnnDocNo == null)
////		{
////			
////			String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
////			
////			java.sql.Connection db2con = null;
////			java.sql.Statement stmt = null;
////			java.sql.ResultSet rs = null;
////			String sql = "SELECT ANNDOCNO FROM " + schema + ".TYPE_MODEL_GEO"
////				+ " WHERE TYPE ='" + getType() +"'"
////				+ " AND MODEL ='" + getModel() +"'"
////				+ " AND GEOGRAPHY ='" + getGeography() +"'";
////			
////			try 
////			{	
////				db2con = DBConnectionManager.getInstance().getConnection();
////				stmt = db2con.createStatement();
////				rs = stmt.executeQuery(sql);
////				if (rs.next()) 
////				{
////					_annAnnDocNo = rs.getString("ANNDOCNO");	
////				 }
////			}	
////			catch (SQLException e)
////			{
////				String msg = "Unable to retrieve announcement document number for a WDFM TMG via SQL: " + sql;
////				HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
////				throw new HWPIMSAbnormalException(msg, e);
////			}	
////			finally
////			{
////				DBConnectionManager.closeRSAndStatement(rs, stmt);
////				DBConnectionManager.closeConnection(db2con);
////			}	
////		}
//		return _annAnnDocNo;
//	}
	
	//RQ0724066720 	changes start
//	public String retrieveDiv(String geography) throws HWPIMSAbnormalException
//	{
////			String _annAnnDocNo_seg_acronym = null;	
//			String _div = null;
//			//String geography ="US";	
//			
////
////		if (_div == null )
////		{
////			
////			String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
////			
////			java.sql.Connection db2con = null;
////			java.sql.Statement stmt = null;
////			java.sql.ResultSet rs = null;
////			
////			//String sql ="select SEGMENTACRONYM from " + schema + ".chw_announcement where anndocno in" +"(select anndocno from "+ schema +".type_model_geo  where type='"
////			//+type+"'AND model='"+model+"' AND geography='"+geography+"')";
////			
////			String sql ="select DIV from " + schema + ".TYPE_MODEL where (TYPE,MODEL) in" +"(select TYPE,MODEL from "+ schema +".type_model_geo  where type='"
////						+type+"'AND model='"+model+"' AND geography='"+geography+"')";
////			
////			
////			try 
////			{	
////				db2con = DBConnectionManager.getInstance().getConnection();
////				stmt = db2con.createStatement();
////				rs = stmt.executeQuery(sql);
////			
////				if (rs.next()) 
////				{
////					_div = rs.getString("DIV");
////						
////				 }
////			}	
////			catch (SQLException e)
////			{
////				String msg = "Unable to retrieve announcement document number for a WDFM TMG via SQL: " + sql;
////				HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
////				throw new HWPIMSAbnormalException(msg, e);
////			}	
////			finally
////			{
////				DBConnectionManager.closeRSAndStatement(rs, stmt);
////				DBConnectionManager.closeConnection(db2con);
////			}	
////		}
//		return _div;
//	}
//	//RQ0724066720 changes end
	
	
//	public static TypeModelGeoWDFM getObject(String type, String model, String geo)
//		throws HWPIMSAbnormalException
//	{
//		TypeModelGeoWDFM tmgw = null;
//		
////		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
////		
////		java.sql.Connection db2con = null;
////		java.sql.Statement stmt = null;
////		java.sql.ResultSet rs = null;
////		String sql = "SELECT * FROM " + schema + ".TYPE_MODEL_GEO_W"
////			+ " WHERE TYPE ='" + type +"'"
////			+ " AND MODEL ='" + model +"'"
////			+ " AND GEOGRAPHY ='" + geo +"'";
////		
////		try 
////		{	
////			db2con = DBConnectionManager.getInstance().getConnection();
////			stmt = db2con.createStatement();
////			rs = stmt.executeQuery(sql);
////			if (rs.next()) 
////			{
////				tmgw = new TypeModelGeoWDFM(rs);
////			}
////		}	
////		catch (SQLException e)
////		{
////			String msg = "Unable to retrieve WDFM type model geo via SQL: " + sql;
////			HWPIMSLog.Write(msg + "\n" + ExceptionUtility.getStackTrace(e),"E");
////			throw new HWPIMSAbnormalException(msg, e);
////		}	
////		finally
////		{
////			DBConnectionManager.closeRSAndStatement(rs, stmt);
////			DBConnectionManager.closeConnection(db2con);
////		}	
//		return tmgw;
//	}
//	
	/**
	 * Returns the qsmUpdateTs.
	 * @return Timestamp
	 */
	public Timestamp getQsmUpdateTs()
	{
		return qsmUpdateTs;
	}

	/**
	 * Sets the qsmUpdateTs.
	 * @param qsmUpdateTs The qsmUpdateTs to set
	 */
	public void setQsmUpdateTs(Timestamp qsmUpdateTs)
	{
		this.qsmUpdateTs = qsmUpdateTs;
	}

	/**
	 * Returns the qsmUpdatedBy.
	 * @return String
	 */
	public String getQsmUpdatedBy()
	{
		return qsmUpdatedBy;
	}

	/**
	 * Sets the qsmUpdatedBy.
	 * @param qsmUpdatedBy The qsmUpdatedBy to set
	 */
	public void setQsmUpdatedBy(String qsmUpdatedBy)
	{
		this.qsmUpdatedBy = qsmUpdatedBy;
	}

	/**
	 * Returns the announcementDate.
	 * @return Date
	 */
	public Date getAnnouncementDate()
	{
		return announcementDate;
	}

	/**
	 * Sets the announcementDate.
	 * @param announcementDate The announcementDate to set
	 */
	public void setAnnouncementDate(Date announcementDate)
	{
		this.announcementDate = announcementDate;
	}

	/**
	 * @return Returns the flfilV.
	 */
	public Vector getFlfilV() {
		return flfilV;
	}
	/**
	 * @param flfilV The flfilV to set.
	 */
	public void setFlfilV(Vector flfilV) {
		this.flfilV = flfilV;
	}
	
	/**
	 * @return Returns true if contains XCC
	 */
	public boolean hasXcc() {
		boolean ans = false;
		
		Enumeration e;
		String fpipe;
		
		e = getFlfilV().elements();
		
		while (e.hasMoreElements()) {
			fpipe = (String) e.nextElement();
			if ("XCC".equalsIgnoreCase(fpipe)) {
				ans = true;
				break;
			}
		}
		
		return ans;
	}	
}
