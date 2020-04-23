package COM.ibm.eannounce.abr.ln.adsxmlbh1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
/**
 * Extract the following for T1 to T2 changes:
•	SEOCGOS.OS

	Since OS is a unique Flag, a change is both a deactivate and an add.
	
	For each SEOCGOS.OS deactivated (i.e. active at T1 and deactivated at T2)
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	OSEntityType = “SEOCGOS”
	OSEntityId = SEOCGOS.ENTITYID
	OS = SEOCGOS.OS
	
	For each SEOCGOS.OS that is added, insert records based on the section named “Insert Additions” where:
	OSEntityType = “SEOCGOS”
	OSEntityId = SEOCGOS.ENTITYID
	OS = SEOCGOS.OS


 * @author guobin
 *
 */
public class WWCOMPATSEOCGOSABR extends ADSCOMPATGEN {
;

	/**********************************
	 * create xml and write to queue
	 */
	public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException, ParserConfigurationException,
		java.rmi.RemoteException, COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, IOException, javax.xml.transform.TransformerException,
		MissingResourceException {
		processThis(abr, profileT1, profileT2, rootEntity, false);

	}
	/***********************************************
	 *  Get the models by os
	 * @throws SQLException 
	 */
	protected void getModelsByOS(ADSABRSTATUS abr, Hashtable diffTbl,String update, String timeofchange) throws SQLException {
		Vector osTbl = new Vector(1);
		Vector vct = (Vector) diffTbl.get("ROOT");
		DiffEntity parentItem = (DiffEntity) vct.firstElement();
		EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem previtem = parentItem.getPriorEntityItem();
		
		
		
		String currosStr = PokUtils.getAttributeFlagValue(curritem, "OS");		
		String piorosStr = PokUtils.getAttributeFlagValue(previtem, "OS");
		abr.addDebug("current | previous " + curritem.getKey()+" OS: "+currosStr + " | "+ piorosStr);
		if (currosStr == null)
			currosStr = "";
		if (piorosStr == null)
			piorosStr = "";
		if (!currosStr.equals(piorosStr)){
			abr.addXMLGenMsg("OS_CHANGE_FOUND", currosStr);
//			if (!"".equals(piorosStr))
//				deActivateCGOS(abr,curritem.getEntityType(),curritem.getEntityID(), piorosStr, update, timeofchange);
//			if (!"".equals(currosStr))
//				populateCompat( abr,  osTbl, curritem, update, timeofchange);
//			TODO BH FS ABR Catlog DB Compatibility Gen 20120627.doc  If the value of SEOCGOS.OS deactivated (i.e. active at T1 and deactivated at T2) is = “OS Independent” (10589), then delete in SEOCGOS in WWTECHCOMPAT table.
			if (OSIndependent.equals(piorosStr))
				deActivateCGOS(abr,curritem.getEntityType(),curritem.getEntityID(), piorosStr, update, timeofchange);
			if (OSIndependent.equals(currosStr))
				populateCompat( abr,  osTbl, curritem, update, timeofchange);
		}
//		 release memory
		osTbl.clear();
	}

	/**
  	 * populate compat for new OS on MODELCGOS
  	 * @param abr
  	 * @param osTbl
  	 * @param curritem
  	 * @throws SQLException
  	 */
  	private void populateCompat(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, String update, String timeofchange) throws SQLException{
  		String sql = null; 
  		String wherestr = 	     	"    where SystemGroup.GroupType = 'SEOCG' and                 \r\n"+
     	"    OSOption.OSType = ? and                        \r\n"+
     	"    OSOption.OSId = ?  with ur                  \r\n";
        	sql = getCommSEOCGSql(wherestr);
  		    
//  			String SystemEntityType = "";
//			int SystemEntityId ;
//			String GroupEntityType = "";
//			int GroupEntityId ;
//			String OSEntityType = "";
//			int OSEntityId;
//			String OSOPTIONType ="";
//			int OSOPTIONId;
//			String OptionEntityType="";
//			int OptionEntityId;
  			Vector SystemOSVector = new Vector();
			Vector OptionOSVector = new Vector();
			Vector WWCOMPATVector = new Vector();
			//abr.addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
			PreparedStatement ps = null;
			ResultSet result2 = null;

			try {
//				long curtime = System.currentTimeMillis();
				Connection conODS = getConnection();
				ps = conODS.prepareStatement(sql);
				/**
				    where GroupType='SEOCG' and                 \r\n"+
			     	OSType = ? and                        \r\n"+
			     	OSId = ?  with ur                  \r\n";
				 */
				ps.setString(1, curritem.getEntityType());
				ps.setInt(2, curritem.getEntityID());
				result2 = ps.executeQuery();
//				TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
				putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
//				int counter = 1;
////				one MODELCG, get all MODEL and etc
//				while(result2.next()){
//					SystemEntityType	= result2.getString("SystemEntityType");
//					SystemEntityId 		= result2.getInt("SystemEntityId");						
//					GroupEntityType 	= result2.getString("GroupEntityType");
//				    GroupEntityId 		= result2.getInt("GroupEntityId");
//					OSEntityType    	= result2.getString("OSEntityType");
//					OSEntityId 			= result2.getInt("OSEntityId");
//					OSOPTIONType        = result2.getString("OSOPTIONType");
//					OSOPTIONId   		= result2.getInt("OSOPTIONId");
//					OptionEntityType   	= result2.getString("OptionEntityType");
//					OptionEntityId 		= result2.getInt("OptionEntityID");
//					WWCOMPATVector.clear();
//					putvalidWWCOMPAT(abr,WWCOMPATVector,SystemOSVector, OptionOSVector,SystemEntityType,
//						 SystemEntityId 		,					
//						  GroupEntityType 	,
//						  GroupEntityId 		,
//						  OSEntityType    	,
//						  OSEntityId 			,
//						  OSOPTIONType       ,
//						  OSOPTIONId   		,
//						  OptionEntityType   ,
//						  OptionEntityId,
//						  timeofchange);
//					osTbl.addAll(WWCOMPATVector);
//					//TODO SET chunk size to avoid out of memory
//					if (osTbl.size()>=WWCOMPAT_ROW_LIMIT){
//						abr.addDebug("Chunking size is " +  WWCOMPAT_ROW_LIMIT + ". Start to run chunking "  + counter++  + " times.");
//						updateCompat(abr,osTbl,update,timeofchange);
////						 release memory
//						osTbl.clear();
//					}
//					
//				} 
//				if (osTbl.size()>0){
//					updateCompat(abr,osTbl,update,timeofchange);
//				}
//				abr.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )  +"|"+ curritem.getKey()
//					+ ": " + Stopwatch.format(System.currentTimeMillis() - curtime));	
//                if (((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )==0){
//                	abr.addOutput("ADSWWCOMPATABR found insert count: 0");
//                }
                
			} finally {
				OptionOSVector.clear();
				WWCOMPATVector.clear();
				SystemOSVector.clear();
				if (result2 != null) {
					try {
						result2.close();
					} catch (Exception e) {
						System.err.println("getMatchingDateIds(), unable to close result. " + e);
					}
					result2 = null;
				}

				if (ps != null) {
					try {
						ps.close();
					} catch (Exception e) {
						System.err.println("getMatchingDateIds(), unable to close ps. " + e);
					}
					ps = null;
				}

			}

		
	  		
  		
  	}
	/**********************************
	 *
	 A.	MQ-Series CID
	 */
	//public String getMQCID() { return "MODELCG"; }
	/**********************************
	 * get the name of the VE to use
	 */
	public String getVeName() {
		return "dummy";
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion() {
		return "$Revision: 1.1 $";
	}
}

