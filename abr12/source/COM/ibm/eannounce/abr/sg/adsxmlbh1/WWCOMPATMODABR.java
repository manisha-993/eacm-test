package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.Vector;
import java.sql.Connection;
import javax.xml.parsers.ParserConfigurationException;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
/**
 * 
 * BH FS ABR Catlog DB Compatibility Gen 20120627.doc 
 * 
 * update following change
 * 1. The Model Compatibility Group” handles Model-To-Model compatibility; however, it is not supported in this release. 
 * 2. delete for each Model.OSLEVEL that is add or deactive
 * 3. Upate 
 *  For each SEOCGMDL that is deactivated, 
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	GroupEntityType = “SEOCG”
	GroupEntityId = SEOCGMDL.ENTITY1ID
    Rows will be inserted into the table (WWTECHCOMPAT) when a SEOCGMDL is added (did not exist at T1 but exists at T2), the along with all of the “Options” found for each existing SEOCGMDL at T2.
    For each SEOCGMDL that is added
    For each SEOCGMDL that exists at T2 but not at T1 from the original extract: 
    Use the VE named WWCSEOCG which has the SEOCGs from the SEOCGMDL as the root entity to extract SEOCGOS.
    For each SEOCGOS where OS = “OS Independent” (10589), perform the following to obtain the “options” side. Perform two extracts for this SEOCG. This may result in one or more “options”
    The rows to insert are:
    Systems: this MODEL and all of the value(s) of OSLEVEL
    Groups: the SEOCG(s) for the SEOCGMDL added
    Options: from the preceding extract for the “Group” SEOCG

 * 
 * This is original requirement:
 * For each MODEL.OSLEVEL deactivated where GroupEntityType equal MODELCG (i.e. active at T1 and deactivated at T2)
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	SystemOS = MODEL.OSLEVEL 
	Note: OSLEVEL is multi-value. This is processed for each value of OSLEVEL deactivated.
	GroupEntityType = “MODELCG”
	
	
	For each MODEL.OSLEVEL deactivated where GroupEntityType equal SEOCG (i.e. active at T1 and deactivated at T2)
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	SystemOS = MODEL.OSLEVEL
	Note: OSLEVEL is multi-value. This is processed for each value of OSLEVEL deactivated.
	GroupEntityType = “SEOCG”
	
	 
	For each MDLCGMDL that is deactivated, 
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	GroupEntityType = “MODELCG”
	GroupEntityId = MDLCGMDL.ENTITY1ID
	
	For each SEOCGMDL that is deactivated, 
	Set
	Activity = “D”
	Updated = NOW()
	TimeOfChange = T2
	Where
	Activity <> “D”
	TimeOfChange < T2
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	GroupEntityType = “SEOCG”
	GroupEntityId = SEOCGMDL.ENTITY1ID
	
	 
	
	For each MODEL.OSLEVEL that is added, insert records based on the section named “Insert Additions” where:
	If MODEL is related to MODELCG via MDLCGMDL
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	SystemOS = MODEL.OSLEVEL
	GroupEntityType = “MODELCG”
	
	If MODEL is related to SEOCG via SEOCGMDL
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	SystemOS = MODEL.OSLEVEL
	GroupEntityType = “SEOCG” 
	
	For each MDLCGMDL that is added, insert records based on the section named “Insert Additions” where:
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID
	SystemOS = MODEL.OSLEVEL
		For every value of OSLEVEL
	GroupEntityType = “MODELCG”
	GroupEntityId = MDLCGMDL.ENTITY1ID
	
	For each MDLCGOSMDL that is added, insert records based on the section named “Insert Additions” where:
	OptionEntityType = “MODEL”
	OptionEntityId = MODEL.ENTITYID
	OS = MODEL.OSLEVEL
		For every value of OSLEVEL
	GroupEntityType = “MODELCG”
	GroupEntityId = MDLCGMDL.ENTITY1ID
	
	
	 
	For each SEOCGMDL that is added, insert records based on the section named “Insert Additions” where:
	SystemEntityType = “MODEL”
	SystemEntityId = MODEL.ENTITYID 
	SystemOS = MODEL.OSLEVEL
	GroupEntityType = “SEOCG”
	GroupEntityId = SEOCGMDL.ENTITY1ID
	
	For each MDLCGOSMDL that is added, insert records based on the section named “Insert Additions” where:
	OptionEntityType = “MODEL”
	OptionEntityId = MODEL.ENTITYID 
	OS = MODEL.OSLEVEL
	OSEntityType = “MODELCGOS”
	OSEntityId = MODELCGOS.ENTITY1ID

 * --VE for MODEL
	insert into opicm.metalinkattr values ('SG' ,'Action/Entity'  , 'ADSWWCOMPATMOD' ,'MDLCGMDL' ,'U','0'  ,'1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', '1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', -1,-1);
	insert into opicm.metalinkattr values ('SG' ,'Action/Entity'  , 'ADSWWCOMPATMOD' ,'SEOCGMDL' ,'U','0'  ,'1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', '1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', -1,-1);
    insert into opicm.metalinkattr values ('SG' ,'Action/Entity'  , 'ADSWWCOMPATMOD2' ,'MDLCGOSMDL' ,'U','0'  ,'1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', '1980-01-01-00.00.00.000000', '9999-12-31-00.00.00.000000', -1,-1);

 * @author guobin
 *
 */

public class WWCOMPATMODABR extends ADSCOMPATGEN {

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
	     * @throws MiddlewareRequestException 
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
			//TODO comment out in the case of OSLEVEL is added 
	  		//For each MODEL.OSLEVEL that is added, insert records based on the section named “Insert Additions”
//	  		for (int i=0; i<currosLvlVct.size(); i++){
//	  			 String activity = checkSYSOSValue(parentItem, (String)currosLvlVct.elementAt(i), "OSLEVEL");
//	  			 if(XMLElem.UPDATE_ACTIVITY.equals(activity) && !populateVec.contains((String)currosLvlVct.elementAt(i)))
//	  				populateVec.add((String)currosLvlVct.elementAt(i));
//	  				 
//	  		}
//	  		
	  		//TODO comment out delVec do not care that OSLEVEL is deactived.
	  		 //For each MODEL.OSLEVEL deactivated where GroupEntityType equal MODELCG (i.e. active at T1 and deactivated at T2)
//	  		for (int i=0; i<priorosLvlVct.size(); i++){
//	  			 String activity = checkSYSOSValue(parentItem,  (String)priorosLvlVct.elementAt(i), "OSLEVEL");
//	  			 if(XMLElem.DELETE_ACTIVITY.equals(activity) && !populateVec.contains((String)priorosLvlVct.elementAt(i)))
//	  				delVec.add((String)priorosLvlVct.elementAt(i));
//	  				 
//	  		}
//	  		
	  		
	  		if (getVeName().equals(VeName)){
	  			abr.addOutput("Extract changes as System MODEL.");
	  			//First step chech whether relate to a MODELCG
//TODO          MDLCGMDL is not support in this release	  					
//	  			Vector MDLCGMDLVector = (Vector)diffTbl.get("MDLCGMDL");
//	  			// check MDLCGMDLVector is not null
//	  			if (MDLCGMDLVector != null){
//	  				abr.addDebug("MDLCGMDLVector size: " + MDLCGMDLVector.size() + "populateVec :" + populateVec.size() +  " delVec" + delVec.size() +populateVec + "|" +  delVec);
//		  			for (int i=0; i<MDLCGMDLVector.size(); i++){
//		  				    DiffEntity mdlcgmdLiff = (DiffEntity)MDLCGMDLVector.elementAt(i);
//				  			if (mdlcgmdLiff.isDeleted()){
//				  				abr.addXMLGenMsg("DELETE_ENTITY",mdlcgmdLiff.getKey());
//				  				EntityItem mdlcg = (EntityItem)mdlcgmdLiff.getPriorEntityItem().getUpLink(0);
//				  				if (mdlcg != null)
//				  				    deActivateSystem(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcg.getEntityType(), mdlcg.getEntityID(), update, timeofchange );
//				  			
//				  			} else if (mdlcgmdLiff.isNew()){
//				  				abr.addXMLGenMsg("NEW_ENTITY",mdlcgmdLiff.getKey());
//				  				EntityItem mdlcg = (EntityItem)mdlcgmdLiff.getCurrentEntityItem().getUpLink(0);
//				  				if (mdlcg != null)
//				  				    populateCompat( abr,  osTbl, curritem , mdlcg, currosLvlVct, update, timeofchange);
//				  			}else{
//				  				if (populateVec.size()>0){
//				  					abr.addXMLGenMsg("OS_CHANGE_FOUND", populateVec.toString());
//				  					EntityItem mdlcg = (EntityItem)mdlcgmdLiff.getCurrentEntityItem().getUpLink(0);
//				  				    if (mdlcg != null)
//				  				    populateCompat(abr,  osTbl, curritem , mdlcg, populateVec, update, timeofchange);
//				  				}
//				  			}
//				  	}
//		  			MDLCGMDLVector.clear();
//	  			}
	  			
	  			Vector SEOCGMDLVector = (Vector)diffTbl.get("SEOCGMDL");
	  			// 	check SEOCGMDLVector is not null
	  			if (SEOCGMDLVector != null){
	  				//abr.addDebug("SEOCGMDLVector size: " + SEOCGMDLVector.size() + "populateVec :" + populateVec.size() +  " delVec" + delVec.size());
			  		for (int i=0; i<SEOCGMDLVector.size(); i++){
			  			DiffEntity seocgmdldiff = (DiffEntity)SEOCGMDLVector.elementAt(i);
			  			if (seocgmdldiff.isDeleted()){
			  				abr.addOutput(seocgmdldiff.getKey() + " is deleted");
			  				EntityItem seocg = (EntityItem)seocgmdldiff.getPriorEntityItem().getUpLink(0);
			  				deActivateSystem(abr, curritem.getEntityType(),curritem.getEntityID(),seocg.getEntityType(), seocg.getEntityID(), update, timeofchange);
			  			} else if (seocgmdldiff.isNew()){
			  				abr.addOutput(seocgmdldiff.getKey() + " is added");
			  				EntityItem seocg = (EntityItem)seocgmdldiff.getCurrentEntityItem().getUpLink(0);
			  				populateCompat(abr,  osTbl, curritem , seocg, currosLvlVct ,update, timeofchange);
			  			} else {
			  				abr.addOutput(seocgmdldiff.getKey() + " already exist. ");
			  			}
			  			//TODO not support this time	
//			  			} else{
//			  				if (populateVec.size()>0){
//			  					abr.addXMLGenMsg("OS_CHANGE_FOUND", populateVec.toString());
//			  					EntityItem seocg = (EntityItem)seocgmdldiff.getCurrentEntityItem().getUpLink(0);
//			  					if (seocg != null)
//			  				    populateCompat(abr,  osTbl, curritem , seocg, populateVec, update, timeofchange);
//			  				}
			  		} 
			  		SEOCGMDLVector.clear();
			  	} else{
	  				abr.addXMLGenMsg("No_SEOCG_FOUND","SEOCGMDL");
	  			}
			  	
	  		}    
	  			//TODO NOT support this time
//		  		if (delVec.size()>0){
//		  			abr.addXMLGenMsg("OS_DELETE_FOUND", delVec.toString());
//  					deActivateSystemOS(abr, curritem.getEntityType(),curritem.getEntityID(), delVec, update, timeofchange);
//		  		}
		  	
//	  		}
	  		//TODO not support in this release.
//	  		} else if(getVeName2().equals(VeName)){
//	  			abr.addOutput("Extract changes as Opiton MODEL.");
//	  			Vector MDLCGOSMDLVector = (Vector)diffTbl.get("MDLCGOSMDL");
//	  			// check whether MDLCGOSMDLVector is not null
//	  			if (MDLCGOSMDLVector != null){
//	  				for (int i=0; i<MDLCGOSMDLVector.size(); i++){
//		  				DiffEntity mdlcgosmdldiff = (DiffEntity)MDLCGOSMDLVector.elementAt(i);
//				  			if (mdlcgosmdldiff.isDeleted()){
//				  				abr.addXMLGenMsg("DELETE_ENTITY",mdlcgosmdldiff.getKey());
//				  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getPriorEntityItem().getUpLink(0);
//				  				if (mdlcgos != null)
//				  				    deActivateOption(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcgos.getEntityType(),mdlcgos.getEntityID(),update, timeofchange);
//				  			} else if (mdlcgosmdldiff.isNew()){
//				  				abr.addXMLGenMsg("NEW_ENTITY",mdlcgosmdldiff.getKey());
//				  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
//				  				if (mdlcgos != null)
//				  				 populateCompat2( abr,  osTbl, curritem , mdlcgos, currosLvlVct, update, timeofchange);
//				  			}else{
//				  				 //the Delete conditon is that the current activate OSLeve is not contain 10589 (OS Independent) or ""( OSLeve is null).     
//						  		// case1 OS <> OS Independ 
//						  		//       if del Win7, delete OS = Win7 and OptionModel = Model
//						  		//       if deactivate OSLeve is 10589 (OS Independent), then delete OS <> current activate OSLeve and OptionModel = Model
//						  		// case2 OS = OS Independ
//						  		//        do not need to Delete.
//				  				// solution: If the current OSLevel is not match MODELCGOS.OS then delete it.
//					  			if (delVec.size()>0){
//					  				abr.addXMLGenMsg("OS_DELETE_FOUND", delVec.toString());
//					  				EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
//					  				if (mdlcgos != null){
//									String osStr = abr.getAttributeFlagEnabledValue(mdlcgos, "OS");
//									abr.addDebug("current: "+mdlcgos.getKey()+" OS: "+osStr + " currosLvlVct" + currosLvlVct);
//									String[] oslvl = oslvlMatch(osStr, currosLvlVct );
//									if (oslvl == null)
//										deActivateOption(abr, curritem.getEntityType(),curritem.getEntityID(), mdlcgos.getEntityType(),mdlcgos.getEntityID(), update, timeofchange);
//					  			
//					  				}
//					  			}
//				  				if (populateVec.size()>0){
//				  					abr.addXMLGenMsg("OS_CHANGE_FOUND", populateVec.toString());
//				  					EntityItem mdlcgos = (EntityItem)mdlcgosmdldiff.getCurrentEntityItem().getUpLink(0);
//					  				if (mdlcgos != null){
//									String osStr = abr.getAttributeFlagEnabledValue(mdlcgos, "OS");
//									abr.addDebug("current: "+mdlcgos.getKey()+" OS: "+osStr + " currosLvlVct" + currosLvlVct);
//									String[] oslvl = oslvlMatch(osStr, populateVec );
//									if (oslvl != null)
//				  					    populateCompat2(abr,  osTbl, curritem , mdlcgos, populateVec , update, timeofchange);
//				  				
//					  				}
//				  				}
//				  				    
//				  				
//				  			}
//				  	}
//	  				MDLCGOSMDLVector.clear();
//	  			}		
//	  		
//	  			
//	  		}	  		
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
	  		if ("MODELCG".equals(mdlcg.getEntityType())){
	  			String wherestr =  "    MODELCG.entityid = ?  and MODEL.entitytype= ? and MODEL.entityid = ? with ur \r\n"; 
            	 sql = getCommMODELCGSql(wherestr);
               }else{
            	  String wherestr = 	"    where SystemGroup.GroupId = ? and SystemGroup.SystemType= ? and SystemGroup.SystemId = ? with ur                  \r\n";
            	 sql = getCommSEOCGSql(wherestr); 
               }
	  		    
//	  			String SystemEntityType ="";
//				int SystemEntityId ;
//				String GroupEntityType ="";
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
				//abr.addDebug("getMatchingDateIds executing with " + sql);
				PreparedStatement ps = null;
				ResultSet result2 = null;
                
				try {
//					long curtime = System.currentTimeMillis();
					Connection conODS = getConnection();
					ps = conODS.prepareStatement(sql);
					ps.setInt(1, mdlcg.getEntityID());
					ps.setString(2, curritem.getEntityType());
					ps.setInt(3, curritem.getEntityID());
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
////						 SET chunk size to avoid out of memory
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
					SystemOSVector.clear();
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
		/*
		 * already know the OptionModel and ModelCGOS to get all the wwcompat 
		 * 
		 */
//	  	private void populateCompat2(ADSABRSTATUS abr, Vector osTbl, EntityItem curritem, EntityItem mdlcgosdiff ,Vector populateVec, String update, String timeofchange) throws SQLException, MiddlewareRequestException{
//	  		String sql = null;   
//            String wherestr = "    MODELCGOS.entityid = ?  and                              \r\n"+
//	  		                  "    MODEL2.entityid = ?  with ur                       \r\n";
////            	sql = getCommMODELCGSql(wherestr);
////	  			String SystemEntityType = "";
////				int SystemEntityId ;
////				String GroupEntityType = "";
////				int GroupEntityId ;
////				String OSEntityType = "";
////				int OSEntityId ;
////				String OSOPTIONType ="";
////				int OSOPTIONId;
////				String OptionEntityType = "";
////				int OptionEntityId ;
//	  			Vector SystemOSVector = new Vector();
//				Vector OptionOSVector = new Vector();
//				Vector WWCOMPATVector = new Vector();
//				OptionOSVector = populateVec;
//				//abr.addDebug("getMatchingDateIds executing with " + PokUtils.convertToHTML(sql));
//				PreparedStatement ps = null;
//				ResultSet result2 = null;
//
//				try {
////					long curtime = System.currentTimeMillis();
//					Connection conODS = getConnection();
//					ps = conODS.prepareStatement(sql);
//					ps.setInt(1, mdlcgosdiff.getEntityID());
//					ps.setInt(2, curritem.getEntityID());
//					result2 = ps.executeQuery();
////					TODO CALL putWWCOMPATVector(ADSABRSTATUS abr, Vector osTbl,  EntityItem curritem, ResultSet result2,  Vector SystemOSVector, Vector OptionOSVector, Vector WWCOMPATVector, String update, String timeofchange ) throws SQLException{
//					putWWCOMPATVector(abr, osTbl, curritem, result2, SystemOSVector, OptionOSVector, WWCOMPATVector, update, timeofchange);
////                    int counter = 1; 
//////					one MODELCG, get all MODEL and etc
////					while(result2.next()){
////						SystemEntityType	= result2.getString("SystemEntityType");
////						SystemEntityId 		= result2.getInt("SystemEntityId");						
////						GroupEntityType 	= result2.getString("GroupEntityType");
////					    GroupEntityId 		= result2.getInt("GroupEntityId");
////						OSEntityType    	= result2.getString("OSEntityType");
////						OSEntityId 			= result2.getInt("OSEntityId");
////						OSOPTIONType        = result2.getString("OSOPTIONType");
////						OSOPTIONId   		= result2.getInt("OSOPTIONId");
////						OptionEntityType   	= result2.getString("OptionEntityType");
////						OptionEntityId 		= result2.getInt("OptionEntityID");
////						WWCOMPATVector.clear();
////						putvalidWWCOMPAT(abr, WWCOMPATVector,SystemOSVector, OptionOSVector,SystemEntityType,
////							 SystemEntityId 		,					
////							  GroupEntityType 	,
////							  GroupEntityId 		,
////							  OSEntityType    	,
////							  OSEntityId 			,
////							  OSOPTIONType       ,
////							  OSOPTIONId   		,
////							  OptionEntityType   ,
////							  OptionEntityId,
////							  timeofchange);
////						osTbl.addAll(WWCOMPATVector);
//////						 SET chunk size to avoid out of memory
////						if (osTbl.size()>=WWCOMPAT_ROW_LIMIT){
////							abr.addDebug("Chunking size is " +  WWCOMPAT_ROW_LIMIT + ". Start to run chunking "  + counter++  + " times.");
////							updateCompat(abr,osTbl,update,timeofchange);
//////							 release memory
////							osTbl.clear();
////						}
////						
////					} 
////					if (osTbl.size()>0){
////						updateCompat(abr,osTbl,update,timeofchange);
////					}
////					abr.addDebug("Time to getMatchingDateIds all WWCOMPATVector size:" + ((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )  +"|"+ curritem.getKey()
////						+ ": " + Stopwatch.format(System.currentTimeMillis() - curtime));	
////	                if (((counter -1)*WWCOMPAT_ROW_LIMIT + osTbl.size() )==0){
////	                	abr.addOutput("ADSWWCOMPATABR found insert count: 0");
////	                }
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
//			
//		  		
//	  		
//	  	}	
    /**********************************
    *
	A.	MQ-Series CID
    */
    //public String getMQCID() { return "MODELCG"; }

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSWWCOMPATMOD";}
    //TODO comment out ADSWWCOMPATMOD2
    //public String getVeName2() { return "ADSWWCOMPATMOD2";}
    public String getStatusAttr() {return "STATUS";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "$Revision: 1.8 $";
    }
}
