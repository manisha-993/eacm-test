//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ParentChildGroup.java,v $
// Revision 1.7  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.6  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.5  2003/10/30 00:56:16  dave
// more profile fixes
//
// Revision 1.4  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.3  2003/01/04 01:02:45  joan
// fix bugs
//
// Revision 1.2  2003/01/03 23:43:00  joan
// fixes
//
// Revision 1.1  2003/01/03 21:35:19  joan
// initial load
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.transactions.OPICMList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
* This object all the LockItems For a given e-announce EntityItem
*/
public class ParentChildGroup extends EANMetaEntity {
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strParentCond = null;
    private OPICMList m_childCondList = new OPICMList();

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * ParentChildGroup
     *
     * @param _ef
     * @param _db
     * @param _prof
     * @param _strGroup
     * @param _strParentCond
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ParentChildGroup(EANMetaFoundation _ef, Database _db, Profile _prof, String _strGroup, String _strParentCond) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_ef, _prof, _strGroup);
        setParentCond(_strParentCond);

        try {
            // The stored procedure ReturnStatus
            String strMethod = "ParentChildGroup constructor";

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            Profile prof = getProfile();

            String strEnterprise = prof.getEnterprise();
            int iOPWGID = prof.getOPWGID();

            DatePackage dpNow = _db.getDates();

            String strNow = dpNow.getNow();

            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.debug(D.EBUG_DETAIL, "ParentChildGroup: Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "ParentChildGroup: OPENID: " + iOPWGID);
            _db.debug(D.EBUG_DETAIL, "ParentChildGroup:" + _strGroup);
            _db.test(_strGroup != null, "_strGroup is null");
            _db.test(_strParentCond != null, "_strParentCond is null");

            try {
                rs = _db.callGBL8201(returnStatus, strEnterprise, _strGroup, strNow, strNow, iOPWGID);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                String strKey = rdrs.getColumn(j, 0).trim();
                String strValue = rdrs.getColumn(j, 1).trim();

                _db.debug(D.EBUG_SPEW, "gbl8201 answers:" + strKey + ":" + strValue);
                m_childCondList.put(strKey, strValue);
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();

            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: ParentChildGroup " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: ParentChildGroup" + rx);
        } finally {
            // Free any statement
            _db.commit();
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, " ParentChildGroup complete");
        }
    }

    /**
     * setParentCond
     *
     * @param _strParentCond
     *  @author David Bigelow
     */
    protected void setParentCond(String _strParentCond) {
        m_strParentCond = _strParentCond;
    }

    /**
     * getParentCond
     *
     * @return
     *  @author David Bigelow
     */
    public String getParentCond() {
        return m_strParentCond;
    }

    /**
     * getChildCond
     *
     * @return
     *  @author David Bigelow
     */
    public OPICMList getChildCond() {
        return m_childCondList;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "ParentChildGroup:" + getKey() + ":");
        strbResult.append(NEW_LINE + "m_strParentCond:" + m_strParentCond + ":");

        if (!_bBrief) {
            strbResult.append(NEW_LINE + "Child Conditions: ");
            for (int i = 0; i < m_childCondList.size(); i++) {
                String s = (String) m_childCondList.getAt(i);
                strbResult.append(NEW_LINE + "i: " + i + ":" + s);
            }
        }

        return new String(strbResult);
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ParentChildGroup.java,v 1.7 2005/03/10 00:17:47 dave Exp $";
    }
}
