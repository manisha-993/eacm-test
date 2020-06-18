//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductDetail.java,v $
// Revision 1.81  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.80  2005/01/04 23:21:27  gregg
// some more for prod detail rel
//
// Revision 1.79  2004/12/02 19:11:16  gregg
// use 9978 for unpublish
//
// Revision 1.78  2004/12/01 22:49:45  gregg
// more fix
//
// Revision 1.77  2004/12/01 22:22:16  gregg
// more fixes
//
// Revision 1.76  2004/12/01 21:59:19  gregg
// fixes
//
// Revision 1.75  2004/12/01 21:49:47  gregg
// adapting to the whole ProductDetailRec paradigm
//
// Revision 1.74  2004/11/09 22:46:37  gregg
// more isEquivalent, etc
//
// Revision 1.73  2004/11/09 20:29:38  gregg
// fix dump
//
// Revision 1.72  2004/11/09 19:30:27  gregg
// isEquivalent != equals!!!
//
// Revision 1.71  2004/11/09 00:19:52  gregg
// slowly adding in debugs
//
// Revision 1.70  2004/11/08 20:52:07  gregg
// to many debugs these days ...
// we need to start over on these to see whats goin on
//
// Revision 1.69  2004/11/05 19:31:06  gregg
// productDetail update logic is a changin' ...
//
// Revision 1.68  2004/11/01 17:23:44  gregg
// compile fix
//
// Revision 1.67  2004/10/29 21:20:54  gregg
// some fixes
//
// Revision 1.66  2004/10/29 21:08:39  gregg
// working on new product update logic
//
// Revision 1.65  2004/10/15 21:02:10  gregg
// focus on loops: settin temp variables to save access times
//
// Revision 1.64  2004/10/15 17:58:55  gregg
// more ticky tack tweaks
//
// Revision 1.63  2004/10/15 17:25:25  gregg
// dropping in some finals
//
// Revision 1.62  2004/10/07 04:19:59  dave
// cleaning up
//
// Revision 1.61  2004/10/06 20:56:29  gregg
// fixing some publish/unpublish logic
//
// Revision 1.60  2004/09/30 17:09:25  gregg
// GBL9982 for PD updates of non-multi-flags
//
// Revision 1.59  2004/09/30 01:40:32  dave
// ok. .if we update the detail .. lets get the header's valfrom
//
// Revision 1.58  2004/09/30 01:11:56  dave
// need to free pending
//
// Revision 1.57  2004/09/30 00:36:00  dave
// more syntax
//
// Revision 1.56  2004/09/30 00:02:20  dave
// new SP's for performance run testing
//
// Revision 1.55  2004/09/29 22:38:26  gregg
// move more debugs to D.ebug
//
// Revision 1.54  2004/09/29 22:33:02  gregg
// remove some debugs
//
// Revision 1.53  2004/09/28 18:20:10  gregg
// s'more updates vs inserts
//
// Revision 1.52  2004/09/28 17:56:28  gregg
// more tuning
//
// Revision 1.51  2004/09/28 16:34:28  gregg
// some experimental tuning
//
// Revision 1.50  2004/09/24 22:00:19  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.49  2004/09/24 02:35:43  dave
// more SPEW
//
// Revision 1.48  2004/09/24 01:28:01  dave
// more trace
//
// Revision 1.47  2004/09/23 18:31:53  dave
// some syntax
//
// Revision 1.46  2004/09/23 18:22:11  dave
// shoring up Layout Context
//
// Revision 1.45  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.44  2004/09/22 21:56:52  gregg
// wereSequencesUpdated() on eProductDetail + fix isEquivalent
//
// Revision 1.43  2004/09/22 17:49:59  gregg
// some updates
//
// Revision 1.42  2004/09/22 15:48:25  gregg
// some cleanup
//
// Revision 1.41  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.40  2004/09/16 22:44:15  gregg
// fix equals
//
// Revision 1.39  2004/09/13 20:54:29  gregg
// some getKey() stuff fer performance
//
// Revision 1.38  2004/09/13 16:56:45  gregg
// PreparedStatementCollection stuff
//
// Revision 1.37  2004/09/09 19:46:03  gregg
// delete routine
//
// Revision 1.36  2004/09/03 18:32:29  gregg
// fix
//
// Revision 1.35  2004/09/03 18:22:25  gregg
// blobs
//
// Revision 1.34  2004/09/03 08:12:54  dave
// trapping null pointer
//
// Revision 1.33  2004/09/02 23:04:25  gregg
// some long detail updating trickery
//
// Revision 1.32  2004/09/02 21:55:31  gregg
// collectionID passing for Longs
//
// Revision 1.30  2004/09/02 21:19:23  gregg
// remove infinite loop
//
// Revision 1.29  2004/09/02 21:03:29  gregg
// use eRecordCollection to manage longtext records
//
// Revision 1.28  2004/09/01 22:43:30  gregg
// fix
//
// Revision 1.27  2004/09/01 22:38:08  gregg
// prodDetailLong ingestion
//
// Revision 1.26  2004/09/01 22:26:24  gregg
// some long text fishing
//
// Revision 1.25  2004/09/01 20:37:59  gregg
// fix null column vals
//
// Revision 1.24  2004/09/01 19:02:01  gregg
// null ptr fix -- get now time from ePtoduct parent...
//
// Revision 1.23  2004/09/01 18:15:05  gregg
// fix
//
// Revision 1.22  2004/09/01 17:42:39  gregg
// eProductDetailLong object
//
// Revision 1.21  2004/09/01 16:05:51  gregg
// changing blob/longtext tables
//
// Revision 1.20  2004/09/01 15:49:20  gregg
// debug sql
//
// Revision 1.19  2004/08/31 22:16:31  gregg
// add table name in eTableRecord constructor
//
// Revision 1.18  2004/08/31 21:49:40  gregg
// more blob fix
//
// Revision 1.17  2004/08/31 21:40:50  gregg
// more blob stuff
//
// Revision 1.16  2004/08/31 21:30:02  gregg
// fix..
//
// Revision 1.15  2004/08/31 21:22:11  gregg
// fix??
//
// Revision 1.14  2004/08/31 21:03:17  gregg
// more for longtext,blob
//
// Revision 1.13  2004/08/31 20:06:30  gregg
// compile fixes
//
// Revision 1.12  2004/08/31 19:57:43  gregg
// some blob stuff
//
// Revision 1.11  2004/08/31 19:15:41  gregg
// setting up for BLOB,LONGTEXT
//
// Revision 1.10  2004/08/31 16:48:12  gregg
// isActive == false for outbounders
//
// Revision 1.9  2004/08/30 20:17:51  gregg
// more inbound/outbound rules
//
// Revision 1.8  2004/08/30 19:33:25  gregg
// inbound/outbound rule implementation
//
// Revision 1.7  2004/08/30 17:57:09  gregg
// isOutboundDerivedAttributeType()
//
// Revision 1.6  2004/08/25 21:21:32  gregg
// more logging
//
// Revision 1.5  2004/08/25 21:10:50  gregg
// some debugs
//
// Revision 1.4  2004/08/25 20:55:19  gregg
// isPublished() method
//
// Revision 1.3  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.2  2004/08/23 22:20:20  gregg
// some defaults for updateSequences.
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;

/**
 * eProductDetail
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class eProductDetail extends eTableRecord {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    public static final String TABLE_NAME = "PRODUCTDETAIL";

    /**
     * FIELD
     */
    public static final String ENTERPRISE = "ENTERPRISE";
    /**
     * FIELD
     */
    public static final String NLSID = "NLSID";
    /**
     * FIELD
     */
    public static final String COUNTRYCODE = "COUNTRYCODE";
    /**
     * FIELD
     */
    public static final String ENTITYTYPE = "ENTITYTYPE";
    /**
     * FIELD
     */
    public static final String ENTITYID = "ENTITYID";
    /**
     * FIELD
     */
    public static final String GSEQUENCE = "GSEQUENCE";
    /**
     * FIELD
     */
    public static final String GVISIBLE = "GVISIBLE";
    /**
     * FIELD
     */
    public static final String ISEQUENCE = "ISEQUENCE";
    /**
     * FIELD
     */
    public static final String IVISIBLE = "IVISIBLE";
    /**
     * FIELD
     */
    public static final String HERITAGE = "HERITAGE";
    /**
     * FIELD
     */
    public static final String ATTRIBUTECODE = "ATTRIBUTECODE";
    /**
     * FIELD
     */
    public static final String ATTRIBUTETYPE = "ATTRIBUTETYPE";
    /**
     * FIELD
     */
    public static final String FLAGCODE = "FLAGCODE";
    /**
     * FIELD
     */
    public static final String ATTRIBUTEVALUE = "ATTRIBUTEVALUE";
    /**
     * FIELD
     */
    public static final String PUBLISHFLAG = "PUBLISHFLAG";
    /**
     * FIELD
     */
    public static final String VALFROM = "VALFROM";
    /**
     * FIELD
     */
    public static final String RECID = "RECID";

    static {

        // BASE TABLE
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTERPRISE, 8);
        eTable.getETable(TABLE_NAME).makeIntColumn(NLSID);
        eTable.getETable(TABLE_NAME).makeStringColumn(COUNTRYCODE, 2);
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTITYTYPE, 32);
        eTable.getETable(TABLE_NAME).makeIntColumn(ENTITYID);
        eTable.getETable(TABLE_NAME).makeIntColumn(GSEQUENCE);
        eTable.getETable(TABLE_NAME).makeIntColumn(GVISIBLE);
        eTable.getETable(TABLE_NAME).makeIntColumn(ISEQUENCE);
        eTable.getETable(TABLE_NAME).makeIntColumn(IVISIBLE);
        eTable.getETable(TABLE_NAME).makeStringColumn(HERITAGE, 128);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTECODE, 32);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTETYPE, 1);
        eTable.getETable(TABLE_NAME).makeStringColumn(FLAGCODE, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTEVALUE, 128);
        eTable.getETable(TABLE_NAME).makeStringColumn(PUBLISHFLAG, 1);
        eTable.getETable(TABLE_NAME).makeStringColumn(VALFROM, 26);
        eTable.getETable(TABLE_NAME).makeIntColumn(RECID);
    }

    /**
     * FIELD
     */
    public static final String NO_COLUMN_VAL = "N/A";
    /**
     * FIELD
     */
    public static final String UNPUBLISH_VAL = "N";
    /**
     * FIELD
     */
    public static final String PUBLISH_VAL = "Y";
    /**
     * FIELD
     */
    public static final String INBOUND_DERIVED_ATTRIBUTETYPE = "D";
    /**
     * FIELD
     */
    public static final String OUTBOUND_DERIVED_ATTRIBUTETYPE = "O";

    private eProductDetailLong m_eProdDetailLong = null;
    private eProductDetailBlob m_eProdDetailBlob = null;
    private boolean m_bSequencesUpdated = false;
    private boolean m_bInsert = false;

    private eProductDetail(
        eProduct _parentProd,
        Profile _prof,
        String _strKey,
        String _strEntityType,
        int _iEntityID,
        int _iGSequence,
        int _iGVisible,
        int _iISequence,
        int _iIVisible,
        String _strHeritage,
        String _strAttCode,
        String _strAttType,
        String _strFlagCode,
        String _strAttValue,
        String _strPublishFlag,
        String _strValFrom,
        int _iRecID)
        throws MiddlewareRequestException, MiddlewareException, SQLException {

        super(_parentProd, _prof, _strKey, TABLE_NAME);

        putStringVal(ENTERPRISE, _prof.getEnterprise());
        putIntVal(NLSID, _parentProd.getIntVal(eProduct.NLSID));
        putStringVal(COUNTRYCODE, _parentProd.getStringVal(eProduct.COUNTRYCODE));
        putStringVal(ENTITYTYPE, _strEntityType);
        putIntVal(ENTITYID, _iEntityID);
        putIntVal(GSEQUENCE, _iGSequence);
        putIntVal(GVISIBLE, _iGVisible);
        putIntVal(ISEQUENCE, _iISequence);
        putIntVal(IVISIBLE, _iIVisible);
        putStringVal(HERITAGE, _strHeritage);
        putStringVal(ATTRIBUTECODE, _strAttCode);
        putStringVal(ATTRIBUTETYPE, _strAttType);
        putStringVal(FLAGCODE, _strFlagCode);
        putStringVal(ATTRIBUTEVALUE, _strAttValue);
        putStringVal(PUBLISHFLAG, _strPublishFlag);
        putStringVal(VALFROM, _strValFrom);
        putIntVal(RECID, _iRecID);
    }
    /**
     * PRODUCTDETAIL table flavor
     *
     * @param _parentProd
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @param _iGSequence
     * @param _iGVisible
     * @param _iISequence
     * @param _iIVisible
     * @param _strHeritage
     * @param _strAttCode
     * @param _strAttType
     * @param _strFlagCode
     * @param _strAttValue
     * @param _strPublishFlag
     * @param _strValFrom
     * @param _iRecID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException 
     */
    public eProductDetail(
        eProduct _parentProd,
        Profile _prof,
        String _strEntityType,
        int _iEntityID,
        int _iGSequence,
        int _iGVisible,
        int _iISequence,
        int _iIVisible,
        String _strHeritage,
        String _strAttCode,
        String _strAttType,
        String _strFlagCode,
        String _strAttValue,
        String _strPublishFlag,
        String _strValFrom,
        int _iRecID)
        throws MiddlewareRequestException, MiddlewareException, SQLException {
        //
        this(
            _parentProd,
            _prof,
            (_prof.getEnterprise()
                + ":"
                + _parentProd.getStringVal(eProduct.ENTITYTYPE)
                + ":"
                + _parentProd.getIntVal(eProduct.ENTITYID)
                + ":"
                + _parentProd.getIntVal(eProduct.NLSID)
                + ":"
                + _strEntityType
                + ":"
                + _iEntityID
                + ":"
                + _strHeritage
                + ":"
                + _strAttCode
                + (_strAttType.equals("F") ? ":" + _strFlagCode : "")),
            _strEntityType,
            _iEntityID,
            _iGSequence,
            _iGVisible,
            _iISequence,
            _iIVisible,
            _strHeritage,
            _strAttCode,
            _strAttType,
            _strFlagCode,
            _strAttValue,
            _strPublishFlag,
            _strValFrom,
            _iRecID);

    }

    /**
     * PRODUCTDETAILLONG table flavor
     *
     * @param _parentProd
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @param _iGSequence
     * @param _iGVisible
     * @param _iISequence
     * @param _iIVisible
     * @param _strHeritage
     * @param _strAttCode
     * @param _strAttValue
     * @param _strPublishFlag
     * @param _strValFrom
     * @param _iLongCollectionID
     * @param _iRecID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException 
     */
    public eProductDetail(
        eProduct _parentProd,
        Profile _prof,
        String _strEntityType,
        int _iEntityID,
        int _iGSequence,
        int _iGVisible,
        int _iISequence,
        int _iIVisible,
        String _strHeritage,
        String _strAttCode,
        String _strAttValue,
        String _strPublishFlag,
        String _strValFrom,
        int _iLongCollectionID,
        int _iRecID)
        throws MiddlewareRequestException, MiddlewareException, SQLException {
        //
        this(
            _parentProd,
            _prof,
            (_prof.getEnterprise() + ":" + _parentProd.getStringVal(eProduct.ENTITYTYPE) + ":" + _parentProd.getIntVal(eProduct.ENTITYID) + ":" + _parentProd.getIntVal(eProduct.NLSID) + ":" + _strEntityType + ":" + _iEntityID + ":" + _strHeritage + ":" + _strAttCode),
            _strEntityType,
            _iEntityID,
            _iGSequence,
            _iGVisible,
            _iISequence,
            _iIVisible,
            _strHeritage,
            _strAttCode,
            "L",
            NO_COLUMN_VAL,
            NO_COLUMN_VAL,
            _strPublishFlag,
            _strValFrom,
            _iRecID);

        m_eProdDetailLong = eRecordCollection.getRecordCollection().getProductDetailLong(this, _iLongCollectionID, _strAttValue);
    }

    /**
     * PRODUCTDETAILBLOB table flavor
     *
     * @param _parentProd
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @param _iGSequence
     * @param _iGVisible
     * @param _iISequence
     * @param _iIVisible
     * @param _strHeritage
     * @param _strAttCode
     * @param _strAttFileName
     * @param _blob
     * @param _strPublishFlag
     * @param _strValFrom
     * @param _iBlobCollectionID
     * @param _iRecID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException 
     */
    public eProductDetail(
        eProduct _parentProd,
        Profile _prof,
        String _strEntityType,
        int _iEntityID,
        int _iGSequence,
        int _iGVisible,
        int _iISequence,
        int _iIVisible,
        String _strHeritage,
        String _strAttCode,
        String _strAttFileName,
        COM.ibm.opicmpdh.objects.Blob _blob,
        String _strPublishFlag,
        String _strValFrom,
        int _iBlobCollectionID,
        int _iRecID)
        throws MiddlewareRequestException, MiddlewareException, SQLException {
        //
        this(
            _parentProd,
            _prof,
            (_prof.getEnterprise() + ":" + _parentProd.getStringVal(eProduct.ENTITYTYPE) + ":" + _parentProd.getIntVal(eProduct.ENTITYID) + ":" + _parentProd.getIntVal(eProduct.NLSID) + ":" + _strEntityType + ":" + _iEntityID + ":" + _strHeritage + ":" + _strAttCode),
            _strEntityType,
            _iEntityID,
            _iGSequence,
            _iGVisible,
            _iISequence,
            _iIVisible,
            _strHeritage,
            _strAttCode,
            "B",
            NO_COLUMN_VAL,
            NO_COLUMN_VAL,
            _strPublishFlag,
            _strValFrom,
            _iRecID);
        
        m_eProdDetailBlob = eRecordCollection.getRecordCollection().getProductDetailBlob(this, _iBlobCollectionID, _blob);

    }

    /**
     * isMultiFlag
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean isMultiFlag() {
        return (getStringVal(ATTRIBUTETYPE).equals("F"));
    }

    private final boolean isOutBoundDerivedAttributeType() {
        return (getStringVal(ATTRIBUTETYPE).equals(OUTBOUND_DERIVED_ATTRIBUTETYPE));
    }

    /**
     * getProductHeader
     *
     * @return
     *  @author David Bigelow
     */
    public final eProduct getProductHeader() {
        return (eProduct) getParent();
    }

    /**
     * getProductUpdater
     *
     * @return
     *  @author David Bigelow
     */
    protected final eProductUpdater getProductUpdater() {
        return getProductHeader().getProductUpdater();
    }
    /**
     * getPreparedStatementCollection
     *
     * @return
     *  @author David Bigelow
     */
    protected final PreparedStatementCollection getPreparedStatementCollection() {
        return getProductUpdater().getPreparedStatementCollection();
    }

    /**
     * updateSequences
     *
     * @param _db
     *  @author David Bigelow
     */
    public final void updateSequences(Database _db) {
        int iPRID = getProductHeader().getIntVal(eProduct.PROJECTID);
        String strGenAreaName_fc = getProductHeader().getStringVal(eProduct.GENAREANAME_FC);
        String strCountryCode = getProductHeader().getStringVal(eProduct.COUNTRYCODE);
        String strContext = getProductHeader().getLayoutContext();
        eRulesCollection erc = eRulesCollection.getRulesCollection(_db, getProfile(), iPRID, strGenAreaName_fc, strCountryCode, strContext);
        String strGroupType = getStringVal(eProductDetail.ENTITYTYPE);
        String strHeritage = getStringVal(eProductDetail.HERITAGE);
        String strItemType = getStringVal(eProductDetail.ATTRIBUTECODE);
        String strNow = getProductHeader().getStringVal(eProduct.VALFROM); // product should have latest now time!!

        int iGSequence = erc.getGroupSeq(strGroupType, strHeritage, strItemType);
        int iGVisible = (erc.isGroupVisible(strGroupType, strHeritage, strItemType) ? 1 : 0);
        int iISequence = erc.getItemSeq(strGroupType, strHeritage, strItemType);
        int iIVisible = (erc.isItemVisible(strGroupType, strHeritage, strItemType) ? 1 : 0);
        if (getIntVal(eProductDetail.GSEQUENCE) != iGSequence) {
            putIntVal(eProductDetail.GSEQUENCE, iGSequence);
            putStringVal(eProductDetail.VALFROM, strNow);
        }
        if (getIntVal(eProductDetail.GVISIBLE) != iGVisible) {
            putIntVal(eProductDetail.GVISIBLE, iGVisible);
            putStringVal(eProductDetail.VALFROM, strNow);
        }
        if (getIntVal(eProductDetail.ISEQUENCE) != iISequence) {
            putIntVal(eProductDetail.ISEQUENCE, iISequence);
            putStringVal(eProductDetail.VALFROM, strNow);
        }
        if (getIntVal(eProductDetail.IVISIBLE) != iIVisible) {
            putIntVal(eProductDetail.IVISIBLE, iIVisible);
            putStringVal(eProductDetail.VALFROM, strNow);
        }
        m_bSequencesUpdated = true;
    }

    /**
     * Make the current Object look like the passed Object.
     *
     * @param _prodDetail 
     */
    public final void ingest(eProductDetail _prodDetail) {
        for (int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if (iColType == INT) {
                putIntVal(getColumnKey(i), _prodDetail.getIntVal(i));
            } else if (iColType == STRING) {
                putStringVal(getColumnKey(i), _prodDetail.getStringVal(i));
            } //else if(getColumnType(i) == BLOB) {
            //      putBlobVal(getColumnKey(i),_prodDetail.getBlobVal(i));
            //  }
        }
        if (hasLongDetail() && _prodDetail.hasLongDetail()) {
            getLongDetail().ingest(_prodDetail.getLongDetail());
        }
        if (hasBlobDetail() && _prodDetail.hasBlobDetail()) {
            getBlobDetail().ingest(_prodDetail.getBlobDetail());
        }
    }

    /**
     * Check column values for equality -- if the relevant columns are equals, we are not putting this ProductDetail.
     * We want to skip valfrom check. this is obvious.
     * We want to skip Sequences ... these are calculated after we do the equals() check
     *
     * @return boolean
     * @param _eProdDetail 
     */
    public final boolean isEquivalent(eProductDetail _eProdDetail) {
        int iColType = 0;
        
        COLUMN_LOOP : 
        for (int i = 0; i < getColumnCount(); i++) {

            if (getColumnKey(i).equals(VALFROM)) {
                continue COLUMN_LOOP;
            }

            iColType = getColumnType(i);
            if (iColType == INT) {
                if (getIntVal(i) != _eProdDetail.getIntVal(i)) {
                    return false;
                }
            } else if (iColType == STRING) {

                // DWB
                // Attempting to get a null pointer
                String strValThis = getStringVal(i);
                String strValThat = _eProdDetail.getStringVal(i);
                if ((strValThis == null && strValThat != null) || (strValThis != null && strValThat == null)) {
                    return false;
                }
                if (!strValThis.equals(strValThat)) {
                    return false;
                }
            }
        }
        //
        if (hasLongDetail() && _eProdDetail.hasLongDetail()) {
            if (!getLongDetail().isEquivalent(_eProdDetail.getLongDetail())) {
                return false;
            }
        }
        if (hasBlobDetail() && _eProdDetail.hasBlobDetail()) {
            if (!getBlobDetail().isEquivalent(_eProdDetail.getBlobDetail())) {
                return false;
            }
        }
        //
        return true;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        if (!_b) {
            for (int i = 0; i < getColumnCount(); i++) {
                sb.append(getStringVal(i) + ":");
            }
        } else {
            String strEnterprise = getStringVal(ENTERPRISE);
            String strEntityType = getStringVal(ENTITYTYPE);
            int iEntityID = getIntVal(ENTITYID);
            String strAttVal = getStringVal(ATTRIBUTEVALUE);
            int iRecID = getIntVal(RECID);
            sb.append(strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttVal + ":" + iRecID);

        }
        return sb.toString();
    }

    /**
     * (non-Javadoc)
     * isActive
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#isActive()
     */
    public final boolean isActive() {
        // NEVER update an Outbound....
        if (isOutBoundDerivedAttributeType()) {
            return false;
        }
        return super.isActive();
    }

    /**
     * unpublish
     *
     * @param _dbODS
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public final void unpublish(Database _dbODS) throws Exception {
        _dbODS.callGBL9978(new ReturnStatus(-1), getStringVal(ENTERPRISE), getIntVal(RECID));
        _dbODS.freeStatement();
        _dbODS.isPending();
    }

    /**
     * (non-Javadoc)
     * update
     *
     * @see COM.ibm.eannounce.hula.eTableRecord#update(COM.ibm.opicmpdh.middleware.Database)
     */
    public final void update(Database _db) throws Exception {

        if (!isActive()) {
            //eProductUpdater.logUpdate("     SKIPPING PD: " + dump(false));
            return;
        }

        //
        // Lets get an ODS connection here
        //
        _db.getODSConnection();

        D.ebug(D.EBUG_SPEW, "TRACK eProductDetail.update:" + getStringVal(ENTITYTYPE) + ":" + getIntVal(ENTITYID) + ":" + getStringVal(ATTRIBUTECODE) + ":isInsert?" + isInsert());

        putStringVal(VALFROM, getProductHeader().getStringVal(eProduct.VALFROM));

        if (isInsert()) {

            // Lets call and sp now.
            _db.callGBL9986(
                new ReturnStatus(-1),
                getStringVal(ENTERPRISE),
                getIntVal(NLSID),
                getStringVal(COUNTRYCODE),
                getStringVal(ENTITYTYPE),
                getIntVal(ENTITYID),
                getIntVal(GSEQUENCE),
                getIntVal(GVISIBLE),
                getIntVal(ISEQUENCE),
                getIntVal(IVISIBLE),
                getStringVal(HERITAGE),
                getStringVal(ATTRIBUTECODE),
                getStringVal(ATTRIBUTETYPE),
                getStringVal(FLAGCODE),
                getStringVal(ATTRIBUTEVALUE),
                getStringVal(PUBLISHFLAG),
                getStringVal(VALFROM),
                getIntVal(RECID));
            ;

            _db.freeStatement();
            _db.isPending();

        } else {

            if (isMultiFlag()) {
                // Lets call and sp now.
                _db.callGBL9984(new ReturnStatus(-1), getStringVal(ENTERPRISE), getStringVal(COUNTRYCODE), getIntVal(GSEQUENCE), getIntVal(GVISIBLE), getIntVal(ISEQUENCE), getIntVal(IVISIBLE), getStringVal(ATTRIBUTEVALUE), getStringVal(PUBLISHFLAG), getStringVal(VALFROM), getIntVal(RECID));
            } else {
                // Lets call and sp now.
                _db.callGBL9982(
                    new ReturnStatus(-1),
                    getStringVal(ENTERPRISE),
                    getStringVal(FLAGCODE),
                    getStringVal(COUNTRYCODE),
                    getIntVal(GSEQUENCE),
                    getIntVal(GVISIBLE),
                    getIntVal(ISEQUENCE),
                    getIntVal(IVISIBLE),
                    getStringVal(ATTRIBUTEVALUE),
                    getStringVal(PUBLISHFLAG),
                    getStringVal(VALFROM),
                    getIntVal(RECID));
            }
            _db.freeStatement();
            _db.isPending();

        }

        if (hasLongDetail()) {
            getLongDetail().update(_db);
        }

        if (hasBlobDetail()) {
            getBlobDetail().update(_db);
        }
        setActive(false);
    }

    /**
     * isPublished
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean isPublished() {
        return (getStringVal(PUBLISHFLAG).equals(PUBLISH_VAL));
    }

    /**
     * getLongDetail
     *
     * @return
     *  @author David Bigelow
     */
    public final eProductDetailLong getLongDetail() {
        return m_eProdDetailLong;
    }

    /**
     * hasLongDetail
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasLongDetail() {
        return (m_eProdDetailLong != null);
    }

    /**
     * getBlobDetail
     *
     * @return
     *  @author David Bigelow
     */
    public final eProductDetailBlob getBlobDetail() {
        return m_eProdDetailBlob;
    }

    /**
     * hasBlobDetail
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasBlobDetail() {
        return (m_eProdDetailBlob != null);
    }

    /**
     * wereSequencesUpdated
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean wereSequencesUpdated() {
        return m_bSequencesUpdated;
    }

    /**
     * isInsert
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean isInsert() {
        return m_bInsert;
    }
    /**
     * setInsert
     *
     * @param _b
     *  @author David Bigelow
     */
    public final void setInsert(boolean _b) {
        m_bInsert = _b;
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: eProductDetail.java,v 1.81 2005/02/09 22:13:43 dave Exp $";
    }

}
