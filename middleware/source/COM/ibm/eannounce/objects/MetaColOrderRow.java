//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaColOrderRow.java,v $
// Revision 1.6  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.5  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.4  2003/12/16 17:18:01  gregg
// equals() method
//
// Revision 1.3  2003/03/26 01:12:18  gregg
// type II constructor
//
// Revision 1.2  2003/03/14 17:54:18  gregg
// remove remote pdh update method from MetaRow class - instead update using db through remote interface
//
// Revision 1.1  2003/03/12 21:04:25  gregg
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
* This is a container that holds all the information representing one Row in the MetaColOrder Table
*
*/
public final class MetaColOrderRow extends MetaRow {

    /*******************************************
     * Perhaps this class should not extend MetaRow, b/c it doesn't use val/eff dates, but oh well....
     ******************************************/

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strEntityType = null;
    private String m_strAttributeCode = null;
    private int m_iOrder = -1;
    private int m_iVisible = -1;
    private int m_iOPWGID = -1;

    /**
     * Standard Constructor: OPWGID will assume that of the passed Profile.
     *
     * @param _prof
     * @param _strEntType
     * @param _strAttCode
     * @param _iOrder
     * @param _iVisible
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaColOrderRow(Profile _prof, String _strEntType, String _strAttCode, int _iOrder, int _iVisible) throws MiddlewareRequestException {
        this(_prof, _prof.getOPWGID(), _strEntType, _strAttCode, _iOrder, _iVisible);
        return;
    }

    /**
     * Used to pass in an OPWGID other than that of the Profile. Needed for Default Column Order Updates.
     *
     * @param _prof
     * @param _iOPWGID
     * @param _strEntType
     * @param _strAttCode
     * @param _iOrder
     * @param _iVisible
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaColOrderRow(Profile _prof, int _iOPWGID, String _strEntType, String _strAttCode, int _iOrder, int _iVisible) throws MiddlewareRequestException {
        super(_prof, _iOPWGID + _prof.getEnterprise() + _strEntType + _strAttCode + _iOrder + _iVisible //key is MetaColOrder's pk
            , "", "", "", "", -1);
        setOPWGID(_iOPWGID);
        setEntityType(_strEntType);
        setAttributeCode(_strAttCode);
        setOrder(_iOrder);
        setVisible(_iVisible);
        return;
    }

    /**
     * (non-Javadoc)
     * equals
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object _obj) {
        if (_obj instanceof MetaColOrderRow) {
            MetaColOrderRow mcor = (MetaColOrderRow) _obj;
            if (getEntityType().equals(mcor.getEntityType()) && getAttributeCode().equals(mcor.getAttributeCode()) && getOPWGID() == mcor.getOPWGID()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Accessors
     *
     * @return int
     */
    public int getOPWGID() {
        return m_iOPWGID;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * getOrder
     *
     * @return
     *  @author David Bigelow
     */
    public int getOrder() {
        return m_iOrder;
    }

    /**
     * getVisible
     *
     * @return
     *  @author David Bigelow
     */
    public int getVisible() {
        return m_iVisible;
    }

    /**
     * Mutators
     *
     * @param _iOPWGID 
     */
    public void setOPWGID(int _iOPWGID) {
        m_iOPWGID = _iOPWGID;
    }

    /**
     * setEntityType
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setEntityType(String _s) {
        m_strEntityType = _s;
        return;
    }

    /**
     * setAttributeCode
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setAttributeCode(String _s) {
        m_strAttributeCode = _s;
        return;
    }

    /**
     * setOrder
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setOrder(int _i) {
        m_iOrder = _i;
        return;
    }

    /**
     * setVisible
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setVisible(int _i) {
        m_iVisible = _i;
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
        strBuf.append(getOPWGID() + ",");
        strBuf.append(getEntityType() + ",");
        strBuf.append(getAttributeCode() + ",");
        strBuf.append(getOrder() + ",");
        strBuf.append(getVisible() + "]");
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
            _db.debug(D.EBUG_SPEW, "(UPDATE) MetaColOrderRow: " + dump(false));
            _db.callGBL7542(new ReturnStatus(-1), getEnterprise(), getOPWGID(), getEntityType(), getAttributeCode(), getOrder(), getVisible());
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
        return "$Id: MetaColOrderRow.java,v 1.6 2005/03/11 22:42:53 dave Exp $";
    }


}
