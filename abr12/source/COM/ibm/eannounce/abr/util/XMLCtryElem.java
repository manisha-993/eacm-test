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
* A.	< COUNTRYELEMENT>

There is an instance of this for every country in AVAIL.COUNTRYLIST where AVAILTYPE="Planned Availability" (146).
If an instance is deleted, then a "delete" is passed. Countries deleted from the COUNTRYLIST are passed as a "delete".
All other changes are Updates.

Instances of AVAIL where AVAILTYPE = "First Order" (143) or "Last Order" (149) apply when there is a matching
instance as follows:
<COUNTRY> = a country in AVAIL.COUNTRYLIST
Deletes do not provide values; however, they do flag matching records as "Update".

*
*/
// $Log: XMLCtryElem.java,v $
// Revision 1.2  2008/05/28 13:44:23  wendy
// Added STATUS to output for spec "SG FS ABR ADS System Feed 20080528c.doc"
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

public class XMLCtryElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for COUNTRYLIST elements
    * 1	<COUNTRYLIST>
    * 0..N	<COUNTRYELEMENT>
    * 1	<COUNTRYACTION>	</COUNTRYACTION>
    * 1	<COUNTRY>	</COUNTRY>
1	<STATUS>	</STATUS>	4	AVAIL	STATUS	Flag Description Class
    * 1	<PUBFROM>	</PUBFROM>
    * 1	<PUBTO>	</PUBTO>
    * 		</COUNTRYELEMENT>
    * 		</COUNTRYLIST>
    *
    */
    public XMLCtryElem()
    {
        super("COUNTRYELEMENT");
    }

    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
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
            // must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
            // AVAILb to have US at T2
            TreeMap ctryElemMap = new TreeMap();
            for (int i=0; i<plnAvlVct.size(); i++){
                DiffEntity availDiff = (DiffEntity)plnAvlVct.elementAt(i);
                buildCtryRecs(ctryElemMap, availDiff, debugSb);
            }// end each planned avail

            // output the elements
            Collection ctryrecs = ctryElemMap.values();
            Iterator itr = ctryrecs.iterator();
            while(itr.hasNext()) {
                CtryRecord ctryRec = (CtryRecord) itr.next();
                //Rows marked as Delete do not need further updating and the Action should not be changed by further updating.
                if (!ctryRec.isDeleted()){
                    // find firstorder avail for this country
                    DiffEntity foAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "143",
                        "COUNTRYLIST", ctryRec.getCountry(), debugSb);
                    // find lastorder avail for this country
                    DiffEntity loAvailDiff = getEntityForAttrs(table, "AVAIL", "AVAILTYPE", "149",
                        "COUNTRYLIST", ctryRec.getCountry(), debugSb);

                    // add other info now
                    ctryRec.setAllFields(foAvailDiff, loAvailDiff, debugSb);
                }
				if (ctryRec.isDisplayable()){
					createNodeSet(document, parent, ctryRec, debugSb);
				}else{
            		debugSb.append("XMLCtryElem.addElements no changes found for "+ctryRec+NEWLINE);
				}
				ctryRec.dereference();
            }

            // release memory
            ctryElemMap.clear();
        }else{
            debugSb.append("XMLCtryElem.addElements no planned AVAILs found"+NEWLINE);
        }
    }
    /********************
    * create the nodes for this ctry|audience record
    */
    private void createNodeSet(Document document, Element parent,
        CtryRecord ctryRec,StringBuffer debugSb)
    {
        Element elem = (Element) document.createElement(nodeName); // create COUNTRYELEMENT
        addXMLAttrs(elem);
        parent.appendChild(elem);

        // add child nodes
        Element child = (Element) document.createElement("COUNTRYACTION");
        child.appendChild(document.createTextNode(ctryRec.getAction()));
        elem.appendChild(child);
        child = (Element) document.createElement("COUNTRY");
        child.appendChild(document.createTextNode(ctryRec.getCountry()));
        elem.appendChild(child);
		child = (Element) document.createElement("STATUS");
		child.appendChild(document.createTextNode(ctryRec.getAvailStatus()));
		elem.appendChild(child);
        child = (Element) document.createElement("PUBFROM");
        child.appendChild(document.createTextNode(ctryRec.getPubFrom()));
        elem.appendChild(child);
        child = (Element) document.createElement("PUBTO");
        child.appendChild(document.createTextNode(ctryRec.getPubTo()));
        elem.appendChild(child);
    }

    /********************
    *
    */
    private void buildCtryRecs(TreeMap ctryElemMap, DiffEntity availDiff, StringBuffer debugSb){
        debugSb.append("XMLCtryElem.buildCtryRecs "+availDiff.getKey()+NEWLINE);

		// must account for AVAILa to have had US, CANADA at T1, and just CANADA at T2 and a new
		// AVAILb to have US at T2
		// only delete action if ctry or aud was removed at t2!!! allow update to override it

        EntityItem curritem = availDiff.getCurrentEntityItem();
        EntityItem prioritem = availDiff.getPriorEntityItem();
        if (availDiff.isDeleted()){ // If the AVAIL was deleted, set Action = Delete
            // mark all records as delete
            EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
            debugSb.append("XMLCtryElem.buildCtryRecs for deleted avail: ctryAtt "+
                        PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+NEWLINE);
            if (ctryAtt!=null){
                MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
                for (int im = 0; im < mfArray.length; im++){
                    // get selection
                    if (mfArray[im].isSelected()) {
                        String ctryVal = mfArray[im].getFlagCode();
						if (ctryElemMap.containsKey(ctryVal)){
							// dont overwrite
							CtryRecord rec = (CtryRecord)ctryElemMap.get(ctryVal);
							debugSb.append("WARNING buildCtryRecs for deleted "+availDiff.getKey()+
								" "+ctryVal+" already exists, keeping orig "+rec+NEWLINE);
						}else{
							CtryRecord ctryRec = new CtryRecord(availDiff, ctryVal);
							ctryRec.setAction(DELETE_ACTIVITY);
							ctryElemMap.put(ctryRec.getKey(),ctryRec);
							debugSb.append("XMLCtryElem.buildCtryRecs for deleted:"+availDiff.getKey()+" rec: "+
								ctryRec.getKey()+NEWLINE);
						}
                    }
                }
            }
        }else if (availDiff.isNew()){ //If the AVAIL was added or updated, set Action = Update
            // mark all records as update
            EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
            debugSb.append("XMLCtryElem.buildCtryRecs for new avail: ctryAtt "+
                    PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+NEWLINE);
            if (ctryAtt!=null){
                MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
                for (int im = 0; im < mfArray.length; im++){
                    // get selection
                    if (mfArray[im].isSelected()) {
                        String ctryVal = mfArray[im].getFlagCode();
						if (ctryElemMap.containsKey(ctryVal)){
							CtryRecord rec = (CtryRecord)ctryElemMap.get(ctryVal);
							debugSb.append("WARNING buildCtryRecs for new "+availDiff.getKey()+
								" "+ctryVal+" already exists, replacing orig "+rec+NEWLINE);
							rec.setUpdateAvail(availDiff);
						}else{
							CtryRecord ctryRec = new CtryRecord(availDiff, ctryVal);
							ctryRec.setAction(UPDATE_ACTIVITY);
							ctryElemMap.put(ctryRec.getKey(),ctryRec);
							debugSb.append("XMLCtryElem.buildCtryRecs for new:"+availDiff.getKey()+" rec: "+
								ctryRec.getKey()+NEWLINE);
						}
                    }
                }
            }
        }else{
            HashSet prevSet = new HashSet();
            HashSet currSet = new HashSet();
            // get current set of countries
            EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
            debugSb.append("XMLCtryElem.buildCtryRecs for curr avail: fAtt "+
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
            debugSb.append("XMLCtryElem.buildCtryRecs for prev avail: fAtt "+
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
                if(!prevSet.contains(ctryVal))  { // If AVAIL.COUNTRYLIST has a country added, set that row's Action = Update
					if (ctryElemMap.containsKey(ctryVal)){
						CtryRecord rec = (CtryRecord)ctryElemMap.get(ctryVal);
						debugSb.append("WARNING buildCtryRecs for added ctry on "+availDiff.getKey()+
							" "+ctryVal+" already exists, replacing orig "+rec+NEWLINE);
						rec.setUpdateAvail(availDiff);
					}else{
						CtryRecord ctryRec = new CtryRecord(availDiff, ctryVal);
						ctryRec.setAction(UPDATE_ACTIVITY);
						ctryElemMap.put(ctryRec.getKey(),ctryRec);
						debugSb.append("XMLCtryElem.buildCtryRecs for added ctry:"+availDiff.getKey()+" rec: "+
							ctryRec.getKey()+NEWLINE);
					}
                }else{
                    // ctry already existed but something else may have changed
					if (ctryElemMap.containsKey(ctryVal)){
						CtryRecord rec = (CtryRecord)ctryElemMap.get(ctryVal);
						debugSb.append("WARNING buildCtryRecs for existing ctry "+availDiff.getKey()+
							" "+ctryVal+" already exists, keeping orig "+rec+NEWLINE);
					}else{
						CtryRecord ctryRec = new CtryRecord(availDiff, ctryVal);
						ctryElemMap.put(ctryRec.getKey(),ctryRec);
						debugSb.append("XMLCtryElem.buildCtryRecs for existing ctry:"+availDiff.getKey()+" rec: "+
							ctryRec.getKey()+NEWLINE);
					}
                }
            }//end of currset while(itr.hasNext())
            itr = prevSet.iterator();
            while(itr.hasNext()) {
                String ctryVal = (String) itr.next();
                if(!currSet.contains(ctryVal))  { //If AVAIL.COUNTRYLIST has a country deleted, set that row's Action = Delete
					if (ctryElemMap.containsKey(ctryVal)){
						CtryRecord rec = (CtryRecord)ctryElemMap.get(ctryVal);
						debugSb.append("WARNING buildCtryRecs for delete ctry on "+availDiff.getKey()+
							" "+ctryVal+" already exists, keeping orig "+rec+NEWLINE);
					}else{
						CtryRecord ctryRec = new CtryRecord(availDiff, ctryVal);
						ctryRec.setAction(DELETE_ACTIVITY);
						ctryElemMap.put(ctryRec.getKey(),ctryRec);
						debugSb.append("XMLCtryElem.buildCtryRecs for delete ctry:"+availDiff.getKey()+" rec: "+
							ctryRec.getKey()+NEWLINE);
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

        debugSb.append("XMLCtryElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL"+
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
                debugSb.append("XMLCtryElem.getPlannedAvails checking["+i+"]: deleted "+diffitem.getKey()+" AVAILTYPE: "+
                    PokUtils.getAttributeFlagValue(prioritem, "AVAILTYPE")+NEWLINE);
                EANFlagAttribute fAtt = (EANFlagAttribute)prioritem.getAttribute("AVAILTYPE");
                if (fAtt!= null && fAtt.isSelected("146")){
                    avlVct.add(diffitem);
                }
            }else{
                debugSb.append("XMLCtryElem.getPlannedAvails checking["+i+"]:"+diffitem.getKey()+" AVAILTYPE: "+
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

        debugSb.append("XMLCtryElem.getEntityForAttrs looking for "+attrCode+":"+attrVal+" and "+
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
                debugSb.append("XMLCtryElem.getEntityForAttrs checking["+i+"]: deleted "+diffitem.getKey()+
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
                    debugSb.append("XMLCtryElem.getEntityForAttrs checking["+i+"]: new "+diffitem.getKey()+
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
                    debugSb.append("XMLCtryElem.getEntityForAttrs checking["+i+"]: current "+diffitem.getKey()+
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
                    debugSb.append("XMLCtryElem.getEntityForAttrs checking["+i+"]: prior "+diffitem.getKey()+
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

    /*******************************
    * one for every AVAIL.COUNTRYLIST
    */
    private static class CtryRecord {
        private String action= null;
        private String country;
		private String availStatus = CHEAT; //AVAIL
        private String pubfrom = CHEAT;
        private String pubto = CHEAT;
        private DiffEntity availDiff;

        boolean isDisplayable() {return action!=null;} // only display those with filled in actions

        CtryRecord(DiffEntity diffitem,String ctry){
            country = ctry;
            availDiff = diffitem;
        }
        void setAction(String s) {  action = s; }
        void setUpdateAvail(DiffEntity avl) {
            availDiff = avl;// allow replacement
            setAction(UPDATE_ACTIVITY);
        }

        /*********************
        * B.	<PUBFROM>

The first applicable / available date is used.
1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
C.	<PUBTO>

The first applicable / available date is used.
1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)

        */
        void setAllFields(DiffEntity foAvailDiff, DiffEntity loAvailDiff,
            StringBuffer debugSb)
        {
            debugSb.append("CtryRecord.setAllFields entered for: "+availDiff.getKey()+" "+getKey()+NEWLINE);
			EntityItem curritem = availDiff.getCurrentEntityItem();
			EntityItem previtem = availDiff.getPriorEntityItem();

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
			debugSb.append("CtryRecord.setAllFields curstatus: "+availStatus+" prevstatus: "+prevStatus+NEWLINE);

			// if diff, set action
			if (!prevStatus.equals(availStatus)){
				setAction(UPDATE_ACTIVITY);
			}

            // set PUBFROM
            // get val from t2 entities
            pubfrom = derivePubFrom(foAvailDiff, false, debugSb);
            String pubfromT1 = derivePubFrom(foAvailDiff, true, debugSb);
            debugSb.append("CtryRecord.setAllFields pubfromT2: "+pubfrom+" pubfromT1: "+pubfromT1+NEWLINE);

            if (!pubfrom.equals(pubfromT1)){
                setAction(UPDATE_ACTIVITY);
            }
            // set PUBTO
            // get val from t2 entities
            pubto = derivePubTo(loAvailDiff, false,debugSb);
            String pubtoT1 = derivePubTo(loAvailDiff, true,debugSb);
            if (!pubto.equals(pubtoT1)){
                setAction(UPDATE_ACTIVITY);
            }
            debugSb.append("CtryRecord.setAllFields action:"+action+" pubtoT2: "+pubto+" pubtoT1: "+pubtoT1+NEWLINE);
        }

        /****************************
        C.	<PUBTO>

		The first applicable / available date is used.
1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
        */
        private String derivePubTo(DiffEntity loAvailDiff, boolean findT1, StringBuffer debugSb)
        {
            debugSb.append("CtryRecord.derivePubTo "+
                " loAvailDiff: "+(loAvailDiff==null?"null":loAvailDiff.getKey())+" findT1:"+findT1+NEWLINE);

            String thedate = CHEAT;
            if (findT1){ // find previous derivation
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isNew()){
					EntityItem item = loAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
					debugSb.append("CtryRecord.derivePubTo loavail thedate: "+thedate+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}
            }else{ //find current derivation
				// try to get it from the lastorder avail
				if (loAvailDiff != null && !loAvailDiff.isDeleted()){
					EntityItem item = loAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
					debugSb.append("CtryRecord.derivePubTo loavail thedate: "+thedate+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}
            }

            return thedate;
        }
        /****************************
        *B.	<PUBFROM>

The first applicable / available date is used.
1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "First Order" (143)
2.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Planned Availability" (146)
        */
        private String derivePubFrom(DiffEntity foAvailDiff,
            boolean findT1, StringBuffer debugSb)   {
            String thedate = CHEAT;
            debugSb.append("CtryRecord.derivePubFrom availDiff: "+availDiff.getKey()+
                " foAvailDiff: "+(foAvailDiff==null?"null":foAvailDiff.getKey())+" findT1:"+findT1+NEWLINE);

            if (findT1){ // find previous derivation
				// try to get it from the lastorder avail
				if (foAvailDiff != null && !foAvailDiff.isNew()){
					EntityItem item = foAvailDiff.getPriorEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
					debugSb.append("CtryRecord.derivePubFrom foavail thedate: "+thedate+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}

                if (CHEAT.equals(thedate)){
                    // try to get it from the plannedavail avail
                    if (!availDiff.isNew()){
                        EntityItem item = availDiff.getPriorEntityItem();
                        EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
                        if (fAtt!= null && fAtt.isSelected(country)){
                            thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
                        }
                        debugSb.append("CtryRecord.derivePubFrom plannedavail thedate: "+thedate+
                            " COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
                    }
                }
            }else{ //find current derivation
				// try to get it from the lastorder avail
				if (foAvailDiff != null && !foAvailDiff.isDeleted()){
					EntityItem item = foAvailDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
					}
					debugSb.append("CtryRecord.derivePubFrom foavail thedate: "+thedate+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}

                if (CHEAT.equals(thedate)){
                    // try to get it from the plannedavail avail
                    if (!availDiff.isDeleted()){
                        EntityItem item = availDiff.getCurrentEntityItem();
                        EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
                        if (fAtt!= null && fAtt.isSelected(country)){
                            thedate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE",", ", CHEAT, false);
                        }
                        debugSb.append("CtryRecord.derivePubFrom plannedavail thedate: "+thedate+
                            " COUNTRYLIST: "+
							PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
                    }
                }
            }

            return thedate;
        }

        String getAction() { return action;}
        String getCountry() { return country;}
		String getAvailStatus() {return availStatus;}
        String getPubFrom() { return pubfrom;}
        String getPubTo() { return pubto;}

        boolean isDeleted() { return DELETE_ACTIVITY.equals(action);}
        String getKey() { return country;}
        void dereference(){
            availDiff = null;
            action= null;
            country= null;
			availStatus = null;
            pubfrom = null;
            pubto = null;
        }

        public String toString() {
            return availDiff.getKey()+" "+getKey()+" action:"+action;
        }
    }
}
