package COM.ibm.eannounce.abr.sg.adsxmlbh1;

//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 *
 1	1.0	<?xml version="1.0" encoding="utf-8"?>		1		
 1	1.0	<TMF_UPDATE xmlns="http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/TMF_UPDATE">		1		
 1	1.0	<PDHDOMAIN>	</PDHDOMAIN>	2	PRODSTRUCT	PDHDOMAIN
 1	1.0	<DTSOFMSG>	</DTSOFMSG>	2	PRODSTRUCT	ABR Queued
 1	1.0	<ACTIVITY>	</ACTIVITY>	2	PRODSTRUCT	'Update'
 1	1.0	<STATUS>	</STATUS>	2	PRODSTRUCT	STATUS
 1	1.0	<ENTITYTYPE>	</ENTITYTYPE>	2	PRODSTRUCT	ENTITYTYPE
 1	1.0	<ENTITYID>	</ENTITYID>	2	PRODSTRUCT	ENTITYID
 <MODELENTITYTYPE>	</MODELENTITYTYPE>	2	PRODSTRUCT	ENTITY2TYPE
 <MODELENTITYID>	</MODELENTITYID>	2	PRODSTRUCT	ENTITY2ID
 1	1.0	<MACHTYPE>	</MACHTYPE>	2	MODEL	MACHTYPEATR
 1	1.0	<MODEL>	</MODEL>	2	MODEL	MODELATR
 1	1.0	<FEATUREENTITYTYPE>	</FEATUREENTITYTYPE>	2		
 1	1.0	<FEATUREENTITYID>	</FEATUREENTITYID>	2		
 1	1.0	<FEATURECODE>	</FEATURECODE>	2	FEATURE	FEATURECODE
 1	1.0	<ORDERCODE>	</ORDERCODE>	2	PRODSTRUCT	ORDERCODE
 1	1.0	<SYSTEMMAX>	</SYSTEMMAX>	2	PRODSTRUCT	SYSTEMMAX
 1	1.0	<SYSTEMMIN>	</SYSTEMMIN>	2	PRODSTRUCT	SYSTEMMIN
 1	1.0	<CONFIGURATORFLAG>	</CONFIGURATORFLAG>	2	PRODSTRUCT	CONFIGURATORFLAG
 1	1.0	<BULKMESINDC>	</BULKMESINDC>	2	PRODSTRUCT	BULKMESINDC
 1	1.0	<INSTALL>	</INSTALL>	2	PRODSTRUCT	INSTALL
 1	1.0	<OSLIST>		2	PRODSTRUCT	
 0..N	1.0	<OSELEMENT>		3		
 1	1.0	<OSACTION>	</OSACTION>	4	derived	OSAction
 1	1.0	<OS>	</OS>	4	PRODSTRUCT	OSLEVEL
 1	1.0		</OSELEMENT>	3		
 1	1.0		</OSLIST>	2		
 1	1.0	<LANGUAGELIST>		2		
 0..N	1.0	<LANGUAGEELEMENT>		3		
 1	1.0	<NLSID>	</NLSID>	4		
 1	1.0	<MKTGNAME>	</MKTGNAME>	4	"PRODSTRUCT
 "	MKTGNAME
 1	1.0	<INVNAME>	</INVNAME>	4	"PRODSTRUCT
 "	INVNAME
 1	1.0		</LANGUAGEELEMENT>	3		
 1	1.0		</LANGUAGELIST>	2		
 1	1.0	<AVAILABILITYLIST>		2	 	
 0..N	1.0	<AVAILABILITYELEMENT>		3		
 1	1.0	<AVAILABILITYACTION>	</AVAILABILITYACTION>	4	derived	AvailabilityAction
 1	1.0	<STATUS>	</STATUS>	4	AVAIL	STATUS
 1	1.0	<COUNTRY_FC>	</COUNTRY_FC>	4	AVAIL	COUNTRYLIST
 1	1.0	<ANNDATE>	</ANNDATE>	4		
 1	1.0	<ANNNUMBER>	</ANNNUMBER>	4		
 1	1.0	<FIRSTORDER>	</FIRSTORDER>	4		
 1	1.0	<PLANNEDAVAILABILITY>	</PLANNEDAVAILABILITY>	4		
 1	1.0	<PUBFROM>	</PUBFROM>	4		
 1	1.0	<PUBTO>	</PUBTO>	4		
 1	1.0	<WDANNDATE>	</WDANNDATE>	4		
 1	1.0	<LASTORDER>	</LASTORDER>	4		
 1	1.0	<EOSANNDATE>	</EOSANNDATE>	4		
 1	1.0	<ENDOFSERVICEDATE>	</ENDOFSERVICEDATE>	4	AVAIL 	EFFECTIVEDATE
 1	1.0		</AVAILABILITYELEMENT>	3		
 1	1.0		</AVAILABILITYLIST>	2		
 1	1.0	<AUDIENCELIST>	</AUDIENCELIST>	2		
 0..N	1.0	<AUDIENCEELEMENT>	</AUDIENCEELEMENT>	2		
 1.0	<AUDIENCEACTION>	</AUDIENCEACTION>	2	derived	AudienceAction
 1	1.0	<AUDIENCE>	</AUDIENCE>	2	PRODSTRUCT	AUDIEN
 1	1.0	<CATALOGOVERRIDELIST>		2	 	
 0..N	1.0	<CATALOGOVERRIDEELEMENT>		3		
 1	1.0	<CATALOGOVERRIDEACTION>	</CATALOGOVERRIDEACTION>	4	derived	CountryAudienceAction
 1	1.0	<AUDIENCE>	</AUDIENCE>	4	CATLGOR	AUDIEN
 1	1.0	<COUNTRY_FC>	</COUNTRY_FC>	4	CATLGOR	COUNTRYLIST
 1	1.0	<PUBFROM>	</PUBFROM>	4	CATLGOR	PUBFROM
 1	1.0	<PUBTO>	</PUBTO>	4	CATLGOR	PUBTO
 1	1.0	<ADDTOCART>	</ADDTOCART>	4	CATLGOR	CATADDTOCART
 1	1.0	<BUYABLE>	</BUYABLE>	4	CATLGOR	CATBUYABLE
 1	1.0	<PUBLISH>	</PUBLISH>	4	CATLGOR	CATPUBLISH
 1	1.0	<CUSTOMIZEABLE>	</CUSTOMIZEABLE>	4	CATLGOR	CATCUSTIMIZE
 1	1.0	<HIDE>	</HIDE>	4	CATLGOR	CATHIDE
 1.0		</CATALOGOVERRIDEELEMENT>	3		
 1.0		</CATALOGOVERRIDELIST>	2		
 1	1.0	<WARRLIST>		2	WARR	
 0..N	1.0	<WARRELEMENT>		3		
 1	1.0	<WARRACTION>	</WARRACTION>	4	derived	WarrantyAction
 1	1.0	<WARRENTITYID>	</WARRENTITYID>	4	WARR	WARRENTITYID
 1	1.0	<WARRID>	</WARRID>	4	WARR	WARRID
 1	1.0	<EFFECTIVEDATE>	</EFFECTIVEDATE>	4	PRODSTRUCTWARR	EFFECTIVEDATE
 1	1.0	<ENDDATE>	</ENDDATE>	4	PRODSTRUCTWARR	ENDDATE
 1	1.0	<COUNTRYLIST>		4		
 0..N	1.0	<COUNTRYELEMENT>		5		
 1	1.0	<COUNTRYACTION>	</COUNTRYACTION>	6	derived	CountryAction
 1	1.0	<COUNTRY_FC>	</COUNTRY_FC>	6	PRODSTRUCTWARR	COUNTRYLIST
 1.0		</COUNTRYELEMENT>	5		
 1.0		</COUNTRYLIST>	4		
 1.0		</WARRELEMENT>	3		
 1.0		</WARRLIST>	2		
 1.0		</TMF_UPDATE>	1	 	
 */
// $Log: ADSPRODSTRUCTABR.java,v $
// Revision 1.18  2014/01/07 13:09:08  guobin
// Fix CR BH FS ABR XML System Feed Mapping 20131106b.doc , LANGUAGE Element NLSID MarketName and Invoice Name extract from FEATURE if no NLSID 1 .
//
// Revision 1.17  2013/11/19 11:50:08  guobin
// move mergelist to middleware.jar part of fix for CQ 227988
//
// Revision 1.16  2013/11/12 16:03:17  guobin
// delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.15  2013/05/16 13:21:28  wangyulo
// the BH CQ 196713 - change XML mapping for TMF_UPDATE/NOCSTSHIP to short description
//
// Revision 1.14  2012/12/13 02:26:52  wangyulo
// Defect 856833 -- TMF_UPDATE delta XML not generated which CONFIGURATORFLAG changed to Do Not Load - CQ 150129
//
// Revision 1.13  2012/09/17 12:58:32  guobin
// Defect 126674 fix Blank Machine Types in XML Cache
//
// Revision 1.12  2011/12/14 02:24:37  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.11  2011/11/10 07:26:40  guobin
// Fix Defect 598849 Invalid countrylist for PRODSTRUCT with no AVAIL. VE  PRODSTRUCT2 don't need to check Planed AVAIL
//
// Revision 1.10  2011/08/23 12:36:45  guobin
// //		Add Default Warranty attr change notename to PUBFROM and PUBTO
//
// Revision 1.9  2011/05/24 13:43:25  lucasrg
// Added <WARRSVCCOVR> (CQ36617)
//
// Revision 1.8  2011/02/15 10:59:49  lucasrg
// Applied mapping updates for DM Cycle 2
//
// Revision 1.7  2010/12/20 10:54:09  guobin
// add Catalog Override Defaults
//
// Revision 1.6  2010/10/29 15:18:05  rick
// changing MQCID again.
//
// Revision 1.5  2010/10/12 19:24:56  rick
// setting new MQCID value
//
// Revision 1.4  2010/09/23 15:22:12  rick
// fixing outer element to TMF_UPDATE
//
// Revision 1.3  2010/09/10 09:29:55  yang
// get CONFIGURATIORFLAG  Short description
//
// Revision 1.2  2010/08/27 10:04:55  yang
// get AVAIL form XMLTMFAVAILIlem
//
// Revision 1.1  2010/08/10 09:57:49  yang
// new create ADSPRODSTRUCTABR
//
// Revision 1.3  2008/05/28 13:46:08  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/07 19:28:40  wendy
// Allow for multiple up or downlinks when getting parent or childid
//
// Revision 1.1  2008/04/29 14:31:38  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSPRODSTRUCTABR extends XMLMQRoot {
	private static final XMLElem XMLMAP;

	static {
		XMLMAP = new XMLGroupElem("TMF_UPDATE");
		XMLMAP.addChild(new XMLVMElem("TMF_UPDATE","1"));
		// level2
		XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
		XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG")); // pull from profile.endofday
		XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
		//XMLMAP.addChild(new XMLElem("STATUS", "STATUS", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
		XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
		XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));

		XMLMAP.addChild(new XMLRelatorElem("MODELENTITYTYPE", "ENTITY2TYPE", "MODEL"));
		XMLMAP.addChild(new XMLRelatorElem("MODELENTITYID", "ENTITY2ID", "MODEL"));

		XMLElem model = new XMLGroupElem(null, "MODEL", "D:MODEL");
		XMLMAP.addChild(model);
		//model.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
		model.addChild(new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
		model.addChild(new XMLElem("MODEL", "MODELATR"));

		XMLMAP.addChild(new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "FEATURE"));
		XMLMAP.addChild(new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "FEATURE"));

		XMLElem feature = new XMLGroupElem(null, "FEATURE", "U:FEATURE");
		XMLMAP.addChild(feature);
		feature.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
		feature.addChild(new XMLElem("FCCAT", "HWFCCAT"));
		feature.addChild(new XMLElem("FCTYPE", "FCTYPE"));

		XMLMAP.addChild(new XMLElem("ORDERCODE", "ORDERCODE", XMLElem.SHORTDESC));
		XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
		XMLMAP.addChild(new XMLElem("SYSTEMMIN", "SYSTEMMIN"));
		XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", XMLElem.SHORTDESC));

		XMLMAP.addChild(new XMLElem("BULKMESINDC", "BULKMESINDC", XMLElem.ATTRVAL));
		XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL", XMLElem.ATTRVAL));
		
		//Add	CQ26599	22,30		1	1,0	<NOCSTSHIP>	</NOCSTSHIP>
		//Defect 948069--BH CQ 196713 - change XML mapping for TMF_UPDATE/NOCSTSHIP to short description
		XMLMAP.addChild(new XMLElem("NOCSTSHIP", "NOCSTSHIP", XMLElem.SHORTDESC));

		//Add	CQ36617	22.40		1	1.0	<WARRSVCCOVR>	</WARRSVCCOVR>
		XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", XMLElem.ATTRVAL));
		
		//<OSLIST>
		XMLElem list = new XMLElem("OSLIST");
		XMLMAP.addChild(list);
		XMLElem listelem = new XMLChgSetElem("OSELEMENT");
		list.addChild(listelem);
		listelem.addChild(new XMLMultiFlagElem("OS", "OSLEVEL", "OSACTION", XMLElem.FLAGVAL));

		//<LANGUAGELIST>
		XMLElem langlist = new XMLElem("LANGUAGELIST");
		XMLMAP.addChild(langlist);

		// level 3
		XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
		langlist.addChild(langelem);
		//level 4
		langelem.addChild(new XMLElem("NLSID", "NLSID"));

		//BH FS ABR XML System Feed Mapping 20131106b.doc TMF <LANGUAGELIST>
		langelem.addChild(new XMLMktgInvElem("MKTGNAME", "MKTGNAME","MKTGNAME",  "U:FEATURE"));
		//langelem.addChild(new XMLFnMktInvElem("MKTGNAME", "MKTGNAME",  "U:FEATURE"));
			
		// BH <INVNAME></INVNAME>  4 PRODSTRUCT INVNAME
		langelem.addChild(new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME",  "U:FEATURE"));
		//langelem.addChild(new XMLFnMktInvElem("INVNAME", "INVNAME",  "U:FEATURE"));

		//<AVAILBILITYLIST>

		list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLTMFAVAILElem());

		//1.0	<AUDIENCELIST>		2 
		list = new XMLElem("AUDIENCELIST");
		XMLMAP.addChild(list);
		//1.0	<AUDIENCEELEMENT>		3
		XMLElem audielem = new XMLChgSetElem("AUDIENCEELEMENT");
		list.addChild(audielem);
		//1.0	<AUDIENCEACTION>	</AUDIENCEACTION>	4
		//1.0	<AUDIENCE>	</AUDIENCE>	4			MODEL	AUDIEN
		audielem.addChild(new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", XMLElem.ATTRVAL));

        //add 20101217 Level 2  Catalog Override Defaults
		
		//Delete - not required for TMF	BH CR MDM 035	59,10		1	1,0	<DEFAULTADDTOCART>
		//Delete - not required for TMF	BH CR MDM 035	59,20		1	1,0	<DEFAULTBUYABLE>
		//Delete - not required for TMF	BH CR MDM 035	59,30		1	1,0	<DEFAULTCUSTOMIZEABLE>
		//Delete - not required for TMF	BH CR MDM 035	59,40		1	1,0	<DEFAULTHIDE>
        //XMLMAP.addChild(new XMLElem("DEFAULTADDTOCART","DEFAULTADDTOCART"));
        //XMLMAP.addChild(new XMLElem("DEFAULTBUYABLE","DEFAULTBUYABLE"));
        //XMLMAP.addChild(new XMLElem("DEFAULTCUSTOMIZEABLE","DEFAULTCUSTOMIZEABLE"));
        //XMLMAP.addChild(new XMLElem("DEFAULTHIDE","DEFAULTHIDE"));		
		
		//1.0 <CATALOGOVERRIDELIST>

		list = new XMLElem("CATALOGOVERRIDELIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCATAElem());

		//1.0 <WARRLIST> structure
		list = new XMLGroupElem("WARRLIST", "WARR");
		XMLMAP.addChild(list);
		// level 3
		XMLElem warrelem = new XMLElem("WARRELEMENT");//check for chgs is controlled by XMLGroupElem
		list.addChild(warrelem);
		// level 4
		warrelem.addChild(new XMLActivityElem("WARRACTION"));
		warrelem.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
		warrelem.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
		warrelem.addChild(new XMLElem("WARRID", "WARRID"));
		XMLElem modelwarrelem = new XMLGroupElem(null, "PRODSTRUCTWARR", "U:PRODSTRUCTWARR");
		warrelem.addChild(modelwarrelem);
//		Add Default Warranty attr change notename to PUBFROM and PUBTO
		modelwarrelem.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
		modelwarrelem.addChild(new XMLElem("PUBTO", "ENDDATE"));
		
		modelwarrelem.addChild(new XMLElem("DEFWARR", "DEFWARR"));
		list = new XMLElem("COUNTRYLIST");
		warrelem.addChild(list);
		// level 5
		listelem = new XMLChgSetElem("COUNTRYELEMENT");
		list.addChild(listelem);
		XMLElem modelwarr2elem = new XMLGroupElem(null, "PRODSTRUCTWARR", "U:PRODSTRUCTWARR");
		listelem.addChild(modelwarr2elem);
		//Need to confirm whether exist this attribute <COUNTRY_FC>	</COUNTRY_FC>	6			PRODSTRUCTWARR	COUNTRYLIST
		modelwarr2elem.addChild(new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", XMLElem.FLAGVAL));

	}

	/**********************************
	 * check if xml should be created for this
	 * CONFIGURATORFLAG where not equal to "Feature code/RPQ is not passed (N)"
	 CONFIGURATORFLAG0040:FlagCode:0040:Feature code/RPQ is not passed (N)
	 */
	//Defect 856833 -- TMF_UPDATE delta XML not generated which CONFIGURATORFLAG changed to Do Not Load - CQ 150129
//	public boolean createXML(EntityItem rootItem) {
//		return !("0040".equals(PokUtils.getAttributeFlagValue(rootItem, "CONFIGURATORFLAG")));
//	}

	/**********************************
	 * get xml object mapping
	 */
	public XMLElem getXMLMap() {
		return XMLMAP;
	}

	/**********************************
	 * get the name of the VE to use
	 */
	public String getVeName() {
		return "ADSPRODSTRUCT";
	}
     
	
	public String getVeName2(){
		return "ADSPRODSTRUCT2";
	}
	/**********************************
	 * get the status attribute to use for this ABR
	 */
	public String getStatusAttr() {
		return "STATUS";
	}

	/**********************************
	 *
	 A.	MQ-Series CID
	 */
	public String getMQCID() {
		return "TMF_UPDATE";
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion() {
		return "$Revision: 1.18 $";
	}
	
	 /* (non-Javadoc)
     * @see COM.ibm.eannounce.abr.sg.adsxmlbh1.XMLMQAdapter#mergeLists(COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSABRSTATUS, COM.ibm.eannounce.objects.EntityList, COM.ibm.eannounce.objects.EntityList)
     */
    protected void mergeLists(ADSABRSTATUS abr, EntityList list1, EntityList list2) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	abr.addDebug("Entered ADSPRODSTRUCTABR call COM.ibm.eannounce.objects.EntityList.mergeLists");

    	COM.ibm.eannounce.objects.EntityList.mergeLists(list1,list2);
    	abr.addDebug("mergeLists:: after merge Extract "+PokUtils.outputList(list1));
    }
        //loop thru list1 and list2 and collect any items that have the same key
//        Hashtable existTbl = new Hashtable();
//        EntityItem lseoparent = list1.getParentEntityGroup().getEntityItem(0);
//    	existTbl.put(lseoparent.getKey(), lseoparent);
//    	
//        for(int i=0; i<list1.getEntityGroupCount(); i++){
//        	EntityGroup eg = list1.getEntityGroup(i);
//        	EntityGroup eg2= list2.getEntityGroup(eg.getEntityType());
//        	if(eg2!=null){
//        		for(int e=0; e<eg2.getEntityItemCount(); e++){
//        			EntityItem item = eg2.getEntityItem(e);
//        			EntityItem item1 = eg.getEntityItem(item.getKey());
//        			if(item1!=null){
//        				existTbl.put(item1.getKey(), item1);
//        			}
//        		}
//        	}
//        }
//        
//        EntityGroup egArray[] = new EntityGroup[list2.getEntityGroupCount()];
//        for(int i=0; i<list2.getEntityGroupCount(); i++){
//        	EntityGroup eg = list2.getEntityGroup(i);
//        	egArray[i]=eg;
//        }
//        // merge lists by moving entitygroups or entityitems or up and downlinks to existing items
//        for(int i=0; i<egArray.length; i++){
//        	EntityGroup eg = egArray[i];
//        	mergeItems(abr,list1, list2, eg.getEntityType(),existTbl);
//        	egArray[i] = null;
//        }
//       	
//       	// release memory
//        egArray = null;
//       	existTbl.clear();
//       	existTbl = null;
//        list2.dereference();
//        
        
//    }
//    
//    /**
//     * merge items for specified entitygroup type
//     * @param abr
//     * @param list1
//     * @param list2
//     * @param groupname
//     * @param existTbl
//     */
//    private void mergeItems(ADSABRSTATUS abr,EntityList list1, EntityList list2, String groupname, Hashtable existTbl){
//        // add all entities from new list2 to first pull list1
//        EntityGroup origGrp = list1.getEntityGroup(groupname);
//        EntityGroup newGrp = list2.getEntityGroup(groupname);
//        if(groupname.equals(list2.getParentEntityGroup().getEntityType())){
//    		newGrp = list2.getParentEntityGroup();
//    	}
//        if(origGrp!=null){
//        	//list1 entitygroup existed 
//        	EntityItem eiArray[] = newGrp.getEntityItemsAsArray();
//        	for (int i=0;i<eiArray.length; i++) {
//        		EntityItem item = eiArray[i];
//        		EntityItem list1Item = origGrp.getEntityItem(item.getKey());
//        		if(list1Item==null){
//        			// put it in the orig list group
//        			origGrp.putEntityItem(item);
//        			// must move metaattr to new group too
//        			item.reassign(origGrp); 
//        			// remove it from new list
//        			newGrp.removeEntityItem(item);
//        			//move any links to existing items
//        			moveLinks(abr,list1Item, item, existTbl);
//        		}else{ 
//        			//entityitem already existed
//        			moveLinks(abr,list1Item, item, existTbl);	
//        		}
//        	}
//        }else{ 
//        	// group did not exist in list1
//        	list2.removeEntityGroup(newGrp);
//        	list1.putEntityGroup(newGrp);
//        	for (int i=0;i<newGrp.getEntityItemCount(); i++) {
//        		EntityItem item = newGrp.getEntityItem(i);
//        		//move any links to existing items
//        		moveLinks(abr,null, item, existTbl);
//        	}
//        }
//    }
//
//    /**
//     * move any links from item2 to item1
//     * @param abr
//     * @param list1Item
//     * @param item2
//     * @param existTbl
//     */
//    private void moveLinks(ADSABRSTATUS abr,EntityItem list1Item, EntityItem item2, Hashtable existTbl)
//    { 
//    	if(list1Item==null){
//        	//item did not exist in list1, the item2 was added to list1 entitygroup
//    		//move any links to list1 items if they already existed
//        	// does any of the item2 links refer to an existing key in list1
//        	if(item2.getUpLinkCount()>0){
//    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
//    			item2.getUpLink().copyInto(upArray);
//    			for (int j=0; j<upArray.length; j++){
//    				EntityItem upItem = upArray[j]; 
//    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
//    				if(upitem1!=null){ // entity existed like MODEL, move up link to it from list2
//    					// remove from list2 item and add to the list1 item
//        				item2.removeUpLink(upItem);
//        				item2.putUpLink(upitem1);
//    				}
//
//    				upArray[j]=null;
//    			}
//    			upArray = null;
//    		}
//    		if(item2.getDownLinkCount()>0){  
//    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
//    			item2.getDownLink().copyInto(downArray);
//    			for (int j=0; j<downArray.length; j++){
//    				EntityItem dnItem = downArray[j]; 
//    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
//    				if(dnitem1!=null){ // entity existed like MODEL, move down link to it from list2
//    					// remove from list2 item and add to the list1 item
//        				item2.removeDownLink(dnItem);
//        				item2.putDownLink(dnitem1);
//    				}
//    				
//    				downArray[j]=null;
//    			}
//    			downArray = null;
//    		}
//    	}else{
//    		//entity already exists in list1, move up and downlinks to list1 from list2 if they dont exist already
//    		if(item2.getUpLinkCount()>0){
//    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
//    			item2.getUpLink().copyInto(upArray);
//    			for (int j=0; j<upArray.length; j++){
//    				EntityItem upItem = upArray[j]; 
//    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
//    				if(upitem1==null){
//    					// remove  from list2  item and add to list1 item
//        				item2.removeUpLink(upItem);
//        				list1Item.putUpLink(upItem);
//    				}
//    	
//    				upArray[j]=null;
//    			}
//    			upArray = null;
//    		}
//    		if(item2.getDownLinkCount()>0){  
//    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
//    			item2.getDownLink().copyInto(downArray);
//    			for (int j=0; j<downArray.length; j++){
//    				EntityItem dnItem = downArray[j]; 
//    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
//    				if(dnitem1==null){
//    					// remove  from list2  item and add to list1 item
//    					item2.removeDownLink(dnItem);
//    					list1Item.putDownLink(dnItem);
//    				}
//    				downArray[j]=null;
//    			}
//    			downArray = null;
//    		}
//    	}
//    }
}
