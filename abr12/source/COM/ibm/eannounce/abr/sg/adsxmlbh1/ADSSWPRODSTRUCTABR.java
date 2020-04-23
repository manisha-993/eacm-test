package COM.ibm.eannounce.abr.sg.adsxmlbh1;

//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

import java.util.Hashtable;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;

/**********************************************************************************
 *
 1	<SWPRODSTRUCT_UPDATE>		1	SWPRODSTRUCT
 1	<PDHDOMAIN>	</PDHDOMAIN>	2	SWPRODSTRUCT	PDHDOMAIN
 1	<DTSOFMSG>	</DTSOFMSG>		2	SWPRODSTRUCT	ABR Queued
 1	<ACTIVITY>	</ACTIVITY>		2	SWPRODSTRUCT	'Update'
 1	<STATUS>	</STATUS>		2	SWPRODSTRUCT	STATUS
 1	<ENTITYTYPE>	</ENTITYTYPE>
 1   <ENTITYID>	</ENTITYID>
 1   <MODELENTITYTYPE>	</MODELENTITYTYPE>
 1   <MODELENTITYID>	</MODELENTITYID>
 1   <MACHTYPE>	</MACHTYPE>
 1   <MODEL>	</MODEL>
 1   <FEATUREENTITYTYPE>	</FEATUREENTITYTYPE> SWFEATURE
 1   <FEATUREENTITYID>	</FEATUREENTITYID>
 1   <FEATURECODE>	</FEATURECODE>
 1   <FCCAT>	</FCCAT>
 1   <FCTYPE>	</FCTYPE>
 1   <ORDERCODE>	</ORDERCODE>  n/a	n/a 
 1   <SYSTEMMAX>	</SYSTEMMAX>  n/a	n/a
 1   <SYSTEMMIN>	</SYSTEMMIN>  n/a	n/a
 1   <CONFIGURATORFLAG>	</CONFIGURATORFLAG>
 1   <BULKMESINDC>	</BULKMESINDC> n/a	n/a
 1   <INSTALL>	</INSTALL>  n/a	n/a
 1	<OSLIST>					2	n/a	n/a
 0..N	<OSELEMENT>				3
 1	<OSACTION>	</OSACTION>		4	n/a	n/a
 1	<OS>	</OS>				4	n/a	n/a
 </OSELEMENT>			3
 </OSLIST>				2
 1	1.0	<LANGUAGELIST>		2		
 0..N	1.0	<LANGUAGEELEMENT>		3		
 1	1.0	<NLSID>	</NLSID>	4		
 1	1.0	<MKTGNAME>	</MKTGNAME>	4	"SWPRODSTRUCT
 "	MKTGNAME
 1	1.0	<INVNAME>	</INVNAME>	4	"SWPRODSTRUCT
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
 </SWPRODSTRUCT_UPDATE>	1
 *
 */
// $Log: ADSSWPRODSTRUCTABR.java,v $
// Revision 1.12  2014/03/25 15:01:40  guobin
// SWPRODSTRUCT INVNAME update- BH FS ABR XML System Feed 20130904.doc
//
// Revision 1.11  2013/11/12 16:03:17  guobin
// delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
// Revision 1.10  2012/09/17 12:59:33  guobin
// Defect 126674 fix Blank Machine Types in XML Cache
//
// Revision 1.9  2012/08/02 13:46:27  wangyulo
// fix the defect 770704- BH Defect BHALM109267 - correction to SWPRODSTRUCT for old data
//
// Revision 1.8  2011/12/14 02:26:22  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.7  2010/11/02 23:05:46  rick
// adding SYSTEMMAX attr for SWPRODSTRUCT
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
// Revision 1.3  2010/09/21 14:05:12  rick
// change for INVNAME element
//
// Revision 1.2  2010/09/10 09:29:43  yang
// get CONFIGURATIORFLAG  Short description
//
// Revision 1.1  2010/08/27 10:05:28  yang
// Initial ADSSWPRODSTRUCTABR
//
// Revision 1.3  2008/05/28 13:46:09  wendy
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
public class ADSSWPRODSTRUCTABR extends XMLMQRoot {
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

		XMLMAP.addChild(new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "SWFEATURE"));
		XMLMAP.addChild(new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "SWFEATURE"));

		XMLElem feature = new XMLGroupElem(null, "SWFEATURE", "U:SWFEATURE");
		XMLMAP.addChild(feature);
		feature.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
		feature.addChild(new XMLElem("FCCAT", "SWFCCAT"));
		feature.addChild(new XMLElem("FCTYPE", "FCTYPE"));

		XMLMAP.addChild(new XMLElem("ORDERCODE"));
		XMLMAP.addChild(new XMLElem("SYSTEMMAX","SYSTEMMAX"));
		XMLMAP.addChild(new XMLElem("SYSTEMMIN"));
		XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG",XMLElem.SHORTDESC));

		XMLMAP.addChild(new XMLElem("BULKMESINDC"));
		XMLMAP.addChild(new XMLElem("INSTALL"));
		XMLMAP.addChild(new XMLElem("OSLIST"));

		//<LANGUAGELIST>
		XMLElem langlist = new XMLElem("LANGUAGELIST");
		XMLMAP.addChild(langlist);

		// level 3
		XMLElem langelem = new XMLNLSElem("LANGUAGEELEMENT");
		langlist.addChild(langelem);
		//level 4
		langelem.addChild(new XMLElem("NLSID", "NLSID"));

		//1.0	<MKTGNAME>	</MKTGNAME>	4			PRODSTRUCT	MKTGNAME 
		langelem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));

		// BH <INVNAME></INVNAME>  4 SWPRODSTRUCT INVNAME or SWFEATURE INVNAME
		//langelem.addChild(new XMLINVNameElem("INVNAME", "INVNAME", "INVNAME", "U:SWFEATURE"));
		//SWPRODSTRUCT INVNAME update- BH FS ABR XML System Feed 20130904.doc
		langelem.addChild(new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME",  "U:SWFEATURE"));					


		//<AVAILBILITYLIST>
		XMLElem list = new XMLElem("AVAILABILITYLIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLTMFAVAILElem());

		//1.0 <CATALOGOVERRIDELIST>
		list = new XMLElem("CATALOGOVERRIDELIST");
		XMLMAP.addChild(list);
		list.addChild(new XMLCATAElem());

	}

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
		return "ADSSWPRODSTRUCT";
	}
	
	/**********************************
	 * get the name of the VE to use
	 */
	public String getVeName2() {
		return "ADSSWPRODSTRUCT2";
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
		return "$Revision: 1.12 $";
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
    	abr.addDebug("Entered ADSLSEOABR.mergeLists");

        //loop thru list1 and list2 and collect any items that have the same key
        Hashtable existTbl = new Hashtable();
        EntityItem lseoparent = list1.getParentEntityGroup().getEntityItem(0);
    	existTbl.put(lseoparent.getKey(), lseoparent);
    	
        for(int i=0; i<list1.getEntityGroupCount(); i++){
        	EntityGroup eg = list1.getEntityGroup(i);
        	EntityGroup eg2= list2.getEntityGroup(eg.getEntityType());
        	if(eg2!=null){
        		for(int e=0; e<eg2.getEntityItemCount(); e++){
        			EntityItem item = eg2.getEntityItem(e);
        			EntityItem item1 = eg.getEntityItem(item.getKey());
        			if(item1!=null){
        				existTbl.put(item1.getKey(), item1);
        			}
        		}
        	}
        }
        
        EntityGroup egArray[] = new EntityGroup[list2.getEntityGroupCount()];
        for(int i=0; i<list2.getEntityGroupCount(); i++){
        	EntityGroup eg = list2.getEntityGroup(i);
        	egArray[i]=eg;
        }
        // merge lists by moving entitygroups or entityitems or up and downlinks to existing items
        for(int i=0; i<egArray.length; i++){
        	EntityGroup eg = egArray[i];
        	mergeItems(abr,list1, list2, eg.getEntityType(),existTbl);
        	egArray[i] = null;
        }
       	
       	// release memory
        egArray = null;
       	existTbl.clear();
       	existTbl = null;
        list2.dereference();
        
        abr.addDebug("mergeLists:: after merge Extract "+PokUtils.outputList(list1));
    }
    
    /**
     * merge items for specified entitygroup type
     * @param abr
     * @param list1
     * @param list2
     * @param groupname
     * @param existTbl
     */
    private void mergeItems(ADSABRSTATUS abr,EntityList list1, EntityList list2, String groupname, Hashtable existTbl){
        // add all entities from new list2 to first pull list1
        EntityGroup origGrp = list1.getEntityGroup(groupname);
        EntityGroup newGrp = list2.getEntityGroup(groupname);
        if(groupname.equals(list2.getParentEntityGroup().getEntityType())){
    		newGrp = list2.getParentEntityGroup();
    	}
        if(origGrp!=null){
        	//list1 entitygroup existed 
        	EntityItem eiArray[] = newGrp.getEntityItemsAsArray();
        	for (int i=0;i<eiArray.length; i++) {
        		EntityItem item = eiArray[i];
        		EntityItem list1Item = origGrp.getEntityItem(item.getKey());
        		if(list1Item==null){
        			// put it in the orig list group
        			origGrp.putEntityItem(item);
        			// must move metaattr to new group too
        			item.reassign(origGrp); 
        			// remove it from new list
        			newGrp.removeEntityItem(item);
        			//move any links to existing items
        			moveLinks(abr,list1Item, item, existTbl);
        		}else{ 
        			//entityitem already existed
        			moveLinks(abr,list1Item, item, existTbl);	
        		}
        	}
        }else{ 
        	// group did not exist in list1
        	list2.removeEntityGroup(newGrp);
        	list1.putEntityGroup(newGrp);
        	for (int i=0;i<newGrp.getEntityItemCount(); i++) {
        		EntityItem item = newGrp.getEntityItem(i);
        		//move any links to existing items
        		moveLinks(abr,null, item, existTbl);
        	}
        }
    }

    /**
     * move any links from item2 to item1
     * @param abr
     * @param list1Item
     * @param item2
     * @param existTbl
     */
    private void moveLinks(ADSABRSTATUS abr,EntityItem list1Item, EntityItem item2, Hashtable existTbl)
    { 
    	if(list1Item==null){
        	//item did not exist in list1, the item2 was added to list1 entitygroup
    		//move any links to list1 items if they already existed
        	// does any of the item2 links refer to an existing key in list1
        	if(item2.getUpLinkCount()>0){
    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
    			item2.getUpLink().copyInto(upArray);
    			for (int j=0; j<upArray.length; j++){
    				EntityItem upItem = upArray[j]; 
    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
    				if(upitem1!=null){ // entity existed like MODEL, move up link to it from list2
    					// remove from list2 item and add to the list1 item
        				item2.removeUpLink(upItem);
        				item2.putUpLink(upitem1);
    				}

    				upArray[j]=null;
    			}
    			upArray = null;
    		}
    		if(item2.getDownLinkCount()>0){  
    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
    			item2.getDownLink().copyInto(downArray);
    			for (int j=0; j<downArray.length; j++){
    				EntityItem dnItem = downArray[j]; 
    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
    				if(dnitem1!=null){ // entity existed like MODEL, move down link to it from list2
    					// remove from list2 item and add to the list1 item
        				item2.removeDownLink(dnItem);
        				item2.putDownLink(dnitem1);
    				}
    				
    				downArray[j]=null;
    			}
    			downArray = null;
    		}
    	}else{
    		//entity already exists in list1, move up and downlinks to list1 from list2 if they dont exist already
    		if(item2.getUpLinkCount()>0){
    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
    			item2.getUpLink().copyInto(upArray);
    			for (int j=0; j<upArray.length; j++){
    				EntityItem upItem = upArray[j]; 
    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
    				if(upitem1==null){
    					// remove  from list2  item and add to list1 item
        				item2.removeUpLink(upItem);
        				list1Item.putUpLink(upItem);
    				}
    	
    				upArray[j]=null;
    			}
    			upArray = null;
    		}
    		if(item2.getDownLinkCount()>0){  
    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
    			item2.getDownLink().copyInto(downArray);
    			for (int j=0; j<downArray.length; j++){
    				EntityItem dnItem = downArray[j]; 
    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
    				if(dnitem1==null){
    					// remove  from list2  item and add to list1 item
    					item2.removeDownLink(dnItem);
    					list1Item.putDownLink(dnItem);
    				}
    				downArray[j]=null;
    			}
    			downArray = null;
    		}
    	}
    }
	
}
