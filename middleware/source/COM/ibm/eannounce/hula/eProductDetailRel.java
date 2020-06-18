//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductDetailRel.java,v $
// Revision 1.3  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2005/01/04 23:21:27  gregg
// some more for prod detail rel
//
// Revision 1.1  2004/12/02 19:34:43  gregg
// initial load
//
//
//

package COM.ibm.eannounce.hula;

//import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import java.sql.*;

/**
 * This class is really a helper for eProductDetail.  It cannot exist without an eProductDetail Parent.
 */
public final class eProductDetailRel extends eTableRecord {

    static final long serialVersionUID = 20011106L;

    public static final String TABLE_NAME = "PRODDETAILREL";

    public static final String ENTERPRISE    = "ENTERPRISE";
    public static final String HEADERID      = "HEADERID";
    public static final String DETAILID      = "DETAILID";
    public static final String PUBLISHFLAG   = "PUBLISHFLAG";
    public static final String VALFROM       = "VALFROM";

    static {

        eTable.getETable(TABLE_NAME).makeStringColumn(ENTERPRISE,8);
        eTable.getETable(TABLE_NAME).makeIntColumn(HEADERID);
        eTable.getETable(TABLE_NAME).makeIntColumn(DETAILID);
        eTable.getETable(TABLE_NAME).makeStringColumn(PUBLISHFLAG,1);
        eTable.getETable(TABLE_NAME).makeStringColumn(VALFROM,26);

    }

    //
    //private static final String m_strODSSchema = eProductProperties.getDatabaseSchema();
    private boolean m_bWasValueChanged = false;
    //

    protected eProductDetailRel(eProduct _prodHeader, eProductDetail _prodDetail, String _strPublishFlag, String _strValFrom) throws MiddlewareRequestException, MiddlewareException, SQLException {

        super(_prodDetail,_prodHeader.getProfile(),(buildKey(_prodHeader,_prodDetail)),TABLE_NAME);

        putStringVal(ENTERPRISE,getProfile().getEnterprise());
        putIntVal(HEADERID,_prodHeader.getIntVal(eProduct.RECID));
        putIntVal(DETAILID,_prodDetail.getIntVal(eProductDetail.RECID));
        putStringVal(PUBLISHFLAG,_strPublishFlag);
        putStringVal(VALFROM,_strValFrom);
    }

    protected static final String buildKey(eProduct _prodHeader, eProductDetail _prodDetail) {
        String s1 = _prodHeader.getStringVal(eProductDetail.ENTERPRISE);
        int i1    = _prodHeader.getIntVal(eProduct.RECID);
        int i2    = _prodDetail.getIntVal(eProductDetail.RECID);
        return s1+":"+i1+":"+i2;
    }

    public final eProduct getProductHeader() {
        return getProductDetail().getProductHeader();
    }

    public final eProductDetail getProductDetail() {
        return (eProductDetail)getParent();
    }

    protected final eProductUpdater getProductUpdater() {
        return getProductDetail().getProductUpdater();
    }
    protected final PreparedStatementCollection getPreparedStatementCollection() {
        return getProductUpdater().getPreparedStatementCollection();
    }

    protected final boolean wasLongValueChanged() {
        return m_bWasValueChanged;
    }


/**
 * Check column values for equality -- if the relevant columns are equals, we are not putting this ProductDetail.
 */

    public final boolean isEquivalent(eProductDetailLong _eProdDetailLong) {
      //COLUMN_LOOP:
        for(int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if(iColType == INT) {
                if(getIntVal(i) != _eProdDetailLong.getIntVal(i)) {
                    //eProductUpdater.debug(getIntVal(i) + " != " + _eProdDetail.getIntVal(i));
                    return false;
                }
            } else if(iColType == STRING) {
                if(!getStringVal(i).equals(_eProdDetailLong.getStringVal(i))) {
                    //eProductUpdater.debug("\"" + getStringVal(i) + "\"" + " !equals \"" + _eProdDetail.getStringVal(i) + "\"");
                    return false;
                }
            }
        }
        return true;
    }


    public final String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < getColumnCount(); i++) {
            sb.append(getStringVal(i) + ":");
        }
        //sb.append("\n");
        return sb.toString();
    }

    public final void unpublish(Database _dbODS) throws Exception {
        _dbODS.callGBL9980(new ReturnStatus(-1),
                           getStringVal(ENTERPRISE),
                           getIntVal(HEADERID),
                           getIntVal(DETAILID));
        _dbODS.freeStatement();
        _dbODS.isPending();
    }

/**
 * This is really an INSERT!
 */
    public final void update(Database  _db) throws Exception {

        _db.callGBL9977(new ReturnStatus(-1),
                        getStringVal(ENTERPRISE),
                        getIntVal(HEADERID),
                        getIntVal(DETAILID),
                        getStringVal(PUBLISHFLAG),
                        getStringVal(VALFROM));
        _db.freeStatement();
        _db.isPending();

    }

/*
 * Version info
 */
    public String getVersion() {
        return new String("$Id: eProductDetailRel.java,v 1.3 2008/01/31 21:05:17 wendy Exp $");
    }


}
