//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PDGCollectInfoItem.java,v $
// Revision 1.5  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.4  2004/08/04 17:30:54  joan
// add new PDG
//
// Revision 1.3  2003/10/31 19:21:27  joan
// change constructor signature
//
// Revision 1.2  2003/06/26 22:25:12  joan
// initial load
//
// Revision 1.1.2.1  2003/06/18 18:17:32  joan
// initial load
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * PDGCollectInfoItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PDGCollectInfoItem extends EANDataFoundation {
    private Boolean m_bSelected = null;
    private String m_strFirstItem = null;
    private String m_strSecondItem = null;
    private String m_strDescription = null;
    private boolean m_bEditable = true;
    /**
     * FIELD
     */
    public String[] m_aColInfos = null;

    /**
     * PDGCollectInfoItem
     *
     * @param _mf
     * @param _prof
     * @param _b
     * @param _strFirst
     * @param _strSecond
     * @param _strDesc
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public PDGCollectInfoItem(EANMetaFoundation _mf, Profile _prof, boolean _b, String _strFirst, String _strSecond, String _strDesc) throws MiddlewareRequestException {
        super(_mf, _prof, _strFirst + _strSecond);
        m_bSelected = new Boolean(_b);
        m_strFirstItem = _strFirst;
        m_strSecondItem = _strSecond;
        m_strDescription = _strDesc;
    }

    /**
     * (non-Javadoc)
     * setSelected
     *
     * @see COM.ibm.eannounce.objects.EANObject#setSelected(boolean)
     */
    public void setSelected(boolean _b) {
        m_bSelected = new Boolean(_b);
    }

    /**
     * (non-Javadoc)
     * isSelected
     *
     * @see COM.ibm.eannounce.objects.EANObject#isSelected()
     */
    public boolean isSelected() {
        return m_bSelected.booleanValue();
    }

    /**
     * getSelected
     *
     * @return
     *  @author David Bigelow
     */
    public Boolean getSelected() {
        return m_bSelected;
    }
    /**
     * setFirstItem
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setFirstItem(String _s) {
        m_strFirstItem = _s;
    }

    /**
     * getFirstItem
     *
     * @return
     *  @author David Bigelow
     */
    public String getFirstItem() {
        return m_strFirstItem;
    }

    /**
     * setSecondItem
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setSecondItem(String _s) {
        m_strSecondItem = _s;
    }

    /**
     * getSecondItem
     *
     * @return
     *  @author David Bigelow
     */
    public String getSecondItem() {
        return m_strSecondItem;
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return m_strDescription;
    }

    /**
     * setEditable
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setEditable(boolean _b) {
        m_bEditable = _b;
    }

    /**
     * isEditable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEditable() {
        return m_bEditable;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "	PDGCollectInfoItem:" + getKey() + ":");
        if (!_bBrief) {
            strbResult.append(NEW_LINE + "		m_bSelected: " + m_bSelected);
            strbResult.append(NEW_LINE + "		m_strFirstItem: " + m_strFirstItem);
            strbResult.append(NEW_LINE + "		m_strSecondItem: " + m_strSecondItem);
            strbResult.append(NEW_LINE + "		m_strDescription: " + m_strDescription);
        }

        return new String(strbResult);

    }

}
