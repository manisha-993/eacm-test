//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.util;

import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;
import java.util.*;
import com.ibm.transform.oim.eacm.diff.*;

/**********************************************************************************
 *  Class used to hold info and structure to be generated for the xml feed
 * for abrs.  outputs several nodes for a multiflag attribute based on changes
 * between time1 and time2.
2.	Multi-Value Flags

<COUNTRYELEMENT>
 <COUNTRYACTION> indicates if the instance is an 'Update' or a 'Delete' of the <COUNTRY>.

 */
//$Log: XMLMultiFlagElem.java,v $
//Revision 1.3  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.2  2010/07/21 21:02:04  wendy
//correct output when the change is an add to existing data
//
//Revision 1.1  2008/04/17 19:37:53  wendy
//Init for
//-   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//-   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//-   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//-   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//-   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI



public class XMLMultiFlagElem extends XMLElem
{
	private String actionNodeName;
	/**********************************************************************************
	 * Constructor for multiflag value elements
	 *
	 * <OSELEMENT>					3
	 * <OSACTION></OSACTION>			4	MODEL	OSAction => "Delete" | "Update"
	 * <OSLEVEL>	</OSLEVEL>			4	MODEL	OSLEVEL
	 * </OSELEMENT>
	 *@param nname String with name of node to be created
	 *@param code String with attribute code
	 *@param anode String with name of action node to be created
	 */
	public XMLMultiFlagElem(String nname, String code, String anode)
	{
		super(nname,code);
		actionNodeName = anode;
	}

	/**********************************************************************************
	 * Constructor for multiflag value elements
	 *
	 *@param nname String with name of node to be created
	 *@param code String with attribute code
	 *@param anode String with name of action node to be created
	 *@param src int for flag attributes
	 */
	public XMLMultiFlagElem(String nname, String code, String anode, int src)
	{
		super(nname,code,src);
		actionNodeName = anode;
	}

	/**********************************************************************************
	 * Get the content node for this F (multiflag) attribute
	 * create one parent and 2 nodes for each value
	 *
	 *@param document Document
	 *@param diffitem DiffEntity
	 *@param parent Element
	 */
	protected Node getContentNode(Document document, DiffEntity diffitem, Element parent,
			StringBuffer debugSb)
	throws java.io.IOException
	{
		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
		EntityItem item = curritem;
		if (diffitem.isDeleted()){
			item = previtem;
			curritem = null;  // deleted root will not be null
		}

		EntityGroup egrp = item.getEntityGroup();
		Node contentElem = null;
		ABRUtil.append(debugSb,"XMLMultiFlagElem.getContentNode node: "+nodeName+" "+diffitem.getKey()+
				" attr "+attrCode+NEWLINE);
		EANMetaAttribute metaAttr = egrp.getMetaAttribute(attrCode);
		if (metaAttr==null) {
			throw new java.io.IOException("Error: Attribute "+attrCode+" not found in "+diffitem.getEntityType()+" META data.");
		}
		// meta exists for this attribute
		if(!metaAttr.getAttributeType().equals("F")){ //MultiFlagAttribute
			throw new java.io.IOException(nodeName+" "+
					attrCode+" is not a MultiFlag attribute in "+diffitem.getEntityType()+" META data");
		}

		Node grandparent = parent.getParentNode();
		HashSet currSet = new HashSet();
		HashSet prevSet = new HashSet();

		if (curritem != null){
			EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						// may need to get flagcode instead of flag value here
						if (attrSrc == FLAGVAL){
							currSet.add(mfArray[i].getFlagCode());
						}else{
							currSet.add(mfArray[i].toString());
						}
					}  // metaflag is selected
				}// end of flagcodes
			}
		}

		if (previtem != null){
			EANFlagAttribute fAtt = (EANFlagAttribute)previtem.getAttribute(attrCode);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						// may need to get flagcode instead of flag value here
						if (attrSrc == FLAGVAL){
							prevSet.add(mfArray[i].getFlagCode());
						}else{
							prevSet.add(mfArray[i].toString());
						}
					}  // metaflag is selected
				}// end of flagcodes
			}
		}

		// no changes found here
		if (prevSet.containsAll(currSet) && currSet.containsAll(prevSet)){
			currSet.clear();
			prevSet.clear();
			return null;
		}

		Hashtable chgTbl = new Hashtable();
		// get all ADDED flags
		Iterator itr = currSet.iterator();
		while(itr.hasNext()) {
			String flagInfo = (String) itr.next();
			if(!prevSet.contains(flagInfo))	{ // was added
				chgTbl.put(flagInfo, UPDATE_ACTIVITY);
			}
		}
		// get all DELETED flags
		itr = prevSet.iterator();
		while(itr.hasNext()) {
			String flagInfo = (String) itr.next();
			if(!currSet.contains(flagInfo))	{ // was deleted
				chgTbl.put(flagInfo, DELETE_ACTIVITY);
			}
		}

		// output the changes 
		itr = chgTbl.keySet().iterator();
		while(itr.hasNext()) {
			String flagInfo = (String) itr.next();
			String activity = (String)chgTbl.get(flagInfo);
			// the last one needs to be returned to the caller to add to the parent	
			if (itr.hasNext()){
				Element newParent = (Element) document.createElement(parent.getTagName());
				grandparent.insertBefore(newParent, parent);
				Element actionElem = (Element) document.createElement(actionNodeName);
				newParent.appendChild(actionElem);
				actionElem.appendChild(document.createTextNode(activity));
				Element newElem = (Element) document.createElement(nodeName);
				newParent.appendChild(newElem);
				newElem.appendChild(document.createTextNode(flagInfo));
			}else{
				// this is the last one
				Element actionElem = (Element) document.createElement(actionNodeName);
				parent.insertBefore(actionElem, parent.getLastChild());
				actionElem.appendChild(document.createTextNode(activity));
				contentElem = document.createTextNode(flagInfo);
			}
		}

		/* this did not handle the case where the only chg was an UPDATE_ACTIVITY and other values existed
			while(itr.hasNext()) {
				String flagInfo = (String) itr.next();
				if(!prevSet.contains(flagInfo))	{ // was added
					// the last one needs to be returned to the caller to add to the parent
					if (itr.hasNext() || prevSet.size()>0){
						Element newParent = (Element) document.createElement(parent.getTagName());
						grandparent.insertBefore(newParent, parent);
						Element actionElem = (Element) document.createElement(actionNodeName);
						newParent.appendChild(actionElem);
						actionElem.appendChild(document.createTextNode(UPDATE_ACTIVITY));
						Element newElem = (Element) document.createElement(nodeName);
						newParent.appendChild(newElem);
						newElem.appendChild(document.createTextNode(flagInfo));
					}else{
						// this is the last one
						Element actionElem = (Element) document.createElement(actionNodeName);
						parent.insertBefore(actionElem, parent.getLastChild());
						actionElem.appendChild(document.createTextNode(UPDATE_ACTIVITY));
						contentElem = document.createTextNode(flagInfo);
					}
				}
			}//end of while(itr.hasNext())

			itr = prevSet.iterator();
			while(itr.hasNext()) {
				String flagInfo = (String) itr.next();
				if(!currSet.contains(flagInfo))	{ // was deleted
					// the last one needs to be returned to the caller to add to the parent
					if (itr.hasNext()){
						Element newParent = (Element) document.createElement(parent.getTagName());
						grandparent.insertBefore(newParent, parent);
						Element actionElem = (Element) document.createElement(actionNodeName);
						newParent.appendChild(actionElem);
						actionElem.appendChild(document.createTextNode(DELETE_ACTIVITY));
						Element newElem = (Element) document.createElement(nodeName);
						newParent.appendChild(newElem);
						newElem.appendChild(document.createTextNode(flagInfo));
					}else{
						// this is the last one
						Element actionElem = (Element) document.createElement(actionNodeName);
						parent.insertBefore(actionElem, parent.getLastChild());
						actionElem.appendChild(document.createTextNode(DELETE_ACTIVITY));
						contentElem = document.createTextNode(flagInfo);
					}
				}
			}//end of while(itr.hasNext())
		 */

		currSet.clear();
		prevSet.clear();
		chgTbl.clear();

		return contentElem;
	}

	/**********************************************************************************
	 * Check to see if there are any changes in this node
	 * must check the attribute because it may not exist at all -> so a deleted or new entity
	 * would not mean a change happened.
	 *
	 *@param diffitem DiffEntity
	 *@param debugSb StringBuffer
	 */
	protected boolean hasChanges(DiffEntity diffitem, StringBuffer debugSb)
	{
		boolean changed=false;

		EntityItem curritem = diffitem.getCurrentEntityItem();
		EntityItem previtem = diffitem.getPriorEntityItem();
		EntityItem item = curritem;
		if (diffitem.isDeleted()){
			item = previtem;
			curritem = null;  // deleted root will not be null
		}

		EntityGroup egrp = item.getEntityGroup();
		EANMetaAttribute metaAttr = egrp.getMetaAttribute(attrCode);
		if (metaAttr==null || !metaAttr.getAttributeType().equals("F")) {
		}else{
			HashSet currSet = new HashSet();
			HashSet prevSet = new HashSet();

			if (curritem != null){
				EANFlagAttribute fAtt = (EANFlagAttribute)curritem.getAttribute(attrCode);
				if (fAtt!=null && fAtt.toString().length()>0){
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i = 0; i < mfArray.length; i++){
						// get selection
						if (mfArray[i].isSelected()){
							// may need to get flagcode instead of flag value here
							if (attrSrc == FLAGVAL){
								currSet.add(mfArray[i].getFlagCode());
							}else{
								currSet.add(mfArray[i].toString());
							}
						}  // metaflag is selected
					}// end of flagcodes
				}
			}

			if (previtem != null){
				EANFlagAttribute fAtt = (EANFlagAttribute)previtem.getAttribute(attrCode);
				if (fAtt!=null && fAtt.toString().length()>0){
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
					for (int i = 0; i < mfArray.length; i++){
						// get selection
						if (mfArray[i].isSelected()){
							// may need to get flagcode instead of flag value here
							if (attrSrc == FLAGVAL){
								prevSet.add(mfArray[i].getFlagCode());
							}else{
								prevSet.add(mfArray[i].toString());
							}
						}  // metaflag is selected
					}// end of flagcodes
				}
			}

			if (prevSet.containsAll(currSet) && currSet.containsAll(prevSet)){
				// no changes found here
			}else{
				changed = true;
			}

			currSet.clear();
			prevSet.clear();
		}

		ABRUtil.append(debugSb,"XMLMultiFlagElem.hasChanges node: "+nodeName+" "+diffitem.getKey()+
				" attr: "+attrCode+" changed: "+changed+NEWLINE);
		return changed;
	}
}
