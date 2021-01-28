// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for abrs.  search must be used to find values for these nodes
*/
// $Log: XMLSLEORGNPLNTCODESearchElem.java,v $
// Revision 1.5  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.4  2013/08/26 12:33:21  wangyulo
// New RTC Workitem 999513 - change MODEL_UPDATE and SEO_UPDATE to use new MODCATG value Servicepac when deriving Sales Org and Plant Code
//
// Revision 1.3  2012/08/02 13:47:42  wangyulo
// fix the defect 770704- BH Defect BHALM109267 - correction to SWPRODSTRUCT for old data
//
// Revision 1.2  2011/03/30 07:46:07  guobin
// add the world wide avail of SLEORGGRP/SLEORGNPLNTCD
//
// Revision 1.1  2011/03/24 16:10:22  guobin
// search for the SLEORGNPLNTCODE entities when there is no avail
//
// Revision 1.1  2008/04/17 19:37:53  wendy
// Init for
//  Search for the MODEL
// 	1.	If there isnâ€™t an AVAIL where AVAILTYPE="Planned Availability" (146) and ANNDATE < â€œ20100301â€� then 
//		retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.MODCATG equals 
//	a.	MODEL.COFCAT if MODEL.COFCAT = â€œHardwareâ€� or â€œSoftwareâ€�
//	b.	â€œHardwareâ€� if MODEL.COFCAT = â€œServiceâ€�
//	Else retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.SLEORGGRP = a value in AVAIL.SLEORGGRP and SLEORGNPLNTCODE.MODCATG equals 
//	a.	MODEL.COFCAT if MODEL.COFCAT = â€œHardwareâ€� or â€œSoftwareâ€�
//	b.	â€œHardwareâ€� if MODEL.COFCAT = â€œServiceâ€�
//  
//  Search for the PRODSTRUCT and SWPRODSTRUCT
//  retrieve all SLEORGNPLNTCODE entities where SLEORGNPLNTCODE.MODCATG = â€œHardwareâ€� for PRODSTRUCT or â€œSoftwareâ€� for SWPRODSTRUCT
//

public class XMLSLEORGNPLNTCODESearchElem extends XMLElem
{
    
    private String SLEORGNPLNTCODE_SEARCH_ACTION="SRDSLEORG";
    private String SLEORGNPLNTCODE_ENTITY= "SLEORGNPLNTCODE";
    private String SLEORGNPLNTCODE_SEARCH_ATTRIBURE="MODCATG";    
    private EntityItem[] SLEORGNPLNTCODEItem = null;
	/**********************************************************************************
    * Constructor for search value elements
    *
    *@param downnname String with name of first node to be created- for model
    *@param upnname String with name of second node to be created- for fc
    *@param srchAct String with search action name
    *@param stype String with entitytype to find
    */
    public XMLSLEORGNPLNTCODESearchElem()
    {
    	super(null);
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
                        	 * Deriving logic in the case where there isnâ€™t an AVAIL where AVAILTYPE="Planned Availability" (146) 
                        	 * and ANNDATE < â€œ20100301â€� is not designed.
                             * If MODEL.COFCAT = â€œServiceâ€�, match to SLEORGNPLNTCODE.MODCATG with value â€œHardwareâ€� 
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
			} else if ("PRODSTRUCT".equals(roottype)) {
				COFCAT = "Hardware";
			} else if ("SWPRODSTRUCT".equals(roottype)) {
				COFCAT = "Software";
			}
		}
		return COFCAT;
	}
 	
    /**********************************************************************************
     * search for entity to get entityid for xml output
     *
     *@param dbCurrent Database
     *@param item EntityItem
     *@param debugSb StringBuffer for debug output
     */
     protected Hashtable doSearch(Database dbCurrent, EntityItem item,StringBuffer debugSb)
     throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
     COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
     COM.ibm.eannounce.objects.SBRException
     {
 		String COFCAT = getCOFCAT(item);
 		ABRUtil.append(debugSb,"XMLSLEORGNPLNTCODESearchElem.doSearch COFCAT" + COFCAT + NEWLINE);   	 
    	Vector attrVct = new Vector();
    	attrVct.add(SLEORGNPLNTCODE_SEARCH_ATTRIBURE);
    	Vector valVct = new Vector();
    	valVct.add(COFCAT);
	   	ABRUtil.append(debugSb,"XMLSLEORGNPLNTCODESearchElem.doSearch searchAction="+SLEORGNPLNTCODE_SEARCH_ACTION + NEWLINE);
	   	ABRUtil.append(debugSb,"XMLSLEORGNPLNTCODESearchElem.doSearch srchType="+SLEORGNPLNTCODE_ENTITY + NEWLINE);
	   	ABRUtil.append(debugSb,"XMLSLEORGNPLNTCODESearchElem.doSearch attribute="+SLEORGNPLNTCODE_SEARCH_ATTRIBURE + NEWLINE);
	   	ABRUtil.append(debugSb,"XMLSLEORGNPLNTCODESearchElem.doSearch value=" + COFCAT + NEWLINE);
    	SLEORGNPLNTCODEItem = ABRUtil.doSearch(dbCurrent, item.getProfile(), SLEORGNPLNTCODE_SEARCH_ACTION,SLEORGNPLNTCODE_ENTITY, false, attrVct, valVct, debugSb);
    	
    	Hashtable xmltable = new Hashtable();
    	if(SLEORGNPLNTCODEItem!=null){
			for(int i=0;i<SLEORGNPLNTCODEItem.length;i++){
				EntityItem  SLEORGNPLNTCODE = SLEORGNPLNTCODEItem[i];				
				
				int entityid = SLEORGNPLNTCODE.getEntityID();
				String entitytype = SLEORGNPLNTCODE.getEntityType();
				String country = getCounryList(dbCurrent,item,entityid,entitytype);				
				String SLEORGGRP = PokUtils.getAttributeValue(SLEORGNPLNTCODE, "SLEORGGRP",";", "", false);				
				Object sop = (Object)xmltable.get(country);
				if(sop==null){
					Vector vct[] = new Vector[2];
					vct[0] = new Vector();
					vct[1] = new Vector();
					Hashtable SLEORGGRP_table = new Hashtable();
					StringTokenizer str = new StringTokenizer(SLEORGGRP,";");
					while (str.hasMoreTokens()) {
						String dir = str.nextToken();
						SLEORGGRP_table.put(dir, "");
					}				
					vct[0].addElement(SLEORGGRP_table);
					vct[1].add(SLEORGNPLNTCODE);
					xmltable.put(country, vct);
				}else{
					Vector vct[] = (Vector[])xmltable.get(country);
					Hashtable SLEORGGRP_table = (Hashtable)vct[0].get(0);
					StringTokenizer str = new StringTokenizer(SLEORGGRP,",");
					while (str.hasMoreTokens()) {
						String dir = str.nextToken();
						SLEORGGRP_table.put(dir, "");
					}					
					vct[0].addElement(SLEORGGRP_table);
					vct[1].add(SLEORGNPLNTCODE);
					xmltable.put(country, vct);				
				}				
			}
		}    	
		return xmltable; 
 	}
     /**
      * 
      * @param dbCurrent
      * @param item
      * @param entityid
      * @param entitytype
      * @return
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
      */
     private String getCounryList(Database dbCurrent, EntityItem item, int entityid, String entitytype) throws MiddlewareRequestException, SQLException, MiddlewareException {
    	 String sReturn ="";
    	 EntityList m_elist = dbCurrent.getEntityList(item.getProfile(),
                 new ExtractActionItem(null, dbCurrent, item.getProfile(),"dummy"),
                 new EntityItem[] { new EntityItem(null, item.getProfile(), entitytype, entityid) });
    	 EntityItem rootEntity  = m_elist.getParentEntityGroup().getEntityItem(0);
    	 
    	 sReturn = PokUtils.getAttributeFlagValue(rootEntity, "COUNTRYLIST");
    	 if(sReturn==null) sReturn=CHEAT;    	 
    	 return sReturn;
	} 
}
