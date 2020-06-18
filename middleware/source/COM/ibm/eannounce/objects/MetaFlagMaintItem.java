// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: MetaFlagMaintItem.java,v $
// Revision 1.16  2012/09/05 14:35:34  wendy
// add dereference() release memory
//
// Revision 1.15  2007/06/04 18:32:30  wendy
// RQ110306297 support max length rule on long description for flags
//
// Revision 1.14  2006/10/05 19:33:48  joan
// MN 29551075
//
// Revision 1.13  2005/03/24 20:19:45  joan
// fixes
//
// Revision 1.12  2005/03/24 18:47:18  joan
// fix compile
//
// Revision 1.11  2005/03/24 18:35:54  joan
// fixes
//
// Revision 1.10  2005/03/08 19:40:33  joan
// fixes
//
// Revision 1.9  2005/03/07 23:00:56  dave
// more Jtest
//
// Revision 1.8  2005/03/04 19:18:53  joan
// work on maintenance flag
//
// Revision 1.7  2005/03/03 21:30:18  joan
// fixes
//
// Revision 1.6  2005/03/03 19:08:37  joan
// fixes
//
// Revision 1.5  2005/03/03 18:40:01  joan
// fixes
//
// Revision 1.4  2005/03/02 23:15:45  joan
// fixes
//
// Revision 1.3  2005/03/02 22:37:23  joan
// fixes
//
// Revision 1.2  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.1  2005/02/25 00:05:17  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.util.Hashtable;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
 * MetaFlagMaintItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaFlagMaintItem extends EANMetaEntity implements EANAddressable {
    private SimpleTextAttribute m_descriptionClass = null;
    private SimpleTextAttribute m_longDescription = null;
    private SimpleTextAttribute m_shortDescription = null;
    private int m_iNLSID = -1;
    private boolean m_bExpired = false;
    private boolean m_bNew = false;

    /**
     * FIELD
     */
    public final static String DESCRIPTIONCLASS = "0";
    /**
     * FIELD
     */
    public final static String LONGDESCRIPTION = "1";
    /**
     * FIELD
     */
    public final static String SHORTDESCRIPTION = "2";
    /**
     * FIELD
     */
    public final static String EXPIRED = "3";
    /**
     * FIELD
     */
    public final static String NLSID = "4";

    private Hashtable m_htOldValues = new Hashtable();

    protected void dereference(){
    	super.dereference();
    	if(m_htOldValues!=null){
    		m_htOldValues.clear();
    		m_htOldValues=null;
    	}
    	m_descriptionClass = null;
        m_longDescription = null;
        m_shortDescription = null;
    }
    /**
     * MetaFlagMaintItem
     *
     * @param _mf
     * @param _prof
     * @param _strDescClass
     * @param _iNLSID
     * @param _strLongDesc
     * @param _strShortDesc
     * @param _bExpired
     * @param _bNew
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaFlagMaintItem(EANMetaFoundation _mf, Profile _prof, String _strKey, String _strDescClass, int _iNLSID, String _strLongDesc, String _strShortDesc, boolean _bExpired, boolean _bNew) throws MiddlewareRequestException {
        super(_mf, _prof, _strKey + _iNLSID);
        m_descriptionClass = new SimpleTextAttribute(this, _prof, DESCRIPTIONCLASS, _strDescClass);
        m_longDescription = new SimpleTextAttribute(this, _prof, LONGDESCRIPTION, _strLongDesc); //RQ110306297 all 3 used DESCRIPTIONCLASS, need separate keys
        m_shortDescription = new SimpleTextAttribute(this, _prof, SHORTDESCRIPTION, _strShortDesc);
        m_iNLSID = _iNLSID;
        m_bExpired = _bExpired;
        m_bNew = _bNew;
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return m_descriptionClass + ":" + m_longDescription + ":" + m_shortDescription + ":" + m_iNLSID;
    }

    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _str) {

        //		System.out.println(strTraceBase + ":" + _str );
        if (_str.equals(DESCRIPTIONCLASS)) {
            return m_descriptionClass;
        } else if (_str.equals(LONGDESCRIPTION)) {
            return m_longDescription;
        } else if (_str.equals(SHORTDESCRIPTION)) {
            return m_shortDescription;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _str, boolean _b) {
		String strTraceBase = "MetaFlagMaintItem get method ";
//        System.out.println(strTraceBase + ":" + _str + ":" + _b);
        if (_str.equals(DESCRIPTIONCLASS)) {
            return m_descriptionClass.getValue();
        } else if (_str.equals(LONGDESCRIPTION)) {
            return m_longDescription.getValue();
        } else if (_str.equals(SHORTDESCRIPTION)) {
            return m_shortDescription.getValue();
        } else if (_str.equals(EXPIRED)) {
            return (m_bExpired ? "Y" : "N");
        } else if (_str.equals(NLSID)) {
            return m_iNLSID + "";
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _str, Object _o) throws EANBusinessRuleException {

        String strValue = null;

        if (!(_o instanceof String)) {
            return false;
        }

        strValue = (String) _o;
        if (_str.equals(DESCRIPTIONCLASS)) {
            if (m_htOldValues.get(DESCRIPTIONCLASS) == null) {
                m_htOldValues.put(DESCRIPTIONCLASS, m_descriptionClass.getValue());
            }
            m_descriptionClass.putValue(strValue);
        } else if (_str.equals(LONGDESCRIPTION)) {
            if (m_htOldValues.get(LONGDESCRIPTION) == null) {
                m_htOldValues.put(LONGDESCRIPTION, m_longDescription.getValue());
            }
            m_longDescription.putValue(strValue);
        } else if (_str.equals(SHORTDESCRIPTION)) {
            if (m_htOldValues.get(SHORTDESCRIPTION) == null) {
                m_htOldValues.put(SHORTDESCRIPTION, m_shortDescription.getValue());
            }

            m_shortDescription.putValue(strValue);
        }

        return false;
    }

    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isEditable(java.lang.String)
     */
    public boolean isEditable(String _str) {
        String strTraceBase = "MetaFlagMaintItem isEditable method ";
        if (isNew() && (!_str.equals(EXPIRED))) {
            return true;
        }
        if (_str.equals(LONGDESCRIPTION) || _str.equals(SHORTDESCRIPTION)) {
            return true;
        }
        return false;
    }

    /**
     * setExpired
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setExpired(boolean _b) {
        m_bExpired = _b;
    }

    /**
     * isExpired
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isExpired() {
        return m_bExpired;
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {
		String strTraceBase = "MetaFlagMaintItem rollback method ";
//		System.out.println(strTraceBase + _str);
        if (_str.equals(DESCRIPTIONCLASS)) {
            String strValue = (String) m_htOldValues.get(DESCRIPTIONCLASS);

            if (strValue != null) {
                m_htOldValues.remove(DESCRIPTIONCLASS);
                m_descriptionClass.putValue(strValue);
            }
        } else if (_str.equals(LONGDESCRIPTION)) {
            String strValue = (String) m_htOldValues.get(LONGDESCRIPTION);

            if (strValue != null) {
                m_htOldValues.remove(LONGDESCRIPTION);
                m_longDescription.putValue(strValue);
            }
        } else if (_str.equals(SHORTDESCRIPTION)) {
            String strValue = (String) m_htOldValues.get(SHORTDESCRIPTION);

            if (strValue != null) {
                m_htOldValues.remove(SHORTDESCRIPTION);
                m_shortDescription.putValue(strValue);
            }
        }
    }

    /**
     * hasChanges
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasChanges() {
        if (m_htOldValues == null) {
            return false;
        }
        return (m_htOldValues.size() > 0) || isNew();
    }

    /**
     * isNew
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isNew() {
        return m_bNew;
    }
    /**
     * commitLocal
     *
     *  @author David Bigelow
     */
    protected void commitLocal() {
        m_htOldValues = new Hashtable();
    }

    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return false;
    }
    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getLockGroup(java.lang.String)
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }
    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return false;
    }

    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        return null;
    }
    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
    }
    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
    }
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
    }
    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _str) {
        return false;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "	MetaFlagMaintItem:" + getKey() + ":");
        if (!_bBrief) {
            strbResult.append(NEW_LINE + "		Description Class: " + m_descriptionClass);
            strbResult.append(NEW_LINE + "		Long Description: " + m_longDescription);
            strbResult.append(NEW_LINE + "		ShortDescription: " + m_shortDescription);
            strbResult.append(NEW_LINE + "		NLSID: " + m_iNLSID);
        }

        return strbResult.toString();

    }

}
