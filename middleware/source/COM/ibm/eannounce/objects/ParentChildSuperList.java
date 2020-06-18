//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ParentChildSuperList.java,v $
// Revision 1.4  2005/03/10 00:17:48  dave
// more Jtest work
//
// Revision 1.3  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.2  2003/06/18 22:33:09  gregg
// inital load to v12
//
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

import java.sql.SQLException;

/**
 * Maintain a list of ParentChildLists.
 */
public class ParentChildSuperList extends EANMetaEntity {

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
     * ParentChildSuperList
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ParentChildSuperList(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(null, _prof, "ParentChildSuperList");
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = _prof.getEnterprise();
            try {
                rs = _db.callGBL7551(new ReturnStatus(-1), strEnterprise);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int iRow = 0; iRow < rdrs.size(); iRow++) {
                String strParentEntType = rdrs.getColumn(iRow, 0);
                String strChildEntType = rdrs.getColumn(iRow, 1);
                _db.debug(D.EBUG_DETAIL, "gbl7551 answers:" + strParentEntType + ":" + strChildEntType);
                putParentChildList(new ParentChildList(_db, _prof, strParentEntType, strChildEntType));
            }
        } finally {

            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * putParentChildList
     *
     * @param _pcl
     *  @author David Bigelow
     */
    public void putParentChildList(ParentChildList _pcl) {
        putData(_pcl);
    }

    /**
     * getParentChildListCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getParentChildListCount() {
        return getDataCount();
    }

    /**
     * getParentChildList
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getParentChildList() {
        return getData();
    }

    /**
     * getParentChildList
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public ParentChildList getParentChildList(int _i) {
        return (ParentChildList) getData(_i);
    }

    /**
     * getParentChildList
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public ParentChildList getParentChildList(String _str) {
        return (ParentChildList) getData(_str);
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ParentChildSuperList.java,v 1.4 2005/03/10 00:17:48 dave Exp $";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("ParentChildSuperList:" + getKey());
        for (int i = 0; i < getParentChildListCount(); i++) {
            ParentChildList pcl = getParentChildList(i);
            strbResult.append(NEW_LINE + pcl.dump(_bBrief));
        }
        return new String(strbResult);
    }

}
