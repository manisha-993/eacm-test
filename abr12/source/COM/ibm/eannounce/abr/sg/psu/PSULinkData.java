//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.psu;

import java.util.Hashtable;

import COM.ibm.opicmpdh.transactions.OPICMList;
  
/**
 * hold onto entity types and ids that need linking
 * To create a relator the ABR needs the entity1 type, entity1 id, entity2 type, entity2 id, and a LinkAction.
 * LinkAction will enforce pdhdomain, check link limits, VE locks, Rule51 and then execute the link
 * 
 * C.	Creating Relators/ Deactivating Relators
 * This function (ABR) will be running in a minimalist environment and as such does not have easy access to 
 * all the information to create (update) relators. Therefore, an “ACTION” will be defined via meta data 
 * (similar to defining an “ACTION” for the user interface) and identified via a PDHUPDATEACT 
 * where PSUCLASS = “Reference” via “PSURELATORACTION”.
 * 
 */
//$Log: PSULinkData.java,v $
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
class PSULinkData extends PSUUpdateData {
	private Hashtable[] childInfoTbls;
	//parent entity
	protected String entityType1;
	protected int entityId1;
	// children
	private OPICMList childrenList = new OPICMList();

	/**
	 * parent entity add children later
	 * @param psu
	 * @param type1 
	 * @param id1
	 */
	PSULinkData(PSUABRSTATUS psu,String type1, int id1){
		super(psu);
		entityType1 = type1;
		entityId1 = id1;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.bh.PSUABRSTATUS.PSUUpdateData#dereference()
	 */
	void dereference(){
		super.dereference();
		entityType1 = null;
		while(childrenList.size()>0){
			PSUChildList psu = (PSUChildList)childrenList.remove(0);
			psu.dereference();
		}
		childrenList = null;
		if(childInfoTbls!=null){
			for(int i=0; i<childInfoTbls.length;i++){
				childInfoTbls[i].clear();
			}
			childInfoTbls=null;
		}
	}
	
	/**
	 * @return
	 */
	OPICMList getChildrenList(){
		return childrenList;
	}
	/**
	 * add child to link, all children will be linked at the same time
	 * @param abr
	 * @param childType
	 * @param childid
	 * @param linkaction
	 * @return
	 */
	PSUUpdateData addChild(PSUABRSTATUS abr, String childType, int childid, String linkaction)
	{
		PSUChildList psulist = (PSUChildList)childrenList.get(linkaction);
		if(psulist==null){
			psulist = new PSUChildList(linkaction);
			childrenList.put(psulist);
		}
		
		//can the same parent and child be passed, and then add another attribute?
		//check childrenList first
		PSUUpdateData psu = (PSUUpdateData)psulist.getChildrenList().get(childType+childid);
		if(psu==null){
			psu = new PSUUpdateData(abr,childType,childid);
			psulist.getChildrenList().put(psu);
			// attributes will be added to the child REK for convenience but must be moved to the newly created relator REK
		}
		return psu;
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#getEntityType()
	 */
	String getEntityType(){
		return entityType1;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#getEntityId()
	 */
	int getEntityId(){
		return entityId1;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#outputUserInfo()
	 */
	void outputUserInfo(){
		// hold info now, output after relator is created
		int childcnt=0;
		for(int x=0;x<childrenList.size();x++){
			PSUChildList psulist = (PSUChildList)childrenList.getAt(x);
			childcnt+=psulist.getChildrenList().size();
		}
			
		childInfoTbls = new Hashtable[childcnt];
		childcnt=0;
		// add link information
		for(int x=0;x<childrenList.size();x++){
			PSUChildList psulist = (PSUChildList)childrenList.getAt(x);
			for(int y=0;y<psulist.getChildrenList().size();y++){
				infoTbl.put(PSUABRSTATUS.UPDATE_CLASS, "Reference");
				infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYTYPE, getEntityType());
				infoTbl.put(PSUABRSTATUS.UPDATE_ENTITYID, ""+getEntityId());

				PSUUpdateData psuchild = (PSUUpdateData)psulist.getChildrenList().getAt(y);
				String psuEntityTypeRef = psuchild.getEntityType();
				String psuEntityIdRef = ""+psuchild.getEntityId();

				// this is an reference action
				infoTbl.put(PSUABRSTATUS.UPDATE_REF_ENTITYTYPE, psuEntityTypeRef);
				infoTbl.put(PSUABRSTATUS.UPDATE_REF_ENTITYID,psuEntityIdRef);
				infoTbl.put(PSUABRSTATUS.UPDATE_RELACT, psulist.getActionName());
				psuchild.fillInAttributeInfo(infoTbl);

				childInfoTbls[childcnt++]=(Hashtable)infoTbl.clone();
	//			abr.addUpdateInfo(infoTbl); // one for each child
			}
		}
	}
	/**
	 * output all user info now with newly created relator in the relatortype column
	 */
	void outputUserInfoWithRelator(){
		if(childInfoTbls!=null){
			int childcnt=0;
			for(int x=0;x<childrenList.size();x++){
				PSUChildList psulist = (PSUChildList)childrenList.getAt(x);
				for(int y=0;y<psulist.getChildrenList().size();y++){
					PSUUpdateData psuchild = (PSUUpdateData)psulist.getChildrenList().getAt(y);
					Hashtable info = childInfoTbls[childcnt++];
					if(psuchild.relatorKey!=null){
						info.put(PSUABRSTATUS.UPDATE_RELTYPE, psuchild.relatorKey);
					}
					abr.addUpdateInfo(info); // one for each child
				}
			}
		}
	}
	/**
	 * OPICMList interface
	 * @return
	 */
	public String hashkey() {
		return entityType1+entityId1;
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.psu.PSUUpdateData#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer(super.toString());
		for(int x=0;x<childrenList.size();x++){
			PSUChildList psuchild = (PSUChildList)childrenList.getAt(x);
			sb.append(":["+x+"]:"+psuchild);
		}
		return sb.toString();
	}
}
