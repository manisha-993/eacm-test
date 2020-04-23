//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.psu;
 
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.MultipleFlag;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.transactions.OPICMObject;
  
/**
 * hold onto entity type and id that need updating
 * attributes to be updated will be added 
 */
//$Log: PSUUpdateData.java,v $
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
class PSUUpdateData implements OPICMObject {
	protected ReturnEntityKey rek;
	protected PSUABRSTATUS abr;
	protected Hashtable infoTbl = new Hashtable();
	protected String relatorKey=null;
	private Vector removeAttrVct; // need to hold onto this until the info is output to user, then use it
	
	/**
	 * used by psulinkdata constructor, the rek will be used for the newly created relator when it
	 * has attr to update
	 * @param psu
	 */
	PSUUpdateData(PSUABRSTATUS psu){
		abr = psu;
	} 
	
	/**
	 * @param psu
	 * @param entityType
	 * @param eid
	 */
	PSUUpdateData(PSUABRSTATUS psu, String entityType, int eid){
		rek = new ReturnEntityKey(entityType,eid, true);
		abr = psu;
	}
	
	/**
	 * get the highest entity id processed
	 * @return
	 */
	int getHighEntityId(){
		return getEntityId();
	}
	/**
	 * get entitytype 
	 * @return
	 */
	String getEntityType(){
		return rek.m_strEntityType;
	}
	/**
	 * get entityid 
	 * @return
	 */
	int getEntityId(){
		return rek.m_iEntityID;
	}
	/**
	 * this attribute will not be sent to the pdh - hold it for now
	 * if any of these attrs do not have values, cant deactivate them
	 * @param attr
	 */
	void addRemoveAttr(Attribute attr){
		if(removeAttrVct==null){
			removeAttrVct = new Vector();
		}
		removeAttrVct.add(attr);
	}
	
	/**
	 * remove any attributes from the returnentitykey now
	 */
	void removeAttrs(){
		if(removeAttrVct!=null && rek != null){
			abr.addDebug(D.EBUG_DETAIL,"PSUUpdateData.removeAttrs: removing "+removeAttrVct);
			rek.m_vctAttributes.removeAll(removeAttrVct);
			removeAttrVct.clear();
		}
	}
	/**
	 * release memory
	 */
	void dereference(){
		if(rek !=null){
			rek.m_strEntityType = null;
			if(rek.m_vctAttributes !=null){
				rek.m_vctAttributes.clear();
				rek.m_vctAttributes = null;
			}
			rek = null;
		}
		if(removeAttrVct!=null){
			removeAttrVct.clear();
			removeAttrVct=null;
		}
		abr = null;
		infoTbl.clear();
		infoTbl = null;
		relatorKey=null;
	}
	
	/**
	 * save the newly created relator - only used for PSULinkData
	 * @param key
	 */
	void setRelatorKey(String key){
		relatorKey = key;
	}
	/**
	 * set or deactivate this uniqueflag attribute
	 * @param attrcode
	 * @param attrvalue
	 * @param cb - cboff will deactivate the attr
	 */
	void setUniqueFlagValue(String attrcode, String attrvalue, ControlBlock cb)
	{
		abr.addDebug(D.EBUG_SPEW,"PSUUpdateData.setUniqueFlagValue "+rek.hashkey()+" "+attrcode+
				" set to: " + attrvalue+" cb.valto: "+cb.getValTo());

		if(!isAttrAdded(attrcode,attrvalue)){
			SingleFlag sf = new SingleFlag (abr.getProfile().getEnterprise(), rek.getEntityType(), rek.getEntityID(),
					attrcode, attrvalue, 1, cb);
			rek.m_vctAttributes.addElement(sf);
		}else{
			abr.addDebug("PSUUpdateData.setUniqueFlagValue: "+rek.hashkey()+" "+attrcode+
					" attrvalue:"+attrvalue+" was already added for updates ");
		}
	}
	/**
	 * set or deactivate this text attribute
	 * @param attrcode
	 * @param attrvalue
	 * @param cb - cboff will deactivate the attr
	 */
	void setTextValue(String attrcode, String attrvalue, ControlBlock cb)
	{
		abr.addDebug(D.EBUG_SPEW,"PSUUpdateData.setTextValue "+rek.hashkey()+" "+attrcode+
				" set to: " + attrvalue+" cb.valto: "+cb.getValTo());

		if(!isAttrAdded(attrcode,attrvalue)){
			COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(abr.getProfile().getEnterprise(),
					rek.getEntityType(), rek.getEntityID(), attrcode, attrvalue, 1, cb);
			rek.m_vctAttributes.addElement(sf);
		}else{
			abr.addDebug("PSUUpdateData.setTextValue: "+rek.hashkey()+" "+attrcode+
					" attrvalue:"+attrvalue+" was already added for updates ");
		}
	}

	/**
	 * set or deactivate this single multiflag 
	 * @param attrcode
	 * @param attrvalue
	 * @param cb - cboff will deactivate the flag
	 */
	void setMultiFlagValue(String attrcode, String attrvalue, ControlBlock cb)
	{
		abr.addDebug(D.EBUG_SPEW,"PSUUpdateData.setMultiFlagValue "+rek.hashkey()+" "+attrcode+
				" set to: " + attrvalue+" cb.valto: "+cb.getValTo());
		if(!isAttrAdded(attrcode,attrvalue)){
			MultipleFlag mf = new MultipleFlag(abr.getProfile().getEnterprise(),
					rek.getEntityType(), rek.getEntityID(), attrcode, attrvalue, 1, cb);
			rek.m_vctAttributes.addElement(mf);
		}else{
			abr.addDebug("PSUUpdateData.setMultiFlagValue: "+rek.hashkey()+" "+attrcode+
					" attrvalue:"+attrvalue+" was already added for updates ");
		}
	}

	/**
	 * check to see if this attr was already added for update
	 * @param attrcode
	 * @param val
	 * @return
	 */
	private boolean isAttrAdded(String attrcode, String val){
		if (rek.m_vctAttributes ==null){
			rek.m_vctAttributes = new Vector();
		}

		boolean exists = false;
		// look at each attr to see if this is there yet
		for (int i=0; i<rek.m_vctAttributes.size(); i++){
			Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(i);
			if (attr.getAttributeCode().equals(attrcode) && 
					attr.getAttributeValue().equals(val)){
				exists = true;
				break;
			}
		}
		return exists;
	}

	/**
	 * OPICMList interface
	 * @return
	 */
	public String hashkey() {
		return rek.hashkey();
	}
	
	/**
	 * add user information
	 */
	void outputUserInfo(){
		infoTbl.put(PSUABRSTATUS.UPDATE_CLASS, "Update");
		infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYTYPE, getEntityType());
		infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYID, ""+getEntityId());

		fillInAttributeInfo(infoTbl);
		
		abr.addUpdateInfo(infoTbl);
	}

	/**
	 * get attribute information, all will be on one row with break delimiters
	 * @param attrInfoTbl
	 */
	protected void fillInAttributeInfo(Hashtable attrInfoTbl){
		if(rek !=null && rek.m_vctAttributes!=null){
			// create break delimited list of attribute info
			StringBuffer attrSb = new StringBuffer();
			StringBuffer attrValSb = new StringBuffer();
			StringBuffer attrTypeSb = new StringBuffer();
			StringBuffer attrActionSb = new StringBuffer();
			for(int x=0;x<rek.m_vctAttributes.size(); x++){
				Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(x);
				// must get type from abr type table, U,A,S are all SingleFlag
				String psuAttrType = abr.getAttrType(attr.getAttributeCode());
				String psuAttrAction = abr.getAttrAction(attr.m_cbControlBlock);
				if(attrSb.length()>0){
					attrSb.append("<br />");
					attrValSb.append("<br />");
					attrActionSb.append("<br />");
					attrTypeSb.append("<br />");
				}
				attrSb.append(attr.getAttributeCode());
				attrValSb.append(attr.getAttributeValue());
				attrActionSb.append(psuAttrAction);
				attrTypeSb.append(psuAttrType);
			}
			attrInfoTbl.put(PSUABRSTATUS.UPDATE_ATTRCODE, attrSb.toString());
			attrInfoTbl.put(PSUABRSTATUS.UPDATE_ATTRTYPE, attrTypeSb.toString());
			attrInfoTbl.put(PSUABRSTATUS.UPDATE_ATTRACT, attrActionSb.toString());
			attrInfoTbl.put(PSUABRSTATUS.UPDATE_ATTRVAL, attrValSb.toString());
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer(hashkey());
		if(rek !=null && rek.m_vctAttributes!=null){
			for (int i=0; i<rek.m_vctAttributes.size(); i++){
				Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(i);
				sb.append (":"+attr.getAttributeCode()+" "+attr.getAttributeValue());
			}
		}
		return sb.toString();
	}
}
