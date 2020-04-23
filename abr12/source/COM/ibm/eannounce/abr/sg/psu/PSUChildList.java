//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.psu;

import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.transactions.OPICMObject;

/**  
 * hold onto PSUUpdateData that will be linked as children - all are the same entitytype and share same linkaction
 */
//$Log: PSUChildList.java,v $
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
class PSUChildList implements OPICMObject  {
	private static final long serialVersionUID = 1L;
	private String actionName;
	// children
	private OPICMList childrenList;

	/**
	 * constructor
	 * @param name
	 */
	PSUChildList(String name){
		actionName = name;
		childrenList = new OPICMList();
	}
	
	String getActionName(){
		return actionName;
	}
	
	OPICMList getChildrenList(){
		return childrenList;
	}
	
	/**
	 * add child to link, all children will be linked at the same time
	 * @param abr
	 * @param childType
	 * @param childid
	 * @return
	 */
	PSUUpdateData addChild(PSUABRSTATUS abr, String childType, int childid){
		//can the same parent and child be passed, and then add another attribute?
		//check childrenList first
		PSUUpdateData psu = (PSUUpdateData)childrenList.get(childType+childid);
		if(psu==null){
			psu = new PSUUpdateData(abr,childType,childid);
			childrenList.put(psu);
			// attributes will be added to the child REK for convenience but must be moved to the newly created relator REK
		}
		return psu;
	}
	/**
	 * release memory
	 */
	void dereference(){
		while(childrenList.size()>0){
			PSUUpdateData psu = (PSUUpdateData)childrenList.remove(0);
			psu.dereference();
		}
		childrenList = null;
		actionName = null;
	}
	
	/* (non-Javadoc)
	 * @see COM.ibm.opicmpdh.transactions.OPICMObject#hashkey()
	 */
	public String hashkey() {
		return actionName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer(hashkey());
		for(int x=0;x<childrenList.size();x++){
			PSUUpdateData psuchild = (PSUUpdateData)childrenList.getAt(x);
			sb.append(":["+x+"]:"+psuchild);
		}
		return sb.toString();
	}
}
