// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs. This class will generate one child for each nlsitem in the profile
* if a value exists in that nls
* 1.	NLSID
All data is passed as US English (NLSID = 1) unless it is part of a complex element where NLSID is
the DB Key for that element.

<LANGUAGEELEMENT>
This is a complex element within <LANGUAGELIST> that has zero or more (0..N) instances.

An instance based on <NLSID> is passed if any element within the instance has changed.

*/
// $Log: XMLNLSElem.java,v $
// Revision 1.4  2020/02/05 13:29:08  xujianbo
// Add debug info to investigate   performance issue
//
// Revision 1.3  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.2  2010/08/10 09:41:38  yang
// new add method hasChanges(DiffEntity diffitem, StringBuffer debugSb)
//
// Revision 1.1  2008/04/17 19:37:53  wendy
// Init for
// -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
// -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
// -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
// -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//

public class XMLNLSElem extends XMLElem
{
    /**********************************************************************************
    * Constructor for nls sensitive elements
    *
    * 1		<LANGUAGELIST>
    * 0..N	<LANGUAGEELEMENT> => will only be created if a value exists in that nls
    * 1		<NLSID>	</NLSID>
    *
    *
    *@param nname String with name of node to be created
    */
    public XMLNLSElem(String nname)
    {
        super(nname);
    }

    /**********************************************************************************
    * Create a node for this element for each nlsid and add to the parent and any children this node has
    *
    *@param dbCurrent Database
    *@param list EntityList
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
        EntityItem parentItem, StringBuffer debugSb)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    java.rmi.RemoteException,
    IOException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
        Profile profile = list.getProfile();
        // always do nlsid=1, english even if no values exist for these nodes fixme this isnt true anymore more work needed if this is used
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
        ABRUtil.append(debugSb,"XMLNLSElem.addElements node:"+nodeName+" "+parentItem.getKey()+" ReadLanguage() "+profile.getReadLanguage()+NEWLINE);
        // create this node and its children for each nlsid
        super.addElements(dbCurrent,list, document, parent,parentItem, debugSb);

        // this is NLS sensitive, do each one
        for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
            NLSItem nlsitem = profile.getReadLanguage(ix);
            if (nlsitem.getNLSID()==1){  // already did this one
                //ABRUtil.append(debugSb,"XMLNLSElem.addElements already handled profile.getReadLanguage("+ix+") "+nlsitem+NEWLINE);
                continue;
            }
            ABRUtil.append(debugSb,"XMLNLSElem.addElements node:"+nodeName+" "+parentItem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+NEWLINE);
            profile.setReadLanguage(ix);
            // check to see if this nlsid has any values before adding the nodeset
            if (hasNodeValueForNLS(parentItem,debugSb)){
                // create this node and its children for each nlsid
                super.addElements(dbCurrent,list, document, parent,parentItem,debugSb);
            }else{
                ABRUtil.append(debugSb,"XMLNLSElem.addElements node:"+nodeName+" "+parentItem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+" does not have any node values"+NEWLINE);
            }
        } // end each read language
        // restore to nlsid=1
        profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
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
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		D.ebug(D.EBUG_ERR,"Working on the item:"+nodeName);
		Profile currprofile = null;
		Profile prevprofile = null;
		EntityItem curritem = parentItem.getCurrentEntityItem();
		EntityItem previtem = parentItem.getPriorEntityItem();
		if (curritem != null){
			currprofile = curritem.getProfile();
		}
		if (previtem != null){
			prevprofile = previtem.getProfile();
		}

        Profile profile = (currprofile==null?prevprofile:currprofile); // both will have the same languages, use one

        // this is NLS sensitive, do each one
        for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
            NLSItem nlsitem = profile.getReadLanguage(ix);

            ABRUtil.append(debugSb,"XMLNLSElem.addElements node:"+nodeName+" "+parentItem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+NEWLINE);
            if (currprofile != null){
            	currprofile.setReadLanguage(ix);
			}
            if (prevprofile != null){
            	prevprofile.setReadLanguage(ix);
			}

			// check to see if this nlsid has any values before adding the nodeset
			// if one node is added, all must be added
            if (hasNodeValueChgForNLS(parentItem,debugSb)){
                // create this node and its children for each nlsid
				Element elem = (Element) document.createElement(nodeName);
				addXMLAttrs(elem);
				if (parent ==null){ // create the root
					document.appendChild(elem);
				}else{ // create a node
					parent.appendChild(elem);
				}

				Node contentElem = getContentNode(document, parentItem, parent, debugSb);
				if (contentElem!=null){
					elem.appendChild(contentElem);
				}

				// all must be output if the parent element is output
				for (int c=0; c<childVct.size(); c++){
					XMLElem childElem = (XMLElem)childVct.elementAt(c);
					childElem.addElements(dbCurrent,table, document,elem,parentItem,debugSb);
				}
            }else{
                ABRUtil.append(debugSb,"XMLNLSElem.addElements node:"+nodeName+" "+parentItem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+" does not have any node value chgs"+NEWLINE);
            }
        } // end each read language
        // restore to nlsid=1
		if (currprofile != null){
			currprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
		}
		if (prevprofile != null){
			prevprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
		}
	}

    /**********************************************************************************
    * Check to see if there are any changes in this node or in the children for each NLS
    * must check the attributes because they may not exist at all -> so a deleted or new entity
    * would not mean a change happened.
    *@param diffitem DiffEntity
    *@param debugSb StringBuffer
    *
    */
    protected boolean hasChanges(Hashtable table, DiffEntity diffitem, StringBuffer debugSb)
    {
		boolean changed=false;
		Profile currprofile = null;
		Profile prevprofile = null;
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
		if (curritem != null){
			currprofile = curritem.getProfile();
		}
		if (previtem != null){
			prevprofile = previtem.getProfile();
		}

        Profile profile = (currprofile==null?prevprofile:currprofile); // both will have the same languages, use one

        // this is NLS sensitive, do each one
        for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
            NLSItem nlsitem = profile.getReadLanguage(ix);

            ABRUtil.append(debugSb,"XMLNLSElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+NEWLINE);
            if (currprofile != null){
            	currprofile.setReadLanguage(ix);
			}
            if (prevprofile != null){
            	prevprofile.setReadLanguage(ix);
			}

			// check to see if this nlsid has any changes
            if (hasNodeValueChgForNLS(diffitem,debugSb)){
				changed = true; // one change one is enough
				break;
            }else{
                ABRUtil.append(debugSb,"XMLNLSElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+" does not have any changed node values"+NEWLINE);
            }
        } // end each read language
        // restore to nlsid=1
		if (currprofile != null){
			currprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
		}
		if (prevprofile != null){
			prevprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
		}

        return changed;
    }
    /**********************************************************************************
     * Check to see if there are any changes in this node or in the children for each NLS
     * must check the attributes because they may not exist at all -> so a deleted or new entity
     * would not mean a change happened.
     *@param diffitem DiffEntity
     *@param debugSb StringBuffer
     */
     protected boolean hasChanges(DiffEntity diffitem, StringBuffer debugSb)
     {
 		boolean changed=false;
 		Profile currprofile = null;
 		Profile prevprofile = null;
 		EntityItem curritem = diffitem.getCurrentEntityItem();
 		EntityItem previtem = diffitem.getPriorEntityItem();
 		if (curritem != null){
 			currprofile = curritem.getProfile();
 		}
 		if (previtem != null){
 			prevprofile = previtem.getProfile();
 		}

         Profile profile = (currprofile==null?prevprofile:currprofile); // both will have the same languages, use one

         // this is NLS sensitive, do each one
         for (int ix = 0; ix < profile.getReadLanguages().size(); ix++) {
             NLSItem nlsitem = profile.getReadLanguage(ix);

             ABRUtil.append(debugSb,"XMLNLSElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+NEWLINE);
             if (currprofile != null){
             	currprofile.setReadLanguage(ix);
 			}
             if (prevprofile != null){
             	prevprofile.setReadLanguage(ix);
 			}

 			// check to see if this nlsid has any changes
             if (hasNodeValueChgForNLS(diffitem,debugSb)){
 				changed = true; // one change one is enough
 				break;
             }else{
                 ABRUtil.append(debugSb,"XMLNLSElem.hasChanges node:"+nodeName+" "+diffitem.getKey()+" ReadLanguage["+ix+"] "+nlsitem+" does not have any changed node values"+NEWLINE);
             }
         } // end each read language
         // restore to nlsid=1
 		if (currprofile != null){
 			currprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
 		}
 		if (prevprofile != null){
 			prevprofile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
 		}

         return changed;
     }
}
