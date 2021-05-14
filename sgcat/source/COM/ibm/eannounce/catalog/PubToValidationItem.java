//
// $Log: PubToValidationItem.java,v $
// Revision 1.3  2011/05/05 11:21:34  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:28  jingb
// no message
//
// Revision 1.2  2006/05/01 19:46:05  gregg
// pub to validation item
//
// Revision 1.1  2006/05/01 19:45:14  gregg
// initial load
//
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


public class PubToValidationItem {

    private String m_strProdEntityType = null;
	private int m_iProdEntityID = -1;
    private String m_strStructEntityType = null;
    private int m_iStructEntityID = -1;
    private String m_strFeatEntityType = null;
	private int m_iFeatEntityID = -1;
	private String m_strPubToDate = null;
	private String m_strWithdrawDate = null;
	private int m_iNLSID = -1;

	public static final void main(String[] _args) {

	}

	public PubToValidationItem(String _strProdEntityType
	                          ,int _iProdEntityID
	                          ,String _strStructEntityType
	                          ,int _iStructEntityID
	                          ,String _strFeatEntityType
	                          ,int _iFeatEntityID
	                          ,String _strPubToDate
	                          ,String _strWithdrawDate
	                          ,int _iNLSID) {
        m_strProdEntityType = _strProdEntityType;
	    m_iProdEntityID = _iProdEntityID;
        m_strStructEntityType = _strStructEntityType;
        m_iStructEntityID = _iStructEntityID;
        m_strFeatEntityType = _strFeatEntityType;
		m_iFeatEntityID = _iFeatEntityID;
	    m_strPubToDate = _strPubToDate;
	    m_strWithdrawDate = _strWithdrawDate;
		m_iNLSID = _iNLSID;
	}


    public String toString() {
		return m_strProdEntityType + ":" +
			   m_iProdEntityID + ":" +
			   m_strStructEntityType + ":" +
			   m_iStructEntityID + ":" +
			   m_strFeatEntityType + ":" +
			   m_iFeatEntityID + ":" +
			   m_strPubToDate + ":" +
			   m_strWithdrawDate + ":" +
			   m_iNLSID;
	}

	public boolean validate() {
		boolean bValid = true;
		if(m_strPubToDate.compareTo(m_strWithdrawDate) < 0) {
			bValid = false;
		}
        return bValid;
	}

	public void notify(Database _db) throws SQLException, MiddlewareException {
        D.ebug(D.EBUG_WARN,"notify() for ite:" + toString());
 	}

}
