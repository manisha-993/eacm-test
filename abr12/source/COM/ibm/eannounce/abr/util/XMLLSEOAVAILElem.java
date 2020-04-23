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
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
* Class used to hold info and structure to be generated for the xml feed
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
// $Log: XMLLSEOAVAILElem.java,v $
// Revision 1.6  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.5  2011/10/31 14:12:58  guobin
// comment out the release momery AVAIL and ANNOUNCE from HashTable
//
// Revision 1.4  2010/09/26 08:21:56  guobin
// // check annVct and availVct is not null
//
// Revision 1.3  2010/09/03 09:50:10  yang
// check annVct and availVct is not null
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

public class XMLLSEOAVAILElem extends XMLElem
{

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
    public XMLLSEOAVAILElem()
    {
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
    public void addElements(Database dbCurrent,Hashtable table, Document document, Element parent,
        DiffEntity parentItem, StringBuffer debugSb)
    throws
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        java.io.IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	boolean isfromModel = isDerivefromLSEO(table,parentItem,debugSb);
      	if (isfromModel == true ){
    		createNodeFromLSEO(dbCurrent, document, parent, parentItem, debugSb);
      	}else{
//      	 get all AVAILs where AVAILTYPE="Planned Availability" (146) - some may be deleted
    	    Vector plnAvlVct = getPlannedAvails(table, debugSb);

    		if (plnAvlVct.size()>0){ // must have planned avail for any of this, wayne said there will always be at least 1
    			// get model audience values, t2[0] current, t1[1] prior
    			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
    			// AVAILb to have US at T2
    			TreeMap ctryAudElemMap = new TreeMap();
    			for (int i=0; i<plnAvlVct.size(); i++){
    				DiffEntity availDiff = (DiffEntity)plnAvlVct.elementAt(i);
    				buildCtryAudRecs(ctryAudElemMap, availDiff, debugSb);
    			}// end each planned avail

    			// output the elements
    			Collection ctryrecs = ctryAudElemMap.values();
    			Iterator itr = ctryrecs.iterator();
    			while(itr.hasNext()) {
    				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
    				//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
    				if (!ctryAudRec.isDeleted()){
    					// find firstorder avail for this country
    					DiffEntity foAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "143",
    						"COUNTRYLIST", ctryAudRec.getCountry(), debugSb);
    					// find lastorder avail for this country
    					DiffEntity loAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "149",
    						"COUNTRYLIST", ctryAudRec.getCountry(), debugSb);
    					DiffEntity endAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "151",
    						"COUNTRYLIST", ctryAudRec.getCountry(), debugSb);
    					
                        // add other info now
    					ctryAudRec.setAllFields(foAvailDiff, loAvailDiff, endAvailDiff, debugSb);
    				}
    				if (ctryAudRec.isDisplayable()){
    					createNodeSet(document, parent, ctryAudRec, debugSb);
    				}else{
                		ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for "+ctryAudRec+NEWLINE);
    				}
    				ctryAudRec.dereference();
    			}

    			// release memory
    			ctryAudElemMap.clear();
//    			Vector annVct = (Vector)table.get("ANNOUNCEMENT");
//    			Vector availVct = (Vector)table.get("AVAIL");
//    			if (annVct != null){
//    				annVct.clear();
//    			}
//    			if (availVct != null){    			
//    				availVct.clear();
//    			}
    		}else{
    			ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no planned AVAILs found"+NEWLINE);
    		}
      	}
    }

	/**
	 * *  Class used to hold info and structure to be generated for the xml feed
* for abrs.
*    * Constructor for <AVAILABILITYLIST> elements
    * <AVAILABILITYLIST> 2	LSEO - for each country in COUNTRYLIST where  WWSEO.SPECBID != 'No' (11457), 
    * <AVIAILABILITYELEMENT>	 3		
	*   <AVAILABILITYACTION>		    4  LSEO	CountryAction
	*   <STATUS>	                    
	*   <COUNTRY>	</COUNTRY>		4	LSEO	COUNTRYLIST - Flag Description Class
	* 	<PUBFROM>	</PUBFROM>		4	LSEO/	LSEOPUBDATEMTRGT 
	* 	<PUBTO>	</PUBTO>			4	LSEO/	LSEOUNPUBDATEMTRGT 
	* 	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>			4	null	
	* </AVIAILABILITYELEMENT>		3
	* </AVAILABILITYLIST>		2
*
	 * @param dbCurrent
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
	private void createNodeFromLSEO(Database dbCurrent, Document document, Element parent, DiffEntity parentItem, StringBuffer debugSb) throws SQLException, MiddlewareException {

        
        EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem prioritem = parentItem.getPriorEntityItem();
		TreeMap ctryAudElemMap = new TreeMap();
		
		if (parentItem.isNew()){ // If the AVAIL was deleted, set Action = Delete
			
			EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt "+
						PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST") +NEWLINE);
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
								ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for New "+parentItem.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for New:"+parentItem.getKey()+" rec: "+
									ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
			}
		 }else if(!parentItem.isDeleted()){
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
				//put all current country into currvSet.
				EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt "+
							PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+NEWLINE);
				if (ctryAtt!=null){
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++){
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							currSet.add(ctryVal);
							}
						}
					}
				
//				put all prior country  into currvSet.
			    ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
				ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt "+
							PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+NEWLINE);
				if (ctryAtt!=null){
					MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
					for (int im = 0; im < mfArray.length; im++){
						// get selection
						if (mfArray[im].isSelected()) {
							String ctryVal = mfArray[im].getFlagCode();
							prevSet.add(ctryVal);
							}
						}
					}
//				 look for changes in country
				Iterator itr = currSet.iterator();
				while(itr.hasNext()) {
					String ctryVal = (String) itr.next();
					if(!prevSet.contains(ctryVal))	{ // If a pair of CountryAudience was added, set that row's Action = Update
						//create crossproduct between new ctry and current audience for this item
						if (ctryAudElemMap.containsKey(ctryVal)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on "+parentItem.getKey()+
							" "+ctryVal+" already exists, replacing orig "+rec+NEWLINE);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(null,ctryVal);
							ctryAudRec.setAction(UPDATE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for added ctry:"+parentItem.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
						}
					}else{
						// ctryaudience already existed but something else may have changed
						if (ctryAudElemMap.containsKey(ctryVal)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry on "+parentItem.getKey()+
								" "+ctryVal+" already exists, keeping orig "+rec+NEWLINE);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(null,ctryVal);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for existing ctry:"+parentItem.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
						}
					
					}
				}
				itr = prevSet.iterator();
				while(itr.hasNext()) {
					String ctryVal = (String) itr.next();
					if(!currSet.contains(ctryVal))	{ //If a pair of countryaudience was deleted, set that row's Action = Delete
						//create crossproduct between deleted ctry and previous audience for this item
						if (ctryAudElemMap.containsKey(ctryVal)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(ctryVal);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on "+parentItem.getKey()+
								" "+ctryVal+" already exists, keeping orig "+rec+NEWLINE);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(null, ctryVal);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted ctry:"+parentItem.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
						}	
				
					}
			    }
			}		
		Collection ctryrecs = ctryAudElemMap.values();
		Iterator itr = ctryrecs.iterator();
			while(itr.hasNext()) {
				
				CtryAudRecord ctryAudRec = (CtryAudRecord) itr.next();
				if (!ctryAudRec.isDeleted()){
					//Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
						// add other info now
					
					
//					 get STATUS
					if (curritem != null){
						ctryAudRec.availStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
					}
					
					if(curritem != null){
						ctryAudRec.pubfrom = PokUtils.getAttributeValue(curritem, "LSEOPUBDATEMTRGT","", CHEAT, false);
					}
					String prevpubfrom = CHEAT;
					if (prioritem != null){
						prevpubfrom = PokUtils.getAttributeValue(prioritem, "LSEOPUBDATEMTRGT",", ", CHEAT, false);
					}
					ABRUtil.append(debugSb,"CtryAudRecord.setAllFields pubfrom: "+ctryAudRec.pubfrom+" prevdate: "+prevpubfrom+NEWLINE);

					// if diff, set action
					if (!prevpubfrom.equals(ctryAudRec.pubfrom)){
						ctryAudRec.setAction(UPDATE_ACTIVITY);
					}
					// set pubto
					if(curritem != null){
						ctryAudRec.pubto = PokUtils.getAttributeValue(curritem, "LSEOUNPUBDATEMTRGT","", CHEAT, false);
					}
					String prevpubto = CHEAT;
					if (prioritem != null){
						prevpubto = PokUtils.getAttributeValue(prioritem, "LSEOUNPUBDATEMTRGT",", ", CHEAT, false);
					}
					ABRUtil.append(debugSb,"CtryAudRecord.setAllFields pubto: "+ctryAudRec.pubto+" prevdate: "+prevpubto+NEWLINE);

					// if diff, set action
					if (!prevpubto.equals(ctryAudRec.pubto)){
						ctryAudRec.setAction(UPDATE_ACTIVITY);
					} 		
				}
				if (ctryAudRec.isDisplayable()){
					createNodeSet(document, parent, ctryAudRec, debugSb);
				}else{
            		ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for "+ctryAudRec.country+NEWLINE);
				}
				ctryAudRec.dereference();
		}
		ctryAudElemMap.clear();
	}
    /********************
    * create the nodes for this ctry|audience record
    */
 	private void createNodeSet(Document document, Element parent,
        CtryAudRecord ctryAudRec,StringBuffer debugSb)
    {
		Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
		addXMLAttrs(elem);
		parent.appendChild(elem);

		// add child nodes
		Element child = (Element) document.createElement("AVAILABILITYACTION");
		child.appendChild(document.createTextNode(""+ctryAudRec.getAction()));
		elem.appendChild(child);
		child = (Element) document.createElement("STATUS");
		child.appendChild(document.createTextNode(""+ctryAudRec.getAvailStatus()));
		elem.appendChild(child);
		child = (Element) document.createElement("COUNTRY_FC");
		child.appendChild(document.createTextNode(""+ctryAudRec.getCountry()));
		elem.appendChild(child);
		
		//child = (Element) document.createElement("EARLIESTSHIPDATE");
		//child.appendChild(document.createTextNode(""+ctryAudRec.getShipDate()));
		//elem.appendChild(child);
		child = (Element) document.createElement("PUBFROM");
		child.appendChild(document.createTextNode(""+ctryAudRec.getPubFrom()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBTO");
		child.appendChild(document.createTextNode(""+ctryAudRec.getPubTo()));
		elem.appendChild(child);
		child = (Element) document.createElement("ENDOFSERVICEDATE");
		child.appendChild(document.createTextNode(""+ctryAudRec.getEndOfService()));
		elem.appendChild(child);
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
  
    private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, StringBuffer debugSb){


		ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs "+availDiff.getKey()+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (availDiff.isDeleted()){ // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt "+
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
								ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for deleted "+availDiff.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(DELETE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for deleted:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
			}
		}else if (availDiff.isNew()){ //If the AVAIL was added or updated, set Action = Update
			// mark all records as update
			EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt "+
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
								ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for new "+availDiff.getKey()+
									" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
								rec.setUpdateAvail(availDiff);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for new:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey() + NEWLINE);
							}
						}
					}
				}
		}else{// else if one country in the countrylist has changed, update this row to update!
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
			// get current set of countries
			EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt "+
				PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+
			    PokUtils.getAttributeFlagValue(curritem, "ANNCODENAME") +NEWLINE);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						currSet.add(mfArray[i].getFlagCode());
					}  // metaflag is selected
				}// end of flagcodes
			}

			// get prev set of countries
			fAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt " +
					PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+
			        PokUtils.getAttributeFlagValue(prioritem, "ANNCODENAME") +NEWLINE);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						prevSet.add(mfArray[i].getFlagCode());
					}  // metaflag is selected
				}// end of flagcodes
			}

			// look for changes in country
			Iterator itr = currSet.iterator();
			while(itr.hasNext()) {
				String ctryVal = (String) itr.next();
				if(!prevSet.contains(ctryVal))	{ // If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
				
						String mapkey = ctryVal;						
						if (ctryAudElemMap.containsKey(mapkey)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for added ctry on "+availDiff.getKey()+
							" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
							rec.setUpdateAvail(availDiff);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
							ctryAudRec.setAction(UPDATE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for added ctry:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey() + NEWLINE);
						}
				}else{
					// BH this country has already exist, put into ctryaudrec, but don't udpate Action to 'update'/'delete'.
					String mapkey = ctryVal;
					if (ctryAudElemMap.containsKey(mapkey)){
						CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
						ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for existing ctry but new aud on "+availDiff.getKey()+
							" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
					}else{
						CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
						ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
						ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for existing ctry:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey() + NEWLINE);
					}
				}
			}//end of currset while(itr.hasNext())
			itr = prevSet.iterator();
			while(itr.hasNext()) {
				String ctryVal = (String) itr.next();
				if(!currSet.contains(ctryVal))	{ //If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
					//create crossproduct between deleted ctry and previous audience for this item	
						String mapkey = ctryVal;
						if (ctryAudElemMap.containsKey(mapkey)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
							ABRUtil.append(debugSb,"WARNING buildCtryAudRecs for delete ctry on "+availDiff.getKey()+
								" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							ABRUtil.append(debugSb,"XMLCtryAudElem.buildCtryAudRecs for delete ctry:"+availDiff.getKey()+" rec: "+
							ctryAudRec.getKey() + NEWLINE);
						}
					}
		
			}
		} // end avail existed at both t1 and t2
	}
    /********************
    * get planned avails - availtype cant be changed
    */
    private Vector getPlannedAvails(Hashtable table, StringBuffer debugSb)
    {
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector)table.get("AVAIL");

        ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL"+
            " allVct.size:"+(allVct==null?"null":""+allVct.size())+NEWLINE);
        if (allVct==null){
			return avlVct;
		}

		// find those of specified type
		for (int i=0; i< allVct.size(); i++){
			DiffEntity diffitem = (DiffEntity)allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()){
				ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails checking["+i+"]: deleted "+diffitem.getKey()+" AVAILTYPE: "+
					PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")+NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute("AVAILTYPE");
				if (fAtt!= null && fAtt.isSelected("146")){
					avlVct.add(diffitem);
				}
			}else{
				ABRUtil.append(debugSb,"XMLCtryAudElem.getPlannedAvails checking["+i+"]:"+diffitem.getKey()+" AVAILTYPE: "+
					PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")+NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("AVAILTYPE");
				if (fAtt!= null && fAtt.isSelected("146")){
					avlVct.add(diffitem);
				}
			}
		}

		return avlVct;
	}
    /********************
    * get entity with specified values - should only be one
    * could be two if one was deleted and one was added, but the added one will override and be an 'update'
    */
    private DiffEntity getEntityForAttrs(Hashtable table, String etype, String attrCode, String attrVal,
    	String attrCode2, String attrVal2,StringBuffer debugSb)
    {
		DiffEntity diffEntity = null;
		Vector allVct = (Vector)table.get(etype);

		ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs looking for "+attrCode+":"+attrVal+" and "+
			attrCode2+":"+attrVal2+" in "+etype+" allVct.size:"+(allVct==null?"null":""+allVct.size())+NEWLINE);
        if (allVct==null){
			return diffEntity;
		}
		// find those of specified type
		for (int i=0; i< allVct.size(); i++){
			DiffEntity diffitem = (DiffEntity)allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()){
				ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking["+i+"]: deleted "+diffitem.getKey()+
					" "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
					" "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode2)+NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
				if (fAtt!= null && fAtt.isSelected(attrVal)){
					fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
					if (fAtt!= null && fAtt.isSelected(attrVal2)){
						diffEntity = diffitem; // keep looking for one that is not deleted
					}
				}
			}else{
				if (diffitem.isNew()){
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking["+i+"]: new "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem, attrCode2)+NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
					if (fAtt!= null && fAtt.isSelected(attrVal)){
						fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode2);
						if (fAtt!= null && fAtt.isSelected(attrVal2)){
							diffEntity = diffitem;
							break;
						}
					}
				}else{
					// must check to see if the prior item had a match too
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking["+i+"]: current "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem, attrCode2)+NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
					if (fAtt!= null && fAtt.isSelected(attrVal)){
						fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode2);
						if (fAtt!= null && fAtt.isSelected(attrVal2)){
							diffEntity = diffitem;
							break;
						}
					}
					ABRUtil.append(debugSb,"XMLCtryAudElem.getEntityForAttrs checking["+i+"]: prior "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode2)+NEWLINE);
					fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
					if (fAtt!= null && fAtt.isSelected(attrVal)){
						fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
						if (fAtt!= null && fAtt.isSelected(attrVal2)){
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
				Vector allVct = (Vector) table.get("WWSEO");
				ABRUtil.append(debugSb,"DerivefromLSEO looking for WWSEO.SPECBID. allVct.size:"
					+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
				if (allVct != null) {
					if (allVct.size()==0){						
						ABRUtil.append(debugSb,"DerivefromLSEO No entities found for WWSEO" + NEWLINE);
					}else{					
//						 find those of specified type
						for (int i = 0; i < allVct.size(); i++) {
							DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
							EntityItem curritem = diffitem.getCurrentEntityItem();
							if (!diffitem.isDeleted()) {
								ABRUtil.append(debugSb,"XMLANNElem.DerivefromLSEO WWSEO checking[" + i + "]:New or Update"
									+ diffitem.getKey() + " SPECBID: " + PokUtils.getAttributeValue(curritem, "SPECBID",", ", CHEAT, false)
									+ NEWLINE);
								EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("SPECBID");
								if (fAtt != null && !fAtt.isSelected("11457")) {
									isfromLSEO = true;
									break;
								}
							}
					 	}
					}

		}
		return isfromLSEO;
	}
    
    
	/*******************************
	* one for every  AVAIL.COUNTRYLIST where availtype='planned availbility(146)  include the avails (delete,new and update) 
	*
	*/
	private static class CtryAudRecord {
		private String action = CHEAT;
		private String country;
		//private String earliestshipdate = CHEAT;// AVAIL
		private String availStatus = CHEAT; //AVAIL
		private String pubfrom = CHEAT; 		//AVAIL/	PubFrom
		private String pubto = CHEAT; 			//AVAIL/	PubTo
		private String endofservice = CHEAT; //ENDOFSERVICE
		private DiffEntity availDiff;

		boolean isDisplayable() {return !action.equals(CHEAT);} // only display those with filled in actions

		CtryAudRecord(DiffEntity diffitem,String ctry){
			country = ctry;
			availDiff = diffitem;
		}
		void setAction(String s) {	action = s;	}
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
         2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = ��Planned Availability�� (146).
         3.	Empty (aka Null)
		*  * 	<PUBTO> 
		The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
        2 .	Empty (aka Null)
	
		* 	 * <ENDOFSERVICEDATE>
        The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = ��End of Service�� (151)
        2.	Empty (aka Null)
		*/
		void setAllFields(DiffEntity foAvailDiff, DiffEntity loAvailDiff, DiffEntity endAvailDiff, StringBuffer debugSb)
		{
            ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: "+availDiff.getKey()+" "+getKey()+NEWLINE);

			EntityItem curritem = availDiff.getCurrentEntityItem();
			EntityItem previtem = availDiff.getPriorEntityItem();

			// set EARLIESTSHIPDATE
			// get current value
//			if (curritem != null){
//				earliestshipdate = PokUtils.getAttributeValue(curritem, "EFFECTIVEDATE",", ", CHEAT, false);
//				if (earliestshipdate==null){
//				earliestshipdate = CHEAT;
//				}	
//			}
			// get priorvalue if it exists
//			String prevdate = CHEAT;
//			if (previtem != null){
//				prevdate = PokUtils.getAttributeValue(previtem, "EFFECTIVEDATE",", ", CHEAT, false);
//				if (prevdate==null){
//					prevdate = CHEAT;
//				}
//			}
//			ABRUtil.append(debugSb,"CtryAudRecord.setAllFields curshipdate: "+earliestshipdate+" prevdate: "+prevdate+NEWLINE);

			// if diff, set action
//			if (!prevdate.equals(earliestshipdate)){
//				setAction(UPDATE_ACTIVITY);
//			}
			// get STATUS
			if (curritem != null){
			    availStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
			    if (availStatus==null){
				    availStatus = CHEAT;
			    }
			}
			// get priorvalue if it exists
			String prevStatus = CHEAT;
			if (previtem != null){
				prevStatus = PokUtils.getAttributeFlagValue(previtem, "STATUS");
				if (prevStatus==null){
					prevStatus = CHEAT;
				}
			}
			ABRUtil.append(debugSb,"CtryAudRecord.setAllFields curstatus: "+availStatus+" prevstatus: "+prevStatus+NEWLINE);

			// if diff, set action
			if (!prevStatus.equals(availStatus)){
				setAction(UPDATE_ACTIVITY);
			}

			//If the country in AVAIL.COUNTRYLIST was newly added or the AVAIL(first order) is newly added, then set Action UPDATE_ACTIVITY
			//If the country in AVAIL.COUNTRYLIST was deleted or AVAIL was deleted, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			//If the AVAIL was updated, but get the current pubfrom is equal to the prior one, then don't set Action UPDATE_ACTIVITY
			if(isNewCountry(foAvailDiff,debugSb)){
				setAction(UPDATE_ACTIVITY);
			}
            //set PUBFROM
			pubfrom = derivePubFrom(foAvailDiff, false, debugSb);
			String pubfromT1 = derivePubFrom(foAvailDiff, true, debugSb);

			if (!pubfrom.equals(pubfromT1)){
				setAction(UPDATE_ACTIVITY);
			}
			// set PUBTO
			pubto = derivePubTo(loAvailDiff, false,debugSb);
			String pubtoT1 = derivePubTo(loAvailDiff, true,debugSb);
			if (!pubto.equals(pubtoT1)){
				setAction(UPDATE_ACTIVITY);
			}
			// BH set ENDOFSERVICE
			endofservice = deriveENDOFSERVICE(endAvailDiff, false,debugSb);
			String endofserviceT1 = deriveENDOFSERVICE(endAvailDiff, true,debugSb);
			if (!endofservice.equals(endofserviceT1)){
				setAction(UPDATE_ACTIVITY);
			}
			ABRUtil.append(debugSb,"CtryAudRecord.setAllFields pubfrom: "+pubfrom+" pubto: "+pubto + " endofservice:" + endofservice + NEWLINE);
		}
		/****************************
		 * all the new added country that in First order Avail set the action is update.
           return whether the country is new.
		 */
		
		private boolean isNewCountry(DiffEntity diffEntity,StringBuffer debugSb){
			
			boolean isNew = false;
			if (diffEntity!=null && diffEntity.isNew()){
				isNew = true;
				ABRUtil.append(debugSb,"CtryAudRecord.setAllFields isNewAvail" + diffEntity.getKey() + NEWLINE);
			}else if (diffEntity!=null && !diffEntity.isDeleted()){				
				EANFlagAttribute fAtt2 = null; 
				EANFlagAttribute fAtt1 = null;
				EntityItem currentitem = diffEntity.getCurrentEntityItem();
				EntityItem prioritem = diffEntity.getPriorEntityItem();	
				if (currentitem != null){
				   fAtt2 = (EANFlagAttribute)currentitem.getAttribute("COUNTRYLIST");
				}
				if (prioritem != null){
					fAtt1 = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
				}				 
				if (fAtt1 != null && !fAtt1.isSelected(country)&& fAtt2 != null && fAtt2.isSelected(country)){
					isNew = true;
					ABRUtil.append(debugSb,"CtryAudRecord.setAllFields isNewCountry" + diffEntity.getKey() + NEWLINE);
				}
			}
			return isNew;
			
		}
		/****************************
		 * <ENDOFSERVICEDATE>
        The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = ��End of Service�� (151)
        2.	Empty (aka Null)

		*/
		private String deriveENDOFSERVICE(DiffEntity endAvailDiff, boolean findT1, StringBuffer debugSb)
		{
			ABRUtil.append(debugSb,"CtryAudRecord.deriveEndOfService "+
				" eofAvailDiff: "+(endAvailDiff==null?"null":endAvailDiff.getKey())+
				" findT1:"+findT1+NEWLINE);

			String thedate = CHEAT;
			if (findT1){ // find previous derivation
                 // try to get it from the lastorder avail
					if (endAvailDiff != null && !endAvailDiff.isNew()){
						EntityItem item = endAvailDiff.getPriorEntityItem();
						if(item!=null){
							EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
							if (fAtt!= null && fAtt.isSelected(country)){
								thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						
							ABRUtil.append(debugSb,"CtryAudRecord.deriveEndOfService eofavail thedate: "+thedate+
								" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
						  }
						}else{
							ABRUtil.append(debugSb,"CtryAudRecord.deriveEndOfService eofAvail priorEnityitem: " + item +NEWLINE);	
						}
                      }
						
			}else{ //find current derivation
				
					// try to get it from the lastorder avail
					if (endAvailDiff != null && !endAvailDiff.isDeleted()){
						EntityItem item = endAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						ABRUtil.append(debugSb,"CtryAudRecord.deriveEndOfService eofavail thedate: "+thedate+
							" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}


			return thedate;
		}
		/****************************
		 * 	<PUBTO> 
		The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
        2 .	Empty (aka Null)
 
		*/
		private String derivePubTo(DiffEntity loAvailDiff,
			boolean findT1, StringBuffer debugSb)
		{
			ABRUtil.append(debugSb,"CtryAudRecord.derivePubTo "+
				" loAvailDiff: "+(loAvailDiff==null?"null":loAvailDiff.getKey())+
				" findT1:"+findT1+NEWLINE);

			String thedate = CHEAT;
			if (findT1){ // find previous derivation
                 // try to get it from the lastorder avail
					if (loAvailDiff != null && !loAvailDiff.isNew()){
						EntityItem item = loAvailDiff.getPriorEntityItem();
						if (item!=null){
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						ABRUtil.append(debugSb,"CtryAudRecord.derivePubTo loavail thedate: "+thedate+
							" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					    }else{
					    	ABRUtil.append(debugSb,"CtryAudRecord.derivePubTo loavail priorEnityitem: " + item +NEWLINE);
					    }
					}
			}else{ //find current derivation
				
					// try to get it from the lastorder avail
					if (loAvailDiff != null && !loAvailDiff.isDeleted()){
						EntityItem item = loAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						ABRUtil.append(debugSb,"CtryAudRecord.derivePubTo loavail thedate: "+thedate+
							" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}


			return thedate;
		}
		/****************************
		 * 	<PUBFROM>
		The first applicable / available date is used.
         1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
         2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = ��Planned Availability�� (146).
         3.	Empty (aka Null)

		*/
		private String derivePubFrom(DiffEntity foAvailDiff,boolean findT1, StringBuffer debugSb)	{
			String thedate = CHEAT;
			ABRUtil.append(debugSb,"CtryAudRecord.derivePubFrom availDiff: "+availDiff.getKey()+
				" foAvailDiff: "+(foAvailDiff==null?"null":foAvailDiff.getKey())+ "findT1:"+findT1+NEWLINE);

			if (findT1){ // find previous derivation
				 //find current derivation
				// try to get it from the firstorder avail
				if (foAvailDiff != null && !foAvailDiff.isNew()){
					EntityItem item = foAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);				
					}
					ABRUtil.append(debugSb,"CtryAudRecord.derivePubFrom foavail thedate: "+thedate +NEWLINE);
					}	
			
				if (CHEAT.equals(thedate)){
					// try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = ��Planned Availability�� (146).
					if(!availDiff.isNew() && availDiff != null){
						EntityItem item = availDiff.getPriorEntityItem();
						Vector relatorVec = item.getDownLink();
						 for (int ii=0; ii<relatorVec.size(); ii++){
	                        	EntityItem availanna = (EntityItem)relatorVec.elementAt(ii);
	                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
	                        		Vector annVct = availanna.getDownLink();
	                        		EntityItem anna = (EntityItem)annVct.elementAt(0);
	                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
	                                ABRUtil.append(debugSb,"CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement "
	 	                        	+ (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);	
	                        		}                  	   
	                        	}
						 }
					}
			}else{
				if (foAvailDiff != null && !foAvailDiff.isDeleted()){
					EntityItem item = foAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);	
					}
					ABRUtil.append(debugSb,"CtryAudRecord.derivePubFrom foavail thedate: "+thedate+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				if (CHEAT.equals(thedate)){
					if(!availDiff.isDeleted() && availDiff != null){
						EntityItem item = availDiff.getCurrentEntityItem();
						Vector annVct = item.getDownLink();
						 for (int ii=0; ii<annVct.size(); ii++){
	                        	EntityItem availanna = (EntityItem)annVct.elementAt(ii);
	                        	if(availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA") ){
	                        		Vector annceVct = availanna.getDownLink();
	                        		EntityItem anna = (EntityItem)annceVct.elementAt(0);
	                                thedate = PokUtils.getAttributeValue(anna,"ANNDATE", ", ", CHEAT, false);
	                                ABRUtil.append(debugSb,"CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement "
	                                + (annVct.size()>1?"There were multiple ANNOUNCEMENTS returned, using first one." + anna.getKey():anna.getKey())+ NEWLINE);
	                        	}
						 }
					}
				}
			}
			return thedate;
		}

		String getAction() { return action;}
		String getCountry() { return country;}
		//String getShipDate() { return earliestshipdate;}
		String getPubFrom() { return pubfrom;}
		String getPubTo() { return pubto;}
		String getEndOfService() {return endofservice;}
		String getAvailStatus() {return availStatus;}

		boolean isDeleted() { return DELETE_ACTIVITY.equals(action);}
		String getKey() { return country ;}
		void dereference(){
			availDiff = null;
			action= null;
			country= null;
			availStatus = null;
			//earliestshipdate = null;
			pubfrom = null;
			pubto = null;
			endofservice = null;
		}

		public String toString() {
			return availDiff.getKey()+" "+getKey()+" action:"+action;
		}
	}
}


