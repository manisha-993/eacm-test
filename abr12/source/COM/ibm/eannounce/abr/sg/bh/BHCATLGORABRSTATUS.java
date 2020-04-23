/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: BHCATLGORABRSTATUS.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2013
 *                LICENSED MATERIAL - PROGRAM PROPERTY OF IBM
 *                REFER TO COPYRIGHT INSTRUCTION FORM#G120-2083
 *                RESTRICTED MATERIALS OF IBM
 *                IBM CONFIDENTIAL
 *
 *   Version: 1.0
 *
 *   Functional Description: 
 *
 *   Component : 
 *   Author(s) Name(s): Will
 *   Date of Creation: May 14, 2013
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   May 14, 2013     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg.bh;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/**
 * BH FS ABR Data Quality 20130211.doc
 * XI.	BHCATLGOR
The existing CATLGOR does not participate in Data Quality Checking nor the triggering of the generation of XML. It will be limited to supporting XCC.

BHCATLGOR is a variation of CATLGOR in support of Blue Harmony (BH). There will be a new Data Quality ABR which will trigger (queue) the generation of XML as needed.

A.	Checking

Checking will be limited to consistency of data such as COUNTRYLIST and dates.

 * Virtual Entity
 * Lev	Entity1	RelType	Relator	Entity2	Dir
 * 0	LSEO			Relator LSEOBHCATLGOR	BHCATLGOR U
 * 0	LSEOBUNDLE		Relator LSEOBUNDLEBHCATLGOR	BHCATLGOR U
 * 0	PRODSTRUCT		Relator PRODSTRUCTBHCATLGOR	BHCATLGOR U
 * 0	MODEL			Relator MDLBHCATLGOR	BHCATLGOR U
 * 0	SWPRODSTRUCT	Relator SWPRODSTRBHCATLGOR	BHCATLGOR U
 * 1	LSEO			Relator	LSEOAVAIL	AVAIL	D
 * 1	WWSEO			Relator	WWSEOLSEO	LSEO	D
 * 1	LSEOBUNDLE		Relator	LSEOBUNDLEAVAIL	AVAIL	D
 * 1 	PRODSTRUCT		Relator OOFAVAIL	AVAIL	D
 * 1	MODEL			Relator MODELAVAIL	AVAIL	D
 * 1 	SWPRODSTRUCT	Relator SWPRODSTRUCTAVAIL	AVAIL	D
 * 1	FEATURE			Relator PRODSTRUCT	MODEL	D
 * 1	SWFEATURE		Relator SWPRODSTRUCT	MODEL	D
 * 2	MODEL			Relator MODELAVAIL	AVAIL	D
 * 

BHCATLGORABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.BHCATLGORABRSTATUS
BHCATLGORABRSTATUS_enabled=true
BHCATLGORABRSTATUS_idler_class=A
BHCATLGORABRSTATUS_keepfile=true
BHCATLGORABRSTATUS_read_only=true
BHCATLGORABRSTATUS_report_type=DGTYPE01
BHCATLGORABRSTATUS_vename=EXRPT3BHCATLGOR1

 * @author Will
 *
 */
public class BHCATLGORABRSTATUS extends DQABRSTATUS {
	
	private static final String[] LSEOABRS = {"ADSABRSTATUS"};
	private static final String[] MODELABRS = {"ADSABRSTATUS"};
	private static final String[] PRODSTRUCTABRS = {"ADSABRSTATUS"};
	private static final String[] SWPRODSTRUCTABRS = {"ADSABRSTATUS"};
	private static final String[] LSEOBUNDLEABRS = {"ADSABRSTATUS"};
	

	/**
	 * Key	EntityType	ID	AttributeCode	Op	EntityType	AttributeCode	By Cty	Draft	CR	RFR	Comment	Error Text
1.00	BHCATLGOR		Root							
2.00	MODEL		MDLBHCATLGOR-u							
3.00	AVAIL	A	MODELAVAIL-d							
4.00	WHEN		AVAILTYPE	=	"Planned Availability"					
5.00			COUNTRYLIST	"IN
aggregate G"	A: AVAIL	COUNTRYLIST		W	E*2	E*2
6.00	LSEO		LSEOBHCATLGOR-u							
7.00	WWSEO		WWSEOLSEO-u							
8.00	IF		SPECBID	=	"No" (11457)					
9.00	AVAIL	B	LSEOAVAIL-d							
10.00	WHEN		AVAILTYPE	=	"Planned Availability"					
11.00			COUNTRYLIST	"IN
aggregate G"	B: AVAIL	COUNTRYLIST		W	E	E
12.00	ELSE	8.00								
13.00			COUNTRYLIST	"IN
aggregate G"	LSEO	COUNTRYLIST		W	E	E
14.00	END	8.00								
15.00	LSEOBUNDLE		LSEOBUNDLEBHCATLGOR-u							
16.00	IF		SPECBID	=	"No" (11457)					
17.00	AVAIL	C	LSEOBUNDLEAVAIL-d							
18.00	WHEN		AVAILTYPE	=	"Planned Availability"					
19.00			COUNTRYLIST	"IN
aggregate G"	C: AVAIL	COUNTRYLIST		W	E	E
20.00	ELSE	16.00								
21.00			COUNTRYLIST	"IN
aggregate G"	LSEOBUNDLE	COUNTRYLIST		W	E	E
22.00	END	16.00								
23.00	PRODSTRUCT		PRODSTRUCTBHCATLGOR-u							
24.00	IF		PRODSTRUCT-u: FEATURE.FCTYPE	=	Primary FC (100) | "Secondary FC" (110)					
25.00	AVAIL	D	OOFAVAIL-d							
26.00	WHEN		AVAILTYPE	=	"Planned Availability"					
27.00			COUNTRYLIST	"IN
aggregate G"	D: AVAIL	COUNTRYLIST		W	E*1	E*1
28.00	ELSE	24.00								
29.00			PRODSTRUCT-u							
30.00			COUNTRYLIST	"IN
aggregate G"	FEATURE	COUNTRYLIST		W	E*1	E*1
31.00	END	24.00								
32.00	SWPRODSTRUCT		SWPRODSTRBHCATLGOR-u							
33.00	MODEL	F	SWPRODSTRUCT-d							
34.00	IF		"No" (11457)	=	MODEL	SPECBID				
35.00	AVAIL	E	SWPRODSTRUCTAVAIL-d							
36.00	WHEN		AVAILTYPE	=	"Planned Availability"					
37.00			COUNTRYLIST	"IN
aggregate G"	E: AVAIL	COUNTRYLIST		W	E*1	E*1
38.00	ELSE	34.00								
39.00	AVAIL	G 	F: MODELAVAIL-d							
40.00			COUNTRYLIST	"IN
aggregate G"	G: AVAIL	COUNTRYLIST		W	E*1	E*1
41.00	END	32.00								

	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag)
			throws Exception {
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
		int checklvl = getCheck_W_E_E(statusFlag);
		ArrayList itemCtry = new ArrayList();
		getCountriesAsList(rootEntity, itemCtry, checklvl);
		EntityGroup eGrp = m_elist.getEntityGroup("MODEL");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
//		1.00	BHCATLGOR		Root							
//		2.00	MODEL		MDLBHCATLGOR-u							
//		3.00	AVAIL	A	MODELAVAIL-d							
//		4.00	WHEN		AVAILTYPE	=	"Planned Availability"					
//		5.00			COUNTRYLIST	"IN
//		aggregate G"	A: AVAIL	COUNTRYLIST		W	E*2	E*2
		// get models thru avail
		Vector mdlVct = PokUtils.getAllLinkedEntities(rootEntity, "MDLBHCATLGOR", "MODEL");		
		for (int i=0; i< mdlVct.size(); i++){
			EntityItem mdlItem = (EntityItem)mdlVct.elementAt(i);
			int checklvl2 = getCheckLevel(checklvl,mdlItem,"ANNDATE");
			addDebug("check model:" + mdlItem.getKey());
			checkAvails(rootEntity, itemCtry,mdlItem,"MODELAVAIL",checklvl2, true);
		}
		mdlVct.clear();
		
//		6.00	LSEO		LSEOBHCATLGOR-u							
//		7.00	WWSEO		WWSEOLSEO-u							
//		8.00	IF		SPECBID	=	"No" (11457)					
//		9.00	AVAIL	B	LSEOAVAIL-d							
//		10.00	WHEN		AVAILTYPE	=	"Planned Availability"					
//		11.00			COUNTRYLIST	"IN
//		aggregate G"	B: AVAIL	COUNTRYLIST		W	E	E
//		12.00	ELSE	8.00								
//		13.00			COUNTRYLIST	"IN
//		aggregate G"	LSEO	COUNTRYLIST		W	E	E
//		14.00	END	8.00								
		Vector lseoVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBHCATLGOR", "LSEO");
		for (Iterator it = lseoVct.iterator(); it.hasNext();) {
			EntityItem lseoItem = (EntityItem) it.next();
			Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoItem,"WWSEOLSEO", "WWSEO");
			EntityItem wwseoItem = (EntityItem)wwseoVct.get(0);
			String specialbid = PokUtils.getAttributeFlagValue(wwseoItem, "SPECBID");
			if(SPECBID_NO.equals(specialbid)){
				checkAvails(rootEntity,itemCtry,lseoItem,"LSEOAVAIL",checklvl, true);
			}else{
				checkCountryList(rootEntity,lseoItem,itemCtry, checklvl);
			}
		}
		lseoVct.clear();
		
//		15.00	LSEOBUNDLE		LSEOBUNDLEBHCATLGOR-u							
//		16.00	IF		SPECBID	=	"No" (11457)					
//		17.00	AVAIL	C	LSEOBUNDLEAVAIL-d							
//		18.00	WHEN		AVAILTYPE	=	"Planned Availability"					
//		19.00			COUNTRYLIST	"IN
//		aggregate G"	C: AVAIL	COUNTRYLIST		W	E	E
//		20.00	ELSE	16.00								
//		21.00			COUNTRYLIST	"IN
//		aggregate G"	LSEOBUNDLE	COUNTRYLIST		W	E	E
//		22.00	END	16.00		
		Vector bundleVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBUNDLEBHCATLGOR", "LSEOBUNDLE");
		for (Iterator it = bundleVct.iterator(); it.hasNext();) {
			EntityItem bundleItem = (EntityItem) it.next();
			String specialbid = PokUtils.getAttributeFlagValue(bundleItem, "SPECBID");
			if(SPECBID_NO.equals(specialbid)){
				checkAvails(rootEntity,itemCtry,bundleItem,"LSEOBUNDLEAVAIL",checklvl, true);
			}else{
				checkCountryList(rootEntity,bundleItem,itemCtry, checklvl);
			}
		}
		bundleVct.clear();
		
//		23.00	PRODSTRUCT		PRODSTRUCTBHCATLGOR-u							
//		24.00	IF		PRODSTRUCT-u: FEATURE.FCTYPE	=	Primary FC (100) | "Secondary FC" (110)					
//		25.00	AVAIL	D	OOFAVAIL-d							
//		26.00	WHEN		AVAILTYPE	=	"Planned Availability"					
//		27.00			COUNTRYLIST	"IN
//		aggregate G"	D: AVAIL	COUNTRYLIST		W	E*1	E*1
//		28.00	ELSE	24.00								
//		29.00			PRODSTRUCT-u							
//		30.00			COUNTRYLIST	"IN
//		aggregate G"	FEATURE	COUNTRYLIST		W	E*1	E*1
//		31.00	END	24.00	
		Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "PRODSTRUCTBHCATLGOR", "PRODSTRUCT");
		for (Iterator it = psVct.iterator(); it.hasNext();) {
			EntityItem psItem = (EntityItem) it.next();
//			EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);
//			Vector featVct = PokUtils.getAllLinkedEntities(psItem,"PRODSTRUCT", "FEATURE");
//			addDebug(" featVct size: "+featVct.size());
			EntityItem featItem = getlinkeditem(psItem,"FEATURE");//(EntityItem)(psItem.getUpLink().get(0));
			EntityItem modelItem = getlinkeditem(psItem,"MODEL");//(EntityItem)(psItem.getDownLink().get(0));
			addDebug(featItem.getEntityType()+featItem.getEntityID());
			addDebug(modelItem.getEntityType()+modelItem.getEntityID());
			int checklvl2 = getCheckLevel(checklvl,modelItem,"ANNDATE");
			addDebug("level1: " + checklvl + " and level2 : " + checklvl2);
			boolean isRPQ = this.isRPQ(featItem);
			if(!isRPQ){
				addDebug("ps - Not RPQ checking");
				checkAvails(rootEntity,itemCtry,psItem,"OOFAVAIL",checklvl2, true);
			}else{
				addDebug("ps - RPQ checking");
				checkCountryList(rootEntity,featItem,itemCtry, checklvl2);
			}
		}
		psVct.clear();
		
//		32.00	SWPRODSTRUCT		SWPRODSTRBHCATLGOR-u							
//		33.00	MODEL	F	SWPRODSTRUCT-d							
//		34.00	IF		"No" (11457)	=	MODEL	SPECBID				
//		35.00	AVAIL	E	SWPRODSTRUCTAVAIL-d							
//		36.00	WHEN		AVAILTYPE	=	"Planned Availability"					
//		37.00			COUNTRYLIST	"IN
//		aggregate G"	E: AVAIL	COUNTRYLIST		W	E*1	E*1
//		38.00	ELSE	34.00								
//		39.00	AVAIL	G 	F: MODELAVAIL-d							
//		40.00			COUNTRYLIST	"IN
//		aggregate G"	G: AVAIL	COUNTRYLIST		W	E*1	E*1
//		41.00	END	32.00	
		Vector swpsVct = PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRBHCATLGOR", "SWPRODSTRUCT");
		for (Iterator it = swpsVct.iterator(); it.hasNext();) {
			EntityItem swpsItem = (EntityItem) it.next();
//			Vector modelVct = PokUtils.getAllLinkedEntities(swpsItem,"SWPRODSTRUCT", "MODEL");			
			EntityItem modelItem = getlinkeditem(swpsItem,"MODEL");//(EntityItem)(swpsItem.getDownLink().get(0));
			int checklvl2 = getCheckLevel(checklvl,modelItem,"ANNDATE");	
			
			String specialbid = PokUtils.getAttributeFlagValue(modelItem, "SPECBID");
			if(SPECBID_NO.equals(specialbid)){
				addDebug("swps - Not specialbid");
				checkAvails(rootEntity,itemCtry,swpsItem,"SWPRODSTRUCTAVAIL",checklvl2, true);
			}else{
				addDebug("swps - specialbid");
				checkAvails(rootEntity,itemCtry,modelItem,"MODELAVAIL",checklvl2, false);
			}
		}
		swpsVct.clear();
	}
	
	private EntityItem getlinkeditem(EntityItem theItem,String etype){
		for (int i=0; i<theItem.getDownLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getDownLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		for (int i=0; i<theItem.getUpLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getUpLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		return null;
	}
	
	private void checkAvails(EntityItem rootEntity,ArrayList ctryList, EntityItem linkedEntity, String linkedRelator, int checklvl, boolean isNoPlanAvailChecking) throws SQLException, MiddlewareException{
		Vector vct = PokUtils.getAllLinkedEntities(linkedEntity, linkedRelator, "AVAIL");
		ArrayList allAvailCtry = new ArrayList();
		boolean hasPlanAvail = false;
		for (Iterator iterator = vct.iterator(); iterator.hasNext();) {
			EntityItem availItem = (EntityItem) iterator.next();
			String availType = PokUtils.getAttributeFlagValue(availItem, "AVAILTYPE");
			addDebug(availItem.getKey()+" availtype "+availType);
			if(PLANNEDAVAIL.equals(availType)){
				ArrayList itemCtry = new ArrayList();
				getCountriesAsList(availItem, itemCtry, checklvl);
				addDebug(availItem.getKey()+" countrylist "+itemCtry);
				allAvailCtry.addAll(itemCtry);	
				hasPlanAvail = true;
			}			
		}
		if(!isNoPlanAvailChecking && !hasPlanAvail){
			return;
		}
		if(!allAvailCtry.containsAll(ctryList)){
//			EntityGroup mdl_eGrp = m_elist.getEntityGroup("MODEL");	
			EntityGroup avail_eGrp = m_elist.getEntityGroup("AVAIL");	
			addDebug("All Avails' ctrylist: " +allAvailCtry + " | " + rootEntity.getKey() + " catlgorCtry: "
					+ ctryList);
//			{LD: BHCATLGOR} {NDN: A: BHCATLGOR must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
			// BH_COUNTRY_MISMATCH_ERR2 = {0} must not include a country that is not in the {1} {2}  Planned Availability
			//TODO add it to properties
			args[0] = getLD_NDN(rootEntity);
			args[1] = linkedEntity.getEntityGroup().getLongDescription();//mdl_eGrp.getLongDescription();
			args[2] = avail_eGrp.getLongDescription();
			ArrayList tmp = new ArrayList();
			tmp.addAll(ctryList);
			tmp.removeAll(allAvailCtry);
			args[3]=getUnmatchedDescriptions(rootEntity, "COUNTRYLIST",tmp);
			createMessage(checklvl, "BH_COUNTRY_MISMATCH_ERR2", args);
			tmp.clear();
		}
		vct.clear();
	}
	
	private void checkCountryList(EntityItem rootEntity, EntityItem item,
			ArrayList ctryList, int checklvl) throws SQLException, MiddlewareException {
		ArrayList itemCtry = new ArrayList();
		getCountriesAsList(item, itemCtry, checklvl);
		if (!itemCtry.containsAll(ctryList)) {
			addDebug(item.getKey() + " ctrylist " + itemCtry + " catlgorCtry "
					+ ctryList);
//			{LD: BHCATLGOR} {NDN: BHCATLGOR} must not include a country that is not in the {LD: LSEO} {LD: COUNTRYLIST}
			args[0] = PokUtils.getAttributeDescription(item.getEntityGroup(),
					"COUNTRYLIST", "COUNTRYLIST");
			args[1] = getLD_NDN(item);
			ArrayList tmp = new ArrayList();
			tmp.addAll(ctryList);
			tmp.removeAll(itemCtry);
			args[2]=getUnmatchedDescriptions(rootEntity, "COUNTRYLIST",tmp);
			createMessage(checklvl, "BH_COUNTRY_MISMATCH_ERR1", args);
			tmp.clear();
		}
		itemCtry.clear();
	}
	

	public String getDescription() {
		
		String desc =  "BHCATLGOR ABR.";

        return desc;
	}

//	272.000		BHCATLGOR		Root Entity							
//	273.000		IF			BHCATLGOR	STATUS	=	"Final" (0020)			
//	274.000		IF		LSEOBUNDLEBHCATLGOR-u	LSEOBUNDLE	STATUS	=	"Final" (0020)			
//	275.000		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
//	276.000		END	274.000								
//	277.000		IF		LSEOBHCATLGOR-u	LSEO	STATUS	=	"Final" (0020)			
//	278.000		SET			LSEO				ADSABRSTATUS		&ADSFEED
//	279.000		END	277.000								
//	280.000		IF		MDLBHCATLGOR-u	MODEL	STATUS	=	"Final" (0020)			
//	281.000		SET			MODEL				ADSABRSTATUS		&ADSFEED
//	282.000		END	280.000								
//	283.000		IF		PRODSTRUCTBHCATLGOR-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
//	284.000		SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
//	285.000		END	283.000								
//	286.000		IF		SWPRODSTRBHCATLGOR-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
//	287.000		SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
//	288.000		END	286.000								
//	289.000		END	273.000								
//	290.000		END	272.000	BHCATLGOR
	protected void completeNowFinalProcessing() throws SQLException,
			MiddlewareException, MiddlewareRequestException {
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		verifyFinalAndQueue(rootEntity, "LSEOBUNDLEBHCATLGOR", "LSEOBUNDLE",LSEOBUNDLEABRS);	
		verifyFinalAndQueue(rootEntity, "LSEOBHCATLGOR", "LSEO",LSEOABRS);	
		verifyFinalAndQueue(rootEntity, "MDLBHCATLGOR", "MODEL",MODELABRS);	
		verifyFinalAndQueue(rootEntity, "PRODSTRUCTBHCATLGOR", "PRODSTRUCT",PRODSTRUCTABRS);	
		verifyFinalAndQueue(rootEntity, "SWPRODSTRBHCATLGOR", "SWPRODSTRUCT",SWPRODSTRUCTABRS);	
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#completeNowR4RProcessing()
	 */
	protected void completeNowR4RProcessing() throws SQLException,
			MiddlewareException, MiddlewareRequestException {
		super.completeNowR4RProcessing();
	}
	
	/**
	 * go from bhcatlgor thru relator to entitytype, if it is final then queue abrs 
	 * @param avail
	 * @param relator
	 * @param entityType
	 * @param attrcodes
	 */
	private void verifyFinalAndQueue(EntityItem bhcatlgor, String relator, String entityType,
			String[] attrcodes) {
		Vector entityVct = PokUtils.getAllLinkedEntities(bhcatlgor, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			if (statusIsFinal(ei)) {
				for (int i=0; i<attrcodes.length; i++){
					setFlagValue(m_elist.getProfile(),attrcodes[i], getQueuedValueForItem(ei,attrcodes[i]), ei);
				}
			}
		}
		entityVct.clear();
	}

}
