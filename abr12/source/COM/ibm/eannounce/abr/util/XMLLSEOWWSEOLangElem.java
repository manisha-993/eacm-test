// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.diff.*;
import COM.ibm.opicmpdh.transactions.*;

/**********************************************************************************
* Base Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a particular entity.
*
*/
// $Log: XMLLSEOWWSEOLangElem.java,v $
// Revision 1.2  2015/01/26 15:53:40  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.1  2010/08/12 05:06:30  rick
// new class to get attribute codes from WWSEO for a
// languagelist with attributecodes from LSEO and WWSEO.
//
//
public class XMLLSEOWWSEOLangElem extends XMLElem
{
    /**********************************************************************************
    * Constructor - used when element does not have text nodes and is not root
    *
    *@param nname String with name of node to be created
    */
    public XMLLSEOWWSEOLangElem(String nname, String attcode)
    {
        super(nname, attcode);
        
    }

    /**********************************************************************************
    * Check to see if this node has a changed value for this NLS, must be a Text attribute
    *
    *@param diffitem DiffEntity
    *@param debugSb StringBuffer
    */
    protected boolean hasNodeValueChgForNLS(DiffEntity diffitem, StringBuffer debugSb)
    {
		boolean hasValue=false;
		
			// check at both times if one existed or not
			EntityItem curritem = diffitem.getCurrentEntityItem();
			EntityItem previtem = diffitem.getPriorEntityItem();
			NLSItem nlsitem = null;
			if (!diffitem.isDeleted()){
				nlsitem = curritem.getProfile().getReadLanguage();
			}else{
				nlsitem = previtem.getProfile().getReadLanguage();
			}

				// avoid using fallback to nlsid==1 for text attributes
				// this node may only want a value for a specific nlsid
				String currVal = "";
				String prevVal = "";
				if (curritem != null){
                                   currVal = getValue(curritem, debugSb, nlsitem); 
				}
				if (previtem != null){
                                    prevVal = getValue(previtem, debugSb, nlsitem); 
				}
				ABRUtil.append(debugSb,"XMLLSEOWWSEOLangElem.hasNodeValueChgForNLS node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage "+nlsitem+
					" attr "+attrCode+"\n currVal: "+currVal+"\n prevVal: "+prevVal+NEWLINE);

				if (!currVal.equals(prevVal)){ // we only care if there was a change
					hasValue = true;
					
				}

			
		
        return hasValue;
    }
    /**********************************************************************************
    * We want to bob up from the LSEO to the WWSEO and get the value from there.  
    *
    *@param document Document
    *@param item EntityItem
    *@param parent Element
    */
    protected String getValue(EntityItem item, StringBuffer debugSb, NLSItem nlsitem)
    {
    	ABRUtil.append(debugSb,"Entering XMLLSEOWWSEOLangElem.getValue" + NEWLINE);
        
    	EntityItem WWSEOLSEO_entity = null;
        EntityItem uplink_item = null;
        
        String value = "";
        if (item.hasUpLinks()){
		for (int i=0; i<item.getUpLinkCount(); i++){
			WWSEOLSEO_entity = (EntityItem)item.getUpLink(i);
			if (WWSEOLSEO_entity.getEntityType().equals("WWSEOLSEO")){
				ABRUtil.append(debugSb,"XMLLSEOWWSEOLangElem.getValue found WWSEOLSEO "+WWSEOLSEO_entity.getKey()+NEWLINE);
				if (WWSEOLSEO_entity.hasUpLinks()) {
					for (int j=0; j<WWSEOLSEO_entity.getUpLinkCount(); j++) {
					uplink_item = (EntityItem)WWSEOLSEO_entity.getUpLink(j);
					if (uplink_item.getEntityType().equals("WWSEO")) {
					    ABRUtil.append(debugSb,"XMLLSEOWWSEOLangElem.getValue found WWSEO "+uplink_item.getKey()+NEWLINE);
                        
                    EANAttribute att = uplink_item.getAttribute(attrCode);
					if (att instanceof EANTextAttribute){
						int nlsid = nlsitem.getNLSID();
						//true if information for the given NLSID is contained in the Text data
						if (((EANTextAttribute)att).containsNLS(nlsid)) {
							value = att.toString();
						} // end attr has this language
					}

					    break;						
					} // end if WWSEO 
					} // end for 
				} // end if 
			break;
			} // end if  
		} // end for 
        } // end if 
        return value;
    }
        
}
