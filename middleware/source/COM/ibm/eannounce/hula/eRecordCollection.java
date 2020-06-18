//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eRecordCollection.java,v $
// Revision 1.8  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.7  2004/12/09 18:29:06  gregg
// getProductDetail methods
//
// Revision 1.6  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.5  2004/09/03 17:55:32  gregg
// getProductDetailBlob
//
// Revision 1.4  2004/09/02 21:53:56  gregg
// introduce collection IDs
//
// Revision 1.3  2004/09/02 21:42:03  gregg
// allow different collections keyed on collection name
//
// Revision 1.2  2004/09/02 21:08:38  gregg
// make some static
//
// Revision 1.1  2004/09/02 21:03:07  gregg
// initial load
//
//

package COM.ibm.eannounce.hula;

//import COM.ibm.eannounce.objects.*;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import java.util.Hashtable;

/**
 * Maintain a common bucket of records for cases where multiple object copies contain references
 *   to the same object record.
 * For now, this includes PRODUCTDETAILLONG. PRODUCTDETAILBLOB.
 *
 * This may include PRODUCTDETAIL itself later...
 *
 */
public class eRecordCollection {

    public static final int FROM_ODS = 1;
    public static final int FROM_PDH = 2;

    private static eRecordCollection c_theRecordCollection = new eRecordCollection();

    private Hashtable m_hashRecords = null;

    private eRecordCollection() {
        m_hashRecords = new Hashtable();
    }

    protected static final eRecordCollection getRecordCollection() {
        return c_theRecordCollection;
    }

    protected final eProductDetailLong getProductDetailLong(eProductDetail _prodDetail, int _iCollectionID, String _strAttValue) throws MiddlewareRequestException, MiddlewareException, SQLException {
        String strKey = _iCollectionID + ":" + eProductDetailLong.buildKey(_prodDetail);
        eProductDetailLong pdLong = (eProductDetailLong)m_hashRecords.get(strKey);
        if(pdLong != null) {
            //eProductUpdater.debug("eRecordCollection.getProductDetailLong:" + _iCollectionID + ":" + pdLong.getKey() + " FOUND");
            return pdLong;
        }
        pdLong = new eProductDetailLong(_prodDetail,_strAttValue);
        m_hashRecords.put(strKey,pdLong);
        //eProductUpdater.debug("eRecordCollection.getProductDetailLong:" + _iCollectionID + ":" + pdLong.getKey() + " CREATED");
        return pdLong;
    }

    protected final eProductDetailBlob getProductDetailBlob(eProductDetail _prodDetail, int _iCollectionID, COM.ibm.opicmpdh.objects.Blob _blob) throws MiddlewareRequestException, MiddlewareException, SQLException {
        String strKey = _iCollectionID + ":" + eProductDetailBlob.buildKey(_prodDetail);
        eProductDetailBlob pdBlob = (eProductDetailBlob)m_hashRecords.get(strKey);
        if(pdBlob != null) {
            //eProductUpdater.debug("eRecordCollection.getProductDetailBlob:" + _iCollectionID + ":" + pdBlob.getKey() + " FOUND");
            return pdBlob;
        }
        pdBlob = new eProductDetailBlob(_prodDetail,_blob);
        m_hashRecords.put(strKey,pdBlob);
        //eProductUpdater.debug("eRecordCollection.getProductDetailBlob:" + _iCollectionID + ":" + pdBlob.getKey() + " CREATED");
        return pdBlob;
    }

/**
 * PRODUCTDETAIL Generic table flavor
 */
    protected final eProductDetail getProductDetail(eProduct _parentProd
                                                   ,int _iCollectionID
                                                   ,String _strEntityType
                                                   ,int _iEntityID
                                                   ,int _iGSequence
                                                   ,int _iGVisible
                                                   ,int _iISequence
                                                   ,int _iIVisible
                                                   ,String _strHeritage
                                                   ,String _strAttCode
                                                   ,String _strAttType
                                                   ,String _strFlagCode
                                                   ,String _strAttValue
                                                   ,String _strPublishFlag
                                                   ,String _strValFrom
                                                   ,int _iRecID) throws MiddlewareRequestException, MiddlewareException, SQLException {
        String strKey = _parentProd.getProfile().getEnterprise()+":"+_parentProd.getStringVal(eProduct.ENTITYTYPE)+":"+_parentProd.getIntVal(eProduct.ENTITYID)+":"+_parentProd.getIntVal(eProduct.NLSID)+":"+_strEntityType+":"+_iEntityID+":"+_strHeritage+":"+_strAttCode+(_strAttType.equals("F")?":"+_strFlagCode:"");
        eProductDetail pd = (eProductDetail)m_hashRecords.get(strKey);
        if(pd != null) {
            return pd;
        }
        pd = new eProductDetail(_parentProd
                               ,_parentProd.getProfile()
                               ,_strEntityType
                               ,_iEntityID
                               ,_iGSequence
                               ,_iGVisible
                               ,_iISequence
                               ,_iIVisible
                               ,_strHeritage
                               ,_strAttCode
                               ,_strAttType
                               ,_strFlagCode
                               ,_strAttValue
                               ,_strPublishFlag
                               ,_strValFrom
                               ,_iRecID);
        m_hashRecords.put(strKey,pd);
        return pd;
    }




/**
 * PRODUCTDETAILLONG table flavor
 */
    protected final eProductDetail getProductDetail(eProduct _parentProd
                                                   ,int _iCollectionID
                                                   ,String _strEntityType
                                                   ,int _iEntityID
                                                   ,int _iGSequence
                                                   ,int _iGVisible
                                                   ,int _iISequence
                                                   ,int _iIVisible
                                                   ,String _strHeritage
                                                   ,String _strAttCode
                                                   ,String _strAttValue
                                                   ,String _strPublishFlag
                                                   ,String _strValFrom
                                                   ,int _iLongCollectionID
                                                   ,int _iRecID) throws MiddlewareRequestException, MiddlewareException, SQLException {

        String strKey = _parentProd.getProfile().getEnterprise()+":"+_parentProd.getStringVal(eProduct.ENTITYTYPE)+":"+_parentProd.getIntVal(eProduct.ENTITYID)+":"+_parentProd.getIntVal(eProduct.NLSID)+":"+_strEntityType+":"+_iEntityID+":"+_strHeritage+":"+_strAttCode;
        eProductDetail pd = (eProductDetail)m_hashRecords.get(strKey);
        if(pd != null) {
            return pd;
        }
        pd = new eProductDetail(_parentProd
                               ,_parentProd.getProfile()
                               ,_strEntityType
                               ,_iEntityID
                               ,_iGSequence
                               ,_iGVisible
                               ,_iISequence
                               ,_iIVisible
                               ,_strHeritage
                               ,_strAttCode
                               ,_strAttValue
                               ,_strPublishFlag
                               ,_strValFrom
                               ,_iLongCollectionID
                               ,_iRecID);
         m_hashRecords.put(strKey,pd);
         return pd;
    }


/**
 * PRODUCTDETAILBLOB table flavor
 */
    protected final eProductDetail getProductDetail(eProduct _parentProd
                         ,int _iCollectionID
                         ,String _strEntityType
                         ,int _iEntityID
                         ,int _iGSequence
                         ,int _iGVisible
                         ,int _iISequence
                         ,int _iIVisible
                         ,String _strHeritage
                         ,String _strAttCode
                         ,String _strAttFileName
                         ,COM.ibm.opicmpdh.objects.Blob _blob
                         ,String _strPublishFlag
                         ,String _strValFrom
                         ,int _iBlobCollectionID
                         ,int _iRecID) throws MiddlewareRequestException, MiddlewareException, SQLException {

        String strKey = _parentProd.getProfile().getEnterprise()+":"+_parentProd.getStringVal(eProduct.ENTITYTYPE)+":"+_parentProd.getIntVal(eProduct.ENTITYID)+":"+_parentProd.getIntVal(eProduct.NLSID)+":"+_strEntityType+":"+_iEntityID+":"+_strHeritage+":"+_strAttCode;
        eProductDetail pd = (eProductDetail)m_hashRecords.get(strKey);
        if(pd != null) {
            return pd;
        }
        pd = new eProductDetail(_parentProd
                               ,_parentProd.getProfile()
                               ,_strEntityType
                               ,_iEntityID
                               ,_iGSequence
                               ,_iGVisible
                               ,_iISequence
                               ,_iIVisible
                               ,_strHeritage
                               ,_strAttCode
                               ,_strAttFileName
                               ,_blob
                               ,_strPublishFlag
                               ,_strValFrom
                               ,_iBlobCollectionID
                               ,_iRecID);
        m_hashRecords.put(strKey,pd);
        return pd;
    }



    /*private final boolean containsRecord(eTableRecord _record) {
        return (m_hashRecords.get(_record.getKey()) != null);
    }*/

}





