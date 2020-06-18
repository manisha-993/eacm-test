//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaFoundation;

/**
 * eLayoutGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class eLayoutGroup extends EANMetaFoundation {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    protected String m_strType = null;
    /**
     * FIELD
     */
    protected boolean m_bVisible = false;
    /**
     * FIELD
     */
    protected String m_strValFrom = null;
    /**
     * FIELD
     */
    protected int m_iSeq = 9999;
    /**
     * FIELD
     */
    protected boolean m_bMulti = false;
    /**
     * FIELD
     */
    protected EANList m_elLayoutItem = new EANList();

    /**
     * Version info
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: eLayoutGroup.java,v 1.11 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * eLayoutGroup
     *
     * @param _ef
     * @param _strType
     * @param _strDesc
     * @param _iSeq
     * @param _bVisible
     * @param _bMulti
     * @param _strValFrom
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected eLayoutGroup(EANFoundation _ef, String _strType, String _strDesc, int _iSeq, boolean _bVisible, boolean _bMulti, String _strValFrom) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_ef, null, _strType);

        m_strType = _strType;
        m_bVisible = _bVisible;
        m_iSeq = _iSeq;
        m_bMulti = _bMulti;
        m_strValFrom = _strValFrom;
        putLongDescription(1, _strDesc);

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
     * isVisible
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isVisible() {
        return m_bVisible;
    }

    /**
     * isMulti
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isMulti() {
        return m_bMulti;
    }

    /**
     * getSeq
     *
     * @return
     *  @author David Bigelow
     */
    public int getSeq() {
        return m_iSeq;
    }

    /**
     * toString
     *
     * @return String
     *  @author David Bigelow
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     * dump
     *
     * @param _bBrief
     * @return
     *  @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(this.toString());
        return new String(strbResult);
    }

    /**
     * getLayoutItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getLayoutItemCount() {
        return m_elLayoutItem.size();
    }

    /**
     * getLayoutItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public eLayoutItem getLayoutItem(int _i) {
        return (eLayoutItem) m_elLayoutItem.getAt(_i);
    }

    /**
     * getLayoutItem
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public eLayoutItem getLayoutItem(String _str) {
        return (eLayoutItem) m_elLayoutItem.get(_str);
    }

    /**
     * getLayoutItem
     *
     * @param _strHeritage
     * @param _strEntityType
     * @param _strAttributeCode
     * @return
     *  @author David Bigelow
     */
    public eLayoutItem getLayoutItem(String _strHeritage, String _strEntityType, String _strAttributeCode) {
        String strKey = _strHeritage + "." + _strEntityType + "." + _strAttributeCode;
        return (eLayoutItem) m_elLayoutItem.get(strKey);
    }

    /**
     * putLayoutItem
     *
     * @param _li
     *  @author David Bigelow
     */
    public void putLayoutItem(eLayoutItem _li) {
        if (!m_elLayoutItem.containsKey(_li.getKey())) {
            m_elLayoutItem.put(_li);
        }
    }

    /**
     * getDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getDescription() {
        return getLongDescription();
    }
}
