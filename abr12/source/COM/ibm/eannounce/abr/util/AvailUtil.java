package COM.ibm.eannounce.abr.util;

import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: AvailUtil.java,v $
//Revision 1.11  2018/08/23 11:04:45  dlwlan
//fix error when building for story Withdrawal RFA Number generation
//
//Revision 1.10  2018/08/23 07:06:40  dlwlan
//1865844: Withdrawal RFA Number generation  for MODELCONVERT
//
//Revision 1.9  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.8  2014/03/25 14:57:54  guobin
//update the debug information
//
//Revision 1.7  2013/12/03 13:15:21  guobin
//BHALM00231247 - update for TMF.<PUBFROM> that add "IF". should I make the change on <ANNDATE>,<FIRSTORDER>,<PUBFROM> etc that the applicable countries are in the FEATURE.COUNTRYLIST if MODEL is old data which have no Planed avail.
//
//Revision 1.6  2013/11/19 12:10:15  guobin
//add isExistFinal and compatmodel method
//
//Revision 1.5  2013/11/12 16:04:36  guobin
//delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
//Revision 1.4  2013/11/08 07:53:42  guobin
//getAvailAnnAttributeDate change country in ANNOUNCEMENT.countrylist.
//
//Revision 1.3  2013/11/07 14:22:02  wangyulo
//get the status of the selected entity
//
//Revision 1.2  2013/11/06 06:51:21  wangyulo
//add a new function getAvailAnnDateByAnntype
//
//Revision 1.1  2013/11/06 05:58:47  wangyulo
//Add some public utility function for the change of BH FS ABR XML System Feed Mapping 20130904b.doc
//
//Initial for
//-   BH FS ABR XML System Feed Mapping 20130904b.doc

public class AvailUtil extends XMLElem {
	public AvailUtil(String nname) {
		super(nname);
	}

 
	
	static String compatModel = "V200309";
//	Compatibility Mode will send a subset of <AVAILABILITYELEMENT> based on the XML <STATUS> which may be different than the root entityStatus(STATUS).
//	IF <STATUS> = 0040, the use only <AVAILABILITYELEMENT> where the row in the table has column 2 <STATUS> =RFR 
//	IF <STATUS> = 0020, the use only <AVAILABILITYELEMENT> where the row in the table has column 2 <STATUS> =Final
	public static boolean iscompatmodel (){
		String modelvalue = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+"compatibility",CHEAT);
//		ABRUtil.append(debugSb,"compatModel compatbility mode:" + modelvalue);
//		
	    if (!compatModel.equals(modelvalue)){
	    	return false;
	    }
		return true;
	}
	
	

	/**
	 * isExistFinal
	 * @param dbCurrent
	 * @param parentDiff
	 * @param nodeName
	 * @param debugSb
	 * @return
	 * @throws MiddlewareRequestException
	 */
	public static boolean isExistFinal(Database dbCurrent, DiffEntity parentDiff,  String nodeName,  StringBuffer debugSb) throws MiddlewareRequestException
    {
    	boolean existFinal = false;
    
	    
	    AttributeChangeHistoryGroup statusHistory = null;
	    EntityItem item  = parentDiff.getCurrentEntityItem();
	    if (item != null){
	    	Profile m_prof = item.getProfile();	    
		    EANAttribute att = item.getAttribute(nodeName);
		    if (att != null) {
		    	statusHistory = new AttributeChangeHistoryGroup(dbCurrent, m_prof, att);
		    } else {
		    	ABRUtil.append(debugSb, nodeName + " of "+item.getKey()+ "  was null");
		        return false;
		    }
		    
		    if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
	            for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
	                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
	                if (achi != null) {
	                	String status = achi.getFlagCode();
	                	if(status!=null && status.equals(STATUS_FINAL)){
	                		existFinal = true;
	                		break;
	                	}
	                }
	            }
	        } else {
	        	ABRUtil.append(debugSb,"Entity STATUS has no changed history!");
	        	existFinal = false;
	        }	    	
	    }	    
	    statusHistory = null;
	    return existFinal;
    }
	//boolean isExistfinal = isExistFinal(dbCurrent,  parentItem,   "STATUS", debugSb);
	/**
	 * Get the EntityItem from the DiffEntity according to the value of findT1 is true or false 
	 * @param findT1
	 * @param diffEntity
	 * @return
	 */
	public static EntityItem getEntityItem(boolean findT1, DiffEntity diffEntity) {
		EntityItem item = null;
		if(findT1){
			if(diffEntity != null && !diffEntity.isNew()) {
				item = diffEntity.getPriorEntityItem();					
			}
		}else{
			if (diffEntity != null && !diffEntity.isDeleted()) {
				item = diffEntity.getCurrentEntityItem();						
			}			
		}
		return item;
	}	
	
	/**
	 * AVAIL - down --> AVAILANNA (attribute: ANNDATE ANNNUMBER)
	 * @param findT1
	 * @param availDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param country
	 * @param debugSb
	 * @return
	 */
	public static String[] getAvailAnnAttributeDate(boolean findT1,DiffEntity availDiff, String thedate, String rfrthedate, String country, String attribute, StringBuffer debugSb){			
		String sReturn[] = new String[2];			
		EntityItem item = AvailUtil.getEntityItem(findT1, availDiff);
		if (item != null ) {
			//EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
            //if (fAtt != null && fAtt.isSelected(country)) {
			Vector relatorVec = item.getDownLink();
			ABRUtil.append(debugSb,"AvailUtil.getAvailAnnAttributeDate looking for downlink of AVAIL" + " annVct.size: "
					+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
					+ item.getDownLinkCount() + NEWLINE);
			for (int ii = 0; ii < relatorVec.size(); ii++) {
				EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
				if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
					Vector annVct = availanna.getDownLink();
					EntityItem anna = (EntityItem) annVct.elementAt(0);
					EANFlagAttribute fAtt = (EANFlagAttribute) anna.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						String annstatus = PokUtils.getAttributeFlagValue(anna, "ANNSTATUS");
						String anndate = PokUtils.getAttributeValue(anna, attribute, ", ", CHEAT, false);
						ABRUtil.append(debugSb,"AvailUtil.getAvailAnnAttributeDate annstatus=" + annstatus +";attribute code=" + attribute +";attribute value="+ anna + NEWLINE);						
						if (annstatus != null && annstatus.equals("0020") && CHEAT.equals(thedate) ){
							thedate = anndate;								
						}else if (annstatus != null && annstatus.equals("0040") && CHEAT.equals(rfrthedate) ){
							rfrthedate = anndate;								
						}
						ABRUtil.append(debugSb,"AvailUtil.getAvailAnnAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);								
					}
				}
			}
		}	
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;			
	}
	 /**
	 *  avail ( EFFECTIVEDATE,)
	 *  COUNTRYLIST --> country selected --> entity attribute
	 * @param foAvailDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param debugSb
	 * @return
	 */
	public static String[] getAvailAttributeDate(boolean findT1, DiffEntity foAvailDiff, String thedate,
			String rfrthedate, String country,String attribute, StringBuffer debugSb) {
		String sReturn[] = new String[2];
		EntityItem item = AvailUtil.getEntityItem(findT1, foAvailDiff);		
		if (item!=null) {
			EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
			if (fAtt != null && fAtt.isSelected(country)) {
				String annstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
				String entityDate = PokUtils.getAttributeValue(item, attribute, ", ", CHEAT, false);	
				ABRUtil.append(debugSb,"AvailUtil.getAvailAttributeDate annstatus=" + annstatus +";attribute code=" + attribute +";attribute value="+ entityDate + NEWLINE);				
				if (annstatus != null && annstatus.equals("0020") && CHEAT.equals(thedate) ){
					thedate = entityDate;								
				}else if (annstatus != null && annstatus.equals("0040") && CHEAT.equals(rfrthedate) ){
					rfrthedate = entityDate;								
				}
				ABRUtil.append(debugSb,"AvailUtil.getAvailAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
				
			}
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;	
	}
	
	/**
	 * get the attribute value of parent entity 
	 * @param findT1
	 * @param parentDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param attribute
	 * @param debugSb
	 * @return
	 */
	public static String[] getParentAttributeDate(boolean findT1, DiffEntity parentDiff, 
			String thedate, String rfrthedate, String attribute, StringBuffer debugSb) {
		String sReturn[] = new String[2];
		EntityItem pItem = AvailUtil.getEntityItem(findT1, parentDiff);
		if (pItem != null) {
			//attribute WTHDRWEFFCTVDATE
			String entityStatus = PokUtils.getAttributeFlagValue(pItem, "STATUS");
			String entityDate = PokUtils.getAttributeValue(pItem, attribute, "", CHEAT, false);
			if (entityStatus != null && entityStatus.equals("0020") && CHEAT.equals(thedate) ){
				thedate = entityDate;								
			}else if (entityStatus != null && entityStatus.equals("0040") && CHEAT.equals(rfrthedate) ){
				rfrthedate = entityDate;								
			}				
			ABRUtil.append(debugSb,"AvailUtil.getParentAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;	
	}
	
	 /**
	 *  avail ( EFFECTIVEDATE,)
	 *  COUNTRYLIST --> country selected --> entity attribute
	 * @param foAvailDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param debugSb
	 * @return
	 */
	public static String[] getBHcatlgorAttributeDate(boolean findT1, DiffEntity parentDiff, DiffEntity entityDiff, String thedate,
			String rfrthedate, String country,String attribute, StringBuffer debugSb) {
		String sReturn[] = new String[2];
		EntityItem item = AvailUtil.getEntityItem(findT1, entityDiff);
		EntityItem parentItem = AvailUtil.getEntityItem(findT1, parentDiff);
		
		if (item!=null) {
			EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
			if (fAtt != null && fAtt.isSelected(country)) {
				String entityStatus = PokUtils.getAttributeFlagValue(parentItem, "STATUS");
				String entityDate = PokUtils.getAttributeValue(item, attribute, ", ", CHEAT, false);	
				
				ABRUtil.append(debugSb,"AvailUtil.getBHcatlgorAttributeDate annstatus=" + entityStatus +";attribute code=" + attribute +";attribute value="+ entityDate + NEWLINE);
				if (entityStatus != null && entityStatus.equals("0020") && CHEAT.equals(thedate) ){
					thedate = entityDate;								
				}else if (entityStatus != null && entityStatus.equals("0040") && CHEAT.equals(rfrthedate) ){
					rfrthedate = entityDate;								
				}
				ABRUtil.append(debugSb,"AvailUtil.getBHcatlgorAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
			}
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;	
	}
	
	
	
	/**
	 * parent PRODSTRUCT (ANNDATE or GENAVAILDATE or WITHDRAWDATE)
	 * @param findT1
	 * @param parentDiff
	 * @param plModelAvailDiff
	 * @param feaCtry
	 * @param thedate
	 * @param rfrthedate
	 * @param country
	 * @param attributecode
	 * @param debugSb
	 * @return
	 */
	public static String[] getProdstructAttributeDate(boolean findT1,DiffEntity parentDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], String thedate, String rfrthedate, 
			String country, String prodstructAttribute, StringBuffer debugSb){
		String sReturn[] = new String[2];			
		EntityItem plModelitem = AvailUtil.getEntityItem(findT1, plModelAvailDiff);			
		Vector feaVector = (findT1) ? feaCtry[0] : feaCtry[1];
		if (applicableCtry(feaVector,  plModelitem,  country)){							
			EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
			if (item!=null && parentDiff.getEntityType().equals("PRODSTRUCT")){
				String tmfstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
				//the attributecode can be "ANNDATE" or "GENAVAILDATE" or "WITHDRAWDATE"					
				String entityDate = PokUtils.getAttributeValue(item, prodstructAttribute, ", ", CHEAT, false);
				ABRUtil.append(debugSb,"AvailUtil.getProdstructAttributeDate annstatus=" + tmfstatus +";attribute code=" + prodstructAttribute +";attribute value="+ entityDate + NEWLINE);
				
				if (tmfstatus != null && tmfstatus.equals("0020") && CHEAT.equals(thedate)){
					thedate = entityDate;
				}else if (tmfstatus != null && tmfstatus.equals("0040") && CHEAT.equals(rfrthedate)){
					rfrthedate = entityDate;
				}
				ABRUtil.append(debugSb,"AvailUtil.getProdstructAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
			}							
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;			
	}
	
	/**
	 * TMF --down --> MODEL   (ANNDATE ,   GENAVAILDATE  )      
	 * TMF <--- up -- FEATURE (FIRSTANNDATE,GENAVAILDATE)
	 * @param findT1
	 * @param parentDiff
	 * @param plModelAvailDiff
	 * @param feaCtry
	 * @param thedate
	 * @param rfrthedate
	 * @param debugSb
	 * @return
	 */
	public static String[] getModelFeatureAttributeDate(boolean findT1,DiffEntity parentDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], String thedate, String rfrthedate, 
			String country, String modelAttribute, String featureAttribute, StringBuffer debugSb){
		String[] sReturn = new String[2];
		//start
		EntityItem plModelitem = AvailUtil.getEntityItem(findT1, plModelAvailDiff);
		Vector feaVector = (findT1) ? feaCtry[0] : feaCtry[1];			
		String modelDate = CHEAT;
		String dateSelfStatus = CHEAT;
		//TODO
		if (applicableCtry(feaVector, plModelitem, country)){
			EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
			if (item!=null){
				//String parentstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
				if (item.hasDownLinks()) {
					for (int i = 0; i < item.getDownLinkCount(); i++) {
						EntityItem entity = (EntityItem) item.getDownLink(i);
						if (entity.getEntityType().equals("MODEL")) {
							//String entityStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
							//modelAttribute will be "ANNDATE" or "GENAVAILDATE"
						    modelDate = PokUtils.getAttributeValue(entity, modelAttribute, ", ", CHEAT, false);
						    dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
							ABRUtil.append(debugSb,"AvailUtil.getModelFeatureAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
							break;
						}
					}
				}
				if (item.hasUpLinks()) {
					for (int i = 0; i < item.getUpLinkCount(); i++) {
						EntityItem entity = (EntityItem) item.getUpLink(i);
						if (entity.getEntityType().equals("FEATURE")) {
							//String featurestatus = PokUtils.getAttributeFlagValue(item, "STATUS");
							//featureAttribute will be "FIRSTANNDATE" or "GENAVAILDATE"
							String featureDate = PokUtils.getAttributeValue(entity, featureAttribute, ", ", CHEAT, false);
							//ABRUtil.append(debugSb,"AvailUtil.getModelFeatureAttributeDate feature STATUS=" + featurestatus +";attribute code=" + featureAttribute +";attribute value="+ featureDate + NEWLINE);
							
							if (!CHEAT.equals(featureDate)) {
								if (!CHEAT.equals(modelDate)) {
									// find the max date between
									// model.anndate and
									// feature.firstanndate
									if (featureDate.compareTo(modelDate) > 0) {
										modelDate = featureDate;
										dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
									}
									ABRUtil.append(debugSb,"AvailUtil.getModelFeatureAttributeDate modelDate=" + modelDate + ";featureDate=" + featureDate  + NEWLINE);
								} else {
									modelDate = featureDate;
									dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
								}								
								
							}
							break;
						}
					}
				}
				if (dateSelfStatus != null && dateSelfStatus.equals("0020") && CHEAT.equals(thedate)){
					thedate = modelDate;
				} else if (dateSelfStatus != null && dateSelfStatus.equals("0040") && CHEAT.equals(rfrthedate)){
					rfrthedate = modelDate;
				}
				ABRUtil.append(debugSb,"AvailUtil.getModelFeatureAttributeDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
				
			}	
		}	
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;
	}
	
	/**
	 * get the SWPRODSTRUCT ---> model (anndate)
	 * @param findT1
	 * @param parentDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param debugSb
	 * @return
	 */
	public static String[] getSwprodModelAnnDate(boolean findT1,DiffEntity parentDiff, String thedate, String rfrthedate, StringBuffer debugSb){
		String[] sReturn = new String[2];
		
		EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
		if (item!= null && parentDiff.getEntityType().equals("SWPRODSTRUCT")){
			if (item.hasDownLinks()) {
				for (int i = 0; i < item.getDownLinkCount(); i++) {
					EntityItem entity = (EntityItem) item.getDownLink(i);
					if (entity.getEntityType().equals("MODEL")) {
						String modelstatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
						String modelanndate = PokUtils.getAttributeValue(entity, "ANNDATE", ", ", CHEAT, false);
						ABRUtil.append(debugSb,"AvailUtil.getSwprodModelAnnDate modelstatus = " + modelstatus +";modelanndate="+ modelanndate + NEWLINE);						
						if (modelstatus != null && modelstatus.equals("0020") && CHEAT.equals(thedate)){
							thedate = modelanndate;
						} else if (modelstatus != null && modelstatus.equals("0040") && CHEAT.equals(rfrthedate)){
							rfrthedate = modelanndate;
						}
						ABRUtil.append(debugSb,"AvailUtil.getSwprodModelAnnDate thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
						break;
					}
				}
			}
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;
		
	}

	/**
	 * get the  ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE =End of Service (151) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13) 
	 *          ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
	 * @param findT1
	 * @param endAvailDiff
	 * @param thedate
	 * @param rfrthedate
	 * @param country
	 * @param anntype
	 * @param debugSb
	 * @return
	 */
	public static String[] getAvailAnnDateByAnntype(boolean findT1, DiffEntity endAvailDiff, String thedate, String rfrthedate, String country, String anntype,
			StringBuffer debugSb) {
		String[] sReturn = new String[2];
		EntityItem item = AvailUtil.getEntityItem(findT1, endAvailDiff);
		
		if (item != null){
			//EntityItem item = endAvailDiff.getPriorEntityItem();
			EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
			if (fAtt!= null && fAtt.isSelected(country)){

				Vector relatorVec = item.getDownLink();

				ABRUtil.append(debugSb,"AvailUtil.getEOSANNDate looking for downlink of AVAIL" + " annVct.size: "
					+ (relatorVec == null ? "null" : "" + relatorVec.size()) + "Downlinkcount: "
					+ item.getDownLinkCount() + NEWLINE);
				for (int ii = 0; ii < relatorVec.size(); ii++) {
					EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);

					ABRUtil.append(debugSb,"AvailUtil.getEOSANNDate looking for downlink of AVAIL " + availanna.getKey()
						+ "entitytype is: " + availanna.getEntityType() + NEWLINE);

					if (availanna.getEntityType().equals("AVAILANNA") && availanna.hasDownLinks()) {
						// get get Announcement. it could return multiple results. Either check that you have the right one.
						Vector annVct = availanna.getDownLink();
						for (int iii = 0; iii < annVct.size(); iii++) {
							EntityItem anna = (EntityItem) annVct.elementAt(iii);
							ABRUtil.append(debugSb,"AvailUtil.getEOSANNDate looking for downlink of AVAILANNA " + anna.getKey()
								+ "entitytype is: " + anna.getEntityType() + "Attriubte ANNTYPE is: "
								+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + NEWLINE);
							EANFlagAttribute fANNAtt = (EANFlagAttribute) anna.getAttribute("ANNTYPE");
							if (fANNAtt != null && fANNAtt.isSelected(anntype)) {
								String entityStatus = PokUtils.getAttributeFlagValue(anna, "ANNSTATUS");
								String entityDate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
								if (entityStatus != null && entityStatus.equals("0020") && CHEAT.equals(thedate)){
									thedate = entityDate;
								} else if (entityStatus != null && entityStatus.equals("0040") && CHEAT.equals(rfrthedate)){
									rfrthedate = entityDate;
								}									
							} else {
								ABRUtil.append(debugSb,"AvailUtil.getEOSANNDate ANNTYPE: "
									+ PokUtils.getAttributeFlagValue(anna, "ANNTYPE") + "is not equal " + "End Of Life - Discontinuance of service(13)" + NEWLINE);
							}
						}
					} else {
						ABRUtil.append(debugSb,"AvailUtil.getEOSANNDate no downlink of AVAILANNA was found" + NEWLINE);
					}

				}

			}
		}
		sReturn[0]= thedate;
		sReturn[1]= rfrthedate;
		return sReturn;
	}
	/***************************************************************************
	 * get entity with specified values - should only be one could be two if one
	 * was deleted and one was added, but the added one will override and be an
	 * 'update'
	 */
	public static DiffEntity getEntityForAttrs(Hashtable table, String etype, Vector allVct, String attrCode, String attrVal, String attrCode2,
		String attrVal2, boolean T1, StringBuffer debugSb) {
		DiffEntity diffEntity = null;
		ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs  at T1 "  + T1 +" looking for " + attrCode + ":" + attrVal + " and " + attrCode2 + ":"
			+ attrVal2 + " in " + etype + " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}
		if (T1){
			// find those of specified type
			for (int i = 0; i < allVct.size(); i++) {
				DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
				EntityItem prioritem = diffitem.getPriorEntityItem();
				if (!diffitem.isNew()) {
					ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs checking[" + i + "]: deleted/update " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem; // keep looking for one that is
													// not deleted
						}
					}
				}
			}			
		}else{
			// find those of specified type
			for (int i = 0; i < allVct.size(); i++) {
				DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
				EntityItem curritem = diffitem.getCurrentEntityItem();
				EntityItem prioritem = diffitem.getPriorEntityItem();
				if (diffitem.isDeleted()) {
					ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem; // keep looking for one that is
													// not deleted
						}
					}
				} else {
					if (diffitem.isNew()) {
						ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey() + " "
							+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								break;
							}
						}
					} else {
						// must check to see if the prior item had a match too
						ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs checking[" + i + "]: current " + diffitem.getKey() + " "
							+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								break;
							}
						}
						ABRUtil.append(debugSb,"AvailUtil.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey() + " "
							+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
						if (fAtt != null && fAtt.isSelected(attrVal)) {
							fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
							if (fAtt != null && fAtt.isSelected(attrVal2)) {
								diffEntity = diffitem;
								// break; see if there is another that is current
							}
						}
					}
				}
			}
		}
		

		return diffEntity;
	}
	/**
	 * applicableCtry are intersection of MODEL.PLANEDAVAIL.Countrylist and FEATURE.Countrylists
	 * @param feaVector
	 * @param plModelitem
	 * @param country
	 * @return
	 */
	public static boolean applicableCtry(Vector feaVector, EntityItem plModelitem, String country){
		boolean applicable = false;
		if (feaVector != null && feaVector.contains(country)){	
			if (plModelitem != null ){
				EANFlagAttribute fAtt = (EANFlagAttribute) plModelitem.getAttribute("COUNTRYLIST");
			    if (fAtt != null && fAtt.isSelected(country)) {
			    	applicable = true;
			    }
			} else{
				applicable = true;
			}
		}
			return applicable;
	}
}
