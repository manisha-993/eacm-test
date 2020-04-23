package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;
import java.util.*;

import org.w3c.dom.*;
/**********************************************************************************
 * Final – Defect BH 194763 <BUNDLETYPE> derivation
 * For XML LSEOBUNDLE_UPDATE, added logic to 
 * determine <BUNDLETYPE> a single value based on LSEOBUNDLE BUNDLETYPE.
 * 
 * 	If BUNDLETYPE= ‘Hardware’ exists, 
 *    	Then use “Hardware”
 * 	Else if BUNDLETYPE=’Software’ exists,
 *   	Then use “Software”
 * 	Else 
 * 		use “Service”
 * 	END IF
 * 
 */
//$Log: XMLBundleTypeElem.java,v $
//Revision 1.1  2013/05/14 12:25:38  wangyulo
//WI 945853 - Change mapping for BUNDLETYPE in LSEOBUNDLE_UPDATE XML
//

//Init for
//-   WI 945853 - Change mapping for BUNDLETYPE in LSEOBUNDLE_UPDATE XML
//-   in BH FS ABR XML System Feed Mapping 20130508.doc
//
public class XMLBundleTypeElem extends XMLElem {

	String BUNDLETYPE=CHEAT;
	
	String Hardware = "Hardware";
	String Software = "Software";
	String Service = "Service";

	private String getBUNDLETYPE() {
		return BUNDLETYPE;
	}
	private void setBUNDLETYPE(String _BUNDLETYPE)
	{
		BUNDLETYPE = _BUNDLETYPE;
	}

	public XMLBundleTypeElem() {
		super(null);
	}

	/***************************************************************************
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
	 * @throws
	 */
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent, DiffEntity parentItem,
		StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException, java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		java.rmi.RemoteException, java.io.IOException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {		
		String entitytype = parentItem.getEntityType();
		
		EntityItem item = parentItem.getCurrentEntityItem();		
		if("LSEOBUNDLE".equals(entitytype)){
			setBundleType(item, debugSb);			
		}	
		createNodeSet(document, parent, debugSb);
	}
		
	
	/**
	 * set BundleType attribute of LSEOBUNDLE
	 * @param item
	 * @param debugSb
	 */
	private void setBundleType(EntityItem item , StringBuffer debugSb) {
		String sBundleType;
		/**
		 * LSEOBUNDLE.BUNDLETYPE
		 *
		 * 	If BUNDLETYPE= ‘Hardware’ exists, 
		 *    	Then use “Hardware”
		 * 	Else if BUNDLETYPE=’Software’ exists,
		 *   	Then use “Software”
		 * 	Else 
		 * 		use “Service”
		 * 	END IF
		 */

		String BUNDLETYPES = PokUtils.getAttributeValue(item, "BUNDLETYPE", ",", CHEAT, false);;
		if(BUNDLETYPES.indexOf(Hardware)>-1){
			sBundleType = Hardware;
		}else if(BUNDLETYPES.indexOf(Software)>-1){
			sBundleType = Software;
		}else{
			sBundleType = Service;
		}
		setBUNDLETYPE(sBundleType);
		
		
	}
	
    /**
     * create nodeset of ZCONF
     * @param document
     * @param parent
     * @param debugSb
     */
	private void createNodeSet(Document document, Element parent, StringBuffer debugSb) {
		Element child = (Element) document.createElement("BUNDLETYPE"); // create COUNTRYAUDIENCEELEMENT
		child.appendChild(document.createTextNode("" + getBUNDLETYPE()));
		parent.appendChild(child);
	}

	
}
