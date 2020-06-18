//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTransitionRow.java,v $
// Revision 1.6  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.5  2003/03/14 17:54:18  gregg
// remove remote pdh update method from MetaRow class - instead update using db through remote interface
//
// Revision 1.4  2003/03/12 20:52:49  gregg
// add updatePdhRemote method to ALL MetaRows
//
// Revision 1.3  2002/11/06 22:43:27  gregg
// removing display statements
//
// Revision 1.2  2002/08/23 22:15:21  gregg
// updatePdhMeta method - only throw MiddlewareException
//
// Revision 1.1  2002/04/12 17:41:37  gregg
// initial load
//
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
* This is a container that holds all the information representing one Row in the MetaTransition Table
*
*/
public final class MetaTransitionRow extends MetaRow {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strTransitionCode = null;
    private String m_strAttributeCode1 = null;
    private String m_strFlag1 = null;
    private String m_strAttributeCode2 = null;
    private String m_strFlag2 = null;

    /**
     * MetaTransitionRow
     * 
     * @param _prof
     * @param _strTransitionCode
     * @param _strAttributeCode1
     * @param _strFlag1
     * @param _strAttributeCode2
     * @param _strFlag2
     * @param _strValFrom
     * @param _strValTo
     * @param _strEffFrom
     * @param _strEffTo
     * @param _iTranId
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTransitionRow(Profile _prof, String _strTransitionCode, String _strAttributeCode1, String _strFlag1, String _strAttributeCode2, String _strFlag2, String _strValFrom, String _strValTo, String _strEffFrom, String _strEffTo, int _iTranId) throws MiddlewareRequestException {
        super(_prof, _strTransitionCode + _strAttributeCode1 + _strFlag1 + _strAttributeCode2 + _strFlag2 + _prof.getEnterprise() + _strValFrom + _strEffFrom, _strValFrom, _strValTo, _strEffFrom, _strEffTo, _iTranId);
        setTransitionCode(_strTransitionCode);
        setAttributeCode1(_strAttributeCode1);
        setFlag1(_strFlag1);
        setAttributeCode2(_strAttributeCode2);
        setFlag2(_strFlag2);
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getFlag2() {
        return m_strFlag2;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setFlag2(String _s) {
        m_strFlag2 = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getTransitionCode() {
        return m_strTransitionCode;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setTransitionCode(String _s) {
        m_strTransitionCode = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getAttributeCode1() {
        return m_strAttributeCode1;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setAttributeCode1(String _s) {
        m_strAttributeCode1 = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getFlag1() {
        return m_strFlag1;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setFlag1(String _s) {
        m_strFlag1 = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getAttributeCode2() {
        return m_strAttributeCode2;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setAttributeCode2(String _s) {
        m_strAttributeCode2 = _s;
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
        strBuf.append(getTransitionCode() + ",");
        strBuf.append(getAttributeCode1() + ",");
        strBuf.append(getFlag1() + ",");
        strBuf.append(getAttributeCode2() + ",");
        strBuf.append(getFlag2() + ",");
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
            _db.debug(D.EBUG_SPEW, "(UPDATE) MetaTransitionRow: " + dump(false));
            _db.callGBL7519(new ReturnStatus(-1), getOPWGID(), getEnterprise(), getTransitionCode(), getAttributeCode1(), getFlag1(), getAttributeCode2(), getFlag2(), getTranID(), getEffFrom(), getEffTo());
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
        return "$Id: MetaTransitionRow.java,v 1.6 2005/03/08 23:15:46 dave Exp $";
    }

}
