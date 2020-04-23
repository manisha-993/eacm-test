// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.diff.*;
import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
* Base Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a particular entity.
*
*/
// $Log: XMLFEATQTYElem.java,v $
// Revision 1.4  2015/01/26 15:53:40  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.3  2010/08/10 17:58:11  rick
// adding override for haschanges.
//
// Revision 1.2  2010/07/19 14:45:55  rick
// changes to support FEATURE and SWFEATURE in
// FEATURELIST for LSEO XML.
//
// Revision 1.1  2010/06/22 16:11:46  rick
// new util method to get CONFQTY attr from a
// WWSEOPRODSTRUCT or WWSEOSWPROSTRUCT or LSEOPRODSTRUCT or LSEOSWPRODSTUCT for a
// FEATURE.
//
//
public class XMLFEATQTYElem extends XMLElem
{
    /**********************************************************************************
    * Constructor - used when element does not have text nodes and is not root
    *
    *@param nname String with name of node to be created
    */
    public XMLFEATQTYElem(String nname, String attcode)
    {
        super(nname, attcode);
        
    }

    /**********************************************************************************
    * We want to bob down from the FEATURE or SWFEATURE to the PRODSTRUCT or SWPRODSTRUCT, and bop up to the 
    * WWSEOPRODSTRUCT or WWSEOSWPRODSTRUCT or LSEOPRODSTRUCT or LSEOSWPRODSTRUCT. 
    * We get CONFQTY or SWCONFQTY from this entity. If there isn't one
    * defined then the default is 1.
    * 
    *
    *@param document Document
    *@param item EntityItem
    *@param parent Element
    */
    protected Node getContentNode(Document document, EntityItem item, Element parent,
        StringBuffer debugSb)
    throws IOException
    {
    	ABRUtil.append(debugSb,"XMLFEATQTYElem.getContentNode entered for: "+item.getKey()+NEWLINE);
		return document.createTextNode(getQTYValue(item,debugSb));
    }

    /**********************************************************************************
    * Check to see if there are any changes in this node or in the children
    *
    *@param table Hashtable
    *@param diffitem DiffEntity
    *@param debugSb StringBuffer
    * @return
    */
    protected boolean hasChanges(Hashtable table, DiffEntity diffitem, StringBuffer debugSb)
    {
		boolean changed=false;
		ABRUtil.append(debugSb,"XMLFEATQTYElem.hasChanges entered for: "+diffitem.getKey()+NEWLINE);
		
		// check at both times if one existed or not
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
				
		String currVal = "";
		String prevVal = "";
		if (curritem != null){
			ABRUtil.append(debugSb,"XMLFEATQTYElem.hasChanges getting currVal"+NEWLINE);
		    currVal = getQTYValue(curritem, debugSb);
		}
				
		if (previtem != null){
			ABRUtil.append(debugSb,"XMLFEATQTYElem.hasChanges getting prevVal"+NEWLINE);
            prevVal = getQTYValue(previtem, debugSb);
		}
				
		ABRUtil.append(debugSb,"XMLFEATQTYElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+
		" attr CONFQTY or SWCONFQTY \n currVal: "+currVal+"\n prevVal: "+prevVal+NEWLINE);

		if (!currVal.equals(prevVal)){ // we only care if there was a change
		   changed = true;
		}
			
	    return changed;
    }

    /**********************************************************************************
    * We want to bob down from the FEATURE or SWFEATURE to the PRODSTRUCT or SWPRODSTRUCT, and bop up to the 
    * WWSEOPRODSTRUCT or WWSEOSWPRODSTRUCT or LSEOPRODSTRUCT or LSEOSWPRODSTRUCT. 
    * We get CONFQTY or SWCONFQTY from this entity. If there isn't one
    * defined then the default is 1.
    * 
    *
    *@param document Document
    *@param item EntityItem
    *@param parent Element
    */
    protected String getQTYValue(EntityItem item, StringBuffer debugSb)
    {
    	ABRUtil.append(debugSb,"Entering XMLFEATQTYElem.getQTYValue" + NEWLINE);
        
    	EntityItem prodstruct_entity = null;
        EntityItem uplink_item = null;
        
        String value = "1";
        if (item.hasDownLinks()){
		for (int i=0; i<item.getDownLinkCount(); i++){
			prodstruct_entity = (EntityItem)item.getDownLink(i);
			if (prodstruct_entity.getEntityType().equals("PRODSTRUCT") ||
                            prodstruct_entity.getEntityType().equals("SWPRODSTRUCT")){
				ABRUtil.append(debugSb,"XMLFEATQTYElem.getQTYValue found PRODSTRUCT or SWPRODSTRUCT "+prodstruct_entity.getKey()+NEWLINE);
				if (prodstruct_entity.hasUpLinks()) {
					for (int j=0; j<prodstruct_entity.getUpLinkCount(); j++) {
					uplink_item = (EntityItem)prodstruct_entity.getUpLink(j);
					if (uplink_item.getEntityType().equals("LSEOPRODSTRUCT") ||
                        uplink_item.getEntityType().equals("LSEOSWPRODSTRUCT") ||                                            
					    uplink_item.getEntityType().equals("WWSEOPRODSTRUCT") ||
                        uplink_item.getEntityType().equals("WWSEOSWPRODSTRUCT")) {
					    ABRUtil.append(debugSb,"XMLFEATQTYElem.getQTYValue found PRODSTRUCT or SWPRODSTRUCT uplink "+uplink_item.getKey()+NEWLINE);
                        if (prodstruct_entity.getEntityType().equals("PRODSTRUCT")) { 
					        value = PokUtils.getAttributeValue(uplink_item, attrCode, ", ","1", false);
                        }
                        else {
                              value = PokUtils.getAttributeValue(uplink_item, "SWCONFQTY", ", ","1", false);
                             }
					    break;						
					} // end if 
					} // end for 
				} // end if 
			break;
			} // end if  
		} // end for 
        } // end if 
        return value;
    }

        
}
