//
// $Log: SalesStatusItem.java,v $
// Revision 1.2  2011/05/05 11:21:32  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:29  jingb
// no message
//
// Revision 1.1.1.1  2006/03/30 17:36:31  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.8  2006/03/07 18:42:17  gregg
// removing 9306 due to rmi bloating
//
// Revision 1.7  2006/03/07 18:12:45  gregg
// debuig
//
// Revision 1.6  2006/03/07 00:27:29  gregg
// more date stuff
//
// Revision 1.5  2006/03/07 00:05:05  gregg
// more valfrom/valto stuff
//
// Revision 1.4  2006/03/06 23:39:37  gregg
// ps fix
//
// Revision 1.3  2006/03/06 23:12:27  gregg
// update logic
//
// Revision 1.2  2006/03/06 21:40:34  gregg
// update
//
// Revision 1.1  2006/03/06 21:15:50  gregg
// initial load
//
//

package COM.ibm.eannounce.catalog;

import java.sql.*;

import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Enumeration;


public class SalesStatusItem {

    private String m_strVARCONDTYPE = null;
    private String m_strMATNR = null;
    private String m_strLASTUPDATED = null;

	public static final void main(String[] _args) {

	}

	public SalesStatusItem(String _strVARCONDTYPE, String _strMATNR, String _strLASTUPDATED) {
		m_strVARCONDTYPE = _strVARCONDTYPE;
		m_strMATNR = _strMATNR;
		m_strLASTUPDATED = _strLASTUPDATED;
	}

    public String getVARCONDTYPE() {
		return m_strVARCONDTYPE;
	}

	public String getMATNR() {
		return m_strMATNR;
	}

	public String getLASTUPDATED() {
        return m_strLASTUPDATED;
    }

    public String toString() {
		return getVARCONDTYPE() + ":" + getMATNR() + ":" + getLASTUPDATED();
	}

	public void update(Database _db, String _strValTo) throws SQLException, MiddlewareException {
		String strEntity = "select distinct locentitytype, locentityid from gbli.product where partnumber = ?";
		String strUpdateProduct = "update gbli.product set pubtodate = ?, valfrom = ? where locentitytype = ? and locentityid = ?";
		String strUpdateProdStruct = "update gbli.prodstruct set pubtodate = ?, valfrom = ? where prodentitytype = ? and prodentityid = ?";

		PreparedStatement psEntity = null;
		PreparedStatement psProduct = null;
		PreparedStatement psProdStruct = null;

		Connection con = _db.getPDHConnection();

		psEntity = con.prepareStatement(strEntity);
		psProduct = con.prepareStatement(strUpdateProduct);
		psProdStruct = con.prepareStatement(strUpdateProdStruct);

		ResultSet rs = null;

        try {
            psEntity = con.prepareStatement(strEntity);
            psEntity.setString(1,getMATNR());
            rs = psEntity.executeQuery();

            String strEntityType = null;
            int iEntityID = -1;

            while(rs.next()) {
				strEntityType = rs.getString(1);
				iEntityID = rs.getInt(2);
				D.ebug(D.EBUG_DETAIL,"SalesStatus Entity found:" + strEntityType + ":" + iEntityID);
			}

			if(strEntityType != null) {

				D.ebug(D.EBUG_SPEW,"SalesStatus updateing with:" + getLASTUPDATED() + ":" + _strValTo + ":" + strEntityType + ":" + iEntityID);

				psProduct.setString(1,getLASTUPDATED());
				psProduct.setString(2,_strValTo);
				psProduct.setString(3,strEntityType);
				psProduct.setInt(4,iEntityID);
			    psProduct.executeUpdate();

				psProdStruct.setString(1,getLASTUPDATED());
				psProdStruct.setString(2,_strValTo);
				psProdStruct.setString(3,strEntityType);
				psProdStruct.setInt(4,iEntityID);
			    psProdStruct.executeUpdate();
			}

        }
        finally {
			if(rs != null) {
				rs.close();
				rs = null;
			}
            psEntity.close();
            psEntity = null;
            psProduct.close();
            psProduct = null;
            psProdStruct.close();
            psProdStruct = null;
        }
	}

}
