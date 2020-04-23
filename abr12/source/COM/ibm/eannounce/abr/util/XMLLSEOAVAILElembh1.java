//Licensed Materials -- Property of IBM
// 
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
 *  Class used to hold info and structure to be generated for the xml feed
 * for abrs.
 *    * Constructor for <AVAILABILITYLIST> elements
 * <AVAILABILITYLIST> 2	AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
 * <AVIAILABILITYELEMENT>	 3		
 *   <AVAILABILITYACTION>		    4  AVAIL	CountryAction
 *   <STATUS>	                    
 *   <COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST - Flag Description Class 
 
 *   <EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>		4	AVAIL/
 * 	<PUBFROM>	</PUBFROM>		4	AVAIL/	PubFrom
 * 	<PUBTO>	</PUBTO>			4	AVAIL/	PubTo
 * 	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>			4	AVAIL/	Endofservice	
 * </AVIAILABILITYELEMENT>		3
 * </AVAILABILITYLIST>		2
 *
 */
// $Log: XMLLSEOAVAILElembh1.java,v $
// Revision 1.32  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.31  2014/03/25 14:40:59  guobin
// flows to BH prof srv - multi status change - more broadly then we needed. data not in final sent as final- Dave to investigate w Rupal
//
// Revision 1.30  2013/12/03 13:19:29  guobin
// fix delete XML Spec sent to dev - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.29  2013/09/13 06:02:39  guobin
// Defect 997578 - <WARRLIST> tag created when there is no matching country between WARR Relator and LSEO AVAIL.COUNTRYLIST
//
// Revision 1.28  2013/08/16 05:11:30  wangyulo
// fix the issue RCQ00222829 for the BHCATLGOR which change the CATLGOR to BHCATLGOR
//
// Revision 1.27  2012/08/24 15:29:08  wangyulo
// fix the Defects( 123011 and 123362) -- CATLGOR PUBFROM/TO not in XML
//
// Revision 1.26  2012/08/10 13:03:41  wangyulo
// Fix the defect 778199 for the Update the SEO determination of <PUBTO> and <LASTORDER> for GA products
//
// Revision 1.25  2012/01/22 05:38:35  guobin
// RTM work item number on the change is 643541 / BHCQ 81991 Update to XML System Feed Mapping 20120117.doc - correct design for PUBFROM, FIRSTORDER, PUBTO
//
// Revision 1.24  2012/01/05 13:38:38  liuweimi
// Defect 623414 - for changes to the handling of AVAILABILITYLIST for Special Bid LSEOs and LSEOBUNDLEs
//
// Revision 1.23  2011/11/17 13:17:32  liuweimi
// Fix Defect - 591202 based on BH FS ABR Data Transformation System Feed 20111031.doc
//
// Revision 1.22  2011/10/31 14:13:16  guobin
// comment out the release momery AVAIL and ANNOUNCE from HashTable
//
// Revision 1.21  2011/08/26 01:55:41  guobin
// add AVAILIBILITYLIST countrylist to table for WARRLIST to check COUNTRYLIST
//
// Revision 1.20  2011/08/17 08:46:10  guobin
// update the defect for generation of Sales Org /Plant Code in SEO_UPDATE
//
// Revision 1.19  2011/07/15 14:52:27  guobin
// check COFCATentity != null  for  parent Model of LSEO.
//
// Revision 1.18  2011/03/30 07:45:10  guobin
// add the world wide avail of SLEORGGRP/SLEORGNPLNTCD
//
// Revision 1.17  2011/03/03 14:03:32  guobin
// add SLEORGNPLNTCODE for the LSEO and LSEO special
//
// Revision 1.16  2011/02/22 07:35:16  guobin
// change for the LSEO special bid
//
// Revision 1.15  2011/02/16 03:15:15  guobin
// add change for LSEO SPECBID
//
// Revision 1.14  2011/02/14 05:35:45  guobin
// changes for the merge list for special bid LSEO
//
// Revision 1.13  2011/01/25 05:47:43  guobin
// change the LSEO path to D:AVAILSLEORGA:D, not from the root
//
// Revision 1.12  2011/01/24 14:16:53  guobin
// add hasChanges method
//
// Revision 1.11  2011/01/22 14:11:47  guobin
// Add new association from LSEOBUNDLE to SLEORGA path = "D:LSEOBUNDLESLEORGA:D";
//
// Revision 1.10  2011/01/21 12:39:14  guobin
// find the WWSEOLSEO  relator  of MODEL
//
// Revision 1.9  2011/01/14 09:34:45  guobin
// Set values	to <SLEORGGRPLIST>, <SLEORGNPLNTCODELIST> when LSEOBUNDLE.SPECBID =11458(Yes)
//
// Revision 1.8  2011/01/05 02:55:14  guobin
// Add AVAIL action is DELETE_ACTIVITY case
//
// Revision 1.7  2010/12/29 06:27:27  guobin
// Add <SLEORGGRPLIST> in <AVAILABILITYLIST>
//
// Revision 1.6  2010/11/22 13:46:36  guobin
// fix the parameter problem of deriveWDANNDATE().
//
// Revision 1.5  2010/10/19 09:46:10  guobin
// Add isDeriveFromLSEOBUNDLE() and createNodeFromLSEOBUNDLE
//
// Revision 1.4  2010/09/17 04:27:55  yang
// When geting CATLGOR.PUBFROM and PUBTO need to check CATLGOR.OFFCOUNTRY = country
//
// Revision 1.3  2010/08/26 08:50:51  yang
// Derive PubFrom ,Plannedavailability from CATLGOR
//
// Revision 1.2  2010/07/06 09:49:47  yang
// Complete all elements for 1.0
//
// Revision 1.1  2010/06/23 09:52:08  yang
// <AVAILABILITYLIST> for LSEO bh1.0
//
// Revision 1.2  2010/06/09 03:29:29  yang
// Line 394. Change to CtryAudRecord.country
//
// Revision 1.1  2010/06/03 15:22:01  yang
// build AVAILABILITYLIST for LSEO
//
// Revision 1.15  2010/04/15 01:21:18  yang
//  when derive from Model set AVAILABILITYACTION = Update
//
// Revision 1.14  2010/03/22 15:03:35  yang
// Add derivefromModel where there is no Planned Avail and Modle.Anndate less than 2010-03-31.
//
// Revision 1.13  2010/02/05 20:14:46  rick
// format prob take 4
//
// Revision 1.12  2010/02/05 20:12:15  rick
// format prob take 3
//
// Revision 1.11  2010/02/05 20:06:43  rick
// format prob take 2
//
// Revision 1.10  2010/02/05 19:37:07  rick
// possible format problem.
//
// Revision 1.9  2010/02/05 19:25:05  rick
// change <ENDOFSERVICE> to <ENDOFSERVICEDATE>
//
// Revision 1.8  2010/01/29 01:19:24  yang
// change isNewCountry().
//
// Revision 1.7  2010/01/28 08:46:27  yang
// comment out <EARLIESTSHIPDATE>, it is not for wave1
//
// Revision 1.6  2010/01/11 16:30:14  yang
// Use the first one of the results of AVAILANNA getDownLink() .
//
// Revision 1.5  2009/12/24 12:57:24  yang
// BH get<PUBFROM> from Avail downlink() Announcement.
//
// Revision 1.4  2009/12/17 11:36:11  yang
// *** empty log message ***
//
// Revision 1.3  2009/12/15 08:46:08  yang
// BH
//
// Revision 1.2  2009/12/10 14:31:58  yang
// BH
//
// Revision 1.1  2009/12/09 09:52:17  yang
// For BH <AVAILABILITYLIST>
//
// Revision 1.3  2008/05/28 13:44:23  wendy
// Added STATUS to output for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/04/29 14:26:12  wendy
// Add defaults
//
// Revision 1.1  2008/04/17 19:37:53  wendy
// Init for
// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class XMLLSEOAVAILElembh1 extends XMLElem {
	private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
	private String countryKey = "AVAILCOUNTRYLIST";

	/**********************************************************************************
	 * Constructor for <AVAILABILITYLIST> elements
	 * <AVAILABILITYLIST> 2	AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
	 * <AVIAILABILITYELEMENT>	 3		
	 *   <AVAILABILITYACTION>		    4  AVAIL	CountryAction
	 *   <STATUS>	                    
	 *   <COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST - Flag Description Class
	 
	 *   <EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>		4	AVAIL/
	 * 	<PUBFROM>	</PUBFROM>		4	AVAIL/	PubFrom
	 * 	<PUBTO>	</PUBTO>			4	AVAIL/	PubTo
	 * 	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>			4	AVAIL/	Endofservice	
	 * </AVIAILABILITYELEMENT>		3
	 * </AVAILABILITYLIST>		2
	 *
	 *
	 */
	public XMLLSEOAVAILElembh1() {
		super("AVAILABILITYELEMENT");
	}
	
	/**********************************************************************************
	 *@param dbCurrent Database
	 *@param table Hashtable of Vectors of DiffEntity
	 *@param document Document needed to create nodes
	 *@param parent Element node to add this node too
	 *@param parentItem DiffEntity - parent to use if path is specified in XMLGroupElem, item to use otherwise
	 *@param debugSb StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		String parenttype = parentItem.getEntityType();
	    String path = null;
//		 get parent Model for SLEORGGRPLIST and set Path to get SLEORGGRPLINTOCDE
		EntityItem COFCATentity = null ;
		if(parenttype.equals("LSEO")){
			path = "D:AVAILSLEORGA:D";
			if (parentItem.getCurrentEntityItem() != null)
	        {
	        	Vector uprelator = parentItem.getCurrentEntityItem().getUpLink();
	        	TT: for (int i=0; i<uprelator.size(); i++){
	        		EntityItem lseowwseo = (EntityItem)uprelator.get(i);
	        		if (lseowwseo != null && "WWSEOLSEO".equals(lseowwseo.getEntityType())){
	        			EntityItem wwseo= (EntityItem)lseowwseo.getUpLink(0);
	        			if(wwseo != null && "WWSEO".equals(wwseo.getEntityType())){
	        				Vector uprelator2 = wwseo.getUpLink();
	        				for(int j=0;j<uprelator2.size();j++){
	        					EntityItem modelwwseo= (EntityItem)uprelator2.get(j);
	        					if (modelwwseo != null && "MODELWWSEO".equals(modelwwseo.getEntityType())){
	        						COFCATentity = (EntityItem)modelwwseo.getUpLink(0);
	        						break TT;
	        					}        					
	        				}
	        			}        			 
	        		}
	        	}//end TT       	
	        }
		}
//		}else {
//			COFCATentity=parentItem.getCurrentEntityItem();
//			path = "D:LSEOBUNDLEAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
//		}
//		 * availType: LSEOSPECBID
//		 *            LSEO
//		 *            LSEOBUNDLESPECBID
//		 *            LSEOBUNDLE 
		//compatbility model V200309
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
		String availType = CHEAT;

		boolean isfromLSEOModel = isDerivefromLSEO(table, parentItem,  debugSb);
		
		if (isfromLSEOModel){
			path = null;
//			path = "D:LSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
//			path = "U:"+parentItem.getCurrentEntityItem().getKey()+":U:WWSEOLSEO:U:WWSEO:U:WWSEOPRODSTRUCT:D:PRODSTRUCT:U:MODEL:D:MODELAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
			availType = "LSEOSPECBID";
			createNodeFromLSEO(table, availType, document, parent, parentItem, COFCATentity, path,  isExistfinal,  compatModel,debugSb);	
//			if (COFCATentity!=null){
//				createNodeFromLSEOModel(table,COFCATentity.getKey(),dbCurrent, document, parent, parentItem, path,availType, debugSb);
//			}else{
//				ABRUtil.append(debugSb,"These is no MODLE relate to WWSEO.");
//			}		
//		}else if (isDerivefromLSEOBUNDLE(parentItem, debugSb)){
//			path = "D:LSEOBUNDLESLEORGA:D";
//			availType ="LSEOBUNDLESPECBID";
//			createNodeFromLSEOBUNDLE(table, availType, document, parent, parentItem, COFCATentity, path, debugSb);
		}else {
			//      	 get all AVAILs where AVAILTYPE="Planned Availability" (146) - some may be deleted
//			if(parenttype.equals("LSEO")){
//				availType = "LSEO";
//			}else{
//				availType = "LSEOBUNDLE";
//			}
			availType = "LSEO";
			Vector plnAvlVct = getPlannedAvails(table, availType, debugSb);

			if (plnAvlVct.size() > 0) { // must have planned avail for any of this, wayne said there will always be at least 1
				// get model audience values, t2[0] current, t1[1] prior
				// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
				// AVAILb to have US at T2			
		        
				
				//build T1 country list
				TreeMap ctryAudElemMap = new TreeMap();
				for (int i = 0; i < plnAvlVct.size(); i++) {
					DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
					buildCtryAudRecs(ctryAudElemMap, availDiff, true, debugSb);
				}// end each planned avail

				//build T2 country list
				for (int i = 0; i < plnAvlVct.size(); i++) {
					DiffEntity availDiff = (DiffEntity) plnAvlVct.elementAt(i);
					buildCtryAudRecs(ctryAudElemMap, availDiff, false, debugSb);
				}// end each planned avail

				
				// output the elements
				Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
				Collection ctryrecs = ctryAudElemMap.values();
				Iterator itr = ctryrecs.iterator();
				StringBuffer countrysb = new StringBuffer();
				while (itr.hasNext()) {
//					add coutrylist to table for WARRLIST to check whether MODLEWARR|PRODSTRUCTWARR. coutrylist in this AVAILIBILITYLIST.countrylist
    				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
    				//new added(update)
 //   				if ("LSEO".equals(availType)){	
 //   					if (countrysb.length()==0){
 //       					countrysb.append(ctryAudRec.getCountry());
 //       				}else{
 //       					countrysb.append("|" + ctryAudRec.getCountry());
 //       				}
//    				}
    				 
					//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
//					if (!ctryAudRec.isDeleted()) {
					// find firstorder avail for this country
					DiffEntity foAvailDiff = getEntityForAttrs(table, availType, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRec
						.getCountry(), debugSb);
					// find lastorder avail for this country
					DiffEntity loAvailDiff = getEntityForAttrs(table, availType, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRec
						.getCountry(), debugSb);
					DiffEntity endAvailDiff = getEntityForAttrs(table, availType, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRec
						.getCountry(), debugSb);
					//endMktAvailDiff
					DiffEntity endMktAvailDiff = getEntityForAttrs(table, availType, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRec
							.getCountry(), debugSb);
					
					DiffEntity catlgorDiff = getCatlgor(table, availType, mdlAudVct, ctryAudRec.getCountry(), debugSb);
					// add other info now
	        	
					ctryAudRec.setAllFields(parentItem, catlgorDiff, foAvailDiff, loAvailDiff, endAvailDiff, endMktAvailDiff, table, COFCATentity, ctryAudRec.availDiff, path, parent,  isExistfinal,  compatModel,debugSb);
	
				
					if (ctryAudRec.isDisplayable()||ctryAudRec.isrfrDisplayable()) {
						//new added
						if ("LSEO".equals(availType)){	
	    					if (countrysb.length()==0){
	        					countrysb.append(ctryAudRec.getCountry());
	        				}else{
	        					countrysb.append("|" + ctryAudRec.getCountry());
	        				}
	    				}
						//new added end
						createNodeSet(table, document, parent, COFCATentity,  ctryAudRec.availDiff, ctryAudRec, path, availType, debugSb);
					} else {
						ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for " + ctryAudRec + NEWLINE);
					}
					ctryAudRec.dereference();
				}
				//add countrylist to table
				if ("LSEO".equals(availType))
				table.put(countryKey, countrysb.toString());
				// release memory
				ctryAudElemMap.clear();
//				Vector annVct = (Vector) table.get("ANNOUNCEMENT");
//				Vector availVct = (Vector) table.get("AVAIL");
//				availVct.clear();
//				annVct.clear();
			} else {
				ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
			}
		}
	}
	
	
	/**
	 * availType: LSEOSPECBID
	 *            LSEO
	 *            LSEOBUNDLESPECBID
	 *            LSEOBUNDLE            
	 * 
	 * @param table
	 * @param isfromLSEOModel
	 * @param debugSb
	 * @return
	 */
	private Vector getSeoAndLseobundleAvail(Hashtable table,String availType,StringBuffer debugSb){
		
		Vector allVct = (Vector) table.get("AVAIL");
		//printTable(table, debugSb);
		Vector overrideVct = new Vector(1); 
		if (allVct != null) {
			if(availType.equals("LSEOBUNDLE")|| availType.equals("LSEOBUNDLESPECBID")){
				overrideVct=allVct;
			}else{
				for (int i = 0; i < allVct.size(); i++) {
					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
					if(diffitem.toString().indexOf("MODELAVAIL")>-1){
						if(availType.equals("LSEOSPECBID")) 
						{
							overrideVct.add(diffitem);
						}
					} else if(diffitem.toString().indexOf("LSEOAVAIL")>-1){
						if(availType.equals("LSEO")) {
							overrideVct.add(diffitem);
						}
					}					
				}
			}
		}
		return overrideVct;		
	}
	/**
	 * availType: LSEOSPECBID
	 *            LSEO
	 *            LSEOBUNDLESPECBID
	 *            LSEOBUNDLE            
	 *  
	 * @param table
	 * @param availType
	 * @param debugSb
	 * @return
	 */
	private Vector getLseoAndLseobundleCatlgor(Hashtable table,String availType,StringBuffer debugSb){
	    //RCQ00222829 change  CATLGOR to BHCATLGOR base on the doc BH FS ABR XML System Feed Mapping 20130214.doc		
		Vector allVct = (Vector) table.get("BHCATLGOR");
		Vector overrideVct = new Vector(1); 
		DiffEntity diffitem = null;
		EntityItem curritem = null;
		String BHStatus ="";
		//add the check of the status of BHCATLGOR
		if (allVct != null) {
			if(availType.equals("LSEOBUNDLE")|| availType.equals("LSEOBUNDLESPECBID")){
				for (int i = 0; i < allVct.size(); i++) {
					diffitem = (DiffEntity) allVct.elementAt(i);
					curritem = diffitem.getCurrentEntityItem();
					BHStatus  = PokUtils.getAttributeFlagValue(curritem,"STATUS");
					if(STATUS_FINAL.equals(BHStatus)){
						overrideVct.add(diffitem);
					}
				}
			}else{
				for (int i = 0; i < allVct.size(); i++) {
					diffitem = (DiffEntity) allVct.elementAt(i);
					curritem = diffitem.getCurrentEntityItem();
					BHStatus  = PokUtils.getAttributeFlagValue(curritem,"STATUS");
					if(STATUS_FINAL.equals(BHStatus)){
						if(!diffitem.getKey().equals("BHCATLGOR")){	
							if(diffitem.toString().indexOf("LSEOBHCATLGOR")>-1){
								 overrideVct.add(diffitem);
							}
						}
					}
				}
			}
		}
		return overrideVct;	
	}
//	private Vector getLseoAndLseobundleCatlgor(Hashtable table,String availType,StringBuffer debugSb){
//		Vector allVct = (Vector) table.get("CATLGOR");
//		Vector overrideVct = new Vector(1); 
//		if (allVct != null) {
//			if(availType.equals("LSEOBUNDLE")|| availType.equals("LSEOBUNDLESPECBID")){
//				overrideVct=allVct;
//			}else{
//				for (int i = 0; i < allVct.size(); i++) {
//					DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
//					if(!diffitem.getKey().equals("CATLGOR")){	
////						if(diffitem.toString().indexOf("MDLCATLGOR")>-1){
////							if(availType.equals("LSEOSPECBID")) overrideVct.add(diffitem);
////						} else 
//							if(diffitem.toString().indexOf("LSEOCATLGOR")>-1){
//							 overrideVct.add(diffitem);
//						}
//					}
//				}
//			}
//		}
//		return overrideVct;		
//	}

	/**
	 * print table VE information
	 * @param table
	 * @param debugSb
	 */
//	private void printTable(Hashtable table, StringBuffer debugSb) {
//		ABRUtil.append(debugSb,"XMLCtryAudElem.printTable for new lseo:" + NEWLINE);
//		Iterator it = table.keySet().iterator();
//		while (it.hasNext()){
//			String key =(String)it.next();
//			ABRUtil.append(debugSb,"table:key=" + key + ";value=" + table.get(key)+NEWLINE);
//		}
//		
//	}


	
	/**
	 * *  Class used to hold info and structure to be generated for the xml feed
	 * for abrs.
	 * 1.	If LSEOBUNDLE.SPECBID = 11458 (Yes) then
	 * a)	<ACTION> 
     * This is derived based on each value of LSEO.COUNTRYLIST.
     * b)	<STATUS>
     * LSEO.STATUS
     * c)	<COUNTRY_FC>
     * The description class of LSEO.COUNTRYLIST. There is one <AVAILABILITYELEMENT> for each value of COUNTRYLIST 
     * d)	<ANNDATE>
     * BUNDLPUBDATEMTRGT
     * e)	<ANNNUMBER>
     * Empty
     * f)	<FIRSTORDER>
     * BUNDLPUBDATEMTRGT
     * g)	<PLANNEDAVAILABILITY>
     * BUNDLPUBDATEMTRGT
     * h)	<PUBFROM>
     * 1.	LSEOBUNDLECATLGOR-d: CATLGOR.PUBFROM
     * 2.	BUNDLPUBDATEMTRGT
     * i)	<PUBTO>
     * 1.	LSEOBUNDLECATLGOR-d: CATLGOR.PUBTO
     * 2.	BUNDLUNPUBDATEMTRGT
     * j)	<WDANNDATE>
     * BUNDLUNPUBDATEMTRGT
     * k)	<LASTORDER>
     * BUNDLUNPUBDATEMTRGT
     * l)	<EOSANNDATE>
     * Null or empty
     * m)	<ENDOFSERVICEDATE>
     * Null or empty
	 * @param Hashtable
	 * @param document
	 * @param parent
	 * @param parentItem
	 * @param debugSb
	 * @param returnStatus
	 * @param anndate
	 * @param withdrawanndate
	 * @throws SQLException
	 * @throws MiddlewareException 
	 */
//	private void createNodeFromLSEOBUNDLE(Hashtable table, String availType, Document document, Element parent, DiffEntity parentItem, EntityItem COFCATentity, String path,
//		StringBuffer debugSb) throws SQLException, MiddlewareException {
//
//		EntityItem curritem = parentItem.getCurrentEntityItem();
//		EntityItem prioritem = parentItem.getPriorEntityItem();
//		TreeMap ctryAudElemMap = new TreeMap();
//		
//		if (parentItem.isNew()) { // If the AVAIL was deleted, set Action = Delete
//
//			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt "
//				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						String mapkey = ctryVal;
//						if (ctryAudElemMap.containsKey(mapkey)) {
//							// dont overwrite
//							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for New " + parentItem.getKey() + " " + mapkey
//								+ " already exists, keeping orig " + rec + NEWLINE);
//						} else {
//							CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
//							ctryAudRec.setAction(UPDATE_ACTIVITY);
//							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for New:" + parentItem.getKey() + " rec: "
//								+ ctryAudRec.getKey() + NEWLINE);
//						}
//					}
//				}
//			}
//		} else if (!parentItem.isDeleted()) {
//			HashSet prevSet = new HashSet();
//			HashSet currSet = new HashSet();
//			//put all current country into currvSet.
//			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt "
//				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						currSet.add(ctryVal);
//					}
//				}
//			}
//
//			//				put all prior country  into currvSet.
//			ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt "
//				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						prevSet.add(ctryVal);
//					}
//				}
//			}
//			//				 look for changes in country
//			Iterator itr = currSet.iterator();
//			while (itr.hasNext()) {
//				String ctryVal = (String) itr.next();
//				if (!prevSet.contains(ctryVal)) { // If a pair of CountryAudience was added, set that row's Action = Update
//					//create crossproduct between new ctry and current audience for this item
//					if (ctryAudElemMap.containsKey(ctryVal)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on " + parentItem.getKey() + " " + ctryVal
//							+ " already exists, replacing orig " + rec + NEWLINE);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
//						ctryAudRec.setAction(UPDATE_ACTIVITY);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for added ctry:" + parentItem.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//				} else {
//					// ctryaudience already existed but something else may have changed
//					if (ctryAudElemMap.containsKey(ctryVal)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry on " + parentItem.getKey() + " " + ctryVal
//							+ " already exists, keeping orig " + rec + NEWLINE);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + parentItem.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//
//				}
//			}
//			itr = prevSet.iterator();
//			while (itr.hasNext()) {
//				String ctryVal = (String) itr.next();
//				if (!currSet.contains(ctryVal)) { //If a pair of countryaudience was deleted, set that row's Action = Delete
//					//create crossproduct between deleted ctry and previous audience for this item
//					if (ctryAudElemMap.containsKey(ctryVal)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on " + parentItem.getKey() + " " + ctryVal
//							+ " already exists, keeping orig " + rec + NEWLINE);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
//						ctryAudRec.setAction(DELETE_ACTIVITY);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted ctry:" + parentItem.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//
//				}
//			}
//		}
//		Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
//		Collection ctryrecs = ctryAudElemMap.values();
//		Iterator itr = ctryrecs.iterator();
//		while (itr.hasNext()) {
//
//			CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
//			if (!ctryAudRec.isDeleted()) {
//
//				DiffEntity catlgorDiff = getCatlgor(table, availType, mdlAudVct, ctryAudRec.getCountry(), debugSb);
//				
//				ctryAudRec.setAllFields(parentItem, catlgorDiff, table, COFCATentity, path,  parent,  debugSb);
//				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
//				// add other info now
//
//				//get STATUS
////				if (curritem != null) {
////					ctryAudRec.availStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
////				}
//				//set PUBFROM ,plannedavailability 
//				//pubfrom is
//				//1.	LSEOBUNDLECATLGOR-d: CATLGOR.PUBFROM
//				//2.	LSEOBUNDLE. 2.	BUNDLPUBDATEMTRGT (SEO Publish Date Target
//			
//				//add the check of the country of ctryAudRec with the country of catlgorDiff, 
//				//the country of ctryAudRec should be in the country of catlgorDiff
//				//the BHCATLGOR have multiple countries, so it need add the check for the country
////				EANFlagAttribute fCountry = null;
////				if (catlgorDiff != null && !catlgorDiff.isDeleted()) {
////					EntityItem currItem = catlgorDiff.getCurrentEntityItem();
////					fCountry = (EANFlagAttribute) currItem.getAttribute(BHCOUNTRYLIST);
////					if (fCountry != null && fCountry.isSelected(ctryAudRec.country)) {
////						ctryAudRec.pubfrom = PokUtils.getAttributeValue(currItem, "PUBFROM", ", ", CHEAT, false);
////						ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom catlgor thedate: " + ctryAudRec.pubfrom + NEWLINE);
////					}					
////				}
////				if (CHEAT.equals(ctryAudRec.pubfrom)) {
////					if (curritem != null) {
////						ctryAudRec.pubfrom = PokUtils.getAttributeValue(curritem, "BUNDLPUBDATEMTRGT", "", CHEAT, false);
////					}
////				}
////				String prevpubfrom = CHEAT;
////				if (catlgorDiff != null && !catlgorDiff.isNew()) {
////					EntityItem prevItem = catlgorDiff.getPriorEntityItem();
////					fCountry = (EANFlagAttribute) prevItem.getAttribute(BHCOUNTRYLIST);
////					if (fCountry != null && fCountry.isSelected(ctryAudRec.country)) {
////						prevpubfrom = PokUtils.getAttributeValue(prevItem, "PUBFROM", ", ", CHEAT, false);
////						ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom catlgor thedate: " + prevpubfrom + NEWLINE);
////					}					
////				}
////				if (CHEAT.equals(prevpubfrom)) {
////					if (prioritem != null) {
////						prevpubfrom = PokUtils.getAttributeValue(prioritem, "BUNDLPUBDATEMTRGT", ", ", CHEAT, false);
////					}
////					ABRUtil.append(debugSb,"XMLAVAILElem.setAllFields pubfrom: " + ctryAudRec.pubfrom + " prevdate: " + prevpubfrom
////						+ NEWLINE);
////				}
////				// if diff, set action
////				if (!prevpubfrom.equals(ctryAudRec.pubfrom)) {
////					ctryAudRec.setAction(UPDATE_ACTIVITY);
////				}
////				ctryAudRec.plannedavailability = ctryAudRec.pubfrom;
////				//<PLANNEDAVAILABILITY> is
////				//BUNDLPUBDATEMTRGT
////				//<FIRSTORDER> is
////				//BUNDLPUBDATEMTRGT
////				//<ANNDATE> is
////				//BUNDLPUBDATEMTRGT
////				if (curritem != null) {
////					ctryAudRec.firstorder = PokUtils.getAttributeValue(curritem, "BUNDLPUBDATEMTRGT", "", CHEAT, false);
////				}
////				String prevfirstorder = CHEAT;
////				if (prioritem != null) {
////					prevfirstorder = PokUtils.getAttributeValue(prioritem, "BUNDLPUBDATEMTRGT", ", ", CHEAT, false);
////				}
////				if (!prevfirstorder.equals(ctryAudRec.firstorder)) {
////					ctryAudRec.setAction(UPDATE_ACTIVITY);
////				}
////				ctryAudRec.anndate = ctryAudRec.firstorder;
//////				ctryAudRec.plannedavailability = ctryAudRec.firstorder;
////
////				// lastorder,  WDANNDATE
////				//<WDANNDATE> , <LASTORDER>  
////				//is LSEOBUNDLE. BUNDLUNPUBDATEMTRGT (SEO Unpublish Date Target 
////				if (curritem != null) {
////					ctryAudRec.lastorder = PokUtils.getAttributeValue(curritem, "BUNDLUNPUBDATEMTRGT", "", CHEAT, false);
////				}
////				String prevlastorder = CHEAT;
////				if (prioritem != null) {
////					prevlastorder = PokUtils.getAttributeValue(prioritem, "BUNDLUNPUBDATEMTRGT", ", ", CHEAT, false);
////				}
////				ABRUtil.append(debugSb,"XMLAVAILElem.setAllFields lastorder: " + ctryAudRec.lastorder + " prevdate: " + prevlastorder + NEWLINE);
////
////				// if diff, set action
////				if (!prevlastorder.equals(ctryAudRec.lastorder)) {
////					ctryAudRec.setAction(UPDATE_ACTIVITY);
////				}
////				ctryAudRec.wdanndate = ctryAudRec.lastorder;
////                //<PUBTO>
////				//1.	LSEOBUNDLECATLGOR-d: CATLGOR.PUBTO
////				//2.	BUNDLUNPUBDATEMTRGT				
////				
////				if (catlgorDiff != null && !catlgorDiff.isDeleted()) {
////					EntityItem currItem = catlgorDiff.getCurrentEntityItem();
////					fCountry = (EANFlagAttribute) currItem.getAttribute(BHCOUNTRYLIST);
////					if (fCountry != null && fCountry.isSelected(ctryAudRec.country)) {
////						ctryAudRec.pubto = PokUtils.getAttributeValue(currItem, "PUBTO", ", ", CHEAT, false);
////						ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo catlgor thedate: " + ctryAudRec.pubto + NEWLINE);
////					}					
////				}
////				if (CHEAT.equals(ctryAudRec.pubto)) {
////					if (curritem != null) {
////						ctryAudRec.pubto = PokUtils.getAttributeValue(curritem, "BUNDLUNPUBDATEMTRGT", "", CHEAT, false);
////					}
////				}
////				
////				String prevpubto = CHEAT;
////				if (catlgorDiff != null && !catlgorDiff.isNew()) {
////					EntityItem prevItem = catlgorDiff.getPriorEntityItem();
////					fCountry = (EANFlagAttribute) prevItem.getAttribute(BHCOUNTRYLIST);
////					if (fCountry != null && fCountry.isSelected(ctryAudRec.country)) {
////						prevpubto = PokUtils.getAttributeValue(prevItem, "PUBTO", ", ", CHEAT, false);
////						ABRUtil.append(debugSb,"XMLAVAILElem.derivePubto catlgor thedate: " + prevpubto + NEWLINE);
////					}					
////				}
////				if (CHEAT.equals(prevpubto)) {
////					if (prioritem != null) {
////						prevpubto = PokUtils.getAttributeValue(prioritem, "BUNDLUNPUBDATEMTRGT", ", ", CHEAT, false);
////					}
////					ABRUtil.append(debugSb,"XMLAVAILElem.setAllFields pubto: " + ctryAudRec.pubto + " prevdate: " + prevpubto
////						+ NEWLINE);
////				}
////				// if diff, set action
////				if (!prevpubto.equals(ctryAudRec.pubto)) {
////					ctryAudRec.setAction(UPDATE_ACTIVITY);
////				}
////			
////				
////                boolean SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, COFCATentity, parentItem, path, ctryAudRec.country, debugSb);			    
////				if (SLEORGGRPChaned){
////					ctryAudRec.setAction(UPDATE_ACTIVITY);
////				}
//		
//			}
//			if (ctryAudRec.isDisplayable()) {
//				createNodeSet(table, document, parent, COFCATentity, parentItem, ctryAudRec, path, availType, debugSb);
//			} else {
//				ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for " + ctryAudRec.country + NEWLINE);
//			}
//			ctryAudRec.dereference();
//		}
//		ctryAudElemMap.clear();
//	}
	
	private void createNodeFromLSEO(Hashtable table, String availType, Document document, Element parent, DiffEntity parentItem, EntityItem COFCATentity, String path,
		boolean isExistfinal, boolean compatModel, StringBuffer debugSb) throws SQLException, MiddlewareException {

			EntityItem curritem = parentItem.getCurrentEntityItem();
			EntityItem prioritem = parentItem.getPriorEntityItem();
			TreeMap ctryAudElemMap = new TreeMap();
			
			if (parentItem.isNew()) { // If the AVAIL was deleted, set Action = Delete

				EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt "
					+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
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
								ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for New " + parentItem.getKey() + " " + mapkey
									+ " already exists, keeping orig " + rec + NEWLINE);
							} else {
								CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
								ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for New:" + parentItem.getKey() + " rec: "
									+ ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
				}
			} else if (!parentItem.isDeleted()) {
				HashSet prevSet = new HashSet();
				HashSet currSet = new HashSet();
				//put all current country into currvSet.
				EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt "
					+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							currSet.add(ctryVal);
						}
					}
				}

				//				put all prior country  into currvSet.
				ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt "
					+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
				if (ctryAtt != null) {
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							prevSet.add(ctryVal);
						}
					}
				}
				//				 look for changes in country
				Iterator itr = currSet.iterator();
				while (itr.hasNext()) {
					String ctryVal = (String) itr.next();
					if (!prevSet.contains(ctryVal)) { // If a pair of CountryAudience was added, set that row's Action = Update
						//create crossproduct between new ctry and current audience for this item
						if (ctryAudElemMap.containsKey(ctryVal)) {
							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on " + parentItem.getKey() + " " + ctryVal
								+ " already exists, replacing orig " + rec + NEWLINE);
						} else {
							CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
							ctryAudRec.setAction(UPDATE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for added ctry:" + parentItem.getKey() + " rec: "
								+ ctryAudRec.getKey() + NEWLINE);
						}
					} else {
						// ctryaudience already existed but something else may have changed
						if (ctryAudElemMap.containsKey(ctryVal)) {
							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry on " + parentItem.getKey() + " " + ctryVal
								+ " already exists, keeping orig " + rec + NEWLINE);
						} else {
							CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + parentItem.getKey() + " rec: "
								+ ctryAudRec.getKey() + NEWLINE);
						}

					}
				}
				itr = prevSet.iterator();
				while (itr.hasNext()) {
					String ctryVal = (String) itr.next();
					if (!currSet.contains(ctryVal)) { //If a pair of countryaudience was deleted, set that row's Action = Delete
						//create crossproduct between deleted ctry and previous audience for this item
						if (ctryAudElemMap.containsKey(ctryVal)) {
							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on " + parentItem.getKey() + " " + ctryVal
								+ " already exists, keeping orig " + rec + NEWLINE);
						} else {
							CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted ctry:" + parentItem.getKey() + " rec: "
								+ ctryAudRec.getKey() + NEWLINE);
						}

					}
				}
			}
			Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
			Collection ctryrecs = ctryAudElemMap.values();
			Iterator itr = ctryrecs.iterator();
			//new added
			StringBuffer countrysb = new StringBuffer(); 
			//new added end
			while (itr.hasNext()) {

				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				DiffEntity catlgorDiff = getCatlgor(table, availType, mdlAudVct, ctryAudRec.getCountry(), debugSb);
				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
				// add other info now

				//TODO doc BH FS ABR XML System Feed Mapping 20131106.doc add two status in XML 
				
        	
				ctryAudRec.setAllLSEOFields(parentItem, catlgorDiff, table, COFCATentity, path,  parent,   isExistfinal,  compatModel, debugSb);
				
				if (ctryAudRec.isDisplayable()||ctryAudRec.isrfrDisplayable()) {
					createNodeSet(table, document, parent, COFCATentity, parentItem, ctryAudRec, path, availType, debugSb);
					//new added 
					if (countrysb.length()==0){
						countrysb.append(ctryAudRec.getCountry());
					}else{
						countrysb.append("|" + ctryAudRec.getCountry());
					}
					//new added end
				} else {
					ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for " + ctryAudRec.country + NEWLINE);
				}
				ctryAudRec.dereference();
			}
			//new added
			table.put(countryKey, countrysb.toString());
			//new added end
			ctryAudElemMap.clear();
		}
	
	/********************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Hashtable table, Document document, Element parent, EntityItem COFCATitem, DiffEntity realtedSLEORGGRP, 
			     CtryAudRecord ctryAudRec, String path, String availType, StringBuffer debugSb) {
		
		if(ctryAudRec.isDisplayable()){
			Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
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
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getLastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEosanndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getEndOfService()));
			elem.appendChild(child);
			//add SLEORGGRP
			if(availType.equals("LSEO")){
				SLEORGGRP.displayAVAILSLEORGLSEO(table, document, elem, COFCATitem, realtedSLEORGGRP, 
						path, ctryAudRec.country, ctryAudRec.getAction(), availType, debugSb);
			}else{
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATitem, realtedSLEORGGRP, 
						path, ctryAudRec.country, ctryAudRec.getAction(), debugSb);
			}
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
			child = (Element) document.createElement("LASTORDER");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrlastorder()));
			elem.appendChild(child);
			child = (Element) document.createElement("EOSANNDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfreosanndate()));
			elem.appendChild(child);
			child = (Element) document.createElement("ENDOFSERVICEDATE");
			child.appendChild(document.createTextNode("" + ctryAudRec.getRfrendofservice()));
			elem.appendChild(child);
			//add SLEORGGRP
			if(availType.equals("LSEO")){
				SLEORGGRP.displayAVAILSLEORGLSEO(table, document, elem, COFCATitem, realtedSLEORGGRP, 
						path, ctryAudRec.country, ctryAudRec.getAction(), availType, debugSb);
			}else{
				SLEORGGRP.displayAVAILSLEORG(table, document, elem, COFCATitem, realtedSLEORGGRP, 
						path, ctryAudRec.country, ctryAudRec.getAction(), debugSb);
			}			
		}
	}

	/******************** this method has changed for BH. 
	 * Create rows in the table as follows:
	 * Insert one row for each Audience in MODEL.AUDIEN & each Country in AVAIL.COUNTRYLIST where AVAILTYPE = 146
	 * If the AVAIL was deleted, set Action = Delete
	 * If the AVAIL was added or updated, set Action = Update
	 * 
	 * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
	 * If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
	 *
	 * Note:
	 * Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
	 * If any of the following steps have data that do not match an existing row in this table, ignore that data.
	 */
	/********************
	 * get audience values from t1 and t2 for this model, do it once and use for each avail
	 */
	private Vector[] getModelAudience(DiffEntity modelDiff, StringBuffer debugSb) {
		ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience for " + modelDiff.getKey() + NEWLINE);

		EANFlagAttribute audienceAtt = (EANFlagAttribute) modelDiff.getCurrentEntityItem().getAttribute("AUDIEN");
		Vector currAudVct = new Vector(1);
		Vector prevAudVct = new Vector(1);
		Vector vct[] = new Vector[2];
		vct[0] = currAudVct;
		vct[1] = prevAudVct;
		ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience cur audienceAtt " + audienceAtt + NEWLINE);
		if (audienceAtt != null) {
			MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					currAudVct.addElement(mfArray[i].toString()); // this is long desc
				}
			}
		}

		if (!modelDiff.isNew()) {
			audienceAtt = (EANFlagAttribute) modelDiff.getPriorEntityItem().getAttribute("AUDIEN");
			ABRUtil.append(debugSb,"XMLCtryAudElem.getModelAudience new audienceAtt " + audienceAtt + NEWLINE);
			if (audienceAtt != null) {
				MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						prevAudVct.addElement(mfArray[i].toString()); // this is long desc
					}
				}
			}
		}

		return vct;
	}

//	private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, StringBuffer debugSb) {
//
//		ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs " + availDiff.getKey() + NEWLINE);
//
//		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
//		// AVAILb to have US at T2
//		// only delete action if ctry or aud was removed at t2!!! allow update to override it
//
//		EntityItem curritem = availDiff.getCurrentEntityItem();
//		EntityItem prioritem = availDiff.getPriorEntityItem();
//		if (availDiff.isDeleted()) { // If the AVAIL was deleted, set Action = Delete
//			// mark all records as delete
//			EANFlagAttribute ctryAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt "
//				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") + NEWLINE);
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						String mapkey = ctryVal;
//						if (ctryAudElemMap.containsKey(mapkey)) {
//							// dont overwrite
//							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted " + availDiff.getKey() + " " + mapkey
//								+ " already exists, keeping orig " + rec + NEWLINE);
//						} else {
//							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
//							ctryAudRec.setAction(DELETE_ACTIVITY);
//							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted:" + availDiff.getKey() + " rec: "
//								+ ctryAudRec.getKey() + NEWLINE);
//						}
//					}
//				}
//			}
//		} else if (availDiff.isNew()) { //If the AVAIL was added or updated, set Action = Update
//			// mark all records as update
//			EANFlagAttribute ctryAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt "
//				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")
//				+ PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") + NEWLINE);
//			if (ctryAtt != null) {
//				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
//				for (int im = 0; im < mfArray.length; im++) {
//					// get selection
//					if (mfArray[im].isSelected()) {
//						String ctryVal = mfArray[im].getFlagCode();
//						String mapkey = ctryVal;
//						if (ctryAudElemMap.containsKey(mapkey)) {
//							CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new " + availDiff.getKey() + " " + mapkey
//								+ " already exists, replacing orig " + rec + NEWLINE);
//							rec.setUpdateAvail(availDiff);
//						} else {
//							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
//							ctryAudRec.setAction(UPDATE_ACTIVITY);
//							ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new:" + availDiff.getKey() + " rec: "
//								+ ctryAudRec.getKey() + NEWLINE);
//						}
//					}
//				}
//			}
//		} else {// else if one country in the countrylist has changed, update this row to update!
//			HashSet prevSet = new HashSet();
//			HashSet currSet = new HashSet();
//			// get current set of countries
//			EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt "
//				+ PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")
//				+ PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") + NEWLINE);
//			if (fAtt != null && fAtt.toString().length() > 0) {
//				// Get the selected Flag codes.
//				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
//				for (int i = 0; i < mfArray.length; i++) {
//					// get selection
//					if (mfArray[i].isSelected()) {
//						currSet.add(mfArray[i].getFlagCode());
//					} // metaflag is selected
//				}// end of flagcodes
//			}
//
//			// get prev set of countries
//			fAtt = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
//			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt "
//				+ PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")
//				+ PokUtils.getAttributeFlagValue(prioritem, "ANNCODENAME") + NEWLINE);
//			if (fAtt != null && fAtt.toString().length() > 0) {
//				// Get the selected Flag codes.
//				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
//				for (int i = 0; i < mfArray.length; i++) {
//					// get selection
//					if (mfArray[i].isSelected()) {
//						prevSet.add(mfArray[i].getFlagCode());
//					} // metaflag is selected
//				}// end of flagcodes
//			}
//
//			// look for changes in country
//			Iterator itr = currSet.iterator();
//			while (itr.hasNext()) {
//				String ctryVal = (String) itr.next();
//				if (!prevSet.contains(ctryVal)) { // If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
//
//					String mapkey = ctryVal;
//					if (ctryAudElemMap.containsKey(mapkey)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on " + availDiff.getKey() + " " + mapkey
//							+ " already exists, replacing orig " + rec + NEWLINE);
//						rec.setUpdateAvail(availDiff);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
//						ctryAudRec.setAction(UPDATE_ACTIVITY);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for added ctry:" + availDiff.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//				} else {
//					// BH this country has already exist, put into ctryaudrec, but don't udpate Action to 'update'/'delete'.
//					String mapkey = ctryVal;
//					if (ctryAudElemMap.containsKey(mapkey)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry but new aud on " + availDiff.getKey() + " "
//							+ mapkey + " already exists, keeping orig " + rec + NEWLINE);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + availDiff.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//				}
//			}//end of currset while(itr.hasNext())
//			itr = prevSet.iterator();
//			while (itr.hasNext()) {
//				String ctryVal = (String) itr.next();
//				if (!currSet.contains(ctryVal)) { //If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
//					//create crossproduct between deleted ctry and previous audience for this item	
//					String mapkey = ctryVal;
//					if (ctryAudElemMap.containsKey(mapkey)) {
//						CtryAudRecord rec = (CtryAudRecord) ctryAudElemMap.get(mapkey);
//						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on " + availDiff.getKey() + " " + mapkey
//							+ " already exists, keeping orig " + rec + NEWLINE);
//					} else {
//						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
//						ctryAudRec.setAction(DELETE_ACTIVITY);
//						ctryAudElemMap.put(ctryAudRec.getKey(), ctryAudRec);
//						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for delete ctry:" + availDiff.getKey() + " rec: "
//							+ ctryAudRec.getKey() + NEWLINE);
//					}
//				}
//
//			}
//		} // end avail existed at both t1 and t2
//	}
	
	  private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, boolean T1, StringBuffer debugSb){


			ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs build T1 country list " + T1 + " " + availDiff.getKey()+NEWLINE);

			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
			// AVAILb to have US at T2
			// only delete action if ctry or aud was removed at t2!!! allow update to override it

			EntityItem curritem = availDiff.getCurrentEntityItem();
			EntityItem prioritem = availDiff.getPriorEntityItem();
			if (T1){
				if (!availDiff.isNew()){ // If the AVAIL was deleted, set Action = Delete
					// mark all records as delete
					EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
					ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for deleted / update avail at T1: ctryAtt "+
								PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST") +NEWLINE);
					if (ctryAtt!=null){
						MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
						for (int im = 0; im < mfArray.length; im++){
							// get selection
							if (mfArray[im].isSelected()) {
								String ctryVal = mfArray[im].getFlagCode();
								String mapkey = ctryVal;
								if (ctryAudElemMap.containsKey(mapkey)){
									// dont overwrite
									CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
									ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted / update "+availDiff.getKey()+
										" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
								}else{
									CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
									ctryAudRec.setAction(DELETE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								}
							}
						}				
					}
				}			
			}else {
				if (!availDiff.isDeleted()){ //If the AVAIL was added or updated, set Action = Update
					// mark all records as update			
					EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
					ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt "+
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
									if (DELETE_ACTIVITY.equals(rec.action)){
										ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new /udpate"+availDiff.getKey()+
											" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
											rec.setUpdateAvail(availDiff);
											rec.setAction(CHEAT);
									}
								}else{
									CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
									ctryAudRec.setAction(UPDATE_ACTIVITY);
									ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
									ABRUtil.append(debugSb,"XMLAVAILElem.buildCtryAudRecs for new:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey() + NEWLINE);
								}
							}
						}
					}
				}
			}
		}

	/********************
	 * get planned avails - availtype cant be changed
	 */
	private Vector getPlannedAvails(Hashtable table, String availType, StringBuffer debugSb) {
		Vector avlVct = new Vector(1);
		//Vector allVct = (Vector) table.get("AVAIL");
		Vector allVct = getSeoAndLseobundleAvail(table, availType,debugSb);

		ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
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
				ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails checking[" + i + "]: deleted " + diffitem.getKey()
					+ " AVAILTYPE: " + PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			} else {
				ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails checking[" + i + "]:" + diffitem.getKey() + " AVAILTYPE: "
					+ PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE") + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
				if (fAtt != null && fAtt.isSelected("146")) {
					avlVct.add(diffitem);
				}
			}
		}

		return avlVct;
	}
	/**
	 * check the item is from the same relator that it connect from the root entity
	 * @param item
	 * @param debugSb
	 * @return
	 */
	/********************
	 * get entity with specified values - should only be one
	 * could be two if one was deleted and one was added, but the added one will override and be an 'update'
	 */
	private DiffEntity getEntityForAttrs(Hashtable table, String availType, String etype, String attrCode, String attrVal, String attrCode2,
		String attrVal2, StringBuffer debugSb) {
		DiffEntity diffEntity = null;
		//Vector allVct = (Vector) table.get(etype);
		Vector allVct = getSeoAndLseobundleAvail(table, availType, debugSb);
		
		ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs looking for " + attrCode + ":" + attrVal + " and " + attrCode2 + ":"
			+ attrVal2 + " in " + etype + " allVct.size:" + (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return diffEntity;
		}
		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking[" + i + "]: deleted " + diffitem.getKey() + " "
					+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
					+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
				if (fAtt != null && fAtt.isSelected(attrVal)) {
					fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
					if (fAtt != null && fAtt.isSelected(attrVal2)) {
						diffEntity = diffitem; // keep looking for one that is not deleted
					}
				}
			} else {
				if (diffitem.isNew()) {
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking[" + i + "]: new " + diffitem.getKey() + " "
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
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking[" + i + "]: current " + diffitem.getKey() + " "
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
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking[" + i + "]: prior " + diffitem.getKey() + " "
						+ attrCode + ":" + PokUtils.getAttributeFlagValue(prioritem, attrCode) + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);
					fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode);
					if (fAtt != null && fAtt.isSelected(attrVal)) {
						fAtt = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
						if (fAtt != null && fAtt.isSelected(attrVal2)) {
							diffEntity = diffitem;
							//break; see if there is another that is current
						}
					}
				}
			}
		}

		return diffEntity;
	}

	/********************
	 *   If WWSEO.SPECBID is not equal to 'No' (11457), then derive from LSEO.   
	 *  @param table  
	 *         Hashtable that contain all the entities.
	 *  @param availtype 
	 *         AVAIL.AVAILTYPE 
	 *  @param debugSb
	 *         StringBuffer logo output.
	 *         
	 *   If WWSEO.SPECBID is not equal to 'No' (11457), then derive from 
	 *  from LSEO attributes
	 **/
	private boolean isDerivefromLSEO(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		boolean isfromLSEO = false;
		if (parentItem.getEntityType().equals("LSEO")){
			Vector allVct = (Vector) table.get("WWSEO");
			ABRUtil.append(debugSb,"DerivefromLSEO looking for WWSEO.SPECBID. allVct.size:" + (allVct == null ? "null" : "" + allVct.size())
				+ NEWLINE);
			if (allVct != null) {
				if (allVct.size() == 0) {
					ABRUtil.append(debugSb,"DerivefromLSEO No entities found for WWSEO" + NEWLINE);
				} else {
					//						 find those of specified type
					for (int i = 0; i < allVct.size(); i++) {
						DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
						EntityItem curritem = diffitem.getCurrentEntityItem();
						if (!diffitem.isDeleted()) {
							ABRUtil.append(debugSb,"XMLANNElem.DerivefromLSEO WWSEO checking[" + i + "]:New or Update" + diffitem.getKey()
								+ " SPECBID: " + PokUtils.getAttributeValue(curritem, "SPECBID", ", ", CHEAT, false) + NEWLINE);
							EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("SPECBID");
							if (fAtt != null && !fAtt.isSelected("11457")) {
								isfromLSEO = true;
								break;
							}
						}
					}
				}

			}	
		}	
		return isfromLSEO;
	}
	/***************************************************************************
	 * If LSEOBUNDLE.SPECBID is not equal to 11458 (Yes) , then derive from
	 * LSEO.
	 * 
	 * @param table
	 *            Hashtable that contain all the entities.
	 * @param availtype
	 *            AVAIL.AVAILTYPE
	 * @param debugSb
	 *            StringBuffer logo output.
	 * 
	 * If WWSEO.SPECBID is not equal to 'No' (11457), then derive from from LSEO
	 * attributes
	 */
//	private boolean isDerivefromLSEOBUNDLE(DiffEntity parentItem, StringBuffer debugSb) {
//		boolean isfromLSEO = false;
//		if (parentItem.getEntityType().equals("LSEOBUNDLE")) {
//			EntityItem curritem = parentItem.getCurrentEntityItem();
//			if (curritem != null) {
//				ABRUtil.append(debugSb,"XMLANNElem.DerivefromLSEO" + curritem.getKey() + " SPECBID: "
//					+ PokUtils.getAttributeValue(curritem, "SPECBID", ", ", CHEAT, false) + NEWLINE);
//				EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("SPECBID");
//				if (fAtt != null && fAtt.isSelected("11458")) {
//					isfromLSEO = true;
//				}
//			}
//		}
//		return isfromLSEO;
//	}
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
	private DiffEntity getCatlgor(Hashtable table, String availType, Vector mdlAudVct[], String attrVal2, StringBuffer debugSb) {
		// remove the check of the CATLGOR Audience 2012-08-24
		DiffEntity diffEntity = null;
		//Vector allVct = (Vector) table.get("CATLGOR");
		Vector allVct = getLseoAndLseobundleCatlgor(table, availType, debugSb);
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
			if (diffitem.isDeleted()) {
				ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking[" + i + "]: deleted " + diffitem.getKey() + " " + attrCode2 + ":"
					+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);				
				EANFlagAttribute fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
				if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
					diffEntity = diffitem; // keep looking for one that is not deleted
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

					ABRUtil.append(debugSb,"XMLCtryAudElem.getCatlgor checking[" + i + "]: prior " + diffitem.getKey() + " " + attrCode2 + ":"
						+ PokUtils.getAttributeFlagValue(prioritem, attrCode2) + NEWLINE);					
					fAtt2 = (EANFlagAttribute) prioritem.getAttribute(attrCode2);
					if (fAtt2 != null && fAtt2.isSelected(attrVal2)) {
						diffEntity = diffitem;
						break; //see if there is another that is current
					}							
				}
			}
		}

		return diffEntity;
	}

	/*******************************
	 * one for every  AVAIL.COUNTRYLIST where availtype='planned availbility(146)  include the avails (delete,new and update) 
	 *
	 */
	private static class CtryAudRecord  extends CtryRecord{

		
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

		/*********************
		 * 3.	<EARLIESTSHIPDATE>
		 * 	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		 * 	this avail cannot be deleted at this point
		 * 
		 *  * 	<PUBFROM>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned Availability (146).
		 3.	Empty (aka Null)
		 *  * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 2 .	Empty (aka Null)
		 
		 * 	 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = End of Service (151)
		 2.	Empty (aka Null)
		 */
		void setAllFields(DiffEntity parentDiffEntity, DiffEntity catlgorDiff, DiffEntity foAvailDiff, DiffEntity loAvailDiff, DiffEntity endAvailDiff, DiffEntity endMktAvailDiff, Hashtable table, EntityItem COFCATentity, DiffEntity relatedSLEORGGRP, String path,Element parent,
			 boolean isExistfinal, boolean compatModel, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + availDiff.getKey() + " " + getKey() + NEWLINE);

			availStatus = "0020";
            rfravailStatus = "0040";
            
			//ANNDATE is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
            String[] anndates  = deriveAnnDate(false, debugSb);
            String[] anndatesT1 = deriveAnnDate(true, debugSb);

			
			//ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
            String[] annnumbers = deriveAnnNumber(false, debugSb);
            String[] annnumbersT1 = deriveAnnNumber(true, debugSb);

		
			//FIRSTORDER - should be AVAIL.EFFECTIVEDATE where AVAILTYPE = 143 or null.
            String[] firstorders = deriveFIRSTORDER(parentDiffEntity, foAvailDiff, false, debugSb);
            String[] firstordersT1 = deriveFIRSTORDER(parentDiffEntity, foAvailDiff, true, debugSb);

         // PLANNEDAVAILABILITY is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
            String[] plannedavailabilitys = derivePlannedavailability(false, debugSb);
            String[] plannedavailabilitysT1 = derivePlannedavailability(true, debugSb);
            
			//LASTORDER is equal to PUBTO. 			
			
			
			//If the country in AVAIL.COUNTRYLIST was newly added or the AVAIL(first order) is newly added, then set Action UPDATE_ACTIVITY
			//If the country in AVAIL.COUNTRYLIST was deleted or AVAIL was deleted, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			//If the AVAIL was updated, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
//			if (isNewCountry(foAvailDiff, debugSb)) {
//				setAction(UPDATE_ACTIVITY);
//			}
			//set PUBFROM
			String[] pubfroms  = derivePubFrom(parentDiffEntity, catlgorDiff, foAvailDiff, false, debugSb);
			String[] pubfromsT1 = derivePubFrom(parentDiffEntity, catlgorDiff, foAvailDiff, true, debugSb);

			// set PUBTO
			String[] pubtos = derivePubTo(parentDiffEntity, catlgorDiff, loAvailDiff, false, debugSb);
			String[] pubtosT1 = derivePubTo(parentDiffEntity, catlgorDiff, loAvailDiff, true, debugSb);


			// set WDANNDATE
			String[] wdanndates = deriveWDANNDATE(endMktAvailDiff, false, debugSb);
			String[] wdanndatesT1 = deriveWDANNDATE(endMktAvailDiff, true, debugSb);


            //set LASTORDER
			String[] lastorders = deriveLastOrder(parentDiffEntity, loAvailDiff, false, debugSb);
			String[] lastordersT1 = deriveLastOrder(parentDiffEntity, loAvailDiff, true, debugSb);

        	//set EOSANNDATE
			String[] eosanndates = deriveEOSANNDATE(endAvailDiff, false, debugSb);
			String[] eosanndatesT1 = deriveEOSANNDATE(endAvailDiff, true, debugSb);

			// BH set ENDOFSERVICE
			String[] endofservices = deriveENDOFSERVICE(endAvailDiff, false, debugSb);
			String[] endofservicesT1 = deriveENDOFSERVICE(endAvailDiff, true, debugSb);
			
			
		    handleResults(anndates, anndatesT1, annnumbers, annnumbersT1,firstorders, firstordersT1,
	              plannedavailabilitys, plannedavailabilitysT1, pubfroms,pubfromsT1, pubtos,  pubtosT1,
	              wdanndates,  wdanndatesT1,  lastorders,  lastordersT1,  endofservices,  endofservicesT1,
	               eosanndates,  eosanndatesT1, country,  isExistfinal,  compatModel,  debugSb);
			
			
			//boolean SLEORGGRPChaned = false;
		
//			SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, COFCATentity, relatedSLEORGGRP, path, country, debugSb);			    
//				
//			if (SLEORGGRPChaned){
//				if(existfnialT2){
//					setAction(UPDATE_ACTIVITY);
//					setrfrAction(UPDATE_ACTIVITY);
//				}else{
//					setrfrAction(UPDATE_ACTIVITY);
//				}
//			}			
		}
//		d)	<ANNDATE>
//		LSEO.LSEOPUBDATEMTRGT
//		e)	<ANNNUMBER>
//		Empty (aka Null)
//		f)	<FIRSTORDER>
//		LSEO.LSEOPUBDATEMTRGT
//		g)	<PLANNEDAVAILABILITY>
//		The first applicable / available date is used
//		1.	LSEOBHCATLGOR-d: BHCATLGOR.PUBFROM
//		2.	LSEO. LSEOPUBDATEMTRGT (鈥淟SEO Publish Date 鈥�Target鈥�
//		h)	<PUBFROM>
//		The first applicable / available date is used
//		1.	LSEOBHCATLGOR-d: BHCATLGOR.PUBFROM
//		2.	LSEO. LSEOPUBDATEMTRGT (鈥淟SEO Publish Date 鈥�Target鈥�
//		i)	<PUBTO>
//		The first applicable / available date is used
//		1.	LSEOBHCATLGOR-d: BHCATLGOR.PUBTO
//		2.	LSEO. LSEOUNPUBDATEMTRGT (鈥淟SEO Unpublish Date 鈥�Target鈥� 
//		j)	<WDANNDATE>
//		LSEO.LSEOUNPUBDATEMTRGT
//		k)	<LASTORDER>
//		LSEO.LSEOUNPUBDATEMTRGT
//		l)	<EOSANNDATE>
//		Null or empty
//		m)	<ENDOFSERVICEDATE>
//		Null or empty 

		/**
		 * setAllFields for lseo avail
		 * @param parentDiffEntity
		 * @param parentItem
		 * @param catlgorDiff
		 * @param table
		 * @param COFCATentity
		 * @param relatedSLEORGGRP
		 * @param path
		 * @param parent
		 * @param debugSb
		 */
		void setAllLSEOFields(DiffEntity parentDiffEntity, DiffEntity catlgorDiff, Hashtable table, EntityItem COFCATentity, String path,  Element parent, boolean isExistfinal, boolean compatModel, StringBuffer debugSb){
			availStatus = "0020";
            rfravailStatus = "0040";
			
			ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for country is belong to LSEO " + (parentDiffEntity == null ? "null" : parentDiffEntity.getKey()) + ". catlgorDiff is " +  (catlgorDiff == null ? "null" : catlgorDiff.getKey())
				+ NEWLINE);
			
			String[] emptys = {CHEAT,CHEAT};
			String[] pubfroms = deriveLSEOPubFrom(parentDiffEntity,catlgorDiff,false,debugSb);
			String[] pubfromsT1 = deriveLSEOPubFrom(parentDiffEntity,catlgorDiff,true,debugSb);
			
			
			String[] firstorders = deriveLSEOFirstOrder(parentDiffEntity,false,debugSb);
			String[] firstordersT1= deriveLSEOFirstOrder(parentDiffEntity,true,debugSb);
			
			//ctryAudRec.plannedavailability = ctryAudRec.pubfrom;
			//ctryAudRec.anndate = ctryAudRec.firstorder;
			
			
			
			String[] lastorders = deriveLSEOLastOrder(parentDiffEntity,false,debugSb);
			String[] lastordersT1 = deriveLSEOLastOrder(parentDiffEntity,true,debugSb);
			
			
			String[] pubtos = deriveLSEOPubTo(parentDiffEntity,catlgorDiff,false,debugSb);
			String[] pubtosT1 = deriveLSEOPubTo(parentDiffEntity,catlgorDiff,true,debugSb);
			
			handleResults(firstorders, firstordersT1, emptys, emptys,firstorders, firstordersT1,
				pubfroms, pubfromsT1, pubfroms,pubfromsT1, pubtos,  pubtosT1,
				lastorders,  lastordersT1,  lastorders,  lastordersT1,  emptys,  emptys,
				emptys,  emptys, country,  isExistfinal,  compatModel,  debugSb);
			
//			boolean SLEORGGRPChaned	= SLEORGGRP.hasChanges(table, COFCATentity, parentDiffEntity, path, country, debugSb);			    	    
//			if (SLEORGGRPChaned) {
//				if(existfnialT2){
//					setAction(UPDATE_ACTIVITY);
//					setrfrAction(UPDATE_ACTIVITY);
//				}else{
//					setrfrAction(UPDATE_ACTIVITY);
//				}
//			}
		}
		/**
		 * deriveLSEOPubFrom
		 * @param parentDiff
		 * @param catlgorDiff
		 * @param findT1
		 * @param debugSb
		 * @return
		 */
		private String[] deriveLSEOPubFrom(DiffEntity parentDiff, DiffEntity catlgorDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveLSEOPubFrom catlgorDiff: " 
				+ (catlgorDiff == null ? "null" : catlgorDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1 get the catlgorDiff.PUBFROM 
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate, country, "PUBFROM", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//LSEO.LSEOPUBDATEMTRGT
				//     LSEOPUBDATEMTRGT
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "LSEOPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		/**
		 * deriveLSEOFirstOrder
		 * @param parentDiff
		 * @param findT1
		 * @param debugSb
		 * @return
		 */
		private String[] deriveLSEOFirstOrder(DiffEntity parentDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveLSEOFirstOrder parentDiff: " 
				+ (parentDiff == null ? "null" : parentDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//LSEO.LSEOPUBDATEMTRGT
				//     LSEOPUBDATEMTRGT
				//LSEO.LSEOPUBDATEMTRGT
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "LSEOPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		/**
		 * deriveLSEOLastOrder
		 * @param parentDiff
		 * @param findT1
		 * @param debugSb
		 * @return
		 */
		private String[] deriveLSEOLastOrder(DiffEntity parentDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveLSEOLastOrder parentDiff: " 
				+ (parentDiff == null ? "null" : parentDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//LSEO.LSEOUNPUBDATEMTRGT
				//     LSEOUNPUBDATEMTRGT
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "LSEOUNPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
		/**
		 * deriveLSEOPubTo
		 * @param parentDiff
		 * @param catlgorDiff
		 * @param findT1
		 * @param debugSb
		 * @return
		 */
		private String[] deriveLSEOPubTo(DiffEntity parentDiff, DiffEntity catlgorDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveLSEOPubTo catlgorDiff: " 
				+ (catlgorDiff == null ? "null" : catlgorDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1 get the catlgorDiff.PUBFROM 
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate, country, "PUBTO", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//LSEO.LSEOPUBDATEMTRGT
				//     LSEOUNPUBDATEMTRGT 
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "LSEOUNPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}
//		/****************************
//		 * all the new added country that in First order Avail set the action is update.
//		 return whether the country is new.
//		 */
//
//		private boolean isNewCountry(DiffEntity diffEntity, StringBuffer debugSb) {
//
//			boolean isNew = false;
//			if (diffEntity != null && diffEntity.isNew()) {
//				isNew = true;
//				ABRUtil.append(debugSb,"XMLAVAILElem.setAllFields isNewAvail" + diffEntity.getKey() + NEWLINE);
//			} else if (diffEntity != null && !diffEntity.isDeleted()) {
//				EANFlagAttribute fAtt2 = null;
//				EANFlagAttribute fAtt1 = null;
//				EntityItem currentitem = diffEntity.getCurrentEntityItem();
//				EntityItem prioritem = diffEntity.getPriorEntityItem();
//				if (currentitem != null) {
//					fAtt2 = (EANFlagAttribute) currentitem.getAttribute("COUNTRYLIST");
//				}
//				if (prioritem != null) {
//					fAtt1 = (EANFlagAttribute) prioritem.getAttribute("COUNTRYLIST");
//				}
//				if (fAtt1 != null && !fAtt1.isSelected(country) && fAtt2 != null && fAtt2.isSelected(country)) {
//					isNew = true;
//					ABRUtil.append(debugSb,"XMLAVAILElem.setAllFields isNewCountry" + diffEntity.getKey() + NEWLINE);
//				}
//			}
//			return isNew;
//
//		}

		/****************************
		 * <ENDOFSERVICEDATE>
		 The first applicable / available date is used.
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = End of Service (151)
		 2.	Empty (aka Null)

		 */
		private String[] deriveENDOFSERVICE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.deriveEndOfService " + " eofAvailDiff: "
				+ (endAvailDiff == null ? "null" : endAvailDiff.getKey()) + " findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];			
			String[] temps   = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// try to get it from the lastorder avail
				temps = AvailUtil.getAvailAttributeDate(findT1, endAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/****************************
		 * 	<PUBTO> 
		 The first applicable / available date is used.
		 1.	MDLCATLGOR-d: CATLGOR.PUBTO
		 2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		 3 .	Empty (aka Null)
		 
		 */
		private String[] derivePubTo(DiffEntity parentDiffEntity, DiffEntity catlgorDiff, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePubTo " + " loAvailDiff: " + (loAvailDiff == null ? "null" : loAvailDiff.getKey())
				+ " findT1:" + findT1 + NEWLINE);
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];			
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1.LSEOCATLGOR-d: CATLGOR.PUBTO
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiffEntity, catlgorDiff, thedate, rfrthedate, country, "PUBTO", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2.AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/****************************
		 * 	<PUBFROM>
		 0. LSEOCATLGOR-d: CATLGOR.PUBFROM
		 1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		 2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned Availability (146).
		 3.	Empty (aka Null)

		 */
		private String[] derivePubFrom(DiffEntity parentDiff, DiffEntity catlgorDiff, DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			ABRUtil.append(debugSb,"XMLAVAILElem.derivePubFrom availDiff: " + availDiff.getKey() + " foAvailDiff: "
				+ (foAvailDiff == null ? "null" : foAvailDiff.getKey()) + "findT1:" + findT1 + NEWLINE);
			
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String[] sReturn = new String[2];
			String[] temps = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1 get the catlgorDiff.PUBFROM 
				temps = AvailUtil.getBHcatlgorAttributeDate(findT1, parentDiff, catlgorDiff, thedate, rfrthedate, country, "PUBFROM", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2 get the foAvailDiff.EFFECTIVEDATE
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
				//4 get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//5. LSEO.LSEOPUBDATEMTRGT
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiff, thedate, rfrthedate, "LSEOPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			sReturn[0]= thedate;
			sReturn[1]= rfrthedate;
			return sReturn;
		}

		/****************************
		 * 	<ANNNUMBER>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNNUMBER for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
		 2.	Empty (aka Null)

		 */
		private String[] deriveAnnNumber(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;			
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveAnnNumber availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);	
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get the AVAIL(146)--> ANNOUNCEMENT.ANNDATE
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNNUMBER", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}		
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}

		/****************************
		 * 	<ANNDATE>
		 1.	ANNNUMBER is ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = Planned Availability(146).
		 2.	Empty (aka Null)

		 */
		private String[] deriveAnnDate(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveAnnDate availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			//1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;	
		}

		/****************************
		 * 	<FIRSTORDER>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = First Order
		 2.	<ANNDATE>

		 */
		private String[] deriveFIRSTORDER(DiffEntity parentDiffEntity, DiffEntity foAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.deriveFIRSTORDER availDiff: " + (availDiff == null ? "null" : availDiff.getKey()) 
				 + "foAvailDiff " + foAvailDiff + (foAvailDiff == null ? "null" : foAvailDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//1.get the first order avail effective date
				temps = AvailUtil.getAvailAttributeDate(findT1, foAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//2. LSEOAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Planned Availability"
				//find current derivation
				temps = AvailUtil.getAvailAnnAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "ANNDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//3.get the plAvailDiff.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//4. LSEO.LSEOPUBDATEMTRGT
				temps = AvailUtil.getParentAttributeDate(findT1, parentDiffEntity, thedate, rfrthedate, "LSEOPUBDATEMTRGT", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}

			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;
		}
		
		

		/****************************
		 * 	<PLANNEDAVAILABILITY>
		 1.	derivePlannedavailability is AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		 2.	Empty (aka Null)

		 */
		private String[] derivePlannedavailability(boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];			
			
			String temps[] = new String[2];
			ABRUtil.append(debugSb,"XMLLSEOAVAILElembh1.derivePlannedavailability availDiff: " + (availDiff == null ? "null" : availDiff.getKey())
				 + "findT1:" + findT1 + NEWLINE);
			//1 try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE =   Planned Availability   (146).
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {                                    
				temps = AvailUtil.getAvailAttributeDate(findT1, availDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0] = thedate;
			returns[1] = rfrthedate;
			return returns;	
		}

		/****************************
		 * 	<EOSANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = End of Service(151) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Discontinuance of service" (13)
		 2.	Empty (aka Null)

		 */
		private String[] deriveEOSANNDATE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =13
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endAvailDiff, thedate, rfrthedate, country, "13", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
		}

		/****************************
		 * 	<WDANNDATE>
		 1.	MODELAVAIL-d: AVAILANNA-d: ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE = "Last Order" (149) and ANNOUNCEMENT.ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		 2.	Empty (aka Null)

		 */
		private String[] deriveWDANNDATE(DiffEntity endMktAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				// get the ANNOUNCEMENT.ANNDATE where ANNTYPE =14
				temps = AvailUtil.getAvailAnnDateByAnntype(findT1, endMktAvailDiff, thedate, rfrthedate, country, "14", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
		}
		/***********************************************************************
		 * <LASTORDER> The first applicable / available date is used. 1.
		 * AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149) 2 .
		 * Empty (aka Null)
		 * 
		 */
		private String[] deriveLastOrder(DiffEntity parentDiffEntity, DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb) {
			String thedate = CHEAT;
			String rfrthedate = CHEAT;
			String returns[] = new String[2];
			String temps[] = new String[2];
			
			if (CHEAT.equals(thedate) || CHEAT.equals(rfrthedate)) {
				//get AVAIL.EFFECTIVEDATE
				temps = AvailUtil.getAvailAttributeDate(findT1, loAvailDiff, thedate, rfrthedate, country, "EFFECTIVEDATE", debugSb);
				thedate = temps[0];
				rfrthedate = temps[1];
			}
			
			returns[0]= thedate;
			returns[1]= rfrthedate;
			return returns;
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
