//Licensed Materials -- Property of IBM
// 
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;

/*******************************************************************************
 * Constructor for <AVAILABILITYLIST> elements <AVAILABILITYLIST> 2
 * <AVAILABILITYELEMENT> 3 <AVAILABILITYACTION> </AVAILABILITYACTION> 4 derived
 * AvailabilityAction <STATUS> </STATUS> 4 AVAIL STATUS <COUNTRY_FC>
 * </COUNTRY_FC> 4 AVAIL COUNTRYLIST <ANNDATE> </ANNDATE> 4 <ANNNUMBER>
 * </ANNNUMBER> 4 <FIRSTORDER> </FIRSTORDER> 4 <PLANNEDAVAILABILITY>
 * </PLANNEDAVAILABILITY> 4 <PUBFROM> </PUBFROM> 4 <PUBTO> </PUBTO> 4
 * <WDANNDATE> </WDANNDATE> 4 <LASTORDER> </LASTORDER> 4 <EOSANNDATE>
 * </EOSANNDATE> 4 <ENDOFSERVICEDATE> </ENDOFSERVICEDATE> 4 AVAIL EFFECTIVEDATE
 * </AVAILABILITYELEMENT> 3 </AVAILABILITYLIST> 2
 */
public class XMLTMFAVAILElem extends XMLElem {
	
	private String PRODSTRUCT ="PRODSTRUCT";
	
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
	public XMLTMFAVAILElem() {
		super("AVAILABILITYELEMENT");
	}

	/***************************************************************************
	 * @param dbCurrent
	 *            Database
	 * @param table
	 *            Hashtable of Vectors of DiffEntity
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param parentItem
	 *            DiffEntity - parent to use if path is specified in
	 *            XMLGroupElem, item to use otherwise
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
		TreeMap ctryAudElemMap = new TreeMap();
		// if there is no Planed Avail, build records for all world wide
		// countries
		// If an AVAIL is not found for the PRODSTRUCT, 
		//then generate <AVAILABILITYELEMENT> ID 41.00 for each country in the intersection 
		// of MODEL: MODELAVAIL-d: AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability
		// and of FEATURE.COUNTRYLIST. If the MODEL does not have an AVAIL of type planned Availability 
		// then use FEATURE.COUNTRYLIST.
		boolean isfeacnty = false;
		boolean isAvailcnty = false;
		boolean isDerivefromMODEL = false;
		String tmfavailType = "";
		String modavailType = "MODELAVAIL";	
	    String m_strEpoch = "1980-01-01-00.00.00.000000";
	    boolean isDelta = true;
	    String availofType;
	    
		boolean isPRODSTRUCT = parentItem.getEntityType().equals(PRODSTRUCT)?true:false;
			// make a change according to BH FS ABR XML System Feed Mapping 20130508.doc 
//		   Consider the following scenario:
//		    1.	A MODEL was IDLed as old data (ANNDATA <20010301  and hence does not have aPlanned Availability . When it is moved toFinal, XML is generated with an < AVAILABILITYELEMENT> for every country in countrylist.
//			2.	A year later, the MODEL is moved fromFinal  toChange Request. Then aPlanned Availability with a STATUS =Draft is added to the MODEL with a Countrylist ofGermany.
//			3.	The MODEL is returned to a STATUS ofReady for Review; however, since it was onceFinal, no data is generated.
//			4.	The MODEL is returned to a STATUS ofFinal. Since there is an AVAIL with a STATUS =Draft, then XML is NOT generated since there is not an AVAIL with a STATUS =Final.
//			5.	The AVAIL is moved to a STATUS ofFinal. Delta XML will be generated. Since at T1, the Countrylist is defined to be World Wide and at T2 the Countrylist isGermany, then an <AVAILABILITYELEMENT> has to be generated for the World Wide list of Countries. All Countries exceptGermany will have <AVAILABILITYACTION> set toDelete. <AVAILABILITYACTION> will be set toUpdate forGermany.
//			6.	If there is a BHCATLGOR with Countrylist ofGermany andChina, onlyGermany will be considered since it is the only valid Country at T2.
		if (isPRODSTRUCT){
			tmfavailType = "OOFAVAIL";	
		} else{
			tmfavailType = "SWPRODSTRUCTAVAIL";
		}
		boolean compatModel = false;
	    boolean isExistfinal = false;
	    compatModel = AvailUtil.iscompatmodel();
		
	    if(!compatModel){
	    	//new added(xml status)
    		String status = null;
    		status = (String) table.get("_chSTATUS");
    		ABRUtil.append(debugSb,"the status is" + status + NEWLINE);
    		if(STATUS_FINAL.equals(status)){
    			isExistfinal = true;
    		}else{
    			isExistfinal = false;
    		}
		}
		    
		Vector tmfAvailVct = getAvailOfAvailType(table,tmfavailType,debugSb);
		Vector modallVct = getAvailOfAvailType(table,modavailType,debugSb);	
		EntityItem priorItem = parentItem.getPriorEntityItem();
	    //ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements priorItem is " + (priorItem == null?" is null":"not null")+ " profileT1 valOn: " + priorItem.getProfile().getValOn()+ NEWLINE);
		if (priorItem != null && m_strEpoch.equals(priorItem.getProfile().getValOn())){
			isDelta = false;
		}
		//if is Delta XML, then build Delete Countrylist.
		if (isDelta){
		    availofType = getCtryAVAL(table, parentItem, true, tmfAvailVct, debugSb);
	    	if (availofType == null) {    				    		
	    		isDerivefromMODEL = isDerivefromMODEL(table,modallVct,true,debugSb);
	    		if (isDerivefromMODEL){				
					//case for the PRODSTRUCT that get the avail from the MODEL
					if(isPRODSTRUCT){
//							boolean hasDeleProdAvail = buildDeleteProdCtryAudRecs(table, parentItem, ctryAudElemMap, debugSb);
						// Why there is boolean avariable hasDeleProdAvail, Because if it's true it means that at T1 time, there is Prodstruct->Planed AVAIL,
						// COUNTRY_FC at T1 is from Prodstruct->Planed AVAIL.Countrylist, But at T2 time COUNTRY_FC is from MODEL->Planed AVAIL.Countrylist, to 
						// avoid missing country, all countrylist of Model->planedavail.countrylist at T2 should be showed with status is 'UPDATE'
						// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some
						// may be deleted
						Vector[] feaCtry = getFeatureCtryVector(table, parentItem, debugSb);
						Vector plnAvlVct = getPlannedAvails(table, modallVct, debugSb);
						for (int i = 0; i < plnAvlVct.size(); i++) {
							DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
							buildCtryAudRecs(ctryAudElemMap, availDiff, feaCtry, true, debugSb);
						}// end each planned avail
					}else{
						//case for the SWPRODSTRUCT that get the avail from the MODEL
	                    //SWPRODSTRUCT don't nedd to consider FEATURE.COUNTRYLIST.					
						//Vector[] feaCtry = getFeatureCtryVector(table, parentItem, debugSb);
						Vector plnAvlVct = getPlannedAvails(table, modallVct, debugSb);
						//Vector[] availCtry = getSWPRODSTRUCTModelAvailCtryVector(table, plnAvlVct, debugSb);
						
						for (int i = 0; i < plnAvlVct.size(); i++) {
							DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
							buildCtryAudRecs(ctryAudElemMap, availDiff, null, true, debugSb);
						}// en
					}
	    		}else{
	    			if(isPRODSTRUCT){
						//build countries from FEATURE.countrylist
	    				buildFeatureCtryAudRecs(table, parentItem, ctryAudElemMap, true, debugSb);
					}else{
						//build countries from avail.countrylist
						//for all World Wide Countries with values from MODEL attributes
						buildWWCtryAudRecs(table, parentItem, ctryAudElemMap, dbCurrent, true, debugSb);					
					}
						
				}
	    		
	    	}else{
                //according to BH FS ABR XML System Feed Mapping 20130614.doc 
	    		//Bing add 
	    		//2 .The aggregated (UNION) of the countries found forAvailability(AVAIL)Country List(COUNTRYLIST)for all Availability Type(AVAILTYPE) that matches the earlier STATUS filtering criteria and the UNION of anyCatalog Overrides(BHCATLGOR)Country List(COUNTRYLIST).
	    		buildBHCatlgorRecs(table, ctryAudElemMap, true, debugSb);
	    		
	    		
	    		// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some
				// may be deleted
				// according to BH FS ABR XML System Feed Mapping 20121001.doc 
	    		
	    		//Bing add 6/28/2013 BH FS ABR XML System Feed Mapping 20130614.doc
	    		//The countrylist of AVAIL is the sub set of countrylist of Planed AVAIL. so Countrylist of Planed AVAIL can instead of the union of All AVAIL types, 
	    		//For the filter of the AVAIL, it need get the union of All AVAIL types 
	    		Vector AvailVct = new Vector();
	    		if ("146".equals(availofType)){
	    			AvailVct = tmfAvailVct;//getAVAILofType(table, tmfAvailVct, availofType, debugSb);
	    		}else {
	    			AvailVct = tmfAvailVct;
	    		}				
				for (int i = 0; i < AvailVct.size(); i++) {
					DiffEntity availDiff = (DiffEntity) AvailVct.elementAt(i);
					buildCtryAudRecs(ctryAudElemMap, availDiff, null, true, debugSb);
				}// end each planned avail
	    		
	    	}	
		}		
    	//Begin build T2 country records change false to true 
		availofType = getCtryAVAL(table, parentItem, false, tmfAvailVct, debugSb);
	    //availofType = getCtryAVAL(table, parentItem, false, tmfAvailVct, debugSb);
		if (availofType == null) {
			//CQ BHALM109267 SWPRODSTRUCT may not have an AVAIL of typelanned Availablity if it is older than 0100301
			/**
			 * BH FS ABR XML System Feed Mapping 20120711.doc P88
			 * If an AVAIL is not found for the SWPRODSTRUCT, then generate <AVAILABILITYELEMENT> ID 38.00 for each country found in the first one that applies:
				with values from MODEL attributes as the follows:
				ID 78.00 <ANNDATE> = ANNDATE
				ID 78.10 <ANNNUMBER> = empty
				ID 79.00 <FIRSTORDER> = ANNDATE
				ID 80.00 <PLANNEDAVAILABILITY> = ANNDATE
				ID 81.00 <PUBFROM> = ANNDATE
				ID 83.00 <PUBTO> = WTHDRWEFFCTVDATE
				ID 85.00 <WDANNDATE> = WITHDRAWDATE
				ID 86.00 <LASTORDER> = WTHDRWEFFCTVDATE
				ID 87.00 <EOSANNDATE> = empty
				ID 88.00 <ENDOFSERVICEDATE> = empty
				
			  @@ CQ BHALM109267 get the date
			  case 1: If an AVAIL is found for the SWPRODSTRUCT( PRODSTRUCT ),  the AVAIL(the blue color in the text)  get from OOFAVAIL-d: or SWPRODSTRUCTAVAIL-d
    		  case 2: If an AVAIL is not found for the SWPRODSTRUCT( PRODSTRUCT) and MODEL: MODELAVAIL-d: AVAIL where AVAILTYPE =lanned Availability exists ,  
			   then the AVAIL(the blue color in the text) get from MODELAVAIL-d: AVAIL 
			  From Rupal : IF AVAIL is not found at SWPRODSTRUCT but MODEL.AVAIL  where AVAILTYPE="Planned Availability"  exists. Avail data get from MODEL.AVAIL-d:AVAIL.

			 */
			isDerivefromMODEL = isDerivefromMODEL(table,modallVct,false,debugSb);
			if (isDerivefromMODEL){				
				//case for the PRODSTRUCT that get the avail from the MODEL
				if(isPRODSTRUCT){
					//boolean hasDeleProdAvail = buildDeleteProdCtryAudRecs(table, parentItem, ctryAudElemMap, debugSb);
					// Why there is boolean avariable hasDeleProdAvail, Because if it's true it means that at T1 time, there is Prodstruct->Planed AVAIL,
					// COUNTRY_FC at T1 is from Prodstruct->Planed AVAIL.Countrylist, But at T2 time COUNTRY_FC is from MODEL->Planed AVAIL.Countrylist, to 
					// avoid missing country, all countrylist of Model->planedavail.countrylist at T2 should be showed with status is 'UPDATE'
					// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some
					// may be deleted
					Vector[] feaCtry = getFeatureCtryVector(table, parentItem, debugSb);
					Vector plnAvlVct = getPlannedAvails(table, modallVct, debugSb);
					for (int i = 0; i < plnAvlVct.size(); i++) {
						DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
						buildCtryAudRecs(ctryAudElemMap, availDiff, feaCtry, false, debugSb);
					}// end each planned avail
				}else{
					//case for the SWPRODSTRUCT that get the avail from the MODEL
                    //SWPRODSTRUCT don't nedd to consider FEATURE.COUNTRYLIST.
					//Vector[] feaCtry = getFeatureCtryVector(table, parentItem, debugSb);
					Vector plnAvlVct = getPlannedAvails(table, modallVct, debugSb);
					//Vector[] availCtry = getSWPRODSTRUCTModelAvailCtryVector(table, plnAvlVct, debugSb);
					
					for (int i = 0; i < plnAvlVct.size(); i++) {
						DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
						buildCtryAudRecs(ctryAudElemMap, availDiff, null, false, debugSb);
					}// end each planned avail					
				}				
			}else{	
				if(isPRODSTRUCT){
					//build countries from FEATURE.countrylist
					buildFeatureCtryAudRecs(table, parentItem, ctryAudElemMap, false, debugSb);
					isfeacnty = true;
				}else{
					//build countries from avail.countrylist
					//for all World Wide Countries with values from MODEL attributes
					buildWWCtryAudRecs(table, parentItem, ctryAudElemMap, dbCurrent, false, debugSb);
					isAvailcnty = true;					
				}
			}
			
		} else {	
//			according to BH FS ABR XML System Feed Mapping 20130614.doc 
    		//Bing add 
    		//2 .The aggregated (UNION) of the countries found forAvailability(AVAIL)Country List(COUNTRYLIST)for all Availability Type(AVAILTYPE) that matches the earlier STATUS filtering criteria and the UNION of anyCatalog Overrides(BHCATLGOR)Country List(COUNTRYLIST).
    		buildBHCatlgorRecs(table, ctryAudElemMap, false, debugSb);
			// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some
			// may be deleted
			// according to BH FS ABR XML System Feed Mapping 20121001.doc 
    		//Bing add 6/28/2013 BH FS ABR XML System Feed Mapping 20130614.doc
    		//The countrylist of AVAIL is the sub set of countrylist of Planed AVAIL. so Countrylist of Planed AVAIL can instead of the union of All AVAIL types, 
    		Vector AvailVct = new Vector();
    		if ("146".equals(availofType)){
    			AvailVct = tmfAvailVct;//getAVAILofType(table, tmfAvailVct, availofType, debugSb);
    		}else {
    			AvailVct = tmfAvailVct;
    		}				
			for (int i = 0; i < AvailVct.size(); i++) {
				DiffEntity availDiff = (DiffEntity) AvailVct.elementAt(i);
				buildCtryAudRecs(ctryAudElemMap, availDiff, null, false, debugSb);
			}// end each planned avail
		}
		// output the elements
//		Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
		Collection ctryrecs = ctryAudElemMap.values();
		Iterator itr = ctryrecs.iterator();
		//add WorldWide search
		Hashtable xmltable = null;
		if(itr.hasNext() && (isfeacnty || isAvailcnty)){
			xmltable = searchSLORGNPLNTCODE( dbCurrent, parentItem.getCurrentEntityItem(),  debugSb);
			
//			XMLSLEORGNPLNTCODESearchElem search = new XMLSLEORGNPLNTCODESearchElem();			
//			try {
//				xmltable = search.doSearch(dbCurrent, parentItem.getCurrentEntityItem(), debugSb);
//			} catch (SBRException exc) {
//				java.io.StringWriter exBuf = new java.io.StringWriter();
//				exc.printStackTrace(new java.io.PrintWriter(exBuf));
//				ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements search!! SBRException: "+exBuf.getBuffer().toString()+ NEWLINE);
//				throw new MiddlewareException("Error :  doSearch XMLSLEORGNPLNTCODESearchElem.java");				
//			}			
		}
		//add WorldWide search end
		while (itr.hasNext()) {
			CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
			// Rows marked as Delete do not need further updating and the Action
			// should not be changed by further updating.
			//if (!ctryAudRec.isDeleted()) {	
				
				// updated based on onte 2013-1-18 Wayne:
//				There are two cases where a TMF would not have a'Planned Availability'
//				RPQ (aka Special Bid) - this would not have any AVAILs
//				Old Data is where the MODEL.ANNDATE < '20100301'  - this may have other AVAILs (e.g.'Last Order'
//
//
//				From the spec (note: since this is for a TMF (PRODSTRUCT), the second item is the one that applies. In the case of Old Data where there isn't a'Planned Availability' but there is a'Last Order', there would be an <AVAILABILITYELEMENT> based on #2 and the'Last Order' avails would provide dates whenever a country in the COUNTRYLIST exists for a country FROM #2.)
//
//				The list of countries for <COUNTRY_FC) is based on the following in priority order (i.e. the first one that is applicable is used):
//				1. The aggregated of the countries found for 'Availability' (AVAIL) 'Country List' (COUNTRYLIST) where Availability Type (AVAILTYPE) equals one of the following in priority order:
//				'Planned Availability' (146)
//				'Last Order' (149)
//				'End of Service' (151)
//				2.	For PRODSTRUCT
//				The intersection of the following if not empty:
//				MODELAVAIL-d: AVAIL.COUNTRYLIST where AVAILTYPE = 'Planned Availability' (146)
//				FEATURE.COUNTRYLIST
//				FEATURE.COUNTRYLIST
//				3.	For SWPRODSTRUCT 
//				MODELAVAIL-d: AVAIL.COUNTRYLIST where AVAILTYPE = 'Planned Availability' (146)
//				World Wide (i.e. all countries)
				//In case 2, still using LastOrder AVAIL from TMF-AVAIL.			
//				 find firstorder avail for this country
			DiffEntity plaAvailDiffT1 = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRec
				.getCountry(), true, debugSb);	
			DiffEntity plaAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);	
			// find firstorder avail for this country
			DiffEntity foAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			// find lastorder avail for this country
			DiffEntity loAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			// find 'End of Service' (151) for this country
			DiffEntity endAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			//find 'End of Marketing' (200) for this country 
			DiffEntity endMktAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", tmfAvailVct, "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			//Why using MODEL.AVAIL (149), because doc BH FS ABR XML System Feed Mapping20121001.doc page 99:
			//ID 46.00 <PUBTO> 4.	Min of the following three that are applicable
//				1)	First of the following two
//				a)	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = last Order(149)
//				By the AVAIL.COUNTRYLIST
//				b)	MODEL. WTHDRWEFFCTVDATE;
//				By AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability(146)
//				2)	SWFEATURE. WITHDRAWDATEEFF_T; 
//				Applies to all Countries that get this far
//				3)	FEATURE. WITHDRAWDATEEFF_T
//				By FEATURE.COUNTRYLIST
			DiffEntity plModelAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", modallVct, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			
			DiffEntity loModelAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", modallVct, "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			// find 'End of Service' (151) for this country
			DiffEntity endModelAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", modallVct, "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			//find 'End of Marketing' (200) for this country 
			DiffEntity endModelMktAvailDiff = AvailUtil.getEntityForAttrs(table, "AVAIL", modallVct, "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRec
				.getCountry(), false, debugSb);
			
			//because need deirve<PUBTO> 3.PRODSTRUCT. WTHDRWEFFCTVDATE applicable countries are intersection of feature and avail. so need feature countrylist here.
			//3.	PRODSTRUCT. WTHDRWEFFCTVDATE
			//The applicable countries are the intersection of the MODEL AVAIL.COUNTRYLIST where AVAILTYPE = last Order(149). and the FEATURE.COUNTRYLIST.

			Vector[] feaCtry = getFeatureCtryVector(table, parentItem, debugSb);
			
			if(feaCtry==null){
				feaCtry = new Vector[2];
			}				
			// find catlgor for this country is equal to
			// CATLGOR.OFFCOUNTRY and PRODSTRUCT.AUDIEC
			DiffEntity catlgorDiff = getCatlgor(table, ctryAudRec.getCountry(), debugSb);
			//if country a both exist at T1 and T2, but from the different Planed AVAI. the old approach could not find ANNDATE at T1. so get T1 Planed avail for each country.
			DiffEntity[] plaAvailDiffs = new DiffEntity[2];
			plaAvailDiffs[0] = plaAvailDiffT1;
			plaAvailDiffs[1] = plaAvailDiff;
			
			
			ctryAudRec.setAllFields(plaAvailDiffs, feaCtry, catlgorDiff, parentItem, foAvailDiff, loAvailDiff, endAvailDiff, endMktAvailDiff, loModelAvailDiff, endModelAvailDiff, endModelMktAvailDiff, plModelAvailDiff, table,  isExistfinal,  compatModel, debugSb);
		//}
		    if (ctryAudRec.isDisplayable()||ctryAudRec.isrfrDisplayable()) {
				if(isfeacnty ||isAvailcnty){
					if (xmltable == null){
					    xmltable = searchSLORGNPLNTCODE( dbCurrent, parentItem.getCurrentEntityItem(),  debugSb);
					}
					createNodeSet(table, document, parent, parentItem, ctryAudRec, xmltable, debugSb);
				}else{
					if (isPlannedAvail(ctryAudRec.availDiff)){
						createNodeSet(table, document, parent, parentItem, ctryAudRec, debugSb);
					} else {
						//1If there isn’t an AVAIL where AVAILTYPE=Planned Availability(146) then retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.MODCATG equals 
						//a.MODEL.COFCAT if MODEL.COFCAT =Hardware or Software
						//.....
						if (xmltable == null){
						    xmltable = searchSLORGNPLNTCODE( dbCurrent, parentItem.getCurrentEntityItem(),  debugSb);
						}  						
						createNodeSet(table, document, parent, parentItem, ctryAudRec, xmltable, debugSb);
						
					}    		
				}
			} else {
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements no changes found for " + ctryAudRec + NEWLINE);
			}
			ctryAudRec.dereference();
		}

		// release memory
		if (xmltable != null){
			xmltable.clear();	
		}
		ctryAudElemMap.clear();
		Vector annVct = (Vector) table.get("ANNOUNCEMENT");
		Vector availVct = (Vector) table.get("AVAIL");
		if (annVct != null) {
			annVct.clear();
		}
		if (availVct != null) {
			availVct.clear();
		}
	}
   private Hashtable searchSLORGNPLNTCODE(Database dbCurrent, EntityItem item, StringBuffer debugSb) throws SQLException, MiddlewareException{
    	XMLSLEORGNPLNTCODESearchElem search = new XMLSLEORGNPLNTCODESearchElem();
    	Hashtable xmltable = new Hashtable();
    	try {
    		xmltable = search.doSearch(dbCurrent, item, debugSb);
    	} catch (MiddlewareShutdownInProgressException e) {
    		e.printStackTrace();
    	} catch (SBRException exc) {
    		java.io.StringWriter exBuf = new java.io.StringWriter();
    		exc.printStackTrace(new java.io.PrintWriter(exBuf));
    		ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements search!! SBRException: "+exBuf.getBuffer().toString()+ NEWLINE);
    		exc.printStackTrace();				
    	}
    	ABRUtil.append(debugSb,"XMLTMFAVAILElem.addElements search!! " + NEWLINE);
        
    	return xmltable;
    }
	 /********************
	    * get planned avails - availtype cant be changed
	    */
    private boolean isPlannedAvail(DiffEntity diffitem)
    {

    	boolean result = false;
        if (diffitem==null){
			return false;
		}
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem prioritem = diffitem.getPriorEntityItem();
		if (diffitem.isDeleted()){
			EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute("AVAILTYPE");
			if (fAtt!= null && fAtt.isSelected("146")){
				result = true;
			}
		}else{
			EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("AVAILTYPE");
			if (fAtt!= null && fAtt.isSelected("146")){
				result = true;
			}
		}

		return result;
	}

	private void buildWWCtryAudRecs(Hashtable table,  DiffEntity parentItem, TreeMap ctryAudElemMap, Database dbCurrent, boolean T1, StringBuffer debugSb)
		throws MiddlewareException, SQLException {
		ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = "SG";
//        String anndate = CHEAT;
//        String withdrawanndate = CHEAT;
//        String withdrawdate = CHEAT;
//        EntityItem item = null;
//        EntityItem curritem = null;          
        ABRUtil.append(debugSb,"buildWWCtryAudRecs at " + (T1?"T1":"T2")  + " Time."+ NEWLINE);
        
        if (parentItem != null){
        	EntityItem curritem = parentItem.getCurrentEntityItem();
		    strEnterprise = curritem.getProfile().getEnterprise();
        }
//        if (parentItem != null){
//        	item = parentItem.getCurrentEntityItem();
//			if (item.hasDownLinks()) {
//				for (int i = 0; i < item.getDownLinkCount(); i++) {
//					EntityItem entity = (EntityItem) item.getDownLink(i);
//					if (entity.getEntityType().equals("MODEL")) {
//						curritem = entity;
//						break;
//					}
//				}
//			} 
//			if (item.hasUpLinks()) {
//				for (int i = 0; i < item.getUpLinkCount(); i++) {
//					EntityItem entity = (EntityItem) item.getUpLink(i);
//					if (entity.getEntityType().equals("SWFEATURE")) {
//						withdrawanndate = PokUtils.getAttributeValue(entity, "WITHDRAWANNDATE_T", ", ", CHEAT, false);
//					    withdrawdate = PokUtils.getAttributeValue(entity, "WITHDRAWDATEEFF_T", ", ", CHEAT, false);
//						break;
//					}
//				}
//			}
//        	if (curritem != null){
//        		strEnterprise = curritem.getProfile().getEnterprise();
//    			anndate = PokUtils.getAttributeValue(curritem, "ANNDATE",", ", CHEAT, false);
////    		    withdrawanndate = PokUtils.getAttributeValue(curritem, "WTHDRWEFFCTVDATE",", ", CHEAT, false);
////    		    withdrawdate = PokUtils.getAttributeValue(curritem, "WITHDRAWDATE",", ", CHEAT, false);
//    		   }
//        	
//        	 ABRUtil.append(debugSb,"buildWWCtryAudRecs anndate from Model, withdrawanndte/withdrawdate from SWFEATURE "  + curritem.getKey() + " thedate: "
//					+ anndate + " withdrawanndate: " + withdrawanndate +  " withdrawdate: " + withdrawdate +NEWLINE);
//        }
		
		try {
			 // for all World Wide Countries 
			 rs = dbCurrent.callGBL9999A(returnStatus, strEnterprise, "GENAREASELECTION","1999","COUNTRYLIST");
			 rdrs = new ReturnDataResultSet(rs);
		    } finally {
			if (rs != null){
				rs.close();
				rs = null;
			}
			dbCurrent.commit();
			dbCurrent.freeStatement();
			dbCurrent.isPending();
		}
		if (T1){
			for (int iii = 0; iii < rdrs.size(); iii++) {
				String country = rdrs.getColumn(iii, 0).trim();
				ABRUtil.append(debugSb,"CQ BHALM109267 derivefrommodel world wide counry "+ country +NEWLINE);
				
				String mapkey = country;
				if (!ctryAudElemMap.containsKey(mapkey)){
					CtryAudRecord ctryAudRec = new CtryAudRecord(null, country);
					//For the the case of - If there isn't an AVAIL where AVAILTYPE="Planned Availability" (146) and ANNDATE < '20100301' - 
	                //ANNNUMBER  is empty
					//PLANNEDAVAILABILITY is MODEL.ANNDATE 
					ctryAudRec.action = DELETE_ACTIVITY;	
					ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
				}
			}			
			
		} else {
			for (int iii = 0; iii < rdrs.size(); iii++) {
				String country = rdrs.getColumn(iii, 0).trim();
				ABRUtil.append(debugSb,"CQ BHALM109267 derivefrommodel world wide counry "+ country +NEWLINE);
				
				String mapkey = country;
				if (!ctryAudElemMap.containsKey(mapkey)){
					CtryAudRecord ctryAudRec = new CtryAudRecord(null, country);
					//For the the case of - If there isn't an AVAIL where AVAILTYPE="Planned Availability" (146) and ANNDATE < '20100301' - 
	                //ANNNUMBER  is empty
//					//PLANNEDAVAILABILITY is MODEL.ANNDATE 
					ctryAudRec.action = UPDATE_ACTIVITY;
//					ctryAudRec.anndate = anndate;
//					ctryAudRec.firstorder = anndate;
//					ctryAudRec.plannedavailability = anndate;
//					ctryAudRec.pubfrom = anndate;
//					ctryAudRec.pubto = withdrawdate;
//					ctryAudRec.wdanndate = withdrawanndate;
//					ctryAudRec.lastorder = withdrawdate;		
					ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);					
				} else{
					//overwrite it
					CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
					rec.setUpdateAvail(null);
					rec.setAction(CHEAT);
//					rec.anndate = anndate;
//					rec.firstorder = anndate;
//					rec.plannedavailability = anndate;
//					rec.pubfrom = anndate;
//					rec.pubto = withdrawdate;
//					rec.wdanndate = withdrawanndate;
//					rec.lastorder = withdrawdate;					
					ABRUtil.append(debugSb,"WARNING buildWWCtryAudRecs country" + mapkey + "already exists, overwrite it. " + NEWLINE);				
				}				
			}			
		}		
	}
	/**
	 * @param dbCurrent
	 * @param ctryAudElemMap
	 * @param debugSb
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void buildFeatureCtryAudRecs(Hashtable table,  DiffEntity parentItem, TreeMap ctryAudElemMap, boolean T1, StringBuffer debugSb)
		throws MiddlewareException {
		Vector allVct = (Vector) table.get("FEATURE");
		ABRUtil.append(debugSb,"buildT1FeatureCtryAudRecs at " + (T1?"T1":"T2")  + " Time."+ NEWLINE);
		if (T1){
			for (int ii=0; ii<allVct.size(); ii++){
				DiffEntity feaDiff = (DiffEntity)allVct.elementAt(ii);
				EntityItem prioritem = feaDiff.getPriorEntityItem();
				if (!feaDiff.isNew()) { // If the AVAIL was deleted, set Action
					// = Delete
					// mark all records as delete
					EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
					ABRUtil.append(debugSb,"buildFeatureCtryAudRecs for deleted/udpate feature: ctryAtt "
						+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
					if (ctryAtt != null) {
						MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected()) {
								String ctryVal = mfArray[im].getFlagCode();
								String mapkey = ctryVal;
								if (ctryAudElemMap.containsKey(mapkey)) {
									// dont overwrite
									CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
									ABRUtil.append(debugSb,"WARNING buildFeatureCtryAudRecs for deleted/udpate " + feaDiff.getKey() + " " + mapkey
										+ " already exists, keeping orig " + rec + NEWLINE);
								} else {
									CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
									ctryAudRec.setAction(DELETE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
									ABRUtil.append(debugSb,"buildFeatureCtryAudRecs for deleted/udpate:" + feaDiff.getKey() + " rec: "
										+ ctryAudRec.getKey() + NEWLINE);
								}
							}
						}
					}	
				} 
			}
		}else {
			for (int ii=0; ii<allVct.size(); ii++){
				DiffEntity feaDiff = (DiffEntity)allVct.elementAt(ii);
				EntityItem curritem = feaDiff.getCurrentEntityItem();
				if (!feaDiff.isDeleted()) { // If the AVAIL was added or updated,
					// set Action = Update
					// mark all records as update
					EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
					ABRUtil.append(debugSb,"buildFeatureCtryAudRecs for new /udpated feature:  ctryAtt "
						+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
					if (ctryAtt != null) {
						MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected()) {
								String ctryVal = mfArray[im].getFlagCode();
								String mapkey = ctryVal;
								if (ctryAudElemMap.containsKey(mapkey)) {
									CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
									ABRUtil.append(debugSb,"WARNING buildFeatureCtryAudRecs for new /updated " + feaDiff.getKey() + " " + mapkey
										+ " already exists, replacing orig " + rec + NEWLINE);
									rec.setUpdateAvail(null);
									rec.setAction(CHEAT);
								} else {
									CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
									ctryAudRec.setAction(UPDATE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
									ABRUtil.append(debugSb,"buildFeatureCtryAudRecs for new/ updated:" + feaDiff.getKey() + " rec: "
										+ ctryAudRec.getKey() + NEWLINE);
								}
							}
						}
					}
				} // end avail existed at both t1 and t2					
			}		
		}			
	}
	/**
	 * @param dbCurrent
	 * @param ctryAudElemMap
	 * @param debugSb
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private Vector[] getFeatureCtryVector(Hashtable table, DiffEntity parentItem, StringBuffer debugSb)
		throws MiddlewareException {
		Vector currFeaVct = new Vector(1);
		Vector prevFeaVct = new Vector(1);
		Vector vct[] = new Vector[2];
		vct[0] = prevFeaVct;
		vct[1] = currFeaVct;
		String type = "";
		if ("PRODSTRUCT".equals(parentItem.getEntityType())){
			type = "FEATURE";
		}else if ("SWPRODSTRUCT".equals(parentItem.getEntityType())){
			return null;
	    }
		Vector allVct = (Vector) table.get(type);
		
		for (int ii=0; ii<allVct.size(); ii++){
			DiffEntity feaDiff = (DiffEntity)allVct.elementAt(ii);
			EntityItem curritem = feaDiff.getCurrentEntityItem();
			EntityItem prioritem = feaDiff.getPriorEntityItem();
			if (prioritem != null) { 
				// = Delete
				// mark all records as delete
				EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"putFeatureCtryVector for deleted feature: ctryAtt "
					+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							String mapkey = ctryVal;
							if (!prevFeaVct.contains(mapkey)) {
								prevFeaVct.add(mapkey);
							} 
						}
					}
				}
			} 
			if (curritem != null) { // If the AVAIL was added or updated,
				// set Action = Update
				// mark all records as update
				EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"putFeatureCtryVector for new feature:  ctryAtt "
					+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							String mapkey = ctryVal;
							if (!currFeaVct.contains(mapkey)) {
								currFeaVct.add(mapkey);
							} 
						}
					}
				}
			} 
		}
		return vct;
	}

	/***************************************************************************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table,Document document, Element parent,DiffEntity parentItem, CtryAudRecord ctryAudRec, StringBuffer debugSb) {
		
		if(ctryAudRec.isDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create
			// COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);
			
			// add child nodes
			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAction()));
			elem.appendChild(child);
			child = (Element) document.createElement("STATUS");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAvailStatus()));
			elem.appendChild(child);
			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAnndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAnnnumber()));
			elem.appendChild(child);
			child = (Element) document.createElement("FIRSTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getFirstorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPlannedavailability()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBFROM");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPubFrom()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBTO");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPubTo()));
			elem.appendChild(child);
			child = (Element) document.createElement("WDANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getWdanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			//add SLEORGGRP
			//if("SWPRODSTRUCT".equals(parentItem.getEntityType())){
			//SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, "D:SWPRODSTRUCTAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			//}
			//else{
			//SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, "D:OOFAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			//}
			SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, null, ctryAudRec.country, ctryAudRec.action, debugSb);
		}
		
		if(ctryAudRec.isrfrDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create
																		// COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);
	
			// add child nodes
			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfraction()));
			elem.appendChild(child);
			child = (Element) document.createElement("STATUS");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfravailStatus()));
			elem.appendChild(child);
			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfranndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrannnumber()));
			elem.appendChild(child);
			child = (Element) document.createElement("FIRSTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrfirstorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrplannedavailability()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBFROM");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubfrom()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBTO");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubto()));
			elem.appendChild(child);
			child = (Element) document.createElement("WDANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrwdanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
			//add SLEORGGRP
			//if("SWPRODSTRUCT".equals(parentItem.getEntityType())){
			//SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, "D:SWPRODSTRUCTAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			//}
			//else{
			//SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, "D:OOFAVAIL:D:AVAIL:D:AVAILSLEORGA:D", ctryAudRec.country, ctryAudRec.action, debugSb);
			//}
			SLEORGGRP.displayAVAILSLEORG(table, document, elem, parentItem.getCurrentEntityItem(), ctryAudRec.availDiff, null, ctryAudRec.country, ctryAudRec.action, debugSb);
		}
	
	
	}
	
	/***************************************************************************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table,Document document, Element parent,DiffEntity parentItem, CtryAudRecord ctryAudRec, Hashtable xmltable, StringBuffer debugSb) {
		
		if(ctryAudRec.isDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create
			// COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);
			
			// add child nodes
			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAction()));
			elem.appendChild(child);
			child = (Element) document.createElement("STATUS");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAvailStatus()));
			elem.appendChild(child);
			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAnndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getAnnnumber()));
			elem.appendChild(child);
			child = (Element) document.createElement("FIRSTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getFirstorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPlannedavailability()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBFROM");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPubFrom()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBTO");
			child.appendChild(document.createTextNode("" + ctryAudRec.getPubTo()));
			elem.appendChild(child);
			child = (Element) document.createElement("WDANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getWdanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			//add createWorldWideAvilNote
			createWorldWideAvilNote(document, ctryAudRec, xmltable, elem, debugSb);
		}
		if(ctryAudRec.isrfrDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create
																		// COUNTRYAUDIENCEELEMENT
			addXMLAttrs(elem);
			parent.appendChild(elem);
	
			// add child nodes
			Element child = (Element) document.createElement("AVAILABILITYACTION");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfraction()));
			elem.appendChild(child);
			child = (Element) document.createElement("STATUS");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfravailStatus()));
			elem.appendChild(child);
			child = (Element) document.createElement("COUNTRY_FC");
			child.appendChild(document.createTextNode("" + ctryAudRec.getCountry()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfranndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrannnumber()));
			elem.appendChild(child);
			child = (Element) document.createElement("FIRSTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrfirstorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("PLANNEDAVAILABILITY");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrplannedavailability()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBFROM");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubfrom()));
			elem.appendChild(child);
			child = (Element) document.createElement("PUBTO");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrpubto()));
			elem.appendChild(child);
			child = (Element) document.createElement("WDANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrwdanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFMARKETANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEomannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			//Story 1865979 Withdrawal RFA Number generation
			child = (Element) document.createElement("ENDOFSERVICEANNNUMBER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosannnum()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
	 		//add createWorldWideAvilNote
	 		createWorldWideAvilNote(document, ctryAudRec, xmltable, elem, debugSb);
		}
	}
	
	/**
	 * this method work for Feature.countrylist, only show when action is Update. 
	 * update the duplicate SLEORGGRP in the SLEORGGRPLIST
	 * @param document
	 * @param ctryAudRec
	 * @param xmltable
	 * @param elem
	 * @throws DOMException
	 */
	private void createWorldWideAvilNote(Document document, CtryAudRecord ctryAudRec, Hashtable xmltable, Element elem, StringBuffer debugSb) throws DOMException {
		Element child;
		String wwcry = ctryAudRec.getCountry();
		Object sop_cry = (Object)xmltable.get(wwcry);
		if(sop_cry!=null && UPDATE_ACTIVITY.equals(ctryAudRec.action)){
			//SLEORGGRPLIST
			Element SLEORGGRPLISTElement = (Element) document.createElement("SLEORGGRPLIST");
			elem.appendChild(SLEORGGRPLISTElement);
			Vector vct[] = (Vector[])xmltable.get(wwcry);
			Hashtable SLEORGGRP = (Hashtable)vct[0].get(0);
			Enumeration egroup = SLEORGGRP.keys();
			Vector multiVector = new Vector();
			while (egroup.hasMoreElements()) {
				String key = (String) egroup.nextElement();
				key = key.trim();
				if(multiVector.contains(key)){
					continue;
				}
				multiVector.add(key);
				Element childElement = (Element) document.createElement("SLEORGGRPELEMENT");
				SLEORGGRPLISTElement.appendChild(childElement);
				child = (Element) document.createElement("SLEOORGGRPACTION");
				child.appendChild(document.createTextNode("" + UPDATE_ACTIVITY));
				childElement.appendChild(child);
				child = (Element) document.createElement("SLEORGGRP");
				child.appendChild(document.createTextNode("" + key));
				childElement.appendChild(child);
			}
			multiVector.clear();
			//SLEORGNPLNTCODELIST
			Element SLEORGNPLNTCODELISTElement = (Element) document.createElement("SLEORGNPLNTCODELIST");
			elem.appendChild(SLEORGNPLNTCODELISTElement);
			Vector entityVector = vct[1];
			Vector duplVector = new Vector();
			for(int i=0;i<entityVector.size();i++){
				EntityItem entity = (EntityItem)entityVector.get(i);
				String sleorg = PokUtils.getAttributeValue(entity, "SLEORG",", ", "", false);
				String plntcd = PokUtils.getAttributeValue(entity, "PLNTCD",", ", "", false);
				String key = sleorg + plntcd;
				if(duplVector.contains(key)){
					continue;
				}
				duplVector.add(key);
				Element childElement = (Element) document.createElement("SLEORGNPLNTCODEELEMENT");
				SLEORGNPLNTCODELISTElement.appendChild(childElement);
				child = (Element) document.createElement("SLEORGNPLNTCODEACTION");
				child.appendChild(document.createTextNode("" + UPDATE_ACTIVITY));
				childElement.appendChild(child);
				child = (Element) document.createElement("SLEORG");				
				child.appendChild(document.createTextNode("" + sleorg));
				childElement.appendChild(child);
				child = (Element) document.createElement("PLNTCD");				
				child.appendChild(document.createTextNode("" + plntcd));
				childElement.appendChild(child);		
			}
			duplVector.clear();
		}else{
			//SLEORGGRPLIST
			Element SLEORGGRPLISTElement = (Element) document.createElement("SLEORGGRPLIST");
			elem.appendChild(SLEORGGRPLISTElement);
			//SLEORGNPLNTCODELIST
			Element SLEORGNPLNTCODELISTElement = (Element) document.createElement("SLEORGNPLNTCODELIST");
			elem.appendChild(SLEORGNPLNTCODELISTElement);
		}
	}

//	/***************************************************************************
//	 * get audience values from t1 and t2 for this model, do it once and use for
//	 * each avail
//	 */
//	private Vector[] getModelAudience(DiffEntity modelDiff, StringBuffer debugSb) {
//		ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience for " + modelDiff.getKey() + NEWLINE);
//
//		EANFlagAttribute audienceAtt = (EANFlagAttribute) modelDiff.getCurrentEntityItem().getAttribute("AUDIEN");
//		Vector currAudVct = new Vector(1);
//		Vector prevAudVct = new Vector(1);
//		Vector vct[] = new Vector[2];
//		vct[0] = currAudVct;
//		vct[1] = prevAudVct;
//		ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience cur audienceAtt " + audienceAtt + NEWLINE);
//		if (audienceAtt != null) {
//			MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
//			for (int i = 0; i < mfArray.length; i++) {
//				// get selection
//				if (mfArray[i].isSelected()) {
//					currAudVct.addElement(mfArray[i].toString()); // this is
//																	// long desc
//				}
//			}
//		}
//
//		if (!modelDiff.isNew()) {
//			audienceAtt = (EANFlagAttribute) modelDiff.getPriorEntityItem().getAttribute("AUDIEN");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience new audienceAtt " + audienceAtt + NEWLINE);
//			if (audienceAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
//				for (int i = 0; i < mfArray.length; i++) {
//					// get selection
//					if (mfArray[i].isSelected()) {
//						prevAudVct.addElement(mfArray[i].toString()); // this
//																		// is
//																		// long
//																		// desc
//					}
//				}
//			}
//		}
//
//		return vct;
//	}

	/**
	 * ****************** this method has changed for BH. Create rows in the
	 * table as follows: Insert one row for each Audience in MODEL.AUDIEN & each
	 * Country in AVAIL.COUNTRYLIST where AVAILTYPE = 146 If the AVAIL was
	 * deleted, set Action = Delete If the AVAIL was added or updated, set
	 * Action = Update
	 * 
	 * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
	 * If AVAIL.COUNTRYLIST has a country deleted, set that row's Action =
	 * Delete
	 * 
	 * Note: Rows marked as Delete do not need further updating and the Action
	 * should not be changed by further updating. If any of the following steps
	 * have data that do not match an existing row in this table, ignore that
	 * data.
	 */

	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, Vector[] feaCtry, boolean T1, StringBuffer debugSb) {

		ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildCtryAudRecs " + (T1?"T1 ":"T2 ") + availDiff.getKey() + NEWLINE);
		

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA
		// at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update
		// to override it
		if (T1){
			EntityItem prioritem = availDiff.getPriorEntityItem();
			if (!availDiff.isNew()) { // If the AVAIL was deleted, set Action										// = Delete
				// mark all records as delete
				EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate avail: ctryAtt "
					+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							String mapkey = ctryVal;
							if (ctryAudElemMap.containsKey(mapkey)) {
								// dont overwrite
								CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
								ABRUtil.append(debugSb,"WARNING buildT1CtryAudRecs for delete/udpate" + availDiff.getKey() + " " + mapkey
									+ " already exists, keeping orig " + rec + NEWLINE);
							} else {
								if (feaCtry != null && feaCtry[0].contains(mapkey) ){
									CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
									ctryAudRec.setAction(DELETE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
									ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate:" + availDiff.getKey() + " rec: "
										+ ctryAudRec.getKey() + NEWLINE);
								} else if (feaCtry == null){
									CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
									ctryAudRec.setAction(DELETE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
									ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate:" + availDiff.getKey() + " rec: "
										+ ctryAudRec.getKey() + NEWLINE);
								}															
							}
						}
					}
				}
			} 
		} else{
			EntityItem curritem = availDiff.getCurrentEntityItem();
			if (!availDiff.isDeleted()){ //If the AVAIL was added or updated, set Action = Update
				// mark all records as update			
				EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildCtryAudRecs for new/udpate avail:  ctryAtt and anncodeAtt "+
						PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+ 
				        PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") +NEWLINE);
				if (ctryAtt!=null){
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++){
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();					
								String mapkey = ctryVal;
								if (ctryAudElemMap.containsKey(mapkey)){
									CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
									if (isPlannedAvail(availDiff) || DELETE_ACTIVITY.equals(rec.action)){
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new/update "+availDiff.getKey()+
											" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
										if(UPDATE_ACTIVITY.equals(rec.action)){
											rec.setUpdateAvail(availDiff);
										}else{
											rec.setUpdateAvail(availDiff);
											rec.setAction(CHEAT);
										}
									}
								}else{
									if (feaCtry != null && feaCtry[1].contains(mapkey) ){
										CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
										ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
										ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildCtryAudRecs for new/update:"+availDiff.getKey()+" rec: "+
										ctryAudRec.getKey() + NEWLINE);										
									}else if (feaCtry == null){
										CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
										ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
										ABRUtil.append(debugSb,"XMLTMFAVAILElem.buildCtryAudRecs for new/update:"+availDiff.getKey()+" rec: "+
										ctryAudRec.getKey() + NEWLINE);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * BH FS ABR XML System Feed Mapping 20130614.doc
	 * build BHCATLGOR.countrylist in AVAILBILITYLIST Country_fc.
	 * AVAIL.COUNTRYLIST. In other words, the list of countries is the UNION of COUNTRLIST for all AVAILs and the UNION of anyCatalog Overrides(BHCATLGOR Country List(COUNTRYLIST
	 * @param ctryAudElemMap
	 * @param T1
	 * @param debugSb
	 */
	
	private void buildBHCatlgorRecs(Hashtable table, TreeMap ctryAudElemMap, boolean T1, StringBuffer debugSb) {

		Vector allVct = (Vector) table.get("BHCATLGOR");
		String attrCode2 = BHCOUNTRYLIST; // need flag code
		ABRUtil.append(debugSb,"buildBHCatlgorRecs looking for T1" +  T1 + " in CATLGOR allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct != null) {
			if (T1){
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem item = diffitem.getPriorEntityItem();
					//add the check of the status of BHCATLGOR
					if (!diffitem.isNew()) {
						String BHStatus  = PokUtils.getAttributeFlagValue(item,"STATUS");
						if(!STATUS_FINAL.equals(BHStatus)){
							continue;
						}
						EANFlagAttribute ctryAtt = (EANFlagAttribute) item.getAttribute(attrCode2);
						if (ctryAtt != null) {
							MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
							for (int im = 0; im < mfArray.length; im++) {
								// get selection
								if (mfArray[im].isSelected()) {
									String ctryVal = mfArray[im].getFlagCode();
									String mapkey = ctryVal;
									if (ctryAudElemMap.containsKey(mapkey)) {
										// dont overwrite
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										ABRUtil.append(debugSb,"WARNING buildBHCatlgorRecs for delete/udpate" + mapkey
											+ " already exists, keeping orig " + rec + NEWLINE);
									}else{
							            CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
										ctryAudRec.setAction(DELETE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"buildBHCatlgorRecs for delete/udpate rec: "
											+ ctryAudRec.getKey() + NEWLINE);														
									}
								}
							}
						}
					}
				}			
			} else{
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem item = diffitem.getCurrentEntityItem();
					//add the check of the status of BHCATLGOR
					if (!diffitem.isDeleted()) {
						String BHStatus  = PokUtils.getAttributeFlagValue(item,"STATUS");
						if(!STATUS_FINAL.equals(BHStatus)){
							continue;
						}
						EANFlagAttribute ctryAtt = (EANFlagAttribute) item.getAttribute(attrCode2);
						if (ctryAtt != null) {
							MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
							for (int im = 0; im < mfArray.length; im++) {
								// get selection
								if (mfArray[im].isSelected()) {
									String ctryVal = mfArray[im].getFlagCode();
									String mapkey = ctryVal;
									if (ctryAudElemMap.containsKey(mapkey)) {
										CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
										rec.setUpdateAvail(null);
										rec.setAction(CHEAT);
										ABRUtil.append(debugSb,"WARNING buildBHCatlgorRecs for new/update" + mapkey
											+ " already exists, replace orig with Update action" + rec + NEWLINE);
									}else{
							            CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
										ctryAudRec.setAction(UPDATE_ACTIVITY);
										ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
										ABRUtil.append(debugSb,"buildBHCatlgorRecs for new/udpate rec: "
											+ ctryAudRec.getKey() + NEWLINE);														
									}
								}
							}
						}
					}
				}								
			}
		}				
	}
	
	/***************************************************************************
	 * get planned avails - availtype cant be changed
	 */
	private Vector getPlannedAvails(Hashtable table, Vector allVct, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		ABRUtil.append(debugSb,"XMLTMFAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}

		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey() + " AVAILTYPE: "
					+ PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			} else {
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.getPlannedAvails checking[" + i + "]:" + diffitem.getKey() + " AVAILTYPE: "
					+ PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			}
		}

		return avlVct;
	}
	

	/***************************************************************************
	 * get planned avails and last order avali and end service avail  - availtype cant be changed
	 * 1.	The aggregated of the countries found for Availability (AVAIL) Country List (COUNTRYLIST) where Availability Type (AVAILTYPE) equals one of the following in priority order:
	 * 1. planed avail
	 * 2. LastOrder avail
	 * 3. End of service avail
	 * availofType is "146"|"149"|"151"
	 */
//	private Vector getAVAILofType(Hashtable table, Vector allVct, String availofType, StringBuffer debugSb) {
//		Vector avlVct = new Vector(1);
//		ABRUtil.append(debugSb,"XMLTMFAVAILElem looking for AVAILTYPE " +  availofType  + " allVct.size:"
//			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
//		if (allVct == null) {
//			return avlVct;
//		}
//
//		// find those of specified type
//		for (int i = 0; i < allVct.size(); i++) {
//			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
//			EntityItem curritem = diffitem.getCurrentEntityItem();
//			EntityItem prioritem = diffitem.getPriorEntityItem();
//			if (diffitem.isDeleted()) {
//				ABRUtil.append(debugSb,"XMLTMFAVAILElem checking[" + i + "]: deleted " + diffitem.getKey() + " AVAILTYPE: "
//					+ PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
//				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
//				if (fAtt != null && (fAtt.isSelected(availofType))) {
//					avlVct.add(diffitem);
//				}
//			} else {
//				ABRUtil.append(debugSb,"XMLAVAILElem checking[" + i + "]: update or new " + diffitem.getKey() + " AVAILTYPE: "
//					+ PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
//				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
//				if (fAtt != null && fAtt.isSelected(availofType)) {
//					avlVct.add(diffitem);
//				}
//			}
//		}
//
//		return avlVct;
//	}


//	/***************************************************************************
//	 * get entity with specified values - should only be one could be two if one
//	 * was deleted and one was added, but the added one will override and be an
//	 * 'update'
//	 */
//	private DiffEntity getEntityForAttrs(Hashtable table, String etype, Vector allVct, String attrCode, String attrVal, String attrCode2,
//		String attrVal2, boolean T1, StringBuffer debugSb) {
//		DiffEntity diffEntity = null;
//		ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs  at T1 "  + T1 +" looking for " + attrCode + ":" + attrVal + " and " + attrCode2 + ":"
//			+ attrVal2 + " in " + etype + " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
//		if (allVct == null) {
//			return diffEntity;
//		}
//		if (T1){
//			// find those of specified type
//			for (int i = 0; i < allVct.size(); i++) {
//				DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
//				EntityItem prioritem = diffitem.getPriorEntityItem();
//				if (!diffitem.isNew()) {
//					ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs checking[" + i + "]: deleted/update " + diffitem.getKey() + " "
//						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
//						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
//					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
//					if (fAtt != null && fAtt.isSelected(attrVal)) {
//						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
//						if (fAtt != null && fAtt.isSelected(attrVal2)) {
//							diffEntity = diffitem; // keep looking for one that is
//													// not deleted
//						}
//					}
//				}
//			}			
//		}else{
//			// find those of specified type
//			for (int i = 0; i < allVct.size(); i++) {
//				DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
//				EntityItem curritem = diffitem.getCurrentEntityItem();
//				EntityItem prioritem = diffitem.getPriorEntityItem();
//				if (diffitem.isDeleted()) {
//					ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey() + " "
//						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
//						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
//					EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
//					if (fAtt != null && fAtt.isSelected(attrVal)) {
//						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
//						if (fAtt != null && fAtt.isSelected(attrVal2)) {
//							diffEntity = diffitem; // keep looking for one that is
//													// not deleted
//						}
//					}
//				} else {
//					if (diffitem.isNew()) {
//						ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey() + " "
//							+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
//							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
//						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
//						if (fAtt != null && fAtt.isSelected(attrVal)) {
//							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
//							if (fAtt != null && fAtt.isSelected(attrVal2)) {
//								diffEntity = diffitem;
//								break;
//							}
//						}
//					} else {
//						// must check to see if the prior item had a match too
//						ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs checking[" + i + "]: current " + diffitem.getKey() + " "
//							+ attrCode + ":" + PokUtils.getAttributeFlagValue(curritem, attrCode) + " " + attrCode2 + ":"
//							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
//						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode);
//						if (fAtt != null && fAtt.isSelected(attrVal)) {
//							fAtt = (EANFlagAttribute) curritem.getAttribute(attrCode2);
//							if (fAtt != null && fAtt.isSelected(attrVal2)) {
//								diffEntity = diffitem;
//								break;
//							}
//						}
//						ABRUtil.append(debugSb,"XMLTMFAVAILElem.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey() + " "
//							+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
//							+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
//						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
//						if (fAtt != null && fAtt.isSelected(attrVal)) {
//							fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
//							if (fAtt != null && fAtt.isSelected(attrVal2)) {
//								diffEntity = diffitem;
//								// break; see if there is another that is current
//							}
//						}
//					}
//				}
//			}
//		}
//		
//
//		return diffEntity;
//	}

	/***************************************************************************
	 * return Boolean check If there isn  an AVAIL where AVAILTYPE="Planned
	 * Availability" (146)
	 * 
	 * @param table
	 *            Hashtable that contain all the entities.
	 * @param availtype
	 *            AVAIL.AVAILTYPE
	 * @param debugSb
	 *            StringBuffer logo output.
	 * 
	 * If there isn  an AVAIL where AVAILTYPE="Planned Availability" (146) then
	 * from TMF attributes Note: Condition1 is there isn't an Planned Avail.
	 * Bing new added 6/27/2013  BH FS ABR XML System Feed Mapping 20130614.doc
	 * 1 If MODEL.ANNDATE <= 2010-03-01  AND there is no Availability (AVAIL) where Availability Type (AVAILTYPE) = Planned Availability (146), then skip to step 3.
       2 The aggregated (UNION) of the countries found for Availability (AVAIL) Country List (COUNTRYLIST) for all Availability Type (AVAILTYPE) that matches the earlier STATUS filtering criteria and the UNION of any Catalog Overrides (BHCATLGOR) Country List (COUNTRYLIST)..

	 * 
	 * 
	 */
	private String getCtryAVAL(Hashtable table, DiffEntity parentItem, boolean T1, Vector allVct, StringBuffer debugSb) {
		String result = null;
		String temp = null;
		DiffEntity modeldiff = null;
		Vector modelVct = (Vector) table.get("MODEL");
		if (modelVct != null) {		
		    modeldiff = (DiffEntity) modelVct.elementAt(0);
		}
		if (isOldData(table, modeldiff,  allVct,  debugSb, T1)){
			ABRUtil.append(debugSb,"getCtryAVAL isOldData return null, skip to step 3.");
			return null;
		}
		
		ABRUtil.append(debugSb,"getCtryAVAL looking for AVAILTYPE: 146,149 and 151 " + (T1?"T1":"T2") + " in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);				
		if (allVct != null) {	
			if (T1){
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem prioritem = diffitem.getPriorEntityItem();
					if (!diffitem.isNew()) {
						ABRUtil.append(debugSb,"getCtryAVAL.getAvails checking[" + i + "]:Delete or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
						if (fAtt != null && (fAtt.isSelected("146")||fAtt.isSelected("149")||fAtt.isSelected("151"))) {
							if (fAtt.isSelected("146")){
								temp = "146";
							}else if (fAtt.isSelected("149")){
								temp = "149";
							}else{
								temp = "151";
							}
							if (result == null){
                                result = temp;
							}else {
								if (temp.compareTo(result)<0){
									result = temp;
								}
								
							}
						
						}
					}
				}
			}else{
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem curritem = diffitem.getCurrentEntityItem();
					if (!diffitem.isDeleted()) {
						ABRUtil.append(debugSb,"getCtryAVAL.getAvails checking[" + i + "]:New or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
						if (fAtt != null && (fAtt.isSelected("146")||fAtt.isSelected("149")||fAtt.isSelected("151"))) {
							if (fAtt.isSelected("146")){
								temp = "146";
							}else if (fAtt.isSelected("149")){
								temp = "149";
							}else{
								temp = "151";
							}
							if (result == null){
                                result = temp;
							}else {
								if (temp.compareTo(result)<0){
									result = temp;
								}
								
							}
						
						}
					}
				}
			}						
		}
		//CQ BHALM109267 SWPRODSTRUCT may not have an AVAIL of type planned Availablity if it is older than 20100301
		//start
//		if (parentItem.getEntityType().equals("SWPRODSTRUCT")){
//			ABRUtil.append(debugSb,"There must has a planed AVAIL for SWPRODSTRUCT, otherwise there is no xml report!" + NEWLINE);
//			return false;
//		}
		//end
		return result;
	}
	  /********************
	 *  return Boolean check If there isn't an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE less than "20100301"   
	 *  @param table  
	 *         Hashtable that contain all the entities.
	 *  @param availtype 
	 *         AVAIL.AVAILTYPE 
	 *  @param debugSb
	 *         StringBuffer logo output.
	 *         
	 *  If there isn't an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE <  0100301 then
	 *  from MODEL attributes
	 *  Note:
	 *  Condition1 is there isn't an Planned Avail.  
	 *  Condition2 is Model.ANNDATE <  0100301 - removed based on BH FS ABR XML System Feed Mapping 20120612.doc
	 *  Bing new added 6/28/2013
	 **/
	private boolean isOldData(Hashtable table, DiffEntity parentItem, Vector tmfAvailVct, StringBuffer debugSb, boolean T1) {
		boolean condition1 = false;
		boolean condition2 = false;
		String ANNDATE_Fix = "2010-03-01";		
		if (T1){
			if (tmfAvailVct != null) {
				// find those of specified type
				for (int i = 0; i < tmfAvailVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) tmfAvailVct.elementAt(i);
					EntityItem prioritem = diffitem.getPriorEntityItem();
					//ABRUtil.append(debugSb,"debug4 "+ T1 + "difftiem is: " +( diffitem.isNew()?" is new":" is not new")+ NEWLINE);
					if (!diffitem.isNew()) {
						ABRUtil.append(debugSb,"T1 isOldData() getAvails checking[" + i + "]:New or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
						if (fAtt != null && fAtt.isSelected("146")) {
							condition1 = true;
							break;
						}
					}
				}
			}
//			//BH FS ABR XML System Feed Mapping 20130426.doc add For GA offerings, normally an Availability (AVAIL) of type Planned Availability is required; however, 
			//if it is is Old Data (i.e. MODEL.ANNDATE <20100301), then it will be considered World Wide (i.e. all of the Countries in COUNTRYLIST). 
			if (parentItem != null){
				EntityItem priorItem = parentItem.getPriorEntityItem();
				if (priorItem != null) {
					String anndate = PokUtils.getAttributeValue(priorItem, "ANNDATE", ", ", CHEAT, false);
					condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
					ABRUtil.append(debugSb,"T1 isOldData() get model ANNDATE" + priorItem.getKey() + " ANNDATE: " + anndate
						+ NEWLINE);
				}	
			}		
		}else {
			if (tmfAvailVct != null) {
			// find those of specified type
				for (int i = 0; i < tmfAvailVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) tmfAvailVct.elementAt(i);
					EntityItem curritem = diffitem.getCurrentEntityItem();
					if (!diffitem.isDeleted()) {
						ABRUtil.append(debugSb,"T2 isOldData() getAvails checking[" + i + "]:New or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
						if (fAtt != null && fAtt.isSelected("146")) {
							condition1 = true;
							break;
						}
					}
			    }
			}
	        //BH FS ABR XML System Feed Mapping 20130426.doc add For GA offerings, normally an Availability (AVAIL) of type Planned Availability is required; however, if it is is Old Data (i.e. MODEL.ANNDATE <20100301), then it will be considered World Wide (i.e. all of the Countries in COUNTRYLIST). 
			if (parentItem != null){
				EntityItem curritem = parentItem.getCurrentEntityItem();
				if (curritem != null) {
					String anndate = PokUtils.getAttributeValue(curritem, "ANNDATE", ", ", CHEAT, false);
					condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
					ABRUtil.append(debugSb,"T2 isOldData() model ANNDATE" + curritem.getKey() + " ANNDATE: " + anndate
						+ NEWLINE);
					}
			}
		}
		return (!condition1) && condition2;				
	}
	/***************************************************************************
	 * return Boolean check If there isn  an AVAIL where AVAILTYPE="Planned
	 * Availability" (146)
	 * 
	 * @param table
	 *            Hashtable that contain all the entities.
	 * @param availtype
	 *            AVAIL.AVAILTYPE
	 * @param debugSb
	 *            StringBuffer logo output.
	 * 
	 * If there isn  an AVAIL where AVAILTYPE="Planned Availability" (146) then
	 * from MODEL attributes Note: Condition1 is there isn't an Planned Avail.
	 */
	private boolean isDerivefromMODEL(Hashtable table, Vector allVct, boolean T1, StringBuffer debugSb) {
		boolean condition1 = false;		
		ABRUtil.append(debugSb,"isDerivefromMODEL.getAvails looking for AVAILTYPE: 146 " + (T1?"T1":"T2") + " in AVAIL" + " allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct != null) {
			if (T1){
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem prioritem = diffitem.getPriorEntityItem();
					if (!diffitem.isNew()) {
						ABRUtil.append(debugSb,"DerivefromModel.getAvails checking[" + i + "]:Delete or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
						if (fAtt != null && fAtt.isSelected("146")) {
							condition1 = true;
							break;
						}
					}
				}						
			}else{
//				 find those of specified type
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					EntityItem curritem = diffitem.getCurrentEntityItem();
					if (!diffitem.isDeleted()) {
						ABRUtil.append(debugSb,"DerivefromModel.getAvails checking[" + i + "]:New or Update"
							+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")
							+ NEWLINE);
						EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
						if (fAtt != null && fAtt.isSelected("146")) {
							condition1 = true;
							break;
						}
					}
				}				
			}
		}
		return condition1;
	}
	/**
	 * availType: MODELAVAIL
	 *            OOFAVAIL
	 *            SWPRODSTRUCTAVAIL
	 *            
	 *                      
	 * 
	 * @param table
	 * @param isfromLSEOModel
	 * @param debugSb
	 * @return
	 */
    private Vector getAvailOfAvailType(Hashtable table,String availType,StringBuffer debugSb){
		
		Vector allVct = (Vector) table.get("AVAIL");
		//printTable(table, debugSb);
		Vector overrideVct = new Vector(1); 
		if (allVct != null) {
			for (int i = 0; i < allVct.size(); i++) {
				DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
				//ABRUtil.append(debugSb,"getAvailOfAvailType diffitem.toString():" + diffitem.toString() + NEWLINE);
				ABRUtil.append(debugSb,"getAvailOfAvailType looking for AVAIL type " + availType + " there are " + allVct.size() +" AVAILs, " +  diffitem.getKey() +NEWLINE);
				if (diffitem.isDeleted()){
					EntityItem priorItem = diffitem.getPriorEntityItem();
					if (priorItem.hasUpLinks()) {
						for (int ii = 0; ii < priorItem.getUpLinkCount(); ii++) {
							EntityItem entity = (EntityItem) priorItem.getUpLink(ii);
							//ABRUtil.append(debugSb,"getAvailOfAvailType priorItem.getUpLink type:" + entity.getEntityType() + NEWLINE);
							if (entity.getEntityType().equals(availType)) {
								overrideVct.add(diffitem);
								break;
							}
						}
					}
				} else{
					EntityItem currItem = diffitem.getCurrentEntityItem();
					if (currItem.hasUpLinks()) {
						for (int ii = 0; ii < currItem.getUpLinkCount(); ii++) {
							EntityItem entity = (EntityItem) currItem.getUpLink(ii);
							//ABRUtil.append(debugSb,"getAvailOfAvailType currItem.getUpLink type:" + entity.getEntityType() + NEWLINE);
							if (entity.getEntityType().equals(availType)) {
								overrideVct.add(diffitem);
								break;
							}
						}
					}					
				}
						
			}
		}
		return overrideVct;		
	}
	
	/***************************************************************************
	 * CATAUDIENCE CBP Catalog - Business Partner Catalog - Business Partner
	 * CATAUDIENCE CIR Catalog - Indirect/Reseller Catalog - Indirect/Reseller
	 * CATAUDIENCE LE LE Direct LE Direct CATAUDIENCE None None None CATAUDIENCE
	 * Shop Public Public
	 * 
	 * AUDIEN 100 SDI Channel AUDIEN 10046 Catalog - Business Partner Catalog -
	 * Business Partner AUDIEN 10048 Catalog - Indirect/Reseller Catalog -
	 * Indirect/Reseller AUDIEN 10054 Public Public AUDIEN 10055 None None
	 * AUDIEN 10062 LE Direct LE Direct AUDIEN 10067 DAC/MAX DAC/MAX get entity
	 * with specified values - should only be one could be two if one was
	 * deleted and one was added, but the added one will override and be an
	 * 'update'
	 */
	private DiffEntity getCatlgor(Hashtable table, String attrVal2, StringBuffer debugSb) {
		// remove the check of the CATLGOR Audience 2012-08-24
		DiffEntity diffEntity = null;
	    //RCQ00222829 change  CATLGOR to BHCATLGOR base on the doc BH FS ABR XML System Feed Mapping 20130214.doc		
		Vector allVct = (Vector) table.get("BHCATLGOR");
//		String attrCode2 = "OFFCOUNTRY"; // need flag code
		String attrCode2 = BHCOUNTRYLIST; // need flag code
		ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor looking for " + attrCode2 + ":" + attrVal2 + " in CATLGOR allVct.size:"
			+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}

		// find those of specified type
		diffloop: for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			//add the check of the status of BHCATLGOR
			String BHStatus  = PokUtils.getAttributeFlagValue(curritem,"STATUS");
			if(!STATUS_FINAL.equals(BHStatus)){
				continue;
			}
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking[" + i + "]: deleted " + diffitem.getKey() + " " + attrCode2 + ":"
					+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
				EANFlagAttribute fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
				if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
					diffEntity = diffitem; // keep looking for one
					// that is not deleted
					break;
				}
				
			} else {
				if (diffitem.isNew()) {
					ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking[" + i + "]: new " + diffitem.getKey() + " " + attrCode2 + ":"
							+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt2 = (EANFlagAttribute) curritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break diffloop;
					}
				} else {
					// must check to see if the prior item had a match too
					ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking[" + i + "]: current " + diffitem.getKey() + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(curritem, attrCode2) + NEWLINE);
					EANFlagAttribute fAtt2 = (EANFlagAttribute) curritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break diffloop;
					}
					
					fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break; // see if there is another that is
								// current
					}
				}
			}
		}

		return diffEntity;
	}

	/***************************************************************************
	 * one for every AVAIL.COUNTRYLIST where availtype='planned availbility(146)
	 * include the avails (delete,new and update)
	 * 
	 */
	private static class CtryAudRecord extends CtryRecord {
		
		
		public String country;
		private DiffEntity availDiff;


		CtryAudRecord(DiffEntity diffitem, String ctry) {
			super(null);
			country = ctry;
			availDiff = diffitem;
		}

		void setUpdateAvail(DiffEntity avl) {
			availDiff = avl;// allow replacement
			setAction(UPDATE_ACTIVITY);
		}
		
		
		/***********************************************************************
		 * a) ID 42.00 <ANNDATE> 1. ANNOUNCEMENT.ANNDATE 2. PRODSTRUCT.ANNDATE
		 * 3. Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR MODEL.ANNDATE if
		 * SWFEATURE). b) ID 42.10 <ANNNUMBER> 1. ANNOUNCEMENT.ANNNUMBR from the
		 * ANNOUNCEMENT used for the preceding token <ANNDATE> 2. empty
		 * 
		 * c) ID 43.00 <FIRSTORDER> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE
		 * first Order2. ANNOUNCEMENT.ANNDATE 3. PRODSTRUCT.GENAVAILDATE 4.
		 * MODEL.GENAVAILDATE d) ID 44.00 <PLANNEDAVAILABILITY> 1.
		 * AVAIL.EFFECTIVEDATE where AVAILTYPE planned Availability2.
		 * PRODSTRUCT.GENAVAILDATE 3. Max{FEATURE.GENAVAILDATE;
		 * MODEL.GENAVAILDATE} e) ID 45.00 <PUBFROM> 1. ANNOUNCEMENT.ANNDATE 2.
		 * PRODSTRUCT.ANNDATE 3. Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR
		 * MODEL.ANNDATE if SWFEATURE).
		 * 
		 * f) ID 46.00 <PUBTO> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE =last
		 * Order 2. PRODSTRUCT. WTHDRWEFFCTVDATE 3. Min(MODEL.
		 * WTHDRWEFFCTVDATE; SWFEATURE. WITHDRAWDATEEFF_T; FEATURE.
		 * WITHDRAWDATEEFF_T} g) ID 47.00 <WDANNDATE> 1. ANNOUNCEMENT.ANNDATE
		 * where AVAIL.AVAILTYPE = last Order2. PRODSTRUCT. WITHDRAWDATE 3.
		 * Min{MODEL. WITHDRAWDATE; FEATURE. WITHDRAWANNDATE_T; SWFEATURE.
		 * WITHDRAWANNDATE_T} h) ID 48.00 <LASTORDER> Same as <PUBTO> i) ID
		 * 49.00 <EOSANNDATE> Empty
		 * 
		 * j) ID 50.00 <ENDOFSERVICEDATE> Empty
		 * 
		 * 		vct[0] = prevFeaVct;
		 *      vct[1] = currFeaVct;
		 */
		void setAllFields(DiffEntity[] diffPlanavail, Vector[] feaCtry, DiffEntity catlgorDiff, DiffEntity parentItem, DiffEntity foAvailDiff, DiffEntity loAvailDiff,
			DiffEntity endAvailDiff, DiffEntity endMktAvailDiff, DiffEntity loModelAvailDiff, DiffEntity endModelAvailDiff, DiffEntity endModelMktAvailDiff, DiffEntity plModelAvailDiff, Hashtable table,  boolean isExistfinal, boolean compatModel, StringBuffer debugSb) {
			
			
//			 set STATUS
			availStatus = "0020";
            rfravailStatus = "0040";
			
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for country is belong to availDiff " + (availDiff == null ? "null" : availDiff.getKey()) + ". OFFAVAIL Planned AVAIL is " +  (diffPlanavail[1] == null ? "null" : diffPlanavail[1].getKey())
				+ NEWLINE);
			// ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where
			// AVAIL.AVAILTYPE = planned Availability(146).
			// If prodstruct has no Planed AVAIL, then  pass unll into availDiff. anndate gose to prodstruct.anndate. 
			String[] anndates = deriveAnnDate(false, diffPlanavail[1], parentItem, plModelAvailDiff, feaCtry, debugSb);
//			anndate = anndates[0];
//			rfranndate = anndates[1];			 
			String[] anndatesT1 = deriveAnnDate(true, diffPlanavail[0], parentItem, plModelAvailDiff, feaCtry, debugSb);
//			anndateT1 = anndatesT1[0];
//			rfranndateT1 = anndatesT1[1];						 


			// ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where
			// AVAIL.AVAILTYPE = planned Availability(146).
			String[] annnumbers = deriveAnnNumber(false, diffPlanavail[1], debugSb);
//			annnumber = annnumbers[0];
//			rfrannnumber = annnumbers[1];
			String[] annnumbersT1 = deriveAnnNumber(true, diffPlanavail[0], debugSb);
//			annnumberT1 = annnumbersT1[0];
//			rfrannnumberT1 = annnumbersT1[1];
			
			

			// FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143
			// or null.
			
			// update firstorder , if planed avail from MODEL, then ignore it. go to next priority.
			String[] firstorders = deriveFIRSTORDER(false, diffPlanavail[1], parentItem, foAvailDiff,  plModelAvailDiff, feaCtry, debugSb);
//			firstorder = firstorders[0];
//			rfrfirstorder = firstorders[1];			                         
			String[] firstordersT1 = deriveFIRSTORDER(true, diffPlanavail[0], parentItem, foAvailDiff,  plModelAvailDiff, feaCtry, debugSb);
//			firstorderT1 = firstordersT1[0];
//			rfrfirstorderT1 = firstordersT1[1];
			
			
			// LASTORDER is equal to PUBTO.
			// PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE
			// = "Planned Availability" (146)
			String[] plannedavailabilitys = derivePLANNEDAVAILABILITY(false, diffPlanavail[1], parentItem, plModelAvailDiff, feaCtry, debugSb);
//			plannedavailability = plannedavailabilitys[0];
//			rfrplannedavailability = plannedavailabilitys[1];
			String[] plannedavailabilitysT1 = derivePLANNEDAVAILABILITY(true, diffPlanavail[0], parentItem, plModelAvailDiff, feaCtry, debugSb);
//			plannedavailabilityT1 = plannedavailabilitysT1[0];
//			rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
			

			// set PUBFROM
			//udpate derivePubFrom  if planedavail from Model , then gose to next priority. 
			String[] pubfroms = derivePubFrom(false, diffPlanavail[1], catlgorDiff, foAvailDiff, parentItem, plModelAvailDiff, feaCtry, debugSb);
//			pubfrom = pubfroms[0];
//			rfrpubfrom = pubfroms[1];
		    String[] pubfromsT1 = derivePubFrom(true, diffPlanavail[0], catlgorDiff, foAvailDiff, parentItem, plModelAvailDiff, feaCtry, debugSb);
//		    pubfromT1 = pubfromsT1[0];
//		    rfrpubfromT1 = pubfromsT1[1];

			// set PUBTO
			String[] pubtos = derivePubTo(false, catlgorDiff, parentItem, loAvailDiff, plModelAvailDiff, loModelAvailDiff, feaCtry, debugSb);
//			pubto = pubtos[0];
//		    rfrpubto = pubtos[1];
			String[] pubtosT1 = derivePubTo(true, catlgorDiff, parentItem, loAvailDiff, plModelAvailDiff, loModelAvailDiff, feaCtry, debugSb);
//			pubtoT1 = pubtosT1[0];
//		    rfrpubtoT1 = pubtosT1[1];
			

			// set WDANNDATE
			String[] wdanndates = deriveWDANNDATE(false, parentItem, loAvailDiff, endMktAvailDiff, endModelMktAvailDiff, plModelAvailDiff, feaCtry, debugSb);
//			wdanndate =wdanndates[0];
//			rfrwdanndate = wdanndates[1];			
			String[] wdanndatesT1 = deriveWDANNDATE(true, parentItem, loAvailDiff, endMktAvailDiff, endModelMktAvailDiff, plModelAvailDiff, feaCtry, debugSb);
//			wdanndateT1 =wdanndatesT1[0];
//			rfrwdanndateT1 = wdanndatesT1[1];
//				
			// set ENDOFMARKETANNNUMBER Story 1865979 Withdrawal RFA Number generation
            String[] eomannnums = deriveENDOFMARKETANNNUMBER(parentItem, loAvailDiff, endMktAvailDiff, endModelMktAvailDiff, false, debugSb);
            String[] eomannnumsT1 = deriveENDOFMARKETANNNUMBER(parentItem, loAvailDiff, endMktAvailDiff, endModelMktAvailDiff, true, debugSb);

			// set LASTORDER
			String[] lastorders = deriveLastOrder(false, catlgorDiff, parentItem, loAvailDiff, loModelAvailDiff, plModelAvailDiff, feaCtry, debugSb);
//			lastorder = lastorders[0];
//			rfrlastorder = lastorders[1];
			String[] lastordersT1 = deriveLastOrder(true, catlgorDiff, parentItem, loAvailDiff, loModelAvailDiff, plModelAvailDiff, feaCtry, debugSb);
//			lastorderT1 = lastordersT1[0];
//			rfrlastorderT1 = lastordersT1[1];

			
			//setENDOFSERVICEDATE
			String[] endofservices = deriveENDOFSERVICEDATE(false, parentItem, endAvailDiff, endModelAvailDiff, debugSb);
//			endofservice = endofservices[0];
//			rfrendofservice = endofservices[1];			              
			String[] endofservicesT1 = deriveENDOFSERVICEDATE(true, parentItem, endAvailDiff, endModelAvailDiff, debugSb);
//			endofserviceT1 = endofservicesT1[0];
//			rfrendofserviceT1 = endofservicesT1[1];

			//setEOSANNDATE
			String[] eosanndates = deriveEOSANNDATE(false, parentItem, endAvailDiff, endModelAvailDiff, debugSb);
//			eosanndate= eosanndates[0];
//			rfreosanndate = eosanndates[1];			
			String[] eosanndatesT1 = deriveEOSANNDATE(true, parentItem, endAvailDiff, endModelAvailDiff, debugSb);
//			eosanndateT1 = eosanndatesT1[0];
//			rfreosanndateT1 = eosanndatesT1[1];
			
			//set ENDOFSERVICEANNNUMBER Story 1865979 Withdrawal RFA Number generation
            String[] eosannnums = deriveENDOFSERVICEANNNUMBER(parentItem, endAvailDiff, endModelAvailDiff, false, debugSb);
            String[] eosannnumsT1 = deriveENDOFSERVICEANNNUMBER(parentItem, endAvailDiff, endModelAvailDiff, true, debugSb);
            
			handleResults(anndates, anndatesT1, annnumbers, annnumbersT1,firstorders, firstordersT1,
			              plannedavailabilitys, plannedavailabilitysT1, pubfroms,pubfromsT1, pubtos,  pubtosT1,
			              wdanndates,  wdanndatesT1, eomannnums, eomannnumsT1, lastorders,  lastordersT1,  endofservices,  endofservicesT1,
			               eosanndates,  eosanndatesT1, eosannnums, eosannnumsT1, country,  isExistfinal,  compatModel,  debugSb);
			

			
			//comment out this part, it may not correct. becuase availDiff in SLEORGGRP.hasChanges() may not planed avail, it 's the avalil that the country come from. 
//			boolean SLEORGGRPChaned = false;
//			add SLEORGGRP
//			if("SWPRODSTRUCT".equals(parentItem.getEntityType())){
//				SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, parentItem.getCurrentEntityItem(), availDiff, "D:SWPRODSTRUCTAVAIL:D:AVAIL:D:AVAILSLEORGA:D", country, debugSb);
//			}
//			else{
//				SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, parentItem.getCurrentEntityItem(), availDiff, "D:OOFAVAIL:D:AVAIL:D:AVAILSLEORGA:D", country, debugSb);
//			}
//			SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, parentItem.getCurrentEntityItem(), availDiff, null, country, debugSb);
//			if (SLEORGGRPChaned) {
//				if(existfnialT2){
//					setAction(UPDATE_ACTIVITY);
//					setrfrAction(UPDATE_ACTIVITY);
//				}else{
//					setrfrAction(UPDATE_ACTIVITY);
//				}
//			}
		}

	
		
		/***********************************************************************
		 old: * ID 46.00 <PUBTO> 0. PRODSTRUCTCATLGOR-d: CATLGOR.PUBTO or
		 * SWPRODSTRCATLGOR-d. CATLGOR.PUBTO 1. AVAIL.EFFECTIVEDATE where
		 * AVAILTYPE = last Order2. PRODSTRUCT. WTHDRWEFFCTVDATE 3.
		 * Min(MODEL. WTHDRWEFFCTVDATE; SWFEATURE. WITHDRAWDATEEFF_T; FEATURE.
		 * WITHDRAWDATEEFF_T}
		 
		 New: BH FS XML Feed Mapping20121001.doc
		  f)	ID 46.00 <PUBTO>
			1.	PRODSTRUCTCATLGOR-d: CATLGOR.PUBTO or
			SWPRODSTRCATLGOR-d. CATLGOR.PUBTO
			By country found in CATLGOR. OFFCOUNTRY
			2.	OOFAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = last Order(149).
			By country found in AVAIL.COUNTRYLIST
			3.	PRODSTRUCT. WTHDRWEFFCTVDATE
			The applicable countries are the intersection of the MODEL AVAIL.COUNTRYLIST where AVAILTYPE = last Order(149). and the FEATURE.COUNTRYLIST.
			4.	Min of the following three that are applicable
			1)	First of the following two
			a)	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = last Order(149)
			By the AVAIL.COUNTRYLIST
			b)	MODEL. WTHDRWEFFCTVDATE;
			By AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability(146)
			2)	SWFEATURE. WITHDRAWDATEEFF_T; 
			Applies to all Countries that get this far
			3)	FEATURE. WITHDRAWDATEEFF_T
			By FEATURE.COUNTRYLIST
			5.	empty

		 */
		private String[] derivePubTo(boolean findT1, DiffEntity catlgorDiff, DiffEntity parentDiff, DiffEntity loAvailDiff, DiffEntity plModelAvailDiff, DiffEntity loModelAvailDiff,
			Vector[] feaCtry, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.derivePubTo " + " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
				+ " findT1:" + findT1 + NEWLINE);

			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String sReturn[] = new String[2];
			//TODO
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the catlgorDiff PUBTO date
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate, country, "PUBTO", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the loAvailDiff EFFECTIVEDATE date
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//				3.	PRODSTRUCT. WTHDRWEFFCTVDATE
//				The applicable countries are the intersection of the MODEL AVAIL.COUNTRYLIST where AVAILTYPE = last Order (149). and the FEATURE.COUNTRYLIST.
//             feaCtry[0] is priorCountrylist.
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, loModelAvailDiff, feaCtry, thedate, rfrthedate,country, "WTHDRWEFFCTVDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}	
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//				4.	Min of the following four that are applicable
//				1)	If PRODSTUCT.ORDERCODE =Initial(5957), then 
//				First of the following two
//				a)	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = Last Order (149)
//				By the AVAIL.COUNTRYLIST
//				b)	MODEL. WTHDRWEFFCTVDATE;
//				By AVAIL.COUNTRYLIST where AVAILTYPE = Planned Availability(146)
//				2)	PRODSTRUCT. WTHDRWEFFCTVDATE
//				3)	SWFEATURE. WITHDRAWDATEEFF_T 
//				Applies to all Countries that get this far
//				4)	FEATURE. WITHDRAWDATEEFF_T
//				By FEATURE.COUNTRYLIST
				temps = getModelFeatureSpDate(findT1, parentDiff, loModelAvailDiff, plModelAvailDiff, thedate, rfrthedate, debugSb);
				//temps = getSpecialDate(findT1,parentDiff, plModelAvailDiff, loModelAvailDiff,thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/***********************************************************************
		 * 0. PRODSTRUCTCATLGOR-d: CATLGOR.PUBFROM or SWPRODSTRCATLGOR-d:
		 * CATLGOR.PUBFROM 1. ANNOUNCEMENT.ANNDATE 2. PRODSTRUCT.ANNDATE 3.
		 * Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR MODEL.ANNDATE if
		 * SWFEATURE).
		 // update as following sequence
		  e)	ID 45.00 <PUBFROM>
			1.	PRODSTRUCTCATLGOR-d: CATLGOR.PUBFROM or
			SWPRODSTRCATLGOR-d: CATLGOR.PUBFROM
			2.	OOFAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = first Orderor
			SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = first Order
			3.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAILTYPE = planned Availability(146). 
			See step 4 for the AVAIL path.
			4.	OOFAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = planned Availabilityor
			SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = plaanned Availability
			5.	PRODSTRUCT.ANNDATE
			6.	Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} OR MODEL.ANNDATE if SWFEATURE).
		 */
		private String[] derivePubFrom(boolean findT1, DiffEntity availDiff, DiffEntity catlgorDiff, DiffEntity foAvailDiff, DiffEntity parentDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], StringBuffer debugSb) {
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.derivePubFrom " + " catlgorDiff: "
					+ (catlgorDiff == null ? "null" : catlgorDiff.getKey()) + " findT1:" + findT1 + NEWLINE);
			
			String rfrthedate = CHEAT;
			String sReturn[] = new String[2];
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1 find current derivation
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate, country, "PUBFROM", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2 20120117  add
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL
				// where AVAIL.AVAILTYPE = "Planned Availability" (146).
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//4 20120117  add
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//5.	IF PRODSTRUCT, PRODSTRUCT.ANNDATE 
				//The applicable countries are the intersection of the MODEL’s AVAIL.COUNTRYLIST and the FEATURE.COUNTRYLIST.
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate,country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 6. IF PRODSTRUCT, Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE}
				//The applicable countries are the intersection of the MODEL’s AVAIL.COUNTRYLIST and the FEATURE.COUNTRYLIST.
				temps = AvailUtil.getModelFeatureAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate, country, "ANNDATE","FIRSTANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// this is new added BH FS ABR XML System Feed Mapping 20121001.doc page 98:
//				7.	IF SWPRODSTUCT IF SWPRODSTRUCT, MODEL.GENAVAILDATE
//				The applicable countries is the MODEL  AVAIL.COUNTRYLIST
				temps = getSWprodCountryDate(findT1,parentDiff,plModelAvailDiff, thedate,rfrthedate,country, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
				
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//8.IF SWPRODSTRUCT THEN MODEL.ANNDATE - If the MODEL does not have any AVAILs where AVAILTYPE =Planned Availability, then it is World Wide (i.e. all countries) 
				temps = AvailUtil.getSwprodModelAnnDate(findT1, parentDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/**
		 * @param parentDiff
		 * @param plModelAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		private String[] getSWprodCountryDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity plModelAvailDiff, String thedate, String rfrthedate, String country, 
				StringBuffer debugSb) {
			String[] sReturn = new String[2];
			
			EntityItem plModelitem = AvailUtil.getEntityItem(findT1, plModelAvailDiff);
			
			if (plModelitem != null ) {
				//EntityItem plModelitem = plModelAvailDiff.getPriorEntityItem();
				EANFlagAttribute fAtt = (EANFlagAttribute) plModelitem.getAttribute("COUNTRYLIST");
				if (fAtt != null && fAtt.isSelected(country)) {
					EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
					if (item != null) {
						if (parentDiff.getEntityType().equals("SWPRODSTRUCT")){
							//EntityItem item = parentDiff.getPriorEntityItem();							
							if (item.hasDownLinks()) {
								for (int i = 0; i < item.getDownLinkCount(); i++) {
									EntityItem entity = (EntityItem) item.getDownLink(i);
									if (entity.getEntityType().equals("MODEL")) {
										String entityDate = PokUtils.getAttributeValue(entity, "GENAVAILDATE", ", ", CHEAT, false);
										String entityStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
										
										ABRUtil.append(debugSb,"XMLTMFAVAILElem.derivePubFrom getting GENAVAILDATE from " + entity.getKey()
											+ " GENAVAILDATE is:" + thedate + NEWLINE);
										if (entityStatus != null && entityStatus.equals("0020") && CHEAT.equals(thedate)){
											thedate = entityDate;
										} else if (entityStatus != null && entityStatus.equals("0040") && CHEAT.equals(rfrthedate)){
											rfrthedate = entityDate;
										}
										break;
									}
								}
							}
						}
					}
				}
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		

		

		/***********************************************************************
		 * <ANNNUMBER> 1. ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL
		 * where AVAIL.AVAILTYPE = planned Availability (146). 2. Empty (aka
		 * Null)
		 * 
		 */
		private String[] deriveAnnNumber(boolean findT1, DiffEntity availDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String anns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveAnnNumber availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
			 + "findT1:" + findT1 + NEWLINE);	
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			anns[0] = thedate;
			anns[1] = rfrthedate;
			return anns;
		}
		


		/***********************************************************************
		 * <ANNDATE> 1. ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where
		 * AVAIL.AVAILTYPE = planned Availability (146). 2. Empty (aka Null)
		 * 
		 */
		private String[] deriveAnnDate(boolean findT1, DiffEntity availDiff, DiffEntity parentDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String anns[] = new String[2];						
			String temps[] = new String[2];
			
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveAnnDate availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				+ " plModelAvailDiff: " + (plModelAvailDiff == null ? "null" : plModelAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);	
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) && CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate,country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) && CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getModelFeatureAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate, country, "ANNDATE","FIRSTANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) && CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getSwprodModelAnnDate(findT1, parentDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			anns[0] = thedate;
			anns[1] = rfrthedate;
			return anns;
		}
		

		

		/***********************************************************************
		 * <FIRSTORDER> 1. AVAIL.EFFECTIVEDATE where AVAILTYPE first Order 2.
		 * ANNOUNCEMENT.ANNDATE 3. PRODSTRUCT.GENAVAILDATE 4. MODEL.GENAVAILDATE
		
		// UPDATE as following sequence according to doc 20120117.doc
		c)	ID 43.00 <FIRSTORDER>
		1.	OOFAVAIL-d: or SWPRODSTRUCTAVAIL-d: then AVAIL.EFFECTIVEDATE for the AVAIL where AVAILTYPE =first Order  
		2.	OOFAVAIL-d: or SWPRODSTRUCTAVAIL-d: then AVAILANNA-d: ANNOUNCEMENT.ANNDATE for the AVAIL where AVAILTYPE = planned Availability (146). 
		3.	OOFAVAIL-d: or SWPRODSTRUCTAVAIL-d: then AVAIL.EFFECTIVEDATE for the AVAIL where AVAILTYPE = planned Availability  
		4.	PRODSTRUCT.ANNDATE
		5.	Max{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} or MODEL.ANNDATE
		6.	empty
        */
		private String[] deriveFIRSTORDER(boolean findT1, DiffEntity availDiff, DiffEntity parentDiff, DiffEntity foAvailDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String fos[] = new String[2];	
			
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveFIRSTORDER availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				+ " foAvailDiff: " + (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);	
			
			//TODO
			//TODO
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1.get the first order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2.get the plan avail effective announcement date
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3.get the plan avail efftive date
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//4.get the Prodstruct ann date
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate,country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//5.get the 
				temps = AvailUtil.getModelFeatureAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate, country, "ANNDATE","FIRSTANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//6.get the swprodstruct --> model ann date
				temps = AvailUtil.getSwprodModelAnnDate(findT1, parentDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			fos[0] = thedate;
			fos[1] = rfrthedate;
			return fos;
		}


		
		
		/***********************************************************************
		 * <PLANNEDAVAILABILITY> 
		 * 1. AVAIL.EFFECTIVEDATE where AVAILTYPE planned Availability 
		 * 2. PRODSTRUCT.GENAVAILDATE 
		 * 3. Max{FEATURE.GENAVAILDATE; MODEL.GENAVAILDATE}
		 */
		private String[] derivePLANNEDAVAILABILITY(boolean findT1, DiffEntity availDiff, DiffEntity parentDiff, DiffEntity plModelAvailDiff, Vector feaCtry[], StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String plans[] = new String[2];
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.derivePLANNEDAVAILABILITY availDiff: "
				+ (availDiff == null ? "null" : availDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the plan avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the plan avail effective date
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate,country, "GENAVAILDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the 
				temps = AvailUtil.getModelFeatureAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate, country, "GENAVAILDATE","GENAVAILDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the 
				temps = getSwprodModelDate(findT1, parentDiff, plModelAvailDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the 
				temps = AvailUtil.getSwprodModelAnnDate(findT1, parentDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			plans[0] = thedate;
			plans[1] = rfrthedate;
			return plans;
		}	

		/**
		 * @param parentDiff
		 * @param plModelAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		public String[] getSwprodModelDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity plModelAvailDiff, String thedate, String rfrthedate,
				StringBuffer debugSb) {
			String[] sReturn = new String[2];
			EntityItem plModelitem = AvailUtil.getEntityItem(findT1, plModelAvailDiff);
			
			if (plModelitem != null) {				
				EANFlagAttribute fAtt = (EANFlagAttribute) plModelitem.getAttribute("COUNTRYLIST");
				if (fAtt != null && fAtt.isSelected(country)) {
					EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
					if (item != null) {
						//EntityItem item = parentDiff.getPriorEntityItem();
						if (item.getEntityType().equals("SWPRODSTRUCT")){
							if (item.hasDownLinks()) {
								for (int i = 0; i < item.getDownLinkCount(); i++) {
									EntityItem entity = (EntityItem) item.getDownLink(i);
									if (entity.getEntityType().equals("MODEL")) {
										String modelstatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
										
										String modelDate = PokUtils.getAttributeValue(entity, "GENAVAILDATE", ", ", CHEAT, false);
										ABRUtil.append(debugSb,"XMLTMFAVAILElem.derivePLANNEDAVAILABILITY getting GENAVAILDATE from "
											+ entity.getKey() + " GENAVAILDATE is:" + thedate + NEWLINE);
										//TODO
										if (modelstatus != null && modelstatus.equals("0020") && CHEAT.equals(thedate)){
											thedate = modelDate;
										} else if (modelstatus != null && modelstatus.equals("0040") && CHEAT.equals(rfrthedate)){
											rfrthedate = modelDate;
										}
										
										break;
									}
								}
							}
						}
					}
				}
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}


		/***********************************************************************
		 Old:* ID 47.00 <WDANNDATE> 1. ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE =
		 * last Order 2. PRODSTRUCT. WITHDRAWDATE 3. Min{MODEL. WITHDRAWDATE;
		 * FEATURE. WITHDRAWANNDATE_T; SWFEATURE. WITHDRAWANNDATE_T}
		 // update according to doc 20120117.doc
		 1.	ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing
		 New: logic according to doc 20131001.doc
		 1.	AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14).
		Since a PRODSTRUCT (SWPRODSTRUCT) can have multiple AVAILs each of which can be in a different ANNOUNCEMENT, this may vary by ANNOUNCEMENT which is applicable for a set of countries found in ANNOUNCEMENT.COUNTRYLIST.
		2.	PRODSTRUCT. WITHDRAWDATE
		The applicable countries are the intersection of the MODEL  AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability (146) and the FEATURE.COUNTRYLIST.
		3.	Min of the following three
		1)	First of the following two
		a)	The child MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14). ANNOUNCEMENT.COUNTRYLIST
		b)	MODEL. WITHDRAWDATE
		2)	SWFEATURE. WITHDRAWANNDATE_T
		Applies to all Countries that get this far
		3)	FEATURE. WITHDRAWANNDATE_T 
		By FEATURE.COUNTRYLIST
		4.	 empty.
		update at 20181105
		1.	AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = last Order AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14).
		Since a PRODSTRUCT (SWPRODSTRUCT) can have multiple AVAILs each of which can be in a different ANNOUNCEMENT, this may vary by ANNOUNCEMENT which is applicable for a set of countries found in ANNOUNCEMENT.COUNTRYLIST.
		2.	AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14).
		Since a PRODSTRUCT (SWPRODSTRUCT) can have multiple AVAILs each of which can be in a different ANNOUNCEMENT, this may vary by ANNOUNCEMENT which is applicable for a set of countries found in ANNOUNCEMENT.COUNTRYLIST.
		3.	PRODSTRUCT. WITHDRAWDATE
		The applicable countries are the intersection of the MODEL  AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability (146) and the FEATURE.COUNTRYLIST.
		4.	Min of the following three
		1)	First of the following two
		a)	The child MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14). ANNOUNCEMENT.COUNTRYLIST
		b)	MODEL. WITHDRAWDATE
		2)	SWFEATURE. WITHDRAWANNDATE_T
		Applies to all Countries that get this far
		3)	FEATURE. WITHDRAWANNDATE_T 
		By FEATURE.COUNTRYLIST
		5.	 empty.
		 */
		private String[] deriveWDANNDATE(boolean findT1, DiffEntity parentDiff, DiffEntity loAvailDiff, DiffEntity endMktAvailDiff, DiffEntity endModelMktAvailDiff, 
				DiffEntity plModelAvailDiff, Vector[] feaCtry, StringBuffer debugSb) {
			
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String wdDate[] = new String[2];
			
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE lastOrderAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the lastOrderAvailDiff effective announcement date
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the endMktAvailDiff effective announcement date
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endMktAvailDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2.	PRODSTRUCT. WITHDRAWDATE date
				//The applicable countries are the intersection of the MODEL  AVAIL.COUNTRYLIST where AVAILTYPE = planned Availability (146) and the FEATURE.COUNTRYLIST.
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, plModelAvailDiff, feaCtry, thedate, rfrthedate, country, "WITHDRAWDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//				3.	Min of the following four that are applicable
//				1)	If PRODSTUCT.ORDERCODE = initial (5957), then
//				First of the following two
//				a)	The child MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14). ANNOUNCEMENT.COUNTRYLIST
//				b)	MODEL. WITHDRAWDATE
//				2)	PRODSTRUCT. WITHDRAWDATE
//				3)	SWFEATURE. WITHDRAWANNDATE_T
//				Applies to all Countries that get this far
//				4)	FEATURE. WITHDRAWANNDATE_T 
//				By FEATURE.COUNTRYLIST
				temps = getModelFeatureSpecialDate(findT1, parentDiff,
						endModelMktAvailDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			wdDate[0] = thedate;
			wdDate[1] = rfrthedate;
			
			return wdDate;
		}
		/****************************
		 * <ENDOFMARKETANNNUMBER>
		 1.	ANNOUNCEMENT.ANNNUMBR from the ANNOUNCEMENT used for the preceding token < WDANNDATE >
		 2.	Empty (aka Null)
		 */
		private String[] deriveENDOFMARKETANNNUMBER(DiffEntity parentDiff, DiffEntity loAvailDiff, DiffEntity endMktAvailDiff, DiffEntity endModelMktAvailDiff, boolean findT1, StringBuffer debugSb){
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveEndOfMarketAnnNumber lastOrderAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
                + "findT1:" + findT1 + NEWLINE);
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(146)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(146)--> ANNOUNCEMENT.ANNNUMBER
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endMktAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//				If PRODSTUCT.ORDERCODE = initial (5957), 
//				The child MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNNUMBER where AVAIL.AVAILTYPE = end of Marketing AND ANNOUNCEMENT.ANNTYPE = end Of Life - Withdrawal from mktg (14). ANNOUNCEMENT.COUNTRYLIST
				temps = getModelRelatedEOMANNNUM(findT1, parentDiff, endModelMktAvailDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		/**
		 * 
		 * @param findT1
		 * @param parentDiff
		 * @param endModelMktAvailDiff
		 * @param thedate
		 * @param rfrthedate
		 * @param debugSb
		 * @return
		 */
		public String[] getModelRelatedEOMANNNUM(boolean findT1,
				DiffEntity parentDiff, DiffEntity endModelMktAvailDiff,
				String thedate, String rfrthedate, StringBuffer debugSb) {
			String[] sReturn = new String[2];
			String entityDate = CHEAT;
			String dateSelfStatus = CHEAT;
			
			EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
						
			if (item != null) {
			    boolean flag = false;
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					if(item != null){
						String ordercode = PokUtils.getAttributeFlagValue(item, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
				}
				
				if (flag){					
					EntityItem modelitem = AvailUtil.getEntityItem(findT1, endModelMktAvailDiff);
				    if (modelitem != null) {
						//EntityItem modelitem = endModelMktAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute) modelitem.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							Vector relatorVec = modelitem.getDownLink();
							for (int ii = 0; ii < relatorVec.size(); ii++) {
								EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
								if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
									Vector annVct = availanna.getDownLink();
									EntityItem anna = (EntityItem) annVct.elementAt(0);
									entityDate = PokUtils.getAttributeValue(anna, "ANNNUMBER", ", ", CHEAT, false);
									ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveEOMANNNUMBER looking for downlink of AVAILANNA : Announcement "
										    + "ANNNUMBER :" + entityDate + NEWLINE);
									
									if (!CHEAT.equals(entityDate)) {																			
											dateSelfStatus = PokUtils.getAttributeFlagValue(anna, "STATUS");
										}
									}
									break;
								}
									
							}
						
						}
					}					
				if (dateSelfStatus != null && dateSelfStatus.equals("0020") && CHEAT.equals(thedate)){
					thedate = entityDate;
				} else if (dateSelfStatus != null && dateSelfStatus.equals("0040") && CHEAT.equals(rfrthedate)){
					rfrthedate = entityDate;
				}
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);	
			}	
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/**
		 * TODO need double check 
		 * @param parentDiff
		 * @param endModelMktAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		public String[] getModelFeatureSpecialDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity endModelMktAvailDiff, String thedate, String rfrthedate,
				StringBuffer debugSb) {
			
			String[] sReturn = new String[2];
			String entityDate = CHEAT;
			String dateSelfStatus = CHEAT;
			
			EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
			
			
			if (item != null) {
				//String parentstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
				//EntityItem item = parentDiff.getPriorEntityItem();
			    boolean flag = false;
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					if(item != null){
						String ordercode = PokUtils.getAttributeFlagValue(item, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
				}
				
				if (flag){
					if (item.hasDownLinks()) {
						for (int i = 0; i < item.getDownLinkCount(); i++) {
							EntityItem entity = (EntityItem) item.getDownLink(i);
							if (entity.getEntityType().equals("MODEL")) {
								entityDate = PokUtils.getAttributeValue(entity, "WITHDRAWDATE", ", ", CHEAT, false);
								dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
								ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from " + entity.getKey()
									+ " WITHDRAWDATE is:" + entityDate + NEWLINE);
								break;
							}
						}
					}
					EntityItem modelitem = AvailUtil.getEntityItem(findT1, endModelMktAvailDiff);
				    if (modelitem != null) {
						//EntityItem modelitem = endModelMktAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute) modelitem.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							Vector relatorVec = modelitem.getDownLink();
							for (int ii = 0; ii < relatorVec.size(); ii++) {
								EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
								if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
									Vector annVct = availanna.getDownLink();
									EntityItem anna = (EntityItem) annVct.elementAt(0);
									String modeldate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
									ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE looking for downlink of AVAILANNA : Announcement "
										    + "ANNDATE :" + modeldate + NEWLINE);
									
									if (!CHEAT.equals(modeldate)) {
										if (!CHEAT.equals(entityDate)) {
											// find the mix date between
											// model.anndate and
											// feature.firstanndate
											if (modeldate.compareTo(entityDate) < 0) {
												entityDate = modeldate;
												dateSelfStatus = PokUtils.getAttributeFlagValue(anna, "STATUS");
												debugSb
													.append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and ANNDATE|MODELAVAIL ANNOUNCEMENT.ANNDATE}, WITHDRAWDATE is:"
														+ entityDate + NEWLINE);
											}
										} else {
											entityDate = modeldate;
											dateSelfStatus = PokUtils.getAttributeFlagValue(anna, "STATUS");
										}
									}
									break;
								}
									
							}
						
						}
					}	
				}
				//New added 2)	PRODSTRUCT. WITHDRAWDATE
				if (parentDiff.getEntityType().equals("PRODSTRUCT")) {
					String date = PokUtils.getAttributeValue(item, "WITHDRAWDATE", ", ", CHEAT, false);
					ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE looking for PRODSTRUCT.WITHDRAWDATE " + item.getKey()
						+ date + NEWLINE);
					if (!CHEAT.equals(date)) {
						if (!CHEAT.equals(entityDate)) {
							// find the mix date between
							// model.anndate and
							// feature.firstanndate
							if (date.compareTo(entityDate) < 0) {
								entityDate = date;
								dateSelfStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
								debugSb
									.append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and ANNDATE|MODELAVAIL ANNOUNCEMENT.ANNDATE and PRODSTRUCT. WITHDRAWDATE}, WITHDRAWDATE is:"
										+ entityDate + NEWLINE);
							}
						} else {
							entityDate = date;
							dateSelfStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
						}
					}	
				}	

				if (item.hasUpLinks()) {
					for (int i = 0; i < item.getUpLinkCount(); i++) {
						EntityItem entity = (EntityItem) item.getUpLink(i);
						if (entity.getEntityType().equals("FEATURE") || entity.getEntityType().equals("SWFEATURE")) {
							String date = PokUtils.getAttributeValue(entity, "WITHDRAWANNDATE_T", ", ", CHEAT, false);
							ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWANNDATE_T from " + entity.getKey()
								+ " WITHDRAWANNDATE_T is:" + date + NEWLINE);
							if (!CHEAT.equals(date)) {
								if (!CHEAT.equals(entityDate)) {
									// find the mix date between
									// model.anndate and
									// feature.firstanndate
									if (date.compareTo(entityDate) < 0) {
										entityDate = date;
										dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
										debugSb
											.append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and FEATURE|SWFEATURE.WITHDRAWANNDATE_T}, WITHDRAWDATE is:"
												+ entityDate + NEWLINE);
									}
								} else {
									entityDate = date;
									dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
								}
							}
							break;
						}
					}
				}
				//
				if (dateSelfStatus != null && dateSelfStatus.equals("0020") && CHEAT.equals(thedate)){
					thedate = entityDate;
				} else if (dateSelfStatus != null && dateSelfStatus.equals("0040") && CHEAT.equals(rfrthedate)){
					rfrthedate = entityDate;
				}
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveWDANNDATE thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);	
		    }		
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		

		/***********************************************************************
		 * h)	ID 48.00 <LASTORDER>
           Same as <PUBTO> except skip the first one in the list (i.e. CATLGOR does not apply)
		 * 
		 */
		private String[] deriveLastOrder(boolean findT1, DiffEntity catlgorDiff, DiffEntity parentDiff,  DiffEntity loAvailDiff, DiffEntity loModelAvailDiff,  DiffEntity plModelAvailDiff,
			Vector[] feaCtry, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder " + " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
					+ " findT1:" + findT1 + NEWLINE);

			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String lastOrders[] = new String[2];
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the last order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// 2.	PRODSTRUCT. WTHDRWEFFCTVDATE date
				//The applicable countries are the intersection of the MODEL  AVAIL.COUNTRYLIST where AVAILTYPE = Last Order (149) and the FEATURE.COUNTRYLIST.
				temps = AvailUtil.getProdstructAttributeDate(findT1, parentDiff, loModelAvailDiff, feaCtry, thedate, rfrthedate, country, "WTHDRWEFFCTVDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
//				4.	Min of the following four that are applicable
//				1)	If PRODSTUCT.ORDERCODE =Initial(5957), then 
//				First of the following two
//				a)	MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAILTYPE = Last Order (149)
//				By the AVAIL.COUNTRYLIST
//				b)	MODEL. WTHDRWEFFCTVDATE;
//				By AVAIL.COUNTRYLIST where AVAILTYPE = Planned Availability(146)
//				2)	PRODSTRUCT. WTHDRWEFFCTVDATE
//				3)	SWFEATURE. WITHDRAWDATEEFF_T 
//				Applies to all Countries that get this far
//				4)	FEATURE. WITHDRAWDATEEFF_T
//				By FEATURE.COUNTRYLIST
				temps = getModelFeatureSpDate(findT1, parentDiff, loModelAvailDiff, plModelAvailDiff, thedate, rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			lastOrders[0]= thedate;
			lastOrders[1]= rfrthedate;
			return lastOrders;
		}

		/**
		 * TODO need double check the value
		 * @param parentDiff
		 * @param loModelAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		private String[] getModelFeatureSpDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity loModelAvailDiff, DiffEntity plModelAvailDiff,String thedate, String rfrthedate,
				StringBuffer debugSb) {
			String[] sReturn = new String[2];
			String entityDate = CHEAT;
			String dateSelfStatus = CHEAT;
			
			
			EntityItem item = AvailUtil.getEntityItem(findT1, parentDiff);
			
			if (item != null) {
				//EntityItem item = parentDiff.getPriorEntityItem();
				//String parentstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
			    boolean flag = false;
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					if(item != null){
						String ordercode = PokUtils.getAttributeFlagValue(item, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
				}
				

				if (flag){
					EntityItem plModelAvailitem = AvailUtil.getEntityItem(findT1, plModelAvailDiff);
					if (item.hasDownLinks()) {
						for (int i = 0; i < item.getDownLinkCount(); i++) {
							EntityItem entity = (EntityItem) item.getDownLink(i);
							if(plModelAvailitem!=null){
								EANFlagAttribute fAtt = (EANFlagAttribute) plModelAvailitem.getAttribute("COUNTRYLIST");
								if (fAtt != null && fAtt.isSelected(country)) {
									if (entity.getEntityType().equals("MODEL")) {
										entityDate = PokUtils.getAttributeValue(entity, "WTHDRWEFFCTVDATE", ", ", CHEAT, false);
										dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
										ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder getting WTHDRWEFFCTVDATE from " + entity.getKey()
											+ " PubTo is:" + entityDate + NEWLINE);
										break;
									}
								}
							}							
						}
					}
					EntityItem loModelitem = AvailUtil.getEntityItem(findT1, loModelAvailDiff);
					if (loModelitem != null) {
						//EntityItem loModelitem = loModelAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute) loModelitem.getAttribute("COUNTRYLIST");
						if (fAtt != null && fAtt.isSelected(country)) {
							String lomodelDate = PokUtils.getAttributeValue(loModelitem, "EFFECTIVEDATE", ", ", CHEAT, false);
							ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder lomodelavail entityDate: " + loModelitem + NEWLINE);
							if (!CHEAT.equals(lomodelDate)) {
								if (!CHEAT.equals(entityDate)) {
									// find the mix date between
									// model.anndate and
									// feature.firstanndate
									if (lomodelDate.compareTo(entityDate) < 0) {
										entityDate = lomodelDate;
										dateSelfStatus = PokUtils.getAttributeFlagValue(loModelitem, "STATUS");
										debugSb
											.append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {MODEL.WTHDRWEFFCTVDATE and AVAIL.EFFECTIVEDATE MODELAVAIL.AVAIL}, PubTo is:"
												+ entityDate + NEWLINE);
									}
								} else {
									entityDate = lomodelDate;
									dateSelfStatus = PokUtils.getAttributeFlagValue(loModelitem, "STATUS");
								}
							}
						}
					}																
				}
				//add 2)	PRODSTRUCT. WITHDRAWDATE
	
				if (parentDiff != null && parentDiff.getEntityType().equals("PRODSTRUCT")) {
					String date = PokUtils.getAttributeValue(item, "WTHDRWEFFCTVDATE", ", ", CHEAT, false);
					ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder looking for PRODSTRUCT.WTHDRWEFFCTVDATE" + item.getKey()
						+ date + NEWLINE);
					if (!CHEAT.equals(date)) {
						if (!CHEAT.equals(entityDate)) {
							// find the mix date between
							//  entityDate and date
							if (date.compareTo(entityDate) < 0) {
								entityDate = date;
								dateSelfStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
								debugSb
									.append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {PRODSTRUCT.WITHDRAWDATE and MODEL.WTHDRWEFFCTVDATE and ANNOUNCEMENT.ANNDATE MODELAVAIL}, PubTo is:"
										+ entityDate + NEWLINE);
							}
						} else {
							entityDate = date;
							dateSelfStatus = PokUtils.getAttributeFlagValue(item, "STATUS");
						}
					}							
				}
									
				if (item.hasUpLinks()) {
					for (int i = 0; i < item.getUpLinkCount(); i++) {
						EntityItem entity = (EntityItem) item.getUpLink(i);
						if (entity.getEntityType().equals("FEATURE") || entity.getEntityType().equals("SWFEATURE")) {
							String date = PokUtils.getAttributeValue(entity, "WITHDRAWDATEEFF_T", ", ", CHEAT, false);
							ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder getting WITHDRAWDATEEFF_T from " + entity.getKey()
								+ " PubTo is:" + date + NEWLINE);
							if (!CHEAT.equals(date)) {
								if (!CHEAT.equals(entityDate)) {
									// find the mix date between
									// model.anndate and
									// feature.firstanndate
									if (date.compareTo(entityDate) < 0) {
										entityDate = date;
										dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
										debugSb
											.append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {MODEL.WTHDRWEFFCTVDATE and AVAIL.EFFECTIVEDATE MODELAVAIL and FEATURE|SWFEATURE.WITHDRAWDATEEFF_T}, PubTo is:"
												+ entityDate + NEWLINE);
									}
								} else {
									entityDate = date;
									dateSelfStatus = PokUtils.getAttributeFlagValue(entity, "STATUS");
								}
							}
							break;
						}
					}
				}		
				//
				if (dateSelfStatus != null && dateSelfStatus.equals("0020") && CHEAT.equals(thedate)){
					thedate = entityDate;
				} else if (dateSelfStatus != null && dateSelfStatus.equals("0040") && CHEAT.equals(rfrthedate)){
					rfrthedate = entityDate;
				}
				ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveLastOrder thedate=" + thedate +";rfrthedate=" + rfrthedate  + NEWLINE);
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		
		
		
//		i)	ID 49.00 <EOSANNDATE>
//		1.	OOFAVAIL-d: OR SWPRODSTRUCTAVAIL-d: then AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		2.	IF PRODSRTRUCT.ORDERCODE = initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		3.	SWPRODSTRUCT-d: MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		4.	Empty

		private String[] deriveEOSANNDATE(boolean findT1, DiffEntity parentDiff, DiffEntity endAvailDiff, DiffEntity endModelAvailDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String sReturn[] = new String[2];
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveEOSANNDATE endAvailDiff: " + (endAvailDiff == null ? "null" : endAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = getModelFeatureEOSDate(findT1,parentDiff, endModelAvailDiff, thedate,rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}

		/**
		 * @param parentDiff
		 * @param endModelAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		private String[] getModelFeatureEOSDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity endModelAvailDiff, String thedate, String rfrthedate,
				StringBuffer debugSb) {
			String[] sReturn = new String[2];
			EntityItem item = AvailUtil.getEntityItem(findT1, endModelAvailDiff);
			EntityItem parentItem = AvailUtil.getEntityItem(findT1, parentDiff);
			
			
			if (item != null && parentItem!=null) {
				//String parentstatus = PokUtils.getAttributeFlagValue(parentItem, "STATUS");
				boolean flag = false;
				//2.	IF PRODSRTRUCT.ORDERCODE = Initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE 
				//		where AVAIL.AVAILTYPE = End of Service(151) and ANNOUNCEMENT = End Of Life  Discontinuance of Service(13).
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					EntityItem pitem = AvailUtil.getEntityItem(findT1, parentDiff);
					if(pitem!=null){
						String ordercode = PokUtils.getAttributeFlagValue(pitem, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
										
				} else if (parentDiff.getEntityType().equals("SWPRODSTRUCT")){
					flag = true;
				}
				if (flag){
					//EntityItem item = endModelAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector relatorVec = item.getDownLink();
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annVct.elementAt(0);
								String anndate = PokUtils.getAttributeValue(anna, "ANNDATE", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveEOSANNDATE looking for MODELAVAIL downlink of AVAILANNA : Announcement "
									+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
										+ anna.getKey() : anna.getKey()) + NEWLINE);
								String annstatus = PokUtils.getAttributeFlagValue(anna, "ANNSTATUS");
								if (annstatus != null && annstatus.equals("0020") && CHEAT.equals(thedate)){
									thedate = anndate;
								} else if (annstatus != null && annstatus.equals("0040") && CHEAT.equals(rfrthedate)){
									rfrthedate = anndate;
								}
							}
						}
					}															
				}
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;			
		}
		
//		m)	<ENDOFSERVICEANNNUMBER>
//		1.	OOFAVAIL-d: OR SWPRODSTRUCTAVAIL-d: then AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		2.	IF PRODSRTRUCT.ORDERCODE = initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		3.	SWPRODSTRUCT-d: MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = end of Service (151) and ANNOUNCEMENT = end Of Life  Discontinuance of Service (13).
//		4.	Empty
		private String[] deriveENDOFSERVICEANNNUMBER( DiffEntity parentDiff, DiffEntity endAvailDiff, DiffEntity endModelAvailDiff,
				boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String sReturn[] = new String[2];
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveEOSANNDATE endAvailDiff: " + (endAvailDiff == null ? "null" : endAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = getModelRelatedEOSANNNUM(findT1,parentDiff, endModelAvailDiff, thedate,rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0] = thedate;
			sReturn[1] = rfrthedate;
			return sReturn;
		}
		/**
		 * 
		 * @param findT1
		 * @param parentDiff
		 * @param endModelAvailDiff
		 * @param thedate
		 * @param rfrthedate
		 * @param debugSb
		 * @return
		 */
		private String[] getModelRelatedEOSANNNUM(boolean findT1,
				DiffEntity parentDiff, DiffEntity endModelAvailDiff,
				String thedate, String rfrthedate, StringBuffer debugSb) {
			String[] sReturn = new String[2];
			EntityItem item = AvailUtil.getEntityItem(findT1, endModelAvailDiff);
			EntityItem parentItem = AvailUtil.getEntityItem(findT1, parentDiff);
			
			
			if (item != null && parentItem!=null) {
				//String parentstatus = PokUtils.getAttributeFlagValue(parentItem, "STATUS");
				boolean flag = false;
				//2.	IF PRODSRTRUCT.ORDERCODE = Initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE 
				//		where AVAIL.AVAILTYPE = End of Service(151) and ANNOUNCEMENT = End Of Life  Discontinuance of Service(13).
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					EntityItem pitem = AvailUtil.getEntityItem(findT1, parentDiff);
					if(pitem!=null){
						String ordercode = PokUtils.getAttributeFlagValue(pitem, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
										
				} else if (parentDiff.getEntityType().equals("SWPRODSTRUCT")){
					flag = true;
				}
				if (flag){
					//EntityItem item = endModelAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						Vector relatorVec = item.getDownLink();
						for (int ii = 0; ii < relatorVec.size(); ii++) {
							EntityItem availanna = (EntityItem) relatorVec.elementAt(ii);
							if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
								Vector annVct = availanna.getDownLink();
								EntityItem anna = (EntityItem) annVct.elementAt(0);
								String annnum = PokUtils.getAttributeValue(anna, "ANNNUMBER", ", ", CHEAT, false);
								ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveEOSANNNUM looking for MODELAVAIL downlink of AVAILANNA : Announcement "
									+ (annVct.size() > 1 ? "There were multiple ANNOUNCEMENTS returned, using first one."
										+ anna.getKey() : anna.getKey()) + NEWLINE);
								String annstatus = PokUtils.getAttributeFlagValue(anna, "ANNSTATUS");
								if (annstatus != null && annstatus.equals("0020") && CHEAT.equals(thedate)){
									thedate = annnum;
								} else if (annstatus != null && annstatus.equals("0040") && CHEAT.equals(rfrthedate)){
									rfrthedate = annnum;
								}
							}
						}
					}															
				}
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;	
		}
		
//		j)	ID 50.00 <ENDOFSERVICEDATE> 
//		1.	OOFAVAIL-d: OR SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = end of Service (151).
//		2.	IF PRODSRTRUCT.ORDERCODE = initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = end of Service (151).
//		3.	SWPRODSTRUCT-d: MODELAVAIL-d: AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = end of Service (151). 
//		4.	Empty

		private String[] deriveENDOFSERVICEDATE(boolean findT1, DiffEntity parentDiff, DiffEntity endAvailDiff, DiffEntity endModelAvailDiff, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveENDOFSERVICEDATE endAvailDiff: " + (endAvailDiff == null ? "null" : endAvailDiff.getKey())
				+ "findT1:" + findT1 + NEWLINE);
			
			String[] sReturn = new String[2];
			
			String temps[] = new String[2];
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the plan avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the plan avail effective date
				temps = getModelFeatureENDSDate(findT1,parentDiff, endModelAvailDiff, thedate,rfrthedate, debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;	 
			
		}
		
		/**
		 * @param parentDiff
		 * @param endModelAvailDiff
		 * @param thedate
		 * @param debugSb
		 * @return
		 */
		private String[] getModelFeatureENDSDate(boolean findT1, DiffEntity parentDiff,
				DiffEntity endModelAvailDiff, String thedate, String rfrthedate,
				StringBuffer debugSb) {
			String[] sReturn = new String[2];
			EntityItem item = AvailUtil.getEntityItem(findT1, endModelAvailDiff);
			
			//EntityItem parentItem = AvailUtil.getEntityItem(findT1, parentDiff);
			
			if (item != null) {
				//String parentstatus = PokUtils.getAttributeFlagValue(parentItem, "STATUS");
				boolean flag = false;
				//2.	IF PRODSRTRUCT.ORDERCODE = Initial (5957) THEN PRODSTRUCT-d:  MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE 
				//		where AVAIL.AVAILTYPE = End of Service(151) and ANNOUNCEMENT = End Of Life  Discontinuance of Service(13).
				if (parentDiff.getEntityType().equals("PRODSTRUCT")){
					EntityItem pitem = AvailUtil.getEntityItem(findT1, parentDiff);
					if(pitem!=null){
						String ordercode = PokUtils.getAttributeFlagValue(pitem, "ORDERCODE");
						if (ordercode != null && "5957".equals(ordercode)){
							flag = true;																							
						}
					}
										
				} else if (parentDiff.getEntityType().equals("SWPRODSTRUCT")){
					flag = true;
				}
				if (flag){
					//EntityItem item = endModelAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("COUNTRYLIST");
					if (fAtt != null && fAtt.isSelected(country)) {
						String effdate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", ", ", CHEAT, false);
						String effstatus = PokUtils.getAttributeFlagValue(item, "STATUS");
						if (effstatus != null && effstatus.equals("0020") && CHEAT.equals(thedate)){
							thedate = effdate;
						} else if (effstatus != null && effstatus.equals("0040") && CHEAT.equals(rfrthedate)){
							rfrthedate = effdate;
						}						
						ABRUtil.append(debugSb,"XMLTMFAVAILElem.deriveENDOFSERVICEDATE endAvailDiff MODELAVAIL, thedate: " + thedate + NEWLINE);
					}																			
				}				
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;			
		}
		
		

		String getCountry() {
			return country;
		}

		String getKey() {
			return country;
		}


		public String toString() {
			return (availDiff!=null?availDiff.getKey()+ " action:" + action:"There is no AVAIL! ");
		}
	}
}
