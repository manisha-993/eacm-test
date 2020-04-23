// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;

import com.ibm.transform.oim.eacm.diff.DiffEntity;

/*******************************************************************************
 * Class used to hold info and structure to be generated for the xml feed for
 * abrs. Checks for deleted or updated entity
 */
// $Log: XMLLSEOBUNDLEINSTALLElem.java,v $
// Revision 1.2  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.1  2011/12/14 02:29:11  guobin
// update the install attribute for the LSEOBUNDLEI
//
// - BH FS ABR Data Transformation System Feed 20110916.doc
//<INSTALL>
// If BUNDLETYPE=’Hardware’ exists, then derive INSTALL as follows:
//	  Navigate each LSEOBUNDLELSEO –d, WWSEOLSEO –u and MODELWWSEO –u to find parent MODEL.
//	  If MODEL.COFCAT = “Hardware” (100), set <INSTALL> to MODEL.INSTALL Long Description 
//    for NLSID=1 if it exists (else set to null)
// Else <INSTALL> is null 
//
//
public class XMLLSEOBUNDLEINSTALLElem extends XMLElem {
	public XMLLSEOBUNDLEINSTALLElem() {
		super(null);
	}
	/**
	 * get long flag description
	 * @param _ei
	 * @param _strAttrCode
	 * @return
	 */
	public String getAttributeLongFlagDesc(EntityItem _ei, String _strAttrCode) {
	    String strRetValue = null;
	    String strFlagValues = "";
	    EANAttribute EANAttr = _ei.getAttribute(_strAttrCode);

	    if (EANAttr == null) {
	    	D.ebug(D.EBUG_ERR,"getAttributeLongFlagDesc:entityattribute is null");
	      return null;
	    }
	    strRetValue = EANAttr.toString();
	    EntityGroup eg= _ei.getEntityGroup();

	    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(_strAttrCode);
	    switch (eanMetaAtt.getAttributeType().charAt(0)) {
	        case 'F':
	        case 'U':
	        case 'S':
	          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
	          for (int i = 0; i < mfAttr.length; i++) {
	            if (mfAttr[i].isSelected()) {
	              if (mfAttr[i].getShortDescription()!=null)  {
	                if (strFlagValues.trim().length() > 0 ) {
	                  strFlagValues += "|" + mfAttr[i].getLongDescription();
	                } else {
	                strFlagValues = mfAttr[i].getLongDescription();
	                }
	              }else {
	            	  D.ebug(D.EBUG_ERR,"getAttributeLongFlagDesc:NULL returned for "+mfAttr[i].getFlagCode());
	              }
	            }
	          }
	          strRetValue = strFlagValues;
	          break;
	        default:

	    }
	    D.ebug(D.EBUG_SPEW,"getAttributeLongFlagDesc:Attribute values are " + strFlagValues);

	    return strRetValue;

	  }

	/***************************************************************************
	 * Create a node for this element and add to the parent and any children
	 * this node has
	 * 
	 * @param dbCurrent
	 *            Database
	 * @param table
	 *            Hashtable of Vectors of DiffEntity
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param parentItem
	 *            DiffEntity - parent to use if path is specified in
	 *            XMLGroupElem, item to use otherwise
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table,
			Document document, Element parent, DiffEntity parentItem,
			StringBuffer debugSb)
			throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.rmi.RemoteException, java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		
		String strINSTALL = "";
		boolean isHardware = false;
		EntityItem item = parentItem.getCurrentEntityItem();
		if (parentItem.isDeleted()) {
			item = parentItem.getPriorEntityItem();
		}
		
//		<INSTALL>
//		 If BUNDLETYPE=’Hardware’ exists, then derive INSTALL as follows:
//			  Navigate each LSEOBUNDLELSEO –d, WWSEOLSEO –u and MODELWWSEO –u to find parent MODEL.
//			  If MODEL.COFCAT = “Hardware” (100), set <INSTALL> to MODEL.INSTALL Long Description 
//		    for NLSID=1 if it exists (else set to null)
//		 Else <INSTALL> is null 
		//1. check the BUNDLETYPE=’Hardware’
		String attrcode = "BUNDLETYPE";
		String sBUNDLETYPE = getAttributeLongFlagDesc(item, attrcode);
		ABRUtil.append(debugSb,"LSEOBUNDLE BUNDLETYPE="+ sBUNDLETYPE + NEWLINE);
		if(sBUNDLETYPE ==null){
			ABRUtil.append(debugSb,"LSEOBUNDLE is null." + NEWLINE);
			strINSTALL = "";
		}else if(sBUNDLETYPE.indexOf("Hardware")>-1){
			ABRUtil.append(debugSb,"LSEOBUNDLE is Hardware ." + NEWLINE);
			isHardware = true;
		}else{
			ABRUtil.append(debugSb,"LSEOBUNDLE is not Hardware ." + NEWLINE);
			strINSTALL = "";
		}
		
		//2. if true, find INSTALL attributevalue of MODEL 
		if(isHardware){
			strINSTALL = getModelOfINSTALL(item,debugSb);
		}else{
			strINSTALL = "";
		}
		if(strINSTALL ==null) strINSTALL ="";		
		createNodeSet(document, parent, strINSTALL, debugSb);
	}
	/**
	 * @param item
	 * LSEOBUNDLELSEO –d, WWSEOLSEO –u and MODELWWSEO –u to find parent MODEL
	 * only one hardware MODEL
	 */
	private String getModelOfINSTALL(EntityItem item,StringBuffer debugSb) {
		//1. get MODEL
		String sINSTALL="";
		Vector relatorsV1 = item.getDownLink();
		TT: for (int i=0; i<relatorsV1.size(); i++){
			EntityItem relator1 = (EntityItem)relatorsV1.get(i);
			if (relator1 != null && "LSEOBUNDLELSEO".equals(relator1.getEntityType())){
				EntityItem LSEO= (EntityItem)relator1.getDownLink(0);
				Vector relatorsV2 = LSEO.getUpLink();
				for(int j=0;j<relatorsV2.size();j++){
					EntityItem relator2= (EntityItem)relatorsV2.get(j);
					if (relator2 != null && "WWSEOLSEO".equals(relator2.getEntityType())){
						EntityItem WWSEO= (EntityItem)relator2.getUpLink(0);
						Vector relatorsV3 = WWSEO.getUpLink();
						for(int k=0;k<relatorsV3.size();k++){
							EntityItem relator3= (EntityItem)relatorsV3.get(k);
							if (relator3 != null && "MODELWWSEO".equals(relator3.getEntityType())){
								EntityItem MODEL= (EntityItem)relator3.getUpLink(0);
								String COFCAT = getAttributeLongFlagDesc(MODEL, "COFCAT");
								ABRUtil.append(debugSb,"MODEL COFCAT="+ COFCAT+ NEWLINE);
								if(COFCAT!=null){
									if(COFCAT.indexOf("Hardware")>-1){
										sINSTALL = getAttributeLongFlagDesc(MODEL, "INSTALL");
										break TT;
									}
								}								
							}							
						}						
					}        					
				}   			 
			}
		}
		//end TT
		return sINSTALL;
	}
	
	/**
	 * create nodeset of LSEOBUNDLE INSTALL
	 * 
	 * @param document
	 * @param parent
	 * @param debugSb
	 */
	private void createNodeSet(Document document, Element parent,
			String strINSTALL, StringBuffer debugSb) {
		Element child = (Element) document.createElement("INSTALL"); // create
		child.appendChild(document.createTextNode("" + strINSTALL));
		parent.appendChild(child);
	}	

}
