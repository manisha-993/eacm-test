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
 * --VE for MODELCG
   insert into opicm.metalinkattr values ('SG' ,'Action/Entity'  , 'ADSWWCOMPATMODCG' ,'MDLCGMDLCGOS' ,'D','0'  ,'1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', '1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', -1,-1);
-------------------------------------------
 * Extract the following for T1 to T2 changes:
•	MODELCG.OKTOPUB
•	MODELCG.BRANDCD
•	MDLCGMDLCGOS where Entity1ID = MODELCG.entityid

	For MODELCG.OKTOPUB = Delete (Delete) as a change, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	GroupEntityType = “MODELCG”
	GroupEntityID = MODELCG.ENTITYID
	
	If MODELCG.OKTOPUB = Delete (Delete) then after the preceding is processed, deactivate MODELCG in the PDH.
	
	 
	
	If MODELCG.OKTOPUB = {Yes (Yes) | No (No)} was changed, then update records as follows:
	Set
	Activity = “C”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	GroupEntityType = “MODELCG”
	GroupEntityID = MODELCG.ENTITYID
	
	If MODELCG is new, then insert records based on the section named “Insert Additions” where:
	GroupEntityType = “MODELCG”
	GroupEntityID = MODELCG.ENTITYID
	
	
	If MDLCGMDLCGOS is deactivated, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	GroupEntityType = “MODELCG”
	GroupEntityID = MODELCG.ENTITYID
	OSEntityType = “MODELCGOS”
	OSEntityId = MDLCGMDLCGOS.Entity2Id
	
	If MDLCGMDLCGOS is new, then insert records based on the section named “Insert Additions” where:
	GroupEntityType = “MODELCG”
	GroupEntityID = MODELCG.ENTITYID

 * 
 * @author guobin
 *
 */

public class WWCOMPATMODCGABR extends ADSCOMPATGEN {

	/**********************************
	    * create xml and write to queue
	    */
	    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
	    throws
	    java.sql.SQLException,
	    COM.ibm.opicmpdh.middleware.MiddlewareException,
	    ParserConfigurationException,
	    java.rmi.RemoteException,
	    COM.ibm.eannounce.objects.EANBusinessRuleException,
	    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	    IOException,
	    javax.xml.transform.TransformerException,
		MissingResourceException
	    {
            processThis(abr, profileT1, profileT2, rootEntity, false);

		}	
	     /***********************************************
	      *  Get the models by os
	     * @throws SQLException 
	      */
	  	protected void getModelsByOS(ADSABRSTATUS abr, Hashtable diffTbl,String update, String timeofchange) throws SQLException
	  	{
	  		Vector osTbl = new Vector();
			Vector vct = (Vector)diffTbl.get("ROOT");
			DiffEntity parentItem = (DiffEntity)vct.firstElement();
			EntityItem curritem = parentItem.getCurrentEntityItem();
			

			//If MODELCG is new, then insert records based on the section named “Insert Additions” where:
			//GroupEntityType = “MODELCG”
			//GroupEntityID = MODELCG.ENTITYID
			
			if (parentItem.isNew()){
				abr.addXMLGenMsg("NEW_ENTITY",parentItem.getKey());
				populateCompat( abr,  osTbl, curritem, update, timeofchange);
			}else{
//				For MODELCG.OKTOPUB = Delete (Delete) as a change, then
//				Set
//				Activity = “D”
//				Updated = NOW()
//				TimeOfChange = T2
//				Where
//				Activity <> “D”
//				TimeOfChange < T2
//				GroupEntityType = “MODELCG”
//				GroupEntityID = MODELCG.ENTITYID
				
//				For MODELCG.BRANDCD as a change, then
//				Set
//				Activity = “C”
//				Updated = NOW()
//				TimeOfChange = T2
//				Where
//				Activity <> “D”
//				TimeOfChange < T2
//				GroupEntityType = “MODELCG”
//				GroupEntityID = MODELCG.ENTITYID
				String oktoput = getValue(parentItem,"OKTOPUB");
				String brandcd = getValue(parentItem,"BRANDCD");
				if (oktoput != null || brandcd != null){
					if ("Delete".equals(oktoput)){
						abr.addXMLGenMsg("OKTOPUB_CHANGE_FOUND", oktoput);
						deActivateCG(abr, curritem.getEntityType(),curritem.getEntityID(),update, timeofchange);
					}else{
						abr.addXMLGenMsg("INTEREST_CHANGE_FOUND", parentItem.getKey());
						String curr_oktoput = PokUtils.getAttributeValue(curritem, "OKTOPUB",", ", "", false);
						String curr_brandcd = PokUtils.getAttributeFlagValue(curritem, "BRANDCD");
						if (curr_brandcd == null){
							curr_brandcd = "";
						}
						updateCGOKTOPUB(abr, curritem.getEntityType(),curritem.getEntityID(),curr_oktoput,curr_brandcd, update, timeofchange);
					}
				}
				Vector MDLCGMDLCGOSVector = (Vector)diffTbl.get("MDLCGMDLCGOS");
	  			
	  			for (int i=0; i<MDLCGMDLCGOSVector.size(); i++){
	  				DiffEntity mdlcgmdlcgos = (DiffEntity)MDLCGMDLCGOSVector.elementAt(i);
			  			if (mdlcgmdlcgos.isDeleted()){
			  				abr.addXMLGenMsg("DELETE_ENTITY", mdlcgmdlcgos.getKey());
			  				EntityItem mdlcgos = (EntityItem)mdlcgmdlcgos.getPriorEntityItem().getDownLink(0);
			  				if (mdlcgos != null)
			  				    deActivateCGOS(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcgos.getEntityType(), mdlcgos.getEntityID(), update, timeofchange );
			  			
			  			} else if (mdlcgmdlcgos.isNew()){
			  				abr.addXMLGenMsg("NEW_ENTITY",mdlcgmdlcgos.getKey());
			  				EntityItem mdlcgos = (EntityItem)mdlcgmdlcgos.getCurrentEntityItem().getDownLink(0);
			  				if (mdlcgos != null)
			  				populateCompat2( abr,  osTbl, curritem , mdlcgos, update, timeofchange);
			  			}
			  	}
			}

		osTbl.clear();
	  	}
	  	/**
	  	 * populate compat for new related modlecgos
	  	 * @param abr
	  	 * @param osTbl
	  	 * @param curritem
	  	 * @throws SQLException
	  	 */
	  	private void populateCompat2(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, EntityItem mdlcgos, String update, String timeofchange) throws SQLException{
	  		String sql = null; 
	  		String wherestr =      "    MODELCGOS.entityid = ? and \r\n"+  
	                               "    MODELCG.entityid   = ? with ur \r\n";  
            	sql = getCommMODELCGSql(wherestr);
	  		    
//	  			String SystemEntityType = "";
//				int SystemEntityId ;
//				String GroupEntityType = "";
//				int GroupEntityId ;
//				String OSEntityType = "";
//				int OSEntityId;
//				String OSOPTIONType ="";
//				int OSOPTIONId;
//				String OptionEntityType="";
//				int OptionEntityId;
	  			Vector SystemOSVector = new Vector();
				Vector OptionOSVector = new Vector();
				Vector WWCOMPATVector = new Vector();
				//abr.addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
				PreparedStatement ps = null;
				ResultSet result2 = null;

				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					ps.setInt(1, mdlcgos.getEntityID());
					ps.setInt(2, curritem.getEntityID());
					result2 = ps.executeQuery();
//					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
//                    int counter =1 ;
////					one MODELCG, get all MODEL and etc
//					while(result2.next()){
//						SystemEntityType	= result2.getString("SystemEntityType");
//						SystemEntityId 		= result2.getInt("SystemEntityId");						
//						GroupEntityType 	= result2.getString("GroupEntityType");
//					    GroupEntityId 		= result2.getInt("GroupEntityId");
//						OSEntityType    	= result2.getString("OSEntityType");
//						OSEntityId 			= result2.getInt("OSEntityId");
//						OSOPTIONType        = result2.getString("OSOPTIONType");
//						OSOPTIONId   		= result2.getInt("OSOPTIONId");
//						OptionEntityType   	= result2.getString("OptionEntityType");
//						OptionEntityId 		= result2.getInt("OptionEntityID");
//						WWCOMPATVector.clear();
//						putvalidWWCOMPAT(abr, WWCOMPATVector,SystemOSVector, OptionOSVector,SystemEntityType,
//							 SystemEntityId 		,					
//							  GroupEntityType 	,
//							  GroupEntityId 		,
//							  OSEntityType    	,
//							  OSEntityId 			,
//							  OSOPTIONType       ,
//							  OSOPTIONId   		,
//							  OptionEntityType   ,
//							  OptionEntityId,
//							  timeofchange);
//						osTbl.addAll(WWCOMPATVector);
////						TODO SET chunk size to avoid out of memory
//						if (osTbl.size()>=WWCOMPAT_ROW_LIMIT){
//							abr.addDebug("Chunking size is " +  WWCOMPAT_ROW_LIMIT + ". Start to run chunking "  + counter++  + " times.");
//							updateCompat(abr,osTbl,update,timeofchange);
////							 release memory
//							osTbl.clear();
//						}
//						
//					} 
//					if (osTbl.size()>0){
//						updateCompat(abr,osTbl,update,timeofchange);
//					}
//					abr.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )  +"|"+ curritem.getKey()
//						+ ": " + Stopwatch.format(System.currentTimeMillis() - curtime));	
//	                if (((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )==0){
//	                	abr.addOutput("ADSWWCOMPATABR found insert count: 0");
//	                }

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
	  	/**
	  	 * populate compat for new MODELCG
	  	 * @param abr
	  	 * @param osTbl
	  	 * @param curritem
	  	 * @throws SQLException
	  	 */
	  	private void populateCompat(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, String update, String timeofchange) throws SQLException{
	  		String sql = null;  
	  		String wherestr =  "    MODELCG.entityid                = ? with ur \r\n"; 
            	sql = getCommMODELCGSql(wherestr);
	  		    
//	  			String SystemEntityType = "";
//				int SystemEntityId ;
//				String GroupEntityType = "";
//				int GroupEntityId ;
//				String OSEntityType = "";
//				int OSEntityId;
//				String OSOPTIONType ="";
//				int OSOPTIONId;
//				String OptionEntityType="";
//				int OptionEntityId;
	  			Vector SystemOSVector = new Vector();
				Vector OptionOSVector = new Vector();
				Vector WWCOMPATVector = new Vector();
				//abr.addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
				PreparedStatement ps = null;
				ResultSet result2 = null;

				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					ps.setInt(1, curritem.getEntityID());
					result2 = ps.executeQuery();
//					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
//                    int counter = 1;
////					one MODELCG, get all MODEL and etc
//					while(result2.next()){
//						SystemEntityType	= result2.getString("SystemEntityType");
//						SystemEntityId 		= result2.getInt("SystemEntityId");						
//						GroupEntityType 	= result2.getString("GroupEntityType");
//					    GroupEntityId 		= result2.getInt("GroupEntityId");
//						OSEntityType    	= result2.getString("OSEntityType");
//						OSEntityId 			= result2.getInt("OSEntityId");
//						OSOPTIONType        = result2.getString("OSOPTIONType");
//						OSOPTIONId   		= result2.getInt("OSOPTIONId");
//						OptionEntityType   	= result2.getString("OptionEntityType");
//						OptionEntityId 		= result2.getInt("OptionEntityID");
//						WWCOMPATVector.clear();
//						putvalidWWCOMPAT(abr, WWCOMPATVector,SystemOSVector, OptionOSVector,SystemEntityType,
//							 SystemEntityId 		,					
//							  GroupEntityType 	,
//							  GroupEntityId 		,
//							  OSEntityType    	,
//							  OSEntityId 			,
//							  OSOPTIONType       ,
//							  OSOPTIONId   		,
//							  OptionEntityType   ,
//							  OptionEntityId,
//							  timeofchange);
//						osTbl.addAll(WWCOMPATVector);
////						TODO SET chunk size to avoid out of memory
//						if (osTbl.size()>=WWCOMPAT_ROW_LIMIT){
//							abr.addDebug("Chunking size is " +  WWCOMPAT_ROW_LIMIT + ". Start to run chunking "  + counter++  + " times.");
//							updateCompat(abr,osTbl,update,timeofchange);
////							 release memory
//							osTbl.clear();
//						}
//						
//					} 
//					if (osTbl.size()>0){
//						updateCompat(abr,osTbl,update,timeofchange);
//					}
//					abr.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )  +"|"+ curritem.getKey()
//						+ ": " + Stopwatch.format(System.currentTimeMillis() - curtime));	
//	                if (((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )==0){
//	                	abr.addOutput("ADSWWCOMPATABR found insert count: 0");
//	                }

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
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSWWCOMPATMODCG";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.1 $";
    }
}
