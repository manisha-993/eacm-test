//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.*;
import java.util.*;

/**
 *  represents one row in a db2 view
 *
 */
// $Log: QueryItem.java,v $
// Revision 1.3  2009/05/11 15:14:22  wendy
// Support dereference for memory release
//
// Revision 1.2  2008/08/08 21:43:12  wendy
// CQ00006067-WI : LA CTO - More support for QueryAction
//
// Revision 1.1  2008/07/31 18:59:06  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
public class QueryItem extends EANFoundation implements EANAddressable {

	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2008  All Rights Reserved.";
	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.3 $";

	private Hashtable columnValuesTbl = null;
	protected void dereference(){
		if (columnValuesTbl!= null){
			columnValuesTbl.clear();
			columnValuesTbl = null;
		}
		super.dereference();
	}
	/*******
	 * Constructor
	 * @param _f
	 * @param _prof
	 * @param _s
	 * @param colsTbl
	 * @throws MiddlewareRequestException
	 */	
	public QueryItem(EANFoundation _f, Profile _prof, String _key, Hashtable colsTbl)
			throws MiddlewareRequestException {
		super(_f, _prof, _key);
		columnValuesTbl = colsTbl;		
	}
	
	/**********
	 * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
	 */
	public Object get(String _s, boolean _b) {
		return columnValuesTbl.get(_s);
	}

	/**********
	 * @see COM.ibm.eannounce.objects.EANFoundation#dump(boolean)
	 */
	public String dump(boolean _brief) {
		StringBuffer sb = new StringBuffer("EntityID: "+getKey());
		Enumeration eNum = columnValuesTbl.keys();
		while(eNum.hasMoreElements()){
			String key = (String)eNum.nextElement();
			sb.append("\n"+key+":"+columnValuesTbl.get(key));
		}
		
		return sb.toString();
	}

	/**********
	 * @see COM.ibm.eannounce.objects.EANFoundation#getLongDescription()
	 */
	public String getLongDescription() {
		return getKey();
	}

	/**********
	 * @see COM.ibm.eannounce.objects.EANFoundation#getShortDescription()
	 */
	public String getShortDescription() {
		return "";
	}

	/****************
	 * @see COM.ibm.eannounce.objects.EANFoundation#toString()
	 */
	public String toString() {
		return dump(true);
	}	
	// unused EANAddressable methods
	//------------------------------------------------------------------------------
	public EANFoundation getEANObject(String _str) {
		return null;
	}
	public String getHelp(String _str) {
		return null;
	}
	public LockGroup getLockGroup(String _s) {
		return null;
	}
	public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
		return false;
	}
	public boolean isChildAttribute(String _str) {
		return false;
	}
	public boolean isEditable(String _s) {
		return false;
	}
	public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
		return false;
	}
	public boolean isParentAttribute(String _str) {
		return false;
	}
	public boolean put(String _s, Object _o) throws EANBusinessRuleException {
		return false;
	}
	public void resetLockGroup(String _s, LockList _ll) {}
	public void rollback(String _str) {	}
	public void setParentEntityItem(EntityItem _ei) {}
	public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {}

}
