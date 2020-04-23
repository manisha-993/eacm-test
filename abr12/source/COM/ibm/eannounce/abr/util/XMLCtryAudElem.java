// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;
import java.util.*;
import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.
* A.	<COUNTRYAUDIENCELIST>
* 1.	<COUNTRYAUDIENCEELEMENT>
* 	There is an instance of this for every AVAIL.COUNTRYLIST where AVAILTYPE="Planned Availability" (146) and
* 	<AUDIENCE> is equal to either
* 	(a) all values of CATLGOR.AUDIEN where AllValuesOf(AVAIL.COUNTRYLIST) IN (CATLGOR.COUNTRYLIST)
* 	OR (logical or)	if none, then (b) one instance of Null.
* 2.	<COUNTRYAUDIENCEACTION>
* 	This is derived solely on AVAIL.COUNTRYLIST where AVAILTYPE="Planned Availability" (146).
* 3.	<EARLIESTSHIPDATE>
* 	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
* 4.	<PUBFROM>
* 	The first applicable / available date is used.
* 	1.	CATLGOR.PUBFROM
* 	2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
* 	3.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
* 5.	<PUBTO>
* 	The first applicable / available date is used.
* 	1.	CATLGOR.PUBTO
* 	2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
*
*/
// $Log: XMLCtryAudElem.java,v $
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

public class XMLCtryAudElem extends XMLElem
{
	private static final String DEFAULT_NO = "No";
	private static final String DEFAULT_YES = "Yes";

	/**********************************************************************************
    * Constructor for COUNTRYAUDIENCELIST elements
    * <COUNTRYAUDIENCELIST>			2	AVAIL - for each country in COUNTRYLIST where AVAILTYPE = 146 (Planned Availability)
	* <COUNTRYAUDIENCEELEMENT>		3
	* 	<COUNTRYAUDIENCEACTION>	</COUNTRYAUDIENCEACTION>	4	AVAIL	CountryAudienceAction
	* 	<COUNTRY>	</COUNTRY>		4	AVAIL	COUNTRYLIST - Flag Description Class
	1	<STATUS>	</STATUS>		4	AVAIL	STATUS	Flag Description Class
	* 	<AUDIENCE>	</AUDIENCE>		4	CATLGOR	CATAUDIENCE - CATLGOR where AVAIL.COUNTRYLIST LeftOuterJoin
	*															CATLGOR.COUNTRYLIST CrossProduct
	*															MODEL.AUDIEN LeftOuterJoin CATLGOR.CATAUDIENCE
	* 	<EARLIESTSHIPDATE>	</EARLIESTSHIPDATE>		4	AVAIL/CATLGOR	ShipDate
	* 	<PUBFROM>	</PUBFROM>		4	AVAIL/CATLGOR	PubFrom
	* 	<PUBTO>	</PUBTO>			4	AVAIL/CATLGOR	PubTo
	* 	<ADDTOCART>	</ADDTOCART>	4	CATLGOR	CATADDTOCART
	* 	<BUYABLE>	</BUYABLE>		4	CATLGOR	CATBUYABLE
	* 	<PUBLISH>	</PUBLISH>		4	CATLGOR	CATPUBLISH
	* 	<CUSTOMIZEABLE>	</CUSTOMIZEABLE>	4	CATLGOR	CATCUSTIMIZE
	* 	<HIDE>	</HIDE>				4	CATLGOR	CATHIDE
	* </COUNTRYAUDIENCEELEMENT>		3
	* </COUNTRYAUDIENCELIST>		2
    *
    *
    */
    public XMLCtryAudElem()
    {
        super("COUNTRYAUDIENCEELEMENT");
    }

    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
    Wayne Kehrli: for each planned avail, i look at the countrylist..
	Wayne Kehrli: output a crossproduct of  plannedavail.countrylist and model.audience
	Wayne Kehrli: merge catlgor where match - including derivation of pubfrom and pubto
	Wayne Kehrli: and COUNTRYAUDIENCEACTION (update|delete derivation) reflects the country-audience pairing from time1 versus time2
	Wayne Kehrli: so - if AVAIL is deleted - then that results in a COUNTRYAUDIENCACTION of delete
	Wayne Kehrli: for model.audien and the avail's countrtylist
	Wayne Kehrli: if catlgor is deleted - then you have to update the matching crossproduct
	Wendy: what is the match done on for catlgor? how to know when it belongs to a particular plannedavail's set of output?
	Wayne Kehrli: for catlgor - use its countrylist and audience - that creates a cross product
		and then match the  corresponding one from model + audien
	Wendy: will it match completely?
	Wendy: or be a subset?
	Wayne Kehrli: only consider the matching subset
	Wayne Kehrli: there could be CATLGOR that doesn't match any - ignore it
	Wayne Kehrli: and there could be some model +  avail that do not have matching catlgor
    *
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
		// get all AVAILs where AVAILTYPE="Planned Availability" (146) - some may be deleted
	    Vector plnAvlVct = getPlannedAvails(table, debugSb);

		if (plnAvlVct.size()>0){ // must have planned avail for any of this, wayne said there will always be at least 1
			// get model audience values, t2[0] current, t1[1] prior
			Vector mdlAudVct[] = getModelAudience(parentItem, debugSb);
			// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
			// AVAILb to have US at T2
			TreeMap ctryAudElemMap = new TreeMap();
			for (int i=0; i<plnAvlVct.size(); i++){
				DiffEntity availDiff = (DiffEntity)plnAvlVct.elementAt(i);

				// get all possible combinations of MODEL.AUDIEN crossproduct AVAIL.COUNTRYLIST
				buildCtryAudRecs(ctryAudElemMap, availDiff, mdlAudVct, debugSb);
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
					//find catlgor for this country and audience
					DiffEntity catlgorDiff = getCatlgor(table, ctryAudRec.getAudience(), ctryAudRec.getCountry(), debugSb);

					// add other info now
					ctryAudRec.setAllFields(foAvailDiff, loAvailDiff, catlgorDiff, debugSb);
				}
				if (ctryAudRec.isDisplayable()){
					createNodeSet(document, parent, ctryAudRec, debugSb);
				}else{
            		debugSb.append("XMLCtryAudElem.addElements no changes found for "+ctryAudRec+NEWLINE);
				}
				ctryAudRec.dereference();
			}

			// release memory
			ctryAudElemMap.clear();
			mdlAudVct = null;
		}else{
			debugSb.append("XMLCtryAudElem.addElements no planned AVAILs found"+NEWLINE);
		}
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
		Element child = (Element) document.createElement("COUNTRYAUDIENCEACTION");
		child.appendChild(document.createTextNode(ctryAudRec.getAction()));
		elem.appendChild(child);
		child = (Element) document.createElement("COUNTRY");
		child.appendChild(document.createTextNode(ctryAudRec.getCountry()));
		elem.appendChild(child);
		child = (Element) document.createElement("STATUS");
		child.appendChild(document.createTextNode(ctryAudRec.getAvailStatus()));
		elem.appendChild(child);
		child = (Element) document.createElement("AUDIENCE");
		child.appendChild(document.createTextNode(ctryAudRec.getAudience()));
		elem.appendChild(child);
		child = (Element) document.createElement("EARLIESTSHIPDATE");
		child.appendChild(document.createTextNode(ctryAudRec.getShipDate()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBFROM");
		child.appendChild(document.createTextNode(ctryAudRec.getPubFrom()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBTO");
		child.appendChild(document.createTextNode(ctryAudRec.getPubTo()));
		elem.appendChild(child);
		child = (Element) document.createElement("ADDTOCART");
		child.appendChild(document.createTextNode(ctryAudRec.getAddToCart()));
		elem.appendChild(child);
		child = (Element) document.createElement("BUYABLE");
		child.appendChild(document.createTextNode(ctryAudRec.getBuyable()));
		elem.appendChild(child);
		child = (Element) document.createElement("PUBLISH");
		child.appendChild(document.createTextNode(ctryAudRec.getPublish()));
		elem.appendChild(child);
		child = (Element) document.createElement("CUSTOMIZEABLE");
		child.appendChild(document.createTextNode(ctryAudRec.getCustomizeable()));
		elem.appendChild(child);
		child = (Element) document.createElement("HIDE");
		child.appendChild(document.createTextNode(ctryAudRec.getHide()));
		elem.appendChild(child);
	}

    /********************
    * get audience values from t1 and t2 for this model, do it once and use for each avail
    */
    private Vector[] getModelAudience(DiffEntity modelDiff, StringBuffer debugSb){
		debugSb.append("XMLCtryAudElem.getModelAudience for "+modelDiff.getKey()+NEWLINE);

		EANFlagAttribute audienceAtt = (EANFlagAttribute)modelDiff.getCurrentEntityItem().getAttribute("AUDIEN");
		Vector currAudVct = new Vector(1);
		Vector prevAudVct = new Vector(1);
		Vector vct[] = new Vector[2];
		vct[0] = currAudVct;
		vct[1] = prevAudVct;
		debugSb.append("XMLCtryAudElem.getModelAudience cur audienceAtt "+audienceAtt+NEWLINE);
		if (audienceAtt!=null){
			MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
			for (int i = 0; i < mfArray.length; i++){
				// get selection
				if (mfArray[i].isSelected()) {
					currAudVct.addElement(mfArray[i].toString()); // this is long desc
				}
			}
		}

		if (!modelDiff.isNew()){
			audienceAtt = (EANFlagAttribute)modelDiff.getPriorEntityItem().getAttribute("AUDIEN");
			debugSb.append("XMLCtryAudElem.getModelAudience new audienceAtt "+audienceAtt+NEWLINE);
			if (audienceAtt!=null){
				MetaFlag[] mfArray = (MetaFlag[]) audienceAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()) {
						prevAudVct.addElement(mfArray[i].toString()); // this is long desc
					}
				}
			}
		}

		return vct;
	}

    /********************
    * Create rows in the table as follows:
    * Insert one row for each Audience in MODEL.AUDIEN & each Country in AVAIL.COUNTRYLIST where AVAILTYPE = 146
    * If the AVAIL was deleted, set Action = Delete
    * If the AVAIL was added or updated, set Action = Update
    * If AUDIEN was added, set that row's Action = Update
    * If AUDIEN was deleted, set that row's Action= Delete
    * If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
    * If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
    *
    * Note:
    * Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
    * If any of the following steps have data that do not match an existing row in this table, ignore that data.
    */
    private void buildCtryAudRecs(TreeMap ctryAudElemMap, DiffEntity availDiff, Vector mdlAudVct[], StringBuffer debugSb){
		Vector currAudVct = mdlAudVct[0];
		Vector prevAudVct = mdlAudVct[1];

		debugSb.append("XMLCtryAudElem.buildCtryAudRecs "+availDiff.getKey()+" currAudVct:"+currAudVct+
			" prevAudVct:"+prevAudVct+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

		EntityItem curritem = availDiff.getCurrentEntityItem();
		EntityItem prioritem = availDiff.getPriorEntityItem();
		if (availDiff.isDeleted()){ // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
			debugSb.append("XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt "+
						PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+NEWLINE);
			if (ctryAtt!=null){
				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < mfArray.length; im++){
					// get selection
					if (mfArray[im].isSelected()) {
						String ctryVal = mfArray[im].getFlagCode();
						//create crossproduct between ctry and audience for this item
						for (int a=0; a<prevAudVct.size(); a++){
							String audience = prevAudVct.elementAt(a).toString();
							String mapkey = ctryVal+"|"+audience;
							if (ctryAudElemMap.containsKey(mapkey)){
								// dont overwrite
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								debugSb.append("WARNING buildCtryAudRecs for deleted "+availDiff.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,audience);
								ctryAudRec.setAction(DELETE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								debugSb.append("XMLCtryAudElem.buildCtryAudRecs for deleted:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey()+NEWLINE);
							}
						}
					}
				}
			}
		}else if (availDiff.isNew()){ //If the AVAIL was added or updated, set Action = Update
			// mark all records as update
			EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
			debugSb.append("XMLCtryAudElem.buildCtryAudRecs for new avail: ctryAtt "+
					PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+NEWLINE);
			if (ctryAtt!=null){
				MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
				for (int im = 0; im < mfArray.length; im++){
					// get selection
					if (mfArray[im].isSelected()) {
						String ctryVal = mfArray[im].getFlagCode();
						//create crossproduct between ctry and audience for this item
						for (int a=0; a<currAudVct.size(); a++){
							String audience = currAudVct.elementAt(a).toString();
							String mapkey = ctryVal+"|"+audience;
							if (ctryAudElemMap.containsKey(mapkey)){
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								debugSb.append("WARNING buildCtryAudRecs for new "+availDiff.getKey()+
									" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
								rec.setUpdateAvail(availDiff);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,audience);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								debugSb.append("XMLCtryAudElem.buildCtryAudRecs for new:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey()+NEWLINE);
							}
						}
					}
				}
			}
		}else{
			HashSet prevSet = new HashSet();
			HashSet currSet = new HashSet();
			// get current set of countries
			EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
			debugSb.append("XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt "+
				PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+NEWLINE);
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
			debugSb.append("XMLCtryAudElem.buildCtryAudRecs for prev avail: fAtt "+
					PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+NEWLINE);
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
					//create crossproduct between new ctry and current audience for this item
					for (int a=0; a<currAudVct.size(); a++){
						String audience = currAudVct.elementAt(a).toString();
						String mapkey = ctryVal+"|"+audience;
						if (ctryAudElemMap.containsKey(mapkey)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
							debugSb.append("WARNING buildCtryAudRecs for added ctry on "+availDiff.getKey()+
							" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
							rec.setUpdateAvail(availDiff);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,audience);
							ctryAudRec.setAction(UPDATE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							debugSb.append("XMLCtryAudElem.buildCtryAudRecs for added ctry:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
						}
					}
				}else{
					// ctry already existed but something else may have changed
					// look for changes in audience
					//create crossproduct between ctry and audience for this item
					for (int a=0; a<currAudVct.size(); a++){
						String curAud = currAudVct.elementAt(a).toString();
						String mapkey = ctryVal+"|"+curAud;

						if (!prevAudVct.contains(curAud)){ //If AUDIEN was added, set that row's Action = Update
							if (ctryAudElemMap.containsKey(mapkey)){
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								debugSb.append("WARNING buildCtryAudRecs for existing ctry but new aud on "+availDiff.getKey()+
									" "+mapkey+" already exists, replacing orig "+rec+NEWLINE);
								rec.setUpdateAvail(availDiff);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,curAud);
								ctryAudRec.setAction(UPDATE_ACTIVITY);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								debugSb.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry but new aud:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey()+NEWLINE);
							}
						}else{
							if (ctryAudElemMap.containsKey(mapkey)){
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								debugSb.append("WARNING buildCtryAudRecs for existing ctry but new aud on "+availDiff.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
							}else{
								CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,curAud);
								ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
								debugSb.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry:"+availDiff.getKey()+" rec: "+
									ctryAudRec.getKey()+NEWLINE);
							}
						}
					}

					for (int a=0; a<prevAudVct.size(); a++){
						String prevAud = prevAudVct.elementAt(a).toString();
						if (!currAudVct.contains(prevAud)){ // If AUDIEN was deleted, set that row's Action= Delete
							String mapkey = ctryVal+"|"+prevAud;
							if (ctryAudElemMap.containsKey(mapkey)){
								CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
								debugSb.append("WARNING buildCtryAudRecs for existing ctry but del aud on "+availDiff.getKey()+
									" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
								continue;
							}

							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,prevAud);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							debugSb.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry but del aud:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
						}
					}
				}
			}//end of currset while(itr.hasNext())
			itr = prevSet.iterator();
			while(itr.hasNext()) {
				String ctryVal = (String) itr.next();
				if(!currSet.contains(ctryVal))	{ //If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
					//create crossproduct between deleted ctry and previous audience for this item
					for (int a=0; a<prevAudVct.size(); a++){
						String audience = prevAudVct.elementAt(a).toString();
						String mapkey = ctryVal+"|"+audience;
						if (ctryAudElemMap.containsKey(mapkey)){
							CtryAudRecord rec = (CtryAudRecord)ctryAudElemMap.get(mapkey);
							debugSb.append("WARNING buildCtryAudRecs for delete ctry on "+availDiff.getKey()+
								" "+mapkey+" already exists, keeping orig "+rec+NEWLINE);
						}else{
							CtryAudRecord ctryAudRec = new CtryAudRecord(availDiff, ctryVal,audience);
							ctryAudRec.setAction(DELETE_ACTIVITY);
							ctryAudElemMap.put(ctryAudRec.getKey(),ctryAudRec);
							debugSb.append("XMLCtryAudElem.buildCtryAudRecs for delete ctry:"+availDiff.getKey()+" rec: "+
								ctryAudRec.getKey()+NEWLINE);
						}
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

        debugSb.append("XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL"+
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
				debugSb.append("XMLCtryAudElem.getPlannedAvails checking["+i+"]: deleted "+diffitem.getKey()+" AVAILTYPE: "+
					PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")+NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute("AVAILTYPE");
				if (fAtt!= null && fAtt.isSelected("146")){
					avlVct.add(diffitem);
				}
			}else{
				debugSb.append("XMLCtryAudElem.getPlannedAvails checking["+i+"]:"+diffitem.getKey()+" AVAILTYPE: "+
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

		debugSb.append("XMLCtryAudElem.getEntityForAttrs looking for "+attrCode+":"+attrVal+" and "+
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
				debugSb.append("XMLCtryAudElem.getEntityForAttrs checking["+i+"]: deleted "+diffitem.getKey()+
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
					debugSb.append("XMLCtryAudElem.getEntityForAttrs checking["+i+"]: new "+diffitem.getKey()+
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
					debugSb.append("XMLCtryAudElem.getEntityForAttrs checking["+i+"]: current "+diffitem.getKey()+
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
					debugSb.append("XMLCtryAudElem.getEntityForAttrs checking["+i+"]: prior "+diffitem.getKey()+
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
    CATAUDIENCE	CBP	Catalog - Business Partner	Catalog - Business Partner
	CATAUDIENCE	CIR	Catalog - Indirect/Reseller	Catalog - Indirect/Reseller
	CATAUDIENCE	LE	LE Direct	LE Direct
	CATAUDIENCE	None	None	None
	CATAUDIENCE	Shop	Public	Public

	AUDIEN	100		SDI Channel
	AUDIEN	10046	Catalog - Business Partner	Catalog - Business Partner
	AUDIEN	10048	Catalog - Indirect/Reseller	Catalog - Indirect/Reseller
	AUDIEN	10054	Public	Public
	AUDIEN	10055	None	None
	AUDIEN	10062	LE Direct	LE Direct
	AUDIEN	10067	DAC/MAX	DAC/MAX
    * get entity with specified values - should only be one
    * could be two if one was deleted and one was added, but the added one will override and be an 'update'
    */
    private DiffEntity getCatlgor(Hashtable table, String attrVal, String attrVal2,StringBuffer debugSb)
    {
		DiffEntity diffEntity = null;
		Vector allVct = (Vector)table.get("CATLGOR");
		String attrCode = "CATAUDIENCE"; // need flag desc
		String attrCode2 = "OFFCOUNTRY"; // need flag code
		debugSb.append("XMLCtryAudElem.getCatlgor looking for "+attrCode+":"+attrVal+" and "+
			attrCode2+":"+attrVal2+" in CATLGOR allVct.size:"+(allVct==null?"null":""+allVct.size())+NEWLINE);
        if (allVct==null){
			return diffEntity;
		}

		// find those of specified type
		diffloop:for (int i=0; i< allVct.size(); i++){
			DiffEntity diffitem = (DiffEntity)allVct.elementAt(i);
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem prioritem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()){
				debugSb.append("XMLCtryAudElem.getCatlgor checking["+i+"]: deleted "+diffitem.getKey()+
					" "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
					" "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode2)+NEWLINE);
				EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
				if (fAtt!=null && fAtt.toString().length()>0){
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i2 = 0; i2 < mfArray.length; i2++){
						// get selection
						if (mfArray[i2].isSelected() && mfArray[i2].toString().equals(attrVal)){
							EANFlagAttribute fAtt2 = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
							if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
								diffEntity = diffitem; // keep looking for one that is not deleted
								break;
							}
						}  // metaflag is selected
					}// end of flagcodes
				}
			}else{
				if (diffitem.isNew()){
					debugSb.append("XMLCtryAudElem.getCatlgor checking["+i+"]: new "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem, attrCode2)+NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
					if (fAtt!=null && fAtt.toString().length()>0){
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
						for (int i2 = 0; i2 < mfArray.length; i2++){
							// get selection
							if (mfArray[i2].isSelected() && mfArray[i2].toString().equals(attrVal)){
								EANFlagAttribute fAtt2 = (EANFlagAttribute)curritem.getAttribute(attrCode2);
								if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
									diffEntity = diffitem;
									break diffloop;
								}
							}  // metaflag is selected
						}// end of flagcodes
					}
				}else{
					// must check to see if the prior item had a match too
					debugSb.append("XMLCtryAudElem.getCatlgor checking["+i+"]: current "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(curritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(curritem, attrCode2)+NEWLINE);
					EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
					if (fAtt!=null && fAtt.toString().length()>0){
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
						for (int i2 = 0; i2 < mfArray.length; i2++){
							// get selection
							if (mfArray[i2].isSelected() && mfArray[i2].toString().equals(attrVal)){
								EANFlagAttribute fAtt2 = (EANFlagAttribute)curritem.getAttribute(attrCode2);
								if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
									diffEntity = diffitem;
									break diffloop;
								}
							}  // metaflag is selected
						}// end of flagcodes
					}

					debugSb.append("XMLCtryAudElem.getCatlgor checking["+i+"]: prior "+diffitem.getKey()+
						" "+attrCode+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode)+
						" "+attrCode2+":"+PokUtils.getAttributeFlagValue(prioritem, attrCode2)+NEWLINE);
					fAtt = (EANFlagAttribute)prioritem.getAttribute(attrCode);
					if (fAtt!=null && fAtt.toString().length()>0){
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
						for (int i2 = 0; i2 < mfArray.length; i2++){
							// get selection
							if (mfArray[i2].isSelected() && mfArray[i2].toString().equals(attrVal)){
								EANFlagAttribute fAtt2 = (EANFlagAttribute)prioritem.getAttribute(attrCode2);
								if (fAtt2!= null && fAtt2.isSelected(attrVal2)){
									diffEntity = diffitem;
									break; //see if there is another that is current
								}
							}  // metaflag is selected
						}// end of flagcodes
					}
				}
			}
		}

		return diffEntity;
	}

	/*******************************
	* one for every MODEL.AUDIEN crossproduct AVAIL.COUNTRYLIST
	Several tokens have default values if there is not a value in CATLGOR. The defaults are:
	-	<ADDTOCART>  = 'No'
	-	<BUYABLE>  = 'No'
	-	<PUBLISH> = 'No'
	-	<CUSTOMIZEABLE> = 'Yes'
	-	<HIDE> = 'No'
	*/
	private static class CtryAudRecord {
		private String action= null;
		private String audience;
		private String country;
		private String earliestshipdate = CHEAT;// AVAIL
		private String availStatus = CHEAT; //AVAIL
		private String pubfrom = CHEAT; 		//AVAIL/CATLGOR	PubFrom
		private String pubto = CHEAT; 			//AVAIL/CATLGOR	PubTo
		private String addtocart = DEFAULT_NO; 		//CATLGOR	CATADDTOCART
		private String buyable = DEFAULT_NO; 		//CATLGOR	CATBUYABLE
		private String publish= DEFAULT_NO;			//CATLGOR	CATPUBLISH
		private String customizeable= DEFAULT_YES;	//CATLGOR	CATCUSTIMIZE
		private String hide= DEFAULT_NO; 			//CATLGOR	CATHIDE
		private DiffEntity availDiff;

		boolean isDisplayable() {return action!=null;} // only display those with filled in actions

		CtryAudRecord(DiffEntity diffitem,String ctry, String aud){
			country = ctry;
			audience = aud;
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
		* 4.	<PUBFROM>
		* 	The first applicable / available date is used.
		* 	1.	CATLGOR.PUBFROM
		* 	2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		* 	3.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		* 5.	<PUBTO>
		* 	The first applicable / available date is used.
		* 	1.	CATLGOR.PUBTO
		* 	2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		* 	3.	Null
		*/
		void setAllFields(DiffEntity foAvailDiff, DiffEntity loAvailDiff,
			DiffEntity catlgorDiff, StringBuffer debugSb)
		{
            debugSb.append("CtryRecord.setAllFields entered for: "+availDiff.getKey()+" "+getKey()+NEWLINE);

			EntityItem curritem = availDiff.getCurrentEntityItem();
			EntityItem previtem = availDiff.getPriorEntityItem();

			// set EARLIESTSHIPDATE
			// get current value
			earliestshipdate = PokUtils.getAttributeValue(curritem, "EFFECTIVEDATE",", ", CHEAT, false);
			// get priorvalue if it exists
			String prevdate = CHEAT;
			if (previtem != null){
				prevdate = PokUtils.getAttributeValue(previtem, "EFFECTIVEDATE",", ", CHEAT, false);
			}
			debugSb.append("CtryAudRecord.setAllFields curshipdate: "+earliestshipdate+" prevdate: "+prevdate+NEWLINE);

			// if diff, set action
			if (!prevdate.equals(earliestshipdate)){
				setAction(UPDATE_ACTIVITY);
			}
			// get STATUS
			availStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
			if (availStatus==null){
				availStatus = CHEAT;
			}
			// get priorvalue if it exists
			String prevStatus = CHEAT;
			if (previtem != null){
				prevStatus = PokUtils.getAttributeFlagValue(previtem, "STATUS");
				if (prevStatus==null){
					prevStatus = CHEAT;
				}
			}
			debugSb.append("CtryAudRecord.setAllFields curstatus: "+availStatus+" prevstatus: "+prevStatus+NEWLINE);

			// if diff, set action
			if (!prevStatus.equals(availStatus)){
				setAction(UPDATE_ACTIVITY);
			}

			// set PUBFROM
			// get val from t2 entities
			pubfrom = derivePubFrom(foAvailDiff, catlgorDiff, false, debugSb);
			String pubfromT1 = derivePubFrom(foAvailDiff, catlgorDiff, true, debugSb);
			debugSb.append("CtryAudRecord.setAllFields pubfromT2: "+pubfrom+" pubfromT1: "+pubfromT1+NEWLINE);

			if (!pubfrom.equals(pubfromT1)){
				setAction(UPDATE_ACTIVITY);
			}
			// set PUBTO
			// get val from t2 entities
			pubto = derivePubTo(loAvailDiff, catlgorDiff, false,debugSb);
			String pubtoT1 = derivePubTo(loAvailDiff, catlgorDiff, true,debugSb);
			if (!pubto.equals(pubtoT1)){
				setAction(UPDATE_ACTIVITY);
			}
			debugSb.append("CtryAudRecord.setAllFields action:"+action+" pubtoT2: "+pubto+" pubtoT1: "+pubtoT1+NEWLINE);

			// set CATLGOR fields
			/*
			Update rows in the table for each instance of CATLGOR, and Country matches a CATLGOR.OFFCOUNTRY.
			-	If the CALGOR was deleted, do not update the table with any attributes from CATLGOR; however, set the Action = Update
			-	If the CALGOR was added, update the table with CATLGOR attributes and set the Action = Update
			-	If the country in CALGOR.OFFCOUNTRY was changed (an add and a delete in the FLAG table), then for both Countries set the Action = Update. For the country deleted, do not update any other attributes. For the country added, update the table with CATLGOR attributes.
			*/
			if (catlgorDiff != null){
				if(!catlgorDiff.isDeleted()){
					boolean currCtry = true;

					if(catlgorDiff.isNew()){
						setAction(UPDATE_ACTIVITY);
					}else{ // check if country is new or removed
						curritem = catlgorDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("OFFCOUNTRY");
						currCtry = fAtt!= null && fAtt.isSelected(country);
						if (!currCtry){ // must have been a match at t1
							setAction(UPDATE_ACTIVITY);
						}
					}

					if (currCtry){
						curritem = catlgorDiff.getCurrentEntityItem();
						addtocart = PokUtils.getAttributeValue(curritem, "CATADDTOCART",", ", DEFAULT_NO, false);
						buyable = PokUtils.getAttributeValue(curritem, "CATBUYABLE",", ", DEFAULT_NO, false);
						publish = PokUtils.getAttributeValue(curritem, "CATPUBLISH",", ", DEFAULT_NO, false);
						customizeable = PokUtils.getAttributeValue(curritem, "CATCUSTIMIZE",", ", DEFAULT_YES, false);
						hide = PokUtils.getAttributeValue(curritem, "CATHIDE",", ", DEFAULT_NO, false);
					}
				}else{
					setAction(UPDATE_ACTIVITY);
					// catlgor values will not be displayed so this is an update
				}
				debugSb.append("CtryAudRecord.setAllFields after catlgor action:"+action+NEWLINE);
			}
		}

		/****************************
		*5.	<PUBTO>
		The first applicable / available date is used.
		1.	CATLGOR.PUBTO
		2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
		*/
		private String derivePubTo(DiffEntity loAvailDiff, DiffEntity catlgorDiff,
			boolean findT1, StringBuffer debugSb)
		{
			debugSb.append("CtryAudRecord.derivePubTo "+
				" loAvailDiff: "+(loAvailDiff==null?"null":loAvailDiff.getKey())+
				" catlgorDiff: "+(catlgorDiff==null?"null":catlgorDiff.getKey())+" findT1:"+findT1+NEWLINE);

			String thedate = CHEAT;
			if (findT1){ // find previous derivation
				if (catlgorDiff != null && !catlgorDiff.isNew()){
					EntityItem item = catlgorDiff.getPriorEntityItem();
					// make sure it was this country at prior time
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("OFFCOUNTRY");
					if (fAtt != null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "PUBTO","", CHEAT, false);
					}
					debugSb.append("CtryAudRecord.derivePubTo catlgor thedate: "+thedate+
						" OFFCOUNTRY: "+PokUtils.getAttributeFlagValue(item, "OFFCOUNTRY")+NEWLINE);
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the lastorder avail
					if (loAvailDiff != null && !loAvailDiff.isNew()){
						EntityItem item = loAvailDiff.getPriorEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubTo loavail thedate: "+thedate+
							" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
			}else{ //find current derivation
				if (catlgorDiff != null && !catlgorDiff.isDeleted()){
					EntityItem item = catlgorDiff.getCurrentEntityItem();
					// make sure it was this country at prior time
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("OFFCOUNTRY");
					if (fAtt != null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "PUBTO","", CHEAT, false);
					}
					debugSb.append("CtryAudRecord.derivePubTo catlgor thedate: "+thedate+
						" OFFCOUNTRY: "+PokUtils.getAttributeFlagValue(item, "OFFCOUNTRY")+NEWLINE);
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the lastorder avail
					if (loAvailDiff != null && !loAvailDiff.isDeleted()){
						EntityItem item = loAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubTo loavail thedate: "+thedate+
							" COUNTRYLIST: "+PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
			}

			return thedate;
		}
		/****************************
		*4.	<PUBFROM>
		The first applicable / available date is used.
		1.	CATLGOR.PUBFROM
		2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
		3.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
		*/
		private String derivePubFrom(DiffEntity foAvailDiff, DiffEntity catlgorDiff,
			boolean findT1, StringBuffer debugSb)	{
			String thedate = CHEAT;
			debugSb.append("CtryAudRecord.derivePubFrom availDiff: "+availDiff.getKey()+
				" foAvailDiff: "+(foAvailDiff==null?"null":foAvailDiff.getKey())+
				" catlgorDiff: "+(catlgorDiff==null?"null":catlgorDiff.getKey())+" findT1:"+findT1+NEWLINE);

			if (findT1){ // find previous derivation
				if (catlgorDiff != null && !catlgorDiff.isNew()){
					EntityItem item = catlgorDiff.getPriorEntityItem();
					// make sure it was this country at prior time
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("OFFCOUNTRY");
					if (fAtt != null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "PUBFROM","", CHEAT, false);
					}
					debugSb.append("CtryAudRecord.derivePubFrom catlgor thedate: "+thedate+
						" OFFCOUNTRY: "+
						PokUtils.getAttributeFlagValue(item, "OFFCOUNTRY")+NEWLINE);
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the lastorder avail
					if (foAvailDiff != null && !foAvailDiff.isNew()){
						EntityItem item = foAvailDiff.getPriorEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubFrom foavail thedate: "+thedate+
							" COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the plannedavail avail
					if (!availDiff.isNew()){
						EntityItem item = availDiff.getPriorEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubFrom plannedavail thedate: "+thedate+
							" COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
			}else{ //find current derivation
				if (catlgorDiff != null && !catlgorDiff.isDeleted()){
					EntityItem item = catlgorDiff.getCurrentEntityItem();
					// make sure it was this country at prior time
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("OFFCOUNTRY");
					if (fAtt != null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "PUBFROM","", CHEAT, false);
						debugSb.append("CtryAudRecord.derivePubFrom catlgor thedate: "+thedate+
							" OFFCOUNTRY: "+
							PokUtils.getAttributeFlagValue(item, "OFFCOUNTRY")+NEWLINE);
					}
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the lastorder avail
					if (foAvailDiff != null && !foAvailDiff.isDeleted()){
						EntityItem item = foAvailDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubFrom foavail thedate: "+thedate+
							" COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
				if (CHEAT.equals(thedate)){
					// try to get it from the plannedavail avail
					if (!availDiff.isDeleted()){
						EntityItem item = availDiff.getCurrentEntityItem();
						EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
						if (fAtt!= null && fAtt.isSelected(country)){
							thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
						}
						debugSb.append("CtryAudRecord.derivePubFrom plannedavail thedate: "+thedate+
							" COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
					}
				}
			}

			return thedate;
		}

		String getAction() { return action;}
		String getAudience() { return audience;}
		String getCountry() { return country;}
		String getShipDate() { return earliestshipdate;}
		String getPubFrom() { return pubfrom;}
		String getPubTo() { return pubto;}
		String getAddToCart() { return addtocart;}
		String getBuyable() { return buyable;}
		String getPublish() { return publish;}
		String getCustomizeable() { return customizeable;}
		String getHide() { return hide;}
		String getAvailStatus() {return availStatus;}

		boolean isDeleted() { return DELETE_ACTIVITY.equals(action);}
		String getKey() { return country+"|"+audience;}
		void dereference(){
			availDiff = null;
			action= null;
			audience= null;
			country= null;
			availStatus = null;
			earliestshipdate = null;
			pubfrom = null;
			pubto = null;
			addtocart = null;
			buyable = null;
			publish= null;
			customizeable= null;
			hide= null;
		}

		public String toString() {
			return availDiff.getKey()+" "+getKey()+" action:"+action;
		}
	}
}
