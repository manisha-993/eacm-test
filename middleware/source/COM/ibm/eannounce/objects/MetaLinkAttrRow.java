//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaLinkAttrRow.java,v $
// Revision 1.12  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.11  2003/03/14 17:54:18  gregg
// remove remote pdh update method from MetaRow class - instead update using db through remote interface
//
// Revision 1.10  2003/03/12 20:52:49  gregg
// add updatePdhRemote method to ALL MetaRows
//
// Revision 1.9  2002/11/06 22:43:27  gregg
// removing display statements
//
// Revision 1.8  2002/08/23 22:15:20  gregg
// updatePdhMeta method - only throw MiddlewareException
//
// Revision 1.7  2002/04/11 20:08:35  gregg
// free db stmt
//
// Revision 1.6  2002/03/29 17:30:27  gregg
// made class final
//
// Revision 1.5  2002/03/12 23:23:53  gregg
// we are now updating to database
//
// Revision 1.4  2002/03/07 20:14:58  gregg
// syntax
//
// Revision 1.3  2002/03/07 20:05:07  gregg
// setLinkType in constructor
//
// Revision 1.2  2002/03/07 18:28:54  gregg
// ensure key = pk for MetaLinkAttribute table
//
// Revision 1.1  2002/03/01 18:28:11  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
* This is a container that holds all the information representing one Row in the MetaDescription Table
*
*/
public final class MetaLinkAttrRow extends MetaRow {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strLinkType1 = null;
    private String m_strLinkType2 = null;
    private String m_strLinkCode = null;
    private String m_strLinkValue = null;
    private String m_strLinkType = null;

    /**
     * MetaLinkAttrRow
     * @param _prof
     * @param _strLinkType
     * @param _strLinkType1
     * @param _strLinkType2
     * @param _strLinkCode
     * @param _strLinkvalue
     * @param _strValFrom
     * @param _strValTo
     * @param _strEffFrom
     * @param _strEffTo
     * @param _iTranId
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaLinkAttrRow(Profile _prof, String _strLinkType, String _strLinkType1, String _strLinkType2, String _strLinkCode, String _strLinkvalue, String _strValFrom, String _strValTo, String _strEffFrom, String _strEffTo, int _iTranId) throws MiddlewareRequestException {
        super(_prof, _strLinkType + _strLinkCode + _strLinkType1 + _strLinkType2 + _prof.getEnterprise() + _strValFrom + _strEffFrom, _strValFrom, _strValTo, _strEffFrom, _strEffTo, _iTranId);
        setLinkType1(_strLinkType1);
        setLinkType2(_strLinkType2);
        setLinkCode(_strLinkCode);
        setLinkValue(_strLinkvalue);
        setLinkType(_strLinkType);
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getLinkType() {
        return m_strLinkType;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLinkType(String _s) {
        m_strLinkType = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getLinkType1() {
        return m_strLinkType1;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLinkType1(String _s) {
        m_strLinkType1 = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getLinkType2() {
        return m_strLinkType2;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLinkType2(String _s) {
        m_strLinkType2 = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getLinkCode() {
        return m_strLinkCode;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLinkCode(String _s) {
        m_strLinkCode = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getLinkValue() {
        return m_strLinkValue;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLinkValue(String _s) {
        m_strLinkValue = _s;
        return;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        StringBuffer strBuf = new StringBuffer("[");
        strBuf.append(getEnterprise() + ",");
        strBuf.append(getLinkType() + ",");
        strBuf.append(getLinkType1() + ",");
        strBuf.append(getLinkType2() + ",");
        strBuf.append(getLinkCode() + ",");
        strBuf.append(getLinkValue() + ",");
        strBuf.append(getValFrom() + ",");
        strBuf.append(getValTo() + ",");
        strBuf.append(getEffFrom() + ",");
        strBuf.append(getEffTo() + ",");
        strBuf.append(getOPWGID() + ",");
        strBuf.append(getTranID() + "]");
        return strBuf.toString();
    }

    /**
     * Update the corresponding row int the PDH
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void updatePdh(Database _db) throws MiddlewareException {
        try {
            _db.debug(D.EBUG_SPEW, "(UPDATE) MetaLinkAttrRow: " + dump(false));
            _db.callGBL7504(new ReturnStatus(-1), getOPWGID(), getEnterprise(), getLinkType(), getLinkType1(), getLinkType2(), getLinkCode(), getLinkValue(), getTranID(), getEffFrom(), getEffTo());
            _db.freeStatement();
            _db.isPending();
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return;
    }

    /**
     * Get the version Id - generated by CVS
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: MetaLinkAttrRow.java,v 1.12 2005/03/08 23:15:46 dave Exp $";
    }

}
