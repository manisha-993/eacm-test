//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ParentChildList.java,v $
// Revision 1.16  2005/03/10 00:17:48  dave
// more Jtest work
//
// Revision 1.15  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.14  2003/06/18 22:32:01  gregg
// new Constructor
//
// Revision 1.13  2003/06/16 22:53:17  ted
// *** empty log message ***
//
// Revision 1.10  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.9  2003/04/15 23:23:17  dave
// EvaluatorII implementation
//
// Revision 1.8  2003/01/09 22:07:27  joan
// fix message
//
// Revision 1.7  2003/01/09 21:54:56  joan
// fix message
//
// Revision 1.6  2003/01/09 01:29:35  joan
// fix test method
//
// Revision 1.5  2003/01/04 01:02:45  joan
// fix bugs
//
// Revision 1.4  2003/01/03 23:51:55  joan
// fix errors
//
// Revision 1.3  2003/01/03 23:43:01  joan
// fixes
//
// Revision 1.2  2003/01/03 22:04:26  joan
// fix exception
//
// Revision 1.1  2003/01/03 21:35:19  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.T;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.transactions.OPICMList;

import java.sql.SQLException;

/**
 * ParentChildList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ParentChildList extends EANMetaEntity {

    // Instance variables
    private String m_strParentEntityType = null;
    private String m_strChildEntityType = null;
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * ParentChildList
     *
     * @param _db
     * @param _prof
     * @param _strParentEntityType
     * @param _strChildEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ParentChildList(Database _db, Profile _prof, String _strParentEntityType, String _strChildEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _strParentEntityType + _strChildEntityType);
        m_strParentEntityType = _strParentEntityType;
        m_strChildEntityType = _strChildEntityType;

        try {

            String strMethod = "ParentChildList constructor";

            // The stored procedure ReturnStatus
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            String strEnterprise = _prof.getEnterprise();
            int iOPWGID = _prof.getOPWGID();

            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            dpNow.getForever();

            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.debug(D.EBUG_DETAIL, "ParentChildList: Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "ParentChildList: OPENID: " + iOPWGID);
            _db.debug(D.EBUG_DETAIL, "ParentChildList:" + m_strParentEntityType + ":" + m_strChildEntityType);


            // getting all the lock records based on strEntityType, iEntityID
            try {
                rs = _db.callGBL8200(returnStatus, strEnterprise, m_strParentEntityType, m_strChildEntityType, strNow, strNow, iOPWGID);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                String strGroup = rdrs.getColumn(j, 0);
                String strParentCond = rdrs.getColumn(j, 1);

                ParentChildGroup pcg = new ParentChildGroup(this, _db, _prof, strGroup, strParentCond);
                _db.debug(D.EBUG_SPEW, "gbl8200 answers:" + strGroup + ":" + strParentCond);

                putParentChildGroup(pcg);
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();
     
            _db.debug(D.EBUG_ERR, "RuntimeException trapped at:" + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);
     
            throw new MiddlewareException("RuntimeException trapped at: " + rx);
        } finally {
 
            _db.freeStatement();
            _db.isPending();

        }
    }

    /**
     * ParentChildList
     *
     * @param _db
     * @param _prof
     * @param _pEI
     * @param _cEI
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ParentChildList(Database _db, Profile _prof, EntityItem _pEI, EntityItem _cEI) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_db, _prof, _pEI.getEntityType(), _cEI.getEntityType());
    }

    /**
     * testParentChild
     *
     * @param _pEI
     * @param _cEI
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.eannounce.objects.EntityItemException
     * @return
     *  @author David Bigelow
     */
    public boolean testParentChild(EntityItem _pEI, EntityItem _cEI) throws MiddlewareRequestException, EntityItemException {

        EntityItemException eie = new EntityItemException();
        boolean bParent = false;
        boolean bChild = false;

        T.est(_pEI != null, "Parent entity is null");
        T.est(_cEI != null, "Child entity is null");
        T.est(_pEI.getEntityType().equals(m_strParentEntityType), "Wrong Parent EntityType: " + _pEI.getEntityType() + ":" + m_strParentEntityType);
        T.est(_cEI.getEntityType().equals(m_strChildEntityType), "Wrong Child EntityType: " + _cEI.getEntityType() + ":" + m_strChildEntityType);

        if (getParentChildGroupCount() <= 0) {
            eie.add(_pEI, "No Parent/Child classifications defined.");
            throw eie;
        }

        for (int i = 0; i < getParentChildGroupCount(); i++) {
            ParentChildGroup pcg = getParentChildGroup(i);
            String strParentCond = pcg.getParentCond();

            // check parent conditions
            if (EvaluatorII.test(_pEI, strParentCond)) {
 
                OPICMList condList = pcg.getChildCond();
                bParent = true;
 
                for (int j = 0; j < condList.size(); j++) {
                    String cond = (String) condList.getAt(j);
                    if (EvaluatorII.test(_cEI, cond)) {
                        bChild = true;
                    }
                }
            }
        }

        if (!bParent) {
            eie.add(_pEI, "Parent/Child classifications do not apply");
        } else if (bParent && !bChild) {
            eie.add(_cEI, "Parent/Child classifications do not match");
        } else if (bParent && bChild) {
            return true;
        }

        if (eie.getErrorCount() > 0) {
            throw eie;
        }

        return false;
    }

    /**
     * putParentChildGroup
     *
     * @param _pcg
     *  @author David Bigelow
     */
    public void putParentChildGroup(ParentChildGroup _pcg) {
        putData(_pcg);
    }
    /**
     * returns the number of ParentChildGroups in this list
     *
     * @return int
     */
    public int getParentChildGroupCount() {
        return getDataCount();
    }

    /**
     * returns the EANList that contains all the tracked ParentChildGroups
     *
     * @return EANList
     */
    public EANList getParentChildGroup() {
        return getData();
    }

    /**
     * returns the ParentChildGroup at index i
     *
     * @return ParentChildGroup
     * @param _i 
     */
    public ParentChildGroup getParentChildGroup(int _i) {
        return (ParentChildGroup) getData(_i);
    }

    /**
     * returns the ParentChildGroup based upon the passed Key
     *
     * @return ParentChildGroup
     * @param _str 
     */
    public ParentChildGroup getParentChildGroup(String _str) {
        return (ParentChildGroup) getData(_str);
    }

    /*
    * Resets the ParentChildGroups in this list
    */
    /**
     * removeParentChildGroup
     *
     *  @author David Bigelow
     */
    public void removeParentChildGroup() {
        resetData();
    }

    /*
    * Removes the lockgroup in the list based upon the passed Key
    */
    /**
     * removeParentChildGroup
     *
     * @param _str
     *  @author David Bigelow
     */
    public void removeParentChildGroup(String _str) {
        getData().remove(_str);
    }

    /*
    * Removes the lockgroup in the list based upon the passed index
    * @param i index
    */
    /**
     * removeParentChildGroup
     *
     * @param _i
     *  @author David Bigelow
     */
    public void removeParentChildGroup(int _i) {
        getData().remove(_i);
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ParentChildList.java,v 1.16 2005/03/10 00:17:48 dave Exp $";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("ParentChildList:" + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getParentChildGroupCount(); i++) {
                ParentChildGroup pcg = getParentChildGroup(i);
                strbResult.append(NEW_LINE + pcg.dump(_bBrief));
            }
        }
        return new String(strbResult);
    }

    /**
     * ParentChildList
     *
     * @param _db
     * @param _prof
     * @param _pEG
     * @param _cEG
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ParentChildList(Database _db, Profile _prof, EntityGroup _pEG, EntityGroup _cEG) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _pEG.getEntityType() + _cEG.getEntityType());
        m_strParentEntityType = _pEG.getEntityType();
        m_strChildEntityType = _cEG.getEntityType();

        try {
            // The stored procedure ReturnStatus
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            String strEnterprise = _prof.getEnterprise();
            int iOPWGID = _prof.getOPWGID();

            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();

            _db.debug(D.EBUG_DETAIL, "ParentChildList: Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "ParentChildList: OPENID: " + iOPWGID);
            _db.debug(D.EBUG_DETAIL, "ParentChildList:" + m_strParentEntityType + ":" + m_strChildEntityType);

            try {            // getting all the lock records based on strEntityType, iEntityID
                rs = _db.callGBL8200(returnStatus, strEnterprise, m_strParentEntityType, m_strChildEntityType, strNow, strNow, iOPWGID);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                String strGroup = rdrs.getColumn(j, 0);
                String strParentCond = rdrs.getColumn(j, 1);
                ParentChildGroup pcg = new ParentChildGroup(this, _db, _prof, strGroup, strParentCond);

                _db.debug(D.EBUG_SPEW, "gbl8200 answers:" + strGroup + ":" + strParentCond);

                putParentChildGroup(pcg);
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: "  + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: "  + rx);
        } finally {
            // Free any statement
            _db.freeStatement();
            _db.isPending();

        }
    }
}
