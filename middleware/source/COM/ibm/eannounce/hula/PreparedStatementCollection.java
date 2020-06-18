//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PreparedStatementCollection.java,v $
// Revision 1.13  2004/10/07 05:21:00  dave
// fixed the close all statement
//
// Revision 1.12  2004/09/24 22:00:19  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.11  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.10  2004/09/22 21:09:44  gregg
// reset Vector, Hashtable in closeAll()
//
// Revision 1.9  2004/09/21 17:14:40  gregg
// debug on putPS
//
// Revision 1.8  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.7  2004/09/14 18:04:10  gregg
// product/productdetail query keys
//
// Revision 1.6  2004/09/13 17:16:34  gregg
// another fix
//
// Revision 1.5  2004/09/13 17:08:27  gregg
// null ptr fix
//
// Revision 1.4  2004/09/13 16:45:17  gregg
// PRODUCTDETAIL_DELETE_FC
//
// Revision 1.3  2004/09/13 16:19:02  gregg
// static
//
// Revision 1.2  2004/09/13 15:58:39  gregg
// closeAll
//
// Revision 1.1  2004/09/13 15:53:32  gregg
// initial load
//
//
//

package COM.ibm.eannounce.hula;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;


public class PreparedStatementCollection {

    public static final String PRODUCTDETAIL_DELETE     = "PRODUCTDETAIL_DELETE";
    public static final String PRODUCTDETAIL_INSERT     = "PRODUCTDETAIL_INSERT";
    public static final String PRODUCT_QUERY            = "PRODUCT_QUERY";
    public static final String PRODUCT_EXISTS_QUERY            = "PRODUCT_EXISTS_QUERY";
    public static final String PRODUCT_INSERT           = "PRODUCT_INSERT";
    public static final String PRODUCT_DELETE           = "PRODUCT_DELETE";
    public static final String PRODUCTDETAIL_QUERY      = "PRODUCTDETAIL_QUERY";
    public static final String PRODUCTDETAILLONG_QUERY  = "PRODUCTDETAILLONG_QUERY";
    public static final String PRODUCTDETAILBLOB_QUERY  = "PRODUCTDETAILBLOB_QUERY";
    public static final String PRODUCTDETAILLONG_DELETE = "PRODUCTDETAILLONG_DELETE";
    public static final String PRODUCTDETAILBLOB_DELETE = "PRODUCTDETAILBLOB_DELETE";
    public static final String PRODUCTDETAILLONG_INSERT = "PRODUCTDETAILLONG_INSERT";
    public static final String PRODUCTDETAILBLOB_INSERT = "PRODUCTDETAILBLOB_INSERT";
    public static final String BLOB_EXISTS_QUERY        = "BLOB_EXISTS_QUERY";


    //private static PreparedStatementCollection c_psc = new PreparedStatementCollection();

    private Hashtable m_hash = null;
    private Vector m_vct = null;

    protected PreparedStatementCollection() {
        m_hash = new Hashtable();
        m_vct = new Vector();
    }

    //public static final PreparedStatementCollection getCollection() {
      //  return c_psc;
    //}

    public final void putPS(String _strKey, PreparedStatement _ps) {
        //eProductUpdater.debug("putPS:" + _strKey + ":" + _ps.toString());
        m_hash.put(_strKey,_ps);
        m_vct.addElement(_ps);
    }

    public final PreparedStatement getPS(String _strKey) {
        return (PreparedStatement)m_hash.get(_strKey);
    }


    public final void closeAll() throws SQLException {
        for(int i = 0; i < m_vct.size(); i++) {
            PreparedStatement ps = (PreparedStatement)m_vct.elementAt(i);
            ps.close();
            ps = null;
        }
        m_hash = new Hashtable();
        m_vct = new Vector();
    }

}
