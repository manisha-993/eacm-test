//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.psu;

import java.util.Vector; 
   
/**
 * hold onto entity type and id that need deactivation
 * 
 * To deactivate a relator the ABR needs the relator type and relator id and a DeleteAction.
 * DeleteAction will enforce pdhdomain, check locks, prevent orphans, check for dependencies and execute the delete
 * by expiring relator references and deactivate all attributes
 * 
 * C.	Creating Relators/ Deactivating Relators
 * This function (ABR) will be running in a minimalist environment and as such does not have easy access to 
 * all the information to create (update) relators. Therefore, an “ACTION” will be defined via meta data 
 * (similar to defining an “ACTION” for the user interface) and identified via a PDHUPDATEACT 
 * where PSUCLASS = “Reference” via “PSURELATORACTION”.
 */
//$Log: PSUDeleteData.java,v $
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
class PSUDeleteData extends PSUUpdateData {
	protected String actionName;
	protected String deleteType;
	protected Vector idVct = new Vector();

	/**
	 * @param psu
	 * @param delType
	 */
	PSUDeleteData(PSUABRSTATUS psu, String delType){
		super(psu);
		deleteType = delType;
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#getEntityType()
	 */
	String getEntityType(){
		return deleteType;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#getHighEntityId()
	 */
	int getHighEntityId(){
		return ((Integer)idVct.lastElement()).intValue();
	}
	/* (non-Javadoc)
	 * part of hashkey so always use first id
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#getEntityId()
	 */
	int getEntityId(){
		return ((Integer)idVct.firstElement()).intValue();
	}
	/**
	 * @param actnm
	 */
	void setActionName(String actnm){
		actionName = actnm;
	}
	/**
	 * add a entity id to be deactivated
	 * @param delid
	 */
	void addDeleteId(int delid){
		idVct.add(new Integer(delid));
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#outputUserInfo()
	 */
	void outputUserInfo(){
		//list deactivated relators
		for(int x=0;x<idVct.size();x++){
			infoTbl.put(PSUABRSTATUS.UPDATE_CLASS, "Reference");
			infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYTYPE, getEntityType());
			infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYID, idVct.elementAt(x).toString());
			infoTbl.put(PSUABRSTATUS.UPDATE_ATTRACT,PSUABRSTATUS.PSUATTRACTION_D);
			infoTbl.put(PSUABRSTATUS.UPDATE_RELACT, actionName);
			abr.addUpdateInfo(infoTbl);  // one row for each delete made
		}
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.bh.PSUABRSTATUS.PSUUpdateData#dereference()
	 */
	void dereference(){
		super.dereference();
		deleteType = null;
		idVct.clear();
		idVct = null;
		actionName = null;
	}
	/**
	 * OPICMList interface
	 * @return
	 */
	public String hashkey() {
		return deleteType+idVct.firstElement();
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#toString()
	 */
	public String toString(){
		return deleteType+":"+idVct;
	}
}