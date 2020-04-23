// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import com.ibm.transform.oim.eacm.diff.*;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
/**********************************************************************************
* this must be done here, the map cannot be used because there are multiple
MODEL2MODEL elements for one root MODELCG based on operating systems
1	<MODELCG_UPDATE>						1
1	<DTSOFMSG>	</DTSOFMSG>					2				Date/Time Stamp of Message
1..N	<MODEL2MODEL>						2
1	<PDHDOMAIN>	</PDHDOMAIN>				3	MODELCG	PDHDOMAIN
1	<ACTIVITY>	</ACTIVITY>					3		ACTIVITY	Derived	Update | Delete
1	<UPDATED>	</UPDATED>					3		valfrom	Derived	MAX of the various DTS
1	<SYSTEMENTITYTYPE>	</SYSTEMENTITYTYPE>	3	MODEL	ENTITYTYPE	'MODEL'
1	<SYSTEMENTITYID>	</SYSTEMENTITYID>	3	MODEL	ENTITYID
1	<SYSTEMOS>	</SYSTEMOS>					3	MODEL	OSLEVEL	Flag Description Class
				MODEL.COFGRP		COFGRP = "Base" & COFCAT <> "Service"
1	<GROUPENTITYTYPE>	</GROUPENTITYTYPE>	3	MODELCG	ENTITYTYPE	'MODELCG'
1	<GROUPENTITYID>	</GROUPENTITYID>		3	MODELCG	ENTITYID
1	<OKTOPUB>	</OKTOPUB>					3	MODELCG	OKTOPUB		{Y | N}
1	<GROUPOSENTITYTYPE>	</GROUPOSENTITYTYPE>	3	MODELCGOS	ENTITYTYPE	'MODELCGOS'
1	<GROUPOSENTITYID>	</GROUPOSENTITYID>	3	MODELCGOS	ENTITYID
1	<OPTIONOS>	</OPTIONOS>					3	MODELCGOS	OS
1	<OPTIONENTITYTYPE>	</OPTIONENTITYTYPE>	3	MODEL	ENTITYTYPE	'MODEL'
1	<OPTIONENTITYID>	</OPTIONENTITYID>	3	MODEL	ENTITYID
1	<COMPATIBILITYPUBLISHINGFLAG>	</COMPATIBILITYPUBLISHINGFLAG>	3	MDLCGOSMDL	COMPATPUBFLG		{No; Yes}
1	<RELATIONSHIPTYPE>	</RELATIONSHIPTYPE>	3	MDLCGOSMDL	RELTYPE	Description Class
1	<PUBLISHFROM>	</PUBLISHFROM>			3	MDLCGOSMDL	PUBFROM
1	<PUBLISHTO>	</PUBLISHTO>				3	MDLCGOSMDL	PUBTO
		</MODEL2MODEL>						2
		</MODELCG_UPDATE>					1

*/
// ADSWWCOMPATABR.java,v
// Revision 1.3  2008/05/28 13:46:07  wendy
// updates for spec "SG FS ABR ADS System Feed 20080528c.doc"
//
// Revision 1.2  2008/05/27 14:28:59  wendy
// Clean up RSA warnings
//
// Revision 1.1  2008/05/03 23:32:41  wendy
//  Init for
//   -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//   -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//   -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//   -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//   -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class ADSWWCOMPATABR extends XMLMQChanges
{
//from		  rel			to
//MODELCG	  MDLCGMDL		MODEL
//MODELCG	  MDLCGMDLCGOS	MODELCGOS
//MODELCGOS	  MDLCGOSMDL	MODEL

/*
ADSWWCOMPAT	MDLCGMDL		D	0
ADSWWCOMPAT	MDLCGMDLCGOS	D	0
ADSWWCOMPAT	MDLCGOSMDL		D	1

*/
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
		// look at ADSTYPE
		String etype = "MODELCG";

		// Find all instances of the VE Structure that changed between T1 and T2, and then
		// create XML for each instance where the structure has changed or an attribute of
		// interest has changed.
		Vector rootIds = getAffectedRoots(abr,getVeName(), profileT2, etype,
			profileT1.getValOn(), profileT2.getValOn());

		if (rootIds.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND",etype);
		}else{
			// get all of this at once to check pdhdomain
			EntityItem eiArray[] = new EntityItem[rootIds.size()];
			for (int i=0; i<rootIds.size(); i++){
				eiArray[i] = new EntityItem(null, profileT2, etype,
					Integer.parseInt((String)rootIds.elementAt(i)));
			}

			// pull just roots to check pdhdomain before doing anything else
			EntityList rootlist = abr.getDB().getEntityList(profileT2,
					new ExtractActionItem(null, abr.getDB(), profileT2,"dummy"),eiArray);

			EntityGroup eg = rootlist.getParentEntityGroup();
			for (int i=0; i<eg.getEntityItemCount(); i++){
				EntityItem item = eg.getEntityItem(i);
				abr.addDebug("ADSWWCOMPATABR checking root "+item.getKey());
				if (abr.domainNeedsChecks(item)){
					// build model2model xml
					processThis(abr, profileT1, profileT2, item, true);
				}else{
					abr.addXMLGenMsg("DOMAIN_NOT_LISTED",item.getKey());
				}
			}

			// release memory
			rootlist.dereference();
			for (int i=0; i<eiArray.length; i++){
				eiArray[i] = null;
			}
			eiArray = null;
			rootIds.clear();
		}
	}

    /***********************************************
    *  Get the xml
    *
    *@return java.lang.String
    */
    protected String generateXML(ADSABRSTATUS abr, Hashtable diffTbl)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException
    {
		String xml = null;
		Vector vct = (Vector)diffTbl.get("ROOT");
		DiffEntity parentItem = (DiffEntity)vct.firstElement();

		//use this difftbl and build one xml for each os
		Vector model2modelVct = getModelsByOS(abr,diffTbl);
		if (model2modelVct.size()==0){
			//NO_OS_MATCH_FND=No matches found in OS and OSLEVEL for {0}
			abr.addXMLGenMsg("NO_OS_MATCH_FND",parentItem.getKey());
		}else{
			abr.addDebug("ADSWWCOMPATABR.generateXML found "+model2modelVct.size()+" m2m for "+parentItem.getKey());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();  // Create
			Element root = (Element) document.createElement("MODELCG_UPDATE");
			// create the root
			document.appendChild(root);

			Element elem = (Element) document.createElement("DTSOFMSG");
			elem.appendChild(document.createTextNode(parentItem.getCurrentEntityItem().getProfile().getEndOfDay()));
			root.appendChild(elem);

			// create one MODEL2MODEL for each one found
			for (int i=0; i<model2modelVct.size(); i++){
				M2MInfo m2m = (M2MInfo)model2modelVct.elementAt(i);

				if (!m2m.isDisplayable()){
					abr.addDebug("No changes found in "+m2m);
					continue;
				}

				Element parent = (Element) document.createElement("MODEL2MODEL");
				root.appendChild(parent);

				elem = (Element) document.createElement("PDHDOMAIN");
				elem.appendChild(document.createTextNode(m2m.getPdhdomain()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("ACTIVITY");
				elem.appendChild(document.createTextNode(m2m.getActivity()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("SYSTEMENTITYTYPE");
				elem.appendChild(document.createTextNode(m2m.getSysType()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("SYSTEMENTITYID");
				elem.appendChild(document.createTextNode(m2m.getSysID()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("SYSTEMOS");
				elem.appendChild(document.createTextNode(m2m.getOS()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("GROUPENTITYTYPE");
				elem.appendChild(document.createTextNode(m2m.getGrpType()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("GROUPENTITYID");
				elem.appendChild(document.createTextNode(m2m.getGrpID()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("OKTOPUB");
				elem.appendChild(document.createTextNode(m2m.getOktopub()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("GROUPOSENTITYTYPE");
				elem.appendChild(document.createTextNode(m2m.getGrpOsType()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("GROUPOSENTITYID");
				elem.appendChild(document.createTextNode(m2m.getGrpOsID()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("OPTIONOS");
				elem.appendChild(document.createTextNode(m2m.getOptOS()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("OPTIONENTITYTYPE");
				elem.appendChild(document.createTextNode(m2m.getOptType()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("OPTIONENTITYID");
				elem.appendChild(document.createTextNode(m2m.getOptID()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("COMPATIBILITYPUBLISHINGFLAG");
				elem.appendChild(document.createTextNode(m2m.getCompatPubFlag()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("RELATIONSHIPTYPE");
				elem.appendChild(document.createTextNode(m2m.getRelType()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("PUBLISHFROM");
				elem.appendChild(document.createTextNode(m2m.getPubFrom()));
				parent.appendChild(elem);

				elem = (Element) document.createElement("PUBLISHTO");
				elem.appendChild(document.createTextNode(m2m.getPubTo()));
				parent.appendChild(elem);

				// release memory
				m2m.dereference();
			}

			xml = abr.transformXML(this, document);
// reduce file size	abr.addDebug("ADSWWCOMPATABR: Generated xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);

			// release memory
			model2modelVct.clear();
			document = null;
			factory = null;
			builder = null;
			root = null;
			elem = null;
			System.gc();
		}

		return xml;
    }

    /***********************************************
    *  Get the models by os
    */
	private Vector getModelsByOS(ADSABRSTATUS abr, Hashtable diffTbl)
	{
		Vector m2mVct = new Vector(1);
		Vector vct = (Vector)diffTbl.get("ROOT");
		DiffEntity parentItem = (DiffEntity)vct.firstElement();
		EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem previtem = parentItem.getPriorEntityItem();

		// MODELCG-MDLCGMDL-MODEL.OSLEVEL
		Vector currSysVct = PokUtils.getAllLinkedEntities(curritem, "MDLCGMDL", "MODEL");
		Vector prevSysVct = PokUtils.getAllLinkedEntities(previtem, "MDLCGMDL", "MODEL");

		// MODELCG-MDLCGMDLCGOS-MODELCGOS.OS
		Vector currOSVct = PokUtils.getAllLinkedEntities(curritem, "MDLCGMDLCGOS", "MODELCGOS");
		Vector prevOSVct = PokUtils.getAllLinkedEntities(previtem, "MDLCGMDLCGOS", "MODELCGOS");

		Hashtable osTbl = new Hashtable();

		buildOSTbl(abr, osTbl, diffTbl, parentItem,	currSysVct, currOSVct, true);
		buildOSTbl(abr, osTbl, diffTbl, parentItem,	prevSysVct, prevOSVct, false);

		prevSysVct.clear();
		currSysVct.clear();
		currOSVct.clear();
		prevOSVct.clear();
		if (osTbl.size()>0){
			m2mVct.addAll(osTbl.values());
			osTbl.clear();
		}
		return m2mVct;
	}

	private void buildOSTbl(ADSABRSTATUS abr, Hashtable osTbl, Hashtable diffTbl, DiffEntity parentItem,
		Vector sysmdlVct, Vector mdlcgosVct, boolean current)
	{
		for (int i=0; i<sysmdlVct.size(); i++){
			EntityItem mdlitem = (EntityItem)sysmdlVct.elementAt(i);
			DiffEntity diffmdlitem = (DiffEntity)diffTbl.get(mdlitem.getKey());
//			MODEL	COFGRP = "Base" & COFCAT <> "Service"
//150		Base
//102		Service

			String modelCOFCAT = abr.getAttributeFlagEnabledValue(mdlitem, "COFCAT");
			String modelCOFGRP = abr.getAttributeFlagEnabledValue(mdlitem, "COFGRP");
			abr.addDebug((current?"current ":"previous ")+ mdlitem.getKey()+" COFCAT: "+modelCOFCAT+" COFGRP: "+modelCOFGRP);

			if ("150".equals(modelCOFGRP) && !"102".equals(modelCOFCAT)){
				// get all oslevel values
				Vector osLvlVct = new Vector(1);
				EANFlagAttribute fAtt = (EANFlagAttribute)mdlitem.getAttribute("OSLEVEL");
				if (fAtt!=null && fAtt.toString().length()>0){
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i2 = 0; i2 < mfArray.length; i2++){
						// get selection
						if (mfArray[i2].isSelected()){
							osLvlVct.add(mfArray[i2].getFlagCode());
						}
					}
				}
				abr.addDebug((current?"current ":"previous ")+mdlitem.getKey()+" OSLEVEL: "+osLvlVct);
				if (osLvlVct.size()==0){
					continue; //if "null" - then no match
				}

				//MODELCGOS-MDLCGOSMDL-MODEL
				for (int os = 0; os<mdlcgosVct.size(); os++){
					// get OS
					EntityItem ositem = (EntityItem)mdlcgosVct.elementAt(os);
					DiffEntity mdlcgositem = (DiffEntity)diffTbl.get(ositem.getKey());
					String osStr = abr.getAttributeFlagEnabledValue(ositem, "OS");
					abr.addDebug((current?"current ":"previous ")+ositem.getKey()+" OS: "+osStr);

					//find match
					String[] oslvl = oslvlMatch(osStr, osLvlVct);
					if (oslvl != null){
						for (int d=0; d<ositem.getDownLinkCount(); d++){
							EANEntity relator = ositem.getDownLink(d);
							DiffEntity mdlcgosmdlitem = (DiffEntity)diffTbl.get(relator.getKey());
							for (int d2 =0; d2<relator.getDownLinkCount(); d2++){
								EANEntity optmodel = relator.getDownLink(d2);
								DiffEntity optmdlitem = (DiffEntity)diffTbl.get(optmodel.getKey());
								for(int x=0; x<oslvl.length; x++){
									String key = generateM2MKey(diffmdlitem,
											parentItem, oslvl[x],mdlcgositem, mdlcgosmdlitem, optmdlitem,osStr);
									if (!osTbl.containsKey(key)){
										M2MInfo m2m = new M2MInfo(diffmdlitem,
											parentItem, oslvl[x],mdlcgositem, mdlcgosmdlitem, optmdlitem,osStr);
										osTbl.put(key,m2m);
									}
								}
							}
						}
					}
				}

				osLvlVct.clear();
			}
		}
	}
	/***********************************
	A.	Handling OSLEVEL and OS

	In the following, children of 'Model Compatibility Group' (MODELCG) are referred to as 'MODEL (System)'
	and children of 'Compatibility Group By OS" (MODELCGOS) are referred to as 'MODEL (Option)'. Also, OS shows
	in the User Interface as 'Operating System' and OSLEVEL shows as 'OS Level'.

	There are three conditions that need to be considered. The conditions are considered in order and the first
	condition that applies is used.
	1.	MODEL (System).OSLEVEL = "OS Independent" (10589) or "None Specified" (10582), then pass this MODEL (System)
	with its OSLEVEL and all MODELCGOS children MODEL (Option) its 'OS'.
	2.	MODELCGOS.OS = "OS Independent" (10589 then pass this MODEL (System) with its OSLEVEL and this MODELCGOS
	children MODEL (Option) its 'OS'.
	3.	Neither of the preceding criteria is met, then if MODELCGOS.OS matches (is in) one of the values for MODEL
	(System).OSLEVEL, then pass this MODEL (System) with its OSLEVEL and this MODELCGOS children MODEL (Option) its 'OS'.

	If none of the above conditions is met, do not pass that MODELCGOS nor its MODEL (Option).

	*/
	private String[] oslvlMatch(String osStr, Vector osLvlVct){
		String matchval[] = null;
		if (osLvlVct.contains("10589")){
			matchval = new String[] {"10589"};
		}else if(osLvlVct.contains("10582")){
			matchval = new String[] {"10582"};
		}else if ("10589".equals(osStr)){
			matchval = new String[osLvlVct.size()];
			osLvlVct.copyInto(matchval);
		}else if (osStr !=null && osLvlVct.contains(osStr)){
			matchval = new String[] {osStr};
		}

		return matchval;
	}

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "MODELCG"; }

    /**********************************
    * get the name of the VE to use
    */
    public String getVeName() { return "ADSWWCOMPAT";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.3";
    }

	private static String generateM2MKey(DiffEntity mdlitem, DiffEntity mdlcgitem, String os,
			DiffEntity mdlcgositem, DiffEntity mdlcgosmdlitem, DiffEntity optmdlitem, String optos){
		return mdlitem.getKey()+"|"+mdlcgitem.getKey()+"|"+os+
				mdlcgositem.getKey()+"|"+mdlcgosmdlitem.getKey()+"|"+optmdlitem.getKey()+"|"+optos;
	}
    /***********************************************
    *  class for each os
	MODELCGOS	OS	U
	MODEL		OSLEVEL	F
    */
    private static class M2MInfo{
		private DiffEntity sysmdlDiff = null;
		private DiffEntity mdlcgDiff = null;
		private DiffEntity mdlcgosDiff = null;
		private DiffEntity mdlcgosmdlDiff = null;
		private DiffEntity optmdlDiff = null;
		private String activity = XMLElem.CHEAT;
		//private String updated = XMLElem.CHEAT;
		private String systemos = null;
		private String optionos = null;
		private String pdhdomain = null;
		private String oktopub = null;
		private String compatpubflag = null;
		private String pubfrom = null;
		private String pubto = null;
		private String reltype= null;

		M2MInfo(DiffEntity mdlitem, DiffEntity mdlcgitem, String os,
			DiffEntity mdlcgositem, DiffEntity mdlcgosmdlitem, DiffEntity optmdlitem,
			String optos){
			sysmdlDiff = mdlitem;
			mdlcgDiff = mdlcgitem;
			mdlcgosDiff = mdlcgositem;
			mdlcgosmdlDiff = mdlcgosmdlitem;
			optmdlDiff = optmdlitem;
			systemos = os;
			optionos = optos;

			if (sysmdlDiff.isNew() || mdlcgDiff.isNew() || mdlcgosDiff.isNew() ||
				mdlcgosmdlDiff.isNew() || optmdlDiff.isNew()){
				activity = XMLElem.UPDATE_ACTIVITY;
			}
			if (sysmdlDiff.isDeleted() || mdlcgDiff.isDeleted() || mdlcgosDiff.isDeleted() ||
				mdlcgosmdlDiff.isDeleted() || optmdlDiff.isDeleted()){
				activity = XMLElem.DELETE_ACTIVITY;
			}
			//check to see if this systemos or optionos was newly added or deleted
			checkOSValue(sysmdlDiff, systemos, "OSLEVEL");
			checkOSValue(mdlcgosDiff, optionos, "OS");

			pdhdomain = getValue(mdlcgDiff, "PDHDOMAIN");//MODELCG	PDHDOMAIN
			oktopub = getValue(mdlcgDiff, "OKTOPUB");//MODELCG	OKTOPUB
			compatpubflag = getValue(mdlcgosmdlDiff, "COMPATPUBFLG");//MDLCGOSMDL	COMPATPUBFLG
			pubfrom = getValue(mdlcgosmdlDiff, "PUBFROM");//MDLCGOSMDL	PUBFROM
			pubto = getValue(mdlcgosmdlDiff, "PUBTO");//MDLCGOSMDL	PUBTO
			reltype= getFlagValue(mdlcgosmdlDiff, "RELTYPE");//MMDLCGOSMDL	RELTYPE	Description Class
		}

		boolean isDisplayable() {return activity!=null;} // only display those with filled in actions

		//check to see if this systemos or optionos was newly added or deleted
		private void checkOSValue(DiffEntity diff, String osvalue, String attrcode){
			if (activity==null){
				EntityItem item = diff.getCurrentEntityItem();
				EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(attrcode);
				if (fAtt== null || !fAtt.isSelected(osvalue)){
					activity = XMLElem.UPDATE_ACTIVITY;
				}else{
					item = diff.getPriorEntityItem();
					fAtt = (EANFlagAttribute)item.getAttribute(attrcode);
					if (fAtt== null || !fAtt.isSelected(osvalue)){
						activity = XMLElem.UPDATE_ACTIVITY;
					}
				}
			}
		}
		String getKey() {
			return generateM2MKey(sysmdlDiff, mdlcgDiff, systemos,mdlcgosDiff,mdlcgosmdlDiff,optmdlDiff,optionos);
		}
		String getOS() { return systemos;}
		String getPdhdomain() { return pdhdomain;}
		String getOktopub() {  return oktopub;}
		String getCompatPubFlag() {  return compatpubflag;}
		String getPubFrom() {  return pubfrom; }
		String getPubTo() {  return pubto; }
		String getRelType() { return reltype;}
		String getSysType() { return sysmdlDiff.getEntityType();}
		String getSysID() { return ""+sysmdlDiff.getEntityID();}
		String getGrpType() { return mdlcgDiff.getEntityType();}
		String getGrpID() { return ""+mdlcgDiff.getEntityID();}
		String getGrpOsType() { return mdlcgosDiff.getEntityType();}
		String getGrpOsID() { return ""+mdlcgosDiff.getEntityID();}
		String getOptType() { return optmdlDiff.getEntityType();}
		String getOptID() { return ""+optmdlDiff.getEntityID();}
		String getOptOS() { return optionos;} //MODELCGOS	OS
		String getActivity() { return activity;}

		void dereference(){
			sysmdlDiff = null;
			mdlcgDiff = null;
			systemos = null;
			activity = null;
			pdhdomain = null;
			oktopub = null;
			compatpubflag = null;
			pubfrom = null;
			pubto = null;
			reltype= null;
			optionos = null;
		}

		private String getValue(DiffEntity diffitem, String attrcode){
			String curvalue = XMLElem.CHEAT;
			String prevvalue = XMLElem.CHEAT;
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem previtem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()){
				prevvalue = PokUtils.getAttributeValue(previtem, attrcode,", ", XMLElem.CHEAT, false);
			}else if (diffitem.isNew()){
				curvalue = PokUtils.getAttributeValue(curritem, attrcode,", ", XMLElem.CHEAT, false);
			}else{
				prevvalue = PokUtils.getAttributeValue(previtem, attrcode,", ", XMLElem.CHEAT, false);
				curvalue = PokUtils.getAttributeValue(curritem, attrcode,", ", XMLElem.CHEAT, false);
			}

			if (!prevvalue.equals(curvalue) && activity==null){
				activity = XMLElem.UPDATE_ACTIVITY;
			}

			return curvalue;
		}

		private String getFlagValue(DiffEntity diffitem, String attrcode){
			String curvalue = XMLElem.CHEAT;
			String prevvalue = XMLElem.CHEAT;
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem previtem = diffitem.getPriorEntityItem();
			if (diffitem.isDeleted()){
				prevvalue = PokUtils.getAttributeFlagValue(previtem, attrcode);
				if (prevvalue==null){
					prevvalue = XMLElem.CHEAT;
				}
			}else if (diffitem.isNew()){
				curvalue = PokUtils.getAttributeFlagValue(curritem, attrcode);
				if (curvalue==null){
					curvalue = XMLElem.CHEAT;
				}
			}else{
				prevvalue = PokUtils.getAttributeFlagValue(previtem, attrcode);
				curvalue = PokUtils.getAttributeFlagValue(curritem, attrcode);
				if (curvalue==null){
					curvalue = XMLElem.CHEAT;
				}
				if (prevvalue==null){
					prevvalue = XMLElem.CHEAT;
				}
			}

			if (!prevvalue.equals(curvalue) && activity==null){
				activity = XMLElem.UPDATE_ACTIVITY;
			}

			return curvalue;
		}

		public String toString() {
			return getKey()+" activity:"+activity;
		}
	}
}
