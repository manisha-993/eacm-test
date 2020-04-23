package COM.ibm.eannounce.abr.sg.adsxmlbh1;

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
 * --VE for ADSWWCOMPATMDLCGOSMDL1
-----------------------------------------------------------------------
insert into opicm.metalinkattr values ('SG' ,'Action/Entity'  , 'ADSWWCOMPATMDLCGOSMDL1' ,'MDLCGOSMDL' ,'D','0'  ,'1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', '1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', -1,-1);
----------------------------------------------------------------------
Extract the following for T1 to T2 changes:
•	MDLCGOSMDL
•	MDLCGOSMDL.COMPATPUBFLG 
•	MDLCGOSMDL.RELTYPE
•	MDLCGOSMDL.PUBFROM 
•	MDLCGOSMDL.PUBTO

	If MDLCGOSMDL is added, insert records based on the section named “Insert Additions” where:
	OSEntityType = MDLCGOSMDL.Entity1Type
	OSEntityId = MDLCGOSMDL.Entity1Id
	OptionEntityType = MDLCGOSMDL.Entity2Type
	OptionEntityId = MDLCGOSMDL.Entity2Id
	
	 
	
	The following is performed only if MDLCGOSMDL was not added.
	
	If MDLCGOSMDL.COMPATPUBFLG = Delete (Delete) as a change, then 
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	OSEntityType = MDLCGOSMDL.Entity1Type
	OSEntityId = MDLCGOSMDL.Entity1Id
	OptionEntityType = MDLCGOSMDL.Entity2Type
	OptionEntityId = MDLCGOSMDL.Entity2Id
	
	If MDLCGOSMDL.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate MDLCGOSMDL in the PDH.
	
	
	If this MDLCGOSMDL is new, then insert records based on the section named “Insert Additions” where:
	OSEntityType = MDLCGOSMDL.Entity1Type
	OSEntityId = MDLCGOSMDL.Entity1Id
	OptionEntityType = MDLCGOSMDL.Entity2Type
	OptionEntityId = MDLCGOSMDL.Entity2Id
	
	 
	
	The following is performed only if none of the preceding applied.
	
	If MDLCGOSMDL.COMPATPUBFLG = {Yes (Yes) | No (No)} was changed, then update records as follows:
	 
	Set 
	Activity = “C”
	Updated = NOW()
	TimeOfChange = T2
	COMPATIBILITYPUBLISHINGFLAG = MDLCGOSMDL.COMPATPUBFLG
	RELTYPE = MDLCGOSMDL.RELTYPE
	PUBFROM = MDLCGOSMDL.PUBFROM 
	PUBTO = MDLCGOSMDL.PUBTO
	Where
	Activity <> “D”
	TimeOfChange < T2
	OSEntityType = MDLCGOSMDL.Entity1Type
	OSEntityId = MDLCGOSMDL.Entity1Id
	OptionEntityType = MDLCGOSMDL.Entity2Type
	OptionEntityId = MDLCGOSMDL.Entity2Id

 * @author guobin
 *
 */
public class WWCOMPATMDLCGOSMDLABR extends ADSCOMPATGEN {
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
	      *  MDLCGOSMDL is relator 
	      *  case 1. T1 = 1980..., Status is New, 
	      *  case 2. COMPATPUBFLG ='Delete', then Deactivate MDLCGOSMDL.
	      */
		protected void getModelsByOS(ADSABRSTATUS abr, Hashtable diffTbl,String update, String timeofchange) throws SQLException
	  	{
	  		Vector osTbl = new Vector();
			Vector vct = (Vector)diffTbl.get("ROOT");
			DiffEntity parentItem = (DiffEntity)vct.firstElement();
			EntityItem curritem = parentItem.getCurrentEntityItem();

			if (parentItem.isNew()){
				abr.addXMLGenMsg("NEW_ENTITY",parentItem.getKey());
				populateCompat( abr,  osTbl, curritem, update, timeofchange);
			}else{
//				If MDLCGOSMDL.COMPATPUBFLG = Delete (Delete) as a change, then 
//				Set
//				Activity = “D”
//				Updated = NOW()
//				TimeOfChange = T2
//				Where
//				Activity <> “D”
//				TimeOfChange < T2
//				OSEntityType = MDLCGOSMDL.Entity1Type
//				OSEntityId = MDLCGOSMDL.Entity1Id
//				OptionEntityType = MDLCGOSMDL.Entity2Type
//				OptionEntityId = MDLCGOSMDL.Entity2Id

				String oktoput = getValue(parentItem,"COMPATPUBFLG");
				String pubfrom = getValue(parentItem,"PUBFROM");
				String pubto = getValue(parentItem,"PUBTO");
				String reltype = getFlagValue(parentItem,"RELTYPE");
							
				if (oktoput != null || pubfrom != null || pubto != null || reltype != null) {

				EntityItem optmdl = (EntityItem) curritem.getDownLink(0);
				EntityItem cgos = (EntityItem) curritem.getUpLink(0);
				if (optmdl != null && cgos != null) {
					if ("Delete".equals(oktoput)) {
						abr.addXMLGenMsg("COMPATPUBFLG_CHANGE_FOUND", oktoput);
						deActivateMDLCGOSMDL(abr, cgos.getEntityType(), cgos.getEntityID(), optmdl.getEntityType(), optmdl
							.getEntityID(), update, timeofchange);
					} else {
						abr.addXMLGenMsg("INTEREST_CHANGE_FOUND", parentItem.getKey());
						String curr_oktoput = PokUtils.getAttributeValue(curritem, "COMPATPUBFLG", ", ", "", false);
						String curr_pubfrom = PokUtils.getAttributeValue(curritem, "PUBFROM", ", ", "", false);
						String curr_pubto = PokUtils.getAttributeValue(curritem, "PUBTO", ", ", "", false);
						String curr_reltype = PokUtils.getAttributeFlagValue(curritem, "RELTYPE");
						if (curr_reltype == null){
							curr_reltype = "";
						}
						updateCOMPATPUBFLG(abr, cgos.getEntityType(), cgos.getEntityID(), optmdl.getEntityType(), optmdl
							.getEntityID(), curr_oktoput, curr_pubfrom, curr_pubto, curr_reltype, update, timeofchange);
					}
				}
			}
		}
	 osTbl.clear();
	}
		/**
	  	 * populate compat for new MDLCGOSMDL
	  	 * @param abr
	  	 * @param osTbl
	  	 * @param curritem
	  	 * @throws SQLException
	  	 */
	  	private void populateCompat(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, String update, String timeofchange) throws SQLException{
	  		String sql = null; 
	  		String wherestr =  "    MDLCGOSMDL.entityid = ? with ur \r\n";
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
 //               int counter = 1;
				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					ps.setInt(1, curritem.getEntityID());
					result2 = ps.executeQuery();
//					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
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
//						
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
    *
	A.	MQ-Series CID
    */
    //public String getMQCID() { return "MODELCG"; }

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSWWCOMPATMDLCGOSMDL1";}
    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.5 $";
    }
}
