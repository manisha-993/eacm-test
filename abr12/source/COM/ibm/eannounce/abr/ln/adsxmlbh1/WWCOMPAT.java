package COM.ibm.eannounce.abr.ln.adsxmlbh1;

//$Log: WWCOMPAT.java,v $
//Revision 1.1  2015/02/04 14:55:49  wangyul
//RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
//Revision 1.2  2011/04/19 07:29:00  guobin
//add method getKey()
//
//Revision 1.1  2011/04/01 09:20:58  guobin
//add for the WWCOMPAT IDL
//
//Init for
// An IDL of this table (WWTECHCOMPAT) is required.
//
//	The section on Insert Additions covers the seven unique queries that should be used to populate this table from the PRICE schema.
//
//	Note:  TimeOfIDL is the time that they IDL is started. This can be set in a property file for the ABR.

public class WWCOMPAT {

	String BRANDCD_FC="";
	String SystemEntityType="";
	int SystemEntityId;
	String SystemOS ="";
	String GroupEntityType="";
	int GroupEntityId;
	String OKTOPUB="";
	String OSEntityType="";
	int OSEntityId;
	String OS="";
	String OptionEntityType="";
	int OptionEntityId;
	String CompatibilityPublishingFlag="";
	String RelationshipType="";
	String PublishFrom="";
	String PublishTo="";
	

	public WWCOMPAT() {
		
	}


	

	/**
	 * @return the bRANDCD_FC
	 */
	public String getBRANDCD_FC() {
		return BRANDCD_FC;
	}


	/**
	 * @param brandcd_fc the bRANDCD_FC to set
	 */
	public void setBRANDCD_FC(String brandcd_fc) {
		BRANDCD_FC = brandcd_fc;
	}


	/**
	 * @return the compatibilityPublishingFlag
	 */
	public String getCompatibilityPublishingFlag() {
		return CompatibilityPublishingFlag;
	}


	/**
	 * @param compatibilityPublishingFlag the compatibilityPublishingFlag to set
	 */
	public void setCompatibilityPublishingFlag(String compatibilityPublishingFlag) {
		CompatibilityPublishingFlag = compatibilityPublishingFlag;
	}


	/**
	 * @return the groupEntityId
	 */
	public int getGroupEntityId() {
		return GroupEntityId;
	}


	/**
	 * @param groupEntityId the groupEntityId to set
	 */
	public void setGroupEntityId(int groupEntityId) {
		GroupEntityId = groupEntityId;
	}


	/**
	 * @return the groupEntityType
	 */
	public String getGroupEntityType() {
		return GroupEntityType;
	}


	/**
	 * @param groupEntityType the groupEntityType to set
	 */
	public void setGroupEntityType(String groupEntityType) {
		GroupEntityType = groupEntityType;
	}


	/**
	 * @return the oKTOPUB
	 */
	public String getOKTOPUB() {
		return OKTOPUB;
	}


	/**
	 * @param oktopub the oKTOPUB to set
	 */
	public void setOKTOPUB(String oktopub) {
		OKTOPUB = oktopub;
	}


	/**
	 * @return the optionEntityId
	 */
	public int getOptionEntityId() {
		return OptionEntityId;
	}


	/**
	 * @param optionEntityId the optionEntityId to set
	 */
	public void setOptionEntityId(int optionEntityId) {
		OptionEntityId = optionEntityId;
	}


	/**
	 * @return the optionEntityType
	 */
	public String getOptionEntityType() {
		return OptionEntityType;
	}


	/**
	 * @param optionEntityType the optionEntityType to set
	 */
	public void setOptionEntityType(String optionEntityType) {
		OptionEntityType = optionEntityType;
	}


	/**
	 * @return the oS
	 */
	public String getOS() {
		return OS;
	}


	/**
	 * @param os the oS to set
	 */
	public void setOS(String os) {
		OS = os;
	}


	/**
	 * @return the oSEntityId
	 */
	public int getOSEntityId() {
		return OSEntityId;
	}


	/**
	 * @param entityId the oSEntityId to set
	 */
	public void setOSEntityId(int entityId) {
		OSEntityId = entityId;
	}


	/**
	 * @return the oSEntityType
	 */
	public String getOSEntityType() {
		return OSEntityType;
	}


	/**
	 * @param entityType the oSEntityType to set
	 */
	public void setOSEntityType(String entityType) {
		OSEntityType = entityType;
	}


	/**
	 * @return the publishFrom
	 */
	public String getPublishFrom() {
		return PublishFrom;
	}


	/**
	 * @param publishFrom the publishFrom to set
	 */
	public void setPublishFrom(String publishFrom) {
		PublishFrom = publishFrom;
	}


	/**
	 * @return the publishTo
	 */
	public String getPublishTo() {
		return PublishTo;
	}


	/**
	 * @param publishTo the publishTo to set
	 */
	public void setPublishTo(String publishTo) {
		PublishTo = publishTo;
	}


	/**
	 * @return the relationshipType
	 */
	public String getRelationshipType() {
		return RelationshipType;
	}


	/**
	 * @param relationshipType the relationshipType to set
	 */
	public void setRelationshipType(String relationshipType) {
		RelationshipType = relationshipType;
	}


	/**
	 * @return the systemEntityId
	 */
	public int getSystemEntityId() {
		return SystemEntityId;
	}


	/**
	 * @param systemEntityId the systemEntityId to set
	 */
	public void setSystemEntityId(int systemEntityId) {
		SystemEntityId = systemEntityId;
	}


	/**
	 * @return the systemEntityType
	 */
	public String getSystemEntityType() {
		return SystemEntityType;
	}


	/**
	 * @param systemEntityType the systemEntityType to set
	 */
	public void setSystemEntityType(String systemEntityType) {
		SystemEntityType = systemEntityType;
	}


	/**
	 * @return the systemOS
	 */
	public String getSystemOS() {
		return SystemOS;
	}


	/**
	 * @param systemOS the systemOS to set
	 */
	public void setSystemOS(String systemOS) {
		SystemOS = systemOS;
	}

	public  String getKey(){
		return SystemEntityType +"|"+SystemEntityId+"|"+ SystemOS + "|"  + 
		GroupEntityType+"|"+GroupEntityId+"|"+OSEntityType+"|"+OSEntityId+"|"+OS+"|"+OptionEntityType + "|"+OptionEntityId ;
	}
}
