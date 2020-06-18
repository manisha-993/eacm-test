//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTranslationItem.java,v $
// Revision 1.15  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.14  2004/01/13 19:54:41  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.13  2003/08/26 19:55:15  dave
// some syntax
//
// Revision 1.12  2003/08/26 19:42:29  dave
// providing getKey on AttributeMetaTranslation to
// match key of MetaTranslationItem
//
// Revision 1.11  2003/08/18 19:04:47  dave
// syntax (forgot member variable)
//
// Revision 1.10  2003/08/18 18:34:51  dave
// added AttributeDescription
//
// Revision 1.9  2003/08/08 18:55:20  dave
// syntax changes
//
// Revision 1.8  2003/08/08 18:04:25  dave
// o.k. Meta API Conv II
//
// Revision 1.7  2003/08/07 20:14:10  dave
// minor changes and logging
//
// Revision 1.6  2003/08/07 18:46:57  dave
// adding NLSItem Tracking
//
// Revision 1.5  2003/08/07 18:00:40  dave
// Syntax Errors
//
// Revision 1.4  2003/08/07 17:46:01  dave
// syntax
//
// Revision 1.3  2003/08/07 17:32:29  dave
// more syntax
//
// Revision 1.2  2003/08/07 17:14:17  dave
// whoops.  Synctax
//
// Revision 1.1  2003/08/07 17:03:31  dave
// new classes to simplify POK code for Flag Translation
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.util.Hashtable;

/**
* Provides storage/playback facility to 'Bookmark' one specific location in Navigation.
*/
public final class MetaTranslationItem extends EANMetaFoundation {

    static final long serialVersionUID = 1L;

    private String m_strType = null;
    private String m_strCode = null;
    private String m_strCodeDesc = null;
    private String m_strValue = null;
    private String m_strValFrom = null;

    /**
     * Constructor
     *
     * @param _strCode
     * @param _strCodeDesc
     * @param _strValue
     * @param _strValFrom
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _mf
     * @param _prof
     * @param _strType 
     */
    public MetaTranslationItem(EANMetaFoundation _mf, Profile _prof, String _strType, String _strCode, String _strCodeDesc, String _strValue, String _strValFrom) throws MiddlewareRequestException {
        super(_mf, _prof, _strType + _strCode + _strValue);
        m_strType = _strType;
        m_strCode = _strCode;
        m_strCodeDesc = _strCodeDesc;
        m_strValue = _strValue;
        m_strValFrom = _strValFrom;
        m_hsh2 = new Hashtable();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer();
        sb.append("MetaTranslationItem: " + getKey() + " - " + m_strType + ":" + m_strCode + ":" + m_strValue + ":" + m_strValFrom + ":" + getLongDescription() + ":" + m_hsh2);
        sb.append(NEW_LINE);
        return sb.toString();
    }

    /**
     * getType
     *
     * @return
     *  @author David Bigelow
     */
    public String getType() {
        return m_strType;
    }

    /**
     * getCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getCode() {
        return m_strCode;
    }

    /**
     * getCodeDesc
     *
     * @return
     *  @author David Bigelow
     */
    public String getCodeDesc() {
        return m_strCodeDesc;
    }

    /**
     * getValue
     *
     * @return
     *  @author David Bigelow
     */
    public String getValue() {
        return m_strValue;
    }
    /**
     * getValFrom
     *
     * @return
     *  @author David Bigelow
     */
    public String getValFrom() {
        return m_strValFrom;
    }

    /**
     * getTargetNLSItems
     *
     * @return
     *  @author David Bigelow
     */
    public Hashtable getTargetNLSItems() {
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        return m_hsh2;
    }

    /**
     * putTargetNLSItem
     *
     * @param _nls
     *  @author David Bigelow
     */
    public void putTargetNLSItem(NLSItem _nls) {
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        m_hsh2.put(_nls.getNLSID() + "", _nls);
    }

    /**
     * containsTargetNLSItem
     *
     * @param _nls
     * @return
     *  @author David Bigelow
     */
    public boolean containsTargetNLSItem(NLSItem _nls) {
        if (m_hsh2 == null) {
            return false;
        }
        return m_hsh2.containsKey(_nls.getNLSID() + "");
    }
}
