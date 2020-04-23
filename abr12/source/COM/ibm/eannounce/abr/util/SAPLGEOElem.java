// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs. build Geography section, one per country
*
* B.	Geography Section : GEODATE
*	For each instance of GEODATE.COUNTRYLIST, generate an instance of the Geography Section List.
*
* C.	Geography Section : Avail
* 	For each instance of AVAIL.COUNTRYLIST where AVAIL.AVAILTYPE in {149, AVT220, 143,146},
* generate an instance of the Geography Section List.
*
*/
// $Log: SAPLGEOElem.java,v $
// Revision 1.2  2007/11/05 21:41:55  wendy
// fix typo
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//
public class SAPLGEOElem extends SAPLElem
{
	private String assocName;
    /**********************************************************************************
    * Constructor for nls sensitive elements
    *
    *2  <GeographyList>
    *one Geography child per geodate or avail and their countrylist
    *3  <Geography> use GEODATE.COUNTRYLIST to GENERALAREA where GENERALAREA.GENAREATYPE=2452 (Country)
    *       or use AVAIL.COUNTRYLIST to GENERALAREA where GENERALAREA.GENAREATYPE=2452 (Country)
    *4  <RFAGEO>                    GENERALAREA.RFAGEO
    *4  <Country>                   GENERALAREA.GENAREANAME
    *4  <SalesOrg>                  GENERALAREA.SLEORG
    *4  <SalesOffice>               GENERALAREA.SLEOFFC
    *4  <ProductAnnounceDateCountry>    GEODATE.PUBFROMDATE
    *4  <ProductWithdrawalDate>     GEODATE.PUBTODATE
    *3  </Geography>
    *2  </GeographyList>
    *
    *@param nname String with name of node to be created
    *@param sourceType String with entity type to get COUNTRYLIST from
    *@param assoc String with association to get to GENERALAREA
    */
    public SAPLGEOElem(String nname, String src, String assoc)
    {
        super(nname,src,null,false);
        assocName = assoc;
    }

    /**********************************************************************************
    * Get AVAIL entities to output
    * we only want those where AVAIL.AVAILTYPE
    * in {149, AVT220, 143,146}
    *@param egrp EntityGroup
    */
    public static Vector getAvailEntities(EntityGroup egrp)
    {
        Vector vct = new Vector();
		// "149" Last Order
		// "AVT220" Lease Rental Withdrawal
		// "143" First Order
		// "146" Planned Availability
		for(int i=0; i<egrp.getEntityItemCount(); i++){
			EntityItem item = egrp.getEntityItem(i);
			for(int t=0; t<AVAIL_ORDER.length; t++){
				if(PokUtils.isSelected(item, "AVAILTYPE", AVAIL_ORDER[t])){
					vct.addElement(item);
					break;
				}
			}
		}

        return vct;
    }

    /**********************************************************************************
    * Get entities to output
    * the group could be GEODATE or AVAIL, if AVAIL we only want those where AVAIL.AVAILTYPE
    * in {149, AVT220, 143,146}
    *@param egrp EntityGroup
    */
    protected Vector getEntities(EntityGroup egrp)
    {
        Vector vct = null;
        if (!egrp.getEntityType().equals("AVAIL")){  // must be GEODATE here
            vct = super.getEntities(egrp);
        }else{ // only want these AVAIL.AVAILTYPE
			// "149" Last Order
			// "AVT220" Lease Rental Withdrawal
			// "143" First Order
			// "146" Planned Availability
			vct = getAvailEntities(egrp);
        }
        return vct;
    }

    /**********************************************************************************
    * Create a node for this element for each geo and add to the parent
    * tell child which entity to use
    *
	*@param dbCurrent Database
	*@param list EntityList
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
        StringBuffer debugSb)
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
        EntityGroup egrp = list.getEntityGroup(etype);
        if (egrp==null){
            Element elem = (Element) document.createElement(nodeName);
            addXMLAttrs(elem);
            parent.appendChild(elem);
            elem.appendChild(document.createTextNode("Error: "+etype+" not found in extract!"));
            // add any children
            for (int c=0; c<childVct.size(); c++){
                SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
                childElem.addElements(dbCurrent,list, document,elem,debugSb);
            }
        }else{
            // the group could be GEODATE or AVAIL, if AVAIL we only want specific AVAILTYPEs
            Vector entVct = getEntities(egrp);
            if(etype.equals("AVAIL")){ // debug only
				for (int e=0; e<egrp.getEntityItemCount(); e++){
					EntityItem item = egrp.getEntityItem(e);
					debugSb.append("SAPLGEOElem: AVAILs "+item.getKey()+" "+
						PokUtils.getAttributeValue(item, "AVAILTYPE",", ", "", false)+NEWLINE);
				}
			}
			debugSb.append("SAPLGEOElem: entVct "+entVct+NEWLINE);

            if(entVct.size()==0){ // no entities from filtering
                if(egrp.getEntityItemCount()>0){
                    debugSb.append("SAPLGEOElem: Warning No "+etype+" entity met filter criteria. Cannot gen valid geo tags"+NEWLINE);
                    for(int u=0; u<egrp.getEntityItemCount(); u++){
                        EntityItem eitem = egrp.getEntityItem(u);
                        debugSb.append(eitem.getKey()+" did not meet filter for SAPLGEOElem: AVAILTYPE="+
                            PokUtils.getAttributeValue(eitem, "AVAILTYPE",", ", "", false)+
                        NEWLINE);
                    }
                }else{
                    debugSb.append("SAPLGEOElem: ERROR No "+etype+" entity in extract, generating empty geo tags"+NEWLINE);
                }
                // this will create an Geography element and all its children with empty values
                Element elem = (Element) document.createElement(nodeName);
                addXMLAttrs(elem);
                parent.appendChild(elem);
                // add any children
                addChildren(dbCurrent,list, document, elem, null, null, "None", debugSb);
            }else{ // just make sure assoc is in ve
            	if(list.getEntityGroup(assocName)==null){
                    debugSb.append("SAPLGEOElem: ERROR No "+assocName+" entity in extract, generating empty geo tags"+NEWLINE);
					// this will create an Geography element and all its children with empty values
					Element elem = (Element) document.createElement(nodeName);
					addXMLAttrs(elem);
					parent.appendChild(elem);
					// add any children
	                addChildren(dbCurrent,list, document, elem, null, null, "None", debugSb);
					entVct.clear(); // prevent any avails in this list from creating empty geotags below
				}
			}

            // geo output must be everything for one country at a time
            // there may be multiple avails and geodates with the same country
            Hashtable ctryTbl = groupByCtry(entVct, debugSb);
            for (Enumeration e = ctryTbl.keys(); e.hasMoreElements();)
            {
                String ctryFlag = (String)e.nextElement();
                Vector ctryItemVct = (Vector)ctryTbl.get(ctryFlag);  // this is a GEODATE or one or more AVAILs for a country

                /*
                D.	Geography Section - GENERALAREA
				Use the instance of COUNTRYLIST via an Association to GENERALAREA where
				GENERALAREA.GENAREATYPE = 2452. The use this instance of GENERALAREA for the
				EACM XML Payloads specified attributes.
                */

                // get the generalarea for each value, all items in the vct will have an assoc to this ctryflag
                EntityItem genAreaItem =
                	getGeneralArea(((EntityItem)ctryItemVct.firstElement()), assocName, ctryFlag, debugSb);

				if(genAreaItem==null){
            	    debugSb.append("SAPLGEOElem: WARNING no GENERALAREA item found for ctryflag:"+ctryFlag+NEWLINE);
				}

                // create one geography node for each country and for each set of children
                Element elem = (Element) document.createElement(nodeName);
                addXMLAttrs(elem);
                parent.appendChild(elem);
                // add any children
           	    debugSb.append("SAPLGEOElem: getting children for ctryflag:"+ctryFlag+NEWLINE);
                addChildren(dbCurrent,list, document, elem, genAreaItem, ctryItemVct, ctryFlag, debugSb);
            } // handle each country
            entVct.clear();
        } // group was in extract
    }

    /**********************************************************************************
    * Add children to this node
	*@param dbCurrent Database
	*@param list EntityList
	*@param document Document needed to create nodes
	*@param elem Element node to add this node too
	*@param genAreaItem EntityItem for generalarea, may be null
	*@param ctryItemVct Vector of entityitems for this country, may be null
	*@param ctryFlag String country flag code
	*@param debugSb StringBuffer for debug output
    */
    private void addChildren(
		Database dbCurrent,EntityList list, Document document, Element elem,
		EntityItem genAreaItem, Vector ctryItemVct, String ctryFlag, StringBuffer debugSb)
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
		for (int c=0; c<childVct.size(); c++){
			SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
			if (childElem instanceof SAPLItemElem){
				if ("GENERALAREA".equals(childElem.etype)){
					((SAPLItemElem)childElem).addElements(genAreaItem, document, elem,debugSb);
				}else
					if ("GEODATE".equals(childElem.etype)){
						EntityItem geodateItem = null;
						if (ctryItemVct !=null){
							if (ctryItemVct.size()>0){
								geodateItem = (EntityItem)ctryItemVct.firstElement();
							}
							if (ctryItemVct.size()>1){
								debugSb.append("SAPLGEOElem: Error: more than one GEODATE found for ctryflag: "+ctryFlag+
								" Using first one "+geodateItem.getKey()+NEWLINE);
							}
						}
						((SAPLItemElem)childElem).addElements(geodateItem, document, elem,debugSb);
				}else {
					// this should not happen!!!
					debugSb.append("SAPLGEOElem: Unrecognized SAPLItemElem "+childElem.etype+NEWLINE);
					childElem.addElements(dbCurrent,list, document,elem,debugSb);
				}
			/*}else if (childElem instanceof SAPLGEOFilteredElem){
				// this is needs an AVAIL of specific AVAILTYPE like for EFFECTIVEDATE
				// find it in the vector for this country
				((SAPLGEOFilteredElem)childElem).addElements(ctryItemVct, document, elem, debugSb);
			}else if (childElem instanceof SAPLGEOAvailElem){
				// this is needs AVAIL for a country
				// find it in the vector for this country
				((SAPLGEOAvailElem)childElem).addElements(ctryItemVct, document, elem, debugSb);
			} else if (childElem instanceof SAPLGEOAnnElem){
				// this is needs AVAIL for a country to get to ANNOUNCEMENT
				// find it in the vector for this country
				((SAPLGEOAnnElem)childElem).addElements(ctryItemVct, document, elem, debugSb);*/
			} else {
				// derived classes SAPLGEOFilteredElem, SAPLGEOAvailElem, SAPLGEOAnnElem override this
				// method, base class will throw an exception because this vector of country items
				// is meaningless there
				childElem.addGEOElements(ctryItemVct, document, elem, debugSb);
			}
		}// add each child
	}
    /**********************************************************************************
    * geo output must be everything for one country at a time
    * there may be multiple avails and geodates with the same country
    *@return Hashtable with ctry flag code as key, Vector as element
    */
    private Hashtable groupByCtry(Vector entVct, StringBuffer debugSb)
    {
        Hashtable ctryTbl = new Hashtable();
        for (int e=0; e<entVct.size(); e++){
            EntityItem item = (EntityItem)entVct.elementAt(e);  // this is a GEODATE or an AVAIL
            // get countrylist attr, it is F
            EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
            if (fAtt==null){
                debugSb.append(item.getKey()+" did not have a value for COUNTRYLIST"+NEWLINE);
                continue;
            }
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            for (int i = 0; i < mfArray.length; i++){
                // get selection
                if (mfArray[i].isSelected()){
                    String ctryFlag = mfArray[i].getFlagCode();
//                          String ctryDesc = mfArray[i].toString();
                    // get the Vector for this ctry
                    Vector vct = (Vector)ctryTbl.get(ctryFlag);
                    if (vct==null){
                        vct = new Vector();
                        ctryTbl.put(ctryFlag,vct);
                    }
                    vct.addElement(item);
                }  // metaflag is selected
            }// end of countrylist flagcodes
        }// look at each GEODATE or AVAIL
        return ctryTbl;
    }
    /**********************************************************************************
    * Get the GENERALAREA with GENAREATYPE = 2452. and GENAREANAME=ctryFlag assoc to this AVAIL or GEODATE
    *
    *@param item EntityItem AVAIL or GEODATE
    *@param assocName String association name
    *@param ctryFlag String country flag code
    *@param debugSb StringBuffer used for debug output
    */
	private EntityItem getGeneralArea(EntityItem item, String assocName, String ctryFlag, StringBuffer debugSb){
		EntityItem genAreaItem = null;
		Vector genareaVector = PokUtils.getAllLinkedEntities(item, assocName, "GENERALAREA");
		//debugSb.append("SAPLGEOElem:getGeneralArea: "+item.getKey()+" has "+genareaVector.size()+" GENERALAREA thru "+assocName+NEWLINE);
		// find those of GENAREATYPE = 2452.
		Vector ctryVector = PokUtils.getEntitiesWithMatchedAttr(genareaVector, "GENAREATYPE", "2452");
		//debugSb.append("SAPLGEOElem:getGeneralArea: "+item.getKey()+" has "+ctryVector.size()+" GENERALAREA.GENAREATYPE = 2452 "+NEWLINE);

		for (int ii=0; ii<ctryVector.size(); ii++){
			EntityItem gaitem = (EntityItem) ctryVector.elementAt(ii);
    		if(PokUtils.isSelected(gaitem, "GENAREANAME", ctryFlag)) {
				genAreaItem = gaitem;
				break;
			}
		}

		return genAreaItem;
	}

}
