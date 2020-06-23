package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.XMLActivityElem;
import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.abr.util.XMLGroupElem;
import COM.ibm.eannounce.abr.util.XMLNotificationElem;
import COM.ibm.eannounce.abr.util.XMLRELATElem;
import COM.ibm.eannounce.abr.util.XMLVMElem;

public class ADSMAINTPRODSTRUCTABR extends XMLMQRoot{

	private static final XMLElem XMLMAP;
	
	static {
		 XMLMAP = new XMLGroupElem("MAINTPRODSTRUCT_UPDATE");
		 XMLMAP.addChild(new XMLVMElem("MAINTPRODSTRUCT_UPDATE","1"));
		 //level2
		 XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
		 XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG"));
		 XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
//		 XMLElem model = new XMLGroupElem(null, "SVCMOD", "D:SVCMOD");
//		 XMLMAP.addChild(model);
//		 String modelatr = new XMLElem("MODELATR","MODELATR").toString();
//		 String mtypeatr = new XMLElem("MACHTYPEATR","SMACHTYPEATR").toString();	 
		 
		 XMLMAP.addChild(new XMLRELATElem("OFFERING_ID","SMACHTYPEATR","MODELATR"));
		 
//		 XMLElem feature = new XMLGroupElem(null, "MAINTFEATURE", "U:MAINTFEATURE");
//		 XMLMAP.addChild(feature);
		 XMLMAP.addChild(new XMLRELATElem("FEATURE_CD","FEATURECODE"));
		 
		 XMLMAP.addChild(new XMLElem("QTY_MIN_NUM","QTYMINNUM"));
		 XMLMAP.addChild(new XMLElem("QTY_MAX_NUM","QTYMAXNUM"));
		 XMLMAP.addChild(new XMLElem("FEATURE_S_NM","TMFMKTGSHRTDESC"));
		 XMLMAP.addChild(new XMLElem("FEATURE_L_NM","TMFMKTGLONGDESC"));
		 XMLMAP.addChild(new XMLElem("STATUS","STATUS"));
		 XMLMAP.addChild(new XMLElem("MAP_TO_PRICE_TYPE_CD","MAPTOPRICETYPECD"));
		 XMLMAP.addChild(new XMLElem("FEATURE_CATEG_CD","FEATURECATEGCD"));
		 XMLMAP.addChild(new XMLElem("FEATURE_MODIF_IND","FEATUREMODIFIND"));
		 XMLMAP.addChild(new XMLElem("SERVICE_LEVEL_MAPPING_CD","SERVICELEVELMAPPINGCD"));
		 
		 
	}
	/**********************************
     * get xml object mapping
     */
    public XMLElem getXMLMap() {
        return XMLMAP;
    }

    /**********************************
     * get the name of the VE to use
     */
    public String getVeName() { return "ADSMAINTPRODSTRUCT"; }

    /**********************************
     * get the status attribute to use for this ABR
     */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
     *
     A.	MQ-Series CID
     */
   
	public String getMQCID() {	return "MAINTPRODSTRUCT_UPDATE";}

    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
    public String getVersion()
    {
        return "$Revision: 1.0 $";//"1.0";
    }
	
  
	

}
