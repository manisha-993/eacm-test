//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PartNo.java,v $
// Revision 1.17  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.16  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.15  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.14  2005/01/26 23:20:02  dave
// Jtest clean up
//
// Revision 1.13  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.12  2004/12/20 20:23:32  roger
// Support for sessions of PartNo uniqueness checking
//
// Revision 1.11  2004/09/15 23:39:10  dave
// fixing change group and sp's
//
// Revision 1.10  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.9  2004/07/29 23:15:47  roger
// Fixes
//
// Revision 1.8  2004/07/29 22:31:20  roger
// Fixes
//
// Revision 1.7  2004/07/29 21:33:45  roger
// Fixes
//
// Revision 1.6  2004/07/26 20:20:14  roger
// Avoid null pointer
//
// Revision 1.5  2004/07/26 19:18:13  roger
// Fixes
//
// Revision 1.4  2004/07/26 17:41:21  roger
// Fixes
//
// Revision 1.3  2004/07/26 17:04:39  roger
// Change scope
//
// Revision 1.2  2004/07/26 17:03:45  roger
// First pass ...
//
// Revision 1.1  2004/07/26 16:46:53  roger
// New module for PartNo reservations
//
//


package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 * This module supports PartNo reservations
 * @version @date
 */
public class PartNo implements Serializable, Cloneable {
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
     * Creates an PartNo Object
     */
    private PartNo() {
    }

    /**
     * returns a new SessionID
     *
     * @param _db
     * @param _prof
     * @return
     * @author Dave
     */
    public static int getSessionId(Database _db, Profile _prof) {
        int returnSessionId = -1;
        try {
            returnSessionId = _db.getNextEntityID(_prof, "PARTNO");
        } catch (Exception z) {
            D.ebug("something happened getting sessonid for PARTNO " + z);
        }
        return returnSessionId;
    }

    private static String get(Database _db, String _strPartNo) {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        String strReturn = "";

        try {
            _db.freeStatement();
            _db.isPending("begin getting PartNo");
            D.ebug(D.EBUG_DETAIL, "PartNo reservation GET for " + _strPartNo);

            rs = _db.callGBL9994(returnStatus, "GET", _strPartNo, 0);

            if (rs.next()) {
                strReturn = rs.getString(1).trim();
            }

        } catch (Exception z) {
            D.ebug("something happened getting PartNo " + _strPartNo + " " + z);

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    _db.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs = null;
        }

        _db.freeStatement();
        _db.isPending("end getting PartNo");

        return strReturn;
    }
    /**
     * put a partnumber into the tmp space
     *
     * @param _db
     * @param _strPartNo
     * @param _intSessionId
     * @author Dave
     */
    public static void put(
        Database _db,
        String _strPartNo,
        int _intSessionId) {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;

        try {
            _db.freeStatement();
            _db.isPending("begin putting PartNo");
            D.ebug(
                D.EBUG_DETAIL,
                "PartNo reservation PUT for "
                    + _strPartNo
                    + " Session "
                    + _intSessionId);

            rs =
                _db.callGBL9994(returnStatus, "PUT", _strPartNo, _intSessionId);

            // flush the result set
            while (rs.next()) {
            }

        } catch (Exception z) {
            D.ebug("something happened putting PartNo " + _strPartNo + " " + z);
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                    _db.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs = null;
        }
        _db.freeStatement();
        _db.isPending("end putting PartNo");

    }

    /**
     * Remove me from the database
     * @param _db
     * @param _strPartNo
     * @author Dave
     */
    public static void remove(Database _db, String _strPartNo) {

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;

        try {
            _db.freeStatement();
            _db.isPending("begin removing PartNo");
            D.ebug(
                D.EBUG_DETAIL,
                "PartNo reservation REMOVE for " + _strPartNo);

            rs = _db.callGBL9994(returnStatus, "REMOVE", _strPartNo, 0);

            // flush the result set
            while (rs.next()) {
            }

        } catch (Exception z) {
            D.ebug(
                "something happened removing PartNo " + _strPartNo + " " + z);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    _db.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs = null;
        }

        _db.freeStatement();
        _db.isPending("end removing PartNo");

    }

    /**
     * remove for Session
     *
     * @param _db
     * @param _strPartNo
     * @param _intSessionId
     * @author Dave
     */
    public static void removeForSession(
        Database _db,
        String _strPartNo,
        int _intSessionId) {

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;

        try {
            _db.freeStatement();
            _db.isPending("begin removing session PartNo");
            D.ebug(
                D.EBUG_DETAIL,
                "PartNo reservation REMOVE Session for "
                    + _strPartNo
                    + " SessionId "
                    + _intSessionId);

            rs =
                _db.callGBL9994(
                    returnStatus,
                    "SESSION",
                    _strPartNo,
                    _intSessionId);

            // flush the result set
            while (rs.next()) {
            }

        } catch (Exception z) {
            D.ebug(
                "something happened removing Session PartNo "
                    + _strPartNo
                    + " "
                    + z);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    _db.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs = null;
        }

        _db.freeStatement();
        _db.isPending("end removing session PartNo");

    }

    /**
     * exists
     *
     * @param _db
     * @param _strPartNo
     * @return
     * @author Dave
     */
    public static boolean exists(Database _db, String _strPartNo) {
        return (_strPartNo.equals(PartNo.get(_db, _strPartNo)));
    }
    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: PartNo.java,v 1.17 2008/01/31 21:29:04 wendy Exp $";
    }
}
