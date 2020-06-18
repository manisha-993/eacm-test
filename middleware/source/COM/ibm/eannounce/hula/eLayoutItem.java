//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eLayoutItem.java,v $
// Revision 1.11  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.10  2004/09/07 21:26:45  dave
// syntax
//
// Revision 1.9  2004/09/07 20:49:25  dave
// two flavors of layoutgroups (single, multi)
//
// Revision 1.8  2004/08/27 20:59:00  dave
// adding Valfrom to all objects
//
// Revision 1.7  2004/08/27 16:34:53  dave
// changes
//
// Revision 1.6  2004/08/26 04:50:46  dave
// rework on rules
//
// Revision 1.5  2004/08/23 16:47:35  dave
// more import fixing
//
// Revision 1.4  2004/08/23 16:40:41  dave
// fixing imports
//
// Revision 1.3  2004/08/23 16:20:41  dave
// new import statements
//
// Revision 1.2  2004/08/23 16:18:14  dave
// Move to new package
//
// Revision 1.1  2004/08/23 16:15:20  dave
// moving eObjects to hula subdirectory
//
// Revision 1.7  2004/08/19 18:30:47  dave
// commit new Pricing Stuff from ODS
//
// Revision 1.6  2004/08/19 16:34:28  dave
// exposing methods
//
// Revision 1.5  2004/08/18 22:52:07  dave
// more changes
//
// Revision 1.4  2004/08/18 21:59:33  dave
// proteced vis public
//
// Revision 1.3  2004/08/18 21:27:00  dave
// syntax
//
// Revision 1.2  2004/08/18 21:21:18  dave
// syntax
//
// Revision 1.1  2004/08/18 21:09:07  dave
// new eLayoutGroup, eLayoutItem
//
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaFoundation;


/**
 * eLayoutItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class eLayoutItem extends EANMetaFoundation {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    protected String m_strType = null;
    /**
     * FIELD
     */
    protected String m_strSubType = null;
    /**
     * FIELD
     */
    protected String m_strHeritage = null;
    /**
     * FIELD
     */
    protected String m_strValFrom = null;
    /**
     * FIELD
     */
    protected boolean m_bVisible = false;
    /**
     * FIELD
     */
    protected int m_iSeq = 9999;
    /**
     * FIELD
     */
    protected eProductDetail m_pd = null;
    /**
     * Version info
     *
     * @return String
     * @return String
     */
    public String getVersion() {
        return "$Id: eLayoutItem.java,v 1.11 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * eLayoutItem
     *
     * @param _ef
     * @param _strType
     * @param _strHeritage
     * @param _strSubType
     * @param _strDesc
     * @param _iSeq
     * @param _bVisible
     * @param _strValFrom
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected eLayoutItem(EANFoundation _ef, String _strType, String _strHeritage, String _strSubType, String _strDesc, int _iSeq, boolean _bVisible, String _strValFrom) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_ef, null, _strHeritage + '.' + _strType + '.' + _strSubType);

        m_strType = _strType;
        m_strSubType = _strSubType;
        m_strHeritage = _strHeritage;
        m_bVisible = _bVisible;
        m_iSeq = _iSeq;
        m_strValFrom = _strValFrom;
        putLongDescription(1, _strDesc);

    }

    /**
     * Lets derive one here with this constructors
     *
     * @param _li
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    protected eLayoutItem(eLayoutItem _li) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_li, null, _li.getKey());

        m_strType = _li.getType();
        m_strSubType = _li.getSubType();
        m_strHeritage = _li.getHeritage();
        m_bVisible = _li.isVisible();
        m_iSeq = _li.getSeq();
        m_strValFrom = _li.getValFrom();
        putLongDescription(_li.getLongDescription());

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
     * getSeq
     *
     * @return
     *  @author David Bigelow
     */
    public int getSeq() {
        return m_iSeq;
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

    /**
     * getHeritage
     *
     * @return
     *  @author David Bigelow
     */
    public String getHeritage() {
        return m_strHeritage;
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
     * getSubType
     *
     * @return
     *  @author David Bigelow
     */
    public String getSubType() {
        return m_strType;
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
     * toString
     *
     * @return
     * @author David Bigelow
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     * dump
     * @param _bBrief
     * @return String
     * @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(m_strType + "." + m_strHeritage + "." + m_strSubType + "." + getLongDescription() + "." + m_iSeq + "." + m_bVisible);
        return new String(strbResult);
    }

    /**
     * isRequired
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isRequired() {
        eRulesCollection rc = null;
        eLayoutGroup lg = (eLayoutGroup) getParent();
        if (lg == null) {
            return false;
        }
        rc = (eRulesCollection) lg.getParent();
        if (rc == null) {
            return false;
        }
        return rc.isRequired(this);
    }

    /**
     * setProductDetail
     *
     * @param _pd
     *  @author David Bigelow
     */
    protected void setProductDetail(eProductDetail _pd) {
        m_pd = _pd;
    }

    /**
     * getProductDetail
     *
     * @return
     *  @author David Bigelow
     */
    protected eProductDetail getProductDetail() {
        return m_pd;
    }

}
