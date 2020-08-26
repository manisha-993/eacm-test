package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.util.XMLActivityElem;
import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.abr.util.XMLGroupElem;
import COM.ibm.eannounce.abr.util.XMLSWSTMFAVAILElem;
import COM.ibm.eannounce.abr.util.XMLStatusElem;
import COM.ibm.eannounce.abr.util.XMLNotificationElem;
import COM.ibm.eannounce.abr.util.XMLRELATElem;
import COM.ibm.eannounce.abr.util.XMLVMElem;

public class ADSSWSPRODSTRUCTABR extends XMLMQRoot{

	private static final XMLElem XMLMAP;
	
	static {
		 XMLMAP = new XMLGroupElem("SWSPRODSTRUCT_UPDATE");
		 XMLMAP.addChild(new XMLVMElem("SWSPRODSTRUCT_UPDATE","1"));
		 //level2
		 XMLMAP.addChild(new XMLElem("PDHDOMAIN","PDHDOMAIN"));
		 XMLMAP.addChild(new XMLNotificationElem("DTSOFMSG"));
		 XMLMAP.addChild(new XMLActivityElem("ACTIVITY"));
//		 XMLElem model = new XMLGroupElem(null, "SVCMOD", "D:SVCMOD");
//		 XMLMAP.addChild(model);	 		 
		 XMLMAP.addChild(new XMLElem("ENTITYTYPE","ENTITYTYPE"));
		 XMLMAP.addChild(new XMLElem("ENTITYID","ENTITYID"));
		 XMLMAP.addChild(new XMLRELATElem("OFFERING_ID","SMACHTYPEATR","MODELATR"));
		 XMLElem model = new XMLGroupElem(null, "SVCMOD", "D:SVCMOD");
		 XMLMAP.addChild(model);
		 model.addChild(new XMLElem("OFFERING_MARKETING_NM", "INVNAME"));
		 
		 XMLElem feature = new XMLGroupElem(null, "SWSFEATURE", "U:SWSFEATURE");
		 XMLMAP.addChild(feature);
		 feature.addChild(new XMLElem("FEATURE_CD","FEATURECODE"));
//		 feature.addChild(new XMLElem("FEATURE_MKTNG_NAME","FCMKTNAME"));
		 
		 XMLMAP.addChild(new XMLElem("QTY_MIN_NUM","QTYMINNUM"));
		 XMLMAP.addChild(new XMLElem("QTY_MAX_NUM","QTYMAXNUM"));
		 XMLMAP.addChild(new XMLElem("FEATURE_S_NM","TMFMKTGSHRTDESC"));
		 XMLMAP.addChild(new XMLElem("FEATURE_L_NM","TMFMKTGLONGDESC"));
		 XMLMAP.addChild(new XMLStatusElem("STATUS", "STATUS", XMLElem.FLAGVAL));
		 XMLMAP.addChild(new XMLElem("MAP_TO_PRICE_TYPE_CD","MAPTOPRICETYPECD"));
		 XMLMAP.addChild(new XMLElem("FEATURE_CATEG_CD","FEATURECATEGCD"));
		 XMLMAP.addChild(new XMLElem("FEATURE_MODIF_IND","FEATUREMODIFIND"));
		 XMLMAP.addChild(new XMLElem("SERVICE_LEVEL_MAPPING_CD","SERVICELEVELMAPPINGCD"));
		 	 
		 XMLElem list = new XMLElem("AVAILABILITYLIST");
		 XMLMAP.addChild(list);
		 list.addChild(new XMLSWSTMFAVAILElem());
		 
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
    public String getVeName() { return "ADSSWSPRODSTRUCT"; }

    
    
    /**********************************
     * get the status attribute to use for this ABR
     */
    public String getStatusAttr() { return "STATUS";}

    /**********************************
     *
     A.	MQ-Series CID
     */
   
	public String getMQCID() {	return "SWSPRODSTRUCT_UPDATE";}

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
