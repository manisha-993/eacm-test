//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTranslationGroup.java,v $
// Revision 1.16  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.15  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.14  2003/08/26 19:55:15  dave
// some syntax
//
// Revision 1.13  2003/08/18 18:34:40  dave
// added AttributeCodeDesc
//
// Revision 1.12  2003/08/08 18:04:25  dave
// o.k. Meta API Conv II
//
// Revision 1.11  2003/08/07 20:42:25  dave
// fixing an ispending issue
//
// Revision 1.10  2003/08/07 20:14:10  dave
// minor changes and logging
//
// Revision 1.9  2003/08/07 19:48:09  dave
// removing implements ...
//
// Revision 1.8  2003/08/07 19:37:11  dave
// removing the abstract thing
//
// Revision 1.7  2003/08/07 18:46:57  dave
// adding NLSItem Tracking
//
// Revision 1.6  2003/08/07 18:13:46  dave
// added exception throwing
//
// Revision 1.5  2003/08/07 18:00:40  dave
// Syntax Errors
//
// Revision 1.4  2003/08/07 17:46:01  dave
// syntax
//
// Revision 1.3  2003/08/07 17:32:29  dave
// more syntax
//
// Revision 1.2  2003/08/07 17:14:17  dave
// whoops.  Synctax
//
// Revision 1.1  2003/08/07 17:03:31  dave
// new classes to simplify POK code for Flag Translation
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manage a collection of MetaTranslationItems
 */
public class MetaTranslationGroup extends EANMetaEntity {

    static final long serialVersionUID = 1L;

    /**
     * MetaTranslationGroup
     *
     * @param _db
     * @param _prof
     * @param _anls
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public MetaTranslationGroup(Database _db, Profile _prof, NLSItem[] _anls) throws MiddlewareException, MiddlewareRequestException, SQLException {

        super(null, _prof, _prof.getEnterprise());

        try {

            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = _prof.getEnterprise();

            // Loop through the NLSItems and see what we get

            for (int i = 0; i < _anls.length; i++) {
                int iTargetNLS = _anls[i].getNLSID();
                try {
                    rs = _db.callGBL2921(new ReturnStatus(-1), strEnterprise, iTargetNLS);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                    rs.close();
                    rs = null;
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                _db.debug(D.EBUG_DETAIL, "gbl2921:record.count:" + rdrs.size());

                for (int iy = 0; iy < rdrs.size(); iy++) {
                    String strType = rdrs.getColumn(iy, 0);
                    String strCode = rdrs.getColumn(iy, 1);
                    String strCodeDesc = rdrs.getColumn(iy, 2);
                    String strValue = rdrs.getColumn(iy, 3);
                    String strValFrom = rdrs.getColumn(iy, 4);
                    String strDesc = rdrs.getColumn(iy, 5);
                    _db.debug(D.EBUG_SPEW, "gbl2921:answers:" + strType + ":" + strCode + ":" + strCodeDesc + ":" + strValue + ":" + strValFrom + ":" + strDesc);

                    // Find it in the list.. if not already in the list.. we will need to
                    // make it.. and place it in the list.. with the description.. then
                    MetaTranslationItem mti = getMetaTranslationItem(strType + strCode + strValue);
                    if (mti == null) {
                        mti = new MetaTranslationItem(this, _prof, strType, strCode, strCodeDesc, strValue, strValFrom);
                        mti.putLongDescription(strDesc);
                        putMetaTranslationItem(mti);
                    }

                    // we add the nlsItem it was found under to its list
                    mti.putTargetNLSItem(_anls[i]);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * putMetaTranslationItem
     *
     * @param _mti
     *  @author David Bigelow
     */
    protected void putMetaTranslationItem(MetaTranslationItem _mti) {
        putData(_mti);
    }

    /**
     * getMetaTranslationItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getMetaTranslationItem() {
        return getData();
    }

    /**
     * getMetaTranslationItem
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public MetaTranslationItem getMetaTranslationItem(String _str) {
        return (MetaTranslationItem) getData(_str);
    }

    /**
     * getMetaTranslationItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaTranslationItem getMetaTranslationItem(int _i) {
        return (MetaTranslationItem) getData(_i);
    }

    /**
     * getMetaTranslationItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaTranslationItemCount() {
        return getDataCount();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("MetaTranslationGroup:" + getKey() + NEW_LINE);
        for (int i = 0; i < getMetaTranslationItemCount(); i++) {
            sb.append(getMetaTranslationItem(i).dump(false));
        }
        return sb.toString();
    }

}
