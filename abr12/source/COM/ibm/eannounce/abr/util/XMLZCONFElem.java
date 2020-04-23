package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;
import java.util.*;

import org.w3c.dom.*;
/**********************************************************************************
 * Add default catalog overrides
 * 
 */
//$Log: XMLZCONFElem.java,v $
//Revision 1.16  2015/01/26 15:53:39  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.15  2013/11/08 04:42:25  guobin
//fix the nullpoint exception by remove the debug infor .
//
//Revision 1.14  2013/11/05 13:40:24  guobin
//RCQ00202556: BH W1 - EACM Support for strategic IPC Indication (ZCONF) (related to 197094)
//
//Revision 1.13  2012/11/15 14:32:43  wangyulo
//update for the Defect 833124 --change for MODEL ADSABRSTATUS is required to allow DEFAULTCUSTOMIZEABLE attribute
//
//Revision 1.12  2012/11/14 14:51:14  wangyulo
//Defect 833124 --change for MODEL ADSABRSTATUS is required to allow DEFAULTCUSTOMIZEABLE attribute in the XML to be set to "Y" for SW Models not on IPC List but with SPECBID='Yes'
//
//Revision 1.11  2012/03/02 08:00:33  wangyulo
//Changing IPC List for Default Catalog Flags - CQ 87997 - Defect 672249
//
//Revision 1.10  2011/01/21 13:33:13  guobin
//fix the priced or notPriced attribute of LSEO
//
//Revision 1.9  2011/01/21 12:11:41  guobin
//add debug log
//
//Revision 1.8  2011/01/21 11:45:23  guobin
//update the WWSEO.SEOORDERCODE of LSEO
//
//Revision 1.7  2011/01/21 09:07:36  guobin
//change the domainTable list
//
//Revision 1.6  2011/01/21 09:03:52  guobin
//add the log
//
//Init for
//-   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//-   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//-   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//-   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//-   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
public class XMLZCONFElem extends XMLElem {

	String DEFAULTHIDE=CHEAT;
	String DEFAULTBUYABLE=CHEAT;
	String DEFAULTADDTOCART=CHEAT;		
	String DEFAULTCUSTOMIZEABLE=CHEAT;
	
	String Hardware = "Hardware";
	String Software = "Software";
	String Service = "Service";

	private String getDEFAULTADDTOCART() {
		return DEFAULTADDTOCART;
	}

	private String getDEFAULTBUYABLE() {
		return DEFAULTBUYABLE;
	}

	private String getDEFAULTCUSTOMIZEABLE() {
		return DEFAULTCUSTOMIZEABLE;
	}
	private String getDEFAULTHIDE() {
		return DEFAULTHIDE;
	}
	private void setAllFields(String _DEFAULTHIDE,String _DEFAULTBUYABLE,String _DEFAULTADDTOCART,String _DEFAULTCUSTOMIZEABLE)
	{
		DEFAULTHIDE = _DEFAULTHIDE;             
		DEFAULTBUYABLE = _DEFAULTBUYABLE;          
		DEFAULTADDTOCART = _DEFAULTADDTOCART;		
		DEFAULTCUSTOMIZEABLE = _DEFAULTCUSTOMIZEABLE;
	}

	public XMLZCONFElem() {
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
		/** From David Starke
		 * FYI - this is the WRONG list of PDHDOMAINs for IPC List.  
		 * Here is the correct list.  Let's go ahead and hard code it 
		 * for now to speed up the fix -- 
		 * we will have a CR for handling of Default Catalog settings 
		 * and we may make IPC list configurable at that point. 
    		IPC List PDH Domains: 
    		xSeries
    		Midrange Disk
    		SANB
			Mid Range Tape
			Other Storage
			SANM
			Converged Products
			Storage Network Switches 
			--RSS
			DIV71
			RSS Software
		 *
		 * From Linda Bahner
		 * Please remove RSS from the list below.  
		 * The rest of the domains are correct. 
		 */
		
		//new added RCQ00202556 
		/*
		String domains =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" , "_IPCDomainList",
				"xSeries,Midrange Disk,SANB,Mid Range Tape,Other Storage,SANM,Converged Products,Storage Network Switches,DIV71,RSS Software");
		
		Hashtable domainTable = new Hashtable();		
		StringTokenizer st1 = new StringTokenizer(domains,",");
        while (st1.hasMoreTokens()) {        	
           domainTable.put(st1.nextToken(),"");
        }		

		Hashtable SWMATable = new Hashtable();
		//"Maintenance",  "MaintFeature", "OptionalFeature", 
		//"SubscrFeature", "Subscription", "Support", "SupportFeature"
		SWMATable.put("Maintenance", "Maintenance");
		SWMATable.put("MaintFeature", "MaintFeature");
		SWMATable.put("OptionalFeature", "OptionalFeature");
		SWMATable.put("SubscrFeature", "SubscrFeature");
		SWMATable.put("Subscription", "Subscription");
		SWMATable.put("Support", "Support");
		SWMATable.put("SupportFeature", "SupportFeature");
		*/
		
		EntityItem item = parentItem.getCurrentEntityItem();
		//MODEL	SVCMOD LSEOBUNDLE LSEO PRODSTRUCT SWPRODSTRUCT		
		if("MODEL".equals(entitytype)){
			//new added RCQ00202556
		//	setMODELZCONF(domainTable, SWMATable,item , debugSb);	
			setMODELZCONF(item , debugSb);
		}else if("SVCMOD".equals(entitytype)){
			//for the future
		//	setSVCMODZCONF(domainTable, item, debugSb);	
			setSVCMODZCONF(item, debugSb);	
		}else if("LSEOBUNDLE".equals(entitytype)){
			setLSEOBUNDLEZCONF(item, debugSb);			
		}else if("LSEO".equals(entitytype)){
			setLSEOZCONF(item, debugSb);				
		}else if("SVCSEO".equals(entitytype)){
			//for the future
			setSVCSEO(item, debugSb);		
		}	
		createNodeSet(document, parent, debugSb);
	}
	/**
	 * set ZCONF attribute of SVCSEO
	 * @param item
	 * @param debugSb
	 */
	private void setSVCSEO(EntityItem item , StringBuffer debugSb) {
//		String note2 = "";
//		Vector relatorVec = item.getUpLink();
//		EntityItem root = null;
//		for (int ii = 0; ii < relatorVec.size(); ii++) {
//			EntityItem secoRelator = (EntityItem) relatorVec.elementAt(ii);
//			if (secoRelator.hasUpLinks() && secoRelator.getEntityType().equals("SVCMODSVCSEO")) {
//				Vector annVct = secoRelator.getUpLink();
//				root = (EntityItem) annVct.elementAt(0);
//			}
//		}
	}
	/**
	 * set ZCONF attribute of LSEO
	 * @param item
	 * @param debugSb
	 */
	private void setLSEOZCONF(EntityItem item , StringBuffer debugSb) {
		String note1 = CHEAT;
		String note2 = CHEAT;
		String note3 = CHEAT;
		String note4 = CHEAT;
		
		EntityItem COFCATentity = findCOFCAT(item);	
		if(COFCATentity!=null){
			note1 = PokUtils.getAttributeValue(COFCATentity, "COFCAT", ", ", CHEAT, false);//get the MODEL of COFCAT
		}		
		//LSEO.PRCINDC = “Yes” implies “Priced”
		// "Not Priced" means PRCINDC = No, or (PRCINDC=Yes and ZEROPRICE=Yes)                                                                                    

		String PRCINDC = PokUtils.getAttributeValue(item, "PRCINDC", ", ", CHEAT, false);
		String ZEROPRICE = PokUtils.getAttributeValue(item, "ZEROPRICE", ", ", CHEAT, false);
		if("No".equalsIgnoreCase(PRCINDC)){
			note2 = "NotPriced";
		}else if("Yes".equalsIgnoreCase(PRCINDC)){
			if("Yes".equalsIgnoreCase(ZEROPRICE)){
				note2 = "NotPriced";
			}else{
				note2 = "Priced";
			}			
		}
		//note3 = Entity type + SPECBID for SEO  
		//if SPECBID=No then GASEO  else if SPECBID=Yes the Custom SEO
		String SPECBID = PokUtils.getAttributeValue(item, "SPECBID", ", ", CHEAT, false);
		//LSEOBUNDLE.SPECBID = 11458 (Yes)
		if(!"Yes".equalsIgnoreCase(SPECBID)){
			note3 = "CustomSEO";
		}else{
			note3 = "GASEO";
		}		
		//note4: WWSEO.SEOORDERCODE - Initial means System SEO, MES means Options SEO
		String SEOORDERCODE = "";
		EntityItem wwseo = null;
		Vector uprelator = item.getUpLink();
    	for (int i=0; i<uprelator.size(); i++){
    		EntityItem lseowwseo = (EntityItem)uprelator.get(i);
    		if (lseowwseo != null && "WWSEOLSEO".equals(lseowwseo.getEntityType())){
    			wwseo= (EntityItem)lseowwseo.getUpLink(0);
    		}
    	}
    	if(wwseo!=null){
    		SEOORDERCODE  = PokUtils.getAttributeValue(wwseo, "SEOORDERCODE", ", ", CHEAT, false);
    	}
    	
    	if("Initial".equalsIgnoreCase(SEOORDERCODE)){
			note4 = "SystemSEO";
		} else if("MES".equalsIgnoreCase(SEOORDERCODE)){
			note4 = "OptionsSEO";
		}
    	ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOZCONF getAttributeValue note1: " + note1  + " COFCAT: "	+ note1 + NEWLINE);
    	ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOZCONF getAttributeValue note2: " + note2  + " PRCINDC: " + PRCINDC +" ZEROPRICE:" +ZEROPRICE+ NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOZCONF getAttributeValue note3: " + note3  + " SPECBID: " + SPECBID + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOZCONF getAttributeValue note4: " + note4  + " SEOORDERCODE: " + SEOORDERCODE + NEWLINE);
		if(Hardware.equalsIgnoreCase(note1)){
			if("OptionsSEO".equalsIgnoreCase(note4)){
				//N	Y	Y	N
				setAllFields("N","Y","Y","N");
			}else if("SystemSEO".equalsIgnoreCase(note4)){
				//now SPECBID note3 is no effto to the ZCONF
				//if("GASEO".equalsIgnoreCase(note3))
				if("Priced".equalsIgnoreCase(note2)){
					//N	Y	Y	Y
					setAllFields("N","Y","Y","Y");
				}else{
					//Y	N	N	N
					setAllFields("Y","N","N","N");
				}					
			}				
		}else if(Software.equalsIgnoreCase(note1)){
			if("OptionsSEO".equalsIgnoreCase(note4)){
				//N	Y	Y	N
				setAllFields("N","Y","Y","N");
			}else {
				//now SPECBID note3 is no effto to the ZCONF
				//if("GASEO".equalsIgnoreCase(note3))
				if("Priced".equalsIgnoreCase(note2)){
					//N	Y	Y	N
					setAllFields("N","Y","Y","N");
				}else{
					//Y	N	N	N
					setAllFields("Y","N","N","N");
				}	
			}
		}else if(Service.equalsIgnoreCase(note1)){
			if("Priced".equalsIgnoreCase(note2)){
				//N	Y	Y	N
				setAllFields("N","Y","Y","N");
			}else{
				//Y	N	N	N
				setAllFields("Y","N","N","N");
			}
		}
	}
	/**
	 * find the MODEL entity of LSEO
	 * @param item
	 * @return
	 */
	private EntityItem findCOFCAT(EntityItem item) {
		EntityItem COFCATentity = null ;
		if (item != null)
        {
        	Vector uprelator = item.getUpLink();
        	TT: for (int i=0; i<uprelator.size(); i++){
        		EntityItem lseowwseo = (EntityItem)uprelator.get(i);
        		if (lseowwseo != null && "WWSEOLSEO".equalsIgnoreCase(lseowwseo.getEntityType())){
        			EntityItem wwseo= (EntityItem)lseowwseo.getUpLink(0);
        			if(wwseo != null && "WWSEO".equalsIgnoreCase(wwseo.getEntityType())){
        				Vector uprelator2 = wwseo.getUpLink();
        				for(int j=0;j<uprelator2.size();j++){
        					EntityItem modelwwseo= (EntityItem)uprelator2.get(j);
        					if (modelwwseo != null && "MODELWWSEO".equalsIgnoreCase(modelwwseo.getEntityType())){
        						COFCATentity = (EntityItem)modelwwseo.getUpLink(0);
        						break TT;
        					}        					
        				}
        			}        			 
        		}
        	}//end TT       	
        }
		return COFCATentity;
	}
	/**
	 * set ZCONF attribute of LSEOBUNDLE
	 * @param item
	 * @param debugSb
	 */
	private void setLSEOBUNDLEZCONF(EntityItem item , StringBuffer debugSb) {
		String note1;
		//String note2 = "Priced";;
		//LSEOBUNDLE.BUNDLETYPE  Yeah that looks good
		//If not specified, then “Hardware” 
		//If one of the values = “Hardware”, then “Hardware”
		//else IF one of the values = “Software”, then “Software”
		//else “Service” 
		String SPECBID = PokUtils.getAttributeValue(item, "SPECBID", ", ", CHEAT, false);
		//LSEOBUNDLE.SPECBID = 11458 (Yes)
		String BUNDLETYPE = PokUtils.getAttributeValue(item, "BUNDLETYPE", ",", CHEAT, false);;
		if("CHEAT".equals(BUNDLETYPE) || BUNDLETYPE.indexOf(Hardware)>-1){
			note1 = Hardware;
		}else if(BUNDLETYPE.indexOf(Software)>-1){
			note1 = Software;
		}else{
			note1 = Service;
		}
		
		ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOBUNDLEZCONF getAttributeValue note1: " + note1  + " BUNDLETYPE: "	+ BUNDLETYPE + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setLSEOBUNDLEZCONF getAttributeValue SPECBID: " + SPECBID + NEWLINE);
		
		if(Hardware.equalsIgnoreCase(note1)){
			//N	Y	Y	Y
			setAllFields("N","Y","Y","Y");
		}else{
			//N	Y	Y	N
			setAllFields("N","Y","Y","N");
		}
	}
	/**
	 * set ZCONF attribute of SVCMOD
	 * @param domainTable
	 * @param item
	 * @param debugSb
	 */
	
	//new added RCQ00202556 (jara)
	private void setSVCMODZCONF(EntityItem item , StringBuffer debugSb) {
		String note7;
		note7 = PokUtils.getAttributeValue(item, "OFERCONFIGTYPE", ", ", CHEAT, false);//OFERCONFIGTYPE
		ABRUtil.append(debugSb,"XMLZCONFElem.setSVCMODZCONF getAttributeValue note7: " + note7  + " OFERCONFIGTYPE: " + note7 + NEWLINE);
		setAllFields("N","Y","Y","N");
	}
		
/*	private void setSVCMODZCONF(Hashtable domainTable, EntityItem item , StringBuffer debugSb) {
	    String note5;
		String note7;
		String PDHDOMAIN = PokUtils.getAttributeValue(item, "PDHDOMAIN", ", ", CHEAT, false);
		if(domainTable.containsKey(PDHDOMAIN)){
			note5="InIPC";
		}else{
			note5="NotInIPC";
		}
		note7 = PokUtils.getAttributeValue(item, "OFERCONFIGTYPE", ", ", CHEAT, false);//OFERCONFIGTYPE
		
		ABRUtil.append(debugSb,"XMLZCONFElem.setSVCMODZCONF getAttributeValue note5: " + note5  + " PDHDOMAIN: "	+ PDHDOMAIN + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setSVCMODZCONF getAttributeValue note7: " + note7  + " OFERCONFIGTYPE: " + note7 + NEWLINE);
		
		if("Configurable".equalsIgnoreCase(note7)){
			if("InIPC".equalsIgnoreCase(note5)){
				//N	N	N	Y
				setAllFields("N","N","N","Y");
			}else{
				//N	N	N	N
				setAllFields("N","N","N","N");
			}				
		}else{
			//N	Y	Y	N
			setAllFields("N","Y","Y","N");
		}   
	}*/
	
    /**
     * set ZCONF attribute of MODEL
     * @param domainTable
     * @param SWMATable
     * @param item
     * @param debugSb
     */
	
		//new added RCQ00202556 
		private void setMODELZCONF(EntityItem item , StringBuffer debugSb) {
		String note1;
		String note5;
		
		note1 = PokUtils.getAttributeValue(item, "COFCAT", ", ", CHEAT, false);		
		
		/**
		 * Look at attribute SUPRTCONFIGURATOR 
		 * If the list of values includes “IPC” (SPTC3) then use the “IPC” row.
		 * If the list does not include “IPC” (SPTC3), then use the “Not IPC” row.
		 */
		EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute("SUPRTCONFIGURATOR");
		if (fAtt != null && fAtt.isSelected("SPTC3")) {
			note5="InIPC";
		}else{
			note5="NotInIPC";
		}
			
				
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note1: " + note1  + " COFCAT: " + note1 + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note5: " + note5  + NEWLINE);
		
		if(Hardware.equalsIgnoreCase(note1) || Software.equalsIgnoreCase(note1)){
			if("InIPC".equalsIgnoreCase(note5)){
				//N	N	N	Y
				setAllFields("N","N","N","Y");
			}else{
				//N	N	N	N
				setAllFields("N","N","N","N");
			}
		}
		else if(Service.equalsIgnoreCase(note1)){
			//Y	N	N	N
			setAllFields("Y","N","N","N");				
		}
	}
		
		
		
		/*
	private void setMODELZCONF(Hashtable domainTable, Hashtable SWMATable, EntityItem item , StringBuffer debugSb) {
		String note1;
		String note5;
		String note6;
		String note8;
		
		note1 = PokUtils.getAttributeValue(item, "COFCAT", ", ", CHEAT, false);		
		String PDHDOMAIN = PokUtils.getAttributeValue(item, "PDHDOMAIN", ", ", CHEAT, false);
		
		
		
		if(domainTable.containsKey(PDHDOMAIN)){
			note5="InIPC";
		}else{
			note5="NotInIPC";
		}
		String COFSUBCAT = PokUtils.getAttributeValue(item, "COFSUBCAT", ", ", CHEAT, false);
		if(SWMATable.containsKey(COFSUBCAT)){
			note6="SWMA";
		}else{
			note6="NotSWMA";
		}
		
		String SPECBID = PokUtils.getAttributeValue(item, "SPECBID", ", ", CHEAT, false);
		if("Yes".equalsIgnoreCase(SPECBID)){
			note8="Yes";
		}else{
			note8="No";
		}
		
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note1: " + note1  + " COFCAT: " + note1 + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note5: " + note5  + " PDHDOMAIN: "	+ PDHDOMAIN + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note6: " + note6  + " COFSUBCAT: "	+ COFSUBCAT + NEWLINE);
		ABRUtil.append(debugSb,"XMLZCONFElem.setMODELZCONF getAttributeValue note8: " + note8  + " SPECBID: "	+ SPECBID + NEWLINE);
		if(Hardware.equalsIgnoreCase(note1)){
			if("InIPC".equalsIgnoreCase(note5)){
				//N	N	N	Y
				setAllFields("N","N","N","Y");
			}else{
				//N	N	N	N
				setAllFields("N","N","N","N");
			}
		}else if(Software.equalsIgnoreCase(note1)){
			if("SWMA".equalsIgnoreCase(note6)){
				//N	N	N	N
				setAllFields("N","N","N","N");
			}else{
				if("InIPC".equalsIgnoreCase(note5)){
					//N	N	N	Y
					setAllFields("N","N","N","Y");;
				}else{
					//N	N	N	N
					//setAllFields("N","N","N","N");
					if("Yes".equalsIgnoreCase(note8)){
						setAllFields("N","N","N","Y");
					}else{
						setAllFields("N","N","N","N");
					}
					
				}
			}
		}else if(Service.equalsIgnoreCase(note1)){
			//Y	N	N	N
			setAllFields("Y","N","N","N");				
		}
	}
		*/
	
    /**
     * create nodeset of ZCONF
     * @param document
     * @param parent
     * @param debugSb
     */
	private void createNodeSet(Document document, Element parent, StringBuffer debugSb) {
		Element child = (Element) document.createElement("DEFAULTADDTOCART"); // create COUNTRYAUDIENCEELEMENT
		child.appendChild(document.createTextNode("" + getDEFAULTADDTOCART()));
		parent.appendChild(child);
		child = (Element) document.createElement("DEFAULTBUYABLE");
		child.appendChild(document.createTextNode("" + getDEFAULTBUYABLE()));
		parent.appendChild(child);
		child = (Element) document.createElement("DEFAULTCUSTOMIZEABLE");
		child.appendChild(document.createTextNode("" + getDEFAULTCUSTOMIZEABLE()));
		parent.appendChild(child);
		child = (Element) document.createElement("DEFAULTHIDE");
		child.appendChild(document.createTextNode("" + getDEFAULTHIDE()));
		parent.appendChild(child);

	}

	
}
