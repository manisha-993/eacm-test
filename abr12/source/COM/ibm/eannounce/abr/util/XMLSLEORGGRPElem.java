package COM.ibm.eannounce.abr.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;

import com.ibm.transform.oim.eacm.diff.DiffEntity;
import com.ibm.transform.oim.eacm.util.PokUtils;

/*
 * <SLEORGGRPLIST>	
 <SLEORGGRPELEMENT>	
 <SLEOORGGRPACTION>	</SLEOORGGRPACTION>
 <SLEORGGRP>	</SLEORGGRP>
 </SLEORGGRPELEMENT>
 </SLEORGGRPLIST>
 <SLEORGNPLNTCODELIST>	
 <SLEORGNPLNTCODEELEMENT>	
 <SLEORGNPLNTCODEACTION>	</SLEORGNPLNTCODEACTION>
 <SLEORG>	</SLEORG>
 <PLNTCD>	</PLNTCD>
 </SLEORGNPLNTCODEELEMENT>
 </SLEORGNPLNTCODELIST>
 */
//$Log: XMLSLEORGGRPElem.java,v $
//Revision 1.35  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.34  2013/08/26 12:33:02  wangyulo
//New RTC Workitem 999513 - change MODEL_UPDATE and SEO_UPDATE to use new MODCATG value Servicepac when deriving Sales Org and Plant Code
//
//Revision 1.33  2012/08/13 15:32:41  wangyulo
//remove debug information
//
//Revision 1.32  2011/12/14 02:30:24  guobin
//update the install attribute for the LSEOBUNDLEI
//
//Revision 1.31  2011/11/17 13:17:32  liuweimi
//Fix Defect - 591202 based on BH FS ABR Data Transformation System Feed 20111031.doc
//
//Revision 1.30  2011/11/10 07:35:08  guobin
//Fix Defect 598849 Invalid countrylist for PRODSTRUCT with no AVAIL. If path = null, return all.
//
//Revision 1.29  2011/08/17 08:41:03  guobin
//update the defect for generation of Sales Org /Plant Code in SEO_UPDATE
//
//Revision 1.28  2011/08/17 08:39:36  guobin
//update the defect for generation of Sales Org /Plant Code in SEO_UPDATE
//
//Revision 1.27  2011/03/29 06:08:17  guobin
//change the getCOFCAT of SVCMOD and SVCSEO
//
//Revision 1.26  2011/03/14 14:44:19  guobin
//remove some debug information
//
//Revision 1.25  2011/03/04 06:22:14  guobin
//when the SLEORGGRP of AVAIL or TAXCODE etc has no value, put all the SLEORGGRP of SLEORGNPLNTCODE into the Hashtable,else only put the matching SLEORGGRP.
//
//Revision 1.24  2011/03/03 14:03:52  guobin
//add SLEORGNPLNTCODE for the LSEO and LSEO special
//
//Revision 1.23  2011/03/03 07:30:58  guobin
//update for SLEORGNPLNTCODE that all the  SLEORGGRP as a groupList and  SLEORG and CODE as a groupList, only when the group is changed or deleted, then show the update.
//
//Revision 1.22  2011/03/01 13:02:13  guobin
//update for the SVCSEO --> AVAIL --> SLEORGNPLNTCODE
//
//Revision 1.21  2011/02/26 02:35:03  guobin
//update the case that SVCSEO is new and SOP is no change
//
//Revision 1.20  2011/02/24 09:55:50  guobin
//for the case
//T1:
//SVCMOD --> SVCSEO1 --> AVAIL -->SOP
//T2:
//SVCMOD --> SVCSEO1--> AVAIL -->SOP
//SVCMOD --> SVCSEO2--> AVAIL--->SOP
//SOP should be showed
//
//Revision 1.19  2011/02/17 06:54:27  guobin
//add some condition for the TAXCATG and TAXGRP to show SOP entity
//
//Revision 1.18  2011/02/16 03:17:13  guobin
//add change for LSEO SPECBID
//
//Revision 1.17  2011/02/10 10:12:38  guobin
// change the multi cofcats of LSBOUNDLE
//
//Revision 1.16  2011/02/10 09:20:25  guobin
//LSEOBUNDL have multi cofcats in the boundtype attribute
//
//Revision 1.15  2011/01/30 06:39:30  guobin
//if the SLEORGGRP is not only update but alose delete, set as update
//
//Revision 1.14  2011/01/28 08:53:44  guobin
//update for the COFCAT of MODEL and LSEOBUNDLE
//
//Revision 1.13  2011/01/26 08:33:02  guobin
//add the case of SVCSEO for getCOFCAT
//
//Revision 1.12  2011/01/24 12:55:13  guobin
//First set the deleted item then set the updated item
//
//Revision 1.11  2011/01/24 10:05:27  guobin
//change the TAXGRP in the hasChanges
//
//Revision 1.10  2011/01/24 09:23:56  guobin
//add the D:AVAILSLEORGA:D case
//
//Revision 1.9  2011/01/24 09:09:19  guobin
//add the delete action and set value for the element
//
//Revision 1.8  2011/01/22 12:52:35  guobin
//For the MODELCONVERT  set path  = "D:AVAILSLEORGA:D", root Is AVAIL.
//
//Revision 1.7  2011/01/22 11:33:09  guobin
//Add Null checking of SLEORGGRP
//
//Revision 1.6  2011/01/22 03:14:17  guobin
//change the setAction(DELETE_ACTIVITY)
//
//Revision 1.5  2011/01/21 12:33:05  guobin
//add comment log
//
public class XMLSLEORGGRPElem extends XMLElem {

	private String path = null;
//    private boolean debug = true; //if debug, set debug = true to print the table information

	/**
	 * constructor constructor for AVAIL 
	 */
	public XMLSLEORGGRPElem() {
		super("SLEORGGRPLIST");
	}

	/**
	 * constructor for TAXCODELIST and TAXCOD
	 * @param _path
	 */
	public XMLSLEORGGRPElem(String _path) {
		super("SLEORGGRPLIST");
		path = _path;
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
		//ABRUtil.append(debugSb,"XMLSVCMODAVAILElembh1:parentItem: " + parentItem.getKey() + NEWLINE);
		if ("TAXCATG".equals(parentItem.getEntityType()) || "TAXGRP".equals(parentItem.getEntityType()) || "D:AVAILSLEORGA:D".equals(path)) {
			EntityItem item = parentItem.getCurrentEntityItem();
			if (parentItem.isDeleted()) {
				item = parentItem.getPriorEntityItem();
			}
			EntityItem root = null;

			if (item != null) {
				Vector vec = item.getUpLink();
				EntityItem relator = (EntityItem) vec.elementAt(0);
				if (relator != null) {
					root = (EntityItem) relator.getUpLink().elementAt(0);
				}
			}
			displayAVAILSLEORG(table, document, parent, root, parentItem, path, CHEAT, CHEAT, debugSb);
		}
	}

	/**
	 * hasChanges override XMLElem.java.
	 * @param table
	 * @param parentItem
	 * @param debugSb
	 * @return 
	 */
	protected boolean hasChanges(Hashtable table, DiffEntity diffitem, StringBuffer debugSb) {

		boolean haschange = false;
		if ("TAXCATG".equals(diffitem.getEntityType()) || "TAXGRP".equals(diffitem.getEntityType()) || "D:AVAILSLEORGA:D".equals(path)) {
			EntityItem item = diffitem.getCurrentEntityItem();
			EntityItem root = null;

			if (item != null) {
				Vector vec = item.getUpLink();
				EntityItem relator = (EntityItem) vec.elementAt(0);
				if (relator != null) {
					root = (EntityItem) relator.getUpLink().elementAt(0);
				}
			}
			haschange = hasChanges(table, root, diffitem, path, CHEAT,debugSb);
		}
		return haschange;
	}

	/**
	 * hasChanges for AVAIL
	 * @param table
	 * @param parentItem
	 * @param relatedDiff
	 * @param path
	 * @param ctry
	 * @param debugSb
	 * @return
	 */
	protected boolean hasChanges(Hashtable table, EntityItem parentItem, DiffEntity relatedDiff, String path, String ctry,
		StringBuffer debugSb) {
		boolean changed = false;
		if (parentItem != null && relatedDiff != null) {
			String COFCAT = "";
			COFCAT = getCOFCAT(parentItem);
			String  rootkey = null;
			// if <SLEORGGRPLIST> in <TAXCATEGORYLIST>, then getting SLEORGNPLNTCODE which related to TAX.
			// if <SLEORGGRPLIST> in <AVAIL>, then getting SLEORGNPLNTCODE which related to AVAIl and rootkey is MODEL.
			if ("TAXCATG".equals(relatedDiff.getEntityType()) || "TAXGRP".equals(relatedDiff.getEntityType()) || "D:AVAILSLEORGA:D".equals(path) ) {
				rootkey = relatedDiff.getKey();				
			}else{
				rootkey = parentItem.getKey();
			} 
			boolean SVCSEOIsNew = false;
			boolean isSVCSEO = false;
			if("SVCSEO".equals(parentItem.getEntityType())){
				isSVCSEO = true;
			}
			Vector SLEORGNPLNTVct = getSLEORGNPLNTcode(table, rootkey, path, isSVCSEO, debugSb);
			if (SLEORGNPLNTVct.size() > 0) {
				Hashtable chgTbl = new Hashtable();
				Hashtable T1GrpTbl = new Hashtable();
				Hashtable T2GrpTbl = new Hashtable();
				Vector[] SLEORGGRPVector = getSLEORGGRP(relatedDiff, debugSb);
				// buid and filter the Vector
				Vector ctrySOPElemMap = new Vector();

				for (int i = 0; i < SLEORGNPLNTVct.size(); i++) {
					DiffEntity sopDiff = (DiffEntity) SLEORGNPLNTVct.elementAt(i);
					buildCtrySOPRecs(ctrySOPElemMap, sopDiff, COFCAT, SLEORGGRPVector, ctry, SVCSEOIsNew, T1GrpTbl, T2GrpTbl, debugSb);
				}// end each planned avail
				
				changed = createCodeNodes(null,debugSb,ctrySOPElemMap,null);
				ABRUtil.append(debugSb,"XMLSLEORGGRPElem.hasChanges:  for SLEORGNPLNT " + changed + NEWLINE);
				getChgTbl(T1GrpTbl, T2GrpTbl,chgTbl);
				if(chgTbl.size()>0){
					changed = true;
					ABRUtil.append(debugSb,"XMLSLEORGGRPElem.hasChanges:  for SLEORGGRP " + changed + NEWLINE);
				}
				ctrySOPElemMap.clear();
				chgTbl.clear();
				T1GrpTbl.clear();
				T2GrpTbl.clear();
				SLEORGGRPVector[0].clear();
				SLEORGGRPVector[1].clear();
			}
		}
		return changed;
	}
	/**
	 * add this method for case that the availType = "LSEOSPECBID"
	 * called in the XMLLSEOAVAILElembh1.setAllFieldsMODEL
	 * @param table
	 * @param parentItem
	 * @param relatedDiff
	 * @param path
	 * @param ctry
	 * @param availType
	 * @param debugSb
	 * @return
	 */
	protected boolean hasChangesMODEL(Hashtable table, EntityItem parentItem, DiffEntity relatedDiff, String path, String ctry,
			String availType,StringBuffer debugSb) {
			boolean changed = false;
			if (parentItem != null && relatedDiff != null) {
				String COFCAT = "";
				COFCAT = getCOFCAT(parentItem);
				String  rootkey = null;
				// if <SLEORGGRPLIST> in <TAXCATEGORYLIST>, then getting SLEORGNPLNTCODE which related to TAX.
				// if <SLEORGGRPLIST> in <AVAIL>, then getting SLEORGNPLNTCODE which related to AVAIl and rootkey is MODEL.
				if ("TAXCATG".equals(relatedDiff.getEntityType()) || "TAXGRP".equals(relatedDiff.getEntityType()) || "D:AVAILSLEORGA:D".equals(path) ) {
					rootkey = relatedDiff.getKey();				
				}else{
					rootkey = parentItem.getKey();
				} 
				boolean SVCSEOIsNew = false;
				boolean isSVCSEO = false;
				if("SVCSEO".equals(parentItem.getEntityType())){
					isSVCSEO = true;
				}
				//Vector SLEORGNPLNTVct = getSLEORGNPLNTcode(table, rootkey, path, isSVCSEO, availType,debugSb);
				Vector SLEORGNPLNTVct = getSLEORGNPLNTcodeLSEO(table, rootkey, path, isSVCSEO, availType, debugSb);
				if (SLEORGNPLNTVct.size() > 0) {
					Hashtable chgTbl = new Hashtable();
					Hashtable T1GrpTbl = new Hashtable();
					Hashtable T2GrpTbl = new Hashtable();
					Vector[] SLEORGGRPVector = getSLEORGGRP(relatedDiff, debugSb);
					// buid and filter the Vector
					Vector ctrySOPElemMap = new Vector();

					for (int i = 0; i < SLEORGNPLNTVct.size(); i++) {
						DiffEntity sopDiff = (DiffEntity) SLEORGNPLNTVct.elementAt(i);
						buildCtrySOPRecs(ctrySOPElemMap, sopDiff, COFCAT, SLEORGGRPVector, ctry, SVCSEOIsNew, T1GrpTbl, T2GrpTbl, debugSb);
					}// end each planned avail
					
					changed = createCodeNodes(null,debugSb,ctrySOPElemMap,null);
					ABRUtil.append(debugSb,"XMLSLEORGGRPElem.hasChanges:  for SLEORGNPLNT" + changed + NEWLINE);
					getChgTbl(T1GrpTbl, T2GrpTbl,chgTbl);
					if(chgTbl.size()>0){
						changed = true;
					}
					ctrySOPElemMap.clear();
					chgTbl.clear();
					T1GrpTbl.clear();
					T2GrpTbl.clear();
					SLEORGGRPVector[0].clear();
					SLEORGGRPVector[1].clear();
				}
			}
			return changed;
		}

	/**
	 * @param parentItem
	 * @param COFCAT
	 * @return
	 */
	private String getCOFCAT(EntityItem parentItem) {
		String COFCAT = CHEAT;
		if (parentItem != null) {
			String roottype = parentItem.getEntityType();
			String attrcode = null;
			if ("MODEL".equals(roottype)) {
				attrcode = "COFCAT";
				EANFlagAttribute fAtt = (EANFlagAttribute)parentItem.getAttribute(attrcode);
				if (fAtt!=null && fAtt.toString().length()>0){
                    // Get the selected Flag codes.
                    MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                    for (int i = 0; i < mfArray.length; i++){
                        // get selection
                        if (mfArray[i].isSelected()) {
                        	COFCAT = (mfArray[i].toString()); // get longdesription
                        	/**
                        	 * Deriving logic in the case where there isn't an AVAIL where AVAILTYPE="Planned Availability" (146) 
                        	 * and ANNDATE <  20100301  is not designed.
                             * If MODEL.COFCAT =  Service , match to SLEORGNPLNTCODE.MODCATG with value  Hardware  
                             * and requires code changes. So we have to defer the test cases until code update.
                        	 */
                        	if("Service".equalsIgnoreCase(COFCAT)){
                        		//New change for RTC Workitem 999513 base on the BH FS ABR XML System Feed Mapping 20130820.doc
                        		COFCAT = "Servicepac";
                        	}
                        	break;
                        }
                    }
				}
			} else if("SVCMOD".equals(roottype)) {				
				COFCAT = "Service";
			} else if ("SVCSEO".equals(roottype)) {
				COFCAT = "Service";
			}else if ("FCTRANSACTION".equals(roottype)) {
				COFCAT = "Hardware"; 
			} else if ("LSEOBUNDLE".equals(roottype)) {
				/**
				 * It will use LSEOBUNDLE.BUNDLETYPE to deriving sales org and requires code changes. 
				 * So we have to defer the test cases until code update.
				 * muiti- BUNDLETYPE
				 * 
				 * New change for the LSEOBUNDLE
				 * >>---If BUNDLETYPE=’Hardware’ exists, use ‘Hardware’
                 * >>-	Else if BUNDLETYPE=’Software’ exists, use ‘Software’
                 * >>-	Else use ‘Service’
				 * 
				 */
				attrcode = "BUNDLETYPE";
				EANFlagAttribute fAtt = (EANFlagAttribute)parentItem.getAttribute(attrcode);
				if (fAtt!=null && fAtt.toString().length()>0){
                    // Get the selected Flag codes.
                    MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                    for (int i = 0; i < mfArray.length; i++){
                        // get selection
                        //get multi cofcat
                        if (mfArray[i].isSelected()){
                        	if(COFCAT.equals(CHEAT)){
                        		COFCAT = (mfArray[i].toString());
                        	}else{
                        		COFCAT = COFCAT +  "," + (mfArray[i].toString());
                        	}                        	
                        }
                    }
				}
				//changed
				if(COFCAT.indexOf("Hardware")>-1){
					COFCAT ="Hardware";
				}else if(COFCAT.indexOf("Software")>-1){
					COFCAT ="Software";
				}else{
					COFCAT ="Service";
				}
			} else if ("PRODSTRUCT".equals(roottype)) {
				COFCAT = "Hardware";
			} else if ("SWPRODSTRUCT".equals(roottype)) {
				COFCAT = "Software"; 
			}
		}
		return COFCAT;
	}
	/**
	 * print the table information
	 * @param table
	 * @param debug
	 * @param debugSb
	 */
//	private void printTable(Hashtable table, boolean debug, StringBuffer debugSb) {
//		if(debug){
//			ABRUtil.append(debugSb,"XMLSLEORGGRPElem.printTable for debug" + NEWLINE);
//			Iterator it = table.keySet().iterator();
//			while (it.hasNext()){	
//				String key =(String)it.next();
//				ABRUtil.append(debugSb,"table:key=" + key + ";value=" + table.get(key)+NEWLINE);
//			}
//		}
//		
//	}

	/**
	 * dispaly two list   <SLEORGGRPLIST> and <SLEORGNPLNTCODELIST>
	 * @param table
	 * @param document
	 * @param parent
	 * @param parentItem
	 * @param relatedDiff
	 * @param path
	 * @param ctry
	 * @param outAction
	 * @param debugSb
	 */
	public void displayAVAILSLEORG(Hashtable table, Document document, Element parent, EntityItem parentItem,
		DiffEntity relatedDiff, String path, String ctry, String outAction, StringBuffer debugSb) {
		
		if (parentItem != null && relatedDiff != null && !DELETE_ACTIVITY.equals(outAction)) {			
			
			String COFCAT = "";
			COFCAT = getCOFCAT(parentItem);
			String  rootkey = null;
			// if <SLEORGGRPLIST> in <TAXCATEGORYLIST>, then getting SLEORGNPLNTCODE which related to TAX.
			// if <SLEORGGRPLIST> in <AVAIL>, then getting SLEORGNPLNTCODE which related to AVAIl and rootkey is MODEL.
			if ("TAXCATG".equals(relatedDiff.getEntityType()) || "TAXGRP".equals(relatedDiff.getEntityType()) || "D:AVAILSLEORGA:D".equals(path)) {
				rootkey = relatedDiff.getKey();
			}else{
				rootkey = parentItem.getKey();
			}
			
			//for the case 
			//T1:
			//SVCMOD --> SVCSEO1 --> AVAIL -->SOP
			//T2:
			//SVCMOD --> SVCSEO1--> AVAIL -->SOP
			//SVCMOD --> SVCSEO2--> AVAIL--->SOP
			//SOP should show in the update
			boolean SVCSEOIsNew = false;
			boolean isSVCSEO = false;
			if("SVCSEO".equals(parentItem.getEntityType())){
//				printTable(table,debug,debugSb);
				isSVCSEO = true;				
				if(path.startsWith("NEW")){
					SVCSEOIsNew = true;					
				}
				path ="D:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:"+relatedDiff.getKey()+":D:AVAILSLEORGA:D";				
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG path="+path+ NEWLINE);
			}
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG SVCSEOIsNew="+SVCSEOIsNew+",EntityType="+parentItem.getKey()+ NEWLINE);
			Vector SLEORGNPLNTVct = getSLEORGNPLNTcode(table, rootkey, path, isSVCSEO, debugSb);
			if (SLEORGNPLNTVct.size() > 0) {
				Hashtable chgTbl = new Hashtable();
				Hashtable T1GrpTbl = new Hashtable();
				Hashtable T2GrpTbl = new Hashtable();
				Vector[] SLEORGGRPVector = getSLEORGGRP(relatedDiff, debugSb);
				//buid and filter the Vector
				Vector ctrySOPElemMap = new Vector();

				for (int i = 0; i < SLEORGNPLNTVct.size(); i++) {
					DiffEntity sopDiff = (DiffEntity) SLEORGNPLNTVct.elementAt(i);
					buildCtrySOPRecs(ctrySOPElemMap, sopDiff, COFCAT, SLEORGGRPVector, ctry, SVCSEOIsNew, T1GrpTbl, T2GrpTbl, debugSb);
				}// end each planned avail
				
				//create <SLEORGGRPLIST>
				getChgTbl(T1GrpTbl, T2GrpTbl,chgTbl);
				createGroupNodeSet(document, parent, chgTbl, debugSb);
				//create <SLEORGNPLNTCODELIST>
				Element elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create COUNTRYAUDIENCEELEMENT
				parent.appendChild(elem);
				createCodeNodes(document, debugSb, ctrySOPElemMap, elem);
				ctrySOPElemMap.clear();
				chgTbl.clear();
				T1GrpTbl.clear();
				T2GrpTbl.clear();
				SLEORGGRPVector[0].clear();
				SLEORGGRPVector[1].clear();
			} else {
				Element elem = (Element) document.createElement(nodeName); // create List 			 		
				parent.appendChild(elem);
				elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create SLEORGNPLNTCODELIST
				parent.appendChild(elem);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG no SLEORGGRP found" + NEWLINE);
			}

		} else {
			Element elem = (Element) document.createElement(nodeName); // create List 			 		
			parent.appendChild(elem);
			elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create SLEORGNPLNTCODELIST
			parent.appendChild(elem);
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG no Parentitem and  related AVAIl found" + NEWLINE);
		}

	}

	private boolean createCodeNodes(Document document, StringBuffer debugSb,
			Vector ctrySOPElemMap, Element elem) {
		if(ctrySOPElemMap.size() == 0){
			ABRUtil.append(debugSb,"XMLSLEORGGRPElem.createCodeNodes:  no related SLEORGGRP found!" + NEWLINE);
			return false;
		}
//		first get all nochange and update change
		Vector nochange = new Vector();
		Vector updatechange = new Vector();
		Vector deleteChange = new Vector();
		for (int i = 0; i < ctrySOPElemMap.size(); i++) {
			CtrySOPRecord simplectrySopRec = (CtrySOPRecord) ctrySOPElemMap.get(i);
			simplectrySopRec.setallFields(null, debugSb);
			String simplecode = simplectrySopRec.getSLEORG()+simplectrySopRec.getPLNTCD();
			if (!simplectrySopRec.isDisplayable()) {									
				nochange.add(simplecode);				
			}else if(UPDATE_ACTIVITY.equals(simplectrySopRec.getAction())){
				updatechange.add(simplecode);
			}else if(DELETE_ACTIVITY.equals(simplectrySopRec.getAction())){
				deleteChange.add(simplecode);
			}
		}
		Vector codes = new Vector();
		
		for (int j = 0; j < ctrySOPElemMap.size(); j++) {
			CtrySOPRecord ctrySopRec = (CtrySOPRecord) ctrySOPElemMap.get(j);
			//ctrySopRec.setallFields(debugSb);
			String historyCode = ctrySopRec.getSLEORG()+ctrySopRec.getPLNTCD();
			String action = ctrySopRec.getAction();
			boolean isInclude = !nochange.contains(historyCode) //if no change existed, don't need to include
			&& (DELETE_ACTIVITY.equals(action) && !updatechange.contains(historyCode)// for deletion, also need to check update change
					|| (UPDATE_ACTIVITY.equals(action) && !deleteChange.contains(historyCode)));
			if (isInclude) {
				if(document != null && elem != null){
					String code = ctrySopRec.getSLEORG()+ctrySopRec.getPLNTCD() + action;
					if(!codes.contains(code)){//remove duplicated change
						createCodeNodeSet(document, elem, ctrySopRec, debugSb);
						codes.add(code);
					}
				}else{
					return true;
				}
			}
			ctrySopRec.dereference();
		}
		codes.clear();
		return false;
	}
	
		
	/**
	 * dispaly two list   <SLEORGGRPLIST> and <SLEORGNPLNTCODELIST>
	 * @param table
	 * @param document
	 * @param parent
	 * @param parentItem
	 * @param relatedDiff
	 * @param path
	 * @param ctry
	 * @param outAction
	 * @param debugSb
	 */
	public void displayAVAILSLEORGLSEO(Hashtable table, Document document, Element parent, EntityItem parentItem,
		DiffEntity relatedDiff, String path, String ctry, String outAction, String availType, StringBuffer debugSb) {
		
		if (parentItem != null && relatedDiff != null && !DELETE_ACTIVITY.equals(outAction)) {			
			
			String COFCAT = "";
			COFCAT = getCOFCAT(parentItem);
			String  rootkey = null;
			// if <SLEORGGRPLIST> in <TAXCATEGORYLIST>, then getting SLEORGNPLNTCODE which related to TAX.
			// if <SLEORGGRPLIST> in <AVAIL>, then getting SLEORGNPLNTCODE which related to AVAIl and rootkey is MODEL.
			if ("TAXCATG".equals(relatedDiff.getEntityType()) || "TAXGRP".equals(relatedDiff.getEntityType()) || "D:AVAILSLEORGA:D".equals(path)) {
				rootkey = relatedDiff.getKey();
			}else{
				rootkey = parentItem.getKey();
			}
			
			//for the case 
			//T1:
			//SVCMOD --> SVCSEO1 --> AVAIL -->SOP
			//T2:
			//SVCMOD --> SVCSEO1--> AVAIL -->SOP
			//SVCMOD --> SVCSEO2--> AVAIL--->SOP
			//SOP should show in the update
			boolean SVCSEOIsNew = false;
			boolean isSVCSEO = false;
			if("SVCSEO".equals(parentItem.getEntityType())){
				//printTable(table,debug,debugSb);
				isSVCSEO = true;				
				if(path.startsWith("NEW")){
					SVCSEOIsNew = true;					
				}
				path ="D:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:"+relatedDiff.getKey()+":D:AVAILSLEORGA:D";				
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG path="+path+ NEWLINE);
			}
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG SVCSEOIsNew="+SVCSEOIsNew+",EntityType="+parentItem.getKey()+ NEWLINE);
			//printTable(table,debug,debugSb);
			Vector SLEORGNPLNTVct = getSLEORGNPLNTcodeLSEO(table, rootkey, path, isSVCSEO, availType, debugSb);
			ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG ctry = "+ctry+";SLEORGNPLNTVct   size="+SLEORGNPLNTVct.size() + NEWLINE);
			if (SLEORGNPLNTVct.size() > 0) {
				Hashtable chgTbl = new Hashtable();
				Hashtable T1GrpTbl = new Hashtable();
				Hashtable T2GrpTbl = new Hashtable();
				Vector[] SLEORGGRPVector = getSLEORGGRP(relatedDiff, debugSb);
				//buid and filter the Vector
				Vector ctrySOPElemMap = new Vector();
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG currSLEORGGRPVct size="+SLEORGGRPVector[0].size()+";prevSLEORGGRPVct size="+SLEORGGRPVector[1].size()+NEWLINE);
				for (int i = 0; i < SLEORGNPLNTVct.size(); i++) {
					DiffEntity sopDiff = (DiffEntity) SLEORGNPLNTVct.elementAt(i);
					buildCtrySOPRecs(ctrySOPElemMap, sopDiff, COFCAT, SLEORGGRPVector, ctry, SVCSEOIsNew, T1GrpTbl, T2GrpTbl, debugSb);
				}// end each planned avail
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG ctrySOPElemMap size="+ctrySOPElemMap.size()+NEWLINE);
				
				//create <SLEORGGRPLIST>
				getChgTbl(T1GrpTbl, T2GrpTbl,chgTbl);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG chgTbl ="+chgTbl.size()+NEWLINE);
				createGroupNodeSet(document, parent, chgTbl, debugSb);
				//create <SLEORGNPLNTCODELIST>
				Element elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create COUNTRYAUDIENCEELEMENT
				parent.appendChild(elem);
				createCodeNodes(document, debugSb, ctrySOPElemMap, elem);
				ctrySOPElemMap.clear();
				chgTbl.clear();
				T1GrpTbl.clear();
				T2GrpTbl.clear();
				SLEORGGRPVector[0].clear();
				SLEORGGRPVector[1].clear();
			} else {
				Element elem = (Element) document.createElement(nodeName); // create List 			 		
				parent.appendChild(elem);
				elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create SLEORGNPLNTCODELIST
				parent.appendChild(elem);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG no SLEORGGRP found" + NEWLINE);
			}

		} else {
			Element elem = (Element) document.createElement(nodeName); // create List 			 		
			parent.appendChild(elem);
			elem = (Element) document.createElement("SLEORGNPLNTCODELIST"); // create SLEORGNPLNTCODELIST
			parent.appendChild(elem);
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.displayAVAILSLEORG no Parentitem and  related AVAIl found" + NEWLINE);
		}

	}

	private void getChgTbl(Hashtable T1GrpTbl, Hashtable T2GrpTbl, Hashtable chgTbl) {
		Enumeration T1keys = T1GrpTbl.keys();
		while(T1keys.hasMoreElements()){
			String Grp = (String) T1keys.nextElement();
			if(!T2GrpTbl.containsKey(Grp)){
				chgTbl.put(Grp, DELETE_ACTIVITY);				
			}
		}
		
		Enumeration T2keys = T2GrpTbl.keys();
		while(T2keys.hasMoreElements()){
			String Grp = (String) T2keys.nextElement();
			if(!T1GrpTbl.containsKey(Grp)){
				chgTbl.put(Grp, UPDATE_ACTIVITY);				
			}
		}
		
	}

	/**
	 * createCodeNodeSet for SLEORGGRPELEMENT
	 * @param document
	 * @param parent
	 * @param ctrySopRec
	 * @param debugSb
	 */
	private void createCodeNodeSet(Document document, Element parent, CtrySOPRecord ctrySopRec, StringBuffer debugSb) {

		//add child nodes
		Element childElement = (Element) document.createElement("SLEORGNPLNTCODEELEMENT");
		parent.appendChild(childElement);
		Element child = (Element) document.createElement("SLEORGNPLNTCODEACTION");
		child.appendChild(document.createTextNode("" + ctrySopRec.getAction()));
		childElement.appendChild(child);
		child = (Element) document.createElement("SLEORG");
		child.appendChild(document.createTextNode("" + ctrySopRec.getSLEORG()));
		childElement.appendChild(child);
		child = (Element) document.createElement("PLNTCD");
		child.appendChild(document.createTextNode("" + ctrySopRec.getPLNTCD()));
		childElement.appendChild(child);

	}

	/**
	 * createGroupNodeSet for SLEORGGRPELEMENT
	 * @param document
	 * @param parent
	 * @param checktable
	 * @param debugSb
	 */
	private void createGroupNodeSet(Document document, Element parent, Hashtable chgTbl, StringBuffer debugSb) {
		Element elem = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT	 			 		
		parent.appendChild(elem);
		Enumeration keys = chgTbl.keys();
		while(keys.hasMoreElements()){
			String SLEORGGRP = (String)keys.nextElement();
			String SLEOORGGRPACTION = (String)chgTbl.get(SLEORGGRP);
			Element childElement = (Element) document.createElement("SLEORGGRPELEMENT");
			elem.appendChild(childElement);
			Element child = (Element) document.createElement("SLEOORGGRPACTION");
			child.appendChild(document.createTextNode("" + SLEOORGGRPACTION));
			childElement.appendChild(child);
			child = (Element) document.createElement("SLEORGGRP");
			child.appendChild(document.createTextNode("" + SLEORGGRP));
			childElement.appendChild(child);
		}
	}
	/**
	 * 
	 * @param relatedDiff
	 * @param debugSb
	 * @return
	 */
	private Vector[] getSLEORGGRP(DiffEntity relatedDiff, StringBuffer debugSb) {
		//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGGRP for " + relatedDiff.getKey() + NEWLINE);
		Vector currSLEORGGRPVct = new Vector(1);
		Vector prevSLEORGGRPVct = new Vector(1);
		Vector vct[] = new Vector[2];
		vct[0] = currSLEORGGRPVct;
		vct[1] = prevSLEORGGRPVct;
		EntityItem curritem = relatedDiff.getCurrentEntityItem();
		if (curritem != null){
			EANFlagAttribute SLEORGGRPAtt = (EANFlagAttribute) curritem.getAttribute("SLEORGGRP");
			
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGGRP cur SLEORGGRP " + SLEORGGRPAtt + NEWLINE);
			if (SLEORGGRPAtt != null) {
				MetaFlag[] mfArray = (MetaFlag[]) SLEORGGRPAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						currSLEORGGRPVct.addElement(mfArray[i].toString()); // this is long desc
					}
				}
			}	
		}
		if (!relatedDiff.isNew()) {
			EANFlagAttribute Att = (EANFlagAttribute) relatedDiff.getPriorEntityItem().getAttribute("SLEORGGRP");
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGGRP prior SLEORGGRP " + Att + NEWLINE);
			if (Att != null) {
				MetaFlag[] mfArray = (MetaFlag[]) Att.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						prevSLEORGGRPVct.addElement(mfArray[i].toString()); // this is long desc
					}
				}
			}
		}

		return vct;
	}

	/**
	 * check the item is from the same relator that it connect from the root entity
	 * @param item
	 * @param debugSb
	 * @return
	 */
	private Vector getSLEORGNPLNTcodeLSEO(Hashtable table, String rootkey, String path, boolean isSVCSEO, String availType, StringBuffer debugSb) {

		Vector LSEOVct = (Vector) table.get("SLEORGNPLNTCODE");		
		Vector allVct = new Vector(1); 
		if (LSEOVct != null) {
			for (int i = 0; i < LSEOVct.size(); i++) {
				DiffEntity diffitem = (DiffEntity) LSEOVct.elementAt(i);
				if(diffitem.toString().indexOf("MODELAVAIL")>-1){
					if(availType.equals("LSEOSPECBID")) 
					{
						allVct.add(diffitem);
					}
				} else if(diffitem.toString().indexOf("LSEOAVAIL")>-1){
					if(availType.equals("LSEO")||availType.equals("LSEOSPECBID")) {
						allVct.add(diffitem);
					}
				}
			}
		}	
		return allVct;
	}
	
	/**
	 * check the item is from the same relator that it connect from the root entity
	 * @param item
	 * @param debugSb
	 * @return
	 */
	private Vector getSLEORGNPLNTcode(Hashtable table, String rootkey, String path, boolean isSVCSEO, StringBuffer debugSb) {

		Vector overrideVct = new Vector(1);
		Vector avlVct = new Vector(1);
		Vector allVct = (Vector) table.get("SLEORGNPLNTCODE");

		//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGNPLNTcode looking for AVAILTYPE:146 in AVAIL" + " allVct.size:"
		//	+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
		if (allVct == null) {
			return avlVct;
		}
		//TODO PRODSTRUCT XMLTMFAVAILElem.java no path for Model avail.
		if (path == null){
			return allVct;
		}
		
		StringTokenizer st1 = new StringTokenizer(path, ":");
		int level = (st1.countTokens() - 1) / 2;
		String relator = CHEAT;
		String[] path1 = new String[level];

		while (st1.hasMoreTokens()) {
			st1.nextToken();
			if (st1.hasMoreTokens()) {
				relator = st1.nextToken();
				path1[--level] = relator;
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGNPLNTcode: node:" + nodeName + " path:" + path + " dir:" + dir
				//	+ " relator " + relator + NEWLINE);

			}
		}
		// find those of specified type
		for (int i = 0; i < allVct.size(); i++) {
			DiffEntity de = (DiffEntity) allVct.elementAt(i);
			StringTokenizer st2 = new StringTokenizer(de.toString(), " ");
			String currentPath = CHEAT;
			while (st2.hasMoreTokens()) {
				currentPath = st2.nextToken();
				if (currentPath.startsWith("path:"))
					break;
			}
			StringTokenizer st3 = new StringTokenizer(currentPath, ":");
			int level2 = st3.countTokens();
			String[] currPath = new String[level2];
			int j = 0;
			while (st3.hasMoreTokens()) {
				String value = st3.nextToken();
				if (!"path".equals(value)) {
					currPath[j++] = value;
					//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGNPLNTcode: currPath level: " + j + "  value:" + value + NEWLINE);
				}

			}
			boolean isDeriveParent = false;
			int pathLength = path1.length;
			for (int jj = 0; jj < pathLength; jj++) {
				if (currPath[jj].startsWith(path1[jj])) {
					isDeriveParent = true;
				} else {
					isDeriveParent = false;
					break;
				}
				if (jj == pathLength - 1 && isDeriveParent) {
					if(jj!=7 && isSVCSEO==false){
						if (currPath[pathLength] != null && currPath[pathLength].startsWith(rootkey)) {
							isDeriveParent = true;
						} else {
							isDeriveParent = false;
						}
					}
				}
			}
			if (isDeriveParent) {
				overrideVct.add(de);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.getSLEORGNPLNTcode: put SLEORGGRP into Vector" + de.getKey() + NEWLINE);
			}

		}
		return overrideVct;
	}
	private void addGrpTbl(EntityItem entityItem, Hashtable GrpTbl,Vector SLEORGGRPVector, StringBuffer debugSb, boolean iscurrent){
		EANFlagAttribute sopAtt = (EANFlagAttribute) entityItem.getAttribute("SLEORGGRP");
		if (sopAtt != null ){
			MetaFlag[] mfArray = (MetaFlag[]) sopAtt.get();
			//ABRUtil.append(debugSb,"addGrpTbl isCurrent:" + iscurrent + " SLEORGROP mfArray :" + sopAtt.toString() + NEWLINE);
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (mfArray[im].isSelected()) {
					String sopVal = mfArray[im].toString();
					//when the SLEORGGRP of AVAIL or TAXCODE etc has no value, put all the SLEORGGRP of 
					//SLEORGNPLNTCODE into the Hashtable,else only put the matching SLEORGGRP.
					if(SLEORGGRPVector.size()==0){
						GrpTbl.put(sopVal, CHEAT);						
					}else if (SLEORGGRPVector.contains(sopVal)) {
						GrpTbl.put(sopVal, CHEAT);	
					}					
				}
			}
		}	

	}

	/**
	 *	<SLEORGGRPLIST>
	 See  SLEORGGRPLIST and SLEORGNPLNTCODE Implementation Example below for further details.
	 4.	Retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.SLEORGGRP = a value in AVAIL.SLEORGGRP and SLEORGNPLNTCODE.MODCATG = MODEL.COFCAT. 
	 5.	From the retrieved entities, select those where SLEORGNPLNTCODE.COUNTRYLIST = the value of COUNTRY_FC for which AVAILABILITYELEMENT is being generated.
	 6.	For each unique value of SLEORGNPLNTCODE.SLEORGGRP, create a SLEORGGRPELEMENT, where <SLEORGGRP> is set to that unique value.  If no rows are selected in (2), then no SLEORGGRPELEMENTs are created.
	 n)	<SLEORGNPLNTCODELIST>
	 See  SLEORGGRPLIST and SLEORGNPLNTCODELIST Implementation Example below for further details.
	 4.	Retrieve all SLEORGNPLNTCODE entities where SLEORGPLNTCODE.SLEORGGRP = a value in AVAIL.SLEORGGRP and SLEORGPLNTCODE.MODCATG = MODEL.COFCAT.  
	 5.	From the retrieved entities, select those where SLEORGPLNTCODE.COUNTRYLIST = the value of COUNTRY_FC for which AVAILABILITYELEMENT is being generated.
	 6.	For each selected row, create a SLEORGNPLNTCODEELEMENT, where
	  	 <SLEORG> = SLEORGNPLNTCODE.SLEORG
	  	<PLNTCD> = SLEORGNPLNTCODE.PLNTCD
	 If no rows are selected in (2), then no SLEORGNPLNTOCDEELEMENTs are created.
	 * @param ctryAudElemMap
	 * @param sopDiff
	 * @param pItem
	 * @param relatedDiff
	 * @param ctry
	 * @param debugSb
	 */
	private void buildCtrySOPRecs(Vector ctryAudElemMap, DiffEntity sopDiff, String COFCAT, Vector[] SLEORGGRPVector, String ctry,
		boolean SVCSEOIsNew, Hashtable T1GrpTbl, Hashtable T2GrpTbl, StringBuffer debugSb) {

		//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildCtrySOPRecs key=" + sopDiff.getKey() + NEWLINE);

		EntityItem curritem = sopDiff.getCurrentEntityItem();
		EntityItem prioritem = sopDiff.getPriorEntityItem();

		Vector currSLEORGGRPVector = SLEORGGRPVector[0];
		Vector priorSLEORGGRPVector = SLEORGGRPVector[1];
		if (sopDiff.isDeleted()) { // If the AVAIL was deleted, set Action = Delete
			// mark all records as delete
			boolean isAvailable = checkSLEORGGRPAvailable(ctry, prioritem, COFCAT, priorSLEORGGRPVector, debugSb,false);
			if (isAvailable) {
				CtrySOPRecord ctrySOPRec = new CtrySOPRecord(sopDiff);
				ctrySOPRec.setAction(DELETE_ACTIVITY);
				ctryAudElemMap.add(ctrySOPRec);		
				//add T1 group table
				addGrpTbl(prioritem,T1GrpTbl,priorSLEORGGRPVector,debugSb,false);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs for deleted:" + sopDiff.getKey() + NEWLINE);
			}
		} else if (sopDiff.isNew()) {
			// mark all records as update
			boolean isAvailable = checkSLEORGGRPAvailable(ctry, curritem, COFCAT, currSLEORGGRPVector, debugSb,true);
			//ABRUtil.append(debugSb,"test case spLSEO 21 isAvailable=" + isAvailable + NEWLINE);
			if (isAvailable) {
				CtrySOPRecord ctrySOPRec = new CtrySOPRecord(sopDiff);
				ctrySOPRec.setAction(UPDATE_ACTIVITY);
				ctryAudElemMap.add(ctrySOPRec);				
				//add T2 group table
				addGrpTbl(curritem,T2GrpTbl,currSLEORGGRPVector,debugSb,true);			
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs for new:" + sopDiff.getKey() + NEWLINE);
			}

		} else {//
			// T1 
			
			boolean t1Exist = checkSLEORGGRPAvailable(ctry, prioritem, COFCAT, priorSLEORGGRPVector, debugSb , false);
			boolean t2Exist = checkSLEORGGRPAvailable(ctry, curritem, COFCAT, currSLEORGGRPVector, debugSb, true);
			//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs key="+sopDiff.getKey()+";t1Exist=" + t1Exist + ";t2Exist= "+t2Exist + NEWLINE);
			if (t1Exist && t2Exist) {
				CtrySOPRecord ctrySOPRec = new CtrySOPRecord(sopDiff);
				ctryAudElemMap.add(ctrySOPRec);
				if(SVCSEOIsNew){
					ctrySOPRec.setAction(UPDATE_ACTIVITY);
				}				
				//add T1 and T2 group table
				addGrpTbl(prioritem,T1GrpTbl,priorSLEORGGRPVector,debugSb,false);
				addGrpTbl(curritem,T2GrpTbl,currSLEORGGRPVector,debugSb,true);				
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs for change:" + sopDiff.getKey() + NEWLINE);
			}
			if (t1Exist == true && t2Exist == false) {
				CtrySOPRecord ctrySOPRec = new CtrySOPRecord(sopDiff);
				ctrySOPRec.setAction(DELETE_ACTIVITY);
				ctryAudElemMap.add(ctrySOPRec);				
				//add T1 and T2 group table
				addGrpTbl(prioritem,T1GrpTbl,priorSLEORGGRPVector,debugSb,false);
				//addGrpTbl(curritem,T2GrpTbl,currSLEORGGRPVector,debugSb);
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs for change: ctrySOPRec=" + ctrySOPRec.toString() + NEWLINE);
			}
			if (t1Exist == false && t2Exist == true) {
				CtrySOPRecord ctrySOPRec = new CtrySOPRecord(sopDiff);
				ctrySOPRec.setAction(UPDATE_ACTIVITY);
				ctryAudElemMap.add(ctrySOPRec);				
				//add T1 and T2 group table
				//addGrpTbl(prioritem,T1GrpTbl,priorSLEORGGRPVector,debugSb);
				addGrpTbl(curritem,T2GrpTbl,currSLEORGGRPVector,debugSb,true);		
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.buildSOPRecs for change:" + sopDiff.getKey() + NEWLINE);
			}
		}
	}

	/**
	 * checKSLEORGGRPAvailable
	 * @param ctry
	 * @param item
	 * @param pItem
	 * @param relatedItem
	 * @param debugSb
	 * @return
	 */
	private boolean checkSLEORGGRPAvailable(String ctry, EntityItem SOPitem, String modelCOFCAT, Vector SLEORGGRPVector,
		StringBuffer debugSb, boolean iscurrent) {
		
		//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.checkSLEORGGRPAvailable  iscurrent="+ iscurrent +NEWLINE);
		boolean isAvailable = false;

		String cofcat = "";
		EANFlagAttribute fAtt = (EANFlagAttribute)SOPitem.getAttribute("MODCATG");
		if (fAtt!=null && fAtt.toString().length()>0){
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            for (int i = 0; i < mfArray.length; i++){
                // get selection
                if (mfArray[i].isSelected())
                {
                	cofcat = (mfArray[i].toString()); // get longdesription 
                	break;
                }
            }
		}
		//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.checkSLEORGGRPAvailable for Countrylist: ctryAtt " 
		//	+ PokUtils.getAttributeFlagValue(SOPitem, "COUNTRYLIST") + " ctry: " + ctry   + " SLORGGRP COFCAT : " + cofcat +  " modelCOFCAT : " + modelCOFCAT + NEWLINE);
		//case LSBOUNDLE
		if (modelCOFCAT!=null && modelCOFCAT.indexOf(",")>-1){
			if("".equals(cofcat)) return false;
			else if(modelCOFCAT.indexOf(cofcat)==-1) return false;			
		}else{
			if(!cofcat.equals(modelCOFCAT)) return false;
		}
		if (SLEORGGRPVector.size() > 0 ) {
			EANFlagAttribute sopAtt = (EANFlagAttribute) SOPitem.getAttribute("SLEORGGRP");
			
			if (sopAtt != null ){
				MetaFlag[] mfArray = (MetaFlag[]) sopAtt.get();
				//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.checkSLEORGGRPAvailable SLEORGGRP ="+mfArray.toString() + NEWLINE);
				for (int im = 0; im < mfArray.length; im++) {
					// get selection
					if (mfArray[im].isSelected()) {
						String sopVal = mfArray[im].toString();
						//Retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.SLEORGGRP = a value in AVAIL.SLEORGGRP and SLEORGNPLNTCODE.MODCATG = MODEL.COFCAT.
						if (SLEORGGRPVector.contains(sopVal)) {
								//From the retrieved entities, select those where SLEORGNPLNTCODE.COUNTRYLIST = the value of COUNTRY_FC for which AVAILABILITYELEMENT is being generated.
								if (!CHEAT.equals(ctry)) {
									EANFlagAttribute ctryAtt = (EANFlagAttribute) SOPitem.getAttribute("COUNTRYLIST");
									//ABRUtil.append(debugSb,"XMLSLEORGGRPElem.checkSLEORGGRPAvailable SLEO cntry = " +  ctryAtt.toString() + NEWLINE);
									if (ctryAtt != null && ctryAtt.isSelected(ctry)) {
										isAvailable = true;
										break;
									}
								}else{
									isAvailable = true;
									break;
								}
							}
					}
				}
			}
		} else if (!CHEAT.equals(ctry)) {
			//If this is an AVAIL for a withdrawn product and has no SLEORGGP values, select all rows where SLEORGNPLNTCODE.COUNTRYLIST equals a value in AVAIL.COUNTRYLIST, and where SLEORGNPLNTCODE.MODCATG = MODEL.COFCAT.
			EANFlagAttribute ctryAtt = (EANFlagAttribute) SOPitem.getAttribute("COUNTRYLIST");
			if (ctryAtt != null && ctryAtt.isSelected(ctry)) {
			    isAvailable = true;
			}
		}
		return isAvailable;
	}

	/**
	 * create CtrySOPRecord instance for each SLEORGGRP
	 * @author guobin
	 *
	 */
	private static class CtrySOPRecord {
		private String action = CHEAT;

		private Vector SLEORGGRP = new Vector(); 

		private String SLEORG = CHEAT;

		private String PLNTCD = CHEAT;

		private DiffEntity sopDiff;

		boolean isDisplayable() {
			return !action.equals(CHEAT);
		} // only display those with filled in actions

		public void dereference() {
			action = null;
			SLEORGGRP = null;
			SLEORG = null;
			PLNTCD = null;
		}

		CtrySOPRecord(DiffEntity diffitem) {
			sopDiff = diffitem;
		}

		void setAction(String s) {
			action = s;
		}

		public String getAction() {
			return action;
		}

		public String getPLNTCD() {
			return PLNTCD;
		}

		public String getSLEORG() {
			return SLEORG;
		}

//		public Vector getSLEORGGRP() {
//			return SLEORGGRP;
//		}

		public void setallFields(String outAction, StringBuffer debugSb) {
			//ABRUtil.append(debugSb,"CtryRecord.setAllFields entered for: " + sopDiff.getKey() + NEWLINE);

			EntityItem curritem = sopDiff.getCurrentEntityItem();
			EntityItem previtem = sopDiff.getPriorEntityItem();
			/**
			 * not handle the outaction and action
			 */
//			if ("".equals(action) || CHEAT.equals(action))
//				setAction(UPDATE_ACTIVITY); 
//			if (UPDATE_ACTIVITY.equals(outAction) && action.equals(CHEAT))
//				setAction(UPDATE_ACTIVITY); 
//			if (UPDATE_ACTIVITY.equals(outAction))
//				setAction(UPDATE_ACTIVITY);
			if (!DELETE_ACTIVITY.equals(action)) {
				String priorPLNTCD = CHEAT;
				if (curritem != null) {
					PLNTCD = PokUtils.getAttributeValue(curritem, "PLNTCD", ", ", CHEAT, false);
				}
				if (previtem != null) {
					priorPLNTCD = PokUtils.getAttributeValue(previtem, "PLNTCD", ", ", CHEAT, false);
				}
				if (!PLNTCD.equals(priorPLNTCD)) {
					setAction(UPDATE_ACTIVITY);
				}

				String priorSLEORG = CHEAT;
				if (curritem != null) {
					SLEORG = PokUtils.getAttributeValue(curritem, "SLEORG", ", ", CHEAT, false);
				}
				if (previtem != null) {
					priorSLEORG = PokUtils.getAttributeValue(previtem, "SLEORG", ", ", CHEAT, false);
				}
				if (!SLEORG.equals(priorSLEORG)) {
					setAction(UPDATE_ACTIVITY);
				}
				if (curritem != null) {
					EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute("SLEORGGRP");
					if (fAtt!=null && fAtt.toString().length()>0){
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
						for (int i = 0; i < mfArray.length; i++){
							// get selection
							if (mfArray[i].isSelected()){
								SLEORGGRP.add(mfArray[i].toString());
							}  // metaflag is selected
						}// end of flagcodes
					}
				}	
				if(sopDiff.isDeleted()){
					if (previtem != null) {
						PLNTCD = PokUtils.getAttributeValue(previtem, "PLNTCD", ", ", CHEAT, false);
						SLEORG = PokUtils.getAttributeValue(previtem, "SLEORG", ", ", CHEAT, false);
						setAction(DELETE_ACTIVITY);
					}
				}
			} else {
				if (previtem != null) {
					PLNTCD = PokUtils.getAttributeValue(previtem, "PLNTCD", ", ", CHEAT, false);
					SLEORG = PokUtils.getAttributeValue(previtem, "SLEORG", ", ", CHEAT, false);
					setAction(DELETE_ACTIVITY);
				}
			}
			

		}
		public String toString() {
			return "action:" + action + " SLEORGGP:" + SLEORGGRP + " SLEORG:" + SLEORG + " PLNTCD:" + PLNTCD;
		}
	}

}
