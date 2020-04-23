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

import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;

public class WWCOMPATLSEOBUNDLEABR extends ADSCOMPATGEN {


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
	      *  Get the lseobundle  by os
	      */
	 	protected void getModelsByOS(ADSABRSTATUS abr, Hashtable diffTbl, String update, String timeofchange) throws SQLException
	 	{
	 
 		Vector osTbl = new Vector();
 		Vector vct = (Vector)diffTbl.get("ROOT");
 		DiffEntity parentItem = (DiffEntity)vct.firstElement();
 		EntityItem curritem = parentItem.getCurrentEntityItem();
 		EntityItem previtem = parentItem.getPriorEntityItem();

 		
       // extract MODELCG
 		Vector populateVec = new Vector ();
 		Vector delVec = new Vector();
 		String VeName = (String)diffTbl.get("VeName");
      
 		Vector currosLvlVct = new Vector(1);
 		setSystemSoLvVct(currosLvlVct, curritem);
 		Vector priorosLvlVct = new Vector(1);
 		setSystemSoLvVct(priorosLvlVct, previtem);
		//TODO  BH FS ABR Catlog DB Compatibility Gen 20120627.doc comment out each OSLEVEL that is changed processes.
 		//For each MODEL.OSLEVEL that is added, insert records based on the section named “Insert Additions”
// 		for (int i=0; i<currosLvlVct.size(); i++){
// 			 String activity = checkSYSOSValue(parentItem, (String)currosLvlVct.elementAt(i), "OSLEVEL");
// 			 if(XMLElem.UPDATE_ACTIVITY.equals(activity) && !populateVec.contains((String)currosLvlVct.elementAt(i)))
// 				populateVec.add((String)currosLvlVct.elementAt(i));
// 				 
// 		}
// 		 //For each MODEL.OSLEVEL deactivated where GroupEntityType equal MODELCG (i.e. active at T1 and deactivated at T2)
// 		for (int i=0; i<priorosLvlVct.size(); i++){
// 			 String activity = checkSYSOSValue(parentItem,  (String)priorosLvlVct.elementAt(i), "OSLEVEL");
// 			 if(XMLElem.DELETE_ACTIVITY.equals(activity) && !populateVec.contains((String)priorosLvlVct.elementAt(i)))
// 				delVec.add((String)priorosLvlVct.elementAt(i));
// 				 
// 		}
 		
 		
 		if (getVeName().equals(VeName)){
 			abr.addOutput("Extract changes as System.");
 			//First step chech whether relate to a SEOCGSEO
 		
 			Vector SEOCGMDLVector = (Vector)diffTbl.get("SEOCGBDL");
 			//TODO CHECK SEOCGMDLVector is not null
 			if (SEOCGMDLVector != null){
 				for (int i=0; i<SEOCGMDLVector.size(); i++){
 		  			DiffEntity seocgmdldiff = (DiffEntity)SEOCGMDLVector.elementAt(i);
 		  			if (seocgmdldiff.isDeleted()){
 		  				abr.addOutput(seocgmdldiff.getKey() + " is deleted");
 		  				EntityItem seocg = (EntityItem)seocgmdldiff.getPriorEntityItem().getUpLink(0);
 		  				deActivateSystem(abr, curritem.getEntityType(),curritem.getEntityID(),seocg.getEntityType(), seocg.getEntityID(), update, timeofchange);
 		  			} else if (seocgmdldiff.isNew()){
 		  				abr.addOutput(seocgmdldiff.getKey() + " is added");
 		  				EntityItem seocg = (EntityItem)seocgmdldiff.getCurrentEntityItem().getUpLink(0);
 		  				populateCompat(abr,  osTbl, curritem , seocg, currosLvlVct, update, timeofchange);
 		  			} else {
		  				abr.addOutput(seocgmdldiff.getKey() + " already exist. ");
		  			}
// 		  			else{
// 		  				if (populateVec.size()>0){
// 		  					abr.addXMLGenMsg("OS_CHANGE_FOUND", populateVec.toString());
// 		  					EntityItem seocg = (EntityItem)seocgmdldiff.getCurrentEntityItem().getUpLink(0);
// 		  					if (seocg != null)
// 		  				    populateCompat(abr,  osTbl, curritem , seocg, populateVec, update, timeofchange);
// 		  				}
// 		  			} 
 		  		}
 				SEOCGMDLVector.clear();
 			} else{
  				abr.addXMLGenMsg("No_SEOCG_FOUND","SEOCGBDL");
  			}
//	  		if (delVec.size()>0){
//	  			abr.addXMLGenMsg("OS_DELETE_FOUND", delVec.toString());
//				deActivateSystemOS(abr, curritem.getEntityType(),curritem.getEntityID(), delVec, update, timeofchange);
//	  		}
	  			
		
	  		
 		
// 		} else if(getVeName2().equals(VeName)){
// 			abr.addOutput("Extract changes as Option.");
// 			Vector MDLCGOSMDLVector = (Vector)diffTbl.get("SEOCGOSBDL");
// 			//TODO check MDLCGOSMDLVector is not null
// 			if (MDLCGOSMDLVector != null){
// 				for (int i=0; i<MDLCGOSMDLVector.size(); i++){
// 	 				DiffEntity mdlcgosmdldiff = (DiffEntity)MDLCGOSMDLVector.elementAt(i);
// 			  			if (mdlcgosmdldiff.isDeleted()){
// 			  				abr.addXMLGenMsg("DELETE_ENTITY",mdlcgosmdldiff.getKey());
// 			  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getPriorEntityItem().getUpLink(0);
// 			  				if (mdlcgos != null)
// 			  				    deActivateOption(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcgos.getEntityType(),mdlcgos.getEntityID(),update, timeofchange);
// 			  			} else if (mdlcgosmdldiff.isNew()){
// 			  				abr.addXMLGenMsg("NEW_ENTITY",mdlcgosmdldiff.getKey());
// 			  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
// 			  				if (mdlcgos != null)
// 			  				 populateCompat2( abr,  osTbl, curritem , mdlcgos, currosLvlVct, update, timeofchange);
// 			  			}else{
// 			  				 //the Delete conditon is that the current activate OSLeve is not contain 10589 (OS Independent) or ""( OSLeve is null).     
// 					  		// case1 OS <> OS Independ 
// 					  		//       if del Win7, delete OS = Win7 and OptionModel = Model
// 					  		//       if deactivate OSLeve is 10589 (OS Independent), then delete OS <> current activate OSLeve and OptionModel = Model
// 					  		// case2 OS = OS Independ
// 					  		//        do not need to Delete.
// 			  				// solution: If the current OSLevel is not match MODELCGOS.OS then delete it.
// 				  			if (delVec.size()>0){
// 				  				abr.addXMLGenMsg("OS_DELETE_FOUND", delVec.toString());
// 				  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
// 				  				if (mdlcgos != null){
// 								String osStr = abr.getAttributeFlagEnabledValue(mdlcgos, "OS");
// 								abr.addDebug("current: "+mdlcgos.getKey()+" OS: "+osStr + " currosLvlVct" + currosLvlVct);
// 								String[] oslvl = oslvlMatch(osStr, currosLvlVct );
// 								if (oslvl == null)
// 									deActivateOption(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcgos.getEntityType(),mdlcgos.getEntityID(), update, timeofchange);
// 				  			
// 				  				}
// 				  			}
// 			  				if (populateVec.size()>0){
// 			  					abr.addXMLGenMsg("OS_CHANGE_FOUND", populateVec.toString());
// 			  					EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
// 				  				if (mdlcgos != null){
// 								String osStr = abr.getAttributeFlagEnabledValue(mdlcgos, "OS");
// 								abr.addDebug("current: "+mdlcgos.getKey()+" OS: "+osStr + " currosLvlVct" + currosLvlVct);
// 								String[] oslvl = oslvlMatch(osStr, populateVec );
// 								if (oslvl != null)
// 			  					    populateCompat2(abr,  osTbl, curritem , mdlcgos, populateVec, update, timeofchange);
// 			  				
// 				  				}
// 			  				}
// 			  				    
// 			  				
// 			  			}
// 			  	}	
// 				MDLCGOSMDLVector.clear();
// 			}	
 		}	  		
 	     populateVec.clear();  	
 		 currosLvlVct.clear();	  	
 	     priorosLvlVct.clear();
 		 delVec.clear();  		
 		 osTbl.clear();
 	}
	 	
		/*
	 	 * populateCompat1
	 	 */
	 	private void populateCompat(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, EntityItem  mdlcg ,Vector populateVec, String update, String timeofchange) throws SQLException{
	 		String sql = null;   
	 		String wherestr =     	"    where SystemGroup.SystemType = ? and SystemGroup.SystemId = ? and SystemGroup.GroupId = ? with ur                  \r\n";
	       	   sql = getCommSEOCGSql(wherestr);
	        
	 		    
//	 			String SystemEntityType ="";
//				int SystemEntityId ;
//				String GroupEntityType = "";
//				int GroupEntityId;
//				String OSEntityType = "";
//				int OSEntityId;
//				String OSOPTIONType ="";
//				int OSOPTIONId;
//				String OptionEntityType="";
//				int OptionEntityId;
	 			Vector SystemOSVector = new Vector();
				Vector OptionOSVector = new Vector();
				Vector WWCOMPATVector = new Vector();
				SystemOSVector = populateVec;
//				abr.addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
				PreparedStatement ps = null;
				ResultSet result2 = null;

				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					ps.setString(1,curritem.getEntityType());
					ps.setInt(2, curritem.getEntityID());
					ps.setInt(3, mdlcg.getEntityID());
					result2 = ps.executeQuery();
//					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
//					int counter = 1;
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
	 	//TODO COMMENT OUT LSEOBoundel as option process. 
		/*
		 * already know the OptionModel and ModelCGOS to get all the wwcompat 
		 * 
		 */
//	 	private void populateCompat2(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, EntityItem mdlcgosdiff ,Vector populateVec, String update, String timeofchange) throws SQLException{
//	 		String sql = null;   
//            String wherestr =  	"    where OSOption.OptionId = ?  and                                 \r\n"+
//	    	"    OSOption.OptionType = ? and                                      \r\n"+
//	    	"    OSOption.OSId = ? and                                            \r\n"+
//	    	"    OSOption.OSType = ? with ur                                      \r\n";
//	       	 sql = getCommSEOCGSql(wherestr);
//	       
//	 		    
//	 			String SystemEntityType = "";
//				int SystemEntityId ;
//				String GroupEntityType = "";
//				int GroupEntityId ;
//				String OSEntityType = "";
//				int OSEntityId ;
//				String OSOPTIONType ="";
//				int OSOPTIONId;
//				String OptionEntityType = "";
//				int OptionEntityId;
//	 			Vector SystemOSVector = new Vector();
//				Vector OptionOSVector = new Vector();
//				Vector WWCOMPATVector = new Vector();
//				OptionOSVector = populateVec;
//				abr.addDebug("getMatchingDateIds executing with " + sql);
//				PreparedStatement ps = null;
//				ResultSet result2 = null;
//
//				try {
//					long curtime = System.currentTimeMillis();
//					Connection conODS = getConnection();
//					ps = conODS.prepareStatement(sql);
//					/*
//					 * 	"    where OSOption.OptionId = ?  and                         \r\n"+
//		    	     OSOption.OptionType = ? and                                      \r\n"+
//		    	     OSOption.OSId = ? and                                            \r\n"+
//		    	     OSOption.OSType = ? with ur                                      \r\n";
//					 */
//					ps.setInt(1, curritem.getEntityID());
//					ps.setString(2, curritem.getEntityType());
//					ps.setInt(3, mdlcgosdiff.getEntityID());
//					ps.setString(4, mdlcgosdiff.getEntityType());
//					result2 = ps.executeQuery();
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
//
//
//				} finally {
//					WWCOMPATVector.clear();
//					SystemOSVector.clear();
//					if (result2 != null) {
//						try {
//							result2.close();
//						} catch (Exception e) {
//							System.err.println("getMatchingDateIds(), unable to close result. " + e);
//						}
//						result2 = null;
//					}
//
//					if (ps != null) {
//						try {
//							ps.close();
//						} catch (Exception e) {
//							System.err.println("getMatchingDateIds(), unable to close ps. " + e);
//						}
//						ps = null;
//					}
//				}
//			
//	 	}
//	 
    /**********************************
    *
	A.	MQ-Series CID
    */
    //public String getMQCID() { return "MODELCG"; }

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSWWCOMPATLSEOBUNDLE1";}
    
    //TODO comment out LSEOBUNDLE as Option process according according to  BH FS ABR Catlog DB Compatibility Gen 20120627.doc
    //Case 2: “Option” (Right Side)
    //Since the BH downstream systems do not want variations based on Operating System, 
    //there is no need to update the Table (WWTECHCOMPAT). The DQ ABR will be updated so that this ABR is not queued; however,
    //until then this ABR should simply set its return code to Pass and exit.
    //Case 2: End


    // public String getVeName2() { return "ADSWWCOMPATLSEOBUNDLE2";}

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
