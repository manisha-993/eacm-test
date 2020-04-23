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
 * 
T1 = the prior time the ABR attribute was set to “Queued” or TimeOfIDL
T2 = the time that the ABR attribute was set to “Queued”

Extract the following for T1 to T2 changes:
•	SEOCGOSSVCSEO
•	SEOCGOSSVCSEO.COMPATPUBFLG 
•	SEOCGOSSVCSEO.RELTYPE
•	SEOCGOSSVCSEO.PUBFROM 
•	SEOCGOSSVCSEO.PUBTO

	If SEOCGOSSVCSEO is added, insert records based on the section named “Insert Additions” where:
	OSEntityType = SEOCGOSSVCSEO.Entity1Type
	OSEntityId = SEOCGOSSVCSEO.Entity1Id
	OptionEntityType = SEOCGOSSVCSEO.Entity2Type
	OptionEntityId = SEOCGOSSVCSEO.Entity2Id
	
	 
	The following is performed only if SEOCGOSSVCSEO was not added.
	
	If SEOCGOSSVCSEO.COMPATPUBFLG = Delete (Delete) added, then
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	OSEntityType = SEOCGOSSVCSEO.Entity1Type
	OSEntityId = SEOCGOSSVCSEO.Entity1Id
	OptionEntityType = SEOCGOSSVCSEO.Entity2Type
	OptionEntityId = SEOCGOSSVCSEO.Entity2Id
	
	If SEOCGOSSVCSEO.COMPATPUBFLG = Delete (Delete) then after the preceding is processed, deactivate SEOCGOSSVCSEO in the PDH.
	
	For this SEOCGOSSVCSEO.COMPATPUBFLG is new, then insert records based on the section named “Insert Additions” where:
	OSEntityType = SEOCGOSSVCSEO.Entity1Type
	OSEntityId = SEOCGOSSVCSEO.Entity1Id
	OptionEntityType = SEOCGOSSVCSEO.Entity2Type
	OptionEntityId = SEOCGOSSVCSEO.Entity2Id
	
	 
	The following is performed only if none of the preceding applied.
	
	If SEOCGOSSVCSEO.COMPATPUBFLG = {Yes (Yes) | No (No)} was changed, then update records as follows:
	
	
	For this SEOCGOSSVCSEO, 
	Set 
	Activity = “C”
	Updated = NOW()
	TimeOfChange = T2 
	COMPATIBILITYPUBLISHINGFLAG = SEOCGOSSVCSEO.COMPATPUBFLG
	RELTYPE = SEOCGOSSVCSEO.RELTYPE
	PUBFROM = SEOCGOSSVCSEO.PUBFROM 
	PUBTO = SEOCGOSSVCSEO.PUBTO
	Where
	Activity <> “D”
	TimeOfChange < T2
	OSEntityType = SEOCGOSSVCSEO.Entity1Type
	OSEntityId = SEOCGOSSVCSEO.Entity1Id
	OptionEntityType = SEOCGOSSVCSEO.Entity2Type
	OptionEntityId = SEOCGOSSVCSEO.Entity2Id

 * @author guobin
 *
 */

public class WWCOMPATSEOCGOSSVCSEOABR extends ADSCOMPATGEN {

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
		/**
		 * 	     /***********************************************
	      *  Get the Boundles by os
	      *  SEOCGOSBDL is relator 
	      *  case 1. T1 = 1980..., Status is New, 
	      *  case 2. T1 <> 1980.., Status is changed, there in no possible to delete SEOCGOS an LSEOBUNDLE, only need to consider the sysmod is Deleted in T2.
	      *          in  the case of sysmod is deleted, this case will be handled by WWCOMPATSEOCGABR and WWCOMPATSEOCGOSABR.
	      *  case 3. COMPATPUBFLG ='Delete', then Deactivate SEOCGOSBDL.
	     	      *  Get the models by os
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
							deActivateMDLCGOSMDL(abr, cgos.getEntityType(), cgos.getEntityID(), optmdl.getEntityType(), optmdl.getEntityID(), update, timeofchange);
						} else {
							abr.addXMLGenMsg("INTEREST_CHANGE_FOUND", parentItem.getKey());
							String curr_oktoput = PokUtils.getAttributeValue(curritem, "COMPATPUBFLG", ", ", "", false);
							String curr_pubfrom = PokUtils.getAttributeValue(curritem, "PUBFROM", ", ", "", false);
							String curr_pubto = PokUtils.getAttributeValue(curritem, "PUBTO", ", ", "", false);
							String curr_reltype = PokUtils.getAttributeFlagValue(curritem, "RELTYPE");
							if (curr_reltype == null){
								curr_reltype = "";
							}
							updateCOMPATPUBFLG(abr, cgos.getEntityType(), cgos.getEntityID(), optmdl.getEntityType(), optmdl.getEntityID(), curr_oktoput, curr_pubfrom, curr_pubto, curr_reltype, update, timeofchange);
						}
					}
				}		
			}

	 osTbl.clear();
	}
		/**
	  	 * populate compat for new getSEOCGOSSVCSEOSql
	  	 * @param abr
	  	 * @param osTbl
	  	 * @param curritem
	  	 * @throws SQLException
	  	 */
	  	private void populateCompat(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, String update, String timeofchange) throws SQLException{
	  		String sql = null;  
	  		String wherestr = 	"    where OSOPTIONType = ? and OSOPTIONId= ? with ur                  \r\n";
           	sql = getCommSEOCGSql(wherestr);
	  		    
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
//                int counter = 1;
				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					/**
					 *where OSOPTIONType = ? and OSOPTIONId= ? with ur                  \r\n";
					 */
					ps.setString(1, curritem.getEntityType());
					ps.setInt(2, curritem.getEntityID());
					result2 = ps.executeQuery();
//					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
//
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
//						//TODO SET chunk size to avoid out of memory
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
    public String getVeName() { return "ADSWWCOMPATSEOCGOSSVCSEO";}

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


