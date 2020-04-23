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
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
* Class used to hold info and structure to be generated for the xml feed
* for abrs.  values are fixed for these nodes
*/
// $Log: XMLSVCMODCVMSPECElem.java,v $
// Revision 1.6  2015/01/26 15:53:40  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.5  2011/08/23 13:02:22  guobin
// update getContent method
//
// Revision 1.3  2011/03/16 07:20:37  guobin
// add hasChanges method
//
// Revision 1.2  2011/03/14 09:03:49  guobin
// update the mapping and add derivation rule for SVCMOD
//
// Revision 1.1  2011/03/11 03:08:30  guobin
//  Change 20110310	Change mapping and add derivation rule for SVCMOD
//
// Init for
// Change 20110310	Change mapping and add derivation rule	
// 104.00		1	1.0	<REFCVMSPECID>	</REFCVMSPECID>	8	SVCMOD_UPDATE /CHRGCOMPLIST /CHRGCOMPELEMENT /PRICEPOINTLIST /PRICEPOINTELEMENT /REFCVMSPECLIST /REFCVMSPECELEMENT /REFCVMSPECID				
// CVMSPEC	"CHARACID// VMSPECID"		Text	

//From CVMSPEC find CVM then
// 	If CVM.CVMTYPE = 'C1' (Characteristic) 
//		set to CVMSPEC.CHARACID, 
//	else 
//		set to CVMSPEC.VMSPECID  
//
//

public class XMLSVCMODCVMSPECElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for null value elements
    *
    */
	private String path = null;
	private String etype =null;
    public XMLSVCMODCVMSPECElem(String nname)
    {
    	super(nname);
    	path = null;
    }    

    public XMLSVCMODCVMSPECElem(String nname, String code, String etype1, String rootpath )
    {
    	super(nname,code);
         path = rootpath;
         etype = etype1;
    }    

    /**
     * check if it has changed
     *@param table Hashtable of Vectors of DiffEntity
     *@param table Hashtable of Vectors of DiffEntity 
     *@param debugSb StringBuffer for debug output
     */
    protected boolean hasChanges(Hashtable table, DiffEntity parentItem, StringBuffer debugSb) {
        //check at both times if one existed or not
		EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem previtem = parentItem.getPriorEntityItem();
		boolean changed = false;
    	String currVal = "";
		String prevVal = "";
		//T1 time
		if (previtem != null){
			prevVal = getCONTENTS(previtem, debugSb);
		}
		//T2 time
		if (curritem != null){
			currVal = getCONTENTS(curritem, debugSb);			
		}
		if (!currVal.equals(prevVal)){ // we only care if there was a change
			changed = true;
		}		
		
    	return changed;
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
		String currContents= "";
		String parenttype = parentItem.getEntityType();
		ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem entitytype ="  + parenttype + NEWLINE);
		if(parenttype.equals("CVMSPEC")){
			EntityItem currentItem = parentItem.getCurrentEntityItem();
			currContents = getCONTENTS(currentItem, debugSb);
		} 

        createNodeSet(document,parent,currContents,debugSb);
	}

	/**
	 * From CVMSPEC find CVM then
 	 *	CVM.CVMTYPE must equal to CVMSPEC.CVMTYPE
	 *		If CVM.CVMTYPE = 'C1' (Characteristic) 
	 *			set to CVMSPEC.CHARACID, 
	 *		else 
	 *	set to CVMSPEC.VMSPECID 
	 *
	 * @param parentItem
	 * @param debugSb
	 * @param CONTENTS
	 * @param findCVM
	 * @return
	 */
	private String getCONTENTS(EntityItem cvmspecItem, StringBuffer debugSb) {
		
		boolean findCVM = false;
		String CONTENTS = "";
		String CVMTYPE = "";
		if (cvmspecItem != null){
			if (path!=null){
				Vector desVec = getPathEntity(cvmspecItem,debugSb);
				for (int i=0; i<desVec.size(); i++){
					EntityItem desentityitem = (EntityItem )desVec.elementAt(i);
				    CVMTYPE = PokUtils.getAttributeFlagValue(desentityitem, attrCode);
	    			if(CVMTYPE.equalsIgnoreCase("C1")){
	    				CONTENTS = PokUtils.getAttributeValue(cvmspecItem, "CHARVAL", ", ", CHEAT, false);	        				
	    			}else if (CVMTYPE.equalsIgnoreCase("C2")){			
	    				CONTENTS = PokUtils.getAttributeValue(cvmspecItem, "VMSPECID", ", ", CHEAT, false);
	    			}
				}
				ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem get from Path CVMTYPE:" + CVMTYPE + " cvmspecID:" + CONTENTS + NEWLINE);
			}else {			 
				Vector uprelator = cvmspecItem.getUpLink();
				for (int i=0; i<uprelator.size(); i++){
					EntityItem upItem = (EntityItem)uprelator.get(i);
					if (upItem != null && "CVMCVMSPEC".equals(upItem.getEntityType())){
						//find the CVM
						EntityItem cvmItem = (EntityItem)upItem.getUpLink(0);
						if(cvmItem!=null){
			    		     CVMTYPE = PokUtils.getAttributeFlagValue(cvmItem, "CVMTYPE");
			    			if(CVMTYPE!=null && CVMTYPE.equalsIgnoreCase("C1")){
			    				CONTENTS = PokUtils.getAttributeValue(cvmspecItem, "CHARACID", ", ", CHEAT, false);
			    				findCVM = true;		    				
				    			break;
			    			}
						}
					}
				}
				if(!findCVM){
					CONTENTS = PokUtils.getAttributeValue(cvmspecItem, "VMSPECID", ", ", CHEAT, false);
				}	
			}	
		}
		
		return CONTENTS;
	}
	
	
	/**
	 * create nodeset of the REFCVMSPECID attribute of CVMSPEC 
	 * 
	 * @param document
	 * @param parent
	 * @param debugSb
	 */
	private void createNodeSet(Document document, Element parent,
			String CONTENTS, StringBuffer debugSb) {
		Element child = (Element) document.createElement(nodeName); 
		child.appendChild(document.createTextNode("" + CONTENTS));
		parent.appendChild(child);
	}	
	/**
	 * through the path get the destinaiton entity.
	 * @param parentitem
	 * @param path
	 * @param debugSb
	 * @return
	 */
	private Vector getPathEntity(EntityItem parentitem, StringBuffer debugSb){
		Vector parentitemsVct = new Vector(1);
		Vector overrideVct = new Vector(1);
		parentitemsVct.add(parentitem);
		StringTokenizer st1 = new StringTokenizer(path, ":");
		while (st1.hasMoreTokens()) {
			String dir = st1.nextToken();
			String destination = etype;
			if (st1.hasMoreTokens()) {
				destination = st1.nextToken();
			}
			ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem.getItems: node:" + nodeName + " path:" + path + " dir:" + dir + " destination "
				+ destination + NEWLINE);
			// know we know dir and type needed
			Vector tmp = new Vector();
			for (int p = 0; p < parentitemsVct.size(); p++) {
				EntityItem pitem = (EntityItem) parentitemsVct.elementAt(p);
				//ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem.getItems: loop pitem " + pitem.getKey() + NEWLINE);
				Vector linkVct = null;
				if (dir.equals("D")) {
					linkVct = pitem.getDownLink();
				} else {
					linkVct = pitem.getUpLink();
				}
				for (int i = 0; i < linkVct.size(); i++) {
					EntityItem entity = (EntityItem) linkVct.elementAt(i);
					//ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem.getItems: linkloop entity " + entity.getKey() + NEWLINE);
					if (entity.getEntityType().equals(destination)) {
						if (st1.hasMoreTokens()) {
							//keep looking
							tmp.add(entity);
						} else {
							//find diffitem in table
							overrideVct.add(entity);
							//ABRUtil.append(debugSb,"XMLSVCMODCVMSPECElem.getItems: find entity key=" + entity.getKey() + NEWLINE);
						}
					}
				}// end linkloop
			}// end parentloop
			parentitemsVct.clear();// remove all
			parentitemsVct = tmp;
		}

		parentitemsVct.clear();
		return overrideVct;
	}
		
}
