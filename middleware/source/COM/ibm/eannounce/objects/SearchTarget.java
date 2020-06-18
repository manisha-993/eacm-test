//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SearchTarget.java,v $
// Revision 1.3  2008/09/08 17:44:50  wendy
// Cleanup some RSA warnings
//
// Revision 1.2  2004/06/18 17:24:07  joan
// work on edit relator
//
// Revision 1.1  2003/08/21 23:51:55  joan
// initial load
//

package COM.ibm.eannounce.objects;


import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


/**
* This manages SearchTarget information
* This manages any domain related entities
*/
public class SearchTarget extends EANDataFoundation implements EANAddressable {

	public static final String SELECTED_FLAG = "0";
	public static final String TARGET_DESC = "1";

	private String m_strEntityType = null;
	private String m_strAttributeCode = null;
	private String m_strETDesc = null;
	private String m_strAttrDesc = null;
	/**
	* Main method which performs a simple test of this class
	*/
	public static void main(String arg[]) {
	}

	public SearchTarget(EANFoundation _mf, Profile _prof, String _strEntityType, String _strAttributeCode) throws MiddlewareRequestException {
		super(_mf, _prof, _strEntityType + _strAttributeCode);
		m_strEntityType = _strEntityType;
		m_strAttributeCode = _strAttributeCode;
    }

	public Object get(String _s, boolean _b) {
		if (_s.equals(SELECTED_FLAG)) {
  			return new Boolean(isSelected());
		} else if (_s.equals( TARGET_DESC)) {
  			return getLongDescription();
		}

		return null;
	}

	public String getLongDescription() {
		if (m_strETDesc != null && m_strAttrDesc != null) {
			return m_strAttrDesc + " - " + m_strETDesc;
		}
		return m_strAttributeCode + " - " + m_strEntityType;
	}
	public boolean put(String _s, Object _o) {
		if (_s.equals(SELECTED_FLAG) && isEditable(_s)) {
     		setSelected(((Boolean)_o).booleanValue());
     		return true;
		} else if (_s.equals(TARGET_DESC) && isEditable(_s)) {
  			return false;
		}
		return false;
	}

	/*
	* The only attribute you can change is the selectable item
	*/
	public boolean isEditable(String _s) {
		if (_s.equals(SELECTED_FLAG)) {
     		return true;
		} else if (_s.equals( TARGET_DESC)) {
  			return false;
		}
		return false;
	}

	protected void setETDescription(String _s) {
		m_strETDesc = _s;
	}

	public String getETDescription() {
		return m_strETDesc;
	}

	public String getEntityType() {
		return m_strEntityType;
	}

	protected void setAttrDescription(String _s) {
		m_strAttrDesc = _s;
	}

	public String getAttrDescription() {
		return m_strAttrDesc;
	}

	public String getAttributeCode() {
		return m_strAttributeCode;
	}

	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("\nSearchTarget:" + getKey() + ":");
		strbResult.append("\nEntity Type:" + getEntityType() + ":");
		strbResult.append("\nEntity Description:" + getETDescription() + ":");
		strbResult.append("\nAttribute Code:" + getAttributeCode() + ":");
		strbResult.append("\nAttribute Description:" + getAttrDescription() + ":");
		return new String(strbResult);
	}

	public String toString() {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append(getKey());
		return new String(strbResult);
	}

 	/**
 	* Return the date/time this class was generated
 	* @return the date/time this class was generated
 	*/
 	public String getVersion() {
 	   return new String("$Id: SearchTarget.java,v 1.3 2008/09/08 17:44:50 wendy Exp $");
 	}

	public EANFoundation getEANObject(String _str) {return null;}
	public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {return false;}
	public LockGroup getLockGroup(String _s) {return null;}
	public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {return false;}
	public void rollback(String _str) { }
	public String getHelp(String _str) {return null;}
	public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) { }
	public void resetLockGroup(String _s, LockList _ll) { }
	public void setParentEntityItem(EntityItem _ei) { }
    public boolean isParentAttribute(String _str) { return false;}
    public boolean isChildAttribute(String _str) {return false;}


}
