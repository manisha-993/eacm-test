/*
 * Created on May 31, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */

package COM.ibm.eannounce.catalog;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Given a product object, this will return the list of compatible products
 * as a collection of 'light' product objects
 
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompatibleProdCollection {

    private ArrayList m_compatList = null; //This will hold the list of products (use instead of ProductCollection)          
    /**
     * CompatibleProdCollection
     *
     * @param _product
     */
    public CompatibleProdCollection(Product _product) {
        getCompatibleProducts();
    }

    /**
     * getCompatibleProducts
     *
     * @return
     */
    public ArrayList getCompatibleProducts() {

        if (m_compatList == null) {
            setpcCompatibleProd();
        }
        return m_compatList;
    }

    private void setpcCompatibleProd() {
        // Create the product collection object
        //Query the db to get the compatible objects and return the collection of products
        //Create the Product objects and add to the ProductCollection

        String strWWPnumber = null;
        String strOS_FC = null;
        int iLprodId = 0;

        m_compatList = new ArrayList();
        /*         
                select OS_FC,
                       WWPARTNUMBERTO,
                       LPRODUCTIDTO,
                       MARKETEX,
                       VALFROM,
                       VALTO,
                       ISACTIVE
                 FROM GBLI.PRODCOMPAT 
                 WHERE 
                  Enterprise=? AND 
                  COUNTRYCODE=? AND
                  WWPARTNUMBERFROM =? AND
                  LPRODUCTIDFROM = ? AND 
                  ISACTIVE = 'YES'
                            
         */
        Database dbProd = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {

            dbProd = new Database(); //Dont know where to get this from except to make one
            //rs = call the sp above
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;

            dbProd.commit();
            dbProd.freeStatement();
            dbProd.isPending();

            for (int i = 0; i < rdrs.size(); i++) {
                //I dont know what to do with OS_FC
                strOS_FC = rdrs.getColumn(i, 0).trim();

                strWWPnumber = rdrs.getColumn(i, 1).trim();
                iLprodId = rdrs.getColumnInt(i, 2);
                /* ProductId myProd = new ProductId(m_strEnterprise, 
                                                     m_strCountryCode,
                                                     m_strLanguageCode, 
                                                     m_iNLSId,
                                                     m_strPartNumber,
                                                     m_strLProdid,
                                                     m_strOS,
                                                     strOS_FC, 
                                                     m_strEntityType, 
                                                     m_iEntityID);         //Create the ProductId object               
                 */
                //m_compatList.add(myProd);
            }
        } catch (SQLException se) {
            D.ebug(D.EBUG_DETAIL, se.getMessage());
            se.printStackTrace();
        } finally {
            dbProd = null;
        }

    }
}

//$Log: CompatibleProdCollection.java,v $
//Revision 1.2  2011/05/05 11:21:32  wendy
//src from IBMCHINA
//
//Revision 1.1.1.1  2007/06/05 02:09:12  jingb
//no message
//
//Revision 1.1.1.1  2006/03/30 17:36:28  gregg
//Moving catalog module from middleware to
//its own module.
//
//Revision 1.8  2005/06/01 03:32:14  dave
//JTest clean up
//
//Revision 1.7  2005/05/26 07:20:10  dave
//new SP and introduction of the Catalog Object
//
//Revision 1.6  2005/05/19 23:18:46  dave
//more commenting
//
//Revision 1.5  2005/05/19 16:16:08  dave
//tm comment out to fix
//
//Revision 1.4  2005/05/17 19:23:24  bala
//fix
//
//Revision 1.3  2005/05/17 18:32:53  bala
//cleanup
//
//Revision 1.2  2005/05/17 18:13:17  bala
//fixes
//
//Revision 1.1  2005/05/16 18:44:29  bala
//First cut at this
//
