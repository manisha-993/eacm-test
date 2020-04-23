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
* C.    <COUNTRYLIST>
the Country List (COUNTRYLIST) of Catalog Category Navigation (CATNAV) is used as the "master"
for selecting the subset of related Images (IMG).
D.  <IMAGELIST>
Image (IMG) entity data is passed only if:
Include Image (IMG) where AnyValueOf(CATNAVIMG: IMG.COUNTRYLIST) IN (CATNAV.COUNTRYLIST)
*
*/
// $Log: XMLCtryImgElem.java,v $
// Revision 1.6  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.5  2013/12/20 06:44:40  guobin
// Fixed Defect 1063855 - problem on IMGLIST is not in Languagelist- looks like the XML does not match the XSD
//
// Revision 1.4  2010/12/02 13:17:47  guobin
// Create instances of <IMAGEELEMENT> tag when there is no change in IMAG.COUNTRYLIST.
//
// Revision 1.3  2010/11/02 19:24:42  rick
// element name changes for BH 1.0 CATNAV
//
// Revision 1.2  2008/05/28 13:44:23  wendy
// Added STATUS to output for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.1  2008/04/30 11:50:13  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLCtryImgElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for COUNTRYELEMENT and IMAGE elements
    * 1 <COUNTRYLIST>                   2   CATNAV
0..N    <COUNTRYELEMENT>            3   CATNAV/IMG
1   <COUNTRYLISTACTION> </COUNTRYLISTACTION>    4   CATNAV/IMG  CountryListAction   Derived
1   <COUNTRYCODE>   </COUNTRYCODE>  4   CATNAV  COUNTRYLIST Flag Description Class
1   <IMAGELIST>                     4   IMG
0..N    <IMAGEELEMENT>              5   IMG
1   <IMAGEENTITYID> </IMAGEENTITYID>    6   IMG IMAGEENTITYID   IMG.ENTITYID
1   <IMAGEACTION>   </IMAGEACTION>  6   IMG ImageAction Derived
1   <PUBFROM>   </PUBFROM>          6   IMG PUBFROM
    <PUBTO> </PUBTO>                6   IMG PUBTO
1	<STATUS>	</STATUS>			6	IMG	STATUS	Flag Description Class
1   <IMAGEDESCRIPTION>  </IMAGEDESCRIPTION> 6   IMG IMGDESC
1   <MARKETINGIMAGEFILENAME>    </MARKETINGIMAGEFILENAME>   6   IMG MKTGIMGFILENAM
        </IMAGEELEMENT>             5
        </IMAGELIST>                4
        </COUNTRYELEMENT>           3
        </COUNTRYLIST>              2
    *
    */
    public XMLCtryImgElem()
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

		Vector imgvct = (Vector)table.get("IMG");
        TreeMap imgElemMap = new TreeMap();  // key = ctry, value = vector of ImgRecord elements

  		if (imgvct!= null && imgvct.size()>0){
            for (int i=0; i<imgvct.size(); i++){
                DiffEntity imgDiff = (DiffEntity)imgvct.elementAt(i);
                buildImgCtryRecs(imgElemMap, imgDiff, debugSb);
            }
		}else{
            ABRUtil.append(debugSb,"XMLCtryImgElem.addElements no IMGs found"+NEWLINE);
        }

		// add ImgRecord to catnav countries
        TreeMap ctryElemMap = buildCtryRecs(imgElemMap, parentItem, debugSb);

		// output the elements
		Collection ctryrecs = ctryElemMap.values();
		Iterator itr = ctryrecs.iterator();
		while(itr.hasNext()) {
			CtryRecord ctryRec = (CtryRecord) itr.next();
			if (ctryRec.isDisplayable()){
				createNodeSet(document, parent, ctryRec, debugSb);
			}else{
				ABRUtil.append(debugSb,"XMLCtryImgElem.addElements no changes found for "+ctryRec+NEWLINE);
			}
			ctryRec.dereference();
		}

		// release memory
		ctryElemMap.clear();

    }
    /***************************************************************************
	 * create the nodes for this ctry|audience record
	 */
	private void createNodeSet(Document document, Element parent, CtryRecord ctryRec, StringBuffer debugSb) {
		Element elem = (Element) document.createElement(nodeName); // create
																	// COUNTRYELEMENT
		addXMLAttrs(elem);
		parent.appendChild(elem);

		// add child nodes
		Element child = (Element) document.createElement("COUNTRYACTION");
		child.appendChild(document.createTextNode(ctryRec.getAction()));
		elem.appendChild(child);
		child = (Element) document.createElement("COUNTRY_FC");
		child.appendChild(document.createTextNode(ctryRec.getCountry()));
		elem.appendChild(child);
		Vector imgRecVct = ctryRec.getImgRecVct();
		Element list = (Element) document.createElement("IMAGELIST");
		elem.appendChild(list);
		if (imgRecVct != null) {
			// boolean hasaction = false;
			// // check for an action
			// for (int i=0; i<imgRecVct.size(); i++){
			// ImgRecord imgrec = (ImgRecord)imgRecVct.elementAt(i);
			// if (imgrec.isDisplayable()){
			// hasaction = true;
			// break;
			// }
			// }

			// if (hasaction){

			for (int i = 0; i < imgRecVct.size(); i++) {
				ImgRecord imgrec = (ImgRecord) imgRecVct.elementAt(i);
				// if (imgrec.isDisplayable()){
				Element imgelem = (Element) document.createElement("IMAGEELEMENT");
				list.appendChild(imgelem);

				child = (Element) document.createElement("IMAGEENTITYTYPE");
				child.appendChild(document.createTextNode("IMG"));
				imgelem.appendChild(child);

				child = (Element) document.createElement("IMAGEENTITYID");
				child.appendChild(document.createTextNode(imgrec.getEntityID()));
				imgelem.appendChild(child);

				child = (Element) document.createElement("ACTIVITY");
				child.appendChild(document.createTextNode(imgrec.isDisplayable() ? imgrec.getAction() : UPDATE_ACTIVITY));
				imgelem.appendChild(child);

				if (!imgrec.isDeleted()) {

					child = (Element) document.createElement("PUBFROM");
					child.appendChild(document.createTextNode(imgrec.getPubFrom()));
					imgelem.appendChild(child);

					child = (Element) document.createElement("PUBTO");
					child.appendChild(document.createTextNode(imgrec.getPubTo()));
					imgelem.appendChild(child);

					child = (Element) document.createElement("STATUS");
					child.appendChild(document.createTextNode(imgrec.getImgStatus()));
					imgelem.appendChild(child);

					child = (Element) document.createElement("IMAGEDESCRIPTION");
					child.appendChild(document.createTextNode(imgrec.getDesc()));
					imgelem.appendChild(child);

					child = (Element) document.createElement("MARKETINGIMAGEFILENAME");
					child.appendChild(document.createTextNode(imgrec.getFilename()));
					imgelem.appendChild(child);
					// }
					// }
				}
			} // has action to display
		} // imgvct != null
	}

    /***************************************************************************
	 * 
	 */
    private void buildImgCtryRecs(TreeMap ctryElemMap, DiffEntity imgDiff, StringBuffer debugSb){
        ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs "+imgDiff.getKey()+NEWLINE);

        EntityItem curritem = imgDiff.getCurrentEntityItem();
        EntityItem prioritem = imgDiff.getPriorEntityItem();
        if (imgDiff.isDeleted()){ // If the IMG was deleted, set Action = Delete
            // mark all records as delete
            EANFlagAttribute ctryAtt = (EANFlagAttribute)prioritem.getAttribute("COUNTRYLIST");
            ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for deleted "+imgDiff.getKey()+" ctryAtt "+
                        PokUtils.getAttributeFlagValue(prioritem, "COUNTRYLIST")+NEWLINE);
            if (ctryAtt!=null){
                MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
                for (int im = 0; im < mfArray.length; im++){
                    // get selection
                    if (mfArray[im].isSelected()) {
                        String ctryVal = mfArray[im].getFlagCode();
                        Vector vct = (Vector)ctryElemMap.get(ctryVal);
                        if (vct==null){
							vct = new Vector(1);
                            ctryElemMap.put(ctryVal,vct);
						}

						ImgRecord ctryRec = new ImgRecord(imgDiff, ctryVal);
						ctryRec.setAction(DELETE_ACTIVITY);
						vct.add(ctryRec);
						ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for deleted:"+imgDiff.getKey()+" rec: "+
							ctryRec.getKey()+NEWLINE);
                    }
                }
            }
        }else if (imgDiff.isNew()){ //If the IMG was added or updated, set Action = Update
            // mark all records as update
            EANFlagAttribute ctryAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
            ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for new "+imgDiff.getKey()+" ctryAtt "+
                    PokUtils.getAttributeFlagValue(curritem, "COUNTRYLIST")+NEWLINE);
            if (ctryAtt!=null){
                MetaFlag[] mfArray = (MetaFlag[]) ctryAtt.get();
                for (int im = 0; im < mfArray.length; im++){
                    // get selection
                    if (mfArray[im].isSelected()) {
                        String ctryVal = mfArray[im].getFlagCode();
                        Vector vct = (Vector)ctryElemMap.get(ctryVal);
                        if (vct==null){
							vct = new Vector(1);
                            ctryElemMap.put(ctryVal,vct);
						}
						ImgRecord ctryRec = new ImgRecord(imgDiff, ctryVal);
						ctryRec.setAction(UPDATE_ACTIVITY);
						ctryRec.setAllFields(debugSb);
						vct.add(ctryRec);
						ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for new:"+imgDiff.getKey()+" rec: "+
							ctryRec.getKey()+NEWLINE);
                    }
                }
            }
        }else{
            HashSet prevSet = new HashSet();
            HashSet currSet = new HashSet();
            // get current set of countries
            EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
            ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for curr "+imgDiff.getKey()+" fAtt "+
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
            ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for prev "+imgDiff.getKey()+" fAtt "+
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
                if(!prevSet.contains(ctryVal))  { // If IMG.COUNTRYLIST has a country added, set that row's Action = Update
					Vector vct = (Vector)ctryElemMap.get(ctryVal);
					if (vct==null){
						vct = new Vector(1);
						ctryElemMap.put(ctryVal,vct);
					}
					ImgRecord ctryRec = new ImgRecord(imgDiff, ctryVal);
					ctryRec.setAction(UPDATE_ACTIVITY);
					ctryRec.setAllFields(debugSb);
					vct.add(ctryRec);
					ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for added ctry:"+imgDiff.getKey()+" rec: "+
						ctryRec.getKey()+NEWLINE);
                }else{
                    // ctry already existed but something else may have changed
					Vector vct = (Vector)ctryElemMap.get(ctryVal);
					if (vct==null){
						vct = new Vector(1);
						ctryElemMap.put(ctryVal,vct);
					}
					ImgRecord ctryRec = new ImgRecord(imgDiff, ctryVal);
					ctryRec.setAllFields(debugSb);
					vct.add(ctryRec);
					ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for existing ctry:"+imgDiff.getKey()+" rec: "+
						ctryRec.getKey()+NEWLINE);
                }
            }//end of currset while(itr.hasNext())
            itr = prevSet.iterator();
            while(itr.hasNext()) {
                String ctryVal = (String) itr.next();
                if(!currSet.contains(ctryVal))  { //If IMG.COUNTRYLIST has a country deleted, set that row's Action = Delete
					Vector vct = (Vector)ctryElemMap.get(ctryVal);
					if (vct==null){
						vct = new Vector(1);
						ctryElemMap.put(ctryVal,vct);
					}
					ImgRecord ctryRec = new ImgRecord(imgDiff, ctryVal);
					ctryRec.setAction(DELETE_ACTIVITY);
					vct.add(ctryRec);
					ABRUtil.append(debugSb,"XMLCtryImgElem.buildImgCtryRecs for delete ctry:"+imgDiff.getKey()+" rec: "+
						ctryRec.getKey()+NEWLINE);
                }
            }
        } // end img existed at both t1 and t2
    }

    private TreeMap buildCtryRecs(TreeMap imgElemMap, DiffEntity parentItem,StringBuffer debugSb)
    {
		TreeMap ctryElemMap = new TreeMap();
		HashSet prevSet = new HashSet();
		HashSet currSet = new HashSet();
        EntityItem curritem = parentItem.getCurrentEntityItem();

		// get current set of countries
		EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("COUNTRYLIST");
		ABRUtil.append(debugSb,"XMLCtryImgElem.buildCtryRecs for current "+curritem.getKey()+" fAtt "+
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

		if (!parentItem.isDeleted()){
	        EntityItem previtem = parentItem.getPriorEntityItem();
			fAtt = (EANFlagAttribute)previtem.getAttribute("COUNTRYLIST");
			ABRUtil.append(debugSb,"XMLCtryImgElem.buildCtryRecs for prev "+previtem.getKey()+" fAtt "+
				PokUtils.getAttributeFlagValue(previtem, "COUNTRYLIST")+NEWLINE);
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
		}

		// look for changes in country
		Iterator itr = currSet.iterator();
		while(itr.hasNext()) {
			String ctryVal = (String) itr.next();
			if(!prevSet.contains(ctryVal))  { // If COUNTRYLIST has a country added, set that row's Action = Update
				Vector vct = (Vector)imgElemMap.get(ctryVal);
				CtryRecord ctryRec = new CtryRecord(vct, ctryVal);
				ctryRec.setAction(UPDATE_ACTIVITY);
				ctryElemMap.put(ctryVal,ctryRec);
				ABRUtil.append(debugSb,"XMLCtryImgElem.buildCtryRecs for added rec: "+ctryRec.getKey()+NEWLINE);
			}else{
				// ctry already existed but something else may have changed
				Vector vct = (Vector)imgElemMap.get(ctryVal);
				CtryRecord ctryRec = new CtryRecord(vct, ctryVal);
				ctryElemMap.put(ctryVal,ctryRec);
				ABRUtil.append(debugSb,"XMLCtryImgElem.buildCtryRecs for existing ctry rec: "+ctryRec.getKey()+NEWLINE);
			}
		}//end of currset while(itr.hasNext())

		itr = prevSet.iterator();
		while(itr.hasNext()) {
			String ctryVal = (String) itr.next();
			if(!currSet.contains(ctryVal))  { //If COUNTRYLIST has a country deleted, set that row's Action = Delete
				Vector vct = (Vector)imgElemMap.get(ctryVal);
				CtryRecord ctryRec = new CtryRecord(vct, ctryVal);
				ctryRec.setAction(DELETE_ACTIVITY);
				ctryElemMap.put(ctryVal,ctryRec);
				ABRUtil.append(debugSb,"XMLCtryImgElem.buildCtryRecs for delete ctry rec: "+
					ctryRec.getKey()+NEWLINE);
			}
		}

		return ctryElemMap;
	}

    /*******************************
    * one for every IMG.COUNTRYLIST
    */
    private static class ImgRecord {
        private String action= null;
        private String country;
		private String imgStatus = CHEAT; //AVAIL
        private String pubfrom = CHEAT;
        private String pubto = CHEAT;
        private String desc = CHEAT;
        private String fname = CHEAT;
        private DiffEntity imgDiff;

        boolean isDisplayable() {return action!=null;} // only display those with filled in actions

        ImgRecord(DiffEntity diffitem,String ctry){
            country = ctry;
            imgDiff = diffitem;
        }
        void setAction(String s) {  action = s; }
        /*********************
        */
        void setAllFields(StringBuffer debugSb)
        {
            ABRUtil.append(debugSb,"ImgRecord.setAllFields entered for: "+imgDiff.getKey()+" "+getKey()+NEWLINE);

			EntityItem curritem = imgDiff.getCurrentEntityItem();
			EntityItem previtem = imgDiff.getPriorEntityItem();

			// get STATUS
			imgStatus = PokUtils.getAttributeFlagValue(curritem, "STATUS");
			if (imgStatus==null){
				imgStatus = CHEAT;
			}
			// get priorvalue if it exists
			String prevStatus = CHEAT;
			if (previtem != null){
				prevStatus = PokUtils.getAttributeFlagValue(previtem, "STATUS");
				if (prevStatus==null){
					prevStatus = CHEAT;
				}
			}
			ABRUtil.append(debugSb,"ImgRecord.setAllFields curstatus: "+imgStatus+" prevstatus: "+prevStatus+NEWLINE);

            // set PUBFROM
            // get val from t2 entities
            pubfrom = getValue(false, "PUBFROM", debugSb);
            String pubfromT1 = getValue(true, "PUBFROM", debugSb);
            ABRUtil.append(debugSb,"ImgRecord.setAllFields pubfromT2: "+pubfrom+" pubfromT1: "+pubfromT1+NEWLINE);

            if (!pubfrom.equals(pubfromT1)){
                setAction(UPDATE_ACTIVITY);
            }
            // set PUBTO
            // get val from t2 entities
            pubto = getValue(false, "PUBTO", debugSb);
            String pubtoT1 = getValue(true, "PUBTO", debugSb);
            if (!pubto.equals(pubtoT1)){
                setAction(UPDATE_ACTIVITY);
            }

            ABRUtil.append(debugSb,"ImgRecord.setAllFields action:"+action+" pubtoT2: "+pubto+" pubtoT1: "+pubtoT1+NEWLINE);

            // set IMGDESC
            // get val from t2 entities
            desc = getValue(false, "IMGDESC", debugSb);
            String descT1 = getValue(true, "IMGDESC", debugSb);
            if (!desc.equals(descT1)){
                setAction(UPDATE_ACTIVITY);
            }

            ABRUtil.append(debugSb,"ImgRecord.setAllFields action:"+action+" desc: "+desc+" descT1: "+descT1+NEWLINE);

            // set MKTGIMGFILENAM
            // get val from t2 entities
            fname = getValue(false, "MKTGIMGFILENAM", debugSb);
            String fnameT1 = getValue(true, "MKTGIMGFILENAM", debugSb);
            if (!fname.equals(fnameT1)){
                setAction(UPDATE_ACTIVITY);
            }

            ABRUtil.append(debugSb,"ImgRecord.setAllFields action:"+action+" fname: "+fname+" fnameT1: "+fnameT1+NEWLINE);

        }

       /****************************
        *get the value
        */
        private String getValue(boolean findT1, String attCode, StringBuffer debugSb)
        {
            String value = CHEAT;
            ABRUtil.append(debugSb,"ImgRecord.getValue imgDiff: "+imgDiff.getKey()+" findT1:"+findT1+NEWLINE);

            if (findT1){ // find previous derivation
				if (!imgDiff.isNew()){
					EntityItem item = imgDiff.getPriorEntityItem();
                    EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						value = PokUtils.getAttributeValue(item, attCode,", ", CHEAT, false);
					}
					ABRUtil.append(debugSb,"ImgRecord.getValue value: "+value+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}
            }else{ //find current derivation
				if (!imgDiff.isDeleted()){
					EntityItem item = imgDiff.getCurrentEntityItem();
					EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
					if (fAtt!= null && fAtt.isSelected(country)){
						value = PokUtils.getAttributeValue(item, attCode,", ", CHEAT, false);
					}
					ABRUtil.append(debugSb,"ImgRecord.getValue value: "+value+
						" COUNTRYLIST: "+
						PokUtils.getAttributeFlagValue(item, "COUNTRYLIST")+NEWLINE);
				}
            }

            return value;
        }

        String getAction() { return action;}
        String getCountry() { return country;}
        String getPubFrom() { return pubfrom;}
        String getPubTo() { return pubto;}
		String getImgStatus() {return imgStatus;}
        String getDesc() { return desc;}
        String getFilename() { return fname;}
        String getEntityID() {  return ""+imgDiff.getEntityID(); }

        boolean isDeleted() { return DELETE_ACTIVITY.equals(action);}
        String getKey() { return country;}
        void dereference(){
            imgDiff = null;
            action= null;
			imgStatus = null;
            country= null;
            pubfrom = null;
            pubto = null;
            desc = null;
            fname = null;
        }

        public String toString() {
            return imgDiff.getKey()+" "+getKey()+" action:"+action;
        }
    }


    /*******************************
    * one for every CATNAV.COUNTRYLIST
    */
    private static class CtryRecord {
        private String action= null;
        private String country;
        private Vector imgRecVct;

        boolean isDisplayable() {return action!=null;} // only display those with filled in actions

        CtryRecord(Vector vct,String ctry){
            country = ctry;
            imgRecVct = vct;
            if (imgRecVct != null){
				// check for an action
				for (int i=0; i<imgRecVct.size(); i++){
					ImgRecord imgrec = (ImgRecord)imgRecVct.elementAt(i);
					if (imgrec.isDisplayable()){
						action = UPDATE_ACTIVITY;
						break;
					}
				}
			}
        }
        void setAction(String s) {  action = s; }

        String getAction() { return action;}
        String getCountry() { return country;}
        Vector getImgRecVct() { return imgRecVct;}

        boolean isDeleted() { return DELETE_ACTIVITY.equals(action);}
        String getKey() { return country;}
        void dereference(){
            if (imgRecVct != null){
				for (int i=0; i<imgRecVct.size(); i++){
					ImgRecord imgrec = (ImgRecord)imgRecVct.elementAt(i);
					imgrec.dereference();
				}
			}

            imgRecVct = null;
            action= null;
            country= null;
        }

        public String toString() {
            return getKey()+" action:"+action;
        }
    }
}
