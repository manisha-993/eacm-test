// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;
import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
* Base Class used to hold info and structure to be generated for the xml feed
* for abrs.  This acts on a particular entity.
*
*/
// $Log: XMLRelatorElem.java,v $
// Revision 1.4  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.3  2010/08/10 09:59:27  yang
// get Feature and Model through the Path.
//
// Revision 1.2  2008/05/27 14:11:45  wendy
// Clean up RSA warnings
//
// Revision 1.1  2008/05/07 19:26:48  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public class XMLRelatorElem extends XMLElem
{
    private String destinationType;

    /**********************************************************************************
    * Constructor - used when element does not have text nodes and is not root
    *
    *@param nname String with name of node to be created
    */
    public XMLRelatorElem(String nname, String attcode, String dest)
    {
        super(nname, attcode);
        destinationType = dest;
    }

    /**********************************************************************************
    * Get the content node for this attribute(s), if this is a F (multiflag) then
    * create one parent and node for each value
    *
    *@param document Document
    *@param item EntityItem
    *@param parent Element
    */
    protected Node getContentNode(Document document, EntityItem item, Element parent,
        StringBuffer debugSb)
    throws IOException
    {
        if (attrCode==null || item==null){
            return null;
        }

        if (attrCode.equals("ENTITY1ID")){
            // use value from entity1 id
            String value = CHEAT;
            if (item.hasUpLinks()){
				for (int i=0; i<item.getUpLinkCount(); i++){
					EntityItem entity = (EntityItem)item.getUpLink(i);
					if (entity.getEntityType().equals(destinationType)){
						value = ""+entity.getEntityID();
						ABRUtil.append(debugSb,"XMLRelatorElem getting "+attrCode+" from "+entity.getKey()+NEWLINE);
						break;
					}
				}
            }
            return document.createTextNode(value);
        }
        if (attrCode.equals("ENTITY1TYPE")){
            // use value from entity1 id
            String value = CHEAT;
            if (item.hasUpLinks()){
				for (int i=0; i<item.getUpLinkCount(); i++){
					EntityItem entity = (EntityItem)item.getUpLink(i);
					if (entity.getEntityType().equals(destinationType)){
						value = ""+entity.getEntityType();
						ABRUtil.append(debugSb,"XMLRelatorElem getting "+attrCode+" from "+entity.getKey()+NEWLINE);
						break;
					}
				}
            }
            return document.createTextNode(value);
        }
        if (attrCode.equals("ENTITY2ID")){
            // use value from entity2 id
            String value = CHEAT;
            if (item.hasDownLinks()){
				for (int i=0; i<item.getDownLinkCount(); i++){
					EntityItem entity = (EntityItem)item.getDownLink(i);
					if (entity.getEntityType().equals(destinationType)){
						value = ""+entity.getEntityID();
						ABRUtil.append(debugSb,"XMLRelatorElem getting "+attrCode+" from "+entity.getKey()+NEWLINE);
						break;
					}
				}
            }
            return document.createTextNode(value);
        }
        if (attrCode.equals("ENTITY2TYPE")){
            // use value from entity2 id
            String value = CHEAT;
            if (item.hasDownLinks()){
				for (int i=0; i<item.getDownLinkCount(); i++){
					EntityItem entity = (EntityItem)item.getDownLink(i);
					if (entity.getEntityType().equals(destinationType)){
						value = ""+entity.getEntityType();
						ABRUtil.append(debugSb,"XMLRelatorElem getting "+attrCode+" from "+entity.getKey()+NEWLINE);
						break;
					}
				}
            }
            return document.createTextNode(value);
        }

        return null;
    }
}
