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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.*;



public class XMLAVAILElemFCT extends XMLElem
{
	
	private static String countryStr="1427,1428,1429,1430,1431,1432,1433,1434,1435,1436,1437,1438,1439,1440,1441,1442,1443,1444,1445,1446,1447,1448,1449,1450,1451,1452,1453,1454,1455,1456,1457,1458,1459,1460,1461,1462,1463,1464,1465,1466,1467,1468,1469,1470,1471,1472,1473,1474,1475,1476,1477,1478,1479,1480,1481,1482,1483,1484,1485,1486,1487,1488,1489,1490,1491,1492,1493,1494,1495,1497,1498,1499,1500,1501,1502,1503,1504,1505,1506,1507,1508,1509,1510,1511,1512,1513,1514,1515,1516,1517,1518,1519,1520,1521,1522,1523,1524,1525,1526,1527,1528,1529,1530,1531,1532,1533,1534,1535,1536,1537,1538,1539,1540,1541,1542,1543,1544,1545,1546,1547,1548,1549,1550,1551,1552,1553,1554,1555,1556,1557,1558,1559,1560,1561,1562,1563,1564,1565,1566,1567,1568,1569,1570,1571,1572,1573,1574,1575,1576,1577,1578,1580,1581,1582,1583,1584,1585,1586,1587,1588,1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599,1600,1601,1602,1603,1604,1605,1606,1607,1608,1609,1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1626,1627,1628,1629,1630,1631,1632,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,1659,1660,1661,1662,1663,1664,1665,1666,1668,1669,1670";

	
    public XMLAVAILElemFCT()
    {
        super("COUNTRYELEMENT");
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
    	Element elem = (Element) document.createElement("COUNTRYLIST"); // create COUNTRYAUDIENCEELEMENT
		addXMLAttrs(elem);
		parent.appendChild(elem);
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
    				
    				if (ctryAudRec.isDisplayable()){
    					createNodeSet(document, elem, ctryAudRec, debugSb);
    				}else{
                		ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no changes found for "+ctryAudRec+NEWLINE);
    				}
    				ctryAudRec.dereference();
    			}

    			// release memory
    			ctryAudElemMap.clear();
//    			Vector annVct = (Vector)table.get("ANNOUNCEMENT");
//    			Vector availVct = (Vector)table.get("AVAIL");
//    			availVct.clear();
//    			annVct.clear();
    		}else{

    				String [] countrys = countryStr.split(",");
    			
    			for(int i =0;i<countrys.length;i++) {
    				
    				Element elems = (Element) document.createElement(nodeName); // create COUNTRYAUDIENCEELEMENT
    				addXMLAttrs(elems);
    				elem.appendChild(elems);

    				// add child nodes
    				Element child = (Element) document.createElement("COUNTRYACTION");
    				child.appendChild(document.createTextNode("Update"));
    				elems.appendChild(child);
    			
    				child = (Element) document.createElement("COUNTRY_FC");
    				child.appendChild(document.createTextNode(countrys[i]));
    				elems.appendChild(child);
    				
    			}
    			

    			// release memory
    			
    			ABRUtil.append(debugSb,"XMLCtryAudElem.addElements no planned AVAILs found"+NEWLINE);
    			
    			
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
		Element child = (Element) document.createElement("COUNTRYACTION");
		child.appendChild(document.createTextNode(""+ctryAudRec.getAction()));
		elem.appendChild(child);
	
		child = (Element) document.createElement("COUNTRY_FC");
		child.appendChild(document.createTextNode(""+ctryAudRec.getCountry()));
		elem.appendChild(child);
		
		//child = (Element) document.createElement("EARLIESTSHIPDATE");
		//child.appendChild(document.createTextNode(""+ctryAudRec.getShipDate()));
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
	 *  return Boolean check If there isn鈥檛 an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE less than "20100301"   
	 *  @param table  
	 *         Hashtable that contain all the entities.
	 *  @param availtype 
	 *         AVAIL.AVAILTYPE 
	 *  @param debugSb
	 *         StringBuffer logo output.
	 *         
	 *  If there isn鈥檛 an AVAIL where AVAILTYPE="Planned Availability" (146), and ANNDATE < 鈥�0100301鈥�then
	 *  from MODEL attributes
	 *  Note:
	 *  Condition1 is there isn't an Planned Avail.  
	 *  Condition2 is Model.ANNDATE < 鈥�0100301鈥�- removed based on BH FS ABR XML System Feed Mapping 20120612.doc
	 **/
	private boolean isDerivefromModel(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
		boolean condition1 = true;
//		boolean condition2 = false;
		if (parentItem != null) {
			if (parentItem.getEntityType().equals("MODEL") || parentItem.getEntityType().equals("SVCMOD")) {
				String ANNDATE_Fix = "2010-03-01";
				Vector allVct = (Vector) table.get("AVAIL");
				ABRUtil.append(debugSb,"DerivefromModel.getAvails looking for AVAILTYPE: 146 " + "in AVAIL" + " allVct.size:"
					+ (allVct == null ? "null" : "" + allVct.size()) + NEWLINE);
				if (allVct != null) {
					// find those of specified type
					for (int i = 0; i < allVct.size(); i++) {
						DiffEntity diffitem = (DiffEntity) allVct.elementAt(i);
						EntityItem curritem = diffitem.getCurrentEntityItem();
						if (!diffitem.isDeleted()) {
							ABRUtil.append(debugSb,"XMLANNElem.DerivefromModel.getAvails checking[" + i + "]:New or Update"
								+ diffitem.getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(curritem, "AVAILTYPE")
								+ NEWLINE);
							EANFlagAttribute fAtt = (EANFlagAttribute) curritem.getAttribute("AVAILTYPE");
							if (fAtt != null && fAtt.isSelected("146")) {
								condition1 = false;
								break;
							}
						}
					}
				}
				//removed based on BH FS ABR XML System Feed Mapping 20120612.doc
//				EntityItem curritem = parentItem.getCurrentEntityItem();
//				if (curritem != null) {
//					String anndate = PokUtils.getAttributeValue(curritem, "ANNDATE", ", ", CHEAT, false);
//					condition2 = anndate.compareTo(ANNDATE_Fix) <= 0;
//					ABRUtil.append(debugSb,"XMLANNElem.DerivefromModel.get model ANNDATE" + curritem.getKey() + " ANNDATE: " + anndate
//						+ NEWLINE);
//				}
			}

		}
		return condition1;
//		&& condition2;
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
         2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
         3.	Empty (aka Null)
		*  * 	<PUBTO> 
		The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = "Last Order" (149)
        2 .	Empty (aka Null)
	
		* 	 * <ENDOFSERVICEDATE>
        The first applicable / available date is used.
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 锟斤拷End of Service锟斤拷 (151)
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
        1.	AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE = 锟斤拷End of Service锟斤拷 (151)
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
         2.	ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
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
					// try to get it from ANNOUNCEMENT.ANNDATE for the AVAIL where AVAIL.AVAILTYPE = 锟斤拷Planned Availability锟斤拷 (146).
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


